
package org.drip.spline.pchip;

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
 * <i>AkimaLocalC1Generator</i> implements the regime using the Akima (1970) Local C<sup>1</sup> Generator.
 *
 * <br>
 *  <ul>
	 * <li>Construct an Instance of <i>AkimaLocalC1Generator</i> from the Array of the supplied Predictor Ordinates and the Response Values</li>
	 * <li>Generate the C<sup>1</sup> Array</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/pchip/README.md">Monotone Convex Themed PCHIP Splines</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class AkimaLocalC1Generator
{
	private double[] _responseValueArray = null;
	private double[] _predictorOrdinateArray = null;
	private double[] _extendedResponseValueArray = null;
	private double[] _extendedPredictorOrdinateArray = null;

	/**
	 * Construct an Instance of <i>AkimaLocalC1Generator</i> from the Array of the supplied Predictor
	 *  Ordinates and the Response Values
	 *  
	 * @param predictorOrdinateArray Array of the Predictor Ordinates
	 * @param responseValueArray Array of the Response Values
	 * 
	 * @return Instance of AkimaLocalC1Generator
	 */

	public static final AkimaLocalC1Generator Create (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray)
	{
		AkimaLocalC1Generator akimaLocalC1Generator = null;

		try {
			akimaLocalC1Generator = new AkimaLocalC1Generator (predictorOrdinateArray, responseValueArray);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return akimaLocalC1Generator.extendPredictorOrdinate() && akimaLocalC1Generator.extendResponseValue()
			? akimaLocalC1Generator : null;
	}

	private AkimaLocalC1Generator (
		final double[] predictorOrdinateArray,
		final double[] responseValueArray)
		throws Exception
	{
		if (null == (_predictorOrdinateArray = predictorOrdinateArray) ||
			null == (_responseValueArray = responseValueArray)) {
			throw new Exception ("AkimaLocalC1Generator ctr: Invalid Inputs");
		}

		int predictorOrdinateCount = _predictorOrdinateArray.length;

		if (2 >= predictorOrdinateCount || predictorOrdinateCount != _responseValueArray.length) {
			throw new Exception ("AkimaLocalC1Generator ctr: Invalid Inputs");
		}
	}

	private boolean extendPredictorOrdinate()
	{
		int predictorOrdinateCount = _predictorOrdinateArray.length;
		int extendedPredictorOrdinateCount = predictorOrdinateCount + 4;
		_extendedPredictorOrdinateArray = new double[extendedPredictorOrdinateCount];

		for (int extendedPredictorOrdinateIndex = 0;
			extendedPredictorOrdinateIndex < extendedPredictorOrdinateCount;
			++extendedPredictorOrdinateIndex) {
			if (2 <= extendedPredictorOrdinateIndex &&
				extendedPredictorOrdinateCount - 3 >= extendedPredictorOrdinateIndex) {
				_extendedPredictorOrdinateArray[extendedPredictorOrdinateIndex] =
					_predictorOrdinateArray[extendedPredictorOrdinateIndex - 2];
			}
		}

		double skippedLeftPredictorWidth = _predictorOrdinateArray[2] - _predictorOrdinateArray[0];
		_extendedPredictorOrdinateArray[0] = _predictorOrdinateArray[0] - skippedLeftPredictorWidth;
		_extendedPredictorOrdinateArray[1] = _predictorOrdinateArray[1] - skippedLeftPredictorWidth;
		double skippedRightPredictorWidth = _predictorOrdinateArray[predictorOrdinateCount - 1] -
			_predictorOrdinateArray[predictorOrdinateCount - 3];
		_extendedPredictorOrdinateArray[extendedPredictorOrdinateCount - 2] =
			_predictorOrdinateArray[predictorOrdinateCount - 2] + skippedRightPredictorWidth;
		_extendedPredictorOrdinateArray[extendedPredictorOrdinateCount - 1] =
			_predictorOrdinateArray[predictorOrdinateCount - 1] + skippedRightPredictorWidth;
		return true;
	}

	private boolean setExtendedResponseValue (
		final int extendedPredictorOrdinateIndex,
		final boolean right)
	{
		if (right) {
			_extendedResponseValueArray[extendedPredictorOrdinateIndex] = 2. * (
				_extendedResponseValueArray[extendedPredictorOrdinateIndex - 1] -
				_extendedResponseValueArray[extendedPredictorOrdinateIndex - 2]
			) / (
				_extendedPredictorOrdinateArray[extendedPredictorOrdinateIndex - 1] -
				_extendedPredictorOrdinateArray[extendedPredictorOrdinateIndex - 2]
			) - (
				(
					_extendedResponseValueArray[extendedPredictorOrdinateIndex - 2] -
					_extendedResponseValueArray[extendedPredictorOrdinateIndex - 3]
				) / (
					_extendedPredictorOrdinateArray[extendedPredictorOrdinateIndex - 2] -
					_extendedPredictorOrdinateArray[extendedPredictorOrdinateIndex - 3]
				)
			);
			_extendedResponseValueArray[extendedPredictorOrdinateIndex] =
				_extendedResponseValueArray[extendedPredictorOrdinateIndex] * (
					_extendedResponseValueArray[extendedPredictorOrdinateIndex] -
					_extendedResponseValueArray[extendedPredictorOrdinateIndex - 1]
				) + _extendedResponseValueArray[extendedPredictorOrdinateIndex - 1];
		} else {
			_extendedResponseValueArray[extendedPredictorOrdinateIndex] = 2. * (
				_extendedResponseValueArray[extendedPredictorOrdinateIndex + 2] -
				_extendedResponseValueArray[extendedPredictorOrdinateIndex + 1]
			) / (
				_extendedPredictorOrdinateArray[extendedPredictorOrdinateIndex + 2] -
				_extendedPredictorOrdinateArray[extendedPredictorOrdinateIndex + 1]
			) - (
				(
					_extendedResponseValueArray[extendedPredictorOrdinateIndex + 3] -
					_extendedResponseValueArray[extendedPredictorOrdinateIndex + 2]
				) / (
					_extendedPredictorOrdinateArray[extendedPredictorOrdinateIndex + 3] -
					_extendedPredictorOrdinateArray[extendedPredictorOrdinateIndex + 2]
				)
			);
			_extendedResponseValueArray[extendedPredictorOrdinateIndex] =
				_extendedResponseValueArray[extendedPredictorOrdinateIndex + 1] -
				_extendedResponseValueArray[extendedPredictorOrdinateIndex] * (
					_extendedResponseValueArray[extendedPredictorOrdinateIndex + 1] -
					_extendedResponseValueArray[extendedPredictorOrdinateIndex]
				);
		}

		return true;
	}

	private boolean extendResponseValue()
	{
		int responseValueCount = _responseValueArray.length;
		int extendedResponseValueCount = responseValueCount + 4;
		_extendedResponseValueArray = new double[extendedResponseValueCount];

		for (int extendedPredictorOrdinateIndex = 0;
			extendedPredictorOrdinateIndex < extendedResponseValueCount;
			++extendedPredictorOrdinateIndex) {
			if (2 <= extendedPredictorOrdinateIndex &&
				extendedResponseValueCount - 3 >= extendedPredictorOrdinateIndex) {
				_extendedResponseValueArray[extendedPredictorOrdinateIndex] =
					_responseValueArray[extendedPredictorOrdinateIndex - 2];
			}
		}

		return setExtendedResponseValue (1, false) &&
			setExtendedResponseValue (0, false) &&
			setExtendedResponseValue (extendedResponseValueCount - 2, true) &&
			setExtendedResponseValue (extendedResponseValueCount - 1, true);
	}

	/**
	 * Generate the C<sup>1</sup> Array
	 * 
	 * @return The C<sup>1</sup> Array
	 */

	public double[] C1()
	{
		int predictorOrdinateCount = _predictorOrdinateArray.length;
		double[] c1Array = new double[predictorOrdinateCount];
		double[] extendedSlopeArray = new double[predictorOrdinateCount + 3];

		for (int predictorOrdinateIndex = 0; predictorOrdinateIndex < predictorOrdinateCount + 3;
			++predictorOrdinateIndex) {
			extendedSlopeArray[predictorOrdinateIndex] = (
				_extendedResponseValueArray[predictorOrdinateIndex + 1] -
				_extendedResponseValueArray[predictorOrdinateIndex]
			) / (
				_extendedPredictorOrdinateArray[predictorOrdinateIndex + 1] -
				_extendedPredictorOrdinateArray[predictorOrdinateIndex]
			);
		}

		for (int predictorOrdinateIndex = 0; predictorOrdinateIndex < predictorOrdinateCount;
			++predictorOrdinateIndex) {
			double slope10 = Math.abs (
				extendedSlopeArray[predictorOrdinateIndex + 1] - extendedSlopeArray[predictorOrdinateIndex]
			);

			double slope32 = Math.abs (
				extendedSlopeArray[predictorOrdinateIndex + 3] -
				extendedSlopeArray[predictorOrdinateIndex + 2]
			);

			c1Array[predictorOrdinateIndex] = 0. == slope10 && 0. == slope32 ? 0.5 * (
				extendedSlopeArray[predictorOrdinateIndex + 1] + extendedSlopeArray[predictorOrdinateIndex + 2]
			) : (
				slope32 * extendedSlopeArray[predictorOrdinateIndex + 1] +
				slope10 * extendedSlopeArray[predictorOrdinateIndex + 2]
			) / (slope10 + slope32);
		}

		return c1Array;
	}
}
