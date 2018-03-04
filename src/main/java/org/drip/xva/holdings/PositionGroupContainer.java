
package org.drip.xva.holdings;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * PositionGroupContainer contains a Set of Position/Collateral Groups. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, eSSRN, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955.
 *  
 *  - Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting, eSSRN,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PositionGroupContainer
{
	private org.drip.xva.holdings.PositionGroup[] _positionGroupArray = null;

	/**
	 * Generate a PositionGroupContainer Instance with a Solo Group
	 * 
	 * @param positionGroup The PositionGroup Instance
	 * 
	 * @return The Solo PositionGroupContainer
	 */

	public static final PositionGroupContainer Solo (
		final org.drip.xva.holdings.PositionGroup positionGroup)
	{
		try
		{
			return new PositionGroupContainer (
				new org.drip.xva.holdings.PositionGroup[]
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
		final org.drip.xva.holdings.PositionGroup[] positionGroupArray)
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

	public org.drip.xva.holdings.PositionGroup[] positionGroupArray()
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

	public java.util.Map<java.lang.String, org.drip.xva.holdings.PositionGroupSegment> creditDebtSegments()
	{
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.holdings.PositionGroupSegment>
			creditDebtPositionGroupSegment = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.holdings.PositionGroupSegment>();

		int positionGroupCount = _positionGroupArray.length;

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			org.drip.xva.holdings.PositionGroup positionGroup = _positionGroupArray[positionGroupIndex];

			java.lang.String groupID =
				positionGroup.positionGroupSpecification().creditDebtGroupSpecification().id();

			boolean segmentPresent = creditDebtPositionGroupSegment.containsKey (groupID);

			org.drip.xva.holdings.PositionGroupSegment positionGroupSegment = segmentPresent ?
				creditDebtPositionGroupSegment.get (groupID) : new org.drip.xva.holdings.PositionGroupSegment();

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
		java.util.Map<java.lang.String, org.drip.xva.holdings.PositionGroupSegment>
			creditDebtPositionGroupSegment = creditDebtSegments();

		int creditDebtPositionGroupSegmentCount = creditDebtPositionGroupSegment.size();

		int creditDebtPositionGroupSegmentIndex = 0;
		org.drip.xva.netting.CollateralGroupPath[][] creditDebtSegmentPathArray = new
			org.drip.xva.netting.CollateralGroupPath[creditDebtPositionGroupSegmentCount][];

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.holdings.PositionGroupSegment>
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

	public java.util.Map<java.lang.String, org.drip.xva.holdings.PositionGroupSegment> fundingSegments()
	{
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.holdings.PositionGroupSegment>
			fundingPositionGroupSegment = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.holdings.PositionGroupSegment>();

		int positionGroupCount = _positionGroupArray.length;

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			org.drip.xva.holdings.PositionGroup positionGroup = _positionGroupArray[positionGroupIndex];

			java.lang.String groupID =
				positionGroup.positionGroupSpecification().fundingGroupSpecification().id();

			boolean segmentPresent = fundingPositionGroupSegment.containsKey (groupID);

			org.drip.xva.holdings.PositionGroupSegment positionGroupSegment = segmentPresent ?
				fundingPositionGroupSegment.get (groupID) : new org.drip.xva.holdings.PositionGroupSegment();

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
		java.util.Map<java.lang.String, org.drip.xva.holdings.PositionGroupSegment>
			fundingPositionGroupSegment = fundingSegments();

		int fundingPositionGroupSegmentCount = fundingPositionGroupSegment.size();

		int fundingPositionGroupSegmentIndex = 0;
		org.drip.xva.netting.CollateralGroupPath[][] fundingSegmentPathArray = new
			org.drip.xva.netting.CollateralGroupPath[fundingPositionGroupSegmentCount][];

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.holdings.PositionGroupSegment>
			fundingPositionGroupSegmentEntry : fundingPositionGroupSegment.entrySet())
		{
			fundingSegmentPathArray[fundingPositionGroupSegmentIndex++] =
				fundingPositionGroupSegmentEntry.getValue().collateralGroupPathArray();
		}

		return fundingSegmentPathArray;
	}
}
