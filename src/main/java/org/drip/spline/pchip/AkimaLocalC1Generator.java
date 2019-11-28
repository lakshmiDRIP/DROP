
package org.drip.spline.pchip;

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
 * <i>AkimaLocalC1Generator</i> generates the local control C1 Slope using the Akima Cubic Algorithm.
 *
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Akima (1970): A New Method of Interpolation and Smooth Curve Fitting based on Local Procedures
 * 				<i>Journal of the Association for the Computing Machinery</i> <b>17 (4)</b> 589-602
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/pchip/README.md">Monotone Convex Themed PCHIP Splines</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class AkimaLocalC1Generator {
	private double[] _adblResponseValue = null;
	private double[] _adblPredictorOrdinate = null;
	private double[] _adblExtendedResponseValue = null;
	private double[] _adblExtendedPredictorOrdinate = null;

	/**
	 * Construct an Instance of AkimaLocalC1Generator from the Array of the supplied Predictor Ordinates
	 *  and the Response Values
	 *  
	 * @param adblPredictorOrdinate Array of the Predictor Ordinates
	 * @param adblResponseValue Array of the Response Values
	 * 
	 * @return Instance of AkimaLocalC1Generator
	 */

	public static final AkimaLocalC1Generator Create (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue)
	{
		AkimaLocalC1Generator alcr = null;

		try {
			alcr = new AkimaLocalC1Generator (adblPredictorOrdinate, adblResponseValue);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return alcr.extendPredictorOrdinate() && alcr.extendResponseValue() ? alcr : null;
	}

	private AkimaLocalC1Generator (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue)
		throws java.lang.Exception
	{
		if (null == (_adblPredictorOrdinate = adblPredictorOrdinate) || null == (_adblResponseValue =
			adblResponseValue))
			throw new java.lang.Exception ("AkimaLocalC1Generator ctr: Invalid Inputs");

		int iNumPredictorOrdinate = _adblPredictorOrdinate.length;

		if (2 >= iNumPredictorOrdinate || iNumPredictorOrdinate != _adblResponseValue.length)
			throw new java.lang.Exception ("AkimaLocalC1Generator ctr: Invalid Inputs");
	}

	private boolean extendPredictorOrdinate()
	{
		int iNumPredictorOrdinate = _adblPredictorOrdinate.length;
		int iNumExtendedPredictorOrdinate = iNumPredictorOrdinate + 4;
		_adblExtendedPredictorOrdinate = new double[iNumExtendedPredictorOrdinate];

		for (int i = 0; i < iNumExtendedPredictorOrdinate; ++i) {
			if (2 <= i && iNumExtendedPredictorOrdinate - 3 >= i)
				_adblExtendedPredictorOrdinate[i] = _adblPredictorOrdinate[i - 2];
		}

		double dblSkippedLeftPredictorWidth = _adblPredictorOrdinate[2] - _adblPredictorOrdinate[0];
		_adblExtendedPredictorOrdinate[0] = _adblPredictorOrdinate[0] - dblSkippedLeftPredictorWidth;
		_adblExtendedPredictorOrdinate[1] = _adblPredictorOrdinate[1] - dblSkippedLeftPredictorWidth;
		double dblSkippedRightPredictorWidth = _adblPredictorOrdinate[iNumPredictorOrdinate - 1] -
			_adblPredictorOrdinate[iNumPredictorOrdinate - 3];
		_adblExtendedPredictorOrdinate[iNumExtendedPredictorOrdinate - 2] =
			_adblPredictorOrdinate[iNumPredictorOrdinate - 2] + dblSkippedRightPredictorWidth;
		_adblExtendedPredictorOrdinate[iNumExtendedPredictorOrdinate - 1] =
			_adblPredictorOrdinate[iNumPredictorOrdinate - 1] + dblSkippedRightPredictorWidth;
		return true;
	}

	private boolean setExtendedResponseValue (
		final int i,
		final boolean bRight)
	{
		if (bRight) {
			_adblExtendedResponseValue[i] = 2. * (_adblExtendedResponseValue[i - 1] -
				_adblExtendedResponseValue[i - 2]) / (_adblExtendedPredictorOrdinate[i - 1] -
					_adblExtendedPredictorOrdinate[i - 2]) - ((_adblExtendedResponseValue[i - 2] -
						_adblExtendedResponseValue[i - 3]) / (_adblExtendedPredictorOrdinate[i - 2] -
							_adblExtendedPredictorOrdinate[i - 3]));
			_adblExtendedResponseValue[i] = _adblExtendedResponseValue[i] * (_adblExtendedResponseValue[i] -
				_adblExtendedResponseValue[i - 1]) + _adblExtendedResponseValue[i - 1];
		} else {
			_adblExtendedResponseValue[i] = 2. * (_adblExtendedResponseValue[i + 2] -
				_adblExtendedResponseValue[i + 1]) / (_adblExtendedPredictorOrdinate[i + 2] -
					_adblExtendedPredictorOrdinate[i + 1]) - ((_adblExtendedResponseValue[i + 3] -
						_adblExtendedResponseValue[i + 2]) / (_adblExtendedPredictorOrdinate[i + 3] -
							_adblExtendedPredictorOrdinate[i + 2]));
			_adblExtendedResponseValue[i] = _adblExtendedResponseValue[i + 1] - _adblExtendedResponseValue[i]
				* (_adblExtendedResponseValue[i + 1] - _adblExtendedResponseValue[i]);
		}

		return true;
	}

	private boolean extendResponseValue()
	{
		int iNumResponseValue = _adblResponseValue.length;
		int iNumExtendedResponseValue = iNumResponseValue + 4;
		_adblExtendedResponseValue = new double[iNumExtendedResponseValue];

		for (int i = 0; i < iNumExtendedResponseValue; ++i) {
			if (2 <= i && iNumExtendedResponseValue - 3 >= i)
				_adblExtendedResponseValue[i] = _adblResponseValue[i - 2];
		}

		return setExtendedResponseValue (1, false) && setExtendedResponseValue (0, false) &&
			setExtendedResponseValue (iNumExtendedResponseValue - 2, true) && setExtendedResponseValue
				(iNumExtendedResponseValue - 1, true) ? true : false;
	}

	public double[] C1()
	{
		int iNumPredictorOrdinate = _adblPredictorOrdinate.length;
		double[] adblC1 = new double[iNumPredictorOrdinate];
		double[] adblExtendedSlope = new double[iNumPredictorOrdinate + 3];

		for (int i = 0; i < iNumPredictorOrdinate + 3; ++i)
			adblExtendedSlope[i] = (_adblExtendedResponseValue[i + 1] - _adblExtendedResponseValue[i]) /
				(_adblExtendedPredictorOrdinate[i + 1] - _adblExtendedPredictorOrdinate[i]);

		for (int i = 0; i < iNumPredictorOrdinate; ++i) {
			double dblSlope10 = java.lang.Math.abs (adblExtendedSlope[i + 1] - adblExtendedSlope[i]);

			double dblSlope32 = java.lang.Math.abs (adblExtendedSlope[i + 3] - adblExtendedSlope[i + 2]);

			if (0. == dblSlope10 && 0. == dblSlope32)
				adblC1[i] = 0.5 * (adblExtendedSlope[i + 1] + adblExtendedSlope[i + 2]);
			else
				adblC1[i] = (dblSlope32 * adblExtendedSlope[i + 1] + dblSlope10 * adblExtendedSlope[i + 2]) /
					(dblSlope10 + dblSlope32);
		}

		return adblC1;
	}
}
