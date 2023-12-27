
package org.drip.state.creator;

import org.drip.analytics.date.JulianDate;
import org.drip.spline.basis.ExponentialTensionSetParams;
import org.drip.spline.basis.KaklisPandelisSetParams;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.grid.OverlappingStretchSpan;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.params.SegmentInelasticDesignControl;
import org.drip.spline.stretch.BoundarySettings;
import org.drip.spline.stretch.MultiSegmentSequence;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.curve.BasisSplineDeterministicVolatility;
import org.drip.state.identifier.CustomLabel;
import org.drip.state.identifier.VolatilityLabel;
import org.drip.state.nonlinear.FlatForwardVolatilityCurve;
import org.drip.state.volatility.VolatilityCurve;

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
 * <i>ScenarioDeterministicVolatilityBuilder</i> implements the construction of the basis spline
 * 	deterministic volatility term structure using the input instruments and their quotes. It exposes the
 *  following Functions:
 *
 *  <ul>
 * 		<li>Construct the Deterministic Volatility Term Structure Instance using the specified Custom Spline</li>
 * 		<li>Construct the Deterministic Volatility Term Structure Instance based off of a Cubic Polynomial Spline</li>
 * 		<li>Construct the Deterministic Volatility Term Structure Instance based off of a Quartic Polynomial Spline</li>
 * 		<li>Construct the Deterministic Volatility Term Structure Instance based off of a Kaklis-Pandelis Polynomial Tension Spline</li>
 * 		<li>Construct the Deterministic Volatility Term Structure Instance based off of a KLK Hyperbolic Tension Spline</li>
 * 		<li>Construct the Deterministic Volatility Term Structure Instance based off of a KLK Rational Linear Tension Spline</li>
 * 		<li>Construct the Deterministic Volatility Term Structure Instance based off of a KLK Rational Quadratic Tension Spline</li>
 * 		<li>Construct the Flat Constant Forward Volatility Forward Curve</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/README.md">Scenario State Curve/Surface Builders</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ScenarioDeterministicVolatilityBuilder
{

	/**
	 * Construct the Deterministic Volatility Term Structure Instance using the specified Custom Spline
	 * 
	 * @param name Name of the the Term Structure Instance
	 * @param startDate The Start Date
	 * @param currency Currency
	 * @param dateArray Array of Dates
	 * @param impliedVolatilityArray Array of Implied Volatility Nodes
	 * @param segmentCustomBuilderControl Segment Custom Builder Parameters
	 * 
	 * @return Instance of the Term Structure
	 */

	public static final VolatilityCurve CustomSplineTermStructure (
		final String name,
		final JulianDate startDate,
		final String currency,
		final int[] dateArray,
		final double[] impliedVolatilityArray,
		final SegmentCustomBuilderControl segmentCustomBuilderControl)
	{
		if (null == startDate || null == dateArray || null == impliedVolatilityArray ||
			null == segmentCustomBuilderControl) {
			return null;
		}

		int dateNodeCount = dateArray.length;
		SegmentCustomBuilderControl[] segmentCustomBuilderControlArray =
			new SegmentCustomBuilderControl[dateNodeCount - 1];

		if (0 == dateNodeCount || dateNodeCount != impliedVolatilityArray.length) {
			return null;
		}

		for (int dateNodeIndex = 0; dateNodeIndex < dateNodeCount - 1; ++dateNodeIndex) {
			segmentCustomBuilderControlArray[dateNodeIndex] = segmentCustomBuilderControl;
		}

		try {
			return new BasisSplineDeterministicVolatility (
				startDate.julian(),
				CustomLabel.Standard (name),
				currency,
				new OverlappingStretchSpan (
					MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
						name,
						dateArray,
						impliedVolatilityArray,
						segmentCustomBuilderControlArray,
						null,
						BoundarySettings.NaturalStandard(),
						MultiSegmentSequence.CALIBRATE
					)
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Deterministic Volatility Term Structure Instance based off of a Cubic Polynomial Spline
	 * 
	 * @param name Name of the the Term Structure Instance
	 * @param startDate The Start Date
	 * @param currency Currency
	 * @param tenorArray Array of Tenors
	 * @param impliedVolatilityArray Array of Implied Volatility Nodes
	 * 
	 * @return The Deterministic Volatility Term Structure Instance based off of a Cubic Polynomial Spline
	 */

	public static final VolatilityCurve CubicPolynomialTermStructure (
		final String name,
		final JulianDate startDate,
		final String currency,
		final String[] tenorArray,
		final double[] impliedVolatilityArray)
	{
		if (null == startDate || null == tenorArray) {
			return null;
		}

		int tenorCount = tenorArray.length;
		int[] dateArray = new int[tenorCount];

		if (0 == tenorCount) {
			return null;
		}

		for (int tenorIndex = 0; tenorIndex < tenorCount; ++tenorIndex) {
			dateArray[tenorIndex] = startDate.addTenor (tenorArray[tenorIndex]).julian();
		}

		try {
			return CustomSplineTermStructure (
				name,
				startDate,
				currency,
				dateArray,
				impliedVolatilityArray,
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
	 * Construct the Deterministic Volatility Term Structure Instance based off of a Quartic Polynomial
	 * 	Spline
	 * 
	 * @param name Name of the the Term Structure Instance
	 * @param startDate The Start Date
	 * @param currency Currency
	 * @param tenorArray Array of Tenors
	 * @param impliedVolatilityArray Array of Implied Volatility Nodes
	 * 
	 * @return The Deterministic Volatility Term Structure Instance based off of a Quartic Polynomial Spline
	 */

	public static final VolatilityCurve QuarticPolynomialTermStructure (
		final String name,
		final JulianDate startDate,
		final String currency,
		final String[] tenorArray,
		final double[] impliedVolatilityArray)
	{
		if (null == startDate || null == tenorArray) {
			return null;
		}

		int tenorCount = tenorArray.length;
		int[] dateArray = new int[tenorCount];

		if (0 == tenorCount) {
			return null;
		}

		for (int tenorIndex = 0; tenorIndex < tenorCount; ++tenorIndex) {
			dateArray[tenorIndex] = startDate.addTenor (tenorArray[tenorIndex]).julian();
		}

		try {
			return CustomSplineTermStructure (
				name,
				startDate,
				currency,
				dateArray,
				impliedVolatilityArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new PolynomialFunctionSetParams (5),
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
	 * Construct the Deterministic Volatility Term Structure Instance based off of a Kaklis-Pandelis
	 * 	Polynomial Tension Spline
	 * 
	 * @param name Name of the the Term Structure Instance
	 * @param startDate The Start Date
	 * @param currency Currency
	 * @param tenorArray Array of Tenors
	 * @param impliedVolatilityArray Array of Implied Volatility Nodes
	 * 
	 * @return The Deterministic Volatility Term Structure Instance based off of a Kaklis-Pandelis Polynomial
	 * 	Tension Spline
	 */

	public static final VolatilityCurve KaklisPandelisTermStructure (
		final String name,
		final JulianDate startDate,
		final String currency,
		final String[] tenorArray,
		final double[] impliedVolatilityArray)
	{
		if (null == startDate || null == tenorArray) {
			return null;
		}

		int tenorCount = tenorArray.length;
		int[] dateArray = new int[tenorCount];

		if (0 == tenorCount) {
			return null;
		}

		for (int tenorIndex = 0; tenorIndex < tenorCount; ++tenorIndex) {
			dateArray[tenorIndex] = startDate.addTenor (tenorArray[tenorIndex]).julian();
		}

		try {
			return CustomSplineTermStructure (
				name,
				startDate,
				currency,
				dateArray,
				impliedVolatilityArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_KAKLIS_PANDELIS,
					new KaklisPandelisSetParams (2),
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
	 * Construct the Deterministic Volatility Term Structure Instance based off of a KLK Hyperbolic Tension
	 * 	Spline
	 * 
	 * @param name Name of the the Term Structure Instance
	 * @param startDate The Start Date
	 * @param currency Currency
	 * @param tenorArray Array of Tenors
	 * @param impliedVolatilityArray Array of Implied Volatility Nodes
	 * @param tension Tension
	 * 
	 * @return The Deterministic Volatility Term Structure Instance based off of a KLK Hyperbolic Tension
	 * 	Spline
	 */

	public static final VolatilityCurve KLKHyperbolicTermStructure (
		final String name,
		final JulianDate startDate,
		final String currency,
		final String[] tenorArray,
		final double[] impliedVolatilityArray,
		final double tension)
	{
		if (null == startDate || null == tenorArray) {
			return null;
		}

		int tenorCount = tenorArray.length;
		int[] dateArray = new int[tenorCount];

		if (0 == tenorCount) {
			return null;
		}

		for (int tenorIndex = 0; tenorIndex < tenorCount; ++tenorIndex) {
			dateArray[tenorIndex] = startDate.addTenor (tenorArray[tenorIndex]).julian();
		}

		try {
			return CustomSplineTermStructure (
				name,
				startDate,
				currency,
				dateArray,
				impliedVolatilityArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION,
					new ExponentialTensionSetParams (tension),
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
	 * Construct the Deterministic Volatility Term Structure Instance based off of a KLK Rational Linear
	 * 	Tension Spline
	 * 
	 * @param name Name of the the Term Structure Instance
	 * @param startDate The Start Date
	 * @param currency Currency
	 * @param tenorArray Array of Tenors
	 * @param impliedVolatilityArray Array of Implied Volatility Nodes
	 * @param tension Tension
	 * 
	 * @return The Deterministic Volatility Term Structure Instance based off of a KLK Rational Linear
	 * 	Tension Spline
	 */

	public static final VolatilityCurve KLKRationalLinearTermStructure (
		final String name,
		final JulianDate startDate,
		final String currency,
		final String[] tenorArray,
		final double[] impliedVolatilityArray,
		final double tension)
	{
		if (null == startDate || null == tenorArray) {
			return null;
		}

		int tenorCount = tenorArray.length;
		int[] dateArray = new int[tenorCount];

		if (0 == tenorCount) {
			return null;
		}

		for (int tenorIndex = 0; tenorIndex < tenorCount; ++tenorIndex) {
			dateArray[tenorIndex] = startDate.addTenor (tenorArray[tenorIndex]).julian();
		}

		try {
			return CustomSplineTermStructure (
				name,
				startDate,
				currency,
				dateArray,
				impliedVolatilityArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION,
					new ExponentialTensionSetParams (tension),
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
	 * Construct the Deterministic Volatility Term Structure Instance based off of a KLK Rational Quadratic
	 * 	Tension Spline
	 * 
	 * @param name Name of the the Term Structure Instance
	 * @param startDate The Start Date
	 * @param currency Currency
	 * @param tenorArray Array of Tenors
	 * @param impliedVolatilityArray Array of Implied Volatility Nodes
	 * @param tension Tension
	 * 
	 * @return The Deterministic Volatility Term Structure Instance based off of a KLK Rational Quadratic
	 * 	Tension Spline
	 */

	public static final VolatilityCurve KLKRationalQuadraticTermStructure (
		final String name,
		final JulianDate startDate,
		final String currency,
		final String[] tenorArray,
		final double[] impliedVolatilityArray,
		final double tension)
	{
		if (null == startDate || null == tenorArray) {
			return null;
		}

		int tenorCount = tenorArray.length;
		int[] dateArray = new int[tenorCount];

		if (0 == tenorCount) {
			return null;
		}

		for (int tenorIndex = 0; tenorIndex < tenorCount; ++tenorIndex) {
			dateArray[tenorIndex] = startDate.addTenor (tenorArray[tenorIndex]).julian();
		}

		try {
			return CustomSplineTermStructure (
				name,
				startDate,
				currency,
				dateArray,
				impliedVolatilityArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION,
					new ExponentialTensionSetParams (tension),
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
	 * Construct the Flat Constant Forward Volatility Forward Curve
	 * 
	 * @param epochDate Epoch Date
	 * @param volatilityLabel Forward Volatility Label
	 * @param currency Currency
	 * @param flatVolatility Flat Volatility
	 * 
	 * @return The Volatility Curve Instance
	 */

	public static final VolatilityCurve FlatForward (
		final int epochDate,
		final VolatilityLabel volatilityLabel,
		final String currency,
		final double flatVolatility)
	{
		try {
			return new FlatForwardVolatilityCurve (
				epochDate,
				volatilityLabel,
				currency,
				new int[] {epochDate},
				new double[] {flatVolatility}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
