
package org.drip.spline.pchip;

import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.linearalgebra.LinearSystemSolver;
import org.drip.numerical.linearalgebra.LinearizationOutput;

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
 * <i>MinimalQuadraticHaganWest</i> implements the regime using the Hagan and West (2006) Minimal Quadratic
 * 	Estimator.
 *
 * <br>
 *  <ul>
 * 		<li>Calculate an instance of <i>MinimalQuadraticHaganWest</i></li>
 * 		<li>Calculate the Response Value given the Predictor Ordinate</li>
 * 		<li>Calculate the Conserved Constraint</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/pchip/README.md">Monotone Convex Themed PCHIP Splines</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MinimalQuadraticHaganWest
{
	private double[] _aArray = null;
	private double[] _bArray = null;
	private double[] _cArray = null;
	private double _weight = Double.NaN;
	private double[] _observationArray = null;
	private double[] _predictorOrdinateArray = null;

	/**
	 * Create an instance of <i>MinimalQuadraticHaganWest</i>
	 * 
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param observationArray Array of Observations
	 * @param weight Relative Weights applied across the first and the second derivatives
	 * 
	 * @return Instance of <i>MinimalQuadraticHaganWest</i>
	 */

	public static final MinimalQuadraticHaganWest Create (
		final double[] predictorOrdinateArray,
		final double[] observationArray,
		final double weight)
	{
		MinimalQuadraticHaganWest minimalQuadraticHaganWest = null;

		try {
			minimalQuadraticHaganWest = new MinimalQuadraticHaganWest (
				predictorOrdinateArray,
				observationArray,
				weight
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return minimalQuadraticHaganWest.setupCoefficients() ? minimalQuadraticHaganWest : null;
	}

	private MinimalQuadraticHaganWest (
		final double[] predictorOrdinateArray,
		final double[] observationArray,
		final double weight)
		throws Exception
	{
		if (null == (_observationArray = observationArray) ||
			null == (_predictorOrdinateArray = predictorOrdinateArray) ||
			!NumberUtil.IsValid (_weight = weight)
		) {
			throw new Exception ("MinimalQuadraticHaganWest ctr: Invalid Inputs!");
		}

		int observationCount = _observationArray.length;

		if (1 >= observationCount || observationCount + 1 != _predictorOrdinateArray.length) {
			throw new Exception ("MinimalQuadraticHaganWest ctr: Invalid Inputs!");
		}
	}

	private boolean setupCoefficients()
	{
		int observationCount = _observationArray.length;
		_aArray = new double[observationCount];
		_bArray = new double[observationCount];
		_cArray = new double[observationCount];
		double[] hArray = new double[observationCount];
		double[] rhsArray = new double[3 * observationCount];
		double[][] coefficientMatrix = new double[3 * observationCount][3 * observationCount];

		for (int observationIndexI = 0; observationIndexI < 3 * observationCount; ++observationIndexI) {
			rhsArray[observationIndexI] = 0.;

			for (int observationIndexJ = 0; observationIndexJ < 3 * observationCount; ++observationIndexJ) {
				coefficientMatrix[observationIndexI][observationIndexJ] = 0.;
			}
		}

		for (int observationIndex = 0; observationIndex < observationCount; ++observationIndex) {
			hArray[observationIndex] = _predictorOrdinateArray[observationIndex + 1] -
				_predictorOrdinateArray[observationIndex];
		}

		/*
		 * Setting up the coefficient linear constraint equation set
		 * 
		 * 	- Left index => Equation Index
		 * 	- Right Index => Coefficient Index
		 */

		/*
		 * Set up the conserved quantities; Laid out as:
		 * 		A_i + (H_i / 2.) * B_i + (H_i * H_i / 3.) * C_i = Observation_i
		 */

		for (int equationIndex = 0; equationIndex < observationCount; ++equationIndex) {
			int segmentIndex = equationIndex;
			rhsArray[equationIndex] = _observationArray[equationIndex]; // Z_i
			coefficientMatrix[equationIndex][3 * segmentIndex] = 1.; // A_i
			coefficientMatrix[equationIndex][3 * segmentIndex + 1] = 0.5 * hArray[segmentIndex]; // B_i
			coefficientMatrix[equationIndex][3 * segmentIndex + 2] =
				hArray[segmentIndex] * hArray[segmentIndex] / 3.; // C_i
		}

		/*
		 * Set up the continuity constraints; Laid out as:
		 * 		A_i + H_i * B_i + (H_i * H_i) * C_i - A_i+1 = 0.
		 */

		for (int equationIndex = observationCount; equationIndex < 2 * observationCount - 1; ++equationIndex)
		{
			rhsArray[equationIndex] = 0.;
			int segmentIndex = equationIndex - observationCount;
			coefficientMatrix[equationIndex][3 * segmentIndex] = 1.; // A_i
			coefficientMatrix[equationIndex][3 * segmentIndex + 1] = hArray[segmentIndex]; // B_i
			coefficientMatrix[equationIndex][3 * segmentIndex + 2] =
				hArray[segmentIndex] * hArray[segmentIndex]; // C_i
			coefficientMatrix[equationIndex][3 * segmentIndex + 3] = -1.; // A_i+1
		}

		/*
		 * Set up the derivative penalty minimizer; Laid out as:
		 * 		w * B_i + (2. * H_i) * C_i - w * B_i+1 = 0.
		 */

		for (int equationIndex = 2 * observationCount - 1; equationIndex < 3 * observationCount - 2;
			++equationIndex) {
			rhsArray[equationIndex] = 0.;
			int segmentIndex = equationIndex - 2 * observationCount + 1;
			coefficientMatrix[equationIndex][3 * segmentIndex + 1] = _weight; // B_i
			coefficientMatrix[equationIndex][3 * segmentIndex + 2] = 2. * hArray[segmentIndex]; // C_i
			coefficientMatrix[equationIndex][3 * segmentIndex + 4] = -1. * _weight; // B_i+1
		}

		/*
		 * Left Boundary Condition: Starting Left Slope is zero, i.e., B_0 = 0.
		 */

		rhsArray[3 * observationCount - 2] = 0.;
		coefficientMatrix[3 * observationCount - 2][1] = 1.;

		/*
		 * Right Boundary Condition: Final First Derivative is zero, i.e., B_n-1 = 0.
		 */

		rhsArray[3 * observationCount - 1] = 0.;
		coefficientMatrix[3 * observationCount - 1][3 * observationCount - 2] = 1.;

		LinearizationOutput gaussianEliminationLinearizationOutput =
			LinearSystemSolver.SolveUsingGaussianElimination (coefficientMatrix, rhsArray);

		if (null == gaussianEliminationLinearizationOutput) {
			return false;
		}

		double[] coefficientArray = gaussianEliminationLinearizationOutput.getTransformedRHS();

		if (null == coefficientArray || 3 * observationCount != coefficientArray.length) {
			return false;
		}

		int segmentIndex = 0;

		for (int observationIndex = 0; observationIndex < 3 * observationCount; ++observationIndex) {
			if (0 == observationIndex % 3) {
				_aArray[segmentIndex] = coefficientArray[observationIndex];
			} else if (1 == observationIndex % 3) {
				_bArray[segmentIndex] = coefficientArray[observationIndex];
			} else if (2 == observationIndex % 3) {
				_cArray[segmentIndex++] = coefficientArray[observationIndex];
			}
		}

		return true;
	}

	private int containingIndex (
		final double predictorOrdinate,
		final boolean includeLeft,
		final boolean includeRight)
		throws Exception
	{
		for (int segmentIndex = 0 ; segmentIndex < _aArray.length; ++segmentIndex) {
			boolean leftValid = includeLeft ? _predictorOrdinateArray[segmentIndex] <= predictorOrdinate :
				_predictorOrdinateArray[segmentIndex] < predictorOrdinate;

			boolean rightValid = includeRight ?
				_predictorOrdinateArray[segmentIndex + 1] >= predictorOrdinate :
				_predictorOrdinateArray[segmentIndex + 1] > predictorOrdinate;

			if (leftValid && rightValid) {
				return segmentIndex;
			}
		}

		throw new Exception ("MinimalQuadraticHaganWest::containingIndex => Cannot locate Containing Index");
	}

	/**
	 * Calculate the Response Value given the Predictor Ordinate
	 * 
	 * @param predictorOrdinate The Predictor Ordinate
	 * 
	 * @return The Response Value
	 * 
	 * @throws Exception Thrown if the input is invalid
	 */

	public double responseValue (
		final double predictorOrdinate)
		throws Exception
	{
		int containingIndex = containingIndex (predictorOrdinate, true, true);

		return _aArray[containingIndex] + _bArray[containingIndex] * (
			predictorOrdinate - _predictorOrdinateArray[containingIndex]
		) + _cArray[containingIndex] * (predictorOrdinate - _predictorOrdinateArray[containingIndex]) *
			(predictorOrdinate - _predictorOrdinateArray[containingIndex]);
	}

	/**
	 * Calculate the Conserved Constraint
	 * 
	 * @return The Conserved Constraint
	 */

	public double[] calcConservedConstraint()
	{
		int observationCount = _observationArray.length;
		double[] conservedConstraintArray = new double[observationCount];

		for (int observationIndex = 0; observationIndex < observationCount; ++observationIndex) {
			conservedConstraintArray[observationIndex] = _aArray[observationIndex] +
				_bArray[observationIndex] * 0.5 + _cArray[observationIndex] / 3.;
		}

		return conservedConstraintArray;
	}
}
