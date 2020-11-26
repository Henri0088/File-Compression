
package utils;

/**
 * Custom data structure which mimics the behavior of
 * Java's HashMap<String, Integer>
 */
public class CHashMap {
    
    private Pair[] hashTable;
    private String[] keySet;
    private int pointer;
    
    public CHashMap() {
        this.hashTable = new Pair[4096];
        this.keySet = new String[4096];
        this.pointer = 0;
    }
    
    /**
     * Inserts a new key-value pair into the HashMap
     * @param key String
     * @param value Integer
     */
    public void put(String key, int value) {
        int index = hashCode(key);
        
        keySet[pointer] = key;
        pointer++;
        
        if (hashTable[index] == null) {
            hashTable[index] = new Pair(key, value);
        } else {
            Pair newPair = new Pair(key, value);
            newPair.setNext(hashTable[index]);
            hashTable[index] = newPair;
        }
    }
    
    /**
     * Returns integer associated with the key
     * @param key String
     * @return Integer value corresponding to key
     */
    public int get(String key) {
        int index = hashCode(key);
        
        Pair currPair = hashTable[index];
        while (true) {
            if (currPair.getKey().equals(key)) {
                return currPair.getValue();
            } else {
                currPair = currPair.getNext();
            }
        }
    }
    
    /**
     * Returns true if key is present in the HashMap,
     * false if not.
     * @param key to look up.
     * @return true/false
     */
    public boolean containsKey(String key) {
        for (int i = 0; i < pointer; i++) {
            if (keySet[i].equals(key)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns the amount of key-value pairs in the HashMap.
     * @return int
     */
    public int size() {
        return pointer;
    }
    
    private void increaseKeySet() {
        System.out.println("INCREASING SIZE!");
        int newSize = keySet.length + 2048;
        String[] newSet = new String[newSize];
        System.arraycopy(keySet, 0, newSet, 0, keySet.length);
        keySet = newSet;
    }
    
    /**
     * Uses polynomial hashing with A = 7. Also rolls back to 0 if 2^32 - 1 is reached.
     * @param str
     * @return 
     */
    private int hashCode(String str) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash += Math.pow(7, str.length() - i) * (int) str.charAt(i);
            if (hash == Integer.MAX_VALUE) {
                hash = 0;
            }
        }
        return hash % 4096;
    }
}
