package compress;

import io.InputReader;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give file to compress:");
        String file = scanner.nextLine();
        
        InputReader reader = new InputReader(file);
        String str = reader.readLines();
        
        System.out.println("--------------------------");
        System.out.println("HUFFMAN");
        System.out.println("--------------------------");
        
        Huffman huff = new Huffman();
        
        long start = System.nanoTime();
        String cStr = huff.compress(str);
        long end = System.nanoTime();
        System.out.println("COMPRESS: " + (end - start)/1000000 + " ms");
        
        start = System.nanoTime();
        String dStr = huff.decompress(cStr);
        end = System.nanoTime();
        System.out.println("DECOMPRESS: " + (end - start)/1000000 + " ms");
        
        System.out.println("Size: " + str.length() + " compressed: " + cStr.length()/8);
        
        if (str.equals(dStr)) {
            System.out.println("MATCH!");
        } else {
            System.out.println("ERROR!");
            System.out.println("ORIGINAL: " + str.substring(0, 100));
            System.out.println("DECOMP: " + dStr.substring(0, 100));
        }
        
        System.out.println("");
        System.out.println("--------------------------");
        System.out.println("Lempel-Ziv-Welch");
        System.out.println("--------------------------");
        
        LZW lzw = new LZW();
        start = System.nanoTime();
        cStr = lzw.compress(str);
        end = System.nanoTime();
        
        System.out.println("COMPRESS: " + (end - start)/1000000 + " ms");
        
        start = System.nanoTime();
        dStr = lzw.decompress(cStr);
        end = System.nanoTime();
        System.out.println("DECOMPRESS: " + (end - start)/1000000 + " ms");
        
        System.out.println("Size: " + str.length() + " compressed: " + cStr.length()/8);
        
        if (str.equals(dStr)) {
            System.out.println("MATCH!");
        } else {
            System.out.println("ERROR!");
            System.out.println("ORIGINAL: " + str.substring(0, 100));
            System.out.println("DECOMP: " + dStr.substring(0, 100));
        }
    }
}
