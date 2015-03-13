package com.sri.datastructures;

import com.sri.utility.KVPair;
import java.util.Iterator;

public class Heap<Key extends Comparable<Key>, Value extends Comparable<Value>> {

    protected List<Key> keys;
    protected List<Value> values;
    protected int heapCount = 0;

    public Heap() {
        keys = new List<Key>();
        values = new List<Value>();
    }

    public Heap(Key[] k, Value[] v) {
        keys = new List<Key>();
        values = new List<Value>();
        for (int i = 0; i < k.length; i++) {
            addKeyValue(k[i], v[i]);
        }
    }

    //return the number of items in the queue
    public int heapSize() {
        return heapCount;
    }

    //reset the queueCount back to the number of elements in the heap
    //the queueCount may have been decreased during a heap sort
    public void resetCount() {
        heapCount = keys.getCount();
    }

    public KVPair<Key, Value> search(Key k, Value v) {
        Iterator<Key> itK = keys.iterator();
        Iterator<Value> itV = values.iterator();

        //return the index of the item in case if the calling function needs to update the KVPair
        int index = -1;
        while (itK.hasNext() && itV.hasNext()) {
            Key tmpK = itK.next();
            Value tmpV = itV.next();
            index++;
            if (k.compareTo(tmpK) == 0 && v.compareTo(tmpV) == 0) {
                return new KVPair<Key, Value>(index, tmpK, tmpV);
            }
        }
        return null;
    }

    public KVPair<Key, Value> searchKey(Key k) {
        Iterator<Key> itK = keys.iterator();
        Iterator<Value> itV = values.iterator();

        //return the index of the item in case if the calling function needs to update the KVPair
        int index = -1;
        while (itK.hasNext() && itV.hasNext()) {
            Key tmpK = itK.next();
            Value tmpV = itV.next();
            index++;
            if (k.compareTo(tmpK) == 0) {
                return new KVPair<Key, Value>(index, tmpK, tmpV);
            }
        }
        return null;
    }

    public KVPair<Key, Value> searchValue(Value v) {
        Iterator<Key> itK = keys.iterator();
        Iterator<Value> itV = values.iterator();

        //return the index of the item in case if the calling function needs to update the KVPair
        int index = -1;
        while (itK.hasNext() && itV.hasNext()) {
            Key tmpK = itK.next();
            Value tmpV = itV.next();
            index++;
            if (v.compareTo(tmpV) == 0) {
                return new KVPair<Key, Value>(index, tmpK, tmpV);
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return heapCount == 0 ? true : false;
    }

    //reinitialize the heap
    public void init() {
        keys.deleteAll();
        values.deleteAll();
        heapCount = 0;
    }

    //add a heap element
    public void addKeyValue(Key k, Value v) {
        keys.add(k);
        values.add(v);
        heapCount++;
    }

    //return the value at index i (zero based index system)
    public KVPair<Key, Value> getKeyValue(int index) {
        if (index >= heapSize()) {
            return null;
        }

        KVPair<Key, Value> kvPair = new KVPair<Key, Value>(keys.get(index), values.get(index));
        return kvPair;
    }

    //set the value at index i (zero based index system)
    public boolean setKeyValue(int i, KVPair<Key, Value> kvPair) {
        if (keys.set(i, kvPair.getKey()) && values.set(i, kvPair.getValue())) {
            return true;
        } else {
            return false;
        }
    }

    //delete the value at index i
    public boolean deleteKeyValue(int i) {
        if (keys.delete(i) && values.delete(i)) {
            heapCount--;
            return true;
        }
        return false;
    }

    //swap the key/value pairs between i1 and i2
    public boolean swap(int i1, int i2) {
        if (i1 >= heapSize() || i2 >= heapSize()) {
            return false;
        }

        //tmp holding variables
        KVPair<Key, Value> kvPair1 = new KVPair<Key, Value>(keys.get(i1), values.get(i1));
        KVPair<Key, Value> kvPair2 = new KVPair<Key, Value>(keys.get(i2), values.get(i2));

        //swap them out
        setKeyValue(i1, kvPair2);
        setKeyValue(i2, kvPair1);
        return true;
    }

    public String toString() {
        String outStr = "";
        for (int i = 0; i < heapCount; i++) {
            KVPair<Key, Value> kvPair = getKeyValue(i);
            outStr += "\r";
            outStr += "Key: " + kvPair.getKey().toString() + "\t Value: " + kvPair.getValue().toString() + "";
        }
        outStr += "\r";
        return outStr;
    }

    // given an index, get the parent
    protected int getParent(int index) {
        // the parent index of a given index is index/2
        // but because of the zero based index it causes some issues.
        // for e.g., parent of index 3 in a zero based index system
        // will be 2/2 (which is 1) but that is wrong the parent should be zero
        // so to fix this we add 1 then divide by 2 to get the parent,
        // then subtract 1 to return it to the zero based index. For
        // the example above index 2 is ((2 + 1)/2 - 1) which is zero and that
        // is the correct parent
        return Math.max(((index + 1) / 2) - 1, 0);
    }

    protected int getLeft(int index) {
        // see the comments above for explanation on why we subtract one at the end
        // the formula is 2*index
        return ((index + 1) * 2) - 1;
    }

    protected int getRight(int index) {
        // see the comments above for explanation on why we subtract one at the end
        // the formula is (2 * index + 1)
        return (((index + 1) * 2) + 1) - 1;
    }

}
