package com.sri.datastructures;

@SuppressWarnings("unused")
public class BTree<Key extends Comparable<Key>, Value> {

    BTreeNode<Key, Value> root = null;
    private int maxChildren; // 2t
    private int maxKeys; // 2t - 1
    private int minKeys; // t -1
    private int minChildren; // t

    public BTree(int twoT) {
        maxChildren = twoT;
        maxKeys = twoT - 1;
        minChildren = twoT / 2;
        minKeys = minChildren - 1;
        root = new BTreeNode<Key, Value>(maxChildren);
        root.setLeaf(true);
        root.setNumChildren(0);
    }

    public BTreeNode<Key, Value> searchCLR(BTreeNode<Key, Value> node,
            Key searchKey) {
        int i = 0;
        int keySize = node.getNumChildren();

        while (i < keySize
                && ((Comparable<Key>) searchKey).compareTo(node.getKey(i)) > 0) // checks the value of the children to see if it is contained in the
        // children
        {
            i++;
        }

        if (i <= keySize
                && ((Comparable<Key>) searchKey).compareTo(node.getKey(i)) == 0) {
            return node;
        }

        if (node.isLeaf()) // since this is a leaf there are no more children below
        // this node so return null
        {
            return null;
        }

        // diskRead (node.getChild(i), searchKey);
        return searchCLR(node.getChild(i), searchKey);
    }

    // insert
    public void insert(Key k, Value v) {
        BTreeNode<Key, Value> r = root;

        if (r.getNumKeys() == maxKeys) {
            // root is full so split the node
            BTreeNode<Key, Value> s = new BTreeNode<Key, Value>(maxChildren);
            root = s;
            s.setLeaf(false);
            s.setNumKeys(0);
            s.addChild(r);
            splitChild(s, 0, r);
            insertNonFull(s, k, v);
        } else {
            insertNonFull(r, k, v);
        }
    }

    public BTreeNode<Key, Value> search(Key k) {
        return searchCLR(root, k);
    }

    // displays btree
    public void print() {
        display(root);
        // System.out.println("There are " + count + " values in total.");
    }

    private void insertNonFull(BTreeNode<Key, Value> x, Key k, Value v) {
        int i = x.getNumKeys() - 1; // subtracting one because arrays start with
        // zero
        if (x.isLeaf()) {
            while (i >= 0 && x.getKey(i) != null
                    && ((Comparable<Key>) k).compareTo(x.getKey(i)) < 0) {
                x.setKey(i + 1, x.getKey(i), x.getValue(i));
                i--;
            }
			// BTreeNodeEntry<Key, Value> p = new BTreeNodeEntry<Key, Value> (k,
            // v);
            x.setKey(i + 1, k, v);
            x.setNumKeys(x.getNumKeys() + 1);
        } else {
            while (i >= 0 && x.getKey(i) != null
                    && ((Comparable<Key>) k).compareTo(x.getKey(i)) < 0) {
                i--;
            }
            i++;
            // TODO: disk read child i of x
            if (x.getChild(i) != null && x.getChild(i).getNumKeys() == maxKeys) {
                splitChild(x, i, x.getChild(i));
                if (((Comparable<Key>) k).compareTo(x.getKey(i)) < 0) {
                    i++;
                }
            }
            insertNonFull(x.getChild(i), k, v);
        }
    }

    /*
     * Split a full node using the following rules - median key value goes to
     * the parent - all key values less than the median goes stay with the
     * splitNode - all key values higher than the median goes to a new node
     * 
     * splitChild - the node to be split parent - parent of the split node
     * splitIndex - index at which point the splitChild is to be split
     * (typically the calling function will provide the median or minKeys)
     */
    private void splitChild(BTreeNode<Key, Value> parent, int splitIndex,
            BTreeNode<Key, Value> splitChild) {
        BTreeNode<Key, Value> newNode = new BTreeNode<Key, Value>(maxChildren);

		// copy the right side of the splitChild node to the new node
        // the number of the keys to be copied will be 't' (or minKeys)
        newNode.setLeaf(splitChild.isLeaf());
        newNode.setNumKeys(minKeys);
        for (int j = 0; j < minKeys; j++) // it is not <= minKeys because array start at zero
        {
            newNode.setKey(j, splitChild.getKey(j + minChildren),
                    splitChild.getValue(j + minChildren)); // we add a "+1"
        }															// because j starts
        // at zero - cancel
        // not needed
        // because I screwed
        // up

        if (!splitChild.isLeaf()) // copy the children since splitChild is not a leaf
        // after this loop newNode has both the right side keys and children
        // of the splitChild
        {
            for (int j = 0; j <= minKeys; j++) {
                newNode.addChild(splitChild.getChild(j + minChildren));
            }
        }

		// since the splitChild keys/values/children are reduced, update the
        // number of keys in the splitChild
        splitChild.setNumKeys(minKeys);

        // shift the keys in the parent to the right by one
        for (int j = parent.getNumKeys(); j >= splitIndex + 1; j--) // removed the +1 for j because arrays start at zero
        // for (int j = parent.getNumKeys() + 1; j >= splitIndex + 1; j-- )
        {
            parent.setChild(parent.getChild(j), j + 1);
        }

        // add the new node to the parent
        parent.setChild(newNode, splitIndex + 1);
		// parent.setChild(newNode, splitIndex);

		// shift the keys
        // for (int j = parent.getNumKeys(); j >= splitIndex; j-- )
        for (int j = parent.getNumKeys(); j > splitIndex; j--) // removed the >=
        {
            parent.setKey(j + 1, parent.getKey(j), parent.getValue(j));
        }

		// minChildren = t but we do minChildren-1 because Java arrays start at
        // zero
        // however we may need to make it conditional on whether maxChildren is
        // even... this seemed to work when it was an odd number
        parent.setKey(splitIndex, splitChild.getKey(minChildren - 1),
                splitChild.getValue(minChildren - 1));
        parent.setNumKeys(parent.getNumKeys() + 1);

        // TODO: write nodes parent, splitNode and newNode to disk ---
    }

    // displays btree
    private void display(BTreeNode<Key, Value> n) {
        for (int i = 0; i < n.getNumKeys(); i++) {
            if (n.getKey(i) != null) {
                System.out.print("Key: " + n.getKey(i) + "\t Value: "
                        + n.getValue(i) + "\n");
            }
        }
        for (int j = 0; j < n.getNumChildren(); j++) {
            if (n.getChild(j) != null) {
                System.out.println("\n" + "Key: " + n.getKey(0) + "\t Value: "
                        + n.getValue(0) + " " + j + "\n");
                display(n.getChild(j));
            }
        }
        System.out.println();
    }

    private void diskRead(BTreeNode<Key, Value> child, Key searchKey) {
    }

    private BTreeNode<Key, Value> allocateNode() {
		// allocates an empty record or space in the database...
        // but here we will just allocate memory for node and return it;
        return null;
    }
}
