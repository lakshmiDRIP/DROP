
package org.drip.state.creator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>ScenarioTermStructureBuilder</i> implements the construction of the basis spline term structure using
 * the input instruments and their quotes.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/README.md">Scenario State Curve/Surface Builders</a></li>
 *  </ul>
 * <br><br>
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
