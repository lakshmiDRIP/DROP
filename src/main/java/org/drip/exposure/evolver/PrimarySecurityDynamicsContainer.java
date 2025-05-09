
package org.drip.exposure.evolver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>PrimarySecurityDynamicsContainer</i> holds the Economy with the following Traded Assets - the Overnight
 * Index Numeraire, the Collateral Scheme Numeraire, the Default-able Dealer Bond Numeraire, the Array of
 * Default-able Client Numeraires, and an Asset that follows Brownian Motion. The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and
 *  				the Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955
 *  				<b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies <i>Risk</i> <b>23
 *  				(12)</b> 82-87
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  		<li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure Group Level Collateralized/Uncollateralized Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/evolver/README.md">Securities and Exposure States Evolvers</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PrimarySecurityDynamicsContainer extends org.drip.exposure.evolver.DynamicsContainer
{
	private java.lang.String _csaID = null;
	private java.lang.String _overnightID = null;
	private java.lang.String _clientFundingID = null;
	private java.lang.String _dealerSeniorFundingID = null;
	private java.lang.String _dealerSubordinateFundingID = null;
	private java.util.List<java.lang.String> _assetIDList = null;

	/**
	 * PrimarySecurityDynamicsContainer Constructor
	 * 
	 * @param assetList The List of Asset Primary Securities
	 * @param overnight The Overnight Index Primary Security
	 * @param csa The CSA Primary Security
	 * @param dealerSeniorFunding Dealer Senior Funding Primary Security
	 * @param dealerSubordinateFunding Dealer Subordinate Funding Primary Security
	 * @param clientFunding Client Funding Primary Security
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PrimarySecurityDynamicsContainer (
		final java.util.List<org.drip.exposure.evolver.PrimarySecurity> assetList,
		final org.drip.exposure.evolver.PrimarySecurity overnight,
		final org.drip.exposure.evolver.PrimarySecurity csa,
		final org.drip.exposure.evolver.PrimarySecurity dealerSeniorFunding,
		final org.drip.exposure.evolver.PrimarySecurity dealerSubordinateFunding,
		final org.drip.exposure.evolver.PrimarySecurity clientFunding)
		throws java.lang.Exception
	{
		if (!addPrimarySecurity (overnight) ||
			!addPrimarySecurity (csa) ||
			!addPrimarySecurity (dealerSeniorFunding) ||
			!addPrimarySecurity (clientFunding) ||
			!addPrimarySecurity (dealerSubordinateFunding))
		{
			throw new java.lang.Exception ("PrimarySecurityDynamicsContainer Constructor => Invalid Inputs");
		}

		if (null != assetList && 0 != assetList.size())
		{
			_assetIDList = new java.util.ArrayList<java.lang.String>();

			for (org.drip.exposure.evolver.PrimarySecurity asset : assetList)
			{
				addPrimarySecurity (asset);

				if (null != asset)
				{
					_assetIDList.add (asset.id());
				}
			}
		}

		org.drip.state.identifier.LatentStateLabel csaLabel = csa.label();

		org.drip.state.identifier.LatentStateLabel overnightLabel = overnight.label();

		org.drip.state.identifier.LatentStateLabel clientFundingLabel = clientFunding.label();

		org.drip.state.identifier.LatentStateLabel dealerSeniorFundingLabel = dealerSeniorFunding.label();

		org.drip.state.identifier.LatentStateLabel dealerSubordinateFundingLabel = null ==
			dealerSubordinateFunding ? null : dealerSubordinateFunding.label();

		if (!(csaLabel instanceof org.drip.state.identifier.CSALabel) ||
			!(overnightLabel instanceof org.drip.state.identifier.OvernightLabel) ||
			!(dealerSeniorFundingLabel instanceof org.drip.state.identifier.EntityFundingLabel) ||
			!(clientFundingLabel instanceof org.drip.state.identifier.EntityFundingLabel) ||
			(null != dealerSubordinateFundingLabel && !(dealerSubordinateFundingLabel instanceof
				org.drip.state.identifier.EntityFundingLabel)))
		{
			throw new java.lang.Exception ("PrimarySecurityDynamicsContainer Constructor => Invalid Inputs");
		}

		_csaID = csa.id();

		_overnightID = overnight.id();

		_clientFundingID = clientFunding.id();

		_dealerSeniorFundingID = dealerSeniorFunding.id();

		_dealerSubordinateFundingID = null == dealerSubordinateFundingLabel ? null :
			dealerSubordinateFunding.id();
	}

	/**
	 * Retrieve the Asset Primary Security List
	 * 
	 * @return The Asset Primary Security List
	 */

	public java.util.List<org.drip.exposure.evolver.PrimarySecurity> assetList()
	{
		if (null == _assetIDList || 0 == _assetIDList.size())
		{
			return null;
		}

		java.util.List<org.drip.exposure.evolver.PrimarySecurity> assetList = new
			java.util.ArrayList<org.drip.exposure.evolver.PrimarySecurity>();

		for (java.lang.String assetID : _assetIDList)
		{
			assetList.add (primarySecurity (assetID));
		}

		return assetList;
	}

	/**
	 * Retrieve the Overnight Index Primary Security
	 * 
	 * @return The Overnight Index Primary Security
	 */

	public org.drip.exposure.evolver.PrimarySecurity overnight()
	{
		return primarySecurity (_overnightID);
	}

	/**
	 * Retrieve the CSA Primary Security
	 * 
	 * @return The CSA Primary Security
	 */

	public org.drip.exposure.evolver.PrimarySecurity csa()
	{
		return primarySecurity (_csaID);
	}

	/**
	 * Retrieve the Dealer Senior Funding Primary Security
	 * 
	 * @return The Dealer Senior Funding Primary Security
	 */

	public org.drip.exposure.evolver.PrimarySecurity dealerSeniorFunding()
	{
		return primarySecurity (_dealerSeniorFundingID);
	}

	/**
	 * Retrieve the Dealer Subordinate Funding Primary Security
	 * 
	 * @return The Dealer Subordinate Funding Primary Security
	 */

	public org.drip.exposure.evolver.PrimarySecurity dealerSubordinateFunding()
	{
		return primarySecurity (_dealerSubordinateFundingID);
	}

	/**
	 * Retrieve the Client Funding Primary Security
	 * 
	 * @return The Client Funding Primary Security
	 */

	public org.drip.exposure.evolver.PrimarySecurity clientFunding()
	{
		return primarySecurity (_clientFundingID);
	}
}
