package OOD;

import java.util.*;

public class MyHashMap<K,V> {
    
    private static class Cell<K,V> {
        private K key;
        private V val;

        private Cell(K key, V val) {
            this.key = key;
            this.val = val;
        }

        private K getKey() {
            return this.key;
        }

        private V getVal() {
            return this.val;
        }
        private void setKey(K key) {
            this.key = key;
        }
        private void setVal(V val) {
            this.val = val;
        }

        @Override 
        public int hashCode() {
            if (this.key == null) {
                return 0;
            }
            return this.key.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (!(o instanceof Cell)) {
                return false;
            }
            Cell<K, V> that = (Cell<K,V>) o;
            if (that.getKey() == null) {
                return this.getKey() == null;
            }
            return this.getKey().equals(that.getKey());
        }
    }

    private List<Cell<K,V>>[] buckets;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR = 0.75d;

    public MyHashMap(){
        this(256);
    }
    public MyHashMap(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException();

        this.capacity = capacity;
        size = 0;
        buckets = new List[this.capacity];

    }
    private int getHash(Cell<K,V> cell) {
        return Math.abs(cell.hashCode() % this.capacity);

    }
    public boolean put(K key, V val) {
        Cell<K,V> cell = new Cell<>(key, val);
        int idx = getHash(cell);
        if (this.buckets[idx] == null) {
            this.buckets[idx] = new ArrayList<Cell<K,V>>();
        }
        for (Cell<K,V> c: this.buckets[idx]) {
            if (c.equals(cell)) {
                c.setVal(val);
                return true;
            }
        }
        this.buckets[idx].add(cell);
        this.size++;
        if (this.size >= this.capacity *LOAD_FACTOR) {
            expand();
        }
        return true;
    }
    
    public V get(K key) {
        Cell<K,V> cell = new Cell<>(key, null);
        int idx = getHash(cell);
        if (this.buckets[idx] == null) {
            System.out.println("not found this key");
            return null;
        }
        for (Cell<K,V> c: this.buckets[idx]) {
            if (c.equals(cell)) {
                return c.getVal();
            }
        }
        System.out.println("not found this key");
        return null;
    }

    public boolean remove(K key) {
        Cell<K,V> cell = new Cell<>(key, null);
        int idx = getHash(cell);
        if (this.buckets[idx] == null) {
            System.out.println("no key found");
            return false;
        }
        Cell<K,V> target = null;
        for (Cell<K,V> c: this.buckets[idx]) {
            if (c.equals(cell)) {
                target = c;
                break;
            }
        }
        if (target == null) {
            System.out.println("no key found");
            return false;
        }
        target = this.buckets[idx].get(this.buckets[idx].size() - 1);
        this.buckets[idx].remove(this.buckets[idx].size() - 1);
        this.size--;
        return true;
    }

    private void expand(){
        this.capacity = this.capacity * 2;
        List<Cell<K,V>>[] newBuckets = new List[this.capacity];
        for(List<Cell<K,V>> bucket: this.buckets) {
            if (bucket == null) {
                continue;
            }
            for(Cell<K,V> c: bucket) {
                int idx = getHash(c);
                if (newBuckets[idx] == null) {
                    newBuckets[idx] = new ArrayList<>();
                }
                newBuckets[idx].add(c);
            }
        }
        this.buckets = newBuckets;
    }
    public static void main(String[] args) {
        System.out.println("----------start----------");
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        map.put(1,1);
        System.out.println(map.get(1));
        map.put(1,2);
        System.out.println(map.get(1));
        map.remove(1);
        System.out.println(map.get(1));
        System.out.println("----------end-----------");
    }



}