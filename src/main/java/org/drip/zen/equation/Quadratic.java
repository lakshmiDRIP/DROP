
package org.drip.zen.equation;

/*
 * 1) a, b, c
 * 2) Constructor
 * 3) Retrieve a, b, c
 * 4) Find Roots
 * 5) Introduce concept of NULL
 */

public class Quadratic {
	private double _a;
	private double _b;
	private double _c;

	public Quadratic (double a, double b, double c)
	{
		_a = a;
		_b = b;
		_c = c;
	}

	public double a()
	{
		return _a;
	}

	public double b()
	{
		return _b;
	}

	public double c()
	{
		return _c;
	}

	public double[] findRoots()
	{
		double bSquaredMinus4ac = _b * _b - 4 * _a * _c;

		if (bSquaredMinus4ac < 0) return null;

		double[] roots = new double[2];
		roots[0] = (-1. * _b + Math.sqrt (bSquaredMinus4ac)) / (2 * _a);
		roots[1] = (-1. * _b - Math.sqrt (bSquaredMinus4ac)) / (2 * _a);
		return roots;
	}
}
