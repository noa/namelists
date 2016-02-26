package edu.jhu;

import edu.jhu.Helper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.interfaces.EntityDocumentProcessor;
import org.wikidata.wdtk.datamodel.interfaces.EntityIdValue;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.MonolingualTextValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.SiteLink;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.datamodel.interfaces.StatementGroup;
import org.wikidata.wdtk.datamodel.interfaces.Value;
import org.wikidata.wdtk.datamodel.interfaces.ValueSnak;

import com.google.common.collect.Lists;
import com.google.common.base.Charsets;
import com.google.common.collect.Iterables;
import com.google.common.io.Files;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.base.Splitter;

public class ListExtractor implements EntityDocumentProcessor {
    int nprocessed = 0;

    Map<String, String> type_label_map = Maps.newHashMap();
    Set<String> lang_set = Sets.newHashSet();

    class GazetteerEntry {
        public ItemIdValue id;
        public String name;
        public SiteLink link;
        public List<MonolingualTextValue> aliases;

        public GazetteerEntry(ItemIdValue id, String name,  List<MonolingualTextValue> aliases, SiteLink link) {
            this.id = id;
            this.name = name;
            this.aliases = aliases;
            this.link = link;
        }
    }

    Map<String, Long> inlinks = Maps.newHashMap();
    List<String> language_codes = Lists.newArrayList();
    HashMap<String, ArrayList<GazetteerEntry>> gaz_entries = Maps.newHashMap();
    Set<ItemIdValue> type_set = Sets.newHashSet();

    public ListExtractor(String type_path, String lang_path) throws IOException {
        readEntityTypes(type_path);
        readLangTypes(lang_path);

        System.exit(0);
        
        // language_codes.add("en");

        // int ntypes = 0;
        // for(String id : SimpleGazetteerExtractor.type_label_map.keySet()) {
        //     type_set.add( Datamodel.makeWikidataItemIdValue(id) );
        //     ntypes ++;
        // }
        // System.out.println(ntypes + " types");
        //System.exit(1);
    }

    public static void main(String[] args) throws IOException {
        Helper.configureLogging();
        ListExtractor processor = new ListExtractor("wiki_types.txt", "wiki_lang.txt");
        Helper.processEntitiesFromWikidataDump(processor);
        processor.writeFinalResults();
    }

    public void readEntityTypes(String filePath) throws IOException {
        final File file = new File(filePath);
        for(String line : Files.readLines(file, Charsets.UTF_8)) {
            Iterable<String> iter = Splitter.on(" ").trimResults().omitEmptyStrings().split(line);
            String typeId = Iterables.getFirst(iter, "NIL");
            String typeStr = Iterables.getLast(iter, "NIL");
            this.type_label_map.put(typeId, typeStr);
        }
    }

    public void readLangTypes(String filePath) throws IOException {
        final File file = new File(filePath);
        for(String line : Files.readLines(file, Charsets.UTF_8)) {
            Iterable<String> iter = Splitter.on(" ").trimResults().omitEmptyStrings().split(line);
            String lang = Iterables.getFirst(iter, "NIL");
            this.lang_set.add(lang);
        }
    }

    public boolean matchSet(StatementGroup statementGroup, Set<ItemIdValue> set) {
        for(ItemIdValue id : set) {
            if(containsValue(statementGroup, id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void processItemDocument(ItemDocument itemDocument) {

        // Print status once in a while
        if (this.nprocessed % 100000 == 0) {
            printStatus();
		}
        this.nprocessed++;

        Map<String, Boolean> type_keep = Maps.newHashMap();
        for (StatementGroup sg : itemDocument.getStatementGroups()) {
            EntityIdValue subject = sg.getSubject();
            if(inlinks.containsKey(subject.getId())) {
                inlinks.put(subject.getId(), inlinks.get(subject.getId())+1);
            } else {
                inlinks.put(subject.getId(), new Long(1));
            }

            switch (sg.getProperty().getId()) {
            case "P31": // P31 is "instance of"
                for(ItemIdValue t : type_set) {
                    boolean match = matchSet(sg, Sets.newHashSet(t));
                    type_keep.put(t.getId(), match);
                }

                break;
            }
        }

        for(String type : type_keep.keySet()) {
            if (!type_keep.get(type)) continue;

            Map<String, MonolingualTextValue> labels = itemDocument.getLabels();
            if ( !labels.containsKey("en") ) continue;
            String name = labels.get("en").getText();
            SiteLink link = itemDocument.getSiteLinks().get("enwiki");
            if (link == null) continue;
            
            GazetteerEntry entry = new GazetteerEntry(itemDocument.getItemId(), name, itemDocument.getAliases().get("en"), link);
            
            if(gaz_entries.containsKey(type)){
                gaz_entries.get(type).add(entry);
            } else {
                ArrayList<GazetteerEntry> es = Lists.newArrayList(entry);
                gaz_entries.put(type, es);
            }
        }
    }

    @Override
    public void processPropertyDocument(PropertyDocument propertyDocument) {
        // TODO Auto-generated method stub

    }

    /**
     * Prints the current status to the system output.
     */
    private void printStatus() {
        for(String code : Arrays.asList("en")) {
            int total = 0;
            for(String key : gaz_entries.keySet()) {
                int size = gaz_entries.get(key).size();
                String label = this.type_label_map.get(key);
                assert( label != null );
                if(label == null) {
                    System.out.println("Missing key: " + key);
                    System.exit(1);
                }
                System.out.println(code + " " + key + " " + label + " " + size);
                total += size;
            }
            System.out.println("total entries = " + total);
        }
    }

    public void printGaz(PrintStream out, ArrayList<GazetteerEntry> gaz, String label) {
        for(GazetteerEntry entry : gaz) {
            long ninlinks = 0;
            if(inlinks.containsKey(entry.id.getId())) {
                ninlinks = inlinks.get(entry.id.getId());
            }
            out.print(entry.id.getId() + "\t" + ninlinks + "\t" + label + "\t" + entry.name);
            if(entry.aliases != null) {
                for(MonolingualTextValue a : entry.aliases) {
                    out.print("\t" + a.getText());
                }
            }
            out.println("");
        }
    }

    public void writeFinalResults() {
        printStatus();

        for(String code : language_codes) {
            // Print the gazetteer
            try (PrintStream out = new PrintStream(Helper.openExampleFileOuputStream(code + "_gazetteer.txt"))) {

                for(String key : gaz_entries.keySet()) {
                    printGaz(out, gaz_entries.get(key), this.type_label_map.get(key));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            // Print counts
            try (PrintStream out = new PrintStream(Helper.openExampleFileOuputStream(code + "_gazetteer_counts.txt"))) {

                for(String key : gaz_entries.keySet()) {
                    //printGaz(out, gaz_entries.get(key), SimpleGazetteerExtractor.type_label_map.get(key));
                    out.println(this.type_label_map.get(key) + " " + gaz_entries.get(key).size());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if the given group of statements contains the given value as the
     * value of a main snak of some statement.
     *
     * @param statementGroup
     *            the statement group to scan
     * @param value
     *            the value to scan for
     * @return true if value was found
     */
    private boolean containsValue(StatementGroup statementGroup, Value value) {
        // Iterate over all statements
        for (Statement s : statementGroup.getStatements()) {
            // Find the main claim and check if it has a value
            if (s.getClaim().getMainSnak() instanceof ValueSnak) {
                Value v = ((ValueSnak) s.getClaim().getMainSnak()).getValue();
                // Check if the value is an ItemIdValue
                if (value.equals(v)) {
                    return true;
                }
            }
        }
        return false;
    }

}
