package dataStructures;

public class RBTree<T extends Comparable<T>> extends Tree<T> {
	@SuppressWarnings("hiding")
	protected class RBTreeElement<T extends Comparable<T>> extends TreeElement<T> {
		private RBColor color;

		public RBTreeElement(T insertItem) {
			super(insertItem);
		}

		public RBColor getColor() {
			return color;
		}

		public void setColor(RBColor color) {
			this.color = color;
		}
	}
	/*
	 * Following are IMMUTABLE properties of a Red Black tree
	 * 
	 * 1. Every node is either red or black.
	 * 2. The root is black.
	 * 3. Every leaf (NIL) is black.
	 * 4. If a node is red, then both its children are black.
	 * 5. For each node, all paths from the node to descendant leaves contain the same number of black nodes
	 * 
	 */

	// do all paths from root to leaf have same number of black edges?
	public boolean isBalanced() {
		int black = 0; // number of black links on path from root to min and root to max should be the same
		RBTreeElement<T> x = (RBTreeElement<T>) root;
		while (x != null) {
			if (x.getColor() == RBColor.BLACK)
				black++;
			x = (RBTreeElement<T>) x.getLeft();
		}

		x = (RBTreeElement<T>) root;
		while (x != null) {
			if (x.getColor() == RBColor.BLACK)
				black--;
			x = (RBTreeElement<T>) x.getRight();
		}
		return black == 0;
	}

	// the rbTree insertion similar to a regular tree. The main difference is
	// that we have to call rbInsertFixUp to adjust the colors.
	// unlike the regular tree, we will NOT use recursion because we just want
	// to try something new
	public void rbInsert(T insertItem) {
		RBTreeElement<T> parent = null;
		RBTreeElement<T> current = (RBTreeElement<T>) root;
		
		// find the insertion point and identify the parent
		while (current != null) {
			parent = current;
			if (insertItem.compareTo(current.getElement()) == -1)
				current = (RBTreeElement<T>) current.getLeft();
			else
				current = (RBTreeElement<T>) current.getRight();
		}


		//create the new element and set the parent.
		RBTreeElement<T> newElem = new RBTreeElement<T> (insertItem);
		newElem.setParent(parent);
		if (parent == null) {
			// the new node is the root
			root = newElem;
			newElem.setColor(RBColor.BLACK);
			return;
		} else if (newElem.getElement().compareTo(parent.getElement()) == -1)
			// it is lower than 'y' so it goes to the left
			parent.setLeft(newElem);
		else
			// it is higher than 'y' so it goes to the right
			parent.setRight(newElem);

		// just set these to null for now... the rbInsertFix will change them
		newElem.setLeft(null);
		newElem.setRight(null);
		newElem.setColor(RBColor.RED); // all nodes start as RED

		// fix the tree balance...
		rbInsertFixup(newElem);
	}

	public void print() {
		// override the base class print so we can print the node colors
		print((RBTreeElement<T>) root, 0);
	}

	public void rbDelete (T item) {
		rbDelete((RBTreeElement<T>) search (root, new RBTreeElement<T>(item)));	
	}
	/*
	 * Doesn't use sentinels (as in CLR)
	 * 
	 * deleteItem - item to be deleted; note it is expected that the calling
	 * function did a find or other approach to find the tree item. This CANNOT
	 * be a newly created item.
	 */
	private void rbDelete(RBTreeElement<T> deleteItem) {
		if (deleteItem == null) //duh
			return;
		
		// get the next
		RBTreeElement<T> y, x;
		if (deleteItem.getLeft() == null || deleteItem.getRight() == null)
			y = deleteItem;
		else
			// if the delete item has children, then find the y
			y = (RBTreeElement<T>) successor(deleteItem);

		if (y.getLeft() != null)
			x = (RBTreeElement<T>) y.getLeft();
		else
			x = (RBTreeElement<T>) y.getRight();

		if (x != null)
			x.setParent(y.getParent());

		if (y.getParent() == null)
			root = y;
		else if (y == y.getParent().getLeft())
			y.getParent().setLeft(x);
		else
			y.getParent().setRight(x);

		if (y != deleteItem)
			deleteItem.setElement(y.getElement());

		if (y.getColor() == RBColor.BLACK)
			rbDeleteFixup(x);
	}

