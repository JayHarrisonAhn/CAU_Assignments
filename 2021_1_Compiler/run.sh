#!/bin/bash

python3 analyzer/lexical_analyzer.py $1 $1.lex_out
python3 analyzer/syntax_analyzer.py $1.lex_out