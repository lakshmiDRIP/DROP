
package org.drip.xva.topology;

import java.util.Map;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.state.identifier.CSALabel;
import org.drip.state.identifier.OvernightLabel;
import org.drip.xva.proto.CollateralGroupSpecification;
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
 * <i>CollateralGroup</i> represents an Aggregation of Position Groups over a common Collateral
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

public class CollateralGroup extends ObjectSpecification
{
	private Map<String, PositionGroup> _positionGroupMap = null;
	private CollateralGroupSpecification _collateralGroupSpecification = null;

	/**
	 * CollateralGroup Constructor
	 * 
	 * @param id The Collateral Group ID
	 * @param name The Collateral Group Name
	 * @param collateralGroupSpecification The Collateral Group Specification
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public CollateralGroup (
		final String id,
		final String name,
		final CollateralGroupSpecification collateralGroupSpecification)
		throws Exception
	{
		super (id, name);

		if (null == (_collateralGroupSpecification = collateralGroupSpecification)) {
			throw new Exception ("CollateralGroup Contructor => Invalid Inputs");
		}

		_positionGroupMap = new CaseInsensitiveHashMap<PositionGroup>();
	}

	/**
	 * Retrieve the Collateral Group Specification
	 * 
	 * @return The Collateral Group Specification
	 */

	public CollateralGroupSpecification collateralGroupSpecification()
	{
		return _collateralGroupSpecification;
	}

	/**
	 * Retrieve the Position Group Map
	 * 
	 * @return The Position Group Map
	 */

	public Map<String, PositionGroup> positionGroupMap()
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
		final PositionGroup positionGroup)
	{
		if (null == positionGroup) {
			return false;
		}

		_positionGroupMap.put (positionGroup.id(), positionGroup);

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
		final String positionGroupID)
	{
		return null != positionGroupID && _positionGroupMap.containsKey (positionGroupID);
	}

	/**
	 * Retrieve the Position Group identified by the specified ID
	 * 
	 * @param positionGroupID The Position Group ID
	 * 
	 * @return The Position Group
	 */

	public PositionGroup positionGroup (
		final String positionGroupID)
	{
		return containsPositionGroup (positionGroupID) ? _positionGroupMap.get (positionGroupID) : null;
	}

	/**
	 * Retrieve the Overnight Label
	 * 
	 * @return The Overnight Label
	 */

	public OvernightLabel overnightLabel()
	{
		return _collateralGroupSpecification.overnightLabel();
	}

	/**
	 * Retrieve the CSA Label
	 * 
	 * @return The CSA Label
	 */

	public CSALabel csaLabel()
	{
		return _collateralGroupSpecification.csaLabel();
	}
}
