
package org.drip.state.creator;

import org.drip.analytics.date.JulianDate;
import org.drip.product.definition.Component;
import org.drip.spline.basis.ExponentialTensionSetParams;
import org.drip.spline.basis.KaklisPandelisSetParams;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.grid.OverlappingStretchSpan;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.params.SegmentInelasticDesignControl;
import org.drip.spline.stretch.BoundarySettings;
import org.drip.spline.stretch.MultiSegmentSequence;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.curve.BasisSplineRepoCurve;
import org.drip.state.nonlinear.FlatForwardRepoCurve;
import org.drip.state.repo.RepoCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>ScenarioRepoCurveBuilder</i> implements the Construction of the Scenario Repo Curve using the Input
 * 	Instruments and their Quotes. It implements the following Functions:
 * 
 * <ul>
 * 		<li>Create an Instance of the Custom Splined Repo Curve</li>
 * 		<li>Create an Instance of the Cubic Polynomial Splined Repo Curve</li>
 * 		<li>Create an Instance of the Quartic Polynomial Splined Repo Curve</li>
 * 		<li>Create an Instance of the Kaklis-Pandelis Splined Repo Curve</li>
 * 		<li>Create an Instance of the KLK Hyperbolic Splined Repo Curve</li>
 * 		<li>Create an Instance of the KLK Rational Linear Splined Repo Curve</li>
 * 		<li>Create an Instance of the KLK Rational Quadratic Splined Repo Curve</li>
 * 		<li>Construct a Repo Curve using the Flat Repo Rate</li>
 * </ul>
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

public class ScenarioRepoCurveBuilder
{

	/**
	 * Create an Instance of the Custom Splined Repo Curve
	 * 
	 * @param name Curve Name
	 * @param spotDate The Spot Date
	 * @param component The Underlying Repo Component
	 * @param dateArray Array of the Dates
	 * @param repoRateArray Array of the Repo Rates
	 * @param segmentCustomBuilderControl The Segment Custom Builder Control
	 * 
	 * @return The Instance of the Custom Splined Repo Curve
	 */

