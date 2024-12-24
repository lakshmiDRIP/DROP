
package org.drip.service.product;

import java.util.ArrayList;
import java.util.List;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.historical.attribution.PositionChangeComponents;
import org.drip.historical.engine.FixFloatExplainProcessor;
import org.drip.historical.engine.HorizonChangeExplainExecutor;
import org.drip.market.otc.FixedFloatSwapConvention;
import org.drip.market.otc.IBORFixedFloatContainer;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.rates.FixFloatComponent;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;

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
 * <i>FixFloatAPI</i> contains the Functionality associated with the Horizon Analysis of the Fix Float Swap.
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Compute the Horizon Change Attribution Details for the Specified Fix-Float Swap</li>
 * 		<li>Generate the Funding Curve Horizon Metrics #1</li>
 * 		<li>Generate the Funding Curve Horizon Metrics #2</li>
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

public class FixFloatAPI
{

	/**
	 * Compute the Horizon Change Attribution Details for the Specified Fix-Float Swap
	 * 
	 * @param firstDiscountCurve First Day Discount Curve
	 * @param secondDiscountCurve Second Date Discount Curve
	 * @param rollDownDiscountCurveMap Map of the Roll Down Discount Curve
	 * @param maturityTenor Fix Float Swap Maturity Tenor
	 * 
	 * @return The Horizon Change Attribution Instance
	 */

