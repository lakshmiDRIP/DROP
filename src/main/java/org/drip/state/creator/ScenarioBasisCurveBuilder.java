
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
import org.drip.state.basis.BasisCurve;
import org.drip.state.curve.BasisSplineBasisCurve;
import org.drip.state.identifier.ForwardLabel;

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
 * <i>ScenarioBasisCurveBuilder</i> implements the construction of the scenario basis curve using the input
 * instruments and their quotes. It exposes the following
 *  functions:
 *
 *  <ul>
 * 		<li>Create an Instance of the Custom Splined Basis Curve</li>
 * 		<li>Create an Instance of the Cubic Polynomial Splined Basis Curve</li>
 * 		<li>Create an Instance of the Quartic Polynomial Splined Basis Curve</li>
 * 		<li>Create an Instance of the Kaklis-Pandelis Splined Basis Curve</li>
 * 		<li>Create an Instance of the KLK Hyperbolic Splined Basis Curve</li>
 * 		<li>Create an Instance of the KLK Rational Linear Splined Basis Curve</li>
 * 		<li>Create an Instance of the KLK Rational Quadratic Splined Basis Curve</li>
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

public class ScenarioBasisCurveBuilder
{

	/**
	 * Create an Instance of the Custom Splined Basis Curve
	 * 
	 * @param name Curve Name
	 * @param spotDate The Spot Date
	 * @param referenceForwardLabel Reference Leg FRI
	 * @param derivedForwardLabel Derived Leg FRI
	 * @param basisOnReference TRUE - The Basis Quote is on the Reference Leg
	 * @param tenorArray Array of the Tenors
	 * @param basisArray Array of the Basis Spreads
	 * @param segmentCustomBuilderControl The Segment Custom Builder Control
	 * 
	 * @return The Instance of the Basis Curve
	 */

