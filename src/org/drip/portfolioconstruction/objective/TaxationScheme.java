
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
 * TaxationScheme exposes Taxation related Functionality.
 *
 * @author Lakshmi Krishnamurthy
 */

public interface TaxationScheme {

	/**
	 * Computes the Standard Net US Tax Gain
	 * 
	 * @param adblInitialHoldings The Initial Holdings Array
	 * @param adblFinalHoldings The Final Holdings Array
	 * 
	 * @return The Net Standard US Tax Gain
	 * 
	 * @throws java.lang.Exception Thrown if the Standard Net Tax Gain cannot be calculated
	 */

	public abstract double standardNetTaxGainUS (
		final double[] adblInitialHoldings,
		final double[] adblFinalHoldings)
		throws java.lang.Exception;

	/**
	 * Computes the Custom Net Tax Gain
	 * 
	 * @param adblInitialHoldings The Initial Holdings Array
	 * @param adblFinalHoldings The Final Holdings Array
	 * 
	 * @return The Custom Net Tax Gain
	 * 
	 * @throws java.lang.Exception Thrown if the Custom Net Tax Gain cannot be calculated
	 */

	public abstract double customNetTaxGain (
		final double[] adblInitialHoldings,
		final double[] adblFinalHoldings)
		throws java.lang.Exception;

	/**
	 * Computes the Tax Liability
	 * 
	 * @param adblInitialHoldings The Initial Holdings Array
	 * @param adblFinalHoldings The Final Holdings Array
	 * 
	 * @return The Tax Liability
	 * 
	 * @throws java.lang.Exception Thrown if the Custom Net Tax Gain cannot be calculated
	 */

	public abstract double taxLiability (
		final double[] adblInitialHoldings,
		final double[] adblFinalHoldings)
		throws java.lang.Exception;
}
