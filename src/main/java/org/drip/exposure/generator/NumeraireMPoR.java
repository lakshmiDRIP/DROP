
package org.drip.exposure.generator;

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
 * <i>NumeraireMPoR</i> estimates the MPoR Variation Margin and the Trade Payments for the generic Numeraire
 * off of the Realized Market Path. The References are:
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
 *  			Pykhtin, M. (2009): Modeling Counter-party Credit Exposure in the Presence of Margin
 *  				Agreements http://www.risk-europe.com/protected/michael-pykhtin.pdf
 *  		</li>
 *  		<li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/generator/README.md">Generator</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class NumeraireMPoR implements org.drip.exposure.mpor.VariationMarginTradePaymentVertex
{
	private double _notional = java.lang.Double.NaN;
	private org.drip.state.identifier.LatentStateLabel _latentStateLabel = null;

	/**
	 * NumeraireMPoR Constructor
	 * 
	 * @param latentStateLabel The Latent State Label
	 * @param notional The Notional
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NumeraireMPoR (
		final org.drip.state.identifier.LatentStateLabel latentStateLabel,
		final double notional)
		throws java.lang.Exception
	{
		if (null == (_latentStateLabel = latentStateLabel) ||
			!org.drip.quant.common.NumberUtil.IsValid (_notional = notional))
		{
			throw new java.lang.Exception ("NumeraireMPoR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Notional
	 * 
	 * @return The Notional
	 */

	public double notional()
	{
		return _notional;
	}

	/**
	 * Retrieve the Latent State Label
	 * 
	 * @return The Latent State Label
	 */

	public org.drip.state.identifier.LatentStateLabel latentStateLabel()
	{
		return _latentStateLabel;
	}

	@Override public double variationMarginEstimate (
		final int forwardDate,
		final org.drip.exposure.universe.MarketPath marketPath)
		throws java.lang.Exception
	{
		if (null == marketPath)
		{
			throw new java.lang.Exception ("NumeraireVertex::variationMarginEstimate => Invalid Inputs");
		}

		return _notional * marketPath.marketVertex (forwardDate).latentStateValue (_latentStateLabel);
	}

	@Override public org.drip.exposure.mpor.TradePayment tradePayment (
		final int forwardDate,
		final org.drip.exposure.universe.MarketPath marketPath)
	{
		return org.drip.exposure.mpor.TradePayment.Standard (0.);
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
		org.drip.exposure.mpor.TradePayment[] denseTradePaymentArray = new
			org.drip.exposure.mpor.TradePayment[denseDateCount];

		org.drip.exposure.mpor.TradePayment zeroTradePayment = org.drip.exposure.mpor.TradePayment.Standard
			(0.);

		for (int denseDateIndex = 0; denseDateIndex < denseDateCount; ++denseDateIndex)
		{
			denseTradePaymentArray[denseDateIndex] = zeroTradePayment;
		}

		return denseTradePaymentArray;
	}
}
