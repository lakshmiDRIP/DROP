
package org.drip.exposure.generator;

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
 * <i>PortfolioMPoR</i> estimates the MPoR Variation Margin and the Trade Payments for the Component MPoR's
 * of a given Portfolio off of the Realized Market Path. The References are:
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
 *  			Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and
 *  				the Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955
 *  				<b>eSSRN</b>
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure">Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/generator">Generator</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PortfolioMPoR implements org.drip.exposure.mpor.VariationMarginTradePaymentVertex
{
	private java.util.List<org.drip.exposure.mpor.VariationMarginTradePaymentVertex> _componentMPoRList = new
		java.util.ArrayList<org.drip.exposure.mpor.VariationMarginTradePaymentVertex>();

	/**
	 * Retrieve the List of Component MPoR's
	 * 
	 * @return The List of Component MPoR's
	 */

	public java.util.List<org.drip.exposure.mpor.VariationMarginTradePaymentVertex> componentMPoRList()
	{
		return _componentMPoRList;
	}

	@Override public double variationMarginEstimate (
		final int forwardDate,
		final org.drip.exposure.universe.MarketPath marketPath)
		throws java.lang.Exception
	{
		double variationMarginEstimate = 0.;

		for (org.drip.exposure.mpor.VariationMarginTradePaymentVertex componentMPoR : _componentMPoRList)
		{
			variationMarginEstimate += componentMPoR.variationMarginEstimate (
				forwardDate,
				marketPath
			);
		}

		return variationMarginEstimate;
	}

	@Override public org.drip.exposure.mpor.TradePayment tradePayment (
		final int forwardDate,
		final org.drip.exposure.universe.MarketPath marketPath)
	{
		double clientPayment = 0.;
		double dealerPayment = 0.;

		for (org.drip.exposure.mpor.VariationMarginTradePaymentVertex componentMPoR : _componentMPoRList)
		{
			org.drip.exposure.mpor.TradePayment tradePayment = componentMPoR.tradePayment (
				forwardDate,
				marketPath
			);

			if (null == tradePayment)
			{
				return null;
			}

			dealerPayment += tradePayment.dealer();

			clientPayment += tradePayment.client();
		}

		try
		{
			return new org.drip.exposure.mpor.TradePayment (
				dealerPayment,
				clientPayment
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.exposure.mpor.TradePayment[] denseTradePaymentArray (
		final int startDate,
		final int endDate,
		final org.drip.exposure.universe.MarketPath marketPath)
	{
		if (endDate < startDate)
		{
			return null;
		}

		int denseDateCount = endDate - startDate + 1;
		double[] clientTradePaymentArray = new double[denseDateCount];
		double[] dealerTradePaymentArray = new double[denseDateCount];
		org.drip.exposure.mpor.TradePayment[] denseTradePaymentArray = new
			org.drip.exposure.mpor.TradePayment[denseDateCount];

		for (int denseDateIndex = 0; denseDateIndex < denseDateCount; ++denseDateIndex)
		{
			clientTradePaymentArray[denseDateIndex] = 0.;
			dealerTradePaymentArray[denseDateIndex] = 0.;
		}

		for (org.drip.exposure.mpor.VariationMarginTradePaymentVertex componentMPoR : _componentMPoRList)
		{
			org.drip.exposure.mpor.TradePayment[] componentDenseTradePaymentArray =
				componentMPoR.denseTradePaymentArray (
					startDate,
					endDate,
					marketPath
				);

			if (null == componentDenseTradePaymentArray)
			{
				return null;
			}

			for (int denseDateIndex = 0; denseDateIndex < denseDateCount; ++denseDateIndex)
			{
				clientTradePaymentArray[denseDateIndex] +=
					componentDenseTradePaymentArray[denseDateIndex].client();

				dealerTradePaymentArray[denseDateIndex] +=
					componentDenseTradePaymentArray[denseDateIndex].dealer();
			}
		}

		for (int denseDateIndex = 0; denseDateIndex < denseDateCount; ++denseDateIndex)
		{
			try
			{
				denseTradePaymentArray[denseDateIndex] = new org.drip.exposure.mpor.TradePayment (
					dealerTradePaymentArray[denseDateIndex],
					clientTradePaymentArray[denseDateIndex]
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return denseTradePaymentArray;
	}
}
