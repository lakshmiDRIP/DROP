
package org.drip.dynamics.sabr;

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
 *  DRIP is a free, full featured, fixed income rates, credit, and FX analytics library with a focus towards
 *  	pricing/valuation, risk, and market making.
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
 * ImpliedBlackVolatility contains the Output of the Black Volatility Implication Calculations.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ImpliedBlackVolatility {
	private double _dblA = java.lang.Double.NaN;
	private double _dblB = java.lang.Double.NaN;
	private double _dblZ = java.lang.Double.NaN;
	private double _dblTTE = java.lang.Double.NaN;
	private double _dblChiZ = java.lang.Double.NaN;
	private double _dblStrike = java.lang.Double.NaN;
	private double _dblATMForwardRate = java.lang.Double.NaN;
	private double _dblImpliedVolatility = java.lang.Double.NaN;

	/**
	 * ImpliedBlackVolatility Constructor
	 * 
	 * @param dblStrike Strike
	 * @param dblATMForwardRate Forward Rate
	 * @param dblTTE Time To Expiry
	 * @param dblA A
	 * @param dblZ Z
	 * @param dblChiZ Chi (Z)
	 * @param dblB B
	 * @param dblImpliedVolatility The Implied Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public ImpliedBlackVolatility (
		final double dblStrike,
		final double dblATMForwardRate,
		final double dblTTE,
		final double dblA,
		final double dblZ,
		final double dblChiZ,
		final double dblB,
		final double dblImpliedVolatility)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblStrike = dblStrike) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblATMForwardRate = dblATMForwardRate) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblTTE = dblTTE) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblA = dblA) ||
						!org.drip.quant.common.NumberUtil.IsValid (_dblZ = dblZ) ||
							!org.drip.quant.common.NumberUtil.IsValid (_dblChiZ = dblChiZ) ||
								!org.drip.quant.common.NumberUtil.IsValid (_dblB = dblB) ||
									!org.drip.quant.common.NumberUtil.IsValid (_dblImpliedVolatility =
										dblImpliedVolatility))
			throw new java.lang.Exception ("ImpliedBlackVolatility ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Strike
	 * 
	 * @return The Strike
	 */

	public double strike()
	{
		return _dblStrike;
	}

	/**
	 * Retrieve the ATM Forward Rate
	 * 
	 * @return The ATM Forward Rate
	 */

	public double atmForwardRate()
	{
		return _dblATMForwardRate;
	}

	/**
	 * Retrieve TTE
	 * 
	 * @return TTE
	 */

	public double tte()
	{
		return _dblTTE;
	}

	/**
	 * Retrieve A
	 * 
	 * @return A
	 */

	public double a()
	{
		return _dblA;
	}

	/**
	 * Retrieve Z
	 * 
	 * @return Z
	 */

	public double z()
	{
		return _dblZ;
	}

	/**
	 * Retrieve Chi
	 * 
	 * @return Chi
	 */

	public double chi()
	{
		return _dblChiZ;
	}

	/**
	 * Retrieve B
	 * 
	 * @return B
	 */

	public double b()
	{
		return _dblB;
	}

	/**
	 * Retrieve the Implied Volatility
	 * 
	 * @return The Implied Volatility
	 */

	public double impliedVolatility()
	{
		return _dblImpliedVolatility;
	}
}
