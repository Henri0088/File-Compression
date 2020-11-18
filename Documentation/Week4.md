## Weekly report 4

### What did I do/How has the program progressed?
I implemented the LZW compression algorithm, I also created a very barebones console UI for my application which will most likely change during the upcoming weeks.

### What caused problems?
My LZW algorithm was almost arbitrarily throwing a nullPointerException when decompressing with certain inputs, the cause of this problem took a while for me to figure out
but [this](https://www2.cs.duke.edu/csed/curious/compression/lzw.html) article pointed me in the right direction and it seems to work for all inputs now.

### What will be done next?
My next priority will be implementing my own HashMap data structure. After that I have my two compression algorithms using only my own data structures. 
This means that I can start thinking about ways to make my implementations more efficient, my LZW algorithm doesn't yet use variable width codes for example.

### Time spent
Coding, testing, documenting and implementing LZW took about 13 hours this week.
