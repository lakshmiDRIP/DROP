
package org.drip.sample.spline;

import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.spline.bspline.*;

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
 * <i>BasisMonicBSpline</i> implements Samples for the Construction and the usage of various monic basis B
 * 	Splines. It demonstrates the following:
 * 	- Construction of segment B Spline Hat Basis Functions.
 * 	- Estimation of the derivatives and the basis envelope cumulative integrands.
 * 	- Estimation of the normalizer and the basis envelope cumulative normalized integrands.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/spline/README.md">Basis Monic Multic Tension Spline</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BasisMonicBSpline {

	/*
	 * This sample illustrates the construction and the usage of the monic basis B Splines. It shows the
	 * 	following:
	 * 	- Construct the segment basis monic function from the specified hat type, the shape controller, the
	 * 		derivative order, and the tension.
	 * 	- Compare the responses emitted by the basis hat functions and the monic basis functions.
	 * 	- Compute the normalized cumulative emitted by the monic basis functions.
	 * 	- Compute the ordered derivative emitted by the monic basis functions.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void TestMonicHatBasis (
		final String strHatType,
		final String strShapeController,
		final TensionBasisHat[] aTBH,
		final double[] adblPredictorOrdinate,
		final String strTest)
		throws Exception
	{
		/*
		 * Construct the segment basis monic function from the specified hat type, the shape controller, the
		 *  derivative order, and the tension.
		 */

		SegmentBasisFunction me = SegmentBasisFunctionGenerator.Monic (
			strHatType,
			strShapeController,
			adblPredictorOrdinate,
			2,
			aTBH[0].tension()
		);

		/*
		 * Compare the responses emitted by the basis hat functions and the monic basis functions.
		 */

		double dblX = 1.0;
		double dblXIncrement = 0.25;

		System.out.println ("\n\t-------------------------------------------------");

		System.out.println ("\t--------------" + strTest + "-------------");

		System.out.println ("\t-------------------------------------------------\n");

		System.out.println ("\t-------------X---|---LEFT---|---RIGHT--|--MONIC--\n");

		while (dblX <= 3.0) {
			System.out.println (
				"\tResponse[" + FormatUtil.FormatDouble (dblX, 1, 3, 1.) + "] : " +
				FormatUtil.FormatDouble (aTBH[0].evaluate (dblX), 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (aTBH[1].evaluate (dblX), 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (me.evaluate (dblX), 1, 5, 1.));

			dblX += dblXIncrement;
		}

		System.out.println ("\n\t------------------------------------------------\n");

		/*
		 * Compute the normalized cumulative emitted by the monic basis functions.
		 */

		dblX = 1.0;

		while (dblX <= 3.0) {
			System.out.println (
				"\t\tNormCumulative[" + FormatUtil.FormatDouble (dblX, 1, 3, 1.) + "] : " +
				FormatUtil.FormatDouble (me.normalizedCumulative (dblX), 1, 5, 1.)
			);

			dblX += dblXIncrement;
		}

		System.out.println ("\n\t------------------------------------------------\n");

		/*
		 * Compute the ordered derivative emitted by the monic basis functions.
		 */

		dblX = 1.0;
		int iOrder = 1;

		while (dblX <= 3.0) {
			System.out.println (
				"\t\t\tDeriv[" + FormatUtil.FormatDouble (dblX, 1, 3, 1.) + "] : " +
				FormatUtil.FormatDouble (me.derivative (dblX, iOrder), 1, 5, 1.)
			);

			dblX += dblXIncrement;
		}

		System.out.println ("\n\t-----------------------------------------------\n");
	}

	/*
	 * This sample illustrates the construction and usage of raw/processed basis tension splines, and their
	 * 	comparisons with the correspondingly constructed monic hat basis functions. It shows the following:
	 * 	- Construct the Processed Hyperbolic Tension Hat Pair from the co-ordinate arrays, the Ck, and the
	 * 		tension.
	 * 	- Implement and test the basis monic spline function using the constructed Processed Hyperbolic
	 * 		Tension Hat Pair and the Rational Linear Shape Controller.
	 * 	- Construct the Raw Hyperbolic Tension Hat Pair from the co-ordinate arrays and the tension.
	 * 	- Implement and test the basis monic spline function using the constructed Raw Hyperbolic Tension Hat
	 * 		Pair and the Rational Linear Shape Controller.
	 * 	- Construct the Processed Cubic Rational Tension Hat Pair from the co-ordinate arrays, Linear
	 * 		Rational Shape Controller, and no tension.
	 * 	- Implement and test the basis monic spline function using the constructed Flat Processed Cubic
	 * 		Tension Hat Pair and the Rational Linear Shape Controller.
	 * 	- Construct the Processed Cubic Rational Tension Hat Pair from the co-ordinate arrays, Linear
	 * 		Rational Shape Controller, and non-zero tension.
	 * 	- Implement and test the basis monic spline function using the constructed Processed Cubic Rational
	 * 		Tension Hat Pair and the Rational Linear Shape Controller.
	 * 	- Construct the Processed Cubic Rational Tension Hat Pair from the co-ordinate arrays, Quadratic
	 * 		Rational Shape Controller, and the tension.
	 * 	- Implement and test the basis monic spline function using the constructed Processed Cubic Rational
	 * 		Tension Hat Pair and the Quadratic Linear Shape Controller.
	 * 	- Construct the Processed Cubic Rational Tension Hat Pair from the co-ordinate arrays, Exponential
	 * 		Rational Shape Controller, and the tension.
	 * 	- Implement and test the basis monic spline function using the constructed Processed Cubic Rational
	 * 		Tension Hat Pair and the Rational Exponential Shape Controller.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void BasisMonicBSplineSample()
		throws Exception
	{
		double[] adblPredictorOrdinate = new double[] {1., 2., 3.};

		/*
		 * Construct the Processed Hyperbolic Tension Hat Pair from the co-ordinate arrays, the Ck, and the
		 *  tension.
		 */

		TensionBasisHat[] aTBHProcessed = BasisHatPairGenerator.ProcessedHyperbolicTensionHatPair (
			adblPredictorOrdinate[0],
			adblPredictorOrdinate[1],
			adblPredictorOrdinate[2],
			2,
			1.
		);

		/*
		 * Implement and test the basis monic spline function using the constructed Processed Hyperbolic
		 * 	Tension Hat Pair and the Rational Linear Shape Controller.
		 */

		TestMonicHatBasis (
			BasisHatPairGenerator.PROCESSED_TENSION_HYPERBOLIC,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			aTBHProcessed,
			adblPredictorOrdinate,
			" PROCESSED HYPERBOLIC "
		);

		/*
		 * Construct the Raw Hyperbolic Tension Hat Pair from the co-ordinate arrays and the tension.
		 */

		TensionBasisHat[] aTBHStraight = BasisHatPairGenerator.HyperbolicTensionHatPair (
			adblPredictorOrdinate[0],
			adblPredictorOrdinate[1],
			adblPredictorOrdinate[2],
			1.
		);

		/*
		 * Implement and test the basis monic spline function using the constructed Raw Hyperbolic Tension
		 * 	Hat Pair and the Rational Linear Shape Controller.
		 */

		TestMonicHatBasis (
			BasisHatPairGenerator.RAW_TENSION_HYPERBOLIC,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			aTBHStraight,
			adblPredictorOrdinate,
			" STRAIGHT  HYPERBOLIC "
		);

		/*
		 * Construct the Processed Cubic Rational Tension Hat Pair from the co-ordinate arrays, Linear
		 * 	Rational Shape Controller, and no tension.
		 */

		TensionBasisHat[] aTBHCubicRationalPlain = BasisHatPairGenerator.ProcessedCubicRationalHatPair (
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			adblPredictorOrdinate[0],
			adblPredictorOrdinate[1],
			adblPredictorOrdinate[2],
			2,
			0.
		);

		/*
		 * Implement and test the basis monic spline function using the constructed Flat Processed Cubic
		 * 	Tension Hat Pair and the Rational Linear Shape Controller.
		 */

		TestMonicHatBasis (
			BasisHatPairGenerator.PROCESSED_CUBIC_RATIONAL,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			aTBHCubicRationalPlain,
			adblPredictorOrdinate,
			"     CUBIC     FLAT   "
		);

		/*
		 * Construct the Processed Cubic Rational Tension Hat Pair from the co-ordinate arrays, Linear
		 * 	Rational Shape Controller, and non-zero tension.
		 */

		TensionBasisHat[] aTBHCubicRationalLinear = BasisHatPairGenerator.ProcessedCubicRationalHatPair (
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			adblPredictorOrdinate[0],
			adblPredictorOrdinate[1],
			adblPredictorOrdinate[2],
			2,
			1.
		);

		/*
		 * Implement and test the basis monic spline function using the constructed Processed Cubic Rational
		 * 	Tension Hat Pair and the Rational Linear Shape Controller.
		 */

		TestMonicHatBasis (
			BasisHatPairGenerator.PROCESSED_CUBIC_RATIONAL,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			aTBHCubicRationalLinear,
			adblPredictorOrdinate,
			" CUBIC LINEAR RATIONAL "
		);

		/*
		 * Construct the Processed Cubic Rational Tension Hat Pair from the co-ordinate arrays, Quadratic
		 * 	Rational Shape Controller, and the tension.
		 */

		TensionBasisHat[] aTBHCubicRationalQuadratic = BasisHatPairGenerator.ProcessedCubicRationalHatPair (
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_QUADRATIC,
			adblPredictorOrdinate[0],
			adblPredictorOrdinate[1],
			adblPredictorOrdinate[2],
			2,
			1.
		);

		/*
		 * Implement and test the basis monic spline function using the constructed Processed Cubic Rational
		 * 	Tension Hat Pair and the Quadratic Linear Shape Controller.
		 */

		TestMonicHatBasis (
			BasisHatPairGenerator.PROCESSED_CUBIC_RATIONAL,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_QUADRATIC,
			aTBHCubicRationalQuadratic,
			adblPredictorOrdinate,
			" CUBIC  QUAD  RATIONAL "
		);

		/*
		 * Construct the Processed Cubic Rational Tension Hat Pair from the co-ordinate arrays, Exponential
		 * 	Rational Shape Controller, and the tension.
		 */

		TensionBasisHat[] aTBHCubicRationalExponential = BasisHatPairGenerator.ProcessedCubicRationalHatPair (
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_EXPONENTIAL,
			adblPredictorOrdinate[0],
			adblPredictorOrdinate[1],
			adblPredictorOrdinate[2],
			2,
			1.
		);

		/*
		 * Implement and test the basis monic spline function using the constructed Processed Cubic Rational
		 * 	Tension Hat Pair and the Rational Exponential Shape Controller.
		 */

		TestMonicHatBasis (
			BasisHatPairGenerator.PROCESSED_CUBIC_RATIONAL,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_EXPONENTIAL,
			aTBHCubicRationalExponential,
			adblPredictorOrdinate,
			" CUBIC  EXP  RATIONAL "
		);
	}

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		BasisMonicBSplineSample();

		EnvManager.TerminateEnv();
	}
}
