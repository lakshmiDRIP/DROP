
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
 * CreditDebtGroup represents an Aggregation of Collateral Groups with a common Credit Debt Specification.
 *  The References are:
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

public class CreditDebtGroup extends org.drip.xva.proto.ObjectSpecification
{
	private org.drip.xva.proto.CreditDebtGroupSpecification _creditDebtGroupSpecification = null;
	private java.util.Map<java.lang.String, org.drip.xva.topology.CollateralGroup> _collateralGroupMap =
		null;

	/**
	 * CreditDebtGroup Constructor
	 * 
	 * @param id CreditDebtGroup ID
	 * @param name CreditDebtGroup Name
	 * @param creditDebtGroupSpecification The CreditDebtGroup Specification
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CreditDebtGroup (
		final java.lang.String id,
		final java.lang.String name,
		final org.drip.xva.proto.CreditDebtGroupSpecification creditDebtGroupSpecification)
		throws java.lang.Exception
	{
		super (
			id,
			name
		);

		if (null == (_creditDebtGroupSpecification = creditDebtGroupSpecification))
		{
			throw new java.lang.Exception ("CreditDebtGroup Constructor => Invalid Inputs");
		}

		_collateralGroupMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.topology.CollateralGroup>();
	}

	/**
	 * Retrieve the Credit Debt Group Specification
	 * 
	 * @return The Credit Debt Group Specification
	 */

	public org.drip.xva.proto.CreditDebtGroupSpecification creditDebtGroupSpecification()
	{
		return _creditDebtGroupSpecification;
	}

	/**
	 * Retrieve the Collateral Group Map
	 * 
	 * @return The Collateral Group Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.topology.CollateralGroup> collateralGroupMap()
	{
		return _collateralGroupMap;
	}

	/**
	 * Add the specified Collateral Group
	 * 
	 * @param collateralGroup The Collateral Group
	 * 
	 * @return TRUE - The Collateral Group successfully added
	 */

	public boolean addCollateralGroup (
		final org.drip.xva.topology.CollateralGroup collateralGroup)
	{
		if (null == collateralGroup)
		{
			return false;
		}

		_collateralGroupMap.put (
			collateralGroup.id(),
			collateralGroup
		);

		return true;
	}

	/**
	 * Indicates if the Collateral Group identified by the specified ID
	 * 
	 * @param collateralGroupID The Collateral Group ID
	 * 
	 * @return TRUE - The Collateral Group Exists
	 */

	public boolean containsCollateralGroup (
		final java.lang.String collateralGroupID)
	{
		return null == collateralGroupID || collateralGroupID.isEmpty() ? false :
			_collateralGroupMap.containsKey (collateralGroupID);
	}

	/**
	 * Retrieve the Collateral Group identified by the specified ID
	 * 
	 * @param collateralGroupID The Collateral Group ID
	 * 
	 * @return The Collateral Group
	 */

	public org.drip.xva.topology.CollateralGroup collateralGroup (
		final java.lang.String collateralGroupID)
	{
		return containsCollateralGroup (collateralGroupID) ? _collateralGroupMap.get (collateralGroupID) :
			null;
	}

	/**
	 * Retrieve the Bank Hazard Label
	 * 
	 * @return The Bank Hazard Label
	 */

	public org.drip.state.identifier.EntityHazardLabel bankHazardLabel()
	{
		return _creditDebtGroupSpecification.bankHazardLabel();
	}

	/**
	 * Retrieve the Counter Party Hazard Label
	 * 
	 * @return The Counter Party Hazard Label
	 */

	public org.drip.state.identifier.EntityHazardLabel counterPartyHazardLabel()
	{
		return _creditDebtGroupSpecification.counterPartyHazardLabel();
	}

	/**
	 * Retrieve the Bank Senior Recovery Label
	 * 
	 * @return The Bank Senior Recovery Label
	 */

	public org.drip.state.identifier.EntityRecoveryLabel bankSeniorRecoveryLabel()
	{
		return _creditDebtGroupSpecification.bankSeniorRecoveryLabel();
	}

	/**
	 * Retrieve the Bank Subordinate Recovery Label
	 * 
	 * @return The Bank Subordinate Recovery Label
	 */

	public org.drip.state.identifier.EntityRecoveryLabel bankSubordinateRecoveryLabel()
	{
		return _creditDebtGroupSpecification.bankSubordinateRecoveryLabel();
	}

	/**
	 * Retrieve the Counter Party Senior Recovery Label
	 * 
	 * @return The Counter Party Senior Recovery Label
	 */

	public org.drip.state.identifier.EntityRecoveryLabel counterPartyRecoveryLabel()
	{
		return _creditDebtGroupSpecification.counterPartyRecoveryLabel();
	}

	/**
	 * Retrieve the Overnight Label Map
	 * 
	 * @return The Overnight Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.OvernightLabel> overnightLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.OvernightLabel> overnightLabelMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.OvernightLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CollateralGroup>
			collateralGroupMapEntry : _collateralGroupMap.entrySet())
		{
			org.drip.state.identifier.OvernightLabel overnightLabel =
				collateralGroupMapEntry.getValue().overnightLabel();

			java.lang.String overnightLabelFQN = overnightLabel.fullyQualifiedName();

			if (!overnightLabelMap.containsKey (overnightLabelFQN))
			{
				overnightLabelMap.put (
					overnightLabelFQN,
					overnightLabel
				);
			}
		}

		return overnightLabelMap;
	}

	/**
	 * Retrieve the CSA Label Map
	 * 
	 * @return The CSA Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.CSALabel> csaLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.CSALabel> csaLabelMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.CSALabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CollateralGroup>
			collateralGroupMapEntry : _collateralGroupMap.entrySet())
		{
			org.drip.state.identifier.CSALabel csaLabel = collateralGroupMapEntry.getValue().csaLabel();

			java.lang.String csaLabelFQN = csaLabel.fullyQualifiedName();

			if (!csaLabelMap.containsKey (csaLabelFQN))
			{
				csaLabelMap.put (
					csaLabelFQN,
					csaLabel
				);
			}
		}

		return csaLabelMap;
	}
}
