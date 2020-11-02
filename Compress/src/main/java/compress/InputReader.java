package compress;

import java.io.*;
import java.lang.StringBuilder;

public class InputReader implements IOReader {
    
    private BufferedReader reader;
    
    public InputReader(String file) { 
        try {
            this.reader = new BufferedReader(new FileReader(file));
        } catch (Exception e) { // Better exception type?
            System.out.println("Exception in InputReader " + e);
        }
    }
    
    public String readLines() {
        StringBuilder str = new StringBuilder();
        
        try {
            while (reader.ready()) {
                str.append(reader.readLine()).append("_");
            }
        } catch (Exception e) {
            
        }
        return str.toString();
    }
    
}
