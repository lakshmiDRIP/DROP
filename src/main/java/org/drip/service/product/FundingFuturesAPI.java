
package org.drip.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.drip.analytics.cashflow.ComposableUnitPeriod;
import org.drip.analytics.date.JulianDate;
import org.drip.historical.attribution.PositionChangeComponents;
import org.drip.historical.attribution.PositionMarketSnap;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.rates.SingleStreamComponent;
import org.drip.service.template.ExchangeInstrumentBuilder;
import org.drip.state.creator.ScenarioDiscountCurveBuilder;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>FundingFuturesAPI</i> contains the Functionality associated with the Horizon Analysis of the Funding
 * 	Futures. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Generate the Funding Futures Horizon Metrics #1</li>
 * 		<li>Generate the Funding Futures Horizon Metrics #2</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/product/README.md">Product Horizon PnL Attribution Decomposition</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FundingFuturesAPI
{

	/**
	 * Generate the Funding Futures Horizon Metrics
	 * 
	 * @param previousDate Previous Date
	 * @param spotDate Spot Date
	 * @param expiryDate Expiry Date
	 * @param previousQuote Previous Funding Futures Rates
	 * @param spotQuote Spot Funding Futures Rates
	 * @param currency Funding Currency
	 * 
	 * @return The Funding Futures Horizon Metrics
	 */

	public static final PositionChangeComponents HorizonMetrics (
		final JulianDate previousDate,
		final JulianDate spotDate,
		final JulianDate expiryDate,
		final double previousQuote,
		final double spotQuote,
		final String currency)
	{
		SingleStreamComponent futuresSingleStreamComponent = ExchangeInstrumentBuilder.ForwardRateFutures (
			expiryDate.addMonths (3),
			currency
		);

		if (null == futuresSingleStreamComponent) {
			return null;
		}

		ComposableUnitPeriod composableUnitPeriod =
			futuresSingleStreamComponent.couponPeriods().get (0).periods().get (0);

		CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

		if (!curveSurfaceQuoteContainer.setFundingState (
			ScenarioDiscountCurveBuilder.DiscretelyCompoundedFlatRate (
				previousDate,
				currency,
				previousQuote,
				composableUnitPeriod.couponDC(),
				composableUnitPeriod.freq()
			)
		))
		{
			return null;
		}

		Map<String, Double> previousMeasuresMap = futuresSingleStreamComponent.value (
			ValuationParams.Spot (previousDate.julian()),
			null,
			curveSurfaceQuoteContainer,
			null
		);

		if (null == previousMeasuresMap ||
			!previousMeasuresMap.containsKey ("DV01") ||
			!previousMeasuresMap.containsKey ("ForwardRate") ||
			!previousMeasuresMap.containsKey ("PV"))
		{
			return null;
		}

		double previousDV01 = 10000. * previousMeasuresMap.get ("DV01");

		double previousForwardRate = previousMeasuresMap.get ("ForwardRate");

		if (!curveSurfaceQuoteContainer.setFundingState (
			ScenarioDiscountCurveBuilder.DiscretelyCompoundedFlatRate (
				spotDate,
				currency,
				spotQuote,
				composableUnitPeriod.couponDC(),
				composableUnitPeriod.freq()
			)
		))
		{
			return null;
		}

		Map<String, Double> spotMeasuresMap = futuresSingleStreamComponent.value (
			ValuationParams.Spot (spotDate.julian()),
			null,
			curveSurfaceQuoteContainer,
			null
		);

		if (null == spotMeasuresMap ||
			!spotMeasuresMap.containsKey ("DV01") ||
			!spotMeasuresMap.containsKey ("ForwardRate") ||
			!spotMeasuresMap.containsKey ("PV"))
		{
			return null;
		}

		double spotDV01 = 10000. * spotMeasuresMap.get ("DV01");

		double spotForwardRate = spotMeasuresMap.get ("ForwardRate");

		try {
			PositionMarketSnap previousPositionMarketSnap = new PositionMarketSnap (
				previousDate,
				previousMeasuresMap.get ("PV")
			);

			if (!previousPositionMarketSnap.addManifestMeasureSnap (
					"ForwardRate",
					previousForwardRate, previousDV01,
					previousForwardRate
				) || !previousPositionMarketSnap.setR1 (
					"DV01",
					previousDV01
				) || !previousPositionMarketSnap.setR1 (
					"ForwardRate",
					previousForwardRate
				) || !previousPositionMarketSnap.setC1 (
					"FloaterLabel",
					futuresSingleStreamComponent.forwardLabel().get ("DERIVED").fullyQualifiedName()
				)
			)
			{
				return null;
			}

			PositionMarketSnap spotPositionMarketSnap = new PositionMarketSnap (
				spotDate,
				spotMeasuresMap.get ("PV")
			);

			if (!spotPositionMarketSnap.addManifestMeasureSnap (
					"ForwardRate",
					spotForwardRate,
					spotDV01,
					spotForwardRate
				) || !spotPositionMarketSnap.setR1 (
					"DV01",
					spotDV01
				) || !spotPositionMarketSnap.setR1 (
					"ForwardRate",
					spotForwardRate
				) || !spotPositionMarketSnap.setC1 (
					"FloaterLabel",
					futuresSingleStreamComponent.forwardLabel().get ("DERIVED").fullyQualifiedName()
				)
			)
			{
				return null;
			}

			return new PositionChangeComponents (
				false,
				previousPositionMarketSnap,
				spotPositionMarketSnap,
				0.,
				null
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Funding Futures Horizon Metrics
	 * 
	 * @param closingDateArray Array of Closing Dates
	 * @param expiryDateArray Array of Expiry Dates
	 * @param futuresQuoteArray Array of Closing Futures Quotes
	 * @param currency Funding Currency
	 * 
	 * @return The Funding Futures Horizon Metrics
	 */

	public static final List<PositionChangeComponents> HorizonChangeAttribution (
		final JulianDate[] closingDateArray,
		final JulianDate[] expiryDateArray,
		final double[] futuresQuoteArray,
		final String currency)
	{
		if (null == closingDateArray || null == expiryDateArray || null == futuresQuoteArray) {
			return null;
		}

		if (0 == closingDateArray.length ||
			closingDateArray.length != expiryDateArray.length ||
			closingDateArray.length != futuresQuoteArray.length)
		{
			return null;
		}

		List<PositionChangeComponents> positionChangeComponentsList =
			new ArrayList<PositionChangeComponents>();

		for (int closingDateIndex = 1; closingDateIndex < closingDateArray.length; ++closingDateIndex) {
			if (expiryDateArray[closingDateIndex - 1].julian() != expiryDateArray[closingDateIndex].julian())
			{
				continue;
			}

			PositionChangeComponents positionChangeComponents = HorizonMetrics (
				closingDateArray[closingDateIndex - 1],
				closingDateArray[closingDateIndex],
				expiryDateArray[closingDateIndex],
				futuresQuoteArray[closingDateIndex - 1],
				futuresQuoteArray[closingDateIndex],
				currency
			);

			if (null == positionChangeComponents) {
				continue;
			}

			positionChangeComponentsList.add (positionChangeComponents);
		}

		return positionChangeComponentsList;
	}
}
