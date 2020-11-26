## Weekly report 5

### What did I do/How has the program progressed?
I implemented my own HashMap datastructure, it's not generic but as my algorithms only needed String --> Integer mapping it is sufficient.
I also migrated my both algorithms to use this new CHashMap and did testing to make sure everything was still in order.

### What caused problems?
My LZW was sometimes not decompressing correctly and I couldn't quite put my finger on it. I soon realized that while my compression algorithm caps the amount of
entries in the encoding HashMap to 4096 as I use 12-bit codes, my decompression algorithm didn't have this cap and was building the decompression array wrong in the
last index.

### What will be done next?
I now have my two algorithms working correctly using only my own datastructures. I could spend next week creating a better UI for my application but as I think that
is a secondary topic on this course I'm probably going to see if I can make my algorithms more efficient.

I should probably do more performance testing as well. For example times with the default Java HashMap and PriorityQueue vs with my own CPriorityQueue and CHashMap.

Creating a separate LZW that uses variable-width codes instead of 12-bit codes could be interesting as well.

### Time spent
Coding, testing, peer-reviewing and implementing CHashMap took about 11 hours this week.
