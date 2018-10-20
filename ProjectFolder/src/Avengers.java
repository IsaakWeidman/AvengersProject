/*
This project takes in a file containing the entire script of the Avengers movie. Our task is to use that file as data to
analyze. This project is for practicing abstraction when coding.

Coded by Isaak Weidman, and Brennan Mulligan, collectively.
 */

import java.util.*;
import java.io.*;

public class Avengers {
	public static void main(String[] args) {
		
		//Basic Objects and variables for the main
		Scanner reader = new Scanner(System.in);
		int userInput;
		
		//Holds the script line by line
		List<String> script;
		List<String> lines;
		List<String> words;
		
		//Part A code:
		
		script = readFile(new File("AvengersScript.txt"));
		lines = removeWhiteSpace(script);
		words = splitList(lines, "[^a-zA-Z]");
		
		System.out.print("Do you want to print the script and its processes to the screen?\n[1] yes, [2] no\n>");
		userInput = reader.nextInt();
		
		
		//While Loop takes the input from the user and loops it to make sure they enter a valid value.
		while(userInput != 2) {
			
			if (userInput == 1) {
				while (userInput != 0) {
					
					System.out.print("What list to print\n[1] Script\n[2] Lines\n[3] Words\nOr enter \'0\' to cancel\n>");
					userInput = reader.nextInt();
					
					switch (userInput) {
						case 1:
							printList(script);
							break;
						case 2:
							printList(lines);
							break;
						case 3:
							printList(words);
							break;
					}
				}
			} else if (userInput != 2 && userInput != 1) {
				System.out.println("The value you entered was incorrect");
				System.out.println("Please enter again.\n[1] yes, [2] no\n");
				userInput = reader.nextInt();
			}
			if(userInput == 0)
				break;
		}
		
		//TODO build methods that analyze the information for Part B
		//Part B code:
		
		String word1, word2;
		
		System.out.print("\nCheckFrequency():\nPlease enter a word to check\n>");
		word1 = reader.nextLine();//flush io stream
		word1 = reader.nextLine();
		System.out.print("Please enter a second word\n>");
		word2 = reader.nextLine();
		
		//Compare the frequency of word1, to word 2, in List words
		System.out.printf("\n%s shows up more frequently.", checkFrequency(word1, word2, words));
		
		//Gather 4 words before and after a given word
		System.out.print("\ngetContext():\nPlease enter a word to get context about\n>");
		String input = reader.nextLine();
		String[] context;
		context = getContext(input, words);
		System.out.println(context);
		
		
	}//end main()
	
	//===================================================================================================================
	
	//Reads in a specified file into an array list and returns the result
	//Coded by Isaak Weidman
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
	//Coded by Isaak Weidman
	public static List<String> removeWhiteSpace(List<String> list) {
		
		//Remove empty lines and any unnecessary white space. (tabs before/after text, and spaces more than one lone)
		List<String> newList = new ArrayList<>();
		
		//Adds the item from the old list to the new list so long as it is not equal to ""
		for(String s : list) {
			if(!s.isEmpty()) {
				s.replaceAll("[^a-zA-Z ]", "");
				s.replaceAll("\\p{P}", "");
				newList.add(s.trim());
			}
		}
		
		return newList;
	}//end removeWhiteSpace
	
	//===================================================================================================================
	
	//Splits each element of a list of Strings by a pattern specified.
	//Coded by Isaak Weidman
	public static List<String> splitList(List<String> source, String pattern) {
		
		List<String> words = new ArrayList<>();
		String[] splitWords;

		for(String s : source) {
			splitWords = s.split(pattern);
			
			for(String itm : splitWords) {
				words.add(itm);
			}
		}
		
		//Check for and remove empty lines
		for(int i = 0; i < words.size(); i++) {
			if(words.get(i).isEmpty())
				words.remove(i);
		}
		
		
		return words;
	}//end splitList()
	
	//===================================================================================================================
	
	public static int timesWordUsed (List<String> source, String word) {
		int times = 0;
		
		for(String s: source)
		{
			if (s.equalsIgnoreCase(word))
				times++;
		}
		return times;
	}//end timesWordUsed()
	
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
	}//end connected()
	
	//===================================================================================================================
	
	//Checks the frequency of two Strings in a list of Strings and returns the String that shows up the most. If both
	//Strings show up the same amount in the List, return null.
	public static String checkFrequency(String string1, String string2, List<String> list) {
		
		int s1Counter = 0, s2Counter = 0;
		
		//count frequency of occurrences for each String
		for(String s: list) {
			if(s.equals(string1))
				s1Counter++;
			else if(s.equals(string2))
				s2Counter++;
		}
		
		//Compare frequency of Occurrences
		if(s1Counter > s2Counter)
			return string1;
		else if(s1Counter < s2Counter)
			return string2;
		else
			return null;
	}//end checkFrequency()
	
	//===================================================================================================================
	
	//Gathers 4 words before and after the given word
	//Coded by Isaak Weidman
	public static String[] getContext(String str, List<String> source) {
		
		//List of Strings where the first 4 elements are the 4 before given String and the last 4 are after the given.
		String[] context = new String[8];
		int counter = 0;
		
		//Gather 4 words before
		for(int i = source.indexOf(str); i >= (i - 4); i--) {
			context[counter] = source.get(i);
			counter++;
		}
		
		//Gather 4 words after
		for(int i = source.indexOf(str); i <= (i + 4); i++) {
			context[counter] = source.get(i);
			counter++;
		}
		
		return context;
	}//end getContext()
	
	//===================================================================================================================
	
	//Simply prints a list of Strings line by line: keeps the main method small.
	//Coded by Isaak Weidman
	public static void printList(List<String> list) {
		
		for(String s : list) {
			System.out.println(s);
		}
	}
}
