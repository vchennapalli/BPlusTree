import java.util.*;

/** Fundamental data structure used in the program for leaf nodes
 * key - float
 * values - arraylist of strings**/
public class Pair {
	private float key;
	private ArrayList<String> values = new ArrayList<String>();
	
	/** constructor of the class **/
	public Pair(float key, String value) {
		setKey(key);
		appendValue(value);		
	}
	
	/** returns key of the pair **/
	public float getKey() {
		return key;
	}
	
	/** sets the key of the pair **/
	public void setKey(float key) {
		this.key = key;
	}
	
	/* returns size of the value arraylist
	public int getValuesSize() {
		return values.size();
	}*/
	
	/** helper function for the user **/
	public void printValues() {
		for(int i = 0; i < values.size(); i++) {
			System.out.print(values.get(i) + ", ");
		}
	}
	
	/** returns values of the pair **/
	public ArrayList<String> getValues() {
		return values;
	}
	
	/** appends the new value corresponding to same key **/
	public void appendValue(String value) {
		values.add(value);
	}
}
