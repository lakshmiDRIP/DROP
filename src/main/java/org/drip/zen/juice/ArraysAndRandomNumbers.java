
package org.drip.zen.juice;

/*
 * 1) Concept of Random Number
 * 2) Generate a Random Number between 0 and 1
 * 3) Concept of evenly distributed Random Number between Left and Right
 * 4) Generate a Random Number between "Lower Bound" and "Upper Bound"
 * 5) Concept of an Array - syntax and Usage
 * 6) Generate an Array of bounded Random Numbers
 * 7) Concept of Distribution of Buckets
 */

public class ArraysAndRandomNumbers {

	static double RandomNumber0_1()
	{
		return Math.random();
	}

	static double RandomNumber (double lowerBound, double upperBound)
	{
		double randomNumber = lowerBound + (upperBound - lowerBound) * RandomNumber0_1();

		return randomNumber;
	}

	static double[] RandomNumberArray (double lowerBound, double upperBound, int arraySize)
	{
		double[] randomArray = new double[arraySize];

		for (int counter = 0; counter < arraySize; counter = counter + 1)
		{
			randomArray[counter] = RandomNumber (lowerBound, upperBound);
		}

		return randomArray;
	}

	static int[] QuartileDistribution (double lowerBound, double upperBound, double[] randomNumberArray)
	{
		int numberOfBuckets = 4;
		int[] quartileBuckets = new int[numberOfBuckets];
		int arraySize = randomNumberArray.length;
		double bucketWidth = (upperBound - lowerBound) / numberOfBuckets;

		for (int counter = 0; counter < arraySize; counter = counter + 1)
		{
			double randomNumber = randomNumberArray[counter];

			if (randomNumber >= lowerBound && randomNumber < lowerBound + bucketWidth)
				quartileBuckets[0] = quartileBuckets[0] + 1;
			else if (randomNumber >= lowerBound + bucketWidth && randomNumber < lowerBound + 2 * bucketWidth)
				quartileBuckets[1] = quartileBuckets[1] + 1;
			else if (randomNumber >= lowerBound + 2 * bucketWidth && randomNumber < lowerBound + 3 * bucketWidth)
				quartileBuckets[2] = quartileBuckets[2] + 1;
			else if (randomNumber >= lowerBound + 3 * bucketWidth && randomNumber <= upperBound)
				quartileBuckets[3] = quartileBuckets[3] + 1;
		}

		return quartileBuckets;
	}

	public static void main (String[] input)
	{
		double myLowerBound = 0;
		double myUpperBound = 100;
		int myArraySize = 100000;

		double[] myRandomNumberArray = RandomNumberArray (myLowerBound, myUpperBound, myArraySize);

		int[] myQuartileBuckets = QuartileDistribution (myLowerBound, myUpperBound, myRandomNumberArray);

		System.out.println ("\tFirst Quartile  : " + myQuartileBuckets[0] + " | " + (100. * myQuartileBuckets[0] / myArraySize) + "%");

		System.out.println ("\tSecond Quartile : " + myQuartileBuckets[1] + " | " + (100. * myQuartileBuckets[1] / myArraySize) + "%");

		System.out.println ("\tThird Quartile  : " + myQuartileBuckets[2] + " | " + (100. * myQuartileBuckets[2] / myArraySize) + "%");

		System.out.println ("\tFourth Quartile : " + myQuartileBuckets[3] + " | " + (100. * myQuartileBuckets[3] / myArraySize) + "%");
	}
}
