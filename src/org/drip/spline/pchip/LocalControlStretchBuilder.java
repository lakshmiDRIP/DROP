
package org.drip.spline.pchip;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * LocalControlStretchBuilder exports Stretch creation/calibration methods to generate customized basis
 *  splines, with customized segment behavior using the segment control. It provides the following
 *  local-control functionality:
 *  - Create a Stretch off of Hermite Splines from the specified the Predictor Ordinates, the Response
 *  	Values, the Custom Slopes, and the Segment Builder Parameters.
 *  - Create Hermite/Bessel C1 Cubic Spline Stretch.
 *  - Create Hyman (1983) Monotone Preserving Stretch.
 *  - Create Hyman (1989) enhancement to the Hyman (1983) Monotone Preserving Stretch.
 *  - Create the Harmonic Monotone Preserving Stretch.
 *  - Create the Van Leer Limiter Stretch.
 *  - Create the Huynh Le Floch Limiter Stretch.
 *  - Generate the local control C1 Slope using the Akima Cubic Algorithm.
 *  - Generate the local control C1 Slope using the Hagan-West Monotone Convex Algorithm.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LocalControlStretchBuilder {

	/**
	 * Create a Stretch off of Hermite Splines from the specified the Predictor Ordinates, the Response
	 *  Values, the Custom Slopes, and the Segment Builder Parameters.
	 * 
	 * @param strName Stretch Name
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * @param adblCustomSlope Array of Custom Slopes
	 * @param aSCBC Array of Segment Builder Parameters
	 * @param sbfr Stretch Fitness Weighted Response
	 * @param iSetupMode Calibration Set up Mode NATURAL | FINANCIAL | FLOATING | NOTAKNOT
	 * 
	 * @return The Instance of the Hermite Spline Stretch
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CustomSlopeHermiteSpline (
		final java.lang.String strName,
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue,
		final double[] adblCustomSlope,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final int iSetupMode)
	{
		org.drip.spline.stretch.MultiSegmentSequence msr =
			org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateUncalibratedStretchEstimator (strName,
				adblPredictorOrdinate, aSCBC);

		if (null == msr || null == adblResponseValue || null == adblCustomSlope) return null;

		int iNumResponseValue = adblResponseValue.length;
		org.drip.spline.params.SegmentPredictorResponseDerivative[] aSPRDLeft = new
			org.drip.spline.params.SegmentPredictorResponseDerivative[iNumResponseValue - 1];
		org.drip.spline.params.SegmentPredictorResponseDerivative[] aSPRDRight = new
			org.drip.spline.params.SegmentPredictorResponseDerivative[iNumResponseValue - 1];

		if (1 >= iNumResponseValue || adblPredictorOrdinate.length != iNumResponseValue ||
			adblCustomSlope.length != iNumResponseValue)
			return null;

		for (int i = 0; i < iNumResponseValue; ++i) {
			org.drip.spline.params.SegmentPredictorResponseDerivative sprd = null;

			try {
				sprd = new org.drip.spline.params.SegmentPredictorResponseDerivative (adblResponseValue[i],
					new double[] {adblCustomSlope[i]});
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (0 == i)
				aSPRDLeft[i] = sprd;
			else if (iNumResponseValue - 1 == i)
				aSPRDRight[i - 1] = sprd;
			else {
				aSPRDLeft[i] = sprd;
				aSPRDRight[i - 1] = sprd;
			}
		}

		return msr.setupHermite (aSPRDLeft, aSPRDRight, null, sbfr, iSetupMode) ? msr : null;
	}

	/**
	 * Create a Stretch off of Hermite Splines from the specified the Predictor Ordinates, the Response
	 *  Values, the Custom Slopes, and the Segment Builder Parameters.
	 * 
	 * @param strName Stretch Name
	 * @param aiPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * @param adblCustomSlope Array of Custom Slopes
	 * @param aSCBC Array of Segment Builder Parameters
	 * @param sbfr Stretch Fitness Weighted Response
	 * @param iSetupMode Calibration Set up Mode NATURAL | FINANCIAL | FLOATING | NOTAKNOT
	 * 
	 * @return The Instance of the Hermite Spline Stretch
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CustomSlopeHermiteSpline (
		final java.lang.String strName,
		final int[] aiPredictorOrdinate,
		final double[] adblResponseValue,
		final double[] adblCustomSlope,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final int iSetupMode)
	{
		if (null == aiPredictorOrdinate) return null;

		int iNumPredictorOrdinate = aiPredictorOrdinate.length;
		double[] adblPredictorOrdinate = new double[iNumPredictorOrdinate];

		if (0 == iNumPredictorOrdinate) return null;

		for (int i = 0; i < iNumPredictorOrdinate; ++i)
			adblPredictorOrdinate[i] = aiPredictorOrdinate[i];

		return CustomSlopeHermiteSpline (strName, adblPredictorOrdinate, adblResponseValue, adblCustomSlope,
			aSCBC, sbfr, iSetupMode);
	}

	/**
	 * Create Hermite/Bessel C1 Cubic Spline Stretch
	 * 
	 * @param strName Stretch Name
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * @param aSCBC Array of Segment Builder Parameters
	 * @param sbfr Stretch Fitness Weighted Response
	 * @param iSetupMode Segment Setup Mode
	 * @param bEliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param bApplyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return Hermite/Bessel C1 Cubic Spline Stretch
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CreateBesselCubicSplineStretch (
		final java.lang.String strName,
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final int iSetupMode,
		final boolean bEliminateSpuriousExtrema,
		final boolean bApplyMonotoneFilter)
	{
		org.drip.spline.pchip.LocalMonotoneCkGenerator lmcg =
			org.drip.spline.pchip.LocalMonotoneCkGenerator.Create (adblPredictorOrdinate, adblResponseValue,
				org.drip.spline.pchip.LocalMonotoneCkGenerator.C1_BESSEL, bEliminateSpuriousExtrema,
					bApplyMonotoneFilter);

		return null == lmcg ? null : CustomSlopeHermiteSpline (strName, adblPredictorOrdinate,
			adblResponseValue, lmcg.C1(), aSCBC, sbfr, iSetupMode);
	}

	/**
	 * Create Hyman (1983) Monotone Preserving Stretch. The reference is:
	 * 
	 * 	Hyman (1983) Accurate Monotonicity Preserving Cubic Interpolation -
	 *  	SIAM J on Numerical Analysis 4 (4), 645-654.
	 * 
	 * @param strName Stretch Name
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * @param aSCBC Array of Segment Builder Parameters
	 * @param sbfr Stretch Fitness Weighted Response
	 * @param iSetupMode Segment Setup Mode
	 * @param bEliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param bApplyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return Hyman (1983) Monotone Preserving Stretch
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CreateHyman83MonotoneStretch (
		final java.lang.String strName,
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final int iSetupMode,
		final boolean bEliminateSpuriousExtrema,
		final boolean bApplyMonotoneFilter)
	{
		org.drip.spline.pchip.LocalMonotoneCkGenerator lmcg =
			org.drip.spline.pchip.LocalMonotoneCkGenerator.Create (adblPredictorOrdinate, adblResponseValue,
				org.drip.spline.pchip.LocalMonotoneCkGenerator.C1_HYMAN83, bEliminateSpuriousExtrema,
					bApplyMonotoneFilter);

		return null == lmcg ? null : CustomSlopeHermiteSpline (strName, adblPredictorOrdinate,
			adblResponseValue, lmcg.C1(), aSCBC, sbfr, iSetupMode);
	}

	/**
	 * Create Hyman (1989) enhancement to the Hyman (1983) Monotone Preserving Stretch. The reference is:
	 * 
	 * 	Doherty, Edelman, and Hyman (1989) Non-negative, monotonic, or convexity preserving cubic and quintic
	 *  	Hermite interpolation - Mathematics of Computation 52 (186), 471-494.
	 * 
	 * @param strName Stretch Name
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * @param aSCBC Array of Segment Builder Parameters
	 * @param sbfr Stretch Fitness Weighted Response
	 * @param iSetupMode Segment Setup Mode
	 * @param bEliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param bApplyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return Hyman (1989) Monotone Preserving Stretch
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CreateHyman89MonotoneStretch (
		final java.lang.String strName,
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final int iSetupMode,
		final boolean bEliminateSpuriousExtrema,
		final boolean bApplyMonotoneFilter)
	{
		org.drip.spline.pchip.LocalMonotoneCkGenerator lmcg =
			org.drip.spline.pchip.LocalMonotoneCkGenerator.Create (adblPredictorOrdinate, adblResponseValue,
				org.drip.spline.pchip.LocalMonotoneCkGenerator.C1_HYMAN89, bEliminateSpuriousExtrema,
					bApplyMonotoneFilter);

		return null == lmcg ? null : CustomSlopeHermiteSpline (strName, adblPredictorOrdinate,
			adblResponseValue, lmcg.C1(), aSCBC, sbfr, iSetupMode);
	}

	/**
	 * Create the Harmonic Monotone Preserving Stretch. The reference is:
	 * 
	 * 	Fritcsh and Butland (1984) A Method for constructing local monotonic piece-wise cubic interpolants -
	 *  	SIAM J on Scientific and Statistical Computing 5, 300-304.
	 * 
	 * @param strName Stretch Name
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * @param aSCBC Array of Segment Builder Parameters
	 * @param sbfr Stretch Fitness Weighted Response
	 * @param iSetupMode Segment Setup Mode
	 * @param bEliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param bApplyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return Harmonic Monotone Preserving Stretch
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CreateHarmonicMonotoneStretch (
		final java.lang.String strName,
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final int iSetupMode,
		final boolean bEliminateSpuriousExtrema,
		final boolean bApplyMonotoneFilter)
	{
		org.drip.spline.pchip.LocalMonotoneCkGenerator lmcg =
			org.drip.spline.pchip.LocalMonotoneCkGenerator.Create (adblPredictorOrdinate, adblResponseValue,
				org.drip.spline.pchip.LocalMonotoneCkGenerator.C1_HARMONIC, bEliminateSpuriousExtrema,
					bApplyMonotoneFilter);

		return null == lmcg ? null : CustomSlopeHermiteSpline (strName, adblPredictorOrdinate,
			adblResponseValue, lmcg.C1(), aSCBC, sbfr, iSetupMode);
	}

	/**
	 * Create the Van Leer Limiter Stretch. The reference is:
	 * 
	 * 	Van Leer (1974) Towards the Ultimate Conservative Difference Scheme. II - Monotonicity and
	 * 		Conservation combined in a Second-Order Scheme, Journal of Computational Physics 14 (4), 361-370.
	 * 
	 * @param strName Stretch Name
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * @param aSCBC Array of Segment Builder Parameters
	 * @param sbfr Stretch Fitness Weighted Response
	 * @param iSetupMode Segment Setup Mode
	 * @param bEliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param bApplyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return The Van Leer Limiter Stretch
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CreateVanLeerLimiterStretch (
		final java.lang.String strName,
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final int iSetupMode,
		final boolean bEliminateSpuriousExtrema,
		final boolean bApplyMonotoneFilter)
	{
		org.drip.spline.pchip.LocalMonotoneCkGenerator lmcg =
			org.drip.spline.pchip.LocalMonotoneCkGenerator.Create (adblPredictorOrdinate, adblResponseValue,
				org.drip.spline.pchip.LocalMonotoneCkGenerator.C1_VAN_LEER, bEliminateSpuriousExtrema,
					bApplyMonotoneFilter);

		return null == lmcg ? null : CustomSlopeHermiteSpline (strName, adblPredictorOrdinate,
			adblResponseValue, lmcg.C1(), aSCBC, sbfr, iSetupMode);
	}

	/**
	 * Create the Kruger Stretch. The reference is:
	 * 
	 * 	Kruger (2002) Constrained Cubic Spline Interpolations for Chemical Engineering Application,
	 *  	http://www.korf.co.uk/spline.pdf
	 * 
	 * @param strName Stretch Name
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * @param aSCBC Array of Segment Builder Parameters
	 * @param sbfr Stretch Fitness Weighted Response
	 * @param iSetupMode Segment Setup Mode
	 * @param bEliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param bApplyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return The Kruger Stretch
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CreateKrugerStretch (
		final java.lang.String strName,
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final int iSetupMode,
		final boolean bEliminateSpuriousExtrema,
		final boolean bApplyMonotoneFilter)
	{
		org.drip.spline.pchip.LocalMonotoneCkGenerator lmcg =
			org.drip.spline.pchip.LocalMonotoneCkGenerator.Create (adblPredictorOrdinate, adblResponseValue,
				org.drip.spline.pchip.LocalMonotoneCkGenerator.C1_KRUGER, bEliminateSpuriousExtrema,
					bApplyMonotoneFilter);

		return null == lmcg ? null : CustomSlopeHermiteSpline (strName, adblPredictorOrdinate,
			adblResponseValue, lmcg.C1(), aSCBC, sbfr, iSetupMode);
	}

	/**
	 * Create the Huynh Le Floch Limiter Stretch. The reference is:
	 * 
	 * 	Huynh (1993) Accurate Monotone Cubic Interpolation, SIAM J on Numerical Analysis 30 (1), 57-100.
	 * 
	 * @param strName Stretch Name
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * @param aSCBC Array of Segment Builder Parameters
	 * @param sbfr Stretch Fitness Weighted Response
	 * @param iSetupMode Segment Setup Mode
	 * @param bEliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param bApplyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return The Huynh Le Floch Limiter Stretch
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CreateHuynhLeFlochLimiterStretch (
		final java.lang.String strName,
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final int iSetupMode,
		final boolean bEliminateSpuriousExtrema,
		final boolean bApplyMonotoneFilter)
	{
		org.drip.spline.pchip.LocalMonotoneCkGenerator lmcg =
			org.drip.spline.pchip.LocalMonotoneCkGenerator.Create (adblPredictorOrdinate, adblResponseValue,
				org.drip.spline.pchip.LocalMonotoneCkGenerator.C1_KRUGER, bEliminateSpuriousExtrema,
					bApplyMonotoneFilter);

		return null == lmcg ? null : CustomSlopeHermiteSpline (strName, adblPredictorOrdinate,
			adblResponseValue, lmcg.C1(), aSCBC, sbfr, iSetupMode);
	}

	/**
	 * Generate the local control C1 Slope using the Akima Cubic Algorithm. The reference is:
	 * 
	 * 	Akima (1970): A New Method of Interpolation and Smooth Curve Fitting based on Local Procedures,
	 * 		Journal of the Association for the Computing Machinery 17 (4), 589-602.
	 * 
	 * @param strName Stretch Name
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * @param aSCBC Array of Segment Builder Parameters
	 * @param sbfr Stretch Fitness Weighted Response
	 * @param iSetupMode Segment Setup Mode
	 * @param bEliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param bApplyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return The Akima Local Control Stretch Instance
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CreateAkimaStretch (
		final java.lang.String strName,
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final int iSetupMode,
		final boolean bEliminateSpuriousExtrema,
		final boolean bApplyMonotoneFilter)
	{
		org.drip.spline.pchip.LocalMonotoneCkGenerator lmcg =
			org.drip.spline.pchip.LocalMonotoneCkGenerator.Create (adblPredictorOrdinate, adblResponseValue,
				org.drip.spline.pchip.LocalMonotoneCkGenerator.C1_AKIMA, bEliminateSpuriousExtrema,
					bApplyMonotoneFilter);

		return null == lmcg ? null : CustomSlopeHermiteSpline (strName, adblPredictorOrdinate,
			adblResponseValue, lmcg.C1(), aSCBC, sbfr, iSetupMode);
	}

	/**
	 * Generate the local control C1 Slope using the Hagan-West Monotone Convex Algorithm. The references
	 *  are:
	 * 
	 * 	Hagan, P., and G. West (2006): Interpolation Methods for Curve Construction, Applied Mathematical
	 * 	 Finance 13 (2): 89-129.
	 * 
	 * 	Hagan, P., and G. West (2008): Methods for Curve a Yield Curve, Wilmott Magazine: 70-81.
	 * 
	 * @param strName Stretch Name
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblObservation Array of Observations
	 * @param aSCBC Array of Segment Builder Parameters
	 * @param sbfr Stretch Fitness Weighted Response
	 * @param iSetupMode Segment Setup Mode
	 * @param bLinearNodeInference Apply Linear Node Inference from Observations
	 * @param bEliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param bApplyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return The Monotone-Convex Local Control Stretch Instance
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CreateMonotoneConvexStretch (
		final java.lang.String strName,
		final double[] adblPredictorOrdinate,
		final double[] adblObservation,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final int iSetupMode,
		final boolean bLinearNodeInference,
		final boolean bEliminateSpuriousExtrema,
		final boolean bApplyMonotoneFilter)
	{
		org.drip.spline.pchip.MonotoneConvexHaganWest mchw =
			org.drip.spline.pchip.MonotoneConvexHaganWest.Create (adblPredictorOrdinate, adblObservation,
				bLinearNodeInference);

		if (null == mchw) return null;

		org.drip.spline.pchip.LocalMonotoneCkGenerator lmcg =
			org.drip.spline.pchip.LocalMonotoneCkGenerator.Create (mchw.predictorOrdinates(),
				mchw.responseValues(), org.drip.spline.pchip.LocalMonotoneCkGenerator.C1_MONOTONE_CONVEX,
					bEliminateSpuriousExtrema, bApplyMonotoneFilter);

		return null == lmcg ? null : CustomSlopeHermiteSpline (strName, mchw.predictorOrdinates(),
			mchw.responseValues(), lmcg.C1(), aSCBC, sbfr, iSetupMode);
	}
}
