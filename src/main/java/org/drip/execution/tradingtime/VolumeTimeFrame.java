
package org.drip.execution.tradingtime;

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
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
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
 * <i>VolumeTimeFrame</i> implements the Pre- and Post-transformed Increment in the Volume Time Space as used
 * in the "Trading Time" Model. The References are:
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Execution</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/tradingtime/README.md">Trading Time</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class VolumeTimeFrame extends org.drip.measure.realization.JumpDiffusionEdge {
	private double _dblHoldings = java.lang.Double.NaN;
	private double _dblTradeRate = java.lang.Double.NaN;

	/**
	 * VolumeTimeFrame Constructor
	 * 
	 * @param dblTimeIncrement Time Increment
	 * @param dblPrevious The Previous Realization
	 * @param dblTemporal The Temporal Increment
	 * @param dblBrownian The Brownian Increment
	 * @param dblVolatility The Volatility
	 * @param dblHoldings Current Holdings
	 * @param dblTradeRate Current Trade Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public VolumeTimeFrame (
		final double dblTimeIncrement,
		final double dblPrevious,
		final double dblTemporal,
		final double dblBrownian,
		final double dblVolatility,
		final double dblHoldings,
		final double dblTradeRate)
		throws java.lang.Exception
	{
		super (dblPrevious, dblVolatility * dblVolatility * dblTemporal, new
			org.drip.measure.realization.StochasticEdgeDiffusion (dblVolatility * dblBrownian), null, new
				org.drip.measure.realization.JumpDiffusionEdgeUnit (dblTimeIncrement, dblBrownian, 0.));

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblHoldings = dblHoldings) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblTradeRate = dblTradeRate / (dblVolatility *
				dblVolatility)))
			throw new java.lang.Exception ("VolumeTimeFrame Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Holdings
	 * 
	 * @return The Holdings
	 */

	public double holdings()
	{
		return _dblHoldings;
	}

	/**
	 * Retrieve the Trade Rate
	 * 
	 * @return The Trade Rate
	 */

	public double tradeRate()
	{
		return _dblTradeRate;
	}

	/**
	 * Generate the Transaction Cost Increment
	 * 
	 * @param cv The Coordinated Variation Parameters
	 * 
	 * @return The Transaction Cost Increment
	 * 
	 * @throws java.lang.Exception Throw if the Inputs are Invalid
	 */

	public double transactionCostIncrement (
		final org.drip.execution.tradingtime.CoordinatedVariation cv)
		throws java.lang.Exception
	{
		if (null == cv)
			throw new java.lang.Exception ("VolumeTimeFrame::transactionCostIncrement => Invalid Inputs");

		return _dblHoldings * diffusionStochastic() + cv.invariant() * _dblTradeRate * _dblTradeRate *
			deterministic();
	}
}
