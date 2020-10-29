# Specification document

The goal of this project is to implement two algorithms by which information can be compressed. Decompressing should also be possible.
The two methods chosen for this task are:
* Huffman coding
* Lempel-Ziv-Welch 

## Required data structures

For Huffman coding at least the following data structures will be required:
* Binary Tree
* Priority Queue (Will most likely be implemented using a binary heap)
* Queue

For Lempel-Ziv-Welch at least a hash table is requred.

Further data structures will also be implemented if needed.

## Input / Output
The program will receive a .txt file. The contents of the .txt file will then either be compressed or decompressed into a new .txt file. 

The program will be usable from a console, a simple GUI might be implemented with JavaFX but this a low priority.

## Target time- and spacecomplexity

For Huffman coding the compression should be doable in O(n logn) while the spacecomplexity would be O(n) where n is the number of symbols in the .txt file.

For Lempel-Ziv-Welch the compression should be doable in O(n) where n is the number of symbols in the .txt file. No single target space complexity is specified for
LZW since it doesn't depend on the length of the .txt file but instead on the amount of unique substrings in it.

## Sources

As a quick introduction to the topics wikipedia was used:
* [Huffman coding](https://en.wikipedia.org/wiki/Huffman_coding)
* [Lempel-Ziv-Welch](https://en.wikipedia.org/wiki/Lempel–Ziv–Welch)

## Other

All code and documentation will be written in English.

Java will be used to implement the algorithms.

This course will be used as a part of a TKT (Tietojenkäsittelytieteen kandidaatti) degree.
