
package org.drip.analytics.output;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * BondEOSMetrics carries the Option Adjusted Metrics for a Bond with Embedded Options.
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
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblOASTM = dblOASTM))
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
