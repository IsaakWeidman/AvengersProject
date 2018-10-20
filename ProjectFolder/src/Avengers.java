import java.util.*;
import java.io.*;

public class Avengers {
	public static void main(String[] args) {
		
		//Holds the script line by line
		List<String> script;
		List<String> lines;
		List<String> words;
		
		//Read in the script using the read in method
		script = readFile(new File("AvengersScript.txt"));
		
		//Print the script to the screen
		for(String s : script) {
			System.out.println(s);
		}
		
		
		lines = removeWhiteSpace(script);
		
		for(String s : lines) {
			System.out.println(s);
		}
		
		
		//TODO split the script into individual words without punctuation.
		
		words = splitList(lines, "[^a-zA-Z]");
		
		for(String s : words) {
			System.out.println(s);
		}
		
	}//end main()
	
	//===================================================================================================================
	
	//Reads in a specified file into an array list and returns the result
	public static List<String> readFile(File file) {
		
		List<String> result = new ArrayList<>();
		
		try {
			
			String line;
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			//Adds line to the result list. if line == null, then the file is completely read in.
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
			
		} catch (IOException e) {
			System.out.printf("File \'%s\' was not found.", file.getName());
		}
		
		return result;
	}//end readFile()
	
	//===================================================================================================================
	
	//Removes all empty lines in an arrayList
	public static List<String> removeWhiteSpace(List<String> list) {
		
		//Remove empty lines and any unnecessary white space. (tabs before/after text, and spaces more than one lone)
		List<String> newList = new ArrayList<>();
		
		//Adds the item from the old list to the new list so long as it is not equal to ""
		for(String s : list) {
			if(!s.trim().isEmpty())
				newList.add(s);
		}
		
		return newList;
	}//end removeWhiteSpace
	
	//===================================================================================================================
	
	public static List<String> splitList(List<String> source, String pattern) {
		
		//TODO split each line of the source by the pattern provided.
		List<String> words = new ArrayList<>();
		String[] splitWords;
		
		for(String s : source) {
			splitWords = s.split(pattern);
		}
		
		return words;
	}
	
	//===================================================================================================================
	
	public static int timesWordUsed (List<String> source, String word) {
		int times = 0;
		
		for(String s: source)
		{
			if (s.equalsIgnoreCase(word))
				times++;
		}
		return times;
	}
	
	//===================================================================================================================
	
	public static String connected (List<String> source, String word, String connector) {
		ArrayList<String> associates = new ArrayList<String>();
		ArrayList<Integer> associateNum = new ArrayList<Integer>();
		int count = 0;
		String associate = "";
		
		for (int x = 0; x < source.size(); x++)
		{
			if (source.get(x).equalsIgnoreCase(word))
			{
				if (source.get(x - 1).equalsIgnoreCase(connector))
				{
					for (int y = 0; y < associates.size(); y++)
					{
						if (source.get(x - 2).equals(associates.get(y)))
							associateNum.set(y, associateNum.get(y) + 1);
						else {
							associates.set(associates.size(), source.get(x - 2));
							associateNum.set(associates.size() - 1, 1);
						}
						if (associateNum.get(y) > count) {
							associate = associates.get(y);
							count = associateNum.get(y);
						}
					}
				}
				else if (source.get(x + 1).equalsIgnoreCase(connector))
				{
					for (int y = 0; y < associates.size(); y++)
					{
						if (source.get(x + 2).equals(associates.get(y)))
							associateNum.set(y, associateNum.get(y) + 1);
						else {
							associates.set(associates.size(), source.get(x + 2));
							associateNum.set(associates.size() - 1, 1);
						}
						if (associateNum.get(y) > count) {
							associate = associates.get(y);
							count = associateNum.get(y);
						}
					}
				}
			}
		}
		return associate;
	}
}
