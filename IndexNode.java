import java.util.ArrayList;

/**child class of Node with properties 
 * specific to an index node**/
public class IndexNode extends Node {
	private ArrayList<Float> indices;
	private ArrayList<Node> childPointers;
	
	/** constructor for the creation of a root index node **/
	public IndexNode(int degree, float key, Node leftChild, Node rightChild) {
		super();
		this.degree = degree;
		indices = new ArrayList<Float>();
		childPointers = new ArrayList<Node>();
		indices.add(0,key);
		hasChild = true;
		this.childPointers.add(leftChild);
		this.childPointers.add(rightChild);
	}
	
	/** constructor for creation of a non-root index node **/
	public IndexNode(int degree) {
		super();
		this.degree = degree;
		hasChild = true;
	}
	
	/** inserts a new index and its right child pointer 
	 * at appropriate position **/
	public boolean insertChild(float key, Node rightChild) {
		int size = indices.size();
		for(int i = 0; i < size; i++) {
			if(indices.get(i) < key) {
				continue;
			}
			else {
				indices.add(i, key);
				childPointers.add(i+1, rightChild);
				return true;
			}
		}
		
		indices.add(size, key);
		childPointers.add(size+1, rightChild);
		return true;
	}
	
	/** passes the data pair recursively into the appropriate child node **/
	public Node insertDataPair(float key, String value) {
		Node child = findChildPointer(key);
		return child;
	}
	
	/** passes the search key recursively into the 
	 * appropriate child node at each level**/
	public Pair search(float key) {
		Node child = findChildPointer(key);
		return child.search(key);
	}
	
	/** passes the lower range limit into the appropriate child node **/
	public ArrayList<Pair> search(float key1, float key2) {
		Node child = findChildPointer(key1);
		return child.search(key1, key2);
	}
	
	/** finds and returns appropriate child pointer for the given key **/
	private Node findChildPointer(float key) {
		int size = indices.size();

		if(key < indices.get(0)) return this.childPointers.get(0);
		if(size == 1) return this.childPointers.get(1);
		for(int i = 0; i < size-1; i++) {
			if((key >= indices.get(i)) && (key < indices.get(i+1))){
				return childPointers.get(i+1);
			}	
		}
		return childPointers.get(size);
	}
	
	/*public void printData() {
		int size = indices.size();
		for(int i = 0; i < size; i++) {
			System.out.print(indices.get(i) + ", ");
		}
		System.out.print("\n");
		for(int i = 0; i < size + 1; i++) {
			childPointers.get(i).printData();
		}
	}*/
	
	/** checks if the node's capacity overflowed **/
	public boolean isFull() {
		return indices.size() == degree;
	}
	
	/** returns the middle key of the node that has to be 
	 * inserted into parent index node **/
	public float getMiddleKey() {
		return this.indices.get((degree-1)/2);
	}
	
	/** splits the index node and returns the newly formed node **/
	Node splitNode() {
		int degree = this.degree;
		IndexNode newNode = new IndexNode(degree);
		newNode.indices = new ArrayList<Float> (indices.subList((degree+1)/2, degree));
		this.indices = new ArrayList<Float> (indices.subList(0, (degree-1)/2));
		newNode.childPointers = new ArrayList<Node> (childPointers.subList((degree+1)/2, degree+1));
		this.childPointers = new ArrayList<Node> (childPointers.subList(0, (degree+1)/2));
		return newNode;
	}

}
