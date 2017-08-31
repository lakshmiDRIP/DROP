
package org.drip.execution.bayesian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * PriorDriftDistribution holds the Prior Belief Distribution associated with the Directional Drift. The
 *  References are:
 * 
 * 	- Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs, Journal of Financial Markets 1
 * 		1-50.
 * 
 * 	- Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3 (2)
 * 		5-39.
 * 
 * 	- Brunnermeier, L. K., and L. H. Pedersen (2005): Predatory Trading, Journal of Finance 60 (4) 1825-1863.
 *
 * 	- Almgren, R., and J. Lorenz (2006): Bayesian Adaptive Trading with a Daily Cycle, Journal of Trading 1
 * 		(4) 38-46.
 * 
 * 	- Kissell, R., and R. Malamut (2007): Algorithmic Decision Making Framework, Journal of Trading 1 (1)
 * 		12-21.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PriorDriftDistribution extends org.drip.measure.gaussian.R1UnivariateNormal {

	/**
	 * Construct an Instance of Prior Drift Distribution
	 * 
	 * @param dblExpectation Expectation of the Prior Drift
	 * @param dblConfidence Confidence of the Prior Drift
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PriorDriftDistribution (
		final double dblExpectation,
		final double dblConfidence)
		throws java.lang.Exception
	{
		super (dblExpectation, dblConfidence);
	}

	/**
	 * Retrieve the Expectation of the Prior Drift Distribution
	 * 
	 * @return The Expectation of the Prior Drift Distribution
	 */

	public double expectation()
	{
		return mean();
	}

	/**
	 * Retrieve the Confidence of the Prior Drift Distribution
	 * 
	 * @return The Confidence of the Prior Drift Distribution
	 */

	public double confidence()
	{
		return java.lang.Math.sqrt (variance());
	}

	/**
	 * Generate the given Number of Bayesian Drift Realizations
	 * 
	 * @param iNumRealization The Number of Realizations to be generated
	 * 
	 * @return Array of the Drift Realizations
	 */

	public double[] realizedDrift (
		final int iNumRealization)
	{
		if (0 >= iNumRealization) return null;

		double[] adblRealizedDrift = new double[iNumRealization];

		double dblConfidence = confidence();

		double dblExpectation = mean();

		for (int i = 0; i < iNumRealization; ++i) {
			try {
				adblRealizedDrift[i] = dblExpectation + dblConfidence *
					org.drip.measure.gaussian.NormalQuadrature.InverseCDF (java.lang.Math.random());
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adblRealizedDrift;
	}
}
