
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
 * BookGraph represents the Directed Graph of all the Encompassing Funding Groups. The References are:
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

public class BookGraph extends org.drip.xva.proto.ObjectSpecification
{
	private java.util.Map<java.lang.String, org.drip.xva.topology.FundingGroup> _fundingGroupMap = null;

	/**
	 * BookGraph Constructor
	 * 
	 * @param id BookGraph ID
	 * @param name BookGraph Name
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BookGraph (
		final java.lang.String id,
		final java.lang.String name)
		throws java.lang.Exception
	{
		super (
			id,
			name
		);

		_fundingGroupMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.topology.FundingGroup>();
	}

	/**
	 * Retrieve the Funding Group Map
	 * 
	 * @return The Funding Group Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupMap()
	{
		return _fundingGroupMap;
	}

	/**
	 * Add the specified Funding Group
	 * 
	 * @param fundingGroup The Funding Group
	 * 
	 * @return TRUE - The Funding Group successfully set
	 */

	public boolean addFundingGroup (
		final org.drip.xva.topology.FundingGroup fundingGroup)
	{
		if (null == fundingGroup)
		{
			return false;
		}

		_fundingGroupMap.put (
			fundingGroup.id(),
			fundingGroup
		);

		return true;
	}

	/**
	 * Indicate if the Funding Group identified by the ID exists
	 * 
	 * @param fundingGroupID The Funding Group ID
	 * 
	 * @return TRUE - The Funding Group Exists
	 */

	public boolean containsFundingGroup (
		final java.lang.String fundingGroupID)
	{
		return null == fundingGroupID || fundingGroupID.isEmpty() ? false : _fundingGroupMap.containsKey
			(fundingGroupID);
	}

	/**
	 * Retrieve the Funding Group identified by the ID
	 * 
	 * @param fundingGroupID The Funding Group ID
	 * 
	 * @return TRUE - The Funding Group
	 */

