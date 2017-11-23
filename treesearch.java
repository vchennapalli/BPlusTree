import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**interface class for reading commands from and writing output into files**/
public class treesearch {
	private BPlusTree bPlusTree;
	
	/**reads the commands from the input file and calls execute function**/
	private void readCommands(File inputFile) {
		try {
			FileReader fr = new FileReader(inputFile);
			BufferedReader br = new BufferedReader(fr);
			String command;
			
			//bplus tree initialization
			int degree = Integer.parseInt((br.readLine()).trim());
			initializeBPlusTree(degree);
			
			while ((command = br.readLine()) != null) {
				executeCommand(command);
			}
			
			br.close();
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
      }
	
	/**creates a b+ tree and initializes it**/
	private void initializeBPlusTree(int degree) {
		bPlusTree = new BPlusTree(degree);
	}
	
	/**extracts the command from the string input and executes it.
	 * if anything is returned, calls function to write into output file**/
	private void executeCommand(String command) {
		String[] data = {null, null};
		Pattern p = Pattern.compile("\\((.*?)\\)");
		Matcher m = p.matcher(command);
		
		while(m.find()) {
			data = (m.group(1)).split(",");
		}
		
		//insert key and value
		if(command.contains("Insert")) {
				float key = Float.parseFloat(data[0]);
				String value = data[1];
				bPlusTree.insertPair(key, value);
		} else {
			//range search
			if(command.contains(",")) {
				float key1 = Float.parseFloat(data[0]);
				float key2 = Float.parseFloat(data[1]);
				ArrayList<Pair> pairs = bPlusTree.searchPair(key1, key2);
				String str = convertToString(pairs);
				writeOutput(str);
			} else {
				//key search
				float key = Float.parseFloat(data[0]);
				Pair pair=bPlusTree.searchPair(key);
				String str = convertToString(pair);
				writeOutput(str);
			}
		}
	}
	
	/** concatenates all multiple keys and corresponding values 
	 * into one string returns null if no keys exists in the interval **/
	private String convertToString(ArrayList<Pair> pairs) {
		String str = "";
		float key;
		ArrayList<String> values;
		if(pairs.isEmpty()) {
			return "Null";
		}
		for(int i = 0; i < pairs.size(); i++) {
			key = pairs.get(i).getKey();
			values = pairs.get(i).getValues();
			for(int j = 0; j < values.size(); j++) {
				str += concatenateHelper(key, values.get(j)); 
			}
		}
		str = str.substring(0, str.length() - 2);
		return str;
	}
	
	/** helper function for concatenation of all values of one key **/ 
	private String concatenateHelper(float key, String value) {
		return "(" + key + ", " + value + "), ";
	}
	
	/** concatenates all values corresponding to given key
	 * returns null if given key doesn't exist **/
	private String convertToString(Pair pair) {
		String str = "";
		ArrayList<String> values;
		if(pair == null) return "Null";
		values = pair.getValues();
		for(int i = 0; i < values.size(); i++)
			str += values.get(i) + ", ";
		str = str.substring(0, str.length() - 2);
		return str; 
	}
	
	/** writes the output into output_file.txt file **/
	private void writeOutput(String output) {
		
		try(FileWriter filewriter = new FileWriter("output_file.txt", true); 
				BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
				PrintWriter out = new PrintWriter(bufferedwriter))
			{
				out.println(output);
			} catch (IOException e) {
				System.out.println("Error in writing into output file.");
			}
	}
	
	/** main function.Takes the input file name as the argument. 
	 * an instance of treesearch is created and calls the 
	 * function to read the commands from input file **/ 
	public static void main(String[] args) throws IOException {
		File inputFile = new File(args[0]);
		treesearch tsearch = new treesearch();
		tsearch.readCommands(inputFile);	 
	}
}