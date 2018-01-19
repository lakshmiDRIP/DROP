
package org.drip.xva.set;

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
 * GroupAggregationContainer holds the Named Group Specifications. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 *  	Presence of Counter-party Credit Risk for the Fixed Income Market, World Scientific Publishing,
 *  	Singapore.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class GroupAggregationContainer
{
	private org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.set.CollateralGroupSpecification>
		_collateralGroupMap = null;
	private
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.set.CounterPartyGroupSpecification>
			_counterPartyGroupMap = null;
	private org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.set.NettingGroupSpecification>
		_nettingGroupMap = null;
	private org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.set.RollUpGroupSpecification>
		_fundingGroupMap = null;

	/**
	 * GroupAggregationContainer Constructor
	 */

	public GroupAggregationContainer()
	{
		_collateralGroupMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.set.CollateralGroupSpecification>();

		_counterPartyGroupMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.set.CounterPartyGroupSpecification>();

		_nettingGroupMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.set.NettingGroupSpecification>();

		_fundingGroupMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.set.RollUpGroupSpecification>();
	}

	/**
	 * Retrieve the Collateral Group Map
	 * 
	 * @return The Collateral Group Map
	 */

	public org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.set.CollateralGroupSpecification>
		collateralGroupMap()
	{
		return _collateralGroupMap;
	}

	/**
	 * Retrieve the Counter Party Group Map
	 * 
	 * @return The Counter Party Group Map
	 */

	public org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.set.CounterPartyGroupSpecification>
		counterPartyGroupMap()
	{
		return _counterPartyGroupMap;
	}

	/**
	 * Retrieve the Netting Group Map
	 * 
	 * @return The Netting Group Map
	 */

	public org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.set.NettingGroupSpecification>
		nettingGroupMap()
	{
		return _nettingGroupMap;
	}

	/**
	 * Retrieve the Funding Group Map
	 * 
	 * @return The Funding Group Map
	 */

	public org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.set.RollUpGroupSpecification>
		fundingGroupMap()
	{
		return _fundingGroupMap;
	}

	/**
	 * Add the Collateral Group Specification
	 * 
	 * @param collateralGroupSpecification Collateral Group Specification
	 * 
	 * @return TRUE - The Collateral Group Specification successfully added
	 */

	public boolean addCollateralGroupSpecification (
		final org.drip.xva.set.CollateralGroupSpecification collateralGroupSpecification)
	{
		if (null == collateralGroupSpecification)
		{
			return false;
		}

		_collateralGroupMap.put (
			collateralGroupSpecification.id(),
			collateralGroupSpecification
		);

		return true;
	}

	/**
	 * Indicate if the Map contains the Collateral Group Specification
	 * 
	 * @param id The Collateral Group ID
	 * 
	 * @return TRUE - The Map contains the Collateral Group Specification
	 */

	public boolean containsCollateralGroupSpecification (
		final java.lang.String id)
	{
		return null != id && !id.isEmpty() && _collateralGroupMap.containsKey (id);
	}

	/**
	 * Retrieve the Collateral Group Specification corresponding to the ID
	 * 
	 * @param id The Collateral Group ID
	 * 
	 * @return The Collateral Group Specification
	 */

	public org.drip.xva.set.CollateralGroupSpecification collateralGroupSpecification (
		final java.lang.String id)
	{
		return !containsCollateralGroupSpecification (id) ? null : _collateralGroupMap.get (id);
	}

	/**
	 * Add the Counter Party Group Specification
	 * 
	 * @param counterPartyGroupSpecification Counter Party Group Specification
	 * 
	 * @return TRUE - The Counter Party Group Specification successfully added
	 */

	public boolean addCounterPartyGroupSpecification (
		final org.drip.xva.set.CounterPartyGroupSpecification counterPartyGroupSpecification)
	{
		if (null == counterPartyGroupSpecification)
		{
			return false;
		}

		_counterPartyGroupMap.put (
			counterPartyGroupSpecification.id(),
			counterPartyGroupSpecification
		);

		return true;
	}

	/**
	 * Indicate if the Map contains the Counter Party Group Specification
	 * 
	 * @param id The Counter Party Group ID
	 * 
	 * @return TRUE - The Map contains the Counter Party Group Specification
	 */

	public boolean containsCounterPartyGroupSpecification (
		final java.lang.String id)
	{
		return null != id && !id.isEmpty() && _counterPartyGroupMap.containsKey (id);
	}

	/**
	 * Retrieve the Counter Party Group Specification corresponding to the ID
	 * 
	 * @param id The Counter Party Group ID
	 * 
	 * @return The Counter Party Group Specification
	 */

	public org.drip.xva.set.CounterPartyGroupSpecification counterPartyGroupSpecification (
		final java.lang.String id)
	{
		return !containsCounterPartyGroupSpecification (id) ? null : _counterPartyGroupMap.get (id);
	}

	/**
	 * Add the Netting Group Specification
	 * 
	 * @param nettingGroupSpecification Netting Group Specification
	 * 
	 * @return TRUE - The Netting Group Specification successfully added
	 */

	public boolean addNettingGroupSpecification (
		final org.drip.xva.set.NettingGroupSpecification nettingGroupSpecification)
	{
		if (null == nettingGroupSpecification)
		{
			return false;
		}

		_nettingGroupMap.put (
			nettingGroupSpecification.id(),
			nettingGroupSpecification
		);

		return true;
	}

	/**
	 * Indicate if the Map contains the Netting Group Specification
	 * 
	 * @param id The Netting Group ID
	 * 
	 * @return TRUE - The Map contains the Netting Group Specification
	 */

	public boolean containsNettingGroupSpecification (
		final java.lang.String id)
	{
		return null != id && !id.isEmpty() && _nettingGroupMap.containsKey (id);
	}

	/**
	 * Retrieve the Netting Group Specification corresponding to the ID
	 * 
	 * @param id The Netting Group ID
	 * 
	 * @return The Netting Group Specification
	 */

	public org.drip.xva.set.NettingGroupSpecification nettingGroupSpecification (
		final java.lang.String id)
	{
		return !containsNettingGroupSpecification (id) ? null : _nettingGroupMap.get (id);
	}

	/**
	 * Add the Funding Group Specification
	 * 
	 * @param fundingGroupSpecification Funding Group Specification
	 * 
	 * @return TRUE - The Funding Group Specification successfully added
	 */

	public boolean addFundingGroupSpecification (
		final org.drip.xva.set.RollUpGroupSpecification fundingGroupSpecification)
	{
		if (null == fundingGroupSpecification)
		{
			return false;
		}

		_fundingGroupMap.put (
			fundingGroupSpecification.id(),
			fundingGroupSpecification
		);

		return true;
	}

	/**
	 * Indicate if the Map contains the Funding Group Specification
	 * 
	 * @param id The Funding Group ID
	 * 
	 * @return TRUE - The Map contains the Funding Group Specification
	 */

	public boolean containsFundingGroupSpecification (
		final java.lang.String id)
	{
		return null != id && !id.isEmpty() && _fundingGroupMap.containsKey (id);
	}

	/**
	 * Retrieve the Funding Group Specification corresponding to the ID
	 * 
	 * @param id The Funding Group ID
	 * 
	 * @return The Funding Group Specification
	 */

	public org.drip.xva.set.RollUpGroupSpecification fundingGroupSpecification (
		final java.lang.String id)
	{
		return !containsFundingGroupSpecification (id) ? null : _fundingGroupMap.get (id);
	}
}
