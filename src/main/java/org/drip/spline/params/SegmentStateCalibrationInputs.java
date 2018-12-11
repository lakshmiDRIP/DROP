
package org.drip.spline.params;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>SegmentStateCalibrationInputs</i> implements basis per-segment Calibration Parameter Input Set. It
 * exposes the following functionality:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Retrieve the Array of the Calibration Predictor Ordinates.
 *  	</li>
 *  	<li>
 *  		Retrieve the Array of the Calibration Response Values.
 *  	</li>
 *  	<li>
 *  		Retrieve the Array of the Left/Right Edge Derivatives.
 *  	</li>
 *  	<li>
 *  		Retrieve the Segment Best Fit Response.
 *  	</li>
 *  	<li>
 *  		Retrieve the Array of Segment Basis Flexure Constraints.
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline">Spline</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/params">Params</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SegmentStateCalibrationInputs {
	private double[] _adblResponseValue = null;
	private double[] _adblLeftEdgeDeriv = null;
	private double[] _adblRightEdgeDeriv = null;
	private double[] _adblPredictorOrdinate = null;
	private org.drip.spline.params.SegmentBestFitResponse _sbfr = null;
	private org.drip.spline.params.SegmentBasisFlexureConstraint[] _aSBFC = null;

	/**
	 * SegmentStateCalibrationInputs Constructor
	 * 
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * @param adblLeftEdgeDeriv Array of the Left Edge Derivatives
	 * @param adblRightEdgeDeriv Array of the Right Edge  Derivatives
	 * @param aSBFC Array of the Segment Basis Flexure Constraints
	 * @param sbfr Segment Basis Fit Response
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public SegmentStateCalibrationInputs (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue,
		final double[] adblLeftEdgeDeriv,
		final double[] adblRightEdgeDeriv,
		final org.drip.spline.params.SegmentBasisFlexureConstraint[] aSBFC,
		final org.drip.spline.params.SegmentBestFitResponse sbfr)
		throws java.lang.Exception
	{
		_sbfr = sbfr;
		int iNumSBFC = null == (_aSBFC = aSBFC) ? 0 : _aSBFC.length;
		int iNumLeftEdgeDeriv = null == (_adblLeftEdgeDeriv = adblLeftEdgeDeriv) ? 0 :
			_adblLeftEdgeDeriv.length;
		int iNumResponseValue = null == (_adblResponseValue = adblResponseValue) ? 0 :
			_adblResponseValue.length;
		int iNumRightEdgeDeriv = null == (_adblRightEdgeDeriv = adblRightEdgeDeriv) ? 0 :
			_adblRightEdgeDeriv.length;
		int iNumPredictorOrdinate = null == (_adblPredictorOrdinate = adblPredictorOrdinate) ? 0 :
			_adblPredictorOrdinate.length;

		if (null == _sbfr && null == _aSBFC && null == _adblPredictorOrdinate && null == _adblResponseValue
			&& null == _adblLeftEdgeDeriv && null == _adblRightEdgeDeriv)
			throw new java.lang.Exception ("SegmentStateCalibrationInputs ctr: Invalid Inputs");

		if (iNumPredictorOrdinate != iNumResponseValue || (null == _sbfr && 0 == iNumSBFC && 0 ==
			iNumPredictorOrdinate && 0 == iNumLeftEdgeDeriv && 0 == iNumRightEdgeDeriv))
			throw new java.lang.Exception ("SegmentStateCalibrationInputs ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Array of the Calibration Predictor Ordinates
	 * 
	 * @return The Array of the Calibration Predictor Ordinates
	 */

	public double[] predictorOrdinates()
	{
		return _adblPredictorOrdinate;
	}

	/**
	 * Retrieve the Array of the Calibration Response Values
	 * 
	 * @return The Array of the Calibration Response Values
	 */

	public double[] responseValues()
	{
		return _adblResponseValue;
	}

	/**
	 * Retrieve the Array of the Left Edge Derivatives
	 * 
	 * @return The Array of the Left Edge Derivatives
	 */

	public double[] leftEdgeDeriv()
	{
		return _adblLeftEdgeDeriv;
	}

	/**
	 * Retrieve the Array of the Right Edge Derivatives
	 * 
	 * @return The Array of the Right Edge Derivatives
	 */

	public double[] rightEdgeDeriv()
	{
		return _adblRightEdgeDeriv;
	}

	/**
	 * Retrieve the Segment Best Fit Response
	 * 
	 * @return The Segment Best Fit Response
	 */

	public org.drip.spline.params.SegmentBestFitResponse bestFitResponse()
	{
		return _sbfr;
	}

	/**
	 * Retrieve the Array of Segment Basis Flexure Constraints
	 * 
	 * @return The Array of Segment Basis Flexure Constraints
	 */

	public org.drip.spline.params.SegmentBasisFlexureConstraint[] flexureConstraint()
	{
		return _aSBFC;
	}
}
