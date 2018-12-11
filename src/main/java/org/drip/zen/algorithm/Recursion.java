
package org.drip.zen.algorithm;

public class Recursion {

	static int RecursiveSum (int numberToSum)
	{
		if (numberToSum == 1)
			return 1;

		return numberToSum + RecursiveSum (numberToSum - 1);
	}

	static int Fibonacci (int sequenceNumber)
	{
		if (sequenceNumber == 0 || sequenceNumber == 1)
			return 1;

		return Fibonacci (sequenceNumber - 1) + Fibonacci (sequenceNumber - 2);
	}

	public static void main (String[] inputs)
	{
		int number = 10;

		System.out.println ("\n\tSum of first " + number + " numbers is " + RecursiveSum (number) + "\n");

		int fibonacciSequence = 10;

		for (int i = 0; i <= fibonacciSequence; i = i + 1)
			System.out.println ("\tFibonacci[" + i + "]: " + Fibonacci (i));
	}
}
