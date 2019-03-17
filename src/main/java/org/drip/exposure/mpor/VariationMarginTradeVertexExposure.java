
package org.drip.exposure.mpor;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>VariationMarginTradeVertexExposure</i> holds the Variation Margin, Trade Payments, and Exposures for a
 * specific Forward Vertex Date. The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies <i>Risk</i> <b>23
 *  				(12)</b> 82-87
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-
 *  				party Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/mpor/README.md">MPoR</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class VariationMarginTradeVertexExposure
{
	private double _clientTradePaymentGap = java.lang.Double.NaN;
	private double _variationMarginPosting = java.lang.Double.NaN;
	private double _variationMarginEstimate = java.lang.Double.NaN;
	private double _clientDealerTradePaymentGap = java.lang.Double.NaN;
	private org.drip.exposure.csatimeline.LastFlowDates _lastFlowDates = null;

	/**
	 * VariationMarginTradeVertexExposure Constructor
	 * 
	 * @param variationMarginEstimate The Calculation Agent Generated Variation Margin Estimate
	 * @param variationMarginPosting The Actual Variation Margin Posted from Collateral Rules and Operational
	 * 		Delays
	 * @param clientTradePaymentGap The Client Trade Payment Gap
	 * @param clientDealerTradePaymentGap The Client-to-Dealer Net Trade Payment Gap
	 * @param lastFlowDates The Last Flow Dates
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public VariationMarginTradeVertexExposure (
		final double variationMarginEstimate,
		final double variationMarginPosting,
		final double clientTradePaymentGap,
		final double clientDealerTradePaymentGap,
		final org.drip.exposure.csatimeline.LastFlowDates lastFlowDates)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_variationMarginEstimate = variationMarginEstimate) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_variationMarginPosting = variationMarginPosting) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_clientTradePaymentGap = clientTradePaymentGap) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_clientDealerTradePaymentGap =
				clientDealerTradePaymentGap) ||
			null == (_lastFlowDates = lastFlowDates))
		{
			throw new java.lang.Exception
				("VariationMarginTradeVertexExposure Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Calculation Agent Generated Variation Margin Estimate
	 * 
	 * @return The Calculation Agent Generated Variation Margin Estimate
	 */

	public double variationMarginEstimate()
	{
		return _variationMarginEstimate;
	}

	/**
	 * Retrieve the Actual Variation Margin Posted from Collateral Rules and Operational Delays
	 * 
	 * @return The Actual Variation Margin Posted from Collateral Rules and Operational Delays
	 */

	public double variationMarginPosting()
	{
		return _variationMarginPosting;
	}

	/**
	 * Retrieve the Variation Margin Gap
	 * 
	 * @return The Variation Margin Gap
	 */

	public double variationMarginGap()
	{
		return _variationMarginEstimate - _variationMarginPosting;
	}

	/**
	 * Retrieve the Client Trade Payment Gap
	 * 
	 * @return The Client Trade Payment Gap
	 */

	public double clientTradePaymentGap()
	{
		return _clientTradePaymentGap;
	}

	/**
	 * Retrieve the Client-to-Dealer Net Trade Payment Gap
	 * 
	 * @return The Client-to-Dealer Net Trade Payment Gap
	 */

	public double clientDealerTradePaymentGap()
	{
		return _clientDealerTradePaymentGap;
	}

	/**
	 * Retrieve the Trade Payment Gap
	 * 
	 * @return The Trade Payment Gap
	 */

	public double tradePaymentGap()
	{
		return _clientTradePaymentGap + _clientDealerTradePaymentGap;
	}

	/**
	 * Retrieve the Collateralized Exposure
	 * 
	 * @return The Collateralized Exposure
	 */

	public double collateralizedExposure()
	{
		return _variationMarginEstimate + _clientTradePaymentGap + _clientDealerTradePaymentGap -
			_variationMarginPosting;
	}

	/**
	 * Retrieve the Collateralized Positive Exposure
	 * 
	 * @return The Collateralized Positive Exposure
	 */

	public double collateralizedPositiveExposure()
	{
		double collateralizedExposure = collateralizedExposure();

		return collateralizedExposure > 0. ? collateralizedExposure : 0.;
	}

	/**
	 * Retrieve the Last Flow Dates
	 * 
	 * @return The Last Flow Dates
	 */

	public org.drip.exposure.csatimeline.LastFlowDates lastFlowDates()
	{
		return _lastFlowDates;
	}
}
