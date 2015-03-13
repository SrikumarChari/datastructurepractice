package com.sri.datastructures;

public class Queue <E extends Comparable<E>> {
	protected List<E> values;
	protected int queueCount = 0;
	
	public Queue () {
		values = new List<E> ();
	}

	public E dequeue () {
		//remove the first item
		if (queueCount != -1) {
			queueCount--;
			return values.extractHead();
		} else {
			System.out.println("Empty queue");
			return null;
		}
	}
	
	public void enqueue (E queueItem) {
		//add to the end of the queue
		values.add(queueItem);
		queueCount++;
	}
	
	//return the number of items in the queue
	public int queueCount () {
		return values.getCount();
	}
	
	public boolean isEmpty() {
		if (queueCount >= 0)
			return false;
		else
			return true;
	}
}
