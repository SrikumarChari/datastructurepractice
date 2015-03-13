package com.sri.datastructures;

import com.sri.utility.KVPair;

public class MinPriorityQueue<Key extends Comparable<Key>, Value extends Comparable<Value>> extends MinHeap<Key, Value> {

    public MinPriorityQueue(Key[] keys, Value[] values) {
        super(keys, values);
    }

    public MinPriorityQueue() {
    }

    //return the minimum - in this case it will be head of the list
    public KVPair<Key, Value> heapMin() {
        return getKeyValue(0);
    }

    //extract max - removes the max and adjusts the heap/queue accordingly
    public KVPair<Key, Value> extractMin() {
        if (heapSize() == 0) {
            return null;
        }

        //make a copy of the max
        KVPair<Key, Value> retKVPair = heapMin();

		//copy the last element in the queue to the first and rebuild max heap
        //this is similar to the heapsort functionality
        KVPair<Key, Value> kvLast = getKeyValue(heapSize() - 1);
        setKeyValue(0, kvLast);
        deleteKeyValue(heapSize() - 1);
        minHeapify(0);
        return retKVPair;
    }

    public boolean decreaseKey(int index, Key newKey, Value newValue) {
        if (index < 0) {
            return false;
        }
        if (newKey.compareTo(getKeyValue(index).getKey()) > 0) {
            System.out.println("Max Priority Queue: new key is greater than current key");
            return false;
        } else {
            setKeyValue(index, new KVPair<Key, Value>(newKey, newValue));
            while (index > 0 && getKeyValue(getParent(index)).getKey().compareTo(getKeyValue(index).getKey()) > 0) {
                swap(index, getParent(index));
                index = getParent(index);
            }
            return true;
        }
    }

    public void minHeapInsert(Key newKey, Value newValue) {
		//first add the element to the heap (and increase the heap count)
        //in CLRS page 140, they set the new key at the last 
        addKeyValue(newKey, newValue);
        decreaseKey(heapSize() - 1, newKey, newValue);
    }
}
