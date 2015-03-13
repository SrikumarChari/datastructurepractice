package com.sri.datastructures;

import com.sri.utility.KVPair;

public class MaxHeap<Key extends Comparable<Key>, Value extends Comparable<Value>> extends Heap<Key, Value> {
	// extends the Queue implementation to provide a Max Priority Queue (Page
    // 138 CLRS) This uses Heap algorithms which requires an (integer) index and
    // the value at that index The base class Queue already has the list of
    // 'values', here we extend it to provide

	// We don't have MAX-HEAP value - we will support a theoretically infinite
    // size
    public MaxHeap(Key[] keys, Value[] values) {
        super(keys, values);
        buildMaxHeap();
    }

    /*
     * build a max heap
     * 
     * we are mimicking an array representation using List
     * for storing an n-element heap, 
     * the leaves are the nodes indexed by n/2 + 1, n/2 + 2, . . . , n.
     */
    public void buildMaxHeap() {
        for (int i = heapSize() / 2 - 1; i >= 0; i--) //see comments above... all elements n/2+1, ..., n are leaves,
        //no need to traverse the entire array. Start at n/2
        //we subtract a 1 from the starting value of i because of zero based index
        {
            maxHeapify(i);
        }
    }

	//it is assumed that the list is already populated
    //with the elements to be populated
    public void heapSort() {
        buildMaxHeap();
        for (int i = heapSize() - 1; i >= 0; i--) {
            swap(0, i);
            heapCount--;
            maxHeapify(0);
        }
        resetCount();
    }

    /*
     * When MAX-HEAPIFY is called, it is assumed that the binary trees rooted at
     * LEFT(i) and RIGHT(i) are max-heaps, but that A[i] may be smaller than
     * its children, thus violating the max-heap property. The function of
     * MAX-HEAPIFY is to let the value at A[i] "float down" in the maxheap so
     * that the subtree rooted at index i becomes a max-heap.
     * 
     * the properties of the heap are similar to the binary tree, the largest 
     * value is at the top
     */
    protected void maxHeapify(int heapIndex) {
        int left = getLeft(heapIndex);
        int right = getRight(heapIndex);
        int largest = -1;

        //see if the left value is larger
        KVPair<Key, Value> kvLeft = getKeyValue(left);
        KVPair<Key, Value> kvRight = getKeyValue(right);
        if (left <= heapSize() - 1 && kvLeft.getKey().compareTo(getKeyValue(heapIndex).getKey()) > 0) {
            largest = left;
        } else {
            largest = heapIndex;
        }

        //see if the right value is larger
        if (right <= heapSize() - 1 && kvRight.getKey().compareTo(getKeyValue(largest).getKey()) > 0) {
            largest = right;
        }

		// if the index (queueIndex) being validated for the largest is not the
        // largest, exchange the values with the largest and
        // then recurse into the largest side and swap smaller elements
        if (largest != heapIndex) {
            swap(largest, heapIndex);
            maxHeapify(largest);
        }
    }
}
