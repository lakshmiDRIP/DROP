
package org.drip.spline.pchip;

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
	private double[] _observationArray = null;
	private double[] _predictorOrdinateArray = null;
	private double _dblWeight = Double.NaN;

	/**
	 * Create an instance of <i>MinimalQuadraticHaganWest</i>
	 * 
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblObservation Array of Observations
	 * @param dblWeight Relative Weights applied across the first and the second derivatives
	 * 
	 * @return Instance of <i>MinimalQuadraticHaganWest</i>
	 */

	public static final MinimalQuadraticHaganWest Create (
		final double[] adblPredictorOrdinate,
		final double[] adblObservation,
		final double dblWeight)
	{
		MinimalQuadraticHaganWest mchw = null;

		try {
			mchw = new MinimalQuadraticHaganWest (adblPredictorOrdinate, adblObservation, dblWeight);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return mchw.setupCoefficients() ? mchw : null;
	}

	private MinimalQuadraticHaganWest (
		final double[] adblPredictorOrdinate,
		final double[] adblObservation,
		final double dblWeight)
		throws java.lang.Exception
	{
		if (null == (_observationArray = adblObservation) || null == (_predictorOrdinateArray =
			adblPredictorOrdinate) || !org.drip.numerical.common.NumberUtil.IsValid (_dblWeight = dblWeight))
			throw new java.lang.Exception ("MinimalQuadraticHaganWest ctr: Invalid Inputs!");

		int iNumObservation = _observationArray.length;

		if (1 >= iNumObservation || iNumObservation + 1 != _predictorOrdinateArray.length)
			throw new java.lang.Exception ("MinimalQuadraticHaganWest ctr: Invalid Inputs!");
	}

	private boolean setupCoefficients()
	{
		int iNumObservation = _observationArray.length;
		_aArray = new double[iNumObservation];
		_bArray = new double[iNumObservation];
		_cArray = new double[iNumObservation];
		double[] adblH = new double[iNumObservation];
		double[] adblRHS = new double[3 * iNumObservation];
		double[][] aadblCoeffMatrix = new double[3 * iNumObservation][3 * iNumObservation];

		for (int i = 0; i < 3 * iNumObservation; ++i) {
			adblRHS[i] = 0.;

			for (int j = 0; j < 3 * iNumObservation; ++j)
				aadblCoeffMatrix[i][j] = 0.;
		}

		for (int i = 0; i < iNumObservation; ++i)
			adblH[i] = _predictorOrdinateArray[i + 1] - _predictorOrdinateArray[i];

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

		for (int iEq = 0; iEq < iNumObservation; ++iEq) {
			int iSegmentIndex = iEq;
			adblRHS[iEq] = _observationArray[iEq]; // Z_i
			aadblCoeffMatrix[iEq][3 * iSegmentIndex] = 1.; // A_i
			aadblCoeffMatrix[iEq][3 * iSegmentIndex + 1] = 0.5 * adblH[iSegmentIndex]; // B_i
			aadblCoeffMatrix[iEq][3 * iSegmentIndex + 2] = adblH[iSegmentIndex] * adblH[iSegmentIndex] / 3.; // C_i
		}

		/*
		 * Set up the continuity constraints; Laid out as:
		 * 		A_i + H_i * B_i + (H_i * H_i) * C_i - A_i+1 = 0.
		 */

		for (int iEq = iNumObservation; iEq < 2 * iNumObservation - 1; ++iEq) {
			adblRHS[iEq] = 0.;
			int iSegmentIndex = iEq - iNumObservation;
			aadblCoeffMatrix[iEq][3 * iSegmentIndex] = 1.; // A_i
			aadblCoeffMatrix[iEq][3 * iSegmentIndex + 1] = adblH[iSegmentIndex]; // B_i
			aadblCoeffMatrix[iEq][3 * iSegmentIndex + 2] = adblH[iSegmentIndex] * adblH[iSegmentIndex]; // C_i
			aadblCoeffMatrix[iEq][3 * iSegmentIndex + 3] = -1.; // A_i+1
		}

		/*
		 * Set up the derivative penalty minimizer; Laid out as:
		 * 		w * B_i + (2. * H_i) * C_i - w * B_i+1 = 0.
		 */

		for (int iEq = 2 * iNumObservation - 1; iEq < 3 * iNumObservation - 2; ++iEq) {
			adblRHS[iEq] = 0.;
			int iSegmentIndex = iEq - 2 * iNumObservation + 1;
			aadblCoeffMatrix[iEq][3 * iSegmentIndex + 1] = _dblWeight; // B_i
			aadblCoeffMatrix[iEq][3 * iSegmentIndex + 2] = 2. * adblH[iSegmentIndex]; // C_i
			aadblCoeffMatrix[iEq][3 * iSegmentIndex + 4] = -1. * _dblWeight; // B_i+1
		}

		/*
		 * Left Boundary Condition: Starting Left Slope is zero, i.e., B_0 = 0.
		 */

		adblRHS[3 * iNumObservation - 2] = 0.;
		aadblCoeffMatrix[3 * iNumObservation - 2][1] = 1.;

		/*
		 * Right Boundary Condition: Final First Derivative is zero, i.e., B_n-1 = 0.
		 */

		adblRHS[3 * iNumObservation - 1] = 0.;
		aadblCoeffMatrix[3 * iNumObservation - 1][3 * iNumObservation - 2] = 1.;

		org.drip.numerical.linearalgebra.LinearizationOutput lssGaussianElimination =
			org.drip.numerical.linearalgebra.LinearSystemSolver.SolveUsingGaussianElimination (aadblCoeffMatrix,
				adblRHS);

		if (null == lssGaussianElimination) return false;

		double[] adblCoeff = lssGaussianElimination.getTransformedRHS();

		if (null == adblCoeff || 3 * iNumObservation != adblCoeff.length) return false;

		int iSegment = 0;

		for (int i = 0; i < 3 * iNumObservation; ++i) {
			if (0 == i % 3)
				_aArray[iSegment] = adblCoeff[i];
			else if (1 == i % 3)
				_bArray[iSegment] = adblCoeff[i];
			else if (2 == i % 3) {
				_cArray[iSegment] = adblCoeff[i];
				++iSegment;
			}
		}

		return true;
	}

	private int containingIndex (
		final double dblPredictorOrdinate,
		final boolean bIncludeLeft,
		final boolean bIncludeRight)
		throws java.lang.Exception
	{
		int iNumSegment = _aArray.length;

		for (int i = 0 ; i < iNumSegment; ++i) {
			boolean bLeftValid = bIncludeLeft ? _predictorOrdinateArray[i] <= dblPredictorOrdinate :
				_predictorOrdinateArray[i] < dblPredictorOrdinate;

			boolean bRightValid = bIncludeRight ? _predictorOrdinateArray[i + 1] >= dblPredictorOrdinate :
				_predictorOrdinateArray[i + 1] > dblPredictorOrdinate;

			if (bLeftValid && bRightValid) return i;
		}

		throw new java.lang.Exception
			("MinimalQuadraticHaganWest::containingIndex => Cannot locate Containing Index");
	}

	/**
	 * Calculate the Response Value given the Predictor Ordinate
	 * 
	 * @param dblPredictorOrdinate The Predictor Ordinate
	 * 
	 * @return The Response Value
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public double responseValue (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		int i = containingIndex (dblPredictorOrdinate, true, true);

		return _aArray[i] + _bArray[i] * (dblPredictorOrdinate - _predictorOrdinateArray[i]) + _cArray[i] *
			(dblPredictorOrdinate - _predictorOrdinateArray[i]) * (dblPredictorOrdinate -
				_predictorOrdinateArray[i]);
	}

	/**
	 * Calculate the Conserved Constraint
	 * 
	 * @return The Conserved Constraint
	 */

	public double[] calcConservedConstraint()
	{
		int iNumObservation = _observationArray.length;
		double[] adblConservedConstraint = new double[iNumObservation];

		for (int i = 0; i < iNumObservation; ++i)
			adblConservedConstraint[i] = _aArray[i] + _bArray[i] * 0.5 + _cArray[i] / 3.;

		return adblConservedConstraint;
	}
}
