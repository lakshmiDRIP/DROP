
package org.drip.specialfunction.digamma;

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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Suite</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/digamma/README.md">Estimation Techniques for Digamma Function</a></li>
 *  </ul>
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
