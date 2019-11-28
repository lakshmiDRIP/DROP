
package org.drip.spline.stretch;

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
 * <i>SingleSegmentSequence</i> is the interface that exposes functionality that spans multiple segments. Its
 * derived instances hold the ordered segment sequence, the segment control parameters, and, if available,
 * the spanning Jacobian. SingleSegmentSequence exports the following group of functionality:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Construct adjoining segment sequences in accordance with the segment control parameters
 *  	</li>
 *  	<li>
 * 			Calibrate according to a varied set of (i.e., NATURAL/FINANCIAL) boundary conditions
 *  	</li>
 *  	<li>
 * 			Estimate both the value, the ordered derivatives, and the Jacobian (quote/coefficient) at the
 * 				given ordinate
 *  	</li>
 *  	<li>
 * 			Compute the monotonicity details - segment/Stretch level monotonicity, co-monotonicity, local
 * 				monotonicity
 *  	</li>
 *  	<li>
 * 			Predictor Ordinate Details - identify the left/right predictor ordinate edges, and whether the
 * 				given predictor ordinate is a knot
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/README.md">Multi-Segment Sequence Spline Stretch</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public interface SingleSegmentSequence {

	/**
	 * Set up (i.e., calibrate) the individual Segments in the Stretch to the Response Values corresponding
	 * 	to each Segment Predictor right Ordinate.
	 * 
	 * @param dblStretchLeadingResponse Stretch Left-most Response
	 * @param adblSegmentRightEdgeResponse Array of Segment Right Edge Responses
	 * @param sbfr Stretch Fitness Weighted Response
	 * @param bs The Calibration Boundary Condition
	 * @param iCalibrationDetail The Calibration Detail
	 * 
	 * @return TRUE - Set up was successful
	 */

	public abstract boolean setup (
		final double dblStretchLeadingResponse,
		final double[] adblSegmentRightEdgeResponse,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final org.drip.spline.stretch.BoundarySettings bs,
		final int iCalibrationDetail);

	/**
	 * Calculate the Response Value at the given Predictor Ordinate
	 * 
	 * @param dblPredictorOrdinate Predictor Ordinate
	 * 
	 * @return The Response Value
	 * 
	 * @throws java.lang.Exception Thrown if the Response Value cannot be calculated
	 */

	public abstract double responseValue (
		final double dblPredictorOrdinate)
		throws java.lang.Exception;

	/**
	 * Calculate the Response Value Derivative at the given Predictor Ordinate for the specified order
	 * 
	 * @param dblPredictorOrdinate Predictor Ordinate
	 * @param iOrder Order the Derivative
	 * 
	 * @return The Response Value
	 * 
	 * @throws java.lang.Exception Thrown if the Response Value Derivative cannot be calculated
	 */

	public abstract double responseValueDerivative (
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception;

	/**
	 * Calculate the Response Derivative to the Calibration Inputs at the specified Ordinate
	 * 
	 * @param dblPredictorOrdinate Predictor Ordinate
	 * @param iOrder Order of Derivative desired
	 * 
	 * @return Jacobian of the Response Derivative to the Calibration Inputs at the Ordinate
	 */

	public abstract org.drip.numerical.differentiation.WengertJacobian jackDResponseDCalibrationInput (
		final double dblPredictorOrdinate,
		final int iOrder);

	/**
	 * Calculate the Response Derivative to the Manifest Measure at the specified Ordinate
	 * 
	 * @param strManifestMeasure Manifest Measure whose Sensitivity is sought
	 * @param dblPredictorOrdinate Predictor Ordinate
	 * @param iOrder Order of Derivative desired
	 * 
	 * @return Jacobian of the Response Derivative to the Quote at the Ordinate
	 */

	public abstract org.drip.numerical.differentiation.WengertJacobian jackDResponseDManifestMeasure (
		final java.lang.String strManifestMeasure,
		final double dblPredictorOrdinate,
		final int iOrder);

	/**
	 * Identify the Monotone Type for the Segment underlying the given Predictor Ordinate
	 * 
	 * @param dblPredictorOrdinate Predictor Ordinate
	 * 
	 * @return Segment Monotone Type
	 */

	public abstract org.drip.spline.segment.Monotonocity monotoneType (
		final double dblPredictorOrdinate);

	/**
	 * Indicate if all the comprising Segments are Monotone
	 * 
	 * @return TRUE - Fully locally monotonic
	 * 
	 * @throws java.lang.Exception Thrown if the Segment Monotone Type could not be estimated
	 */

	public abstract boolean isLocallyMonotone()
		throws java.lang.Exception;

	/**
	 * Verify whether the Stretch mini-max Behavior matches the Measurement
	 * 
	 * @param adblMeasuredResponse The Array of Measured Responses
	 * 
	 * @return TRUE - Stretch is co-monotonic with the measured Responses
	 * 
	 * @throws java.lang.Exception Thrown if the Segment Monotone Type could not be estimated
	 */

	public abstract boolean isCoMonotone (
		final double[] adblMeasuredResponse)
		throws java.lang.Exception;

	/**
	 * Is the given Predictor Ordinate a Knot Location
	 * 
	 * @param dblPredictorOrdinate Predictor Ordinate
	 * 
	 * @return TRUE - Given Predictor Ordinate corresponds to a Knot
	 */

	public abstract boolean isKnot (
		final double dblPredictorOrdinate);

	/**
	 * Reset the Predictor Ordinate Node Index with the given Response
	 * 
	 * @param iPredictorOrdinateNodeIndex The Predictor Ordinate Node Index whose Response is to be reset
	 * @param dblResetResponse The Response to reset
	 * 
	 * @return TRUE - Reset succeeded
	 */

	public abstract boolean resetNode (
		final int iPredictorOrdinateNodeIndex,
		final double dblResetResponse);

	/**
	 * Reset the Predictor Ordinate Node Index with the given Segment Constraint
	 * 
	 * @param iNodeIndex The Predictor Ordinate Node Index whose Response is to be reset
	 * @param srvcReset The Segment Constraint
	 * 
	 * @return TRUE - Reset succeeded
	 */

	public abstract boolean resetNode (
		final int iNodeIndex,
		final org.drip.spline.params.SegmentResponseValueConstraint srvcReset);

	/**
	 * Return the Left Predictor Ordinate Edge
	 * 
	 * @return The Left Predictor Ordinate Edge
	 */

	public abstract double getLeftPredictorOrdinateEdge();

	/**
	 * Return the Right Predictor Ordinate Edge
	 * 
	 * @return The Right Predictor Ordinate Edge
	 */

	public abstract double getRightPredictorOrdinateEdge();

	/**
	 * Convert the Segment Sequence into an AbstractUnivariate Instance
	 * 
	 * @return The AbstractUnivariate Instance
	 */

	public abstract org.drip.function.definition.R1ToR1 toAU();
}
