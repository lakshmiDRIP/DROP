
package org.drip.xva.csadynamics;

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
 * NumeraireInducedMeasureShift computes the Shift of the Forward Terminal Distribution between the Non-CSA
 *  and the CSA Cases. The References are:
 *  
 *  - Antonov, A., and M. Arneguy (2009): Analytical Formulas for Pricing CMS Products in the LIBOR Market
 *  	Model with Stochastic Volatility, https://papers.ssrn.com/sol3/Papers.cfm?abstract_id=1352606, eSSRN.
 *  
 *  - Burgard, C., and M. Kjaer (2009): Modeling and successful Management of Credit Counter-party Risk of
 *  	Derivative Portfolios, ICBI Conference, Rome.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Johannes, M., and S. Sundaresan (2007): Pricing Collateralized Swaps, Journal of Finance 62 383-410.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class NumeraireInducedMeasureShift
{
	private double _csaForward = java.lang.Double.NaN;
	private double _noCSAForward = java.lang.Double.NaN;
	private double _terminalVariance = java.lang.Double.NaN;

	/**
	 * NumeraireInducedMeasureShift Constructor
	 * 
	 * @param csaForward The CSA Implied Forward Value
	 * @param noCSAForward The No CSA Implied Forward Value
	 * @param terminalVariance The Terminal Variance of the Underlying
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NumeraireInducedMeasureShift (
		final double csaForward,
		final double noCSAForward,
		final double terminalVariance)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_csaForward = csaForward) ||
			!org.drip.quant.common.NumberUtil.IsValid (_noCSAForward = noCSAForward) ||
			!org.drip.quant.common.NumberUtil.IsValid (_terminalVariance = terminalVariance))
		{
			throw new java.lang.Exception ("NumeraireInducedMeasureShift Constructor => Invalid Inputs");
		}
	}

	/**
	 * Return the Value of the Forward Contract under CSA
	 * 
	 * @return The Value of the Forward Contract under CSA
	 */

	public double csaForward()
	{
		return _csaForward;
	}

	/**
	 * Return the Value of the Forward Contract under No CSA Criterion
	 * 
	 * @return The Value of the Forward Contract under No CSA Criterion
	 */

	public double noCSAForward()
	{
		return _noCSAForward;
	}

	/**
	 * Return the Terminal Variance of the Underlying
	 * 
	 * @return The Terminal Variance of the Underlying
	 */

	public double terminalVariance()
	{
		return _terminalVariance;
	}

	/**
	 * Return the Linear Strike Coefficient of the Relative Measure Differential
	 * 
	 * @return The Linear Strike Coefficient of the Relative Measure Differential
	 */

	public double alpha1()
	{
		return (_noCSAForward - _csaForward) / _terminalVariance;
	}

	/**
	 * Return the Constant Strike Coefficient of the Relative Measure Differential
	 * 
	 * @return The Constant Strike Coefficient of the Relative Measure Differential
	 */

	public double alpha0()
	{
		return 1. - alpha1() * _csaForward;
	}

	/**
	 * Compute the No CSA/CSA Density Re-scaling using the Antonov and Arneguy (2009) Linear Proxy Approach
	 * 
	 * @param k The Strike at which the Density Re-scaling is Sought
	 * 
	 * @return The No CSA/CSA Density Re-scaling using the Antonov and Arneguy (2009) Linear Proxy Approach
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double densityRescale (
		final double k)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (k))
		{
			throw new java.lang.Exception ("NumeraireInducedMeasureShift::densityRescale => Invalid Inputs");
		}

		double dblAlpha1 = (_noCSAForward - _csaForward) / _terminalVariance;
		return 1. - dblAlpha1 * _csaForward + dblAlpha1 * k;
	}
}
