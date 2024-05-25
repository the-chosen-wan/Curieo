#!/bin/bash
if [ "$#" -ne 2 ]; then
  echo "Usage: $0 <input-file> <output-file>"
  exit 1
fi


IMAGE_NAME="thelegitjet/curieo:latest"

if docker image inspect "$IMAGE_NAME" &> /dev/null; then
    echo "Docker Image already pulled"
else
    docker pull "$IMAGE_NAME"
fi

INPUT_FILE=$1
OUTPUT_FILE=$2

if [ ! -f "$INPUT_FILE" ]; then
    echo "Input File does not exist"
    exit 1
fi


if [ ! -f "$OUTPUT_FILE" ]; then
    touch "$OUTPUT_FILE"
fi

docker run --rm  -v $(pwd)/$"$INPUT_FILE":/app/input.txt -v $(pwd)/"$OUTPUT_FILE":/app/output.txt thelegitjet/curieo  input.txt output.txt