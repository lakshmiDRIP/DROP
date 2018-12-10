
package org.drip.state.creator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>ScenarioFXCurveBuilder</i> implements the construction of the scenario FX Curve using the input FX
 * Curve instruments.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator">Creator</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ScenarioFXCurveBuilder {

	/**
	 * Build the Shape Preserving FX Curve using the Custom Parameters
	 * 
	 * @param llsc The Linear Latent State Calibrator Instance
	 * @param aStretchSpec Array of the Latent State Stretches
	 * @param cp The FX Currency Pair
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs Market Parameters
	 * @param vcp Quoting Parameters
	 * @param dblEpochResponse The Starting Response Value
	 * 
	 * @return Instance of the Shape Preserving Discount Curve
	 */

	public static final org.drip.state.fx.FXCurve ShapePreservingFXCurve (
		final org.drip.state.inference.LinearLatentStateCalibrator llsc,
		final org.drip.state.inference.LatentStateStretchSpec[] aStretchSpec,
		final org.drip.product.params.CurrencyPair cp,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblEpochResponse)
	{
		if (null == llsc) return null;

		try {
			org.drip.state.fx.FXCurve fxCurve = new org.drip.state.curve.BasisSplineFXForward (cp,
				llsc.calibrateSpan (aStretchSpec, dblEpochResponse, valParams, pricerParams, vcp, csqs));

			return fxCurve.setCCIS (new org.drip.analytics.input.LatentStateShapePreservingCCIS (llsc,
				aStretchSpec, valParams, pricerParams, vcp, csqs)) ? fxCurve : null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an instance of the Shape Preserver of the desired basis type, using the specified basis set
	 * 	builder parameters.
	 * 
	 * @param strName Curve Name
	 * @param cp The FX Currency Pair
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs Market Parameters
	 * @param vcp Quoting Parameters
	 * @param aCalibComp Array of Calibration Components
	 * @param strManifestMeasure The Calibration Manifest Measure
	 * @param adblQuote Array of Calibration Quotes
	 * @param dblEpochResponse The Stretch Start DF
	 * @param scbc Segment Custom Builder Control Parameters
	 * 
	 * @return Instance of the Shape Preserver of the desired basis type
	 */

	public static final org.drip.state.fx.FXCurve ShapePreservingFXCurve (
		final java.lang.String strName,
		final org.drip.product.params.CurrencyPair cp,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp,
		final java.lang.String strManifestMeasure,
		final double[] adblQuote,
		final double dblEpochResponse,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc)
	{
		if (null == strName || strName.isEmpty() || null == valParams || null == scbc || null ==
			strManifestMeasure || strManifestMeasure.isEmpty())
			return null;

		int iNumQuote = null == adblQuote ? 0 : adblQuote.length;
		int iNumComp = null == aCalibComp ? 0 : aCalibComp.length;

		if (0 == iNumComp || iNumComp != iNumQuote) return null;

		try {
			org.drip.state.identifier.FXLabel fxLabel = null;

			if (aCalibComp[0] instanceof org.drip.product.rates.DualStreamComponent)
				fxLabel = ((org.drip.product.rates.DualStreamComponent)
					aCalibComp[0]).derivedStream().fxLabel();
			else {
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel>
					mapFXLabel = aCalibComp[0].fxLabel();

				if (null != mapFXLabel && 0 != mapFXLabel.size()) fxLabel = mapFXLabel.get ("DERIVED");
			}

			org.drip.state.representation.LatentStateSpecification[] aLSS = new
				org.drip.state.representation.LatentStateSpecification[] {new
					org.drip.state.representation.LatentStateSpecification
						(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FX,
							org.drip.analytics.definition.LatentStateStatic.FX_QM_FORWARD_OUTRIGHT,
								fxLabel)};

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
				org.drip.state.inference.LinearLatentStateCalibrator (scbc,
					org.drip.spline.stretch.BoundarySettings.FinancialStandard(),
						org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE, null, null);

			return ShapePreservingFXCurve (llsc, aStretchSpec, cp, valParams, pricerParams, csqs, vcp,
				dblEpochResponse);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an instance of the Shape Preserver of the desired basis type, using the specified basis set
	 * 	builder parameters.
	 * 
	 * @param strName Curve Name
	 * @param cp The FX Currency Pair
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs Market Parameters
	 * @param vcp Quoting Parameters
	 * @param strBasisType The Basis Type
	 * @param fsbp The Function Set Basis Parameters
	 * @param aCalibComp Array of Calibration Components
	 * @param strManifestMeasure The Calibration Manifest Measure
	 * @param adblQuote Array of Calibration Quotes
	 * @param dblEpochResponse The Stretch Start DF
	 * 
	 * @return Instance of the Shape Preserver of the desired basis type
	 */

	public static final org.drip.state.fx.FXCurve ShapePreservingFXCurve (
		final java.lang.String strName,
		final org.drip.product.params.CurrencyPair cp,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final java.lang.String strBasisType,
		final org.drip.spline.basis.FunctionSetBuilderParams fsbp,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp,
		final java.lang.String strManifestMeasure,
		final double[] adblQuote,
		final double dblEpochResponse)
	{
		if (null == strName || strName.isEmpty() || null == strBasisType || strBasisType.isEmpty() || null ==
			valParams || null == fsbp || null == strManifestMeasure || strManifestMeasure.isEmpty())
			return null;

		int iNumQuote = null == adblQuote ? 0 : adblQuote.length;
		int iNumComp = null == aCalibComp ? 0 : aCalibComp.length;

		if (0 == iNumComp || iNumComp != iNumQuote) return null;

		try {
			org.drip.state.identifier.FXLabel fxLabel = null;

			if (aCalibComp[0] instanceof org.drip.product.rates.DualStreamComponent)
				fxLabel = ((org.drip.product.rates.DualStreamComponent)
					aCalibComp[0]).derivedStream().fxLabel();
			else {
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel>
					mapFXLabel = aCalibComp[0].fxLabel();

				if (null != mapFXLabel && 0 != mapFXLabel.size()) fxLabel = mapFXLabel.get ("DERIVED");
			}

			org.drip.state.representation.LatentStateSpecification[] aLSS = new
				org.drip.state.representation.LatentStateSpecification[] {new
					org.drip.state.representation.LatentStateSpecification
						(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FX,
							org.drip.analytics.definition.LatentStateStatic.FX_QM_FORWARD_OUTRIGHT,
								fxLabel)};

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
					org.drip.spline.params.SegmentCustomBuilderControl (strBasisType, fsbp,
						org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), new
							org.drip.spline.params.ResponseScalingShapeControl (true, new
								org.drip.function.r1tor1.QuadraticRationalShapeControl (0.)), null),
									org.drip.spline.stretch.BoundarySettings.FinancialStandard(),
										org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE, null, null);

			return ShapePreservingFXCurve (llsc, aStretchSpec, cp, valParams, pricerParams, csqs, vcp,
				dblEpochResponse);
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
	 * @param cp The FX Currency Pair
	 * @param iSpotDate Spot Date
	 * @param aComp Array of Calibration Components
	 * @param adblQuote Array of Calibration Quotes
	 * @param strManifestMeasure The Calibration Manifest Measure
	 * @param dblFXSpot The FX Spot
	 * 
	 * @return Instance of the Shape Preserver of the Cubic Polynomial Type
	 */

	public static final org.drip.state.fx.FXCurve CubicPolyShapePreserver (
		final java.lang.String strName,
		final org.drip.product.params.CurrencyPair cp,
		final int iSpotDate,
		final org.drip.product.definition.CalibratableComponent[] aComp,
		final double[] adblQuote,
		final java.lang.String strManifestMeasure,
		final double dblFXSpot)
	{
		try {
			return ShapePreservingFXCurve (strName, cp, org.drip.param.valuation.ValuationParams.Spot
				(iSpotDate), null, null, null,
					org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
						org.drip.spline.basis.PolynomialFunctionSetParams (4), aComp, strManifestMeasure,
							adblQuote, dblFXSpot);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Custom Splined FX Forward Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param cp The Currency Pair
	 * @param astrTenor Array of the Tenors
	 * @param adblFXForward Array of the FX Forwards
	 * @param scbc The Segment Custom Builder Control
	 * @param dblFXSpot FX Spot
	 * 
	 * @return The Instance of the FX Forward Curve
	 */

	public static final org.drip.state.fx.FXCurve CustomSplineCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final org.drip.product.params.CurrencyPair cp,
		final java.lang.String[] astrTenor,
		final double[] adblFXForward,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc,
		final double dblFXSpot)
	{
		if (null == strName || strName.isEmpty() || null == astrTenor || null == dtStart ||
			!org.drip.quant.common.NumberUtil.IsValid (dblFXSpot))
			return null;

		int iNumTenor = astrTenor.length;

		if (0 == iNumTenor) return null;

		int[] aiBasisPredictorOrdinate = new int[iNumTenor + 1];
		double[] adblBasisResponseValue = new double[iNumTenor + 1];
		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumTenor];

		for (int i = 0; i <= iNumTenor; ++i) {
			if (0 != i) {
				java.lang.String strTenor = astrTenor[i - 1];

				if (null == strTenor || strTenor.isEmpty()) return null;

				org.drip.analytics.date.JulianDate dtMaturity = dtStart.addTenor (strTenor);

				if (null == dtMaturity) return null;

				aiBasisPredictorOrdinate[i] = dtMaturity.julian();
			} else
				aiBasisPredictorOrdinate[i] = dtStart.julian();

			adblBasisResponseValue[i] = 0 == i ? dblFXSpot : adblFXForward[i - 1];

			if (0 != i) aSCBC[i - 1] = scbc;
		}

		try {
			return new org.drip.state.curve.BasisSplineFXForward (cp, new
				org.drip.spline.grid.OverlappingStretchSpan
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
						(strName, aiBasisPredictorOrdinate, adblBasisResponseValue, aSCBC, null,
							org.drip.spline.stretch.BoundarySettings.NaturalStandard(),
								org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE)));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Cubic Polynomial Splined FX Forward Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param cp The Currency Pair
	 * @param astrTenor Array of the Tenors
	 * @param adblFXForward Array of the FX Forwards
	 * @param dblFXSpot FX Spot
	 * 
	 * @return The Instance of the FX Forward Curve
	 */

	public static final org.drip.state.fx.FXCurve CubicPolynomialCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final org.drip.product.params.CurrencyPair cp,
		final java.lang.String[] astrTenor,
		final double[] adblFXForward,
		final double dblFXSpot)
	{
		try {
			return CustomSplineCurve (strName, dtStart, cp, astrTenor, adblFXForward, new
				org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
						org.drip.spline.basis.PolynomialFunctionSetParams (4),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null),
								dblFXSpot);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Quartic Polynomial Splined FX Forward Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param cp The Currency Pair
	 * @param astrTenor Array of the Tenors
	 * @param adblFXForward Array of the FX Forwards
	 * @param dblFXSpot FX Spot
	 * 
	 * @return The Instance of the FX Forward Curve
	 */

	public static final org.drip.state.fx.FXCurve QuarticPolynomialCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final org.drip.product.params.CurrencyPair cp,
		final java.lang.String[] astrTenor,
		final double[] adblFXForward,
		final double dblFXSpot)
	{
		try {
			return CustomSplineCurve (strName, dtStart, cp, astrTenor, adblFXForward, new
				org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
						org.drip.spline.basis.PolynomialFunctionSetParams (5),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null),
								dblFXSpot);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Kaklis-Pandelis Splined FX Forward Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param cp The Currency Pair
	 * @param astrTenor Array of the Tenors
	 * @param adblFXForward Array of the FX Forwards
	 * @param dblFXSpot FX Spot
	 * 
	 * @return The Instance of the FX Forward Curve
	 */

	public static final org.drip.state.fx.FXCurve KaklisPandelisCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final org.drip.product.params.CurrencyPair cp,
		final java.lang.String[] astrTenor,
		final double[] adblFXForward,
		final double dblFXSpot)
	{
		try {
			return CustomSplineCurve (strName, dtStart, cp, astrTenor, adblFXForward, new
				org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KAKLIS_PANDELIS, new
						org.drip.spline.basis.KaklisPandelisSetParams (2),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null),
								dblFXSpot);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Hyperbolic Splined FX Forward Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param cp The Currency Pair
	 * @param astrTenor Array of the Tenors
	 * @param adblFXForward Array of the FX Forwards
	 * @param dblFXSpot FX Spot
	 * @param dblTension The Tension Parameter
	 * 
	 * @return The Instance of the FX Forward Curve
	 */

	public static final org.drip.state.fx.FXCurve KLKHyperbolicCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final org.drip.product.params.CurrencyPair cp,
		final java.lang.String[] astrTenor,
		final double[] adblFXForward,
		final double dblFXSpot,
		final double dblTension)
	{
		try {
			return CustomSplineCurve (strName, dtStart, cp, astrTenor, adblFXForward, new
				org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION,
						new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null),
								dblFXSpot);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Rational Linear Splined FX Forward Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param cp The Currency Pair
	 * @param astrTenor Array of the Tenors
	 * @param adblFXForward Array of the FX Forwards
	 * @param dblFXSpot FX Spot
	 * @param dblTension The Tension Parameter
	 * 
	 * @return The Instance of the FX Forward Curve
	 */

	public static final org.drip.state.fx.FXCurve KLKRationalLinearCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final org.drip.product.params.CurrencyPair cp,
		final java.lang.String[] astrTenor,
		final double[] adblFXForward,
		final double dblFXSpot,
		final double dblTension)
	{
		try {
			return CustomSplineCurve (strName, dtStart, cp, astrTenor, adblFXForward, new
				org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION,
				new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
					org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null),
						dblFXSpot);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Rational Quadratic Splined FX Forward Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param cp The Currency Pair
	 * @param astrTenor Array of the Tenors
	 * @param adblFXForward Array of the FX Forwards
	 * @param dblFXSpot FX Spot
	 * @param dblTension The Tension Parameter
	 * 
	 * @return The Instance of the FX Forward Curve
	 */

	public static final org.drip.state.fx.FXCurve KLKRationalQuadraticCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final org.drip.product.params.CurrencyPair cp,
		final java.lang.String[] astrTenor,
		final double[] adblFXForward,
		final double dblFXSpot,
		final double dblTension)
	{
		try {
			return CustomSplineCurve (strName, dtStart, cp, astrTenor, adblFXForward, new
				org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION,
				new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
					org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null),
						dblFXSpot);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
