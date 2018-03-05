
package org.drip.xva.topology;

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
 * CollateralGroup represents an Aggregation of Position Groups over a common Collateral Specification. The
 *  References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Albanese, C., L. Andersen, and, S. Iabichino (2015): The FVA Puzzle: Accounting, Risk Management, and
 *  	Collateral Trading <b>https://papers.ssrn.com/sol3/paper.cfm?abstract_id_2517301</b><br><br>
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CollateralGroup extends org.drip.xva.proto.ObjectSpecification
{
	private org.drip.xva.proto.CollateralGroupSpecification _collateralGroupSpecification = null;
	private java.util.Map<java.lang.String, org.drip.xva.topology.PositionGroup> _positionGroupMap = null;

	/**
	 * CollateralGroup Constructor
	 * 
	 * @param id The Collateral Group ID
	 * @param name The Collateral Group Name
	 * @param collateralGroupSpecification The Collateral Group Specification
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CollateralGroup (
		final java.lang.String id,
		final java.lang.String name,
		final org.drip.xva.proto.CollateralGroupSpecification collateralGroupSpecification)
		throws java.lang.Exception
	{
		super (
			id,
			name
		);

		if (null == (_collateralGroupSpecification = collateralGroupSpecification))
		{
			throw new java.lang.Exception ("CollateralGroup Contructor => Invalid Inputs");
		}

		_positionGroupMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.topology.PositionGroup>();
	}

	/**
	 * Retrieve the Collateral Group Specification
	 * 
	 * @return The Collateral Group Specification
	 */

	public org.drip.xva.proto.CollateralGroupSpecification collateralGroupSpecification()
	{
		return _collateralGroupSpecification;
	}

	/**
	 * Retrieve the Position Group Map
	 * 
	 * @return The Position Group Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.topology.PositionGroup> positionGroupMap()
	{
		return _positionGroupMap;
	}

	/**
	 * Add the specified Position Group
	 * 
	 * @param positionGroup The Position Group
	 * 
	 * @return TRUE - The Position Group successfully added
	 */

	public boolean addPositionGroup (
		final org.drip.xva.topology.PositionGroup positionGroup)
	{
		if (null == positionGroup)
		{
			return false;
		}

		_positionGroupMap.put (
			positionGroup.id(),
			positionGroup
		);

		return true;
	}

	/**
	 * Indicates if the Position Group identified by the specified ID
	 * 
	 * @param positionGroupID The Position Group ID
	 * 
	 * @return TRUE - The Position Group Exists
	 */

	public boolean containsPositionGroup (
		final java.lang.String positionGroupID)
	{
		return null == positionGroupID || positionGroupID.isEmpty() ? false : _positionGroupMap.containsKey
			(positionGroupID);
	}

	/**
	 * Retrieve the Position Group identified by the specified ID
	 * 
	 * @param positionGroupID The Position Group ID
	 * 
	 * @return The Position Group
	 */

	public org.drip.xva.topology.PositionGroup positionGroup (
		final java.lang.String positionGroupID)
	{
		return containsPositionGroup (positionGroupID) ? _positionGroupMap.get (positionGroupID) : null;
	}

	/**
	 * Retrieve the Overnight Label
	 * 
	 * @return The Overnight Label
	 */

	public org.drip.state.identifier.OvernightLabel overnightLabel()
	{
		return _collateralGroupSpecification.overnightLabel();
	}

	/**
	 * Retrieve the CSA Label
	 * 
	 * @return The CSA Label
	 */

	public org.drip.state.identifier.CSALabel csaLabel()
	{
		return _collateralGroupSpecification.csaLabel();
	}
}