	/*
	 * definitions item - the inserted item parent, grandparent - they are
	 * obvious uncle - grandparent's left or right child
	 * 
	 * The function deals with the following three cases:
	 * 
	 * Case 1: item's uncle is red Case 2: item's uncle is black and the item is
	 * a right child Case 3: item's uncle is black and the item is a left child
	 */
	private void rbInsertFixup(RBTreeElement<T> insertItem) {
		// some helper variables to make the code more readble
		RBTreeElement<T> y;
		if (insertItem == null)
			return;

		while (insertItem != null
				&& insertItem.getParent() != null
				&& insertItem.getParent().getParent() != null
				&& ((RBTreeElement<T>) insertItem.getParent()).getColor() == RBColor.RED) {
			if (insertItem.getParent() == insertItem.getParent().getParent()
					.getLeft()) {
				// get the right uncle
				y = (RBTreeElement<T>) insertItem.getParent().getParent()
						.getRight();
				if (y != null && y.getColor() == RBColor.RED) {// the uncle is
																// red
					// Case 1: item's uncle is red
					((RBTreeElement<T>) insertItem.getParent())
							.setColor(RBColor.BLACK);
					y.setColor(RBColor.BLACK);
					((RBTreeElement<T>) insertItem.getParent().getParent())
							.setColor(RBColor.RED);

					// now go up the tree and adjust color accordingly
					// we've already adjusted the parent's color, so next
					// iteration will be the current grandparent
					insertItem = (RBTreeElement<T>) insertItem.getParent()
							.getParent();
				} else { // uncle is BLACK
					if (insertItem.getParent().getRight() == insertItem) {
						// Case 2: item's uncle is black and the item is a right child
						// insertItem = (myRBTreeElement<T>)
						// insertItem.getParent();
						insertItem = (RBTreeElement<T>) insertItem.getParent();
						leftRotate(insertItem); // transform the Case 2 into a
												// Case 3 situation and fix it
												// below
					}

					// the insert item is the left child (or has become the left
					// child after the left rotate above)
					// Case 3: item's uncle is black and the item is a left child
					((RBTreeElement<T>) insertItem.getParent())
							.setColor(RBColor.BLACK);
					((RBTreeElement<T>) insertItem.getParent().getParent())
							.setColor(RBColor.RED);
					rightRotate((RBTreeElement<T>) insertItem.getParent()
							.getParent());
				}
			} else { // parent == getParent().getParent().getright ()
				// get the left uncle
				y = (RBTreeElement<T>) insertItem.getParent().getParent()
						.getLeft();
				if (y != null && y.getColor() == RBColor.RED) {
					// Case 1: item's uncle is red
					((RBTreeElement<T>) insertItem.getParent())
							.setColor(RBColor.BLACK);
					y.setColor(RBColor.BLACK);
					((RBTreeElement<T>) insertItem.getParent().getParent())
							.setColor(RBColor.RED);

					// now go up the tree and adjust color accordingly
					// we've already adjusted the parent's color, so next
					// iteration will be the current grandparent
					insertItem = (RBTreeElement<T>) insertItem.getParent()
							.getParent();
				} else {
					if (insertItem.getParent().getLeft() == insertItem) {
						// Case 2: item's uncle is black and the item is a right
						// child
						insertItem = (RBTreeElement<T>) insertItem.getParent();
						rightRotate((RBTreeElement<T>) insertItem); // transform the Case 2 into a Case 3 situation and fix it below
					}

					// the insert item is the left child (or has become the left
					// child after the right rotate above)
					// Case 3: item's uncle is black and the item is a left child
					((RBTreeElement<T>) insertItem.getParent())
							.setColor(RBColor.BLACK);
					((RBTreeElement<T>) insertItem.getParent().getParent())
							.setColor(RBColor.RED);
					leftRotate((RBTreeElement<T>) insertItem.getParent()
							.getParent());
				}
			}
		}
		((RBTreeElement<T>) root).setColor(RBColor.BLACK);
	}

