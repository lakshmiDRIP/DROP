
package org.drip.capital.entity;

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
 * <i>CapitalUnitEventContainer</i> contains all the Stress Event Specifications across all of the Event Types that
 * 	belong inside of the a Capital Unit. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/entity/README.md">Economic Risk Capital Estimation Nodes</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CapitalUnitEventContainer
{
	private org.drip.capital.stress.SystemicEventContainer _systemicEventContainer = null;
	private org.drip.capital.stress.IdiosyncraticEventContainer _idiosyncraticEventContainer = null;

	/**
	 * Empty CapitalUnitEventContainer Constructor
	 */

	public CapitalUnitEventContainer()
	{
	}

	/**
	 * Add Systemic Event
	 * 
	 * @param systemicEvent The Systemic Event
	 * 
	 * @return TRUE - The Systemic Event successfully added
	 */

	public boolean addSystemicEvent (
		final org.drip.capital.stress.Event systemicEvent)
	{
		if (null == _systemicEventContainer)
		{
			_systemicEventContainer = new org.drip.capital.stress.SystemicEventContainer();
		}

		return _systemicEventContainer.addEvent (
			systemicEvent
		);
	}

	/**
	 * Indicate if the Systemic Event is Available
	 * 
	 * @param systemicEventName Systemic Event Name
	 * 
	 * @return TRUE - The Systemic Event is Available
	 */

	public boolean containsSystemicEvent (
		final java.lang.String systemicEventName)
	{
		return null == _systemicEventContainer ? false : _systemicEventContainer.containsEvent (
			systemicEventName
		);
	}

	/**
	 * Retrieve the Systemic Event by Name
	 * 
	 * @param systemicEventName Systemic Event by Name
	 * 
	 * @return The Systemic Event
	 */

	public org.drip.capital.stress.Event systemicEvent (
		final java.lang.String systemicEventName)
	{
		return null == _systemicEventContainer ? null : _systemicEventContainer.event (
			systemicEventName
		);
	}

	/**
	 * Add Idiosyncratic Event
	 * 
	 * @param idiosyncraticEvent The Idiosyncratic Event
	 * 
	 * @return TRUE - The Idiosyncratic Event successfully added
	 */

	public boolean addIdiosyncraticEvent (
		final org.drip.capital.stress.Event idiosyncraticEvent)
	{
		if (null == _idiosyncraticEventContainer)
		{
			_idiosyncraticEventContainer = new org.drip.capital.stress.IdiosyncraticEventContainer();
		}

		return _idiosyncraticEventContainer.addEvent (
			idiosyncraticEvent
		);
	}

	/**
	 * Indicate if the Idiosyncratic Event is Available
	 * 
	 * @param idiosyncraticEventName Idiosyncratic Event Name
	 * 
	 * @return TRUE - The Idiosyncratic Event is Available
	 */

	public boolean containsIdiosyncraticEvent (
		final java.lang.String idiosyncraticEventName)
	{
		return null == _idiosyncraticEventContainer ? false : _idiosyncraticEventContainer.containsEvent (
			idiosyncraticEventName
		);
	}

	/**
	 * Retrieve the Idiosyncratic Event by Name
	 * 
	 * @param idiosyncraticEventName Idiosyncratic Event by Name
	 * 
	 * @return The Idiosyncratic Event
	 */

	public org.drip.capital.stress.Event idiosyncraticEvent (
		final java.lang.String idiosyncraticEventName)
	{
		return null == _idiosyncraticEventContainer ? null : _idiosyncraticEventContainer.event (
			idiosyncraticEventName
		);
	}

	/**
	 * Retrieve the Systemic Event Container
	 * 
	 * @return The Systemic Event Container
	 */

	public org.drip.capital.stress.SystemicEventContainer systemicEventContainer()
	{
		return _systemicEventContainer;
	}

	/**
	 * Retrieve the Idiosyncratic Event Container
	 * 
	 * @return The Idiosyncratic Event Container
	 */

	public org.drip.capital.stress.IdiosyncraticEventContainer idiosyncraticEventContainer()
	{
		return _idiosyncraticEventContainer;
	}

	/**
	 * Add Correlated Stress Event PnL Series
	 * 
	 * @param systemicEventName Systemic Stress Event Name
	 * @param correlatedEventName Correlated Stress Event Name
	 * @param correlatedEventPnLSeries Correlated Stress Event PnL Series
	 * 
	 * @return TRUE - The Correlated Stress Event PnL Series successfully added
	 */

	public boolean addCorrelatedEvent (
		final java.lang.String systemicEventName,
		final java.lang.String correlatedEventName,
		final org.drip.capital.stress.PnLSeries correlatedEventPnLSeries)
	{
		if (null == _systemicEventContainer)
		{
			return false;
		}

		org.drip.capital.stress.Event systemicEvent = _systemicEventContainer.event (
			systemicEventName
		);

		if (null == systemicEvent)
		{
			return false;
		}

		return systemicEvent.attachStressEventPnL (
			correlatedEventName,
			correlatedEventPnLSeries
		);
	}

	/**
	 * Indicate if the Correlated Event is Available
	 * 
	 * @param systemicEventName Systemic Stress Event Name
	 * @param correlatedEventName Correlated Stress Event Name
	 * 
	 * @return TRUE - The Correlated Event is Available
	 */

	public boolean containsCorrelatedEvent (
		final java.lang.String systemicEventName,
		final java.lang.String correlatedEventName)
	{
		if (null == _systemicEventContainer)
		{
			return false;
		}

		return !_systemicEventContainer.containsEvent (
			systemicEventName
		) ? false : _systemicEventContainer.event (
			systemicEventName
		).containsAttachedEvent (
			correlatedEventName
		);
	}

	/**
	 * Retrieve the Correlated Stress Event PnL
	 * 
	 * @param systemicEventName Systemic Stress Event Name
	 * @param correlatedEventName Correlated Stress Event Name
	 * 
	 * @return TRUE - The Correlated Stress Event PnL
	 */

	public org.drip.capital.stress.PnLSeries correlatedEvent (
		final java.lang.String systemicEventName,
		final java.lang.String correlatedEventName)
	{
		return !_systemicEventContainer.containsEvent (
			systemicEventName
		) ? null : _systemicEventContainer.event (
			systemicEventName
		).attachedEventPnL (
			correlatedEventName
		);
	}
}
