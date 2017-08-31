
package org.drip.state.creator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * ScenarioDiscountCurveBuilder implements the the construction of the scenario discount curve using the input
 * 	discount curve instruments, and a wide variety of custom builds. It implements the following
 * 	functionality:
 * 	- Non-linear Custom Discount Curve
 * 	- Shape Preserving Discount Curve Builds - Standard Cubic Polynomial/Cubic KLK Hyperbolic Tension, and
 * 	 	other Custom Builds
 * 	- Smoothing Local/Control Custom Build - DC/Forward/Zero Rate LSQM's
 * 	- "Industry Standard Methodologies" - DENSE/DUALDENSE/CUSTOMDENSE and Hagan-West Forward Interpolator
 * 		Schemes
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ScenarioDiscountCurveBuilder {
	static class CompQuote {
		double _dblQuote = java.lang.Double.NaN;
		org.drip.product.definition.CalibratableComponent _comp = null;
 
		CompQuote (
			final org.drip.product.definition.CalibratableComponent comp,
			final double dblQuote)
		{
			_comp = comp;
			_dblQuote = dblQuote;
		}
	}

	private static final boolean s_bBlog = false;

	private static final CompQuote[] CompQuote (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final java.lang.String strCurrency,
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtInitialMaturity,
		final org.drip.analytics.date.JulianDate dtTerminalMaturity,
		final java.lang.String strTenor,
		final boolean bIsIRS)
	{
		java.util.List<java.lang.Double> lsCalibQuote = new java.util.ArrayList<java.lang.Double>();

		java.util.List<org.drip.product.definition.CalibratableComponent> lsCompDENSE = new
			java.util.ArrayList<org.drip.product.definition.CalibratableComponent>();

		org.drip.analytics.date.JulianDate dtMaturity = dtInitialMaturity;

		while (dtMaturity.julian() <= dtTerminalMaturity.julian()) {
			try {
				org.drip.product.definition.CalibratableComponent comp = null;

				if (bIsIRS) {
					java.lang.String strMaturityTenor = ((int) ((dtMaturity.julian() - dtEffective.julian())
						* 12 / 365.25)) + "M";

					org.drip.market.otc.FixedFloatSwapConvention ffConv =
						org.drip.market.otc.IBORFixedFloatContainer.ConventionFromJurisdiction (strCurrency,
							"ALL", strMaturityTenor, "MAIN");

					if (null == ffConv) return null;

					comp = ffConv.createFixFloatComponent (dtEffective, strMaturityTenor, 0., 0., 1.);
				} else {
					org.drip.param.period.ComposableFloatingUnitSetting cfusDeposit = new
						org.drip.param.period.ComposableFloatingUnitSetting ("3M",
							org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
								null, org.drip.state.identifier.ForwardLabel.Standard (strCurrency + "-3M"),
									org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
						0.);

					org.drip.param.period.CompositePeriodSetting cpsDeposit = new
						org.drip.param.period.CompositePeriodSetting (4, "3M", strCurrency, null, 1., null,
							null, null, null);

					comp = new org.drip.product.rates.SingleStreamComponent ("DEPOSIT_" + dtMaturity, new
						org.drip.product.rates.Stream
							(org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit
								(org.drip.analytics.support.CompositePeriodBuilder.EdgePair (dtEffective,
									dtMaturity), cpsDeposit, cfusDeposit)), null);
				}

				lsCompDENSE.add (comp);

				lsCalibQuote.add (comp.measureValue (valParams, null, csqs, null, "Rate"));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (null == (dtMaturity = dtMaturity.addTenorAndAdjust (strTenor, strCurrency))) return null;
		}

		int iNumDENSEComp = lsCompDENSE.size();

		if (0 == iNumDENSEComp) return null;

		CompQuote[] aCQ = new CompQuote[iNumDENSEComp];

		for (int i = 0; i < iNumDENSEComp; ++i)
			aCQ[i] = new CompQuote (lsCompDENSE.get (i), lsCalibQuote.get (i));

		return aCQ;
	}

	/**
	 * Create an DiscountCurveScenarioContainer Instance from the currency and the array of the calibration
	 * 	instruments
	 * 
	 * @param strCurrency Currency
	 * @param aCalibInst Array of the calibration instruments
	 * 
	 * @return The DiscountCurveScenarioContainer instance
	 */

	public static final org.drip.param.market.DiscountCurveScenarioContainer FromIRCSG (
		final java.lang.String strCurrency,
		final org.drip.product.definition.CalibratableComponent[] aCalibInst)
	{
		try {
			return new org.drip.param.market.DiscountCurveScenarioContainer (aCalibInst);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create Discount Curve from the Calibration Instruments
	 * 
	 * @param dt Valuation Date
	 * @param strCurrency Currency
	 * @param aCalibInst Input Calibration Instruments
	 * @param adblCalibQuote Input Calibration Quotes
	 * @param astrCalibMeasure Input Calibration Measures
	 * @param lsfc Latent State Fixings Container
	 * 
	 * @return The Calibrated Discount Curve
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve NonlinearBuild (
		final org.drip.analytics.date.JulianDate dt,
		final java.lang.String strCurrency,
		final org.drip.product.definition.CalibratableComponent[] aCalibInst,
		final double[] adblCalibQuote,
		final java.lang.String[] astrCalibMeasure,
		final org.drip.param.market.LatentStateFixingsContainer lsfc)
	{
		return null == dt ? null : org.drip.state.boot.DiscountCurveScenario.Standard
			(org.drip.param.valuation.ValuationParams.Spot (dt.julian()), aCalibInst, adblCalibQuote,
				astrCalibMeasure, 0., null, lsfc, null);
	}

	/**
	 * Build the Shape Preserving Discount Curve using the Custom Parameters
	 * 
	 * @param strCurrency Currency
	 * @param llsc The Linear Latent State Calibrator Instance
	 * @param aStretchSpec Array of the Instrument Representation Stretches
	 * @param valParam Valuation Parameters
	 * @param pricerParam Pricer Parameters
	 * @param csqs Market Parameters
	 * @param quotingParam Quoting Parameters
	 * @param dblEpochResponse The Starting Response Value
	 * 
	 * @return Instance of the Shape Preserving Discount Curve
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve ShapePreservingDFBuild (
		final java.lang.String strCurrency,
		final org.drip.state.inference.LinearLatentStateCalibrator llsc,
		final org.drip.state.inference.LatentStateStretchSpec[] aStretchSpec,
		final org.drip.param.valuation.ValuationParams valParam,
		final org.drip.param.pricer.CreditPricerParams pricerParam,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParam,
		final double dblEpochResponse)
	{
		if (null == llsc) return null;

		try {
			org.drip.spline.grid.Span spanDF = llsc.calibrateSpan (aStretchSpec, dblEpochResponse, valParam,
				pricerParam, quotingParam, csqs);

			if (null == spanDF) return null;

			org.drip.state.curve.DiscountFactorDiscountCurve dcdf = new
				org.drip.state.curve.DiscountFactorDiscountCurve (strCurrency, spanDF);

			return dcdf.setCCIS (new org.drip.analytics.input.LatentStateShapePreservingCCIS (llsc,
				aStretchSpec, valParam, pricerParam, quotingParam, csqs)) ? dcdf : null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Build a Globally Smoothed Instance of the Discount Curve using the Custom Parameters
	 * 
	 * @param dcShapePreserver Instance of the Shape Preserving Discount Curve
	 * @param llsc The Linear Latent State Calibrator Instance
	 * @param gccp Global Smoothing Curve Control Parameters
	 * @param valParam Valuation Parameters
	 * @param pricerParam Pricer Parameters
	 * @param csqs Market Parameters
	 * @param quotingParam Quoting Parameters
	 * 
	 * @return Globally Smoothed Instance of the Discount Curve
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve SmoothingGlobalControlBuild (
		final org.drip.state.discount.MergedDiscountForwardCurve dcShapePreserver,
		final org.drip.state.inference.LinearLatentStateCalibrator llsc,
		final org.drip.state.estimator.GlobalControlCurveParams gccp,
		final org.drip.param.valuation.ValuationParams valParam,
		final org.drip.param.pricer.CreditPricerParams pricerParam,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParam)
	{
		if (null == dcShapePreserver) return null;

		if (null == gccp) return dcShapePreserver;

		java.lang.String strSmootheningQM = gccp.smootheningQuantificationMetric();

		java.util.Map<java.lang.Integer, java.lang.Double> mapQMTruth = dcShapePreserver.canonicalTruthness
			(strSmootheningQM);

		if (null == mapQMTruth) return null;

		int iTruthSize = mapQMTruth.size();

		if (0 == iTruthSize) return null;

		java.util.Set<java.util.Map.Entry<java.lang.Integer, java.lang.Double>> esQMTruth =
			mapQMTruth.entrySet();

		if (null == esQMTruth || 0 == esQMTruth.size()) return null;

		java.lang.String strName = dcShapePreserver.label().fullyQualifiedName();

		int i = 0;
		int[] aiDate = new int[iTruthSize];
		double[] adblQM = new double[iTruthSize];
		org.drip.spline.params.SegmentCustomBuilderControl[] aPRBP = new
			org.drip.spline.params.SegmentCustomBuilderControl[iTruthSize - 1];

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> meQMTruth : esQMTruth) {
			if (null == meQMTruth) return null;

			if (0 != i) aPRBP[i - 1] = gccp.defaultSegmentBuilderControl();

			aiDate[i] = meQMTruth.getKey();

			adblQM[i++] = meQMTruth.getValue();

			if (s_bBlog)
				System.out.println ("\t\t" + new org.drip.analytics.date.JulianDate (meQMTruth.getKey()) +
					" = " + meQMTruth.getValue());
		}

		try {
			org.drip.spline.stretch.MultiSegmentSequence stretch =
				org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
					(strName + "_STRETCH", aiDate, adblQM, aPRBP, gccp.bestFitWeightedResponse(),
						gccp.calibrationBoundaryCondition(), gccp.calibrationDetail());

			org.drip.state.discount.MergedDiscountForwardCurve dcMultiPass = null;

			if (org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR.equalsIgnoreCase
				(strSmootheningQM))
				dcMultiPass = new org.drip.state.curve.DiscountFactorDiscountCurve (strName, new
					org.drip.spline.grid.OverlappingStretchSpan (stretch));
			else if
				(org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE.equalsIgnoreCase
				(strSmootheningQM))
				dcMultiPass = new org.drip.state.curve.ZeroRateDiscountCurve (strName, new
					org.drip.spline.grid.OverlappingStretchSpan (stretch));

			return dcMultiPass;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Build a Locally Smoothed Instance of the Discount Curve using the Custom Parameters
	 * 
	 * @param dcShapePreserver Instance of the Shape Preserving Discount Curve
	 * @param llsc The Linear Latent State Calibrator Instance
	 * @param lccp Local Smoothing Curve Control Parameters
	 * @param valParam Valuation Parameters
	 * @param pricerParam Pricer Parameters
	 * @param csqs Market Parameters
	 * @param quotingParam Quoting Parameters
	 * 
	 * @return Locally Smoothed Instance of the Discount Curve
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve SmoothingLocalControlBuild (
		final org.drip.state.discount.MergedDiscountForwardCurve dcShapePreserver,
		final org.drip.state.inference.LinearLatentStateCalibrator llsc,
		final org.drip.state.estimator.LocalControlCurveParams lccp,
		final org.drip.param.valuation.ValuationParams valParam,
		final org.drip.param.pricer.CreditPricerParams pricerParam,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParam)
	{
		if (null == dcShapePreserver) return null;

		if (null == lccp) return dcShapePreserver;

		java.lang.String strSmootheningQM = lccp.smootheningQuantificationMetric();

		java.util.Map<java.lang.Integer, java.lang.Double> mapQMTruth = dcShapePreserver.canonicalTruthness
			(strSmootheningQM);

		if (null == mapQMTruth) return null;

		int iTruthSize = mapQMTruth.size();

		if (0 == iTruthSize) return null;

		java.util.Set<java.util.Map.Entry<java.lang.Integer, java.lang.Double>> esQMTruth =
			mapQMTruth.entrySet();

		if (null == esQMTruth || 0 == esQMTruth.size()) return null;

		java.lang.String strName = dcShapePreserver.label().fullyQualifiedName();

		int i = 0;
		int[] aiDate = new int[iTruthSize];
		double[] adblQM = new double[iTruthSize];
		org.drip.spline.params.SegmentCustomBuilderControl[] aPRBP = new
			org.drip.spline.params.SegmentCustomBuilderControl[iTruthSize - 1];

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> meQMTruth : esQMTruth) {
			if (null == meQMTruth) return null;

			if (0 != i) aPRBP[i - 1] = lccp.defaultSegmentBuilderControl();

			aiDate[i] = meQMTruth.getKey();

			adblQM[i++] = meQMTruth.getValue();

			if (s_bBlog)
				System.out.println ("\t\t" + new org.drip.analytics.date.JulianDate (meQMTruth.getKey()) +
					" = " + meQMTruth.getValue());
		}

		try {
			org.drip.spline.pchip.LocalMonotoneCkGenerator lcr =
				org.drip.spline.pchip.LocalMonotoneCkGenerator.Create (aiDate, adblQM,
					lccp.C1GeneratorScheme(), lccp.eliminateSpuriousExtrema(), lccp.applyMonotoneFilter());

			if (null == lcr) return null;

			org.drip.spline.stretch.MultiSegmentSequence stretch =
				org.drip.spline.pchip.LocalControlStretchBuilder.CustomSlopeHermiteSpline (strName +
					"_STRETCH", aiDate, adblQM, lcr.C1(), aPRBP, lccp.bestFitWeightedResponse(),
						lccp.calibrationDetail());

			org.drip.state.discount.MergedDiscountForwardCurve dcMultiPass = null;

			if (org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR.equalsIgnoreCase
				(strSmootheningQM))
				dcMultiPass = new org.drip.state.curve.DiscountFactorDiscountCurve (strName, new
					org.drip.spline.grid.OverlappingStretchSpan (stretch));
			else if
				(org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE.equalsIgnoreCase
				(strSmootheningQM))
				dcMultiPass = new org.drip.state.curve.ZeroRateDiscountCurve (strName, new
					org.drip.spline.grid.OverlappingStretchSpan (stretch));

			return dcMultiPass;
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
	 * @param valParams Valuation Parameters
	 * @param pricerParam Pricer Parameters
	 * @param csqs Market Parameters
	 * @param quotingParam Quoting Parameters
	 * @param strBasisType The Basis Type
	 * @param fsbp The Function Set Basis Parameters
	 * @param aCalibComp1 Array of Calibration Components #1
	 * @param adblQuote1 Array of Calibration Quotes #1
	 * @param astrManifestMeasure1 Array of Manifest Measures for component Array #1
	 * @param aCalibComp2 Array of Calibration Components #2
	 * @param adblQuote2 Array of Calibration Quotes #2
	 * @param astrManifestMeasure2 Array of Manifest Measures for component Array #2
	 * @param dblEpochResponse The Stretch Start DF
	 * @param bZeroSmooth TRUE - Turn on the Zero Rate Smoothing
	 * 
	 * @return Instance of the Shape Preserver of the desired basis type
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve DFRateShapePreserver (
		final java.lang.String strName,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParam,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParam,
		final java.lang.String strBasisType,
		final org.drip.spline.basis.FunctionSetBuilderParams fsbp,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp1,
		final double[] adblQuote1,
		final java.lang.String[] astrManifestMeasure1,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp2,
		final double[] adblQuote2,
		final java.lang.String[] astrManifestMeasure2,
		final double dblEpochResponse,
		final boolean bZeroSmooth)
	{
		if (null == strName || strName.isEmpty() || null == strBasisType || strBasisType.isEmpty() || null ==
			valParams || null == fsbp)
			return null;

		int iNumQuote1 = null == adblQuote1 ? 0 : adblQuote1.length;
		int iNumQuote2 = null == adblQuote2 ? 0 : adblQuote2.length;
		int iNumComp1 = null == aCalibComp1 ? 0 : aCalibComp1.length;
		int iNumComp2 = null == aCalibComp2 ? 0 : aCalibComp2.length;
		org.drip.state.estimator.LocalControlCurveParams lccp = null;
		org.drip.state.inference.LinearLatentStateCalibrator llsc = null;
		org.drip.state.inference.LatentStateStretchSpec stretchSpec1 = null;
		org.drip.state.inference.LatentStateStretchSpec stretchSpec2 = null;
		org.drip.state.representation.LatentStateSpecification[] aLSS = null;
		org.drip.state.inference.LatentStateStretchSpec[] aStretchSpec = null;
		org.drip.state.representation.LatentStateSpecification lssFunding = null;
		org.drip.state.discount.MergedDiscountForwardCurve dcShapePreserving = null;
		int iNumManifestMeasures1 = null == astrManifestMeasure1 ? 0 : astrManifestMeasure1.length;
		int iNumManifestMeasures2 = null == astrManifestMeasure2 ? 0 : astrManifestMeasure2.length;
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
			mapForwardLabel = null;

		if ((0 == iNumComp1 && 0 == iNumComp2) || iNumComp1 != iNumQuote1 || iNumComp2 != iNumQuote2 ||
			iNumComp1 != iNumManifestMeasures1 || iNumComp2 != iNumManifestMeasures2)
			return null;

		java.lang.String strCurrency = (0 == iNumComp1 ? aCalibComp2 : aCalibComp1)[0].payCurrency();

		try {
			lssFunding = new org.drip.state.representation.LatentStateSpecification
				(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FUNDING,
					org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR,
						org.drip.state.identifier.FundingLabel.Standard (strCurrency));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		if (0 != iNumComp1) mapForwardLabel = aCalibComp1[0].forwardLabel();

		if (null == mapForwardLabel && 0 != iNumComp2) mapForwardLabel = aCalibComp2[0].forwardLabel();

		if (null == mapForwardLabel || 0 == mapForwardLabel.size())
			aLSS = new org.drip.state.representation.LatentStateSpecification[] {lssFunding};
		else {
			try {
				aLSS = new org.drip.state.representation.LatentStateSpecification[] {lssFunding, new
					org.drip.state.representation.LatentStateSpecification
						(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FORWARD,
							org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE,
								mapForwardLabel.get ("DERIVED"))};
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		if (0 != iNumComp1) {
			org.drip.state.inference.LatentStateSegmentSpec[] aSegmentSpec = new
				org.drip.state.inference.LatentStateSegmentSpec[iNumComp1];

			try {
				for (int i = 0; i < iNumComp1; ++i) {
					org.drip.product.calib.ProductQuoteSet pqs = aCalibComp1[i].calibQuoteSet (aLSS);

					if (null == pqs || !pqs.set (astrManifestMeasure1[i], adblQuote1[i])) return null;

					aSegmentSpec[i] = new org.drip.state.inference.LatentStateSegmentSpec (aCalibComp1[i],
						pqs);
				}

				stretchSpec1 = new org.drip.state.inference.LatentStateStretchSpec (strName + "_COMP1",
					aSegmentSpec);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		if (0 != iNumComp2) {
			org.drip.state.inference.LatentStateSegmentSpec[] aSegmentSpec = new
				org.drip.state.inference.LatentStateSegmentSpec[iNumComp2];

			try {
				for (int i = 0; i < iNumComp2; ++i) {
					org.drip.product.calib.ProductQuoteSet pqs = aCalibComp2[i].calibQuoteSet (aLSS);

					if (null == pqs || !pqs.set (astrManifestMeasure2[i], adblQuote2[i])) return null;

					aSegmentSpec[i] = new org.drip.state.inference.LatentStateSegmentSpec (aCalibComp2[i],
						pqs);
				}

				stretchSpec2 = new org.drip.state.inference.LatentStateStretchSpec (strName + "_COMP2",
					aSegmentSpec);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		if (null == stretchSpec1 && null == stretchSpec2) return null;

		if (null == stretchSpec1)
			aStretchSpec = new org.drip.state.inference.LatentStateStretchSpec[] {stretchSpec2};
		else if (null == stretchSpec2)
			aStretchSpec = new org.drip.state.inference.LatentStateStretchSpec[] {stretchSpec1};
		else
			aStretchSpec = new org.drip.state.inference.LatentStateStretchSpec[] {stretchSpec1,
				stretchSpec2};

		try {
			llsc = new org.drip.state.inference.LinearLatentStateCalibrator (new
				org.drip.spline.params.SegmentCustomBuilderControl (strBasisType, fsbp,
					org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), new
						org.drip.spline.params.ResponseScalingShapeControl (true, new
							org.drip.function.r1tor1.QuadraticRationalShapeControl (0.)), null),
								org.drip.spline.stretch.BoundarySettings.NaturalStandard(),
									org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE, null, null);

			dcShapePreserving = ShapePreservingDFBuild (strCurrency, llsc, aStretchSpec, valParams,
				pricerParam, csqs, quotingParam, dblEpochResponse);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		if (!bZeroSmooth) return dcShapePreserving;

		try {
			lccp = new org.drip.state.estimator.LocalControlCurveParams
				(org.drip.spline.pchip.LocalMonotoneCkGenerator.C1_HYMAN83,
					org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE, new
						org.drip.spline.params.SegmentCustomBuilderControl
							(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
								org.drip.spline.basis.PolynomialFunctionSetParams (4),
									org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), new
										org.drip.spline.params.ResponseScalingShapeControl (true, new
											org.drip.function.r1tor1.QuadraticRationalShapeControl (0.)),
												null),
													org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE,
														null, null, true, true);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return SmoothingLocalControlBuild (dcShapePreserving, llsc, lccp, valParams, null, null, null);
	}

	/**
	 * Construct an instance of the Shape Preserver of the KLK Hyperbolic Tension Type, using the specified
	 *  basis set builder parameters.
	 * 
	 * @param strName Curve Name
	 * @param valParams Valuation Parameters
	 * @param aCalibComp1 Array of Calibration Components #1
	 * @param adblQuote1 Array of Calibration Quotes #1
	 * @param astrManifestMeasure1 Array of Manifest Measures for component Array #1
	 * @param aCalibComp2 Array of Calibration Components #2
	 * @param adblQuote2 Array of Calibration Quotes #2
	 * @param astrManifestMeasure2 Array of Manifest Measures for component Array #2
	 * @param bZeroSmooth TRUE - Turn on the Zero Rate Smoothing
	 * 
	 * @return Instance of the Shape Preserver of the desired basis type
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve CubicKLKHyperbolicDFRateShapePreserver (
		final java.lang.String strName,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp1,
		final double[] adblQuote1,
		final java.lang.String[] astrManifestMeasure1,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp2,
		final double[] adblQuote2,
		final java.lang.String[] astrManifestMeasure2,
		final boolean bZeroSmooth)
	{
		try {
			return DFRateShapePreserver (strName, valParams, null, null, null,
				org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION, new
					org.drip.spline.basis.ExponentialTensionSetParams (1.), aCalibComp1, adblQuote1,
						astrManifestMeasure1, aCalibComp2, adblQuote2, astrManifestMeasure2, 1.,
							bZeroSmooth);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an instance of the Shape Preserver of the Cubic Polynomial Type, using the specified
	 *  basis set builder parameters.
	 * 
	 * @param strName Curve Name
	 * @param valParams Valuation Parameters
	 * @param aCalibComp1 Array of Calibration Components #1
	 * @param adblQuote1 Array of Calibration Quotes #1
	 * @param astrManifestMeasure1 Array of Manifest Measures for component Array #1
	 * @param aCalibComp2 Array of Calibration Components #2
	 * @param adblQuote2 Array of Calibration Quotes #2
	 * @param astrManifestMeasure2 Array of Manifest Measures for component Array #2
	 * @param bZeroSmooth TRUE - Turn on the Zero Rate Smoothing
	 * 
	 * @return Instance of the Shape Preserver of the desired basis type
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve CubicPolyDFRateShapePreserver (
		final java.lang.String strName,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp1,
		final double[] adblQuote1,
		final java.lang.String[] astrManifestMeasure1,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp2,
		final double[] adblQuote2,
		final java.lang.String[] astrManifestMeasure2,
		final boolean bZeroSmooth)
	{
		try {
			return DFRateShapePreserver (strName, valParams, null, null, null,
				org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
					org.drip.spline.basis.PolynomialFunctionSetParams (4), aCalibComp1, adblQuote1,
						astrManifestMeasure1, aCalibComp2, adblQuote2, astrManifestMeasure2, 1.,
							bZeroSmooth);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Customizable DENSE Curve Creation Methodology - the references are:
	 * 
	 *  - Sankar, L. (1997): OFUTS – An Alternative Yield Curve Interpolator F. A. S. T. Research
	 *  	Documentation Bear Sterns.
	 *  
	 *  - Nahum, E. (2004): Changes to Yield Curve Construction – Linear Stripping of the Short End of the
	 *  	Curve F. A. S. T. Research Documentation Bear Sterns.
	 *  
	 *  - Kinlay, J., and X. Bai (2009): Yield Curve Construction Models – Tools and Techniques 
	 *  	(http://www.jonathankinlay.com/Articles/Yield Curve Construction Models.pdf)
	 *  
	 * @param strName The Curve Name
	 * @param valParams Valuation Parameters
	 * @param aCalibComp1 Array of Stretch #1 Calibration Components
	 * @param adblQuote1 Array of Stretch #1 Calibration Quotes
	 * @param strTenor1 Stretch #1 Instrument set re-construction Tenor
	 * @param astrManifestMeasure1 Array of Manifest Measures for component Array #1
	 * @param aCalibComp2 Array of Stretch #2 Calibration Components
	 * @param adblQuote2 Array of Stretch #2 Calibration Quotes
	 * @param strTenor2 Stretch #2 Instrument set re-construction Tenor
	 * @param astrManifestMeasure2 Array of Manifest Measures for component Array #2
	 * @param tldf The Turns List
	 * 
	 * @return The Customized DENSE Curve.
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve CustomDENSE (
		final java.lang.String strName,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp1,
		final double[] adblQuote1,
		final java.lang.String strTenor1,
		final java.lang.String[] astrManifestMeasure1,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp2,
		final double[] adblQuote2,
		final java.lang.String strTenor2,
		final java.lang.String[] astrManifestMeasure2,
		final org.drip.state.discount.TurnListDiscountFactor tldf)
	{
		org.drip.state.discount.MergedDiscountForwardCurve dcShapePreserver = CubicKLKHyperbolicDFRateShapePreserver
			(strName, valParams, aCalibComp1, adblQuote1, astrManifestMeasure1, aCalibComp2, adblQuote2,
				astrManifestMeasure2, false);

		if (null == dcShapePreserver || (null != tldf && !dcShapePreserver.setTurns (tldf))) return null;

		org.drip.param.market.CurveSurfaceQuoteContainer csqs = org.drip.param.creator.MarketParamsBuilder.Create
			(dcShapePreserver, null, null, null, null, null, null);

		if (null == csqs) return null;

		CompQuote[] aCQ1 = null;

		java.lang.String strCurrency = aCalibComp1[0].payCurrency();

		if (null == strTenor1 || strTenor1.isEmpty()) {
			if (null != aCalibComp1) {
				int iNumComp1 = aCalibComp1.length;

				if (0 != iNumComp1) {
					aCQ1 = new CompQuote[iNumComp1];

					for (int i = 0; i < iNumComp1; ++i)
						aCQ1[i] = new CompQuote (aCalibComp1[i], adblQuote1[i]);
				}
			}
		} else
			aCQ1 = CompQuote (valParams, csqs, strCurrency, aCalibComp1[0].effectiveDate(),
				aCalibComp1[0].maturityDate(), aCalibComp1[aCalibComp1.length - 1].maturityDate(), strTenor1,
					false);

		if (null == strTenor2 || strTenor2.isEmpty()) return dcShapePreserver;

		CompQuote[] aCQ2 = CompQuote (valParams, csqs, strCurrency, aCalibComp2[0].effectiveDate(),
			aCalibComp2[0].maturityDate(), aCalibComp2[aCalibComp2.length - 1].maturityDate(), strTenor2,
				true);

		int iNumDENSEComp1 = null == aCQ1 ? 0 : aCQ1.length;
		int iNumDENSEComp2 = null == aCQ2 ? 0 : aCQ2.length;
		int iTotalNumDENSEComp = iNumDENSEComp1 + iNumDENSEComp2;

		if (0 == iTotalNumDENSEComp) return null;

		double[] adblCalibQuote = new double[iTotalNumDENSEComp];
		java.lang.String[] astrCalibMeasure = new java.lang.String[iTotalNumDENSEComp];
		org.drip.product.definition.CalibratableComponent[] aCalibComp = new
			org.drip.product.definition.CalibratableComponent[iTotalNumDENSEComp];

		for (int i = 0; i < iNumDENSEComp1; ++i) {
			astrCalibMeasure[i] = "Rate";
			aCalibComp[i] = aCQ1[i]._comp;
			adblCalibQuote[i] = aCQ1[i]._dblQuote;
		}

		for (int i = iNumDENSEComp1; i < iTotalNumDENSEComp; ++i) {
			astrCalibMeasure[i] = "Rate";
			aCalibComp[i] = aCQ2[i - iNumDENSEComp1]._comp;
			adblCalibQuote[i] = aCQ2[i - iNumDENSEComp1]._dblQuote;
		}

		try {
			return ScenarioDiscountCurveBuilder.NonlinearBuild (new org.drip.analytics.date.JulianDate
				(valParams.valueDate()), strCurrency, aCalibComp, adblCalibQuote, astrCalibMeasure, null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * The Standard DENSE Curve Creation Methodology - this uses no re-construction set for the short term,
	 * 	and uses 3M dense re-construction for the Swap Set. The references are:
	 * 
	 *  - Sankar, L. (1997): OFUTS – An Alternative Yield Curve Interpolator F. A. S. T. Research
	 *  	Documentation Bear Sterns.
	 *  
	 *  - Nahum, E. (2004): Changes to Yield Curve Construction – Linear Stripping of the Short End of the
	 *  	Curve F. A. S. T. Research Documentation Bear Sterns.
	 *  
	 *  - Kinlay, J., and X. Bai (2009): Yield Curve Construction Models – Tools and Techniques 
	 *  	(http://www.jonathankinlay.com/Articles/Yield Curve Construction Models.pdf)
	 *  
	 * @param strName The Curve Name
	 * @param valParams Valuation Parameters
	 * @param aCalibComp1 Array of Stretch #1 Calibration Components
	 * @param adblQuote1 Array of Stretch #1 Calibration Quotes
	 * @param astrManifestMeasure1 Array of Manifest Measures for component Array #1
	 * @param aCalibComp2 Array of Stretch #2 Calibration Components
	 * @param adblQuote2 Array of Stretch #2 Calibration Quotes
	 * @param astrManifestMeasure2 Array of Manifest Measures for component Array #2
	 * @param tldf The Turns List
	 * 
	 * @return The Customized DENSE Curve.
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve DENSE (
		final java.lang.String strName,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp1,
		final double[] adblQuote1,
		final java.lang.String[] astrManifestMeasure1,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp2,
		final double[] adblQuote2,
		final java.lang.String[] astrManifestMeasure2,
		final org.drip.state.discount.TurnListDiscountFactor tldf)
	{
		return CustomDENSE (strName, valParams, aCalibComp1, adblQuote1, null, astrManifestMeasure1,
			aCalibComp2, adblQuote2, "3M", astrManifestMeasure2, tldf);
	}

	/**
	 * The DUAL DENSE Curve Creation Methodology - this uses configurable re-construction set for the short
	 *  term, and another configurable re-construction for the Swap Set. 1D re-construction tenor for the
	 *  short end will result in CDF (Constant Daily Forward) Discount Curve. The references are:
	 * 
	 *  - Sankar, L. (1997): OFUTS – An Alternative Yield Curve Interpolator F. A. S. T. Research
	 *  	Documentation Bear Sterns.
	 *  
	 *  - Nahum, E. (2004): Changes to Yield Curve Construction – Linear Stripping of the Short End of the
	 *  	Curve F. A. S. T. Research Documentation Bear Sterns.
	 *  
	 *  - Kinlay, J., and X. Bai (2009): Yield Curve Construction Models – Tools and Techniques 
	 *  	(http://www.jonathankinlay.com/Articles/Yield Curve Construction Models.pdf)
	 *  
	 * @param strName The Curve Name
	 * @param valParams Valuation Parameters
	 * @param aCalibComp1 Array of Stretch #1 Calibration Components
	 * @param adblQuote1 Array of Stretch #1 Calibration Quotes
	 * @param strTenor1 Stretch #1 Instrument set re-construction Tenor
	 * @param astrManifestMeasure1 Array of Manifest Measures for component Array #1
	 * @param aCalibComp2 Array of Stretch #2 Calibration Components
	 * @param adblQuote2 Array of Stretch #2 Calibration Quotes
	 * @param strTenor2 Stretch #2 Instrument set re-construction Tenor
	 * @param astrManifestMeasure2 Array of Manifest Measures for component Array #2
	 * @param tldf The Turns List
	 * 
	 * @return The Customized DENSE Curve.
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve DUALDENSE (
		final java.lang.String strName,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp1,
		final double[] adblQuote1,
		final java.lang.String strTenor1,
		final java.lang.String[] astrManifestMeasure1,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp2,
		final double[] adblQuote2,
		final java.lang.String strTenor2,
		final java.lang.String[] astrManifestMeasure2,
		final org.drip.state.discount.TurnListDiscountFactor tldf)
	{
		return CustomDENSE (strName, valParams, aCalibComp1, adblQuote1, strTenor1, astrManifestMeasure1,
			aCalibComp2, adblQuote2, strTenor2, astrManifestMeasure2, tldf);
	}

	/**
	 * Create an Instance of the Custom Splined Discount Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart Tenor Start Date
	 * @param strCurrency The Currency
	 * @param aiDate Array of Dates
	 * @param adblDF Array of Discount Factors
	 * @param scbc The Segment Custom Builder Control
	 * 
	 * @return The Instance of the Basis Curve
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve CustomSplineDiscountCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final int[] aiDate,
		final double[] adblDF,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc)
	{
		if (null == strName || strName.isEmpty() || null == aiDate || null == dtStart) return null;

		int iNumDate = aiDate.length;

		if (0 == iNumDate) return null;

		double[] adblResponseValue = new double[iNumDate + 1];
		double[] adblPredictorOrdinate = new double[iNumDate + 1];
		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumDate];

		for (int i = 0; i <= iNumDate; ++i) {
			adblPredictorOrdinate[i] = 0 == i ? dtStart.julian() : aiDate[i - 1];

			adblResponseValue[i] = 0 == i ? 1. : adblDF[i - 1];

			if (0 != i) aSCBC[i - 1] = scbc;
		}

		try {
			return new org.drip.state.curve.DiscountFactorDiscountCurve (strCurrency, new
				org.drip.spline.grid.OverlappingStretchSpan
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
						(strName, adblPredictorOrdinate, adblResponseValue, aSCBC, null,
							org.drip.spline.stretch.BoundarySettings.NaturalStandard(),
								org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE)));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Cubic Polynomial Splined DF Discount Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart Tenor Start Date
	 * @param strCurrency The Currency
	 * @param aiDate Array of Dates
	 * @param adblDF Array of Discount Factors
	 * 
	 * @return The Instance of the Discount Curve
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve CubicPolynomialDiscountCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final int[] aiDate,
		final double[] adblDF)
	{
		try {
			return CustomSplineDiscountCurve (strName, dtStart, strCurrency, aiDate, adblDF, new
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
	 * Create an Instance of the Quartic Polynomial Splined DF Discount Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart Tenor Start Date
	 * @param strCurrency The Currency
	 * @param aiDate Array of Dates
	 * @param adblDF Array of Discount Factors
	 * 
	 * @return The Instance of the Discount Curve
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve QuarticPolynomialDiscountCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final int[] aiDate,
		final double[] adblDF)
	{
		try {
			return CustomSplineDiscountCurve (strName, dtStart, strCurrency, aiDate, adblDF, new
				org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
						org.drip.spline.basis.PolynomialFunctionSetParams (5),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Kaklis-Pandelis Splined DF Discount Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart Tenor Start Date
	 * @param strCurrency The Currency
	 * @param aiDate Array of Dates
	 * @param adblDF Array of Discount Factors
	 * 
	 * @return The Instance of the Discount Curve
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve KaklisPandelisDiscountCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final int[] aiDate,
		final double[] adblDF)
	{
		try {
			return CustomSplineDiscountCurve (strName, dtStart, strCurrency, aiDate, adblDF, new
				org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KAKLIS_PANDELIS, new
						org.drip.spline.basis.KaklisPandelisSetParams (2),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Hyperbolic Splined DF Discount Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart Tenor Start Date
	 * @param strCurrency The Currency
	 * @param aiDate Array of Dates
	 * @param adblDF Array of Discount Factors
	 * @param dblTension The Tension Parameter
	 * 
	 * @return The Instance of the Discount Curve
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve KLKHyperbolicDiscountCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final int[] aiDate,
		final double[] adblDF,
		final double dblTension)
	{
		try {
			return CustomSplineDiscountCurve (strName, dtStart, strCurrency, aiDate, adblDF, new
				org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION,
						new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Exponential Splined DF Discount Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart Tenor Start Date
	 * @param strCurrency The Currency
	 * @param aiDate Array of Dates
	 * @param adblDF Array of Discount Factors
	 * @param dblTension The Tension Parameter
	 * 
	 * @return The Instance of the Discount Curve
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve KLKExponentialDiscountCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final int[] aiDate,
		final double[] adblDF,
		final double dblTension)
	{
		try {
			return CustomSplineDiscountCurve (strName, dtStart, strCurrency, aiDate, adblDF, new
			org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_EXPONENTIAL_TENSION,
				new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
					org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Linear Rational Splined DF Discount Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart Tenor Start Date
	 * @param strCurrency The Currency
	 * @param aiDate Array of Dates
	 * @param adblDF Array of Discount Factors
	 * @param dblTension The Tension Parameter
	 * 
	 * @return The Instance of the Discount Curve
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve KLKRationalLinearDiscountCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final int[] aiDate,
		final double[] adblDF,
		final double dblTension)
	{
		try {
			return CustomSplineDiscountCurve (strName, dtStart, strCurrency, aiDate, adblDF, new
				org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION,
				new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
					org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Quadratic Rational Splined DF Discount Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart Tenor Start Date
	 * @param strCurrency The Currency
	 * @param aiDate Array of Dates
	 * @param adblDF Array of Discount Factors
	 * @param dblTension The Tension Parameter
	 * 
	 * @return The Instance of the Discount Curve
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve KLKRationalQuadraticDiscountCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final int[] aiDate,
		final double[] adblDF,
		final double dblTension)
	{
		try {
			return CustomSplineDiscountCurve (strName, dtStart, strCurrency, aiDate, adblDF, new
				org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION,
				new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
					org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Build a Discount Curve from an array of discount factors
	 * 
	 * @param dtStart Start Date
	 * @param strCurrency Currency
	 * @param aiDate Array of dates
	 * @param adblDF array of discount factors
	 * 
	 * @return Discount Curve
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve BuildFromDF (
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final int aiDate[],
		final double adblDF[])
	{
		if (null == aiDate || 0 == aiDate.length || null == adblDF || aiDate.length != adblDF.length ||
			null == dtStart || null == strCurrency || strCurrency.isEmpty())
			return null;

		double dblDFBegin = 1.;
		double[] adblRate = new double[aiDate.length];

		double dblPeriodBegin = dtStart.julian();

		for (int i = 0; i < aiDate.length; ++i) {
			if (aiDate[i] <= dblPeriodBegin) return null;

			adblRate[i] = 365.25 / (aiDate[i] - dblPeriodBegin) * java.lang.Math.log (dblDFBegin /
				adblDF[i]);

			dblDFBegin = adblDF[i];
			dblPeriodBegin = aiDate[i];
		}

		try {
			return new org.drip.state.nonlinear.FlatForwardDiscountCurve (dtStart, strCurrency, aiDate,
				adblRate, false, "", -1);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a Discount Curve from the Exponentially Compounded Flat Rate
	 * 
	 * @param dtStart Start Date
	 * @param strCurrency Currency
	 * @param dblRate Rate
	 * 
	 * @return Discount Curve
	 */

	public static final org.drip.state.discount.ExplicitBootDiscountCurve ExponentiallyCompoundedFlatRate (
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final double dblRate)
	{
		if (null == dtStart) return null;

		try {
			return new org.drip.state.nonlinear.FlatForwardDiscountCurve (dtStart, strCurrency, new int[]
				{dtStart.julian()}, new double[] {dblRate}, false, "", -1);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a Discount Curve from the Discretely Compounded Flat Rate
	 * 
	 * @param dtStart Start Date
	 * @param strCurrency Currency
	 * @param dblRate Rate
	 * @param strCompoundingDayCount Day Count Convention to be used for Discrete Compounding
	 * @param iCompoundingFreq Frequency to be used for Discrete Compounding
	 * 
	 * @return Discount Curve
	 */

	public static final org.drip.state.discount.ExplicitBootDiscountCurve DiscretelyCompoundedFlatRate (
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final double dblRate,
		final java.lang.String strCompoundingDayCount,
		final int iCompoundingFreq)
	{
		if (null == dtStart) return null;

		try {
			return new org.drip.state.nonlinear.FlatForwardDiscountCurve (dtStart, strCurrency, new int[]
				{dtStart.julian()}, new double[] {dblRate}, true, strCompoundingDayCount, iCompoundingFreq);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a Discount Curve from the Flat Yield
	 * 
	 * @param dtStart Start Date
	 * @param strCurrency Currency
	 * @param dblYield Yield
	 * @param strCompoundingDayCount Day Count Convention to be used for Discrete Compounding
	 * @param iCompoundingFreq Frequency to be used for Discrete Compounding
	 * 
	 * @return The Discount Curve Instance
	 */

	public static final org.drip.state.discount.ExplicitBootDiscountCurve CreateFromFlatYield (
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final double dblYield,
		final java.lang.String strCompoundingDayCount,
		final int iCompoundingFreq)
	{
		if (null == dtStart || !org.drip.quant.common.NumberUtil.IsValid (dblYield)) return null;

		try {
			return new org.drip.state.nonlinear.FlatForwardDiscountCurve (dtStart, strCurrency, new int[]
				{dtStart.julian()}, new double[] {dblYield}, true, strCompoundingDayCount, iCompoundingFreq);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a discount curve from an array of dates/rates
	 * 
	 * @param dtStart Start Date
	 * @param strCurrency Currency
	 * @param aiDate array of dates
	 * @param adblRate array of rates
	 * 
	 * @return Creates the discount curve
	 */

	public static final org.drip.state.discount.ExplicitBootDiscountCurve PiecewiseForward (
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final int[] aiDate,
		final double[] adblRate)
	{
		try {
			return new org.drip.state.nonlinear.FlatForwardDiscountCurve (dtStart, strCurrency, aiDate,
				adblRate, false, "", -1);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
