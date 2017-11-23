import java.util.ArrayList;

/** Stack class to keep track of the path 
 * followed from top to bottom  of the tree **/
public class Stack {
	private ArrayList<Node> stack;
	
	/** constructor of the class.
	 * initializes the arraylist **/
	public Stack() {
		stack = new ArrayList<Node>();
	}
	
	/** checks if the stack is empty **/
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	/** pops the top element of the stack **/
	public Node pop() {
		Node topElement;
		topElement = stack.remove((stack.size()-1));
		return topElement;
	}
	
	/** resets the stack when there is no need 
	 * to follow to the top **/
	public void reset() {
		stack = new ArrayList<Node>();
	}
	
	/** returns size of the stack **/
	public int getSize() {
		return stack.size();
	}
	
	/** pushes a new Node onto the stack **/
	public void push(Node newElement) {
		stack.add(newElement);
	}
}