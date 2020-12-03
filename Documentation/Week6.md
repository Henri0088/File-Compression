## Weekly report 6

### What did I do/How has the program progressed?
This week I polished my program to the point where a github release is now available, files can be compressed and decompressed back with a simple console UI.
I also made multiple performance improvements, with some inputs the algorithms have gotten about 50 times faster this week. One of the largest improvements were
swtiching the "containsKey" method of the CHashMap to be O(1) instead of O(n). The largest by far was switching from using '+' with string concatenation to
StringBuilder, I have gotten so used to Python recently that I had forgotten about the detail that doing "string += string2" in Java is painfully slow.

Furthermore I did documenting and updated the performance test results. A simple new class "Writer" was also added.

### What caused problems?

This week was pretty straightforward since I only really did coding on the UI and I/O side of things. The changes made to the algorithms were trivial enough to
not cause any trouble.

### What will be done next?

Finally, I'm going to see if I can fix some of the issues mentioned [here](https://github.com/Henri0088/File-Compression/blob/main/Documentation/Implementation.md#possible-improvements)

Other than that, I'm starting to be pretty happy with my project. Perhaps I could clean up the code, make sure that my variable names make sense and write a couple
new tests. So basically a mixed bag of final touches.

### Time spent
About 10 hours were spent on the project this week.
