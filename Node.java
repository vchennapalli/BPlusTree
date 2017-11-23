import java.util.ArrayList;

/** node class that has two different kind of 
 * children - index node and leaf node**/
public abstract class Node {
	protected int degree;
	protected boolean hasChild;
	
	abstract Node insertDataPair(float key, String value);
	abstract Pair search(float key);
	abstract ArrayList<Pair> search(float key1, float key2);
	abstract boolean isFull();
	abstract Node splitNode();
	//abstract void printData();
	abstract boolean insertChild(float key, Node rightChild);
	abstract float getMiddleKey();
}