
package org.drip.service.template;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * LatentMarketStateBuilder contains static Helper API to facilitate Construction of the Latent Market States
 * 	as Curves/Surfaces.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LatentMarketStateBuilder {

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
	 * @param scbc Segment Custom Builder Control
	 * 
	 * @return The Funding Curve Instance
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve FundingCurve (
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
		final org.drip.spline.params.SegmentCustomBuilderControl scbc)
	{
		if (null == dtSpot || null == strCurrency || strCurrency.isEmpty()) return null;

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (0, strCurrency);

		org.drip.state.inference.LatentStateStretchSpec lsssDeposit = null;
		org.drip.state.inference.LatentStateStretchSpec lsssFutures = null;
		org.drip.state.inference.LatentStateStretchSpec lsssFixFloat = null;
		int iNumFuturesComp = null == adblFuturesQuote ? 0 : adblFuturesQuote.length;
		int iNumDepositQuote = null == adblDepositQuote ? 0 : adblDepositQuote.length;
		int iNumFixFloatQuote = null == adblFixFloatQuote ? 0 : adblFixFloatQuote.length;
		int iNumDepositComp = null == astrDepositMaturityTenor ? 0 : astrDepositMaturityTenor.length;
		int iNumFixFloatComp = null == astrFixFloatMaturityTenor ? 0 : astrFixFloatMaturityTenor.length;

		if (iNumDepositQuote != iNumDepositComp || iNumFixFloatQuote != iNumFixFloatComp) return null;

		if (0 != iNumDepositComp)
			lsssDeposit = org.drip.state.estimator.LatentStateStretchBuilder.ForwardFundingStretchSpec
				("DEPOSIT", org.drip.service.template.OTCInstrumentBuilder.FundingDeposit (dtEffective,
					strCurrency, astrDepositMaturityTenor), strDepositMeasure, adblDepositQuote);

		if (0 != iNumFuturesComp)
			lsssFutures = org.drip.state.estimator.LatentStateStretchBuilder.ForwardFundingStretchSpec
				("FUTURES", org.drip.service.template.ExchangeInstrumentBuilder.ForwardRateFuturesPack
					(dtEffective, iNumFuturesComp, strCurrency), strFuturesMeasure, adblFuturesQuote);

		if (0 != iNumFixFloatComp)
			lsssFixFloat = org.drip.state.estimator.LatentStateStretchBuilder.ForwardFundingStretchSpec
				("FIXFLOAT", org.drip.service.template.OTCInstrumentBuilder.FixFloatStandard (dtEffective,
					strCurrency, "ALL", astrFixFloatMaturityTenor, "MAIN", 0.), strFixFloatMeasure,
						adblFixFloatQuote);

		try {
			org.drip.state.inference.LinearLatentStateCalibrator lcc = new
				org.drip.state.inference.LinearLatentStateCalibrator (scbc,
					org.drip.spline.stretch.BoundarySettings.NaturalStandard(),
						org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE, null, null);

			return org.drip.state.creator.ScenarioDiscountCurveBuilder.ShapePreservingDFBuild (strCurrency,
				lcc, new org.drip.state.inference.LatentStateStretchSpec[] {lsssDeposit, lsssFutures,
					lsssFixFloat}, org.drip.param.valuation.ValuationParams.Spot (dtSpot.julian()), null,
						null, null, 1.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Shape Preserving Funding Curve Based off of the Input Exchange/OTC Market Instruments
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
	 * 
	 * @return The Funding Curve Instance
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve ShapePreservingFundingCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCurrency,
		final java.lang.String[] astrDepositMaturityTenor,
		final double[] adblDepositQuote,
		final java.lang.String strDepositMeasure,
		final double[] adblFuturesQuote,
		final java.lang.String strFuturesMeasure,
		final java.lang.String[] astrFixFloatMaturityTenor,
		final double[] adblFixFloatQuote,
		final java.lang.String strFixFloatMeasure)
	{
		try {
			return FundingCurve (dtSpot, strCurrency, astrDepositMaturityTenor, adblDepositQuote,
				strDepositMeasure, adblFuturesQuote, strFuturesMeasure, astrFixFloatMaturityTenor,
					adblFixFloatQuote, strFixFloatMeasure, new
						org.drip.spline.params.SegmentCustomBuilderControl
							(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
								org.drip.spline.basis.PolynomialFunctionSetParams (2),
									org.drip.spline.params.SegmentInelasticDesignControl.Create (0, 2), new
										org.drip.spline.params.ResponseScalingShapeControl (true, new
											org.drip.function.r1tor1.QuadraticRationalShapeControl (0.)),
												null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Smooth Funding Curve Based off of the Input Exchange/OTC Market Instruments
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
	 * 
	 * @return The Funding Curve Instance
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve SmoothFundingCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCurrency,
		final java.lang.String[] astrDepositMaturityTenor,
		final double[] adblDepositQuote,
		final java.lang.String strDepositMeasure,
		final double[] adblFuturesQuote,
		final java.lang.String strFuturesMeasure,
		final java.lang.String[] astrFixFloatMaturityTenor,
		final double[] adblFixFloatQuote,
		final java.lang.String strFixFloatMeasure)
	{
		try {
			return FundingCurve (dtSpot, strCurrency, astrDepositMaturityTenor, adblDepositQuote,
				strDepositMeasure, adblFuturesQuote, strFuturesMeasure, astrFixFloatMaturityTenor,
					adblFixFloatQuote, strFixFloatMeasure, new
						org.drip.spline.params.SegmentCustomBuilderControl
							(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
								org.drip.spline.basis.PolynomialFunctionSetParams (4),
									org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), new
										org.drip.spline.params.ResponseScalingShapeControl (true, new
											org.drip.function.r1tor1.QuadraticRationalShapeControl (0.)),
												null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Funding Curve Based off of the Input Exchange/OTC Market Instruments
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
	 * 
	 * @return The Funding Curve Instance
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve FundingCurve (
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
		final int iLatentStateType)
	{
		if (SHAPE_PRESERVING == iLatentStateType)
			return ShapePreservingFundingCurve (dtSpot, strCurrency, astrDepositMaturityTenor, adblDepositQuote,
				strDepositMeasure, adblFuturesQuote, strFuturesMeasure, astrFixFloatMaturityTenor,
					adblFixFloatQuote, strFixFloatMeasure);

		if (SMOOTH == iLatentStateType)
			return SmoothFundingCurve (dtSpot, strCurrency, astrDepositMaturityTenor, adblDepositQuote,
				strDepositMeasure, adblFuturesQuote, strFuturesMeasure, astrFixFloatMaturityTenor,
					adblFixFloatQuote, strFixFloatMeasure);

		return null;
	}

	/**
	 * Construct a Instance of the Forward Curve off of Exchange/OTC Market Instruments
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
	 * @param scbc Segment Custom Builder Control Parameters
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
		final org.drip.spline.params.SegmentCustomBuilderControl scbc)
	{
		if (null == dtSpot || null == forwardLabel || null == dc) return null;

		java.lang.String strCurrency = forwardLabel.currency();

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (0, strCurrency);

		org.drip.state.inference.LatentStateStretchSpec lsssFRA = null;
		org.drip.state.inference.LinearLatentStateCalibrator lcc = null;
		int iNumFRAQuote = null == adblFRAQuote ? 0 : adblFRAQuote.length;
		org.drip.state.inference.LatentStateStretchSpec lsssDeposit = null;
		org.drip.state.inference.LatentStateStretchSpec lsssFixFloat = null;
		org.drip.state.inference.LatentStateStretchSpec lsssFloatFloat = null;
		int iNumDepositQuote = null == adblDepositQuote ? 0 : adblDepositQuote.length;
		org.drip.state.inference.LatentStateStretchSpec lsssSyntheticFloatFloat = null;
		int iNumFRAComp = null == astrFRAMaturityTenor ? 0 : astrFRAMaturityTenor.length;
		int iNumFixFloatQuote = null == adblFixFloatQuote ? 0 : adblFixFloatQuote.length;
		int iNumFloatFloatQuote = null == adblFloatFloatQuote ? 0 : adblFloatFloatQuote.length;
		int iNumDepositComp = null == astrDepositMaturityTenor ? 0 : astrDepositMaturityTenor.length;
		int iNumFixFloatComp = null == astrFixFloatMaturityTenor ? 0 : astrFixFloatMaturityTenor.length;
		int iNumFloatFloatComp = null == astrFloatFloatMaturityTenor ? 0 :
			astrFloatFloatMaturityTenor.length;
		int iNumSyntheticFloatFloatQuote = null == adblSyntheticFloatFloatQuote ? 0 :
			adblSyntheticFloatFloatQuote.length;
		int iNumSyntheticFloatFloatComp = null == astrSyntheticFloatFloatMaturityTenor ? 0 :
			astrSyntheticFloatFloatMaturityTenor.length;

		if (iNumDepositQuote != iNumDepositComp || iNumFRAQuote != iNumFRAComp || iNumFixFloatQuote !=
			iNumFixFloatComp || iNumFloatFloatQuote != iNumFloatFloatComp || iNumSyntheticFloatFloatQuote !=
				iNumSyntheticFloatFloatComp)
			return null;

		if (0 != iNumDepositComp)
			lsssDeposit = org.drip.state.estimator.LatentStateStretchBuilder.ForwardStretchSpec ("DEPOSIT",
				org.drip.service.template.OTCInstrumentBuilder.ForwardRateDeposit (dtEffective,
					astrDepositMaturityTenor, forwardLabel), strDepositMeasure, adblDepositQuote);

		if (0 != iNumFRAComp)
			lsssFRA = org.drip.state.estimator.LatentStateStretchBuilder.ForwardStretchSpec ("FRA",
				org.drip.service.template.OTCInstrumentBuilder.FRAStandard (dtEffective, forwardLabel,
					astrFRAMaturityTenor, adblFRAQuote), strFRAMeasure, adblFRAQuote);

		if (0 != iNumFixFloatComp)
			lsssFixFloat = org.drip.state.estimator.LatentStateStretchBuilder.ForwardStretchSpec ("FIXFLOAT",
				org.drip.service.template.OTCInstrumentBuilder.FixFloatCustom (dtEffective, forwardLabel,
					astrFixFloatMaturityTenor), strFixFloatMeasure, adblFixFloatQuote);

		if (0 != iNumFloatFloatComp)
			lsssFloatFloat = org.drip.state.estimator.LatentStateStretchBuilder.ForwardStretchSpec
				("FLOATFLOAT", org.drip.service.template.OTCInstrumentBuilder.FloatFloat (dtEffective,
					strCurrency, forwardLabel.tenor(), astrFloatFloatMaturityTenor, 0.),
						strFloatFloatMeasure, adblFloatFloatQuote);

		if (0 != iNumSyntheticFloatFloatComp)
			lsssSyntheticFloatFloat = org.drip.state.estimator.LatentStateStretchBuilder.ForwardStretchSpec
				("SYNTHETICFLOATFLOAT", org.drip.service.template.OTCInstrumentBuilder.FloatFloat
					(dtEffective, strCurrency, forwardLabel.tenor(), astrSyntheticFloatFloatMaturityTenor,
						0.), strSyntheticFloatFloatMeasure, adblSyntheticFloatFloatQuote);

		org.drip.state.inference.LatentStateStretchSpec[] aStretchSpec = new
			org.drip.state.inference.LatentStateStretchSpec[] {lsssDeposit, lsssFRA, lsssFixFloat,
				lsssFloatFloat, lsssSyntheticFloatFloat};

		try {
			lcc = new org.drip.state.inference.LinearLatentStateCalibrator (scbc,
				org.drip.spline.stretch.BoundarySettings.NaturalStandard(),
					org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE, null, null);

			return org.drip.state.creator.ScenarioForwardCurveBuilder.ShapePreservingForwardCurve (lcc,
				aStretchSpec, forwardLabel, org.drip.param.valuation.ValuationParams.Spot
					(dtEffective.julian()), null, org.drip.param.creator.MarketParamsBuilder.Create (dc,
						fcReference, null, null, null, null, null, null), null, 0 == iNumDepositComp ?
							adblFRAQuote[0] : adblDepositQuote[0]);
		} catch (java.lang.Exception e) {
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
							org.drip.function.r1tor1.QuadraticRationalShapeControl (0.)), null));
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
							org.drip.function.r1tor1.QuadraticRationalShapeControl (0.)), null));
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
												org.drip.function.r1tor1.QuadraticRationalShapeControl (0.)),
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
												org.drip.function.r1tor1.QuadraticRationalShapeControl (0.)),
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

		/* return null == strBenchmarkTreasuryCode || strBenchmarkTreasuryCode.isEmpty() ? null :
			org.drip.state.creator.ScenarioGovvieCurveBuilder.CustomSplineCurve (strBenchmarkTreasuryCode,
				dtSpot.addBusDays (0, strCurrency), strBenchmarkTreasuryCode, strCurrency, aiDate, adblQuote,
					scbc); */

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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBump)) return null;

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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBump)) return null;

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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBump)) return null;

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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBump)) return null;

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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBump)) return null;

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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBump)) return null;

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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBump)) return null;

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
