
package org.drip.xva.hypothecation;

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
 * CollateralGroupVertexCloseOut holds the Bank and the Counter Party Close Outs at each Re-hypothecation
 *  Collateral Group. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 *  	Presence of Counter-party Credit Risk for the Fixed Income Market, World Scientific Publishing,
 *  	Singapore.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CollateralGroupVertexCloseOut {
	private double _dblBank = java.lang.Double.NaN;
	private double _dblCounterParty = java.lang.Double.NaN;

	/**
	 * Construct a Static Instance of CollateralGroupVertexCloseOut
	 * 
	 * @param cog The Close Out General Instance
	 * @param dblUncollateralizedExposure The Uncollateralized Exposure
	 * @param dblCollateralBalance The Collateral Balance
	 * 
	 * @return The Static Instance of CollateralGroupVertexCloseOut
	 */

	public static final CollateralGroupVertexCloseOut Standard (
		final org.drip.xva.definition.CloseOutGeneral cog,
		final double dblUncollateralizedExposure,
		final double dblCollateralBalance)
	{
		if (null == cog || !org.drip.quant.common.NumberUtil.IsValid (dblUncollateralizedExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblCollateralBalance))
			return null;

		try {
			return new CollateralGroupVertexCloseOut (
				cog.bankDefault (
					dblUncollateralizedExposure,
					dblCollateralBalance
				),
				cog.counterPartyDefault (
					dblUncollateralizedExposure,
					dblCollateralBalance
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CollateralGroupVertexCloseOut Constructor
	 * 
	 * @param dblBank The Bank Close Out
	 * @param dblCounterParty The Counter Party Close Out
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CollateralGroupVertexCloseOut (
		final double dblBank,
		final double dblCounterParty)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblBank = dblBank) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblCounterParty = dblCounterParty))
			throw new java.lang.Exception ("CollateralGroupVertexCloseOut Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Bank Close Out
	 * 
	 * @return The Bank Close Out
	 */

	public double bank()
	{
		return _dblBank;
	}

	/**
	 * Retrieve the Counter Party Close Out
	 * 
	 * @return The Counter Party Close Out
	 */

	public double counterParty()
	{
		return _dblCounterParty;
	}
}
