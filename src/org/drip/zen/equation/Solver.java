
package org.drip.zen.equation;

/*
 * 1) x^2 - 5*x - 50 = 0
 * 2) Factorize and Find out Roots
 * 3) Quadratic Equation and find out Roots
 * 4) Walk Through and Illustrate the Bisection Root Search Algorithm
 * 5) Code Sketch
 * 6) Execute and Test Results
 * 7) Function Class f(x) = y
 * 8) cos x + sin x = 1.2
 * 9) Execute and Test Results
 * 10) Secant Method
 */

public class Solver {

	public static double PositiveQuadraticSolution (double a, double b, double c)
	{
		double xLeft = 0.;
		double xRight = 100.;
		double closenessMatch = 0.0000001;
		double xRootGuess = 0.5 * (xLeft + xRight);

		double functionValue = MathematicalFunction.QuadraticExpression (a, b, c, xRootGuess);

		while (Math.abs (functionValue) > closenessMatch)
		{
			if (functionValue > 0.)
			{
				xRight = xRootGuess;
			} else if (functionValue < 0.)
			{
				xLeft = xRootGuess;
			}

			xRootGuess = 0.5 * (xLeft + xRight);

			functionValue = MathematicalFunction.QuadraticExpression (a, b, c, xRootGuess);
		}

		return xRootGuess;
	}

	public static double NegativeQuadraticSolution (double a, double b, double c)
	{
		double xLeft = -100.;
		double xRight = 0.;
		double closenessMatch = 0.0000001;
		double xRootGuess = 0.5 * (xLeft + xRight);

		double functionValue = MathematicalFunction.QuadraticExpression (a, b, c, xRootGuess);

		while (Math.abs (functionValue) > closenessMatch)
		{
			if (functionValue < 0.)
			{
				xRight = xRootGuess;
			} else if (functionValue > 0.)
			{
				xLeft = xRootGuess;
			}

			xRootGuess = 0.5 * (xLeft + xRight);

			functionValue = MathematicalFunction.QuadraticExpression (a, b, c, xRootGuess);
		}

		return xRootGuess;
	}

	public static double PositiveTrigonometricSolution (double sum)
	{
		double xLeft = 0.;
		double xRight = 0.25 * Math.PI;
		double closenessMatch = 0.0000001;
		double xRootGuess = 0.5 * (xLeft + xRight);

		double functionValue = MathematicalFunction.TrigonometricExpression (sum, xRootGuess);

		while (Math.abs (functionValue) > closenessMatch)
		{
			if (functionValue > 0.)
			{
				xRight = xRootGuess;
			} else if (functionValue < 0.)
			{
				xLeft = xRootGuess;
			}

			xRootGuess = 0.5 * (xLeft + xRight);

			functionValue = MathematicalFunction.TrigonometricExpression (sum, xRootGuess);
		}

		return xRootGuess;
	}

	public static void main (String[] input)
	{
		double positiveQuadraticRoot = PositiveQuadraticSolution (1, -5, -50);

		System.out.println ("Positive Quadratic Root " + positiveQuadraticRoot);

		double negativeQuadraticRoot = NegativeQuadraticSolution (1, -5, -50);

		System.out.println ("Negative Quadratic Root " + negativeQuadraticRoot);

		double positiveTrigonometricRoot = PositiveTrigonometricSolution (1.2);

		System.out.println ("Positive Trigonometric Root (Radians) " + positiveTrigonometricRoot);

		System.out.println ("Positive Trigonometric Root (Degrees) " + positiveTrigonometricRoot * 180. / Math.PI);
	}
}
