# Wikidata Namelists

A Java program to download name lists from Wikidata using the
[Wikidata Toolkit](https://github.com/Wikidata/Wikidata-Toolkit). The
resulting lists contain both the entity names as well as known
aliases.

# Compilation

Via Maven:

```bash
mvn compile
```

# Usage

To get lists for the entity types listed in `wiki_types.txt` and the
languages listed in `wiki_lang.txt`, run:

```bash
$ mvn exec:java -Dexec.mainClass="edu.jhu.ListExtractor" -Dexec.args="-typePath wiki_types.txt -langPath wiki_lang.txt"
```

Note that before anything is downloaded, a Wikidata dump is downloaded
to the `dumpfiles` directory, which may take a while.

Optionally, to get names in *all languages* use the `-allLang` flag:

```bash
$ mvn exec:java -Dexec.mainClass="edu.jhu.ListExtractor" -Dexec.args="-typePath wiki_types.txt -allLang"
```

The results are placed under `results`, which will contain
subdirectories specific to each Wikidata dump. Each subdirectory will
contain `CODE_gazetteer.txt` and `CODE_gazetteer_counts.txt`, where
CODE is a Wikipedia language code.
