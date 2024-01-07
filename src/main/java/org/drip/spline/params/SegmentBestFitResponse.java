
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
 * <i>SegmentBestFitResponse</i> implements basis per-segment Fitness Penalty Parameter Set. Currently it
 * 	contains the Best Fit Penalty Weight Grid Matrix and the corresponding Segment Local Predictor
 * 	Ordinate/Response Match Pair.
 *
 * <br>
 *  <ul>
 * 		<li>Construct the <i>SegmentBestFitResponse</i> Instance from the given Inputs</li>
 * 		<li>Construct the <i>SegmentBestFitResponse</i> Instance from the given Predictor Ordinate/Response Pairs, using Uniform Weightings</li>
 * 		<li>Retrieve the Array of the Fitness Weights</li>
 * 		<li>Retrieve the Indexed Fitness Weight Element</li>
 * 		<li>Retrieve the Array of Predictor Ordinates</li>
 * 		<li>Retrieve the Indexed Predictor Ordinate Element</li>
 * 		<li>Retrieve the Array of Responses</li>
 * 		<li>Retrieve the Indexed Response Element</li>
 * 		<li>Retrieve the Number of Fitness Points</li>
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

public class SegmentBestFitResponse
{
	private double[] _weightArray = null;
	private double[] _responseArray = null;
	private double[] _predictorOrdinateArray = null;

	/**
	 * Construct the <i>SegmentBestFitResponse</i> Instance from the given Inputs
	 * 
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param responseValueArray Array of Response Values
	 * @param weightArray Array of Weights
	 * 
	 * @return Instance of <i>SegmentBestFitResponse</i>
	 */

	public static final SegmentBestFitResponse Create (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray,
		final double[] weightArray)
	{
		SegmentBestFitResponse segmentBestFitResponse = null;

		try {
			segmentBestFitResponse = new SegmentBestFitResponse (
				weightArray,
				responseValueArray,
				predictorOrdinateArray
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return segmentBestFitResponse.normalizeWeights() ? segmentBestFitResponse : null;
	}

	/**
	 * Construct the <i>SegmentBestFitResponse</i> Instance from the given Predictor Ordinate/Response Pairs,
	 *  using Uniform Weightings.
	 * 
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param responseValueArray Array of Response Values
	 * 
	 * @return Instance of <i>SegmentBestFitResponse</i>
	 */

	public static final SegmentBestFitResponse Create (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray)
	{
		if (!NumberUtil.IsValid (predictorOrdinateArray)) {
			return null;
		}

		int weightCount = predictorOrdinateArray.length;
		double[] weightArray = new double[weightCount];

		for (int weightIndex = 0; weightIndex < weightCount; ++weightIndex) {
			weightArray[weightIndex] = 1.;
		}

		return Create (predictorOrdinateArray, responseValueArray, weightArray);
	}

	private SegmentBestFitResponse (
		final double[] weightArray,
		final double[] responseArray,
		final double[] predictorOrdinateArray)
		throws Exception
	{
		if (!NumberUtil.IsValid (_weightArray = weightArray) ||
			!NumberUtil.IsValid (_responseArray = responseArray) ||
			!NumberUtil.IsValid (_predictorOrdinateArray = predictorOrdinateArray)) {
			throw new Exception ("SegmentBestFitResponse ctr: Invalid Inputs");
		}

		int pointsToFit = _weightArray.length;

		if (0 == pointsToFit || _responseArray.length != pointsToFit ||
			_predictorOrdinateArray.length != pointsToFit) {
			throw new Exception ("SegmentBestFitResponse ctr: Invalid Inputs");
		}
	}

	private boolean normalizeWeights()
	{
		double cumulativeWeight = 0.;
		int pointsToFit = _weightArray.length;

		for (int pointIndex = 0; pointIndex < pointsToFit; ++pointIndex) {
			if (0. > _weightArray[pointIndex]) {
				return false;
			}

			cumulativeWeight += _weightArray[pointIndex];
		}

		if (0. >= cumulativeWeight) {
			return false;
		}

		for (int pointIndex = 0; pointIndex < pointsToFit; ++pointIndex) {
			_weightArray[pointIndex] /= cumulativeWeight;
		}

		return true;
	}

	/**
	 * Retrieve the Array of the Fitness Weights
	 * 
	 * @return The Array of the Fitness Weights
	 */

	public double[] weight()
	{
		return _weightArray;
	}

	/**
	 * Retrieve the Indexed Fitness Weight Element
	 * 
	 * @param index The Element Index
	 * 
	 * @return The Indexed Fitness Weight Element
	 * 
	 * @throws Exception Thrown if the Index is Invalid
	 */

	public double weight (
		final int index)
		throws Exception
	{
		if (index >= numPoint()) {
			throw new Exception ("SegmentBestFitResponse::weight => Invalid Index");
		}

		return _weightArray[index];
	}

	/**
	 * Retrieve the Array of Predictor Ordinates
	 * 
	 * @return The Array of Predictor Ordinates
	 */

	public double[] predictorOrdinate()
	{
		return _predictorOrdinateArray;
	}

	/**
	 * Retrieve the Indexed Predictor Ordinate Element
	 * 
	 * @param index The Element Index
	 * 
	 * @return The Indexed Predictor Ordinate Element
	 * 
	 * @throws Exception Thrown if the Index is Invalid
	 */

	public double predictorOrdinate (
		final int index)
		throws Exception
	{
		if (index >= numPoint()) {
			throw new Exception ("SegmentBestFitResponse::predictorOrdinate => Invalid Index");
		}

		return _predictorOrdinateArray[index];
	}

	/**
	 * Retrieve the Array of Responses
	 * 
	 * @return The Array of Responses
	 */

	public double[] response()
	{
		return _responseArray;
	}

	/**
	 * Retrieve the Indexed Response Element
	 * 
	 * @param index The Element Index
	 * 
	 * @return The Indexed Response Element
	 * 
	 * @throws Exception Thrown if the Index is Invalid
	 */

	public double response (
		final int index)
		throws Exception
	{
		if (index >= numPoint()) {
			throw new Exception ("SegmentBestFitResponse::response => Invalid Index");
		}

		return _responseArray[index];
	}

	/**
	 * Retrieve the Number of Fitness Points
	 * 
	 * @return The Number of Fitness Points
	 */

	public int numPoint()
	{
		return null == _responseArray ? 0 : _responseArray.length;
	}
}
