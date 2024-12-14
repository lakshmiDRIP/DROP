
package org.drip.service.template;

import org.drip.analytics.date.JulianDate;
import org.drip.function.r1tor1custom.QuadraticRationalShapeControl;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.valuation.ValuationParams;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.ResponseScalingShapeControl;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.params.SegmentInelasticDesignControl;
import org.drip.spline.stretch.BoundarySettings;
import org.drip.spline.stretch.MultiSegmentSequence;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.creator.ScenarioDiscountCurveBuilder;
import org.drip.state.creator.ScenarioForwardCurveBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.estimator.LatentStateStretchBuilder;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.ForwardLabel;
import org.drip.state.inference.LatentStateStretchSpec;
import org.drip.state.inference.LinearLatentStateCalibrator;

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
 * <i>LatentMarketStateBuilder</i> contains static Helper API to facilitate Construction of the Latent Market
 * 	States as Curves/Surfaces. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Generate a Forward Rate Futures Contract corresponding to the Spot Date</li>
 * 		<li>Generate a Forward Rate Futures Pack corresponding to the Spot Date and the Specified Number of Contracts</li>
 * 		<li>Generate an Instance of Treasury Futures given the Inputs</li>
 * 		<li>Generate the Treasury Futures Instance #1</li>
 * 		<li>Generate the Treasury Futures Instance #2</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/template/README.md">Curve Construction Product Builder Templates</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LatentMarketStateBuilder
{

	/**
	 * Shape Preserving Latent State
	 */

	public static final int SHAPE_PRESERVING = 0;

	/**
	 * Smoothened Latent State
	 */

	public static final int SMOOTH = 1;

	/**
	 * Construct a Funding Curve Based off of the Input Exchange/OTC Market Instruments Using the specified
	 *  Spline
	 * 
	 * @param spotDate The Spot Date
	 * @param currency Currency
	 * @param depositMaturityTenorArray Array of Deposit Maturity Tenors
	 * @param depositQuoteArray Array of Deposit Quotes
	 * @param depositMeasure Deposit Calibration Measure
	 * @param futuresQuoteArray Array of Futures Quotes
	 * @param futuresMeasure Futures Calibration Measure
	 * @param fixFloatMaturityTenorArray Array of Fix Float Swap Maturity Tenors
	 * @param fixFloatQuoteArray Array of Fix Float Swap Quotes
	 * @param fixFloatMeasure Fix Float Calibration Measure
	 * @param segmentCustomBuilderControl Segment Custom Builder Control
	 * 
	 * @return The Funding Curve Instance
	 */

	public static final MergedDiscountForwardCurve FundingCurve (
		final JulianDate spotDate,
		final String currency,
		final String[] depositMaturityTenorArray,
		final double[] depositQuoteArray,
		final String depositMeasure,
		final double[] futuresQuoteArray,
		final String futuresMeasure,
		final String[] fixFloatMaturityTenorArray,
		final double[] fixFloatQuoteArray,
		final String fixFloatMeasure,
		final SegmentCustomBuilderControl segmentCustomBuilderControl)
	{
		if (null == spotDate || null == currency || currency.isEmpty()) {
			return null;
		}

		JulianDate effectiveDate = spotDate.addBusDays (0, currency);

		LatentStateStretchSpec depositLatentStateStretchSpec = null;
		LatentStateStretchSpec futuresLatentStateStretchSpec = null;
		LatentStateStretchSpec fixFloatLatentStateStretchSpec = null;
		int depositQuoteCount = null == depositQuoteArray ? 0 : depositQuoteArray.length;
		int fixFloatQuoteCount = null == fixFloatQuoteArray ? 0 : fixFloatQuoteArray.length;
		int futuresComponentCount = null == futuresQuoteArray ? 0 : futuresQuoteArray.length;
		int depositComponentCount = null == depositMaturityTenorArray ? 0 : depositMaturityTenorArray.length;
		int fixFloatComponentCount =
			null == fixFloatMaturityTenorArray ? 0 : fixFloatMaturityTenorArray.length;

		if (depositQuoteCount != depositComponentCount || fixFloatQuoteCount != fixFloatComponentCount) {
			return null;
		}

		if (0 != depositComponentCount) {
			depositLatentStateStretchSpec = LatentStateStretchBuilder.ForwardFundingStretchSpec (
				"DEPOSIT",
				OTCInstrumentBuilder.FundingDeposit (effectiveDate, currency, depositMaturityTenorArray),
				depositMeasure,
				depositQuoteArray
			);
		}

		if (0 != futuresComponentCount) {
			futuresLatentStateStretchSpec = LatentStateStretchBuilder.ForwardFundingStretchSpec (
				"FUTURES",
				ExchangeInstrumentBuilder.ForwardRateFuturesPack (
					effectiveDate,
					futuresComponentCount,
					currency
				),
				futuresMeasure,
				futuresQuoteArray
			);
		}

		if (0 != fixFloatComponentCount) {
			fixFloatLatentStateStretchSpec = LatentStateStretchBuilder.ForwardFundingStretchSpec (
				"FIXFLOAT",
				OTCInstrumentBuilder.FixFloatStandard (
					effectiveDate,
					currency,
					"ALL",
					fixFloatMaturityTenorArray,
					"MAIN",
					0.
				),
				fixFloatMeasure,
				fixFloatQuoteArray
			);
		}

		try {
			return ScenarioDiscountCurveBuilder.ShapePreservingDFBuild (
				currency,
				new LinearLatentStateCalibrator (
					segmentCustomBuilderControl,
					BoundarySettings.NaturalStandard(),
					MultiSegmentSequence.CALIBRATE,
					null,
					null
				),
				new LatentStateStretchSpec[] {
					depositLatentStateStretchSpec,
					futuresLatentStateStretchSpec,
					fixFloatLatentStateStretchSpec
				},
				ValuationParams.Spot (spotDate.julian()),
				null,
				null,
				null,
				1.
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Single Stretch Funding Curve Based off of the Input Exchange/OTC Market Instruments Using
	 *  the specified Spline
	 * 
	 * @param spotDate The Spot Date
	 * @param currency Currency
	 * @param depositMaturityTenorArray Array of Deposit Maturity Tenors
	 * @param depositQuoteArray Array of Deposit Quotes
	 * @param depositMeasure Deposit Calibration Measure
	 * @param futuresQuoteArray Array of Futures Quotes
	 * @param futuresMeasure Futures Calibration Measure
	 * @param fixFloatMaturityTenorArray Array of Fix Float Swap Maturity Tenors
	 * @param fixFloatQuoteArray Array of Fix Float Swap Quotes
	 * @param fixFloatMeasure Fix Float Calibration Measure
	 * @param segmentCustomBuilderControl Segment Custom Builder Control
	 * 
	 * @return The Funding Curve Instance
	 */

	public static final MergedDiscountForwardCurve SingleStretchFundingCurve (
		final JulianDate spotDate,
		final String currency,
		final String[] depositMaturityTenorArray,
		final double[] depositQuoteArray,
		final String depositMeasure,
		final double[] futuresQuoteArray,
		final String futuresMeasure,
		final String[] fixFloatMaturityTenorArray,
		final double[] fixFloatQuoteArray,
		final String fixFloatMeasure,
		final SegmentCustomBuilderControl segmentCustomBuilderControl)
	{
		if (null == spotDate || null == currency || currency.isEmpty()) {
			return null;
		}

		JulianDate effectiveDate = spotDate.addBusDays (0, currency);

		int fixFloatComponentCount = null == fixFloatMaturityTenorArray ?
			0 : fixFloatMaturityTenorArray.length;
		int depositComponentCount = null == depositMaturityTenorArray ? 0 : depositMaturityTenorArray.length;
		int futuresComponentCount = null == futuresQuoteArray ? 0 : futuresQuoteArray.length;
		int fixFloatQuoteCount = null == fixFloatQuoteArray ? 0 : fixFloatQuoteArray.length;
		int depositQuoteCount = null == depositQuoteArray ? 0 : depositQuoteArray.length;
		int depositFuturesComponentCount = depositComponentCount + futuresComponentCount;
		double[] depositFuturesQuoteArray = new double[depositFuturesComponentCount];
		LatentStateStretchSpec depositFuturesLatentStateStretchSpec = null;
		LatentStateStretchSpec fixFloatLatentStateStretchSpec = null;

		if (depositQuoteCount != depositComponentCount || fixFloatQuoteCount != fixFloatComponentCount) {
			return null;
		}

		for (int depositFuturesComponentIndex = 0;
			depositFuturesComponentIndex < depositFuturesComponentCount;
			++depositFuturesComponentIndex)
		{
			depositFuturesQuoteArray[depositFuturesComponentIndex] =
				depositFuturesComponentIndex < depositComponentCount ?
					depositQuoteArray[depositFuturesComponentIndex] :
					futuresQuoteArray[depositFuturesComponentIndex - depositComponentCount];
		}

		if (0 != depositComponentCount) {
			depositFuturesLatentStateStretchSpec = LatentStateStretchBuilder.ForwardFundingStretchSpec (
				"DEPOSIT",
				OTCInstrumentBuilder.FundingDepositFutures (
					effectiveDate,
					currency,
					depositMaturityTenorArray,
					futuresComponentCount
				),
				depositMeasure,
				depositFuturesQuoteArray
			);
		}

		if (0 != fixFloatComponentCount) {
			fixFloatLatentStateStretchSpec = LatentStateStretchBuilder.ForwardFundingStretchSpec (
				"FIXFLOAT",
				OTCInstrumentBuilder.FixFloatStandard (
					effectiveDate,
					currency,
					"ALL",
					fixFloatMaturityTenorArray,
					"MAIN",
					0.
				),
				fixFloatMeasure,
				fixFloatQuoteArray
			);
		}

		try {
			return ScenarioDiscountCurveBuilder.ShapePreservingDFBuild (
				currency,
				new LinearLatentStateCalibrator (
					segmentCustomBuilderControl,
					BoundarySettings.NaturalStandard(),
					MultiSegmentSequence.CALIBRATE,
					null,
					null
				),
				new LatentStateStretchSpec[] {
					depositFuturesLatentStateStretchSpec,
					fixFloatLatentStateStretchSpec
				},
				ValuationParams.Spot (spotDate.julian()),
				null,
				null,
				null,
				1.
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Shape Preserving Single Stretch Funding Curve Based off of the Input Exchange/OTC Market
	 *  Instruments
	 * 
	 * @param spotDate The Spot Date
	 * @param currency Currency
	 * @param depositMaturityTenorArray Array of Deposit Maturity Tenors
	 * @param depositQuoteArray Array of Deposit Quotes
	 * @param depositMeasure Deposit Calibration Measure
	 * @param futuresQuoteArray Array of Futures Quotes
	 * @param futuresMeasure Futures Calibration Measure
	 * @param fixFloatMaturityTenorArray Array of Fix Float Swap Maturity Tenors
	 * @param fixFloatQuoteArray Array of Fix Float Swap Quotes
	 * @param fixFloatMeasure Fix Float Calibration Measure
	 * 
	 * @return The Single Stretch Funding Curve Instance
	 */

	public static final MergedDiscountForwardCurve SingleStretchShapePreservingFundingCurve (
		final JulianDate spotDate,
		final String currency,
		final String[] depositMaturityTenorArray,
		final double[] depositQuoteArray,
		final String depositMeasure,
		final double[] futuresQuoteArray,
		final String futuresMeasure,
		final String[] fixFloatMaturityTenorArray,
		final double[] fixFloatQuoteArray,
		final String fixFloatMeasure)
	{
		try {
			return SingleStretchFundingCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				futuresQuoteArray,
				futuresMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				fixFloatMeasure,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new PolynomialFunctionSetParams (2),
					SegmentInelasticDesignControl.Create (0, 2),
					new ResponseScalingShapeControl (
						true,
						new QuadraticRationalShapeControl (0.)
					),
					null
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Shape Preserving Funding Curve Based off of the Input Exchange/OTC Market Instruments
	 * 
	 * @param spotDate The Spot Date
	 * @param currency Currency
	 * @param depositMaturityTenorArray Array of Deposit Maturity Tenors
	 * @param depositQuoteArray Array of Deposit Quotes
	 * @param depositMeasure Deposit Calibration Measure
	 * @param futuresQuoteArray Array of Futures Quotes
	 * @param futuresMeasure Futures Calibration Measure
	 * @param fixFloatMaturityTenorArray Array of Fix Float Swap Maturity Tenors
	 * @param fixFloatQuoteArray Array of Fix Float Swap Quotes
	 * @param fixFloatMeasure Fix Float Calibration Measure
	 * 
	 * @return The Funding Curve Instance
	 */

	public static final MergedDiscountForwardCurve ShapePreservingFundingCurve (
		final JulianDate spotDate,
		final String currency,
		final String[] depositMaturityTenorArray,
		final double[] depositQuoteArray,
		final String depositMeasure,
		final double[] futuresQuoteArray,
		final String futuresMeasure,
		final String[] fixFloatMaturityTenorArray,
		final double[] fixFloatQuoteArray,
		final String fixFloatMeasure)
	{
		try {
			return FundingCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				futuresQuoteArray,
				futuresMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				fixFloatMeasure,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new PolynomialFunctionSetParams (2),
					SegmentInelasticDesignControl.Create (0, 2),
					new ResponseScalingShapeControl (
						true,
						new QuadraticRationalShapeControl (0.)
					),
					null
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Smooth Funding Curve Based off of the Input Exchange/OTC Market Instruments
	 * 
	 * @param spotDate The Spot Date
	 * @param currency Currency
	 * @param depositMaturityTenorArray Array of Deposit Maturity Tenors
	 * @param depositQuoteArray Array of Deposit Quotes
	 * @param depositMeasure Deposit Calibration Measure
	 * @param futuresQuoteArray Array of Futures Quotes
	 * @param futuresMeasure Futures Calibration Measure
	 * @param fixFloatMaturityTenorArray Array of Fix Float Swap Maturity Tenors
	 * @param fixFloatQuoteArray Array of Fix Float Swap Quotes
	 * @param fixFloatMeasure Fix Float Calibration Measure
	 * 
	 * @return The Funding Curve Instance
	 */

	public static final MergedDiscountForwardCurve SmoothFundingCurve (
		final JulianDate spotDate,
		final String currency,
		final String[] depositMaturityTenorArray,
		final double[] depositQuoteArray,
		final String depositMeasure,
		final double[] futuresQuoteArray,
		final String futuresMeasure,
		final String[] fixFloatMaturityTenorArray,
		final double[] fixFloatQuoteArray,
		final String fixFloatMeasure)
	{
		try {
			return FundingCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				futuresQuoteArray,
				futuresMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				fixFloatMeasure,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new PolynomialFunctionSetParams (4),
					SegmentInelasticDesignControl.Create (2, 2),
					new ResponseScalingShapeControl (
						true,
						new QuadraticRationalShapeControl (0.)
					),
					null
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Smooth Single Stretch Funding Curve Based off of the Input Exchange/OTC Market Instruments
	 * 
	 * @param spotDate The Spot Date
	 * @param currency Currency
	 * @param depositMaturityTenorArray Array of Deposit Maturity Tenors
	 * @param depositQuoteArray Array of Deposit Quotes
	 * @param depositMeasure Deposit Calibration Measure
	 * @param futuresQuoteArray Array of Futures Quotes
	 * @param futuresMeasure Futures Calibration Measure
	 * @param fixFloatMaturityTenorArray Array of Fix Float Swap Maturity Tenors
	 * @param fixFloatQuoteArray Array of Fix Float Swap Quotes
	 * @param fixFloatMeasure Fix Float Calibration Measure
	 * 
	 * @return The Single Stretch Funding Curve Instance
	 */

	public static final MergedDiscountForwardCurve SingleStretchSmoothFundingCurve (
		final JulianDate spotDate,
		final String currency,
		final String[] depositMaturityTenorArray,
		final double[] depositQuoteArray,
		final String depositMeasure,
		final double[] futuresQuoteArray,
		final String futuresMeasure,
		final String[] fixFloatMaturityTenorArray,
		final double[] fixFloatQuoteArray,
		final String fixFloatMeasure)
	{
		try {
			return SingleStretchFundingCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				futuresQuoteArray,
				futuresMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				fixFloatMeasure,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new PolynomialFunctionSetParams (4),
					SegmentInelasticDesignControl.Create (2, 2),
					new ResponseScalingShapeControl (true, new QuadraticRationalShapeControl (0.)),
					null
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Funding Curve Based off of the Input Exchange/OTC Market Instruments
	 * 
	 * @param spotDate The Spot Date
	 * @param currency Currency
	 * @param depositMaturityTenorArray Array of Deposit Maturity Tenors
	 * @param depositQuoteArray Array of Deposit Quotes
	 * @param depositMeasure Deposit Calibration Measure
	 * @param futuresQuoteArray Array of Futures Quotes
	 * @param futuresMeasure Futures Calibration Measure
	 * @param fixFloatMaturityTenorArray Array of Fix Float Swap Maturity Tenors
	 * @param fixFloatQuoteArray Array of Fix Float Swap Quotes
	 * @param fixFloatMeasure Fix Float Calibration Measure
	 * @param latentStateType SHAPE_PRESERVING/SMOOTH
	 * 
	 * @return The Funding Curve Instance
	 */

	public static final MergedDiscountForwardCurve FundingCurve (
		final JulianDate spotDate,
		final String currency,
		final String[] depositMaturityTenorArray,
		final double[] depositQuoteArray,
		final String depositMeasure,
		final double[] futuresQuoteArray,
		final String futuresMeasure,
		final String[] fixFloatMaturityTenorArray,
		final double[] fixFloatQuoteArray,
		final String fixFloatMeasure,
		final int latentStateType)
	{
		if (SHAPE_PRESERVING == latentStateType) {
			return ShapePreservingFundingCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				futuresQuoteArray,
				futuresMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				fixFloatMeasure
			);
		}

		if (SMOOTH == latentStateType) {
			return SmoothFundingCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				futuresQuoteArray,
				futuresMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				fixFloatMeasure
			);
		}

		return null;
	}

	/**
	 * Construct a Single Stretch Funding Curve Based off of the Input Exchange/OTC Market Instruments
	 * 
	 * @param spotDate The Spot Date
	 * @param currency Currency
	 * @param depositMaturityTenorArray Array of Deposit Maturity Tenors
	 * @param depositQuoteArray Array of Deposit Quotes
	 * @param depositMeasure Deposit Calibration Measure
	 * @param futuresQuoteArray Array of Futures Quotes
	 * @param futuresMeasure Futures Calibration Measure
	 * @param fixFloatMaturityTenorArray Array of Fix Float Swap Maturity Tenors
	 * @param fixFloatQuoteArray Array of Fix Float Swap Quotes
	 * @param strFixFloatMeasure Fix Float Calibration Measure
	 * @param latentStateType SHAPE_PRESERVING/SMOOTH
	 * 
	 * @return The Single Stretch Funding Curve Instance
	 */

	public static final MergedDiscountForwardCurve SingleStretchFundingCurve (
		final JulianDate spotDate,
		final String currency,
		final String[] depositMaturityTenorArray,
		final double[] depositQuoteArray,
		final String depositMeasure,
		final double[] futuresQuoteArray,
		final String futuresMeasure,
		final String[] fixFloatMaturityTenorArray,
		final double[] fixFloatQuoteArray,
		final String strFixFloatMeasure,
		final int latentStateType)
	{
		if (SHAPE_PRESERVING == latentStateType) {
			return SingleStretchShapePreservingFundingCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				futuresQuoteArray,
				futuresMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				strFixFloatMeasure
			);
		}

		if (SMOOTH == latentStateType) {
			return SingleStretchSmoothFundingCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				futuresQuoteArray,
				futuresMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				strFixFloatMeasure
			);
		}

		return null;
	}

	/**
	 * Construct a Instance of the Forward Curve off of Exchange/OTC Market Instruments
	 * 
	 * @param spotDate Spot Date
	 * @param forwardLabel Forward Label
	 * @param depositMaturityTenorArray Array of Deposit Maturity Tenors
	 * @param depositQuoteArray Array of the Deposit Instrument Quotes
	 * @param depositMeasure The Deposit Instrument Calibration Measure
	 * @param fraMaturityTenorArray Array of FRA Maturity Tenors
	 * @param fraQuoteArray Array of the FRA Instrument Quotes
	 * @param fraMeasure The FRA Instrument Calibration Measure
	 * @param fixFloatMaturityTenorArray Array of Fix-Float Maturity Tenors
	 * @param fixFloatQuoteArray Array of the Fix-Float Quotes
	 * @param fixFloatMeasure The Fix-Float Calibration Measure
	 * @param floatFloatMaturityTenorArray Array of Float-Float Maturity Tenors
	 * @param floatFloatQuoteArray Array of the Float-Float Quotes
	 * @param floatFloatMeasure The Float-Float Calibration Measure
	 * @param syntheticFloatFloatMaturityTenorArray Array of Synthetic Float-Float Maturity Tenors
	 * @param syntheticFloatFloatQuoteArray Array of the Synthetic Float-Float Quotes
	 * @param syntheticFloatFloatMeasure The Synthetic Float-Float Calibration Measure
	 * @param mergedDiscountForwardCurve The Base Discount Curve
	 * @param referenceForwardCurve The Reference Forward Curve
	 * @param segmentCustomBuilderControl Segment Custom Builder Control Parameters
	 * 
	 * @return Instance of the Forward Curve
	 */

	public static final ForwardCurve ForwardCurve (
		final JulianDate spotDate,
		final ForwardLabel forwardLabel,
		final String[] depositMaturityTenorArray,
		final double[] depositQuoteArray,
		final String depositMeasure,
		final String[] fraMaturityTenorArray,
		final double[] fraQuoteArray,
		final String fraMeasure,
		final String[] fixFloatMaturityTenorArray,
		final double[] fixFloatQuoteArray,
		final String fixFloatMeasure,
		final String[] floatFloatMaturityTenorArray,
		final double[] floatFloatQuoteArray,
		final String floatFloatMeasure,
		final String[] syntheticFloatFloatMaturityTenorArray,
		final double[] syntheticFloatFloatQuoteArray,
		final String syntheticFloatFloatMeasure,
		final MergedDiscountForwardCurve mergedDiscountForwardCurve,
		final ForwardCurve referenceForwardCurve,
		final SegmentCustomBuilderControl segmentCustomBuilderControl)
	{
		if (null == spotDate || null == forwardLabel || null == mergedDiscountForwardCurve) {
			return null;
		}

		String currency = forwardLabel.currency();

		JulianDate effectiveDate = spotDate.addBusDays (0, currency);

		LatentStateStretchSpec fraLatentStateStretchSpec = null;
		LatentStateStretchSpec depositLatentStateStretchSpec = null;
		LatentStateStretchSpec fixFloatLatentStateStretchSpec = null;
		LatentStateStretchSpec floatFloatLatentStateStretchSpec = null;
		int fraQuoteCount = null == fraQuoteArray ? 0 : fraQuoteArray.length;
		LatentStateStretchSpec syntheticFloatFloatLatentStateStretchSpec = null;
		int depositQuoteCount = null == depositQuoteArray ? 0 : depositQuoteArray.length;
		int fixFloatQuoteCount = null == fixFloatQuoteArray ? 0 : fixFloatQuoteArray.length;
		int fraComponentCount = null == fraMaturityTenorArray ? 0 : fraMaturityTenorArray.length;
		int floatFloatQuoteCount = null == floatFloatQuoteArray ? 0 : floatFloatQuoteArray.length;
		int depositComponentCount = null == depositMaturityTenorArray ? 0 : depositMaturityTenorArray.length;
		int fixFloatComponentCount = null == fixFloatMaturityTenorArray ? 0 :
			fixFloatMaturityTenorArray.length;
		int floatFloatComponentCount = null == floatFloatMaturityTenorArray ? 0 :
			floatFloatMaturityTenorArray.length;
		int syntheticFloatFloatQuoteCount = null == syntheticFloatFloatQuoteArray ? 0 :
			syntheticFloatFloatQuoteArray.length;
		int syntheticFloatFloatComponentCount = null == syntheticFloatFloatMaturityTenorArray ? 0 :
			syntheticFloatFloatMaturityTenorArray.length;

		if (depositQuoteCount != depositComponentCount ||
			fraQuoteCount != fraComponentCount ||
			fixFloatQuoteCount != fixFloatComponentCount ||
			floatFloatQuoteCount != floatFloatComponentCount ||
			syntheticFloatFloatQuoteCount != syntheticFloatFloatComponentCount)
		{
			return null;
		}

		if (0 != depositComponentCount) {
			depositLatentStateStretchSpec = LatentStateStretchBuilder.ForwardStretchSpec (
				"DEPOSIT",
				OTCInstrumentBuilder.ForwardRateDeposit (
					effectiveDate,
					depositMaturityTenorArray,
					forwardLabel
				),
				depositMeasure,
				depositQuoteArray
			);
		}

		if (0 != fraComponentCount) {
			fraLatentStateStretchSpec = LatentStateStretchBuilder.ForwardStretchSpec (
				"FRA",
				OTCInstrumentBuilder.FRAStandard (
					effectiveDate,
					forwardLabel,
					fraMaturityTenorArray,
					fraQuoteArray
				),
				fraMeasure,
				fraQuoteArray
			);
		}

		if (0 != fixFloatComponentCount) {
			fixFloatLatentStateStretchSpec = LatentStateStretchBuilder.ForwardStretchSpec (
				"FIXFLOAT",
				OTCInstrumentBuilder.FixFloatCustom (
					effectiveDate,
					forwardLabel,
					fixFloatMaturityTenorArray
				),
				fixFloatMeasure,
				fixFloatQuoteArray
			);
		}

		if (0 != floatFloatComponentCount) {
			floatFloatLatentStateStretchSpec = LatentStateStretchBuilder.ForwardStretchSpec (
				"FLOATFLOAT",
				OTCInstrumentBuilder.FloatFloat (
					effectiveDate,
					currency,
					forwardLabel.tenor(),
					floatFloatMaturityTenorArray,
					0.
				),
				floatFloatMeasure,
				floatFloatQuoteArray
			);
		}

		if (0 != syntheticFloatFloatComponentCount) {
			syntheticFloatFloatLatentStateStretchSpec = LatentStateStretchBuilder.ForwardStretchSpec (
				"SYNTHETICFLOATFLOAT",
				OTCInstrumentBuilder.FloatFloat (
					effectiveDate,
					currency,
					forwardLabel.tenor(), 
					syntheticFloatFloatMaturityTenorArray,
					0.
				),
				syntheticFloatFloatMeasure,
				syntheticFloatFloatQuoteArray
			);
		}

		try {
			return ScenarioForwardCurveBuilder.ShapePreservingForwardCurve (
				new LinearLatentStateCalibrator (
					segmentCustomBuilderControl,
					BoundarySettings.NaturalStandard(),
					MultiSegmentSequence.CALIBRATE,
					null,
					null
				),
				new LatentStateStretchSpec[] {
					depositLatentStateStretchSpec,
					fraLatentStateStretchSpec,
					fixFloatLatentStateStretchSpec,
					floatFloatLatentStateStretchSpec,
					syntheticFloatFloatLatentStateStretchSpec
				},
				forwardLabel,
				ValuationParams.Spot (effectiveDate.julian()),
				null,
				MarketParamsBuilder.Create (
					mergedDiscountForwardCurve,
					referenceForwardCurve,
					null,
					null,
					null,
					null,
					null,
					null
				),
				null,
				0 == depositComponentCount ? fraQuoteArray[0] : depositQuoteArray[0]
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Instance of the Shape Preserving Forward Curve off of Exchange/OTC Market Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param forwardLabel Forward Label
	 * @param astrDepositMaturityTenor Array of Deposit Maturity Tenors
	 * @param adblDepositQuote Array of the Deposit Instrument Quotes
	 * @param strDepositMeasure The Deposit Instrument Calibration Measure
	 * @param astrFRAMaturityTenor Array of FRA Maturity Tenors
	 * @param adblFRAQuote Array of the FRA Instrument Quotes
	 * @param strFRAMeasure The FRA Instrument Calibration Measure
	 * @param astrFixFloatMaturityTenor Array of Fix-Float Maturity Tenors
	 * @param adblFixFloatQuote Array of the Fix-Float Quotes
	 * @param strFixFloatMeasure The Fix-Float Calibration Measure
	 * @param astrFloatFloatMaturityTenor Array of Float-Float Maturity Tenors
	 * @param adblFloatFloatQuote Array of the Float-Float Quotes
	 * @param strFloatFloatMeasure The Float-Float Calibration Measure
	 * @param astrSyntheticFloatFloatMaturityTenor Array of Synthetic Float-Float Maturity Tenors
	 * @param adblSyntheticFloatFloatQuote Array of the Synthetic Float-Float Quotes
	 * @param strSyntheticFloatFloatMeasure The Synthetic Float-Float Calibration Measure
	 * @param dc The Base Discount Curve
	 * @param fcReference The Reference Forward Curve
	 * 
	 * @return Instance of the Forward Curve
	 */

	public static final org.drip.state.forward.ForwardCurve ShapePreservingForwardCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final java.lang.String[] astrDepositMaturityTenor,
		final double[] adblDepositQuote,
		final java.lang.String strDepositMeasure,
		final java.lang.String[] astrFRAMaturityTenor,
		final double[] adblFRAQuote,
		final java.lang.String strFRAMeasure,
		final java.lang.String[] astrFixFloatMaturityTenor,
		final double[] adblFixFloatQuote,
		final java.lang.String strFixFloatMeasure,
		final java.lang.String[] astrFloatFloatMaturityTenor,
		final double[] adblFloatFloatQuote,
		final java.lang.String strFloatFloatMeasure,
		final java.lang.String[] astrSyntheticFloatFloatMaturityTenor,
		final double[] adblSyntheticFloatFloatQuote,
		final java.lang.String strSyntheticFloatFloatMeasure,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.forward.ForwardCurve fcReference)
	{
		try {
			return ForwardCurve (dtSpot, forwardLabel, astrDepositMaturityTenor, adblDepositQuote,
				strDepositMeasure, astrFRAMaturityTenor, adblFRAQuote, strFRAMeasure,
					astrFixFloatMaturityTenor, adblFixFloatQuote, strFixFloatMeasure,
						astrFloatFloatMaturityTenor, adblFloatFloatQuote, strFloatFloatMeasure,
							astrSyntheticFloatFloatMaturityTenor, adblSyntheticFloatFloatQuote,
								strSyntheticFloatFloatMeasure, dc, fcReference, new
									org.drip.spline.params.SegmentCustomBuilderControl
										(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new org.drip.spline.basis.PolynomialFunctionSetParams (2),
					org.drip.spline.params.SegmentInelasticDesignControl.Create (0, 2), new
						org.drip.spline.params.ResponseScalingShapeControl (true, new
							org.drip.function.r1tor1custom.QuadraticRationalShapeControl (0.)), null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Instance of Smooth Forward Curve off of Exchange/OTC Market Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param forwardLabel Forward Label
	 * @param astrDepositMaturityTenor Array of Deposit Maturity Tenors
	 * @param adblDepositQuote Array of the Deposit Instrument Quotes
	 * @param strDepositMeasure The Deposit Instrument Calibration Measure
	 * @param astrFRAMaturityTenor Array of FRA Maturity Tenors
	 * @param adblFRAQuote Array of the FRA Instrument Quotes
	 * @param strFRAMeasure The FRA Instrument Calibration Measure
	 * @param astrFixFloatMaturityTenor Array of Fix-Float Maturity Tenors
	 * @param adblFixFloatQuote Array of the Fix-Float Quotes
	 * @param strFixFloatMeasure The Fix-Float Calibration Measure
	 * @param astrFloatFloatMaturityTenor Array of Float-Float Maturity Tenors
	 * @param adblFloatFloatQuote Array of the Float-Float Quotes
	 * @param strFloatFloatMeasure The Float-Float Calibration Measure
	 * @param astrSyntheticFloatFloatMaturityTenor Array of Synthetic Float-Float Maturity Tenors
	 * @param adblSyntheticFloatFloatQuote Array of the Synthetic Float-Float Quotes
	 * @param strSyntheticFloatFloatMeasure The Synthetic Float-Float Calibration Measure
	 * @param dc The Base Discount Curve
	 * @param fcReference The Reference Forward Curve
	 * 
	 * @return Instance of the Forward Curve
	 */

	public static final org.drip.state.forward.ForwardCurve SmoothForwardCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final java.lang.String[] astrDepositMaturityTenor,
		final double[] adblDepositQuote,
		final java.lang.String strDepositMeasure,
		final java.lang.String[] astrFRAMaturityTenor,
		final double[] adblFRAQuote,
		final java.lang.String strFRAMeasure,
		final java.lang.String[] astrFixFloatMaturityTenor,
		final double[] adblFixFloatQuote,
		final java.lang.String strFixFloatMeasure,
		final java.lang.String[] astrFloatFloatMaturityTenor,
		final double[] adblFloatFloatQuote,
		final java.lang.String strFloatFloatMeasure,
		final java.lang.String[] astrSyntheticFloatFloatMaturityTenor,
		final double[] adblSyntheticFloatFloatQuote,
		final java.lang.String strSyntheticFloatFloatMeasure,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.forward.ForwardCurve fcReference)
	{
		try {
			return ForwardCurve (dtSpot, forwardLabel, astrDepositMaturityTenor, adblDepositQuote,
				strDepositMeasure, astrFRAMaturityTenor, adblFRAQuote, strFRAMeasure,
					astrFixFloatMaturityTenor, adblFixFloatQuote, strFixFloatMeasure,
						astrFloatFloatMaturityTenor, adblFloatFloatQuote, strFloatFloatMeasure,
							astrSyntheticFloatFloatMaturityTenor, adblSyntheticFloatFloatQuote,
								strSyntheticFloatFloatMeasure, dc, fcReference, new
									org.drip.spline.params.SegmentCustomBuilderControl
										(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new org.drip.spline.basis.PolynomialFunctionSetParams (4),
					org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), new
						org.drip.spline.params.ResponseScalingShapeControl (true, new
							org.drip.function.r1tor1custom.QuadraticRationalShapeControl (0.)), null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Instance of the Smooth/Shape Preserving Forward Curve off of Exchange/OTC Market
	 *  Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param forwardLabel Forward Label
	 * @param astrDepositMaturityTenor Array of Deposit Maturity Tenors
	 * @param adblDepositQuote Array of the Deposit Instrument Quotes
	 * @param strDepositMeasure The Deposit Instrument Calibration Measure
	 * @param astrFRAMaturityTenor Array of FRA Maturity Tenors
	 * @param adblFRAQuote Array of the FRA Instrument Quotes
	 * @param strFRAMeasure The FRA Instrument Calibration Measure
	 * @param astrFixFloatMaturityTenor Array of Fix-Float Maturity Tenors
	 * @param adblFixFloatQuote Array of the Fix-Float Quotes
	 * @param strFixFloatMeasure The Fix-Float Calibration Measure
	 * @param astrFloatFloatMaturityTenor Array of Float-Float Maturity Tenors
	 * @param adblFloatFloatQuote Array of the Float-Float Quotes
	 * @param strFloatFloatMeasure The Float-Float Calibration Measure
	 * @param astrSyntheticFloatFloatMaturityTenor Array of Synthetic Float-Float Maturity Tenors
	 * @param adblSyntheticFloatFloatQuote Array of the Synthetic Float-Float Quotes
	 * @param strSyntheticFloatFloatMeasure The Synthetic Float-Float Calibration Measure
	 * @param dc The Base Discount Curve
	 * @param fcReference The Reference Forward Curve
	 * @param iLatentStateType SHAPE_PRESERVING/SMOOTH
	 * 
	 * @return Instance of the Forward Curve
	 */

	public static final org.drip.state.forward.ForwardCurve ForwardCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final java.lang.String[] astrDepositMaturityTenor,
		final double[] adblDepositQuote,
		final java.lang.String strDepositMeasure,
		final java.lang.String[] astrFRAMaturityTenor,
		final double[] adblFRAQuote,
		final java.lang.String strFRAMeasure,
		final java.lang.String[] astrFixFloatMaturityTenor,
		final double[] adblFixFloatQuote,
		final java.lang.String strFixFloatMeasure,
		final java.lang.String[] astrFloatFloatMaturityTenor,
		final double[] adblFloatFloatQuote,
		final java.lang.String strFloatFloatMeasure,
		final java.lang.String[] astrSyntheticFloatFloatMaturityTenor,
		final double[] adblSyntheticFloatFloatQuote,
		final java.lang.String strSyntheticFloatFloatMeasure,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.forward.ForwardCurve fcReference,
		final int iLatentStateType)
	{
		if (SHAPE_PRESERVING == iLatentStateType)
			return ShapePreservingForwardCurve (dtSpot, forwardLabel, astrDepositMaturityTenor,
				adblDepositQuote, strDepositMeasure, astrFRAMaturityTenor, adblFRAQuote, strFRAMeasure,
					astrFixFloatMaturityTenor, adblFixFloatQuote, strFixFloatMeasure,
						astrFloatFloatMaturityTenor, adblFloatFloatQuote, strFloatFloatMeasure,
							astrSyntheticFloatFloatMaturityTenor, adblSyntheticFloatFloatQuote,
								strSyntheticFloatFloatMeasure, dc, fcReference);

		if (SMOOTH == iLatentStateType)
			return SmoothForwardCurve (dtSpot, forwardLabel, astrDepositMaturityTenor, adblDepositQuote,
				strDepositMeasure, astrFRAMaturityTenor, adblFRAQuote, strFRAMeasure,
					astrFixFloatMaturityTenor, adblFixFloatQuote, strFixFloatMeasure,
						astrFloatFloatMaturityTenor, adblFloatFloatQuote, strFloatFloatMeasure,
							astrSyntheticFloatFloatMaturityTenor, adblSyntheticFloatFloatQuote,
								strSyntheticFloatFloatMeasure, dc, fcReference);

		return null;
	}

	/**
	 * Construct an Overnight Curve from Overnight Exchange/OTC Market Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param strCurrency Currency
	 * @param astrDepositMaturityTenor Array of Deposit Maturity Tenors
	 * @param adblDepositQuote Array of Deposit Quotes
	 * @param strDepositMeasure Deposit Measure
	 * @param astrShortEndOISMaturityTenor Array of Short End OIS Maturity Tenors
	 * @param adblShortEndOISQuote Array of Short End OIS Quotes
	 * @param strShortEndOISMeasure Short End OIS Measure
	 * @param astrOISFuturesEffectiveTenor Array of OIS Futures Effective Tenors
	 * @param astrOISFuturesMaturityTenor Array of OIS Futures Maturity Tenors
	 * @param adblOISFuturesQuote Array of OIS Futures Quotes
	 * @param strOISFuturesMeasure OIS Futures Measure
	 * @param astrLongEndOISMaturityTenor Array of Long End OIS Maturity Tenors
	 * @param adblLongEndOISQuote Array of Long End OIS Quotes
	 * @param strLongEndOISMeasure Long End OIS Measure
	 * @param scbc Segment Custom Builder Control
	 * 
	 * @return Overnight Curve from Overnight OTC Instruments
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve OvernightCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final String strCurrency,
		final java.lang.String[] astrDepositMaturityTenor,
		final double[] adblDepositQuote,
		final java.lang.String strDepositMeasure,
		final java.lang.String[] astrShortEndOISMaturityTenor,
		final double[] adblShortEndOISQuote,
		final java.lang.String strShortEndOISMeasure,
		final java.lang.String[] astrOISFuturesEffectiveTenor,
		final java.lang.String[] astrOISFuturesMaturityTenor,
		final double[] adblOISFuturesQuote,
		final java.lang.String strOISFuturesMeasure,
		final java.lang.String[] astrLongEndOISMaturityTenor,
		final double[] adblLongEndOISQuote,
		final java.lang.String strLongEndOISMeasure,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc)
	{
		if (null == dtSpot) return null;

		org.drip.state.inference.LatentStateStretchSpec lsssDeposit = null;
		org.drip.state.inference.LatentStateStretchSpec lsssOISFutures = null;
		org.drip.state.inference.LatentStateStretchSpec lsssLongEndOIS = null;
		org.drip.state.inference.LatentStateStretchSpec lsssShortEndOIS = null;
		int iNumDepositQuote = null == adblDepositQuote ? 0 : adblDepositQuote.length;
		int iNumOISFuturesQuote = null == adblOISFuturesQuote ? 0 : adblOISFuturesQuote.length;
		int iNumLongEndOISQuote = null == adblLongEndOISQuote ? 0 : adblLongEndOISQuote.length;
		int iNumShortEndOISQuote = null == adblShortEndOISQuote ? 0 : adblShortEndOISQuote.length;
		int iNumDepositComp = null == astrDepositMaturityTenor ? 0 : astrDepositMaturityTenor.length;
		int iNumOISFuturesComp = null == astrOISFuturesMaturityTenor ? 0 :
			astrOISFuturesMaturityTenor.length;
		int iNumOISFuturesComp2 = null == astrOISFuturesEffectiveTenor ? 0 :
			astrOISFuturesEffectiveTenor.length;
		int iNumLongEndOISComp = null == astrLongEndOISMaturityTenor ? 0 :
			astrLongEndOISMaturityTenor.length;
		int iNumShortEndOISComp = null == astrShortEndOISMaturityTenor ? 0 :
			astrShortEndOISMaturityTenor.length;

		if (iNumDepositQuote != iNumDepositComp || iNumShortEndOISQuote != iNumShortEndOISComp ||
			iNumOISFuturesQuote != iNumOISFuturesComp || iNumOISFuturesComp2 != iNumOISFuturesComp ||
				iNumLongEndOISQuote != iNumLongEndOISComp)
			return null;

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (0, strCurrency);

		if (0 != iNumDepositComp)
			lsssDeposit = org.drip.state.estimator.LatentStateStretchBuilder.ForwardFundingStretchSpec
				("DEPOSIT", org.drip.service.template.OTCInstrumentBuilder.OvernightDeposit (dtEffective,
					strCurrency, astrDepositMaturityTenor), strDepositMeasure, adblDepositQuote);

		if (0 != iNumShortEndOISComp)
			lsssShortEndOIS = org.drip.state.estimator.LatentStateStretchBuilder.ForwardFundingStretchSpec
				("SHORTENDOIS", org.drip.service.template.OTCInstrumentBuilder.OISFixFloat (dtEffective,
					strCurrency, astrShortEndOISMaturityTenor, adblShortEndOISQuote, false),
						strShortEndOISMeasure, adblShortEndOISQuote);

		if (0 != iNumOISFuturesComp)
			lsssOISFutures = org.drip.state.estimator.LatentStateStretchBuilder.ForwardFundingStretchSpec
				("OISFUTURES", org.drip.service.template.OTCInstrumentBuilder.OISFixFloatFutures
					(dtEffective, strCurrency, astrOISFuturesEffectiveTenor, astrOISFuturesMaturityTenor,
						adblOISFuturesQuote, false), strOISFuturesMeasure, adblOISFuturesQuote);

		if (0 != iNumLongEndOISComp)
			lsssLongEndOIS = org.drip.state.estimator.LatentStateStretchBuilder.ForwardFundingStretchSpec
				("LONGENDOIS", org.drip.service.template.OTCInstrumentBuilder.OISFixFloat (dtEffective,
					strCurrency, astrLongEndOISMaturityTenor, adblLongEndOISQuote, false),
						strLongEndOISMeasure, adblLongEndOISQuote);

		try {
			org.drip.state.inference.LinearLatentStateCalibrator lcc = new
				org.drip.state.inference.LinearLatentStateCalibrator (scbc,
					org.drip.spline.stretch.BoundarySettings.NaturalStandard(),
						org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE, null, null);

			return org.drip.state.creator.ScenarioDiscountCurveBuilder.ShapePreservingDFBuild (strCurrency,
				lcc, new org.drip.state.inference.LatentStateStretchSpec[] {lsssDeposit, lsssShortEndOIS,
					lsssOISFutures, lsssLongEndOIS}, org.drip.param.valuation.ValuationParams.Spot
						(dtEffective.julian()), null, null, null, 1.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Shape Preserving Overnight Curve from Overnight Exchange/OTC Market Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param strCurrency Currency
	 * @param astrDepositMaturityTenor Array of Deposit Maturity Tenors
	 * @param adblDepositQuote Array of Deposit Quotes
	 * @param strDepositMeasure Deposit Measure
	 * @param astrShortEndOISMaturityTenor Array of Short End OIS Maturity Tenors
	 * @param adblShortEndOISQuote Array of Short End OIS Quotes
	 * @param strShortEndOISMeasure Short End OIS Measure
	 * @param astrOISFuturesEffectiveTenor Array of OIS Futures Effective Tenors
	 * @param astrOISFuturesMaturityTenor Array of OIS Futures Maturity Tenors
	 * @param adblOISFuturesQuote Array of OIS Futures Quotes
	 * @param strOISFuturesMeasure OIS Futures Measure
	 * @param astrLongEndOISMaturityTenor Array of Long End OIS Maturity Tenors
	 * @param adblLongEndOISQuote Array of Long End OIS Quotes
	 * @param strLongEndOISMeasure Long End OIS Measure
	 * 
	 * @return Overnight Curve from Overnight OTC Instruments
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve ShapePreservingOvernightCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final String strCurrency,
		final java.lang.String[] astrDepositMaturityTenor,
		final double[] adblDepositQuote,
		final java.lang.String strDepositMeasure,
		final java.lang.String[] astrShortEndOISMaturityTenor,
		final double[] adblShortEndOISQuote,
		final java.lang.String strShortEndOISMeasure,
		final java.lang.String[] astrOISFuturesEffectiveTenor,
		final java.lang.String[] astrOISFuturesMaturityTenor,
		final double[] adblOISFuturesQuote,
		final java.lang.String strOISFuturesMeasure,
		final java.lang.String[] astrLongEndOISMaturityTenor,
		final double[] adblLongEndOISQuote,
		final java.lang.String strLongEndOISMeasure)
	{
		try {
			return OvernightCurve (dtSpot, strCurrency, astrDepositMaturityTenor, adblDepositQuote,
				strDepositMeasure, astrShortEndOISMaturityTenor, adblShortEndOISQuote, strShortEndOISMeasure,
					astrOISFuturesEffectiveTenor, astrOISFuturesMaturityTenor, adblOISFuturesQuote,
						strOISFuturesMeasure, astrLongEndOISMaturityTenor, adblLongEndOISQuote,
							strLongEndOISMeasure, new org.drip.spline.params.SegmentCustomBuilderControl
								(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
									new org.drip.spline.basis.PolynomialFunctionSetParams (2),
										org.drip.spline.params.SegmentInelasticDesignControl.Create (0, 2),
											new org.drip.spline.params.ResponseScalingShapeControl (true, new
												org.drip.function.r1tor1custom.QuadraticRationalShapeControl (0.)),
													null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Smooth Overnight Curve from Overnight Exchange/OTC Market Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param strCurrency Currency
	 * @param astrDepositMaturityTenor Array of Deposit Maturity Tenors
	 * @param adblDepositQuote Array of Deposit Quotes
	 * @param strDepositMeasure Deposit Measure
	 * @param astrShortEndOISMaturityTenor Array of Short End OIS Maturity Tenors
	 * @param adblShortEndOISQuote Array of Short End OIS Quotes
	 * @param strShortEndOISMeasure Short End OIS Measure
	 * @param astrOISFuturesEffectiveTenor Array of OIS Futures Effective Tenors
	 * @param astrOISFuturesMaturityTenor Array of OIS Futures Maturity Tenors
	 * @param adblOISFuturesQuote Array of OIS Futures Quotes
	 * @param strOISFuturesMeasure OIS Futures Measure
	 * @param astrLongEndOISMaturityTenor Array of Long End OIS Maturity Tenors
	 * @param adblLongEndOISQuote Array of Long End OIS Quotes
	 * @param strLongEndOISMeasure Long End OIS Measure
	 * 
	 * @return Overnight Curve from Overnight OTC Instruments
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve SmoothOvernightCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final String strCurrency,
		final java.lang.String[] astrDepositMaturityTenor,
		final double[] adblDepositQuote,
		final java.lang.String strDepositMeasure,
		final java.lang.String[] astrShortEndOISMaturityTenor,
		final double[] adblShortEndOISQuote,
		final java.lang.String strShortEndOISMeasure,
		final java.lang.String[] astrOISFuturesEffectiveTenor,
		final java.lang.String[] astrOISFuturesMaturityTenor,
		final double[] adblOISFuturesQuote,
		final java.lang.String strOISFuturesMeasure,
		final java.lang.String[] astrLongEndOISMaturityTenor,
		final double[] adblLongEndOISQuote,
		final java.lang.String strLongEndOISMeasure)
	{
		try {
			return OvernightCurve (dtSpot, strCurrency, astrDepositMaturityTenor, adblDepositQuote,
				strDepositMeasure, astrShortEndOISMaturityTenor, adblShortEndOISQuote, strShortEndOISMeasure,
					astrOISFuturesEffectiveTenor, astrOISFuturesMaturityTenor, adblOISFuturesQuote,
						strOISFuturesMeasure, astrLongEndOISMaturityTenor, adblLongEndOISQuote,
							strLongEndOISMeasure, new org.drip.spline.params.SegmentCustomBuilderControl
								(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
									new org.drip.spline.basis.PolynomialFunctionSetParams (4),
										org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2),
											new org.drip.spline.params.ResponseScalingShapeControl (true, new
												org.drip.function.r1tor1custom.QuadraticRationalShapeControl (0.)),
													null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Overnight Curve from Overnight Exchange/OTC Market Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param strCurrency Currency
	 * @param astrDepositMaturityTenor Array of Deposit Maturity Tenors
	 * @param adblDepositQuote Array of Deposit Quotes
	 * @param strDepositMeasure Deposit Measure
	 * @param astrShortEndOISMaturityTenor Array of Short End OIS Maturity Tenors
	 * @param adblShortEndOISQuote Array of Short End OIS Quotes
	 * @param strShortEndOISMeasure Short End OIS Measure
	 * @param astrOISFuturesEffectiveTenor Array of OIS Futures Effective Tenors
	 * @param astrOISFuturesMaturityTenor Array of OIS Futures Maturity Tenors
	 * @param adblOISFuturesQuote Array of OIS Futures Quotes
	 * @param strOISFuturesMeasure OIS Futures Measure
	 * @param astrLongEndOISMaturityTenor Array of Long End OIS Maturity Tenors
	 * @param adblLongEndOISQuote Array of Long End OIS Quotes
	 * @param strLongEndOISMeasure Long End OIS Measure
	 * @param iLatentStateType SHAPE PRESERVING/SMOOTH
	 * 
	 * @return Overnight Curve from Overnight OTC Instruments
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve OvernightCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final String strCurrency,
		final java.lang.String[] astrDepositMaturityTenor,
		final double[] adblDepositQuote,
		final java.lang.String strDepositMeasure,
		final java.lang.String[] astrShortEndOISMaturityTenor,
		final double[] adblShortEndOISQuote,
		final java.lang.String strShortEndOISMeasure,
		final java.lang.String[] astrOISFuturesEffectiveTenor,
		final java.lang.String[] astrOISFuturesMaturityTenor,
		final double[] adblOISFuturesQuote,
		final java.lang.String strOISFuturesMeasure,
		final java.lang.String[] astrLongEndOISMaturityTenor,
		final double[] adblLongEndOISQuote,
		final java.lang.String strLongEndOISMeasure,
		final int iLatentStateType)
	{
		if (SHAPE_PRESERVING == iLatentStateType)
			return ShapePreservingOvernightCurve (dtSpot, strCurrency, astrDepositMaturityTenor,
				adblDepositQuote, strDepositMeasure, astrShortEndOISMaturityTenor, adblShortEndOISQuote,
					strShortEndOISMeasure, astrOISFuturesEffectiveTenor, astrOISFuturesMaturityTenor,
						adblOISFuturesQuote, strOISFuturesMeasure, astrLongEndOISMaturityTenor,
							adblLongEndOISQuote, strLongEndOISMeasure);

		if (SMOOTH == iLatentStateType)
			return SmoothOvernightCurve (dtSpot, strCurrency, astrDepositMaturityTenor, adblDepositQuote,
				strDepositMeasure, astrShortEndOISMaturityTenor, adblShortEndOISQuote, strShortEndOISMeasure,
					astrOISFuturesEffectiveTenor, astrOISFuturesMaturityTenor, adblOISFuturesQuote,
						strOISFuturesMeasure, astrLongEndOISMaturityTenor, adblLongEndOISQuote,
							strLongEndOISMeasure);

		return null;
	}

	/**
	 * Construct a Credit Curve from Overnight Exchange/OTC Market Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param strCredit Credit Curve
	 * @param astrMaturityTenor Maturity Tenor
	 * @param adblCoupon Coupon Array
	 * @param adblQuote Array of Market Quotes
	 * @param strMeasure Calibration Measure
	 * @param dc Discount Curve
	 * 
	 * @return The Credit Curve Instance
	 */

	public static final org.drip.state.credit.CreditCurve CreditCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCredit,
		final java.lang.String[] astrMaturityTenor,
		final double[] adblCoupon,
		final double[] adblQuote,
		final java.lang.String strMeasure,
		final org.drip.state.discount.MergedDiscountForwardCurve dc)
	{
		if (null == dtSpot || null == dc) return null;

		java.lang.String strCurrency = dc.currency();

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (0, strCurrency);

		org.drip.product.definition.CreditDefaultSwap[] aCDS =
			org.drip.service.template.OTCInstrumentBuilder.CDS (dtEffective, astrMaturityTenor, adblCoupon,
				strCurrency, strCredit);

		if (null == aCDS) return null;

		int iNumCDS = aCDS.length;
		java.lang.String[] astrMeasure = new java.lang.String[iNumCDS];

		if (0 == iNumCDS) return null;

		for (int i = 0; i < iNumCDS; ++i)
			astrMeasure[i] = strMeasure;

		return org.drip.state.creator.ScenarioCreditCurveBuilder.Custom (strCredit, dtEffective, aCDS, dc,
			adblQuote, astrMeasure, "CAD".equalsIgnoreCase (strCurrency) || "EUR".equalsIgnoreCase
				(strCurrency) || "GBP".equalsIgnoreCase (strCurrency) || "HKD".equalsIgnoreCase (strCurrency)
					|| "USD".equalsIgnoreCase (strCurrency) ? 0.40 : 0.25, "QuotedSpread".equals
						(strMeasure));
	}

	/**
	 * Construct a Credit Curve from the specified Calibration CDS Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param aCDS Array of the Calibration CDS Instruments
	 * @param adblQuote Array of Market Quotes
	 * @param strMeasure Calibration Measure
	 * @param dc Discount Curve
	 * 
	 * @return The Credit Curve Instance
	 */

	public static final org.drip.state.credit.CreditCurve CreditCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.product.definition.CreditDefaultSwap[] aCDS,
		final double[] adblQuote,
		final java.lang.String strMeasure,
		final org.drip.state.discount.MergedDiscountForwardCurve dc)
	{
		if (null == dtSpot || null == dc) return null;

		java.lang.String strCurrency = dc.currency();

		if (null == aCDS) return null;

		int iNumCDS = aCDS.length;
		java.lang.String[] astrMeasure = new java.lang.String[iNumCDS];

		if (0 == iNumCDS) return null;

		for (int i = 0; i < iNumCDS; ++i)
			astrMeasure[i] = strMeasure;

		return org.drip.state.creator.ScenarioCreditCurveBuilder.Custom
			(aCDS[0].creditLabel().referenceEntity(), dtSpot, aCDS, dc, adblQuote, astrMeasure,
				"CAD".equalsIgnoreCase (strCurrency) || "EUR".equalsIgnoreCase (strCurrency) ||
					"GBP".equalsIgnoreCase (strCurrency) || "HKD".equalsIgnoreCase (strCurrency) ||
						"USD".equalsIgnoreCase (strCurrency) ? 0.40 : 0.25, "QuotedSpread".equals
							(strMeasure));
	}

	/**
	 * Construct a Govvie Curve from the Treasury Instruments
	 * 
	 * @param strCode Treasury Code
	 * @param dtSpot Spot Date
	 * @param adtEffective Array of Effective Dates
	 * @param adtMaturity Array of Maturity Dates
	 * @param adblCoupon Array of Coupons
	 * @param adblQuote Array of Market Quotes
	 * @param strMeasure Calibration Measure
	 * @param scbc Segment Custom Builder Control Parameters
	 * 
	 * @return The Govvie Curve Instance
	 */

	public static final org.drip.state.govvie.GovvieCurve GovvieCurve (
		final java.lang.String strCode,
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.analytics.date.JulianDate[] adtEffective,
		final org.drip.analytics.date.JulianDate[] adtMaturity,
		final double[] adblCoupon,
		final double[] adblQuote,
		final java.lang.String strMeasure,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc)
	{
		org.drip.product.credit.BondComponent[] aTreasury =
			org.drip.service.template.TreasuryBuilder.FromCode (strCode, adtEffective, adtMaturity,
				adblCoupon);

		if (null == aTreasury) return null;

		int iNumTreasury = aTreasury.length;
		int[] aiDate = new int[iNumTreasury];

		if (0 == iNumTreasury || adblQuote.length != iNumTreasury) return null;

		for (int i = 0; i < iNumTreasury; ++i)
			aiDate[i] = adtMaturity[i].julian();

		java.lang.String strCurrency = aTreasury[0].currency();

		java.lang.String strBenchmarkTreasuryCode =
			org.drip.market.issue.TreasurySettingContainer.CurrencyBenchmarkCode (strCurrency);

		return null == strBenchmarkTreasuryCode || strBenchmarkTreasuryCode.isEmpty() ? null :
			org.drip.state.creator.ScenarioGovvieCurveBuilder.CustomSplineCurve (strBenchmarkTreasuryCode,
				dtSpot, strBenchmarkTreasuryCode, strCurrency, aiDate, adblQuote, scbc);
	}

	/**
	 * Construct a Shape Preserving Govvie Curve from the Treasury Instruments
	 * 
	 * @param strCode Treasury Code
	 * @param dtSpot Spot Date
	 * @param adtEffective Array of Effective Dates
	 * @param adtMaturity Array of Maturity Dates
	 * @param adblCoupon Array of Coupons
	 * @param adblQuote Array of Market Quotes
	 * @param strMeasure Calibration Measure
	 * 
	 * @return The Govvie Curve Instance
	 */

	public static final org.drip.state.govvie.GovvieCurve ShapePreservingGovvieCurve (
		final java.lang.String strCode,
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.analytics.date.JulianDate[] adtEffective,
		final org.drip.analytics.date.JulianDate[] adtMaturity,
		final double[] adblCoupon,
		final double[] adblQuote,
		final java.lang.String strMeasure)
	{
		try {
			return GovvieCurve (strCode, dtSpot, adtEffective, adtMaturity, adblCoupon, adblQuote,
				strMeasure, new org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
						org.drip.spline.basis.PolynomialFunctionSetParams (2),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (0, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Smooth Govvie Curve from the Treasury Instruments
	 * 
	 * @param strCode Treasury Code
	 * @param dtSpot Spot Date
	 * @param adtEffective Array of Effective Dates
	 * @param adtMaturity Array of Maturity Dates
	 * @param adblCoupon Array of Coupons
	 * @param adblQuote Array of Market Quotes
	 * @param strMeasure Calibration Measure
	 * 
	 * @return The Govvie Curve Instance
	 */

	public static final org.drip.state.govvie.GovvieCurve SmoothGovvieCurve (
		final java.lang.String strCode,
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.analytics.date.JulianDate[] adtEffective,
		final org.drip.analytics.date.JulianDate[] adtMaturity,
		final double[] adblCoupon,
		final double[] adblQuote,
		final java.lang.String strMeasure)
	{
		try {
			return GovvieCurve (strCode, dtSpot, adtEffective, adtMaturity, adblCoupon, adblQuote,
				strMeasure, new org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
						org.drip.spline.basis.PolynomialFunctionSetParams (4),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Govvie Curve from the Treasury Instruments
	 * 
	 * @param strCode Treasury Code
	 * @param dtSpot Spot Date
	 * @param adtEffective Array of Effective Dates
	 * @param adtMaturity Array of Maturity Dates
	 * @param adblCoupon Array of Coupons
	 * @param adblQuote Array of Market Quotes
	 * @param strMeasure Calibration Measure
	 * @param iLatentStateType SHAPE PRESERVING/SMOOTH
	 * 
	 * @return The Govvie Curve Instance
	 */

	public static final org.drip.state.govvie.GovvieCurve GovvieCurve (
		final java.lang.String strCode,
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.analytics.date.JulianDate[] adtEffective,
		final org.drip.analytics.date.JulianDate[] adtMaturity,
		final double[] adblCoupon,
		final double[] adblQuote,
		final java.lang.String strMeasure,
		final int iLatentStateType)
	{
		if (SHAPE_PRESERVING == iLatentStateType)
			return ShapePreservingGovvieCurve (strCode, dtSpot, adtEffective, adtMaturity, adblCoupon,
				adblQuote, strMeasure);

		if (SMOOTH == iLatentStateType)
			return SmoothGovvieCurve (strCode, dtSpot, adtEffective, adtMaturity, adblCoupon, adblQuote,
				strMeasure);

		return null;
	}

	/**
	 * Construct an FX Curve from the FX Forward Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param cp The FX Currency Pair
	 * @param astrMaturityTenor Array of Maturity Tenors
	 * @param adblQuote Array of FX Forwards
	 * @param strMeasure Calibration Measure
	 * @param dblFXSpot FX Spot
	 * @param scbc Segment Custom Builder Builder Parameters
	 * 
	 * @return The FX Curve Instance
	 */

	public static final org.drip.state.fx.FXCurve FXCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.product.params.CurrencyPair cp,
		final java.lang.String[] astrMaturityTenor,
		final double[] adblQuote,
		final java.lang.String strMeasure,
		final double dblFXSpot,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc)
	{
		if (null == dtSpot || null == cp) return null;

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (0, cp.denomCcy());

		org.drip.product.fx.FXForwardComponent[] aFXFC =
			org.drip.service.template.OTCInstrumentBuilder.FXForward (dtEffective, cp, astrMaturityTenor);

		if (null == aFXFC) return null;

		int iNumFXFC = aFXFC.length;

		if (0 == iNumFXFC || adblQuote.length != iNumFXFC) return null;

		return org.drip.state.creator.ScenarioFXCurveBuilder.ShapePreservingFXCurve ( cp.code(), cp,
			org.drip.param.valuation.ValuationParams.Spot (dtEffective.julian()), null, null, null, aFXFC,
				strMeasure, adblQuote, dblFXSpot, scbc);
	}

	/**
	 * Construct a Shape Preserving FX Curve from the FX Forward Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param cp The FX Currency Pair
	 * @param astrMaturityTenor Array of Maturity Tenors
	 * @param adblQuote Array of FX Forwards
	 * @param strMeasure Calibration Measure
	 * @param dblFXSpot FX Spot
	 * 
	 * @return The FX Curve Instance
	 */

	public static final org.drip.state.fx.FXCurve ShapePreservingFXCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.product.params.CurrencyPair cp,
		final java.lang.String[] astrMaturityTenor,
		final double[] adblQuote,
		final java.lang.String strMeasure,
		final double dblFXSpot)
	{
		try {
			return FXCurve (dtSpot, cp, astrMaturityTenor, adblQuote, strMeasure, dblFXSpot, new
				org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
						org.drip.spline.basis.PolynomialFunctionSetParams (2),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (0, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Smooth FX Curve from the FX Forward Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param cp The FX Currency Pair
	 * @param astrMaturityTenor Array of Maturity Tenors
	 * @param adblQuote Array of FX Forwards
	 * @param strMeasure Calibration Measure
	 * @param dblFXSpot FX Spot
	 * 
	 * @return The FX Curve Instance
	 */

	public static final org.drip.state.fx.FXCurve SmoothFXCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.product.params.CurrencyPair cp,
		final java.lang.String[] astrMaturityTenor,
		final double[] adblQuote,
		final java.lang.String strMeasure,
		final double dblFXSpot)
	{
		try {
			return FXCurve (dtSpot, cp, astrMaturityTenor, adblQuote, strMeasure, dblFXSpot, new
				org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
						org.drip.spline.basis.PolynomialFunctionSetParams (4),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an FX Curve from the FX Forward Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param cp The FX Currency Pair
	 * @param astrMaturityTenor Array of Maturity Tenors
	 * @param adblQuote Array of FX Forwards
	 * @param strMeasure Calibration Measure
	 * @param dblFXSpot FX Spot
	 * @param iLatentStateType SHAPE PRESERVING/SMOOTH
	 * 
	 * @return The FX Curve Instance
	 */

	public static final org.drip.state.fx.FXCurve FXCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.product.params.CurrencyPair cp,
		final java.lang.String[] astrMaturityTenor,
		final double[] adblQuote,
		final java.lang.String strMeasure,
		final double dblFXSpot,
		final int iLatentStateType)
	{
		if (SHAPE_PRESERVING == iLatentStateType)
			return ShapePreservingFXCurve (dtSpot, cp, astrMaturityTenor, adblQuote, strMeasure, dblFXSpot);

		if (SMOOTH == iLatentStateType)
			return SmoothFXCurve (dtSpot, cp, astrMaturityTenor, adblQuote, strMeasure, dblFXSpot);

		return null;
	}

	/**
	 * Forward Rate Volatility Latent State Construction from Cap/Floor Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param forwardLabel Forward Label
	 * @param bIsCap TRUE - Create and Use Array of Caps
	 * @param astrMaturityTenor Array of Cap/floor Maturities
	 * @param adblStrike Array of Cap/Floor Strikes
	 * @param adblQuote Array of Cap/Floor Quotes
	 * @param strMeasure Calibration Measure
	 * @param dc Discount Curve Instance
	 * @param fc Forward Curve Instance
	 * 
	 * @return Instance of the Forward Rate Volatility Curve
	 */

	public static final org.drip.state.volatility.VolatilityCurve ForwardRateVolatilityCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final boolean bIsCap,
		final java.lang.String[] astrMaturityTenor,
		final double[] adblStrike,
		final double[] adblQuote,
		final java.lang.String strMeasure,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.forward.ForwardCurve fc)
	{
		if (null == dtSpot || null == astrMaturityTenor || null == dc) return null;

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (0, dc.currency());

		int iNumComp = astrMaturityTenor.length;
		java.lang.String[] astrCalibMeasure = new java.lang.String[iNumComp];

		if (0 == iNumComp) return null;

		for (int i = 0; i < iNumComp; ++i)
			astrCalibMeasure[i] = strMeasure;

		return org.drip.state.creator.ScenarioLocalVolatilityBuilder.NonlinearBuild
			(forwardLabel.fullyQualifiedName() + "::VOL", dtEffective, forwardLabel,
				org.drip.service.template.OTCInstrumentBuilder.CapFloor (dtEffective, forwardLabel,
					astrMaturityTenor, adblStrike, bIsCap), adblQuote, astrCalibMeasure, dc, fc, null);
	}

	/**
	 * Construct a Map of Tenor Bumped Funding Curve Based off of the Input Exchange/OTC Market Instruments
	 * 
	 * @param dtSpot The Spot Date
	 * @param strCurrency Currency
	 * @param astrDepositMaturityTenor Array of Deposit Maturity Tenors
	 * @param adblDepositQuote Array of Deposit Quotes
	 * @param strDepositMeasure Deposit Calibration Measure
	 * @param adblFuturesQuote Array of Futures Quotes
	 * @param strFuturesMeasure Futures Calibration Measure
	 * @param astrFixFloatMaturityTenor Array of Fix Float Swap Maturity Tenors
	 * @param adblFixFloatQuote Array of Fix Float Swap Quotes
	 * @param strFixFloatMeasure Fix Float Calibration Measure
	 * @param iLatentStateType SHAPE_PRESERVING/SMOOTH
	 * @param dblBump The Tenor Node Bump Amount
	 * @param bIsProportional TRUE - The Bump Applied is Proportional
	 * 
	 * @return The Tenor Bumped Funding Curve Map
	 */

	public static final
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
			BumpedFundingCurve (
				final org.drip.analytics.date.JulianDate dtSpot,
				final java.lang.String strCurrency,
				final java.lang.String[] astrDepositMaturityTenor,
				final double[] adblDepositQuote,
				final java.lang.String strDepositMeasure,
				final double[] adblFuturesQuote,
				final java.lang.String strFuturesMeasure,
				final java.lang.String[] astrFixFloatMaturityTenor,
				final double[] adblFixFloatQuote,
				final java.lang.String strFixFloatMeasure,
				final int iLatentStateType,
				final double dblBump,
				final boolean bIsProportional)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblBump)) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
			mapBumpedCurve = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>();

		try {
			org.drip.param.definition.ManifestMeasureTweak mmtFLAT = new
				org.drip.param.definition.ManifestMeasureTweak
					(org.drip.param.definition.ManifestMeasureTweak.FLAT, bIsProportional, dblBump);

			if (null != adblDepositQuote) {
				int iNumDeposit = adblDepositQuote.length;

				for (int i = 0; i < iNumDeposit; ++i) {
					org.drip.state.discount.MergedDiscountForwardCurve dcDepositQuoteBumped = FundingCurve
						(dtSpot, strCurrency, astrDepositMaturityTenor,
							org.drip.analytics.support.Helper.TweakManifestMeasure (adblDepositQuote, new
								org.drip.param.definition.ManifestMeasureTweak (i, bIsProportional,
									dblBump)), strDepositMeasure, adblFuturesQuote, strFuturesMeasure,
										astrFixFloatMaturityTenor, adblFixFloatQuote, strFixFloatMeasure,
											iLatentStateType);

					if (null != dcDepositQuoteBumped)
						mapBumpedCurve.put ("DEPOSIT::" + astrDepositMaturityTenor[i],
							dcDepositQuoteBumped);
				}
			}

			double[] adblDepositParallelBump = org.drip.analytics.support.Helper.TweakManifestMeasure
				(adblDepositQuote, mmtFLAT);

			org.drip.state.discount.MergedDiscountForwardCurve dcDepositQuoteBumped = FundingCurve (dtSpot,
				strCurrency, astrDepositMaturityTenor, adblDepositParallelBump, strDepositMeasure,
					adblFuturesQuote, strFuturesMeasure, astrFixFloatMaturityTenor, adblFixFloatQuote,
						strFixFloatMeasure, iLatentStateType);

			if (null != dcDepositQuoteBumped) mapBumpedCurve.put ("DEPOSIT::PLL", dcDepositQuoteBumped);

			if (null != adblFuturesQuote) {
				int iNumFutures = adblFuturesQuote.length;

			for (int i = 0; i < iNumFutures; ++i) {
				org.drip.state.discount.MergedDiscountForwardCurve dcFuturesQuoteBumped = FundingCurve
					(dtSpot, strCurrency, astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure,
						org.drip.analytics.support.Helper.TweakManifestMeasure (adblFuturesQuote, new
							org.drip.param.definition.ManifestMeasureTweak (i, bIsProportional, dblBump)),
								strFuturesMeasure, astrFixFloatMaturityTenor, adblFixFloatQuote,
									strFixFloatMeasure, iLatentStateType);

				if (null != dcFuturesQuoteBumped) mapBumpedCurve.put ("FUTURES::" + i, dcFuturesQuoteBumped);
				}
			}

			double[] adblFuturesParallelBump = org.drip.analytics.support.Helper.TweakManifestMeasure
				(adblFuturesQuote, mmtFLAT);

			org.drip.state.discount.MergedDiscountForwardCurve dcFuturesQuoteBumped = FundingCurve (dtSpot,
				strCurrency, astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure,
					adblFuturesParallelBump, strFuturesMeasure, astrFixFloatMaturityTenor, adblFixFloatQuote,
						strFixFloatMeasure, iLatentStateType);

			if (null != dcFuturesQuoteBumped) mapBumpedCurve.put ("FUTURES::P", dcFuturesQuoteBumped);

			if (null != adblFixFloatQuote) {
				int iNumFixFloat = adblFixFloatQuote.length;

				for (int i = 0; i < iNumFixFloat; ++i) {
					org.drip.state.discount.MergedDiscountForwardCurve dcFixFloatQuoteBumped = FundingCurve
						(dtSpot, strCurrency, astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure,
							adblFuturesQuote, strFuturesMeasure, astrFixFloatMaturityTenor,
								org.drip.analytics.support.Helper.TweakManifestMeasure (adblFixFloatQuote,
									new org.drip.param.definition.ManifestMeasureTweak (i, bIsProportional,
										dblBump)), strFixFloatMeasure, iLatentStateType);

					if (null != dcFixFloatQuoteBumped)
						mapBumpedCurve.put ("FIXFLOAT::" + astrFixFloatMaturityTenor[i],
							dcFixFloatQuoteBumped);
				}

				double[] adblFixFloatParallelBump = org.drip.analytics.support.Helper.TweakManifestMeasure
					(adblFixFloatQuote, mmtFLAT);

				org.drip.state.discount.MergedDiscountForwardCurve dcFixFloatQuoteBumped = FundingCurve
					(dtSpot, strCurrency, astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure,
						adblFuturesQuote, strFuturesMeasure, astrFixFloatMaturityTenor,
							adblFixFloatParallelBump, strFixFloatMeasure, iLatentStateType);

				if (null != dcFixFloatQuoteBumped)
					mapBumpedCurve.put ("FIXFLOAT::PLL", dcFixFloatQuoteBumped);

				org.drip.state.discount.MergedDiscountForwardCurve dcFundingBase = FundingCurve (dtSpot,
					strCurrency, astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure,
						adblFuturesQuote, strFuturesMeasure, astrFixFloatMaturityTenor, adblFixFloatQuote,
							strFixFloatMeasure, iLatentStateType);

				if (null != dcFundingBase) mapBumpedCurve.put ("BASE", dcFundingBase);

				org.drip.state.discount.MergedDiscountForwardCurve dcFundingBumped = FundingCurve (dtSpot,
					strCurrency, astrDepositMaturityTenor, adblDepositParallelBump, strDepositMeasure,
						adblFuturesParallelBump, strFuturesMeasure, astrFixFloatMaturityTenor,
							adblFixFloatParallelBump, strFixFloatMeasure, iLatentStateType);

				if (null != dcFundingBumped) mapBumpedCurve.put ("BUMP", dcFundingBumped);
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return mapBumpedCurve;
	}

	/**
	 * Construct a Map of Tenor Bumped Funding Curve Based off of the Underlying Forward Curve Shift
	 * 
	 * @param dtSpot The Spot Date
	 * @param strCurrency Currency
	 * @param astrDepositMaturityTenor Array of Deposit Maturity Tenors
	 * @param adblDepositQuote Array of Deposit Quotes
	 * @param strDepositMeasure Deposit Calibration Measure
	 * @param adblFuturesQuote Array of Futures Quotes
	 * @param strFuturesMeasure Futures Calibration Measure
	 * @param astrFixFloatMaturityTenor Array of Fix Float Swap Maturity Tenors
	 * @param adblFixFloatQuote Array of Fix Float Swap Quotes
	 * @param strFixFloatMeasure Fix Float Calibration Measure
	 * @param iLatentStateType SHAPE_PRESERVING/SMOOTH
	 * @param dblBump The Tenor Node Bump Amount
	 * @param bIsProportional TRUE - The Bump Applied is Proportional
	 * 
	 * @return The Tenor Bumped Funding Curve Map
	 */

	public static final
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
			BumpedForwardFundingCurve (
				final org.drip.analytics.date.JulianDate dtSpot,
				final java.lang.String strCurrency,
				final java.lang.String[] astrDepositMaturityTenor,
				final double[] adblDepositQuote,
				final java.lang.String strDepositMeasure,
				final double[] adblFuturesQuote,
				final java.lang.String strFuturesMeasure,
				final java.lang.String[] astrFixFloatMaturityTenor,
				final double[] adblFixFloatQuote,
				final java.lang.String strFixFloatMeasure,
				final int iLatentStateType,
				final double dblBump,
				final boolean bIsProportional)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblBump)) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
			mapBumpedCurve = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>();

		org.drip.state.discount.MergedDiscountForwardCurve dcFundingBase = SingleStretchFundingCurve (dtSpot, strCurrency,
			astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure, adblFuturesQuote,
				strFuturesMeasure, astrFixFloatMaturityTenor, adblFixFloatQuote, strFixFloatMeasure,
					iLatentStateType);

		if (null == dcFundingBase) return null;

		int iNumDeposit = null == astrDepositMaturityTenor ? 0 : astrDepositMaturityTenor.length;
		int iNumFixFloat = null == adblFixFloatQuote ? 0 : adblFixFloatQuote.length;
		int iNumFutures = null == adblFuturesQuote ? 0 : adblFuturesQuote.length;
		int iNumDepositFutures = iNumDeposit + iNumFutures;
		int iNumDepositFuturesFixFloat = iNumDepositFutures + iNumFixFloat;
		int[] aiDate = new int[iNumDepositFuturesFixFloat];

		org.drip.product.rates.SingleStreamComponent[] aSSC =
			org.drip.service.template.ExchangeInstrumentBuilder.ForwardRateFuturesPack (dtSpot, iNumFutures,
				strCurrency);

		for (int i = 0; i < iNumDeposit; ++i)
			aiDate[i] = dtSpot.addTenor (astrDepositMaturityTenor[i]).julian();

		for (int i = iNumDeposit; i < iNumDepositFutures; ++i)
			aiDate[i] = aSSC[i - iNumDeposit].maturityDate().julian();

		for (int i = iNumDepositFutures; i < iNumDepositFuturesFixFloat; ++i)
			aiDate[i] = dtSpot.addTenor (astrFixFloatMaturityTenor[i - iNumDepositFutures]).julian();

		org.drip.state.nonlinear.FlatForwardDiscountCurve ffdc = dcFundingBase.flatNativeForward (aiDate,
			0.);

		if (null == ffdc) return null;

		mapBumpedCurve.put ("base", ffdc);

		org.drip.state.nonlinear.FlatForwardDiscountCurve ffdcBumped = dcFundingBase.flatNativeForward
			(aiDate, dblBump);

		if (null == ffdcBumped) return null;

		mapBumpedCurve.put ("bump", ffdcBumped);

		for (int i = 0; i < iNumDepositFuturesFixFloat; ++i) {
			org.drip.state.nonlinear.FlatForwardDiscountCurve ffdcTenorBumped =
				dcFundingBase.flatNativeForwardEI (aiDate, i, dblBump);

			if (null == ffdcTenorBumped) return null;

			mapBumpedCurve.put ("tenor::" + i, ffdcTenorBumped);
		}

		return mapBumpedCurve;
	}

	/**
	 * Construct a Map of Tenor Bumped Forward Curve Based off of the Input Exchange/OTC Market Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param forwardLabel Forward Label
	 * @param astrDepositMaturityTenor Array of Deposit Maturity Tenors
	 * @param adblDepositQuote Array of the Deposit Instrument Quotes
	 * @param strDepositMeasure The Deposit Instrument Calibration Measure
	 * @param astrFRAMaturityTenor Array of FRA Maturity Tenors
	 * @param adblFRAQuote Array of the FRA Instrument Quotes
	 * @param strFRAMeasure The FRA Instrument Calibration Measure
	 * @param astrFixFloatMaturityTenor Array of Fix-Float Maturity Tenors
	 * @param adblFixFloatQuote Array of the Fix-Float Quotes
	 * @param strFixFloatMeasure The Fix-Float Calibration Measure
	 * @param astrFloatFloatMaturityTenor Array of Float-Float Maturity Tenors
	 * @param adblFloatFloatQuote Array of the Float-Float Quotes
	 * @param strFloatFloatMeasure The Float-Float Calibration Measure
	 * @param astrSyntheticFloatFloatMaturityTenor Array of Synthetic Float-Float Maturity Tenors
	 * @param adblSyntheticFloatFloatQuote Array of the Synthetic Float-Float Quotes
	 * @param strSyntheticFloatFloatMeasure The Synthetic Float-Float Calibration Measure
	 * @param dc The Base Discount Curve
	 * @param fcReference The Reference Forward Curve
	 * @param iLatentStateType SHAPE_PRESERVING/SMOOTH
	 * @param dblBump The Tenor Node Bump Amount
	 * @param bIsProportional TRUE - The Bump Applied is Proportional
	 * 
	 * @return The Tenor Bumped Forward Curve Map
	 */

	public static final
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.forward.ForwardCurve>
			BumpedForwardCurve (
				final org.drip.analytics.date.JulianDate dtSpot,
				final org.drip.state.identifier.ForwardLabel forwardLabel,
				final java.lang.String[] astrDepositMaturityTenor,
				final double[] adblDepositQuote,
				final java.lang.String strDepositMeasure,
				final java.lang.String[] astrFRAMaturityTenor,
				final double[] adblFRAQuote,
				final java.lang.String strFRAMeasure,
				final java.lang.String[] astrFixFloatMaturityTenor,
				final double[] adblFixFloatQuote,
				final java.lang.String strFixFloatMeasure,
				final java.lang.String[] astrFloatFloatMaturityTenor,
				final double[] adblFloatFloatQuote,
				final java.lang.String strFloatFloatMeasure,
				final java.lang.String[] astrSyntheticFloatFloatMaturityTenor,
				final double[] adblSyntheticFloatFloatQuote,
				final java.lang.String strSyntheticFloatFloatMeasure,
				final org.drip.state.discount.MergedDiscountForwardCurve dc,
				final org.drip.state.forward.ForwardCurve fcReference,
				final int iLatentStateType,
				final double dblBump,
				final boolean bIsProportional)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblBump)) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.forward.ForwardCurve>
			mapBumpedCurve = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.forward.ForwardCurve>();

		try {
			org.drip.param.definition.ManifestMeasureTweak mmtFLAT = new
				org.drip.param.definition.ManifestMeasureTweak
					(org.drip.param.definition.ManifestMeasureTweak.FLAT, bIsProportional, dblBump);

			if (null != adblDepositQuote) {
				int iNumDeposit = adblDepositQuote.length;

				for (int i = 0; i < iNumDeposit; ++i) {
					org.drip.state.forward.ForwardCurve fcDepositQuoteBumped = ForwardCurve (dtSpot,
						forwardLabel, astrDepositMaturityTenor,
							org.drip.analytics.support.Helper.TweakManifestMeasure (adblDepositQuote, new
								org.drip.param.definition.ManifestMeasureTweak (i, bIsProportional,
									dblBump)), strDepositMeasure, astrFRAMaturityTenor, adblFRAQuote,
										strFRAMeasure, astrFixFloatMaturityTenor, adblFixFloatQuote,
											strFixFloatMeasure, astrFloatFloatMaturityTenor,
												adblFloatFloatQuote, strFloatFloatMeasure,
													astrSyntheticFloatFloatMaturityTenor,
														adblSyntheticFloatFloatQuote,
															strSyntheticFloatFloatMeasure, dc, fcReference,
																iLatentStateType);

					if (null != fcDepositQuoteBumped)
						mapBumpedCurve.put ("DEPOSIT::" + astrDepositMaturityTenor[i],
							fcDepositQuoteBumped);
				}
			}

			double[] adblDepositParallelBump = org.drip.analytics.support.Helper.TweakManifestMeasure
				(adblDepositQuote, mmtFLAT);

			org.drip.state.forward.ForwardCurve fcDepositQuoteBumped = ForwardCurve (dtSpot, forwardLabel,
				astrDepositMaturityTenor, adblDepositParallelBump, strDepositMeasure, astrFRAMaturityTenor,
					adblFRAQuote, strFRAMeasure, astrFixFloatMaturityTenor, adblFixFloatQuote,
						strFixFloatMeasure, astrFloatFloatMaturityTenor, adblFloatFloatQuote,
							strFloatFloatMeasure, astrSyntheticFloatFloatMaturityTenor,
								adblSyntheticFloatFloatQuote, strSyntheticFloatFloatMeasure, dc,
									fcReference, iLatentStateType);

			if (null != fcDepositQuoteBumped) mapBumpedCurve.put ("DEPOSIT::PLL", fcDepositQuoteBumped);

			if (null != adblFRAQuote) {
				int iNumFRA = adblFRAQuote.length;

				for (int i = 0; i < iNumFRA; ++i) {
					org.drip.state.forward.ForwardCurve fcFRAQuoteBumped = ForwardCurve (dtSpot,
						forwardLabel, astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure,
							astrFRAMaturityTenor, org.drip.analytics.support.Helper.TweakManifestMeasure
								(adblFRAQuote, new org.drip.param.definition.ManifestMeasureTweak (i,
									bIsProportional, dblBump)), strFRAMeasure, astrFixFloatMaturityTenor,
										adblFixFloatQuote, strFixFloatMeasure, astrFloatFloatMaturityTenor,
											adblFloatFloatQuote, strFloatFloatMeasure,
												astrSyntheticFloatFloatMaturityTenor,
													adblSyntheticFloatFloatQuote,
														strSyntheticFloatFloatMeasure, dc, fcReference,
															iLatentStateType);

					if (null != fcFRAQuoteBumped)
						mapBumpedCurve.put ("FRA::" + astrFRAMaturityTenor[i], fcFRAQuoteBumped);
				}
			}

			double[] adblFRAParallelBump = org.drip.analytics.support.Helper.TweakManifestMeasure
				(adblFRAQuote, mmtFLAT);

			org.drip.state.forward.ForwardCurve fcFRAQuoteBumped = ForwardCurve (dtSpot, forwardLabel,
				astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure, astrFRAMaturityTenor,
					adblFRAParallelBump, strFRAMeasure, astrFixFloatMaturityTenor, adblFixFloatQuote,
						strFixFloatMeasure, astrFloatFloatMaturityTenor, adblFloatFloatQuote,
							strFloatFloatMeasure, astrSyntheticFloatFloatMaturityTenor,
								adblSyntheticFloatFloatQuote, strSyntheticFloatFloatMeasure, dc,
									fcReference, iLatentStateType);

			if (null != fcFRAQuoteBumped) mapBumpedCurve.put ("FRA::PLL", fcFRAQuoteBumped);

			if (null != adblFixFloatQuote) {
				int iNumFixFloat = adblFixFloatQuote.length;

				for (int i = 0; i < iNumFixFloat; ++i) {
					org.drip.state.forward.ForwardCurve fcFixFloatQuoteBumped = ForwardCurve (dtSpot,
						forwardLabel, astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure,
							astrFRAMaturityTenor, adblFRAQuote, strFRAMeasure, astrFixFloatMaturityTenor,
								org.drip.analytics.support.Helper.TweakManifestMeasure (adblFixFloatQuote,
									new org.drip.param.definition.ManifestMeasureTweak (i, bIsProportional,
										dblBump)), strFixFloatMeasure, astrFloatFloatMaturityTenor,
											adblFloatFloatQuote, strFloatFloatMeasure,
												astrSyntheticFloatFloatMaturityTenor,
													adblSyntheticFloatFloatQuote,
														strSyntheticFloatFloatMeasure, dc, fcReference,
															iLatentStateType);

					if (null != fcFixFloatQuoteBumped)
						mapBumpedCurve.put ("FIXFLOAT::" + astrFixFloatMaturityTenor[i],
							fcFixFloatQuoteBumped);
				}
			}

			double[] adblFixFloatParallelBump = org.drip.analytics.support.Helper.TweakManifestMeasure
				(adblFixFloatQuote, mmtFLAT);

			org.drip.state.forward.ForwardCurve fcFixFloatQuoteBumped = ForwardCurve (dtSpot, forwardLabel,
				astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure, astrFRAMaturityTenor,
					adblFRAQuote, strFRAMeasure, astrFixFloatMaturityTenor, adblFixFloatParallelBump,
						strFixFloatMeasure, astrFloatFloatMaturityTenor, adblFloatFloatQuote,
							strFloatFloatMeasure, astrSyntheticFloatFloatMaturityTenor,
								adblSyntheticFloatFloatQuote, strSyntheticFloatFloatMeasure, dc,
									fcReference, iLatentStateType);

			if (null != fcFixFloatQuoteBumped) mapBumpedCurve.put ("FIXFLOAT::PLL", fcFixFloatQuoteBumped);

			if (null != adblFloatFloatQuote) {
				int iNumFloatFloat = adblFloatFloatQuote.length;

				for (int i = 0; i < iNumFloatFloat; ++i) {
					org.drip.state.forward.ForwardCurve fcFloatFloatQuoteBumped = ForwardCurve (dtSpot,
						forwardLabel, astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure,
							astrFRAMaturityTenor, adblFRAQuote, strFRAMeasure, astrFixFloatMaturityTenor,
								adblFRAQuote, strFixFloatMeasure, astrFloatFloatMaturityTenor,
									org.drip.analytics.support.Helper.TweakManifestMeasure
										(adblFloatFloatQuote, new
											org.drip.param.definition.ManifestMeasureTweak (i,
												bIsProportional, dblBump)), strFloatFloatMeasure,
													astrSyntheticFloatFloatMaturityTenor,
														adblSyntheticFloatFloatQuote,
															strSyntheticFloatFloatMeasure, dc, fcReference,
																iLatentStateType);

					if (null != fcFloatFloatQuoteBumped)
						mapBumpedCurve.put ("FLOATFLOAT::" + astrFloatFloatMaturityTenor[i],
							fcFloatFloatQuoteBumped);
				}
			}

			double[] adblFloatFloatParallelBump = org.drip.analytics.support.Helper.TweakManifestMeasure
				(adblFloatFloatQuote, mmtFLAT);

			org.drip.state.forward.ForwardCurve fcFloatFloatQuoteBumped = ForwardCurve (dtSpot, forwardLabel,
				astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure, astrFRAMaturityTenor,
					adblFRAQuote, strFRAMeasure, astrFixFloatMaturityTenor, adblFixFloatQuote,
						strFixFloatMeasure, astrFloatFloatMaturityTenor, adblFloatFloatParallelBump,
							strFloatFloatMeasure, astrSyntheticFloatFloatMaturityTenor,
								adblSyntheticFloatFloatQuote, strSyntheticFloatFloatMeasure, dc,
									fcReference, iLatentStateType);

			if (null != fcFloatFloatQuoteBumped)
				mapBumpedCurve.put ("FLOATFLOAT::PLL", fcFloatFloatQuoteBumped);

			if (null != adblSyntheticFloatFloatQuote) {
				int iNumSyntheticFloatFloat = adblSyntheticFloatFloatQuote.length;

				for (int i = 0; i < iNumSyntheticFloatFloat; ++i) {
					org.drip.state.forward.ForwardCurve fcSyntheticFloatFloatQuoteBumped = ForwardCurve
						(dtSpot, forwardLabel, astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure,
							astrFRAMaturityTenor, adblFRAQuote, strFRAMeasure, astrFixFloatMaturityTenor,
								adblFixFloatQuote, strFixFloatMeasure, astrFloatFloatMaturityTenor,
									adblFloatFloatQuote, strFloatFloatMeasure,
										astrSyntheticFloatFloatMaturityTenor,
											org.drip.analytics.support.Helper.TweakManifestMeasure
												(adblSyntheticFloatFloatQuote, new
													org.drip.param.definition.ManifestMeasureTweak (i,
														bIsProportional, dblBump)),
															strSyntheticFloatFloatMeasure, dc, fcReference,
																iLatentStateType);

					if (null != fcSyntheticFloatFloatQuoteBumped)
						mapBumpedCurve.put ("SYNTHETICFLOATFLOAT::" +
							astrSyntheticFloatFloatMaturityTenor[i], fcSyntheticFloatFloatQuoteBumped);
				}
			}

			double[] adblSyntheticFloatFloatParallelBump =
				org.drip.analytics.support.Helper.TweakManifestMeasure (adblSyntheticFloatFloatQuote,
					mmtFLAT);

			org.drip.state.forward.ForwardCurve fcSyntheticFloatFloatQuoteBumped = ForwardCurve (dtSpot,
				forwardLabel, astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure,
					astrFRAMaturityTenor, adblFRAQuote, strFRAMeasure, astrFixFloatMaturityTenor,
						adblFixFloatQuote, strFixFloatMeasure, astrFloatFloatMaturityTenor,
							adblFloatFloatQuote, strFloatFloatMeasure, astrSyntheticFloatFloatMaturityTenor,
								adblSyntheticFloatFloatParallelBump, strSyntheticFloatFloatMeasure, dc,
									fcReference, iLatentStateType);

			if (null != fcSyntheticFloatFloatQuoteBumped)
				mapBumpedCurve.put ("SYNTHETICFLOATFLOAT::PLL", fcSyntheticFloatFloatQuoteBumped);

			org.drip.state.forward.ForwardCurve fcQuoteBase = ForwardCurve (dtSpot, forwardLabel,
				astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure, astrFRAMaturityTenor,
					adblFRAQuote, strFRAMeasure, astrFixFloatMaturityTenor, adblFixFloatQuote,
						strFixFloatMeasure, astrFloatFloatMaturityTenor, adblFloatFloatQuote,
							strFloatFloatMeasure, astrSyntheticFloatFloatMaturityTenor,
								adblSyntheticFloatFloatQuote, strSyntheticFloatFloatMeasure, dc,
									fcReference, iLatentStateType);

			if (null != fcQuoteBase) mapBumpedCurve.put ("BASE", fcQuoteBase);

			org.drip.state.forward.ForwardCurve fcQuoteBump = ForwardCurve (dtSpot, forwardLabel,
				astrDepositMaturityTenor, adblDepositParallelBump, strDepositMeasure, astrFRAMaturityTenor,
					adblFRAParallelBump, strFRAMeasure, astrFixFloatMaturityTenor, adblFixFloatParallelBump,
						strFixFloatMeasure, astrFloatFloatMaturityTenor, adblFloatFloatParallelBump,
							strFloatFloatMeasure, astrSyntheticFloatFloatMaturityTenor,
								adblSyntheticFloatFloatParallelBump, strSyntheticFloatFloatMeasure, dc,
									fcReference, iLatentStateType);

			if (null != fcQuoteBump) mapBumpedCurve.put ("BUMP", fcQuoteBump);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return mapBumpedCurve;
	}

	/**
	 * Construct a Map of Tenor + Parallel Bumped Overnight Curves
	 * 
	 * @param dtSpot Spot Date
	 * @param strCurrency Currency
	 * @param astrDepositMaturityTenor Array of Deposit Maturity Tenors
	 * @param adblDepositQuote Array of Deposit Quotes
	 * @param strDepositMeasure Deposit Measure
	 * @param astrShortEndOISMaturityTenor Array of Short End OIS Maturity Tenors
	 * @param adblShortEndOISQuote Array of Short End OIS Quotes
	 * @param strShortEndOISMeasure Short End OIS Measure
	 * @param astrOISFuturesEffectiveTenor Array of OIS Futures Effective Tenors
	 * @param astrOISFuturesMaturityTenor Array of OIS Futures Maturity Tenors
	 * @param adblOISFuturesQuote Array of OIS Futures Quotes
	 * @param strOISFuturesMeasure OIS Futures Measure
	 * @param astrLongEndOISMaturityTenor Array of Long End OIS Maturity Tenors
	 * @param adblLongEndOISQuote Array of Long End OIS Quotes
	 * @param strLongEndOISMeasure Long End OIS Measure
	 * @param iLatentStateType SHAPE PRESERVING/SMOOTH
	 * @param dblBump The Tenor Node Bump Amount
	 * @param bIsProportional TRUE - The Bump Applied is Proportional
	 * 
	 * @return Map of Overnight Curves
	 */

	public static final
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
			BumpedOvernightCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final String strCurrency,
		final java.lang.String[] astrDepositMaturityTenor,
		final double[] adblDepositQuote,
		final java.lang.String strDepositMeasure,
		final java.lang.String[] astrShortEndOISMaturityTenor,
		final double[] adblShortEndOISQuote,
		final java.lang.String strShortEndOISMeasure,
		final java.lang.String[] astrOISFuturesEffectiveTenor,
		final java.lang.String[] astrOISFuturesMaturityTenor,
		final double[] adblOISFuturesQuote,
		final java.lang.String strOISFuturesMeasure,
		final java.lang.String[] astrLongEndOISMaturityTenor,
		final double[] adblLongEndOISQuote,
		final java.lang.String strLongEndOISMeasure,
		final int iLatentStateType,
		final double dblBump,
		final boolean bIsProportional)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblBump)) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
			mapBumpedCurve = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>();

		try {
			org.drip.param.definition.ManifestMeasureTweak mmtFLAT = new
				org.drip.param.definition.ManifestMeasureTweak
					(org.drip.param.definition.ManifestMeasureTweak.FLAT, bIsProportional, dblBump);

			if (null != adblDepositQuote) {
				int iNumDeposit = adblDepositQuote.length;

				for (int i = 0; i < iNumDeposit; ++i) {
					org.drip.state.discount.MergedDiscountForwardCurve dcOvernightDepositBumped =
						OvernightCurve (dtSpot, strCurrency, astrDepositMaturityTenor,
							org.drip.analytics.support.Helper.TweakManifestMeasure (adblDepositQuote, new
								org.drip.param.definition.ManifestMeasureTweak (i, bIsProportional,
									dblBump)), strDepositMeasure, astrShortEndOISMaturityTenor,
										adblShortEndOISQuote, strShortEndOISMeasure,
											astrOISFuturesEffectiveTenor, astrOISFuturesMaturityTenor,
												adblOISFuturesQuote, strOISFuturesMeasure,
													astrLongEndOISMaturityTenor, adblLongEndOISQuote,
														strLongEndOISMeasure, iLatentStateType);

					if (null != dcOvernightDepositBumped)
						mapBumpedCurve.put ("DEPOSIT::" + astrDepositMaturityTenor[i],
							dcOvernightDepositBumped);
				}
			}

			double[] adblDepositParallelBump = org.drip.analytics.support.Helper.TweakManifestMeasure
				(adblDepositQuote, mmtFLAT);

			org.drip.state.discount.MergedDiscountForwardCurve dcOvernightDepositBumped = OvernightCurve
				(dtSpot, strCurrency, astrDepositMaturityTenor, adblDepositParallelBump, strDepositMeasure,
					astrShortEndOISMaturityTenor, adblShortEndOISQuote, strShortEndOISMeasure,
						astrOISFuturesEffectiveTenor, astrOISFuturesMaturityTenor, adblOISFuturesQuote,
							strOISFuturesMeasure, astrLongEndOISMaturityTenor, adblLongEndOISQuote,
								strLongEndOISMeasure, iLatentStateType);

			if (null != dcOvernightDepositBumped)
				mapBumpedCurve.put ("DEPOSIT::PLL", dcOvernightDepositBumped);

			if (null != adblShortEndOISQuote) {
				int iNumShortEndOIS = adblShortEndOISQuote.length;

				for (int i = 0; i < iNumShortEndOIS; ++i) {
					org.drip.state.discount.MergedDiscountForwardCurve dcOvernightShortEndOISBumped =
						OvernightCurve (dtSpot, strCurrency, astrDepositMaturityTenor, adblDepositQuote,
							strDepositMeasure, astrShortEndOISMaturityTenor,
								org.drip.analytics.support.Helper.TweakManifestMeasure (adblShortEndOISQuote,
									new org.drip.param.definition.ManifestMeasureTweak (i, bIsProportional,
										dblBump)), strShortEndOISMeasure, astrOISFuturesEffectiveTenor,
											astrOISFuturesMaturityTenor, adblOISFuturesQuote,
												strOISFuturesMeasure, astrLongEndOISMaturityTenor,
													adblLongEndOISQuote, strLongEndOISMeasure,
														iLatentStateType);

					if (null != dcOvernightShortEndOISBumped)
						mapBumpedCurve.put ("SHORTENDOIS::" + astrShortEndOISMaturityTenor[i],
							dcOvernightShortEndOISBumped);
				}
			}

			double[] adblShortEndOISParallelBump = org.drip.analytics.support.Helper.TweakManifestMeasure
				(adblShortEndOISQuote, mmtFLAT);

			org.drip.state.discount.MergedDiscountForwardCurve dcOvernightShortEndOISBumped = OvernightCurve
				(dtSpot, strCurrency, astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure,
					astrShortEndOISMaturityTenor, adblShortEndOISParallelBump, strShortEndOISMeasure,
						astrOISFuturesEffectiveTenor, astrOISFuturesMaturityTenor,
							adblOISFuturesQuote, strOISFuturesMeasure, astrLongEndOISMaturityTenor,
								adblLongEndOISQuote, strLongEndOISMeasure, iLatentStateType);

			if (null != dcOvernightShortEndOISBumped)
				mapBumpedCurve.put ("SHORTENDOIS::PLL", dcOvernightShortEndOISBumped);

			if (null != adblOISFuturesQuote) {
				int iNumOISFutures = adblOISFuturesQuote.length;

				for (int i = 0; i < iNumOISFutures; ++i) {
					org.drip.state.discount.MergedDiscountForwardCurve dcOvernightOISFuturesBumped =
						OvernightCurve (dtSpot, strCurrency, astrDepositMaturityTenor, adblDepositQuote,
							strDepositMeasure, astrShortEndOISMaturityTenor, adblShortEndOISQuote,
								strShortEndOISMeasure, astrOISFuturesEffectiveTenor,
									astrOISFuturesMaturityTenor,
										org.drip.analytics.support.Helper.TweakManifestMeasure
											(adblOISFuturesQuote, new
												org.drip.param.definition.ManifestMeasureTweak (i,
													bIsProportional, dblBump)), strOISFuturesMeasure,
														astrLongEndOISMaturityTenor, adblLongEndOISQuote,
															strLongEndOISMeasure, iLatentStateType);

					if (null != dcOvernightOISFuturesBumped)
						mapBumpedCurve.put ("OISFUTURES::" + astrOISFuturesEffectiveTenor[i] + " x " +
							astrOISFuturesMaturityTenor[i], dcOvernightOISFuturesBumped);
				}
			}

			double[] adblOISFuturesParallelBump = org.drip.analytics.support.Helper.TweakManifestMeasure
				(adblOISFuturesQuote, mmtFLAT);

			org.drip.state.discount.MergedDiscountForwardCurve dcOvernightOISFuturesBumped = OvernightCurve
				(dtSpot, strCurrency, astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure,
					astrShortEndOISMaturityTenor, adblShortEndOISQuote, strShortEndOISMeasure,
						astrOISFuturesEffectiveTenor, astrOISFuturesMaturityTenor,
							adblOISFuturesParallelBump, strOISFuturesMeasure, astrLongEndOISMaturityTenor,
								adblLongEndOISQuote, strLongEndOISMeasure, iLatentStateType);

			if (null != dcOvernightOISFuturesBumped)
				mapBumpedCurve.put ("OISFUTURES::PARALLEL", dcOvernightOISFuturesBumped);

			if (null != adblLongEndOISQuote) {
				int iNumLongEndOIS = adblLongEndOISQuote.length;

				for (int i = 0; i < iNumLongEndOIS; ++i) {
					org.drip.state.discount.MergedDiscountForwardCurve dcOvernightLongEndOISBumped =
						OvernightCurve (dtSpot, strCurrency, astrDepositMaturityTenor, adblDepositQuote,
							strDepositMeasure, astrShortEndOISMaturityTenor, adblShortEndOISQuote,
								strShortEndOISMeasure, astrOISFuturesEffectiveTenor,
									astrOISFuturesMaturityTenor, adblOISFuturesQuote, strOISFuturesMeasure,
										astrLongEndOISMaturityTenor,
											org.drip.analytics.support.Helper.TweakManifestMeasure
												(adblLongEndOISQuote, new
													org.drip.param.definition.ManifestMeasureTweak (i,
														bIsProportional, dblBump)), strLongEndOISMeasure,
															iLatentStateType);

					if (null != dcOvernightLongEndOISBumped)
						mapBumpedCurve.put ("LONGENDOIS::" + astrLongEndOISMaturityTenor[i],
							dcOvernightLongEndOISBumped);
				}
			}

			double[] adblLongEndOISParallelBump = org.drip.analytics.support.Helper.TweakManifestMeasure
				(adblLongEndOISQuote, mmtFLAT);

			org.drip.state.discount.MergedDiscountForwardCurve dcOvernightLongEndOISBumped = OvernightCurve
				(dtSpot, strCurrency, astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure,
					astrShortEndOISMaturityTenor, adblShortEndOISQuote, strShortEndOISMeasure,
						astrOISFuturesEffectiveTenor, astrOISFuturesMaturityTenor, adblOISFuturesQuote,
							strOISFuturesMeasure, astrLongEndOISMaturityTenor, adblLongEndOISParallelBump,
								strLongEndOISMeasure, iLatentStateType);

			if (null != dcOvernightLongEndOISBumped)
				mapBumpedCurve.put ("LONGENDOIS::PLL", dcOvernightLongEndOISBumped);

			org.drip.state.discount.MergedDiscountForwardCurve dcOvernightBase = OvernightCurve (dtSpot,
				strCurrency, astrDepositMaturityTenor, adblDepositQuote, strDepositMeasure,
					astrShortEndOISMaturityTenor, adblShortEndOISQuote, strShortEndOISMeasure,
						astrOISFuturesEffectiveTenor, astrOISFuturesMaturityTenor, adblOISFuturesQuote,
							strOISFuturesMeasure, astrLongEndOISMaturityTenor, adblLongEndOISQuote,
								strLongEndOISMeasure, iLatentStateType);

			if (null != dcOvernightBase) mapBumpedCurve.put ("BASE", dcOvernightBase);

			org.drip.state.discount.MergedDiscountForwardCurve dcOvernightBump = OvernightCurve (dtSpot,
				strCurrency, astrDepositMaturityTenor, adblDepositParallelBump, strDepositMeasure,
					astrShortEndOISMaturityTenor, adblShortEndOISParallelBump, strShortEndOISMeasure,
						astrOISFuturesEffectiveTenor, astrOISFuturesMaturityTenor,
							adblOISFuturesParallelBump, strOISFuturesMeasure, astrLongEndOISMaturityTenor,
								adblLongEndOISParallelBump, strLongEndOISMeasure, iLatentStateType);

			if (null != dcOvernightBump) mapBumpedCurve.put ("BUMP", dcOvernightBump);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return mapBumpedCurve;
	}

	/**
	 * Construct a Tenor + Parallel Map of Bumped Credit Curves from Overnight Exchange/OTC Market Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param strCredit Credit Curve
	 * @param astrMaturityTenor Maturity Tenor
	 * @param adblCoupon Coupon Array
	 * @param adblQuote Array of Market Quotes
	 * @param strMeasure Calibration Measure
	 * @param dc Discount Curve
	 * @param dblBump The Tenor Node Bump Amount
	 * @param bIsProportional TRUE - The Bump Applied is Proportional
	 * 
	 * @return Map of Bumped Credit Curves
	 */

	public static final org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>
		BumpedCreditCurve (
			final org.drip.analytics.date.JulianDate dtSpot,
			final java.lang.String strCredit,
			final java.lang.String[] astrMaturityTenor,
			final double[] adblCoupon,
			final double[] adblQuote,
			final java.lang.String strMeasure,
			final org.drip.state.discount.MergedDiscountForwardCurve dc,
			final double dblBump,
			final boolean bIsProportional)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblBump)) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve> mapBumpedCurve =
			new org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>();

		if (null != adblQuote) {
			int iNumComp = adblQuote.length;

			for (int i = 0; i < iNumComp; ++i) {
				try {
					org.drip.state.credit.CreditCurve ccBumped = CreditCurve (dtSpot, strCredit,
						astrMaturityTenor, adblCoupon, org.drip.analytics.support.Helper.TweakManifestMeasure
							(adblQuote, new org.drip.param.definition.ManifestMeasureTweak (i,
								bIsProportional, dblBump)), strMeasure, dc);

					if (null != ccBumped) mapBumpedCurve.put ("CDS::" + astrMaturityTenor[i], ccBumped);
				} catch (java.lang.Exception e) {
					e.printStackTrace();
				}
			}
		}

		try {
			org.drip.state.credit.CreditCurve ccBase = CreditCurve (dtSpot, strCredit, astrMaturityTenor,
				adblCoupon, adblQuote, strMeasure, dc);

			if (null != ccBase) mapBumpedCurve.put ("BASE", ccBase);

			org.drip.state.credit.CreditCurve ccBumped = CreditCurve (dtSpot, strCredit, astrMaturityTenor,
				adblCoupon, org.drip.analytics.support.Helper.TweakManifestMeasure (adblQuote, new
					org.drip.param.definition.ManifestMeasureTweak
						(org.drip.param.definition.ManifestMeasureTweak.FLAT, bIsProportional, dblBump)),
							strMeasure, dc);

			if (null != ccBumped) mapBumpedCurve.put ("BUMP", ccBumped);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return mapBumpedCurve;
	}

	/**
	 * Construct a Tenor + Parallel Map of Govvie Curves from the Treasury Instruments
	 * 
	 * @param strCode The Govvie Code
	 * @param dtSpot Spot Date
	 * @param adtEffective Array of Effective Dates
	 * @param adtMaturity Array of Maturity Dates
	 * @param adblCoupon Array of Coupons
	 * @param adblQuote Array of Market Quotes
	 * @param strMeasure Calibration Measure
	 * @param iLatentStateType SHAPE PRESERVING/SMOOTH
	 * @param dblBump The Tenor Node Bump Amount
	 * @param bIsProportional TRUE - The Bump Applied is Proportional
	 * 
	 * @return Map of Govvie Curve Instance
	 */

	public static final org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.govvie.GovvieCurve>
		BumpedGovvieCurve (
			final java.lang.String strCode,
			final org.drip.analytics.date.JulianDate dtSpot,
			final org.drip.analytics.date.JulianDate[] adtEffective,
			final org.drip.analytics.date.JulianDate[] adtMaturity,
			final double[] adblCoupon,
			final double[] adblQuote,
			final java.lang.String strMeasure,
			final int iLatentStateType,
			final double dblBump,
			final boolean bIsProportional)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblBump)) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.govvie.GovvieCurve> mapBumpedCurve =
			new org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.govvie.GovvieCurve>();

		if (null != adblQuote) {
			int iNumComp = adblQuote.length;

			for (int i = 0; i < iNumComp; ++i) {
				try {
					org.drip.state.govvie.GovvieCurve gcBumped = GovvieCurve (strCode, dtSpot, adtEffective,
						adtMaturity, adblCoupon, org.drip.analytics.support.Helper.TweakManifestMeasure
							(adblQuote, new org.drip.param.definition.ManifestMeasureTweak (i,
								bIsProportional, dblBump)), strMeasure, iLatentStateType);

					if (null != gcBumped) mapBumpedCurve.put ("TSY::" + adtMaturity[i], gcBumped);
				} catch (java.lang.Exception e) {
					e.printStackTrace();
				}
			}
		}

		try {
			org.drip.state.govvie.GovvieCurve gcBase = GovvieCurve (strCode, dtSpot, adtEffective,
				adtMaturity, adblCoupon, adblQuote, strMeasure, iLatentStateType);

			if (null != gcBase) mapBumpedCurve.put ("BASE", gcBase);

			org.drip.state.govvie.GovvieCurve gcBumped = GovvieCurve (strCode, dtSpot, adtEffective,
				adtMaturity, adblCoupon, org.drip.analytics.support.Helper.TweakManifestMeasure (adblQuote,
					new org.drip.param.definition.ManifestMeasureTweak
						(org.drip.param.definition.ManifestMeasureTweak.FLAT, bIsProportional, dblBump)),
							strMeasure, iLatentStateType);

			if (null != gcBumped) mapBumpedCurve.put ("BUMP", gcBumped);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return mapBumpedCurve;
	}

	/**
	 * Construct a Tenor + Parallel Map of FX Curve from the FX Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param cp The FX Currency Pair
	 * @param astrMaturityTenor Array of Maturity Tenors
	 * @param adblQuote Array of FX Forwards
	 * @param strMeasure Calibration Measure
	 * @param dblFXSpot FX Spot
	 * @param iLatentStateType SHAPE PRESERVING/SMOOTH
	 * @param dblBump The Tenor Node Bump Amount
	 * @param bIsProportional TRUE - The Bump Applied is Proportional
	 * 
	 * @return Map of FX Curve Instance
	 */

	public static final org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.fx.FXCurve>
		BumpedFXCurve (
			final org.drip.analytics.date.JulianDate dtSpot,
			final org.drip.product.params.CurrencyPair cp,
			final java.lang.String[] astrMaturityTenor,
			final double[] adblQuote,
			final java.lang.String strMeasure,
			final double dblFXSpot,
			final int iLatentStateType,
			final double dblBump,
			final boolean bIsProportional)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblBump)) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.fx.FXCurve> mapBumpedCurve = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.fx.FXCurve>();

		if (null != adblQuote) {
			int iNumComp = adblQuote.length;

			for (int i = 0; i < iNumComp; ++i) {
				try {
					org.drip.state.fx.FXCurve fxCurveBumped = FXCurve (dtSpot, cp, astrMaturityTenor,
						org.drip.analytics.support.Helper.TweakManifestMeasure (adblQuote, new
							org.drip.param.definition.ManifestMeasureTweak (i, bIsProportional, dblBump)),
								strMeasure, dblFXSpot, iLatentStateType);

					if (null != fxCurveBumped)
						mapBumpedCurve.put ("FXFWD::" + astrMaturityTenor[i], fxCurveBumped);
				} catch (java.lang.Exception e) {
					e.printStackTrace();
				}
			}
		}

		try {
			org.drip.state.fx.FXCurve fxCurveBase = FXCurve (dtSpot, cp, astrMaturityTenor, adblQuote,
				strMeasure, dblFXSpot, iLatentStateType);

			if (null != fxCurveBase) mapBumpedCurve.put ("BASE", fxCurveBase);

			org.drip.state.fx.FXCurve fxCurveBump = FXCurve (dtSpot, cp, astrMaturityTenor,
				org.drip.analytics.support.Helper.TweakManifestMeasure (adblQuote, new
					org.drip.param.definition.ManifestMeasureTweak
						(org.drip.param.definition.ManifestMeasureTweak.FLAT, bIsProportional, dblBump)),
							strMeasure, dblFXSpot, iLatentStateType);

			if (null != fxCurveBump) mapBumpedCurve.put ("BUMP", fxCurveBump);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return mapBumpedCurve;
	}

	/**
	 * Construct a Tenor + Parallel Forward Volatility Latent State Construction from Cap/Floor Instruments
	 * 
	 * @param dtSpot Spot Date
	 * @param forwardLabel Forward Label
	 * @param bIsCap TRUE - Create and Use Array of Caps
	 * @param astrMaturityTenor Array of Cap/floor Maturities
	 * @param adblStrike Array of Cap/Floor Strikes
	 * @param adblQuote Array of Cap/Floor Quotes
	 * @param strMeasure Calibration Measure
	 * @param dc Discount Curve Instance
	 * @param fc Forward Curve Instance
	 * @param dblBump The Tenor Node Bump Amount
	 * @param bIsProportional TRUE - The Bump Applied is Proportional
	 * 
	 * @return Map of Forward Volatility Curve Instance
	 */

	public static final
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>
			BumpedForwardVolatilityCurve (
				final org.drip.analytics.date.JulianDate dtSpot,
				final org.drip.state.identifier.ForwardLabel forwardLabel,
				final boolean bIsCap,
				final java.lang.String[] astrMaturityTenor,
				final double[] adblStrike,
				final double[] adblQuote,
				final java.lang.String strMeasure,
				final org.drip.state.discount.MergedDiscountForwardCurve dc,
				final org.drip.state.forward.ForwardCurve fc,
				final double dblBump,
				final boolean bIsProportional)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblBump)) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>
			mapBumpedCurve = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>();

		if (null != adblQuote) {
			int iNumComp = adblQuote.length;

			for (int i = 0; i < iNumComp; ++i) {
				try {
					org.drip.state.volatility.VolatilityCurve forwardVolatilityCurveBumped =
						ForwardRateVolatilityCurve (dtSpot, forwardLabel, bIsCap, astrMaturityTenor,
							adblStrike, org.drip.analytics.support.Helper.TweakManifestMeasure (adblQuote,
								new org.drip.param.definition.ManifestMeasureTweak (i, bIsProportional,
									dblBump)), strMeasure, dc, fc);

					if (null != forwardVolatilityCurveBumped)
						mapBumpedCurve.put ("CAPFLOOR::" + astrMaturityTenor[i],
							forwardVolatilityCurveBumped);
				} catch (java.lang.Exception e) {
					e.printStackTrace();
				}
			}
		}

		try {
			org.drip.state.volatility.VolatilityCurve forwardVolatilityCurveBase = ForwardRateVolatilityCurve
				(dtSpot, forwardLabel, bIsCap, astrMaturityTenor, adblStrike, adblQuote, strMeasure, dc, fc);

			if (null != forwardVolatilityCurveBase) mapBumpedCurve.put ("BASE", forwardVolatilityCurveBase);

			org.drip.state.volatility.VolatilityCurve forwardVolatilityCurveBumped =
				ForwardRateVolatilityCurve (dtSpot, forwardLabel, bIsCap, astrMaturityTenor, adblStrike,
					org.drip.analytics.support.Helper.TweakManifestMeasure (adblQuote, new
						org.drip.param.definition.ManifestMeasureTweak
							(org.drip.param.definition.ManifestMeasureTweak.FLAT, bIsProportional, dblBump)),
								strMeasure, dc, fc);

			if (null != forwardVolatilityCurveBumped)
				mapBumpedCurve.put ("BUMP", forwardVolatilityCurveBumped);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return mapBumpedCurve;
	}
}
