
package org.drip.sample.stretch;

import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.*;
import org.drip.spline.params.*;
import org.drip.spline.stretch.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>KnottedRegressionSplineEstimator</i> shows the sample construction and usage of Knot-based Regression
 * 	Splines. It demonstrates construction of the segment's predictor ordinate/response value combination, and
 * 	eventual calibration.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/stretch/README.md">Knot Insertion Curvature Roughness Penalty</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class KnottedRegressionSplineEstimator {

	/*
	 * Build Polynomial Segment Control Parameters
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final SegmentCustomBuilderControl PolynomialSegmentControlParams (
		final int iNumBasis,
		final SegmentInelasticDesignControl sdic)
		throws Exception
	{
		return new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
			new PolynomialFunctionSetParams (iNumBasis),
			sdic,
			null,
			null
		);
	}

	/*
	 * Basis Spline Stretch Test Sample. Performs the following:
	 * 	- Construct the Array of Segment Builder Parameters - one per segment.
	 *  - Construct a Stretch instance using the predictor ordinate array and the Segment Best Fit Response Values.
	 *  - Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian
	 *  - Compute the Segment Curvature Penalty Estimate.
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final void BasisSplineStretchTest (
		final double[] adblX,
		final SegmentCustomBuilderControl scbc,
		final StretchBestFitResponse sbfr)
		throws Exception
	{
		double dblX = 1.;
		double dblXMax = 10.;

		/*
		 * Array of Segment Builder Parameters - one per segment
		 */

		SegmentCustomBuilderControl[] aSCBC = new SegmentCustomBuilderControl[adblX.length - 1]; 

		for (int i = 0; i < adblX.length - 1; ++i)
			aSCBC[i] = scbc;

		/*
		 * Construct a Stretch instance using the predictor ordinate array and the Segment Best Fit Response Values
		 */

		MultiSegmentSequence mss = MultiSegmentSequenceBuilder.CreateRegressionSplineEstimator (
			"SPLINE_STRETCH",
			adblX, // predictors
			aSCBC, // Basis Segment Builder parameters
			sbfr,
			BoundarySettings.NaturalStandard(), // Boundary Condition - Natural
			MultiSegmentSequence.CALIBRATE // Calibrate the Stretch predictors to the responses
		);

		/*
		 * Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian
		 */

		while (dblX <= dblXMax) {
			System.out.println ("Y[" + dblX + "] " + FormatUtil.FormatDouble (mss.responseValue (dblX), 1, 2, 1.) + " | " +
				mss.monotoneType (dblX));

			System.out.println ("\t\tJacobian Y[" + dblX + "]=" + mss.jackDResponseDCalibrationInput (dblX, 1).displayString());

			dblX += 1.;
		}

		/*
		 * Compute the Segment Curvature Penalty Estimate
		 */

		System.out.println ("\tSPLINE_STRETCH DPE: " + mss.curvatureDPE());
	}

	/*
	 * Bring together to compose the Regression Spline Estimator Test. It is made up of the following steps:
	 * 	- Set the Predictor Ordinate Knot Points.
	 * 	- Construct a set of Predictor Ordinates, their Responses, and corresponding Weights to serve as
	 * 		weighted closeness of fit.
	 * 	- Construct the segment inelastic parameter that is C2 (iK = 2 sets it to C2), with 2nd order
	 * 		roughness penalty derivative, and without constraint.
	 * 	- Basis Spline Stretch Test Using the Segment Best Fit Response.
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final void RegressionSplineEstimatorTest()
		throws Exception
	{
		/*
		 * Set the Knot Points
		 */

		double[] adblX = new double[] { 1.00,  5.00, 10.00};

		/*
		 * Construct a set of Predictor Ordinates, their Responses, and corresponding Weights to serve as
		 *  weighted closeness of fit.
		 */

		StretchBestFitResponse sbfr = StretchBestFitResponse.Create (
			new double[] { 2.28,  2.52,  2.73, 3.00,  5.50, 8.44,  8.76,  9.08,  9.80,  9.92},
			new double[] {14.27, 12.36, 10.61, 9.25, -0.50, 7.92, 10.07, 12.23, 15.51, 16.36},
			new double[] { 1.09,  0.82,  1.34, 1.10,  0.50, 0.79,  0.65,  0.49,  0.24,  0.21}
		);

		/*
		 * Construct the segment inelastic parameter that is C2 (iK = 2 sets it to C2), with 2nd order
		 * 	roughness penalty derivative, and without constraint
		 */

		int iK = 2;
		int iRoughnessPenaltyDerivativeOrder = 2;

		SegmentInelasticDesignControl sdic = SegmentInelasticDesignControl.Create (
			iK,
			iRoughnessPenaltyDerivativeOrder
		);

		int iPolyNumBasis = 4;

		/*
		 * Basis Spline Stretch Test Using the Segment Best Fit Response
		 */

		BasisSplineStretchTest (
			adblX,
			PolynomialSegmentControlParams (
				iPolyNumBasis,
				sdic
			),
			sbfr
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		RegressionSplineEstimatorTest();

		EnvManager.TerminateEnv();
	}
}