	/*
	 * w - delete item's sibling
	 * 
	 */
	private void rbDeleteFixup(RBTreeElement<T> deleteItem) {
		while (deleteItem != null && deleteItem != root && deleteItem.getColor() == RBColor.BLACK) {
			if (deleteItem == deleteItem.getParent().getLeft()) {
				RBTreeElement<T> w = (RBTreeElement<T>) deleteItem.getParent().getRight();
				if (w.getColor() == RBColor.RED) {
					//Case 1: sibling is RED, convert it to case 2, 3 or 4
					((RBTreeElement<T>) deleteItem.getParent()).setColor(RBColor.BLACK);
					leftRotate ((RBTreeElement<T>) deleteItem.getParent());
					w = (RBTreeElement<T>) deleteItem.getParent().getRight();
				}
				if (((RBTreeElement<T>) w.getLeft()).getColor() == RBColor.BLACK && ((RBTreeElement<T>) w.getRight()).getColor() == RBColor.BLACK) {
					//Case 2: xs sibling w is black, and both of ws children are black
					w.setColor(RBColor.RED);
					
					//go up the tree and continue fix up process
					deleteItem = (RBTreeElement<T>) deleteItem.getParent();
				} else {
					if (((RBTreeElement<T>) w.getRight()).getColor() == RBColor.BLACK) {
						//Case 3: xs sibling w is black, ws left child is red, and ws right child is black
						((RBTreeElement<T>) w.getLeft()).setColor(RBColor.BLACK);
						w.setColor(RBColor.RED);
						rightRotate(w);
						w = (RBTreeElement<T>) deleteItem.getParent().getRight();
					}
					//Case 4: x's sibling w is black, and w's right child is red
					w.setColor(((RBTreeElement<T>) deleteItem.getParent()).getColor());
					((RBTreeElement<T>) deleteItem.getParent()).setColor(RBColor.BLACK);
					((RBTreeElement<T>) w.getRight()).setColor(RBColor.BLACK);
					leftRotate((RBTreeElement<T>) deleteItem.getParent());
					deleteItem = (RBTreeElement<T>) root;
				}
			} else { //symmetrical to the 'if' case with left and right swapped
				RBTreeElement<T> w = (RBTreeElement<T>) deleteItem.getParent().getLeft();
				if (w.getColor() == RBColor.RED) {
					//Case 1: sibling is RED, convert it to case 2, 3 or 4
					((RBTreeElement<T>) deleteItem.getParent()).setColor(RBColor.BLACK);
					rightRotate ((RBTreeElement<T>) deleteItem.getParent());
					w = (RBTreeElement<T>) deleteItem.getParent().getLeft();
				}
				if (((RBTreeElement<T>) w.getRight()).getColor() == RBColor.BLACK && ((RBTreeElement<T>) w.getLeft()).getColor() == RBColor.BLACK) {
					//Case 2: x's sibling w is black, and both of w's children are black
					w.setColor(RBColor.RED);

					//go up the tree and continue fix up process
					deleteItem = (RBTreeElement<T>) deleteItem.getParent();
				} else {
					if (((RBTreeElement<T>) w.getLeft()).getColor() == RBColor.BLACK) {
						((RBTreeElement<T>) w.getRight()).setColor(RBColor.BLACK);
						//Case 3: x's sibling w is black, w's left child is red, and w's right child is black
						w.setColor(RBColor.RED);
						leftRotate(w);
						w = (RBTreeElement<T>) deleteItem.getParent().getLeft();
					}
					//Case 4: x's sibling w is black, and w's right child is red
					w.setColor(((RBTreeElement<T>) deleteItem.getParent()).getColor());
					((RBTreeElement<T>) deleteItem.getParent()).setColor(RBColor.BLACK);
					((RBTreeElement<T>) w.getLeft()).setColor(RBColor.BLACK);
					rightRotate((RBTreeElement<T>) deleteItem.getParent());
					deleteItem = (RBTreeElement<T>) root;
				}				
			}
		}
		deleteItem.setColor(RBColor.BLACK);
	}

	/*
	 * In this function we "rotate the subtree starting at "item" such that
	 * "item.right" becomes the parent of "item". "item" left subtree must be
	 * intact and the left subtree of "item.right" becomes the right subtree of
	 * "item". The "item.right" right subtree must be intact
	 */
	private void leftRotate(RBTreeElement<T> item) {
		// get right subtree
		TreeElement<T> y = item.getRight();
		if (y != null) {
			// turn y's left subtree into item's right subtree
			item.setRight(y.getLeft());
			if (y.getLeft() != null) {
				y.getLeft().setParent(item);
			}

			// link items's parent to y
			y.setParent(item.getParent());
			if (item.getParent() == null)
				root = y;
			else if (item == item.getParent().getLeft())
				item.getParent().setLeft(y);
			else
				item.getParent().setRight(y);
			y.setLeft(item);
			item.setParent(y);
		}
	}

	/*
	 * Right rotate This is symmetrical to left rotate
	 */
	private void rightRotate(RBTreeElement<T> item) {
		// get left subtree
		TreeElement<T> y = item.getLeft();
		if (y != null) {
			// turn y's right subtree
			item.setLeft(y.getRight());

			// link "item's" parent to y
			if (y.getRight() != null) {
				y.getRight().setParent(item);
			}

			y.setParent(item.getParent());
			if (item.getParent() == null)
				root = y;
			else if (item.getParent().getLeft() == item)
				item.getParent().setLeft(y);
			else
				item.getParent().setRight(y);
			y.setRight(item);
			item.setParent(y);
		}
	}

	private void print(RBTreeElement<T> treeItem, int indentation) {
		if (treeItem == null) // duh
			return;
		if (treeItem.getRight() != null)
			print ((RBTreeElement<T>) treeItem.getRight(), indentation + 8);

		//add the spaces so the output shows the tree relationships
		for (int i = 0; i < indentation; i++)
			System.out.print(" ");

		if (treeItem.getColor() == RBColor.BLACK)
			System.out.println(treeItem.getElement());
		else
			System.out.println("<" + treeItem.getElement() + ">");
		if (treeItem.getLeft() != null) {
			print((RBTreeElement<T>) treeItem.getLeft(), indentation + 8);
		}
	}

//	// does every path from the root to a leaf have the given number of black
//	// links?
//	private boolean isBalanced(myRBTreeElement<T> x, int black) {
//		if (x == null)
//			return black == 0;
//		if (x.getColor() == RBColor.RED)
//			black--;
//		return isBalanced((myRBTreeElement<T>) x.getLeft(), black)
//				&& isBalanced((myRBTreeElement<T>) x.getRight(), black);
//	}
}
