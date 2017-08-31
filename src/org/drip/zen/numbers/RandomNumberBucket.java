
package org.drip.zen.numbers;

public class RandomNumberBucket {

	public static double CalculatePercentage (int count, int total)
	{
		double percent = 100. * (double) count / (double) total;
		return percent;
	}

	public static int FindBucketIndex (double number)
	{
		int bucketIndex = 0;

		if (number > 0 && number <= 0.2)
			bucketIndex = 0;
		else if (number > 0.2 && number <= 0.4)
			bucketIndex = 1;
		else if (number > 0.4 && number <= 0.6)
			bucketIndex = 2;
		else if (number > 0.6 && number <= 0.8)
			bucketIndex = 3;
		else if (number > 0.8 && number <= 1.0)
			bucketIndex = 4;

		return bucketIndex;
	}

	public static void main (String[] args)
	{
		int countbucket0 = 0;
		int countbucket1 = 0;
		int countbucket2 = 0;
		int countbucket3 = 0;
		int countbucket4 = 0;

		int totalTrials = 1000;
		int trialNumber = 1;

		while (trialNumber <= totalTrials)
		{
			double randomNumber = Math.random();

			int randomNumberBucket = FindBucketIndex (randomNumber);

			if (randomNumberBucket == 0)
				countbucket0 = countbucket0 + 1;
			else if (randomNumberBucket == 1)
				countbucket1 = countbucket1 + 1;
			else if (randomNumberBucket == 2)
				countbucket2 = countbucket2 + 1;
			else if (randomNumberBucket == 3)
				countbucket3 = countbucket3 + 1;
			else if (randomNumberBucket == 4)
				countbucket4 = countbucket4 + 1;

			trialNumber = trialNumber + 1;
		}

		System.out.println ("\t[" + countbucket0 + " | " + countbucket1 + " | " + countbucket2 + " | " + countbucket3 + " | " + countbucket4 + "]");

		System.out.println ("\t[" +
			CalculatePercentage (countbucket0, totalTrials) + "% | " +
			CalculatePercentage (countbucket1, totalTrials) + "% | " +
			CalculatePercentage (countbucket2, totalTrials) + "% | " +
			CalculatePercentage (countbucket3, totalTrials) + "% | " +
			CalculatePercentage (countbucket4, totalTrials) + "%]"
		);
	}
}
