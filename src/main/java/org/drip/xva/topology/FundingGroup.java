
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
 * FundingGroup represents an Aggregation of Credit Debt Groups with a common Funding Group Specification.
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

public class FundingGroup extends org.drip.xva.proto.ObjectSpecification
{
	private org.drip.xva.proto.FundingGroupSpecification _fundingGroupSpecification = null;
	private java.util.Map<java.lang.String, org.drip.xva.topology.CreditDebtGroup> _creditDebtGroupMap =
		null;

	/**
	 * FundingGroup Constructor
	 * 
	 * @param id FundingGroup ID
	 * @param name FundingGroup Name
	 * @param fundingGroupSpecification Funding Group Specification
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FundingGroup (
		final java.lang.String id,
		final java.lang.String name,
		final org.drip.xva.proto.FundingGroupSpecification fundingGroupSpecification)
		throws java.lang.Exception
	{
		super (
			id,
			name
		);

		if (null == (_fundingGroupSpecification = fundingGroupSpecification))
		{
			throw new java.lang.Exception ("FundingGroup Constructor => Invalid Inputs");
		}

		_creditDebtGroupMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.topology.CreditDebtGroup>();
	}

	/**
	 * Retrieve the Funding Group Specification
	 * 
	 * @return The Funding Group Specification
	 */

	public org.drip.xva.proto.FundingGroupSpecification fundingGroupSpecification()
	{
		return _fundingGroupSpecification;
	}

	/**
	 * Retrieve the Credit Debt Group Map
	 * 
	 * @return The Credit Debt Group Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.topology.CreditDebtGroup> creditDebtGroupMap()
	{
		return _creditDebtGroupMap;
	}

	/**
	 * Add the specified CreditDebtGroup Instance
	 * 
	 * @param creditDebtGroup The CreditDebtGroup Instance
	 * 
	 * @return TRUE - The CreditDebtGroup Instance successfully added
	 */

	public boolean addCreditDebtGroup (
		final org.drip.xva.topology.CreditDebtGroup creditDebtGroup)
	{
		if (null == creditDebtGroup)
		{
			return false;
		}

		_creditDebtGroupMap.put (
			creditDebtGroup.id(),
			creditDebtGroup
		);

		return true;
	}

	/**
	 * Indicate the specified CreditDebtGroup ID is available
	 * 
	 * @param creditDebtGroupID The CreditDebtGroup ID
	 * 
	 * @return TRUE - The CreditDebtGroup is available
	 */

	public boolean containsCreditDebtGroup (
		final java.lang.String creditDebtGroupID)
	{
		return null == creditDebtGroupID || creditDebtGroupID.isEmpty() ? false :
			_creditDebtGroupMap.containsKey (creditDebtGroupID);
	}

	/**
	 * Retrieve the CreditDebtGroup
	 * 
	 * @param creditDebtGroupID The CreditDebtGroup ID
	 * 
	 * @return The CreditDebtGroup Instance
	 */

	public org.drip.xva.topology.CreditDebtGroup creditDebtGroup (
		final java.lang.String creditDebtGroupID)
	{
		return containsCreditDebtGroup (creditDebtGroupID) ? _creditDebtGroupMap.get (creditDebtGroupID) :
			null;
	}

	/**
	 * Retrieve the Bank Senior Funding Label
	 * 
	 * @return The Bank Senior Funding Label
	 */

	public org.drip.state.identifier.EntityFundingLabel bankSeniorFundingLabel()
	{
		return _fundingGroupSpecification.bankSeniorFundingLabel();
	}

	/**
	 * Retrieve the Counter Party Funding Label
	 * 
	 * @return The Counter Party Funding Label
	 */

	public org.drip.state.identifier.EntityFundingLabel counterPartyFundingLabel()
	{
		return _fundingGroupSpecification.counterPartyFundingLabel();
	}

	/**
	 * Retrieve the Bank Subordinate Funding Label
	 * 
	 * @return The Bank Subordinate Funding Label
	 */

