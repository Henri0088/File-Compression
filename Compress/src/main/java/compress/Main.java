package compress;

public class Main {

    public static void main(String[] args) {
        InputReader reader1 = new InputReader("test.txt");
        BitWriter writer = new BitWriter("testOutput.bin");
        
        String str = reader1.readLines();
        
        Huffman huff = new Huffman();
        String cStr = huff.compress(str);
        writer.writeBits(cStr);
        
        BinaryInputReader reader2 = new BinaryInputReader("testOutput.bin");
        String dStr = huff.decompress(reader2.readLines());
        System.out.println(dStr);
        
    }
    
}
