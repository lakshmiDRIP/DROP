
package org.drip.spline.segment;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>BasisEvaluator</i> implements the Segment's Basis Evaluator Functions. It exports the following
 * functions:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Retrieve the number of Segment's Basis Functions
 *  	</li>
 *  	<li>
 * 			Set the Inelastics that provides the enveloping Context the Basis Evaluation
 *  	</li>
 *  	<li>
 * 			Clone/Replicate the current Basis Evaluator Instance
 *  	</li>
 *  	<li>
 * 			Compute the Response Value of the indexed Basis Function at the specified Predictor Ordinate
 *  	</li>
 *  	<li>
 * 			Compute the Basis Function Value at the specified Predictor Ordinate
 *  	</li>
 *  	<li>
 * 			Compute the Response Value at the specified Predictor Ordinate
 *  	</li>
 *  	<li>
 * 			Compute the Ordered Derivative of the Response Value off of the indexed Basis Function at the
 * 				specified Predictor Ordinate
 *  	</li>
 *  	<li>
 * 			Compute the Ordered Derivative of the Response Value off of the Basis Function Set at the
 * 			specified Predictor Ordinate
 *  	</li>
 *  	<li>
 * 			Compute the Response Value Derivative at the specified Predictor Ordinate.
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline">Spline</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment">Segment</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public interface BasisEvaluator {

	/**
	 * Retrieve the number of Segment's Basis Functions
	 * 
	 * @return The Number of Segment's Basis Functions
	 */

	public abstract int numBasis();

	/**
	 * Set the Inelastics that provides the enveloping Context the Basis Evaluation
	 * 
	 * @param ics The Inelastic Settings
	 * 
	 * @return TRUE - The inelastics has been set
	 */

	public abstract boolean setContainingInelastics (
		final org.drip.spline.segment.LatentStateInelastic ics);

	/**
	 * Clone/Replicate the current Basis Evaluator Instance
	 * 
	 * @return TRUE - The Replicated Basis Evaluator Instance
	 */

	public abstract BasisEvaluator replicate();

	/**
	 * Compute the Response Value of the indexed Basis Function at the specified Predictor Ordinate
	 * 
	 * @param dblPredictorOrdinate The specified Predictor Ordinate
	 * @param iBasisFunctionIndex Index representing the Basis Function in the Basis Function Set
	 *  
	 * @return The Response Value of the indexed Basis Function at the specified Predictor Ordinate
	 * 
	 * @throws java.lang.Exception Thrown if the Ordered Derivative cannot be computed
	 */

	public abstract double shapedBasisFunctionResponse (
		final double dblPredictorOrdinate,
		final int iBasisFunctionIndex)
		throws java.lang.Exception;

	/**
	 * Compute the Basis Function Value at the specified Predictor Ordinate
	 * 
	 * @param adblResponseBasisCoeff Array of the Response Basis Coefficients
	 * @param dblPredictorOrdinate The specified Predictor Ordinate
	 * 
	 * @return The Basis Function Value
	 * 
	 * @throws java.lang.Exception Thrown if the Basis Function Value cannot be computed
	 */

	public abstract double unshapedResponseValue (
		final double[] adblResponseBasisCoeff,
		final double dblPredictorOrdinate)
		throws java.lang.Exception;

	/**
	 * Compute the Response Value at the specified Predictor Ordinate
	 * 
	 * @param adblResponseBasisCoeff Array of the Response Basis Coefficients
	 * @param dblPredictorOrdinate The specified Predictor Ordinate
	 * 
	 * @return The Response Value
	 * 
	 * @throws java.lang.Exception Thrown if the Basis Function Value cannot be computed
	 */

	public abstract double responseValue (
		final double[] adblResponseBasisCoeff,
		final double dblPredictorOrdinate)
		throws java.lang.Exception;

	/**
	 * Compute the Ordered Derivative of the Response Value off of the indexed Basis Function at the
	 *	specified Predictor Ordinate
	 * 
	 * @param dblPredictorOrdinate The specified Predictor Ordinate
	 * @param iOrder Order of the Derivative
	 * @param iBasisFunctionIndex Index representing the Basis Function in the Basis Function Set
	 *  
	 * @return The Ordered Derivative of the Response Value off of the Indexed Basis Function
	 * 
	 * @throws java.lang.Exception Thrown if the Ordered Derivative cannot be computed
	 */

	public abstract double shapedBasisFunctionDerivative (
		final double dblPredictorOrdinate,
		final int iOrder,
		final int iBasisFunctionIndex)
		throws java.lang.Exception;

	/**
	 * Compute the Ordered Derivative of the Response Value off of the Basis Function Set at the specified
	 *  Predictor Ordinate
	 * 
	 * @param adblResponseBasisCoeff Array of the Response Basis Coefficients
	 * @param dblPredictorOrdinate The specified Predictor Ordinate
	 * @param iOrder Order of the Derivative
	 * 
	 * @return The Ordered Derivative of the Response Value off of the Basis Function Set
	 * 
	 * @throws java.lang.Exception Thrown if the Ordered Derivative of the Basis Function Set cannot be
	 * 	computed
	 */

	public abstract double unshapedBasisFunctionDerivative (
		final double[] adblResponseBasisCoeff,
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception;

	/**
	 * Compute the Response Value Derivative at the specified Predictor Ordinate
	 * 
	 * @param adblResponseBasisCoeff Array of the Response Basis Coefficients
	 * @param dblPredictorOrdinate The specified Predictor Ordinate
	 * @param iOrder Order of the Derivative
	 * 
	 * @return The Response Value Derivative
	 * 
	 * @throws java.lang.Exception Thrown if the Response Value Derivative cannot be computed
	 */

	public abstract double responseValueDerivative (
		final double[] adblResponseBasisCoeff,
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception;
}
