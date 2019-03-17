
package org.drip.xva.basel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>OTCAccountingPolicy</i> implements the Generic Basel Accounting Policy using the Streamlined Accounting
 * Framework for OTC Derivatives, as described in Albanese and Andersen (2014). The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  			Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955
 *  			<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		BCBS (2012): <i>Consultative Document: Application of Own Credit Risk Adjustments to
 *  			Derivatives</i> <b>Basel Committee on Banking Supervision</b>
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/basel">Basel</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class OTCAccountingPolicy
{
	private double _cet1Change = java.lang.Double.NaN;
	private double _portfolioValueChange = java.lang.Double.NaN;
	private double _contraLiabilityChange = java.lang.Double.NaN;
	private double _fundingTransferPricing = java.lang.Double.NaN;

	/**
	 * OTCAccountingPolicy Constructor
	 * 
	 * @param fundingTransferPricing The Funding Transfer Pricing
	 * @param cet1Change The CET1 Change
	 * @param contraLiabilityChange The Contra-Liability Change
	 * @param portfolioValueChange The Portfolio Value Change
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public OTCAccountingPolicy (
		final double fundingTransferPricing,
		final double cet1Change,
		final double contraLiabilityChange,
		final double portfolioValueChange)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_fundingTransferPricing = fundingTransferPricing) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_cet1Change = cet1Change) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_contraLiabilityChange = contraLiabilityChange) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_portfolioValueChange = portfolioValueChange))
		{
			throw new java.lang.Exception ("OTCAccountingPolicy Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Funding Transfer Pricing
	 * 
	 * @return The Funding Transfer Pricing
	 */

	public double fundingTransferPricing()
	{
		return _fundingTransferPricing;
	}

	/**
	 * Retrieve the CET1 Change
	 * 
	 * @return The CET1 Change
	 */

	public double cet1Change()
	{
		return _cet1Change;
	}

	/**
	 * Retrieve the Contra-Liability Change
	 * 
	 * @return The Contra-Liability Change
	 */

	public double contraLiabilityChange()
	{
		return _contraLiabilityChange;
	}

	/**
	 * Retrieve the Portfolio Value Change
	 * 
	 * @return The Portfolio Value Change
	 */

	public double portfolioValueChange()
	{
		return _portfolioValueChange;
	}
}
