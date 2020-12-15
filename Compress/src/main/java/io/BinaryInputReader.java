
package io;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class for reading compressed data from a file,
 * bytes don't need to be valid UTF-8 characters
 * @author henri
 */
public class BinaryInputReader {
    
    private String filePath;
    
    public BinaryInputReader(String file) {
        this.filePath = file;
    }
    
    public String readLines() {
        try {
            byte[] byteArr = Files.readAllBytes(Paths.get(filePath));
            StringBuilder str = new StringBuilder();
            
            for (byte byt : byteArr) {
                str.append(Integer.toBinaryString((byt & 0xFF) + 0x100).substring(1));
            }
            return str.toString();
        } catch (Exception e) {
            System.out.println("No file found!");
        }
        return "";
    }
    
}
