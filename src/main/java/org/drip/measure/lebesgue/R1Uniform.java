
package org.drip.measure.lebesgue;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>R1Uniform</i> implements the R<sup>1</sup> Lebesgue (i.e., Bounded Uniform) Distribution, with a
 * Uniform Distribution between a Lower and an Upper Bound.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/lebesgue">Lebesgue</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1Uniform extends org.drip.measure.continuous.R1Univariate {
	protected static final int GRID_WIDTH = 100;

	private double _dblLeftPredictorOrdinateEdge = java.lang.Double.NaN;
	private double _dblRightPredictorOrdinateEdge = java.lang.Double.NaN;

	/**
	 * Construct a R^1 Bounded Uniform Distribution
	 * 
	 * @param dblLeftPredictorOrdinateEdge The Left Predictor Ordinate Edge
	 * @param dblRightPredictorOrdinateEdge The Right Predictor Ordinate Edge
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public R1Uniform (
		final double dblLeftPredictorOrdinateEdge,
		final double dblRightPredictorOrdinateEdge)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblLeftPredictorOrdinateEdge =
			dblLeftPredictorOrdinateEdge) || !org.drip.quant.common.NumberUtil.IsValid
				(_dblRightPredictorOrdinateEdge = dblRightPredictorOrdinateEdge) ||
					_dblRightPredictorOrdinateEdge <= _dblLeftPredictorOrdinateEdge)
			throw new java.lang.Exception ("R1Uniform Constructor: Invalid Inputs");
	}

	/**
	 * Retrieve the Left Predictor Ordinate Edge
	 * 
	 * @return The Left Predictor Ordinate Edge
	 */

	public double leftEdge()
	{
		return _dblLeftPredictorOrdinateEdge;
	}

	/**
	 * Retrieve the Right Predictor Ordinate Edge
	 * 
	 * @return The Right Predictor Ordinat Edge
	 */

	public double rightEdge()
	{
		return _dblRightPredictorOrdinateEdge;
	}

	@Override public double cumulative (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception ("R1Uniform::cumulative => Invalid Inputs");

		if (dblX <= _dblLeftPredictorOrdinateEdge) return 0.;

		if (dblX >= _dblRightPredictorOrdinateEdge) return 1.;

		return (dblX - _dblLeftPredictorOrdinateEdge) / (_dblRightPredictorOrdinateEdge -
			_dblLeftPredictorOrdinateEdge);
	}

	@Override public double incremental (
		final double dblXLeft,
		final double dblXRight)
		throws java.lang.Exception
	{
		return cumulative (dblXRight) - cumulative (dblXLeft);
	}

	@Override public double invCumulative (
		final double dblY)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblY) || dblY < 0. || dblY > 1.)
			throw new java.lang.Exception ("R1Uniform::invCumulative => Invalid inputs");

	    return dblY * (_dblRightPredictorOrdinateEdge - _dblLeftPredictorOrdinateEdge) +
	    	_dblLeftPredictorOrdinateEdge;
	}

	@Override public double density (
		final double dblX)
		throws java.lang.Exception
	{
		return dblX <= _dblLeftPredictorOrdinateEdge || dblX >= _dblRightPredictorOrdinateEdge ? 0. : 1. /
			(_dblRightPredictorOrdinateEdge - _dblLeftPredictorOrdinateEdge);
	}

	@Override public double mean()
	{
	    return 0.5 * (_dblRightPredictorOrdinateEdge + _dblLeftPredictorOrdinateEdge);
	}

	@Override public double variance()
	{
	    return (_dblRightPredictorOrdinateEdge - _dblLeftPredictorOrdinateEdge) *
	    	(_dblRightPredictorOrdinateEdge - _dblLeftPredictorOrdinateEdge) / 12.;
	}

	@Override public org.drip.quant.common.Array2D histogram()
	{
		double[] adblX = new double[GRID_WIDTH];
		double[] adblY = new double[GRID_WIDTH];
		double dblWidth = (_dblRightPredictorOrdinateEdge - _dblLeftPredictorOrdinateEdge) / GRID_WIDTH;

		for (int i = 0; i < GRID_WIDTH; ++i) {
			adblY[i] = 1. / GRID_WIDTH;
			adblX[i] = _dblLeftPredictorOrdinateEdge + (i + 1) * dblWidth;
		}

		return org.drip.quant.common.Array2D.FromArray (adblX, adblY);
	}
}
