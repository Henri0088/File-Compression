
package compress;

import java.io.File;
import java.io.FileOutputStream;
import java.util.BitSet;

public class BitWriter {
    
    private File file;
    
    public BitWriter(String file) {
        this.file = new File(file);
    }
    
    public void writeBits(String str) {
        // Convert str to bits.
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
