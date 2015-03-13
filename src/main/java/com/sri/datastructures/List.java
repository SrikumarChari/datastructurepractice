package com.sri.datastructures;

import java.util.Iterator;

public class List<E extends Comparable<E>> implements Comparable<List<E>>, Iterable<E> {

    @SuppressWarnings("hiding")
    private class ListElement<E extends Comparable<E>> extends Object {

        private E item;
        private ListElement<E> prev;
        private ListElement<E> next;
        private List<E> parent;

        ListElement(E item) {
            this.item = item;
        }

        public ListElement<E> getNext() {
            return next;
        }

        public void setNext(ListElement<E> next) {
            this.next = next;
        }

        public ListElement<E> getPrev() {
            return prev;
        }

        public void setPrev(ListElement<E> prev) {
            this.prev = prev;
        }

        public E getItem() {
            return item;
        }

        public void setItem(E item) {
            this.item = item;
        }

        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o.getClass() != this.getClass()) {
                System.out.println("Called ListElement.equals with an object that is not ListElement");
                return false;
            } else if (item.equals(((ListElement<E>) o).item)) {
                return true;
            } else {
                return false;
            }
        }

        @SuppressWarnings("unused")
        public List<E> getParent() {
            return parent;
        }

        public void setParent(List<E> parent) {
            this.parent = parent;
        }
    }

    private ListElement<E> head;
    private ListElement<E> tail;
    private int elementCount = 0;

    public List() {
        head = null;
        tail = null;
    }

    public int getCount() {
        return elementCount;
    }

    public void setCount(int count) {
        this.elementCount = count;
    }

    public E getHead() {
        return head.getItem();
    }

    public E getTail() {
        return tail.getItem();
    }

    public void setTail(ListElement<E> tail) {
        this.tail = tail;
    }

    public String toString() {
        String outStr = "";
        Iterator<E> itL = iterator();
        while (itL.hasNext()) {
            outStr += itL.next().toString() + "\r";
        }
        return outStr;
    }

    // simple search function
    public E search(E item) {
        ListElement<E> t = head;
        while (t != null) {
            if (t.getItem().equals(item)) {
                return t.getItem();
            } else {
                t = t.getNext();
            }
        }
        return null;
    }

    public void add(E item) { //adds to the end
        //create a list item
        ListElement<E> l = new ListElement<E>(item);
        l.setParent(this);
        // special cases of an empty list
        if (head == null) {
            head = l;
            tail = head;
            elementCount++;
            return;
        }

        // otherwise add all the item to the end and make the new item the tail
        l.setPrev(tail);
        tail.setNext(l);
        tail = tail.getNext();

        // update the count
        elementCount++;
    }

    public E addUnique(E item) { //adds to the end
        E tmp = search(item);
        if (search(item) != null) {
            return tmp;
        }

        //create a list item
        ListElement<E> l = new ListElement<E>(item);
        l.setParent(this);

        // special cases of an empty list
        if (head == null) {
            head = l;
            tail = head;
            elementCount++;
            return l.getItem();
        }

        // otherwise add all the item to the end and make the new item the tail
        l.setPrev(tail);
        tail.setNext(l);
        tail = tail.getNext();

        // update the count
        elementCount++;
        return l.getItem();
    }

    // return the number of items in the list.
    public void insertHead(E item) {
        //create a list item
        ListElement<E> l = new ListElement<E>(item);
        l.setParent(this);

        // special cases of an empty list
        if (head == null) {
            head = l;
            tail = head;
            elementCount++;
            return;
        }

		// otherwise add all new items to the beginning of the list and make the
        // new item the head
        l.setNext(head);
        head.setPrev(l);
        head = head.getPrev();

        // update the count
        elementCount++;
    }

    // extract head - could be used for queues
    public E extractHead() {
        ListElement<E> tmp = head;
        if (elementCount == 0) // empty list
        {
            return null;
        } else if (elementCount == 1) {
            // only one item in the list
            elementCount = 0;; // reset to 0
            head = null;
            return tmp.getItem();
        }

        // make the second list item the head and reduce the count
        ListElement<E> nextHead = head.getNext();
        nextHead.setPrev(null);
        head = nextHead;
        elementCount--;

        return tmp.getItem();
    }

    // extract tail - could be used for stacks
    public E extractTail() {
        ListElement<E> tmp = tail;
        if (elementCount == 0) // empty list
        {
            return null;
        } else if (elementCount == 1) {
            // only one item in the list
            elementCount--;
            return tmp.getItem();
        }

        // make the second last list item the tail and reduce the count
        ListElement<E> nextTail = tail.getPrev();
        nextTail.setNext(null);
        tail = nextTail;
        elementCount--;

        return tmp.getItem();
    }

    public boolean delete(E item) {
        ListElement<E> t = head; // start from the head
        while (t != null) {
            if (t.getItem().compareTo(item) == 0) {
                // found it, so de-link the item and connect predecessor and successor
                ListElement<E> prev = t.getPrev();
                ListElement<E> next = t.getNext();

                if (next != null) {
                    next.setPrev(prev);
                } else //'tail' is being deleted, "prev" is the new tail
                {
                    tail = prev;
                }
                if (prev != null) {
                    prev.setNext(next);
                } else //head is being deleted, 'next' is the head
                {
                    head = next;
                }
                elementCount--;
                return true;
            } else {
                t = t.getNext();
            }
        }
        return false;
    }

	//the next three functions provide random index based operations
    //provides an index based access to the list
    public E get(int index) {
        if (index >= elementCount) //we are implementing a zero based index
        {
            return null;
        }

        Iterator<E> itL = iterator();
        E retVal = null;
        for (int i = 0; itL.hasNext() && i <= index; i++) {
            retVal = itL.next();
        }
        return retVal;
    }

    //provides an index based access to the list
    public boolean set(int index, E element) {
        if (index >= elementCount) //we are implementing a zero based index to be consistent with Java
        //the >= check when elementCount is zero and index 0 is being requested
        //it will correctly return null;
        {
            return false;
        }

        ListElement<E> tmpElement = head;
        for (int i = 0; tmpElement != null && i <= index; i++) {
            if (i == index) {
                tmpElement.setItem(element);
                return true;
            }
            tmpElement = tmpElement.getNext();
        }
        return false;
    }

    //delete the element at "index" i
    public boolean delete(int index) {
        ListElement<E> t = head; // start from the head
        @SuppressWarnings("unused")
        E tmpE;

        //deal with the special cases...
        if (index == 0) {
            return ((tmpE = extractHead()) != null) ? true : false;
        } else if (index == elementCount - 1) {
            return ((tmpE = extractTail()) != null) ? true : false;
        }

        int j = 0;
        while (t != null) {
            if (j == index) {
                // found it, so de-link the item and connect predecessor and successor
                ListElement<E> prev = t.getPrev();
                ListElement<E> next = t.getNext();

                prev.setNext(next);
                next.setPrev(prev);
                elementCount--;
                return true;
            }
            t = t.getNext();
            j++;
        }
        return false;
    }

    public void deleteAll() {
        elementCount = 0;
        head = tail = null;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {

            private ListElement<E> currentElement = head;

            @Override
            public boolean hasNext() {
                if (currentElement == null) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public E next() {
                E tmp = currentElement.getItem();
                currentElement = currentElement.next;
                return tmp;
            }

            @Override
            public void remove() {
                //currentElement = currentElement.next;
                ListElement<E> tmp = currentElement.getPrev();
                ListElement<E> tmpNext = currentElement.getNext();
                currentElement.getNext().setPrev(tmp);
                tmp.setNext(currentElement.getNext());
                currentElement = tmpNext;
            }
        };
        return it;
    }

    @Override
    public int compareTo(List<E> l) {
        //check if the count is the same
        if (elementCount != l.getCount()) {
            return -1;
        }

        Iterator<E> itThis = iterator();
        Iterator<E> itThat = l.iterator();
        while (itThis.hasNext() && itThat.hasNext()) {
            E eThis = itThis.next();
            E eThat = itThat.next();
            if (eThis.compareTo(eThat) != 0) {
                return -1;
            }
        }
        return 0;
    }
}
