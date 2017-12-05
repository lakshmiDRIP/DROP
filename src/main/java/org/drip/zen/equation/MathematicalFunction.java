
package org.drip.zen.equation;

public class MathematicalFunction {

	public static double QuadraticExpression (double a, double b, double c, double x)
	{
		double expressionValue = a * x * x + b * x + c;
		return expressionValue;
	}

	public static double TrigonometricExpression (double sum, double x)
	{
		double functionValue = Math.cos (x) + Math.sin (x) - sum;
		return functionValue;
	}
}
