
package org.drip.xva.topology;

import java.util.Map;

import org.drip.state.identifier.CSALabel;
import org.drip.state.identifier.EntityFundingLabel;
import org.drip.state.identifier.EntityHazardLabel;
import org.drip.state.identifier.EntityRecoveryLabel;
import org.drip.state.identifier.OvernightLabel;

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
 * <i>AdiabatMarketParams</i> contains the Market Parameters that correspond to a given Adiabat. The
 * References are:
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

public class AdiabatMarketParams
{
	private Map<String, CSALabel> _csaLabelMap = null;
	private Map<String, OvernightLabel> _overnightLabelMap = null;
	private Map<String, EntityHazardLabel> _clientHazardLabelMap = null;
	private Map<String, EntityHazardLabel> _dealerHazardLabelMap = null;
	private Map<String, EntityFundingLabel> _clientFundingLabelMap = null;
	private Map<String, EntityRecoveryLabel> _clientRecoveryLabelMap = null;
	private Map<String, EntityFundingLabel> _dealerSeniorFundingLabelMap = null;
	private Map<String, EntityRecoveryLabel> _dealerSeniorRecoveryLabelMap = null;
	private Map<String, EntityFundingLabel> _dealerSubordinateFundingLabelMap = null;
	private Map<String, EntityRecoveryLabel> _dealerSubordinateRecoveryLabelMap = null;

	/**
	 * AdiabatMarketParams Constructor
	 * 
	 * @param overnightLabelMap Map of Overnight Labels
	 * @param csaLabelMap Map of CSA Labels
	 * @param dealerHazardLabelMap Map of Dealer Hazard Labels
	 * @param clientHazardLabelMap Map of Client Hazard Labels
	 * @param dealerSeniorRecoveryLabelMap Map of Dealer Senior Recovery Labels
	 * @param clientRecoveryLabelMap Map of Client Recovery Labels
	 * @param dealerSubordinateRecoveryLabelMap Map of Dealer Subordinate Recovery Labels
	 * @param dealerSeniorFundingLabelMap Map of Dealer Senior Funding Labels
	 * @param clientFundingLabelMap Map of Client Funding Labels
	 * @param dealerSubordinateFundingLabelMap Map of Dealer Subordinate Funding Labels
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public AdiabatMarketParams (
		final Map<String, OvernightLabel> overnightLabelMap,
		final Map<String, CSALabel> csaLabelMap,
		final Map<String, EntityHazardLabel> dealerHazardLabelMap,
		final Map<String, EntityHazardLabel> clientHazardLabelMap,
		final Map<String, EntityRecoveryLabel> dealerSeniorRecoveryLabelMap,
		final Map<String, EntityRecoveryLabel> clientRecoveryLabelMap,
		final Map<String, EntityRecoveryLabel> dealerSubordinateRecoveryLabelMap,
		final Map<String, EntityFundingLabel> dealerSeniorFundingLabelMap,
		final Map<String, EntityFundingLabel> clientFundingLabelMap,
		final Map<String, EntityFundingLabel> dealerSubordinateFundingLabelMap)
		throws Exception
	{
		if (null == (_overnightLabelMap = overnightLabelMap) || 0 == _overnightLabelMap.size() ||
			null == (_csaLabelMap = csaLabelMap) || 0 == _csaLabelMap.size() ||
			null == (_dealerHazardLabelMap = dealerHazardLabelMap) || 0 == _dealerHazardLabelMap.size() ||
			null == (_clientHazardLabelMap = clientHazardLabelMap) || 0 == _clientHazardLabelMap.size() ||
			null == (_dealerSeniorRecoveryLabelMap = dealerSeniorRecoveryLabelMap) ||
				0 == _dealerSeniorRecoveryLabelMap.size() ||
			null == (_clientRecoveryLabelMap = clientRecoveryLabelMap) ||
				0 == _clientRecoveryLabelMap.size() ||
			null == (_dealerSeniorFundingLabelMap = dealerSeniorFundingLabelMap) ||
				0 == _dealerSeniorFundingLabelMap.size() ||
			null == (_clientFundingLabelMap = clientFundingLabelMap) || 0 == _clientFundingLabelMap.size()) {
			throw new Exception ("AdiabatMarketParams Constructor => Invalid Inputs");
		}

		_dealerSubordinateFundingLabelMap = dealerSubordinateFundingLabelMap;
		_dealerSubordinateRecoveryLabelMap = dealerSubordinateRecoveryLabelMap;
	}

	/**
	 * Retrieve the Map of Overnight Labels
	 * 
	 * @return The Map of Overnight Labels
	 */

	public Map<String, OvernightLabel> overnightLabelMap()
	{
		return _overnightLabelMap;
	}

	/**
	 * Retrieve the Map of CSA Labels
	 * 
	 * @return The Map of CSA Labels
	 */

	public Map<String, CSALabel> csaLabelMap()
	{
		return _csaLabelMap;
	}

	/**
	 * Retrieve the Map of Dealer Hazard Labels
	 * 
	 * @return The Map of Dealer Hazard Labels
	 */

	public Map<String, EntityHazardLabel> dealerHazardLabelMap()
	{
		return _dealerHazardLabelMap;
	}

	/**
	 * Retrieve the Map of Client Hazard Labels
	 * 
	 * @return The Map of Client Hazard Labels
	 */

	public Map<String, EntityHazardLabel> clientHazardLabelMap()
	{
		return _clientHazardLabelMap;
	}

	/**
	 * Retrieve the Map of Dealer Senior Recovery Labels
	 * 
	 * @return The Map of Dealer Senior Recovery Labels
	 */

	public Map<String, EntityRecoveryLabel> dealerSeniorRecoveryLabelMap()
	{
		return _dealerSeniorRecoveryLabelMap;
	}

	/**
	 * Retrieve the Map of Client Recovery Labels
	 * 
	 * @return The Map of Client Recovery Labels
	 */

	public Map<String, EntityRecoveryLabel> clientRecoveryLabelMap()
	{
		return _clientRecoveryLabelMap;
	}

	/**
	 * Retrieve the Map of Dealer Subordinate Recovery Labels
	 * 
	 * @return The Map of Dealer Subordinate Recovery Labels
	 */

	public Map<String, EntityRecoveryLabel> dealerSubordinateRecoveryLabelMap()
	{
		return _dealerSubordinateRecoveryLabelMap;
	}

	/**
	 * Retrieve the Map of Dealer Senior Funding Labels
	 * 
	 * @return The Map of Dealer Senior Funding Labels
	 */

	public Map<String, EntityFundingLabel> dealerSeniorFundingLabelMap()
	{
		return _dealerSeniorFundingLabelMap;
	}

	/**
	 * Retrieve the Map of Client Funding Labels
	 * 
	 * @return The Map of Client Funding Labels
	 */

	public Map<String, EntityFundingLabel> clientFundingLabelMap()
	{
		return _clientFundingLabelMap;
	}

	/**
	 * Retrieve the Map of Dealer Subordinate Funding Labels
	 * 
	 * @return The Map of Dealer Subordinate Funding Labels
	 */

	public Map<String, EntityFundingLabel> dealerSubordinateFundingLabelMap()
	{
		return _dealerSubordinateFundingLabelMap;
	}
}
