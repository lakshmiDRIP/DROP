
package org.drip.xva.derivative;

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
 * AssetGreekVertex holds the Derivative XVA Value, its Delta, and its Gamma to the Asset Value. The
 *  References are:
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

public class AssetGreekVertex {
	private double _dblDerivativeXVAValue = java.lang.Double.NaN;
	private double _dblDerivativeFairValue = java.lang.Double.NaN;
	private double _dblDerivativeXVAValueDelta = java.lang.Double.NaN;
	private double _dblDerivativeXVAValueGamma = java.lang.Double.NaN;

	/**
	 * AssetGreekVertex Constructor
	 * 
	 * @param dblDerivativeXVAValue The Derivative XVA Value
	 * @param dblDerivativeXVAValueDelta The Derivative XVA Value Delta
	 * @param dblDerivativeXVAValueGamma The Derivative XVA Value Gamma
	 * @param dblDerivativeFairValue The Derivative "Fair" Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AssetGreekVertex (
		final double dblDerivativeXVAValue,
		final double dblDerivativeXVAValueDelta,
		final double dblDerivativeXVAValueGamma,
		final double dblDerivativeFairValue)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblDerivativeXVAValue = dblDerivativeXVAValue) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblDerivativeXVAValueDelta =
				dblDerivativeXVAValueDelta) || !org.drip.quant.common.NumberUtil.IsValid
					(_dblDerivativeXVAValueGamma = dblDerivativeXVAValueGamma))
			throw new java.lang.Exception ("AssetGreekVertex Constructor => Invalid Inputs");

		_dblDerivativeFairValue = dblDerivativeFairValue;
	}

	/**
	 * Retrieve the Derivative XVA Value
	 * 
	 * @return The Derivative XVA Value
	 */

	public double derivativeXVAValue()
	{
		return _dblDerivativeXVAValue;
	}

	/**
	 * Retrieve the Derivative XVA Value Delta
	 * 
	 * @return The Derivative XVA Value Delta
	 */

	public double derivativeXVAValueDelta()
	{
		return _dblDerivativeXVAValueDelta;
	}

	/**
	 * Retrieve the Derivative XVA Value Gamma
	 * 
	 * @return The Derivative XVA Value Gamma
	 */

	public double derivativeXVAValueGamma()
	{
		return _dblDerivativeXVAValueGamma;
	}

	/**
	 * Retrieve the Derivative De-XVA "Fair" Value
	 * 
	 * @return The Derivative De-XVA "Fair" Value
	 */

	public double derivativeFairValue()
	{
		return _dblDerivativeFairValue;
	}

	/**
	 * Retrieve the Derivative XVA Adjustment
	 * 
	 * @return The Derivative XVA Adjustment
	 */

	public double derivativeXVA()
	{
		return _dblDerivativeXVAValue - _dblDerivativeXVAValue;
	}
}
