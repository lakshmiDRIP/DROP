
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
 * Adiabat represents the Directed Graph of all the Encompassing Funding Groups inside of a Closed System
 * 	(i.e., Adiabat). The References are:
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

public class Adiabat extends org.drip.xva.proto.ObjectSpecification
{
	private java.util.Map<java.lang.String, org.drip.xva.topology.FundingGroup> _fundingGroupMap = null;

	/**
	 * Adiabat Constructor
	 * 
	 * @param id BookGraph ID
	 * @param name BookGraph Name
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Adiabat (
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
	 * Retrieve the Dealer Hazard Label Map
	 * 
	 * @return The Dealer Hazard Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel>
		dealerHazardLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel> dealerHazardLabelMap =
			new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityHazardLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupEntry :
			_fundingGroupMap.entrySet())
		{
			java.util.Map<java.lang.String, org.drip.xva.topology.CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
				creditDebtGroupMapEntry : creditDebtGroupMap.entrySet())
			{
				org.drip.state.identifier.EntityHazardLabel dealerHazardLabel =
					creditDebtGroupMapEntry.getValue().dealerHazardLabel();

				java.lang.String dealerHazardLabelFQN = dealerHazardLabel.fullyQualifiedName();

				if (!dealerHazardLabelMap.containsKey (dealerHazardLabelFQN))
				{
					dealerHazardLabelMap.put (
						dealerHazardLabelFQN,
						dealerHazardLabel
					);
				}
			}
		}

		return dealerHazardLabelMap;
	}

	/**
	 * Retrieve the Client Hazard Label Map
	 * 
	 * @return The Client Hazard Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel>
		clientHazardLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel> clientHazardLabelMap =
			new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityHazardLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupEntry :
			_fundingGroupMap.entrySet())
		{
			java.util.Map<java.lang.String, org.drip.xva.topology.CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
				creditDebtGroupMapEntry : creditDebtGroupMap.entrySet())
			{
				org.drip.state.identifier.EntityHazardLabel clientHazardLabel =
					creditDebtGroupMapEntry.getValue().clientPartyHazardLabel();

				java.lang.String clientHazardLabelFQN = clientHazardLabel.fullyQualifiedName();

				if (!clientHazardLabelMap.containsKey (clientHazardLabelFQN))
				{
					clientHazardLabelMap.put (
						clientHazardLabelFQN,
						clientHazardLabel
					);
				}
			}
		}

		return clientHazardLabelMap;
	}

	/**
	 * Retrieve the Dealer Senior Recovery Label Map
	 * 
	 * @return The Dealer Senior Recovery Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		dealerSeniorRecoveryLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
			dealerSeniorRecoveryLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityRecoveryLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupEntry :
			_fundingGroupMap.entrySet())
		{
			java.util.Map<java.lang.String, org.drip.xva.topology.CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
				creditDebtGroupMapEntry : creditDebtGroupMap.entrySet())
			{
				org.drip.state.identifier.EntityRecoveryLabel dealerSeniorRecoveryLabel =
					creditDebtGroupMapEntry.getValue().dealerSeniorRecoveryLabel();

				java.lang.String dealerSeniorRecoveryLabelFQN =
					dealerSeniorRecoveryLabel.fullyQualifiedName();

				if (!dealerSeniorRecoveryLabelMap.containsKey (dealerSeniorRecoveryLabelFQN))
				{
					dealerSeniorRecoveryLabelMap.put (
						dealerSeniorRecoveryLabelFQN,
						dealerSeniorRecoveryLabel
					);
				}
			}
		}

		return dealerSeniorRecoveryLabelMap;
	}

	/**
	 * Retrieve the Client Recovery Label Map
	 * 
	 * @return The Client Recovery Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		clientRecoveryLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel> clientRecoveryLabelMap
			= new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityRecoveryLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupEntry :
			_fundingGroupMap.entrySet())
		{
			java.util.Map<java.lang.String, org.drip.xva.topology.CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
				creditDebtGroupMapEntry : creditDebtGroupMap.entrySet())
			{
				org.drip.state.identifier.EntityRecoveryLabel clientRecoveryLabel =
					creditDebtGroupMapEntry.getValue().clientRecoveryLabel();

				java.lang.String clientRecoveryLabelFQN = clientRecoveryLabel.fullyQualifiedName();

				if (!clientRecoveryLabelMap.containsKey (clientRecoveryLabelFQN))
				{
					clientRecoveryLabelMap.put (
						clientRecoveryLabelFQN,
						clientRecoveryLabel
					);
				}
			}
		}

		return clientRecoveryLabelMap;
	}

	/**
	 * Retrieve the Dealer Subordinate Recovery Label Map
	 * 
	 * @return The Dealer Subordinate Recovery Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		dealerSubordinateRecoveryLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
			dealerSubordinateRecoveryLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityRecoveryLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupEntry :
			_fundingGroupMap.entrySet())
		{
			java.util.Map<java.lang.String, org.drip.xva.topology.CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
				creditDebtGroupMapEntry : creditDebtGroupMap.entrySet())
			{
				org.drip.state.identifier.EntityRecoveryLabel dealerSubordinateRecoveryLabel =
					creditDebtGroupMapEntry.getValue().dealerSubordinateRecoveryLabel();

				java.lang.String dealerSubordinateRecoveryLabelFQN =
					dealerSubordinateRecoveryLabel.fullyQualifiedName();

				if (!dealerSubordinateRecoveryLabelMap.containsKey (dealerSubordinateRecoveryLabelFQN))
				{
					dealerSubordinateRecoveryLabelMap.put (
						dealerSubordinateRecoveryLabelFQN,
						dealerSubordinateRecoveryLabel
					);
				}
			}
		}

		return dealerSubordinateRecoveryLabelMap;
	}

	/**
	 * Retrieve the Dealer Senior Funding Label Map
	 * 
	 * @return The Dealer Senior Funding Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		dealerSeniorFundingLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
			dealerSeniorFundingLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityFundingLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupMapEntry :
			_fundingGroupMap.entrySet())
		{
			org.drip.state.identifier.EntityFundingLabel dealerSeniorFundingLabel =
				fundingGroupMapEntry.getValue().dealerSeniorFundingLabel();

			java.lang.String dealerSeniorFundingLabelFQN = dealerSeniorFundingLabel.fullyQualifiedName();

			if (!dealerSeniorFundingLabelMap.containsKey (dealerSeniorFundingLabelFQN))
			{
				dealerSeniorFundingLabelMap.put (
					dealerSeniorFundingLabelFQN,
					dealerSeniorFundingLabel
				);
			}
		}

		return dealerSeniorFundingLabelMap;
	}

	/**
	 * Retrieve the Client Funding Label Map
	 * 
	 * @return The Client Funding Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		clientFundingLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel> clientFundingLabelMap =
			new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityFundingLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupMapEntry :
			_fundingGroupMap.entrySet())
		{
			org.drip.state.identifier.EntityFundingLabel clientFundingLabel =
				fundingGroupMapEntry.getValue().clientFundingLabel();

			java.lang.String clientFundingLabelFQN = clientFundingLabel.fullyQualifiedName();

			if (!clientFundingLabelMap.containsKey (clientFundingLabelFQN))
			{
				clientFundingLabelMap.put (
					clientFundingLabelFQN,
					clientFundingLabel
				);
			}
		}

		return clientFundingLabelMap;
	}

	/**
	 * Retrieve the Dealer Subordinate Funding Label Map
	 * 
	 * @return The Dealer Subordinate Funding Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		dealerSubordinateFundingLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
			dealerSubordinateFundingLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityFundingLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupMapEntry :
			_fundingGroupMap.entrySet())
		{
			org.drip.state.identifier.EntityFundingLabel dealerSubordinateFundingLabel =
				fundingGroupMapEntry.getValue().dealerSubordinateFundingLabel();

			if (null != dealerSubordinateFundingLabel)
			{
				java.lang.String dealerSubordinateFundingLabelFQN =
					dealerSubordinateFundingLabel.fullyQualifiedName();

				if (!dealerSubordinateFundingLabelMap.containsKey (dealerSubordinateFundingLabelFQN))
				{
					dealerSubordinateFundingLabelMap.put (
						dealerSubordinateFundingLabelFQN,
						dealerSubordinateFundingLabel
					);
				}
			}
		}

		return dealerSubordinateFundingLabelMap;
	}

	/**
	 * Generate the Adiabat Dependent Market Parameters
	 * 
	 * @return The Adiabat Dependent Market Parameters
	 */

