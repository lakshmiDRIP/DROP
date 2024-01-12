
package org.drip.spline.segment;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.integration.R1ToR1Integrator;
import org.drip.spline.params.SegmentBestFitResponse;
import org.drip.spline.params.SegmentFlexurePenaltyControl;

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
 * <i>BestFitFlexurePenalizer</i> implements the Segment's Best Fit, Curvature, and Length Penalizers. It
 * 	provides the following functionality:
 *
 * <br>
 *  <ul>
 *  	<li><i>BestFitFlexurePenalizer</i> Constructor</li>
 *  	<li>Compute the Cross-Curvature Penalty for the given Basis Pair</li>
 *  	<li>Compute the Cross-Length Penalty for the given Basis Pair</li>
 *  	<li>Compute the Best Fit Cross-Product Penalty for the given Basis Pair</li>
 *  	<li>Compute the Basis Pair Penalty Coefficient for the Best Fit and the Curvature Penalties</li>
 *  	<li>Compute the Penalty Constraint for the Basis Pair</li>
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

public class BestFitFlexurePenalizer
{
	private BasisEvaluator _basisEvaluator = null;
	private LatentStateInelastic _latentStateInelastic = null;
	private SegmentBestFitResponse _segmentBestFitResponse = null;
	private SegmentFlexurePenaltyControl _lengthSegmentFlexurePenaltyControl = null;
	private SegmentFlexurePenaltyControl _curvatureSegmentFlexurePenaltyControl = null;

	/**
	 * <i>BestFitFlexurePenalizer</i> constructor
	 * 
	 * @param latentStateInelastic Segment Inelastics
	 * @param curvatureSegmentFlexurePenaltyControl Curvature Penalty Parameters
	 * @param lengthSegmentFlexurePenaltyControl Length Penalty Parameters
	 * @param segmentBestFitResponse Best Fit Weighted Response
	 * @param basisEvaluator The Local Basis Evaluator
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BestFitFlexurePenalizer (
		final LatentStateInelastic latentStateInelastic,
		final SegmentFlexurePenaltyControl curvatureSegmentFlexurePenaltyControl,
		final SegmentFlexurePenaltyControl lengthSegmentFlexurePenaltyControl,
		final SegmentBestFitResponse segmentBestFitResponse,
		final BasisEvaluator basisEvaluator)
		throws Exception
	{
		if (null == (_basisEvaluator = basisEvaluator) ||
			null == (_latentStateInelastic = latentStateInelastic)) {
			throw new Exception ("BestFitFlexurePenalizer ctr: Invalid Inputs");
		}

		_segmentBestFitResponse = segmentBestFitResponse;
		_lengthSegmentFlexurePenaltyControl = lengthSegmentFlexurePenaltyControl;
		_curvatureSegmentFlexurePenaltyControl = curvatureSegmentFlexurePenaltyControl;
	}

	/**
	 * Compute the Cross-Curvature Penalty for the given Basis Pair
	 * 
	 * @param basisIndexI I Basis Index (I is the Summation Index)
	 * @param basisIndexR R Basis Index (R is the Separator Index)
	 * 
	 * @return The Cross-Curvature Penalty for the given Basis Pair
	 * 
	 * @throws Exception Thrown if the Cross-Curvature Penalty cannot be computed
	 */

	public double basisPairCurvaturePenalty (
		final int basisIndexI,
		final int basisIndexR)
		throws Exception
	{
		if (null == _curvatureSegmentFlexurePenaltyControl) {
			return 0.;
		}

		R1ToR1 curvaturePenaltyFunction = new R1ToR1 (null) {
			@Override public double evaluate (
				final double variate)
				throws Exception
			{
				int order = _curvatureSegmentFlexurePenaltyControl.derivativeOrder();

				return _basisEvaluator.shapedBasisFunctionDerivative (variate, order, basisIndexI) *
					_basisEvaluator.shapedBasisFunctionDerivative (variate, order, basisIndexR);
			}

			@Override public double integrate (
				final double begin,
				final double end)
				throws Exception
			{
				return R1ToR1Integrator.Boole (this, begin, end);
			}
		};

		return _curvatureSegmentFlexurePenaltyControl.amplitude() * curvaturePenaltyFunction.integrate (
			_latentStateInelastic.left(),
			_latentStateInelastic.right()
			);
	}

