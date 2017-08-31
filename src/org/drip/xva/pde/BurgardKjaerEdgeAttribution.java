
package org.drip.xva.pde;

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
 * BurgardKjaerEdgeAttribution collects the Attribution Components of the Burgard Kjaer PDE based on the
 *  Risk-Neutral Ito Evolution of the Derivative, as laid out in Burgard and Kjaer (2014). The References
 *  are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): Modeling, Pricing,
 *  	and Hedging Counter-party Credit Exposure - A Technical Guide, Springer Finance, New York.
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

public class BurgardKjaerEdgeAttribution extends org.drip.xva.pde.BurgardKjaerEdge {
	private double _dblDerivativeXVAFundingGrowth = java.lang.Double.NaN;
	private double _dblDerivativeXVABankDefaultGrowth = java.lang.Double.NaN;
	private double _dblDerivativeXVAEarlyTerminationGrowth = java.lang.Double.NaN;
	private double _dblDerivativeXVACounterPartyDefaultGrowth = java.lang.Double.NaN;

	/**
	 * BurgardKjaerEdgeAttribution Constructor
	 * 
	 * @param dblAssetNumeraireBump The Bump in the Asset Numeraire Value
	 * @param dblDerivativeXVAStochasticGrowthDown The Stochastic Down Component of the Derivative XVA Value
	 * 		Growth
	 * @param dblDerivativeXVAStochasticGrowth The Stochastic Component of the Derivative XVA Value Growth
	 * @param dblDerivativeXVAStochasticGrowthUp The Stochastic Up Component of the Derivative XVA Value
	 * 		Growth
	 * @param dblDerivativeXVACollateralGrowth The Collateral Component of the Derivative XVA Value Growth
	 * @param dblDerivativeXVAEarlyTerminationGrowth The Early Termination Component of the Derivative XVA
	 * 		Value Growth
	 * @param dblDerivativeXVAFundingGrowth The Funding Component of the Derivative XVA Value Growth
	 * @param dblDerivativeXVABankDefaultGrowth The Bank Default Component of the Derivative Value XVA Growth
	 * @param dblDerivativeXVACounterPartyDefaultGrowth The Counter Party Default Component of the Derivative
	 * 		XVA Value Growth
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BurgardKjaerEdgeAttribution (
		final double dblAssetNumeraireBump,
		final double dblDerivativeXVAStochasticGrowthDown,
		final double dblDerivativeXVAStochasticGrowth,
		final double dblDerivativeXVAStochasticGrowthUp,
		final double dblDerivativeXVACollateralGrowth,
		final double dblDerivativeXVAEarlyTerminationGrowth,
		final double dblDerivativeXVAFundingGrowth,
		final double dblDerivativeXVABankDefaultGrowth,
		final double dblDerivativeXVACounterPartyDefaultGrowth)
		throws java.lang.Exception
	{
		super (dblAssetNumeraireBump, dblDerivativeXVAStochasticGrowthDown, dblDerivativeXVAStochasticGrowth,
			dblDerivativeXVAStochasticGrowthUp, dblDerivativeXVACollateralGrowth);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblDerivativeXVAEarlyTerminationGrowth =
			dblDerivativeXVAEarlyTerminationGrowth) || !org.drip.quant.common.NumberUtil.IsValid
				(_dblDerivativeXVAFundingGrowth = dblDerivativeXVAFundingGrowth) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblDerivativeXVABankDefaultGrowth =
						dblDerivativeXVABankDefaultGrowth) || !org.drip.quant.common.NumberUtil.IsValid
							(_dblDerivativeXVACounterPartyDefaultGrowth =
								dblDerivativeXVACounterPartyDefaultGrowth))
			throw new java.lang.Exception ("BurgardKjaerEdgeAttribution Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Early Termination Component of the Derivative XVA Value Growth
	 * 
	 * @return The Early Termination Component of the Derivative XVA Value Growth
	 */

	public double derivativeXVAEarlyTerminationGrowth()
	{
		return _dblDerivativeXVAEarlyTerminationGrowth;
	}

	/**
	 * Retrieve the Funding Component of the Derivative XVA Value Growth
	 * 
	 * @return The Funding Component of the Derivative XVA Value Growth
	 */

	public double derivativeXVAFundingGrowth()
	{
		return _dblDerivativeXVAFundingGrowth;
	}

	/**
	 * Retrieve the Bank Default Component of the Derivative XVA Value Growth
	 * 
	 * @return The Bank Default Component of the Derivative XVA Value Growth
	 */

	public double derivativeXVABankDefaultGrowth()
	{
		return _dblDerivativeXVABankDefaultGrowth;
	}

	/**
	 * Retrieve the Counter Party Default Component of the Derivative XVA Value Growth
	 * 
	 * @return The Counter Party Default Component of the Derivative XVA Value Growth
	 */

	public double derivativeXVACounterPartyDefaultGrowth()
	{
		return _dblDerivativeXVACounterPartyDefaultGrowth;
	}

	@Override public double thetaAssetNumeraireDown()
	{
		return super.derivativeXVAStochasticGrowthDown() + super.derivativeXVACollateralGrowth() +
			_dblDerivativeXVAEarlyTerminationGrowth + _dblDerivativeXVAFundingGrowth +
				_dblDerivativeXVABankDefaultGrowth + _dblDerivativeXVACounterPartyDefaultGrowth;
	}

	@Override public double theta()
	{
		return super.derivativeXVAStochasticGrowth() + super.derivativeXVACollateralGrowth() +
			_dblDerivativeXVAEarlyTerminationGrowth + _dblDerivativeXVAFundingGrowth +
				_dblDerivativeXVABankDefaultGrowth + _dblDerivativeXVACounterPartyDefaultGrowth;
	}

	@Override public double thetaAssetNumeraireUp()
	{
		return super.derivativeXVAStochasticGrowthUp() + super.derivativeXVACollateralGrowth() +
			_dblDerivativeXVAEarlyTerminationGrowth + _dblDerivativeXVAFundingGrowth +
				_dblDerivativeXVABankDefaultGrowth + _dblDerivativeXVACounterPartyDefaultGrowth;
	}
}
