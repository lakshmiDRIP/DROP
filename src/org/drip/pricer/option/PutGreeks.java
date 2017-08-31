
package org.drip.pricer.option;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * PutGreeks contains the Sensitivities generated during the Put Option Pricing Run.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PutGreeks extends org.drip.pricer.option.Greeks {
	private double _dblPutPriceFromParity = java.lang.Double.NaN;

	/**
	 * The PutGreeks Constructor
	 * 
	 * @param dblDF The Payoff Discount Factor
	 * @param dblEffectiveVolatility Effective Volatility
	 * @param dblExpectedPayoff Expected Forward Payoff
	 * @param dblExpectedATMPayoff Expected ATM Forward Payoff
	 * @param dblPutPrice Put Price
	 * @param dblPutPriceFromParity Put Price Computed from Put-Call Parity
	 * @param dblPutProb1 Put Probability Term #1
	 * @param dblPutProb2 Put Probability Term #2
	 * @param dblPutDelta Put Delta
	 * @param dblPutVega Put Vega
	 * @param dblPutTheta Put Theta
	 * @param dblPutRho Put Rho
	 * @param dblPutGamma Put Gamma
	 * @param dblPutVanna Put Vanna
	 * @param dblPutVomma Put Vomma
	 * @param dblPutCharm Put Charm
	 * @param dblPutVeta Put Veta
	 * @param dblPutColor Put Color
	 * @param dblPutSpeed Put Speed
	 * @param dblPutUltima Put Ultima
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PutGreeks (
		final double dblDF,
		final double dblEffectiveVolatility,
		final double dblExpectedPayoff,
		final double dblExpectedATMPayoff,
		final double dblPutPrice,
		final double dblPutPriceFromParity,
		final double dblPutProb1,
		final double dblPutProb2,
		final double dblPutDelta,
		final double dblPutVega,
		final double dblPutTheta,
		final double dblPutRho,
		final double dblPutGamma,
		final double dblPutVanna,
		final double dblPutVomma,
		final double dblPutCharm,
		final double dblPutVeta,
		final double dblPutColor,
		final double dblPutSpeed,
		final double dblPutUltima)
		throws java.lang.Exception
	{
		super (dblDF, dblEffectiveVolatility, dblExpectedPayoff, dblExpectedATMPayoff, dblPutPrice,
			dblPutProb1, dblPutProb2, dblPutDelta, dblPutVega, dblPutTheta, dblPutRho, dblPutGamma,
				dblPutVanna, dblPutVomma, dblPutCharm, dblPutVeta, dblPutColor, dblPutSpeed, dblPutUltima);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblPutPrice) &&
			!org.drip.quant.common.NumberUtil.IsValid (_dblPutPriceFromParity = dblPutPriceFromParity))
			throw new java.lang.Exception ("PutGreeks ctr: Invalid Inputs");

		_dblPutPriceFromParity = dblPutPriceFromParity;
	}

	/**
	 * The Put Option Price Computed from the Put-Call Parity Relation
	 * 
	 * @return The Put Option Price Computed from the Put-Call Parity Relation
	 */

	public double putPriceFromParity()
	{
		return _dblPutPriceFromParity;
	}
}
