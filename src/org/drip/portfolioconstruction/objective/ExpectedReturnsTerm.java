
package org.drip.portfolioconstruction.objective;

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
 * ExpectedReturnsTerm holds the Details of the Portfolio Expected Returns Based Objective Terms. Expected
 * 	Returns can be Absolute or in relation to a Benchmark.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ExpectedReturnsTerm extends org.drip.portfolioconstruction.objective.ReturnsTerm {

	/**
	 * ExpectedReturnsTerm Constructor
	 * 
	 * @param strName Name of the Expected Returns Objective Term
	 * @param holdingsInitial Initial Holdings
	 * @param baAlpha Alpha Attributes
	 * @param benchmark Benchmark
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ExpectedReturnsTerm (
		final java.lang.String strName,
		final org.drip.portfolioconstruction.composite.Holdings holdingsInitial,
		final org.drip.portfolioconstruction.composite.BlockAttribute baAlpha,
		final org.drip.portfolioconstruction.composite.Benchmark benchmark)
		throws java.lang.Exception
	{
		super (
			strName,
			"OT_EXPECTED_RETURN",
			"Expected Portfolio Returns Objective Term",
			holdingsInitial,
			baAlpha,
			benchmark
		);
	}

	@Override public org.drip.function.definition.RdToR1 rdtoR1()
	{
		return new org.drip.function.definition.RdToR1 (null)
		{
			@Override public int dimension()
			{
				return initialHoldingsArray().length;
			}

			@Override public double evaluate (
				final double[] adblVariate)
				throws java.lang.Exception
			{
				if (null == adblVariate || !org.drip.quant.common.NumberUtil.IsValid (adblVariate))
					throw new java.lang.Exception ("ExpectedReturnsTerm::rdToR1::evaluate => Invalid Input");

				double[] adblAlpha = alpha();

				double dblExpectedReturn = 0.;
				int iNumAsset = adblAlpha.length;

				if (adblVariate.length != iNumAsset)
					throw new java.lang.Exception
						("ExpectedReturnsTerm::rdToR1::evaluate => Invalid Variate Dimension");

				double[] adblBenchmarkHoldings = benchmarkConstrictedHoldings();

				for (int i = 0; i < iNumAsset; ++i)
					dblExpectedReturn += adblAlpha[i] * (adblVariate[i] - (null == adblBenchmarkHoldings ? 0.
						: adblBenchmarkHoldings[i]));

				return dblExpectedReturn;
			}
		};
	}
}