	public org.drip.xva.topology.AdiabatMarketParams marketParams()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.CSALabel> csaLabelMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.CSALabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.OvernightLabel> overnightLabelMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.OvernightLabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel> dealerHazardLabelMap =
			new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityHazardLabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel> clientHazardLabelMap =
			new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityHazardLabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
			dealerSeniorRecoveryLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityRecoveryLabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel> clientRecoveryLabelMap
			= new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityRecoveryLabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
			dealerSubordinateRecoveryLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityRecoveryLabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
			dealerSeniorFundingLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityFundingLabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel> clientFundingLabelMap =
			new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityFundingLabel>();

		java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
			dealerSubordinateFundingLabelMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityFundingLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.FundingGroup> fundingGroupEntry :
			_fundingGroupMap.entrySet())
		{
			org.drip.state.identifier.EntityFundingLabel dealerSeniorFundingLabel =
				fundingGroupEntry.getValue().dealerSeniorFundingLabel();

			java.lang.String dealerSeniorFundingLabelFQN = dealerSeniorFundingLabel.fullyQualifiedName();

			if (!dealerSeniorFundingLabelMap.containsKey (dealerSeniorFundingLabelFQN))
			{
				dealerSeniorFundingLabelMap.put (
					dealerSeniorFundingLabelFQN,
					dealerSeniorFundingLabel
				);
			}

			org.drip.state.identifier.EntityFundingLabel clientFundingLabel =
				fundingGroupEntry.getValue().clientFundingLabel();

			java.lang.String clientFundingLabelFQN = clientFundingLabel.fullyQualifiedName();

			if (!clientFundingLabelMap.containsKey (clientFundingLabelFQN))
			{
				clientFundingLabelMap.put (
					clientFundingLabelFQN,
					clientFundingLabel
				);
			}

			org.drip.state.identifier.EntityFundingLabel dealerSubordinateFundingLabel =
				fundingGroupEntry.getValue().dealerSubordinateFundingLabel();

			if (null != dealerSubordinateFundingLabel)
			{
				java.lang.String dealerSubordinateFundingLabelFQN =
					dealerSubordinateFundingLabel.fullyQualifiedName();

				if (!dealerSubordinateFundingLabelMap.containsKey (dealerSubordinateFundingLabelFQN))
				{
					dealerSubordinateFundingLabelMap.put (
						dealerSubordinateFundingLabelFQN,
						dealerSubordinateFundingLabel
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

				org.drip.state.identifier.EntityHazardLabel dealerHazardLabel =
					creditDebtGroupEntry.getValue().dealerHazardLabel();

				java.lang.String dealerHazardLabelFQN = dealerHazardLabel.fullyQualifiedName();

				if (!dealerHazardLabelMap.containsKey (dealerHazardLabelFQN))
				{
					dealerHazardLabelMap.put (
						dealerHazardLabelFQN,
						dealerHazardLabel
					);
				}

				org.drip.state.identifier.EntityHazardLabel clientHazardLabel =
					creditDebtGroupEntry.getValue().clientPartyHazardLabel();

				java.lang.String clientHazardLabelFQN = clientHazardLabel.fullyQualifiedName();

				if (!clientHazardLabelMap.containsKey (clientHazardLabelFQN))
				{
					clientHazardLabelMap.put (
						clientHazardLabelFQN,
						clientHazardLabel
					);
				}

				org.drip.state.identifier.EntityRecoveryLabel dealerSeniorRecoveryLabel =
					creditDebtGroupEntry.getValue().dealerSeniorRecoveryLabel();

				java.lang.String dealerSeniorRecoveryLabelFQN =
					dealerSeniorRecoveryLabel.fullyQualifiedName();

				if (!dealerSeniorRecoveryLabelMap.containsKey (dealerSeniorRecoveryLabelFQN))
				{
					dealerSeniorRecoveryLabelMap.put (
						dealerSeniorRecoveryLabelFQN,
						dealerSeniorRecoveryLabel
					);
				}

				org.drip.state.identifier.EntityRecoveryLabel clientRecoveryLabel =
					creditDebtGroupEntry.getValue().clientRecoveryLabel();

				java.lang.String clientRecoveryLabelFQN = clientRecoveryLabel.fullyQualifiedName();

				if (!clientRecoveryLabelMap.containsKey (clientRecoveryLabelFQN))
				{
					clientRecoveryLabelMap.put (
						clientRecoveryLabelFQN,
						clientRecoveryLabel
					);
				}

				org.drip.state.identifier.EntityRecoveryLabel dealerSubordinateRecoveryLabel =
					creditDebtGroupEntry.getValue().dealerSubordinateRecoveryLabel();

				if (null != dealerSubordinateRecoveryLabel)
				{
					java.lang.String dealerSubordinateRecoveryLabelFQN =
						dealerSubordinateRecoveryLabel.fullyQualifiedName();

					if (!dealerSubordinateRecoveryLabelMap.containsKey (dealerSubordinateRecoveryLabelFQN))
					{
						dealerSubordinateRecoveryLabelMap.put (
							dealerSubordinateRecoveryLabelFQN,
							dealerSubordinateRecoveryLabel
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
			return new org.drip.xva.topology.AdiabatMarketParams (
				overnightLabelMap,
				csaLabelMap,
				dealerHazardLabelMap,
				clientHazardLabelMap,
				dealerSeniorRecoveryLabelMap,
				clientRecoveryLabelMap,
				dealerSubordinateRecoveryLabelMap,
				dealerSeniorFundingLabelMap,
				clientFundingLabelMap,
				dealerSubordinateFundingLabelMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
