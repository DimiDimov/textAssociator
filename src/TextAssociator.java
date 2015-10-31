/** 
 * @author Dimi Dimov 1322926
 * @author Darren Hou 1330362
 * CSE 373 A, Fall 2015
 * Assignment #3
 * 10/30/15
 * 
 * TextAssociator represents a collection of associations between words.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TextAssociator {
	private WordInfoSeparateChain[] table;
	private int size;
	
	public static final int INITIAL_SIZE = 23;
	public static final int RESIZE_FACTOR = 2;
	public static final int LOAD_THRESHOLD = 2;
	
	/**
	 * INNER CLASS
	 * Represents a separate chain in your implementation of your hashing
	 * A WordInfoSeparateChain is a list of WordInfo objects that have all
	 * been hashed to the same index of the TextAssociator
	 */
	private class WordInfoSeparateChain {
		private List<WordInfo> chain;
		
		/**
		 * Creates an empty WordInfoSeparateChain without any WordInfo
		 */
		public WordInfoSeparateChain() {
			this.chain = new ArrayList<WordInfo>();
		}
		
		/**
		 * Adds a WordInfo object to the SeparateCahin
		 * Returns true if the WordInfo was successfully added, false otherwise
		 */
		public boolean add(WordInfo wi) {
			if (!chain.contains(wi)) {
				chain.add(wi);
				return true;
			}
			return false;
		}
		
		/**
		 * Removes the given WordInfo object from the separate chain
		 * Returns true if the WordInfo was successfully removed, false otherwise
		 */
		public boolean remove(WordInfo wi) {
			if (chain.contains(wi)) {
				chain.remove(wi);
				return true;
			}
			return false;
		}
		
		/**
		 * Returns the size of this separate chain
		 */ 
		public int size() {
			return chain.size();
		}
		
		/**
		 * Returns the String representation of this separate chain
		 */ 
		public String toString() {
			return chain.toString();
		}
		
		// Returns the list of WordInfo objects in this chain
		public List<WordInfo> getElements() {
			return chain;
		}
	}
	
	
	/**
	 * Creates a new TextAssociator without any associations, with a starting size of 23
	 */
	public TextAssociator() {
		size = INITIAL_SIZE;
		table = new WordInfoSeparateChain[size];
	}
	
	/**
	 * Adds a String word with no associations to the TextAssociator.
	 * 
	 * @param word	String to be added
	 * @return		True if word is added successfully, False otherwise
	 */
	public boolean addNewWord(String word) {
		
		boolean addedSuccessfully = false;
		int index = hashString(word);
		WordInfo newWord = new WordInfo(word);
		
		// makes WordInfoSeparateChain at index if it doesn't already exist
		if (table[index] == null) { 
			table[index] = new WordInfoSeparateChain();
		}
		
		//checks if word isn't already in a WordInfo, adds 
		if (getWordInfo(index, word) == null) { 
			table[index].add(newWord);
			addedSuccessfully = true;
		}
		
		//if the WordInfoSeperateChain has more WordInfo's than the load threshold, resize 
		// hashtable
		if (table[index].size() > LOAD_THRESHOLD) { 
			table = expandTable(table);
		}
			
		return addedSuccessfully;
	}
	
	
	/**
	 * Expands the passed table size by a the resize factor and copies over all elements, 
	 * rehashing them appropriately in the process
	 * 
	 * @param table		A WordInfoSeparateChain[] to be resized
	 * @return 			A temporary table containing all the WordInfo elements of the passed
	 * 					table
	 */
	private WordInfoSeparateChain[] expandTable(WordInfoSeparateChain[] table) {
		size *= RESIZE_FACTOR;
		WordInfoSeparateChain[] tempTable = new WordInfoSeparateChain[size];
		
		// Walk through every possible index in the table
		for (WordInfoSeparateChain chain : table) { 
			if (chain != null) {
				// For each separate chain, grab each individual WordInfo
				for (WordInfo wordInfo : chain.getElements()) {  
					String word = wordInfo.getWord();
					int newIndex = hashString(word);
					
					//checks if WordInfoSeparateChain is already created at table index, 
					// if not creates one
					if (tempTable[newIndex] == null) {        
						tempTable[newIndex] = new WordInfoSeparateChain();
					}
					
					tempTable[newIndex].add(wordInfo);
					
					//check if table needs to be resized because WordInfoChain
					//has more wordInfo's than load threshold
					if (tempTable[newIndex].size() > LOAD_THRESHOLD) { 
						tempTable = expandTable(tempTable);            
					}					
				}
			}
		}	
		return tempTable;
	}
	
	/**
	 * Hashes a String word
	 * 
	 * @param word	The String to be hashed
	 * @return		The hash as an int
	 */
	private int hashString(String word) {
		return Math.abs(word.hashCode() % size);
	}
	
	/**
	 * Adds an association between the given words. Returns true if association correctly added, 
	 * returns false if first parameter does not already exist in the SpellChecker or if 
	 * the association between the two words already exists
	 * 
	 * @param word			The word to be given an association
	 * @param association	Association to be added to word
	 * @return				True if association added, false otherwise
	 */
	public boolean addAssociation(String word, String association) {
		int index = hashString(word);
		WordInfo wi = getWordInfo(index, word);
		if (wi != null) {
			//if association isn't already in WordInfo, add it
			if (!wi.getAssociations().contains(association)) {  
				return wi.addAssociation(association);
			}
		}
		return false;
	}
	
	
	/**
	 * Remove the given word from the TextAssociator.
	 * Note that only a source word can be removed by this method, not an association.
	 * 
	 * @param 	word	String to be removed from the TextAssociator
	 * @return			True if word was removed successfully, false otherwise
	 */
	public boolean remove(String word) {
		int index = hashString(word);
		
		//uses helper method to get the wordInfo using index and string
		WordInfo wi = getWordInfo(index, word); 
		if (wi != null) {	//checks if wordInfo is null, if not, removes it
			return table[index].remove(wi);
		}
		return false;
	}
	
	/**
	 * Returns a set of associations of a given word
	 * 
	 * @param 	word	String to get associations of
	 * @return			A Set<String> all the words associated with the given String, or null 
	 * 					if the given String does not exist in the TextAssociator
	 */
	public Set<String> getAssociations(String word) {
		int index = hashString(word);
		
		//uses helper method to get the wordInfo using index and string
		WordInfo wi = getWordInfo(index, word); 
		if (wi != null) {	//checks if wordInfo is null, if not, returns its associations.
			return wi.getAssociations();
		}
		return null;
	}
	
	/**
	 * Returns the WordInfo object from the table's given index that represents a given word
	 * 
	 * @param index		The location of the word's chain in the table, if it exists
	 * @param word		String to get the WordInfo of
	 * @return			WordInfo object of the passed word, or null if the word is not in the 
	 * 					chain
	 */
	private WordInfo getWordInfo(int index, String word) {
		WordInfoSeparateChain chain = table[index];
		if (chain != null) {
			//goes through each WordInfo in chain at table index given
			for (WordInfo w : chain.getElements()) { 
				//finds word in WordInfo, then returns that WordInfo
				if (w.getWord().equals(word)) { 
					return w;
				}
			}
		}

		return null;
	}
	
	/**
	 * Prints the current associations between words being stored
	 * to System.out in a way that is easy on the eyes
	 */
	public void prettyPrint() {
		System.out.println("Current number of elements : " + size);
		System.out.println("Current table size: " + table.length);
		
		// Walk through every possible index in the table
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				WordInfoSeparateChain bucket = table[i];
				
				// For each separate chain, grab each individual WordInfo
				for (WordInfo curr : bucket.getElements()) {
					System.out.println("\tin table index, " + i + ": " + curr);
				}
			}
		}
		System.out.println();
	}
}