package dataStructures;

import java.util.Iterator;

public class Tree<T extends Comparable<T>> implements Iterable<T> {
	@SuppressWarnings("hiding")
	protected class TreeElement<T extends Comparable<T>> {
		private T element;
		private TreeElement<T> left;
		private TreeElement<T> right;
		private TreeElement<T> parent;

		protected TreeElement() {
			// item = item1;
		}

		protected TreeElement(T item2) {
			element = item2;
		}

		protected T getElement() {
			return element;
		}

		protected void setElement(T item) {
			this.element = item;
		}

		protected TreeElement<T> getLeft() {
			return left;
		}

		protected void setLeft(TreeElement<T> left) {
			this.left = left;
		}

		protected TreeElement<T> getRight() {
			return right;
		}

		protected void setRight(TreeElement<T> right) {
			this.right = right;
		}

		protected TreeElement<T> getParent() {
			return parent;
		}

		protected void setParent(TreeElement<T> parent) {
			this.parent = parent;
		}

		public boolean equals (Object o) {
			@SuppressWarnings("unchecked")
			TreeElement<T> te = (TreeElement<T>)o;
			return element.equals(te.getElement());
		}

		protected int findWhichChild(TreeElement<T> child) {
			// check if "this" is the parent
			if (this != child.parent)
				return -1; // the child belongs to someone else

			if (left != null) {
				if (left == child)
					return 0;
			} else if (right != null) {
				if (right == child)
					return 1;
			}
			return -1; // the child belongs to someone else
		}

		public int compareTo(TreeElement<T> anElement) {
			if (anElement == null)
				return 1;
			return element.compareTo(anElement.getElement());
		}

	}

	//class variables...
	protected TreeElement<T> root;
	protected Integer elementCount = 0;

	public T getRoot() {
		return root.getElement();
	}

	// print the tree with in-order traversal
	public void print() {
		print(root);
	}
	
	//return the number of items
	public Integer getCount() {
		return elementCount;
	}

	//find an item with the native type
	public T search (T item) {
		TreeElement<T> t = search (root, new TreeElement<T>(item));
		if (t != null)
			return t.getElement();
		else
			return null;
	}

	public void insert(T element) {
		TreeElement<T> parent = null;
		TreeElement<T> current = root;
	
		//create a new tree element
		TreeElement<T> newElement = new TreeElement<T>(element);
		
		//find the insertion point and identify the parent
		while (current != null) {
			parent = current;
			if (newElement.getElement().compareTo(current.getElement()) == -1)
				current = current.getLeft();
			else
				current = current.getRight();
		}
		
		//set the parent
		newElement.setParent(parent);
		if (parent == null) {
			//the new node is the root
			root = newElement;
			return;
		} else if (newElement.getElement().compareTo(parent.getElement()) == -1)
			//it is lower than the parent so it goes to the left
			parent.setLeft(newElement);
		else
			//it is higher than parent so it goes to the right
			parent.setRight(newElement);
		elementCount++;
	}

//	// add tree item using normal binary tree rules
//	public void recursiveInsert(T element) {
//		TreeElement<T> newElement = new TreeElement<T>();
//		newElement.setElement(element);
//		
//		// root not established
//		if (root == null) {
//			root = newElement;
//			return;
//		}
//
//		// add the new leaf
//		insertTreeElement(root, newElement);
//	}

	// find the min which by definition is the left most item
	public T min() {
		TreeElement<T> minE = traverseLeft(root); 
		return minE.getElement();
	}

	// find the min which by definition is the left most item
	public T max() {
		TreeElement<T> maxE = traverseRight(root); 
		return maxE.getElement();
	}

