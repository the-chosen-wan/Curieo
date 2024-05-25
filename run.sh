#!/bin/sh

if [ "$#" -ne 2 ]; then
  echo "Usage: $0 <input-file> <output-file>"
  exit 1
fi

INPUT_FILE=$1
OUTPUT_FILE=$2

javac "Curieo/Test.java"

java "Curieo/Test" < "$INPUT_FILE" > "$OUTPUT_FILE"