
package org.drip.service.state;

import java.util.Map;
import java.util.TreeMap;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.Helper;
import org.drip.historical.state.FundingCurveMetrics;
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
 * <i>FundingCurveAPI</i> computes the Metrics associated the Funding Curve State. It provides the following
 * 	Functionality:
 *
 *  <ul>
 * 		<li>Generate the Funding Curve Daily Metrics</li>
 * 		<li>Generate the Funding Curve Horizon Metrics</li>
 * 		<li>Generate the Funding Curve Map</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/state/README.md">Curve Based State Metric Generator</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FundingCurveAPI
{

	/**
	 * Generate the Funding Curve Daily Metrics
	 * 
	 * @param spotDate The Spot Date
	 * @param fixFloatMaturityTenorArray Array of Fix Float Maturity Tenors
	 * @param fixFloatQuoteGrid Array of Fix Float Swap Rates
	 * @param inTenorArray Array of "In" Tenors
	 * @param forTenorArray Array of "For" Tenors
	 * @param currency Funding Currency
	 * @param latentStateType Latent State Type
	 * 
	 * @return The Funding Curve Daily Metrics
	 */

	public static final FundingCurveMetrics DailyMetrics (
		final JulianDate spotDate,
		final String[] fixFloatMaturityTenorArray,
		final double[] fixFloatQuoteGrid,
		final String[] inTenorArray,
		final String[] forTenorArray,
		final String currency,
		final int latentStateType)
	{
		if (null == spotDate ||
			null == fixFloatMaturityTenorArray || 0 == fixFloatMaturityTenorArray.length ||
			null == fixFloatQuoteGrid || 0 == fixFloatQuoteGrid.length ||
			null == inTenorArray || 0 == inTenorArray.length ||
			null == forTenorArray || 0 == forTenorArray.length ||
			fixFloatQuoteGrid.length != fixFloatMaturityTenorArray.length)
		{
			return null;
		}

		double[] forTenorDayCountFractionArray = new double[forTenorArray.length];

		for (int forTenorIndex = 0; forTenorIndex < forTenorArray.length; ++forTenorIndex) {
			try {
				forTenorDayCountFractionArray[forTenorIndex] = Helper.TenorToYearFraction (
					forTenorArray[forTenorIndex]
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		MergedDiscountForwardCurve fundingDiscountCurve = LatentMarketStateBuilder.FundingCurve (
			spotDate,
			currency,
			null,
			null,
			"ForwardRate",
			null,
			"ForwardRate",
			fixFloatMaturityTenorArray,
			fixFloatQuoteGrid,
			"SwapRate",
			latentStateType
		);

		if (null == fundingDiscountCurve) {
			return null;
		}

		try {
			FundingCurveMetrics fundingCurveMetrics = new FundingCurveMetrics (spotDate);

			for (String inTenor : inTenorArray) {
				JulianDate inTenorDate = spotDate.addTenor (inTenor);

				for (int forTenorIndex = 0; forTenorIndex < forTenorArray.length; ++forTenorIndex) {
					if (!fundingCurveMetrics.addNativeForwardRate (
						inTenor,
						forTenorArray[forTenorIndex],
						Math.pow (
							fundingDiscountCurve.df (inTenorDate) /fundingDiscountCurve.df (
								inTenorDate.addTenor (forTenorArray[forTenorIndex])
							),
							1. / forTenorDayCountFractionArray[forTenorIndex]
						) - 1.
					))
					{
						continue;
					}
				}
			}

			return fundingCurveMetrics;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Funding Curve Horizon Metrics
	 * 
	 * @param spotDateArray Array of Spot
	 * @param fixFloatMaturityTenorArray Array of Fix Float Maturity Tenors
	 * @param fixFloatQuoteGrid Array of Fix Float Swap Rates
	 * @param inTenorArray Array of "In" Tenors
	 * @param forTenorArray Array of "For" Tenors
	 * @param currency Funding Currency
	 * @param latentStateType Latent State Type
	 * 
	 * @return The Funding Curve Horizon Metrics
	 */

	public static final Map<JulianDate, FundingCurveMetrics> HorizonMetrics (
		final JulianDate[] spotDateArray,
		final String[] fixFloatMaturityTenorArray,
		final double[][] fixFloatQuoteGrid,
		final String[] inTenorArray,
		final String[] forTenorArray,
		final String currency,
		final int latentStateType)
	{
		if (null == spotDateArray || 0 == spotDateArray.length ||
			null == fixFloatMaturityTenorArray || 0 == fixFloatMaturityTenorArray.length ||
			null == fixFloatQuoteGrid ||
			null == inTenorArray || 0 == inTenorArray.length ||
			null == forTenorArray || 0 == forTenorArray.length)
		{
			return null;
		}

		double[] forTenorDayCountFractionArray = new double[forTenorArray.length];

		for (int forTenorIndex = 0; forTenorIndex < forTenorArray.length; ++forTenorIndex) {
			try {
				forTenorDayCountFractionArray[forTenorIndex] =
					Helper.TenorToYearFraction (forTenorArray[forTenorIndex]);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		Map<JulianDate, FundingCurveMetrics> fundingCurveMetricsMap =
			new TreeMap<JulianDate, FundingCurveMetrics>();

		for (int spotDateIndex = 0; spotDateIndex < spotDateArray.length; ++spotDateIndex) {
			FundingCurveMetrics fundingCurveMetrics = null;
			int fixFloatQuoteCount = null == fixFloatQuoteGrid[spotDateIndex] ?
				0 : fixFloatQuoteGrid[spotDateIndex].length;

			if (fixFloatQuoteCount != fixFloatMaturityTenorArray.length) {
				continue;
			}

			MergedDiscountForwardCurve fundingDiscountCurve = LatentMarketStateBuilder.FundingCurve (
				spotDateArray[spotDateIndex],
				currency,
				null,
				null,
				"ForwardRate",
				null,
				"ForwardRate",
				fixFloatMaturityTenorArray,
				fixFloatQuoteGrid[spotDateIndex],
				"SwapRate",
				latentStateType
			);

			if (null == fundingDiscountCurve) {
				continue;
			}

			try {
				fundingCurveMetrics = new FundingCurveMetrics (spotDateArray[spotDateIndex]);

				for (String inTenor : inTenorArray) {
					JulianDate inTenorDate = spotDateArray[spotDateIndex].addTenor (inTenor);

					for (int forTenorIndex = 0; forTenorIndex < forTenorArray.length; ++forTenorIndex) {
						if (!fundingCurveMetrics.addNativeForwardRate (
							inTenor,
							forTenorArray[forTenorIndex],
							Math.pow (
								fundingDiscountCurve.df (inTenorDate) / fundingDiscountCurve.df (
									inTenorDate.addTenor (forTenorArray[forTenorIndex])
								),
								1. / forTenorDayCountFractionArray[forTenorIndex]
							) - 1.
						))
						{
							continue;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();

				continue;
			}

			fundingCurveMetricsMap.put (spotDateArray[spotDateIndex], fundingCurveMetrics);
		}

		return fundingCurveMetricsMap;
	}

	/**
	 * Generate the Funding Curve Map
	 * 
	 * @param spotDateArray Array of Spot
	 * @param fixFloatMaturityTenorArray Array of Fix Float Maturity Tenors
	 * @param fixFloatQuoteGrid Array of Fix Float Swap Rates
	 * @param currency Funding Currency
	 * @param latentStateType Latent State Type
	 * 
	 * @return The Funding Curve Map
	 */

	public static final Map<JulianDate, MergedDiscountForwardCurve> HistoricalMap (
		final JulianDate[] spotDateArray,
		final String[] fixFloatMaturityTenorArray,
		final double[][] fixFloatQuoteGrid,
		final String currency,
		final int latentStateType)
	{
		if (null == spotDateArray || 0 == spotDateArray.length ||
			null == fixFloatMaturityTenorArray || 0 == fixFloatMaturityTenorArray.length ||
			null == fixFloatQuoteGrid)
		{
			return null;
		}

		Map<JulianDate, MergedDiscountForwardCurve> mapFundingCurve =
			new TreeMap<JulianDate, MergedDiscountForwardCurve>();

		for (int spotDateIndex = 0; spotDateIndex < spotDateArray.length; ++spotDateIndex) {
			int fixFloatQuoteCount = null == fixFloatQuoteGrid[spotDateIndex] ?
				0 : fixFloatQuoteGrid[spotDateIndex].length;

			if (fixFloatQuoteCount != fixFloatMaturityTenorArray.length) {
				continue;
			}

			MergedDiscountForwardCurve fundingDiscountCurve = LatentMarketStateBuilder.FundingCurve (
				spotDateArray[spotDateIndex],
				currency,
				null,
				null,
				"ForwardRate",
				null,
				"ForwardRate",
				fixFloatMaturityTenorArray,
				fixFloatQuoteGrid[spotDateIndex],
				"SwapRate",
				latentStateType
			);

			if (null == fundingDiscountCurve) {
				continue;
			}

			mapFundingCurve.put (spotDateArray[spotDateIndex], fundingDiscountCurve);
		}

		return mapFundingCurve;
	}
}
