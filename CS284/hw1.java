// Maciej Kowalczyk CS 284 - RJ

package assignment1;

import java.util.*;


public class CoinPurse {
	
	//Data Fields & Instances
	private int numGalleons;
	private int numSickles;
	private int numKnuts;
	private int total;
	private static final int CAPACITY = 256;
	Random rand = new Random();
	private static int[] seq1 = new int[] {1,1,1};
	private static int[] seq2 = new int[] {-1,-1,-1};
	//Methods
	public CoinPurse() {
		total = 0;
	}
	
	public CoinPurse(int g, int s, int k) {
		numGalleons = g;
		numSickles = s;
		numKnuts = k;
		total = (k + s + g);
		
		if ((s < 0) || (g < 0) || (k < 0)) {
			throw new IllegalArgumentException(
					"You cannot input a negative amount!");
		}
		else if (total > CAPACITY) {
			throw new IllegalArgumentException(
					"You cannot have more than 256 total coins!");
		}		
	}
	
	//Methods relating to coin type Galleons
	public int depositGalleons(int n) {
		
		if (n < 0) {
			throw new IllegalArgumentException(
					"You cannot input a negative amount!");
		}
		
		else if ((this.numCoins() + n) > CAPACITY) {
			throw new IllegalArgumentException(
					"You cannot have more than 256 total coins!");
		}
		
		numGalleons += n;
		return numGalleons;
	}
	
	public int withdrawGalleons(int n) {
		if (n > numGalleons) {
			throw new IllegalArgumentException(
					"You do not have enough Galleons!");
		}
		
		else if (n < 0) {
			throw new IllegalArgumentException(
					"You cannot withdraw a negative number!");
		}
		
		numGalleons -= n;
		return numGalleons;
	}
	
	//Methods relating to coin type Sickles
	public int depositSickles(int n) {
		
		if (n < 0) {
			throw new IllegalArgumentException(
					"You cannot input a negative amount!");
		}
		
		else if ((this.numCoins() + n) > CAPACITY) {
			throw new IllegalArgumentException(
					"You cannot have more than 256 total coins!");
		}
		
		numSickles += n;
		return numSickles;
	}

	public int withdrawSickles(int n) {
		if (n > numSickles) {
			throw new IllegalArgumentException(
					"You do not have enough Galleons!");
		}
		
		else if (n < 0) {
			throw new IllegalArgumentException(
					"You cannot withdraw a negative number!");
		}
		
		numSickles -= n;
		return numSickles;
	}
	
	//Methods relating to coin type Knuts
	public int depositKnuts(int n) {
		
		if (n < 0) {
			throw new IllegalArgumentException(
					"You cannot input a negative amount!");
		}
		
		else if ((this.numCoins() + n) > CAPACITY) {
			throw new IllegalArgumentException(
					"You cannot have more than 256 total coins!");
		}
		
		numKnuts += n;
		return numKnuts;
	}
	
	public int withdrawKnuts(int n) {

		if (n > numKnuts) {
			throw new IllegalArgumentException(
					"You do not have enough Galleons!");
		}
		
		else if (n < 0) {
			throw new IllegalArgumentException(
					"You cannot withdraw a negative number!");
		}
		
		numKnuts -= n;
		return numKnuts;
	}
	
	//Methods relating to all coins
	public int numCoins() {
		total = numGalleons + numSickles + numKnuts;
		return total;
	}
	
	public int totalvalue() {
		return (numGalleons * 493) + (numSickles * 29) + numKnuts;
	}
	
	public String toString() {
		return ("You currently have " + numGalleons + " Galleons, " + numSickles + " Sickles, and " + numKnuts + " knuts.");
			
	}
	
	public boolean exactChange(int n) {
		
		if (n < 0) {
			throw new IllegalArgumentException(
					"You cannot input a negative amount!");
		}
		
		else if(n > (totalvalue())){
			throw new IllegalArgumentException(
					"You do not have that much total money!");
			
		}
		
		
		int remainder;
		
		int g = (n/493);
		remainder = n%493;
		
		int s = (remainder/29);
		
		
		int k = remainder%29;
		
		
		if (g > numGalleons) {
			n = n - (numGalleons*493);
		} 
		else {
			n = n - (g*493);
		}
		
		
		if (s > numSickles) {
			n = n - (numSickles*29);
		}
		else {
			n = n - (s*29);
		}
		
		
		if (k > numKnuts) {
			n = n - numKnuts;
		}
		else {
			n = n - k;
		}
		
		
		if (n > 0) {
			return false;
		}
		return true;
	}

	public int[] withdraw(int n) {
		if (n < 0) {
			throw new IllegalArgumentException(
					"You cannot withdraw a negative amount!");
		}
		
		else if(n > (this.totalvalue())){
			throw new IllegalArgumentException(
					"You do not have that much total money!");
		}
		
		int G = n/493;
		int remainder = n%493; 
		int S = remainder/29;
		int K = remainder%29; 
		
		if (exactChange(n)) {
			int[] arr = {G, S, K};
			return arr;
		}
		
		else {
			
			if (K > numKnuts) {
				S = S++;
				K = 0;
			}
			if (S > numSickles) {
				G = G++;
				S = 0;
			}
			
			int[] arr = {G, S, K};
			return arr;
		}
		
	}
	
	public int drawRandCoin() {
		if (total == 0) {
			throw new IllegalArgumentException(                        
					"There are no coins to draw from!");              
		}
		int G_counter = numGalleons;
		int S_counter = numSickles;
		int K_counter = numKnuts;
		List<Integer> coins = new ArrayList<>();
		
		while (G_counter > 0) {
			coins.add(2);
			G_counter--;
		}
		
		while (S_counter > 0) {
			coins.add(1);
			S_counter--;
		}
			
		while(K_counter > 0) {
			coins.add(0);
			K_counter--;
		}
		
		int ArrayListlength = coins.size();        //NOTE: This is unbelievably scuffed, but my sleep deprived brain couldnt come up with a better solution
		double Dindex = Math.random() * ArrayListlength;
		int index = (int)Dindex;    //Please for the love of god change these variable names
		
		int randomCoin = coins.get(index);
		
		return randomCoin;
		
	}
	
	public int[] drawRandSequence(int n) {
		
		if (n < 0) {
			throw new IllegalArgumentException(                       
					"Entry must be a positive integer!");              
		}
		
		int[] arr = new int[n];
		
		for (int i = 0; i < n; i++) {
			arr[i] = drawRandCoin();
		}
		
		return arr;
	}
	
	public static int compareSequences(int[] coinSeq1, int[] coinSeq2) {
		int size1 = coinSeq1.length;
		int size2 = coinSeq2.length;
		int score = 0;
		if (size1 != size2) {
			throw new IllegalArgumentException(
					"Both arrays must be the same length!");	
		}
		
		for (int i = 0; i < size1; i++) {
			if (coinSeq1[i] == coinSeq2[i]) {
				continue;
			}
			else if (coinSeq1[i] < coinSeq2[i]) {
				score--;
			}
			else if (coinSeq1[i] > coinSeq2[i]) {
				score++;
			}
		}
		
		if (score > 0) {
			return 1;
		}
		else if (score < 0) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
	
	// Main method. All of this is testing.
	public static void main(String args[]) {
		CoinPurse C1 = new CoinPurse(1, 2, 8);
		System.out.println(C1);
		System.out.println(C1.numCoins());
		System.out.println(C1.totalvalue());
		System.out.println(C1.exactChange(559));
		System.out.println(C1.exactChange(549));
		System.out.println(C1.withdraw(559));
		
	}
}

