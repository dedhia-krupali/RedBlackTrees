/* Name: Krupali Dedhia
 * Class: CS 3345
 * Section: 003
 * Professor: Anjum Chida
 */

/* 
 * This class implements a RedBlackTree with 
 * insert, contains and toString functions
 */

public class RedBlackTree<E extends Comparable<E>> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	private Node<E> nil = new Node<E>();
	private Node<E> root = nil;

	static class Node<E extends Comparable<E>> {
		public E key;
		
		// elements of a Node class
		Node<E> parent;
		Node<E> leftChild;
		Node<E> rightChild;
		private boolean color;
		private int numLeft = 0;
		private int numRight = 0;

		// default constructor
		Node() {
			color = true;
			numLeft = 0;
			numRight = 0;
			parent = null;
			leftChild = null;
			rightChild = null;
		}

		// parameterized constructor
		Node(E key) {
			this();
			this.key = key;
		}

	}
	
	// default constructor
	public RedBlackTree() {
		root.leftChild = nil;
		root.rightChild = nil;
		root.parent = nil;
	}
	
	// public function to insert in RedBlackTree
	public boolean insert(E key) {
		return insert(new Node<E>(key));
	}

	// @param: z, the node to be inserted into the Tree rooted at root
	// Inserts z into the appropriate position in the RedBlackTree while
	// updating numLeft and numRight values.
	private boolean insert(Node<E> z) {

		// Create a reference to root & initialize a node to nil
		Node<E> y = nil;
		Node<E> x = root;

		// While we haven't reached a the end of the tree keep
		// trying to figure out where z should go
		while (!isNil(x)) {
			y = x;

			// if z.key is < than the current key, go left
			if (z.key.compareTo(x.key) < 0) {

				// Update x.numLeft as z is < than x
				x.numLeft++;
				x = x.leftChild;
			}

			// else z.key >= x.key so go right.
			else if (z.key.compareTo(x.key) > 0) {

				// Update x.numGreater as z is => x
				x.numRight++;
				x = x.rightChild;
			} else {
				return false;
			}
		}
		// y will hold z's parent
		z.parent = y;

		// Depending on the value of y.key, put z as the left or
		// right child of y
		if (isNil(y)) {
			root = z;
			// return true;
		} else if (z.key.compareTo(y.key) < 0) {
			y.leftChild = z;
			// return true;
		} else {
			y.rightChild = z;
			// return true;
		}

		// Initialize z's children to nil and z's color to red
		z.leftChild = nil;
		z.rightChild = nil;
		z.color = RED;

		// Call insertFixup(z)
		insertFixup(z);

		return true;
	}

	private void insertFixup(Node<E> z) {
		
		Node<E> y = nil;
		
		// While there is a violation of the RedBlackTree properties..
		while (z.parent.color == RED) {

			// If z's parent is the the left child of it's parent.
			if (z.parent == z.parent.parent.leftChild) {

				// Initialize y to z 's cousin
				y = z.parent.parent.rightChild;

				// Case 1: if y is red...recolor
				if (y.color == RED) {
					z.parent.color = BLACK;
					y.color = BLACK;
					z.parent.parent.color = RED;
					z = z.parent.parent;
				}
				// Case 2: if y is black & z is a right child
				else if (z == z.parent.rightChild) {

					// leftRotaet around z's parent
					z = z.parent;
					leftRotate(z);
				}

				// Case 3: else y is black & z is a left child
				else {
					// recolor and rotate round z's grandpa
					z.parent.color = BLACK;
					z.parent.parent.color = RED;
					rightRotate(z.parent.parent);
				}
			}

			// If z's parent is the right child of it's parent.
			else {

				// Initialize y to z's cousin
				y = z.parent.parent.leftChild;

				// Case 1: if y is red...recolor
				if (y.color == RED) {
					z.parent.color = BLACK;
					y.color = BLACK;
					z.parent.parent.color = RED;
					z = z.parent.parent;
				}

				// Case 2: if y is black and z is a left child
				else if (z == z.parent.leftChild) {
					// rightRotate around z's parent
					z = z.parent;
					rightRotate(z);
				}
				// Case 3: if y is black and z is a right child
				else {
					// recolor and rotate around z's grandpa
					z.parent.color = BLACK;
					z.parent.parent.color = RED;
					leftRotate(z.parent.parent);
				}
			}
		}
		// Color root black at all times
		root.color = BLACK;

	}// end insertFixup(RedBlackNode z)

	// check if node is empty
	private boolean isNil(Node<E> node) {
		// return appropriate value
		return node == nil;

	}

	public int size() {
		// Return the number of nodes to the root's left + the number of
		// nodes on the root's right + the root itself.
		return root.numLeft + root.numRight + 1;
	}// end size()

	private void leftRotate(Node<E> x) {

		// Call leftRotateFixup() which updates the numLeft
		// and numRight values.
		leftRotateFixup(x);

		// Perform the left rotate as described in the algorithm
		// in the course text.
		Node<E> y;
		y = x.rightChild;
		x.rightChild = y.leftChild;

		// Check for existence of y.left and make pointer changes
		if (!isNil(y.leftChild))
			y.leftChild.parent = x;
		y.parent = x.parent;

		// x's parent is nul
		if (isNil(x.parent))
			root = y;

		// x is the left child of it's parent
		else if (x.parent.leftChild == x)
			x.parent.leftChild = y;

		// x is the right child of it's parent.
		else
			x.parent.rightChild = y;

		// Finish of the leftRotate
		y.leftChild = x;
		x.parent = y;
	}// end leftRotate(RedBlackNode x)

	private void leftRotateFixup(Node<E> x) {

		// Case 1: Only x, x.right and x.right.right always are not nil.
		if (isNil(x.leftChild) && isNil(x.rightChild.leftChild)) {
			x.numLeft = 0;
			x.numRight = 0;
			x.rightChild.numLeft = 1;
		}

		// Case 2: x.right.left also exists in addition to Case 1
		else if (isNil(x.leftChild) && !isNil(x.rightChild.leftChild)) {
			x.numLeft = 0;
			x.numRight = 1 + x.rightChild.leftChild.numLeft + x.rightChild.leftChild.numRight;
			x.rightChild.numLeft = 2 + x.rightChild.leftChild.numLeft + x.rightChild.leftChild.numRight;
		}

		// Case 3: x.left also exists in addition to Case 1
		else if (!isNil(x.leftChild) && isNil(x.rightChild.leftChild)) {
			x.numRight = 0;
			x.rightChild.numLeft = 2 + x.leftChild.numLeft + x.leftChild.numRight;

		}
		// Case 4: x.left and x.right.left both exist in addtion to Case 1
		else {
			x.numRight = 1 + x.rightChild.leftChild.numLeft + x.rightChild.leftChild.numRight;
			x.rightChild.numLeft = 3 + x.leftChild.numLeft + x.leftChild.numRight + x.rightChild.leftChild.numLeft
					+ x.rightChild.leftChild.numRight;
		}

	}// end leftRotateFixup(RedBlackNode x)

	// @param: x, The node which the rightRotate is to be performed on.
	// Updates the numLeft and numRight values affected by the Rotate.
	private void rightRotate(Node<E> y) {

		// Call rightRotateFixup to adjust numRight and numLeft values
		rightRotateFixup(y);

		// Perform the rotate as described in the course text.
		Node<E> x = y.leftChild;
		y.leftChild = x.rightChild;

		// Check for existence of x.right
		if (!isNil(x.rightChild))
			x.rightChild.parent = y;
		x.parent = y.parent;

		// y.parent is nil
		if (isNil(y.parent))
			root = x;

		// y is a right child of it's parent.
		else if (y.parent.rightChild == y)
			y.parent.rightChild = x;

		// y is a left child of it's parent.
		else
			y.parent.leftChild = x;
		x.rightChild = y;

		y.parent = x;

	}// end rightRotate(RedBlackNode y)

	// @param: y, the node around which the righRotate is to be performed.
	// Updates the numLeft and numRight values affected by the rotate
	private void rightRotateFixup(Node<E> y) {

		// Case 1: Only y, y.left and y.left.left exists.
		if (isNil(y.rightChild) && isNil(y.leftChild.rightChild)) {
			y.numRight = 0;
			y.numLeft = 0;
			y.leftChild.numRight = 1;
		}

		// Case 2: y.left.right also exists in addition to Case 1
		else if (isNil(y.rightChild) && !isNil(y.leftChild.rightChild)) {
			y.numRight = 0;
			y.numLeft = 1 + y.leftChild.rightChild.numRight + y.leftChild.rightChild.numLeft;
			y.leftChild.numRight = 2 + y.leftChild.rightChild.numRight + y.leftChild.rightChild.numLeft;
		}

		// Case 3: y.right also exists in addition to Case 1
		else if (!isNil(y.rightChild) && isNil(y.leftChild.rightChild)) {
			y.numLeft = 0;
			y.leftChild.numRight = 2 + y.rightChild.numRight + y.rightChild.numLeft;

		}

		// Case 4: y.right & y.left.right exist in addition to Case 1
		else {
			y.numLeft = 1 + y.leftChild.rightChild.numRight + y.leftChild.rightChild.numLeft;
			y.leftChild.numRight = 3 + y.rightChild.numRight + y.rightChild.numLeft + y.leftChild.rightChild.numRight
					+ y.leftChild.rightChild.numLeft;
		}

	}// end rightRotateFixup(RedBlackNode y)]

	public boolean contains(E key) {

		// Initialize a pointer to the root to traverse the tree
		Node<E> current = root;

		// While we haven't reached the end of the tree
		while (!isNil(current)) {

			// If we have found a node with a key equal to key
			if (current.key.equals(key))

				// return that node and exit search(int)
				return true;

			// go left or right based on value of current and key
			else if (current.key.compareTo(key) < 0)
				current = current.rightChild;

			// go left or right based on value of current and key
			else
				current = current.leftChild;
		}

		// we have not found a node whose key is "key"
		return false;

	}

	// public toString function
	public String toString() {
		return toString(root);
	}

	// function to print nodes of RedBlackTree in PreOrderTraversal
	private String toString(Node<E> node) {
		if (node == nil) {
			return "";
		}
		String str1 = "";
		String str2 = "";
		String str = "";
		if (node.color == BLACK) {
			str = str + node.key + " ";
		} else {
			str = str + "*" + node.key + " ";
		}
		str1 = toString(node.leftChild);
		str2 = toString(node.rightChild);
		return str + str1 + str2;
	}

}