	public org.drip.xva.topology.FundingGroup fundingGroup (
		final java.lang.String fundingGroupID)
	{
		return containsFundingGroup (fundingGroupID) ? _fundingGroupMap.get (fundingGroupID) : null;
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

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupEntry :
			_fundingGroupMap.entrySet())
		{
			java.util.Map<java.lang.String, org.drip.xva.topology.CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
				creditDebtGroupEntry : creditDebtGroupMap.entrySet())
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

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupEntry :
			_fundingGroupMap.entrySet())
		{
			java.util.Map<java.lang.String, org.drip.xva.topology.CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
				creditDebtGroupEntry : creditDebtGroupMap.entrySet())
			{
				java.util.Map<java.lang.String, org.drip.xva.topology.CollateralGroup> collateralGroupMap =
					creditDebtGroupEntry.getValue().collateralGroupMap();

				for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CollateralGroup>
					collateralGroupMapEntry : collateralGroupMap.entrySet())
				{
					org.drip.state.identifier.CSALabel csaLabel =
						collateralGroupMapEntry.getValue().csaLabel();

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

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupEntry :
			_fundingGroupMap.entrySet())
		{
			java.util.Map<java.lang.String, org.drip.xva.topology.CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
				creditDebtGroupMapEntry : creditDebtGroupMap.entrySet())
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

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupEntry :
			_fundingGroupMap.entrySet())
		{
			java.util.Map<java.lang.String, org.drip.xva.topology.CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
				creditDebtGroupMapEntry : creditDebtGroupMap.entrySet())
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

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupEntry :
			_fundingGroupMap.entrySet())
		{
			java.util.Map<java.lang.String, org.drip.xva.topology.CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
				creditDebtGroupMapEntry : creditDebtGroupMap.entrySet())
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

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupEntry :
			_fundingGroupMap.entrySet())
		{
			java.util.Map<java.lang.String, org.drip.xva.topology.CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
				creditDebtGroupMapEntry : creditDebtGroupMap.entrySet())
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

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupEntry :
			_fundingGroupMap.entrySet())
		{
			java.util.Map<java.lang.String, org.drip.xva.topology.CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
				creditDebtGroupMapEntry : creditDebtGroupMap.entrySet())
			{
				org.drip.state.identifier.EntityRecoveryLabel bankSubordinateRecoveryLabel =
					creditDebtGroupMapEntry.getValue().bankSubordinateRecoveryLabel();

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

	/**
	 * Retrieve the Bank Senior Funding Label Map
	 * 
	 * @return The Bank Senior Funding Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		bankSeniorFundingLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
			bankSeniorFundingLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityFundingLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupMapEntry :
			_fundingGroupMap.entrySet())
		{
			org.drip.state.identifier.EntityFundingLabel bankSeniorFundingLabel =
				fundingGroupMapEntry.getValue().bankSeniorFundingLabel();

			java.lang.String bankSeniorFundingLabelFQN = bankSeniorFundingLabel.fullyQualifiedName();

			if (!bankSeniorFundingLabelMap.containsKey (bankSeniorFundingLabelFQN))
			{
				bankSeniorFundingLabelMap.put (
					bankSeniorFundingLabelFQN,
					bankSeniorFundingLabel
				);
			}
		}

		return bankSeniorFundingLabelMap;
	}

	/**
	 * Retrieve the Counter Party Funding Label Map
	 * 
	 * @return The Counter Party Funding Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		counterPartyFundingLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
			counterPartyFundingLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityFundingLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupMapEntry :
			_fundingGroupMap.entrySet())
		{
			org.drip.state.identifier.EntityFundingLabel counterPartyFundingLabel =
				fundingGroupMapEntry.getValue().counterPartyFundingLabel();

			java.lang.String counterPartyFundingLabelFQN = counterPartyFundingLabel.fullyQualifiedName();

			if (!counterPartyFundingLabelMap.containsKey (counterPartyFundingLabelFQN))
			{
				counterPartyFundingLabelMap.put (
					counterPartyFundingLabelFQN,
					counterPartyFundingLabel
				);
			}
		}

		return counterPartyFundingLabelMap;
	}

	/**
	 * Retrieve the Bank Subordinate Funding Label Map
	 * 
	 * @return The Bank Subordinate Funding Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		bankSubordinateFundingLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
			bankSubordinateFundingLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityFundingLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupMapEntry :
			_fundingGroupMap.entrySet())
		{
			org.drip.state.identifier.EntityFundingLabel bankSubordinateFundingLabel =
				fundingGroupMapEntry.getValue().bankSubordinateFundingLabel();

			if (null != bankSubordinateFundingLabel)
			{
				java.lang.String bankSubordinateFundingLabelFQN =
					bankSubordinateFundingLabel.fullyQualifiedName();

				if (!bankSubordinateFundingLabelMap.containsKey (bankSubordinateFundingLabelFQN))
				{
					bankSubordinateFundingLabelMap.put (
						bankSubordinateFundingLabelFQN,
						bankSubordinateFundingLabel
					);
				}
			}
		}

		return bankSubordinateFundingLabelMap;
	}

	/**
	 * Generate the Book's Dependent Market Parameters
	 * 
	 * @return The Book's Dependent Market Parameters
	 */

	public org.drip.xva.topology.BookMarketParams marketParams()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.CSALabel> csaLabelMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.CSALabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.OvernightLabel> overnightLabelMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.OvernightLabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel> bankHazardLabelMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityHazardLabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel>
			counterPartyHazardLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityHazardLabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
			bankSeniorRecoveryLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityRecoveryLabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
			counterPartyRecoveryLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityRecoveryLabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
			bankSubordinateRecoveryLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityRecoveryLabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
			bankSeniorFundingLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityFundingLabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
			counterPartyFundingLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityFundingLabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
			bankSubordinateFundingLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityFundingLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupEntry :
			_fundingGroupMap.entrySet())
		{
			org.drip.state.identifier.EntityFundingLabel bankSeniorFundingLabel =
				fundingGroupEntry.getValue().bankSeniorFundingLabel();

			java.lang.String bankSeniorFundingLabelFQN = bankSeniorFundingLabel.fullyQualifiedName();

			if (!bankSeniorFundingLabelMap.containsKey (bankSeniorFundingLabelFQN))
			{
				bankSeniorFundingLabelMap.put (
					bankSeniorFundingLabelFQN,
					bankSeniorFundingLabel
				);
			}

			org.drip.state.identifier.EntityFundingLabel counterPartyFundingLabel =
				fundingGroupEntry.getValue().counterPartyFundingLabel();

			java.lang.String counterPartyFundingLabelFQN = counterPartyFundingLabel.fullyQualifiedName();

			if (!counterPartyFundingLabelMap.containsKey (counterPartyFundingLabelFQN))
			{
				counterPartyFundingLabelMap.put (
					counterPartyFundingLabelFQN,
					counterPartyFundingLabel
				);
			}

			org.drip.state.identifier.EntityFundingLabel bankSubordinateFundingLabel =
				fundingGroupEntry.getValue().bankSubordinateFundingLabel();

			if (null != bankSubordinateFundingLabel)
			{
				java.lang.String bankSubordinateFundingLabelFQN =
					bankSubordinateFundingLabel.fullyQualifiedName();

				if (!bankSubordinateFundingLabelMap.containsKey (bankSubordinateFundingLabelFQN))
				{
					bankSubordinateFundingLabelMap.put (
						bankSubordinateFundingLabelFQN,
						bankSubordinateFundingLabel
					);
				}
			}

			java.util.Map<java.lang.String, org.drip.xva.topology.CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
				creditDebtGroupEntry : creditDebtGroupMap.entrySet())
			{
				java.util.Map<java.lang.String, org.drip.xva.topology.CollateralGroup> collateralGroupMap =
					creditDebtGroupEntry.getValue().collateralGroupMap();

				org.drip.state.identifier.EntityHazardLabel bankHazardLabel =
					creditDebtGroupEntry.getValue().bankHazardLabel();

				java.lang.String bankHazardLabelFQN = bankHazardLabel.fullyQualifiedName();

				if (!bankHazardLabelMap.containsKey (bankHazardLabelFQN))
				{
					bankHazardLabelMap.put (
						bankHazardLabelFQN,
						bankHazardLabel
					);
				}

				org.drip.state.identifier.EntityHazardLabel counterPartyHazardLabel =
					creditDebtGroupEntry.getValue().counterPartyHazardLabel();

				java.lang.String counterPartyHazardLabelFQN = counterPartyHazardLabel.fullyQualifiedName();

				if (!counterPartyHazardLabelMap.containsKey (counterPartyHazardLabelFQN))
				{
					counterPartyHazardLabelMap.put (
						counterPartyHazardLabelFQN,
						counterPartyHazardLabel
					);
				}

				org.drip.state.identifier.EntityRecoveryLabel bankSeniorRecoveryLabel =
					creditDebtGroupEntry.getValue().bankSeniorRecoveryLabel();

				java.lang.String bankSeniorRecoveryLabelFQN = bankSeniorRecoveryLabel.fullyQualifiedName();

				if (!bankSeniorRecoveryLabelMap.containsKey (bankSeniorRecoveryLabelFQN))
				{
					bankSeniorRecoveryLabelMap.put (
						bankSeniorRecoveryLabelFQN,
						bankSeniorRecoveryLabel
					);
				}

				org.drip.state.identifier.EntityRecoveryLabel counterPartyRecoveryLabel =
					creditDebtGroupEntry.getValue().counterPartyRecoveryLabel();

				java.lang.String counterPartyRecoveryLabelFQN =
					counterPartyRecoveryLabel.fullyQualifiedName();

				if (!counterPartyRecoveryLabelMap.containsKey (counterPartyRecoveryLabelFQN))
				{
					counterPartyRecoveryLabelMap.put (
						counterPartyRecoveryLabelFQN,
						counterPartyRecoveryLabel
					);
				}

				org.drip.state.identifier.EntityRecoveryLabel bankSubordinateRecoveryLabel =
					creditDebtGroupEntry.getValue().bankSubordinateRecoveryLabel();

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
					org.drip.state.identifier.CSALabel csaLabel =
						collateralGroupMapEntry.getValue().csaLabel();

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
		}

		try {
			return new org.drip.xva.topology.BookMarketParams (
				overnightLabelMap,
				csaLabelMap,
				bankHazardLabelMap,
				counterPartyHazardLabelMap,
				bankSeniorRecoveryLabelMap,
				counterPartyRecoveryLabelMap,
				bankSubordinateRecoveryLabelMap,
				bankSeniorFundingLabelMap,
				counterPartyFundingLabelMap,
				bankSubordinateFundingLabelMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
