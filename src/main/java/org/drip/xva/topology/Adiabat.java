
package org.drip.xva.topology;

import java.util.Map;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.state.identifier.CSALabel;
import org.drip.state.identifier.EntityFundingLabel;
import org.drip.state.identifier.EntityHazardLabel;
import org.drip.state.identifier.EntityRecoveryLabel;
import org.drip.state.identifier.OvernightLabel;
import org.drip.xva.proto.ObjectSpecification;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>Adiabat</i> represents the Directed Graph of all the Encompassing Funding Groups inside of a Closed
 * System (i.e., Adiabat). The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md">Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/topology/README.md">Collateral, Credit/Debt, Funding Topologies</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Adiabat extends ObjectSpecification
{
	private Map<String, FundingGroup> _fundingGroupMap = null;

	/**
	 * Adiabat Constructor
	 * 
	 * @param id BookGraph ID
	 * @param name BookGraph Name
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public Adiabat (
		final String id,
		final String name)
		throws Exception
	{
		super (id, name);

		_fundingGroupMap = new CaseInsensitiveHashMap<FundingGroup>();
	}

	/**
	 * Retrieve the Funding Group Map
	 * 
	 * @return The Funding Group Map
	 */

	public Map<String, FundingGroup> fundingGroupMap()
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
		final FundingGroup fundingGroup)
	{
		if (null == fundingGroup) {
			return false;
		}

		_fundingGroupMap.put (fundingGroup.id(), fundingGroup);

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
		final String fundingGroupID)
	{
		return null != fundingGroupID && _fundingGroupMap.containsKey (fundingGroupID);
	}

	/**
	 * Retrieve the Funding Group identified by the ID
	 * 
	 * @param fundingGroupID The Funding Group ID
	 * 
	 * @return TRUE - The Funding Group
	 */

	public FundingGroup fundingGroup (
		final String fundingGroupID)
	{
		return containsFundingGroup (fundingGroupID) ? _fundingGroupMap.get (fundingGroupID) : null;
	}

	/**
	 * Retrieve the Overnight Label Map
	 * 
	 * @return The Overnight Label Map
	 */

	public Map<String, OvernightLabel> overnightLabelMap()
	{
		Map<String, OvernightLabel> overnightLabelMap = new CaseInsensitiveHashMap<OvernightLabel>();

		for (Map.Entry<String, FundingGroup> fundingGroupEntry : _fundingGroupMap.entrySet()) {
			Map<String, CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (Map.Entry<String, CreditDebtGroup> creditDebtGroupEntry : creditDebtGroupMap.entrySet()) {
				Map<String, CollateralGroup> collateralGroupMap =
					creditDebtGroupEntry.getValue().collateralGroupMap();

				for (Map.Entry<String, CollateralGroup> collateralGroupMapEntry :
					collateralGroupMap.entrySet()) {
					OvernightLabel overnightLabel = collateralGroupMapEntry.getValue().overnightLabel();

					String overnightLabelFQN = overnightLabel.fullyQualifiedName();

					if (!overnightLabelMap.containsKey (overnightLabelFQN)) {
						overnightLabelMap.put (overnightLabelFQN, overnightLabel);
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

	public Map<String, CSALabel> csaLabelMap()
	{
		Map<String, CSALabel> csaLabelMap = new CaseInsensitiveHashMap<CSALabel>();

		for (Map.Entry<String, FundingGroup> fundingGroupEntry : _fundingGroupMap.entrySet()) {
			Map<String, CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (Map.Entry<String, CreditDebtGroup> creditDebtGroupEntry : creditDebtGroupMap.entrySet()) {
				Map<String, CollateralGroup> collateralGroupMap =
					creditDebtGroupEntry.getValue().collateralGroupMap();

				for (Map.Entry<String, CollateralGroup> collateralGroupMapEntry :
					collateralGroupMap.entrySet()) {
					CSALabel csaLabel = collateralGroupMapEntry.getValue().csaLabel();

					String csaLabelFQN = csaLabel.fullyQualifiedName();

					if (!csaLabelMap.containsKey (csaLabelFQN)) {
						csaLabelMap.put (csaLabelFQN, csaLabel);
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

	public Map<String, EntityHazardLabel> dealerHazardLabelMap()
	{
		Map<String, EntityHazardLabel> dealerHazardLabelMap =
			new CaseInsensitiveHashMap<EntityHazardLabel>();

		for (Map.Entry<String, FundingGroup> fundingGroupEntry : _fundingGroupMap.entrySet()) {
			Map<String, CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (Map.Entry<String, CreditDebtGroup> creditDebtGroupMapEntry : creditDebtGroupMap.entrySet())
			{
				EntityHazardLabel dealerHazardLabel = creditDebtGroupMapEntry.getValue().dealerHazardLabel();

				String dealerHazardLabelFQN = dealerHazardLabel.fullyQualifiedName();

				if (!dealerHazardLabelMap.containsKey (dealerHazardLabelFQN)) {
					dealerHazardLabelMap.put (dealerHazardLabelFQN, dealerHazardLabel);
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

	public Map<String, EntityHazardLabel> clientHazardLabelMap()
	{
		Map<String, EntityHazardLabel> clientHazardLabelMap =
			new CaseInsensitiveHashMap<EntityHazardLabel>();

		for (Map.Entry<String, FundingGroup> fundingGroupEntry : _fundingGroupMap.entrySet()) {
			Map<String, CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (Map.Entry<String, CreditDebtGroup> creditDebtGroupMapEntry : creditDebtGroupMap.entrySet())
			{
				EntityHazardLabel clientHazardLabel =
					creditDebtGroupMapEntry.getValue().clientPartyHazardLabel();

				String clientHazardLabelFQN = clientHazardLabel.fullyQualifiedName();

				if (!clientHazardLabelMap.containsKey (clientHazardLabelFQN)) {
					clientHazardLabelMap.put (clientHazardLabelFQN, clientHazardLabel);
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

	public Map<String, EntityRecoveryLabel> dealerSeniorRecoveryLabelMap()
	{
		Map<String, EntityRecoveryLabel> dealerSeniorRecoveryLabelMap =
			new CaseInsensitiveHashMap<EntityRecoveryLabel>();

		for (Map.Entry<String, FundingGroup> fundingGroupEntry : _fundingGroupMap.entrySet()) {
			Map<String, CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (Map.Entry<String, CreditDebtGroup> creditDebtGroupMapEntry : creditDebtGroupMap.entrySet())
			{
				EntityRecoveryLabel dealerSeniorRecoveryLabel =
					creditDebtGroupMapEntry.getValue().dealerSeniorRecoveryLabel();

				String dealerSeniorRecoveryLabelFQN = dealerSeniorRecoveryLabel.fullyQualifiedName();

				if (!dealerSeniorRecoveryLabelMap.containsKey (dealerSeniorRecoveryLabelFQN)) {
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

	public Map<String, EntityRecoveryLabel> clientRecoveryLabelMap()
	{
		Map<String, EntityRecoveryLabel> clientRecoveryLabelMap =
			new CaseInsensitiveHashMap<EntityRecoveryLabel>();

		for (Map.Entry<String, FundingGroup> fundingGroupEntry : _fundingGroupMap.entrySet()) {
			Map<String, CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (Map.Entry<String, CreditDebtGroup> creditDebtGroupMapEntry : creditDebtGroupMap.entrySet())
			{
				EntityRecoveryLabel clientRecoveryLabel =
					creditDebtGroupMapEntry.getValue().clientRecoveryLabel();

				String clientRecoveryLabelFQN = clientRecoveryLabel.fullyQualifiedName();

				if (!clientRecoveryLabelMap.containsKey (clientRecoveryLabelFQN)) {
					clientRecoveryLabelMap.put (clientRecoveryLabelFQN, clientRecoveryLabel);
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

	public Map<String, EntityRecoveryLabel> dealerSubordinateRecoveryLabelMap()
	{
		Map<String, EntityRecoveryLabel> dealerSubordinateRecoveryLabelMap =
			new CaseInsensitiveHashMap<EntityRecoveryLabel>();

		for (Map.Entry<String, FundingGroup> fundingGroupEntry : _fundingGroupMap.entrySet()) {
			Map<String, CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (Map.Entry<String, CreditDebtGroup> creditDebtGroupMapEntry : creditDebtGroupMap.entrySet())
			{
				EntityRecoveryLabel dealerSubordinateRecoveryLabel =
					creditDebtGroupMapEntry.getValue().dealerSubordinateRecoveryLabel();

				String dealerSubordinateRecoveryLabelFQN =
					dealerSubordinateRecoveryLabel.fullyQualifiedName();

				if (!dealerSubordinateRecoveryLabelMap.containsKey (dealerSubordinateRecoveryLabelFQN)) {
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

	public Map<String, EntityFundingLabel> dealerSeniorFundingLabelMap()
	{
		Map<String, EntityFundingLabel> dealerSeniorFundingLabelMap =
			new CaseInsensitiveHashMap<EntityFundingLabel>();

		for (Map.Entry<String, FundingGroup> fundingGroupMapEntry : _fundingGroupMap.entrySet()) {
			EntityFundingLabel dealerSeniorFundingLabel =
				fundingGroupMapEntry.getValue().dealerSeniorFundingLabel();

			String dealerSeniorFundingLabelFQN = dealerSeniorFundingLabel.fullyQualifiedName();

			if (!dealerSeniorFundingLabelMap.containsKey (dealerSeniorFundingLabelFQN)) {
				dealerSeniorFundingLabelMap.put (dealerSeniorFundingLabelFQN, dealerSeniorFundingLabel);
			}
		}

		return dealerSeniorFundingLabelMap;
	}

	/**
	 * Retrieve the Client Funding Label Map
	 * 
	 * @return The Client Funding Label Map
	 */

	public Map<String, EntityFundingLabel> clientFundingLabelMap()
	{
		Map<String, EntityFundingLabel> clientFundingLabelMap =
			new CaseInsensitiveHashMap<EntityFundingLabel>();

		for (Map.Entry<String, FundingGroup> fundingGroupMapEntry : _fundingGroupMap.entrySet()) {
			EntityFundingLabel clientFundingLabel = fundingGroupMapEntry.getValue().clientFundingLabel();

			String clientFundingLabelFQN = clientFundingLabel.fullyQualifiedName();

			if (!clientFundingLabelMap.containsKey (clientFundingLabelFQN)) {
				clientFundingLabelMap.put (clientFundingLabelFQN, clientFundingLabel);
			}
		}

		return clientFundingLabelMap;
	}

	/**
	 * Retrieve the Dealer Subordinate Funding Label Map
	 * 
	 * @return The Dealer Subordinate Funding Label Map
	 */

	public Map<String, EntityFundingLabel> dealerSubordinateFundingLabelMap()
	{
		Map<String, EntityFundingLabel> dealerSubordinateFundingLabelMap =
			new CaseInsensitiveHashMap<EntityFundingLabel>();

		for (Map.Entry<String, FundingGroup> fundingGroupMapEntry : _fundingGroupMap.entrySet()) {
			EntityFundingLabel dealerSubordinateFundingLabel =
				fundingGroupMapEntry.getValue().dealerSubordinateFundingLabel();

			if (null != dealerSubordinateFundingLabel) {
				String dealerSubordinateFundingLabelFQN = dealerSubordinateFundingLabel.fullyQualifiedName();

				if (!dealerSubordinateFundingLabelMap.containsKey (dealerSubordinateFundingLabelFQN)) {
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

	public AdiabatMarketParams marketParams()
	{
		Map<String, CSALabel> csaLabelMap = new CaseInsensitiveHashMap<CSALabel>();

		Map<String, OvernightLabel> overnightLabelMap = new CaseInsensitiveHashMap<OvernightLabel>();

		Map<String, EntityHazardLabel> clientHazardLabelMap =
			new CaseInsensitiveHashMap<EntityHazardLabel>();

		Map<String, EntityHazardLabel> dealerHazardLabelMap =
			new CaseInsensitiveHashMap<EntityHazardLabel>();

		Map<String, EntityRecoveryLabel> clientRecoveryLabelMap =
			new CaseInsensitiveHashMap<EntityRecoveryLabel>();

		Map<String, EntityRecoveryLabel> dealerSeniorRecoveryLabelMap =
			new CaseInsensitiveHashMap<EntityRecoveryLabel>();

		Map<String, EntityRecoveryLabel> dealerSubordinateRecoveryLabelMap =
			new CaseInsensitiveHashMap<EntityRecoveryLabel>();

		Map<String, EntityFundingLabel> dealerSeniorFundingLabelMap =
			new CaseInsensitiveHashMap<EntityFundingLabel>();

		Map<String, EntityFundingLabel> clientFundingLabelMap =
			new CaseInsensitiveHashMap<EntityFundingLabel>();

		Map<String, EntityFundingLabel> dealerSubordinateFundingLabelMap =
			new CaseInsensitiveHashMap<EntityFundingLabel>();

		for (Map.Entry<String, FundingGroup> fundingGroupEntry : _fundingGroupMap.entrySet()) {
			EntityFundingLabel dealerSeniorFundingLabel =
				fundingGroupEntry.getValue().dealerSeniorFundingLabel();

			String dealerSeniorFundingLabelFQN = dealerSeniorFundingLabel.fullyQualifiedName();

			if (!dealerSeniorFundingLabelMap.containsKey (dealerSeniorFundingLabelFQN)) {
				dealerSeniorFundingLabelMap.put (dealerSeniorFundingLabelFQN, dealerSeniorFundingLabel);
			}

			EntityFundingLabel clientFundingLabel = fundingGroupEntry.getValue().clientFundingLabel();

			String clientFundingLabelFQN = clientFundingLabel.fullyQualifiedName();

			if (!clientFundingLabelMap.containsKey (clientFundingLabelFQN)) {
				clientFundingLabelMap.put (clientFundingLabelFQN, clientFundingLabel);
			}

			EntityFundingLabel dealerSubordinateFundingLabel =
				fundingGroupEntry.getValue().dealerSubordinateFundingLabel();

			if (null != dealerSubordinateFundingLabel) {
				String dealerSubordinateFundingLabelFQN = dealerSubordinateFundingLabel.fullyQualifiedName();

				if (!dealerSubordinateFundingLabelMap.containsKey (dealerSubordinateFundingLabelFQN)) {
					dealerSubordinateFundingLabelMap.put (
						dealerSubordinateFundingLabelFQN,
						dealerSubordinateFundingLabel
					);
				}
			}

			Map<String, CreditDebtGroup> creditDebtGroupMap =
				fundingGroupEntry.getValue().creditDebtGroupMap();

			for (Map.Entry<String, CreditDebtGroup> creditDebtGroupEntry : creditDebtGroupMap.entrySet()) {
				Map<String, CollateralGroup> collateralGroupMap =
					creditDebtGroupEntry.getValue().collateralGroupMap();

				EntityHazardLabel dealerHazardLabel = creditDebtGroupEntry.getValue().dealerHazardLabel();

				String dealerHazardLabelFQN = dealerHazardLabel.fullyQualifiedName();

				if (!dealerHazardLabelMap.containsKey (dealerHazardLabelFQN)) {
					dealerHazardLabelMap.put (dealerHazardLabelFQN, dealerHazardLabel);
				}

				EntityHazardLabel clientHazardLabel =
					creditDebtGroupEntry.getValue().clientPartyHazardLabel();

				String clientHazardLabelFQN = clientHazardLabel.fullyQualifiedName();

				if (!clientHazardLabelMap.containsKey (clientHazardLabelFQN)) {
					clientHazardLabelMap.put (clientHazardLabelFQN, clientHazardLabel);
				}

				EntityRecoveryLabel dealerSeniorRecoveryLabel =
					creditDebtGroupEntry.getValue().dealerSeniorRecoveryLabel();

				String dealerSeniorRecoveryLabelFQN =
					dealerSeniorRecoveryLabel.fullyQualifiedName();

				if (!dealerSeniorRecoveryLabelMap.containsKey (dealerSeniorRecoveryLabelFQN)) {
					dealerSeniorRecoveryLabelMap.put (
						dealerSeniorRecoveryLabelFQN,
						dealerSeniorRecoveryLabel
					);
				}

				EntityRecoveryLabel clientRecoveryLabel =
					creditDebtGroupEntry.getValue().clientRecoveryLabel();

				String clientRecoveryLabelFQN = clientRecoveryLabel.fullyQualifiedName();

				if (!clientRecoveryLabelMap.containsKey (clientRecoveryLabelFQN)) {
					clientRecoveryLabelMap.put (clientRecoveryLabelFQN, clientRecoveryLabel);
				}

				EntityRecoveryLabel dealerSubordinateRecoveryLabel =
					creditDebtGroupEntry.getValue().dealerSubordinateRecoveryLabel();

				if (null != dealerSubordinateRecoveryLabel) {
					String dealerSubordinateRecoveryLabelFQN =
						dealerSubordinateRecoveryLabel.fullyQualifiedName();

					if (!dealerSubordinateRecoveryLabelMap.containsKey (dealerSubordinateRecoveryLabelFQN)) {
						dealerSubordinateRecoveryLabelMap.put (
							dealerSubordinateRecoveryLabelFQN,
							dealerSubordinateRecoveryLabel
						);
					}
				}

				for (Map.Entry<String, CollateralGroup> collateralGroupMapEntry :
					collateralGroupMap.entrySet()) {
					OvernightLabel overnightLabel = collateralGroupMapEntry.getValue().overnightLabel();

					String overnightLabelFQN = overnightLabel.fullyQualifiedName();

					if (!overnightLabelMap.containsKey (overnightLabelFQN)) {
						overnightLabelMap.put (overnightLabelFQN, overnightLabel);
					}

					CSALabel csaLabel = collateralGroupMapEntry.getValue().csaLabel();

					String csaLabelFQN = csaLabel.fullyQualifiedName();

					if (!csaLabelMap.containsKey (csaLabelFQN)) {
						csaLabelMap.put (csaLabelFQN, csaLabel);
					}
				}
			}
		}

		try {
			return new AdiabatMarketParams (
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
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
