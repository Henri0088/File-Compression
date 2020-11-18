# File Compression

## Documentation

### [Specification document](https://github.com/Henri0088/File-Compression/blob/main/Documentation/Specification.md)
### [Output file format](https://github.com/Henri0088/File-Compression/blob/main/Documentation/Output.md)
### [Implementation document (Toteutusdokumentti)](https://github.com/Henri0088/File-Compression/blob/main/Documentation/Implementation.md)
### [Testing document](https://github.com/Henri0088/File-Compression/blob/main/Documentation/Testing.md)

## Weekly reports

* #### [Weekly report 1](https://github.com/Henri0088/File-Compression/blob/main/Documentation/Week1.md)
* #### [Weekly report 2](https://github.com/Henri0088/File-Compression/blob/main/Documentation/Week2.md)
* #### [Weekly report 3](https://github.com/Henri0088/File-Compression/blob/main/Documentation/Week3.md)

## Other
All commands should be ran in the "Compress" directory.

### Testing
The tests can be ran using
```
./gradlew test
```

### Jacoco
The jacoco report can be generated with
```
./gradlew test JacocoTestReport
```
The report can be found at Compress/build/reports/jacoco/test/html/index.html

### Checkstyle
The checkstyle tests can be ran with
```
./gradlew checkstyleMain
```

### Javadoc
Javadoc can be generated with
```
./gradlew javadoc
```
Javadoc can be found at Compress/build/docs/javadoc/index.html
