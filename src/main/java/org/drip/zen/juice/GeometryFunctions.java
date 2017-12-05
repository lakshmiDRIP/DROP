
package org.drip.zen.juice;

/*
 * 1) Area of a Square
 * 2) Perimeter of a Square
 * 3) Area of a Rectangle
 * 4) Perimeter of a Rectangle
 * 5) Area of a Circle
 * 6) Circumference of a Circle
 * 7) Distance of a Point to Origin
 * 8) Distance between 2 Points
 * 9) Sum to first n Numbers
 * 10) Sum from n to m Numbers
 */

public class GeometryFunctions {

	public static final double AreaOfSquare (double length)
	{
		double area = length * length;
		return area;
	}

	public static final double PerimeterOfSquare (double length)
	{
		double perimeter = 2 * length;
		return perimeter;
	}

	public static final double AreaOfRectangle (double length, double breadth)
	{
		double area = length * breadth;
		return area;
	}

	public static final double PerimeterOfRectangle (double length, double breadth)
	{
		double perimeter = length + breadth;
		return perimeter;
	}

	public static final double AreaOfCircle (double radius)
	{
		double area = Math.PI * radius * radius;
		return area;
	}

	public static final double PerimeterOfCircle (double radius)
	{
		double perimeter = 2. * Math.PI * radius;
		return perimeter;
	}

	public static final double DistanceToOrigin (double x, double y)
	{
		double distance = Math.sqrt (x * x + y * y);
		return distance;
	}

	public static final double DistanceBetweenPoints (double x1, double y1, double x2, double y2)
	{
		double distance = Math.sqrt ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return distance;
	}

	public static final double SumToN (double n)
	{
		double sum = n * (n + 1) / 2;
		return sum;
	}

	public static final double SumFromNToM (double n, double m)
	{
		double sumToN = SumToN (n);
		double sumToM = SumToN (m);
		return sumToM - sumToN;
	}

	public static void main (String[] args)
	{
		double squareLength = 6.46;

		double squareArea = AreaOfSquare (squareLength);
		double squarePerimeter = PerimeterOfSquare (squareLength);

		System.out.println ("Square Length: " + squareLength);
		System.out.println ("\tArea: " + squareArea + "; Perimeter: " + squarePerimeter);

		double rectangleLength = 6.46;
		double rectangleBreadth = 6.46;

		double rectangleArea = AreaOfRectangle (rectangleLength, rectangleBreadth);
		double rectanglePerimeter = PerimeterOfRectangle (rectangleLength, rectangleBreadth);

		System.out.println ("Rectangle Length: " + rectangleLength + "; Breadth: " + rectangleBreadth);
		System.out.println ("\tArea: " + rectangleArea + "; Perimeter: " + rectanglePerimeter);

		double circleRadius = 6.46;

		double circleArea = AreaOfCircle (circleRadius);
		double circlePerimeter = PerimeterOfCircle (circleRadius);

		System.out.println ("Circle Radius: " + circleRadius);
		System.out.println ("\tArea: " + circleArea + "; Perimeter: " + circlePerimeter);

		double xCoordinate = 3;
		double yCoordinate = 4;

		double distanceFromOrigin = DistanceToOrigin (xCoordinate, yCoordinate);

		System.out.println ("Point: [" + xCoordinate + ", " + yCoordinate + "]");
		System.out.println ("\tDistance From Origin: " + distanceFromOrigin);

		double xCoordinate1 = 3;
		double yCoordinate1 = 4;
		double xCoordinate2 = 27;
		double yCoordinate2 = 11;

		double distance12 = DistanceBetweenPoints (xCoordinate1, yCoordinate1, xCoordinate2, yCoordinate2);

		System.out.println ("Points: [" + xCoordinate1 + ", " + yCoordinate1 + "] AND [" + xCoordinate2 + ", " + yCoordinate2 + "]");
		System.out.println ("\tDistance Between Points: " + distance12);

		double number = 5;

		double sumOfFirstN = SumToN (number);

		System.out.println ("Number: " + number);
		System.out.println ("\tSum of First Numbers: " + sumOfFirstN);

		double numberN = 5;
		double numberM = 10;

		double sumBetweenNumbers = SumFromNToM (numberN, numberM);

		System.out.println ("Numbers: " + numberN + " => " + numberM);
		System.out.println ("\tSum Between Numbers: " + sumBetweenNumbers);
	}
}
