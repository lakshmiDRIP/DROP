
package org.drip.exposure.csatimeline;

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
 * <i>AndersenPykhtinSokolLag</i> holds the Client/Dealer Margin Flow and Trade Flow Lags using the
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure">Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/csatimeline">CSA Time Line</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AndersenPykhtinSokolLag
{
	private int _clientTradePaymentDelay = -1;
	private int _dealerTradePaymentDelay = -1;
	private int _clientVariationMarginPostingDelay = -1;
	private int _dealerVariationMarginPostingDelay = -1;

	/**
	 * Generate the "Conservative" Parameterization of AndersenPykhtinSokolLag
	 * 
	 * @return The "Conservative" Parameterization of AndersenPykhtinSokolLag
	 */

	public static final AndersenPykhtinSokolLag Conservative()
	{
		try
		{
			return new AndersenPykhtinSokolLag (
				15,
				9,
				8,
				3
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the "Aggressive" Parameterization of AndersenPykhtinSokolLag
	 * 
	 * @return The "Aggressive" Parameterization of AndersenPykhtinSokolLag
	 */

	public static final AndersenPykhtinSokolLag Aggressive()
	{
		try
		{
			return new AndersenPykhtinSokolLag (
				7,
				6,
				4,
				4
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the "Classical+" Parameterization of AndersenPykhtinSokolLag
	 * 
	 * @return The "Classical+" Parameterization of AndersenPykhtinSokolLag
	 */

	public static final AndersenPykhtinSokolLag ClassicalPlus()
	{
		try
		{
			return new AndersenPykhtinSokolLag (
				10,
				10,
				0,
				0
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the "Classical-" Parameterization of AndersenPykhtinSokolLag
	 * 
	 * @return The "Classical-" Parameterization of AndersenPykhtinSokolLag
	 */

	public static final AndersenPykhtinSokolLag ClassicalMinus()
	{
		try
		{
			return new AndersenPykhtinSokolLag (
				10,
				10,
				10,
				10
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * AndersenPykhtinSokolLag Constructor
	 * 
	 * @param clientVariationMarginPostingDelay Client Variation Margin Posting Delay (Business Days)
	 * @param dealerVariationMarginPostingDelay Dealer Variation Margin Posting Gap (Business Days)
	 * @param clientTradePaymentDelay Client Trade Payment Delay (Business Days)
	 * @param dealerTradePaymentDelay Dealer Trade Payment Delay (Business Days)
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AndersenPykhtinSokolLag (
		final int clientVariationMarginPostingDelay,
		final int dealerVariationMarginPostingDelay,
		final int clientTradePaymentDelay,
		final int dealerTradePaymentDelay)
		throws java.lang.Exception
	{
		if (0 > (_clientVariationMarginPostingDelay = clientVariationMarginPostingDelay) ||
			0 > (_dealerVariationMarginPostingDelay = dealerVariationMarginPostingDelay) ||
			0 > (_clientTradePaymentDelay = clientTradePaymentDelay) ||
			0 > (_dealerTradePaymentDelay = dealerTradePaymentDelay) ||
			_clientVariationMarginPostingDelay < _dealerVariationMarginPostingDelay ||
			_dealerVariationMarginPostingDelay < _clientTradePaymentDelay ||
			_clientTradePaymentDelay < _dealerTradePaymentDelay)
		{
			throw new java.lang.Exception ("AndersenPykhtinSokolLag Constuctor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Client Variation Margin Posting Delay
	 * 
	 * @return The Client Variation Margin Posting Delay
	 */

	public int clientVariationMarginPostingDelay()
	{
		return _clientVariationMarginPostingDelay;
	}

	/**
	 * Retrieve the Dealer Variation Margin Posting Delay
	 * 
	 * @return The Dealer Variation Margin Posting Delay
	 */

	public int dealerVariationMarginPostingDelay()
	{
		return _dealerVariationMarginPostingDelay;
	}

	/**
	 * Retrieve the Client Trade Payment Delay
	 * 
	 * @return The Client Trade Payment Delay
	 */

	public int clientTradePaymentDelay()
	{
		return _clientTradePaymentDelay;
	}

	/**
	 * Retrieve the Dealer Trade Payment Delay
	 * 
	 * @return The Dealer Trade Payment Delay
	 */

	public int dealerTradePaymentDelay()
	{
		return _dealerTradePaymentDelay;
	}
}
