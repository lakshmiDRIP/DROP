
package org.drip.measure.gaussian;

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
 * <i>R1UnivariateNormal</i> implements the Univariate R<sup>1</sup> Normal Distribution. It implements the
 * Incremental, the Cumulative, and the Inverse Cumulative Distribution Densities.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/gaussian">Gaussian</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1UnivariateNormal extends org.drip.measure.continuous.R1Univariate {
	private double _dblMean = java.lang.Double.NaN;
	private double _dblSigma = java.lang.Double.NaN;

	/**
	 * Generate a N (0, 1) distribution
	 * 
	 * @return The N (0, 1) distribution
	 */

	public static final org.drip.measure.gaussian.R1UnivariateNormal Standard()
	{
		try {
			return new R1UnivariateNormal (0., 1.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a R1 Normal/Gaussian Distribution
	 * 
	 * @param dblMean Mean of the Distribution
	 * @param dblSigma Sigma of the Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public R1UnivariateNormal (
		final double dblMean,
		final double dblSigma)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblMean = dblMean) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblSigma = dblSigma) || 0. > _dblSigma)
			throw new java.lang.Exception ("R1UnivariateNormal Constructor: Invalid Inputs");
	}

	@Override public double cumulative (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception ("R1UnivariateNormal::cumulative => Invalid Inputs");

		if (0. == _dblSigma) return dblX >= _dblMean ? 1. : 0.;

		return org.drip.measure.gaussian.NormalQuadrature.CDF ((dblX - _dblMean) / _dblSigma);
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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblY) || 0. == _dblSigma)
			throw new java.lang.Exception ("R1UnivariateNormal::invCumulative => Cannot calculate");

	    return org.drip.measure.gaussian.NormalQuadrature.InverseCDF (dblY) * _dblSigma + _dblMean;
	}

	@Override public double density (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception ("R1UnivariateNormal::density => Invalid Inputs");

		if (0. == _dblSigma) return dblX == _dblMean ? 1. : 0.;

		double dblMeanShift = (dblX - _dblMean) / _dblSigma;

		return java.lang.Math.exp (-0.5 * dblMeanShift * dblMeanShift);
	}

	@Override public double mean()
	{
	    return _dblMean;
	}

	@Override public double variance()
	{
	    return _dblSigma * _dblSigma;
	}

	@Override public org.drip.quant.common.Array2D histogram()
	{
		return null;
	}

	@Override public double random()
	{
		try
		{
			return invCumulative (java.lang.Math.random());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return java.lang.Double.NaN;
	}

	/**
	 * Compute the Error Function Around an Absolute Width around the Mean
	 * 
	 * @param dblX The Width
	 * 
	 * @return The Error Function Around an Absolute Width around the Mean
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double errorFunction (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception ("R1UnivariateNormal::errorFunction => Invalid Inputs");

		double dblWidth = java.lang.Math.abs (dblX);

		return cumulative (_dblMean + dblWidth) - cumulative (_dblMean - dblWidth);
	}

	/**
	 * Compute the Confidence given the Width around the Mean
	 * 
	 * @param dblWidth The Width
	 * 
	 * @return The Error Function Around an Absolute Width around the Mean
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double confidence (
		final double dblWidth)
		throws java.lang.Exception
	{
		return errorFunction (dblWidth);
	}

	/**
	 * Compute the Width around the Mean given the Confidence Level
	 * 
	 * @param dblConfidence The Confidence Level
	 * 
	 * @return The Width around the Mean given the Confidence Level
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double confidenceInterval (
		final double dblConfidence)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblConfidence) || 0. >= dblConfidence || 1. <=
			dblConfidence)
			throw new java.lang.Exception ("R1UnivariateNormal::confidenceInterval => Invalid Inputs");

		return invCumulative (0.5 * (1. + dblConfidence));
	}
}
