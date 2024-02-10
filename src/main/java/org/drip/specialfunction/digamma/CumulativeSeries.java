
package org.drip.specialfunction.digamma;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>CumulativeSeries</i> implements the Cumulative Series for Digamma Estimation. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): Handbook of Mathematics Functions <b>Dover Book on
 * 				Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Blagouchine, I. V. (2018): Three Notes on Ser's and Hasse's Representations for the
 * 				Zeta-Functions https://arxiv.org/abs/1606.02044 <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Mezo, I., and M. E. Hoffman (2017): Zeros of the Digamma Function and its Barnes G-function
 * 				Analogue <i>Integral Transforms and Special Functions</i> <b>28 (28)</b> 846-858
 * 		</li>
 * 		<li>
 * 			Whitaker, E. T., and G. N. Watson (1996): <i>A Course on Modern Analysis</i> <b>Cambridge
 * 				University Press</b> New York
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Digamma Function https://en.wikipedia.org/wiki/Digamma_function
 * 		</li>
 * 	</ul>
 * 
 * 	It provides the following functionality:
 *
 *  <ul>
 * 		<li>Construct the R<sup>1</sup> To R<sup>1</sup> Infinite Abramowitz-Stegun (2007) Cumulative Series</li>
 * 		<li>Construct the R<sup>1</sup> To R<sup>1</sup> Infinite Saddle Point Cumulative Series</li>
 * 		<li>Construct the R<sup>1</sup> To R<sup>1</sup> Gauss Cumulative Series</li>
 * 		<li>Construct the R<sup>1</sup> To R<sup>1</sup> Asymptotic Cumulative Series</li>
 * 		<li>Construct the R<sup>1</sup> To R<sup>1</sup> Exponential Asymptotic Cumulative Series</li>
 * 		<li>Construct the R<sup>1</sup> To R<sup>1</sup> Exponential Half-Shifted Asymptotic Cumulative Series</li>
 * 		<li>Construct the R<sup>1</sup> To R<sup>1</sup> Taylor Riemann-Zeta Cumulative Series</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation and Analysis</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/digamma/README.md">Estimation Techniques for Digamma Function</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CumulativeSeries
{

	/**
	 * Construct the R<sup>1</sup> To R<sup>1</sup> Infinite Abramowitz-Stegun (2007) Cumulative Series
	 * 
	 * @param termCount Count of the Number of Terms
	 * 
	 * @return The R<sup>1</sup> To R<sup>1</sup> Infinite Abramowitz-Stegun (2007) Cumulative Series
	 */

	public static final org.drip.numerical.estimation.R1ToR1Series AbramowitzStegun2007 (
		final int termCount)
	{
		try
		{
			java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
				java.util.TreeMap<java.lang.Integer, java.lang.Double>();

			for (int termIndex = 1; termIndex <= termCount; ++termIndex)
			{
				termWeightMap.put (
					termIndex,
					1.
				);
			}

			return new org.drip.numerical.estimation.R1ToR1Series (
				org.drip.specialfunction.digamma.CumulativeSeriesTerm.AbramowitzStegun2007(),
				false,
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the R<sup>1</sup> To R<sup>1</sup> Infinite Saddle Point Cumulative Series
	 * 
	 * @param saddlePointFunction The Saddle Point Generation Function
	 * @param saddlePointCount The Saddle Point Count
	 * 
	 * @return The R<sup>1</sup> To R<sup>1</sup> Infinite Saddle Point Cumulative Series
	 */

	public static final org.drip.numerical.estimation.R1ToR1Series MezoHoffman2017 (
		final org.drip.function.definition.R1ToR1 saddlePointFunction,
		final int saddlePointCount)
	{
		try
		{
			java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
				java.util.TreeMap<java.lang.Integer, java.lang.Double>();

			for (int termIndex = 0; termIndex <= saddlePointCount; ++termIndex)
			{
				termWeightMap.put (
					termIndex,
					1.
				);
			}

			return new org.drip.numerical.estimation.R1ToR1Series (
				org.drip.specialfunction.digamma.CumulativeSeriesTerm.MezoHoffman2017 (
					org.drip.specialfunction.digamma.SaddlePoints.LeadingRoots (
						saddlePointFunction,
						saddlePointCount
					)
				),
				false,
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the R<sup>1</sup> To R<sup>1</sup> Gauss Cumulative Series
	 * 
	 * @param termCount Count of the Number of Terms
	 * 
	 * @return The R<sup>1</sup> To R<sup>1</sup> Gauss Cumulative Series
	 */

	public static final org.drip.numerical.estimation.R1ToR1Series Gauss (
		final int termCount)
	{
		int seriesCount = (termCount - 1) / 2;

		try
		{
			java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
				java.util.TreeMap<java.lang.Integer, java.lang.Double>();

			for (int termIndex = 1; termIndex <= seriesCount; ++termIndex)
			{
				termWeightMap.put (
					termIndex,
					1.
				);
			}

			return new org.drip.numerical.estimation.R1ToR1Series (
				org.drip.specialfunction.digamma.CumulativeSeriesTerm.Gauss (termCount),
				false,
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the R<sup>1</sup> To R<sup>1</sup> Asymptotic Cumulative Series
	 * 
	 * @return The R<sup>1</sup> To R<sup>1</sup> Asymptotic Cumulative Series
	 */

	public static final org.drip.numerical.estimation.R1ToR1Series Asymptotic()
	{
		try
		{
			java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
				java.util.TreeMap<java.lang.Integer, java.lang.Double>();

			termWeightMap.put (
				1,
				-1. / 12.
			);

			termWeightMap.put (
				2,
				1. / 120.
			);

			termWeightMap.put (
				3,
				-1. / 252.
			);

			termWeightMap.put (
				4,
				1. / 240.
			);

			termWeightMap.put (
				5,
				-1. / 132.
			);

			termWeightMap.put (
				6,
				691. / 32760.
			);

			termWeightMap.put (
				7,
				-1. / 12.
			);

			return new org.drip.numerical.estimation.R1ToR1Series (
				org.drip.specialfunction.digamma.CumulativeSeriesTerm.Asymptotic(),
				false,
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the R<sup>1</sup> To R<sup>1</sup> Exponential Asymptotic Cumulative Series
	 * 
	 * @return The R<sup>1</sup> To R<sup>1</sup> Exponential Asymptotic Cumulative Series
	 */

	public static final org.drip.numerical.estimation.R1ToR1Series ExponentialAsymptote()
	{
		try
		{
			java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
				java.util.TreeMap<java.lang.Integer, java.lang.Double>();

			termWeightMap.put (
				1,
				1.
			);

			termWeightMap.put (
				2,
				1. / 2.
			);

			termWeightMap.put (
				3,
				5. / 24.
			);

			termWeightMap.put (
				4,
				1. / 16.
			);

			termWeightMap.put (
				5,
				47. / (48. * 120.)
			);

			termWeightMap.put (
				6,
				1. / (16. * 144.)
			);

			return new org.drip.numerical.estimation.R1ToR1Series (
				org.drip.specialfunction.digamma.CumulativeSeriesTerm.ExponentialAsymptote(),
				false,
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the R<sup>1</sup> To R<sup>1</sup> Exponential Half-Shifted Asymptotic Cumulative Series
	 * 
	 * @return The R<sup>1</sup> To R<sup>1</sup> Exponential Half-Shifted Asymptotic Cumulative Series
	 */

	public static final org.drip.numerical.estimation.R1ToR1Series ExponentialAsymptoteHalfShifted()
	{
		try
		{
			java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
				java.util.TreeMap<java.lang.Integer, java.lang.Double>();

			termWeightMap.put (
				1,
				1. / org.drip.numerical.common.NumberUtil.Factorial (4)
			);

			termWeightMap.put (
				2,
				-37. / (8. * org.drip.numerical.common.NumberUtil.Factorial (6))
			);

			termWeightMap.put (
				3,
				10313. / (72. * org.drip.numerical.common.NumberUtil.Factorial (8))
			);

			termWeightMap.put (
				4,
				-5509121. / (384. * org.drip.numerical.common.NumberUtil.Factorial (10))
			);

			termWeightMap.put (
				5,
				47. / (48. * 120.)
			);

			termWeightMap.put (
				6,
				1. / (16. * 144.)
			);

			return new org.drip.numerical.estimation.R1ToR1Series (
				org.drip.specialfunction.digamma.CumulativeSeriesTerm.ExponentialAsymptoteHalfShifted(),
				false,
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the R<sup>1</sup> To R<sup>1</sup> Taylor Riemann-Zeta Cumulative Series
	 * 
	 * @param riemannZetaEstimator The Riemann-Zeta Estimator
	 * @param termCount Count of the Number of Terms
	 * 
	 * @return The R<sup>1</sup> To R<sup>1</sup> Taylor Riemann-Zeta Cumulative Series
	 */

	public static final org.drip.numerical.estimation.R1ToR1Series TaylorRiemannZeta (
		final org.drip.function.definition.R1ToR1 riemannZetaEstimator,
		final int termCount)
	{
		try
		{
			java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
				java.util.TreeMap<java.lang.Integer, java.lang.Double>();

			for (int termIndex = 1; termIndex <= termCount; ++termIndex)
			{
				termWeightMap.put (
					termIndex,
					1.
				);
			}

			return new org.drip.numerical.estimation.R1ToR1Series (
				org.drip.specialfunction.digamma.CumulativeSeriesTerm.TaylorRiemannZeta
					(riemannZetaEstimator),
				false,
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
