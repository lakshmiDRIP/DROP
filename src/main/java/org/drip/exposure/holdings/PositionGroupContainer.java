
package org.drip.exposure.holdings;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>PositionGroupContainer</i> contains a Set of Position/Collateral Groups. The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies <i>Risk</i> <b>23
 *  				(12)</b> 82-87
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-
 *  				party Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  		<li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure Group Level Collateralized/Uncollateralized Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/holdings/README.md">Holdings Exposure - Position and Dependencies</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PositionGroupContainer
{
	private org.drip.exposure.holdings.PositionGroup[] _positionGroupArray = null;

	/**
	 * Generate a PositionGroupContainer Instance with a Solo Group
	 * 
	 * @param positionGroup The PositionGroup Instance
	 * 
	 * @return The Solo PositionGroupContainer
	 */

	public static final PositionGroupContainer Solo (
		final org.drip.exposure.holdings.PositionGroup positionGroup)
	{
		try
		{
			return new PositionGroupContainer (
				new org.drip.exposure.holdings.PositionGroup[]
				{
					positionGroup
				}
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * PositionGroupContainer Constructor
	 * 
	 * @param positionGroupArray The Position Group Array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PositionGroupContainer (
		final org.drip.exposure.holdings.PositionGroup[] positionGroupArray)
		throws java.lang.Exception
	{
		if (null == (_positionGroupArray = positionGroupArray))
		{
			throw new java.lang.Exception ("PositionGroupContainer Constructor => Invalid Inputs");
		}

		int positionGroupCount = _positionGroupArray.length;

		if (0 == positionGroupCount)
		{
			throw new java.lang.Exception ("PositionGroupContainer Constructor => Invalid Inputs");
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			if (null == _positionGroupArray[positionGroupIndex])
			{
				throw new java.lang.Exception ("PositionGroupContainer Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of Position Groups
	 * 
	 * @return The Array of Position Groups
	 */

	public org.drip.exposure.holdings.PositionGroup[] positionGroupArray()
	{
		return _positionGroupArray;
	}

	/**
	 * Retrieve the Number of the Positions in the Container
	 * 
	 * @return Number of the Positions in the Container
	 */

	public int count()
	{
		return _positionGroupArray.length;
	}

	/**
	 * Set the Specific Position Group's Collateral Group Path
	 * 
	 * @param positionGroupIndex The Index in the Position Group
	 * @param collateralGroupPath Collateral Group Path
	 * 
	 * @return TRUE - The Collateral Group Path successfully set
	 */

	public boolean setCollateralGroupPath (
		final int positionGroupIndex,
		final org.drip.xva.netting.CollateralGroupPath collateralGroupPath)
	{
		return positionGroupIndex >= count() ? false :
			_positionGroupArray[positionGroupIndex].setCollateralGroupPath (collateralGroupPath);
	}

	/**
	 * Retrieve the Position Groups Sorted into Credit Debt Group Segments
	 * 
	 * @return Map of the Position Groups Sorted into Credit Debt Group Segments
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.holdings.PositionGroupSegment> creditDebtSegments()
	{
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.holdings.PositionGroupSegment>
			creditDebtPositionGroupSegment = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.holdings.PositionGroupSegment>();

		int positionGroupCount = _positionGroupArray.length;

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			org.drip.exposure.holdings.PositionGroup positionGroup = _positionGroupArray[positionGroupIndex];

			java.lang.String groupID =
				positionGroup.positionGroupSpecification().creditDebtGroupSpecification().id();

			boolean segmentPresent = creditDebtPositionGroupSegment.containsKey (groupID);

			org.drip.exposure.holdings.PositionGroupSegment positionGroupSegment = segmentPresent ?
				creditDebtPositionGroupSegment.get (groupID) : new org.drip.exposure.holdings.PositionGroupSegment();

			positionGroupSegment.add (positionGroup);

			if (!segmentPresent)
			{
				creditDebtPositionGroupSegment.put (
					groupID,
					positionGroupSegment
				);
			}
		}

		return creditDebtPositionGroupSegment;
	}

	/**
	 * Retrieve the Array of Position Groups Collected into Credit Debt Group Collateral Vertex Paths
	 * 
	 * @return Array of the Position Groups Collected into Credit Debt Group Collateral Vertex Paths
	 */

	public org.drip.xva.netting.CollateralGroupPath[][] creditDebtSegmentPaths()
	{
		java.util.Map<java.lang.String, org.drip.exposure.holdings.PositionGroupSegment>
			creditDebtPositionGroupSegment = creditDebtSegments();

		int creditDebtPositionGroupSegmentCount = creditDebtPositionGroupSegment.size();

		int creditDebtPositionGroupSegmentIndex = 0;
		org.drip.xva.netting.CollateralGroupPath[][] creditDebtSegmentPathArray = new
			org.drip.xva.netting.CollateralGroupPath[creditDebtPositionGroupSegmentCount][];

		for (java.util.Map.Entry<java.lang.String, org.drip.exposure.holdings.PositionGroupSegment>
			creditDebtPositionGroupSegmentEntry : creditDebtPositionGroupSegment.entrySet())
		{
			creditDebtSegmentPathArray[creditDebtPositionGroupSegmentIndex++] =
				creditDebtPositionGroupSegmentEntry.getValue().collateralGroupPathArray();
		}

		return creditDebtSegmentPathArray;
	}

	/**
	 * Retrieve the Position Groups Sorted into Funding Group Segments
	 * 
	 * @return Map of the Position Groups Sorted into Funding Group Segments
	 */

	public java.util.Map<java.lang.String, org.drip.exposure.holdings.PositionGroupSegment> fundingSegments()
	{
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.holdings.PositionGroupSegment>
			fundingPositionGroupSegment = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.exposure.holdings.PositionGroupSegment>();

		int positionGroupCount = _positionGroupArray.length;

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			org.drip.exposure.holdings.PositionGroup positionGroup = _positionGroupArray[positionGroupIndex];

			java.lang.String groupID =
				positionGroup.positionGroupSpecification().fundingGroupSpecification().id();

			boolean segmentPresent = fundingPositionGroupSegment.containsKey (groupID);

			org.drip.exposure.holdings.PositionGroupSegment positionGroupSegment = segmentPresent ?
				fundingPositionGroupSegment.get (groupID) : new org.drip.exposure.holdings.PositionGroupSegment();

			positionGroupSegment.add (positionGroup);

			if (!segmentPresent)
			{
				fundingPositionGroupSegment.put (
					groupID,
					positionGroupSegment
				);
			}
		}

		return fundingPositionGroupSegment;
	}

	/**
	 * Retrieve the Array of Position Groups Collected into Funding Group Collateral Vertex Paths
	 * 
	 * @return Array of the Position Groups Collected into Funding Group Collateral Vertex Paths
	 */

	public org.drip.xva.netting.CollateralGroupPath[][] fundingSegmentPaths()
	{
		java.util.Map<java.lang.String, org.drip.exposure.holdings.PositionGroupSegment>
			fundingPositionGroupSegment = fundingSegments();

		int fundingPositionGroupSegmentCount = fundingPositionGroupSegment.size();

		int fundingPositionGroupSegmentIndex = 0;
		org.drip.xva.netting.CollateralGroupPath[][] fundingSegmentPathArray = new
			org.drip.xva.netting.CollateralGroupPath[fundingPositionGroupSegmentCount][];

		for (java.util.Map.Entry<java.lang.String, org.drip.exposure.holdings.PositionGroupSegment>
			fundingPositionGroupSegmentEntry : fundingPositionGroupSegment.entrySet())
		{
			fundingSegmentPathArray[fundingPositionGroupSegmentIndex++] =
				fundingPositionGroupSegmentEntry.getValue().collateralGroupPathArray();
		}

		return fundingSegmentPathArray;
	}
}
