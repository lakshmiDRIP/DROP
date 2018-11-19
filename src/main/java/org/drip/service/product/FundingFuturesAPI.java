
package org.drip.service.product;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>FundingFuturesAPI</i> contains the Functionality associated with the Horizon Analysis of the Funding
 * Futures.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service">Service</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/product">Product</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FundingFuturesAPI {

	/**
	 * Generate the Funding Futures Horizon Metrics
	 * 
	 * @param dtPrevious Previous Date
	 * @param dtSpot Spot Date
	 * @param dtExpiry Expiry Date
	 * @param dblPreviousQuote Previous Funding Futures Rates
	 * @param dblSpotQuote Spot Funding Futures Rates
	 * @param strCurrency Funding Currency
	 * 
	 * @return The Funding Futures Horizon Metrics
	 */

	public static final org.drip.historical.attribution.PositionChangeComponents HorizonMetrics (
		final org.drip.analytics.date.JulianDate dtPrevious,
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.analytics.date.JulianDate dtExpiry,
		final double dblPreviousQuote,
		final double dblSpotQuote,
		final java.lang.String strCurrency)
	{
		org.drip.product.rates.SingleStreamComponent sscFutures =
			org.drip.service.template.ExchangeInstrumentBuilder.ForwardRateFutures (dtExpiry.addMonths (3),
				strCurrency);

		if (null == sscFutures) return null;

		org.drip.analytics.cashflow.ComposableUnitPeriod cup = sscFutures.couponPeriods().get
			(0).periods().get (0);

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		if (!csqc.setFundingState
			(org.drip.state.creator.ScenarioDiscountCurveBuilder.DiscretelyCompoundedFlatRate (dtPrevious,
				strCurrency, dblPreviousQuote, cup.couponDC(), cup.freq())))
			return null;

		java.util.Map<java.lang.String, java.lang.Double> mapPreviousMeasures = sscFutures.value
			(org.drip.param.valuation.ValuationParams.Spot (dtPrevious.julian()), null, csqc, null);

		if (null == mapPreviousMeasures || !mapPreviousMeasures.containsKey ("DV01") ||
			!mapPreviousMeasures.containsKey ("ForwardRate") || !mapPreviousMeasures.containsKey ("PV"))
			return null;

		double dblPreviousDV01 = 10000. * mapPreviousMeasures.get ("DV01");

		double dblPreviousForwardRate = mapPreviousMeasures.get ("ForwardRate");

		if (!csqc.setFundingState
			(org.drip.state.creator.ScenarioDiscountCurveBuilder.DiscretelyCompoundedFlatRate (dtSpot,
				strCurrency, dblSpotQuote, cup.couponDC(), cup.freq())))
			return null;

		java.util.Map<java.lang.String, java.lang.Double> mapSpotMeasures = sscFutures.value
			(org.drip.param.valuation.ValuationParams.Spot (dtSpot.julian()), null, csqc, null);

		if (null == mapSpotMeasures || !mapSpotMeasures.containsKey ("DV01") || !mapSpotMeasures.containsKey
			("ForwardRate") || !mapSpotMeasures.containsKey ("PV"))
			return null;

		double dblSpotDV01 = 10000. * mapSpotMeasures.get ("DV01");

		double dblSpotForwardRate = mapSpotMeasures.get ("ForwardRate");

		try {
			org.drip.historical.attribution.PositionMarketSnap pmsPrevious = new
				org.drip.historical.attribution.PositionMarketSnap (dtPrevious, mapPreviousMeasures.get
					("PV"));

			if (!pmsPrevious.addManifestMeasureSnap ("ForwardRate", dblPreviousForwardRate, dblPreviousDV01,
				dblPreviousForwardRate) || !pmsPrevious.setR1 ("DV01", dblPreviousDV01) || !pmsPrevious.setR1
					("ForwardRate", dblPreviousForwardRate) || !pmsPrevious.setC1 ("FloaterLabel",
						sscFutures.forwardLabel().get ("DERIVED").fullyQualifiedName()))
				return null;

			org.drip.historical.attribution.PositionMarketSnap pmsSpot = new
				org.drip.historical.attribution.PositionMarketSnap (dtSpot, mapSpotMeasures.get ("PV"));

			if (!pmsSpot.addManifestMeasureSnap ("ForwardRate", dblSpotForwardRate, dblSpotDV01,
				dblSpotForwardRate) || !pmsSpot.setR1 ("DV01", dblSpotDV01) || !pmsSpot.setR1 ("ForwardRate",
					dblSpotForwardRate) || !pmsSpot.setC1 ("FloaterLabel", sscFutures.forwardLabel().get
						("DERIVED").fullyQualifiedName()))
				return null;

			return new org.drip.historical.attribution.PositionChangeComponents (false, pmsPrevious, pmsSpot,
				0., null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Funding Futures Horizon Metrics
	 * 
	 * @param adt Array of Closing Dates
	 * @param adtExpiry Array of Expiry Dates
	 * @param adblFuturesQuote Array of Closing Futures Quotes
	 * @param strCurrency Funding Currency
	 * 
	 * @return The Funding Futures Horizon Metrics
	 */

	public static final java.util.List<org.drip.historical.attribution.PositionChangeComponents>
		HorizonChangeAttribution (
			final org.drip.analytics.date.JulianDate[] adt,
			final org.drip.analytics.date.JulianDate[] adtExpiry,
			final double[] adblFuturesQuote,
			final java.lang.String strCurrency)
	{
		if (null == adt || null == adtExpiry || null == adblFuturesQuote) return null;

		int iNumClose = adt.length;

		if (0 == iNumClose || iNumClose != adtExpiry.length || iNumClose != adblFuturesQuote.length)
			return null;

		java.util.List<org.drip.historical.attribution.PositionChangeComponents> lsPCC = new
			java.util.ArrayList<org.drip.historical.attribution.PositionChangeComponents>();

		for (int i = 1; i < iNumClose; ++i) {
			if (adtExpiry[i - 1].julian() != adtExpiry[i].julian()) continue;

			org.drip.historical.attribution.PositionChangeComponents pcc = HorizonMetrics (adt[i - 1],
				adt[i], adtExpiry[i], adblFuturesQuote[i - 1], adblFuturesQuote[i], strCurrency);

			if (null == pcc) continue;

			lsPCC.add (pcc);
		}

		return lsPCC;
	}
}
