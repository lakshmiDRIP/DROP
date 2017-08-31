
package org.drip.state.creator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * ScenarioBasisCurveBuilder implements the construction of the scenario basis curve using the input
 * 	instruments and their quotes.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ScenarioBasisCurveBuilder {

	/**
	 * Create an Instance of the Custom Splined Basis Curve
	 * 
	 * @param strName Curve Name
	 * @param dtSpot The Spot Date
	 * @param friReference Reference Leg FRI
	 * @param friDerived Derived Leg FRI
	 * @param bBasisOnReference TRUE - The Basis Quote is on the Reference Leg
	 * @param astrTenor Array of the Tenors
	 * @param adblBasis Array of the Basis Spreads
	 * @param scbc The Segment Custom Builder Control
	 * 
	 * @return The Instance of the Basis Curve
	 */

	public static final org.drip.state.basis.BasisCurve CustomSplineBasisCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.state.identifier.ForwardLabel friReference,
		final org.drip.state.identifier.ForwardLabel friDerived,
		final boolean bBasisOnReference,
		final java.lang.String[] astrTenor,
		final double[] adblBasis,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc)
	{
		if (null == strName || null == dtSpot || strName.isEmpty() || null == astrTenor || null == adblBasis)
			return null;

		int iNumTenor = astrTenor.length;
		int[] aiBasisPredictorOrdinate = new int[iNumTenor + 1];
		double[] adblBasisResponseValue = new double[iNumTenor + 1];
		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumTenor];

		if (0 == iNumTenor || iNumTenor != adblBasis.length) return null;

		for (int i = 0; i <= iNumTenor; ++i) {
			if (0 != i) {
				java.lang.String strTenor = astrTenor[i - 1];

				if (null == strTenor || strTenor.isEmpty()) return null;

				org.drip.analytics.date.JulianDate dtMaturity = dtSpot.addTenor (strTenor);

				if (null == dtMaturity) return null;

				aiBasisPredictorOrdinate[i] = dtMaturity.julian();
			} else
				aiBasisPredictorOrdinate[i] = dtSpot.julian();

			adblBasisResponseValue[i] = 0 == i ? adblBasis[0] : adblBasis[i - 1];

			if (0 != i) aSCBC[i - 1] = scbc;
		}

		try {
			return new org.drip.state.curve.BasisSplineBasisCurve (friReference, friDerived,
				bBasisOnReference, new org.drip.spline.grid.OverlappingStretchSpan
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
	 * Create an Instance of the Cubic Polynomial Splined Basis Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param friReference Reference Leg FRI
	 * @param friDerived Derived Leg FRI
	 * @param bBasisOnReference TRUE - The Basis Quote is on the Reference Leg
	 * @param astrTenor Array of the Tenors
	 * @param adblBasis Array of the Basis Spreads
	 * 
	 * @return The Instance of the Basis Curve
	 */

	public static final org.drip.state.basis.BasisCurve CubicPolynomialBasisCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final org.drip.state.identifier.ForwardLabel friReference,
		final org.drip.state.identifier.ForwardLabel friDerived,
		final boolean bBasisOnReference,
		final java.lang.String[] astrTenor,
		final double[] adblBasis)
	{
		try {
			return CustomSplineBasisCurve (strName, dtStart, friReference, friDerived, bBasisOnReference,
				astrTenor, adblBasis, new org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
						org.drip.spline.basis.PolynomialFunctionSetParams (4),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Quartic Polynomial Splined Basis Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param friReference Reference Leg FRI
	 * @param friDerived Derived Leg FRI
	 * @param bBasisOnReference TRUE - The Basis Quote is on the Reference Leg
	 * @param astrTenor Array of the Tenors
	 * @param adblBasis Array of the Basis Spreads
	 * 
	 * @return The Instance of the Basis Curve
	 */

	public static final org.drip.state.basis.BasisCurve QuarticPolynomialBasisCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final org.drip.state.identifier.ForwardLabel friReference,
		final org.drip.state.identifier.ForwardLabel friDerived,
		final boolean bBasisOnReference,
		final java.lang.String[] astrTenor,
		final double[] adblBasis)
	{
		try {
			return CustomSplineBasisCurve (strName, dtStart, friReference, friDerived, bBasisOnReference,
				astrTenor, adblBasis, new org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL, new
						org.drip.spline.basis.PolynomialFunctionSetParams (5),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Kaklis-Pandelis Splined Basis Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param friReference Reference Leg FRI
	 * @param friDerived Derived Leg FRI
	 * @param bBasisOnReference TRUE - The Basis Quote is on the Reference Leg
	 * @param astrTenor Array of the Tenors
	 * @param adblBasis Array of the Basis Spreads
	 * 
	 * @return The Instance of the Basis Curve
	 */

	public static final org.drip.state.basis.BasisCurve KaklisPandelisBasisCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final org.drip.state.identifier.ForwardLabel friReference,
		final org.drip.state.identifier.ForwardLabel friDerived,
		final boolean bBasisOnReference,
		final java.lang.String[] astrTenor,
		final double[] adblBasis)
	{
		try {
			return CustomSplineBasisCurve (strName, dtStart, friReference, friDerived, bBasisOnReference,
				astrTenor, adblBasis, new org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KAKLIS_PANDELIS,
						new org.drip.spline.basis.KaklisPandelisSetParams (2),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Hyperbolic Splined Basis Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param friReference Reference Leg FRI
	 * @param friDerived Derived Leg FRI
	 * @param bBasisOnReference TRUE - The Basis Quote is on the Reference Leg
	 * @param astrTenor Array of the Tenors
	 * @param adblBasis Array of the Basis Spreads
	 * @param dblTension The Tension Parameter
	 * 
	 * @return The Instance of the Basis Curve
	 */

	public static final org.drip.state.basis.BasisCurve KLKHyperbolicBasisCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final org.drip.state.identifier.ForwardLabel friReference,
		final org.drip.state.identifier.ForwardLabel friDerived,
		final boolean bBasisOnReference,
		final java.lang.String[] astrTenor,
		final double[] adblBasis,
		final double dblTension)
	{
		try {
			return CustomSplineBasisCurve (strName, dtStart, friReference, friDerived, bBasisOnReference,
				astrTenor, adblBasis, new org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION,
						new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Rational Linear Splined Basis Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param friReference Reference Leg FRI
	 * @param friDerived Derived Leg FRI
	 * @param bBasisOnReference TRUE - The Basis Quote is on the Reference Leg
	 * @param astrTenor Array of the Tenors
	 * @param adblBasis Array of the Basis Spreads
	 * @param dblTension The Tension Parameter
	 * 
	 * @return The Instance of the Basis Curve
	 */

	public static final org.drip.state.basis.BasisCurve KLKRationalLinearBasisCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final org.drip.state.identifier.ForwardLabel friReference,
		final org.drip.state.identifier.ForwardLabel friDerived,
		final boolean bBasisOnReference,
		final java.lang.String[] astrTenor,
		final double[] adblBasis,
		final double dblTension)
	{
		try {
			return CustomSplineBasisCurve (strName, dtStart, friReference, friDerived, bBasisOnReference,
				astrTenor, adblBasis, new org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION,
				new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
					org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Rational Quadratic Splined Basis Curve
	 * 
	 * @param strName Curve Name
	 * @param dtStart The Tenor Start Date
	 * @param friReference Reference Leg FRI
	 * @param friDerived Derived Leg FRI
	 * @param bBasisOnReference TRUE - The Basis Quote is on the Reference Leg
	 * @param astrTenor Array of the Tenors
	 * @param adblBasis Array of the Basis Spreads
	 * @param dblTension The Tension Parameter
	 * 
	 * @return The Instance of the Basis Curve
	 */

	public static final org.drip.state.basis.BasisCurve KLKRationalQuadraticBasisCurve (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final org.drip.state.identifier.ForwardLabel friReference,
		final org.drip.state.identifier.ForwardLabel friDerived,
		final boolean bBasisOnReference,
		final java.lang.String[] astrTenor,
		final double[] adblBasis,
		final double dblTension)
	{
		try {
			return CustomSplineBasisCurve (strName, dtStart, friReference, friDerived, bBasisOnReference,
				astrTenor, adblBasis, new org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION,
				new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
					org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
