import java.util.ArrayList;

public class LeafNode extends Node{
	private ArrayList<Pair> pairs = new ArrayList<Pair>();
	private LeafNode next = null;
	private LeafNode previous = null;
	
	
	/** constructer to initialize the leaf node **/
	public LeafNode(int degree) {
		setDegree(degree);
		hasChild = false;
	}
	
	/** sets the degree of the node **/
	private void setDegree(int degree) {
		this.degree = degree;
	}
	
	/** checks if the node's capacity overflowed **/
	public boolean isFull() {
		return pairs.size() == degree;
	}
	
	/** inserts the pair into the leaf node 
	 * at appropriate position **/
	public Node insertDataPair(float key, String value) {
		int size = pairs.size();
		for(int i = 0; i < size; i++) {
			if(key == pairs.get(i).getKey()) {
				pairs.get(i).appendValue(value);
				return this;
			}
			else if (key < pairs.get(i).getKey()){
				Pair newEntry = new Pair(key, value);
				pairs.add(i, newEntry);
				return this;
			}
		}
		Pair newEntry = new Pair(key, value);
		pairs.add(size, newEntry);
		return this;
	}
	
	/** splits the leaf node and returns the newly formed node **/
	public Node splitNode() {
		int degree = this.degree;
		LeafNode newNode = new LeafNode(degree);
		newNode.pairs = new ArrayList<Pair> (pairs.subList((degree)/2, degree));
		this.pairs = new ArrayList<Pair> (pairs.subList(0, (degree)/2));
		newNode.previous = this;
		newNode.next = this.next;
		this.next = newNode;
		return newNode;
	}
	
	/** returns the middle key of the node that has to be 
	 * copied into parent index node **/
	public float getMiddleKey() {
		return pairs.get((degree)/2).getKey();
	}
	
	/** searches the node for the requested key.
	 * returns the pair if found and null if not found **/
	public Pair search(float key) {
		int size = pairs.size();
		for(int i = 0; i < size; i++) {
			if(key == pairs.get(i).getKey()) {
				return pairs.get(i);
			}
		}
		return null;
	}
	
	/** searches for the pairs in the node and subsequent 
	 * nodes if needed. returns the arraylist of all 
	 * the found pairs. returns null if none exist **/
	public ArrayList<Pair> search(float key1, float key2) {
		int size = pairs.size();
		ArrayList<Pair> allPairs = new ArrayList<Pair>();
		for(int i = 0; i < size; i++) {
			if(pairs.get(i).getKey() < key1) {
				continue;
			}
			else if((key1 <= pairs.get(i).getKey()) && (pairs.get(i).getKey() <= key2)) {
				allPairs.add(pairs.get(i));
			}
		}
		
		LeafNode nextNode = this.next;
		if((Float.compare(key2, pairs.get(size-1).getKey()) > 0) && nextNode != null) {
			this.searchNextNode(nextNode, key2, allPairs);
		}
		return allPairs;
	}
	
	/** returns all pairs whose keys are less than key2 in the nodes following this node **/
	public ArrayList<Pair> searchNextNode(LeafNode nextNode, float key2, ArrayList<Pair> previousPairs) {
		int size = nextNode.pairs.size();
		//LeafNode followingNode;
		while(nextNode != null) {
			size = nextNode.pairs.size();
			for(int i = 0; i < size; i++) {
				if(nextNode.pairs.get(i).getKey() <= key2) {
					previousPairs.add(nextNode.pairs.get(i));
				}
				else {
					return previousPairs;
				}
			}
			if((Float.compare(key2, nextNode.pairs.get(size-1).getKey()) > 0) && (nextNode.next != null)) {
					nextNode = nextNode.next;
					continue;
			}
			break;
		}
		return previousPairs;
	}
	
	boolean insertChild(float key, Node rightChild) {
		return false;
	}
}
