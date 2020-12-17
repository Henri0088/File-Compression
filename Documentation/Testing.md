## Testing document

### Unit testing

The LZW class is tested by compressing and decompressing strings. For the Huffman class there are also individual tests on some of the steps
such as getting character counts, or building the tree.

Overall test coverage can be generated using:
```
./gradlew test JacocoTestReport
```
The report can be found at Compress/build/reports/jacoco/test/html/index.html


### Further testing

Both LZW and Huffman have been tested with some inputs, the results are shown [here](https://github.com/Henri0088/File-Compression/blob/main/Documentation/Implementation.md#performance-comparison)

The validity of the compression/decompression process can be tested by running the program with java -jar compress.jar, choosing "Performance test" and giving the file name. Make sure that the files are located in the same folder with compress.jar.
The program checks during runtime that the contents of the file have been compressed and decompressed back with no data lost.