	public static final PositionChangeComponents HorizonChangeAttribution (
		final MergedDiscountForwardCurve firstDiscountCurve,
		final MergedDiscountForwardCurve secondDiscountCurve,
		final CaseInsensitiveHashMap<MergedDiscountForwardCurve> rollDownDiscountCurveMap,
		final String maturityTenor)
	{
		if (null == rollDownDiscountCurveMap || 0 == rollDownDiscountCurveMap.size()) {
			return null;
		}

		FixedFloatSwapConvention fixedFloatSwapConvention =
			IBORFixedFloatContainer.ConventionFromJurisdiction (
				firstDiscountCurve.currency(),
				"ALL",
				maturityTenor,
				"MAIN"
			);

		if (null == fixedFloatSwapConvention) {
			return null;
		}

		int settleLag = fixedFloatSwapConvention.spotLag();

		JulianDate firstDiscountCurveDate = firstDiscountCurve.epoch();

		FixFloatComponent fixFloatComponent = fixedFloatSwapConvention.createFixFloatComponent (
			firstDiscountCurveDate,
			maturityTenor,
			0.,
			0.,
			1.
		);

		if (null == fixFloatComponent) {
			return null;
		}

		CurveSurfaceQuoteContainer firstCurveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

		if (!firstCurveSurfaceQuoteContainer.setFundingState (firstDiscountCurve)) {
			return null;
		}

		CurveSurfaceQuoteContainer secondCurveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

		if (!secondCurveSurfaceQuoteContainer.setFundingState (secondDiscountCurve)) {
			return null;
		}

		CaseInsensitiveHashMap<CurveSurfaceQuoteContainer> curveSurfaceQuoteContainerRollDownMap =
			new CaseInsensitiveHashMap<CurveSurfaceQuoteContainer>();

		for (String rollDownTenor : rollDownDiscountCurveMap.keySet()) {
			CurveSurfaceQuoteContainer curveSurfaceQuoteContainerRollDown = new CurveSurfaceQuoteContainer();

			MergedDiscountForwardCurve rollDownDiscountCurve = rollDownDiscountCurveMap.get (rollDownTenor);

			if (null == rollDownDiscountCurve ||
				!curveSurfaceQuoteContainerRollDown.setFundingState (rollDownDiscountCurve)) 
			{
				return null;
			}

			curveSurfaceQuoteContainerRollDownMap.put (rollDownTenor, curveSurfaceQuoteContainerRollDown);
		}

		try {
			double swapRate = fixFloatComponent.measureValue (
				ValuationParams.Spot (
					firstDiscountCurveDate.addBusDays (settleLag, fixFloatComponent.payCurrency()).julian()
				),
				null,
				firstCurveSurfaceQuoteContainer,
				null,
				"SwapRate"
			);

			return HorizonChangeExplainExecutor.GenerateAttribution (
				new
				FixFloatExplainProcessor (
					fixedFloatSwapConvention.createFixFloatComponent (
						firstDiscountCurveDate,
						maturityTenor,
						swapRate,
						0.,
						1.
					),
					settleLag,
					"SwapRate",
					swapRate,
					firstDiscountCurveDate,
					secondDiscountCurve.epoch(),
					firstCurveSurfaceQuoteContainer,
					secondCurveSurfaceQuoteContainer,
					curveSurfaceQuoteContainerRollDownMap
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Funding Curve Horizon Metrics
	 * 
	 * @param firstDiscountCurveDate The First Date
	 * @param secondDiscountCurveDate The Second Date
	 * @param fundingDepositInstrumentTenorArray Array of Funding Curve Deposit Instrument Maturity Tenors
	 * @param firstFundingDepositInstrumentArray Array of First Date Funding Curve Deposit Instrument Quotes
	 * @param secondFundingDepositInstrumentArray Array of Second Date Funding Curve Deposit Instrument
	 *  Quotes
	 * @param fundingFixFloatTenorArray Array of Funding Curve Fix Float Instrument Maturity Tenors
	 * @param firstFundingFixFloatArray Array of First Date Funding Curve Fix Float Swap Rates
	 * @param secondFundingFixFloatArray Array of Second Date Funding Curve Fix Float Swap Rates
	 * @param currency Funding Currency
	 * @param maturityTenor Maturity Tenor
	 * @param rollDownHorizonTenorArray Array of the Roll Down Horizon Tenors
	 * @param latentStateType Latent State Type
	 * 
	 * @return The Funding Curve Horizon Metrics
	 */

	public static final PositionChangeComponents HorizonChangeAttribution (
		final JulianDate firstDiscountCurveDate,
		final JulianDate secondDiscountCurveDate,
		final String[] fundingDepositInstrumentTenorArray,
		final double[] firstFundingDepositInstrumentArray,
		final double[] secondFundingDepositInstrumentArray,
		final String[] fundingFixFloatTenorArray,
		final double[] firstFundingFixFloatArray,
		final double[] secondFundingFixFloatArray,
		final String currency,
		final String maturityTenor,
		final String[] rollDownHorizonTenorArray,
		final int latentStateType)
	{
		if (null == firstDiscountCurveDate ||
			null == secondDiscountCurveDate ||
			firstDiscountCurveDate.julian() >= secondDiscountCurveDate.julian())
		{
			return null;
		}

		int fundingDepositInstrumentCount = null == fundingDepositInstrumentTenorArray ? 0 :
			fundingDepositInstrumentTenorArray.length;
		int firstFundingDepositInstrumentCount = null == firstFundingDepositInstrumentArray ? 0 :
			firstFundingDepositInstrumentArray.length;
		int secondFundingDepositInstrumentCount = null == secondFundingDepositInstrumentArray ? 0 :
			secondFundingDepositInstrumentArray.length;
		int fundingFixFloatCount = null == fundingFixFloatTenorArray ? 0 : fundingFixFloatTenorArray.length;
		int firstFundingFixFloatCount = null == firstFundingFixFloatArray ? 0 :
			firstFundingFixFloatArray.length;
		int secondFundingFixFloatCount = null == secondFundingFixFloatArray ? 0 :
			secondFundingFixFloatArray.length;
		int rollDownHorizonCount = null == rollDownHorizonTenorArray ? 0 : rollDownHorizonTenorArray .length;

		CaseInsensitiveHashMap<MergedDiscountForwardCurve> rollDownDiscountCurveMap =
			0 == rollDownHorizonCount ? null : new CaseInsensitiveHashMap<MergedDiscountForwardCurve>();

		if (0 == fundingDepositInstrumentCount ||
			fundingDepositInstrumentCount != firstFundingDepositInstrumentCount ||
			fundingDepositInstrumentCount != secondFundingDepositInstrumentCount ||
			0 == fundingFixFloatCount ||
			fundingFixFloatCount != firstFundingFixFloatCount ||
			fundingFixFloatCount != secondFundingFixFloatCount)
		{
			return null;
		}

		MergedDiscountForwardCurve firstDiscountCurve = LatentMarketStateBuilder.FundingCurve (
			firstDiscountCurveDate,
			currency,
			fundingDepositInstrumentTenorArray,
			firstFundingDepositInstrumentArray,
			"ForwardRate",
			null,
			"ForwardRate",
			fundingFixFloatTenorArray,
			firstFundingFixFloatArray,
			"SwapRate",
			latentStateType
		);

		MergedDiscountForwardCurve secondDiscountCurve = LatentMarketStateBuilder.FundingCurve (
			secondDiscountCurveDate,
			currency,
			fundingDepositInstrumentTenorArray,
			secondFundingDepositInstrumentArray,
			"ForwardRate",
			null,
			"ForwardRate",
			fundingFixFloatTenorArray,
			secondFundingFixFloatArray,
			"SwapRate",
			latentStateType
		);

		MergedDiscountForwardCurve rollDownDiscountCurve = LatentMarketStateBuilder.FundingCurve (
			secondDiscountCurveDate,
			currency,
			fundingDepositInstrumentTenorArray,
			firstFundingDepositInstrumentArray,
			"ForwardRate",
			null,
			"ForwardRate",
			fundingFixFloatTenorArray,
			firstFundingFixFloatArray,
			"SwapRate",
			latentStateType
		);

		if (null == rollDownDiscountCurve) {
			return null;
		}

		rollDownDiscountCurveMap.put ("Native", rollDownDiscountCurve);

		for (int rollDownHorizon = 0; rollDownHorizon < rollDownHorizonCount; ++rollDownHorizon) {
			MergedDiscountForwardCurve horizonRollDownDiscountCurve = LatentMarketStateBuilder.FundingCurve (
				firstDiscountCurveDate.addTenor (rollDownHorizonTenorArray[rollDownHorizon]),
				currency,
				fundingDepositInstrumentTenorArray,
				firstFundingDepositInstrumentArray,
				"ForwardRate",
				null,
				"ForwardRate",
				fundingFixFloatTenorArray,
				firstFundingFixFloatArray,
				"SwapRate",
				latentStateType
			);

			if (null == horizonRollDownDiscountCurve) {
				return null;
			}

			rollDownDiscountCurveMap.put (
				rollDownHorizonTenorArray[rollDownHorizon],
				horizonRollDownDiscountCurve
			);
		}

		return HorizonChangeAttribution (
			firstDiscountCurve,
			secondDiscountCurve,
			rollDownDiscountCurveMap,
			maturityTenor
		);
	}

	/**
	 * Generate the Funding Curve Horizon Metrics
	 * 
	 * @param spotDateArray Array of Spot
	 * @param horizonGap The Horizon Gap
	 * @param fundingDepositInstrumentTenorArray Array of Funding Curve Deposit Instrument Maturity Tenors
	 * @param fundingDepositInstrumentQuoteGrid Array of Funding Curve Deposit Instrument Forward Rates
	 * @param fundingFixFloatTenorArray Array of Funding Curve Fix Float Instrument Maturity Tenors
	 * @param fundingFixFloatQuoteGrid Array of Funding Curve Fix Float Instrument Swap Rates
	 * @param currency Funding Currency
	 * @param maturityTenor Maturity Tenor
	 * @param rollDownHorizonTenorArray Array of the Roll Down Horizon Tenors
	 * @param latentStateType Latent State Type
	 * 
	 * @return The Funding Curve Horizon Metrics
	 */

	public static final List<PositionChangeComponents> HorizonChangeAttribution (
		final JulianDate[] spotDateArray,
		final int horizonGap,
		final String[] fundingDepositInstrumentTenorArray,
		final double[][] fundingDepositInstrumentQuoteGrid,
		final String[] fundingFixFloatTenorArray,
		final double[][] fundingFixFloatQuoteGrid,
		final String currency,
		final String maturityTenor,
		final String[] rollDownHorizonTenorArray,
		final int latentStateType)
	{
		if (null == spotDateArray ||
			0 >= horizonGap ||
			null == fundingDepositInstrumentQuoteGrid ||
			null == fundingFixFloatQuoteGrid)
		{
			return null;
		}

		int iNumRollDownTenor = null == rollDownHorizonTenorArray ? 0 : rollDownHorizonTenorArray.length;

		if (0 == spotDateArray.length ||
			spotDateArray.length != fundingDepositInstrumentQuoteGrid.length ||
			spotDateArray.length != fundingFixFloatQuoteGrid.length ||
			0 == iNumRollDownTenor)
		{
			return null;
		}

		List<PositionChangeComponents> positionChangeComponentsList =
			new ArrayList<PositionChangeComponents>();

		for (int spotDateIndex = horizonGap; spotDateIndex < spotDateArray.length; ++spotDateIndex) {
			PositionChangeComponents positionChangeComponents = HorizonChangeAttribution (
				spotDateArray[spotDateIndex - horizonGap],
				spotDateArray[spotDateIndex],
				fundingDepositInstrumentTenorArray,
				fundingDepositInstrumentQuoteGrid[spotDateIndex - horizonGap],
				fundingDepositInstrumentQuoteGrid[spotDateIndex],
				fundingFixFloatTenorArray,
				fundingFixFloatQuoteGrid[spotDateIndex - horizonGap],
				fundingFixFloatQuoteGrid[spotDateIndex],
				currency,
				maturityTenor,
				rollDownHorizonTenorArray,
				latentStateType
			);

			if (null != positionChangeComponents) {
				positionChangeComponentsList.add (positionChangeComponents);
			}
		}

		return positionChangeComponentsList;
	}
}
