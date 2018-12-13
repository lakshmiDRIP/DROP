
package org.drip.exposure.csatimeline;

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
 * <i>LastFlowDates</i> holds the Last Client/Dealer Margin Flow and Trade Flow Dates using the
 * Parameterization laid out in Andersen, Pykhtin, and Sokol (2017). The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of
 *  				Initial Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Anfuso, F., D. Aziz, P. Giltinan, and K Loukopoulus (2017): A Sound Modeling and Back-testing
 *  				Framework for Forecasting Initial Margin Requirements
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			BCBS (2015): Margin Requirements for Non-centrally Cleared Derivatives
 *  				https://www.bis.org/bcbs/publ/d317.pdf
 *  		</li>
 *  		<li>
 *  			Pykhtin, M. (2009): Modeling Credit Exposure for Collateralized Counter-parties <i>Journal of
 *  				Credit Risk</i> <b>5 (4)</b> 3-27
 *  		</li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/csatimeline/README.md">CSA Time Line</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LastFlowDates
{
	private org.drip.analytics.date.JulianDate _spot = null;
	private org.drip.analytics.date.JulianDate _valuation = null;
	private org.drip.analytics.date.JulianDate _clientTradePayment = null;
	private org.drip.analytics.date.JulianDate _dealerTradePayment = null;
	private org.drip.analytics.date.JulianDate _variationMarginPeriodEnd = null;
	private org.drip.analytics.date.JulianDate _variationMarginPeriodStart = null;
	private org.drip.analytics.date.JulianDate _clientVariationMarginPosting = null;
	private org.drip.analytics.date.JulianDate _dealerVariationMarginPosting = null;

	/**
	 * Generate a LastFlowDates Instance from the Spot Date and the AndersenPykhtinSokolLag
	 * 
	 * @param spot The Spot Date
	 * @param andersenPykhtinSokolLag AndersenPykhtinSokolLag Instance
	 * @param calendarSet The Business Day Calendar Set
	 * 
	 * @return The LastFlowDates Instance
	 */

	public static final LastFlowDates SpotStandard (
		final org.drip.analytics.date.JulianDate spot,
		final org.drip.exposure.csatimeline.AndersenPykhtinSokolLag andersenPykhtinSokolLag,
		final java.lang.String calendarSet)
	{
		if (null == spot || null == andersenPykhtinSokolLag)
		{
			return null;
		}

		org.drip.analytics.date.JulianDate clientVariationMarginPostingDate = spot.subtractBusDays (
			andersenPykhtinSokolLag.clientVariationMarginPostingDelay(),
			calendarSet
		);

		org.drip.analytics.date.JulianDate dealerVariationMarginPostingDate = spot.subtractBusDays (
			andersenPykhtinSokolLag.dealerVariationMarginPostingDelay(),
			calendarSet
		);

		if (null == clientVariationMarginPostingDate || null == dealerVariationMarginPostingDate)
		{
			return null;
		}

		try
		{
			return new LastFlowDates (
				clientVariationMarginPostingDate.subtractBusDays (
					1,
					calendarSet
				),
				clientVariationMarginPostingDate,
				dealerVariationMarginPostingDate,
				spot.subtractBusDays (
					andersenPykhtinSokolLag.clientTradePaymentDelay(),
					calendarSet
				),
				spot.subtractBusDays (
					andersenPykhtinSokolLag.dealerTradePaymentDelay(),
					calendarSet
				),
				spot,
				clientVariationMarginPostingDate.julian() < dealerVariationMarginPostingDate.julian() ?
					clientVariationMarginPostingDate : dealerVariationMarginPostingDate,
				spot
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * LastFlowDates Constructor
	 * 
	 * @param valuation The Margin Collateral Valuation Date
	 * @param clientVariationMarginPosting The Last Client Variation Margin Posting (Observation) Date
	 * @param dealerVariationMarginPosting The Last Dealer Variation Margin Posting (Observation) Date
	 * @param clientTradePayment The Last Client Trade Payment (Settlement) Date
	 * @param dealerTradePayment The Last Dealer Trade Payment (Settlement) Date
	 * @param spot The Spot Date
	 * @param variationMarginPeriodStart The Variation Margin Period Start Date
	 * @param variationMarginPeriodEnd The Variation Margin Period End Date
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LastFlowDates (
		final org.drip.analytics.date.JulianDate valuation,
		final org.drip.analytics.date.JulianDate clientVariationMarginPosting,
		final org.drip.analytics.date.JulianDate dealerVariationMarginPosting,
		final org.drip.analytics.date.JulianDate clientTradePayment,
		final org.drip.analytics.date.JulianDate dealerTradePayment,
		final org.drip.analytics.date.JulianDate spot,
		final org.drip.analytics.date.JulianDate variationMarginPeriodStart,
		final org.drip.analytics.date.JulianDate variationMarginPeriodEnd)
		throws java.lang.Exception
	{
		if (null == (_valuation = valuation) ||
			null == (_clientVariationMarginPosting = clientVariationMarginPosting) ||
			null == (_dealerVariationMarginPosting = dealerVariationMarginPosting) ||
			null == (_clientTradePayment = clientTradePayment) ||
			null == (_dealerTradePayment = dealerTradePayment) ||
			null == (_spot = spot) ||
			null == (_variationMarginPeriodStart = variationMarginPeriodStart) ||
			null == (_variationMarginPeriodEnd = variationMarginPeriodEnd))
		{
			throw new java.lang.Exception ("LastFlowDates Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Valuation Date
	 * 
	 * @return The Valuation Date
	 */

	public org.drip.analytics.date.JulianDate valuation()
	{
		return _valuation;
	}

	/**
	 * Retrieve the Last Client Variation Margin Posting (Observation) Date
	 * 
	 * @return The Last Client Variation Margin Posting (Observation) Date
	 */

	public org.drip.analytics.date.JulianDate clientVariationMarginPosting()
	{
		return _clientVariationMarginPosting;
	}

	/**
	 * Retrieve the Last Dealer Variation Margin Posting (Observation) Date
	 * 
	 * @return The Last Dealer Variation Margin Posting (Observation) Date
	 */

	public org.drip.analytics.date.JulianDate dealerVariationMarginPosting()
	{
		return _dealerVariationMarginPosting;
	}

	/**
	 * Retrieve the Last Client Trade Payment (Settlement) Date
	 * 
	 * @return The Last Client Trade Payment (Settlement) Date
	 */

	public org.drip.analytics.date.JulianDate clientTradePayment()
	{
		return _clientTradePayment;
	}

	/**
	 * Retrieve the Last Dealer Trade Payment (Settlement) Date
	 * 
	 * @return The Last Dealer Trade Payment (Settlement) Date
	 */

	public org.drip.analytics.date.JulianDate dealerTradePayment()
	{
		return _dealerTradePayment;
	}

	/**
	 * Retrieve the Spot Date
	 * 
	 * @return The Spot Date
	 */

	public org.drip.analytics.date.JulianDate spot()
	{
		return _spot;
	}

	/**
	 * Retrieve the ETD
	 * 
	 * @return The ETD
	 */

	public org.drip.analytics.date.JulianDate etd()
	{
		return _spot;
	}

	/**
	 * Retrieve the Variation Margin Period Start Date
	 * 
	 * @return The Variation Margin Period Start Date
	 */

	public org.drip.analytics.date.JulianDate variationMarginPeriodStart()
	{
		return _variationMarginPeriodStart;
	}

	/**
	 * Retrieve the Variation Margin Period End Date
	 * 
	 * @return The Variation Margin Period End Date
	 */

	public org.drip.analytics.date.JulianDate variationMarginPeriodEnd()
	{
		return _variationMarginPeriodEnd;
	}
}
