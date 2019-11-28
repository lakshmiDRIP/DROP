
package org.drip.spline.params;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>StretchBestFitResponse</i> implements basis per-Stretch Fitness Penalty Parameter Set. Currently it
 * contains the Best Fit Penalty Weight Grid Matrix and the corresponding Local Predictor Ordinate/Response
 * Match Pair. StretchBestFitResponse exports the following methods:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Retrieve the Array of the Fitness Weights.
 *  	</li>
 *  	<li>
 *  		Retrieve the Indexed Fitness Weight Element.
 *  	</li>
 *  	<li>
 *  		Retrieve the Array of Predictor Ordinates.
 *  	</li>
 *  	<li>
 *  		Retrieve the Indexed Predictor Ordinate Element.
 *  	</li>
 *  	<li>
 *  		Retrieve the Array of Responses.
 *  	</li>
 *  	<li>
 *  		Retrieve the Indexed Response Element.
 *  	</li>
 *  	<li>
 *  		Retrieve the Number of Fitness Points.
 *  	</li>
 *  	<li>
 *  		Generate the Segment Local Best Fit Weighted Response contained within the specified Segment.
 *  	</li>
 *  	<li>
 *  		Construct the StretchBestFitResponse Instance from the given Inputs.
 *  	</li>
 *  	<li>
 *  		Construct the StretchBestFitResponse Instance from the given Predictor Ordinate/Response Pairs,
 *  			using Uniform Weightings.
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params/README.md">Spline Segment Construction Control Parameters</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class StretchBestFitResponse {
	private double[] _adblWeight = null;
	private double[] _adblResponse = null;
	private double[] _adblPredictorOrdinate = null;

	/**
	 * Construct the StretchBestFitResponse Instance from the given Inputs
	 * 
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * @param adblWeight Array of Weights
	 * 
	 * @return Instance of StretchBestFitResponse
	 */

	public static final StretchBestFitResponse Create (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue,
		final double[] adblWeight)
	{
		StretchBestFitResponse frp = null;

		try {
			frp = new StretchBestFitResponse (adblWeight, adblResponseValue, adblPredictorOrdinate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return frp.normalizeWeights() ? frp : null;
	}

	/**
	 * Construct the StretchBestFitResponse Instance from the given Inputs
	 * 
	 * @param aiPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * @param adblWeight Array of Weights
	 * 
	 * @return Instance of StretchBestFitResponse
	 */

	public static final StretchBestFitResponse Create (
		final int[] aiPredictorOrdinate,
		final double[] adblResponseValue,
		final double[] adblWeight)
	{
		if (null == aiPredictorOrdinate) return null;

		int iNumPredictorOrdinate = aiPredictorOrdinate.length;
		double[] adblPredictorOrdinate = new double[iNumPredictorOrdinate];

		if (0 == iNumPredictorOrdinate) return null;

		for (int i = 0; i < iNumPredictorOrdinate; ++i)
			adblPredictorOrdinate[i] = aiPredictorOrdinate[i];

		return Create (adblPredictorOrdinate, adblResponseValue, adblWeight);
	}

	/**
	 * Construct the StretchBestFitResponse Instance from the given Predictor Ordinate/Response Pairs, using
	 * 	Uniform Weightings.
	 * 
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * 
	 * @return Instance of StretchBestFitResponse
	 */

	public static final StretchBestFitResponse Create (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (adblPredictorOrdinate)) return null;

		int iNumWeight = adblPredictorOrdinate.length;
		double[] adblWeight = new double[iNumWeight];

		for (int i = 0; i < iNumWeight; ++i)
			adblWeight[i] = 1.;

		return Create (adblPredictorOrdinate, adblResponseValue, adblWeight);
	}

	private StretchBestFitResponse (
		final double[] adblWeight,
		final double[] adblResponse,
		final double[] adblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_adblWeight = adblWeight) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_adblResponse = adblResponse) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_adblPredictorOrdinate = adblPredictorOrdinate))
			throw new java.lang.Exception ("StretchBestFitResponse ctr: Invalid Inputs");

		int iNumPointsToFit = _adblWeight.length;

		if (0 == iNumPointsToFit || _adblResponse.length != iNumPointsToFit || _adblPredictorOrdinate.length
			!= iNumPointsToFit)
			throw new java.lang.Exception ("StretchBestFitResponse ctr: Invalid Inputs");
	}

	private boolean normalizeWeights()
	{
		double dblCumulativeWeight = 0.;
		int iNumPointsToFit = _adblWeight.length;

		for (int i = 0; i < iNumPointsToFit; ++i) {
			if (_adblWeight[i] < 0.) return false;

			dblCumulativeWeight += _adblWeight[i];
		}

		if (0. >= dblCumulativeWeight) return false;

		for (int i = 0; i < iNumPointsToFit; ++i)
			_adblWeight[i] /= dblCumulativeWeight;

		return true;
	}

	/**
	 * Retrieve the Array of the Fitness Weights
	 * 
	 * @return The Array of the Fitness Weights
	 */

	public double[] weight()
	{
		return _adblWeight;
	}

	/**
	 * Retrieve the Indexed Fitness Weight Element
	 * 
	 * @param iIndex The Element Index
	 * 
	 * @return The Indexed Fitness Weight Element
	 * 
	 * @throws java.lang.Exception Thrown if the Index is Invalid
	 */

	public double weight (
		final int iIndex)
		throws java.lang.Exception
	{
		if (iIndex >= numPoint())
			throw new java.lang.Exception ("StretchBestFitResponse::weight => Invalid Index");

		return _adblWeight[iIndex];
	}

	/**
	 * Retrieve the Array of Predictor Ordinates
	 * 
	 * @return The Array of Predictor Ordinates
	 */

	public double[] predictorOrdinate()
	{
		return _adblPredictorOrdinate;
	}

	/**
	 * Retrieve the Indexed Predictor Ordinate Element
	 * 
	 * @param iIndex The Element Index
	 * 
	 * @return The Indexed Predictor Ordinate Element
	 * 
	 * @throws java.lang.Exception Thrown if the Index is Invalid
	 */

	public double predictorOrdinate (
		final int iIndex)
		throws java.lang.Exception
	{
		if (iIndex >= numPoint())
			throw new java.lang.Exception ("StretchBestFitResponse::predictorOrdinate => Invalid Index");

		return _adblPredictorOrdinate[iIndex];
	}

	/**
	 * Retrieve the Array of Responses
	 * 
	 * @return The Array of Responses
	 */

	public double[] response()
	{
		return _adblResponse;
	}

	/**
	 * Retrieve the Indexed Response Element
	 * 
	 * @param iIndex The Element Index
	 * 
	 * @return The Indexed Response Element
	 * 
	 * @throws java.lang.Exception Thrown if the Index is Invalid
	 */

	public double response (
		final int iIndex)
		throws java.lang.Exception
	{
		if (iIndex >= numPoint())
			throw new java.lang.Exception ("StretchBestFitResponse::response => Invalid Index");

		return _adblResponse[iIndex];
	}

	/**
	 * Retrieve the Number of Fitness Points
	 * 
	 * @return The Number of Fitness Points
	 */

	public int numPoint()
	{
		return null == _adblResponse ? 0 : _adblResponse.length;
	}

	/**
	 * Generate the Segment Local Best Fit Weighted Response contained within the specified Segment
	 * 
	 * @param ics The Inelastics Instance to be used for the Localization
	 * 
	 * @return The Segment Local Best Fit Weighted Response
	 */

	public SegmentBestFitResponse sizeToSegment (
		final org.drip.spline.segment.LatentStateInelastic ics)
	{
		if (null == ics) return null;

		int iNumPoint = numPoint();

		java.util.List<java.lang.Integer> lsIndex = new java.util.ArrayList<java.lang.Integer>();

		for (int i = 0; i < iNumPoint; ++i) {
			try {
				if (ics.in (_adblPredictorOrdinate[i])) lsIndex.add (i);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		int iNumLocalPoint = lsIndex.size();

		if (0 == iNumLocalPoint) return null;

		int iIndex = 0;
		double[] adblWeight = new double[iNumLocalPoint];
		double[] adblResponse = new double[iNumLocalPoint];
		double[] adblPredictor = new double[iNumLocalPoint];

		for (int i : lsIndex) {
			adblWeight[iIndex] = _adblWeight[i];
			adblResponse[iIndex] = _adblResponse[i];
			adblPredictor[iIndex++] = _adblPredictorOrdinate[i];
		}

		return org.drip.spline.params.SegmentBestFitResponse.Create (adblPredictor, adblResponse,
			adblWeight);
	}
}
