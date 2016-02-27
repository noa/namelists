#! /usr/bin/env bash

set -e

mvn compile
mvn exec:java -Dexec.mainClass="edu.jhu.ListExtractor" -Dexec.args="-typePath wiki_types.txt -langPath wiki_lang.txt"

# eof
