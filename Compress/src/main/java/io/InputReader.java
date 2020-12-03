package io;

import java.io.*;

/**
 * Class for reading UTF-8 encoded data from a file
 * @author henri
 */
public class InputReader {
    
    private BufferedReader reader;
    
    public InputReader(String file) throws Exception { 
        this.reader = new BufferedReader(new FileReader(file));
    }
    
    /**
     * Read lines from the file specified in the constructor.
     * @return String containing lines from the file
     */
    public String readLines() {
        StringBuilder str = new StringBuilder();
        
        try {
            while (reader.ready()) {
                str.append(reader.readLine()).append("\n");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while reading lines from file");
        }
        return str.toString();
    }
}