	public static final BasisCurve CustomSplineBasisCurve (
		final String name,
		final JulianDate spotDate,
		final ForwardLabel referenceForwardLabel,
		final ForwardLabel derivedForwardLabel,
		final boolean basisOnReference,
		final String[] tenorArray,
		final double[] basisArray,
		final SegmentCustomBuilderControl segmentCustomBuilderControl)
	{
		if (null == spotDate || null == tenorArray || null == basisArray) {
			return null;
		}

		int tenorCount = tenorArray.length;
		int[] basisPredictorOrdinateArray = new int[tenorCount + 1];
		double[] basisResponseValueArray = new double[tenorCount + 1];
		SegmentCustomBuilderControl[] segmentCustomBuilderControlArray =
			new SegmentCustomBuilderControl[tenorCount];

		if (0 == tenorCount || tenorCount != basisArray.length) {
			return null;
		}

		for (int tenorIndex = 0; tenorIndex <= tenorCount; ++tenorIndex) {
			if (0 != tenorIndex) {
				String tenor = tenorArray[tenorIndex - 1];

				if (null == tenor || tenor.isEmpty()) {
					return null;
				}

				JulianDate maturityDate = spotDate.addTenor (tenor);

				if (null == maturityDate) {
					return null;
				}

				basisPredictorOrdinateArray[tenorIndex] = maturityDate.julian();
			} else {
				basisPredictorOrdinateArray[tenorIndex] = spotDate.julian();
			}

			basisResponseValueArray[tenorIndex] = 0 == tenorIndex ?
				basisArray[0] : basisArray[tenorIndex - 1];

			if (0 != tenorIndex) {
				segmentCustomBuilderControlArray[tenorIndex - 1] = segmentCustomBuilderControl;
			}
		}

		try {
			return new BasisSplineBasisCurve (
				referenceForwardLabel,
				derivedForwardLabel,
				basisOnReference,
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
	 * Create an Instance of the Cubic Polynomial Splined Basis Curve
	 * 
	 * @param name Curve Name
	 * @param spotDate The Spot Date
	 * @param referenceForwardLabel Reference Leg FRI
	 * @param derivedForwardLabel Derived Leg FRI
	 * @param basisOnReference TRUE - The Basis Quote is on the Reference Leg
	 * @param tenorArray Array of the Tenors
	 * @param basisArray Array of the Basis Spreads
	 * 
	 * @return The Instance of the Basis Curve
	 */

	public static final BasisCurve CubicPolynomialBasisCurve (
		final String name,
		final JulianDate spotDate,
		final ForwardLabel referenceForwardLabel,
		final ForwardLabel derivedForwardLabel,
		final boolean basisOnReference,
		final String[] tenorArray,
		final double[] basisArray)
	{
		try {
			return CustomSplineBasisCurve (
				name,
				spotDate,
				referenceForwardLabel,
				derivedForwardLabel,
				basisOnReference,
				tenorArray,
				basisArray,
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
	 * Create an Instance of the Quartic Polynomial Splined Basis Curve
	 * 
	 * @param name Curve Name
	 * @param spotDate The Spot Date
	 * @param referenceForwardLabel Reference Leg FRI
	 * @param derivedForwardLabel Derived Leg FRI
	 * @param basisOnReference TRUE - The Basis Quote is on the Reference Leg
	 * @param tenorArray Array of the Tenors
	 * @param basisArray Array of the Basis Spreads
	 * 
	 * @return The Instance of the Basis Curve
	 */

	public static final BasisCurve QuarticPolynomialBasisCurve (
		final String name,
		final JulianDate spotDate,
		final ForwardLabel referenceForwardLabel,
		final ForwardLabel derivedForwardLabel,
		final boolean basisOnReference,
		final String[] tenorArray,
		final double[] basisArray)
	{
		try {
			return CustomSplineBasisCurve (
				name,
				spotDate,
				referenceForwardLabel,
				derivedForwardLabel,
				basisOnReference,
				tenorArray,
				basisArray,
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
	 * Create an Instance of the Kaklis-Pandelis Splined Basis Curve
	 * 
	 * @param name Curve Name
	 * @param spotDate The Spot Date
	 * @param referenceForwardLabel Reference Leg FRI
	 * @param derivedForwardLabel Derived Leg FRI
	 * @param basisOnReference TRUE - The Basis Quote is on the Reference Leg
	 * @param tenorArray Array of the Tenors
	 * @param basisArray Array of the Basis Spreads
	 * 
	 * @return The Instance of the Basis Curve
	 */

	public static final BasisCurve KaklisPandelisBasisCurve (
		final String name,
		final JulianDate spotDate,
		final ForwardLabel referenceForwardLabel,
		final ForwardLabel derivedForwardLabel,
		final boolean basisOnReference,
		final String[] tenorArray,
		final double[] basisArray)
	{
		try {
			return CustomSplineBasisCurve (
				name,
				spotDate,
				referenceForwardLabel,
				derivedForwardLabel,
				basisOnReference,
				tenorArray,
				basisArray,
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
	 * Create an Instance of the KLK Hyperbolic Splined Basis Curve
	 * 
	 * @param name Curve Name
	 * @param spotDate The Spot Date
	 * @param referenceForwardLabel Reference Leg FRI
	 * @param derivedForwardLabel Derived Leg FRI
	 * @param basisOnReference TRUE - The Basis Quote is on the Reference Leg
	 * @param tenorArray Array of the Tenors
	 * @param basisArray Array of the Basis Spreads
	 * @param tension The Tension Parameter
	 * 
	 * @return The Instance of the Basis Curve
	 */

	public static final BasisCurve KLKHyperbolicBasisCurve (
		final String name,
		final JulianDate spotDate,
		final ForwardLabel referenceForwardLabel,
		final ForwardLabel derivedForwardLabel,
		final boolean basisOnReference,
		final String[] tenorArray,
		final double[] basisArray,
		final double tension)
	{
		try {
			return CustomSplineBasisCurve (
				name,
				spotDate,
				referenceForwardLabel,
				derivedForwardLabel,
				basisOnReference,
				tenorArray,
				basisArray,
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
	 * Create an Instance of the KLK Rational Linear Splined Basis Curve
	 * 
	 * @param name Curve Name
	 * @param spotDate The Spot Date
	 * @param referenceForwardLabel Reference Leg FRI
	 * @param derivedForwardLabel Derived Leg FRI
	 * @param basisOnReference TRUE - The Basis Quote is on the Reference Leg
	 * @param tenorArray Array of the Tenors
	 * @param basisArray Array of the Basis Spreads
	 * @param tension The Tension Parameter
	 * 
	 * @return The Instance of the Basis Curve
	 */

	public static final org.drip.state.basis.BasisCurve KLKRationalLinearBasisCurve (
		final String name,
		final JulianDate spotDate,
		final ForwardLabel referenceForwardLabel,
		final ForwardLabel derivedForwardLabel,
		final boolean basisOnReference,
		final String[] tenorArray,
		final double[] basisArray,
		final double tension)
	{
		try {
			return CustomSplineBasisCurve (
				name,
				spotDate,
				referenceForwardLabel,
				derivedForwardLabel,
				basisOnReference,
				tenorArray,
				basisArray,
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
	 * Create an Instance of the KLK Rational Quadratic Splined Basis Curve
	 * 
	 * @param name Curve Name
	 * @param spotDate The Spot Date
	 * @param referenceForwardLabel Reference Leg FRI
	 * @param derivedForwardLabel Derived Leg FRI
	 * @param basisOnReference TRUE - The Basis Quote is on the Reference Leg
	 * @param tenorArray Array of the Tenors
	 * @param basisArray Array of the Basis Spreads
	 * @param tension The Tension Parameter
	 * 
	 * @return The Instance of the Basis Curve
	 */

	public static final org.drip.state.basis.BasisCurve KLKRationalQuadraticBasisCurve (
		final String name,
		final JulianDate spotDate,
		final ForwardLabel referenceForwardLabel,
		final ForwardLabel derivedForwardLabel,
		final boolean basisOnReference,
		final String[] tenorArray,
		final double[] basisArray,
		final double tension)
	{
		try {
			return CustomSplineBasisCurve (
				name,
				spotDate,
				referenceForwardLabel,
				derivedForwardLabel,
				basisOnReference,
				tenorArray,
				basisArray,
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
}
