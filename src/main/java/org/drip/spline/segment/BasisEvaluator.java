
package org.drip.spline.segment;

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
 * <i>BasisEvaluator</i> implements the Segment's Basis Evaluator Functions. It exports the following
 * 	functions:
 *
 * <br>
 *  <ul>
 *  	<li>Retrieve the number of Segment's Basis Functions</li>
 *  	<li>Set the Inelastics that provides the enveloping Context the Basis Evaluation</li>
 *  	<li>Clone/Replicate the current Basis Evaluator Instance</li>
 *  	<li>Compute the Response Value of the indexed Basis Function at the specified Predictor Ordinate</li>
 *  	<li>Compute the Basis Function Value at the specified Predictor Ordinate</li>
 *  	<li>Compute the Response Value at the specified Predictor Ordinate</li>
 *  	<li>Compute the Ordered Derivative of the Response Value off of the indexed Basis Function at the specified Predictor Ordinate</li>
 *  	<li>Compute the Ordered Derivative of the Response Value off of the Basis Function Set at the specified Predictor Ordinate</li>
 *  	<li>Compute the Response Value Derivative at the specified Predictor Ordinate</li>
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
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment/README.md">Flexure Penalizing Best Fit Segment</a></td></tr>
 *  </table>
 *  <br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public interface BasisEvaluator
{

	/**
	 * Retrieve the number of Segment's Basis Functions
	 * 
	 * @return The Number of Segment's Basis Functions
	 */

	public abstract int numBasis();

	/**
	 * Set the Inelastics that provides the enveloping Context the Basis Evaluation
	 * 
	 * @param latentStateInelastic The Inelastic Settings
	 * 
	 * @return TRUE - The inelastics has been set
	 */

	public abstract boolean setContainingInelastics (
		final LatentStateInelastic latentStateInelastic
	);

	/**
	 * Clone/Replicate the current Basis Evaluator Instance
	 * 
	 * @return TRUE - The Replicated Basis Evaluator Instance
	 */

	public abstract BasisEvaluator replicate();

	/**
	 * Compute the Response Value of the indexed Basis Function at the specified Predictor Ordinate
	 * 
	 * @param predictorOrdinate The specified Predictor Ordinate
	 * @param basisFunctionIndex Index representing the Basis Function in the Basis Function Set
	 *  
	 * @return The Response Value of the indexed Basis Function at the specified Predictor Ordinate
	 * 
	 * @throws Exception Thrown if the Ordered Derivative cannot be computed
	 */

	public abstract double shapedBasisFunctionResponse (
		final double predictorOrdinate,
		final int basisFunctionIndex)
		throws Exception;

	/**
	 * Compute the Basis Function Value at the specified Predictor Ordinate
	 * 
	 * @param responseBasisCoefficientArray Array of the Response Basis Coefficients
	 * @param predictorOrdinate The specified Predictor Ordinate
	 * 
	 * @return The Basis Function Value
	 * 
	 * @throws Exception Thrown if the Basis Function Value cannot be computed
	 */

	public abstract double unshapedResponseValue (
		final double[] responseBasisCoefficientArray,
		final double predictorOrdinate)
		throws Exception;

	/**
	 * Compute the Response Value at the specified Predictor Ordinate
	 * 
	 * @param responseBasisCoefficientArray Array of the Response Basis Coefficients
	 * @param predictorOrdinate The specified Predictor Ordinate
	 * 
	 * @return The Response Value
	 * 
	 * @throws Exception Thrown if the Basis Function Value cannot be computed
	 */

	public abstract double responseValue (
		final double[] responseBasisCoefficientArray,
		final double predictorOrdinate)
		throws Exception;

	/**
	 * Compute the Ordered Derivative of the Response Value off of the indexed Basis Function at the
	 *	specified Predictor Ordinate
	 * 
	 * @param predictorOrdinate The specified Predictor Ordinate
	 * @param order Order of the Derivative
	 * @param basisFunctionIndex Index representing the Basis Function in the Basis Function Set
	 *  
	 * @return The Ordered Derivative of the Response Value off of the Indexed Basis Function
	 * 
	 * @throws Exception Thrown if the Ordered Derivative cannot be computed
	 */

	public abstract double shapedBasisFunctionDerivative (
		final double predictorOrdinate,
		final int order,
		final int basisFunctionIndex)
		throws Exception;

	/**
	 * Compute the Ordered Derivative of the Response Value off of the Basis Function Set at the specified
	 *  Predictor Ordinate
	 * 
	 * @param responseBasisCoefficientArray Array of the Response Basis Coefficients
	 * @param predictorOrdinate The specified Predictor Ordinate
	 * @param order Order of the Derivative
	 * 
	 * @return The Ordered Derivative of the Response Value off of the Basis Function Set
	 * 
	 * @throws Exception Thrown if the Ordered Derivative of the Basis Function Set cannot be computed
	 */

	public abstract double unshapedBasisFunctionDerivative (
		final double[] responseBasisCoefficientArray,
		final double predictorOrdinate,
		final int order)
		throws Exception;

	/**
	 * Compute the Response Value Derivative at the specified Predictor Ordinate
	 * 
	 * @param responseBasisCoefficientArray Array of the Response Basis Coefficients
	 * @param predictorOrdinate The specified Predictor Ordinate
	 * @param order Order of the Derivative
	 * 
	 * @return The Response Value Derivative
	 * 
	 * @throws Exception Thrown if the Response Value Derivative cannot be computed
	 */

	public abstract double responseValueDerivative (
		final double[] responseBasisCoefficientArray,
		final double predictorOrdinate,
		final int order)
		throws Exception;
}
