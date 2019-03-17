
package org.drip.execution.impact;

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
 * <i>ParticipationRateLinear</i> implements a Linear Temporary/Permanent Market Impact Function where the
 * Price Change scales linearly with the Trade Rate, along with an Offset. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/impact/README.md">Impact</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ParticipationRateLinear extends org.drip.execution.impact.TransactionFunctionLinear {
	private double _dblSlope = java.lang.Double.NaN;
	private double _dblOffset = java.lang.Double.NaN;

	/**
	 * Construct a Vanilla Zero-Impact ParticipationRateLinear Instance
	 * 
	 * @return The Vanilla Zero-Impact ParticipationRateLinear Instance
	 */

	public static final ParticipationRateLinear NoImpact()
	{
		try {
			return new ParticipationRateLinear (0., 0.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Vanilla Slope-Only ParticipationRateLinear Instance
	 * 
	 * @param dblSlope The Slope
	 *  
	 * @return The Vanilla Slope-Only ParticipationRateLinear Instance
	 */

	public static final ParticipationRateLinear SlopeOnly (
		final double dblSlope)
	{
		try {
			return new ParticipationRateLinear (0., dblSlope);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ParticipationRateLinear Constructor
	 * 
	 * @param dblOffset The Offset Market Impact Parameter
	 * @param dblSlope The Linear Market Impact Slope Parameter
	 * 
	 * @throws java.lang.Exception Propagated up from R1ToR1
	 */

	public ParticipationRateLinear (
		final double dblOffset,
		final double dblSlope)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblOffset = dblOffset) || 0. > _dblOffset ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblSlope = dblSlope) || 0. > _dblSlope)
			throw new java.lang.Exception ("ParticipationRateLinear Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Linear Market Impact Slope Parameter
	 * 
	 * @return The Linear Market Impact Slope Parameter
	 */

	public double slope()
	{
		return _dblSlope;
	}

	/**
	 * Retrieve the Offset Market Impact Parameter
	 * 
	 * @return The Offset Market Impact Parameter
	 */

	public double offset()
	{
		return _dblOffset;
	}

	@Override public double regularize (
		final double dblTradeInterval)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblTradeInterval) || 0 >= dblTradeInterval)
			throw new java.lang.Exception ("ParticipationRateLinear::regularize => Invalid Inputs");

		return 1. / dblTradeInterval;
	}

	@Override public double modulate (
		final double dblTradeInterval)
		throws java.lang.Exception
	{
		return 1.;
	}

	@Override public double evaluate  (
		final double dblTradeRate)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblTradeRate))
			throw new java.lang.Exception ("ParticipationRateLinear::evaluate => Invalid Inputs");

		return (dblTradeRate < 0. ? -1. : 1.) * _dblOffset + _dblSlope * dblTradeRate;
	}

	@Override public double derivative  (
		final double dblTradeRate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (0 >= iOrder)
			throw new java.lang.Exception ("ParticipationRateLinear::derivative => Invalid Inputs");

		return 1 == iOrder ? _dblSlope : 0.;
	}
}
