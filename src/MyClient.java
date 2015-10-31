/** 
 * @author Dimi Dimov 1322926
 * @author Darren Hou 1330362
 * CSE 373 A, Fall 2015
 * Assignment #3
 * 10/30/15
 * 
 * A client class for the TextAssociator. Generates lines of nonsense by importing the 
 * thesaurus and finding synonyms for the intended output text. Has a chance to double 
 * the output size multiple times.
 */

import java.io.*;
import java.util.*;

public class MyClient {
	public static void main(String[] args) throws IOException {
		TextAssociator textAssociator = new TextAssociator();
		
		BufferedReader reader = new BufferedReader(new FileReader("large_thesaurus.txt"));
		String text = null;
		String output = "nonsense";
		
		// Put all words from thesaurus into textAssociator
		while ((text = reader.readLine()) != null) {
			String[] words = text.split(",");
			String currWord = words[0].trim();
			textAssociator.addNewWord(currWord);
			
			for (int i = 1; i < words.length; i++) {
				textAssociator.addAssociation(currWord, words[i].trim());
			}
			
		}
		
		double expandChance = 90;
		Random rand = new Random();
		Scanner input = new Scanner(System.in);
		System.out.print("How many lines? ");
		int response = input.nextInt();
		
		for (int i = 0; i < response; i++) {
			while (rand.nextInt(100) < expandChance) {
				output = output + " " + output;
				
				Scanner scanner = new Scanner(output);
				
				String result = "";
				String[] tokens  = output.split(" ");
				for (String token : tokens) {
					Set<String> words = textAssociator.getAssociations(token.toLowerCase());
					if (words == null) {
						result += " " + token;
					} else {
						result += " " + words.toArray()[rand.nextInt(words.size())];
					}
				}
				
				output = result.trim();
				scanner.close();
				
				expandChance *= 0.8;
			}
			System.out.println(output);
			output = "nonsense";
			expandChance = 90;
		}
		
		input.close();
		reader.close();
		
		System.out.println("\nWell on your way to your English degree.");
	}
	
}
