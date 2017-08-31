
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
 * LocalMonotoneCkGenerator generates customized Local Stretch by trading off Ck for local control. This
 * 	class implements the following variants: Akima, Bessel, Harmonic, Hyman83, Hyman89, Kruger, Monotone
 *  Convex, as well as the Van Leer and the Huynh/LeFloch limiters.
 *  
 *  It also provides the following custom control on the resulting C1:
 *  - Eliminate the Spurious Extrema in the Input C1 Entry.
 *  - Apply the Monotone Filter in the Input C1 Entry.
 *  - Generate a Vanilla C1 Array from the specified Array of Predictor Ordinates and the Response Values.
 *  - Verify if the given Quintic Polynomial is Monotone using the Hyman89 Algorithm, and generate it if
 *  	necessary.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LocalMonotoneCkGenerator {

	/**
	 * C1 Type: Vanilla
	 */

	public static final java.lang.String C1_VANILLA = "C1_VANILLA";

	/**
	 * C1 Type: Akima
	 */

	public static final java.lang.String C1_AKIMA = "C1_AKIMA";

	/**
	 * C1 Type: Bessel
	 */

	public static final java.lang.String C1_BESSEL = "C1_BESSEL";

	/**
	 * C1 Type: Harmonic
	 */

	public static final java.lang.String C1_HARMONIC = "C1_HARMONIC";

	/**
	 * C1 Type: Huynh - Le Floch Limiter
	 */

	public static final java.lang.String C1_HUYNH_LE_FLOCH = "C1_HUYNH_LE_FLOCH";

	/**
	 * C1 Type: Hyman83
	 */

	public static final java.lang.String C1_HYMAN83 = "C1_HYMAN83";

	/**
	 * C1 Type: Hyman89
	 */

	public static final java.lang.String C1_HYMAN89 = "C1_HYMAN89";

	/**
	 * C1 Type: Kruger
	 */

	public static final java.lang.String C1_KRUGER = "C1_KRUGER";

	/**
	 * C1 Type: Monotone Convex
	 */

	public static final java.lang.String C1_MONOTONE_CONVEX = "C1_MONOTONE_CONVEX";

	/**
	 * C1 Type: Van Leer Limiter
	 */

	public static final java.lang.String C1_VAN_LEER = "C1_VAN_LEER";

	private double[] _adblC1 = null;
	private double[] _adblResponseValue = null;
	private double[] _adblPredictorOrdinate = null;

	/**
	 * Eliminate the Spurious Extrema in the Input C1 Entry
	 * 
	 * @param adblC1 The C1 Array in which the Spurious Extrema is to be eliminated
	 * @param adblLinearC1 Array of the Linear C1 Entries
	 * 
	 * @return The C1 Array with the Spurious Extrema eliminated
	 */

	public static final double[] EliminateSpuriousExtrema (
		final double[] adblC1,
		final double[] adblLinearC1)
	{
		if (null == adblC1 || null == adblLinearC1) return null;

		int iNumEntries = adblC1.length;
		double[] adblUpdatedC1 = new double[iNumEntries];
		adblUpdatedC1[0] = adblC1[0];
		adblUpdatedC1[iNumEntries - 1] = adblC1[iNumEntries - 1];

		if (1 >= iNumEntries || iNumEntries != adblLinearC1.length + 1) return null;

		for (int i = 1; i < iNumEntries - 1; ++i)
			adblUpdatedC1[i] = 0. < adblLinearC1[i] ? java.lang.Math.min (java.lang.Math.max (0., adblC1[i]),
				java.lang.Math.min (adblLinearC1[i], adblLinearC1[i - 1])) : java.lang.Math.max
					(java.lang.Math.min (0., adblC1[i]), java.lang.Math.max (adblLinearC1[i],
						adblLinearC1[i - 1]));

		return adblUpdatedC1;
	}

	/**
	 * Apply the Monotone Filter in the Input C1 Entry
	 * 
	 * @param adblC1 The C1 Array in which the Monotone Filter is to be applied
	 * @param adblLinearC1 Array of the Linear C1 Entries
	 * 
	 * @return The C1 Array with the Monotone Filter applied
	 */

	public static final double[] ApplyMonotoneFilter (
		final double[] adblC1,
		final double[] adblLinearC1)
	{
		if (null == adblC1 || null == adblLinearC1) return null;

		int iNumEntries = adblC1.length;
		double[] adblUpdatedC1 = new double[iNumEntries];
		adblUpdatedC1[0] = adblC1[0];

		if (1 >= iNumEntries || iNumEntries != adblLinearC1.length + 1) return null;

		for (int i = 0; i < iNumEntries; ++i) {
			if (0 == i) {
				if (adblC1[0] * adblLinearC1[0] > 0. && adblLinearC1[0] * adblLinearC1[1] > 0. &&
					java.lang.Math.abs (adblC1[0]) < 3. * java.lang.Math.abs (adblLinearC1[0]))
					adblUpdatedC1[0] = 3. * adblLinearC1[0];
				else if (adblC1[0] * adblLinearC1[0] <= 0.)
					adblUpdatedC1[0] = 0.;
			} else if (iNumEntries == i) {
				if (adblC1[i] * adblLinearC1[i - 1] > 0. && adblLinearC1[i - 1] * adblLinearC1[i - 2] > 0. &&
					java.lang.Math.abs (adblC1[i]) < 3. * java.lang.Math.abs (adblLinearC1[i - 1]))
					adblUpdatedC1[i] = 3. * adblLinearC1[i - 1];
				else if (adblC1[i] * adblLinearC1[i - 1] <= 0.)
					adblUpdatedC1[i] = 0.;
			} else
				adblUpdatedC1[i] = adblC1[i];
		}

		return adblUpdatedC1;
	}

	/**
	 * Generate a Vanilla C1 Array from the specified Array of Predictor Ordinates and the Response Values
	 * 
	 * @param adblPredictorOrdinate The Predictor Ordinate Array
	 * @param adblResponseValue The Response Value Array
	 * 
	 * @return The C1 Array
	 */

	public static final double[] LinearC1 (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue)
	{
		int iNumSegment = adblResponseValue.length - 1;
		double[] adblLinearC1 = new double[iNumSegment];

		for (int i = 0; i < iNumSegment; ++i)
			adblLinearC1[i] = (adblResponseValue[i + 1] - adblResponseValue[i]) /
				(adblPredictorOrdinate[i + 1] - adblPredictorOrdinate[i]);

		return adblLinearC1;
	}

	/**
	 * Generate a Bessel C1 Array from the specified Array of Predictor Ordinates and the Response Values
	 * 
	 * @param adblPredictorOrdinate The Predictor Ordinate Array
	 * @param adblResponseValue The Response Value Array
	 * 
	 * @return The C1 Array
	 */

	public static final double[] BesselC1 (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue)
	{
		int iNumResponse = adblResponseValue.length;
		double[] adblBesselC1 = new double[iNumResponse];

		for (int i = 0; i < iNumResponse; ++i) {
			if (0 == i) {
				adblBesselC1[i] = (adblPredictorOrdinate[2] + adblPredictorOrdinate[1] - 2. *
					adblPredictorOrdinate[0]) * (adblResponseValue[1] - adblResponseValue[0]) /
						(adblPredictorOrdinate[1] - adblPredictorOrdinate[0]);
				adblBesselC1[i] -= (adblPredictorOrdinate[1] - adblPredictorOrdinate[0]) *
					(adblResponseValue[2] - adblResponseValue[1]) / (adblPredictorOrdinate[2] -
						adblPredictorOrdinate[1]);
				adblBesselC1[i] /= (adblPredictorOrdinate[2] - adblPredictorOrdinate[0]);
			} else if (iNumResponse - 1 == i) {
				adblBesselC1[i] = (adblPredictorOrdinate[iNumResponse - 1] -
					adblPredictorOrdinate[iNumResponse - 2]) * (adblResponseValue[iNumResponse - 2] -
						adblResponseValue[iNumResponse - 3]) / (adblPredictorOrdinate[iNumResponse - 2] -
							adblPredictorOrdinate[iNumResponse - 3]);
				adblBesselC1[i] -= (2. * adblPredictorOrdinate[iNumResponse - 1] -
					adblPredictorOrdinate[iNumResponse - 2] - adblPredictorOrdinate[iNumResponse - 3]) *
						(adblResponseValue[iNumResponse - 1] - adblResponseValue[iNumResponse - 2]) /
							(adblPredictorOrdinate[iNumResponse - 1] -
								adblPredictorOrdinate[iNumResponse - 2]);
				adblBesselC1[i] /= (adblPredictorOrdinate[iNumResponse - 1] -
					adblPredictorOrdinate[iNumResponse - 3]);
			} else {
				adblBesselC1[i] = (adblPredictorOrdinate[i + 1] - adblPredictorOrdinate[i]) *
					(adblResponseValue[i] - adblResponseValue[i - 1]) / (adblPredictorOrdinate[i] -
						adblPredictorOrdinate[i - 1]);
				adblBesselC1[i] += (adblPredictorOrdinate[i] - adblPredictorOrdinate[i - 1]) *
					(adblResponseValue[i + 1] - adblResponseValue[i]) / (adblPredictorOrdinate[i + 1] -
						adblPredictorOrdinate[i]);
				adblBesselC1[i] /= (adblPredictorOrdinate[iNumResponse - 1] -
					adblPredictorOrdinate[iNumResponse - 3]);
			}
		}

		return adblBesselC1;
	}

	/**
	 * Generate a Hyman83 C1 Array from the specified Array of Predictor Ordinates and the Response Values
	 * 
	 * 	Hyman (1983) Accurate Monotonicity Preserving Cubic Interpolation -
	 *  	SIAM J on Numerical Analysis 4 (4), 645-654.
	 * 
	 * @param adblPredictorOrdinate The Predictor Ordinate Array
	 * @param adblResponseValue The Response Value Array
	 * 
	 * @return The C1 Array
	 */

	public static final double[] Hyman83C1 (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue)
	{
		int iNumResponse = adblResponseValue.length;
		double dblLinearSlopePrev = java.lang.Double.NaN;
		double[] adblHyman83C1 = new double[iNumResponse];

		for (int i = 0; i < iNumResponse; ++i) {
			adblHyman83C1[i] = 0.;
			double dblLinearSlope = iNumResponse - 1 != i ? (adblResponseValue[i + 1] - adblResponseValue[i])
				/ (adblPredictorOrdinate[i + 1] - adblPredictorOrdinate[i]) : java.lang.Double.NaN;

			if (0 != i && iNumResponse - 1 != i) {
				double dblMonotoneIndicator = dblLinearSlopePrev * dblLinearSlope;

				if (0. <= dblMonotoneIndicator)
					adblHyman83C1[i] = 3. * dblMonotoneIndicator / (java.lang.Math.max (dblLinearSlope,
						dblLinearSlopePrev) + 2. * java.lang.Math.min (dblLinearSlope, dblLinearSlopePrev));
			}

			dblLinearSlopePrev = dblLinearSlope;
		}

		return adblHyman83C1;
	}

	/**
	 * Generate a Hyman89 C1 Array from the specified Array of Predictor Ordinates and the Response Values
	 * 
	 * 	Doherty, Edelman, and Hyman (1989) Non-negative, monotonic, or convexity preserving cubic and quintic
	 *  	Hermite interpolation - Mathematics of Computation 52 (186), 471-494.
	 * 
	 * @param adblPredictorOrdinate The Predictor Ordinate Array
	 * @param adblResponseValue The Response Value Array
	 * 
	 * @return The C1 Array
	 */

	public static final double[] Hyman89C1 (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue)
	{
		int iNumResponse = adblResponseValue.length;
		double[] adblHyman89C1 = new double[iNumResponse];

		double[] adblNodeC1 = LinearC1 (adblPredictorOrdinate, adblResponseValue);

		double[] adblBesselC1 = BesselC1 (adblPredictorOrdinate, adblResponseValue);

		for (int i = 0; i < iNumResponse; ++i) {
			if (i < 2 || i >= iNumResponse - 2)
				adblHyman89C1[i] = adblBesselC1[i];
			else {
				double dMuMinus = (adblNodeC1[i - 1] * (2. * (adblPredictorOrdinate[i] -
					adblPredictorOrdinate[i - 1]) + adblPredictorOrdinate[i - 1] -
						adblPredictorOrdinate[i - 2]) - adblNodeC1[i - 2] * (adblPredictorOrdinate[i] -
							adblPredictorOrdinate[i - 1])) / (adblPredictorOrdinate[i] -
								adblPredictorOrdinate[i - 2]);
				double dMu0 = (adblNodeC1[i - 1] * (adblPredictorOrdinate[i + 1] - adblPredictorOrdinate[i])
					+ adblNodeC1[i] * (adblPredictorOrdinate[i] - adblPredictorOrdinate[i - 1])) /
						(adblPredictorOrdinate[i + 1] - adblPredictorOrdinate[i - 1]);
				double dMuPlus = (adblNodeC1[i] * (2. * (adblPredictorOrdinate[i + 1] -
					adblPredictorOrdinate[i]) + adblPredictorOrdinate[i + 2] - adblPredictorOrdinate[i + 1])
						- adblNodeC1[i + 1] * (adblPredictorOrdinate[i + 1] - adblPredictorOrdinate[i])) /
							(adblPredictorOrdinate[i + 2] - adblPredictorOrdinate[i]);

				try {
					double dblM = 3 * org.drip.quant.common.NumberUtil.Minimum (new double[]
						{java.lang.Math.abs (adblNodeC1[i - 1]), java.lang.Math.abs (adblNodeC1[i]),
							java.lang.Math.abs (dMu0), java.lang.Math.abs (dMuPlus)});

					if (!org.drip.quant.common.NumberUtil.SameSign (new double[] {dMu0, dMuMinus,
							adblNodeC1[i - 1] - adblNodeC1[i - 2], adblNodeC1[i] - adblNodeC1[i - 1]}))
						dblM = java.lang.Math.max (dblM, 1.5 * java.lang.Math.min (java.lang.Math.abs (dMu0),
							java.lang.Math.abs (dMuMinus)));
					else if (!org.drip.quant.common.NumberUtil.SameSign (new double[] {-dMu0, -dMuPlus,
							adblNodeC1[i] - adblNodeC1[i - 1], adblNodeC1[i + 1] - adblNodeC1[i]}))
						dblM = java.lang.Math.max (dblM, 1.5 * java.lang.Math.min (java.lang.Math.abs (dMu0),
							java.lang.Math.abs (dMuPlus)));

					adblHyman89C1[i] = 0.;

					if (adblBesselC1[i] * dMu0 > 0.)
						adblHyman89C1[i] = adblBesselC1[i] / java.lang.Math.abs (adblBesselC1[i]) *
							java.lang.Math.min (java.lang.Math.abs (adblBesselC1[i]), dblM);
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		return adblHyman89C1;
	}

	/**
	 * Generate a Harmonic C1 Array from the specified Array of Predictor Ordinates and the Response Values
	 * 
	 * 	Fritcsh and Butland (1984) A Method for constructing local monotonic piece-wise cubic interpolants -
	 *  	SIAM J on Scientific and Statistical Computing 5, 300-304.
	 * 
	 * @param adblPredictorOrdinate The Predictor Ordinate Array
	 * @param adblResponseValue The Response Value Array
	 * 
	 * @return The C1 Array
	 */

	public static final double[] HarmonicC1 (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue)
	{
		int iNumResponse = adblResponseValue.length;
		double[] adblHarmonicC1 = new double[iNumResponse];

		double[] adblLinearC1 = LinearC1 (adblPredictorOrdinate, adblResponseValue);

		for (int i = 0; i < iNumResponse; ++i) {
			if (0 == i) {
				adblHarmonicC1[i] = (adblPredictorOrdinate[2] + adblPredictorOrdinate[1] - 2. *
					adblPredictorOrdinate[0]) * adblLinearC1[0] / (adblPredictorOrdinate[2] -
						adblPredictorOrdinate[0]);
				adblHarmonicC1[i] -= (adblPredictorOrdinate[1] - adblPredictorOrdinate[0]) * adblLinearC1[1]
					/ (adblPredictorOrdinate[2] - adblPredictorOrdinate[0]);
			} else if (iNumResponse - 1 == i) {
				adblHarmonicC1[i] = -(adblPredictorOrdinate[i] - adblPredictorOrdinate[i - 1]) *
					adblLinearC1[i - 2] / (adblPredictorOrdinate[i] - adblPredictorOrdinate[i - 2]);
				adblHarmonicC1[i] += (2. * adblPredictorOrdinate[i] - adblPredictorOrdinate[i - 1] -
					adblPredictorOrdinate[i - 2]) * adblLinearC1[i - 1] / (adblPredictorOrdinate[i] -
						adblPredictorOrdinate[i - 2]);
			} else {
				if (adblLinearC1[i - 1] * adblLinearC1[i] <= 0.)
					adblHarmonicC1[i] = 0.;
				else {
					adblHarmonicC1[i] = (adblPredictorOrdinate[i] - adblPredictorOrdinate[i - 1] + 2. *
						(adblPredictorOrdinate[i + 1] - adblPredictorOrdinate[i])) / (3. *
							(adblPredictorOrdinate[i + 1] - adblPredictorOrdinate[i])) / adblLinearC1[i - 1];
					adblHarmonicC1[i] += (adblPredictorOrdinate[i + 1] - adblPredictorOrdinate[i] + 2. *
						(adblPredictorOrdinate[i] - adblPredictorOrdinate[i - 1])) / (3. *
							(adblPredictorOrdinate[i + 1] - adblPredictorOrdinate[i])) / adblLinearC1[i];
					adblHarmonicC1[i] = 1. / adblHarmonicC1[i];
				}
			}
		}

		return adblHarmonicC1;
	}

	/**
	 * Generate a Van Leer Limiter C1 Array from the specified Array of Predictor Ordinates and the Response
	 *  Values.
	 * 
	 * 	Van Leer (1974) Towards the Ultimate Conservative Difference Scheme. II - Monotonicity and
	 * 		Conservation combined in a Second-Order Scheme, Journal of Computational Physics 14 (4), 361-370.
	 * 
	 * @param adblPredictorOrdinate The Predictor Ordinate Array
	 * @param adblResponseValue The Response Value Array
	 * 
	 * @return The C1 Array
	 */

	public static final double[] VanLeerLimiterC1 (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue)
	{
		int iNumResponse = adblResponseValue.length;
		double[] dblVanLeerLimiterC1 = new double[iNumResponse];

		double[] adblNodeC1 = LinearC1 (adblPredictorOrdinate, adblResponseValue);

		for (int i = 0; i < iNumResponse; ++i) {
			if (0 == i) {
				dblVanLeerLimiterC1[i] = (adblPredictorOrdinate[2] + adblPredictorOrdinate[1] - 2. *
					adblPredictorOrdinate[0]) * adblNodeC1[0] / (adblPredictorOrdinate[2] -
						adblPredictorOrdinate[0]);
				dblVanLeerLimiterC1[i] -= (adblPredictorOrdinate[1] - adblPredictorOrdinate[0]) *
					adblNodeC1[1] / (adblPredictorOrdinate[2] - adblPredictorOrdinate[0]);
			} else if (iNumResponse - 1 == i) {
				dblVanLeerLimiterC1[i] = -(adblPredictorOrdinate[i] - adblPredictorOrdinate[i - 1]) *
					adblNodeC1[i - 2] / (adblPredictorOrdinate[i] - adblPredictorOrdinate[i - 2]);
				dblVanLeerLimiterC1[i] += (2. * adblPredictorOrdinate[i] - adblPredictorOrdinate[i - 1] -
					adblPredictorOrdinate[i - 2]) * adblNodeC1[i - 1] / (adblPredictorOrdinate[i] -
						adblPredictorOrdinate[i - 2]);
			} else {
				if (0. != adblNodeC1[i - 1]) {
					double dblR = adblNodeC1[i] / adblNodeC1[i - 1];

					double dblRAbsolute = java.lang.Math.abs (dblR);

					dblVanLeerLimiterC1[i] = adblNodeC1[i] * (dblR + dblRAbsolute) / (1. + dblRAbsolute);
				} else if (0. >= adblNodeC1[i])
					dblVanLeerLimiterC1[i] = 0.;
				else if (0. < adblNodeC1[i])
					dblVanLeerLimiterC1[i] = 2. * adblNodeC1[i];
			}
		}

		return dblVanLeerLimiterC1;
	}

	/**
	 * Generate a Huynh Le Floch Limiter C1 Array from the specified Array of Predictor Ordinates and the
	 *  Response Values.
	 * 
	 * 	Huynh (1993) Accurate Monotone Cubic Interpolation, SIAM J on Numerical Analysis 30 (1), 57-100.
	 * 
	 * @param adblPredictorOrdinate The Predictor Ordinate Array
	 * @param adblResponseValue The Response Value Array
	 * 
	 * @return The C1 Array
	 */

	public static final double[] HuynhLeFlochLimiterC1 (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue)
	{
		int iNumResponse = adblResponseValue.length;
		double[] adblHuynhLeFlochLimiterC1 = new double[iNumResponse];

		double[] adblNodeC1 = LinearC1 (adblPredictorOrdinate, adblResponseValue);

		for (int i = 0; i < iNumResponse; ++i) {
			if (0 == i) {
				adblHuynhLeFlochLimiterC1[i] = (adblPredictorOrdinate[2] + adblPredictorOrdinate[1] - 2. *
					adblPredictorOrdinate[0]) * adblNodeC1[0] / (adblPredictorOrdinate[2] -
						adblPredictorOrdinate[0]);
				adblHuynhLeFlochLimiterC1[i] -= (adblPredictorOrdinate[1] - adblPredictorOrdinate[0]) *
					adblNodeC1[1] / (adblPredictorOrdinate[2] - adblPredictorOrdinate[0]);
			} else if (iNumResponse - 1 == i) {
				adblHuynhLeFlochLimiterC1[i] = -(adblPredictorOrdinate[i] - adblPredictorOrdinate[i - 1]) *
					adblNodeC1[i - 2] / (adblPredictorOrdinate[i] - adblPredictorOrdinate[i - 2]);
				adblHuynhLeFlochLimiterC1[i] += (2. * adblPredictorOrdinate[i] - adblPredictorOrdinate[i - 1]
					- adblPredictorOrdinate[i - 2]) * adblNodeC1[i - 1] / (adblPredictorOrdinate[i] -
						adblPredictorOrdinate[i - 2]);
			} else {
				double dblMonotoneIndicator = adblNodeC1[i] * adblNodeC1[i - 1];

				if (0. < dblMonotoneIndicator)
					adblHuynhLeFlochLimiterC1[i] = 3. * dblMonotoneIndicator * (adblNodeC1[i] +
						adblNodeC1[i - 1]) / (adblNodeC1[i] * adblNodeC1[i] + adblNodeC1[i - 1] *
							adblNodeC1[i - 1] * 4. * dblMonotoneIndicator);
				else
					adblHuynhLeFlochLimiterC1[i] = 0.;
			}
		}

		return adblHuynhLeFlochLimiterC1;
	}

	/**
	 * Generate a Kruger C1 Array from the specified Array of Predictor Ordinates and the Response Values.
	 * 
	 * 	Kruger (2002) Constrained Cubic Spline Interpolations for Chemical Engineering Application,
	 *  	http://www.korf.co.uk/spline.pdf
	 * 
	 * @param adblPredictorOrdinate The Predictor Ordinate Array
	 * @param adblResponseValue The Response Value Array
	 * 
	 * @return The C1 Array
	 */

	public static final double[] KrugerC1 (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue)
	{
		int iNumResponse = adblResponseValue.length;
		double[] adblKrugerSlope = new double[iNumResponse];

		double[] adblSlopeC1 = LinearC1 (adblPredictorOrdinate, adblResponseValue);

		if (null == adblSlopeC1 || adblSlopeC1.length != iNumResponse - 1) return null;

		for (int i = 0; i < iNumResponse; ++i) {
			if (0 != i && iNumResponse - 1 != i) {
				if (adblSlopeC1[i - 1] * adblSlopeC1[i] <= 0.)
					adblKrugerSlope[i] = 0.;
				else
					adblKrugerSlope[i] = 2. / ((1. / adblSlopeC1[i - 1]) + (1. / adblSlopeC1[i]));
			}
		}

		adblKrugerSlope[0] = 3.5 * adblSlopeC1[0] - 0.5 * adblKrugerSlope[1];
		adblKrugerSlope[iNumResponse - 1] = 3.5 * adblSlopeC1[iNumResponse - 2] - 0.5 *
			adblKrugerSlope[iNumResponse - 2];
		return adblKrugerSlope;
	}

	/**
	 * Generate a Akima C1 Array from the specified Array of Predictor Ordinates and the Response Values.
	 * 
	 * 	Akima (1970): A New Method of Interpolation and Smooth Curve Fitting based on Local Procedures,
	 * 		Journal of the Association for the Computing Machinery 17 (4), 589-602.
	 * 
	 * @param adblPredictorOrdinate The Predictor Ordinate Array
	 * @param adblResponseValue The Response Value Array
	 * 
	 * @return The C1 Array
	 */

	public static final double[] AkimaC1 (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue)
	{
		org.drip.spline.pchip.AkimaLocalC1Generator alcr =
			org.drip.spline.pchip.AkimaLocalC1Generator.Create (adblPredictorOrdinate, adblResponseValue);

		return null == alcr ? null : alcr.C1();
	}

	/**
	 * Verify if the given Quintic Polynomial is Monotone using the Hyman89 Algorithm
	 * 
	 * 	Doherty, Edelman, and Hyman (1989) Non-negative, monotonic, or convexity preserving cubic and quintic
	 *  	Hermite interpolation - Mathematics of Computation 52 (186), 471-494.
	 * 
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * @param adblFirstDerivative Array of First Derivatives
	 * @param adblSecondDerivative Array of Second Derivatives
	 * 
	 * @return TRUE - The given Quintic Polynomial is Monotone
	 * 
	 * @throws java.lang.Exception Thrown if the Monotonicity cannot be determined
	 */

	public static final boolean VerifyHyman89QuinticMonotonicity (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue,
		final double[] adblFirstDerivative,
		final double[] adblSecondDerivative)
		throws java.lang.Exception
	{
		if (null == adblPredictorOrdinate || null == adblResponseValue || null == adblFirstDerivative || null
			== adblSecondDerivative)
			throw new java.lang.Exception
				("LocalMonotoneCkGenerator::VerifyHyman89QuinticMonotonicity => Invalid Inputs");

		int iNumPredictor = adblPredictorOrdinate.length;

		if (1 >= iNumPredictor || iNumPredictor != adblResponseValue.length || iNumPredictor !=
			adblResponseValue.length || iNumPredictor != adblResponseValue.length)
			throw new java.lang.Exception
				("LocalMonotoneCkGenerator::VerifyHyman89QuinticMonotonicity => Invalid Inputs");

		for (int i = 1; i < iNumPredictor - 1; ++i) {
			double dblAbsoluteResponseValue = java.lang.Math.abs (adblResponseValue[i]);

			double dblResponseValueSign = adblResponseValue[i] > 0. ? 1. : -1.;
			double dblHMinus = (adblPredictorOrdinate[i] - adblPredictorOrdinate[i - 1]);
			double dblHPlus = (adblPredictorOrdinate[i + 1] - adblPredictorOrdinate[i]);

			if (-5. * dblAbsoluteResponseValue / dblHPlus > dblResponseValueSign * adblFirstDerivative[i] ||
				5. * dblAbsoluteResponseValue / dblHMinus < dblResponseValueSign * adblFirstDerivative[i])
				return false;

			if (dblResponseValueSign * adblSecondDerivative[i] < dblResponseValueSign * java.lang.Math.max
				(8. * adblFirstDerivative[i] / dblHMinus - 20. * adblResponseValue[i] / dblHMinus /
					dblHMinus, -8. * adblFirstDerivative[i] / dblHPlus - 20. * adblResponseValue[i] /
						dblHPlus / dblHPlus))
				return false;
		}

		return true;
	}

	/**
	 * Generate C1 Slope Quintic Polynomial is Monotone using the Hyman89 Algorithm
	 * 
	 * 	Doherty, Edelman, and Hyman (1989) Non-negative, monotonic, or convexity preserving cubic and quintic
	 *  	Hermite interpolation - Mathematics of Computation 52 (186), 471-494.
	 * 
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * @param adblFirstDerivative Array of First Derivatives
	 * @param adblSecondDerivative Array of Second Derivatives
	 * 
	 * @return The C1 Slope Quintic Stretch
	 */

	public static final double[] Hyman89QuinticMonotoneC1 (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue,
		final double[] adblFirstDerivative,
		final double[] adblSecondDerivative)
	{
		if (null == adblPredictorOrdinate || null == adblResponseValue || null == adblFirstDerivative || null
			== adblSecondDerivative)
			return null;

		int iNumPredictor = adblPredictorOrdinate.length;

		if (1 >= iNumPredictor || iNumPredictor != adblResponseValue.length || iNumPredictor !=
			adblResponseValue.length || iNumPredictor != adblResponseValue.length)
			return null;

		double[] adblAdjFirstDerivative = new double[iNumPredictor];

		double[] adblNodeC1 = LinearC1 (adblPredictorOrdinate, adblResponseValue);

		double[] adblBesselC1 = BesselC1 (adblPredictorOrdinate, adblResponseValue);

		for (int i = 0; i < iNumPredictor; ++i) {
			if (i < 2 || i >= iNumPredictor - 2)
				adblAdjFirstDerivative[i] = adblBesselC1[i];
			else {
				double dblSign = 0.;
				double dblHMinus = (adblPredictorOrdinate[i] - adblPredictorOrdinate[i - 1]);
				double dblHPlus = (adblPredictorOrdinate[i + 1] - adblPredictorOrdinate[i]);

				if (adblFirstDerivative[i - 1] * adblFirstDerivative[i] < 0.)
					dblSign = adblResponseValue[i] > 0. ? 1. : -1.;

				double dblMinSlope = java.lang.Math.min (java.lang.Math.abs (adblFirstDerivative[i - 1]),
					java.lang.Math.abs (adblFirstDerivative[i]));

				if (dblSign >= 0.)
					adblAdjFirstDerivative[i] = java.lang.Math.min (java.lang.Math.max (0.,
						adblFirstDerivative[i]), 5. * dblMinSlope);
				else
					adblAdjFirstDerivative[i] = java.lang.Math.max (java.lang.Math.min (0.,
						adblFirstDerivative[i]), -5. * dblMinSlope);

				double dblA = java.lang.Math.max (0., adblAdjFirstDerivative[i] / adblNodeC1[i - 1]);

				double dblB = java.lang.Math.max (0., adblAdjFirstDerivative[i + 1] / adblNodeC1[i]);

				double dblDPlus = adblAdjFirstDerivative[i] * adblNodeC1[i] > 0. ? adblAdjFirstDerivative[i]
					: 0.;
				double dblDMinus = adblAdjFirstDerivative[i] * adblNodeC1[i - 1] > 0. ?
					adblAdjFirstDerivative[i] : 0.;
				double dblALeft = (-7.9 * dblDPlus - 0.26 * dblDPlus * dblB) / dblHPlus;
				double dblARight = ((20. - 2. * dblB) * adblNodeC1[i] - 8. * dblDPlus - 0.48 * dblDPlus *
					dblB) / dblHPlus;
				double dblBLeft = ((2. * dblA - 20.) * adblNodeC1[i - 1] + 8. * dblDMinus - 0.48 * dblDMinus
					* dblA) / dblHMinus;
				double dblBRight = (7.9 * dblDMinus + 0.26 * dblDMinus * dblA) / dblHMinus;

				if (dblARight <= dblBLeft || dblALeft >= dblBRight) {
					double dblDenom = ((8. + 0.48 * dblB) / dblHPlus) + ((8. + 0.48 * dblA) / dblHMinus);
					adblAdjFirstDerivative[i] = (20. - 2. * dblB) * adblNodeC1[i] / dblHPlus;
					adblAdjFirstDerivative[i] += (20. - 2. * dblA) * adblNodeC1[i - 1] / dblHMinus;
					adblAdjFirstDerivative[i] /= dblDenom;
				}
			}
		}

		return adblAdjFirstDerivative;
	}

	/**
	 * Generate the Local Control Stretch in accordance with the desired Customization Parameters
	 * 
	 * @param adblPredictorOrdinate The Predictor Ordinate Array
	 * @param adblResponseValue The Response Value Array
	 * @param strGeneratorType The C1 Generator Type
	 * @param bEliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param bApplyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return Instance of the Local Control Stretch
	 */

	public static final LocalMonotoneCkGenerator Create (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue,
		final java.lang.String strGeneratorType,
		final boolean bEliminateSpuriousExtrema,
		final boolean bApplyMonotoneFilter)
	{
		try {
			LocalMonotoneCkGenerator lcr = new LocalMonotoneCkGenerator (adblPredictorOrdinate,
				adblResponseValue);

			if (!lcr.generateC1 (strGeneratorType)) return null;

			if (bEliminateSpuriousExtrema && !lcr.eliminateSpuriousExtrema()) return null;

			if (bApplyMonotoneFilter) {
				if (!lcr.applyMonotoneFilter()) return null;
			}

			return lcr;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Local Control Stretch in accordance with the desired Customization Parameters
	 * 
	 * @param aiPredictorOrdinate The Predictor Ordinate Array
	 * @param adblResponseValue The Response Value Array
	 * @param strGeneratorType The C1 Generator Type
	 * @param bEliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param bApplyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return Instance of the Local Control Stretch
	 */

	public static final LocalMonotoneCkGenerator Create (
		final int[] aiPredictorOrdinate,
		final double[] adblResponseValue,
		final java.lang.String strGeneratorType,
		final boolean bEliminateSpuriousExtrema,
		final boolean bApplyMonotoneFilter)
	{
		if (null == aiPredictorOrdinate) return null;

		int iNumPredictorOrdinate = aiPredictorOrdinate.length;
		double[] adblPredictorOrdinate = new double[iNumPredictorOrdinate];

		if (0 == iNumPredictorOrdinate) return null;

		for (int i = 0; i < iNumPredictorOrdinate; ++i)
			adblPredictorOrdinate[i] = aiPredictorOrdinate[i];

		return Create (adblPredictorOrdinate, adblResponseValue, strGeneratorType, bEliminateSpuriousExtrema,
			bApplyMonotoneFilter);
	}

	private LocalMonotoneCkGenerator (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue)
		throws java.lang.Exception
	{
		if (null == (_adblPredictorOrdinate = adblPredictorOrdinate) || null == (_adblResponseValue =
			adblResponseValue))
			throw new java.lang.Exception ("LocalMonotoneCkGenerator ctr: Invalid Inputs!");

		int iSize = _adblPredictorOrdinate.length;

		if (0 == iSize || iSize != _adblResponseValue.length)
			throw new java.lang.Exception ("LocalMonotoneCkGenerator ctr: Invalid Inputs!");
	}

	private boolean generateC1 (
		final java.lang.String strGeneratorType)
	{
		if (null == strGeneratorType || strGeneratorType.isEmpty()) return false;

		if (C1_AKIMA.equalsIgnoreCase (strGeneratorType))
			return null != (_adblC1 = AkimaC1 (_adblPredictorOrdinate, _adblResponseValue)) && 0 !=
				_adblC1.length;

		if (C1_BESSEL.equalsIgnoreCase (strGeneratorType))
			return null != (_adblC1 = BesselC1 (_adblPredictorOrdinate, _adblResponseValue)) && 0 !=
				_adblC1.length;

		if (C1_HARMONIC.equalsIgnoreCase (strGeneratorType))
			return null != (_adblC1 = HarmonicC1 (_adblPredictorOrdinate, _adblResponseValue)) && 0 !=
				_adblC1.length;

		if (C1_HUYNH_LE_FLOCH.equalsIgnoreCase (strGeneratorType))
			return null != (_adblC1 = HuynhLeFlochLimiterC1 (_adblPredictorOrdinate, _adblResponseValue)) &&
				0 != _adblC1.length;

		if (C1_HYMAN83.equalsIgnoreCase (strGeneratorType))
			return null != (_adblC1 = Hyman83C1 (_adblPredictorOrdinate, _adblResponseValue)) && 0 !=
				_adblC1.length;

		if (C1_HYMAN89.equalsIgnoreCase (strGeneratorType))
			return null != (_adblC1 = Hyman89C1 (_adblPredictorOrdinate, _adblResponseValue)) && 0 !=
				_adblC1.length;

		if (C1_KRUGER.equalsIgnoreCase (strGeneratorType))
			return null != (_adblC1 = KrugerC1 (_adblPredictorOrdinate, _adblResponseValue)) && 0 !=
				_adblC1.length;

		if (C1_MONOTONE_CONVEX.equalsIgnoreCase (strGeneratorType))
			return null != (_adblC1 = BesselC1 (_adblPredictorOrdinate, _adblResponseValue)) && 0 !=
			_adblC1.length;

		if (C1_VANILLA.equalsIgnoreCase (strGeneratorType))
			return null != (_adblC1 = LinearC1 (_adblPredictorOrdinate, _adblResponseValue)) && 0 !=
				_adblC1.length;

		if (C1_VAN_LEER.equalsIgnoreCase (strGeneratorType))
			return null != (_adblC1 = VanLeerLimiterC1 (_adblPredictorOrdinate, _adblResponseValue)) && 0 !=
				_adblC1.length;

		return false;
	}

	private boolean eliminateSpuriousExtrema()
	{
		return null != (_adblC1 = EliminateSpuriousExtrema (_adblC1, LinearC1 (_adblPredictorOrdinate,
			_adblResponseValue))) && 0 != _adblC1.length; 
	}

	private boolean applyMonotoneFilter()
	{
		return null != (_adblC1 = ApplyMonotoneFilter (_adblC1, LinearC1 (_adblPredictorOrdinate,
			_adblResponseValue))) && 0 != _adblC1.length; 
	}

	/**
	 * Retrieve the C1 Array
	 * 
	 * @return The C1 Array
	 */

	public double[] C1()
	{
		return _adblC1;
	}
}
