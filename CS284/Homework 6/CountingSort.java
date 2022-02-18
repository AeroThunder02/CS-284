package hw6;
import java.util.*;
public class CountingSort {

	/**
	 * CoutingSort method
	 * @param A: array to be sorted
	 */
	public static void sort(int[] A) {
		int n = A.length;
		int k = A[0];
		 
		 for (int i = 1; i < n; i++) {
			 if (A[i] > k)
				 k = A[i];
		 }
		 
		 int[] B = new int[n + 1];
		 int[] C = new int[k + 1];
		

		 for (int i = 0; i < k; ++i) {
			 C[i] = 0;
		 }
		

		 for (int j = 0; j < n; j++) {
			 C[A[j]]++;
		 }
		

		 for (int i = 1; i <= k; i++) {
			 C[i] += C[i - 1];
		 }
		

		 for (int j = n - 1; j >= 0; j--) {
			 B[C[A[j]] - 1] = A[j];
			 C[A[j]]--;
		 }
		

		 for (int i = 0; i < n; i++) {
			 A[i] = B[i];
		 }
	}
}
