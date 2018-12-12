
package org.drip.execution.parameters;

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
 * <i>PriceMarketImpactPower</i> contains the Power Law based Price Market Impact Inputs used in the
 * Construction of the Impact Parameters for the Almgren and Chriss (2000) Optimal Trajectory Generation
 * Scheme. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 * 		</li>
 * 		<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 * 				Markets</i> <b>1</b> 1-50
 * 		</li>
 * 		<li>
 * 			Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional
 * 				Trades <i>Journal of Finance</i> <b>50</b> 1147-1174
 * 		</li>
 * 		<li>
 * 			Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange
 * 				Analysis of Institutional Equity Trades <i>Journal of Financial Economics</i> <b>46</b>
 * 				265-292
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Execution</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/parameters/README.md">Parameters</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PriceMarketImpactPower extends org.drip.execution.parameters.PriceMarketImpact {
	private double _dblTemporaryImpactExponent = java.lang.Double.NaN;
	private double _dblDailyVolumeExecutionFactor = java.lang.Double.NaN;

	/**
	 * PriceMarketImpactPower Constructor
	 * 
	 * @param ats The Asset Transaction Settings Instance
	 * @param dblPermanentImpactFactor The Fraction of the Daily Volume that triggers One Bid-Ask of
	 *  Permanent Impact Cost
	 * @param dblTemporaryImpactFactor The Fraction of the Daily Volume that triggers One Bid-Ask of
	 *  Temporary Impact Cost
	 * @param dblDailyVolumeExecutionFactor The Daily Reference Execution Rate as a Proportion of the Daily
	 * 	Volume
	 * @param dblTemporaryImpactExponent The Temporary Impact Exponent
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PriceMarketImpactPower (
		final org.drip.execution.parameters.AssetTransactionSettings ats,
		final double dblPermanentImpactFactor,
		final double dblTemporaryImpactFactor,
		final double dblDailyVolumeExecutionFactor,
		final double dblTemporaryImpactExponent)
		throws java.lang.Exception
	{
		super (ats, dblPermanentImpactFactor, dblTemporaryImpactFactor);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblDailyVolumeExecutionFactor =
			dblDailyVolumeExecutionFactor) || 0. >= _dblDailyVolumeExecutionFactor ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblTemporaryImpactExponent =
					dblTemporaryImpactExponent))
			throw new java.lang.Exception ("PriceMarketImpactPower Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Daily Reference Execution Rate as a Proportion of the Daily Volume
	 * 
	 * @return The Daily Reference Execution Rate as a Proportion of the Daily Volume
	 */

	public double dailyVolumeExecutionFactor()
	{
		return _dblDailyVolumeExecutionFactor;
	}

	/**
	 * Generate the Permanent Impact Transaction Function
	 * 
	 * @return The Permanent Impact Transaction Function
	 */

	public org.drip.execution.impact.TransactionFunction permanentTransactionFunction()
	{
		try {
			return new org.drip.execution.impact.ParticipationRateLinear (0., 0.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Temporary Impact Transaction Function
	 * 
	 * @return The Temporary Impact Transaction Function
	 */

	public org.drip.execution.impact.TransactionFunction temporaryTransactionFunction()
	{
		org.drip.execution.parameters.AssetTransactionSettings ats = ats();

		try {
			return new org.drip.execution.impact.ParticipationRatePower (ats.price() *
				temporaryImpactFactor() / java.lang.Math.pow (ats.backgroundVolume() *
					_dblDailyVolumeExecutionFactor, _dblTemporaryImpactExponent),
						_dblTemporaryImpactExponent);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
