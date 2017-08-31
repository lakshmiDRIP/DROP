
package org.drip.execution.latent;

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
 * OrnsteinUhlenbeckSequence holds the Sequence of the Market State that drives the Liquidity and the
 *  Volatility Market States driven using an Ornstein-Uhlenbeck Process. The References are:
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

public class OrnsteinUhlenbeckSequence {
	private int _iCount = 0;
	private org.drip.execution.latent.MarketState[] _aMS = null;
	private double _dblGenerationInterval = java.lang.Double.NaN;
	private org.drip.measure.process.OrnsteinUhlenbeck _ou = null;

	/**
	 * Construct a Standard Systemic Instance of OrnsteinUhlenbeckSequence
	 * 
	 * @param deou The 1D Ornstein-Uhlenbeck Generator Scheme
	 * @param dblGenerationInterval The Generation Interval
	 * @param dblInitialMarketState The Initial Market State
	 * @param iCount Count of the Number of States to be generated
	 * 
	 * @return The OrnsteinUhlenbeckSequence Instance
	 */

	public static final OrnsteinUhlenbeckSequence Systemic (
		final org.drip.measure.dynamics.DiffusionEvaluatorOrnsteinUhlenbeck deou,
		final double dblGenerationInterval,
		final double dblInitialMarketState,
		final int iCount)
	{
		if (null == deou || !org.drip.quant.common.NumberUtil.IsValid (dblGenerationInterval) || 0 >=
			dblGenerationInterval || 1 >= iCount)
			return null;

		double dblTime = 0.;
		org.drip.execution.latent.MarketStateSystemic[] aMSS = new
			org.drip.execution.latent.MarketStateSystemic[iCount];

		try {
			aMSS[0] = new org.drip.execution.latent.MarketStateSystemic (dblInitialMarketState);

			org.drip.measure.process.DiffusionEvolver de = new org.drip.measure.process.DiffusionEvolver
				(deou);

			for (int i = 0; i < iCount - 1; ++i) {
				org.drip.measure.realization.JumpDiffusionEdge gi = de.weinerIncrement (new
					org.drip.measure.realization.JumpDiffusionVertex (dblTime, aMSS[i].common(), 0., false),
						dblGenerationInterval);

				aMSS[i + 1] = new org.drip.execution.latent.MarketStateSystemic (aMSS[i].common() +
					gi.deterministic() + gi.diffusionStochastic());

				dblTime += dblGenerationInterval;
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return new OrnsteinUhlenbeckSequence (deou, aMSS, dblGenerationInterval);
	}

	/**
	 * Construct a Standard Correlated Instance of OrnsteinUhlenbeckSequence
	 * 
	 * @param oup2D The 2D Ornstein-Uhlenbeck Generator Scheme
	 * @param dblGenerationInterval The Generation Interval
	 * @param dblInitialLiquidityMarketState The Initial Liquidity Market State
	 * @param dblInitialVolatilityMarketState The Initial Volatility Market State
	 * @param iCount Count of the Number of States to be generated
	 * 
	 * @return The OrnsteinUhlenbeckSequence Instance
	 */

	public static final OrnsteinUhlenbeckSequence Correlated (
		final org.drip.measure.process.OrnsteinUhlenbeckPair oup2D,
		final double dblGenerationInterval,
		final double dblInitialLiquidityMarketState,
		final double dblInitialVolatilityMarketState,
		final int iCount)
	{
		if (null == oup2D || !org.drip.quant.common.NumberUtil.IsValid (dblGenerationInterval) || 0 >=
			dblGenerationInterval || 1 >= iCount)
			return null;

		org.drip.execution.latent.MarketStateCorrelated[] aMSC = new
			org.drip.execution.latent.MarketStateCorrelated[iCount];

		try {
			aMSC[0] = new org.drip.execution.latent.MarketStateCorrelated (dblInitialLiquidityMarketState,
				dblInitialVolatilityMarketState);

			for (int i = 0; i < iCount - 1; ++i) {
				org.drip.measure.realization.JumpDiffusionEdge[] aGI = oup2D.weinerIncrement
					(aMSC[i].realization(), dblGenerationInterval);

				if (null == aGI || 2 != aGI.length) return null;

				aMSC[i + 1] = new org.drip.execution.latent.MarketStateCorrelated (aMSC[i].liquidity() +
					aGI[0].deterministic() + aGI[0].diffusionStochastic(), aMSC[i].volatility() +
						aGI[1].deterministic() + aGI[1].diffusionStochastic());
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return new OrnsteinUhlenbeckSequence (oup2D, aMSC, dblGenerationInterval);
	}

	private OrnsteinUhlenbeckSequence (
		final org.drip.measure.process.OrnsteinUhlenbeck ou,
		final org.drip.execution.latent.MarketState[] aMS,
		final double dblGenerationInterval)
	{
		_ou = ou;
		_aMS = aMS;
		_iCount = aMS.length;
		_dblGenerationInterval = dblGenerationInterval;
	}

	/**
	 * Retrieve the Total Count of States realized
	 * 
	 * @return The Total Count of States realized
	 */

	public int count()
	{
		return _iCount;
	}

	/**
	 * Retrieve the Generation Interval
	 * 
	 * @return The Generation Interval
	 */

	public double generationInterval()
	{
		return _dblGenerationInterval;
	}

	/**
	 * Retrieve the Sequence of Market State Realization
	 * 
	 * @return The Sequence of Market State Realization
	 */

	public org.drip.execution.latent.MarketState[] realizedMarketState()
	{
		return _aMS;
	}

	/**
	 * Retrieve the Ornstein-Uhlenbeck Generator Scheme Parameters
	 * 
	 * @return The Ornstein-Uhlenbeck Generator Scheme Parameters
	 */

	public org.drip.measure.process.OrnsteinUhlenbeck scheme()
	{
		return _ou;
	}

	/**
	 * Retrieve the Initial Market State
	 * 
	 * @return The Initial Market State
	 */

	public org.drip.execution.latent.MarketState initialMarketState()
	{
		return _aMS[0];
	}
}
