
package io;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Class for writing UTF-8 encoded data into a file
 * @author henri
 */
public class Writer {
    
    private File file;
    
    public Writer(String file) {
        this.file = new File(file);
    }
    
    public void write(String str) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(str.getBytes());
            fos.close();
        } catch (Exception e) { // Better exception type?
            System.out.println("Writing failed!");
        }
    }
}
