
package org.drip.xva.topology;

import java.util.Map;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.state.identifier.CSALabel;
import org.drip.state.identifier.EntityHazardLabel;
import org.drip.state.identifier.EntityRecoveryLabel;
import org.drip.state.identifier.OvernightLabel;
import org.drip.xva.proto.CreditDebtGroupSpecification;
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
 * <i>CreditDebtGroup</i> represents an Aggregation of Collateral Groups with a common Credit Debt
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md">Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/topology/README.md">Collateral, Credit/Debt, Funding Topologies</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CreditDebtGroup extends ObjectSpecification
{
	private Map<String, CollateralGroup> _collateralGroupMap = null;
	private CreditDebtGroupSpecification _creditDebtGroupSpecification = null;

	/**
	 * CreditDebtGroup Constructor
	 * 
	 * @param id CreditDebtGroup ID
	 * @param name CreditDebtGroup Name
	 * @param creditDebtGroupSpecification The CreditDebtGroup Specification
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public CreditDebtGroup (
		final String id,
		final String name,
		final CreditDebtGroupSpecification creditDebtGroupSpecification)
		throws Exception
	{
		super (id, name);

		if (null == (_creditDebtGroupSpecification = creditDebtGroupSpecification)) {
			throw new Exception ("CreditDebtGroup Constructor => Invalid Inputs");
		}

		_collateralGroupMap = new CaseInsensitiveHashMap<CollateralGroup>();
	}

	/**
	 * Retrieve the Credit Debt Group Specification
	 * 
	 * @return The Credit Debt Group Specification
	 */

	public CreditDebtGroupSpecification creditDebtGroupSpecification()
	{
		return _creditDebtGroupSpecification;
	}

	/**
	 * Retrieve the Collateral Group Map
	 * 
	 * @return The Collateral Group Map
	 */

	public Map<String, CollateralGroup> collateralGroupMap()
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
		final CollateralGroup collateralGroup)
	{
		if (null == collateralGroup) {
			return false;
		}

		_collateralGroupMap.put (collateralGroup.id(), collateralGroup);

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
		final String collateralGroupID)
	{
		return null != collateralGroupID && _collateralGroupMap.containsKey (collateralGroupID);
	}

	/**
	 * Retrieve the Collateral Group identified by the specified ID
	 * 
	 * @param collateralGroupID The Collateral Group ID
	 * 
	 * @return The Collateral Group
	 */

	public CollateralGroup collateralGroup (
		final String collateralGroupID)
	{
		return containsCollateralGroup (collateralGroupID) ?
			_collateralGroupMap.get (collateralGroupID) : null;
	}

	/**
	 * Retrieve the Dealer Hazard Label
	 * 
	 * @return The Dealer Hazard Label
	 */

	public EntityHazardLabel dealerHazardLabel()
	{
		return _creditDebtGroupSpecification.dealerHazardLabel();
	}

	/**
	 * Retrieve the Client Hazard Label
	 * 
	 * @return The Client Hazard Label
	 */

	public EntityHazardLabel clientPartyHazardLabel()
	{
		return _creditDebtGroupSpecification.clientHazardLabel();
	}

	/**
	 * Retrieve the Dealer Senior Recovery Label
	 * 
	 * @return The Dealer Senior Recovery Label
	 */

	public EntityRecoveryLabel dealerSeniorRecoveryLabel()
	{
		return _creditDebtGroupSpecification.dealerSeniorRecoveryLabel();
	}

	/**
	 * Retrieve the Dealer Subordinate Recovery Label
	 * 
	 * @return The Dealer Subordinate Recovery Label
	 */

	public EntityRecoveryLabel dealerSubordinateRecoveryLabel()
	{
		return _creditDebtGroupSpecification.dealerSubordinateRecoveryLabel();
	}

	/**
	 * Retrieve the Client Senior Recovery Label
	 * 
	 * @return The Client Senior Recovery Label
	 */

	public EntityRecoveryLabel clientRecoveryLabel()
	{
		return _creditDebtGroupSpecification.clientRecoveryLabel();
	}

	/**
	 * Retrieve the Overnight Label Map
	 * 
	 * @return The Overnight Label Map
	 */

	public Map<String, OvernightLabel> overnightLabelMap()
	{
		Map<String, OvernightLabel> overnightLabelMap = new CaseInsensitiveHashMap<OvernightLabel>();

		for (Map.Entry<String, CollateralGroup> collateralGroupMapEntry : _collateralGroupMap.entrySet()) {
			OvernightLabel overnightLabel = collateralGroupMapEntry.getValue().overnightLabel();

			String overnightLabelFQN = overnightLabel.fullyQualifiedName();

			if (!overnightLabelMap.containsKey (overnightLabelFQN)) {
				overnightLabelMap.put (overnightLabelFQN, overnightLabel);
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

		for (Map.Entry<String, CollateralGroup> collateralGroupMapEntry : _collateralGroupMap.entrySet()) {
			org.drip.state.identifier.CSALabel csaLabel = collateralGroupMapEntry.getValue().csaLabel();

			String csaLabelFQN = csaLabel.fullyQualifiedName();

			if (!csaLabelMap.containsKey (csaLabelFQN)) {
				csaLabelMap.put (csaLabelFQN, csaLabel);
			}
		}

		return csaLabelMap;
	}
}