	public static final RepoCurve CustomSplineRepoCurve (
		final String name,
		final JulianDate spotDate,
		final Component component,
		final int[] dateArray,
		final double[] repoRateArray,
		final SegmentCustomBuilderControl segmentCustomBuilderControl)
	{
		if (null == spotDate || null == dateArray || null == repoRateArray) {
			return null;
		}

		int instrumentCount = dateArray.length;
		int[] basisPredictorOrdinateArray = new int[instrumentCount + 1];
		double[] basisResponseValueArray = new double[instrumentCount + 1];
		SegmentCustomBuilderControl[] segmentCustomBuilderControlArray =
			new SegmentCustomBuilderControl[instrumentCount];

		if (0 == instrumentCount || instrumentCount != repoRateArray.length) {
			return null;
		}

		for (int instrumentIndex = 0; instrumentIndex <= instrumentCount; ++instrumentIndex) {
			basisPredictorOrdinateArray[instrumentIndex] = 0 == instrumentIndex ?
				spotDate.julian() : dateArray[instrumentIndex - 1];

			basisResponseValueArray[instrumentIndex] = 0 == instrumentIndex ?
				repoRateArray[0] : repoRateArray[instrumentIndex - 1];

			if (0 != instrumentIndex) {
				segmentCustomBuilderControlArray[instrumentIndex - 1] = segmentCustomBuilderControl;
			}
		}

		try {
			return new BasisSplineRepoCurve (
				component,
				new OverlappingStretchSpan (
					MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
						name,
						basisPredictorOrdinateArray,
						basisResponseValueArray,
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
	 * Create an Instance of the Cubic Polynomial Splined Repo Curve
	 * 
	 * @param name Curve Name
	 * @param spotDate The Spot Date
	 * @param component The Underlying Repo Component
	 * @param dateArray Array of the Dates
	 * @param repoRateArray Array of the Repo Rates
	 * 
	 * @return The Instance of the Basis Curve
	 */

	public static final RepoCurve CubicPolynomialRepoCurve (
		final String name,
		final JulianDate spotDate,
		final Component component,
		final int[] dateArray,
		final double[] repoRateArray)
	{
		try {
			return CustomSplineRepoCurve (
				name,
				spotDate,
				component,
				dateArray,
				repoRateArray,
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
	 * Create an Instance of the Quartic Polynomial Splined Repo Curve
	 * 
	 * @param name Curve Name
	 * @param spotDate The Spot Date
	 * @param component The Underlying Repo Component
	 * @param dateArray Array of the Dates
	 * @param repoRateArray Array of the Repo Rates
	 * 
	 * @return The Instance of the Splined Repo Curve
	 */

	public static final RepoCurve QuarticPolynomialRepoCurve (
		final String name,
		final JulianDate spotDate,
		final Component component,
		final int[] dateArray,
		final double[] repoRateArray)
	{
		try {
			return CustomSplineRepoCurve (
				name,
				spotDate,
				component,
				dateArray,
				repoRateArray,
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
	 * Create an Instance of the Kaklis-Pandelis Splined Repo Curve
	 * 
	 * @param name Curve Name
	 * @param spotDate The Spot Date
	 * @param component The Underlying Repo Component
	 * @param dateArray Array of the Dates
	 * @param repoRateArray Array of the Repo Rates
	 * 
	 * @return The Instance of the Splined Repo Curve
	 */

	public static final RepoCurve KaklisPandelisRepoCurve (
		final String name,
		final JulianDate spotDate,
		final Component component,
		final int[] dateArray,
		final double[] repoRateArray)
	{
		try {
			return CustomSplineRepoCurve (
				name,
				spotDate,
				component,
				dateArray,
				repoRateArray,
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
	 * Create an Instance of the KLK Hyperbolic Splined Repo Curve
	 * 
	 * @param name Curve Name
	 * @param spotDate The Spot Date
	 * @param component The Underlying Repo Component
	 * @param dateArray Array of the Dates
	 * @param repoRateArray Array of the Repo Rates
	 * @param tension The Tension Parameter
	 * 
	 * @return The Instance of the Splined Repo Curve
	 */

	public static final RepoCurve KLKHyperbolicRepoCurve (
		final String name,
		final JulianDate spotDate,
		final Component component,
		final int[] dateArray,
		final double[] repoRateArray,
		final double tension)
	{
		try {
			return CustomSplineRepoCurve (
				name,
				spotDate,
				component,
				dateArray,
				repoRateArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION,
					new ExponentialTensionSetParams (2),
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
	 * Create an Instance of the KLK Rational Linear Splined Repo Curve
	 * 
	 * @param name Curve Name
	 * @param spotDate The Spot Date
	 * @param component The Underlying Repo Component
	 * @param dateArray Array of the Dates
	 * @param repoRateArray Array of the Repo Rates
	 * @param tension The Tension Parameter
	 * 
	 * @return The Instance of the Repo Curve
	 */

	public static final RepoCurve KLKRationalLinearRepoCurve (
		final String name,
		final JulianDate spotDate,
		final Component component,
		final int[] dateArray,
		final double[] repoRateArray,
		final double tension)
	{
		try {
			return CustomSplineRepoCurve (
				name,
				spotDate,
				component,
				dateArray,
				repoRateArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION,
					new ExponentialTensionSetParams (2),
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
	 * Create an Instance of the KLK Rational Quadratic Splined Repo Curve
	 * 
	 * @param name Curve Name
	 * @param spotDate The Spot Date
	 * @param component The Underlying Repo Component
	 * @param dateArray Array of the Dates
	 * @param repoRateArray Array of the Repo Rates
	 * @param tension The Tension Parameter
	 * 
	 * @return The Instance of the Repo Curve
	 */

	public static final RepoCurve KLKRationalQuadraticRepoCurve (
		final String name,
		final JulianDate spotDate,
		final Component component,
		final int[] dateArray,
		final double[] repoRateArray,
		final double tension)
	{
		try {
			return CustomSplineRepoCurve (
				name,
				spotDate,
				component,
				dateArray,
				repoRateArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION,
					new ExponentialTensionSetParams (2),
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
	 * Construct a Repo Curve using the Flat Repo Rate
	 * 
	 * @param spotDate Spot Date
	 * @param component Repo Component
	 * @param repoRate The Flat Repo Rate
	 * 
	 * @return The Flat Repo Rate Curve
	 */

	public static final RepoCurve FlatRateRepoCurve (
		final JulianDate spotDate,
		final Component component,
		final double repoRate)
	{
		if (null == spotDate) {
			return null;
		}

		int epochDate = spotDate.julian();

		try {
			return new FlatForwardRepoCurve (
				epochDate,
				component,
				new int[] {epochDate},
				new double[] {repoRate}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
