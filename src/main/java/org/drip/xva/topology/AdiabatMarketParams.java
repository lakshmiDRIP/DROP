
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
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva">XVA</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/topology">Topology</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/XVA">XVA Analytics Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AdiabatMarketParams
{
	private java.util.Map<java.lang.String, org.drip.state.identifier.CSALabel> _csaLabelMap = null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.OvernightLabel> _overnightLabelMap =
		null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel>
		_clientHazardLabelMap = null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel>
		_dealerHazardLabelMap = null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		_clientRecoveryLabelMap = null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		_dealerSeniorRecoveryLabelMap = null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		_clientFundingLabelMap = null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		_dealerSubordinateRecoveryLabelMap = null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		_dealerSeniorFundingLabelMap = null;
	private java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		_dealerSubordinateFundingLabelMap = null;

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
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AdiabatMarketParams (
		final java.util.Map<java.lang.String, org.drip.state.identifier.OvernightLabel> overnightLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.CSALabel> csaLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel>
			dealerHazardLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel>
			clientHazardLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
			dealerSeniorRecoveryLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
			clientRecoveryLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
			dealerSubordinateRecoveryLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
			dealerSeniorFundingLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
			clientFundingLabelMap,
		final java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
			dealerSubordinateFundingLabelMap)
		throws java.lang.Exception
	{
		if (null == (_overnightLabelMap = overnightLabelMap) || 0 == _overnightLabelMap.size() ||
			null == (_csaLabelMap = csaLabelMap) || 0 == _csaLabelMap.size() ||
			null == (_dealerHazardLabelMap = dealerHazardLabelMap) || 0 == _dealerHazardLabelMap.size() ||
			null == (_clientHazardLabelMap = clientHazardLabelMap) || 0 == _clientHazardLabelMap.size() ||
			null == (_dealerSeniorRecoveryLabelMap = dealerSeniorRecoveryLabelMap) || 0 ==
				_dealerSeniorRecoveryLabelMap.size() ||
			null == (_clientRecoveryLabelMap = clientRecoveryLabelMap) || 0 == _clientRecoveryLabelMap.size()
				||
			null == (_dealerSeniorFundingLabelMap = dealerSeniorFundingLabelMap) || 0 ==
				_dealerSeniorFundingLabelMap.size() ||
			null == (_clientFundingLabelMap = clientFundingLabelMap) || 0 == _clientFundingLabelMap.size())
		{
			throw new java.lang.Exception ("AdiabatMarketParams Constructor => Invalid Inputs");
		}

		_dealerSubordinateFundingLabelMap = dealerSubordinateFundingLabelMap;
		_dealerSubordinateRecoveryLabelMap = dealerSubordinateRecoveryLabelMap;
	}

	/**
	 * Retrieve the Map of Overnight Labels
	 * 
	 * @return The Map of Overnight Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.OvernightLabel> overnightLabelMap()
	{
		return _overnightLabelMap;
	}

	/**
	 * Retrieve the Map of CSA Labels
	 * 
	 * @return The Map of CSA Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.CSALabel> csaLabelMap()
	{
		return _csaLabelMap;
	}

	/**
	 * Retrieve the Map of Dealer Hazard Labels
	 * 
	 * @return The Map of Dealer Hazard Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel>
		dealerHazardLabelMap()
	{
		return _dealerHazardLabelMap;
	}

	/**
	 * Retrieve the Map of Client Hazard Labels
	 * 
	 * @return The Map of Client Hazard Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityHazardLabel>
		clientHazardLabelMap()
	{
		return _clientHazardLabelMap;
	}

	/**
	 * Retrieve the Map of Dealer Senior Recovery Labels
	 * 
	 * @return The Map of Dealer Senior Recovery Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		dealerSeniorRecoveryLabelMap()
	{
		return _dealerSeniorRecoveryLabelMap;
	}

	/**
	 * Retrieve the Map of Client Recovery Labels
	 * 
	 * @return The Map of Client Recovery Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		clientRecoveryLabelMap()
	{
		return _clientRecoveryLabelMap;
	}

	/**
	 * Retrieve the Map of Dealer Subordinate Recovery Labels
	 * 
	 * @return The Map of Dealer Subordinate Recovery Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityRecoveryLabel>
		dealerSubordinateRecoveryLabelMap()
	{
		return _dealerSubordinateRecoveryLabelMap;
	}

	/**
	 * Retrieve the Map of Dealer Senior Funding Labels
	 * 
	 * @return The Map of Dealer Senior Funding Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		dealerSeniorFundingLabelMap()
	{
		return _dealerSeniorFundingLabelMap;
	}

	/**
	 * Retrieve the Map of Client Funding Labels
	 * 
	 * @return The Map of Client Funding Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		clientFundingLabelMap()
	{
		return _clientFundingLabelMap;
	}

	/**
	 * Retrieve the Map of Dealer Subordinate Funding Labels
	 * 
	 * @return The Map of Dealer Subordinate Funding Labels
	 */

	public java.util.Map<java.lang.String, org.drip.state.identifier.EntityFundingLabel>
		dealerSubordinateFundingLabelMap()
	{
		return _dealerSubordinateFundingLabelMap;
	}
}
