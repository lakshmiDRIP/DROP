
package org.drip.state.creator;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.definition.MarketSurface;
import org.drip.analytics.definition.NodeStructure;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.market.LatentStateFixingsContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.fra.FRAStandardCapFloor;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.params.SegmentInelasticDesignControl;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.boot.VolatilityCurveScenario;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.LatentStateLabel;
import org.drip.state.volatility.VolatilityCurve;

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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>ScenarioLocalVolatilityBuilder</i> implements the construction of the Local Volatility surface using
 * 	the input option instruments, their Call Prices, and a wide variety of custom build schemes. It
 *  implements the following Functions:
 * 
 * <ul>
 * 		<li>Create a Volatility Curve from the Calibration Instruments</li>
 * 		<li>Build an Instance of the Volatility Surface using custom wire span and surface splines</li>
 * 		<li>Construct a Scenario Market Surface off of cubic polynomial wire spline and cubic polynomial surface Spline</li>
 * </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/README.md">Scenario State Curve/Surface Builders</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ScenarioLocalVolatilityBuilder
{

	/**
	 * Create a Volatility Curve from the Calibration Instruments
	 * 
	 * @param name Volatility Curve name
	 * @param spotDate Spot Date
	 * @param underlyingLatentStateLabel Underlying Latent State Label
	 * @param fraStandardCapFloorArray Array of the FRA Cap Floor Instruments
	 * @param calibrationQuoteArray Input Calibration Quotes
	 * @param calibrationMeasureArray Input Calibration Measures
	 * @param discountCurve Base Discount Curve
	 * @param forwardCurve Forward Curve
	 * @param latentStateFixingsContainer Latent State Fixings Container
	 * 
	 * @return The Calibrated Volatility Curve
	 */

	public static final VolatilityCurve NonlinearBuild (
		final String name,
		final JulianDate spotDate,
		final LatentStateLabel underlyingLatentStateLabel,
		final FRAStandardCapFloor[] fraStandardCapFloorArray,
		final double[] calibrationQuoteArray,
		final String[] calibrationMeasureArray,
		final MergedDiscountForwardCurve discountCurve,
		final ForwardCurve forwardCurve,
		final LatentStateFixingsContainer latentStateFixingsContainer)
	{
		return null == spotDate ? null : VolatilityCurveScenario.Standard (
			name,
			ValuationParams.Spot (spotDate.julian()),
			underlyingLatentStateLabel,
			fraStandardCapFloorArray,
			calibrationQuoteArray,
			calibrationMeasureArray,
			false,
			discountCurve,
			forwardCurve,
			latentStateFixingsContainer,
			null
		);
	}

	/**
	 * Build an Instance of the Volatility Surface using custom wire span and surface splines
	 * 
	 * @param name Name of the Volatility Surface
	 * @param startDate Start/Epoch Julian Date
	 * @param currency Currency
	 * @param riskFreeRate Risk Free Discounting Rate
	 * @param strikeArray Array of Strikes
	 * @param maturityArray Array of Maturities
	 * @param callPriceGrid Double Array of the Call Prices
	 * @param wireSpanSegmentCustomBuilderControl The Wire Span Segment Customizer
	 * @param surfaceSegmentCustomBuilderControl The Surface Segment Customizer
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final MarketSurface CustomSplineWireSurface (
		final String name,
		final JulianDate startDate,
		final String currency,
		final double riskFreeRate,
		final double[] strikeArray,
		final double[] maturityArray,
		final double[][] callPriceGrid,
		final SegmentCustomBuilderControl wireSpanSegmentCustomBuilderControl,
		final SegmentCustomBuilderControl surfaceSegmentCustomBuilderControl)
	{
		if (!NumberUtil.IsValid (riskFreeRate)) {
			return null;
		}

		MarketSurface callPriceMarketSurface = ScenarioMarketSurfaceBuilder.CustomSplineWireSurface (
			name + "_CALL_PRICE_SURFACE",
			startDate,
			currency,
			strikeArray,
			maturityArray,
			callPriceGrid,
			wireSpanSegmentCustomBuilderControl,
			surfaceSegmentCustomBuilderControl
		);

		if (null == callPriceMarketSurface) {
			return null;
		}

		int strikeCount = strikeArray.length;
		int maturityCount = maturityArray.length;
		double[][] localVolatilityGrid = new double[strikeCount][maturityCount];
		NodeStructure[] maturityAnchorNodeStructureArray = new NodeStructure[maturityCount];

		for (int maturityIndex = 0; maturityIndex < maturityCount; ++maturityIndex) {
			if (null == (
				maturityAnchorNodeStructureArray[maturityIndex] =
					callPriceMarketSurface.yAnchorTermStructure (
						maturityArray[maturityIndex]
					)
				)
			) {
				return null;
			}
		}

		for (int strikeIndex = 0; strikeIndex < strikeCount; ++strikeIndex) {
			NodeStructure strikeAnchorNodeStructure = callPriceMarketSurface.xAnchorTermStructure (
				strikeArray[strikeIndex]
			);

			if (null == strikeAnchorNodeStructure) {
				return null;
			}

			for (int maturityIndex = 0; maturityIndex < maturityCount; ++maturityIndex) {
				try {
					localVolatilityGrid[strikeIndex][maturityIndex] = Math.sqrt (
						(
							strikeAnchorNodeStructure.nodeDerivative (
								(int) maturityArray[maturityIndex],
								1
							) + riskFreeRate * strikeArray[strikeIndex] *
							maturityAnchorNodeStructureArray[maturityIndex].nodeDerivative (
								(int) strikeArray[strikeIndex],
								1
							)
						) / (
							strikeArray[strikeIndex] * strikeArray[strikeIndex] *
							maturityAnchorNodeStructureArray[maturityIndex].nodeDerivative (
								(int) strikeArray[strikeIndex],
								2
							)
						)
					);
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		return ScenarioMarketSurfaceBuilder.CustomSplineWireSurface (
			name,
			startDate,
			currency,
			strikeArray,
			maturityArray,
			localVolatilityGrid,
			wireSpanSegmentCustomBuilderControl,
			surfaceSegmentCustomBuilderControl
		);
	}

	/**
	 * Construct a Scenario Market Surface off of cubic polynomial wire spline and cubic polynomial surface
	 * 	Spline.
	 * 
	 * @param name Name of the Volatility Surface
	 * @param startDate Start/Epoch Julian Date
	 * @param currency Currency
	 * @param riskFreeRate Risk Free Discounting Rate
	 * @param strikeArray Array of Strikes
	 * @param tenorArray Array of Maturity Tenors
	 * @param nodeGrid Double Array of the Surface Nodes
	 * 
	 * @return Instance of the Market Node Surface
	 */

	public static final MarketSurface CubicPolynomialWireSurface (
		final String name,
		final JulianDate startDate,
		final String currency,
		final double riskFreeRate,
		final double[] strikeArray,
		final String[] tenorArray,
		final double[][] nodeGrid)
	{
		if (null == tenorArray) {
			return null;
		}

		int tenorCount = tenorArray.length;
		double[] maturityArray = new double[tenorCount];
		SegmentCustomBuilderControl surfaceSegmentCustomBuilderControl = null;
		SegmentCustomBuilderControl wireSpanSegmentCustomBuilderControl = null;

		if (0 == tenorCount) {
			return null;
		}

		for (int tenorIndex = 0; tenorIndex < tenorCount; ++tenorIndex) {
			maturityArray[tenorIndex] = startDate.addTenor (tenorArray[tenorIndex]).julian();
		}

		try {
			wireSpanSegmentCustomBuilderControl = new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (4),
				SegmentInelasticDesignControl.Create (2, 2),
				null,
				null
			);

			surfaceSegmentCustomBuilderControl = new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (4),
				SegmentInelasticDesignControl.Create (2, 2),
				null,
				null
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return CustomSplineWireSurface (
			name,
			startDate,
			currency,
			riskFreeRate,
			strikeArray,
			maturityArray,
			nodeGrid,
			wireSpanSegmentCustomBuilderControl,
			surfaceSegmentCustomBuilderControl
		);
	}
}
