
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
 * PositionGroupSegment contains one Segment of a Position/Collateral Group. The References are:
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

public class PositionGroupSegment
{
	private java.util.Set<org.drip.xva.holdings.PositionGroup> _positionGroupSet = new
		java.util.HashSet<org.drip.xva.holdings.PositionGroup>();

	/**
	 * Empty PositionGroupSegment Constructor
	 */

	public PositionGroupSegment()
	{
	}

	/**
	 * Retrieve the Position Group Segment
	 * 
	 * @return The Position Group Segment
	 */

	public java.util.Set<org.drip.xva.holdings.PositionGroup> positionGroupSet()
	{
		return _positionGroupSet;
	}

	/**
	 * Add the Specified Position Group to the Segment
	 * 
	 * @param positionGroup The Position Group
	 * 
	 * @return TRUE - The Position Group successfully added
	 */

	public boolean add (
		final org.drip.xva.holdings.PositionGroup positionGroup)
	{
		if (null == positionGroup)
		{
			return false;
		}

		_positionGroupSet.add (positionGroup);

		return true;
	}

	/**
	 * Retrieve the Position Group Array
	 * 
	 * @return The Position Group Array
	 */

	public org.drip.xva.holdings.PositionGroup[] positionGroupArray()
	{
		int segmentCount = _positionGroupSet.size();

		int segmentIndex = 0;
		org.drip.xva.holdings.PositionGroup[] positionGroupArray = 0 == segmentCount ? null : new
			org.drip.xva.holdings.PositionGroup[segmentCount];

		if (0 == segmentCount)
		{
			return null;
		}

		for (org.drip.xva.holdings.PositionGroup positionGroup : _positionGroupSet)
		{
			positionGroupArray[segmentIndex++] = positionGroup;
		}

		return positionGroupArray;
	}

	/**
	 * Retrieve the Position Group Collateral Path Array
	 * 
	 * @return The Position Group Collateral Path Array
	 */

	public org.drip.xva.netting.CollateralGroupPath[] collateralGroupPathArray()
	{
		int segmentCount = _positionGroupSet.size();

		int segmentIndex = 0;
		org.drip.xva.netting.CollateralGroupPath[] collateralGroupPathArray = 0 == segmentCount ? null
			: new org.drip.xva.netting.CollateralGroupPath[segmentCount];

		if (0 == segmentCount)
		{
			return null;
		}

		for (org.drip.xva.holdings.PositionGroup positionGroup : _positionGroupSet)
		{
			collateralGroupPathArray[segmentIndex++] = positionGroup.collateralGroupPath();
		}

		return collateralGroupPathArray;
	}
}
