package com.sri.utility;

public class KVPair<Key, Value> {
	private Key key;
	private Value value;
	//some functions may return the KVPair to the data structures 
	//instead of requring another search, we will also return the 
	//index or some kind of identifier
	private Integer index = -1; 

	public KVPair(Key newKey, Value newValue) {
		key = newKey;
		value = newValue;
	}

	public KVPair(Integer newIndex, Key newKey, Value newValue) {
		key = newKey;
		value = newValue;
		index = newIndex;
	}

	public KVPair() {
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}
	
	public String toString () {
		return key.toString() + " " + value.toString();
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
