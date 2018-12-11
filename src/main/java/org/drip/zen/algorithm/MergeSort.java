
package org.drip.zen.algorithm;

public class MergeSort {

	static void SwapLocations (
		double[] numberArray,
		final int location1,
		final int location2)
	{
		double tempNumber = numberArray[location1];
		numberArray[location1] = numberArray[location2];
		numberArray[location2] = tempNumber;
	}

	static void SplitAndMerge (
		double[] numberArray,
		int startLocation,
		int endLocation)
	{
		if (startLocation == endLocation)
		{
			return;
		}

		if (startLocation == endLocation - 1)
		{
			if (numberArray[startLocation] > numberArray[endLocation])
				SwapLocations (numberArray, startLocation, endLocation);

			return;
		}

		double[] mergedNumberArray = new double[endLocation - startLocation + 1];
		int midLocation = (startLocation + endLocation) / 2;
		int rightStartLocation = midLocation + 1;
		int rightLocation = rightStartLocation;
		int leftStartLocation = startLocation;
		int leftLocation = leftStartLocation;
		int rightEndLocation = endLocation;
		int leftEndLocation = midLocation;
		int mergedArrayLocation = 0;

		SplitAndMerge (numberArray, leftStartLocation, leftEndLocation);

		SplitAndMerge (numberArray, rightStartLocation, rightEndLocation);

		while (leftLocation <= leftEndLocation && rightLocation <= rightEndLocation)
		{
			if (numberArray[leftLocation] < numberArray[rightLocation]) {
				mergedNumberArray[mergedArrayLocation] = numberArray[leftLocation];
				mergedArrayLocation = mergedArrayLocation + 1;
				leftLocation = leftLocation + 1;
			}
			else if (numberArray[leftLocation] > numberArray[rightLocation])
			{
				mergedNumberArray[mergedArrayLocation] = numberArray[rightLocation];
				mergedArrayLocation = mergedArrayLocation + 1;
				rightLocation = rightLocation + 1;
			}
			else
			{
				mergedNumberArray[mergedArrayLocation] = numberArray[leftLocation];
				mergedNumberArray[mergedArrayLocation] = numberArray[rightLocation];
				mergedArrayLocation = mergedArrayLocation + 1;
				leftLocation = leftLocation + 1;
				mergedArrayLocation = mergedArrayLocation + 1;
				rightLocation = rightLocation + 1;
			}
		}

		while (leftLocation <= leftEndLocation)
		{
			mergedNumberArray[mergedArrayLocation] = numberArray[leftLocation];
			mergedArrayLocation = mergedArrayLocation + 1;
			leftLocation = leftLocation + 1;
		}

		while (rightLocation <= rightEndLocation)
		{
			mergedNumberArray[mergedArrayLocation] = numberArray[rightLocation];
			mergedArrayLocation = mergedArrayLocation + 1;
			rightLocation = rightLocation + 1;
		}

		mergedArrayLocation = 0;

		for (int mergedArrayLeftLocation = leftStartLocation; mergedArrayLeftLocation <= leftEndLocation; mergedArrayLeftLocation = mergedArrayLeftLocation + 1)
		{
			numberArray[mergedArrayLeftLocation] = mergedNumberArray[mergedArrayLocation];
			mergedArrayLocation = mergedArrayLocation + 1;
		}

		for (int mergedArrayRightLocation = rightStartLocation; mergedArrayRightLocation <= rightEndLocation; mergedArrayRightLocation = mergedArrayRightLocation + 1)
		{
			numberArray[mergedArrayRightLocation] = mergedNumberArray[mergedArrayLocation];
			mergedArrayLocation = mergedArrayLocation + 1;
		}
	}

	static void SplitAndMerge (
		double[] numberArray)
	{
		SplitAndMerge (numberArray, 0, numberArray.length - 1);
	}

	public static void main (
		String[] input)
	{
		double[] unsortedNumberArray = {6, 1, 5, 7, 9, 2, 8, 4, 3, 12};

		SplitAndMerge (unsortedNumberArray);

		for (int i = 0; i < unsortedNumberArray.length; i = i + 1)
		{
			System.out.println (unsortedNumberArray[i]);
		}
	}
}
