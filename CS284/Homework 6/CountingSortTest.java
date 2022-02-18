package hw6;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class CountingSortTest extends CountingSort {

	@Test
	void test() {
		int[] A = new int[] {2,5,3,0,2,3,0,3};
		sort(A);
		assertEquals(Arrays.toString(A), "[0, 0, 2, 2, 3, 3, 3, 5]" );
	}
	
	@Test
	void test2() {
		int[] B = new int[] {1,8,8,9,4,5,3,3,2,6,7};
		sort(B);
		assertEquals(Arrays.toString(B), "[1, 2, 3, 3, 4, 5, 6, 7, 8, 8, 9]");
		
	}

}
