package hw6;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class SortTest {
	
	Sort a = new Sort();
	
	@Test
	void test() {
		Integer[] test = new Integer[] {2,6,9,5,6,7,1,0};
		assertEquals(Arrays.toString(a.sort(test)), "[0, 1, 2, 5, 6, 6, 7, 9]");
	}
	
	@Test
	void test2() {
		Integer[] test = new Integer[] {-1, -2, -6, -7, -10};
		assertEquals(Arrays.toString(a.sort(test)), "[-10, -7, -6, -2, -1]");
	}
	
	@Test
	void test3() {
		Integer[] test = new Integer[] {3,4,4,4,4,3,3};
		assertEquals(Arrays.toString(a.sort(test)), "[3, 3, 3, 4, 4, 4, 4]");
	}
	
	@Test
	void test4() {
		Integer[] test = new Integer[] {90, 60, 30, 20, 50};
		assertEquals(Arrays.toString(a.sort(test)), "[20, 30, 50, 60, 90]");
	}
	
	@Test
	void test5() {
		Integer[] test = new Integer[] {0, 1, 2, -1, 3};
		assertEquals(Arrays.toString(a.sort(test)), "[-1, 0, 1, 2, 3]");
	}

}
