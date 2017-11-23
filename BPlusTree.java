import java.util.ArrayList;

/** b+ tree class. executes the given commands by 
 * inserting/searching and returns appropriate output (if any).**/
public class BPlusTree {
	private static Node root;
	final Stack indexNodesPath = new Stack();
	private int degree;
	
	/** constructor of b+ tree. initializes root to leaf node **/
	public BPlusTree(int degree) {
		root = new LeafNode(degree);
		this.degree = degree;
	}
	
	/** top level function that takes care of insertion and 
	 * adjustment of tree (if needed) **/
	public void insertPair(float key, String value) {
		Node leaf;
		indexNodesPath.reset();
		leaf = topDownInsert(key, value);
		bottomUpAdjust(leaf);
	}
	
	/** inserts the <key, value> from the top of the tree. keeps 
	 * track of the path by copying the traversed path into a stack.
	 * returns the leaf node into which insertion was made **/
	private Node topDownInsert(float key, String value) {
		Node node = root;
		while(node.hasChild == true) {
			indexNodesPath.push(node);
			node = node.insertDataPair(key,  value);
		}
		node = node.insertDataPair(key, value);
		return node;
	}
	
	/** adjusts the tree from the bottom to up to maintain 
	 * capacity conditions of nodes (if needed). makes use 
	 * of the earlier stacked data to traverse to the top **/
	private void bottomUpAdjust(Node node) {
		Node newNode;
		float newKey;
		boolean isFull = node.isFull();
		while(isFull) {
			newKey = node.getMiddleKey();
			newNode = node.splitNode();
			if(!(indexNodesPath.isEmpty())) {
				node = indexNodesPath.pop();
				node.insertChild(newKey, newNode);	
			}
			else {
				int degree = this.degree;
				root = new IndexNode(degree, newKey, node, newNode);
				node = root;
			}
			isFull = node.isFull();
		}
	}
	
	/** top level function that takes care of searching the value(s) 
	 * corresponding to given key. returns the <key, [value(s)]> 
	 * pair if found or null otherwise **/
	public Pair searchPair(float key) {
		Node node = root;
		Pair pair = node.search(key);
		return pair;
	}
	
	/**top level function that takes care of searching
	 * the keys and corresponding values in the requested range. 
	 * returns a arraylist of <key, [value(s)]> pairs if found 
	 * or null otherwise **/
	public ArrayList<Pair> searchPair(float key1, float key2) {
		Node node = root;
		ArrayList<Pair> pairs = node.search(key1, key2);
		return pairs;
	}
}