	// find and delete the item
	public boolean delete (T element) {
		TreeElement<T> dElement = search (root, new TreeElement<T> (element));
		if (dElement == null)
			return false;
		else
			return delete(dElement);
	}
	// returns the height of the tree
	 // height of tree (1-node tree has height 0)
    public int height () { return height(root); }
    private int height (TreeElement<T> x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.getLeft()), height(x.getRight()));
    }

	// find the successor
	public T successor(T item) {
		// find the item first
		TreeElement<T> newItem = search(root, new TreeElement<T>(item));
		if (newItem == null)
			//can't find a successor for something that doesn't exist in the tree
			return null;
		
		TreeElement<T> s = successor(newItem);
		if (s != null)
			return s.getElement();
		else
			return null;
	}

	// find the successor
	public T predecessor(T item) {
		// find the item first
		TreeElement<T> newItem = search(root, new TreeElement<T>(item));
		if (newItem == null)
			//can't find a predecessor for something that doesn't exist in the tree
			return null;
		
		TreeElement<T> s = predecessor(newItem);
		if (s != null)
			return s.getElement();
		else
			return null;
	}

	private boolean delete(TreeElement<T> item) {
		return deleteTreeElement(root, item);
	}

	@SuppressWarnings("unused")
	private void insertTreeElement(TreeElement<T> parent, TreeElement<T> item) {
		// use the call back function and compare the items
		if (parent.getElement().compareTo(item.getElement()) >= 0) {
			// if it is a duplicate string or has a lesser value, then add it to
			// the left
			if (parent.getLeft() == null) {
				parent.setLeft(item);
				item.setParent(parent);
			} else
				insertTreeElement(parent.getLeft(), item);
		} else {
			if (parent.getRight() == null) {
				parent.setRight(item);
				item.setParent(parent);
			} else
				insertTreeElement(parent.getRight(), item);
		}
	}

	protected TreeElement<T> search(TreeElement<T> current, TreeElement<T> searchItem) {
		// obviously can do much if these are null
		if (searchItem == null || current == null)
			return null;

		// compare to the current tree item
		int result = current.getElement().compareTo(searchItem.getElement());
		if (result == 0)
			// found it
			return current;
		else if (result > 0)
			// this item is greater, so traverse to the left
			return search(current.getLeft(), searchItem);
		else
			// this item is lesser so traverse to the right
			return search(current.getRight(), searchItem);
	}

	// find the successor
	protected TreeElement<T> successor(TreeElement<T> element) {
		if (element.getRight() != null) {
			// simple case... just simply find the left most child and that
			// will be the successor
			return traverseLeft(element.getRight());
		} 

		// if the right subtree of "item" is empty and "item" has a
		// successor y, then y is the lowest ancestor of "item" whose
		// left child is also an ancestor of "item". To find y, we
		// simply go up the tree from "item" until we encounter a node that
		// is the left child of its parent;
		TreeElement<T> y = element.getParent();
		while (y != null && element.compareTo(y.getRight()) == 0) {
			element = y;
			y = y.getParent();
		}
		return y;
	}

	// find the predecessor
	protected TreeElement<T> predecessor(TreeElement<T> element) {
		if (element.getLeft() != null) {
			// simple case... just simply find the right most child on the left
			// side of the tree and that will be the predecessor
			return traverseRight(element.getLeft());
		} 
		
		// if the left tree of "item" is empty then the predecessor is
		// either the parent or the predecessor y is the lowest
		// ancestor of "item" whole right child is also an ancestor of "item".
		TreeElement<T> y = element.getParent();
		while (y != null && element.compareTo(y.getLeft()) == 0) {
			element = y;
			y = y.getParent();
		}
		return y;
	}


	// given an item, traverse to the left most part of that item
	private TreeElement<T> traverseLeft(TreeElement<T> item) {
		if (item == null || item.getLeft() == null)
			return item;
		return traverseLeft(item.getLeft());
	}

	// given an item, traverse to the left most part of that item
	private TreeElement<T> traverseRight(TreeElement<T> item) {
		if (item.getRight() == null)
			return item;
		return traverseRight(item.getRight());
	}

	private boolean deleteTreeElement(TreeElement<T> treeItem, TreeElement<T> item) {
		if (treeItem == null)
			return false;

		int result = treeItem.getElement().compareTo(item.getElement());
		if (result == 0) {
			// delete the node
			deleteTreeNode(treeItem.getParent(), treeItem);
			elementCount--;
			return true;
		} else if (result > 0) {
			return deleteTreeElement(treeItem.getLeft(), item);
		} else
			return deleteTreeElement(treeItem.getRight(), item);
	}

	private void deleteTreeNode(TreeElement<T> parent, TreeElement<T> node) {
		/*
		 * case 1: the node is the root of the tree, this is the most
		 * complicated scenario; it is at the top of function so we can avoid
		 * checking if the parent is valid later in the functions
		 * 
		 * step 1 - find the smallest value in the right subtree (because all
		 * the values on the left are lower in value step 2 - copy the values
		 * from search in step 1 step 3 - delete the smallest value node and
		 * re-connect the tree
		 */

		if (node.getLeft() != null && node.getRight() != null) {
			// step 1
			TreeElement<T> minItem = traverseLeft(node.getRight());

			// step 2
			node.setElement(minItem.getElement());

			// step 3
			TreeElement<T> minItemParent = minItem.getParent();
			if (minItemParent.findWhichChild(minItem) == 0)
				minItemParent.setLeft(null);
			else
				minItemParent.setRight(null);
			return;
		}

		/*
		 * case 2 - leaf node has no children - fairly straight forward
		 */
		if (node.getLeft() == null && node.getRight() == null) {
			if (parent == null) {
				// this is the root node and there are no children. this results
				// in an empty tree
				root = null;
				return;
			}

			if (parent.findWhichChild(node) == 0)
				// left child
				parent.setLeft(null);
			else
				parent.setRight(null);

			return;
		}

		/*
		 * case 3 - leaf node has only one child - figure which child and
		 * connect that child to the parent
		 */
		if (node.getLeft() != null && node.getRight() == null) {
			if (parent == null) {
				// this is the root node and all children are lesser in value
				// (should consider balancing the tree :) )
				// just make the left node the root
				root = node.getLeft();
				return;
			}
			if (parent.findWhichChild(node) == 0)
				parent.setLeft(node.getLeft());
			else
				parent.setRight(node.getLeft());
		} else if (node.getLeft() == null && node.getRight() != null) {
			if (parent == null) {
				// this is the root node and all children are greater in value
				// (should consider balancing the tree :) )
				// just make the right node the root
				root = node.getRight();
				return;
			}
			if (parent.findWhichChild(node) == 0)
				parent.setLeft(node.getRight());
			else
				parent.setRight(node.getRight());
		}
	}

	private void print(TreeElement<T> treeItem) {
		if (treeItem == null) // duh
			return;

		// first the left side
		print(treeItem.getLeft());

		// print the value
		System.out.println(treeItem.getElement());

		// now the right side
		print(treeItem.getRight());

	}

	@Override
	public Iterator<T> iterator() {		
		Iterator<T> it = new Iterator<T> () {
			private TreeElement<T> current = traverseLeft(root);
			
			@Override
			public boolean hasNext() {
				if (successor(current) != null)
					return true;
				return false;
			}

			@Override
			public T next() {
				T element = current.getElement();
				TreeElement<T> next = successor(current);
				current = next;
				return element;
			}

			@Override
			public void remove() {
				TreeElement<T> tmp = current;
				current = successor(current);
				delete(tmp);
			}
		};
		return it;
	}
}
