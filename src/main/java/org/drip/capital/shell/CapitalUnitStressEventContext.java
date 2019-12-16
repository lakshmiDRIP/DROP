
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
 * <i>CapitalUnitStressEventContext</i> maintains the Systemic, Idiosyncratic, and Correlated Scenarios at
 * 	the Capital Unit Coordinate Level. The References are:
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
	 * Add a Systemic Event to the Capital Unit Coordinate
	 * 
	 * @param capitalCoordinateFQN The Capital Coordinate FQN
	 * @param systemicScenarioPnLSeries Systemic Scenario PnL Series
	 * @param systemicScenarioPnLSeriesPAA Systemic Scenario PnL Series PAA
	 * 
	 * @return TRUE - Systemic Event successfully created and added to the Capital Unit Event Map
	 */

	public boolean addSystemic (
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
			if (!capitalUnitEventContainer.addSystemicEvent (
				new org.drip.capital.stress.Event (
					org.drip.capital.stress.EventSpecification.Systemic1974Baseline(),
					systemicScenarioPnLSeries.baseline1974(),
					null == systemicScenarioPnLSeriesPAA ?
						null : systemicScenarioPnLSeriesPAA.baseline1974DecompositionMap()
				)
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addSystemicEvent (
				new org.drip.capital.stress.Event (
					org.drip.capital.stress.EventSpecification.Systemic2008Baseline(),
					systemicScenarioPnLSeries.baseline2008(),
					null == systemicScenarioPnLSeriesPAA ?
						null : systemicScenarioPnLSeriesPAA.baseline2008DecompositionMap()
				)
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addSystemicEvent (
				new org.drip.capital.stress.Event (
					org.drip.capital.stress.EventSpecification.SystemicDeepDownturn(),
					systemicScenarioPnLSeries.deepDownturn(),
					null == systemicScenarioPnLSeriesPAA ?
						null : systemicScenarioPnLSeriesPAA.deepDownturnDecompositionMap()
				)
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addSystemicEvent (
				new org.drip.capital.stress.Event (
					org.drip.capital.stress.EventSpecification.SystemicDollarDecline(),
					systemicScenarioPnLSeries.dollarDecline(),
					null == systemicScenarioPnLSeriesPAA ?
						null : systemicScenarioPnLSeriesPAA.dollarDeclineDecompositionMap()
				)
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addSystemicEvent (
				new org.drip.capital.stress.Event (
					org.drip.capital.stress.EventSpecification.SystemicInterestRateShock(),
					systemicScenarioPnLSeries.interestRateShock(),
					null == systemicScenarioPnLSeriesPAA ?
						null : systemicScenarioPnLSeriesPAA.interestRateShockDecompositionMap()
				)
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addSystemicEvent (
				new org.drip.capital.stress.Event (
					org.drip.capital.stress.EventSpecification.SystemicLostDecade(),
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
	 * Add a Correlated Event to the Capital Unit Coordinate
	 * 
	 * @param capitalCoordinateFQN The Capital Coordinate FQN
	 * @param correlatedEventName Correlated Event Name
	 * @param correlatedSystemicScenarioPnLSeries Correlated Systemic Scenario PnL Series
	 * 
	 * @return TRUE - Correlated Event successfully created and added to the Capital Unit Event Map
	 */

	public boolean addCorrelated (
		final java.lang.String capitalCoordinateFQN,
		final java.lang.String correlatedEventName,
		final org.drip.capital.shell.SystemicScenarioPnLSeries correlatedSystemicScenarioPnLSeries)
	{
		try
		{
			if (!_capitalUnitEventMap.containsKey (
				capitalCoordinateFQN
			))
			{
				addSystemic (
					capitalCoordinateFQN,
					org.drip.capital.shell.SystemicScenarioPnLSeries.ZERO(),
					null
				);
			}

			org.drip.capital.entity.CapitalUnitEventContainer capitalUnitEventContainer =
				_capitalUnitEventMap.get (
					capitalCoordinateFQN
				);

			if (!capitalUnitEventContainer.addCorrelatedEvent (
				org.drip.capital.definition.SystemicScenarioDefinition.BASELINE_1974,
				correlatedEventName,
				correlatedSystemicScenarioPnLSeries.baseline1974()
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addCorrelatedEvent (
				org.drip.capital.definition.SystemicScenarioDefinition.BASELINE_2008,
				correlatedEventName,
				correlatedSystemicScenarioPnLSeries.baseline2008()
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addCorrelatedEvent (
				org.drip.capital.definition.SystemicScenarioDefinition.DEEP_DOWNTURN,
				correlatedEventName,
				correlatedSystemicScenarioPnLSeries.deepDownturn()
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addCorrelatedEvent (
				org.drip.capital.definition.SystemicScenarioDefinition.DOLLAR_DECLINE,
				correlatedEventName,
				correlatedSystemicScenarioPnLSeries.dollarDecline()
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addCorrelatedEvent (
				org.drip.capital.definition.SystemicScenarioDefinition.INTEREST_RATE_SHOCK,
				correlatedEventName,
				correlatedSystemicScenarioPnLSeries.interestRateShock()
			))
			{
				return false;
			}

			if (!capitalUnitEventContainer.addCorrelatedEvent (
				org.drip.capital.definition.SystemicScenarioDefinition.LOST_DECADE,
				correlatedEventName,
				correlatedSystemicScenarioPnLSeries.lostDecade()
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
	 * Add a Idiosyncratic Event to the Capital Unit Coordinate
	 * 
	 * @param capitalUnitCoordinateFQN The Capital Unit Coordinate FQN
	 * @param scenarioName Scenario Name
	 * @param scenarioProbability Scenario Probability
	 * @param scenarioPnL Scenario PnL
	 * 
	 * @return TRUE - Idiosyncratic Event successfully created and added to the Capital Unit Event Map
	 */

	public boolean addIdiosyncratic (
		final java.lang.String capitalUnitCoordinateFQN,
		final java.lang.String scenarioName,
		final double scenarioProbability,
		final double scenarioPnL)
	{
		try
		{
			if (!_capitalUnitEventMap.containsKey (
				capitalUnitCoordinateFQN
			))
			{
				addSystemic (
					capitalUnitCoordinateFQN,
					org.drip.capital.shell.SystemicScenarioPnLSeries.ZERO(),
					null
				);
			}

			org.drip.capital.entity.CapitalUnitEventContainer capitalUnitEventContainer =
				_capitalUnitEventMap.get (
					capitalUnitCoordinateFQN
				);

			if (!capitalUnitEventContainer.addIdiosyncraticEvent (
				new org.drip.capital.stress.Event (
					new org.drip.capital.stress.EventSpecification (
						scenarioName,
						scenarioProbability
					),
					org.drip.capital.stress.PnLSeries.SingleOutcome (
						scenarioPnL
					),
					null
				)
			))
			{
				return false;
			}

			if (!_capitalUnitEventMap.containsKey (
				capitalUnitCoordinateFQN
			))
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

			if (region.equalsIgnoreCase (
					capitalUnitCoordinate.region()
				) && riskType.equalsIgnoreCase (
					capitalUnitCoordinate.riskType()
				)
			)
			{
				matchingCapitalUnitCoordinateFQNSet.add (
					capitalUnitCoordinateFQN
				);
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

			if (region.equalsIgnoreCase (
				capitalUnitCoordinate.region()
			))
			{
				matchingCapitalUnitCoordinateFQNSet.add (
					capitalUnitCoordinateFQN
				);
			}
		}

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
