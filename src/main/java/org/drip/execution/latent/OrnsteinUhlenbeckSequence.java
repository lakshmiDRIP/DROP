
package org.drip.execution.latent;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>OrnsteinUhlenbeckSequence</i> holds the Sequence of the Market State that drives the Liquidity and the
 * Volatility Market States driven using an Ornstein-Uhlenbeck Process. The References are:
 * 
 * <br><br>
 *  <ul>
 * 		<li>
 * 			Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 * 				https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf
 * 		</li>
 * 		<li>
 * 			Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility <i>SIAM Journal
 * 			of Financial Mathematics</i> <b>3 (1)</b> 163-181
 * 		</li>
 * 		<li>
 * 			Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes <i>Mathematical
 * 				Finance</i> <b>11 (1)</b> 79-96
 * 		</li>
 * 		<li>
 * 			Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility <i>Review of
 * 				Financial Studies</i> <b>7 (4)</b> 631-651
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution">Execution</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/latent">Latent</a></li>
 *  </ul>
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
