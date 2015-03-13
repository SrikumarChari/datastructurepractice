package com.sri.datastructures;

public class MinHeap<Key extends Comparable<Key>, Value extends Comparable<Value>> extends Heap<Key, Value> {

	// We don't have MAX-HEAP value - we will support a theoretically infinite
    // size
    public MinHeap() {

    }

    public MinHeap(Key[] keys, Value[] values) {
        super(keys, values);
        buildMinHeap();
    }

    /*
     * build a min heap
     * 
     * we are mimicking an array representation using List
     * for storing an n-element heap, 
     * the leaves are the nodes indexed by n/2 + 1, n/2 + 2, . . . , n.
     */
    public void buildMinHeap() {
		//see comments above... all elements n/2+1, ..., n are leaves, so start at n/2
        //no need to traverse the entire array. Start at n/2
        for (int i = heapSize() / 2 - 1; i >= 0; i--) {
            minHeapify(i);
        }
    }

    /*
     * Heapsort - 
     */
	//it is assumed that the list is already populated
    //with the elements to be populated
    public void heapSort() {
        buildMinHeap();
        for (int i = heapSize() - 1; i >= 0; i--) {
            swap(0, i);
            heapCount--;
            minHeapify(0);
        }
        resetCount();
    }

    /*
     * When MIN-HEAPIFY is called, it is assumed that the binary trees rooted at
     * LEFT(i) and RIGHT(i) are min-heaps, but that A[i] may be smaller than
     * its children, thus violating the min-heap property. The function of
     * MIN-HEAPIFY is to let the value at A[i] "rise up" in the minheap so
     * that the subtree rooted at index i becomes a min-heap.
     */
    protected void minHeapify(int queueIndex) {
        int left = getLeft(queueIndex);
        int right = getRight(queueIndex);
        int smallest = -1;

        //see if the left value is larger
        if (left <= heapSize() - 1 && getKeyValue(left).getKey().compareTo(getKeyValue(queueIndex).getKey()) < 0) {
            smallest = left;
        } else {
            smallest = queueIndex;
        }

        //see if the right value is larger
        if (right <= heapSize() - 1 && getKeyValue(right).getKey().compareTo(getKeyValue(smallest).getKey()) < 0) {
            smallest = right;
        }

		// if the index (queueIndex) being validated for the largest is not the
        // largest, exchange the values with the largest and
        // then recurse into the largest side and swap smaller elements
        if (smallest != queueIndex) {
            swap(smallest, queueIndex);
            minHeapify(smallest);
        }
    }

}
