
package org.drip.zen.numbers;

public class NumberArray {

	public static int Maximum (int[] numbers)
	{
		int maximum = Integer.MIN_VALUE;

		int numberOfNumbers = numbers.length;

		for (int numberArrayCounter = 0; numberArrayCounter < numberOfNumbers; numberArrayCounter = numberArrayCounter + 1)
		{
			if (numbers[numberArrayCounter] > maximum)
				maximum = numbers[numberArrayCounter];
		}

		return maximum;
	}

	public static void main (String[] args)
	{
		int[] myNumbers = {1, 6, 4, 9, 12, 5, 25, 17};

		System.out.println ("\tMaximum is " + Maximum (myNumbers));
	}
}
