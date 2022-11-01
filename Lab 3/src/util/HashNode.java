package util;

// A node of chains
class HashNode<K, V> {
    public K key;
    public V value;
    public final int hashCode;

    // Reference to next node
    public HashNode<K, V> next;

    // Constructor
    public HashNode(K key, V value, int hashCode)
    {
        this.key = key;
        this.value = value;
        this.hashCode = hashCode;
    }

    @Override
    public String toString(){
        return this.key + " <---> " + this.value;
    }
}
