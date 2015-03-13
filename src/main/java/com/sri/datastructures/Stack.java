package com.sri.datastructures;

public class Stack<T extends Comparable<T>> {
	List<T> list;
	
	Stack (List<T> list) {
		this.list = list; 
	}
	
	boolean isEmpty() {
		if (list.getCount() > 0)
			return false;
		else
			return true;
	}
	
	//push an item to the stack - by definition it goes to the end of the list
	public void push(T item) {
		//add to the end of the list
		list.add(item);
	}
	
	//extract the last added value
	public T pop () {
		T item = list.extractTail();
		if (item == null)
			System.out.print("Stack underflow\r");
		return item;
	}
}
