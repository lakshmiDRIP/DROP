
package org.drip.capital.shell;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
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
		if (map.containsKey (key))
		{
			return false;
		}

		map.put (
			key,
			org.drip.capital.stress.PnLSeries.SingleOutcome (value)
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
	 * Empty SystemicScenaSystemicScenarioPnLSeriesPAArioPnLPAA Constructor
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
	 * Add a Decomposed PnL Entry for the Specified GSST Scenario and PAA Category
	 *  
	 * @param gsstScenarioName GSST Scenario Name
	 * @param paaCategoryName PAA Category Name
	 * @param pnlDecomposition PnL Entry
	 * 
	 * @return TRUE - The Entry is successfully added
	 */

	public boolean addDecompositionEntry (
		final java.lang.String gsstScenarioName,
		final java.lang.String paaCategoryName,
		final double pnlDecomposition)
	{
		if (null == gsstScenarioName || gsstScenarioName.isEmpty() ||
			null == paaCategoryName || paaCategoryName.isEmpty() ||
			!org.drip.numerical.common.NumberUtil.IsValid (pnlDecomposition))
		{
			return false;
		}

		if (org.drip.capital.definition.GSSTDefinition.BASELINE_1974.equalsIgnoreCase (gsstScenarioName))
		{
			return UpdateMap (
				_baseline1974DecompositionMap,
				paaCategoryName,
				pnlDecomposition
			);
		}

		if (org.drip.capital.definition.GSSTDefinition.BASELINE_2008.equalsIgnoreCase (gsstScenarioName))
		{
			return UpdateMap (
				_baseline2008DecompositionMap,
				paaCategoryName,
				pnlDecomposition
			);
		}

		if (org.drip.capital.definition.GSSTDefinition.DEEP_DOWNTURN.equalsIgnoreCase (gsstScenarioName))
		{
			return UpdateMap (
				_deepDownturnDecompositionMap,
				paaCategoryName,
				pnlDecomposition
			);
		}

		if (org.drip.capital.definition.GSSTDefinition.DOLLAR_DECLINE.equalsIgnoreCase (gsstScenarioName))
		{
			return UpdateMap (
				_dollarDeclineDecompositionMap,
				paaCategoryName,
				pnlDecomposition
			);
		}

		if (org.drip.capital.definition.GSSTDefinition.INTEREST_RATE_SHOCK.equalsIgnoreCase (gsstScenarioName))
		{
			return UpdateMap (
				_interestRateShockDecompositionMap,
				paaCategoryName,
				pnlDecomposition
			);
		}

		if (org.drip.capital.definition.GSSTDefinition.LOST_DECADE.equalsIgnoreCase (gsstScenarioName))
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
