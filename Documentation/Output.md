(This file is mostly for my future-self if I happen to forget these details)

## Output file format

Any files compressed by the Huffman class can also be decompressed by the Huffman class any time. The output file includes all
the necessary information to do so.

### Binary tree

The binary tree used to compress characters is included at the start of the file. Since every node in the tree is either a leaf or has two children
it can be expressed using only 1's and 0's.

The nodes are listed in pre-order.

* 0 corresponds to a inner-node (a node which has two children)
* 1 corresponds to a leaf

After each leaf, the next 8 bits express the character, encoded in standard UTF-8.

### Separator

After the binary tree there is a string of 9 1's (1111 1111 1). This marks the end of the binary tree.
This works because while parsing the binary tree we expect a UTF-8 encoded character after a 1 (1 being a leaf). However 0xFF is not a valid UTF-8
character and thus follows that we have hit the separator.

### Compressed data

The actual compressed data follows the separator. This data can be decompressed using the binary tree.

## Caveats
As of 12.11.2020:
The described binary string containing the tree, separator and data is constructed as a String inside the class. However data can only be written into
files as chunks of 8-bits. The length of the constructed string might not be divisible by 8 in which case a few 0's will be concatenated to the end of the
string before writing. It follows that in some instances 1-3 extra characters might appear at the end of the string when it is decompressed.
