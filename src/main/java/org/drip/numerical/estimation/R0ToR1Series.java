
package org.drip.numerical.estimation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>R0ToR1Series</i> generates a Series of Weighted Numerical R<sup>0</sup> To R<sup>1</sup> Terms. The
 * References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/README.md">Function Numerical Estimates/Corrections/Bounds</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R0ToR1Series extends org.drip.numerical.estimation.RxToR1Series
{
	private boolean _cumulative = false;
	private org.drip.numerical.estimation.R0ToR1SeriesTerm _r0Tor1SeriesTerm = null;

	/**
	 * R0ToR1Series Constructor
	 * 
	 * @param r0Tor1SeriesTerm R<sup>0</sup> To R<sup>1</sup> Series Term
	 * @param proportional TRUE - The Series Term is Proportional
	 * @param termWeightMap Error Term Weight Map
	 * @param cumulative TRUE - The Series Term is Cumulative
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R0ToR1Series (
		final org.drip.numerical.estimation.R0ToR1SeriesTerm r0Tor1SeriesTerm,
		final boolean proportional,
		final java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap,
		final boolean cumulative)
		throws java.lang.Exception
	{
		super (
			proportional,
			termWeightMap
		);

		if (null == (_r0Tor1SeriesTerm = r0Tor1SeriesTerm))
		{
			throw new java.lang.Exception ("R0ToR1Series Constructor => Invalid Inputs");
		}

		_cumulative = cumulative;
	}

	/**
	 * Retrieve the R<sup>0</sup> To R<sup>1</sup> Series Term
	 * 
	 * @return The R<sup>0</sup> To R<sup>1</sup> Series Term
	 */

	public org.drip.numerical.estimation.R0ToR1SeriesTerm r0Tor1SeriesTerm()
	{
		return _r0Tor1SeriesTerm;
	}

	/**
	 * Indicate if the Series Term is Incremental or Cumulative
	 * 
	 * @return TRUE - The Series Term is Cumulative
	 */

	public boolean cumulative()
	{
		return _cumulative;
	}

	/**
	 * Generate the Series Expansion using the R<sup>0</sup> To R<sup>1</sup> Term
	 * 
	 * @param zeroOrder The Zero Order Estimate
	 * 
	 * @return The Series Expansion
	 */

	public java.util.TreeMap<java.lang.Integer, java.lang.Double> generate (
		final double zeroOrder)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (zeroOrder))
		{
			return null;
		}

		java.util.TreeMap<java.lang.Integer, java.lang.Double> seriesExpansionMap = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = termWeightMap();

		if (null == termWeightMap || 0 == termWeightMap.size())
		{
			return seriesExpansionMap;
		}

		double scale = proportional() ? zeroOrder : 1.;

		double seriesValue = 0.;

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> termWeightEntry :
			termWeightMap.entrySet())
		{
			int order = termWeightEntry.getKey();

			try
			{
				double orderSeriesValue = scale * termWeightEntry.getValue() * _r0Tor1SeriesTerm.value
					(order);

				seriesExpansionMap.put (
					order,
					_cumulative ? (seriesValue = seriesValue + orderSeriesValue) : orderSeriesValue
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return seriesExpansionMap;
	}

	@Override public double evaluate (
		final double x)
		throws java.lang.Exception
	{
		java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = termWeightMap();

		if (null == termWeightMap || 0 == termWeightMap.size())
		{
			return 0.;
		}

		double scale = proportional() ? 0. : 1.;

		double value = 0.;
		double seriesValue = 0.;

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> termWeightEntry :
			termWeightMap.entrySet())
		{
			int order = termWeightEntry.getKey();

			double orderSeriesValue = scale * termWeightEntry.getValue() * _r0Tor1SeriesTerm.value
				(order);

			value = value + (_cumulative ? (seriesValue = seriesValue + orderSeriesValue) :
				orderSeriesValue);
		}

		return value;
	}

	@Override public double derivative (
		final double x,
		final int order)
		throws java.lang.Exception
	{
		return 0.;
	}
}
