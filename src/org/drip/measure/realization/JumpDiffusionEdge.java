
package org.drip.measure.realization;

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
 * JumpDiffusionEdge implements the Deterministic and the Stochastic Components of a R^1 Marginal Random
 *	Increment Edge as well the Original Marginal Random Variate. The References are:
 * 
 * 	- Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3
 * 		(2) 5-39.
 *
 * 	- Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 * 		https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf.
 *
 * 	- Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility, SIAM Journal of
 * 		Financial Mathematics  3 (1) 163-181.
 * 
 * 	- Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes, Mathematical Finance 11 (1)
 * 		79-96.
 * 
 * 	- Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility, Review of
 * 		Financial Studies 7 (4) 631-651.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class JumpDiffusionEdge {
	private double _dblStart = java.lang.Double.NaN;
	private double _dblDeterministic = java.lang.Double.NaN;
	private org.drip.measure.realization.StochasticEdgeJump _sej = null;
	private org.drip.measure.realization.JumpDiffusionEdgeUnit _jdeu = null;
	private org.drip.measure.realization.StochasticEdgeDiffusion _sed = null;

	/**
	 * Construct the Standard JumpDiffusionEdge Instance
	 * 
	 * @param dblStart The Starting Random Variable Realization
	 * @param dblDeterministic The Deterministic Increment Component
	 * @param dblDiffusionStochastic The Diffusion Stochastic Edge Change Amount
	 * @param bJumpOccurred TRUE - The Jump Occurred in this Edge Period
	 * @param dblHazardRate The Hazard Rate
	 * @param dblHazardIntegral The Level Hazard Integral
	 * @param dblJumpTarget The Jump Target
	 * @param dblTimeIncrement The Time Increment
	 * @param dblUnitDiffusion The Diffusion Random Variable
	 * @param dblUnitJump The Jump Random Variable
	 * 
	 * @return The JumpDiffusionEdge Instance
	 */

	public static final JumpDiffusionEdge Standard (
		final double dblStart,
		final double dblDeterministic,
		final double dblDiffusionStochastic,
		final boolean bJumpOccurred,
		final double dblHazardRate,
		final double dblHazardIntegral,
		final double dblJumpTarget,
		final double dblTimeIncrement,
		final double dblUnitDiffusion,
		final double dblUnitJump)
	{
		try {
			return new JumpDiffusionEdge (dblStart, dblDeterministic, new
				org.drip.measure.realization.StochasticEdgeDiffusion (dblDiffusionStochastic), new
					org.drip.measure.realization.StochasticEdgeJump (bJumpOccurred, dblHazardRate,
						dblHazardIntegral, dblJumpTarget), new
							org.drip.measure.realization.JumpDiffusionEdgeUnit (dblTimeIncrement,
								dblUnitDiffusion, dblUnitJump));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard JumpDiffusionEdge Instance
	 * 
	 * @param dblStart The Starting Random Variable Realization
	 * @param dblDeterministic The Deterministic Increment Component
	 * @param dblDiffusionStochastic The Diffusion Stochastic Edge Change Amount
	 * @param sej The Stochastic Jump Edge Instance
	 * @param jdeu The Random Unit Realization
	 * 
	 * @return The JumpDiffusionEdge Instance
	 */

	public static final JumpDiffusionEdge Standard (
		final double dblStart,
		final double dblDeterministic,
		final double dblDiffusionStochastic,
		final org.drip.measure.realization.StochasticEdgeJump sej,
		final org.drip.measure.realization.JumpDiffusionEdgeUnit jdeu)
	{
		try {
			return new JumpDiffusionEdge (dblStart, dblDeterministic, new
				org.drip.measure.realization.StochasticEdgeDiffusion (dblDiffusionStochastic), sej, jdeu);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * JumpDiffusionEdge Constructor
	 * 
	 * @param dblStart The Starting Random Variable Realization
	 * @param dblDeterministic The Deterministic Increment Component
	 * @param sed The Stochastic Diffusion Edge Instance
	 * @param sej The Stochastic Jump Edge Instance
	 * @param jdeu The Random Unit Realization
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public JumpDiffusionEdge (
		final double dblStart,
		final double dblDeterministic,
		final org.drip.measure.realization.StochasticEdgeDiffusion sed,
		final org.drip.measure.realization.StochasticEdgeJump sej,
		final org.drip.measure.realization.JumpDiffusionEdgeUnit jdeu)
		throws java.lang.Exception
	{
		_sed = sed;
		_sej = sej;

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblStart = dblStart) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblDeterministic = dblDeterministic) || (null == _sed
				&& null == _sej) || null == (_jdeu = jdeu))
			throw new java.lang.Exception ("JumpDiffusionEdge Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Edge Time Increment
	 * 
	 * @return The Edge Time Increment
	 */

	public double timeIncrement()
	{
		return _jdeu.timeIncrement();
	}

	/**
	 * Retrieve the Start Realization
	 * 
	 * @return The Start Realization
	 */

	public double start()
	{
		return _dblStart;
	}

	/**
	 * Retrieve the Deterministic Component
	 * 
	 * @return The Deterministic Component
	 */

	public double deterministic()
	{
		return _dblDeterministic;
	}

	/**
	 * Retrieve the Diffusion Stochastic Component
	 * 
	 * @return The Diffusion Stochastic Component
	 */

	public double diffusionStochastic()
	{
		return null == _sed ? 0. : _sed.change();
	}

	/**
	 * Retrieve the Diffusion Wander Realization
	 * 
	 * @return The Diffusion Wander Realization
	 */

	public double diffusionWander()
	{
		return _jdeu.diffusion();
	}

	/**
	 * Retrieve the Jump Stochastic Component
	 * 
	 * @return The Jump Stochastic Component
	 */

	public double jumpStochastic()
	{
		return null == _sej ? 0. : _sej.target();
	}

	/**
	 * Retrieve the Jump Wander Realization
	 * 
	 * @return The Jump Wander Realization
	 */

	public double jumpWander()
	{
		return _jdeu.jump();
	}

	/**
	 * Retrieve the Finish Realization
	 * 
	 * @return The Finish Realization
	 */

	public double finish()
	{
		return null == _sej || !_sej.jumpOccurred() ? _dblStart + _dblDeterministic + diffusionStochastic() :
			_sej.target();
	}

	/**
	 * Retrieve the Gross Change
	 * 
	 * @return The Gross Change
	 */

	public double grossChange()
	{
		return finish() - _dblStart;
	}

	/**
	 * Retrieve the Stochastic Diffusion Edge Instance
	 * 
	 * @return The Stochastic Diffusion Edge Instance
	 */

	public org.drip.measure.realization.StochasticEdgeDiffusion stochasticDiffusionEdge()
	{
		return _sed;
	}

	/**
	 * Retrieve the Stochastic Jump Edge Instance
	 * 
	 * @return The Stochastic Jump Edge Instance
	 */

	public org.drip.measure.realization.StochasticEdgeJump stochasticJumpEdge()
	{
		return _sej;
	}
}
