
package org.drip.service.template;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.analytics.support.Helper;
import org.drip.function.r1tor1custom.QuadraticRationalShapeControl;
import org.drip.market.issue.TreasurySettingContainer;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.definition.ManifestMeasureTweak;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.credit.BondComponent;
import org.drip.product.definition.CreditDefaultSwap;
import org.drip.product.fx.FXForwardComponent;
import org.drip.product.params.CurrencyPair;
import org.drip.product.rates.SingleStreamComponent;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.ResponseScalingShapeControl;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.params.SegmentInelasticDesignControl;
import org.drip.spline.stretch.BoundarySettings;
import org.drip.spline.stretch.MultiSegmentSequence;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.creator.ScenarioCreditCurveBuilder;
import org.drip.state.creator.ScenarioDiscountCurveBuilder;
import org.drip.state.creator.ScenarioFXCurveBuilder;
import org.drip.state.creator.ScenarioForwardCurveBuilder;
import org.drip.state.creator.ScenarioGovvieCurveBuilder;
import org.drip.state.creator.ScenarioLocalVolatilityBuilder;
import org.drip.state.credit.CreditCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.estimator.LatentStateStretchBuilder;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.fx.FXCurve;
import org.drip.state.govvie.GovvieCurve;
import org.drip.state.identifier.ForwardLabel;
import org.drip.state.inference.LatentStateStretchSpec;
import org.drip.state.inference.LinearLatentStateCalibrator;
import org.drip.state.nonlinear.FlatForwardDiscountCurve;
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
	 * @param fixFloatMeasure Fix Float Calibration Measure
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
		final String fixFloatMeasure,
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
				fixFloatMeasure
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
				fixFloatMeasure
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
	 * 
	 * @return Instance of the Forward Curve
	 */

	public static final ForwardCurve ShapePreservingForwardCurve (
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
		final ForwardCurve referenceForwardCurve)
	{
		try {
			return ForwardCurve (
				spotDate,
				forwardLabel,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				fraMaturityTenorArray,
				fraQuoteArray,
				fraMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				fixFloatMeasure,
				floatFloatMaturityTenorArray,
				floatFloatQuoteArray,
				floatFloatMeasure,
				syntheticFloatFloatMaturityTenorArray,
				syntheticFloatFloatQuoteArray,
				syntheticFloatFloatMeasure,
				mergedDiscountForwardCurve,
				referenceForwardCurve,
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
	 * Construct a Instance of Smooth Forward Curve off of Exchange/OTC Market Instruments
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
	 * 
	 * @return Instance of the Forward Curve
	 */

	public static final ForwardCurve SmoothForwardCurve (
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
		final ForwardCurve referenceForwardCurve)
	{
		try {
			return ForwardCurve (
				spotDate,
				forwardLabel,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				fraMaturityTenorArray,
				fraQuoteArray,
				fraMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				fixFloatMeasure,
				floatFloatMaturityTenorArray,
				floatFloatQuoteArray,
				floatFloatMeasure,
				syntheticFloatFloatMaturityTenorArray,
				syntheticFloatFloatQuoteArray,
				syntheticFloatFloatMeasure,
				mergedDiscountForwardCurve,
				referenceForwardCurve,
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
	 * Construct a Instance of the Smooth/Shape Preserving Forward Curve off of Exchange/OTC Market
	 *  Instruments
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
	 * @param latentStateType SHAPE_PRESERVING/SMOOTH
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
		final int latentStateType)
	{
		if (SHAPE_PRESERVING == latentStateType) {
			return ShapePreservingForwardCurve (
				spotDate,
				forwardLabel,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				fraMaturityTenorArray,
				fraQuoteArray,
				fraMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				fixFloatMeasure,
				floatFloatMaturityTenorArray,
				floatFloatQuoteArray,
				floatFloatMeasure,
				syntheticFloatFloatMaturityTenorArray,
				syntheticFloatFloatQuoteArray,
				syntheticFloatFloatMeasure,
				mergedDiscountForwardCurve,
				referenceForwardCurve
			);
		}

		if (SMOOTH == latentStateType) {
			return SmoothForwardCurve (
				spotDate,
				forwardLabel,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				fraMaturityTenorArray,
				fraQuoteArray,
				fraMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				fixFloatMeasure,
				floatFloatMaturityTenorArray,
				floatFloatQuoteArray,
				floatFloatMeasure,
				syntheticFloatFloatMaturityTenorArray,
				syntheticFloatFloatQuoteArray,
				syntheticFloatFloatMeasure,
				mergedDiscountForwardCurve,
				referenceForwardCurve
			);
		}

		return null;
	}

	/**
	 * Construct an Overnight Curve from Overnight Exchange/OTC Market Instruments
	 * 
	 * @param spotDate Spot Date
	 * @param currency Currency
	 * @param depositMaturityTenorArray Array of Deposit Maturity Tenors
	 * @param depositQuoteArray Array of Deposit Quotes
	 * @param depositMeasure Deposit Measure
	 * @param shortEndOISMaturityTenorArray Array of Short End OIS Maturity Tenors
	 * @param shortEndOISQuoteArray Array of Short End OIS Quotes
	 * @param shortEndOISMeasure Short End OIS Measure
	 * @param oisFuturesEffectiveTenorArray Array of OIS Futures Effective Tenors
	 * @param oisFuturesMaturityTenorArray Array of OIS Futures Maturity Tenors
	 * @param oisFuturesQuoteArray Array of OIS Futures Quotes
	 * @param oisFuturesMeasure OIS Futures Measure
	 * @param longEndOISMaturityTenorArray Array of Long End OIS Maturity Tenors
	 * @param longEndOISQuoteArray Array of Long End OIS Quotes
	 * @param longEndOISMeasure Long End OIS Measure
	 * @param segmentCustomBuilderControl Segment Custom Builder Control
	 * 
	 * @return Overnight Curve from Overnight OTC Instruments
	 */

	public static final MergedDiscountForwardCurve OvernightCurve (
		final JulianDate spotDate,
		final String currency,
		final String[] depositMaturityTenorArray,
		final double[] depositQuoteArray,
		final String depositMeasure,
		final String[] shortEndOISMaturityTenorArray,
		final double[] shortEndOISQuoteArray,
		final String shortEndOISMeasure,
		final String[] oisFuturesEffectiveTenorArray,
		final String[] oisFuturesMaturityTenorArray,
		final double[] oisFuturesQuoteArray,
		final String oisFuturesMeasure,
		final String[] longEndOISMaturityTenorArray,
		final double[] longEndOISQuoteArray,
		final String longEndOISMeasure,
		final SegmentCustomBuilderControl segmentCustomBuilderControl)
	{
		if (null == spotDate) {
			return null;
		}

		LatentStateStretchSpec depositLatentStateStretchSpec = null;
		LatentStateStretchSpec longEndOISLatentStateStretchSpec = null;
		LatentStateStretchSpec oisFuturesLatentStateStretchSpec = null;
		LatentStateStretchSpec shortEndOISLatentStateStretchSpec = null;
		int depositQuoteCount = null == depositQuoteArray ? 0 : depositQuoteArray.length;
		int longEndOISQuoteCount = null == longEndOISQuoteArray ? 0 : longEndOISQuoteArray.length;
		int oisFuturesQuoteCount = null == oisFuturesQuoteArray ? 0 : oisFuturesQuoteArray.length;
		int shortEndOISQuoteCount = null == shortEndOISQuoteArray ? 0 : shortEndOISQuoteArray.length;
		int depositComponentCount = null == depositMaturityTenorArray ? 0 : depositMaturityTenorArray.length;
		int oisFuturesComponentCount = null == oisFuturesMaturityTenorArray ? 0 :
			oisFuturesMaturityTenorArray.length;
		int oisFutures2ComponentCount = null == oisFuturesEffectiveTenorArray ? 0 :
			oisFuturesEffectiveTenorArray.length;
		int longEndOISComponentCount = null == longEndOISMaturityTenorArray ? 0 :
			longEndOISMaturityTenorArray.length;
		int shortEndOISComponentCount = null == shortEndOISMaturityTenorArray ? 0 :
			shortEndOISMaturityTenorArray.length;

		if (depositQuoteCount != depositComponentCount ||
			shortEndOISQuoteCount != shortEndOISComponentCount ||
			oisFuturesQuoteCount != oisFuturesComponentCount ||
			oisFutures2ComponentCount != oisFuturesComponentCount ||
			longEndOISQuoteCount != longEndOISComponentCount)
		{
			return null;
		}

		JulianDate effectiveDate = spotDate.addBusDays (0, currency);

		if (0 != depositComponentCount) {
			depositLatentStateStretchSpec = LatentStateStretchBuilder.ForwardFundingStretchSpec (
				"DEPOSIT",
				OTCInstrumentBuilder.OvernightDeposit (effectiveDate,
					currency,
					depositMaturityTenorArray
				),
				depositMeasure,
				depositQuoteArray
			);
		}

		if (0 != shortEndOISComponentCount) {
			shortEndOISLatentStateStretchSpec = LatentStateStretchBuilder.ForwardFundingStretchSpec (
				"SHORTENDOIS",
				OTCInstrumentBuilder.OISFixFloat (
					effectiveDate,
					currency,
					shortEndOISMaturityTenorArray,
					shortEndOISQuoteArray,
					false
				),
				shortEndOISMeasure,
				shortEndOISQuoteArray
			);
		}

		if (0 != oisFuturesComponentCount) {
			oisFuturesLatentStateStretchSpec = LatentStateStretchBuilder.ForwardFundingStretchSpec (
				"OISFUTURES",
				OTCInstrumentBuilder.OISFixFloatFutures (
					effectiveDate,
					currency,
					oisFuturesEffectiveTenorArray,
					oisFuturesMaturityTenorArray,
					oisFuturesQuoteArray,
					false
				),
				oisFuturesMeasure,
				oisFuturesQuoteArray
			);
		}

		if (0 != longEndOISComponentCount) {
			longEndOISLatentStateStretchSpec = LatentStateStretchBuilder.ForwardFundingStretchSpec (
				"LONGENDOIS",
				OTCInstrumentBuilder.OISFixFloat (
					effectiveDate,
					currency,
					longEndOISMaturityTenorArray,
					longEndOISQuoteArray,
					false
				),
				longEndOISMeasure,
				longEndOISQuoteArray
			);
		}

		try {
			return ScenarioDiscountCurveBuilder.ShapePreservingDFBuild (
				currency,
				new
				LinearLatentStateCalibrator (
					segmentCustomBuilderControl,
					BoundarySettings.NaturalStandard(),
					MultiSegmentSequence.CALIBRATE,
					null,
					null
				),
				new LatentStateStretchSpec[] {
					depositLatentStateStretchSpec,
					shortEndOISLatentStateStretchSpec,
					oisFuturesLatentStateStretchSpec,
					longEndOISLatentStateStretchSpec
				},
				ValuationParams.Spot (effectiveDate.julian()),
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
	 * Construct a Shape Preserving Overnight Curve from Overnight Exchange/OTC Market Instruments
	 * 
	 * @param spotDate Spot Date
	 * @param currency Currency
	 * @param depositMaturityTenorArray Array of Deposit Maturity Tenors
	 * @param depositQuoteArray Array of Deposit Quotes
	 * @param depositMeasure Deposit Measure
	 * @param shortEndOISMaturityTenorArray Array of Short End OIS Maturity Tenors
	 * @param shortEndOISQuoteArray Array of Short End OIS Quotes
	 * @param shortEndOISMeasure Short End OIS Measure
	 * @param oisFuturesEffectiveTenorArray Array of OIS Futures Effective Tenors
	 * @param oisFuturesMaturityTenorArray Array of OIS Futures Maturity Tenors
	 * @param oisFuturesQuoteArray Array of OIS Futures Quotes
	 * @param oisFuturesMeasure OIS Futures Measure
	 * @param longEndOISMaturityTenorArray Array of Long End OIS Maturity Tenors
	 * @param longEndOISQuoteArray Array of Long End OIS Quotes
	 * @param longEndOISMeasure Long End OIS Measure
	 * 
	 * @return Overnight Curve from Overnight OTC Instruments
	 */

	public static final MergedDiscountForwardCurve ShapePreservingOvernightCurve (
		final JulianDate spotDate,
		final String currency,
		final String[] depositMaturityTenorArray,
		final double[] depositQuoteArray,
		final String depositMeasure,
		final String[] shortEndOISMaturityTenorArray,
		final double[] shortEndOISQuoteArray,
		final String shortEndOISMeasure,
		final String[] oisFuturesEffectiveTenorArray,
		final String[] oisFuturesMaturityTenorArray,
		final double[] oisFuturesQuoteArray,
		final String oisFuturesMeasure,
		final String[] longEndOISMaturityTenorArray,
		final double[] longEndOISQuoteArray,
		final String longEndOISMeasure)
	{
		try {
			return OvernightCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				shortEndOISMaturityTenorArray,
				shortEndOISQuoteArray,
				shortEndOISMeasure,
				oisFuturesEffectiveTenorArray,
				oisFuturesMaturityTenorArray,
				oisFuturesQuoteArray,
				oisFuturesMeasure,
				longEndOISMaturityTenorArray,
				longEndOISQuoteArray,
				longEndOISMeasure,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new PolynomialFunctionSetParams (2),
					SegmentInelasticDesignControl.Create (0, 2),
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
	 * Construct a Smooth Overnight Curve from Overnight Exchange/OTC Market Instruments
	 * 
	 * @param spotDate Spot Date
	 * @param currency Currency
	 * @param depositMaturityTenorArray Array of Deposit Maturity Tenors
	 * @param depositQuoteArray Array of Deposit Quotes
	 * @param depositMeasure Deposit Measure
	 * @param shortEndOISMaturityTenorArray Array of Short End OIS Maturity Tenors
	 * @param shortEndOISQuoteArray Array of Short End OIS Quotes
	 * @param shortEndOISMeasure Short End OIS Measure
	 * @param oisFuturesEffectiveTenorArray Array of OIS Futures Effective Tenors
	 * @param oisFuturesMaturityTenorArray Array of OIS Futures Maturity Tenors
	 * @param oisFuturesQuoteArray Array of OIS Futures Quotes
	 * @param oisFuturesMeasure OIS Futures Measure
	 * @param longEndOISMaturityTenorArray Array of Long End OIS Maturity Tenors
	 * @param longEndOISQuoteArray Array of Long End OIS Quotes
	 * @param longEndOISMeasure Long End OIS Measure
	 * 
	 * @return Overnight Curve from Overnight OTC Instruments
	 */

	public static final MergedDiscountForwardCurve SmoothOvernightCurve (
		final JulianDate spotDate,
		final String currency,
		final String[] depositMaturityTenorArray,
		final double[] depositQuoteArray,
		final String depositMeasure,
		final String[] shortEndOISMaturityTenorArray,
		final double[] shortEndOISQuoteArray,
		final String shortEndOISMeasure,
		final String[] oisFuturesEffectiveTenorArray,
		final String[] oisFuturesMaturityTenorArray,
		final double[] oisFuturesQuoteArray,
		final String oisFuturesMeasure,
		final String[] longEndOISMaturityTenorArray,
		final double[] longEndOISQuoteArray,
		final String longEndOISMeasure)
	{
		try {
			return OvernightCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				shortEndOISMaturityTenorArray,
				shortEndOISQuoteArray,
				shortEndOISMeasure,
				oisFuturesEffectiveTenorArray,
				oisFuturesMaturityTenorArray,
				oisFuturesQuoteArray,
				oisFuturesMeasure,
				longEndOISMaturityTenorArray,
				longEndOISQuoteArray,
				longEndOISMeasure,
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
	 * Construct an Overnight Curve from Overnight Exchange/OTC Market Instruments
	 * 
	 * @param spotDate Spot Date
	 * @param currency Currency
	 * @param depositMaturityTenorArray Array of Deposit Maturity Tenors
	 * @param depositQuoteArray Array of Deposit Quotes
	 * @param depositMeasure Deposit Measure
	 * @param shortEndOISMaturityTenorArray Array of Short End OIS Maturity Tenors
	 * @param shortEndOISQuoteArray Array of Short End OIS Quotes
	 * @param shortEndOISMeasure Short End OIS Measure
	 * @param oisFuturesEffectiveTenorArray Array of OIS Futures Effective Tenors
	 * @param oisFuturesMaturityTenorArray Array of OIS Futures Maturity Tenors
	 * @param oisFuturesQuoteArray Array of OIS Futures Quotes
	 * @param oisFuturesMeasure OIS Futures Measure
	 * @param longEndOISMaturityTenorArray Array of Long End OIS Maturity Tenors
	 * @param longEndOISQuoteArray Array of Long End OIS Quotes
	 * @param longEndOISMeasure Long End OIS Measure
	 * @param latentStateType SHAPE PRESERVING/SMOOTH
	 * 
	 * @return Overnight Curve from Overnight OTC Instruments
	 */

	public static final MergedDiscountForwardCurve OvernightCurve (
		final JulianDate spotDate,
		final String currency,
		final String[] depositMaturityTenorArray,
		final double[] depositQuoteArray,
		final String depositMeasure,
		final String[] shortEndOISMaturityTenorArray,
		final double[] shortEndOISQuoteArray,
		final String shortEndOISMeasure,
		final String[] oisFuturesEffectiveTenorArray,
		final String[] oisFuturesMaturityTenorArray,
		final double[] oisFuturesQuoteArray,
		final String oisFuturesMeasure,
		final String[] longEndOISMaturityTenorArray,
		final double[] longEndOISQuoteArray,
		final String longEndOISMeasure,
		final int latentStateType)
	{
		if (SHAPE_PRESERVING == latentStateType) {
			return ShapePreservingOvernightCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				shortEndOISMaturityTenorArray,
				shortEndOISQuoteArray,
				shortEndOISMeasure,
				oisFuturesEffectiveTenorArray,
				oisFuturesMaturityTenorArray,
				oisFuturesQuoteArray,
				oisFuturesMeasure,
				longEndOISMaturityTenorArray,
				longEndOISQuoteArray,
				longEndOISMeasure
			);
		}

		if (SMOOTH == latentStateType) {
			return SmoothOvernightCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				shortEndOISMaturityTenorArray,
				shortEndOISQuoteArray,
				shortEndOISMeasure,
				oisFuturesEffectiveTenorArray,
				oisFuturesMaturityTenorArray,
				oisFuturesQuoteArray,
				oisFuturesMeasure,
				longEndOISMaturityTenorArray,
				longEndOISQuoteArray,
				longEndOISMeasure
			);
		}

		return null;
	}

	/**
	 * Construct a Credit Curve from Overnight Exchange/OTC Market Instruments
	 * 
	 * @param spotDate Spot Date
	 * @param creditCurveName Credit Curve Name
	 * @param maturityTenorArray Maturity Tenor Array
	 * @param couponArray Coupon Array
	 * @param quoteArray Array of Market Quotes
	 * @param calibrationMeasure Calibration Measure
	 * @param mergedDiscountForwardCurve Discount Curve
	 * 
	 * @return The Credit Curve Instance
	 */

	public static final CreditCurve CreditCurve (
		final JulianDate spotDate,
		final String creditCurveName,
		final String[] maturityTenorArray,
		final double[] couponArray,
		final double[] quoteArray,
		final String calibrationMeasure,
		final MergedDiscountForwardCurve mergedDiscountForwardCurve)
	{
		if (null == spotDate || null == mergedDiscountForwardCurve) {
			return null;
		}

		String currency = mergedDiscountForwardCurve.currency();

		JulianDate effectiveDate = spotDate.addBusDays (0, currency);

		CreditDefaultSwap[] creditDefaultSwapArray = OTCInstrumentBuilder.CDS (
			effectiveDate,
			maturityTenorArray,
			couponArray,
			currency,
			creditCurveName
		);

		if (null == creditDefaultSwapArray) {
			return null;
		}

		String[] calibrationMeasureArray = new String[creditDefaultSwapArray.length];

		if (0 == creditDefaultSwapArray.length) {
			return null;
		}

		for (int creditDefaultSwapIndex = 0;
			creditDefaultSwapIndex < creditDefaultSwapArray.length;
			++creditDefaultSwapIndex)
		{
			calibrationMeasureArray[creditDefaultSwapIndex] = calibrationMeasure;
		}

		return ScenarioCreditCurveBuilder.Custom (
			creditCurveName,
			effectiveDate,
			creditDefaultSwapArray,
			mergedDiscountForwardCurve,
			quoteArray,
			calibrationMeasureArray,
			"CAD".equalsIgnoreCase (currency) || "EUR".equalsIgnoreCase (currency) ||
				"GBP".equalsIgnoreCase (currency) || "HKD".equalsIgnoreCase (currency) ||
				"USD".equalsIgnoreCase (currency) ? 0.40 : 0.25,
			"QuotedSpread".equals (calibrationMeasure)
		);
	}

	/**
	 * Construct a Credit Curve from the specified Calibration CDS Instruments
	 * 
	 * @param spotDate Spot Date
	 * @param creditDefaultSwapArray Array of the Calibration CDS Instruments
	 * @param quoteArray Array of Market Quotes
	 * @param calibrationMeasure Calibration Measure
	 * @param mergedDiscountForwardCurve Discount Curve
	 * 
	 * @return The Credit Curve Instance
	 */

	public static final CreditCurve CreditCurve (
		final JulianDate spotDate,
		final CreditDefaultSwap[] creditDefaultSwapArray,
		final double[] quoteArray,
		final String calibrationMeasure,
		final MergedDiscountForwardCurve mergedDiscountForwardCurve)
	{
		if (null == spotDate || null == mergedDiscountForwardCurve) {
			return null;
		}

		String currency = mergedDiscountForwardCurve.currency();

		if (null == creditDefaultSwapArray) {
			return null;
		}

		String[] calibrationMeasureArray = new String[creditDefaultSwapArray.length];

		if (0 == creditDefaultSwapArray.length) {
			return null;
		}

		for (int i = 0; i < creditDefaultSwapArray.length; ++i) {
			calibrationMeasureArray[i] = calibrationMeasure;
		}

		return ScenarioCreditCurveBuilder.Custom (
			creditDefaultSwapArray[0].creditLabel().referenceEntity(),
			spotDate,
			creditDefaultSwapArray,
			mergedDiscountForwardCurve,
			quoteArray, 
			calibrationMeasureArray,
			"CAD".equalsIgnoreCase (currency) || "EUR".equalsIgnoreCase (currency) ||
				"GBP".equalsIgnoreCase (currency) || "HKD".equalsIgnoreCase (currency) ||
					"USD".equalsIgnoreCase (currency) ? 0.40 : 0.25,
			"QuotedSpread".equals (calibrationMeasure)
		);
	}

	/**
	 * Construct a Govvie Curve from the Treasury Instruments
	 * 
	 * @param treasuryCode Treasury Code
	 * @param spotDate Spot Date
	 * @param effectiveDateArray Array of Effective Dates
	 * @param maturityDateArray Array of Maturity Dates
	 * @param couponArray Array of Coupons
	 * @param quoteArray Array of Market Quotes
	 * @param calibrationMeasure Calibration Measure
	 * @param segmentCustomBuilderControl Segment Custom Builder Control Parameters
	 * 
	 * @return The Govvie Curve Instance
	 */

	public static final GovvieCurve GovvieCurve (
		final String treasuryCode,
		final JulianDate spotDate,
		final JulianDate[] effectiveDateArray,
		final JulianDate[] maturityDateArray,
		final double[] couponArray,
		final double[] quoteArray,
		final String calibrationMeasure,
		final SegmentCustomBuilderControl segmentCustomBuilderControl)
	{
		BondComponent[] treasuryComponentArray = TreasuryBuilder.FromCode (
			treasuryCode,
			effectiveDateArray,
			maturityDateArray,
			couponArray
			);

		if (null == treasuryComponentArray) {
			return null;
		}

		int[] treasuryMaturityDateArray = new int[treasuryComponentArray.length];

		if (0 == treasuryComponentArray.length || quoteArray.length != treasuryComponentArray.length) {
			return null;
		}

		for (int i = 0; i < treasuryComponentArray.length; ++i) {
			treasuryMaturityDateArray[i] = maturityDateArray[i].julian();
		}

		String currency = treasuryComponentArray[0].currency();

		String benchmarkTreasuryCode = TreasurySettingContainer.CurrencyBenchmarkCode (currency);

		return null == benchmarkTreasuryCode || benchmarkTreasuryCode.isEmpty() ? null :
			ScenarioGovvieCurveBuilder.CustomSplineCurve (
				benchmarkTreasuryCode,
				spotDate,
				benchmarkTreasuryCode,
				currency,
				treasuryMaturityDateArray,
				quoteArray,
				segmentCustomBuilderControl
			);
	}

	/**
	 * Construct a Shape Preserving Govvie Curve from the Treasury Instruments
	 * 
	 * @param treasuryCode Treasury Code
	 * @param spotDate Spot Date
	 * @param effectiveDateArray Array of Effective Dates
	 * @param maturityDateArray Array of Maturity Dates
	 * @param couponArray Array of Coupons
	 * @param quoteArray Array of Market Quotes
	 * @param calibrationMeasure Calibration Measure
	 * 
	 * @return The Govvie Curve Instance
	 */

	public static final GovvieCurve ShapePreservingGovvieCurve (
		final String treasuryCode,
		final JulianDate spotDate,
		final JulianDate[] effectiveDateArray,
		final JulianDate[] maturityDateArray,
		final double[] couponArray,
		final double[] quoteArray,
		final String calibrationMeasure)
	{
		try {
			return GovvieCurve (
				treasuryCode,
				spotDate,
				effectiveDateArray,
				maturityDateArray,
				couponArray,
				quoteArray,
				calibrationMeasure,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new PolynomialFunctionSetParams (2),
					SegmentInelasticDesignControl.Create (0, 2),
					null,
					null
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Smooth Govvie Curve from the Treasury Instruments
	 * 
	 * @param treasuryCode Treasury Code
	 * @param spotDate Spot Date
	 * @param effectiveDateArray Array of Effective Dates
	 * @param maturityDateArray Array of Maturity Dates
	 * @param couponArray Array of Coupons
	 * @param quoteArray Array of Market Quotes
	 * @param calibrationMeasure Calibration Measure
	 * 
	 * @return The Govvie Curve Instance
	 */

	public static final GovvieCurve SmoothGovvieCurve (
		final String treasuryCode,
		final JulianDate spotDate,
		final JulianDate[] effectiveDateArray,
		final JulianDate[] maturityDateArray,
		final double[] couponArray,
		final double[] quoteArray,
		final String calibrationMeasure)
	{
		try {
			return GovvieCurve (
				treasuryCode,
				spotDate,
				effectiveDateArray,
				maturityDateArray,
				couponArray,
				quoteArray,
				calibrationMeasure,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new PolynomialFunctionSetParams (4),
					SegmentInelasticDesignControl.Create (2, 2),
					null,
					null
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Govvie Curve from the Treasury Instruments
	 * 
	 * @param treasuryCode Treasury Code
	 * @param spotDate Spot Date
	 * @param effectiveDateArray Array of Effective Dates
	 * @param maturityDateArray Array of Maturity Dates
	 * @param couponArray Array of Coupons
	 * @param quoteArray Array of Market Quotes
	 * @param calibrationMeasure Calibration Measure
	 * @param latentStateType SHAPE PRESERVING/SMOOTH
	 * 
	 * @return The Govvie Curve Instance
	 */

	public static final GovvieCurve GovvieCurve (
		final String treasuryCode,
		final JulianDate spotDate,
		final JulianDate[] effectiveDateArray,
		final JulianDate[] maturityDateArray,
		final double[] couponArray,
		final double[] quoteArray,
		final String calibrationMeasure,
		final int latentStateType)
	{
		if (SHAPE_PRESERVING == latentStateType) {
			return ShapePreservingGovvieCurve (
				treasuryCode,
				spotDate,
				effectiveDateArray,
				maturityDateArray,
				couponArray,
				quoteArray,
				calibrationMeasure
			);
		}

		if (SMOOTH == latentStateType) {
			return SmoothGovvieCurve (
				treasuryCode,
				spotDate,
				effectiveDateArray,
				maturityDateArray,
				couponArray,
				quoteArray,
				calibrationMeasure
			);
		}

		return null;
	}

	/**
	 * Construct an FX Curve from the FX Forward Instruments
	 * 
	 * @param spotDate Spot Date
	 * @param currencyPair The FX Currency Pair
	 * @param maturityTenorArray Array of Maturity Tenors
	 * @param quoteArray Array of FX Forwards
	 * @param calibrationMeasure Calibration Measure
	 * @param fxSpot FX Spot
	 * @param segmentCustomBuilderControl Segment Custom Builder Builder Parameters
	 * 
	 * @return The FX Curve Instance
	 */

	public static final FXCurve FXCurve (
		final JulianDate spotDate,
		final CurrencyPair currencyPair,
		final String[] maturityTenorArray,
		final double[] quoteArray,
		final String calibrationMeasure,
		final double fxSpot,
		final SegmentCustomBuilderControl segmentCustomBuilderControl)
	{
		if (null == spotDate || null == currencyPair) {
			return null;
		}

		JulianDate effectiveDate = spotDate.addBusDays (0, currencyPair.denomCcy());

		FXForwardComponent[] fxForwardComponentArray = OTCInstrumentBuilder.FXForward (
			effectiveDate,
			currencyPair,
			maturityTenorArray
			);

		return null == fxForwardComponentArray ||
			0 == fxForwardComponentArray.length ||
			quoteArray.length != fxForwardComponentArray.length ?
			null : ScenarioFXCurveBuilder.ShapePreservingFXCurve (
				currencyPair.code(),
				currencyPair,
				ValuationParams.Spot (effectiveDate.julian()),
				null,
				null,
				null,
				fxForwardComponentArray,
				calibrationMeasure,
				quoteArray,
				fxSpot,
				segmentCustomBuilderControl
			);
	}

	/**
	 * Construct a Shape Preserving FX Curve from the FX Forward Instruments
	 * 
	 * @param spotDate Spot Date
	 * @param currencyPair The FX Currency Pair
	 * @param maturityTenorArray Array of Maturity Tenors
	 * @param quoteArray Array of FX Forwards
	 * @param calibrationMeasure Calibration Measure
	 * @param fxSpot FX Spot
	 * 
	 * @return The FX Curve Instance
	 */

	public static final FXCurve ShapePreservingFXCurve (
		final JulianDate spotDate,
		final CurrencyPair currencyPair,
		final String[] maturityTenorArray,
		final double[] quoteArray,
		final String calibrationMeasure,
		final double fxSpot)
	{
		try {
			return FXCurve (
				spotDate,
				currencyPair,
				maturityTenorArray,
				quoteArray,
				calibrationMeasure,
				fxSpot, new
				SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new PolynomialFunctionSetParams (2),
					SegmentInelasticDesignControl.Create (0, 2),
					null,
					null
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Smooth FX Curve from the FX Forward Instruments
	 * 
	 * @param spotDate Spot Date
	 * @param currencyPair The FX Currency Pair
	 * @param maturityTenorArray Array of Maturity Tenors
	 * @param quoteArray Array of FX Forwards
	 * @param calibrationMeasure Calibration Measure
	 * @param fxSpot FX Spot
	 * 
	 * @return The FX Curve Instance
	 */

	public static final FXCurve SmoothFXCurve (
		final JulianDate spotDate,
		final CurrencyPair currencyPair,
		final String[] maturityTenorArray,
		final double[] quoteArray,
		final String calibrationMeasure,
		final double fxSpot)
	{
		try {
			return FXCurve (
				spotDate,
				currencyPair,
				maturityTenorArray,
				quoteArray,
				calibrationMeasure,
				fxSpot, new
				SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new PolynomialFunctionSetParams (4),
					SegmentInelasticDesignControl.Create (2, 2),
					null,
					null
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an FX Curve from the FX Forward Instruments
	 * 
	 * @param spotDate Spot Date
	 * @param currencyPair The FX Currency Pair
	 * @param maturityTenorArray Array of Maturity Tenors
	 * @param quoteArray Array of FX Forwards
	 * @param calibrationMeasure Calibration Measure
	 * @param fxSpot FX Spot
	 * @param latentStateType SHAPE PRESERVING/SMOOTH
	 * 
	 * @return The FX Curve Instance
	 */

	public static final FXCurve FXCurve (
		final JulianDate spotDate,
		final CurrencyPair currencyPair,
		final String[] maturityTenorArray,
		final double[] quoteArray,
		final String calibrationMeasure,
		final double fxSpot,
		final int latentStateType)
	{
		if (SHAPE_PRESERVING == latentStateType) {
			return ShapePreservingFXCurve (
				spotDate,
				currencyPair,
				maturityTenorArray,
				quoteArray,
				calibrationMeasure,
				fxSpot
			);
		}

		if (SMOOTH == latentStateType) {
			return SmoothFXCurve (
				spotDate,
				currencyPair,
				maturityTenorArray,
				quoteArray,
				calibrationMeasure,
				fxSpot
			);
		}

		return null;
	}

	/**
	 * Forward Rate Volatility Latent State Construction from Cap/Floor Instruments
	 * 
	 * @param spotDate Spot Date
	 * @param forwardLabel Forward Label
	 * @param isCap TRUE - Create and Use Array of Caps
	 * @param maturityTenorArray Array of Cap/floor Maturities
	 * @param strikeArray Array of Cap/Floor Strikes
	 * @param quoteArray Array of Cap/Floor Quotes
	 * @param calibrationMeasure Calibration Measure
	 * @param mergedDiscountForwardCurve Discount Curve Instance
	 * @param forwardCurve Forward Curve Instance
	 * 
	 * @return Instance of the Forward Rate Volatility Curve
	 */

	public static final VolatilityCurve ForwardRateVolatilityCurve (
		final JulianDate spotDate,
		final ForwardLabel forwardLabel,
		final boolean isCap,
		final String[] maturityTenorArray,
		final double[] strikeArray,
		final double[] quoteArray,
		final String calibrationMeasure,
		final MergedDiscountForwardCurve mergedDiscountForwardCurve,
		final ForwardCurve forwardCurve)
	{
		if (null == spotDate || null == maturityTenorArray || null == mergedDiscountForwardCurve) {
			return null;
		}

		JulianDate effectiveDate = spotDate.addBusDays (0, mergedDiscountForwardCurve.currency());

		String[] calibrationMeasureArray = new String[maturityTenorArray.length];

		if (0 == maturityTenorArray.length) {
			return null;
		}

		for (int maturityTenorIndex = 0;
			maturityTenorIndex < maturityTenorArray.length;
			++maturityTenorIndex)
		{
			calibrationMeasureArray[maturityTenorIndex] = calibrationMeasure;
		}

		return ScenarioLocalVolatilityBuilder.NonlinearBuild (
			forwardLabel.fullyQualifiedName() + "::VOL",
			effectiveDate,
			forwardLabel,
			OTCInstrumentBuilder.CapFloor (
				effectiveDate,
				forwardLabel,
				maturityTenorArray,
				strikeArray,
				isCap
			),
			quoteArray,
			calibrationMeasureArray,
			mergedDiscountForwardCurve,
			forwardCurve,
			null
		);
	}

	/**
	 * Construct a Map of Tenor Bumped Funding Curve Based off of the Input Exchange/OTC Market Instruments
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
	 * @param bumpAmount The Tenor Node Bump Amount
	 * @param isProportional TRUE - The Bump Applied is Proportional
	 * 
	 * @return The Tenor Bumped Funding Curve Map
	 */

	public static final CaseInsensitiveTreeMap<MergedDiscountForwardCurve> BumpedFundingCurve (
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
		final int latentStateType,
		final double bumpAmount,
		final boolean isProportional)
	{
		if (!NumberUtil.IsValid (bumpAmount)) {
			return null;
		}

		CaseInsensitiveTreeMap<MergedDiscountForwardCurve> bumpedMergedDiscountForwardCurveMap =
			new CaseInsensitiveTreeMap<MergedDiscountForwardCurve>();

		try {
			ManifestMeasureTweak flatManifestMeasureTweak = new ManifestMeasureTweak (
				ManifestMeasureTweak.FLAT,
				isProportional,
				bumpAmount
			);

			if (null != depositQuoteArray) {
				for (int depositInstrumentIndex = 0;
					depositInstrumentIndex < depositQuoteArray.length;
					++depositInstrumentIndex)
				{
					MergedDiscountForwardCurve depositQuoteBumpedMergedDiscountForwardCurve = FundingCurve (
						spotDate,
						currency,
						depositMaturityTenorArray,
						Helper.TweakManifestMeasure (
							depositQuoteArray,
							new ManifestMeasureTweak (depositInstrumentIndex, isProportional, bumpAmount)
						),
						depositMeasure,
						futuresQuoteArray,
						futuresMeasure,
						fixFloatMaturityTenorArray,
						fixFloatQuoteArray,
						fixFloatMeasure,
						latentStateType
					);

					if (null != depositQuoteBumpedMergedDiscountForwardCurve) {
						bumpedMergedDiscountForwardCurveMap.put (
							"DEPOSIT::" + depositMaturityTenorArray[depositInstrumentIndex],
							depositQuoteBumpedMergedDiscountForwardCurve
						);
					}
				}
			}

			double[] parallelBumpedDepositQuote = Helper.TweakManifestMeasure (
				depositQuoteArray,
				flatManifestMeasureTweak
			);

			MergedDiscountForwardCurve depositQuoteBumpedMergedDiscountForwardCurve = FundingCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				parallelBumpedDepositQuote,
				depositMeasure,
				futuresQuoteArray,
				futuresMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				fixFloatMeasure,
				latentStateType
			);

			if (null != depositQuoteBumpedMergedDiscountForwardCurve) {
				bumpedMergedDiscountForwardCurveMap.put (
					"DEPOSIT::PLL",
					depositQuoteBumpedMergedDiscountForwardCurve
				);
			}

			if (null != futuresQuoteArray) {
				for (int futuresQuoteIndex = 0;
					futuresQuoteIndex < futuresQuoteArray.length;
					++futuresQuoteIndex)
				{
					MergedDiscountForwardCurve futuresQuoteBumpedMergedDiscountForwardCurve = FundingCurve (
						spotDate,
						currency,
						depositMaturityTenorArray,
						depositQuoteArray,
						depositMeasure,
						Helper.TweakManifestMeasure (
							futuresQuoteArray,
							new ManifestMeasureTweak (futuresQuoteIndex, isProportional, bumpAmount)
						),
						futuresMeasure,
						fixFloatMaturityTenorArray,
						fixFloatQuoteArray,
						fixFloatMeasure,
						latentStateType
					);

					if (null != futuresQuoteBumpedMergedDiscountForwardCurve) {
						bumpedMergedDiscountForwardCurveMap.put (
							"FUTURES::" + futuresQuoteIndex,
							futuresQuoteBumpedMergedDiscountForwardCurve
						);
					}
				}
			}

			double[] parallelBumpedFuturesQuote = Helper.TweakManifestMeasure (
				futuresQuoteArray,
				flatManifestMeasureTweak
			);

			MergedDiscountForwardCurve futuresQuoteBumpedMergedDiscountForwardCurve = FundingCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				parallelBumpedFuturesQuote,
				futuresMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				fixFloatMeasure,
				latentStateType
			);

			if (null != futuresQuoteBumpedMergedDiscountForwardCurve) {
				bumpedMergedDiscountForwardCurveMap.put (
					"FUTURES::P",
					futuresQuoteBumpedMergedDiscountForwardCurve
				);
			}

			if (null != fixFloatQuoteArray) {
				for (int i = 0; i < fixFloatQuoteArray.length; ++i) {
					MergedDiscountForwardCurve fixFloatQuoteBumpedMergedDiscountForwardCurve = FundingCurve (
						spotDate,
						currency,
						depositMaturityTenorArray,
						depositQuoteArray,
						depositMeasure,
						futuresQuoteArray,
						futuresMeasure,
						fixFloatMaturityTenorArray,
						Helper.TweakManifestMeasure (
							fixFloatQuoteArray,
							new ManifestMeasureTweak (i, isProportional, bumpAmount)
						),
						fixFloatMeasure,
						latentStateType
					);

					if (null != fixFloatQuoteBumpedMergedDiscountForwardCurve) {
						bumpedMergedDiscountForwardCurveMap.put (
							"FIXFLOAT::" + fixFloatMaturityTenorArray[i],
							fixFloatQuoteBumpedMergedDiscountForwardCurve
						);
					}
				}

				double[] fixFloatQuoteParallelBumpArray = Helper.TweakManifestMeasure (
					fixFloatQuoteArray,
					flatManifestMeasureTweak
				);

				MergedDiscountForwardCurve fixFloatQuoteBumpedMergedDiscountForwardCurve = FundingCurve (
					spotDate,
					currency,
					depositMaturityTenorArray,
					depositQuoteArray,
					depositMeasure,
					futuresQuoteArray,
					futuresMeasure,
					fixFloatMaturityTenorArray,
					fixFloatQuoteParallelBumpArray,
					fixFloatMeasure,
					latentStateType
				);

				if (null != fixFloatQuoteBumpedMergedDiscountForwardCurve) {
					bumpedMergedDiscountForwardCurveMap.put (
						"FIXFLOAT::PLL",
						fixFloatQuoteBumpedMergedDiscountForwardCurve
					);
				}

				MergedDiscountForwardCurve mergedDiscountForwardCurveBaseFundingCurve = FundingCurve (
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
					latentStateType
				);

				if (null != mergedDiscountForwardCurveBaseFundingCurve) {
					bumpedMergedDiscountForwardCurveMap.put (
						"BASE",
						mergedDiscountForwardCurveBaseFundingCurve
					);
				}

				MergedDiscountForwardCurve mergedDiscountForwardCurveBumpedFundingCurve = FundingCurve (
					spotDate,
					currency,
					depositMaturityTenorArray,
					parallelBumpedDepositQuote,
					depositMeasure,
					parallelBumpedFuturesQuote,
					futuresMeasure,
					fixFloatMaturityTenorArray,
					fixFloatQuoteParallelBumpArray,
					fixFloatMeasure,
					latentStateType
				);

				if (null != mergedDiscountForwardCurveBumpedFundingCurve) {
					bumpedMergedDiscountForwardCurveMap.put (
						"BUMP",
						mergedDiscountForwardCurveBumpedFundingCurve
					);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bumpedMergedDiscountForwardCurveMap;
	}

	/**
	 * Construct a Map of Tenor Bumped Funding Curve Based off of the Underlying Forward Curve Shift
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
	 * @param bumpAmount The Tenor Node Bump Amount
	 * @param isProportional TRUE - The Bump Applied is Proportional
	 * 
	 * @return The Tenor Bumped Funding Curve Map
	 */

	public static final CaseInsensitiveTreeMap<MergedDiscountForwardCurve> BumpedForwardFundingCurve (
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
		final int latentStateType,
		final double bumpAmount,
		final boolean isProportional)
	{
		if (!NumberUtil.IsValid (bumpAmount)) {
			return null;
		}

		CaseInsensitiveTreeMap<MergedDiscountForwardCurve> bumpedMergedDiscountForwardCurveMap =
			new CaseInsensitiveTreeMap<MergedDiscountForwardCurve>();

		MergedDiscountForwardCurve baseFundingMergedDiscountForwardCurve = SingleStretchFundingCurve (
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
			latentStateType
		);

		if (null == baseFundingMergedDiscountForwardCurve) {
			return null;
		}

		int depositCount = null == depositMaturityTenorArray ? 0 : depositMaturityTenorArray.length;
		int fixFloatCount = null == fixFloatQuoteArray ? 0 : fixFloatQuoteArray.length;
		int futuresCount = null == futuresQuoteArray ? 0 : futuresQuoteArray.length;
		int depositFuturesCount = depositCount + futuresCount;
		int iNumDepositFuturesFixFloat = depositFuturesCount + fixFloatCount;
		int[] depositFuturesFixFloatMaturityArray = new int[iNumDepositFuturesFixFloat];

		SingleStreamComponent[] singleStreamComponentArray =
			ExchangeInstrumentBuilder.ForwardRateFuturesPack (
				spotDate,
				futuresCount,
				currency
			);

		for (int depositIndex = 0; depositIndex < depositCount; ++depositIndex) {
			depositFuturesFixFloatMaturityArray[depositIndex] = spotDate.addTenor (
				depositMaturityTenorArray[depositIndex]
			).julian();
		}

		for (int depositFuturesIndex = depositCount;
			depositFuturesIndex < depositFuturesCount;
			++depositFuturesIndex)
		{
			depositFuturesFixFloatMaturityArray[depositFuturesIndex] =
				singleStreamComponentArray[depositFuturesIndex - depositCount].maturityDate().julian();
		}

		for (int depositFuturesFixFloatIndex = depositFuturesCount;
			depositFuturesFixFloatIndex < iNumDepositFuturesFixFloat;
			++depositFuturesFixFloatIndex)
		{
			depositFuturesFixFloatMaturityArray[depositFuturesFixFloatIndex] = spotDate.addTenor (
				fixFloatMaturityTenorArray[depositFuturesFixFloatIndex - depositFuturesCount]
			).julian();
		}

		FlatForwardDiscountCurve flatForwardDiscountCurve =
			baseFundingMergedDiscountForwardCurve.flatNativeForward (
				depositFuturesFixFloatMaturityArray,
				0.
			);

		if (null == flatForwardDiscountCurve) {
			return null;
		}

		bumpedMergedDiscountForwardCurveMap.put ("base", flatForwardDiscountCurve);

		FlatForwardDiscountCurve bumpedFlatForwardDiscountCurve =
			baseFundingMergedDiscountForwardCurve.flatNativeForward (
				depositFuturesFixFloatMaturityArray,
				bumpAmount
			);

		if (null == bumpedFlatForwardDiscountCurve) {
			return null;
		}

		bumpedMergedDiscountForwardCurveMap.put ("bump", bumpedFlatForwardDiscountCurve);

		for (int depositFuturesFixFloatIndex = 0;
			depositFuturesFixFloatIndex < iNumDepositFuturesFixFloat;
			++depositFuturesFixFloatIndex)
		{
			FlatForwardDiscountCurve tenorBumpedFlatForwardDiscountCurve =
				baseFundingMergedDiscountForwardCurve.flatNativeForwardEI (
					depositFuturesFixFloatMaturityArray,
					depositFuturesFixFloatIndex,
					bumpAmount
				);

			if (null == tenorBumpedFlatForwardDiscountCurve) {
				return null;
			}

			bumpedMergedDiscountForwardCurveMap.put (
				"tenor::" + depositFuturesFixFloatIndex,
				tenorBumpedFlatForwardDiscountCurve
			);
		}

		return bumpedMergedDiscountForwardCurveMap;
	}

	/**
	 * Construct a Map of Tenor Bumped Forward Curve Based off of the Input Exchange/OTC Market Instruments
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
	 * @param latentStateType SHAPE_PRESERVING/SMOOTH
	 * @param bumpAmount The Tenor Node Bump Amount
	 * @param isProportional TRUE - The Bump Applied is Proportional
	 * 
	 * @return The Tenor Bumped Forward Curve Map
	 */

	public static final CaseInsensitiveTreeMap<ForwardCurve> BumpedForwardCurve (
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
		final int latentStateType,
		final double bumpAmount,
		final boolean isProportional)
	{
		if (!NumberUtil.IsValid (bumpAmount)) {
			return null;
		}

		CaseInsensitiveTreeMap<ForwardCurve> bumpedForwardCurveMap =
			new CaseInsensitiveTreeMap<ForwardCurve>();

		try {
			ManifestMeasureTweak flatManifestMeasureTweak = new ManifestMeasureTweak (
				ManifestMeasureTweak.FLAT,
				isProportional,
				bumpAmount
			);

			if (null != depositQuoteArray) {
				for (int depositQuoteIndex = 0;
					depositQuoteIndex < depositQuoteArray.length;
					++depositQuoteIndex)
				{
					ForwardCurve depositQuoteBumpedForwardCurve = ForwardCurve (
						spotDate,
						forwardLabel,
						depositMaturityTenorArray,
						Helper.TweakManifestMeasure (
							depositQuoteArray,
							new ManifestMeasureTweak (depositQuoteIndex, isProportional, bumpAmount)
						),
						depositMeasure,
						fraMaturityTenorArray,
						fraQuoteArray,
						fraMeasure,
						fixFloatMaturityTenorArray,
						fixFloatQuoteArray,
						fixFloatMeasure,
						floatFloatMaturityTenorArray,
						floatFloatQuoteArray,
						floatFloatMeasure,
						syntheticFloatFloatMaturityTenorArray,
						syntheticFloatFloatQuoteArray,
						syntheticFloatFloatMeasure,
						mergedDiscountForwardCurve,
						referenceForwardCurve,
						latentStateType
					);

					if (null != depositQuoteBumpedForwardCurve) {
						bumpedForwardCurveMap.put (
							"DEPOSIT::" + depositMaturityTenorArray[depositQuoteIndex],
							depositQuoteBumpedForwardCurve
						);
					}
				}
			}

			double[] parallelBumpedDepositQuoteArray = Helper.TweakManifestMeasure (
				depositQuoteArray,
				flatManifestMeasureTweak
			);

			ForwardCurve depositQuoteBumpedForwardCurve = ForwardCurve (
				spotDate,
				forwardLabel,
				depositMaturityTenorArray,
				parallelBumpedDepositQuoteArray,
				depositMeasure,
				fraMaturityTenorArray,
				fraQuoteArray,
				fraMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				fixFloatMeasure,
				floatFloatMaturityTenorArray,
				floatFloatQuoteArray,
				floatFloatMeasure,
				syntheticFloatFloatMaturityTenorArray,
				syntheticFloatFloatQuoteArray,
				syntheticFloatFloatMeasure,
				mergedDiscountForwardCurve,
				referenceForwardCurve,
				latentStateType
			);

			if (null != depositQuoteBumpedForwardCurve) {
				bumpedForwardCurveMap.put ("DEPOSIT::PLL", depositQuoteBumpedForwardCurve);
			}

			if (null != fraQuoteArray) {
				for (int fraQuoteIndex = 0; fraQuoteIndex < fraQuoteArray.length; ++fraQuoteIndex) {
					ForwardCurve fraQuoteBumpedForwardCurve = ForwardCurve (
						spotDate,
						forwardLabel,
						depositMaturityTenorArray,
						depositQuoteArray,
						depositMeasure,
						fraMaturityTenorArray,
						Helper.TweakManifestMeasure (
							fraQuoteArray,
							new ManifestMeasureTweak (fraQuoteIndex, isProportional, bumpAmount)
						),
						fraMeasure,
						fixFloatMaturityTenorArray,
						fixFloatQuoteArray,
						fixFloatMeasure,
						floatFloatMaturityTenorArray,
						floatFloatQuoteArray,
						floatFloatMeasure,
						syntheticFloatFloatMaturityTenorArray,
						syntheticFloatFloatQuoteArray,
						syntheticFloatFloatMeasure,
						mergedDiscountForwardCurve,
						referenceForwardCurve,
						latentStateType
					);

					if (null != fraQuoteBumpedForwardCurve) {
						bumpedForwardCurveMap.put (
							"FRA::" + fraMaturityTenorArray[fraQuoteIndex],
							fraQuoteBumpedForwardCurve
						);
					}
				}
			}

			double[] parallelBumpedFRAQuoteArray = Helper.TweakManifestMeasure (
				fraQuoteArray,
				flatManifestMeasureTweak
			);

			ForwardCurve fraQuoteBumpedForwardCurve = ForwardCurve (
				spotDate,
				forwardLabel,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				fraMaturityTenorArray,
				parallelBumpedFRAQuoteArray,
				fraMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				fixFloatMeasure,
				floatFloatMaturityTenorArray,
				floatFloatQuoteArray,
				floatFloatMeasure,
				syntheticFloatFloatMaturityTenorArray,
				syntheticFloatFloatQuoteArray,
				syntheticFloatFloatMeasure,
				mergedDiscountForwardCurve,
				referenceForwardCurve,
				latentStateType
			);

			if (null != fraQuoteBumpedForwardCurve) {
				bumpedForwardCurveMap.put ("FRA::PLL", fraQuoteBumpedForwardCurve);
			}

			if (null != fixFloatQuoteArray) {
				for (int fixFloatIndex = 0; fixFloatIndex < fixFloatQuoteArray.length; ++fixFloatIndex) {
					ForwardCurve fixFloatQuoteBumpedForwardCurve = ForwardCurve (
						spotDate,
						forwardLabel,
						depositMaturityTenorArray,
						depositQuoteArray,
						depositMeasure,
						fraMaturityTenorArray,
						fraQuoteArray,
						fraMeasure,
						fixFloatMaturityTenorArray,
						Helper.TweakManifestMeasure (
							fixFloatQuoteArray,
							new ManifestMeasureTweak (fixFloatIndex, isProportional, bumpAmount)
						),
						fixFloatMeasure,
						floatFloatMaturityTenorArray,
						floatFloatQuoteArray,
						floatFloatMeasure,
						syntheticFloatFloatMaturityTenorArray,
						syntheticFloatFloatQuoteArray,
						syntheticFloatFloatMeasure,
						mergedDiscountForwardCurve,
						referenceForwardCurve,
						latentStateType
					);

					if (null != fixFloatQuoteBumpedForwardCurve) {
						bumpedForwardCurveMap.put (
							"FIXFLOAT::" + fixFloatMaturityTenorArray[fixFloatIndex],
							fixFloatQuoteBumpedForwardCurve
						);
					}
				}
			}

			double[] fixFloatParallelBumpedQuoteArray = Helper.TweakManifestMeasure (
				fixFloatQuoteArray,
				flatManifestMeasureTweak
			);

			ForwardCurve fixFloatQuoteBumpedForwardCurve = ForwardCurve (
				spotDate,
				forwardLabel,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				fraMaturityTenorArray,
				fraQuoteArray,
				fraMeasure,
				fixFloatMaturityTenorArray,
				fixFloatParallelBumpedQuoteArray,
				fixFloatMeasure,
				floatFloatMaturityTenorArray,
				floatFloatQuoteArray,
				floatFloatMeasure,
				syntheticFloatFloatMaturityTenorArray,
				syntheticFloatFloatQuoteArray,
				syntheticFloatFloatMeasure,
				mergedDiscountForwardCurve,
				referenceForwardCurve,
				latentStateType
			);

			if (null != fixFloatQuoteBumpedForwardCurve) {
				bumpedForwardCurveMap.put ("FIXFLOAT::PLL", fixFloatQuoteBumpedForwardCurve);
			}

			if (null != floatFloatQuoteArray) {
				for (int floatFloatQuoteIndex = 0;
					floatFloatQuoteIndex < floatFloatQuoteArray.length;
					++floatFloatQuoteIndex)
				{
					ForwardCurve floatFloatQuoteBumpedForwardCurve = ForwardCurve (
						spotDate,
						forwardLabel,
						depositMaturityTenorArray,
						depositQuoteArray,
						depositMeasure,
						fraMaturityTenorArray,
						fraQuoteArray,
						fraMeasure,
						fixFloatMaturityTenorArray,
						fraQuoteArray,
						fixFloatMeasure,
						floatFloatMaturityTenorArray,
						Helper.TweakManifestMeasure (
							floatFloatQuoteArray,
							new ManifestMeasureTweak (floatFloatQuoteIndex, isProportional, bumpAmount)
						),
						floatFloatMeasure,
						syntheticFloatFloatMaturityTenorArray,
						syntheticFloatFloatQuoteArray,
						syntheticFloatFloatMeasure,
						mergedDiscountForwardCurve,
						referenceForwardCurve,
						latentStateType
					);

					if (null != floatFloatQuoteBumpedForwardCurve) {
						bumpedForwardCurveMap.put (
							"FLOATFLOAT::" + floatFloatMaturityTenorArray[floatFloatQuoteIndex],
							floatFloatQuoteBumpedForwardCurve
						);
					}
				}
			}

			double[] floatFloatParallelBumpedQuoteArray = Helper.TweakManifestMeasure (
				floatFloatQuoteArray,
				flatManifestMeasureTweak
			);

			ForwardCurve floatFloatQuoteBumpedForwardCurve = ForwardCurve (
				spotDate,
				forwardLabel,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				fraMaturityTenorArray,
				fraQuoteArray,
				fraMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				fixFloatMeasure,
				floatFloatMaturityTenorArray,
				floatFloatParallelBumpedQuoteArray,
				floatFloatMeasure,
				syntheticFloatFloatMaturityTenorArray,
				syntheticFloatFloatQuoteArray,
				syntheticFloatFloatMeasure,
				mergedDiscountForwardCurve,
				referenceForwardCurve,
				latentStateType
			);

			if (null != floatFloatQuoteBumpedForwardCurve) {
				bumpedForwardCurveMap.put ("FLOATFLOAT::PLL", floatFloatQuoteBumpedForwardCurve);
			}

			if (null != syntheticFloatFloatQuoteArray) {
				for (int syntheticFloatFloatQuoteIndex = 0;
					syntheticFloatFloatQuoteIndex < syntheticFloatFloatQuoteArray.length;
					++syntheticFloatFloatQuoteIndex)
				{
					ForwardCurve syntheticFloatFloatQuoteBumpedForwardCurve = ForwardCurve (
						spotDate,
						forwardLabel,
						depositMaturityTenorArray,
						depositQuoteArray,
						depositMeasure,
						fraMaturityTenorArray,
						fraQuoteArray,
						fraMeasure,
						fixFloatMaturityTenorArray,
						fixFloatQuoteArray,
						fixFloatMeasure,
						floatFloatMaturityTenorArray,
						floatFloatQuoteArray,
						floatFloatMeasure,
						syntheticFloatFloatMaturityTenorArray,
						Helper.TweakManifestMeasure (
							syntheticFloatFloatQuoteArray,
							new ManifestMeasureTweak (
								syntheticFloatFloatQuoteIndex,
								isProportional,
								bumpAmount
							)
						),
						syntheticFloatFloatMeasure,
						mergedDiscountForwardCurve,
						referenceForwardCurve,
						latentStateType
					);

					if (null != syntheticFloatFloatQuoteBumpedForwardCurve) {
						bumpedForwardCurveMap.put (
							"SYNTHETICFLOATFLOAT::" +
								syntheticFloatFloatMaturityTenorArray[syntheticFloatFloatQuoteIndex],
							syntheticFloatFloatQuoteBumpedForwardCurve
						);
					}
				}
			}

			double[] syntheticFloatFloatParallelBumpArray = Helper.TweakManifestMeasure (
				syntheticFloatFloatQuoteArray,
				flatManifestMeasureTweak
			);

			ForwardCurve syntheticFloatFloatQuoteBumpedForwardCurve = ForwardCurve (
				spotDate,
				forwardLabel,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				fraMaturityTenorArray,
				fraQuoteArray,
				fraMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				fixFloatMeasure, 
				floatFloatMaturityTenorArray,
				floatFloatQuoteArray,
				floatFloatMeasure,
				syntheticFloatFloatMaturityTenorArray,
				syntheticFloatFloatParallelBumpArray,
				syntheticFloatFloatMeasure,
				mergedDiscountForwardCurve,
				referenceForwardCurve,
				latentStateType
			);

			if (null != syntheticFloatFloatQuoteBumpedForwardCurve) {
				bumpedForwardCurveMap.put (
					"SYNTHETICFLOATFLOAT::PLL",
					syntheticFloatFloatQuoteBumpedForwardCurve
				);
			}

			ForwardCurve baseForwardCurve = ForwardCurve (
				spotDate,
				forwardLabel,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				fraMaturityTenorArray,
				fraQuoteArray,
				fraMeasure,
				fixFloatMaturityTenorArray,
				fixFloatQuoteArray,
				fixFloatMeasure,
				floatFloatMaturityTenorArray,
				floatFloatQuoteArray,
				floatFloatMeasure,
				syntheticFloatFloatMaturityTenorArray,
				syntheticFloatFloatQuoteArray,
				syntheticFloatFloatMeasure,
				mergedDiscountForwardCurve,
				referenceForwardCurve,
				latentStateType
			);

			if (null != baseForwardCurve) {
				bumpedForwardCurveMap.put ("BASE", baseForwardCurve);
			}

			ForwardCurve quoteBumpedForwardCurve = ForwardCurve (
				spotDate,
				forwardLabel,
				depositMaturityTenorArray,
				parallelBumpedDepositQuoteArray,
				depositMeasure,
				fraMaturityTenorArray,
				parallelBumpedFRAQuoteArray,
				fraMeasure,
				fixFloatMaturityTenorArray,
				fixFloatParallelBumpedQuoteArray,
				fixFloatMeasure,
				floatFloatMaturityTenorArray,
				floatFloatParallelBumpedQuoteArray,
				floatFloatMeasure,
				syntheticFloatFloatMaturityTenorArray,
				syntheticFloatFloatParallelBumpArray,
				syntheticFloatFloatMeasure,
				mergedDiscountForwardCurve,
				referenceForwardCurve,
				latentStateType
			);

			if (null != quoteBumpedForwardCurve) {
				bumpedForwardCurveMap.put ("BUMP", quoteBumpedForwardCurve);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bumpedForwardCurveMap;
	}

	/**
	 * Construct a Map of Tenor + Parallel Bumped Overnight Curves
	 * 
	 * @param spotDate Spot Date
	 * @param currency Currency
	 * @param depositMaturityTenorArray Array of Deposit Maturity Tenors
	 * @param depositQuoteArray Array of Deposit Quotes
	 * @param depositMeasure Deposit Measure
	 * @param shortEndOISMaturityTenorArray Array of Short End OIS Maturity Tenors
	 * @param shortEndOISQuoteArray Array of Short End OIS Quotes
	 * @param shortEndOISMeasure Short End OIS Measure
	 * @param oisFuturesEffectiveTenorArray Array of OIS Futures Effective Tenors
	 * @param oisFuturesMaturityTenorArray Array of OIS Futures Maturity Tenors
	 * @param oisFuturesQuoteArray Array of OIS Futures Quotes
	 * @param oisFuturesMeasure OIS Futures Measure
	 * @param longEndOISMaturityTenorArray Array of Long End OIS Maturity Tenors
	 * @param longEndOISQuoteArray Array of Long End OIS Quotes
	 * @param longEndOISMeasure Long End OIS Measure
	 * @param latentStateType SHAPE PRESERVING/SMOOTH
	 * @param bumpAmount The Tenor Node Bump Amount
	 * @param isProportional TRUE - The Bump Applied is Proportional
	 * 
	 * @return Map of Overnight Curves
	 */

	public static final CaseInsensitiveTreeMap<MergedDiscountForwardCurve> BumpedOvernightCurve (
		final JulianDate spotDate,
		final String currency,
		final String[] depositMaturityTenorArray,
		final double[] depositQuoteArray,
		final String depositMeasure,
		final String[] shortEndOISMaturityTenorArray,
		final double[] shortEndOISQuoteArray,
		final String shortEndOISMeasure,
		final String[] oisFuturesEffectiveTenorArray,
		final String[] oisFuturesMaturityTenorArray,
		final double[] oisFuturesQuoteArray,
		final String oisFuturesMeasure,
		final String[] longEndOISMaturityTenorArray,
		final double[] longEndOISQuoteArray,
		final String longEndOISMeasure,
		final int latentStateType,
		final double bumpAmount,
		final boolean isProportional)
	{
		if (!NumberUtil.IsValid (bumpAmount)) {
			return null;
		}

		CaseInsensitiveTreeMap<MergedDiscountForwardCurve> bumpedMergedDiscountForwardCurveMap =
			new CaseInsensitiveTreeMap<MergedDiscountForwardCurve>();

		try {
			ManifestMeasureTweak flatManifestMeasureTweak = new ManifestMeasureTweak (
				ManifestMeasureTweak.FLAT,
				isProportional,
				bumpAmount
			);

			if (null != depositQuoteArray) {
				for (int depositIndex = 0; depositIndex < depositQuoteArray.length; ++depositIndex) {
					MergedDiscountForwardCurve overnightDepositBumpedMergedDiscountForwardCurve =
						OvernightCurve (
							spotDate,
							currency,
							depositMaturityTenorArray,
							Helper.TweakManifestMeasure (
								depositQuoteArray,
								new ManifestMeasureTweak (depositIndex, isProportional, bumpAmount)
							),
							depositMeasure,
							shortEndOISMaturityTenorArray,
							shortEndOISQuoteArray,
							shortEndOISMeasure,
							oisFuturesEffectiveTenorArray,
							oisFuturesMaturityTenorArray,
							oisFuturesQuoteArray,
							oisFuturesMeasure,
							longEndOISMaturityTenorArray,
							longEndOISQuoteArray,
							longEndOISMeasure,
							latentStateType
						);

					if (null != overnightDepositBumpedMergedDiscountForwardCurve) {
						bumpedMergedDiscountForwardCurveMap.put (
							"DEPOSIT::" + depositMaturityTenorArray[depositIndex],
							overnightDepositBumpedMergedDiscountForwardCurve
						);
					}
				}
			}

			double[] parallelBumpedDepositQuoteArray = Helper.TweakManifestMeasure (
				depositQuoteArray,
				flatManifestMeasureTweak
			);

			MergedDiscountForwardCurve overnightDepositBumpedMergedDiscountForwardCurve = OvernightCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				parallelBumpedDepositQuoteArray,
				depositMeasure,
				shortEndOISMaturityTenorArray,
				shortEndOISQuoteArray,
				shortEndOISMeasure,
				oisFuturesEffectiveTenorArray,
				oisFuturesMaturityTenorArray,
				oisFuturesQuoteArray,
				oisFuturesMeasure,
				longEndOISMaturityTenorArray,
				longEndOISQuoteArray,
				longEndOISMeasure,
				latentStateType
			);

			if (null != overnightDepositBumpedMergedDiscountForwardCurve) {
				bumpedMergedDiscountForwardCurveMap.put (
					"DEPOSIT::PLL",
					overnightDepositBumpedMergedDiscountForwardCurve
				);
			}

			if (null != shortEndOISQuoteArray) {
				for (int shortEndOISIndex = 0;
					shortEndOISIndex < shortEndOISQuoteArray.length;
					++shortEndOISIndex)
				{
					MergedDiscountForwardCurve dcOvernightShortEndOISBumped = OvernightCurve (
						spotDate,
						currency,
						depositMaturityTenorArray,
						depositQuoteArray,
						depositMeasure,
						shortEndOISMaturityTenorArray,
						Helper.TweakManifestMeasure (
							shortEndOISQuoteArray,
							new ManifestMeasureTweak (shortEndOISIndex, isProportional, bumpAmount)
						),
						shortEndOISMeasure,
						oisFuturesEffectiveTenorArray,
						oisFuturesMaturityTenorArray,
						oisFuturesQuoteArray,
						oisFuturesMeasure,
						longEndOISMaturityTenorArray,
						longEndOISQuoteArray,
						longEndOISMeasure,
						latentStateType
					);

					if (null != dcOvernightShortEndOISBumped) {
						bumpedMergedDiscountForwardCurveMap.put (
							"SHORTENDOIS::" + shortEndOISMaturityTenorArray[shortEndOISIndex],
							dcOvernightShortEndOISBumped
						);
					}
				}
			}

			double[] shortEndOISQuoteParallelBumpArray = Helper.TweakManifestMeasure (
				shortEndOISQuoteArray,
				flatManifestMeasureTweak
			);

			MergedDiscountForwardCurve overnightShortEndOISBumpedMergedDiscountForwardCurve =
				OvernightCurve (
					spotDate,
					currency,
					depositMaturityTenorArray,
					depositQuoteArray,
					depositMeasure,
					shortEndOISMaturityTenorArray,
					shortEndOISQuoteParallelBumpArray,
					shortEndOISMeasure,
					oisFuturesEffectiveTenorArray,
					oisFuturesMaturityTenorArray,
					oisFuturesQuoteArray,
					oisFuturesMeasure,
					longEndOISMaturityTenorArray,
					longEndOISQuoteArray,
					longEndOISMeasure,
					latentStateType
				);

			if (null != overnightShortEndOISBumpedMergedDiscountForwardCurve) {
				bumpedMergedDiscountForwardCurveMap.put (
					"SHORTENDOIS::PLL",
					overnightShortEndOISBumpedMergedDiscountForwardCurve
				);
			}

			if (null != oisFuturesQuoteArray) {
				for (int oisFuturesIndex = 0;
					oisFuturesIndex < oisFuturesQuoteArray.length;
					++oisFuturesIndex)
				{
					MergedDiscountForwardCurve overnightOISFuturesBumpedMergedDiscountForwardCurve =
						OvernightCurve (
							spotDate,
							currency,
							depositMaturityTenorArray,
							depositQuoteArray,
							depositMeasure,
							shortEndOISMaturityTenorArray,
							shortEndOISQuoteArray,
							shortEndOISMeasure,
							oisFuturesEffectiveTenorArray,
							oisFuturesMaturityTenorArray,
							Helper.TweakManifestMeasure (
								oisFuturesQuoteArray,
								new ManifestMeasureTweak (oisFuturesIndex, isProportional, bumpAmount)
							),
							oisFuturesMeasure,
							longEndOISMaturityTenorArray,
							longEndOISQuoteArray,
							longEndOISMeasure,
							latentStateType
						);

					if (null != overnightOISFuturesBumpedMergedDiscountForwardCurve) {
						bumpedMergedDiscountForwardCurveMap.put (
							"OISFUTURES::" + oisFuturesEffectiveTenorArray[oisFuturesIndex] + " x " +
								oisFuturesMaturityTenorArray[oisFuturesIndex],
							overnightOISFuturesBumpedMergedDiscountForwardCurve
						);
					}
				}
			}

			double[] oisFuturesParallelBumpedQuoteArray = Helper.TweakManifestMeasure (
				oisFuturesQuoteArray,
				flatManifestMeasureTweak
			);

			MergedDiscountForwardCurve overnightOISFuturesBumpedMergedDiscountForwardCurve = OvernightCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				shortEndOISMaturityTenorArray,
				shortEndOISQuoteArray, shortEndOISMeasure,
				oisFuturesEffectiveTenorArray,
				oisFuturesMaturityTenorArray,
				oisFuturesParallelBumpedQuoteArray,
				oisFuturesMeasure,
				longEndOISMaturityTenorArray,
				longEndOISQuoteArray,
				longEndOISMeasure,
				latentStateType
			);

			if (null != overnightOISFuturesBumpedMergedDiscountForwardCurve) {
				bumpedMergedDiscountForwardCurveMap.put (
					"OISFUTURES::PARALLEL",
					overnightOISFuturesBumpedMergedDiscountForwardCurve
				);
			}

			if (null != longEndOISQuoteArray) {
				for (int longEndOISIndex = 0;
					longEndOISIndex < longEndOISQuoteArray.length;
					++longEndOISIndex)
				{
					MergedDiscountForwardCurve overnightLongEndOISBumpedMergedDiscountForwardCurve =
						OvernightCurve (
							spotDate,
							currency,
							depositMaturityTenorArray,
							depositQuoteArray,
							depositMeasure,
							shortEndOISMaturityTenorArray,
							shortEndOISQuoteArray,
							shortEndOISMeasure,
							oisFuturesEffectiveTenorArray,
							oisFuturesMaturityTenorArray,
							oisFuturesQuoteArray,
							oisFuturesMeasure,
							longEndOISMaturityTenorArray,
							Helper.TweakManifestMeasure (
								longEndOISQuoteArray,
								new ManifestMeasureTweak (longEndOISIndex, isProportional, bumpAmount)
							),
							longEndOISMeasure,
							latentStateType
						);

					if (null != overnightLongEndOISBumpedMergedDiscountForwardCurve) {
						bumpedMergedDiscountForwardCurveMap.put (
							"LONGENDOIS::" + longEndOISMaturityTenorArray[longEndOISIndex],
							overnightLongEndOISBumpedMergedDiscountForwardCurve
						);
					}
				}
			}

			double[] longEndOISQuoteParallelBumpArray = Helper.TweakManifestMeasure (
				longEndOISQuoteArray,
				flatManifestMeasureTweak
			);

			MergedDiscountForwardCurve overnightLongEndOISBumpedMergedDiscountForwardCurve = OvernightCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				shortEndOISMaturityTenorArray,
				shortEndOISQuoteArray,
				shortEndOISMeasure,
				oisFuturesEffectiveTenorArray,
				oisFuturesMaturityTenorArray,
				oisFuturesQuoteArray,
				oisFuturesMeasure,
				longEndOISMaturityTenorArray,
				longEndOISQuoteParallelBumpArray,
				longEndOISMeasure,
				latentStateType
			);

			if (null != overnightLongEndOISBumpedMergedDiscountForwardCurve) {
				bumpedMergedDiscountForwardCurveMap.put (
					"LONGENDOIS::PLL",
					overnightLongEndOISBumpedMergedDiscountForwardCurve
				);
			}

			MergedDiscountForwardCurve overnightBaseMergedDiscountForwardCurve = OvernightCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				depositQuoteArray,
				depositMeasure,
				shortEndOISMaturityTenorArray,
				shortEndOISQuoteArray,
				shortEndOISMeasure,
				oisFuturesEffectiveTenorArray,
				oisFuturesMaturityTenorArray,
				oisFuturesQuoteArray,
				oisFuturesMeasure,
				longEndOISMaturityTenorArray,
				longEndOISQuoteArray,
				longEndOISMeasure,
				latentStateType
			);

			if (null != overnightBaseMergedDiscountForwardCurve) {
				bumpedMergedDiscountForwardCurveMap.put ("BASE", overnightBaseMergedDiscountForwardCurve);
			}

			MergedDiscountForwardCurve overnightBumpedMergedDiscountForwardCurve = OvernightCurve (
				spotDate,
				currency,
				depositMaturityTenorArray,
				parallelBumpedDepositQuoteArray,
				depositMeasure,
				shortEndOISMaturityTenorArray,
				shortEndOISQuoteParallelBumpArray,
				shortEndOISMeasure,
				oisFuturesEffectiveTenorArray,
				oisFuturesMaturityTenorArray,
				oisFuturesParallelBumpedQuoteArray,
				oisFuturesMeasure,
				longEndOISMaturityTenorArray,
				longEndOISQuoteParallelBumpArray,
				longEndOISMeasure,
				latentStateType
			);

			if (null != overnightBumpedMergedDiscountForwardCurve) {
				bumpedMergedDiscountForwardCurveMap.put ("BUMP", overnightBumpedMergedDiscountForwardCurve);
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return bumpedMergedDiscountForwardCurveMap;
	}

	/**
	 * Construct a Tenor + Parallel Map of Bumped Credit Curves from Overnight Exchange/OTC Market
	 * 	Instruments
	 * 
	 * @param spotDate Spot Date
	 * @param creditCurveName Credit Curve
	 * @param maturityTenorArray Maturity Tenor
	 * @param couponArray Coupon Array
	 * @param quoteArray Array of Market Quotes
	 * @param calibrationMeasure Calibration Measure
	 * @param mergedDiscountForwardCurve Discount Curve
	 * @param bumpAmount The Tenor Node Bump Amount
	 * @param isProportional TRUE - The Bump Applied is Proportional
	 * 
	 * @return Map of Bumped Credit Curves
	 */

	public static final CaseInsensitiveTreeMap<CreditCurve> BumpedCreditCurve (
		final JulianDate spotDate,
		final String creditCurveName,
		final String[] maturityTenorArray,
		final double[] couponArray,
		final double[] quoteArray,
		final String calibrationMeasure,
		final MergedDiscountForwardCurve mergedDiscountForwardCurve,
		final double bumpAmount,
		final boolean isProportional)
	{
		if (!NumberUtil.IsValid (bumpAmount)) {
			return null;
		}

		CaseInsensitiveTreeMap<CreditCurve> bumpedCreditCurveMap = new CaseInsensitiveTreeMap<CreditCurve>();

		if (null != quoteArray) {
			for (int quoteIndex = 0; quoteIndex < quoteArray.length; ++quoteIndex) {
				try {
					CreditCurve bumpedCreditCurve = CreditCurve (
						spotDate,
						creditCurveName,
						maturityTenorArray,
						couponArray,
						Helper.TweakManifestMeasure (
							quoteArray,
							new ManifestMeasureTweak (quoteIndex, isProportional, bumpAmount)
						),
						calibrationMeasure,
						mergedDiscountForwardCurve
					);

					if (null != bumpedCreditCurve) {
						bumpedCreditCurveMap.put (
							"CDS::" + maturityTenorArray[quoteIndex],
							bumpedCreditCurve
						);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		try {
			CreditCurve baseCreditCurve = CreditCurve (
				spotDate,
				creditCurveName,
				maturityTenorArray,
				couponArray,
				quoteArray,
				calibrationMeasure,
				mergedDiscountForwardCurve
			);

			if (null != baseCreditCurve) {
				bumpedCreditCurveMap.put ("BASE", baseCreditCurve);
			}

			CreditCurve bumpedCreditCurve = CreditCurve (
				spotDate,
				creditCurveName,
				maturityTenorArray,
				couponArray,
				Helper.TweakManifestMeasure (
					quoteArray,
					new ManifestMeasureTweak (ManifestMeasureTweak.FLAT, isProportional, bumpAmount)
				),
				calibrationMeasure,
				mergedDiscountForwardCurve
			);

			if (null != bumpedCreditCurve) {
				bumpedCreditCurveMap.put ("BUMP", bumpedCreditCurve);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bumpedCreditCurveMap;
	}

	/**
	 * Construct a Tenor + Parallel Map of Govvie Curves from the Treasury Instruments
	 * 
	 * @param strCode The Govvie Code
	 * @param spotDate Spot Date
	 * @param adtEffective Array of Effective Dates
	 * @param adtMaturity Array of Maturity Dates
	 * @param couponArray Array of Coupons
	 * @param quoteArray Array of Market Quotes
	 * @param calibrationMeasure Calibration Measure
	 * @param latentStateType SHAPE PRESERVING/SMOOTH
	 * @param bumpAmount The Tenor Node Bump Amount
	 * @param isProportional TRUE - The Bump Applied is Proportional
	 * 
	 * @return Map of Govvie Curve Instance
	 */

	public static final CaseInsensitiveTreeMap<GovvieCurve>
		BumpedGovvieCurve (
			final String strCode,
			final JulianDate spotDate,
			final JulianDate[] adtEffective,
			final JulianDate[] adtMaturity,
			final double[] couponArray,
			final double[] quoteArray,
			final String calibrationMeasure,
			final int latentStateType,
			final double bumpAmount,
			final boolean isProportional)
	{
		if (!NumberUtil.IsValid (bumpAmount)) return null;

		CaseInsensitiveTreeMap<GovvieCurve> mapBumpedCurve =
			new CaseInsensitiveTreeMap<GovvieCurve>();

		if (null != quoteArray) {
			int iNumComp = quoteArray.length;

			for (int i = 0; i < iNumComp; ++i) {
				try {
					GovvieCurve gcBumped = GovvieCurve (strCode, spotDate, adtEffective,
						adtMaturity, couponArray, Helper.TweakManifestMeasure
							(quoteArray, new ManifestMeasureTweak (i,
								isProportional, bumpAmount)), calibrationMeasure, latentStateType);

					if (null != gcBumped) mapBumpedCurve.put ("TSY::" + adtMaturity[i], gcBumped);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		try {
			GovvieCurve gcBase = GovvieCurve (strCode, spotDate, adtEffective,
				adtMaturity, couponArray, quoteArray, calibrationMeasure, latentStateType);

			if (null != gcBase) mapBumpedCurve.put ("BASE", gcBase);

			GovvieCurve gcBumped = GovvieCurve (strCode, spotDate, adtEffective,
				adtMaturity, couponArray, Helper.TweakManifestMeasure (quoteArray,
					new ManifestMeasureTweak
						(ManifestMeasureTweak.FLAT, isProportional, bumpAmount)),
							calibrationMeasure, latentStateType);

			if (null != gcBumped) mapBumpedCurve.put ("BUMP", gcBumped);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapBumpedCurve;
	}

	/**
	 * Construct a Tenor + Parallel Map of FX Curve from the FX Instruments
	 * 
	 * @param spotDate Spot Date
	 * @param cp The FX Currency Pair
	 * @param maturityTenorArray Array of Maturity Tenors
	 * @param quoteArray Array of FX Forwards
	 * @param calibrationMeasure Calibration Measure
	 * @param dblFXSpot FX Spot
	 * @param latentStateType SHAPE PRESERVING/SMOOTH
	 * @param bumpAmount The Tenor Node Bump Amount
	 * @param isProportional TRUE - The Bump Applied is Proportional
	 * 
	 * @return Map of FX Curve Instance
	 */

	public static final CaseInsensitiveTreeMap<FXCurve>
		BumpedFXCurve (
			final JulianDate spotDate,
			final CurrencyPair cp,
			final String[] maturityTenorArray,
			final double[] quoteArray,
			final String calibrationMeasure,
			final double dblFXSpot,
			final int latentStateType,
			final double bumpAmount,
			final boolean isProportional)
	{
		if (!NumberUtil.IsValid (bumpAmount)) return null;

		CaseInsensitiveTreeMap<FXCurve> mapBumpedCurve = new
			CaseInsensitiveTreeMap<FXCurve>();

		if (null != quoteArray) {
			int iNumComp = quoteArray.length;

			for (int i = 0; i < iNumComp; ++i) {
				try {
					FXCurve fxCurveBumped = FXCurve (spotDate, cp, maturityTenorArray,
						Helper.TweakManifestMeasure (quoteArray, new
							ManifestMeasureTweak (i, isProportional, bumpAmount)),
								calibrationMeasure, dblFXSpot, latentStateType);

					if (null != fxCurveBumped)
						mapBumpedCurve.put ("FXFWD::" + maturityTenorArray[i], fxCurveBumped);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		try {
			FXCurve fxCurveBase = FXCurve (spotDate, cp, maturityTenorArray, quoteArray,
				calibrationMeasure, dblFXSpot, latentStateType);

			if (null != fxCurveBase) mapBumpedCurve.put ("BASE", fxCurveBase);

			FXCurve fxCurveBump = FXCurve (spotDate, cp, maturityTenorArray,
				Helper.TweakManifestMeasure (quoteArray, new
					ManifestMeasureTweak
						(ManifestMeasureTweak.FLAT, isProportional, bumpAmount)),
							calibrationMeasure, dblFXSpot, latentStateType);

			if (null != fxCurveBump) mapBumpedCurve.put ("BUMP", fxCurveBump);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapBumpedCurve;
	}

	/**
	 * Construct a Tenor + Parallel Forward Volatility Latent State Construction from Cap/Floor Instruments
	 * 
	 * @param spotDate Spot Date
	 * @param forwardLabel Forward Label
	 * @param bIsCap TRUE - Create and Use Array of Caps
	 * @param maturityTenorArray Array of Cap/floor Maturities
	 * @param adblStrike Array of Cap/Floor Strikes
	 * @param quoteArray Array of Cap/Floor Quotes
	 * @param calibrationMeasure Calibration Measure
	 * @param dc Discount Curve Instance
	 * @param fc Forward Curve Instance
	 * @param bumpAmount The Tenor Node Bump Amount
	 * @param isProportional TRUE - The Bump Applied is Proportional
	 * 
	 * @return Map of Forward Volatility Curve Instance
	 */

	public static final
		CaseInsensitiveTreeMap<VolatilityCurve>
			BumpedForwardVolatilityCurve (
				final JulianDate spotDate,
				final ForwardLabel forwardLabel,
				final boolean bIsCap,
				final String[] maturityTenorArray,
				final double[] adblStrike,
				final double[] quoteArray,
				final String calibrationMeasure,
				final MergedDiscountForwardCurve dc,
				final ForwardCurve fc,
				final double bumpAmount,
				final boolean isProportional)
	{
		if (!NumberUtil.IsValid (bumpAmount)) return null;

		CaseInsensitiveTreeMap<VolatilityCurve>
			mapBumpedCurve = new
				CaseInsensitiveTreeMap<VolatilityCurve>();

		if (null != quoteArray) {
			int iNumComp = quoteArray.length;

			for (int i = 0; i < iNumComp; ++i) {
				try {
					VolatilityCurve forwardVolatilityCurveBumped =
						ForwardRateVolatilityCurve (spotDate, forwardLabel, bIsCap, maturityTenorArray,
							adblStrike, Helper.TweakManifestMeasure (quoteArray,
								new ManifestMeasureTweak (i, isProportional,
									bumpAmount)), calibrationMeasure, dc, fc);

					if (null != forwardVolatilityCurveBumped)
						mapBumpedCurve.put ("CAPFLOOR::" + maturityTenorArray[i],
							forwardVolatilityCurveBumped);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		try {
			VolatilityCurve forwardVolatilityCurveBase = ForwardRateVolatilityCurve
				(spotDate, forwardLabel, bIsCap, maturityTenorArray, adblStrike, quoteArray, calibrationMeasure, dc, fc);

			if (null != forwardVolatilityCurveBase) mapBumpedCurve.put ("BASE", forwardVolatilityCurveBase);

			VolatilityCurve forwardVolatilityCurveBumped =
				ForwardRateVolatilityCurve (spotDate, forwardLabel, bIsCap, maturityTenorArray, adblStrike,
					Helper.TweakManifestMeasure (quoteArray, new
						ManifestMeasureTweak
							(ManifestMeasureTweak.FLAT, isProportional, bumpAmount)),
								calibrationMeasure, dc, fc);

			if (null != forwardVolatilityCurveBumped)
				mapBumpedCurve.put ("BUMP", forwardVolatilityCurveBumped);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapBumpedCurve;
	}
}
