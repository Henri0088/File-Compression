## Implementation document

### General structure

The classes of interest are Huffman and LZW which compress UTF-8 encoded data using Huffman coding and the LZW algorithm respectively. 
Both classes have compress and decompress methods which do exactly as the name suggests. Both take as input a UTF-8 encoded String and output
a String, which only has 1's and 0's. A separate BitWriter class is used to write this String into a file bit by bit instead of 
UTF-8 encoding each 1 and 0.

At the moment the program has a very barebones UI which allows the user to compress any file they want, or use the default test.txt which has some "Lorem
impsum.." inside. The compressed data is written into either 'HuffCompressed' or 'LZWCompressed' depending on the algorithm chosen.

'HuffCompressed' and 'LZWCompressed' can also be decompressed back to 'HuffDecompressed' and 'LZWDecompressed' respectively.


### Time- and spacecomplexity analysis

#### Lempel-Ziv-Welch compression

The LZW compression algorithm reads each character from the input string once. In the worst case each new character c concatenated to the previos ones
form a new substring. In this case the substring without c is encoded. However the 'get' operation of CHashMap works in O(1) assuming that the internal hash function uniformly distributes keys. Also if the CHashMap is not full, a new entry is added. This is also a O(1) operation.

So n characters are looped over and some O(1) operations are done to each of them. So the timecomplexity of the LZW compression is O(n).

#### Lempel-Ziv-Welch decompression

The LZW decompression works similarly to compression, it loops over the input string once and does some O(1) operations. So the timecomplexity of LZW decompression is also O(n)

However in practice the decompression is faster than the compression algorithm. This is because while the compression algorithm uses a hashmap, the decompression algorithm uses a regular array for all its operations. This means that the decompression algorithm doesn't need to calculate hashes for example, resulting in smaller constant factors.

#### Huffman

TODO

### Performance comparison

Below are the results of performance testing, all times are in ms.

| File size | File description | LZW Compress | LZW Decompress | Huff Compress | Huff Decompress |
|-----------|------------------|--------------|----------------|---------------|-----------------|
| 4130      | Lorem ipsum      | 46           | -              | 76            | -               |
| 16356     | Lorem ipsum      | 81           | -              | 166           | -               |
| 65372     | Lorem ipsum      | 312          | -              | 1310          | -               |
| 152089    | alice29.txt      | 1826         | 424            | 5999          | 27437           |


Below is the alice29.txt test again but with both algorithms using CHashMap instead of the default Java one.

| File size | File description | LZW Compress | LZW Decompress | Huff Compress | Huff Decompress |
|-----------|------------------|--------------|----------------|---------------|-----------------|
| 152089    | alice29.txt      | 2442         | 455            | 5987          | 27425           |


Lorem ipsum was generated [here](https://www.i-r-genius.com/lipsum.html)

alice29.txt from [here](https://corpus.canterbury.ac.nz/descriptions/#cantrbry)

Below are the sizes of the compressed files, all sizes are in bytes.

| File size | File description | LZW size | LWZ size % | Huff size | Huff size % |
|-----------|------------------|----------|------------|-----------|-------------|
| 4130      | Lorem ipsum      | 2514     | 61%        | 2284      | 55%         |
| 16356     | Lorem ipsum      | 7004     | 43%        | 8886      | 54%         |
| 65372     | Lorem ipsum      | 23570    | 36%        | 35217     | 54%         |
| 152089    | alice29.txt      | 71754    | 47%        | 84640     | 56%         |

### Possible improvements

Both classes, especially the Huffman one does not perform optimally due to the fact that the class uses Javas actual "String" type for bits during compression.
I will most likely look at implementing some of the parts more optimally later during this course.

Another caveat regarding decompressing with the Huffman class is discussed [here](https://github.com/Henri0088/File-Compression/blob/main/Documentation/Output.md)

### Sources

These are the primary sources that I have used throughout this project.

* [Huffman coding](https://en.wikipedia.org/wiki/Huffman_coding) from Wikipedia
* [Lempel-Ziv-Welch](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch) from Wikipedia
* [LZW Data Compression](https://www2.cs.duke.edu/csed/curious/compression/lzw.html)
* "Introduction to algorithms" Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein.