	public org.drip.state.identifier.EntityFundingLabel bankSubordinateFundingLabel()
	{
		return _fundingGroupSpecification.bankSubordinateFundingLabel();
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

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
			creditDebtGroupEntry : _creditDebtGroupMap.entrySet())
		{
			java.util.Map<java.lang.String, org.drip.xva.topology.CollateralGroup> collateralGroupMap =
				creditDebtGroupEntry.getValue().collateralGroupMap();

			for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CollateralGroup>
				collateralGroupMapEntry : collateralGroupMap.entrySet())
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

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
			creditDebtGroupEntry : _creditDebtGroupMap.entrySet())
		{
			java.util.Map<java.lang.String, org.drip.xva.topology.CollateralGroup> collateralGroupMap =
				creditDebtGroupEntry.getValue().collateralGroupMap();

			for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CollateralGroup>
				collateralGroupMapEntry : collateralGroupMap.entrySet())
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
		}

		return csaLabelMap;
	}

	/**
	 * Retrieve the Bank Hazard Label Map
	 * 
	 * @return The Bank Hazard Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel> bankHazardLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel> bankHazardLabelMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityHazardLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
			creditDebtGroupMapEntry : _creditDebtGroupMap.entrySet())
		{
			org.drip.state.identifier.EntityHazardLabel bankHazardLabel =
				creditDebtGroupMapEntry.getValue().bankHazardLabel();

			java.lang.String bankHazardLabelFQN = bankHazardLabel.fullyQualifiedName();

			if (!bankHazardLabelMap.containsKey (bankHazardLabelFQN))
			{
				bankHazardLabelMap.put (
					bankHazardLabelFQN,
					bankHazardLabel
				);
			}
		}

		return bankHazardLabelMap;
	}

	/**
	 * Retrieve the Counter Party Hazard Label Map
	 * 
	 * @return The Counter Party Hazard Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel>
		counterPartyHazardLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel>
			counterPartyHazardLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityHazardLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
			creditDebtGroupMapEntry : _creditDebtGroupMap.entrySet())
		{
			org.drip.state.identifier.EntityHazardLabel counterPartyHazardLabel =
				creditDebtGroupMapEntry.getValue().counterPartyHazardLabel();

			java.lang.String counterPartyHazardLabelFQN = counterPartyHazardLabel.fullyQualifiedName();

			if (!counterPartyHazardLabelMap.containsKey (counterPartyHazardLabelFQN))
			{
				counterPartyHazardLabelMap.put (
					counterPartyHazardLabelFQN,
					counterPartyHazardLabel
				);
			}
		}

		return counterPartyHazardLabelMap;
	}

	/**
	 * Retrieve the Bank Senior Recovery Label Map
	 * 
	 * @return The Bank Senior Recovery Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		bankSeniorRecoveryLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
			bankSeniorRecoveryLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityRecoveryLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
			creditDebtGroupMapEntry : _creditDebtGroupMap.entrySet())
		{
			org.drip.state.identifier.EntityRecoveryLabel bankSeniorRecoveryLabel =
				creditDebtGroupMapEntry.getValue().bankSeniorRecoveryLabel();

			java.lang.String bankSeniorRecoveryLabelFQN = bankSeniorRecoveryLabel.fullyQualifiedName();

			if (!bankSeniorRecoveryLabelMap.containsKey (bankSeniorRecoveryLabelFQN))
			{
				bankSeniorRecoveryLabelMap.put (
					bankSeniorRecoveryLabelFQN,
					bankSeniorRecoveryLabel
				);
			}
		}

		return bankSeniorRecoveryLabelMap;
	}

	/**
	 * Retrieve the Counter Party Recovery Label Map
	 * 
	 * @return The Counter Party Recovery Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		counterPartyRecoveryLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
			counterPartyRecoveryLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityRecoveryLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
			creditDebtGroupMapEntry : _creditDebtGroupMap.entrySet())
		{
			org.drip.state.identifier.EntityRecoveryLabel counterPartyRecoveryLabel =
				creditDebtGroupMapEntry.getValue().counterPartyRecoveryLabel();

			java.lang.String counterPartyRecoveryLabelFQN = counterPartyRecoveryLabel.fullyQualifiedName();

			if (!counterPartyRecoveryLabelMap.containsKey (counterPartyRecoveryLabelFQN))
			{
				counterPartyRecoveryLabelMap.put (
					counterPartyRecoveryLabelFQN,
					counterPartyRecoveryLabel
				);
			}
		}

		return counterPartyRecoveryLabelMap;
	}

	/**
	 * Retrieve the Bank Subordinate Recovery Label Map
	 * 
	 * @return The Bank Subordinate Recovery Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		bankSubordinateRecoveryLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
			bankSubordinateRecoveryLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityRecoveryLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
			creditDebtGroupMapEntry : _creditDebtGroupMap.entrySet())
		{
			org.drip.state.identifier.EntityRecoveryLabel bankSubordinateRecoveryLabel =
				creditDebtGroupMapEntry.getValue().bankSubordinateRecoveryLabel();

			if (null != bankSubordinateRecoveryLabel)
			{
				java.lang.String bankSubordinateRecoveryLabelFQN =
					bankSubordinateRecoveryLabel.fullyQualifiedName();

				if (!bankSubordinateRecoveryLabelMap.containsKey (bankSubordinateRecoveryLabelFQN))
				{
					bankSubordinateRecoveryLabelMap.put (
						bankSubordinateRecoveryLabelFQN,
						bankSubordinateRecoveryLabel
					);
				}
			}
		}

		return bankSubordinateRecoveryLabelMap;
	}
}
