import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* CSE 373 Starter Code
 * @Author Kevin Quinn
 * 
 * TextAssociator represents a collection of associations between words.
 * See write-up for implementation details and hints
 * 
 */
public class TextAssociator {
	private WordInfoSeparateChain[] table;
	private int size;
	
	/** 
	 * INNER CLASS
	 * Represents a separate chain in your implementation of your hashing
	 * A WordInfoSeparateChain is a list of WordInfo objects that have all
	 * been hashed to the same index of the TextAssociator
	 */
	private class WordInfoSeparateChain {
		private List<WordInfo> chain;
		
		/* Creates an empty WordInfoSeparateChain without any WordInfo
		 */
		public WordInfoSeparateChain() {
			this.chain = new ArrayList<WordInfo>();
		}
		
		/* Adds a WordInfo object to the SeparateCahin
		 * Returns true if the WordInfo was successfully added, false otherwise
		 */
		public boolean add(WordInfo wi) {
			if (!chain.contains(wi)) {
				chain.add(wi);
				return true;
			}
			return false;
		}
		
		/* Removes the given WordInfo object from the separate chain
		 * Returns true if the WordInfo was successfully removed, false otherwise
		 */
		public boolean remove(WordInfo wi) {
			if (chain.contains(wi)) {
				chain.remove(wi);
				return true;
			}
			return false;
		}
		
		// Returns the size of this separate chain
		public int size() {
			return chain.size();
		}
		
		// Returns the String representation of this separate chain
		public String toString() {
			return chain.toString();
		}
		
		// Returns the list of WordInfo objects in this chain
		public List<WordInfo> getElements() {
			return chain;
		}
	}
	
	
	/* Creates a new TextAssociator without any associations 
	 */
	public TextAssociator() {
		size = 1000;
		table = new WordInfoSeparateChain[size];
		
		//TODO: Implement as explained in spec
	}
	
	
	/* Adds a word with no associations to the TextAssociator 
	 * Returns False if this word is already contained in your TextAssociator ,
	 * Returns True if this word is successfully added
	 */
	public boolean addNewWord(String word) {
		
		int index = hashString(word);
		WordInfo newWord = new WordInfo(word);
		
		if (table[index] == null) {
			table[index] = new WordInfoSeparateChain();
		}
		
		if (table[index].size() > 5) {
			expandTable();
		}
		
        if (getWordInfo(index, word) == null) {
        	return table[index].add(newWord);
        	
        }
        return false;
		
//		if (!table[index].getElements().contains(newWord)) {
//			System.out.println("actually false? " + !table[index].getElements().contains(newWord));
//			return table[index].add(newWord);
//		}
//		return false;
		//TODO: check twice?
		//TODO: check for same word
		
		
		//TODO: Implement as explained in spec
	}
	
	private void expandTable() {
		size *= 10;
		WordInfoSeparateChain[] tempTable = new WordInfoSeparateChain[size];
		for (int i = 0; i < table.length; i++) { // iterate through original table
			WordInfoSeparateChain bucket = table[i]; // 
			
			//For each separate chain, grab each individual WordInfo
			if () {
				for (WordInfo curr : bucket.getElements()) { // iterate through chain
					int index = hashString(curr.getWord()); // calculate new index
					if (table[index] == null) {
						table[index] = new WordInfoSeparateChain(); // create new chain
					}
					addNewWord(curr);
				}
			}
		}
		table = tempTable;
	}
	
	private int hashString(String word) {
		return Math.abs(word.hashCode() % size);
	}
	
	/* Adds an association between the given words. Returns true if association correctly added, 
	 * returns false if first parameter does not already exist in the SpellChecker or if 
	 * the association between the two words already exists
	 */
	public boolean addAssociation(String word, String association) {
		int index = hashString(word);
		WordInfo wi = getWordInfo(index, word);
		if (wi != null) {
			if (!wi.getAssociations().contains(association)) {
				return wi.addAssociation(association);
			}
		}
		return false;
		//TODO: Implement as explained in spec
	}
	
	
	/* Remove the given word from the TextAssociator, returns false if word 
	 * was not contained, returns true if the word was successfully removed.
	 * Note that only a source word can be removed by this method, not an association.
	 */
	public boolean remove(String word) {
		int index = hashString(word);
        WordInfo wi = getWordInfo(index, word);
		if (wi != null) {
			return table[index].remove(wi);
		}
		return false;
		//TODO: Implement as explained in spec
	}
	
	
	/* Returns a set of all the words associated with the given String  
	 * Returns null if the given String does not exist in the TextAssociator
	 */
	public Set<String> getAssociations(String word) {
		int index = hashString(word);
        WordInfo wi = getWordInfo(index, word);
        if (wi != null) {
        	return wi.getAssociations();
        }
        return null;
	}
	
	private WordInfo getWordInfo(int index, String word) {
		WordInfoSeparateChain chain = table[index];
		List<WordInfo> list = chain.getElements();
		for (WordInfo w : list) {
			if (w.getWord().equals(word)) {
				return w;
			}
		}
		return null;
	}
	
	/* Prints the current associations between words being stored
	 * to System.out
	 */
	public void prettyPrint() {
		System.out.println("Current number of elements : " + size);
		System.out.println("Current table size: " + table.length);
		
		//Walk through every possible index in the table
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				WordInfoSeparateChain bucket = table[i];
				
				//For each separate chain, grab each individual WordInfo
				for (WordInfo curr : bucket.getElements()) {
					System.out.println("\tin table index, " + i + ": " + curr);
				}
			}
		}
		System.out.println();
	}
}
