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

import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.CmdLineException;

public class ListExtractor implements EntityDocumentProcessor {

    @Option(name="-typePath", usage="File with entity types")
    private String type_path = "wiki_types.txt";

    @Option(name="-langPath", usage="File with languages")
    private String lang_path = "wiki_lang.txt";

    @Option(name="-allLang", usage="Keep all languages")
    private Boolean all_lang = false;

    @Option(name="-verbose", usage="Verbose status updates")
    private Boolean verbose = false;

    class GazetteerEntry {
        public ItemIdValue id;

        public String name;
        public List<MonolingualTextValue> aliases;

        public GazetteerEntry(ItemIdValue id,
                              String name,
                              List<MonolingualTextValue> aliases) {
            this.id = id;
            this.name = name;
            this.aliases = aliases;
        }
    }

    int nprocessed = 0;
    Map<String, String> type_label_map = Maps.newHashMap();
    Set<String> lang_set = Sets.newHashSet();
    List<String> language_codes = Lists.newArrayList();
    HashMap<String, HashMap<String, ArrayList<GazetteerEntry>>> multilingual_entries = Maps.newHashMap();
    Set<ItemIdValue> type_set = Sets.newHashSet();

    public ListExtractor() throws IOException {
        readLangTypes(lang_path);
        readEntityTypes(type_path);
    }

    public static void main(String[] args) throws IOException {
        ListExtractor processor = new ListExtractor();
        final CmdLineParser parser = new CmdLineParser(processor);
        parser.setUsageWidth(80);
        try {
            parser.parseArgument(args);
        } catch( CmdLineException e ) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return;
        }
        Helper.configureLogging();
        Helper.processEntitiesFromWikidataDump(processor);
        processor.writeFinalResults();
    }

    private void readEntityTypes(String filePath) throws IOException {
        final File file = new File(filePath);
        for(String line : Files.readLines(file, Charsets.UTF_8)) {
            Iterable<String> iter = Splitter.on(" ").trimResults().omitEmptyStrings().split(line);
            String typeId = Iterables.getFirst(iter, null);
            String typeStr = Iterables.getLast(iter, null);
            this.type_label_map.put(typeId, typeStr);
            this.type_set.add( Datamodel.makeWikidataItemIdValue(typeId) );
            for(String lang : multilingual_entries.keySet()) {
                ArrayList<GazetteerEntry> entries = Lists.newArrayList();
                multilingual_entries.get(lang).put(typeId, entries);
            }
        }
    }

    private void addLang(String lang) {
        HashMap<String, ArrayList<GazetteerEntry>> gaz_entries = Maps.newHashMap();
        for(String typeId : this.type_label_map.keySet()) {
            ArrayList<GazetteerEntry> type_entries = Lists.newArrayList();
            gaz_entries.put(typeId, type_entries);
        }
        this.multilingual_entries.put(lang, gaz_entries);
        this.lang_set.add(lang);
        this.language_codes.add(lang);
    }

    private void readLangTypes(String filePath) throws IOException {
        final File file = new File(filePath);
        for(String line : Files.readLines(file, Charsets.UTF_8)) {
            Iterable<String> iter = Splitter.on(" ").trimResults().omitEmptyStrings().split(line);
            String lang = Iterables.getFirst(iter, null);
            this.lang_set.add(lang);
            this.language_codes.add(lang);
            HashMap<String, ArrayList<GazetteerEntry>> entries = Maps.newHashMap();
            this.multilingual_entries.put(lang, entries);
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
            for(String lang : labels.keySet()) {
                if(multilingual_entries.get(lang) == null) {
                    if(this.all_lang == false) continue;
                    else {
                        addLang(lang);
                    }
                }

                HashMap<String, ArrayList<GazetteerEntry>> entries = multilingual_entries.get(lang);
                String name = labels.get(lang).getText();
                List<MonolingualTextValue> aliases = itemDocument.getAliases().get(lang);
                GazetteerEntry entry = new GazetteerEntry(itemDocument.getItemId(), name, aliases);
                if(entries.get(type) == null) {
                    System.err.println("type " + type + " not in hash");
                    System.exit(1);
                }
                entries.get(type).add(entry);
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
        for(String code : language_codes) {
            int total = 0;
            HashMap<String, ArrayList<GazetteerEntry>> gaz_entries = multilingual_entries.get(code);
            for(String key : gaz_entries.keySet()) {
                int size = gaz_entries.get(key).size();

                if(size == 0) continue;

                String label = this.type_label_map.get(key);
                assert( label != null );
                if(label == null) {
                    System.out.println("Missing key: " + key);
                    System.exit(1);
                }
                if(this.verbose) {
                    System.out.println(code + " " + key + " " + label + " " + size);
                }
                total += size;
            }

            if(total > 0) System.out.println(code + " total entries = " + total);
        }
    }

    public void printGaz(PrintStream out, ArrayList<GazetteerEntry> gaz, String label) {
        for(GazetteerEntry entry : gaz) {
            out.print(entry.id.getId() + "\t" + label + "\t" + entry.name);
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
                HashMap<String, ArrayList<GazetteerEntry>> gaz_entries = multilingual_entries.get(code);

                for(String key : gaz_entries.keySet()) {
                    printGaz(out, gaz_entries.get(key), this.type_label_map.get(key));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            // Print counts
            try (PrintStream out = new PrintStream(Helper.openExampleFileOuputStream(code + "_gazetteer_counts.txt"))) {
                HashMap<String, ArrayList<GazetteerEntry>> gaz_entries = multilingual_entries.get(code);
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
