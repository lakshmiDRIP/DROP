
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
 * belong inside of the a Capital Unit. The References
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
	private org.drip.capital.stress.GSSTEventContainer _gsstEventContainer = null;
	private org.drip.capital.stress.IBSSTEventContainer _iBSSTEventContainer = null;

	/**
	 * Empty CapitalUnitEventContainer Constructor
	 */

	public CapitalUnitEventContainer()
	{
	}

	/**
	 * Add GSST Event
	 * 
	 * @param gsstEvent The GSST Event
	 * 
	 * @return TRUE - The GSST Event successfully added
	 */

	public boolean addGSSTEvent (
		final org.drip.capital.stress.Event gsstEvent)
	{
		if (null == _gsstEventContainer)
		{
			_gsstEventContainer = new org.drip.capital.stress.GSSTEventContainer();
		}

		return _gsstEventContainer.addEvent (gsstEvent);
	}

	/**
	 * Indicate if the GSST Event is Available
	 * 
	 * @param gsstEventName GSST Event Name
	 * 
	 * @return TRUE - The GSST Event is Available
	 */

	public boolean containsGSSTEvent (
		final java.lang.String gsstEventName)
	{
		return null == _gsstEventContainer ? false : _gsstEventContainer.containsEvent (gsstEventName);
	}

	/**
	 * Retrieve the GSST Event by Name
	 * 
	 * @param gsstEventName GSST Event by Name
	 * 
	 * @return The GSST Event
	 */

	public org.drip.capital.stress.Event gsstEvent (
		final java.lang.String gsstEventName)
	{
		return null == _gsstEventContainer ? null : _gsstEventContainer.event (gsstEventName);
	}

	/**
	 * Add iBSST Event
	 * 
	 * @param iBSSTEvent The iBSST Event
	 * 
	 * @return TRUE - The iBSST Event successfully added
	 */

	public boolean addIBSSTEvent (
		final org.drip.capital.stress.Event iBSSTEvent)
	{
		if (null == _iBSSTEventContainer)
		{
			_iBSSTEventContainer = new org.drip.capital.stress.IBSSTEventContainer();
		}

		return _iBSSTEventContainer.addEvent (iBSSTEvent);
	}

	/**
	 * Indicate if the iBSST Event is Available
	 * 
	 * @param iBSSTEventName iBSST Event Name
	 * 
	 * @return TRUE - The iBSST Event is Available
	 */

	public boolean containsIBSSTEvent (
		final java.lang.String iBSSTEventName)
	{
		return null == _iBSSTEventContainer ? false : _iBSSTEventContainer.containsEvent (iBSSTEventName);
	}

	/**
	 * Retrieve the iBSST Event by Name
	 * 
	 * @param iBSSTEventName iBSST Event by Name
	 * 
	 * @return The iBSST Event
	 */

	public org.drip.capital.stress.Event iBSSTEvent (
		final java.lang.String iBSSTEventName)
	{
		return null == _iBSSTEventContainer ? null : _iBSSTEventContainer.event (iBSSTEventName);
	}

	/**
	 * Retrieve the GSST Event Container
	 * 
	 * @return The GSST Event Container
	 */

	public org.drip.capital.stress.GSSTEventContainer gsstEventContainer()
	{
		return _gsstEventContainer;
	}

	/**
	 * Retrieve the iBSST Event Container
	 * 
	 * @return The iBSST Event Container
	 */

	public org.drip.capital.stress.IBSSTEventContainer iBSSTEventContainer()
	{
		return _iBSSTEventContainer;
	}

	/**
	 * Add cBSST Stress Event PnL Series
	 * 
	 * @param gsstEventName GSST Stress Event Name
	 * @param cbsstEventName cBSST Stress Event Name
	 * @param cbsstEventPnLSeries cBSST Stress Event PnL Series
	 * 
	 * @return TRUE - The cBSST Stress Event PnL Series successfully added
	 */

	public boolean addCBSSTEvent (
		final java.lang.String gsstEventName,
		final java.lang.String cbsstEventName,
		final org.drip.capital.stress.PnLSeries cbsstEventPnLSeries)
	{
		if (null == _gsstEventContainer)
		{
			return false;
		}

		org.drip.capital.stress.Event gsstEvent = _gsstEventContainer.event (gsstEventName);

		if (null == gsstEvent)
		{
			return false;
		}

		return gsstEvent.attachStressEventPnL (
			cbsstEventName,
			cbsstEventPnLSeries
		);
	}

	/**
	 * Indicate if the cBSST Event is Available
	 * 
	 * @param gsstEventName GSST Stress Event Name
	 * @param cBSSTEventName cBSST Stress Event Name
	 * 
	 * @return TRUE - The cBSST Event is Available
	 */

	public boolean containsCBSSTEvent (
		final java.lang.String gsstEventName,
		final java.lang.String cBSSTEventName)
	{
		if (null == _gsstEventContainer)
		{
			return false;
		}

		return !_gsstEventContainer.containsEvent (gsstEventName) ? false :
			_gsstEventContainer.event (gsstEventName).containsAttachedEvent (cBSSTEventName);
	}

	/**
	 * Retrieve the cBSST Stress Event PnL
	 * 
	 * @param gsstEventName GSST Stress Event Name
	 * @param cBSSTEventName cBSST Stress Event Name
	 * 
	 * @return TRUE - The cBSST Stress Event PnL
	 */

	public org.drip.capital.stress.PnLSeries cBSSTEvent (
		final java.lang.String gsstEventName,
		final java.lang.String cBSSTEventName)
	{
		return !_gsstEventContainer.containsEvent (gsstEventName) ? null :
			_gsstEventContainer.event (gsstEventName).attachedEventPnL (cBSSTEventName);
	}
}
