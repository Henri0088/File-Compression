## Weekly report 3

### What did I do/How has the program progressed?

I have finalized my custom PriorityQueue and it is now used by the Huffman class. I also figured out how to include the binary tree in the output file.
Compressed files can now be decompressed any time and all information is found in the file. The format is described [here](https://github.com/Henri0088/File-Compression/blob/main/Documentation/Output.md).
I also needed to implement many new methods to the Huffman class to achieve this. Finally I read up on LZW more thoroughly.

### What caused problems?

I had some problems with my BitWriter class which didn't want to co-operate. I however got it working after all, when I convert my BitSet into a byte array all of
bytes get flipped around, which I then flip back. This works but it's probably far from optimal so I will take a look at it later.

### What will be done next?

First priority is getting a LZW-algorithm up and running. After that I have a picture of all the remaining data strcutures that I need to implement.
I might also start thinking about the UI of my application, most likely a console UI is going to be sufficient.

