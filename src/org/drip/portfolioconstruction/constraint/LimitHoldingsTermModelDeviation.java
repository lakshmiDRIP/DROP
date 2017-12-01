
package org.drip.portfolioconstruction.constraint;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * LimitHoldingsTermModelDeviation holds the Details of a Limit Holdings Benchmark Weights Absolute Deviation
 * 	Constraint Term.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LimitHoldingsTermModelDeviation extends
	org.drip.portfolioconstruction.constraint.LimitHoldingsTerm
{
	private double[] _adblBenchmarkHoldings = null;

	/**
	 * LimitHoldingsTermModelDeviation Constructor
	 * 
	 * @param strName Name of the LimitHoldingsTermModelDeviation Constraint
	 * @param scope Scope of the LimitHoldingsTermModelDeviation Constraint
	 * @param unit Unit of the LimitHoldingsTermModelDeviation Constraint
	 * @param dblMinimum Minimum Value of the LimitHoldingsTermModelDeviation Constraint
	 * @param dblMaximum Maximum Value of the LimitHoldingsTermModelDeviation Constraint
	 * @param adblBenchmarkHoldings Array of the Constricted Benchmark Holdings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid/Inconsistent
	 */

	public LimitHoldingsTermModelDeviation (
		final java.lang.String strName,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double dblMinimum,
		final double dblMaximum,
		final double[] adblBenchmarkHoldings)
		throws java.lang.Exception
	{
		super (
			strName,
			"CT_LIMIT_MODEL_DEVIATION",
			"Limit Holdings Model Deviation Constaint Term",
			scope,
			unit,
			dblMinimum,
			dblMaximum,
			null == adblBenchmarkHoldings ? 0 : adblBenchmarkHoldings.length
		);

		if (null == (_adblBenchmarkHoldings = adblBenchmarkHoldings) || 0 != _adblBenchmarkHoldings.length ||
			!org.drip.quant.common.NumberUtil.IsValid (_adblBenchmarkHoldings))
			throw new java.lang.Exception
				("LimitHoldingsTermModelDeviation Constructor => Invalid Selection");
	}

	/**
	 * Retrieve the Array of Benchmark Constricted Holdings
	 * 
	 * @return Array of Benchmark Constricted Holdings
	 */

	public double[] benchmarkHoldings()
	{
		return _adblBenchmarkHoldings;
	}

	@Override public org.drip.function.definition.RdToR1 rdtoR1()
	{
		return new org.drip.function.definition.RdToR1 (null)
		{
			@Override public int dimension()
			{
				return size();
			}

			@Override public double evaluate (
				final double[] adblFinalHoldings)
				throws java.lang.Exception
			{
				double dblConstraintValue = 0.;
				int iNumAsset = _adblBenchmarkHoldings.length;

				if (null == adblFinalHoldings || !org.drip.quant.common.NumberUtil.IsValid
					(adblFinalHoldings) || adblFinalHoldings.length != iNumAsset)
					throw new java.lang.Exception
						("LimitHoldingsTermModelDeviation::rdToR1::evaluate => Invalid Variate Dimension");

				for (int i = 0; i < iNumAsset; ++i)
					dblConstraintValue += java.lang.Math.abs (_adblBenchmarkHoldings[i] -
						adblFinalHoldings[i]);

				return dblConstraintValue;
			}
		};
	}
}
