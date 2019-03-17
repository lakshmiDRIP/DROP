
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
 * <i>BalanceSheetVertex</i> implements the Balance Sheet Vertex Component of the Streamlined Accounting
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

public class BalanceSheetVertex
{
	private double _cash = java.lang.Double.NaN;
	private double _asset = java.lang.Double.NaN;
	private double _liability = java.lang.Double.NaN;
	private double _contraAsset = java.lang.Double.NaN;
	private double _contraLiability = java.lang.Double.NaN;
	private double _retainedEarnings = java.lang.Double.NaN;

	/**
	 * Unrealized Instance of BalanceSheetVertex
	 * 
	 * @param asset The Asset Account
	 * @param liability The Liability Account
	 * @param contraAsset The Contra Asset Account
	 * @param contraLiability The Contra Liability Account
	 * @param retainedEarnings The Retained Earnings Account
	 * 
	 * @return The Unrealized BalanceSheetVertex Instance
	 */

	public static final BalanceSheetVertex Unrealized (
		final double asset,
		final double liability,
		final double contraAsset,
		final double contraLiability,
		final double retainedEarnings)
	{
		try
		{
			return new BalanceSheetVertex (
				asset,
				liability,
				contraAsset,
				contraLiability,
				retainedEarnings,
				0.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BalanceSheetVertex Constructor
	 * 
	 * @param asset The Asset Account
	 * @param liability The Liability Account
	 * @param contraAsset The Contra Asset Account
	 * @param contraLiability The Contra Liability Account
	 * @param retainedEarnings The Retained Earnings Account
	 * @param cash The Cash Account
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BalanceSheetVertex (
		final double asset,
		final double liability,
		final double contraAsset,
		final double contraLiability,
		final double retainedEarnings,
		final double cash)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_cash = cash) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_asset = asset) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_liability = liability) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_contraAsset = contraAsset) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_contraLiability = contraLiability) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_retainedEarnings = retainedEarnings))
		{
			throw new java.lang.Exception ("BalanceSheetVertex Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Asset Account
	 * 
	 * @return The Asset Account
	 */

	public double asset()
	{
		return _asset;
	}

	/**
	 * Retrieve the Liability Account
	 * 
	 * @return The Liability Account
	 */

	public double liability()
	{
		return _liability;
	}

	/**
	 * Retrieve the Contra Asset Account
	 * 
	 * @return The Contra Asset Account
	 */

	public double contraAsset()
	{
		return _contraAsset;
	}

	/**
	 * Retrieve the Contra Liability Account
	 * 
	 * @return The Contra Liability Account
	 */

	public double contraLiability()
	{
		return _contraLiability;
	}

	/**
	 * Retrieve the Retained Earnings Account
	 * 
	 * @return The Retained Earnings Account
	 */

	public double retainedEarnings()
	{
		return _retainedEarnings;
	}

	/**
	 * Retrieve the Cash Account
	 * 
	 * @return The Cash Account
	 */

	public double cash()
	{
		return _cash;
	}

	/**
	 * Estimate the Portfolio Value (PFV)
	 * 
	 * @return The Portfolio Value (PFV)
	 */

	public double pfv()
	{
		return _asset - _liability;
	}

	/**
	 * Estimate the Equity Account
	 * 
	 * @return The Equity Account
	 */

	public double equity()
	{
		return _cash + _asset - _liability - _contraAsset + _contraLiability + _retainedEarnings;
	}

	/**
	 * Estimate the Core Equity Tier I (CET1) Capital
	 * 
	 * @return The Core Equity Tier I (CET1) Capital
	 */

	public double cet1()
	{
		return _cash + _asset - _liability - _contraAsset + _retainedEarnings;
	}
}
