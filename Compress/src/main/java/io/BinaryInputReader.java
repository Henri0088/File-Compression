
package IO;

import java.nio.file.Files;
import java.nio.file.Paths;

public class BinaryInputReader {
    
    private String filePath;
    
    public BinaryInputReader(String file) {
        this.filePath = file;
    }
    
    public String readLines() {
        try {
            byte[] byteArr = Files.readAllBytes(Paths.get(filePath));
            
            String str = "";
            for (byte byt : byteArr) {
                str += Integer.toBinaryString((byt & 0xFF) + 0x100).substring(1);
            }
            return str;
        } catch (Exception e) {
            System.out.println("Exception in BinaryInputReader " + e);
        }
        return "";
    }
    
}
