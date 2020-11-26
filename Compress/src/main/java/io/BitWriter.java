
package io;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.BitSet;

/**
 * Class for writing binary data into a file.
 * @author Henri Sundquist
 */
public class BitWriter {
    
    private File file;
    
    public BitWriter(String file) {
        this.file = new File(file);
    }
    
    /**
     * Writes the given binary data into a file.
     * @param str Binary string to write
     */
    public void writeBits(String str) {
        if (str.length() % 8 != 0) {
            int filler = 8 - (str.length() % 8);
            for (int i = 0; i < filler; i++) {
                str += "0";
            }
        }
        
        BitSet bits = new BitSet(str.length());
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                bits.set(i);
            }
        }
        byte[] byteArr = bits.toByteArray();
        
        // toByteArray flips bytes around? 
        for (int i = 0; i < byteArr.length; i++) {
            byteArr[i] = reverse(byteArr[i]);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArr);
            fos.close();
        } catch (Exception e) { // Better exception type?
            System.out.println("Writing failed! " + e);
        }
    }
    
    private byte reverse(byte x) {
        byte newX = 0;
        for (int i = 0; i < 8; i++) {
            // Push bits left
            newX <<= 1;
            // Get next bit from x
            byte temp = (byte) (x & 1);
            // Place bit into newX
            newX |= temp;
            // Push bits right
            x >>= 1;
        }
        return newX;
    }
    
}