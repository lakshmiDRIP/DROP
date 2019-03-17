
package org.drip.spline.pchip;

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
 * <i>MinimalQuadraticHaganWest</i> implements the regime using the Hagan and West (2006) Minimal Quadratic
 * Estimator.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline">Spline</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/pchip">PCHIP</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MinimalQuadraticHaganWest {
	private double[] _adblA = null;
	private double[] _adblB = null;
	private double[] _adblC = null;
	private double[] _adblObservation = null;
	private double[] _adblPredictorOrdinate = null;
	private double _dblWeight = java.lang.Double.NaN;

	/**
	 * Create an instance of MinimalQuadraticHaganWest
	 * 
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblObservation Array of Observations
	 * @param dblWeight Relative Weights applied across the first and the second derivatives
	 * 
	 * @return Instance of MinimalQuadraticHaganWest
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
		if (null == (_adblObservation = adblObservation) || null == (_adblPredictorOrdinate =
			adblPredictorOrdinate) || !org.drip.numerical.common.NumberUtil.IsValid (_dblWeight = dblWeight))
			throw new java.lang.Exception ("MinimalQuadraticHaganWest ctr: Invalid Inputs!");

		int iNumObservation = _adblObservation.length;

		if (1 >= iNumObservation || iNumObservation + 1 != _adblPredictorOrdinate.length)
			throw new java.lang.Exception ("MinimalQuadraticHaganWest ctr: Invalid Inputs!");
	}

	private boolean setupCoefficients()
	{
		int iNumObservation = _adblObservation.length;
		_adblA = new double[iNumObservation];
		_adblB = new double[iNumObservation];
		_adblC = new double[iNumObservation];
		double[] adblH = new double[iNumObservation];
		double[] adblRHS = new double[3 * iNumObservation];
		double[][] aadblCoeffMatrix = new double[3 * iNumObservation][3 * iNumObservation];

		for (int i = 0; i < 3 * iNumObservation; ++i) {
			adblRHS[i] = 0.;

			for (int j = 0; j < 3 * iNumObservation; ++j)
				aadblCoeffMatrix[i][j] = 0.;
		}

		for (int i = 0; i < iNumObservation; ++i)
			adblH[i] = _adblPredictorOrdinate[i + 1] - _adblPredictorOrdinate[i];

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
			adblRHS[iEq] = _adblObservation[iEq]; // Z_i
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
				_adblA[iSegment] = adblCoeff[i];
			else if (1 == i % 3)
				_adblB[iSegment] = adblCoeff[i];
			else if (2 == i % 3) {
				_adblC[iSegment] = adblCoeff[i];
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
		int iNumSegment = _adblA.length;

		for (int i = 0 ; i < iNumSegment; ++i) {
			boolean bLeftValid = bIncludeLeft ? _adblPredictorOrdinate[i] <= dblPredictorOrdinate :
				_adblPredictorOrdinate[i] < dblPredictorOrdinate;

			boolean bRightValid = bIncludeRight ? _adblPredictorOrdinate[i + 1] >= dblPredictorOrdinate :
				_adblPredictorOrdinate[i + 1] > dblPredictorOrdinate;

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

		return _adblA[i] + _adblB[i] * (dblPredictorOrdinate - _adblPredictorOrdinate[i]) + _adblC[i] *
			(dblPredictorOrdinate - _adblPredictorOrdinate[i]) * (dblPredictorOrdinate -
				_adblPredictorOrdinate[i]);
	}

	public double[] calcConservedConstraint()
	{
		int iNumObservation = _adblObservation.length;
		double[] adblConservedConstraint = new double[iNumObservation];

		for (int i = 0; i < iNumObservation; ++i)
			adblConservedConstraint[i] = _adblA[i] + _adblB[i] * 0.5 + _adblC[i] / 3.;

		return adblConservedConstraint;
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		double[] adblTime = new double[] {0., 1.0, 2.0};
		double[] adblForwardRate = new double[] {0.02, 0.026};

		MinimalQuadraticHaganWest mqhw = MinimalQuadraticHaganWest.Create (adblTime, adblForwardRate, 0.5);

		double[] adblConservedConstraint = mqhw.calcConservedConstraint();

		for (int i = 0; i < adblConservedConstraint.length; ++i)
			System.out.println ("Conserved Constraint[" + i + "] => " +
				org.drip.numerical.common.FormatUtil.FormatDouble (adblConservedConstraint[i], 1, 6, 1.));

		for (double dblTime = adblTime[0]; dblTime <= adblTime[adblTime.length - 1]; dblTime += 0.25)
			System.out.println ("Response[" + org.drip.numerical.common.FormatUtil.FormatDouble (dblTime, 2, 2,
				1.) + "] = " + org.drip.numerical.common.FormatUtil.FormatDouble (mqhw.responseValue (dblTime), 1,
					6, 1.));
	}
}
