## Implementation document

### General structure

The classes of interest are Huffman and LZW which compress UTF-8 encoded data using Huffman coding and the LZW algorithm respectively. 
Both classes have compress and decompress methods which do exactly what the name suggests. Both take as input a UTF-8 encoded String and output
a String, which only has 1's and 0's. A separate BitWriter class is used to write this String into a file bit by bit instead of 
UTF-8 encoding each 1 and 0.

### Time- and spacecomplexity analysis

#### Lempel-Ziv-Welch compression

The LZW compression algorithm reads each character from the input string once. In the worst case each new character c concatenated to the previous ones
form a new substring. In this case the substring without c is encoded. However the 'get' operation of CHashMap works in O(1) assuming that the internal hash function uniformly distributes keys. Also if the CHashMap is not full, a new entry is added. This is also a O(1) operation.

So n characters are looped over and some O(1) operations are done to each of them. So the timecomplexity of the LZW compression is O(n).

#### Lempel-Ziv-Welch decompression

The LZW decompression works similarly to compression, it loops over the input string once and does some O(1) operations. So the timecomplexity of LZW decompression is also O(n)

However in practice the decompression is faster than the compression algorithm. This is because while the compression algorithm uses a hashmap, the decompression algorithm uses a regular array for all its operations. This means that the decompression algorithm doesn't need to calculate hashes for example, resulting in smaller constant factors.

#### Huffman compression

As seen below, the performance of the Huffman compression algorithm is perhaps more about the amount of unique characters in the data as it is about the
length of the data. However, since my implementation only compresses UTF-8, for the sake of simplicity we are going to assume a maximum of 256 unique characters
are present in the data. We could also do Big-O analysis in terms of unique character count, but 256 is such a small maximum that I don't think it would yield any intresting results.

So if n is the length of the data, the time complexities of the different methods used (in order) are:

getCounts: This method loops over the data once, which makes it O(n)

getQueue: This methods adds a maximum of 256 objects into a priorityqueue. The timecomplexity of adding to a priorityqueue is O(log n), however the operation is performed a maximum of 256 times and doesn't depend on the length of the data, which makes this method O(1)

buildTree: Similarly, this method pulls objects from the priorityqueue a maximum of 511 times and adds a maximum of 256 times, which are independent of the length of the data, so O(1)

getCodes: Since the binary tree has a maximum of 511 nodes, this method similarly to the last two ends up being O(1)

convert: This method is O(n) since it loops over the data once, it also calls convertTree, but this again is independent of data length.

All of these methods are called one after another making the time complexity of the Huffman algorithm O(n). 

#### Huffman decompression

Similarly to compression, the decompression algorithm runs through a couple of methods which first decode the binary tree and then extract binary codes from the tree which do not depend on the length of the initial data. Lastly the data is decompressed by looping over the string once, making the decompression also work in O(n)

In practice the decompression is much slower than the compression. This is because while it mostly does the same operations in reverse as the compression, the length of the string processed is actually longer since the compressed string only contains 1's and 0's. Furthermore while compression uses a normal array for Integer -> String mapping while the decompression needs String -> Integer mapping and uses CHashMap. 

### Performance comparison

Below are the results of performance testing, all times are in ms, sizes reflect the string size in bytes as seen by Java,
which in case of uncompressed UTF-8 data is the same as its length.
In case of compressed data which is presented as a String of 1's and 0's the length is divided by 8.


| Size      | File description | LZW Compress | LZW Decompress | Huff Compress | Huff Decompress |
|-----------|------------------|--------------|----------------|---------------|-----------------|
| 148482    | alice29.txt      | 64           | 15             | 56            | 77              |
| 1227437   | 'A' repeated     | 1207         | 4              | 77            | 87              |
| 2241792   | Alphabet repeated| 459          | 7              | 103           | 288             |
| 4351186   | bible.txt        | 888          | 155            | 156           | 490             |
| 51000000  | bigfile          | 6377         | 93             | 888           | 4135            |

Below are the sizes of the compressed files, all sizes are in bytes.

| Size      | File description | LZW size | LWZ size % | Huff size | Huff size % |
|-----------|------------------|----------|------------|-----------|-------------|
| 148482    | alice29.txt      | 71754    | 48.3%      | 84639     | 57.0%       |
| 1227437   | 'A' repeated     | 2354     | 0.2%       | 153434    | 12.5%       |
| 2241792   | Alphabet repeated| 26071    | 1.2%       | 1349026   | 60.2%       |
| 4351186   | bible.txt        | 2104450  | 48.4%      | 2492113   | 57.3%       |
| 51000000  | bigfile          | 1002850  | 2.0%       | 24125022  | 47.3%       |


.txt files from [here](https://corpus.canterbury.ac.nz/descriptions/#cantrbry)

bigfile was generated using the unix-command
```
yes "tobeornottobe, an interesting question indeed....." | head -n 1000000 > bigfile.txt
```

Below you can see how the different algorithms scale. Data used was world192.txt concatenated with itself, so world192.txt twice. Vertical axis is time in ms and horizontal axis is amount of characters used in the compression process.

![results](https://github.com/Henri0088/File-Compression/blob/main/Documentation/Algorithm%20comparison.png)


### Possible improvements

Since the "strings" in the huffman decompression String -> Integer mapping are actually binary strings a normal array of size 256 could be used to help performance.

A caveat regarding decompressing with the Huffman class is discussed [here](https://github.com/Henri0088/File-Compression/blob/main/Documentation/Output.md)

### Sources

These are the primary sources that I have used throughout this project.

* [Huffman coding](https://en.wikipedia.org/wiki/Huffman_coding) from Wikipedia
* [Lempel-Ziv-Welch](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch) from Wikipedia
* [LZW Data Compression](https://www2.cs.duke.edu/csed/curious/compression/lzw.html)
* "Introduction to algorithms" Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein.
