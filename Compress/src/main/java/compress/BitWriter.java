
package compress;

import java.io.File;
import java.io.FileOutputStream;
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
        BitSet bits = new BitSet(str.length());

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == "1".charAt(0)) {
                bits.set(i);
            }
        }

        byte[] byteArr = bits.toByteArray();
        
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArr);
            fos.close();
        } catch (Exception e) { // Better exception type?
            System.out.println("Writing failed! " + e);
        }
    }
    
}
