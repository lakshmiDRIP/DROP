
package org.drip.spline.params;

import org.drip.numerical.common.NumberUtil;

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
 * <i>SegmentPredictorResponseDerivative</i> contains the segment local parameters used for the segment
 * 	calibration. It holds the edge Y value and the derivatives. It exposes the following functions:
 *
 * <br>
 *  <ul>
 * 		<li>Aggregate the 2 Predictor Ordinate Response Derivatives by applying the Cardinal Tension Weight</li>
 * 		<li><i>SegmentPredictorResponseDerivative</i> constructor</li>
 * 		<li>Retrieve the Response Value</li>
 * 		<li>Retrieve the DResponseDPredictorOrdinate Array</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/README.md">Spline Segment Construction Control Parameters</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SegmentPredictorResponseDerivative
{
	private double _responseValue = Double.NaN;
	private double[] _dResponseDPredictorOrdinateArray = null;

	/**
	 * Aggregate the 2 Predictor Ordinate Response Derivatives by applying the Cardinal Tension Weight
	 * 
	 * @param segmentPredictorResponseDerivativeA Predictor Ordinate Response Derivative A
	 * @param segmentPredictorResponseDerivativeB Predictor Ordinate Response Derivative B
	 * @param cardinalTension Cardinal Tension
	 * 
	 * @return The Aggregated Predictor Ordinate Response Derivatives
	 */

	public static final SegmentPredictorResponseDerivative CardinalEdgeAggregate (
		final SegmentPredictorResponseDerivative segmentPredictorResponseDerivativeA,
		final SegmentPredictorResponseDerivative segmentPredictorResponseDerivativeB,
		final double cardinalTension)
	{
		if (null == segmentPredictorResponseDerivativeA || null == segmentPredictorResponseDerivativeB ||
			!NumberUtil.IsValid (cardinalTension)) {
			return null;
		}

		int derivativeCount = 0;

		double[] edgeDResponseDPredictorOrdinateAArray =
			segmentPredictorResponseDerivativeA.getDResponseDPredictorOrdinate();

		double[] edgeDResponseDPredictorOrdinateBArray =
			segmentPredictorResponseDerivativeB.getDResponseDPredictorOrdinate();

		if (
			(
				null != edgeDResponseDPredictorOrdinateAArray &&
				null == edgeDResponseDPredictorOrdinateBArray
			) || (
				null == edgeDResponseDPredictorOrdinateAArray &&
				null != edgeDResponseDPredictorOrdinateBArray
			) || (
				null != edgeDResponseDPredictorOrdinateAArray &&
				null != edgeDResponseDPredictorOrdinateBArray && (
					derivativeCount = edgeDResponseDPredictorOrdinateAArray.length
				) != edgeDResponseDPredictorOrdinateBArray.length
			)
		) {
			return null;
		}

		double aggregatedEdgeResponseValue = 0.5 * (1. - cardinalTension) * (
			segmentPredictorResponseDerivativeA.responseValue() +
			segmentPredictorResponseDerivativeB.responseValue()
		);

		if (null == edgeDResponseDPredictorOrdinateAArray || null == edgeDResponseDPredictorOrdinateBArray ||
			0 == derivativeCount) {
			try {
				return new SegmentPredictorResponseDerivative (aggregatedEdgeResponseValue, null);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		double[] edgeDResponseDPredictorOrdinateArray = new double[derivativeCount];

		for (int derivativeIndex = 0; derivativeIndex < derivativeCount; ++derivativeIndex) {
			edgeDResponseDPredictorOrdinateArray[derivativeIndex] = 0.5 * (1. - cardinalTension) * (
				edgeDResponseDPredictorOrdinateAArray[derivativeIndex] +
				edgeDResponseDPredictorOrdinateBArray[derivativeIndex]
			);
		}

		try {
			return new SegmentPredictorResponseDerivative (
				aggregatedEdgeResponseValue,
				edgeDResponseDPredictorOrdinateArray
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>SegmentPredictorResponseDerivative</i> constructor
	 * 
	 * @param responseValue Edge Response Value
	 * @param dResponseDPredictorOrdinateArray Array of ordered Edge Derivatives
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	public SegmentPredictorResponseDerivative (
		final double responseValue,
		final double[] dResponseDPredictorOrdinateArray)
		throws Exception
	{
		if (!NumberUtil.IsValid (_responseValue = responseValue)) {
			throw new Exception ("SegmentPredictorResponseDerivative ctr: Invalid Inputs!");
		}

		_dResponseDPredictorOrdinateArray = dResponseDPredictorOrdinateArray;
	}

	/**
	 * Retrieve the Response Value
	 * 
	 * @return The Response Value
	 */

	public double responseValue()
	{
		return _responseValue;
	}

	/**
	 * Retrieve the DResponseDPredictorOrdinate Array
	 * 
	 * @return DResponseDPredictorOrdinate Array
	 */

	public double[] getDResponseDPredictorOrdinate()
	{
		return _dResponseDPredictorOrdinateArray;
	}
}
