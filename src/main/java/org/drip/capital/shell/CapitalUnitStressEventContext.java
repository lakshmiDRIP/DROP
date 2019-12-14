
package org.drip.capital.shell;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalUnitStressEventContext</i> maintains the GSST, iBSST, and cBSST Scenarios at the Capital Unit
 * Coordinate Level. The References
 * 	are:
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

public class CapitalUnitStressEventContext
{
	private java.util.Map<java.lang.String, org.drip.capital.entity.CapitalUnitEventContainer>
		_capitalUnitEventMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.entity.CapitalUnitEventContainer>();

	/**
	 * Empty CapitalUnitStressEventContext Constructor
	 */

	public CapitalUnitStressEventContext()
	{
	}

	/**
	 * Add a GSST Event to the Capital Unit Coordinate
	 * 
	 * @param capitalCoordinateFQN The Capital Coordinate FQN
	 * @param systemicScenarioPnLSeries Systemic Scenario PnL Series
	 * @param systemicScenarioPnLSeriesPAA Systemic Scenario PnL Series PAA
	 * 
	 * @return TRUE - GSST Event successfully created and added to the Capital Unit Event Map
	 */

	public boolean addGSST (
		final java.lang.String capitalCoordinateFQN,
		final org.drip.capital.shell.SystemicScenarioPnLSeries systemicScenarioPnLSeries,
		final org.drip.capital.shell.SystemicScenarioPnLSeriesPAA systemicScenarioPnLSeriesPAA)
	{
		if (null == systemicScenarioPnLSeries)
		{
			return false;
		}

		org.drip.capital.entity.CapitalUnitEventContainer capitalUnitEventContainer =
			new org.drip.capital.entity.CapitalUnitEventContainer();

		try
		{
			if (!capitalUnitEventContainer.addGSSTEvent (
				new org.drip.capital.stress.Event (
					org.drip.capital.stress.EventSpecification.GSST1974Baseline(),
					systemicScenarioPnLSeries.baseline1974(),
					null == systemicScenarioPnLSeriesPAA ?
						null : systemicScenarioPnLSeriesPAA.baseline1974DecompositionMap()
				)
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addGSSTEvent (
				new org.drip.capital.stress.Event (
					org.drip.capital.stress.EventSpecification.GSST2008Baseline(),
					systemicScenarioPnLSeries.baseline2008(),
					null == systemicScenarioPnLSeriesPAA ?
						null : systemicScenarioPnLSeriesPAA.baseline2008DecompositionMap()
				)
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addGSSTEvent (
				new org.drip.capital.stress.Event (
					org.drip.capital.stress.EventSpecification.GSSTDeepDownturn(),
					systemicScenarioPnLSeries.deepDownturn(),
					null == systemicScenarioPnLSeriesPAA ?
						null : systemicScenarioPnLSeriesPAA.deepDownturnDecompositionMap()
				)
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addGSSTEvent (
				new org.drip.capital.stress.Event (
					org.drip.capital.stress.EventSpecification.GSSTDollarDecline(),
					systemicScenarioPnLSeries.dollarDecline(),
					null == systemicScenarioPnLSeriesPAA ?
						null : systemicScenarioPnLSeriesPAA.dollarDeclineDecompositionMap()
				)
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addGSSTEvent (
				new org.drip.capital.stress.Event (
					org.drip.capital.stress.EventSpecification.GSSTInterestRateShock(),
					systemicScenarioPnLSeries.interestRateShock(),
					null == systemicScenarioPnLSeriesPAA ?
						null : systemicScenarioPnLSeriesPAA.interestRateShockDecompositionMap()
				)
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addGSSTEvent (
				new org.drip.capital.stress.Event (
					org.drip.capital.stress.EventSpecification.GSSTLostDecade(),
					systemicScenarioPnLSeries.lostDecade(),
					null == systemicScenarioPnLSeriesPAA ?
						null : systemicScenarioPnLSeriesPAA.lostDecadeDecompositionMap()
				)
			))
			{
				return false;
			}

			_capitalUnitEventMap.put (
				new org.drip.capital.label.CapitalSegmentCoordinate (
					capitalCoordinateFQN
				).fullyQualifiedName(),
				capitalUnitEventContainer
			);

			return true;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Add a cBSST Event to the Capital Unit Coordinate
	 * 
	 * @param capitalCoordinateFQN The Capital Coordinate FQN
	 * @param cBSSTEventName cBSST Event Name
	 * @param cBSSTSystemicScenarioPnLSeries cBSST Systemic Scenario PnL Series
	 * 
	 * @return TRUE - cBSST Event successfully created and added to the Capital Unit Event Map
	 */

	public boolean addCBSST (
		final java.lang.String capitalCoordinateFQN,
		final java.lang.String cBSSTEventName,
		final org.drip.capital.shell.SystemicScenarioPnLSeries cBSSTSystemicScenarioPnLSeries)
	{
		try
		{
			if (!_capitalUnitEventMap.containsKey (capitalCoordinateFQN))
			{
				addGSST (
					capitalCoordinateFQN,
					org.drip.capital.shell.SystemicScenarioPnLSeries.ZERO(),
					null
				);
			}

			org.drip.capital.entity.CapitalUnitEventContainer capitalUnitEventContainer =
				_capitalUnitEventMap.get (capitalCoordinateFQN);

			if (!capitalUnitEventContainer.addCBSSTEvent (
				org.drip.capital.definition.GSSTDefinition.BASELINE_1974,
				cBSSTEventName,
				cBSSTSystemicScenarioPnLSeries.baseline1974()
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addCBSSTEvent (
				org.drip.capital.definition.GSSTDefinition.BASELINE_2008,
				cBSSTEventName,
				cBSSTSystemicScenarioPnLSeries.baseline2008()
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addCBSSTEvent (
				org.drip.capital.definition.GSSTDefinition.DEEP_DOWNTURN,
				cBSSTEventName,
				cBSSTSystemicScenarioPnLSeries.deepDownturn()
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addCBSSTEvent (
				org.drip.capital.definition.GSSTDefinition.DOLLAR_DECLINE,
				cBSSTEventName,
				cBSSTSystemicScenarioPnLSeries.dollarDecline()
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addCBSSTEvent (
				org.drip.capital.definition.GSSTDefinition.INTEREST_RATE_SHOCK,
				cBSSTEventName,
				cBSSTSystemicScenarioPnLSeries.interestRateShock()
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addCBSSTEvent (
				org.drip.capital.definition.GSSTDefinition.LOST_DECADE,
				cBSSTEventName,
				cBSSTSystemicScenarioPnLSeries.lostDecade()
			))
			{
				return false;
			}

			return true;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Add a iBSST Event to the Capital Unit Coordinate
	 * 
	 * @param capitalUnitCoordinateFQN The Capital Unit Coordinate FQN
	 * @param scenarioName Scenario Name
	 * @param scenarioProbability Scenario Probability
	 * @param scenarioPnL Scenario PnL
	 * 
	 * @return TRUE - iBSST Event successfully created and added to the Capital Unit Event Map
	 */

	public boolean addIBSST (
		final java.lang.String capitalUnitCoordinateFQN,
		final java.lang.String scenarioName,
		final double scenarioProbability,
		final double scenarioPnL)
	{
		try
		{
			if (!_capitalUnitEventMap.containsKey (capitalUnitCoordinateFQN))
			{
				addGSST (
					capitalUnitCoordinateFQN,
					org.drip.capital.shell.SystemicScenarioPnLSeries.ZERO(),
					null
				);
			}

			org.drip.capital.entity.CapitalUnitEventContainer capitalUnitEventContainer =
				_capitalUnitEventMap.get (capitalUnitCoordinateFQN) ;

			if (!capitalUnitEventContainer.addIBSSTEvent (
				new org.drip.capital.stress.Event (
					new org.drip.capital.stress.EventSpecification (
						scenarioName,
						scenarioProbability
					),
					org.drip.capital.stress.PnLSeries.SingleOutcome (scenarioPnL),
					null
				)
			))
			{
				return false;
			}

			if (!_capitalUnitEventMap.containsKey (capitalUnitCoordinateFQN))
			{
				_capitalUnitEventMap.put (
					capitalUnitCoordinateFQN,
					capitalUnitEventContainer
				);
			}

			return true;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Retrieve all the Capital Unit Coordinates that have Entries in the Coordinate Scenario Stress Map for the
	 *  specified Risk Type and Region
	 * 
	 * @param region Region
	 * @param riskType Risk Type
	 * 
	 * @return The Capital Unit Coordinate Set
	 */

	public java.util.Set<java.lang.String> matchingCapitalUnitCoordinateSet (
		final java.lang.String region,
		final java.lang.String riskType)
	{
		if (null == region || region.isEmpty() ||
			null == riskType || riskType.isEmpty())
		{
			return null;
		}

		java.util.Set<java.lang.String> matchingCapitalUnitCoordinateFQNSet =
			new java.util.HashSet<java.lang.String>();

		java.util.Set<java.lang.String> capitalUnitCoordinateFQNSet = _capitalUnitEventMap.keySet();

		for (java.lang.String capitalUnitCoordinateFQN : capitalUnitCoordinateFQNSet)
		{
			org.drip.capital.label.BusinessRegionRiskTypeCoordinate capitalUnitCoordinate =
				org.drip.capital.label.BusinessRegionRiskTypeCoordinate.Standard (
					capitalUnitCoordinateFQN
				);

			if (null == capitalUnitCoordinate)
			{
				return null;
			}

			if (region.equalsIgnoreCase (capitalUnitCoordinate.region()) &&
				riskType.equalsIgnoreCase (capitalUnitCoordinate.riskType()))
			{
				matchingCapitalUnitCoordinateFQNSet.add (capitalUnitCoordinateFQN);
			}
		}

		return matchingCapitalUnitCoordinateFQNSet;
	}

	/**
	 * Retrieve all the Capital Unit Coordinates that have Entries in the Coordinate Scenario Stress Map for the
	 *  specified Region
	 * 
	 * @param region Region
	 * 
	 * @return The Child Coordinate Set
	 */

	public java.util.Set<java.lang.String> matchingCapitalUnitCoordinateSet (
		final java.lang.String region)
	{
		if (null == region || region.isEmpty())
		{
			return null;
		}

		java.util.Set<java.lang.String> matchingCapitalUnitCoordinateFQNSet =
			new java.util.HashSet<java.lang.String>();

		java.util.Set<java.lang.String> capitalUnitCoordinateFQNSet = _capitalUnitEventMap.keySet();

		for (java.lang.String capitalUnitCoordinateFQN : capitalUnitCoordinateFQNSet)
		{
			org.drip.capital.label.BusinessRegionRiskTypeCoordinate capitalUnitCoordinate =
				org.drip.capital.label.BusinessRegionRiskTypeCoordinate.Standard (
					capitalUnitCoordinateFQN
				);

			if (null == capitalUnitCoordinate)
			{
				return null;
			}

			if (region.equalsIgnoreCase (capitalUnitCoordinate.region()))
			{
				matchingCapitalUnitCoordinateFQNSet.add (capitalUnitCoordinateFQN);
			}
		}

		System.out.println (matchingCapitalUnitCoordinateFQNSet);

		return matchingCapitalUnitCoordinateFQNSet;
	}

	/**
	 * Retrieve the Capital Unit Stress Map
	 * 
	 * @return The Capital Unit Stress Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.entity.CapitalUnitEventContainer>
		capitalUnitEventMap()
	{
		return _capitalUnitEventMap;
	}
}
