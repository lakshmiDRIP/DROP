
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
 * <i>R1ToR1Series</i> holds the R<sup>1</sup> To R<sup>1</sup> Expansion Terms in the Ordered Series of the
 * Numerical Estimate for a Function. The References are:
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

public class R1ToR1Series extends org.drip.numerical.estimation.RxToR1Series
{
	private org.drip.numerical.estimation.R1ToR1SeriesTerm _r1ToR1SeriesTerm = null;

	/**
	 * R1ToR1Series Constructor
	 * 
	 * @param r1ToR1SeriesTerm R<sup>1</sup> To R<sup>1</sup> Series Expansion Term
	 * @param proportional TRUE - The Expansion Term is Proportional
	 * @param termWeightMap Error Term Weight Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1ToR1Series (
		final org.drip.numerical.estimation.R1ToR1SeriesTerm r1ToR1SeriesTerm,
		final boolean proportional,
		final java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap)
		throws java.lang.Exception
	{
		super (
			proportional,
			termWeightMap
		);

		if (null == (_r1ToR1SeriesTerm = r1ToR1SeriesTerm))
		{
			throw new java.lang.Exception ("R1ToR1Series Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the R<sup>1</sup> To R<sup>1</sup> Series Expansion Term
	 * 
	 * @return The R<sup>1</sup> To R<sup>1</sup> Series Expansion Term
	 */

	public org.drip.numerical.estimation.R1ToR1SeriesTerm r1ToR1SeriesTerm()
	{
		return _r1ToR1SeriesTerm;
	}

	/**
	 * Generate the R<sup>1</sup> To R<sup>1</sup> Series Expansion using the Term
	 * 
	 * @param zeroOrder The Zero Order Estimate
	 * @param x X
	 * 
	 * @return The R<sup>1</sup> To R<sup>1</sup> Series Expansion
	 */

	public java.util.TreeMap<java.lang.Integer, java.lang.Double> generate (
		final double zeroOrder, 
		final double x)
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

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> termWeightEntry :
			termWeightMap.entrySet())
		{
			int order = termWeightEntry.getKey();

			try
			{
				seriesExpansionMap.put (
					order,
					scale * termWeightEntry.getValue() * _r1ToR1SeriesTerm.value (
						order,
						x
					)
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

	/**
	 * Compute the Cumulative Series Value
	 * 
	 * @param zeroOrder The Zero Order Estimate
	 * @param x X
	 * 
	 * @return The Cumulative Series Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double cumulative (
		final double zeroOrder, 
		final double x)
		throws java.lang.Exception
	{
		java.util.TreeMap<java.lang.Integer, java.lang.Double> seriesMap = generate (
			zeroOrder, 
			x
		);

		if (null == seriesMap)
		{
			throw new java.lang.Exception ("R1ToR1Series::cumulative => Invalid Inputs");
		}

		double cumulative = 0.;

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> seriesEntry : seriesMap.entrySet())
		{
			cumulative = cumulative + seriesEntry.getValue();
		}

		return cumulative;
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

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> termWeightEntry :
			termWeightMap.entrySet())
		{
			value = value + scale * termWeightEntry.getValue() * _r1ToR1SeriesTerm.value (
				termWeightEntry.getKey(),
				x
			);
		}

		return value;
	}

	@Override public double derivative (
		final double x,
		final int derivativeOrder)
		throws java.lang.Exception
	{
		java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = termWeightMap();

		if (null == termWeightMap || 0 == termWeightMap.size())
		{
			return 0.;
		}

		double scale = proportional() ? 0. : 1.;

		double value = 0.;

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> termWeightEntry :
			termWeightMap.entrySet())
		{
			value = value + scale * termWeightEntry.getValue() * _r1ToR1SeriesTerm.derivative (
				termWeightEntry.getKey(),
				derivativeOrder,
				x
			);
		}

		return value;
	}
}
