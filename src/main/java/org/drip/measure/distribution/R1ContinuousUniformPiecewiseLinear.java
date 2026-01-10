
package org.drip.measure.distribution;

import org.drip.function.definition.R1ToR1;
import org.drip.function.r1tor1solver.FixedPointFinderBracketing;
import org.drip.function.r1tor1solver.FixedPointFinderOutput;
import org.drip.function.r1tor1solver.VariateIteratorPrimitive;
import org.drip.numerical.common.Array2D;
import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
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
 * <i>R1ContinuousUniformPiecewiseLinear</i> implements the Piecewise Linear R<sup>1</sup> Distributions. It
 *  exports the Methods corresponding to the R<sup>1</sup> Lebesgue Base Class. It provides the following
 *  Functionality:
 *
 *  <ul>
 * 	    <li>Calibrate an <i>R1ContinuousUniformPiecewiseLinear</i> Lebesgue Instance</li>
 * 	    <li><i>R1ContinuousUniformPiecewiseLinear</i> Constructor</li>
 * 	    <li>Retrieve the Array of Predictor Ordinates</li>
 * 	    <li>Retrieve the Array of Piecewise Densities</li>
 * 	    <li>Compute the cumulative under the distribution to the given value</li>
 * 	    <li>Compute the inverse cumulative under the distribution corresponding to the given value</li>
 * 	    <li>Compute the Density under the Distribution at the given Variate</li>
 * 	    <li>Retrieve the Univariate Weighted Histogram</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/distribution/README.md">R<sup>1</sup> and R<sup>d</sup> Continuous Random Measure</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1ContinuousUniformPiecewiseLinear
	extends R1ContinuousUniform
{
	private double[] _piecewiseDensityArray = null;
	private double[] _predictorOrdinateArray = null;

	/**
	 * Calibrate an <i>R1ContinuousUniformPiecewiseLinear</i> Lebesgue Instance
	 * 
	 * @param leftPredictorOrdinateEdge Left Predictor Ordinate Edge
	 * @param rightPredictorOrdinateEdge Right Predictor Ordinate Edge
	 * @param predictorOrdinateArray Array of Intermediate Predictor Ordinates
	 * @param cumulativeProbabilityArray Array of corresponding Cumulative Probabilities
	 * 
	 * @return The <i>R1ContinuousUniformPiecewiseLinear</i> Instance
	 */

	public static final R1ContinuousUniformPiecewiseLinear Standard (
		final double leftPredictorOrdinateEdge,
		final double rightPredictorOrdinateEdge,
		final double[] predictorOrdinateArray,
		final double[] cumulativeProbabilityArray)
	{
		if (!NumberUtil.IsValid (leftPredictorOrdinateEdge) ||
			!NumberUtil.IsValid (rightPredictorOrdinateEdge) ||
			leftPredictorOrdinateEdge >= rightPredictorOrdinateEdge ||
			null == predictorOrdinateArray ||
			null == cumulativeProbabilityArray)
		{
			return null;
		}

		double[] piecewiseDensityArray = new double[predictorOrdinateArray.length + 1];

		if (0 == predictorOrdinateArray.length ||
			predictorOrdinateArray.length != cumulativeProbabilityArray.length)
		{
			return null;
		}

		for (int predictorIndex = 0; predictorIndex <= predictorOrdinateArray.length; ++predictorIndex) {
			double leftPredictorOrdinate = 0 == predictorIndex ?
				leftPredictorOrdinateEdge : predictorOrdinateArray[predictorIndex - 1];

			if (!NumberUtil.IsValid (leftPredictorOrdinate) ||
				leftPredictorOrdinate < leftPredictorOrdinateEdge)
			{
				return null;
			}

			double rightPredictorOrdinate = predictorOrdinateArray.length == predictorIndex ?
				rightPredictorOrdinateEdge : predictorOrdinateArray[predictorIndex];

			if (!NumberUtil.IsValid (rightPredictorOrdinate) ||
				rightPredictorOrdinate <= leftPredictorOrdinate ||
				rightPredictorOrdinate > rightPredictorOrdinateEdge)
			{
				return null;
			}

			double leftCumulativeProbability = 0 == predictorIndex ?
				0. : cumulativeProbabilityArray[predictorIndex - 1];
			double rightCumulativeProbability = predictorOrdinateArray.length == predictorIndex ?
				1. : cumulativeProbabilityArray[predictorIndex];
			piecewiseDensityArray[predictorIndex] =
				2. * (rightCumulativeProbability - leftCumulativeProbability) / (
					rightPredictorOrdinate * rightPredictorOrdinate -
						leftPredictorOrdinate * leftPredictorOrdinate
				);
		}

		try {
			return new R1ContinuousUniformPiecewiseLinear (
				leftPredictorOrdinateEdge,
				rightPredictorOrdinateEdge,
				predictorOrdinateArray,
				piecewiseDensityArray
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>R1ContinuousUniformPiecewiseLinear</i> Constructor
	 * 
	 * @param leftPredictorOrdinateEdge Left Predictor Ordinate Edge
	 * @param rightPredictorOrdinateEdge Right Predictor Ordinate Edge
	 * @param predictorOrdinateArray Array of Intermediate Predictor Ordinates
	 * @param piecewiseDensityArray Array of corresponding Piece-wise Densities
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	public R1ContinuousUniformPiecewiseLinear (
		final double leftPredictorOrdinateEdge,
		final double rightPredictorOrdinateEdge,
		final double[] predictorOrdinateArray,
		final double[] piecewiseDensityArray)
		throws Exception
	{
		super (leftPredictorOrdinateEdge, rightPredictorOrdinateEdge);

		if (null == (_predictorOrdinateArray = predictorOrdinateArray) ||
			null == (_piecewiseDensityArray = piecewiseDensityArray))
		{
			throw new Exception ("R1ContinuousUniformPiecewiseLinear Constructor: Invalid Inputs");
		}

		if (0 == _predictorOrdinateArray.length ||
			_predictorOrdinateArray.length + 1 != _piecewiseDensityArray.length)
		{
			throw new Exception ("R1ContinuousUniformPiecewiseLinear Constructor: Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of Predictor Ordinates
	 * 
	 * @return The Array of Predictor Ordinates
	 */

	public double[] predictorOrdinateArray()
	{
		return _predictorOrdinateArray;
	}

	/**
	 * Retrieve the Array of Piecewise Densities
	 * 
	 * @return The Array of Piecewise Densities
	 */

	public double[] piecewiseDensityArray()
	{
		return _piecewiseDensityArray;
	}

	/**
	 * Compute the cumulative under the distribution to the given value
	 * 
	 * @param x Variate to which the cumulative is to be computed
	 * 
	 * @return The cumulative
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	@Override public double cumulative (
		final double x)
		throws Exception
	{
		if (!NumberUtil.IsValid (x)) {
			throw new Exception ("R1ContinuousUniformPiecewiseLinear::cumulative => Invalid Inputs");
		}

		double leftEdge = leftSupport();

		double rightEdge = rightSupport();

		if (x <= leftEdge) {
			return 0.;
		}

		if (x >= rightEdge) {
			return 1.;
		}

		int segmentIndex = 0;
		double segmentLeft = leftEdge;
		double cumulativeProbability = 0.;
		int maxSegmentIndex = _piecewiseDensityArray.length - 1;

		while (segmentIndex < maxSegmentIndex) {
			double segmentRight = _predictorOrdinateArray[segmentIndex];

			if (x >= segmentLeft && x <= segmentRight) {
				return cumulativeProbability +
					0.5 * _piecewiseDensityArray[segmentIndex] * (x * x - segmentLeft * segmentLeft);
			}

			cumulativeProbability += 0.5 * _piecewiseDensityArray[segmentIndex] *
				(segmentRight * segmentRight - segmentLeft * segmentLeft);
			segmentLeft = segmentRight;
			++segmentIndex;
		}

		return cumulativeProbability + 0.5 * _piecewiseDensityArray[maxSegmentIndex] *
			(x * x - rightEdge * rightEdge);
	}

	/**
	 * Compute the inverse cumulative under the distribution corresponding to the given value
	 * 
	 * @param y Value corresponding to which the inverse cumulative is to be computed
	 * 
	 * @return The inverse cumulative
	 * 
	 * @throws Exception Thrown if the Input is invalid
	 */

	@Override public double invCumulative (
		final double y)
		throws Exception
	{
		if (!NumberUtil.IsValid (y) || y < 0. || y > 1.) {
			throw new Exception ("R1ContinuousUniformPiecewiseLinear::invCumulative => Invalid inputs");
		}

		R1ToR1 r1ToR1CumulativeProbability = new R1ToR1 (null) {
			@Override public double evaluate (
				final double x)
				throws Exception
			{
				return cumulative (x);
			}
		};

		FixedPointFinderOutput fixedPointFinderOutput = new FixedPointFinderBracketing (
			y,
			r1ToR1CumulativeProbability,
			null,
			VariateIteratorPrimitive.BISECTION,
			true
		).findRoot();

		if (null == fixedPointFinderOutput || !fixedPointFinderOutput.containsRoot()) {
			throw new Exception ("R1ContinuousUniformPiecewiseLinear::invCumulative => No roots");
		}

		return fixedPointFinderOutput.getRoot();
	}

	/**
	 * Compute the Density under the Distribution at the given Variate
	 * 
	 * @param x Variate at which the Density needs to be computed
	 * 
	 * @return The Density
	 * 
	 * @throws Exception Thrown if the input is invalid
	 */

	@Override public double density (
		final double x)
		throws Exception
	{
		if (!NumberUtil.IsValid (x)) {
			throw new Exception ("R1ContinuousUniformPiecewiseLinear::density => Invalid Inputs");
		}

		if (x <= leftSupport() || x >= rightSupport()) {
			return 0.;
		}

		int segmentIndex = 0;

		while (segmentIndex < _piecewiseDensityArray.length - 1) {
			if (x >= _predictorOrdinateArray[segmentIndex] && x <= _predictorOrdinateArray[segmentIndex + 1])
			{
				break;
			}

			++segmentIndex;
		}

		return _piecewiseDensityArray[segmentIndex] * x;
	}

	/**
	 * Retrieve the Univariate Weighted Histogram
	 * 
	 * @return The Univariate Weighted Histogram
	 */

	@Override public Array2D histogram()
	{
		double leftEdge = leftSupport();

		double[] xArray = new double[GRID_WIDTH];
		double[] yArray = new double[GRID_WIDTH];

		double width = (rightSupport() - leftEdge) / GRID_WIDTH;

		for (int gridIndex = 0; gridIndex < GRID_WIDTH; ++gridIndex) {
			xArray[gridIndex] = leftEdge + (gridIndex + 1) * width;

			try {
				yArray[gridIndex] = incremental (xArray[gridIndex] - width, xArray[gridIndex]);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return Array2D.FromArray (xArray, yArray);
	}
}
