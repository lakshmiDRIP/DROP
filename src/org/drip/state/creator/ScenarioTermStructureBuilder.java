
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
 * ScenarioTermStructureBuilder implements the construction of the basis spline term structure using the
 * 	input instruments and their quotes.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ScenarioTermStructureBuilder {

	/**
	 * Construct a Term Structure Instance using the specified Custom Spline
	 * 
	 * @param strName Name of the the Term Structure Instance
	 * @param dtStart The Start Date
	 * @param strCurrency Currency
	 * @param adblDate Array of Dates
	 * @param adblNode Array of Term Structure Nodes
	 * @param scbc Segment Custom Builder Parameters
	 * 
	 * @return Instance of the Term Structure
	 */

	public static final org.drip.analytics.definition.NodeStructure CustomSplineTermStructure (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final double[] adblDate,
		final double[] adblNode,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc)
	{
		if (null == strName || strName.isEmpty() || null == dtStart || null == adblDate || null == adblNode
			|| null == scbc)
			return null;

		int iNumDate = adblDate.length;
		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumDate - 1];

		if (0 == iNumDate || iNumDate != adblNode.length) return null;

		for (int i = 0; i < iNumDate - 1; ++i)
			aSCBC[i] = scbc;

		try {
			return new org.drip.state.curve.BasisSplineTermStructure (dtStart.julian(),
				org.drip.state.identifier.CustomLabel.Standard (strName), strCurrency, new
					org.drip.spline.grid.OverlappingStretchSpan
						(org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
							(strName, adblDate, adblNode, aSCBC, null,
								org.drip.spline.stretch.BoundarySettings.NaturalStandard(),
									org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE)));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Term Structure Instance based off of a Cubic Polynomial Spline
	 * 
	 * @param strName Name of the the Term Structure Instance
	 * @param dtStart The Start Date
	 * @param strCurrency Currency
	 * @param astrTenor Array of Tenors
	 * @param adblNode Array of Term Structure Nodes
	 * 
	 * @return The Term Structure Instance based off of a Cubic Polynomial Spline
	 */

	public static final org.drip.analytics.definition.NodeStructure CubicPolynomialTermStructure (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final java.lang.String[] astrTenor,
		final double[] adblNode)
	{
		if (null == dtStart || null == astrTenor) return null;

		int iNumTenor = astrTenor.length;
		double[] adblDate = new double[iNumTenor];

		if (0 == iNumTenor) return null;

		for (int i = 0; i < iNumTenor; ++i)
			adblDate[i] = dtStart.addTenor (astrTenor[i]).julian();

		try {
			return CustomSplineTermStructure (strName, dtStart, strCurrency, adblDate, adblNode, new
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
	 * Construct a Term Structure Instance based off of a Quartic Polynomial Spline
	 * 
	 * @param strName Name of the the Term Structure Instance
	 * @param dtStart The Start Date
	 * @param strCurrency Currency
	 * @param astrTenor Array of Tenors
	 * @param adblNode Array of Term Structure Nodes
	 * 
	 * @return The Term Structure Instance based off of a Quartic Polynomial Spline
	 */

	public static final org.drip.analytics.definition.NodeStructure QuarticPolynomialTermStructure (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final java.lang.String[] astrTenor,
		final double[] adblNode)
	{
		if (null == dtStart || null == astrTenor) return null;

		int iNumTenor = astrTenor.length;
		double[] adblDate = new double[iNumTenor];

		if (0 == iNumTenor) return null;

		for (int i = 0; i < iNumTenor; ++i)
			adblDate[i] = dtStart.addTenor (astrTenor[i]).julian();

		try {
			return CustomSplineTermStructure (strName, dtStart, strCurrency, adblDate, adblNode, new
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
	 * Construct a Term Structure Instance based off of a Kaklis-Pandelis Polynomial Tension Spline
	 * 
	 * @param strName Name of the the Term Structure Instance
	 * @param dtStart The Start Date
	 * @param strCurrency Currency
	 * @param astrTenor Array of Tenors
	 * @param adblNode Array of Term Structure Nodes
	 * 
	 * @return The Term Structure Instance based off of a Kaklis-Pandelis Polynomial Tension Spline
	 */

	public static final org.drip.analytics.definition.NodeStructure KaklisPandelisTermStructure (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final java.lang.String[] astrTenor,
		final double[] adblNode)
	{
		if (null == dtStart || null == astrTenor) return null;

		int iNumTenor = astrTenor.length;
		double[] adblDate = new double[iNumTenor];

		if (0 == iNumTenor) return null;

		for (int i = 0; i < iNumTenor; ++i)
			adblDate[i] = dtStart.addTenor (astrTenor[i]).julian();

		try {
			return CustomSplineTermStructure (strName, dtStart, strCurrency, adblDate, adblNode, new
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
	 * Construct a Term Structure Instance based off of a KLK Hyperbolic Tension Spline
	 * 
	 * @param strName Name of the the Term Structure Instance
	 * @param dtStart The Start Date
	 * @param strCurrency Currency
	 * @param astrTenor Array of Tenors
	 * @param adblNode Array of Term Structure Nodes
	 * @param dblTension Tension
	 * 
	 * @return The Term Structure Instance based off of a KLK Hyperbolic Tension Spline
	 */

	public static final org.drip.analytics.definition.NodeStructure KLKHyperbolicTermStructure (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final java.lang.String[] astrTenor,
		final double[] adblNode,
		final double dblTension)
	{
		if (null == dtStart || null == astrTenor) return null;

		int iNumTenor = astrTenor.length;
		double[] adblDate = new double[iNumTenor];

		if (0 == iNumTenor) return null;

		for (int i = 0; i < iNumTenor; ++i)
			adblDate[i] = dtStart.addTenor (astrTenor[i]).julian();

		try {
			return CustomSplineTermStructure (strName, dtStart, strCurrency, adblDate, adblNode, new
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
	 * Construct a Term Structure Instance based off of a KLK Rational Linear Tension Spline
	 * 
	 * @param strName Name of the the Term Structure Instance
	 * @param dtStart The Start Date
	 * @param strCurrency Currency
	 * @param astrTenor Array of Tenors
	 * @param adblNode Array of Term Structure Nodes
	 * @param dblTension Tension
	 * 
	 * @return The Term Structure Instance based off of a KLK Rational Linear Tension Spline
	 */

	public static final org.drip.analytics.definition.NodeStructure KLKRationalLinearTermStructure (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final java.lang.String[] astrTenor,
		final double[] adblNode,
		final double dblTension)
	{
		if (null == dtStart || null == astrTenor) return null;

		int iNumTenor = astrTenor.length;
		double[] adblDate = new double[iNumTenor];

		if (0 == iNumTenor) return null;

		for (int i = 0; i < iNumTenor; ++i)
			adblDate[i] = dtStart.addTenor (astrTenor[i]).julian();

		try {
			return CustomSplineTermStructure (strName, dtStart, strCurrency, adblDate, adblNode, new
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
	 * Construct a Term Structure Instance based off of a KLK Rational Quadratic Tension Spline
	 * 
	 * @param strName Name of the the Term Structure Instance
	 * @param dtStart The Start Date
	 * @param strCurrency Currency
	 * @param astrTenor Array of Tenors
	 * @param adblNode Array of Term Structure Nodes
	 * @param dblTension Tension
	 * 
	 * @return The Term Structure Instance based off of a KLK Rational Quadratic Tension Spline
	 */

	public static final org.drip.analytics.definition.NodeStructure KLKRationalQuadraticTermStructure (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtStart,
		final java.lang.String strCurrency,
		final java.lang.String[] astrTenor,
		final double[] adblNode,
		final double dblTension)
	{
		if (null == dtStart || null == astrTenor) return null;

		int iNumTenor = astrTenor.length;
		double[] adblDate = new double[iNumTenor];

		if (0 == iNumTenor) return null;

		for (int i = 0; i < iNumTenor; ++i)
			adblDate[i] = dtStart.addTenor (astrTenor[i]).julian();

		try {
			return CustomSplineTermStructure (strName, dtStart, strCurrency, adblDate, adblNode, new
				org.drip.spline.params.SegmentCustomBuilderControl
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION,
						new org.drip.spline.basis.ExponentialTensionSetParams (dblTension),
							org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2), null, null));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
