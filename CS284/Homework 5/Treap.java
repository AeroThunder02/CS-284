//I'll be honest, JavaDoc commenting did not work for me. So I did my best with regular comments if thats fine.

package treaps;

import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

public class Treap<E extends Comparable<E>> {
	
	private static class Node<E>{
		//Data fields
		public E data; //key for search
		public int priority; //random heap priority
		public Node<E> left;
		public Node<E> right;
		
		
		//Node Creation
		public Node(E data, int priority) {
			
			if (data == null) {
				throw new IllegalArgumentException(
						"Invalid Input");
			}
			
			this.data = data;
			this.priority = priority;
			this.left = null;
			this.right = null;			
		}
		
		//Performs a right rotation returning the root node and updating data and priority attributes
		public Node<E> rotateRight(){
			if (left == null) {
				return this;
			}
			Node<E> root = left;
			this.left = root.right;
			root.right = this;
			return root;
		}
		
		//Performs a left rotation returning the root node and updating data and priority attributes
		public Node<E> rotateLeft(){
			if (right == null) {
				return this;
			}
			Node<E> root = right;
			this.right = root.left;
			root.left = this;
			return root;
		}
		
		//String representation of the node
		public String toString() {
			return  "(key=" + data.toString() + ", priority=" + priority + ")";
		}
	}
	
	//data fields
	private Node<E> root;
	private Random priorityGenerator;
	private HashSet<Integer> priorities; 
	/*
	 * I did a bit of reading for this one, along with using some of my past knowledge. 
	 * Using HashSet here allowed me to do three things:
	 * 1. I did not have to constantly check for common priorities, since a HashSet will do that for me. HashSets do not allow common elements
	 * 2. HashSets CAN store null values, so it helps me here in instances where a leaf may be null
	 * 3. Gives me a place to store all of the priorities of the tree, without having to worry about 1 and 2.
	 * 
	 * Essentially, its the perfect way to store everything I need for this assignment, took me a while to implement
	 * despite using a few sources, but I do hope this works well
	 */
	
	//Creating an empty tree with a random priorityGenerator
	public Treap() {
		root = null;
		priorityGenerator = new Random();
		priorities = new HashSet<Integer>();
	}
	
	//Creates an empty tree and instantiates the priorityGenerator using seed
	public Treap(long seed) {
		priorityGenerator = new Random(seed);
		priorities = new HashSet<Integer>();
	}
	
	
	//Methods
	
	//helper function for add to reheap the tree when adding a new node
	private void reheap(Node<E> current, Stack<Node<E>> path) {
		
		while (!path.isEmpty()) {  //Until theres an open node, the tree will continue to reorganize itself as it should
			
			Node<E> parent = path.pop();
			
			if (parent.priority < current.priority) { // maximum heap, if not true, no need to reheap.
				
				if (parent.data.compareTo(current.data) > 0) {
					current = parent.rotateRight();
				}
				else {
					current = parent.rotateLeft();
				}
				
				if (!path.isEmpty()) {
					
					if (path.peek().left == parent) {
						path.peek().left = current;
					}
					else {
						path.peek().right = current;
					}
					
				}
				else {
					this.root = current;
				}
			} 
			else {
				break;
			}
		}
	}

	//Adds a node and inserts the element into the key and a random priority
	public boolean add(E key) {
		int priority = priorityGenerator.nextInt();
		while(priorities.contains(priority)) {
			priority = priorityGenerator.nextInt();
		}
		
		return add(key, priority);
		
	}
	
	//Add method for our treap
    public boolean add(E key, int priority) {
        if (priorities.contains(priority))
            throw new IllegalArgumentException();

        if (root == null) {   //If theres no tree yet, just set it as the root
            root = new Node<E>(key, priority);
            priorities.add(priority);
            return true;
        }
        
        else {
        	Node<E> temp = new Node<E>(key, priority);  
        	Node<E> current = root; //temp variable to store our data when we eventually have to reheap
        	
        	Stack<Node<E>> path = new Stack<Node<E>>();
        	
        	while (current != null) {  //while we don't have anywhere to put it, this will search for an open spot to place the node
        		if (current.data.compareTo(key) == 0) { //checks if the node already exists
        			return false;
        		}
        		
        		if (current.data.compareTo(key) > 0) {
        			path.push(current);
        			if (current.left == null) {
        				current.left = temp;
        				reheap(temp, path);  //reheaping after adding
        				return true;
        			} 
        			else {
        				current = current.left;
        			}
        		} 
        		else {
        			path.push(current);
        			if (current.right == null) {
        				current.right = temp;
        				reheap(temp, path);
        				return true;
        			} 
        			else {
        				current = current.right;
        			}
        		}
        	}
        	return false;
        }
    }

    //method to search for given data and indicate whether or not it is present in the treap. NOTE: useful in delete so do this first
    private boolean find(Node<E> current, E key) {
    	if (current == null) {  //since this is a recursive implementation, this is our base case
    		return false;
    	}
    	else {
    		int compare = current.data.compareTo(key);
    		if (compare == 0) {
    			return true;
    		}
    		else {
    			return find(current.right, key) || find(current.left, key); 
    		}
    	}
    }
    
    //returns the data to look for
    public boolean find(E key) {
    	return find(root, key);
    }
    
	private Node<E> delete(E key, Node<E> current) {
		if (current == null) {
			return current;
		}
		else {
			if (current.data.compareTo(key) < 0) {
				current.right = delete(key, current.left);
			}
			else {
				if (current.right == null) {
					current = current.left;
				}
				else if (current.left == null) {
					current = current.right;
				}
				else {
					if (current.right.priority < current.left.priority) {
						current = current.rotateRight();
						current.right = delete(key, current.right);
					}
					else {
						current = current.rotateLeft();
						current.left = delete(key, current.left);
					}
				}
			}
		}
		return current;
	}
	
	//Wrapper function for delete()
	public boolean delete(E key) {
		if (root == null) {
			return false;
		}
		else if (!find(key)) {
			return false;
		}
		else {
			root = delete(key, root);
			return true;
		}
	}
    
	//Returns a string listing the preorder traversal of the treap
	private StringBuilder toString(Node<E> current, int n) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < n; i++) {
			str.append(" ");
		}
		
		if (current == null) {
			return str.append("null\n");
		}
		else {
			str.append(current.toString() + "\n");
			str.append(toString(current.left, n+1));
			str.append(toString(current.right, n+1));
			return str;
		}
	}
	
	//Wrapper function of toString()
	public String toString() {
		return toString(root, 1).toString();
	}
    
    public static void main(String[] args) {
    	Treap<Integer> testTree = new Treap<Integer>();
		testTree.add(4, 19);
		testTree.add(2, 31);
		testTree.add(6, 70);
		testTree.add(1, 84);
		testTree.add(3, 12);
		testTree.add(5, 83);
		testTree.add(7, 26);
		
		System.out.println(testTree.toString());
    }
}
