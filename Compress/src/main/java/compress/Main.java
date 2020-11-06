package compress;



public class Main {

    public static void main(String[] args) {
        InputReader reader = new InputReader("test.txt");
        BitWriter writer = new BitWriter("testOutput.bin");
        
        String str = reader.readLines();
        Huffman huffman = new Huffman();
        String cStr = huffman.compress(str);
        writer.writeBits(cStr);
        
        System.out.println(str);
        System.out.println(cStr);
        System.out.println(huffman.decompress(cStr));
    }
    
}
