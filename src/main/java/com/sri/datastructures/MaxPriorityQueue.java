package com.sri.datastructures;

import com.sri.utility.KVPair;

public class MaxPriorityQueue<Key extends Comparable<Key>, Value extends Comparable<Value>> extends MaxHeap<Key, Value> {
	//typically a priority queue has a key/value pair - 
    //the priority is determined by the key and value is usually an object
    //the base class provides the keys... for now we will assume that the 
    //Key object has a uniquely identifiable key

    public MaxPriorityQueue(Key[] k, Value[] v) {
        super(k, v);
    }

    //return the maximum - in this case it will be head of the list
    public KVPair<Key, Value> heapMax() {
        return getKeyValue(0);
    }

    //extract max - removes the max and adjusts the heap/queue accordingly
    public KVPair<Key, Value> extractMax() throws IllegalArgumentException {
        if (heapSize() == 0) {
            throw new IllegalArgumentException("Max Priority Queue underflow");
        }

        //make a copy of the max
        KVPair<Key, Value> retKVPair = heapMax();

		//copy the last element in the queue to the first and rebuild max heap
        //this is similar to the heapsort functionality
        KVPair<Key, Value> kvLast = getKeyValue(heapSize() - 1);
        setKeyValue(0, kvLast);
        deleteKeyValue(heapSize() - 1);
        maxHeapify(0);
        return retKVPair;
    }

    public boolean increaseKey(int index, Key newKey, Value newValue) {
        if (index < 0) {
            return false;
        }
        if (newKey.compareTo(getKeyValue(index).getKey()) < 0) {
            System.out.println("Max Priority Queue: new key is smaller than current key");
            return false;
        } else {
            setKeyValue(index, new KVPair<Key, Value>(newKey, newValue));
            while (index > 0 && getKeyValue(getParent(index)).getKey().compareTo(getKeyValue(index).getKey()) < 0) {
                swap(index, getParent(index));
                index = getParent(index);
            }
            return true;
        }
    }

    public void maxHeapInsert(Key newKey, Value newValue) {
		//first add the element to the heap (and increase the heap count)
        //in CLRS page 140, they set the new key at the last 
        addKeyValue(newKey, newValue);
        increaseKey(heapSize() - 1, newKey, newValue);
    }
}
