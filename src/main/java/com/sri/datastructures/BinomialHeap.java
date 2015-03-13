package com.sri.datastructures;

import com.sri.utility.KVPair;

@SuppressWarnings("unused")
public class BinomialHeap<V extends Comparable<V>> {

    @SuppressWarnings("hiding")
    private class BinomialHeapElement<V extends Comparable<V>> {

        private V value;
        private Double key; // forces a number... maybe later consider a GUID
        private BinomialHeapElement<V> parent;
        // the leftmost child, if no children it is null
        private BinomialHeapElement<V> child;
        private BinomialHeapElement<V> sibling;
        private Integer degree = 0; // number of children;
        private BinomialHeapElement<V> nextRoot = null;

        BinomialHeapElement(Double k, V val) {
            value = val;
            key = k;
        }

        public Integer getDegree() {
            return degree;
        }

        public void setDegree(Integer degree) {
            this.degree = degree;
        }

        public BinomialHeapElement<V> getParent() {
            return parent;
        }

        public void setParent(BinomialHeapElement<V> parent) {
            this.parent = parent;
        }

        public BinomialHeapElement<V> getChild() {
            return child;
        }

        public void setChild(BinomialHeapElement<V> child) {
            this.child = child;
        }

        public BinomialHeapElement<V> getSibling() {
            return sibling;
        }

        public void setSibling(BinomialHeapElement<V> sibling) {
            this.sibling = sibling;
        }

        public Double getKey() {
            return key;
        }

        public void setKey(Double newKey) {
            key = newKey;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public BinomialHeapElement<V> getNextRoot() {
            return nextRoot;
        }

        public void setNextRoot(BinomialHeapElement<V> nextRoot) {
            this.nextRoot = nextRoot;
        }
    };

    BinomialHeapElement<V> head = null; //initialization eliminates need for no-argument constructor
    Integer elementCount = 0;

    /*
     * Min - returns the minimum value stored in the heap. A binomial heap is
     * min-heap-ordered, the minimum key must reside in a root node. The
     * BINOMIAL-HEAP-MINIMUM procedure checks all roots,
     */
    public KVPair<Double, V> min() {
        BinomialHeapElement<V> y = null, x = head;
        Double min = Double.POSITIVE_INFINITY;

        while (x != null) {
            if (x.getKey() < min) {
                // setup the minimum KV Pair
                min = x.getKey();

                // progress through the heap to find the min
                y = x;
                x = x.getSibling();
            }
        }

        // found the min value, return it as a KVPair
        KVPair<Double, V> retKVal = null;
        if (y != null) {
            retKVal = new KVPair<Double, V>();
            retKVal.setKey(y.getKey());
            retKVal.setValue(y.getValue());
        }
        return retKVal;
    }

    public void union(BinomialHeap<V> anotherHeap) {

    }

    private BinomialHeap<V> merge(BinomialHeap<V> one, BinomialHeap<V> two) {
        return null;
    }

    /*
     * The operation of uniting two binomial heaps is used by most of the
     * operations. This function links the Bk-1 tree rooted at node y to the
     * Bk-1 tree rooted at node 'parent'; that is, it makes 'parent' the parent
     * of y. Node 'parent' thus becomes the root of a Bk tree.
     */
    private void link(BinomialHeapElement<V> child, BinomialHeapElement<V> parent) {
        child.setParent(parent);
        child.setSibling(parent.getChild());
        parent.setChild(child);
        parent.setDegree(parent.getDegree() + 1);
    }

    /*
     * getter/setter functions
     */
    public BinomialHeapElement<V> getHead() {
        return head;
    }

    public void setHead(BinomialHeapElement<V> head) {
        this.head = head;
    }

    public Integer getElementCount() {
        return elementCount;
    }

    public void setElementCount(Integer elementCount) {
        this.elementCount = elementCount;
    }
}
