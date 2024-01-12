
package org.drip.spline.pchip;

import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.params.SegmentPredictorResponseDerivative;
import org.drip.spline.params.StretchBestFitResponse;
import org.drip.spline.stretch.MultiSegmentSequence;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>LocalControlStretchBuilder</i> exports Stretch creation/calibration methods to generate customized
 * 	basis splines, with customized segment behavior using the segment control. It provides the following
 * 	local-control functionality:
 *
 * <br>
 * <ul>
 * 		<li>Create a Stretch off of Hermite Splines from the specified the Predictor Ordinates, the Response Values, the Custom Slopes, and the Segment Builder Parameters</li>
 * 		<li>Create Hermite/Bessel C<sup>1</sup> Cubic Spline Stretch</li>
 * 		<li>Create Hyman (1983) Monotone Preserving Stretch</li>
 * 		<li>Create Hyman (1989) enhancement to the Hyman (1983) Monotone Preserving Stretch</li>
 * 		<li>Create the Harmonic Monotone Preserving Stretch</li>
 * 		<li>Create the van Leer Limiter Stretch</li>
 * 		<li>Create the Kruger Stretch</li>
 * 		<li>Create the Huynh Le Floch Limiter Stretch</li>
 * 		<li>Generate the local control C<sup>1</sup> Slope using the Akima Cubic Algorithm</li>
 * 		<li>Generate the local control C<sup>1</sup> Slope using the Hagan-West Monotone Convex Algorithm</li>
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
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/pchip/README.md">Monotone Convex Themed PCHIP Splines</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LocalControlStretchBuilder
{

	/**
	 * Create a Stretch off of Hermite Splines from the specified the Predictor Ordinates, the Response
	 *  Values, the Custom Slopes, and the Segment Builder Parameters.
	 * 
	 * @param name Stretch Name
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param responseValueArray Array of Response Values
	 * @param customSlopeArray Array of Custom Slopes
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Parameters
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param setupMode Calibration Set up Mode NATURAL | FINANCIAL | FLOATING | NOTAKNOT
	 * 
	 * @return The Instance of the Hermite Spline Stretch
	 */

	public static final MultiSegmentSequence CustomSlopeHermiteSpline (
		final String name,
		final double[] predictorOrdinateArray,
		final double[] responseValueArray,
		final double[] customSlopeArray,
		final SegmentCustomBuilderControl[] segmentCustomBuilderControlArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final int setupMode)
	{
		MultiSegmentSequence multiSegmentSequence =
			MultiSegmentSequenceBuilder.CreateUncalibratedStretchEstimator (
				name,
				predictorOrdinateArray,
				segmentCustomBuilderControlArray
			);

		if (null == multiSegmentSequence || null == responseValueArray || null == customSlopeArray) {
			return null;
		}

		int responseCount = responseValueArray.length;
		SegmentPredictorResponseDerivative[] leftSegmentPredictorResponseDerivativeArray =
			new SegmentPredictorResponseDerivative[responseCount - 1];
		SegmentPredictorResponseDerivative[] rightSegmentPredictorResponseDerivativeArray =
			new SegmentPredictorResponseDerivative[responseCount - 1];

		if (1 >= responseCount || predictorOrdinateArray.length != responseCount ||
			customSlopeArray.length != responseCount) {
			return null;
		}

		for (int responseIndex = 0; responseIndex < responseCount; ++responseIndex) {
			SegmentPredictorResponseDerivative segmentPredictorResponseDerivative = null;

			try {
				segmentPredictorResponseDerivative = new SegmentPredictorResponseDerivative (
					responseValueArray[responseIndex],
					new double[] {customSlopeArray[responseIndex]}
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			if (0 == responseIndex) {
				leftSegmentPredictorResponseDerivativeArray[responseIndex] =
					segmentPredictorResponseDerivative;
			} else if (responseCount - 1 == responseIndex) {
				rightSegmentPredictorResponseDerivativeArray[responseIndex - 1] =
					segmentPredictorResponseDerivative;
			} else {
				leftSegmentPredictorResponseDerivativeArray[responseIndex] =
					segmentPredictorResponseDerivative;
				rightSegmentPredictorResponseDerivativeArray[responseIndex - 1] =
					segmentPredictorResponseDerivative;
			}
		}

		return multiSegmentSequence.setupHermite (
			leftSegmentPredictorResponseDerivativeArray,
			rightSegmentPredictorResponseDerivativeArray,
			null,
			stretchBestFitResponse,
			setupMode
		) ? multiSegmentSequence : null;
	}

	/**
	 * Create a Stretch off of Hermite Splines from the specified the Predictor Ordinates, the Response
	 *  Values, the Custom Slopes, and the Segment Builder Parameters.
	 * 
	 * @param name Stretch Name
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param responseValueArray Array of Response Values
	 * @param customSlopeArray Array of Custom Slopes
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Parameters
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param setupMode Calibration Set up Mode NATURAL | FINANCIAL | FLOATING | NOTAKNOT
	 * 
	 * @return The Instance of the Hermite Spline Stretch
	 */

	public static final MultiSegmentSequence CustomSlopeHermiteSpline (
		final String name,
		final int[] predictorOrdinateArray,
		final double[] responseValueArray,
		final double[] customSlopeArray,
		final SegmentCustomBuilderControl[] segmentCustomBuilderControlArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final int setupMode)
	{
		if (null == predictorOrdinateArray) {
			return null;
		}

		int predictorCount = predictorOrdinateArray.length;
		double[] clonedPredictorOrdinateArray = new double[predictorCount];

		if (0 == predictorCount) {
			return null;
		}

		for (int predictorIndex = 0; predictorIndex < predictorCount; ++predictorIndex) {
			clonedPredictorOrdinateArray[predictorIndex] = predictorOrdinateArray[predictorIndex];
		}

		return CustomSlopeHermiteSpline (
			name,
			clonedPredictorOrdinateArray,
			responseValueArray,
			customSlopeArray,
			segmentCustomBuilderControlArray,
			stretchBestFitResponse,
			setupMode
		);
	}

	/**
	 * Create Hermite/Bessel C<sup>1</sup> Cubic Spline Stretch
	 * 
	 * @param name Stretch Name
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param responseValueArray Array of Response Values
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Parameters
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param setupMode Segment Setup Mode
	 * @param eliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param applyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return Hermite/Bessel C<sup>1</sup> Cubic Spline Stretch
	 */

	public static final MultiSegmentSequence CreateBesselCubicSplineStretch (
		final String name,
		final double[] predictorOrdinateArray,
		final double[] responseValueArray,
		final SegmentCustomBuilderControl[] segmentCustomBuilderControlArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final int setupMode,
		final boolean eliminateSpuriousExtrema,
		final boolean applyMonotoneFilter)
	{
		LocalMonotoneCkGenerator localMonotoneCkGenerator = LocalMonotoneCkGenerator.Create (
			predictorOrdinateArray,
			responseValueArray,
			LocalMonotoneCkGenerator.C1_BESSEL,
			eliminateSpuriousExtrema,
			applyMonotoneFilter
		);

		return null == localMonotoneCkGenerator ? null : CustomSlopeHermiteSpline (
			name,
			predictorOrdinateArray,
			responseValueArray,
			localMonotoneCkGenerator.C1(),
			segmentCustomBuilderControlArray,
			stretchBestFitResponse,
			setupMode
		);
	}

	/**
	 * Create Hyman (1983) Monotone Preserving Stretch. The reference is:
	 * 
	 * 	Hyman (1983) Accurate Monotonicity Preserving Cubic Interpolation -
	 *  	SIAM J on Numerical Analysis 4 (4), 645-654.
	 * 
	 * @param name Stretch Name
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param responseValueArray Array of Response Values
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Parameters
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param setupMode Segment Setup Mode
	 * @param eliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param applyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return Hyman (1983) Monotone Preserving Stretch
	 */

	public static final MultiSegmentSequence CreateHyman83MonotoneStretch (
		final String name,
		final double[] predictorOrdinateArray,
		final double[] responseValueArray,
		final SegmentCustomBuilderControl[] segmentCustomBuilderControlArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final int setupMode,
		final boolean eliminateSpuriousExtrema,
		final boolean applyMonotoneFilter)
	{
		LocalMonotoneCkGenerator localMonotoneCkGenerator = LocalMonotoneCkGenerator.Create (
			predictorOrdinateArray,
			responseValueArray,
			LocalMonotoneCkGenerator.C1_HYMAN83,
			eliminateSpuriousExtrema,
			applyMonotoneFilter
		);

		return null == localMonotoneCkGenerator ? null : CustomSlopeHermiteSpline (
			name,
			predictorOrdinateArray,
			responseValueArray,
			localMonotoneCkGenerator.C1(),
			segmentCustomBuilderControlArray,
			stretchBestFitResponse,
			setupMode
		);
	}

	/**
	 * Create Hyman (1989) enhancement to the Hyman (1983) Monotone Preserving Stretch. The reference is:
	 * 
	 * 	Doherty, Edelman, and Hyman (1989) Non-negative, monotonic, or convexity preserving cubic and quintic
	 *  	Hermite interpolation - Mathematics of Computation 52 (186), 471-494.
	 * 
	 * @param name Stretch Name
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param responseValueArray Array of Response Values
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Parameters
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param setupMode Segment Setup Mode
	 * @param eliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param applyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return Hyman (1989) Monotone Preserving Stretch
	 */

	public static final MultiSegmentSequence CreateHyman89MonotoneStretch (
		final String name,
		final double[] predictorOrdinateArray,
		final double[] responseValueArray,
		final SegmentCustomBuilderControl[] segmentCustomBuilderControlArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final int setupMode,
		final boolean eliminateSpuriousExtrema,
		final boolean applyMonotoneFilter)
	{
		LocalMonotoneCkGenerator localMonotoneCkGenerator = LocalMonotoneCkGenerator.Create (
			predictorOrdinateArray,
			responseValueArray,
			LocalMonotoneCkGenerator.C1_HYMAN89,
			eliminateSpuriousExtrema,
			applyMonotoneFilter
		);

		return null == localMonotoneCkGenerator ? null : CustomSlopeHermiteSpline (
			name,
			predictorOrdinateArray,
			responseValueArray,
			localMonotoneCkGenerator.C1(),
			segmentCustomBuilderControlArray,
			stretchBestFitResponse,
			setupMode
		);
	}

	/**
	 * Create the Harmonic Monotone Preserving Stretch. The reference is:
	 * 
	 * 	Fritcsh and Butland (1984) A Method for constructing local monotonic piece-wise cubic interpolants -
	 *  	SIAM J on Scientific and Statistical Computing 5, 300-304.
	 * 
	 * @param name Stretch Name
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param responseValueArray Array of Response Values
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Parameters
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param setupMode Segment Setup Mode
	 * @param eliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param applyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return Harmonic Monotone Preserving Stretch
	 */

	public static final MultiSegmentSequence CreateHarmonicMonotoneStretch (
		final String name,
		final double[] predictorOrdinateArray,
		final double[] responseValueArray,
		final SegmentCustomBuilderControl[] segmentCustomBuilderControlArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final int setupMode,
		final boolean eliminateSpuriousExtrema,
		final boolean applyMonotoneFilter)
	{
		LocalMonotoneCkGenerator localMonotoneCkGenerator = LocalMonotoneCkGenerator.Create (
			predictorOrdinateArray,
			responseValueArray,
			LocalMonotoneCkGenerator.C1_HARMONIC,
			eliminateSpuriousExtrema,
			applyMonotoneFilter
		);

		return null == localMonotoneCkGenerator ? null : CustomSlopeHermiteSpline (
			name,
			predictorOrdinateArray,
			responseValueArray,
			localMonotoneCkGenerator.C1(),
			segmentCustomBuilderControlArray,
			stretchBestFitResponse,
			setupMode
		);
	}

	/**
	 * Create the Van Leer Limiter Stretch. The reference is:
	 * 
	 * 	Van Leer (1974) Towards the Ultimate Conservative Difference Scheme. II - Monotonicity and
	 * 		Conservation combined in a Second-Order Scheme, Journal of Computational Physics 14 (4), 361-370.
	 * 
	 * @param name Stretch Name
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param responseValueArray Array of Response Values
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Parameters
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param setupMode Segment Setup Mode
	 * @param eliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param applyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return The Van Leer Limiter Stretch
	 */

	public static final MultiSegmentSequence CreateVanLeerLimiterStretch (
		final String name,
		final double[] predictorOrdinateArray,
		final double[] responseValueArray,
		final SegmentCustomBuilderControl[] segmentCustomBuilderControlArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final int setupMode,
		final boolean eliminateSpuriousExtrema,
		final boolean applyMonotoneFilter)
	{
		LocalMonotoneCkGenerator localMonotoneCkGenerator = LocalMonotoneCkGenerator.Create (
			predictorOrdinateArray,
			responseValueArray,
			LocalMonotoneCkGenerator.C1_VAN_LEER,
			eliminateSpuriousExtrema,
			applyMonotoneFilter
		);

		return null == localMonotoneCkGenerator ? null : CustomSlopeHermiteSpline (
			name,
			predictorOrdinateArray,
			responseValueArray,
			localMonotoneCkGenerator.C1(),
			segmentCustomBuilderControlArray,
			stretchBestFitResponse,
			setupMode
		);
	}

	/**
	 * Create the Kruger Stretch. The reference is:
	 * 
	 * 	Kruger (2002) Constrained Cubic Spline Interpolations for Chemical Engineering Application,
	 *  	http://www.korf.co.uk/spline.pdf
	 * 
	 * @param name Stretch Name
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param responseValueArray Array of Response Values
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Parameters
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param setupMode Segment Setup Mode
	 * @param eliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param applyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return The Kruger Stretch
	 */

	public static final MultiSegmentSequence CreateKrugerStretch (
		final String name,
		final double[] predictorOrdinateArray,
		final double[] responseValueArray,
		final SegmentCustomBuilderControl[] segmentCustomBuilderControlArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final int setupMode,
		final boolean eliminateSpuriousExtrema,
		final boolean applyMonotoneFilter)
	{
		LocalMonotoneCkGenerator localMonotoneCkGenerator = LocalMonotoneCkGenerator.Create (
			predictorOrdinateArray,
			responseValueArray,
			LocalMonotoneCkGenerator.C1_KRUGER,
			eliminateSpuriousExtrema,
			applyMonotoneFilter
		);

		return null == localMonotoneCkGenerator ? null : CustomSlopeHermiteSpline (
			name,
			predictorOrdinateArray,
			responseValueArray,
			localMonotoneCkGenerator.C1(),
			segmentCustomBuilderControlArray,
			stretchBestFitResponse,
			setupMode
		);
	}

	/**
	 * Create the Huynh Le Floch Limiter Stretch. The reference is:
	 * 
	 * 	Huynh (1993) Accurate Monotone Cubic Interpolation, SIAM J on Numerical Analysis 30 (1), 57-100.
	 * 
	 * @param name Stretch Name
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param responseValueArray Array of Response Values
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Parameters
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param setupMode Segment Setup Mode
	 * @param eliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param applyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return The Huynh Le Floch Limiter Stretch
	 */

	public static final MultiSegmentSequence CreateHuynhLeFlochLimiterStretch (
		final String name,
		final double[] predictorOrdinateArray,
		final double[] responseValueArray,
		final SegmentCustomBuilderControl[] segmentCustomBuilderControlArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final int setupMode,
		final boolean eliminateSpuriousExtrema,
		final boolean applyMonotoneFilter)
	{
		LocalMonotoneCkGenerator localMonotoneCkGenerator = LocalMonotoneCkGenerator.Create (
			predictorOrdinateArray,
			responseValueArray,
			LocalMonotoneCkGenerator.C1_HUYNH_LE_FLOCH,
			eliminateSpuriousExtrema,
			applyMonotoneFilter
		);

		return null == localMonotoneCkGenerator ? null : CustomSlopeHermiteSpline (
			name,
			predictorOrdinateArray,
			responseValueArray,
			localMonotoneCkGenerator.C1(),
			segmentCustomBuilderControlArray,
			stretchBestFitResponse,
			setupMode
		);
	}

	/**
	 * Generate the local control C<sup>1</sup> Slope using the Akima Cubic Algorithm. The reference is:
	 * 
	 * 	Akima (1970): A New Method of Interpolation and Smooth Curve Fitting based on Local Procedures,
	 * 		Journal of the Association for the Computing Machinery 17 (4), 589-602.
	 * 
	 * @param name Stretch Name
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param responseValueArray Array of Response Values
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Parameters
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param setupMode Segment Setup Mode
	 * @param eliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param applyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return The Akima Local Control Stretch Instance
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CreateAkimaStretch (
		final String name,
		final double[] predictorOrdinateArray,
		final double[] responseValueArray,
		final SegmentCustomBuilderControl[] segmentCustomBuilderControlArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final int setupMode,
		final boolean eliminateSpuriousExtrema,
		final boolean applyMonotoneFilter)
	{
		LocalMonotoneCkGenerator localMonotoneCkGenerator = LocalMonotoneCkGenerator.Create (
			predictorOrdinateArray,
			responseValueArray,
			LocalMonotoneCkGenerator.C1_AKIMA,
			eliminateSpuriousExtrema,
			applyMonotoneFilter
		);

		return null == localMonotoneCkGenerator ? null : CustomSlopeHermiteSpline (
			name,
			predictorOrdinateArray,
			responseValueArray,
			localMonotoneCkGenerator.C1(),
			segmentCustomBuilderControlArray,
			stretchBestFitResponse,
			setupMode
		);
	}

	/**
	 * Generate the local control C<sup>1</sup> Slope using the Hagan-West Monotone Convex Algorithm. The
	 * 	references are:
	 * 
	 * 	Hagan, P., and G. West (2006): Interpolation Methods for Curve Construction, Applied Mathematical
	 * 	 Finance 13 (2): 89-129.
	 * 
	 * 	Hagan, P., and G. West (2008): Methods for Curve a Yield Curve, Wilmott Magazine: 70-81.
	 * 
	 * @param name Stretch Name
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param observationArray Array of Observations
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Parameters
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param setupMode Segment Setup Mode
	 * @param linearNodeInference Apply Linear Node Inference from Observations
	 * @param eliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param applyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @return The Monotone-Convex Local Control Stretch Instance
	 */

	public static final MultiSegmentSequence CreateMonotoneConvexStretch (
		final String name,
		final double[] predictorOrdinateArray,
		final double[] observationArray,
		final SegmentCustomBuilderControl[] segmentCustomBuilderControlArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final int setupMode,
		final boolean linearNodeInference,
		final boolean eliminateSpuriousExtrema,
		final boolean applyMonotoneFilter)
	{
		MonotoneConvexHaganWest monotoneConvexHaganWest = MonotoneConvexHaganWest.Create (
			predictorOrdinateArray,
			observationArray,
			linearNodeInference
		);

		if (null == monotoneConvexHaganWest) {
			return null;
		}

		LocalMonotoneCkGenerator localMonotoneCkGenerator = LocalMonotoneCkGenerator.Create (
			monotoneConvexHaganWest.predictorOrdinates(),
			monotoneConvexHaganWest.responseValues(),
			LocalMonotoneCkGenerator.C1_MONOTONE_CONVEX,
			eliminateSpuriousExtrema,
			applyMonotoneFilter
		);

		return null == localMonotoneCkGenerator ? null : CustomSlopeHermiteSpline (
			name,
			monotoneConvexHaganWest.predictorOrdinates(),
			monotoneConvexHaganWest.responseValues(),
			localMonotoneCkGenerator.C1(),
			segmentCustomBuilderControlArray,
			stretchBestFitResponse,
			setupMode
		);
	}
}
