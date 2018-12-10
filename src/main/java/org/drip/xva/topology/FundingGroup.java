
package org.drip.xva.topology;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>FundingGroup</i> represents an Aggregation of Credit Debt Groups with a common Funding Group
 * Specification. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Albanese, C., L. Andersen, and, S. Iabichino (2015): The FVA Puzzle: Accounting, Risk Management,
 *  			and Collateral Trading https://papers.ssrn.com/sol3/paper.cfm?abstract_id_2517301
 *  			<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva">XVA</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/topology">Topology</a></li>
 *  </ul>
 * <br><br>
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
	 * Retrieve the Dealer Senior Funding Label
	 * 
	 * @return The Dealer Senior Funding Label
	 */

	public org.drip.state.identifier.EntityFundingLabel dealerSeniorFundingLabel()
	{
		return _fundingGroupSpecification.dealerSeniorFundingLabel();
	}

	/**
	 * Retrieve the Client Funding Label
	 * 
	 * @return The Client Funding Label
	 */

	public org.drip.state.identifier.EntityFundingLabel clientFundingLabel()
	{
		return _fundingGroupSpecification.clientFundingLabel();
	}

	/**
	 * Retrieve the Dealer Subordinate Funding Label
	 * 
	 * @return The Dealer Subordinate Funding Label
	 */

	public org.drip.state.identifier.EntityFundingLabel dealerSubordinateFundingLabel()
	{
		return _fundingGroupSpecification.dealerSubordinateFundingLabel();
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
	 * Retrieve the Dealer Hazard Label Map
	 * 
	 * @return The Dealer Hazard Label Map
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel>
		dealerHazardLabelMap()
	{
		java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel> dealerHazardLabelMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityHazardLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
			creditDebtGroupMapEntry : _creditDebtGroupMap.entrySet())
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
			new org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.identifier.EntityHazardLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
			creditDebtGroupMapEntry : _creditDebtGroupMap.entrySet())
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

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
			creditDebtGroupMapEntry : _creditDebtGroupMap.entrySet())
		{
			org.drip.state.identifier.EntityRecoveryLabel dealerSeniorRecoveryLabel =
				creditDebtGroupMapEntry.getValue().dealerSeniorRecoveryLabel();

			java.lang.String dealerSeniorRecoveryLabelFQN = dealerSeniorRecoveryLabel.fullyQualifiedName();

			if (!dealerSeniorRecoveryLabelMap.containsKey (dealerSeniorRecoveryLabelFQN))
			{
				dealerSeniorRecoveryLabelMap.put (
					dealerSeniorRecoveryLabelFQN,
					dealerSeniorRecoveryLabel
				);
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

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
			creditDebtGroupMapEntry : _creditDebtGroupMap.entrySet())
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

		for (java.util.Map.Entry<java.lang.String, org.drip.xva.topology.CreditDebtGroup>
			creditDebtGroupMapEntry : _creditDebtGroupMap.entrySet())
		{
			org.drip.state.identifier.EntityRecoveryLabel dealerSubordinateRecoveryLabel =
				creditDebtGroupMapEntry.getValue().dealerSubordinateRecoveryLabel();

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
		}

		return dealerSubordinateRecoveryLabelMap;
	}
}
