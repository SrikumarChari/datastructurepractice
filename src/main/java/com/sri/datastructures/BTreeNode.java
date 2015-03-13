package com.sri.datastructures;

@SuppressWarnings("unused")
public class BTreeNode<Key extends Comparable<Key>, Value> {

    private int numChildren = 0;
    private int maxChildren;
    private int numKeys = 0;
    private boolean leaf;
    private BTreeNodeEntry<Key, Value>[] keys = null; //for now let's use array - next assignment will be move it to a linked list
    private BTreeNode<Key, Value>[] children = null;
//	private Comparator<BTreeNode> comparator = new Comparator <BTreeNode>() {
//		@Override
//		public int compare(BTreeNode arg0, BTreeNode arg1) {
//			return (arg0.getKey(0)).compareTo(arg1.getKey(0));
//		}
//	};

    public static class BTreeNodeEntry<Key extends Comparable<Key>, Value> {

        //if the node is a leaf only the value is used, else use "next" to iterate through children

        private Key key;
        private Value value;

        public BTreeNodeEntry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    @SuppressWarnings("unchecked")
    public BTreeNode(int twoT) {
        this.keys = new BTreeNodeEntry[twoT - 1];
        this.children = new BTreeNode[twoT];
        this.maxChildren = twoT;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }

    public int getNumKeys() {
        return numKeys;
    }

    public void setNumKeys(int nKeys) {
        numKeys = nKeys;
    }

    public BTreeNodeEntry<Key, Value>[] getKeys() {
        return keys;
    }

    public void setKeys(BTreeNodeEntry<Key, Value>[] keys) {
        this.keys = keys;
    }

    public Key getKey(int index) {
        return keys[index].key;
    }

    public void setKey(BTreeNodeEntry<Key, Value> key) {
        keys[numKeys] = key;
        //numKeys++;
    }

    public void setKey(int index, Key key, Value value) {
        if (keys[index] == null) {
            keys[index] = new BTreeNodeEntry<Key, Value>(key, value);
        }
        keys[index].key = key;
        keys[index].value = value;
		//keys[index] = key;
        //numKeys++;
    }

    public Value getValue(int index) {
        return keys[index].value;
    }

    public void setValue(int index, Value value) {
        keys[index].value = value;
    }

    public BTreeNode<Key, Value> getChild(int index) {
        return children[index];
    }

    public void setChild(BTreeNode<Key, Value> n, int index) {
        children[index] = n;
        numChildren++;
    }

    public void addChild(BTreeNode<Key, Value> n) {
        if (children[numChildren] == null) {
            children[numChildren] = new BTreeNode<Key, Value>(maxChildren);
        }
        children[numChildren] = n;
        numChildren++;
    }
}
