
package org.drip.capital.shell;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
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
 * <i>SystemicScenarioPnLSeries</i> contains the PnL Series of a Systemic Stress Scenario. The References
 *	are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision(2005): Stress Testing at Major Financial Institutions: Survey
 * 				Results and Practice https://www.bis.org/publ/cgfs24.htm
 * 		</li>
 * 		<li>
 * 			Glasserman, P. (2004): <i>Monte Carlo Methods in Financial Engineering</i> <b>Springer</b>
 * 		</li>
 * 		<li>
 * 			Kupiec, P. H. (2000): Stress Tests and Risk Capital <i>Risk</i> <b>2 (4)</b> 27-39
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md">Basel Market Risk and Operational Capital</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/shell/README.md">Economic Risk Capital Parameter Contexts</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SystemicScenarioPnLSeries
{
	private org.drip.capital.stress.PnLSeries _lostDecade = null;
	private org.drip.capital.stress.PnLSeries _baseline1974 = null;
	private org.drip.capital.stress.PnLSeries _baseline2008 = null;
	private org.drip.capital.stress.PnLSeries _deepDownturn = null;
	private org.drip.capital.stress.PnLSeries _dollarDecline = null;
	private org.drip.capital.stress.PnLSeries _interestRateShock = null;

	/**
	 * Construct the SystemicScenarioPnLSeries with Zeros
	 * 
	 * @return The SystemicScenarioPnLSeries with Zeros
	 */

	public static final SystemicScenarioPnLSeries ZERO()
	{
		try
		{
			return new SystemicScenarioPnLSeries (
				org.drip.capital.stress.PnLSeries.SingleZeroOutcome(),
				org.drip.capital.stress.PnLSeries.SingleZeroOutcome(),
				org.drip.capital.stress.PnLSeries.SingleZeroOutcome(),
				org.drip.capital.stress.PnLSeries.SingleZeroOutcome(),
				org.drip.capital.stress.PnLSeries.SingleZeroOutcome(),
				org.drip.capital.stress.PnLSeries.SingleZeroOutcome()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the SystemicScenarioPnLSeries with Single Outcome
	 * 
	 * @param baseline1974PnL 1974 Baseline PnL
	 * @param baseline2008PnL 2008 Baseline PnL
	 * @param deepDownturnPnL Deep Down-turn PnL
	 * @param dollarDeclinePnL Dollar Decline PnL
	 * @param interestRateShockPnL Interest Rate Shock PnL
	 * @param lostDecadePnL Lost Decade PnL
	 * 
	 * @return The SystemicScenarioPnLSeries with Single Outcome
	 */

	public static final SystemicScenarioPnLSeries SingleOutcome (
		final double baseline1974PnL,
		final double baseline2008PnL,
		final double deepDownturnPnL,
		final double dollarDeclinePnL,
		final double interestRateShockPnL,
		final double lostDecadePnL)
	{
		try
		{
			return new SystemicScenarioPnLSeries (
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					baseline1974PnL
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					baseline2008PnL
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					deepDownturnPnL
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					dollarDeclinePnL
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					interestRateShockPnL
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					lostDecadePnL
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Aggregate the Array of SystemicScenarioPnLSeries onto a Composite SystemicScenarioPnLSeries
	 * 
	 * @param systemicScenarioPnLSeriesArray Array of SystemicScenarioPnLSeries
	 * 
	 * @return The Aggregated, Composite SystemicScenarioPnLSeries
	 */

	public static final SystemicScenarioPnLSeries AggregateComposite (
		final SystemicScenarioPnLSeries[] systemicScenarioPnLSeriesArray)
	{
		if (null == systemicScenarioPnLSeriesArray)
		{
			return null;
		}

		double lostDecadePnLComposite = 0.;
		double baseline1974PnLComposite = 0.;
		double baseline2008PnLComposite = 0.;
		double deepDownturnPnLComposite = 0.;
		double dollarDeclinePnLComposite = 0.;
		double interestRateShockPnLComposite = 0.;
		int pnlCount = systemicScenarioPnLSeriesArray.length;

		if (0 == pnlCount)
		{
			return null;
		}

		for (int pnlIndex = 0;
			pnlIndex < pnlCount;
			++pnlIndex)
		{
			if (null != systemicScenarioPnLSeriesArray[pnlIndex])
			{
				lostDecadePnLComposite = lostDecadePnLComposite +
					systemicScenarioPnLSeriesArray[pnlIndex].lostDecade().composite();

				baseline1974PnLComposite = baseline1974PnLComposite +
					systemicScenarioPnLSeriesArray[pnlIndex].baseline1974().composite();

				baseline2008PnLComposite = baseline2008PnLComposite +
					systemicScenarioPnLSeriesArray[pnlIndex].baseline2008().composite();

				deepDownturnPnLComposite = deepDownturnPnLComposite +
					systemicScenarioPnLSeriesArray[pnlIndex].deepDownturn().composite();

				dollarDeclinePnLComposite = dollarDeclinePnLComposite +
					systemicScenarioPnLSeriesArray[pnlIndex].dollarDecline().composite();

				interestRateShockPnLComposite = interestRateShockPnLComposite +
					systemicScenarioPnLSeriesArray[pnlIndex].interestRateShock().composite();
			}
		}

		try
		{
			return new SystemicScenarioPnLSeries (
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					baseline1974PnLComposite
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					baseline2008PnLComposite
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					deepDownturnPnLComposite
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					dollarDeclinePnLComposite
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					interestRateShockPnLComposite
				),
				org.drip.capital.stress.PnLSeries.SingleOutcome (
					lostDecadePnLComposite
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * SystemicScenarioPnLSeries Constructor
	 * 
	 * @param baseline1974 1974 Baseline PnL Series
	 * @param baseline2008 2008 Baseline PnL Series
	 * @param deepDownturn Deep Down-turn PnL Series
	 * @param dollarDecline Dollar Decline PnL Series
	 * @param interestRateShock Interest Rate Shock PnL Series
	 * @param lostDecade Lost Decade PnL Series
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SystemicScenarioPnLSeries (
		final org.drip.capital.stress.PnLSeries baseline1974,
		final org.drip.capital.stress.PnLSeries baseline2008,
		final org.drip.capital.stress.PnLSeries deepDownturn,
		final org.drip.capital.stress.PnLSeries dollarDecline,
		final org.drip.capital.stress.PnLSeries interestRateShock,
		final org.drip.capital.stress.PnLSeries lostDecade)
		throws java.lang.Exception
	{
		if (null == (_baseline1974 = baseline1974) ||
			null == (_baseline2008 = baseline2008) ||
			null == (_deepDownturn = deepDownturn) ||
			null == (_dollarDecline = dollarDecline) ||
			null == (_interestRateShock = interestRateShock) ||
			null == (_lostDecade = lostDecade))
		{
			throw new java.lang.Exception (
				"SystemicScenarioPnLSeries Constructor => Invalid inputs"
			);
		}
	}

	/**
	 * Retrieve the 1974 Baseline PnL Series
	 * 
	 * @return The 1974 Baseline PnL Series
	 */

	public org.drip.capital.stress.PnLSeries baseline1974()
	{
		return _baseline1974;
	}

	/**
	 * Retrieve the 2008 Baseline PnL Series
	 * 
	 * @return The 2008 Baseline PnL Series
	 */

	public org.drip.capital.stress.PnLSeries baseline2008()
	{
		return _baseline2008;
	}

	/**
	 * Retrieve the Deep Down-turn PnL Series
	 * 
	 * @return The Deep Down-turn PnL Series
	 */

	public org.drip.capital.stress.PnLSeries deepDownturn()
	{
		return _deepDownturn;
	}

	/**
	 * Retrieve the Dollar Decline PnL Series
	 * 
	 * @return The Dollar Decline PnL Series
	 */

	public org.drip.capital.stress.PnLSeries dollarDecline()
	{
		return _dollarDecline;
	}

	/**
	 * Retrieve the Interest Rate Shock PnL Series
	 * 
	 * @return The Interest Rate Shock PnL Series
	 */

	public org.drip.capital.stress.PnLSeries interestRateShock()
	{
		return _interestRateShock;
	}

	/**
	 * Retrieve the Lost Decade PnL Series
	 * 
	 * @return The Lost Decade PnL Series
	 */

	public org.drip.capital.stress.PnLSeries lostDecade()
	{
		return _lostDecade;
	}

	@Override public java.lang.String toString()
	{
		return "[" + _baseline1974.toString() + " | " +
			_baseline2008.toString() + " | " +
			_deepDownturn.toString() + " | " +
			_dollarDecline.toString() + " | " +
			_interestRateShock.toString() + " | " +
			_lostDecade.toString() + "]";
	}
}
