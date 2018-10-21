/*
Brennan Mulligan and Isaak Weidman
Avengers Project
Goes through the script and finds
multiple objectives while remaining
modular and making use of abstraction
*/

import java.io.*;
import java.util.*;

public class Avengers {
	public static void main(String[] args) {

		//Holds the script line by line
		List<String> script;
		List<String> lines;
		List<String> words;
		int count = 0;
		int answered = 0;

		Scanner reader = new Scanner(System.in);

		//Read in the script using the read in method
		script = readFile(new File("AvengersScript.txt"));

		//Print the script to the screen
		for(String s : script) {
			System.out.println(s);
		}

		System.out.println("===========================================================================================");

		lines = removeWhiteSpace(script);

		for(String s : lines) {
			System.out.println(s);
		}

		System.out.println("===========================================================================================");

		//TODO split the script into individual words without punctuation.

		words = splitList(lines, "[^a-zA-Z]");

		for(String s : words) {
			System.out.println(s);
		}

		System.out.println("===========================================================================================");

		System.out.println("The word 'Tesseract' is used " + timesWordUsed(words, "Tesseract") + " times.");
		System.out.println("The word 'Loki' is used " + timesWordUsed(words, "Loki") + " times.");
		System.out.println("The word 'Thor' is used " + timesWordUsed(words, "Thor") + " times.");

		System.out.println("===========================================================================================");

		System.out.println("The name 'Hulk' is used + " + timesWordUsed(words, "Hulk") + " times while Banner is used " + timesWordUsed(words, "Banner") + " times");
		System.out.println("The name 'Iron Man' is used + " + timesWordUsed(words, "Iron") + " times while Tony is used " + timesWordUsed(words, "Tony") + " times");
		System.out.println("The name 'Captain America' is used + " + timesWordUsed(words, "Captain") + " times while Steve is used " + timesWordUsed(words, "Steve") + " times");
		System.out.println("The name 'Black Widow' is used + " + timesWordUsed(words, "widow") + " times while Natasha is used " + timesWordUsed(words, "Natasha") + " times");
		System.out.println("The name 'Hawkeye' is used + " + timesWordUsed(words, "Hawkeye") + " times while Barton is used " + timesWordUsed(words, "Barton") + " times");

		System.out.println("===========================================================================================");

		System.out.println("Tony is most commonly associated with " + connected(words, "Tony", "and"));

		System.out.println("===========================================================================================");

		while (answered == 0)
		{
			System.out.println("This is part of a sentence with Tesseract in it:");
			System.out.println(surrounding(words, "Tesseract", count));
			System.out.println("Does this tell you what the Tesseract is? 0 for no, 1 for yes");

			answered = reader.nextInt();

			if (answered == 0)
				count++;
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

	//Calculates how many times a word is used
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

	//Finds word most commonly connected to a word
	//For example, somoeone could enter Tony for the word
	//and "and" for the connector so it will find words such
	//as "Nick" if the sentence says "Tony and Nick"
	public static String connected (List<String> source, String word, String connector) {
		ArrayList<String> associates = new ArrayList<String>();
		ArrayList<Integer> associateNum = new ArrayList<Integer>();
		int count = 0;
		String associate = "";

		for (int x = 0; x < source.size(); x++)
		{
			if (source.get(x).equalsIgnoreCase(word))
			{
				//This if/else statement checks if the connector is before or after the word
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

	//===================================================================================================================

	//Finds the words surrounding a word
	//Count variable is used to detect how many times the user said 'no'
	//That way, the program can skip when it finds the word again
	//until it gets to a part that has not been used yet
	public static String surrounding (List<String> source, String word, int count) {
		String surrounded = "";
		int methodCount = 0;

		for (int x = 0; x < source.size(); x++)
		{
			if (methodCount == 0)
			{
				if (source.get(x).equalsIgnoreCase(word))
				{
					surrounded = (source.get(x - 4) + source.get(x - 3) + source.get(x - 2) + source.get(x - 1) + source.get(x) + source.get(x + 1) + source.get(x + 2) + source.get(x + 3) + source.get(x + 4));
				}
			}
			else
				methodCount++;
		}
		return surrounded;
	}
}//end Main.Avengers.java
