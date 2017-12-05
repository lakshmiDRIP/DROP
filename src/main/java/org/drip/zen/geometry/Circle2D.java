
package org.drip.zen.geometry;

import org.drip.zen.equation.Quadratic;

/*
 * Circle2D
 * 
 * 1) Instance Fields - center and radius
 * 2) Get the fields
 * 3) Calculate Area
 * 4) Calculate Perimeter
 * 5) Closest Distance to a Point
 * 6) Do they Overlap
 * 7) Do they Touch
 * 8) Distance between a Line and the Circle
 * 9) Does Line intersect the Circle
 * 10) Point inside Circle
 * 11) Intersection Between Circle and Line
 * 12) Concept of Intersection - satisfy equations of both line AND circle
 * 13) Derive the Quadratic Equations
 * 14) Formulate them and encode them
 * 15) Solve for them
 * 16) Translate Circles - 20 Match 2016 #1
 * 17) Angle Extended at Origin - 20 March 2016 #7
 * 17) Tangents to Circle - Formulation + Conception - 20 March 2016 #8
 * 18) Tangential Points - Implementation - 20 March 2016 #9
 * 19) Bring it together - 20 March 2016 #10
 */

public class Circle2D {
	private Point2D _center;
	private double _radius;

	public Circle2D (Point2D center, double radius)
	{
		_center = center;
		_radius = radius;
	}

	public Point2D getCenter()
	{
		return _center;
	}

	public double getRadius()
	{
		return _radius;
	}

	public double area()
	{
		double circleArea = Math.PI * _radius * _radius;
		return circleArea;
	}

	public double perimeter()
	{
		double circumference = 2. * Math.PI * _radius;
		return circumference;
	}

	public boolean overlap (Circle2D anotherCircle)
	{
		double centerX1 = _center.getX();
		double centerY1 = _center.getY();
		double centerX2 = anotherCircle.getCenter().getX();
		double centerY2 = anotherCircle.getCenter().getY();
		double radius1 = getRadius();
		double radius2 = anotherCircle.getRadius();

		double distanceBetweenCenters = Math.sqrt ((centerX1 - centerX2) * (centerX1 - centerX2) + (centerY1 - centerY2) * (centerY1 - centerY2));

		boolean circlesIntersect = false;

		if (distanceBetweenCenters < radius1 + radius2)
			circlesIntersect = true;
		else
			circlesIntersect = false;

		return circlesIntersect;
	}

	public boolean touch (Circle2D anotherCircle)
	{
		double centerX1 = _center.getX();
		double centerY1 = _center.getY();
		double centerX2 = anotherCircle.getCenter().getX();
		double centerY2 = anotherCircle.getCenter().getY();
		double radius1 = getRadius();
		double radius2 = anotherCircle.getRadius();

		double distanceBetweenCenters = Math.sqrt ((centerX1 - centerX2) * (centerX1 - centerX2) + (centerY1 - centerY2) * (centerY1 - centerY2));

		boolean circlesTouch = false;

		if (distanceBetweenCenters == radius1 + radius2)
			circlesTouch = true;
		else
			circlesTouch = false;

		return circlesTouch;
	}

	public double distanceToLine (Line2D line)
	{
		Line2D perpendicularLine = line.perpendicularLineAtPoint (getCenter());

		Point2D intersectingPoint = line.intersection (perpendicularLine);

		double distance = intersectingPoint.distanceBetweenPoints (getCenter());

		return distance;
	}

	public boolean linePassesThrough (Line2D line)
	{
		double distanceBetweenLineAndCenter = distanceToLine (line);

		boolean linePassesThroughTheCircle = false;

		if (distanceBetweenLineAndCenter < getRadius())
			linePassesThroughTheCircle = true;
		else
			linePassesThroughTheCircle = false;

		return linePassesThroughTheCircle;
	}

	public boolean inside (Point2D p)
	{
		boolean inside = false;

		double distanceFromCenter = p.distanceBetweenPoints (getCenter());

		if (distanceFromCenter < getRadius())
			inside = true;
		else
			inside = false;

		return inside;
	}

	public Point2D[] intersections (Line2D l)
	{
		double x1 = _center.getX();
		double y1 = _center.getY();
		double r = _radius;

		double m = l.slope();
		double b = l.yIntercept();

		double quadraticA = 1 + m * m;
		double quadraticB = -2 * x1 + 2 * m * b - 2 * m * y1;
		double quadraticC = x1 * x1 + b * b - 2 * b * y1 + y1 * y1 - r * r;

		Quadratic quad = new Quadratic (quadraticA, quadraticB, quadraticC);

		double[] intersectionX = quad.findRoots();

		if (null == intersectionX) return null;

		Point2D[] intersectingPoints = new Point2D[2];
		intersectingPoints[0] = new Point2D (intersectionX[0], l.YFromX (intersectionX[0]));
		intersectingPoints[1] = new Point2D (intersectionX[1], l.YFromX (intersectionX[1]));
		return intersectingPoints;
	}

	/*
	 * Added 20 March 2016
	 */

	public boolean translate (double xAmount, double yAmount)
	{
		return _center.translate (xAmount, yAmount);
	}

	public double angleAtOrigin()
	{
		double distanceFromCenterToOrigin = _center.distanceFromOrigin();

		double originAngle = Math.asin (_radius / distanceFromCenterToOrigin);

		return originAngle;
	}

	public Point2D[] tangentsFromOrigin()
	{
		double centerAngleAtOrigin = _center.angleAtOrigin() * Math.PI / 180.;

		double circleAngleAtOrigin = angleAtOrigin();

		double tangentDistanceFromOrigin = _radius / Math.sin (centerAngleAtOrigin);

		double angleAbove = centerAngleAtOrigin + circleAngleAtOrigin;
		double angleBelow = centerAngleAtOrigin - circleAngleAtOrigin;

		Point2D[] tangentPoints = new Point2D[2];

		tangentPoints[0] = Point2D.MakeFromPolar (tangentDistanceFromOrigin, angleAbove);

		tangentPoints[1] = Point2D.MakeFromPolar (tangentDistanceFromOrigin, angleBelow);

		return tangentPoints;
	}

	/*
	 * Done Adding 20 March 2016
	 */

	public static void main (String[] args)
	{
		Point2D myCenter = new Point2D (0, 0);
		double myRadius = 1;
		Circle2D myCircle = new Circle2D (myCenter, myRadius);

		Line2D myLine = new Line2D (1, 0);

		Point2D[] circleLineIntersections = myCircle.intersections (myLine);

		circleLineIntersections[0].printCoordinate();

		circleLineIntersections[1].printCoordinate();

		/*
		 * Added 20 March 2016
		 */

		Point2D anotherCenter = new Point2D (5, 5);
		double anotherRadius = 1;
		Circle2D anotherCircle = new Circle2D (anotherCenter, anotherRadius);

		Point2D[] tangentialPoints = anotherCircle.tangentsFromOrigin();

		System.out.println();

		System.out.println ("\tCenter Angle At Origin: " + anotherCircle._center.angleAtOrigin());

		System.out.println ("\tCircle Angle At Origin: " + anotherCircle.angleAtOrigin());

		System.out.println();

		tangentialPoints[0].printCoordinate();

		tangentialPoints[1].printCoordinate();

		System.out.println();

		tangentialPoints[0].printPolarCoordinate();

		tangentialPoints[1].printPolarCoordinate();

		/*
		 * Done Adding 20 March 2016
		 */
	}
}
