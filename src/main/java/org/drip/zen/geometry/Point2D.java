
package org.drip.zen.geometry;

/*
 * 1) Motivation behind Class - Structure and Function
 * 2) x/y fields and printing them
 * 3) Invocation of the Class - Creation of the Class Instance
 * 4) Adding a method - set to Origin
 * 5) Add the Constructor with fields
 * 6) Member Fields with an underscore
 * 7) Distance from Origin
 * 8) Angle with X axis at Origin
 * 9) Degrees to Radians Conversion
 * 10) Quadrant
 * 11) Degrees to Radian with the Qudrant Assignment
 * 12) Distance From Another Point
 * 13) Translate the Points - 20 March 2016 #2
 * 14) Polar Coordinate Representation - 20 March 2016 #3
 * 15) Sketch the Problem Solution - 20 March 2016 #4
 * 16) Point from Polar Coordinates - 20 March 2016 #5
 * 17) Introducing the static Constructor - 20 March 2016 #6
 */

public class Point2D {
	private double _x = -1.;
	private double _y = -1.;

	/*
	 * Added 20 March 2016
	 */

	public static Point2D MakeFromPolar (double r, double angle)
	{
		double x = r * Math.cos (angle);

		double y = r * Math.sin (angle);

		Point2D p = new Point2D (x, y);

		return p;
	}

	/*
	 * Done Adding 20 March 2016
	 */

	Point2D (double x, double y)
	{
		_x = x;
		_y = y;
	}

	public double getX()
	{
		return _x;
	}

	public double getY()
	{
		return _y;
	}

	void printCoordinate()
	{
		System.out.println ("\t[" + _x + ", " + _y + "]");
	}

	void resetToOrigin()
	{
		_x = 0.;
		_y = 0.;
	}

	double distanceFromOrigin()
	{
		double distance = Math.sqrt (_x * _x + _y * _y);

		return distance;
	}

	double distanceBetweenPoints (Point2D anotherPoint)
	{
		double x1 = _x;
		double y1 = _y;
		double x2 = anotherPoint._x;
		double y2 = anotherPoint._y;

		double distance = Math.sqrt ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));

		return distance;
	}

	public double angleAtOrigin()
	{
		double adjustedAngleAtOrigin = 0.;
		double angleAtOriginTan = _y / _x;

		double unadjustedAngleAtOrigin = Math.atan (angleAtOriginTan);

		if (_x > 0 && _y > 0)
			adjustedAngleAtOrigin = unadjustedAngleAtOrigin;
		else if (_x < 0 && _y > 0)
			adjustedAngleAtOrigin = unadjustedAngleAtOrigin + Math.PI;
		else if (_x < 0 && _y < 0)
			adjustedAngleAtOrigin = unadjustedAngleAtOrigin + Math.PI;
		else if (_x > 0 && _y < 0)
			adjustedAngleAtOrigin = unadjustedAngleAtOrigin + 2. * Math.PI;
		else if (_x > 0 && _y < 0)
			adjustedAngleAtOrigin = 0.;

		return adjustedAngleAtOrigin * 180. / Math.PI;
	}

	String quadrant()
	{
		String region;

		if (_x > 0 && _y > 0)
			region = "UPPER RIGHT";
		else if (_x < 0 && _y > 0)
			region = "UPPER LEFT";
		else if (_x < 0 && _y < 0)
			region = "LOWER LEFT";
		else if (_x > 0 && _y < 0)
			region = "LOWER RIGHT";
		else
			region = "ORIGIN";

		return region;
	}

	/*
	 * Added 20 March 2016
	 */

	public boolean translate (double xShift, double yShift)
	{
		_x = _x + xShift;
		_y = _y + yShift;
		return true;
	}

	void printPolarCoordinate()
	{
		System.out.println ("\t[" + distanceFromOrigin() + ", " + angleAtOrigin() + "]");
	}

	/*
	 * Done Adding On 20 March 2016
	 */

	public static void main (String[] args)
	{
		Point2D myPoint = new Point2D (2., 3.);

		myPoint.printCoordinate();

		myPoint._x = 1;
		myPoint._y = 1;

		myPoint.printCoordinate();

		myPoint.resetToOrigin();

		myPoint.printCoordinate();

		System.out.println ("\tDistance From Origin: " + myPoint.distanceFromOrigin());

		Point2D secondPoint = new Point2D (3., 4.);

		System.out.println ("\tDistance Between Points: " + myPoint.distanceBetweenPoints (secondPoint));

		myPoint._x = 2;
		myPoint._y = -2;

		System.out.println ("\tAngle at Origin: " + myPoint.angleAtOrigin());

		System.out.println ("\tQuadrant: " + myPoint.quadrant());
	}
}
