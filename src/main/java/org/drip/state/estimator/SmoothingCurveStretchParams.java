
package org.drip.state.estimator;

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
 * <i>SmoothingCurveStretchParams</i> contains the Parameters needed to hold the Stretch. It provides
 * functionality to:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 * 			The Stretch Best fit Response and the corresponding Quote Sensitivity
 *  	</li>
 *  	<li>
 * 			The Calibration Detail and the Curve Smoothening Quantification Metric
 *  	</li>
 *  	<li>
 * 			The Segment Builder Parameters
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/estimator/README.md">Multi-Pass Customized Stretch Curve</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class SmoothingCurveStretchParams {
	private int _iCalibrationDetail = -1;
	private java.lang.String _strSmootheningQuantificationMetric = "";
	private org.drip.spline.params.StretchBestFitResponse _sbfr = null;
	private org.drip.spline.params.StretchBestFitResponse _sbfrSensitivity = null;

	private
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.spline.params.SegmentCustomBuilderControl>
			_mapSCBC = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.spline.params.SegmentCustomBuilderControl>();

	/**
	 * SmoothingCurveStretchParams constructor
	 * 
	 * @param strSmootheningQuantificationMetric Curve Smoothening Quantification Metric
	 * @param scbcDefault Default Segment Builder Parameters
	 * @param iCalibrationDetail The Calibration Detail
	 * @param sbfr Stretch Fitness Weighted Response
	 * @param sbfrSensitivity Stretch Fitness Weighted Response Sensitivity
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public SmoothingCurveStretchParams (
		final java.lang.String strSmootheningQuantificationMetric,
		final org.drip.spline.params.SegmentCustomBuilderControl scbcDefault,
		final int iCalibrationDetail,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final org.drip.spline.params.StretchBestFitResponse sbfrSensitivity)
		throws java.lang.Exception
	{
		if (null == scbcDefault)
			throw new java.lang.Exception ("SmoothingCurveStretchParams ctr: Invalid Inputs");

		_sbfr = sbfr;
		_sbfrSensitivity = sbfrSensitivity;
		_iCalibrationDetail = iCalibrationDetail;
		_strSmootheningQuantificationMetric = strSmootheningQuantificationMetric;

		_mapSCBC.put ("default", scbcDefault);
	}

	/**
	 * Set the Stretch's Segment Builder Control
	 * 
	 * @param strStretchName Name of the Stretch for which the Segment Builder Parameters need to be set
	 * @param scbc The Segment Builder Parameters
	 * 
	 * @return TRUE - The Segment Builder Control Parameters have been successfully set
	 */

	public boolean setStretchSegmentBuilderControl (
		final java.lang.String strStretchName,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc)
	{
		if (null == strStretchName || strStretchName.isEmpty() || null == scbc) return false;

		_mapSCBC.put (strStretchName, scbc);

		return true;
	}

	/**
	 * Retrieve the Curve Smoothening Quantification Metric
	 * 
	 * @return The Curve Smoothening Quantification Metric
	 */

	public java.lang.String smootheningQuantificationMetric()
	{
		return _strSmootheningQuantificationMetric;
	}

	/**
	 * Retrieve the Calibration Detail
	 * 
	 * @return The Calibration Detail
	 */

	public int calibrationDetail()
	{
		return _iCalibrationDetail;
	}

	/**
	 * Retrieve the Default Segment Builder Parameters
	 * 
	 * @return The Default Segment Builder Parameters
	 */

	public org.drip.spline.params.SegmentCustomBuilderControl defaultSegmentBuilderControl()
	{
		return _mapSCBC.get ("default");
	}

	/**
	 * Retrieve the Segment Builder Parameters
	 * 
	 * @param strStretchName Name of the Stretch for which the Segment Builder Parameters are requested
	 * 
	 * @return The Segment Builder Parameters
	 */

	public org.drip.spline.params.SegmentCustomBuilderControl segmentBuilderControl (
		final java.lang.String strStretchName)
	{
		return _mapSCBC.containsKey (strStretchName) ? _mapSCBC.get (strStretchName) : _mapSCBC.get
			("default");
	}

	/**
	 * Retrieve the Best Fit Weighted Response
	 * 
	 * @return The Best Fit Weighted Response
	 */

	public org.drip.spline.params.StretchBestFitResponse bestFitWeightedResponse()
	{
		return _sbfr;
	}

	/**
	 * Retrieve the Best Fit Weighted Response Sensitivity
	 * 
	 * @return The Best Fit Weighted Response Sensitivity
	 */

	public org.drip.spline.params.StretchBestFitResponse bestFitWeightedResponseSensitivity()
	{
		return _sbfrSensitivity;
	}
}
