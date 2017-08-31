
package org.drip.state.creator;

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
 * ScenarioGovvieCurveBuilder implements the Construction of the Scenario Govvie Curve using the Input Govvie
 *  Curve Instruments.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ScenarioGovvieCurveBuilder {

	/**
	 * Build the Shape Preserving Govvie Curve using the Custom Parameters
	 * 
	 * @param llsc The Linear Latent State Calibrator Instance
	 * @param aStretchSpec Array of the Latent State Stretches
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Currency
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqc Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblEpochResponse The Starting Response Value
	 * 
	 * @return Instance of the Shape Preserving Discount Curve
	 */

	public static final org.drip.state.govvie.GovvieCurve ShapePreservingGovvieCurve (
		final org.drip.state.inference.LinearLatentStateCalibrator llsc,
		final org.drip.state.inference.LatentStateStretchSpec[] aStretchSpec,
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblEpochResponse)
	{
		if (null == llsc) return null;

		try {
			org.drip.state.govvie.GovvieCurve govvieCurve = new org.drip.state.curve.BasisSplineGovvieYield
				(strTreasuryCode, strCurrency, llsc.calibrateSpan (aStretchSpec, dblEpochResponse, valParams,
					pricerParams, vcp, csqc));

			return govvieCurve.setCCIS (new org.drip.analytics.input.LatentStateShapePreservingCCIS (llsc,
				aStretchSpec, valParams, pricerParams, vcp, csqc)) ? govvieCurve : null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of the Shape Preserver of the desired Basis Spline Type, using the specified
	 *  Basis Spline Set Builder Parameters.
	 * 
	 * @param strName Curve Name
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Currency
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqc Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param strBasisType The Basis Type
	 * @param fsbp The Function Set Basis Parameters
	 * @param sdic Segment Design In-elastic Control
	 * @param aCalibComp Array of Calibration Components
	 * @param strManifestMeasure The Calibration Manifest Measure
	 * @param adblQuote Array of Calibration Quotes
	 * 
	 * @return Instance of the Shape Preserver of the Desired Basis Type
	 */

	public static final org.drip.state.govvie.GovvieCurve ShapePreservingGovvieCurve (
		final java.lang.String strName,
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final java.lang.String strBasisType,
		final org.drip.spline.basis.FunctionSetBuilderParams fsbp,
		final org.drip.spline.params.SegmentInelasticDesignControl sdic,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp,
		final java.lang.String strManifestMeasure,
		final double[] adblQuote)
	{
		if (null == strName || strName.isEmpty() || null == strBasisType || strBasisType.isEmpty() || null ==
			valParams || null == fsbp || null == strManifestMeasure || strManifestMeasure.isEmpty())
			return null;

		int iNumQuote = null == adblQuote ? 0 : adblQuote.length;
		int iNumComp = null == aCalibComp ? 0 : aCalibComp.length;

		if (0 == iNumComp || iNumComp != iNumQuote) return null;

		org.drip.state.identifier.GovvieLabel govvieLabel = aCalibComp[0].govvieLabel();

		try {
			org.drip.state.representation.LatentStateSpecification[] aLSS = new
				org.drip.state.representation.LatentStateSpecification[] {new
					org.drip.state.representation.LatentStateSpecification
						(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_GOVVIE,
							org.drip.analytics.definition.LatentStateStatic.GOVVIE_QM_YIELD, govvieLabel)};

			org.drip.state.inference.LatentStateSegmentSpec[] aSegmentSpec = new
				org.drip.state.inference.LatentStateSegmentSpec[iNumComp];

			for (int i = 0; i < iNumComp; ++i) {
				org.drip.product.calib.ProductQuoteSet pqs = aCalibComp[i].calibQuoteSet (aLSS);

				if (null == pqs || !pqs.set (strManifestMeasure, adblQuote[i])) return null;

				aSegmentSpec[i] = new org.drip.state.inference.LatentStateSegmentSpec (aCalibComp[i], pqs);
			}

			org.drip.state.inference.LatentStateStretchSpec[] aStretchSpec = new
				org.drip.state.inference.LatentStateStretchSpec[] {new
					org.drip.state.inference.LatentStateStretchSpec (strName, aSegmentSpec)};

			org.drip.state.inference.LinearLatentStateCalibrator llsc = new
				org.drip.state.inference.LinearLatentStateCalibrator (new
					org.drip.spline.params.SegmentCustomBuilderControl (strBasisType, fsbp, sdic, new
						org.drip.spline.params.ResponseScalingShapeControl (true, new
							org.drip.function.r1tor1.QuadraticRationalShapeControl (0.)), null),
								org.drip.spline.stretch.BoundarySettings.FinancialStandard(),
									org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE, null, null);

			return ShapePreservingGovvieCurve (llsc, aStretchSpec, strTreasuryCode, strCurrency, valParams,
				pricerParams, csqc, vcp, adblQuote[0]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of the Shape Preserver of the Linear Polynomial Type, using the Specified Basis
	 *  Set Builder Parameters.
	 * 
	 * @param strName Curve Name
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Currency
	 * @param iSpotDate Spot Date
	 * @param aComp Array of Calibration Components
	 * @param adblQuote Array of Calibration Quotes
	 * @param strManifestMeasure The Calibration Manifest Measure
	 * 
	 * @return Instance of the Shape Preserver of the Cubic Polynomial Type
	 */

	public static final org.drip.state.govvie.GovvieCurve LinearPolyShapePreserver (
		final java.lang.String strName,
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency,
		final int iSpotDate,
		final org.drip.product.definition.CalibratableComponent[] aComp,
		final double[] adblQuote,
		final java.lang.String strManifestMeasure)
	{
		try {
			return ShapePreservingGovvieCurve (strName, strTreasuryCode, strCurrency,
				org.drip.param.valuation.ValuationParams.Spot (iSpotDate), null, null, null,
					org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
						org.drip.spline.basis.PolynomialFunctionSetParams (2),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (0, 2), aComp,
								strManifestMeasure, adblQuote);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of the Shape Preserver of the Cubic Polynomial Type, using the Specified Basis
	 *  Set Builder Parameters.
	 * 
	 * @param strName Curve Name
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Currency
	 * @param iSpotDate Spot Date
	 * @param aComp Array of Calibration Components
	 * @param adblQuote Array of Calibration Quotes
	 * @param strManifestMeasure The Calibration Manifest Measure
	 * 
	 * @return Instance of the Shape Preserver of the Cubic Polynomial Type
	 */

	public static final org.drip.state.govvie.GovvieCurve CubicPolyShapePreserver (
		final java.lang.String strName,
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency,
		final int iSpotDate,
		final org.drip.product.definition.CalibratableComponent[] aComp,
		final double[] adblQuote,
		final java.lang.String strManifestMeasure)
	{
		try {
			return ShapePreservingGovvieCurve (strName, strTreasuryCode, strCurrency,
				org.drip.param.valuation.ValuationParams.Spot (iSpotDate), null, null, null,
					org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
						org.drip.spline.basis.PolynomialFunctionSetParams (4),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), aComp,
								strManifestMeasure, adblQuote);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Custom Splined Govvie Yield Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Currency
	 * @param aiMaturityDate Array of the Maturity Dates
	 * @param adblYield Array of the Yields
	 * @param scbc The Segment Custom Builder Control
	 * 
	 * @return The Instance of the Govvie Yield Curve
	 */

	public static final org.drip.state.govvie.GovvieCurve CustomSplineCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency,
		final int[] aiMaturityDate,
		final double[] adblYield,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc)
	{
		if (null == strName || strName.isEmpty() || null == dtStart || null == aiMaturityDate || null ==
			adblYield)
			return null;

		int iNumTreasury = aiMaturityDate.length;
		int[] aiPredictorOrdinate = new int[iNumTreasury + 1];
		double[] adblResponseValue = new double[iNumTreasury + 1];
		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumTreasury];

		if (0 == iNumTreasury || iNumTreasury != adblYield.length) return null;

		for (int i = 0; i <= iNumTreasury; ++i) {
			aiPredictorOrdinate[i] = 0 == i ? dtStart.julian() : aiMaturityDate[i - 1];

			adblResponseValue[i] = 0 == i ? adblYield[0] : adblYield[i - 1];

			if (0 != i) aSCBC[i - 1] = scbc;
		}

		try {
			return new org.drip.state.curve.BasisSplineGovvieYield (strTreasuryCode, strCurrency, new
				org.drip.spline.grid.OverlappingStretchSpan
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
						(strName, aiPredictorOrdinate, adblResponseValue, aSCBC, null,
							org.drip.spline.stretch.BoundarySettings.NaturalStandard(),
								org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE)));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Linear Polynomial Splined Govvie Yield Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Currency
	 * @param aiMaturityDate Array of the Maturity Dates
	 * @param adblYield Array of the Govvie Yields
	 * 
	 * @return The Instance of the Govvie Yield Curve
	 */

	/* public static final org.drip.state.govvie.GovvieCurve LinearPolynomialCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency,
		final int[] aiMaturityDate,
		final double[] adblYield)
	{
		try {
			return CustomSplineCurve (strName, dtStart, strTreasuryCode, strCurrency, aiMaturityDate,
				adblYield, new org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
						org.drip.spline.basis.PolynomialFunctionSetParams (2),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (0, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	} */

	/**
	 * Create an Instance of the Cubic Polynomial Splined Govvie Yield Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Currency
	 * @param aiMaturityDate Array of the Maturity Dates
	 * @param adblYield Array of the Govvie Yields
	 * 
	 * @return The Instance of the Govvie Yield Curve
	 */

	public static final org.drip.state.govvie.GovvieCurve CubicPolynomialCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency,
		final int[] aiMaturityDate,
		final double[] adblYield)
	{
		try {
			return CustomSplineCurve (strName, dtStart, strTreasuryCode, strCurrency, aiMaturityDate,
				adblYield, new org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
						org.drip.spline.basis.PolynomialFunctionSetParams (4),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Quartic Polynomial Splined Govvie Yield Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Currency
	 * @param aiMaturityDate Array of the Maturity Dates
	 * @param adblYield Array of the Yields
	 * 
	 * @return The Instance of the Govvie Yield Curve
	 */

	public static final org.drip.state.govvie.GovvieCurve QuarticPolynomialCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency,
		final int[] aiMaturityDate,
		final double[] adblYield)
	{
		try {
			return CustomSplineCurve (strName, dtStart, strTreasuryCode, strCurrency, aiMaturityDate,
				adblYield, new org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
						org.drip.spline.basis.PolynomialFunctionSetParams (5),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Kaklis-Pandelis Splined Govvie Yield Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Currency
	 * @param aiMaturityDate Array of the Maturity Dates
	 * @param adblYield Array of the Yields
	 * 
	 * @return The Instance of the Govvie Yield Curve
	 */

	public static final org.drip.state.govvie.GovvieCurve KaklisPandelisCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency,
		final int[] aiMaturityDate,
		final double[] adblYield)
	{
		try {
			return CustomSplineCurve (strName, dtStart, strTreasuryCode, strCurrency, aiMaturityDate,
				adblYield, new org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KAKLIS_PANDELIS, new
						org.drip.spline.basis.KaklisPandelisSetParams (2),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Hyperbolic Splined Govvie Yield Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Currency
	 * @param aiMaturityDate Array of the Maturity Dates
	 * @param adblYield Array of the Yields
	 * @param dblTension The Tension Parameter
	 * 
	 * @return The Instance of the Govvie Yield Curve
	 */

	public static final org.drip.state.govvie.GovvieCurve KLKHyperbolicCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency,
		final int[] aiMaturityDate,
		final double[] adblYield,
		final double dblTension)
	{
		try {
			return CustomSplineCurve (strName, dtStart, strTreasuryCode, strCurrency, aiMaturityDate,
				adblYield, new org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION,
						new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Rational Linear Splined Govvie Yield Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Currency
	 * @param aiMaturityDate Array of the Maturity Dates
	 * @param adblYield Array of the Yields
	 * @param dblTension The Tension Parameter
	 * 
	 * @return The Instance of the Govvie Yield Curve
	 */

	public static final org.drip.state.govvie.GovvieCurve KLKRationalLinearCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency,
		final int[] aiMaturityDate,
		final double[] adblYield,
		final double dblTension)
	{
		try {
			return CustomSplineCurve (strName, dtStart, strTreasuryCode, strCurrency, aiMaturityDate,
				adblYield, new org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION,
				new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
					org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Rational Quadratic Splined Govvie Yield Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Currency
	 * @param aiMaturityDate Array of the Maturity Dates
	 * @param adblYield Array of the Yields
	 * @param dblTension The Tension Parameter
	 * 
	 * @return The Instance of the Govvie Yield Curve
	 */

	public static final org.drip.state.govvie.GovvieCurve KLKRationalQuadraticCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency,
		final int[] aiMaturityDate,
		final double[] adblYield,
		final double dblTension)
	{
		try {
			return CustomSplineCurve (strName, dtStart, strTreasuryCode, strCurrency, aiMaturityDate,
				adblYield, new org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION,
				new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
					org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Govvie Curve from an Array of Dates and Yields
	 * 
	 * @param iEpochDate Epoch Date
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Currency
	 * @param aiDate Array of Dates
	 * @param adblYield Array of Yields
	 * 
	 * @return The Govvie Curve Instance
	 */

	public static final org.drip.state.govvie.GovvieCurve DateYield (
		final int iEpochDate,
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency,
		final int[] aiDate,
		final double[] adblYield)
	{
		try {
			return new org.drip.state.nonlinear.FlatYieldGovvieCurve (iEpochDate, strTreasuryCode,
				strCurrency, aiDate, adblYield);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Govvie Curve from the Specified Date and Yield
	 * 
	 * @param iEpochDate Epoch Date
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Currency
	 * @param dblYield Yield
	 * 
	 * @return The Govvie Curve Instance
	 */

	public static final org.drip.state.govvie.GovvieCurve ConstantYield (
		final int iEpochDate,
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency,
		final double dblYield)	{
		try {
			return new org.drip.state.nonlinear.FlatYieldGovvieCurve (iEpochDate, strTreasuryCode,
				strCurrency, new int[] {iEpochDate}, new double[] {dblYield});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
