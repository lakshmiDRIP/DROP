
package org.drip.measure.lebesgue;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * R1PiecewiseDisplaced implements the Displaced Piecewise Linear R^1 Distributions. It exports the Methods
 *  corresponding to the R^1 Lebesgue Base Class.
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1PiecewiseDisplaced extends org.drip.measure.lebesgue.R1Uniform {
	private double[] _adblPredictorOrdinate = null;
	private double[] _adblPiecewiseDensitySlope = null;
	private double _dblDensityDisplacement = java.lang.Double.NaN;

	/**
	 * Calibrate an R1PiecewiseDisplaced Lebesgue Instance
	 * 
	 * @param dblLeftPredictorOrdinateEdge Left Predictor Ordinate Edge
	 * @param dblRightPredictorOrdinateEdge Right Predictor Ordinate Edge
	 * @param adblPredictorOrdinate Array of Intermediate Predictor Ordinates
	 * @param adblCumulativeProbability Array of corresponding Cumulative Probabilities
	 * @param dblMean The Distribution Mean
	 * 
	 * @return The R1PiecewiseDisplacedLebesgue Instance
	 */

	public static final R1PiecewiseDisplaced Standard (
		final double dblLeftPredictorOrdinateEdge,
		final double dblRightPredictorOrdinateEdge,
		final double[] adblPredictorOrdinate,
		final double[] adblCumulativeProbability,
		final double dblMean)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblLeftPredictorOrdinateEdge) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRightPredictorOrdinateEdge) ||
				dblLeftPredictorOrdinateEdge >= dblRightPredictorOrdinateEdge || null ==
					adblPredictorOrdinate || null == adblCumulativeProbability ||
						!org.drip.quant.common.NumberUtil.IsValid (dblMean))
			return null;

		int iNumSegment = adblPredictorOrdinate.length + 1;
		double[][] aadblM = new double[iNumSegment + 1][iNumSegment + 1];
		double[] adblSecondDegreeIntegral = new double[iNumSegment];
		double[] adblSlope = new double[iNumSegment];
		double[] adblY = new double[iNumSegment + 1];
		adblY[iNumSegment] = dblMean;
		aadblM[iNumSegment][iNumSegment] = 0.5 * (dblRightPredictorOrdinateEdge *
			dblRightPredictorOrdinateEdge - dblLeftPredictorOrdinateEdge * dblLeftPredictorOrdinateEdge);

		if (1 == iNumSegment || iNumSegment - 1 != adblCumulativeProbability.length) return null;

		for (int i = 0; i < iNumSegment; ++i) {
			adblY[i] = i == iNumSegment - 1 ? 1. : adblCumulativeProbability[i];
			double dblSegmentLeft = 0 == i ? dblLeftPredictorOrdinateEdge : adblPredictorOrdinate[i - 1];
			double dblSegmentRight = iNumSegment - 1 == i ? dblRightPredictorOrdinateEdge :
				adblPredictorOrdinate[i];
			adblSecondDegreeIntegral[i] = 0.5 * (dblSegmentRight * dblSegmentRight - dblSegmentLeft *
				dblSegmentLeft);
			aadblM[i][iNumSegment] = dblSegmentRight - dblLeftPredictorOrdinateEdge;
			aadblM[iNumSegment][i] = (dblSegmentRight * dblSegmentRight * dblSegmentRight - dblSegmentLeft *
				dblSegmentLeft * dblSegmentLeft) / 6.;

			if (!org.drip.quant.common.NumberUtil.IsValid (dblSegmentLeft) || dblSegmentLeft <
				dblLeftPredictorOrdinateEdge)
				return null;

			if (!org.drip.quant.common.NumberUtil.IsValid (dblSegmentRight) || dblSegmentRight <=
				dblSegmentLeft || dblSegmentRight > dblRightPredictorOrdinateEdge)
				return null;
		}

		for (int i = 0; i < iNumSegment; ++i) {
			for (int k = 0; k < iNumSegment; ++k)
				aadblM[i][k] = k > i ? 0. : adblSecondDegreeIntegral[k];
		}

		org.drip.quant.linearalgebra.LinearizationOutput lo =
			org.drip.quant.linearalgebra.LinearSystemSolver.SolveUsingMatrixInversion (aadblM, adblY);

		if (null == lo) return null;

		double[] adblSlopeDisplacement = lo.getTransformedRHS();

		if (null == adblSlopeDisplacement || adblSlopeDisplacement.length != iNumSegment + 1) return null;

		for (int i = 0; i < iNumSegment; ++i)
			adblSlope[i] = adblSlopeDisplacement[i];

		try {
			return new R1PiecewiseDisplaced (dblLeftPredictorOrdinateEdge,
				dblRightPredictorOrdinateEdge, adblPredictorOrdinate, adblSlope,
					adblSlopeDisplacement[iNumSegment]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * R1PiecewiseDisplaced Constructor
	 * 
	 * @param dblLeftPredictorOrdinateEdge Left Predictor Ordinate Edge
	 * @param dblRightPredictorOrdinateEdge Right Predictor Ordinate Edge
	 * @param adblPredictorOrdinate Array of Intermediate Predictor Ordinates
	 * @param adblPiecewiseDensitySlope Array of corresponding Piece-wise Density Slopes
	 * @param dblDensityDisplacement Uniform Density Displacement
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public R1PiecewiseDisplaced (
		final double dblLeftPredictorOrdinateEdge,
		final double dblRightPredictorOrdinateEdge,
		final double[] adblPredictorOrdinate,
		final double[] adblPiecewiseDensitySlope,
		final double dblDensityDisplacement)
		throws java.lang.Exception
	{
		super (dblLeftPredictorOrdinateEdge, dblRightPredictorOrdinateEdge);

		if (null == (_adblPredictorOrdinate = adblPredictorOrdinate) || null == (_adblPiecewiseDensitySlope =
			adblPiecewiseDensitySlope) || !org.drip.quant.common.NumberUtil.IsValid (_dblDensityDisplacement
				= dblDensityDisplacement))
			throw new java.lang.Exception ("R1PiecewiseDisplaced Constructor: Invalid Inputs");

		int iNumPredictorOrdinate = _adblPredictorOrdinate.length;

		if (0 == iNumPredictorOrdinate || iNumPredictorOrdinate + 1 != _adblPiecewiseDensitySlope.length)
			throw new java.lang.Exception ("R1PiecewiseDisplaced Constructor: Invalid Inputs");
	}

	/**
	 * Retrieve the Array of Predictor Ordinates
	 * 
	 * @return The Array of Predictor Ordinates
	 */

	public double[] predictorOrdinates()
	{
		return _adblPredictorOrdinate;
	}

	/**
	 * Retrieve the Array of Piecewise Density Slopes
	 * 
	 * @return The Array of Piecewise Density Slopes
	 */

	public double[] piecewiseDensitySlopes()
	{
		return _adblPiecewiseDensitySlope;
	}

	/**
	 * Retrieve the Density Displacement
	 * 
	 * @return The Density Displacement
	 */

	public double densityDisplacement()
	{
		return _dblDensityDisplacement;
	}

	@Override public double cumulative (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception ("R1PiecewiseDisplaced::cumulative => Invalid Inputs");

		double dblLeftEdge = leftEdge();

		double dblRightEdge = rightEdge();

		if (dblX <= dblLeftEdge) return 0.;

		if (dblX >= dblRightEdge) return 1.;

		int iSegmentIndex = 0;
		double dblSegmentLeft = dblLeftEdge;
		double dblCumulativeProbability = 0.;
		int iMaxSegmentIndex = _adblPiecewiseDensitySlope.length - 1;

		while (iSegmentIndex < iMaxSegmentIndex) {
			double dblSegmentRight = _adblPredictorOrdinate[iSegmentIndex];

			if (dblX >= dblSegmentLeft && dblX <= dblSegmentRight)
				return dblCumulativeProbability + 0.5 * _adblPiecewiseDensitySlope[iSegmentIndex] * (dblX *
					dblX - dblSegmentLeft * dblSegmentLeft) + _dblDensityDisplacement * (dblX -
						dblSegmentLeft);

			dblCumulativeProbability += (0.5 * _adblPiecewiseDensitySlope[iSegmentIndex] * (dblSegmentRight *
				dblSegmentRight - dblSegmentLeft * dblSegmentLeft) + _dblDensityDisplacement *
					(dblSegmentRight - dblSegmentLeft));
			dblSegmentLeft = dblSegmentRight;
			++iSegmentIndex;
		}

		return dblCumulativeProbability + 0.5 * _adblPiecewiseDensitySlope[iMaxSegmentIndex] * (dblX * dblX -
			_adblPredictorOrdinate[iMaxSegmentIndex - 1] * _adblPredictorOrdinate[iMaxSegmentIndex - 1]) +
				_dblDensityDisplacement * (dblX - _adblPredictorOrdinate[iMaxSegmentIndex - 1]);
	}

	@Override public double invCumulative (
		final double dblY)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblY) || dblY < 0. || dblY > 1.)
			throw new java.lang.Exception ("R1PiecewiseDisplaced::invCumulative => Invalid inputs");

		org.drip.function.definition.R1ToR1 r1ToR1CumulativeProbability = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				return cumulative (dblX);
			}
		};

		org.drip.function.r1tor1solver.FixedPointFinderOutput fpfo = new
			org.drip.function.r1tor1solver.FixedPointFinderBracketing (dblY, r1ToR1CumulativeProbability,
				null, org.drip.function.r1tor1solver.VariateIteratorPrimitive.BISECTION, true).findRoot
					(org.drip.function.r1tor1solver.InitializationHeuristics.FromHardSearchEdges (leftEdge(),
						rightEdge()));

		if (null == fpfo || !fpfo.containsRoot())
			throw new java.lang.Exception ("R1PiecewiseDisplaced::invCumulative => No roots");

		return fpfo.getRoot();
	}

	@Override public double density (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception ("R1PiecewiseDisplaced::density => Invalid Inputs");

		if (dblX <= leftEdge() || dblX >= rightEdge()) return 0.;

		int iSegmentIndex = 0;
		int iMaxSegmentIndex = _adblPiecewiseDensitySlope.length - 1;

		while (iSegmentIndex < iMaxSegmentIndex) {
			if (dblX >= _adblPredictorOrdinate[iSegmentIndex] && dblX <=
				_adblPredictorOrdinate[iSegmentIndex + 1])
				break;

			++iSegmentIndex;
		}

		return _adblPiecewiseDensitySlope[iSegmentIndex] * dblX + _dblDensityDisplacement;
	}

	@Override public org.drip.quant.common.Array2D histogram()
	{
		double dblLeftEdge = leftEdge();

		double[] adblX = new double[GRID_WIDTH];
		double[] adblY = new double[GRID_WIDTH];

		double dblWidth = (rightEdge() - dblLeftEdge) / GRID_WIDTH;

		for (int i = 0; i < GRID_WIDTH; ++i) {
			adblX[i] = dblLeftEdge + (i + 1) * dblWidth;

			try {
				adblY[i] = incremental (adblX[i] - dblWidth, adblX[i]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return org.drip.quant.common.Array2D.FromArray (adblX, adblY);
	}
}
