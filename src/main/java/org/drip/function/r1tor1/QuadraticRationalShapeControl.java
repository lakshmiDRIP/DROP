
package org.drip.function.r1tor1;

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
 * <i>QuadraticRationalShapeControl</i> implements the deterministic rational shape control functionality on
 * top of the estimator basis splines inside - [0,...,1) - Globally [x_0,...,x_1):
 * <br>
 * 			y = 1 / [1 + lambda * x * (1-x)]
 * <br>
 *		where is the normalized ordinate mapped as
 * <br>
 * 			x ==== (x - x_i-1) / (x_i - x_i-1)
 * <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function">Function</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1">R<sup>1</sup> To R<sup>1</sup></a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/MachineLearning">Machine Learning Library</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class QuadraticRationalShapeControl extends org.drip.function.definition.R1ToR1 {
	private double _dblLambda = java.lang.Double.NaN;

	/**
	 * QuadraticRationalShapeControl constructor
	 * 
	 * @param dblLambda Tension Parameter
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public QuadraticRationalShapeControl (
		final double dblLambda)
		throws java.lang.Exception
	{
		super (null);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblLambda = dblLambda))
			throw new java.lang.Exception ("QuadraticRationalShapeControl ctr: Invalid tension");
	}

	@Override public double evaluate (
		final double dblX)
		throws java.lang.Exception
	{
		return 1. / (1. + _dblLambda * dblX * (1. - dblX));
	}

	@Override public double derivative (
		final double dblX,
		final int iOrder)
		throws java.lang.Exception
	{
		if (0. == _dblLambda) return 0.;

		double dblD2BetaDX2 = -2. * _dblLambda;
		double dblDBetaDX = _dblLambda * (1. - 2. * dblX);
		double dblBeta = 1. + _dblLambda * dblX * (1. - dblX);

		if (1 == iOrder) return -1. * dblDBetaDX / (dblBeta * dblBeta);

		if (2 == iOrder)
			return (2. * dblDBetaDX * dblDBetaDX - dblBeta * dblD2BetaDX2) / (dblBeta * dblBeta * dblBeta);

		return super.derivative (dblX, iOrder);
	}

	@Override public double integrate (
		final double dblBegin,
		final double dblEnd)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBegin) || !org.drip.quant.common.NumberUtil.IsValid
			(dblEnd))
			throw new java.lang.Exception ("QuadraticRationalShapeControl::integrate => Invalid Inputs");

		double dblAlpha = java.lang.Math.sqrt (0.25 * (_dblLambda + 4.) / _dblLambda);

		return -0.5 * (java.lang.Math.log ((dblEnd - dblAlpha - 0.5) * (dblBegin + dblAlpha - 0.5) /
			(dblEnd + dblAlpha - 0.5) / (dblBegin - dblAlpha - 0.5))) / dblAlpha / _dblLambda;
	}

	/**
	 * Retrieve the shape control coefficient
	 * 
	 * @return Shape control coefficient
	 */

	public double getShapeControlCoefficient()
	{
		return _dblLambda;
	}

	public static final void main (
		final java.lang.String[] astrArgs)
		throws java.lang.Exception
	{
		QuadraticRationalShapeControl qrsc = new QuadraticRationalShapeControl (1.);

		System.out.println (qrsc.derivative (0., 2));

		System.out.println (qrsc.derivative (1., 2));
	}
}
