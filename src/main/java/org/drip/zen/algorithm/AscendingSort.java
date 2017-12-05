
package org.drip.zen.algorithm;

public class AscendingSort {

	static void CompareAndSwap (
		int[] numberArray,
		int leftLocation,
		int rightLocation)
	{
		int leftNumber = numberArray[leftLocation];
		int rightNumber = numberArray[rightLocation];

		if (leftNumber > rightNumber)
		{
			numberArray[leftLocation] = rightNumber;
			numberArray[rightLocation] = leftNumber;
		}
	}

	static void BubbleMaximumToRight (
		int[] numberArray,
		int rightMostLocation)
	{
		for (int location = 0; location <= rightMostLocation; location = location + 1)
		{
			CompareAndSwap (numberArray, location, location + 1);
		}
	}

	static void BubbleSort (
		int[] numberArray)
	{
		for (int sweep = numberArray.length - 2; sweep >= 0; sweep = sweep - 1)
		{
			BubbleMaximumToRight (numberArray, sweep);
		}
	}

	public static void main (
		String[] input)
	{
		int[] unsortedNumberArray = {6, 1, 5, 7, 2, 8, 4, 3};

		BubbleSort (unsortedNumberArray);

		for (int i = 0; i < unsortedNumberArray.length; i = i + 1)
		{
			System.out.println (unsortedNumberArray[i]);
		}
	}
}
