
package org.drip.measure.lebesgue;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>R1PiecewiseLinear</i> implements the Piecewise Linear R<sup>1</sup> Distributions. It exports the
 * Methods corresponding to the R<sup>1</sup> Lebesgue Base Class.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/lebesgue">Lebesgue</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1PiecewiseLinear extends org.drip.measure.lebesgue.R1Uniform {
	private double[] _adblPiecewiseDensity = null;
	private double[] _adblPredictorOrdinate = null;

	/**
	 * Calibrate an R1PiecewiseLinear Lebesgue Instance
	 * 
	 * @param dblLeftPredictorOrdinateEdge Left Predictor Ordinate Edge
	 * @param dblRightPredictorOrdinateEdge Right Predictor Ordinate Edge
	 * @param adblPredictorOrdinate Array of Intermediate Predictor Ordinates
	 * @param adblCumulativeProbability Array of corresponding Cumulative Probabilities
	 * 
	 * @return The R1PiecewiseLinearLebesgue Instance
	 */

	public static final R1PiecewiseLinear Standard (
		final double dblLeftPredictorOrdinateEdge,
		final double dblRightPredictorOrdinateEdge,
		final double[] adblPredictorOrdinate,
		final double[] adblCumulativeProbability)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblLeftPredictorOrdinateEdge) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRightPredictorOrdinateEdge) ||
				dblLeftPredictorOrdinateEdge >= dblRightPredictorOrdinateEdge || null ==
					adblPredictorOrdinate || null == adblCumulativeProbability)
			return null;

		int iNumPredictorOrdinate = adblPredictorOrdinate.length;
		double[] adblPiecewiseDensity = new double[iNumPredictorOrdinate + 1];

		if (0 == iNumPredictorOrdinate || iNumPredictorOrdinate != adblCumulativeProbability.length)
			return null;

		for (int i = 0; i <= iNumPredictorOrdinate; ++i) {
			double dblLeftPredictorOrdinate = 0 == i ? dblLeftPredictorOrdinateEdge :
				adblPredictorOrdinate[i - 1];

			if (!org.drip.quant.common.NumberUtil.IsValid (dblLeftPredictorOrdinate) ||
				dblLeftPredictorOrdinate < dblLeftPredictorOrdinateEdge)
				return null;

			double dblRightPredictorOrdinate = iNumPredictorOrdinate == i ? dblRightPredictorOrdinateEdge :
				adblPredictorOrdinate[i];

			if (!org.drip.quant.common.NumberUtil.IsValid (dblRightPredictorOrdinate) ||
				dblRightPredictorOrdinate <= dblLeftPredictorOrdinate || dblRightPredictorOrdinate >
					dblRightPredictorOrdinateEdge)
				return null;

			double dblLeftCumulativeProbability = 0 == i ? 0. : adblCumulativeProbability[i - 1];
			double dblRightCumulativeProbability = iNumPredictorOrdinate == i ? 1. :
				adblCumulativeProbability[i];
			adblPiecewiseDensity[i] = 2. * (dblRightCumulativeProbability - dblLeftCumulativeProbability) /
				(dblRightPredictorOrdinate * dblRightPredictorOrdinate - dblLeftPredictorOrdinate *
					dblLeftPredictorOrdinate);
		}

		try {
			return new R1PiecewiseLinear (dblLeftPredictorOrdinateEdge,
				dblRightPredictorOrdinateEdge, adblPredictorOrdinate,  adblPiecewiseDensity);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * R1PiecewiseLinear Constructor
	 * 
	 * @param dblLeftPredictorOrdinateEdge Left Predictor Ordinate Edge
	 * @param dblRightPredictorOrdinateEdge Right Predictor Ordinate Edge
	 * @param adblPredictorOrdinate Array of Intermediate Predictor Ordinates
	 * @param adblPiecewiseDensity Array of corresponding Piece-wise Densities
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public R1PiecewiseLinear (
		final double dblLeftPredictorOrdinateEdge,
		final double dblRightPredictorOrdinateEdge,
		final double[] adblPredictorOrdinate,
		final double[] adblPiecewiseDensity)
		throws java.lang.Exception
	{
		super (dblLeftPredictorOrdinateEdge, dblRightPredictorOrdinateEdge);

		if (null == (_adblPredictorOrdinate = adblPredictorOrdinate) || null == (_adblPiecewiseDensity =
			adblPiecewiseDensity))
			throw new java.lang.Exception ("R1PiecewiseLinear Constructor: Invalid Inputs");

		int iNumPredictorOrdinate = _adblPredictorOrdinate.length;

		if (0 == iNumPredictorOrdinate || iNumPredictorOrdinate + 1 != _adblPiecewiseDensity.length)
			throw new java.lang.Exception ("R1PiecewiseLinear Constructor: Invalid Inputs");
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
	 * Retrieve the Array of Piecewise Densities
	 * 
	 * @return The Array of Piecewise Densities
	 */

	public double[] piecewiseDensities()
	{
		return _adblPiecewiseDensity;
	}

	@Override public double cumulative (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception ("R1PiecewiseLinear::cumulative => Invalid Inputs");

		double dblLeftEdge = leftEdge();

		double dblRightEdge = rightEdge();

		if (dblX <= dblLeftEdge) return 0.;

		if (dblX >= dblRightEdge) return 1.;

		int iSegmentIndex = 0;
		double dblSegmentLeft = dblLeftEdge;
		double dblCumulativeProbability = 0.;
		int iMaxSegmentIndex = _adblPiecewiseDensity.length - 1;

		while (iSegmentIndex < iMaxSegmentIndex) {
			double dblSegmentRight = _adblPredictorOrdinate[iSegmentIndex];

			if (dblX >= dblSegmentLeft && dblX <= dblSegmentRight)
				return dblCumulativeProbability + 0.5 * _adblPiecewiseDensity[iSegmentIndex] * (dblX * dblX -
					dblSegmentLeft * dblSegmentLeft);

			dblCumulativeProbability += 0.5 * _adblPiecewiseDensity[iSegmentIndex] * (dblSegmentRight *
				dblSegmentRight - dblSegmentLeft * dblSegmentLeft);
			dblSegmentLeft = dblSegmentRight;
			++iSegmentIndex;
		}

		return dblCumulativeProbability + 0.5 * _adblPiecewiseDensity[iMaxSegmentIndex] * (dblX * dblX -
			dblRightEdge * dblRightEdge);
	}

	@Override public double invCumulative (
		final double dblY)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblY) || dblY < 0. || dblY > 1.)
			throw new java.lang.Exception ("R1PiecewiseLinear::invCumulative => Invalid inputs");

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
				null, org.drip.function.r1tor1solver.VariateIteratorPrimitive.BISECTION, true).findRoot();

		if (null == fpfo || !fpfo.containsRoot())
			throw new java.lang.Exception ("R1PiecewiseLinear::invCumulative => No roots");

		return fpfo.getRoot();
	}

	@Override public double density (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception ("R1PiecewiseLinear::density => Invalid Inputs");

		if (dblX <= leftEdge() || dblX >= rightEdge()) return 0.;

		int iSegmentIndex = 0;
		int iMaxSegmentIndex = _adblPiecewiseDensity.length - 1;

		while (iSegmentIndex < iMaxSegmentIndex) {
			if (dblX >= _adblPredictorOrdinate[iSegmentIndex] && dblX <=
				_adblPredictorOrdinate[iSegmentIndex + 1])
				break;

			++iSegmentIndex;
		}

		return _adblPiecewiseDensity[iSegmentIndex] * dblX;
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
