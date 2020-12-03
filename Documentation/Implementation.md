## Implementation document

### General structure

The classes of interest are Huffman and LZW which compress UTF-8 encoded data using Huffman coding and the LZW algorithm respectively. 
Both classes have compress and decompress methods which do exactly what the name suggests. Both take as input a UTF-8 encoded String and output
a String, which only has 1's and 0's. A separate BitWriter class is used to write this String into a file bit by bit instead of 
UTF-8 encoding each 1 and 0.

### Time- and spacecomplexity analysis

#### Lempel-Ziv-Welch compression

The LZW compression algorithm reads each character from the input string once. In the worst case each new character c concatenated to the previos ones
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

Below are the results of performance testing, all times are in ms, file size in bytes.

| File size | File description | LZW Compress | LZW Decompress | Huff Compress | Huff Decompress |
|-----------|------------------|--------------|----------------|---------------|-----------------|
| 152089    | alice29.txt      | 68           | 15             | 63            | 109             |
| 368900    | 'A' repeated     | 1706         | 4              | 68            | 74              |
| 368911    | Alphabet repeated| 578          | 5              | 64            | 199             |
| 2408281   | world192.txt     | 749          | 71             | 117           | 1094            |
| 4351186   | bible.txt        | 1211         | 85             | 163           | 1645            |

For comparison, here is one of the tests but with LZW using the default JAVA HashMap instead of CHashMap

| File size | File description | LZW Compress | LZW Decompress | 
|-----------|------------------|--------------|----------------|
| 368911    | Alphabet repeated| 62           | 5              | 
| 4351186   | bible.txt        | 839          | 87             |

Below are the sizes of the compressed files, all sizes are in bytes.

| File size | File description | LZW size | LWZ size % | Huff size | Huff size % |
|-----------|------------------|----------|------------|-----------|-------------|
| 152089    | alice29.txt      | 71754    | 47.2%      | 84639     | 55.7%       |
| 368900    | 'A' repeated     | 3760     | 1.0%       | 46116     | 12.5%       |
| 368911    | Alphabet repeated| 8607     | 2.3%       | 221904    | 60.1%       |
| 2408281   | world192.txt     | 1558776  | 64.7%      | 1504199   | 62.5%       |
| 4351186   | bible.txt        | 2104450  | 48.4%      | 2492113   | 57.3%       |


.txt files from [here](https://corpus.canterbury.ac.nz/descriptions/#cantrbry)

LZW seems to be the better algorithm, in terms of decompressing at least. The compression part is more complex, LZW is being hindered by my CHashMap. My hashfunction uses polynomial hashing, however it still generates a lot of collisions slowening the data  structure down, this will hopefully be bettered later on.


### Possible improvements

The hashfunction used by the CHashMap class is generating too many collisions and should be improved.

Since the "strings" in the huffman decompression String -> Integer mapping are actually binary strings a normal array of size 256 could be used to help performance, this will most likely be implemented before the final deadline.

A caveat regarding decompressing with the Huffman class is discussed [here](https://github.com/Henri0088/File-Compression/blob/main/Documentation/Output.md)

### Sources

These are the primary sources that I have used throughout this project.

* [Huffman coding](https://en.wikipedia.org/wiki/Huffman_coding) from Wikipedia
* [Lempel-Ziv-Welch](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch) from Wikipedia
* [LZW Data Compression](https://www2.cs.duke.edu/csed/curious/compression/lzw.html)
* "Introduction to algorithms" Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein.
