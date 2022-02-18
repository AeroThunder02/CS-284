package hw2;

// I pledge my Honor that I have abided by the Stevens Honor System
// Maciej Kowalczyk

public class hw2 {
	//methods
	
	//method with complexity O(n^2)
	public static void method1(int n) {
		int counter = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.println("Operation " + counter);
				counter++;
			}
		}		
	}
	
	//method with complexity O(n^3)
	public static void method2(int n) {
		int counter = 0;
        for (int i = 0; i < n; i++) {
        	
            for (int j = 0; j < n; j++) {
            	
                for (int k = 0; k < n; k++) {
                    System.out.println("Operation " + counter);
                    counter++;
                }
            }
        }
    }
	
	//method with complexity O(log n)
	public static void method3(int n) {
		int counter = 0;
		for (int i = 1; i < n; i*=2) {
			System.out.println("Operation " + counter);
			counter++;
		}
	}
	
	//method with complexity O(n log n)
	public static void method4(int n) {
		int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j *= 2) {
                System.out.println("Operation " + counter);
                counter++;
            }
        }
    }
	
	//method with complexity O(log log n)
	public static void method5(int n) {
        int counter = 0;
        for (int i = 2; i < n; i *= 2) {
            System.out.println("Operation " + counter);
            counter++;
        }
    }
	
	//Extra Credit
	//method with complexity O(2^n)
	public static int method6(int n) {
		if (n == 0) {
			return 1;
		}
		return method6(n-1) + method6(n-1);
	}
	
	
	//testing
	public static void main(String args[]) {
		method1(3);
		method2(3);
		method3(7);
		method4(7);
		method5(7);
		System.out.println(method6(2));
	}
}
