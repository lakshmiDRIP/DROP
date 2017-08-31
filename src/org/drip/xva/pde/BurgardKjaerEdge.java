
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
 * BurgardKjaerEdge holds the Underlier Stochastic and the Credit Risk Free Components of the XVA Derivative
 * 	Value Growth, as laid out in Burgard and Kjaer (2014). The References are:
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

public abstract class BurgardKjaerEdge {
	private double _dblAssetNumeraireBump = java.lang.Double.NaN;
	private double _dblDerivativeXVACollateralGrowth = java.lang.Double.NaN;
	private double _dblDerivativeXVAStochasticGrowth = java.lang.Double.NaN;
	private double _dblDerivativeXVAStochasticGrowthUp = java.lang.Double.NaN;
	private double _dblDerivativeXVAStochasticGrowthDown = java.lang.Double.NaN;

	protected BurgardKjaerEdge (
		final double dblAssetNumeraireBump,
		final double dblDerivativeXVAStochasticGrowthDown,
		final double dblDerivativeXVAStochasticGrowth,
		final double dblDerivativeXVAStochasticGrowthUp,
		final double dblDerivativeXVACollateralGrowth)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblAssetNumeraireBump = dblAssetNumeraireBump) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblDerivativeXVAStochasticGrowthDown =
				dblDerivativeXVAStochasticGrowthDown) || !org.drip.quant.common.NumberUtil.IsValid
					(_dblDerivativeXVAStochasticGrowth = dblDerivativeXVAStochasticGrowth) ||
						!org.drip.quant.common.NumberUtil.IsValid (_dblDerivativeXVAStochasticGrowthUp =
							dblDerivativeXVAStochasticGrowthUp) || !org.drip.quant.common.NumberUtil.IsValid
								(_dblDerivativeXVACollateralGrowth = dblDerivativeXVACollateralGrowth))
			throw new java.lang.Exception ("BurgardKjaerEdge Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Asset Numeraire Bump
	 * 
	 * @return The Asset Numeraire Bump
	 */

	public double assetNumeraireBump()
	{
		return _dblAssetNumeraireBump;
	}

	/**
	 * Retrieve the Stochastic Down Component of the Derivative XVA Value
	 * 
	 * @return The Stochastic Down Component of the Derivative XVA Value
	 */

	public double derivativeXVAStochasticGrowthDown()
	{
		return _dblDerivativeXVAStochasticGrowthDown;
	}

	/**
	 * Retrieve the Stochastic Component of the Derivative XVA Value Growth
	 * 
	 * @return The Stochastic Component of the Derivative XVA Value Growth
	 */

	public double derivativeXVAStochasticGrowth()
	{
		return _dblDerivativeXVAStochasticGrowth;
	}

	/**
	 * Retrieve the Stochastic Up Component of the Derivative XVA Value
	 * 
	 * @return The Stochastic Up Component of the Derivative XVA Value
	 */

	public double derivativeXVAStochasticGrowthUp()
	{
		return _dblDerivativeXVAStochasticGrowthUp;
	}

	/**
	 * Retrieve the Collateral Component of the Derivative XVA Value Growth
	 * 
	 * @return The Collateral Component of the Derivative XVA Value Growth
	 */

	public double derivativeXVACollateralGrowth()
	{
		return _dblDerivativeXVACollateralGrowth;
	}

	/**
	 * Compute the Gross Theta from Asset Numeraire Down
	 * 
	 * @return The Gross Theta from Asset Numeraire Down
	 */

	public abstract double thetaAssetNumeraireDown();

	/**
	 * Compute the Gross Theta from Asset Numeraire Base
	 * 
	 * @return The Gross Theta from Asset Numeraire Base
	 */

	public abstract double theta();

	/**
	 * Compute the Gross Theta from Asset Numeraire Up
	 * 
	 * @return The Gross Theta from Asset Numeraire Up
	 */

	public abstract double thetaAssetNumeraireUp();
}
