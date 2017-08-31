
package org.drip.service.product;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * FundingFuturesAPI contains the Functionality associated with the Horizon Analysis of the Funding Futures.
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
