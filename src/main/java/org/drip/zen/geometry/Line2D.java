
package org.drip.zen.geometry;

/*
 * 1) Slope Intercept Form
 * 2) Construction from slope and intercept
 * 3) Construction from 2 Points
 * 4) Construction From Slope and Point
 * 5) Accessors
 * 6) Y from X
 * 7) X From Y
 * 8) Parallel Line
 * 9) Perpendicular Line
 * 10) Intersection Between Lines
 */

public class Line2D {
	private double _m = 0.;
	private double _b = 0.;

	public Line2D (double m, double b)
	{
		_m = m;
		_b = b;
	}

	public Line2D (Point2D p1, Point2D p2)
	{
		double x1 = p1.getX();
		double x2 = p2.getX();
		double y1 = p1.getY();
		double y2 = p2.getY();

		_m = (y2 - y1) / (x2 - x1);

		_b = y2 - _m * x2;
	}

	public Line2D (double m, Point2D p2)
	{
		double x2 = p2.getX();
		double y2 = p2.getY();

		_m = m;
		_b = y2 - _m * x2;
	}

	public double slope()
	{
		return _m;
	}

	public double yIntercept()
	{
		return _b;
	}

	public void show()
	{
		System.out.println ("Slope: " + _m + "; Intercept: " + _b);
	}

	public double YFromX (double x)
	{
		double y = _m * x + _b;
		return y;
	}

	public double XFromY (double y)
	{
		double x = (y - _b) / _m;
		return x;
	}

	public Line2D parallelLineAtPoint (Point2D p)
	{
		Line2D parallelLine = new Line2D (_m, p);

		return parallelLine;
	}

	public Line2D perpendicularLineAtPoint (Point2D p)
	{
		Line2D perpendicularLine = new Line2D (-1 / _m, p);

		return perpendicularLine;
	}

	public Point2D intersection (Line2D lineOther)
	{
		double b1 = yIntercept();
		double b2 = lineOther.yIntercept();
		double m1 = slope();
		double m2 = lineOther.slope();

		double xIntersection = -1 * (b2 - b1) / (m2 - m1);
		double yIntersection = m1 * xIntersection + b1;

		Point2D pointIntersection = new Point2D (xIntersection, yIntersection);

		return pointIntersection;
	}

	public static void main (String[] args)
	{
		double mySlope = 1;
		double myIntercept = 2;

		Line2D myFirstLine = new Line2D (mySlope, myIntercept);

		myFirstLine.show();

		Point2D point1 = new Point2D (1, 3);

		Point2D point2 = new Point2D (4, 6);

		Line2D mySecondLine = new Line2D (point1, point2);

		mySecondLine.show();

		Line2D myThirdLine = new Line2D (mySlope, point2);

		myThirdLine.show();

		double x1 = 1;

		double y1 = myThirdLine.YFromX (x1);

		System.out.println ("Y From  = " + x1 + " is " + y1);

		double y2 = 1;

		double x2 = myThirdLine.XFromY (y2);

		System.out.println ("X From Y = " + y2 + " is " + x2);

		Point2D linePoint = new Point2D (6, 12);

		Line2D parallelLine = myThirdLine.parallelLineAtPoint (linePoint);

		parallelLine.show();

		Line2D perpendicularLine = myThirdLine.parallelLineAtPoint (linePoint);

		perpendicularLine.show();

		Line2D firstLine = new Line2D (new Point2D (0, 0), new Point2D (1, 1));

		Line2D secondLine = new Line2D (new Point2D (1, 1), new Point2D (2, 0));

		Point2D pIntersect = firstLine.intersection (secondLine);

		pIntersect.printCoordinate();
	}
}
