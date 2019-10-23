package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/*
 * Harshavardhan 
 * Manideep
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	private String w;
	private String currWord;
	private String output;
	private boolean trained = false;
	// The random number generator
	private Random rnGenerator;
	private boolean flag;
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{ 
		if(trained==false){
		String[] source = sourceText.split("[.,!? ]+");
		starter = source[0];
		String prevWord = starter;
		for(int i=1; i<source.length; i++){
			w = source[i];
		//	System.out.println(w);
			flag = false;
			for(ListNode ln : wordList){
				if(prevWord.equals(ln.getWord())){
					flag = true;
					ln.addNextWord(w);
					break;
				}
			}
			if(flag == false){
				ListNode ln = new ListNode(prevWord);
				ln.addNextWord(w);
				wordList.add(ln);
			}
			prevWord = w;
		}
		wordList.add(new ListNode(source[source.length-1]));
		wordList.get(wordList.size()-1).addNextWord(starter);
		trained = true;
		//System.out.println(wordList.toString());
		}
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	
		currWord = starter;
		output = "";
		if(numWords>0)
			output = output.concat(currWord+" ");
		while(numWords>1 && wordList.isEmpty()==false){
			for(ListNode ln : wordList){
				if(ln.getWord().equals(currWord)){
					w = ln.getRandomNextWord(rnGenerator);
					output = output.concat(w+" ");
					currWord = w;
					numWords--;
					break;
				}
			}
			//System.out.println(numWords + " - " + w);
		}
		return output;
	}	
	
	@Override
	public String generateText(String cur,int numWords) {
	
		currWord = cur;
		output = "";
		if(numWords>0)
			output = output.concat(currWord+" ");
		while(numWords>1 && wordList.isEmpty()==false){
			for(ListNode ln : wordList){
				if(ln.getWord().equals(currWord)){
					w = ln.getRandomNextWord(rnGenerator);
					output = output.concat(w+" ");
					currWord = w;
					numWords--;
					break;
				}
			}
			//System.out.println(numWords + " - " + w);
		}
		return output;
	}	
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		//output = "";
		String[] source = sourceText.split("[.,!? ]+");
		starter = source[0];
		String prevWord = starter;
		for(int i=1; i<source.length; i++){
			w = source[i];
			flag = false;
			for(ListNode ln : wordList){
				if(prevWord.equals(ln.getWord())){
					flag = true;
					ln.addNextWord(w);
					break;
				}
			}
			if(flag == false){
				ListNode ln = new ListNode(prevWord);
				ln.addNextWord(w);
				wordList.add(ln);
			}
			prevWord = w;
		}
		wordList.add(new ListNode(source[source.length-1]));
		wordList.get(wordList.size()-1).addNextWord(starter);
	}
	
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		//String textString = "hi there hi Leo";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		
		return nextWords.get(generator.nextInt(nextWords.size()));
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


