	
package org.drip.spline.stretch;

import org.drip.function.definition.R1ToR1;
import org.drip.function.r1tor1solver.FixedPointFinderBrent;
import org.drip.function.r1tor1solver.FixedPointFinderOutput;
import org.drip.function.r1tor1solver.InitializationHeuristics;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.differentiation.WengertJacobian;
import org.drip.numerical.integration.R1ToR1Integrator;
import org.drip.spline.params.SegmentResponseValueConstraint;
import org.drip.spline.params.StretchBestFitResponse;
import org.drip.spline.segment.Monotonocity;

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
 * <i>SingleSegmentLagrangePolynomial</i> implements the SingleSegmentSequence Stretch interface using the
 * 	Lagrange Polynomial Estimator. As such it provides a perfect fit that travels through all the
 * 	predictor/response pairs causing Runge's instability.
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/README.md">Multi-Segment Sequence Spline Stretch</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SingleSegmentLagrangePolynomial
	implements SingleSegmentSequence
{
	private static final double DIFF_SCALE = 1.0e-06;
	private static final int MAXIMA_PREDICTOR_ORDINATE_NODE = 1;
	private static final int MINIMA_PREDICTOR_ORDINATE_NODE = 2;
	private static final int MONOTONE_PREDICTOR_ORDINATE_NODE = 4;

	private double[] _responseValueArray = null;
	private double[] _predictorOrdinateArray = null;

	private static final double CalcAbsoluteMin (
		final double[] yArray)
		throws Exception
	{
		if (null == yArray) {
			throw new Exception ("SingleSegmentLagrangePolynomial::CalcAbsoluteMin => Invalid Inputs");
		}

		int pointCount = yArray.length;

		if (1 >= pointCount) {
			throw new Exception ("SingleSegmentLagrangePolynomial::CalcAbsoluteMin => Invalid Inputs");
		}

		double minimum = Math.abs (yArray[0]);

		for (int pointIndex = 0; pointIndex < pointCount; ++pointIndex) {
			double value = Math.abs (yArray[pointIndex]);

			minimum = minimum > value ? value : minimum;
		}

		return minimum;
	}

	private static final double CalcMinDifference (
		final double[] yArray)
		throws Exception
	{
		if (null == yArray) {
			throw new Exception ("SingleSegmentLagrangePolynomial::CalcMinDifference => Invalid Inputs");
		}

		int pointCount = yArray.length;

		if (1 >= pointCount) {
			throw new Exception ("SingleSegmentLagrangePolynomial::CalcMinDifference => Invalid Inputs");
		}

		double minimumDifference = Math.abs (yArray[0] - yArray[1]);

		for (int pointIndexI = 0; pointIndexI < pointCount; ++pointIndexI) {
			for (int pointIndexJ = pointIndexI + 1; pointIndexJ < pointCount; ++pointIndexJ) {
				double difference = Math.abs (yArray[pointIndexI] - yArray[pointIndexJ]);

				minimumDifference = minimumDifference > difference ? difference : minimumDifference;
			}
		}

		return minimumDifference;
	}

	private static final double EstimateBumpDelta (
		final double[] yArray)
		throws Exception
	{
		double bumpDelta = CalcMinDifference (yArray);

		if (!NumberUtil.IsValid (bumpDelta) || 0. == bumpDelta) {
			bumpDelta = CalcAbsoluteMin (yArray);
		}

		return 0. == bumpDelta ? DIFF_SCALE : bumpDelta * DIFF_SCALE;
	}

	/**
	 * <i>SingleSegmentLagrangePolynomial</i> constructor
	 * 
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * 
	 * @throws Exception Thrown if inputs are invalid
	 */

	public SingleSegmentLagrangePolynomial (
		final double[] predictorOrdinateArray)
		throws Exception
	{
		if (null == (_predictorOrdinateArray = predictorOrdinateArray)) {
			throw new Exception ("SingleSegmentLagrangePolynomial ctr: Invalid Inputs");
		}

		int predictorOrdinateCount = _predictorOrdinateArray.length;

		if (1 >= predictorOrdinateCount) {
			throw new Exception ("SingleSegmentLagrangePolynomial ctr: Invalid Inputs");
		}

		for (int predictorOrdinateIndexI = 0; predictorOrdinateIndexI < predictorOrdinateCount;
			++predictorOrdinateIndexI) {
			for (int predictorOrdinateIndexJ = predictorOrdinateIndexI + 1;
				predictorOrdinateIndexJ < predictorOrdinateCount; ++predictorOrdinateIndexJ) {
				if (_predictorOrdinateArray[predictorOrdinateIndexI] ==
					_predictorOrdinateArray[predictorOrdinateIndexJ]) {
					throw new Exception ("SingleSegmentLagrangePolynomial ctr: Invalid Inputs");
				}
			}
		}
	}

	@Override public boolean setup (
		final double leadingY,
		final double[] responseValueArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final BoundarySettings boundarySettings,
		final int calibrationDetail)
	{
		return null != (_responseValueArray = responseValueArray) &&
			_responseValueArray.length == _predictorOrdinateArray.length;
	}

	@Override public double responseValue (
		final double predictorOrdinate)
		throws Exception
	{
		if (!NumberUtil.IsValid (predictorOrdinate)) {
			throw new Exception ("SingleSegmentLagrangePolynomial::responseValue => Invalid inputs!");
		}

		int predictorOrdinateCount = _predictorOrdinateArray.length;

		if (_predictorOrdinateArray[0] > predictorOrdinate ||
			_predictorOrdinateArray[predictorOrdinateCount - 1] < predictorOrdinate) {
			throw new Exception ("SingleSegmentLagrangePolynomial::responseValue => Input out of range!");
		}

		double response = 0;

		for (int predictorOrdinateIndexI = 0; predictorOrdinateIndexI < predictorOrdinateCount;
			++predictorOrdinateIndexI) {
			double responsePredictorOrdinateContribution = _responseValueArray[predictorOrdinateIndexI];

			for (int predictorOrdinateIndexJ = 0; predictorOrdinateIndexJ < predictorOrdinateCount;
				++predictorOrdinateIndexJ) {
				if (predictorOrdinateIndexI != predictorOrdinateIndexJ) {
					responsePredictorOrdinateContribution = responsePredictorOrdinateContribution *
						(predictorOrdinate - _predictorOrdinateArray[predictorOrdinateIndexJ]) / (
							_predictorOrdinateArray[predictorOrdinateIndexI] -
							_predictorOrdinateArray[predictorOrdinateIndexJ]
						);
				}
			}

			response += responsePredictorOrdinateContribution;
		}

		return response;
	}

	@Override public double responseValueDerivative (
		final double predictorOrdinate,
		final int order)
		throws Exception
	{
		if (!NumberUtil.IsValid (predictorOrdinate) || 0 >= order) {
			throw new Exception (
				"SingleSegmentLagrangePolynomial::responseValueDerivative => Invalid inputs!"
			);
		}

		return new R1ToR1 (null) {
			@Override public double evaluate (
				double x)
				throws Exception
			{
				return responseValue (x);
			}
		}.derivative (predictorOrdinate, order);
	}

	@Override public WengertJacobian jackDResponseDCalibrationInput (
		final double predictorOrdinate,
		final int order)
	{
		if (!NumberUtil.IsValid (predictorOrdinate)) {
			return null;
		}

		double inputResponseSensitivityShift = Double.NaN;
		WengertJacobian responseToInputWengertJacobian = null;
		double responseWithUnadjustedResponseInput = Double.NaN;
		int predictorOrdinateCount = _predictorOrdinateArray.length;

		if (_predictorOrdinateArray[0] > predictorOrdinate ||
			_predictorOrdinateArray[predictorOrdinateCount - 1] < predictorOrdinate) {
			return null;
		}

		try {
			if (!NumberUtil.IsValid (
					inputResponseSensitivityShift = EstimateBumpDelta (_responseValueArray)
				) || !NumberUtil.IsValid (
					responseWithUnadjustedResponseInput = responseValue (predictorOrdinate)
				)
			) {
				return null;
			}

			responseToInputWengertJacobian = new WengertJacobian (1, predictorOrdinateCount);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int predictorOrdinateIndexI = 0; predictorOrdinateIndexI < predictorOrdinateCount;
			++predictorOrdinateIndexI) {
			double[] adblSensitivityShiftedInputResponse = new double[predictorOrdinateCount];

			for (int predictorOrdinateIndexJ = 0; predictorOrdinateIndexJ < predictorOrdinateCount;
				++predictorOrdinateIndexJ) {
				adblSensitivityShiftedInputResponse[predictorOrdinateIndexJ] =
					predictorOrdinateIndexI == predictorOrdinateIndexJ ?
						_responseValueArray[predictorOrdinateIndexJ] + inputResponseSensitivityShift :
						_responseValueArray[predictorOrdinateIndexJ];
			}

			try {
				SingleSegmentLagrangePolynomial singleSegmentLagrangePolynomial =
					new SingleSegmentLagrangePolynomial (_predictorOrdinateArray);

				if (!singleSegmentLagrangePolynomial.setup (
						adblSensitivityShiftedInputResponse[0],
						adblSensitivityShiftedInputResponse,
						null,
						BoundarySettings.FloatingStandard(),
						MultiSegmentSequence.CALIBRATE
					) || !responseToInputWengertJacobian.accumulatePartialFirstDerivative (
						0,
						predictorOrdinateIndexI,
						(
							singleSegmentLagrangePolynomial.responseValue (predictorOrdinate) -
							responseWithUnadjustedResponseInput
						) / inputResponseSensitivityShift
					)
				) {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return responseToInputWengertJacobian;
	}

	@Override public WengertJacobian jackDResponseDManifestMeasure (
		final String manifestMeasure,
		final double predictorOrdinate,
		final int order)
	{
		return null;
	}

	@Override public Monotonocity monotoneType (
		final double predictorOrdinate)
	{
		if (!NumberUtil.IsValid (predictorOrdinate)) {
			return null;
		}

		int predictorOrdinateCount = _predictorOrdinateArray.length;

		if (_predictorOrdinateArray[0] > predictorOrdinate ||
			_predictorOrdinateArray[predictorOrdinateCount - 1] < predictorOrdinate)
			return null;

		if (2 == predictorOrdinateCount) {
			try {
				return new Monotonocity (Monotonocity.MONOTONIC);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		try {
			FixedPointFinderOutput fixedPointFinderOutput = new FixedPointFinderBrent (
				0.,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double x)
						throws Exception
					{
						double deltaX = CalcMinDifference (_predictorOrdinateArray) * DIFF_SCALE;

						return (responseValue (x + deltaX) - responseValue (x)) / deltaX;
					}

					@Override public double integrate (
						final double begin,
						final double end)
						throws Exception
					{
						return R1ToR1Integrator.Boole (this, begin, end);
					}
				},
				true
			).findRoot (InitializationHeuristics.FromHardSearchEdges (0., 1.));

			if (null == fixedPointFinderOutput || !fixedPointFinderOutput.containsRoot()) {
				return new Monotonocity (Monotonocity.MONOTONIC);
			}

			double extremum = fixedPointFinderOutput.getRoot();

			if (!NumberUtil.IsValid (extremum) || 0. >= extremum || 1. <= extremum) {
				return new Monotonocity (Monotonocity.MONOTONIC);
			}

			double deltaX = CalcMinDifference (_predictorOrdinateArray) * DIFF_SCALE;

			double secondDerivative = responseValue (extremum + deltaX) + responseValue (extremum - deltaX) -
				2. * responseValue (predictorOrdinate);

			if (0. > secondDerivative) {
				return new Monotonocity (Monotonocity.MAXIMA);
			}

			if (0. < secondDerivative) {
				return new Monotonocity (Monotonocity.MINIMA);
			}

			if (0. == secondDerivative) {
				return new Monotonocity (Monotonocity.INFLECTION);
			}

			return new Monotonocity (Monotonocity.NON_MONOTONIC);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			return new Monotonocity (Monotonocity.MONOTONIC);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public boolean isLocallyMonotone()
		throws Exception
	{
		Monotonocity monotonocity = monotoneType (
			0.5 * (_predictorOrdinateArray[0] + _predictorOrdinateArray[_predictorOrdinateArray.length - 1])
		);

		return null != monotonocity && Monotonocity.MONOTONIC == monotonocity.type();
	}

	@Override public boolean isCoMonotone (
		final double[] measuredResponseArray)
		throws Exception
	{
		if (null == measuredResponseArray) {
			return false;
		}

		int measuredResponseCount = measuredResponseArray.length;

		if (2 >= measuredResponseCount) {
			return false;
		}

		int[] monotoneTypeArray = new int[measuredResponseCount];
		int[] miniMaxMarkerArray = new int[measuredResponseCount];

		for (int measuredResponseIndex = 0; measuredResponseIndex < measuredResponseCount;
			++measuredResponseIndex) {
			if (0 == measuredResponseIndex || measuredResponseCount - 1 == measuredResponseIndex) {
				miniMaxMarkerArray[measuredResponseIndex] = 0;
			} else {
				if (measuredResponseArray[measuredResponseIndex - 1] <
						measuredResponseArray[measuredResponseIndex] &&
					measuredResponseArray[measuredResponseIndex + 1] <
						measuredResponseArray[measuredResponseIndex]) {
					miniMaxMarkerArray[measuredResponseIndex] = MAXIMA_PREDICTOR_ORDINATE_NODE;
				} else if (measuredResponseArray[measuredResponseIndex - 1] >
						measuredResponseArray[measuredResponseIndex] &&
					measuredResponseArray[measuredResponseIndex + 1] >
						measuredResponseArray[measuredResponseIndex]) {
					miniMaxMarkerArray[measuredResponseIndex] = MINIMA_PREDICTOR_ORDINATE_NODE;
				} else {
					miniMaxMarkerArray[measuredResponseIndex] = MONOTONE_PREDICTOR_ORDINATE_NODE;
				}
			}

			Monotonocity monotonocity = monotoneType (measuredResponseArray[measuredResponseIndex]);

			monotoneTypeArray[measuredResponseIndex] = null != monotonocity ?
				monotonocity.type() : Monotonocity.NON_MONOTONIC;
		}

		for (int measuredResponseIndex = 1; measuredResponseIndex < measuredResponseCount - 1;
			++measuredResponseIndex) {
			if (MAXIMA_PREDICTOR_ORDINATE_NODE == miniMaxMarkerArray[measuredResponseIndex]) {
				if (Monotonocity.MAXIMA != monotoneTypeArray[measuredResponseIndex] &&
					Monotonocity.MAXIMA != monotoneTypeArray[measuredResponseIndex - 1]) {
					return false;
				}
			} else if (MINIMA_PREDICTOR_ORDINATE_NODE == miniMaxMarkerArray[measuredResponseIndex]) {
				if (Monotonocity.MINIMA != monotoneTypeArray[measuredResponseIndex] &&
					Monotonocity.MINIMA != monotoneTypeArray[measuredResponseIndex - 1]) {
					return false;
				}
			}
		}

		return true;
	}

	@Override public boolean isKnot (
		final double predictorOrdinate)
	{
		if (!NumberUtil.IsValid (predictorOrdinate)) {
			return false;
		}

		int predictorOrdinateCount = _predictorOrdinateArray.length;

		if (_predictorOrdinateArray[0] > predictorOrdinate ||
			_predictorOrdinateArray[predictorOrdinateCount - 1] < predictorOrdinate) {
			return false;
		}

		for (int predictorOrdinateIndex = 0; predictorOrdinateIndex < predictorOrdinateCount;
			++predictorOrdinateIndex) {
			if (predictorOrdinate == _predictorOrdinateArray[predictorOrdinateIndex]) {
				return true;
			}
		}

		return false;
	}

	@Override public boolean resetNode (
		final int predictorOrdinateNodeIndex,
		final double resetResponse)
	{
		if (!NumberUtil.IsValid (resetResponse) ||
			predictorOrdinateNodeIndex > _predictorOrdinateArray.length) {
			return false;
		}

		_responseValueArray[predictorOrdinateNodeIndex] = resetResponse;
		return true;
	}

	@Override public boolean resetNode (
		final int predictorOrdinateNodeIndex,
		final SegmentResponseValueConstraint resetSegmentResponseValueConstraint)
	{
		return false;
	}

	@Override public R1ToR1 toAU()
	{
		return new R1ToR1 (null) {
			@Override public double evaluate (
				final double variate)
				throws Exception
			{
				return responseValue (variate);
			}

			@Override public double derivative (
				final double variate,
				final int order)
				throws Exception
			{
				return responseValueDerivative (variate, order);
			}
		};
	}

	@Override public double getLeftPredictorOrdinateEdge()
	{
		return _predictorOrdinateArray[0];
	}

	@Override public double getRightPredictorOrdinateEdge()
	{
		return _predictorOrdinateArray[_predictorOrdinateArray.length - 1];
	}
}
