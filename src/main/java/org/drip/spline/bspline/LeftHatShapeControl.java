
package org.drip.spline.bspline;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * <i>LeftHatShapeControl</i> implements the BasisHatShapeControl interface for the left hat basis set as
 * laid out in the basic framework outlined in Koch and Lyche (1989), Koch and Lyche (1993), and Kvasov
 * (2000) Papers.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline">Spaces</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline">B Spline</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/SplineBuilder">Spline Builder Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LeftHatShapeControl extends org.drip.spline.bspline.BasisHatShapeControl {

	/**
	 * LeftHatShapeControl constructor
	 * 
	 * @param dblLeftPredictorOrdinate The Left Predictor Ordinate
	 * @param dblRightPredictorOrdinate The Right Predictor Ordinate
	 * @param strShapeControlType Type of the Shape Controller to be used - NONE, LINEAR/QUADRATIC Rational
	 * @param dblTension Tension of the Tension Hat Function
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public LeftHatShapeControl (
		final double dblLeftPredictorOrdinate,
		final double dblRightPredictorOrdinate,
		final java.lang.String strShapeControlType,
		final double dblTension)
		throws java.lang.Exception
	{
		super (dblLeftPredictorOrdinate, dblRightPredictorOrdinate, strShapeControlType, dblTension);
	}

	@Override public double evaluate (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!in (dblPredictorOrdinate)) return 0.;

		double dblWidth = right() - left();

		double dblScale = 1. / (dblWidth * (6. + 6. * tension() * dblWidth + 2. * tension() * dblWidth *
			dblWidth));

		if (SHAPE_CONTROL_RATIONAL_LINEAR.equalsIgnoreCase (shapeControlType()))
			return dblScale / (1. + tension() * (right() - dblPredictorOrdinate));

		if (SHAPE_CONTROL_RATIONAL_QUADRATIC.equalsIgnoreCase (shapeControlType()))
			return dblScale / (1. + tension() * (right() - dblPredictorOrdinate) * (dblPredictorOrdinate -
				left()) / dblWidth);

		return (java.lang.Math.exp (-tension() * (right() - dblPredictorOrdinate))) / (dblWidth * (6. + 6. *
			tension() * dblWidth + tension() * dblWidth * dblWidth));
	}

	@Override public double derivative (
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (0 >= iOrder) throw new java.lang.Exception ("LeftHatShapeControl::derivative => Invalid Inputs");

		if (!in (dblPredictorOrdinate) || 0. == tension()) return 0.;

		double dblWidth = right() - left();

		double dblScale = 1. / (dblWidth * (6. + 6. * tension() * dblWidth + 2. * tension() * dblWidth *
			dblWidth));

		if (SHAPE_CONTROL_RATIONAL_LINEAR.equalsIgnoreCase (shapeControlType()))
			return dblScale * org.drip.quant.common.NumberUtil.Factorial (iOrder) * java.lang.Math.pow
				(tension(), iOrder) * java.lang.Math.pow (1. + tension() * (right() - dblPredictorOrdinate),
					-iOrder - 1);

		if (SHAPE_CONTROL_RATIONAL_EXPONENTIAL.equalsIgnoreCase (shapeControlType()))
			return (java.lang.Math.pow (tension(), iOrder) * java.lang.Math.exp (-tension() * (right() -
				dblPredictorOrdinate))) / (dblWidth * (6. + 6. * tension() * dblWidth + tension() * dblWidth
					* dblWidth));

		return super.derivative (dblPredictorOrdinate, iOrder);
	}

	@Override public double integrate (
		final double dblBegin,
		final double dblEnd)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBegin) || !org.drip.quant.common.NumberUtil.IsValid
			(dblEnd))
			throw new java.lang.Exception ("LeftHatShapeControl::integrate => Invalid Inputs");

		double dblBoundedBegin = org.drip.quant.common.NumberUtil.Bound (dblBegin, left(), right());

		double dblBoundedEnd = org.drip.quant.common.NumberUtil.Bound (dblEnd, left(), right());

		if (dblBoundedBegin >= dblBoundedEnd) return 0.;

		if (0. == tension()) return dblBoundedEnd - dblBoundedBegin;

		double dblWidth = right() - left();

		if (SHAPE_CONTROL_RATIONAL_LINEAR.equalsIgnoreCase (shapeControlType()))
			return (java.lang.Math.log ((1. + tension() * (right() - dblBoundedBegin)) / (1. + tension() *
				(right() - dblBoundedEnd)))) / tension() / (dblWidth * (6. + 6. * tension() * dblWidth + 2. *
					tension() * dblWidth * dblWidth));

		if (SHAPE_CONTROL_RATIONAL_EXPONENTIAL.equalsIgnoreCase (shapeControlType()))
			return (java.lang.Math.exp (tension() * (dblBoundedEnd - right())) - java.lang.Math.exp
				(tension() * (dblBoundedBegin - right()))) / tension() / (dblWidth * (6. + 6. * tension() *
					dblWidth + tension() * dblWidth * dblWidth));

		return super.integrate (dblBoundedBegin, dblBoundedEnd);
	}

	@Override public double normalizer()
		throws java.lang.Exception
	{
		double dblWidth = right() - left();

		if (0. == tension()) return dblWidth;

		if (SHAPE_CONTROL_RATIONAL_LINEAR.equalsIgnoreCase (shapeControlType()))
			return (java.lang.Math.log ((1. + tension() * dblWidth))) / tension() / (dblWidth * (6. + 6. *
				tension() * dblWidth + 2. * tension() * dblWidth * dblWidth));

		if (SHAPE_CONTROL_RATIONAL_EXPONENTIAL.equalsIgnoreCase (shapeControlType()))
			return (1. - java.lang.Math.exp (tension() * dblWidth)) / tension() / (dblWidth * (6. + 6. *
				tension() * dblWidth + tension() * dblWidth * dblWidth));

		return super.integrate (left(), right());
	}
}
