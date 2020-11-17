package compress;

import java.util.HashMap;


public class Main {

    public static void main(String[] args) {
        InputReader reader1 = new InputReader("test.txt");
        BitWriter writer = new BitWriter("testOutput.bin");
        
        LZW lempel = new LZW();
        
        String str = reader1.readLines();
        System.out.println(str);
        String cStr = lempel.compress(str);
        System.out.println(cStr);
        String dStr = lempel.decompress(cStr);
        System.out.println(dStr);
        
    }
    
}
