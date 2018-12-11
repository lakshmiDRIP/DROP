
package org.drip.zen.juice;

/*
 * 1) Introduce Probability
 * 2) Motivate the Lesson
 * 3) Outline the Approach
 * 4) Write Down the Formula
 * 5) Random Numbers Between -1 and 1
 * 6) Is Inside Circle
 * 7) Count of Inside Circle
 * 8) Probability To PI
 * 9) Generate Random Numbers
 * 10) Bring them all together
 */

public class PiEstimator {

	static double RandomNumber()
	{
		double random = Math.random();

		double randomBetweenMinus1AndPlus1 = 2. * random - 1;

		return randomBetweenMinus1AndPlus1;
	}

	static boolean IsPointInsideCircle (double x, double y)
	{
		double distanceFromOrigin = Math.sqrt (x * x + y * y);

		boolean insideCircle = false;

		if (distanceFromOrigin < 1.)
		{
			insideCircle = true;
		}
		else
		{
			insideCircle = false;
		}

		return insideCircle;
	}

	static double InsideCircleCount (double[] xArray, double[] yArray)
	{
		double numberInsideCircle = 0.;
		int numberOfPoints = xArray.length;

		for (int counter = 0; counter < numberOfPoints; counter = counter + 1)
		{
			if (IsPointInsideCircle (xArray[counter], yArray[counter]))
			{
				numberInsideCircle = numberInsideCircle + 1;
			}
		}

		return numberInsideCircle;
	}

	static double ProbabilityToPI (double probability)
	{
		double pi = 4. * probability;
		return pi;
	}

	public static void main (String[] input)
	{
		int totalNumberOfPoints = 10000000;

		double[] xPoints = new double[totalNumberOfPoints];
		double[] yPoints = new double[totalNumberOfPoints];

		for (int pointCounter = 0; pointCounter < totalNumberOfPoints; pointCounter = pointCounter + 1)
		{
			xPoints[pointCounter] = RandomNumber();
			yPoints[pointCounter] = RandomNumber();
		}

		double pointsInsideCircle = InsideCircleCount (xPoints, yPoints);

		double probabilityOfPointsInsideCircle = pointsInsideCircle / totalNumberOfPoints;

		double estimatedPI = ProbabilityToPI (probabilityOfPointsInsideCircle);

		System.out.println ("\tNumber of Points Inside Square    : " + totalNumberOfPoints);

		System.out.println ("\tNumber of Points Inside Circle    : " + pointsInsideCircle);

		System.out.println ("\tProbability Of Points in a Circle : " + probabilityOfPointsInsideCircle);

		System.out.println ("\tEstimated PI                      : " + estimatedPI);

		System.out.println ("\tActual PI                         : " + Math.PI);
	}
}
