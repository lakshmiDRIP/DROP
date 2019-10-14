
package org.drip.analytics.output;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>BondEOSMetrics</i> carries the Option Adjusted Metrics for a Bond with Embedded Options.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Date, Cash Flow, and Cash Flow Period Measure Generation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/README.md">Period Product Targeted Valuation Measures</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BondEOSMetrics {
	private double[][] _aadblForwardPrice = null;
	private double _dblOASTM = java.lang.Double.NaN;
	private boolean[][] _aabExerciseIndicator = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _udtOptimalExerciseOAS = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _udtOptimalExercisePrice = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _udtOptimalExerciseValue = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _udtOptimalExerciseOASGap = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _udtOptimalExerciseDuration = null;
	private org.drip.measure.statistics.UnivariateDiscreteThin _udtOptimalExerciseConvexity = null;

	/**
	 * BondEOSMetrics Constructor
	 * 
	 * @param dblOASTM The OAS To Maturity
	 * @param adblOptimalExercisePrice Array of Optimal Exercise Price
	 * @param adblOptimalExerciseValue Array of Optimal Exercise Value
	 * @param adblOptimalExerciseOAS Array of Optimal Exercise OAS
	 * @param adblOptimalExerciseOASGap Array of Optimal Exercise OAS Gap
	 * @param adblOptimalExerciseDuration Array of Optimal Exercise Duration
	 * @param adblOptimalExerciseConvexity Array of Optimal Exercise Convexity
	 * @param aadblForwardPrice Double Array of Path/Vertex Forward Prices
	 * @param aabExerciseIndicator Double Array of Path/Vertex Exercise Indicators
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BondEOSMetrics (
		final double dblOASTM,
		final double[] adblOptimalExercisePrice,
		final double[] adblOptimalExerciseValue,
		final double[] adblOptimalExerciseOAS,
		final double[] adblOptimalExerciseOASGap,
		final double[] adblOptimalExerciseDuration,
		final double[] adblOptimalExerciseConvexity,
		final double[][] aadblForwardPrice,
		final boolean[][] aabExerciseIndicator)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblOASTM = dblOASTM))
			throw new java.lang.Exception ("BondEOSMetrics Constructor => Invalid Inputs");

		_udtOptimalExercisePrice = new org.drip.measure.statistics.UnivariateDiscreteThin
			(adblOptimalExercisePrice);

		_udtOptimalExerciseValue = new org.drip.measure.statistics.UnivariateDiscreteThin
			(adblOptimalExerciseValue);

		_udtOptimalExerciseOAS = new org.drip.measure.statistics.UnivariateDiscreteThin
			(adblOptimalExerciseOAS);

		_udtOptimalExerciseOASGap = new org.drip.measure.statistics.UnivariateDiscreteThin
			(adblOptimalExerciseOASGap);

		_udtOptimalExerciseDuration = new org.drip.measure.statistics.UnivariateDiscreteThin
			(adblOptimalExerciseDuration);

		_udtOptimalExerciseConvexity = new org.drip.measure.statistics.UnivariateDiscreteThin
			(adblOptimalExerciseConvexity);

		_aadblForwardPrice = aadblForwardPrice;
		_aabExerciseIndicator = aabExerciseIndicator;
	}

	/**
	 * Retrieve the Optimal Exercise Price UDT
	 * 
	 * @return The Optimal Exercise Price UDT
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin optimalExercisePrice()
	{
		return _udtOptimalExercisePrice;
	}

	/**
	 * Retrieve the Optimal Exercise Value UDT
	 * 
	 * @return The Optimal Exercise Value UDT
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin optimalExerciseValue()
	{
		return _udtOptimalExerciseValue;
	}

	/**
	 * Retrieve the Optimal Exercise OAS UDT
	 * 
	 * @return The Optimal Exercise OAS UDT
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin optimalExerciseOAS()
	{
		return _udtOptimalExerciseOAS;
	}

	/**
	 * Retrieve the Optimal Exercise OAS Gap UDT
	 * 
	 * @return The Optimal Exercise OAS Gap UDT
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin optimalExerciseOASGap()
	{
		return _udtOptimalExerciseOASGap;
	}

	/**
	 * Retrieve the Optimal Exercise Duration UDT
	 * 
	 * @return The Optimal Exercise Duration UDT
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin optimalExerciseDuration()
	{
		return _udtOptimalExerciseDuration;
	}

	/**
	 * Retrieve the Optimal Exercise Convexity UDT
	 * 
	 * @return The Optimal Exercise Convexity UDT
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin optimalExerciseConvexity()
	{
		return _udtOptimalExerciseConvexity;
	}

	/**
	 * Retrieve the Bond Option Adjusted Spread
	 * 
	 * @return The Bond Option Adjusted Spread
	 */

	public double oas()
	{
		return _udtOptimalExerciseOAS.average();
	}

	/**
	 * Retrieve the Bond Option Adjusted Spread To Maturity
	 * 
	 * @return The Bond Option Adjusted Spread To Maturity
	 */

	public double oasTM()
	{
		return _dblOASTM;
	}

	/**
	 * Retrieve the Bond Option Adjusted Spread Duration
	 * 
	 * @return The Bond Option Adjusted Spread Duration
	 */

	public double oasDuration()
	{
		return _udtOptimalExerciseDuration.average();
	}

	/**
	 * Retrieve the Bond Option Adjusted Spread Convexity
	 * 
	 * @return The Bond Option Adjusted Spread Convexity
	 */

	public double oasConvexity()
	{
		return _udtOptimalExerciseConvexity.average();
	}

	/**
	 * Retrieve the Path/Vertex Forward Price Double Array
	 * 
	 * @return The Path/Vertex Forward Price Double Array
	 */

	public double[][] forwardPrice()
	{
		return _aadblForwardPrice;
	}

	/**
	 * Retrieve the Path/Vertex Exercise Indicator Double Array
	 * 
	 * @return The Path/Vertex Exercise Indicator Double Array
	 */

	public boolean[][] exerciseIndicator()
	{
		return _aabExerciseIndicator;
	}
}
