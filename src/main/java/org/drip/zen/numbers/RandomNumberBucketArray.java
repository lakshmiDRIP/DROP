
package org.drip.zen.numbers;

public class RandomNumberBucketArray {

	public static double CalculatePercentage (int count, int total)
	{
		double percent = 100. * (double) count / (double) total;
		return percent;
	}

	public static int FindBucketIndex (double number, int totalBuckets)
	{
		int bucketIndex = 0;
		double bucketWidth = 1. / totalBuckets;

		for (int i = 0; i < totalBuckets; i = i + 1) {
			double bucketLeftEnd = i * bucketWidth;
			double bucketRightEnd = (i + 1) * bucketWidth;

			if (number > bucketLeftEnd && number <= bucketRightEnd)
				bucketIndex = i;
		}

		return bucketIndex;
	}

	public static void main (String[] args)
	{
		int numberOfBuckets = 10;

		int[] countbucket = new int[numberOfBuckets];

		int totalTrials = 10000;
		int trialNumber = 1;

		while (trialNumber <= totalTrials)
		{
			double randomNumber = Math.random();

			int randomNumberBucket = FindBucketIndex (randomNumber, numberOfBuckets);

			countbucket[randomNumberBucket] = countbucket[randomNumberBucket] + 1;

			trialNumber = trialNumber + 1;
		}

		String countString = "\t| ";

		for (int i = 0; i < numberOfBuckets; i = i + 1)
			countString = countString + countbucket[i] + " | ";

		System.out.println (countString);

		String countPercentString = "\t| ";

		for (int i = 0; i < numberOfBuckets; i = i + 1)
			countPercentString = countPercentString + CalculatePercentage (countbucket[i], totalTrials) + "% | ";

		System.out.println (countPercentString);
	}
}