	/**
	 * Compute the Cross-Length Penalty for the given Basis Pair
	 * 
	 * @param basisIndexI I Basis Index (I is the Summation Index)
	 * @param basisIndexR R Basis Index (R is the Separator Index)
	 * 
	 * @return The Cross-Length Penalty for the given Basis Pair
	 * 
	 * @throws Exception Thrown if the Cross-Length Penalty cannot be computed
	 */

	public double basisPairLengthPenalty (
		final int basisIndexI,
		final int basisIndexR)
		throws Exception
	{
		if (null == _lengthSegmentFlexurePenaltyControl) {
			return 0.;
		}

		R1ToR1 lengthPenaltyFunction = new R1ToR1 (null) {
			@Override public double evaluate (
				final double variate)
				throws Exception
			{
				int order = _lengthSegmentFlexurePenaltyControl.derivativeOrder();

				return _basisEvaluator.shapedBasisFunctionDerivative (variate, order, basisIndexI) *
					_basisEvaluator.shapedBasisFunctionDerivative (variate, order, basisIndexR);
			}

			@Override public double integrate (
				final double begin,
				final double end)
				throws java.lang.Exception
			{
				return R1ToR1Integrator.Boole (this, begin, end);
			}
		};

		return _lengthSegmentFlexurePenaltyControl.amplitude() * lengthPenaltyFunction.integrate (
			_latentStateInelastic.left(),
			_latentStateInelastic.right()
		);
	}

	/**
	 * Compute the Best Fit Cross-Product Penalty for the given Basis Pair
	 * 
	 * @param basisIndexI I Basis Index (I is the Summation Index)
	 * @param basisIndexR R Basis Index (R is the Separator Index)
	 * 
	 * @return The Best Fit Cross-Product Penalty for the given Basis Pair
	 * 
	 * @throws Exception Thrown if the Best Fit Cross-Product Penalty cannot be computed
	 */

	public double basisBestFitPenalty (
		final int basisIndexI,
		final int basisIndexR)
		throws Exception
	{
		if (null == _segmentBestFitResponse) {
			return 0.;
		}

		int pointCount = _segmentBestFitResponse.numPoint();

		if (0 == pointCount) {
			return 0.;
		}

		double basisPairFitnessPenalty = 0.;

		for (int pointIndex = 0; pointIndex < pointCount; ++pointIndex) {
			double predictorOrdinate = _segmentBestFitResponse.predictorOrdinate (pointIndex);

			basisPairFitnessPenalty += _segmentBestFitResponse.weight (pointIndex) *
				_basisEvaluator.shapedBasisFunctionResponse (predictorOrdinate, basisIndexI) *
				_basisEvaluator.shapedBasisFunctionResponse (predictorOrdinate, basisIndexR);
		}

		return basisPairFitnessPenalty / pointCount;
	}

	/**
	 * Compute the Basis Pair Penalty Coefficient for the Best Fit and the Curvature Penalties
	 * 
	 * @param basisIndexI I Basis Index (I is the Summation Index)
	 * @param basisIndexR R Basis Index (R is the Separator Index)
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 * 
	 * @return The Basis Pair Penalty Coefficient for the Fitness and the Curvature Penalties
	 */

	public double basisPairConstraintCoefficient (
		final int basisIndexI,
		final int basisIndexR)
		throws Exception
	{
		return basisPairCurvaturePenalty (basisIndexI, basisIndexR) +
			basisPairLengthPenalty (basisIndexI, basisIndexR) +
			basisBestFitPenalty (basisIndexI, basisIndexR);
	}

	/**
	 * Compute the Penalty Constraint for the Basis Pair
	 * 
	 * @param basisIndexR R Basis Index (R is the Separator Index)
	 * 
	 * @return Penalty Constraint for the Basis Pair
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	public double basisPairPenaltyConstraint (
		final int basisIndexR)
		throws Exception
	{
		if (null == _segmentBestFitResponse) {
			return 0.;
		}

		int pointCount = _segmentBestFitResponse.numPoint();

		if (0 == pointCount) {
			return 0.;
		}

		double basisPairPenaltyConstraint = 0.;

		for (int pointIndex = 0; pointIndex < pointCount; ++pointIndex) {
			double predictorOrdinate = _segmentBestFitResponse.predictorOrdinate (pointIndex);

			basisPairPenaltyConstraint += _segmentBestFitResponse.weight (pointIndex) *
				_basisEvaluator.shapedBasisFunctionResponse (predictorOrdinate, basisIndexR) *
				_segmentBestFitResponse.response (pointIndex);
		}

		return basisPairPenaltyConstraint / pointCount;
	}
}
