
package org.drip.spline.params;

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
 * <i>SegmentStateCalibrationInputs</i> implements basis per-segment Calibration Parameter Input Set. It
 * 	exposes the following functionality:
 *
 * <br>
 *  <ul>
 * 		<li><i>SegmentStateCalibrationInputs</i> Constructor</li>
 * 		<li>Retrieve the Array of the Calibration Predictor Ordinates</li>
 * 		<li>Retrieve the Array of the Calibration Response Values</li>
 * 		<li>Retrieve the Array of the Left Edge Derivatives</li>
 * 		<li>Retrieve the Array of the Right Edge Derivatives</li>
 * 		<li>Retrieve the Segment Best Fit Response</li>
 * 		<li>Retrieve the Array of Segment Basis Flexure Constraints</li>
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

public class SegmentStateCalibrationInputs
{
	private double[] _responseValueArray = null;
	private double[] _predictorOrdinateArray = null;
	private double[] _leftEdgeDerivativeArray = null;
	private double[] _rightEdgeDerivativeArray = null;
	private SegmentBestFitResponse _segmentBestFitResponse = null;
	private SegmentBasisFlexureConstraint[] _segmentBasisFlexureConstraintArray = null;

	/**
	 * <i>SegmentStateCalibrationInputs</i> Constructor
	 * 
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param responseValueArray Array of Response Values
	 * @param leftEdgeDerivativeArray Array of the Left Edge Derivatives
	 * @param rightEdgeDerivativeArray Array of the Right Edge  Derivatives
	 * @param segmentBasisFlexureConstraintArray Array of the Segment Basis Flexure Constraints
	 * @param segmentBestFitResponse Segment Basis Fit Response
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	public SegmentStateCalibrationInputs (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray,
		final double[] leftEdgeDerivativeArray,
		final double[] rightEdgeDerivativeArray,
		final SegmentBasisFlexureConstraint[] segmentBasisFlexureConstraintArray,
		final SegmentBestFitResponse segmentBestFitResponse)
		throws Exception
	{
		_segmentBestFitResponse = segmentBestFitResponse;
		int segmentBasisFlexureConstraintCount =
			null == (_segmentBasisFlexureConstraintArray = segmentBasisFlexureConstraintArray) ? 0 :
			_segmentBasisFlexureConstraintArray.length;
		int leftEdgeDerivativeCount = null == (_leftEdgeDerivativeArray = leftEdgeDerivativeArray) ? 0 :
			_leftEdgeDerivativeArray.length;
		int responseValueCount = null == (_responseValueArray = responseValueArray) ? 0 :
			_responseValueArray.length;
		int rightEdgeDerivativeCount = null == (_rightEdgeDerivativeArray = rightEdgeDerivativeArray) ? 0 :
			_rightEdgeDerivativeArray.length;
		int predictorOrdinateCount = null == (_predictorOrdinateArray = predictorOrdinateArray) ? 0 :
			_predictorOrdinateArray.length;

		if (null == _segmentBestFitResponse && null == _segmentBasisFlexureConstraintArray &&
			null == _predictorOrdinateArray && null == _responseValueArray &&
			null == _leftEdgeDerivativeArray && null == _rightEdgeDerivativeArray) {
			throw new Exception ("SegmentStateCalibrationInputs ctr: Invalid Inputs");
		}

		if (predictorOrdinateCount != responseValueCount || (
			null == _segmentBestFitResponse && 0 == segmentBasisFlexureConstraintCount &&
			0 == predictorOrdinateCount && 0 == leftEdgeDerivativeCount && 0 == rightEdgeDerivativeCount
		)) {
			throw new Exception ("SegmentStateCalibrationInputs ctr: Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of the Calibration Predictor Ordinates
	 * 
	 * @return The Array of the Calibration Predictor Ordinates
	 */

	public double[] predictorOrdinates()
	{
		return _predictorOrdinateArray;
	}

	/**
	 * Retrieve the Array of the Calibration Response Values
	 * 
	 * @return The Array of the Calibration Response Values
	 */

	public double[] responseValues()
	{
		return _responseValueArray;
	}

	/**
	 * Retrieve the Array of the Left Edge Derivatives
	 * 
	 * @return The Array of the Left Edge Derivatives
	 */

	public double[] leftEdgeDeriv()
	{
		return _leftEdgeDerivativeArray;
	}

	/**
	 * Retrieve the Array of the Right Edge Derivatives
	 * 
	 * @return The Array of the Right Edge Derivatives
	 */

	public double[] rightEdgeDeriv()
	{
		return _rightEdgeDerivativeArray;
	}

	/**
	 * Retrieve the Segment Best Fit Response
	 * 
	 * @return The Segment Best Fit Response
	 */

	public org.drip.spline.params.SegmentBestFitResponse bestFitResponse()
	{
		return _segmentBestFitResponse;
	}

	/**
	 * Retrieve the Array of Segment Basis Flexure Constraints
	 * 
	 * @return The Array of Segment Basis Flexure Constraints
	 */

	public org.drip.spline.params.SegmentBasisFlexureConstraint[] flexureConstraint()
	{
		return _segmentBasisFlexureConstraintArray;
	}
}
