
package org.drip.capital.shell;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>SystemicScenarioPnLSeriesPAA</i> contains the PAA Category Decomposition of the PnL Series of a
 * 	Systemic Stress Scenario. The References are:
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

public class SystemicScenarioPnLSeriesPAA
{
	private java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> _lostDecadeDecompositionMap
		= new org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.stress.PnLSeries>();

	private java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries>
		_baseline1974DecompositionMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.stress.PnLSeries>();

	private java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries>
		_baseline2008DecompositionMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.stress.PnLSeries>();

	private java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries>
		_deepDownturnDecompositionMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.stress.PnLSeries>();

	private java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries>
		_dollarDeclineDecompositionMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.stress.PnLSeries>();

	private java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries>
		_interestRateShockDecompositionMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.stress.PnLSeries>();

	private static final boolean UpdateMap (
		final java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> map,
		final java.lang.String key,
		final double value)
	{
		if (map.containsKey (
			key
		))
		{
			return false;
		}

		map.put (
			key,
			org.drip.capital.stress.PnLSeries.SingleOutcome (
				value
			)
		);

		return true;
	}

	private static final double AggregateEntryValues (
		final java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> map)
	{
		if (0 == map.size())
		{
			return 0.;
		}

		double aggregate = 0.;

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.stress.PnLSeries> entry :
			map.entrySet())
		{
			aggregate = aggregate + entry.getValue().composite();
		}

		return aggregate;
	}

	/**
	 * Empty SystemicScenarioPnLSeriesPAA Constructor
	 */

	public SystemicScenarioPnLSeriesPAA()
	{
	}

	/**
	 * Retrieve the 1974 Baseline PAA Category PnL Decomposition Map
	 * 
	 * @return 1974 Baseline PAA Category PnL Decomposition Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries>
		baseline1974DecompositionMap()
	{
		return _baseline1974DecompositionMap;
	}

	/**
	 * Retrieve the 2008 Baseline PAA Category PnL Decomposition Map
	 * 
	 * @return 2008 Baseline PAA Category PnL Decomposition Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries>
		baseline2008DecompositionMap()
	{
		return _baseline2008DecompositionMap;
	}

	/**
	 * Retrieve the Deep Down-turn PAA Category PnL Decomposition Map
	 * 
	 * @return Deep Down-turn PAA Category PnL Decomposition Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries>
		deepDownturnDecompositionMap()
	{
		return _deepDownturnDecompositionMap;
	}

	/**
	 * Retrieve the Dollar Decline PAA Category PnL Decomposition Map
	 * 
	 * @return Dollar Decline PAA Category PnL Decomposition Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries>
		dollarDeclineDecompositionMap()
	{
		return _dollarDeclineDecompositionMap;
	}

	/**
	 * Retrieve the Interest Rate Shock PAA Category PnL Decomposition Map
	 * 
	 * @return Interest Rate Shock PAA Category PnL Decomposition Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries>
		interestRateShockDecompositionMap()
	{
		return _interestRateShockDecompositionMap;
	}

	/**
	 * Retrieve the Lost Decade PAA Category PnL Decomposition Map
	 * 
	 * @return Lost Decade PAA Category PnL Decomposition Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> lostDecadeDecompositionMap()
	{
		return _lostDecadeDecompositionMap;
	}

	/**
	 * Add a Decomposed PnL Entry for the Specified Systemic Scenario and PAA Category
	 *  
	 * @param systemicScenarioName Systemic Scenario Name
	 * @param paaCategoryName PAA Category Name
	 * @param pnlDecomposition PnL Entry
	 * 
	 * @return TRUE - The Entry is successfully added
	 */

	public boolean addDecompositionEntry (
		final java.lang.String systemicScenarioName,
		final java.lang.String paaCategoryName,
		final double pnlDecomposition)
	{
		if (null == systemicScenarioName || systemicScenarioName.isEmpty() ||
			null == paaCategoryName || paaCategoryName.isEmpty() ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				pnlDecomposition
			)
		)
		{
			return false;
		}

		if (org.drip.capital.definition.SystemicScenarioDefinition.BASELINE_1974.equalsIgnoreCase (
			systemicScenarioName
		))
		{
			return UpdateMap (
				_baseline1974DecompositionMap,
				paaCategoryName,
				pnlDecomposition
			);
		}

		if (org.drip.capital.definition.SystemicScenarioDefinition.BASELINE_2008.equalsIgnoreCase (
			systemicScenarioName
		))
		{
			return UpdateMap (
				_baseline2008DecompositionMap,
				paaCategoryName,
				pnlDecomposition
			);
		}

		if (org.drip.capital.definition.SystemicScenarioDefinition.DEEP_DOWNTURN.equalsIgnoreCase (
			systemicScenarioName
		))
		{
			return UpdateMap (
				_deepDownturnDecompositionMap,
				paaCategoryName,
				pnlDecomposition
			);
		}

		if (org.drip.capital.definition.SystemicScenarioDefinition.DOLLAR_DECLINE.equalsIgnoreCase (
			systemicScenarioName
		))
		{
			return UpdateMap (
				_dollarDeclineDecompositionMap,
				paaCategoryName,
				pnlDecomposition
			);
		}

		if (org.drip.capital.definition.SystemicScenarioDefinition.INTEREST_RATE_SHOCK.equalsIgnoreCase (
			systemicScenarioName
		))
		{
			return UpdateMap (
				_interestRateShockDecompositionMap,
				paaCategoryName,
				pnlDecomposition
			);
		}

		if (org.drip.capital.definition.SystemicScenarioDefinition.LOST_DECADE.equalsIgnoreCase (
			systemicScenarioName
		))
		{
			return UpdateMap (
				_lostDecadeDecompositionMap,
				paaCategoryName,
				pnlDecomposition
			);
		}

		return false;
	}

	/**
	 * Generate the Aggregated GSST PnL
	 * 
	 * @return Aggregated GSST PnL
	 */

	public org.drip.capital.shell.SystemicScenarioPnLSeries aggregatePnL()
	{
		return SystemicScenarioPnLSeries.SingleOutcome (
			AggregateEntryValues (
				_baseline1974DecompositionMap
			),
			AggregateEntryValues (
				_baseline2008DecompositionMap
			),
			AggregateEntryValues (
				_deepDownturnDecompositionMap
			),
			AggregateEntryValues (
				_dollarDeclineDecompositionMap
			),
			AggregateEntryValues (
				_interestRateShockDecompositionMap
			),
			AggregateEntryValues (
				_lostDecadeDecompositionMap
			)
		);
	}

	@Override public java.lang.String toString()
	{
		return "[" +
			"{" + _baseline1974DecompositionMap.toString() + "} | " +
			"{" + _baseline2008DecompositionMap.toString() + "} | " +
			"{" + _deepDownturnDecompositionMap.toString() + "} | " +
			"{" + _dollarDeclineDecompositionMap.toString() + "} | " +
			"{" + _interestRateShockDecompositionMap.toString() + "} | " +
			"{" + _lostDecadeDecompositionMap.toString() + "} | " +
		"]";
	}
}
