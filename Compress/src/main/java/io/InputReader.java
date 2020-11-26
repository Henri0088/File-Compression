package io;

import java.io.*;

/**
 * Class for reading UTF-8 encoded data from a file
 * @author henri
 */
public class InputReader {
    
    private BufferedReader reader;
    
    public InputReader(String file) { 
        try {
            this.reader = new BufferedReader(new FileReader(file));
        } catch (Exception e) { // Better exception type?
            System.out.println("Is the filename correct?");
            System.out.println("Exception in InputReader " + e);
        }
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
            System.out.println("Exception in InputReader " + e);
        }
        return str.toString();
    }
}
