/**
 * 
 */
package spelling;

//import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.*;
	
/**
 * WPTree implements WordPath by dynamically creating a tree of words during a Breadth First
 * Search of Nearby words to create a path between two words. 
 * 
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class WPTree implements WordPath {

	// this is the root node of the WPTree
	private WPTreeNode root;
	// used to search for nearby Words
	private NearbyWords nw; 
	
	// This constructor is used by the Text Editor Application
	// You'll need to create your own NearbyWords object here.
	public WPTree () {
		this.root = null;
		// TODO initialize a NearbyWords object
		Dictionary d = new DictionaryHashSet();
		DictionaryLoader.loadDictionary(d, "data/dict.txt");
		this.nw = new NearbyWords(d);
	}
	
	//This constructor will be used by the grader code
	public WPTree (NearbyWords nw) {
		this.root = null;
		this.nw = nw;
	}
	
	// see method description in WordPath interface
	/** Return a path from word1 to word2 through dictionary words with
	 *  the restriction that each step in the path can involve only a
	 *  single character mutation  
	 * @param word1 The first word
	 * @param word2 The second word
	 * @return list of Strings which are the path from word1 to word2
	 *         including word1 and word2
	 */
	public List<String> findPath(String word1, String word2,boolean flag) 
	{
	    // TODO: Implement this method.
		
		/*List<String> path=new ArrayList<>();
		path=nw.distanceOne(word1, true);
		return path;*/
		if(!nw.dict.isWord(word2))//If word1 should also exist in dict 
			return null;			//!nw.dict.isWord(word1) ||
		
		
		Set<String> visited = new HashSet<>();
		visited.add(word1);
		Queue<WPTreeNode> queue = new LinkedList<>();
		root = new WPTreeNode(word1, null);
		queue.add(root);

		while (!queue.isEmpty()) {
			WPTreeNode current = queue.poll();
			for (String n : nw.distanceOne(current.getWord(), flag)) {
				if (!visited.contains(n)) {
					visited.add(n);
					WPTreeNode child = current.addChild(n);
					queue.add(child);
					if (n.equals(word2)) {
						return child.buildPathToRoot();
					}
					
				}
			}
			
		}

		return null;
	}
	
	// Method to print a list of WPTreeNodes (useful for debugging)
	private String printQueue(List<WPTreeNode> list) {
		String ret = "[ ";
		
		for (WPTreeNode w : list) {
			ret+= w.getWord()+", ";
		}
		ret+= "]";
		return ret;
	}
	
}

/* Tree Node in a WordPath Tree. This is a standard tree with each
 * node having any number of possible children.  Each node should only
 * contain a word in the dictionary and the relationship between nodes is
 * that a child is one character mutation (deletion, insertion, or
 * substitution) away from its parent
*/
class WPTreeNode {
    
    private String word;
    private List<WPTreeNode> children;
    private WPTreeNode parent;
    
    /** Construct a node with the word w and the parent p
     *  (pass a null parent to construct the root)  
	 * @param w The new node's word
	 * @param p The new node's parent
	 */
    public WPTreeNode(String w, WPTreeNode p) {
        this.word = w;
        this.parent = p;
        this.children = new LinkedList<WPTreeNode>();
    }
    
    /** Add a child of a node containing the String s
     *  precondition: The word is not already a child of this node
     * @param s The child node's word
	 * @return The new WPTreeNode
	 */
    public WPTreeNode addChild(String s){
        WPTreeNode child = new WPTreeNode(s, this);
        this.children.add(child);
        return child;
    }
    
    /** Get the list of children of the calling object
     *  (pass a null parent to construct the root)  
	 * @return List of WPTreeNode children
	 */
    public List<WPTreeNode> getChildren() {
        return this.children;
    }
   
    /** Allows you to build a path from the root node to 
     *  the calling object
     * @return The list of strings starting at the root and 
     *         ending at the calling object
	 */
    public List<String> buildPathToRoot() {
        WPTreeNode curr = this;
        List<String> path = new LinkedList<String>();
        while(curr != null) {
            path.add(0,curr.getWord());
            curr = curr.parent; 
        }
        return path;
    }
    
    /** Get the word for the calling object
     *
	 * @return Getter for calling object's word
	 */
    public String getWord() {
        return this.word;
    }
    
    /** toString method
    *
	 * @return The string representation of a WPTreeNode
	 */
    public String toString() {
        String ret = "Word: "+word+", parent = ";
        if(this.parent == null) {
           ret+="null.\n";
        }
        else {
           ret += this.parent.getWord()+"\n";
        }
        ret+="[ ";
        for(WPTreeNode curr: children) {
            ret+=curr.getWord() + ", ";
        }
        ret+=(" ]\n");
        return ret;
    }

}
//Alternate FindPath
/*
 *		Objects.requireNonNull(word1);
		Objects.requireNonNull(word2);
 if (!nw.dict.isWord(word1) || !(nw.dict.isWord(word2)))
			return null;
		
	    Queue<WPTreeNode> queue = new LinkedList<WPTreeNode>();
	    HashSet<WPTreeNode> visited = new HashSet<WPTreeNode>();
	    List<String> neighbours = new LinkedList<String>();
		
		this.root = new WPTreeNode(word1, null);
		queue.add(root);
		visited.add(root);
		
		WPTreeNode node = root;
		
		while (!queue.isEmpty() && !visited.contains(word2)) {
			WPTreeNode curr = queue.remove();
			neighbours = nw.distanceOne(curr.getWord(), true);
			
			for (String n: neighbours) {
				if (!visited.contains(n)) {
					node = curr.addChild(n);
					visited.add(node);
					queue.add(node);
					if (n.equals(word2)) {
						return node.buildPathToRoot();
					}
				}
			}
		}
		// no path between word1 and word2
	    return null; 
 * */
