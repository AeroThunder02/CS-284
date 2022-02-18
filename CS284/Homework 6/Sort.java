package hw6;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

public class Sort {
	private static class Interval{
		//Data Fields
		
		private int upper;
		private int lower;
		
		//Constructer for Interval
		
		public Interval(int lower, int upper) {
			this.upper = upper;
			this.lower = lower;
		}
		
		//getters
		public int getLower() {
			return lower;
		}
		
		public int getUpper() {
			return upper;
		}
		
		//Checks for equality against object o
		
		public boolean equals(Object o) {
			if ((((Interval) o).getLower() == lower) && (((Interval) o).getUpper() == upper))
					return true;
			else
				return false;
		}
		
		//hash code generator
		public int hashCode() {
			return this.lower*this.lower + this.upper;
		}
		 
		 
	}
	
	
	//Swap function
	public static <T extends Comparable<T>> void swap(int a, int b, T[] array) {
		T store = array[b];
		array[b] = array[a];
		array[a] = store;
	}

	//Helper function for finding the pivot
	public static <T extends Comparable<T>> void pivot(T[] array, int a, int b, int c) {
		T end = array[a];
		T mid = array[b];
		T start = array[c];
		
		if (end.compareTo(mid) < 0){
			
			if (mid.compareTo(start) < 0) {
				swap(a,b,array);
			} 
			
			else {
				swap(a,c,array);
			}
		} 
		
		else if  (end.compareTo(start) > 0) {
			swap(a,c,array);
		}
	}
	
	//Helper function to partition array
	public static <T extends Comparable<T>> void partitioner(Set<Interval> set, T[] arr, Interval interval) {
		int low = interval.getLower();
		int high = interval.getUpper();
		int mid = (high+1) / 2;
		int t = low;
		int u = t+1;
		
		pivot(arr, low, mid, high);
		
		while (u < high) {
			if(arr[u].compareTo(arr[low]) < 0) {
				t++;
				swap(t, u, arr);
				u++;
			}
			else{
				u++;
			}
		}
		
		swap(t, low, arr);
		set.add(new Interval(0, t-1));
        set.add(new Interval(t+1, high));
        set.remove(set.toArray()[0]);
	}
	
	//bubble sort implementation
	public static<T extends Comparable<T>> void bubbleSort(T[] arr) {
		boolean swap = true;
		
		while(swap) {
			swap = false;
			for (int i = 0; i < arr.length-1; i++){
				if (arr[i].compareTo(arr[i+1])>0){
					swap(i, i+1, arr);
					swap = true;
				}
			}	
		}
	}
	
	//Now for Lamport's sorting algorithm
	public static <T extends Comparable<T>> T[] sort(T[] array) {
		Set<Interval> set = new HashSet();
		set.add(new Interval(0, array.length-1));
		
		while(!set.isEmpty()) {
			Interval[] t = set.toArray(new Interval[set.size()]);
			Interval i = t[0];
			set.remove(i);
			int d = i.getUpper() - i.getLower();
			if(d <= 3) 
				bubbleSort(array);
			
			else
				partitioner(set, array, i);		
		}
		return array;
	}

}
