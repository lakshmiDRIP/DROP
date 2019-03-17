
package org.drip.numerical.estimation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>R1Estimate</i> holds the Bounded R<sup>1</sup> Numerical Estimate of a Function. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Mortici, C. (2011): Improved Asymptotic Formulas for the Gamma Function <i>Computers and
 * 				Mathematics with Applications</i> <b>61 (11)</b> 3364-3369
 * 		</li>
 * 		<li>
 * 			National Institute of Standards and Technology (2018): NIST Digital Library of Mathematical
 * 				Functions https://dlmf.nist.gov/5.11
 * 		</li>
 * 		<li>
 * 			Nemes, G. (2010): On the Coefficients of the Asymptotic Expansion of n!
 * 				https://arxiv.org/abs/1003.2907 <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Toth V. T. (2016): Programmable Calculators – The Gamma Function
 * 				http://www.rskey.org/CMS/index.php/the-library/11
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Stirling's Approximation
 * 				https://en.wikipedia.org/wiki/Stirling%27s_approximation
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/README.md">Function Numerical Estimates/Corrections/Bounds</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1Estimate
{
	private double _baseline = java.lang.Double.NaN;
	private double _lowerBound = java.lang.Double.NaN;
	private double _upperBound = java.lang.Double.NaN;

	private java.util.Map<java.lang.Integer, java.lang.Double> _orderedSeriesMap = new
		java.util.TreeMap<java.lang.Integer, java.lang.Double>();

	/**
	 * Construct a Base Line Version without Bounds
	 * 
	 * @param baseline The Base Line Numerical Estimate
	 * 
	 * @return The Base Line Version without Bounds
	 */

	public static final R1Estimate BaselineOnly (
		final double baseline)
	{
		try
		{
			return new R1Estimate (
				baseline,
				java.lang.Double.NaN,
				java.lang.Double.NaN
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * R1Estimate Constructor
	 * 
	 * @param baseline The Base Line Estimate
	 * @param lowerBound The Lower Bound
	 * @param upperBound The Upper Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1Estimate (
		final double baseline,
		final double lowerBound,
		final double upperBound)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_baseline = baseline))
		{
			throw new java.lang.Exception ("R1Estimate Constructor => Invalid Inputs");
		}

		_lowerBound = lowerBound;
		_upperBound = upperBound;
	}

	/**
	 * Retrieve the Base Line Numerical Estimate
	 * 
	 * @return The Base Line Numerical Estimate
	 */

	public double baseline()
	{
		return _baseline;
	}

	/**
	 * Retrieve the Lower Bound
	 * 
	 * @return The Lower Bound
	 */

	public double lowerBound()
	{
		return _lowerBound;
	}

	/**
	 * Retrieve the Upper Bound
	 * 
	 * @return The Upper Bound
	 */

	public double upperBound()
	{
		return _upperBound;
	}

	/**
	 * Retrieve the Higher Order Series Map
	 * 
	 * @return The Higher Order Series Map
	 */

	public java.util.Map<java.lang.Integer, java.lang.Double> orderedSeriesMap()
	{
		return _orderedSeriesMap;
	}

	/**
	 * Add the Ordered Series Map
	 * 
	 * @param orderedSeriesMap The Ordered Series Map
	 * 
	 * @return TRUE - The Ordered Series Map successfully added
	 */

	public boolean addOrderedSeriesMap (
		final java.util.Map<java.lang.Integer, java.lang.Double> orderedSeriesMap)
	{
		if (null == orderedSeriesMap)
		{
			return false;
		}

		_orderedSeriesMap = orderedSeriesMap;
		return true;
	}

	/**
	 * Retrieve the Series corresponding to the Specified Order
	 * 
	 * @param order The Series Order
	 * 
	 * @return The Series corresponding to the Specified Order
	 */

	public double orderSeries (
		final int order)
	{
		return _orderedSeriesMap.containsKey (order) ? _orderedSeriesMap.get (order) : 0.;
	}

	/**
	 * Compute the Series Cumulative
	 * 
	 * @return The Series Cumulative
	 */

	public double seriesCumulative()
	{
		double seriesCumulative = 0.;

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> orderedSeriesEntry :
			_orderedSeriesMap.entrySet())
		{
			seriesCumulative = seriesCumulative + orderedSeriesEntry.getValue();
		}

		return seriesCumulative;
	}
}
