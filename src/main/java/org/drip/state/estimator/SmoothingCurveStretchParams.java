
package org.drip.state.estimator;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.params.StretchBestFitResponse;

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
 * <i>SmoothingCurveStretchParams</i> contains the Parameters needed to hold the Stretch. It provides
 * functionality to:
 *
 *  <ul>
 *  	<li><i>SmoothingCurveStretchParams</i> constructor</li>
 *  	<li>Set the Stretch's Segment Builder Control</li>
 *  	<li>Retrieve the Curve Smoothening Quantification Metric</li>
 *  	<li>Retrieve the Calibration Detail</li>
 *  	<li>Retrieve the Default Segment Builder Parameters</li>
 *  	<li>Retrieve the Segment Builder Parameters</li>
 *  	<li>Retrieve the Best Fit Weighted Response</li>
 *  	<li>Retrieve the Best Fit Weighted Response Sensitivity</li>
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
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/estimator/README.md">Multi-Pass Customized Stretch Curve</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class SmoothingCurveStretchParams
{
	private int _calibrationDetail = -1;
	private String _smootheningQuantificationMetric = "";
	private StretchBestFitResponse _stretchBestFitResponse = null;
	private StretchBestFitResponse _stretchBestFitResponseSensitivity = null;

	private CaseInsensitiveHashMap<SegmentCustomBuilderControl> _segmentCustomBuilderControlMap =
		new CaseInsensitiveHashMap<SegmentCustomBuilderControl>();

	/**
	 * <i>SmoothingCurveStretchParams</i> constructor
	 * 
	 * @param smootheningQuantificationMetric Curve Smoothening Quantification Metric
	 * @param segmentCustomBuilderControlDefault Default Segment Builder Parameters
	 * @param calibrationDetail The Calibration Detail
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param stretchBestFitResponseSensitivity Stretch Fitness Weighted Response Sensitivity
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	public SmoothingCurveStretchParams (
		final String smootheningQuantificationMetric,
		final SegmentCustomBuilderControl segmentCustomBuilderControlDefault,
		final int calibrationDetail,
		final StretchBestFitResponse stretchBestFitResponse,
		final StretchBestFitResponse stretchBestFitResponseSensitivity)
		throws Exception
	{
		if (null == segmentCustomBuilderControlDefault) {
			throw new Exception ("SmoothingCurveStretchParams ctr: Invalid Inputs");
		}

		_calibrationDetail = calibrationDetail;
		_stretchBestFitResponse = stretchBestFitResponse;
		_smootheningQuantificationMetric = smootheningQuantificationMetric;
		_stretchBestFitResponseSensitivity = stretchBestFitResponseSensitivity;

		_segmentCustomBuilderControlMap.put ("default", segmentCustomBuilderControlDefault);
	}

	/**
	 * Set the Stretch's Segment Builder Control
	 * 
	 * @param stretchName Name of the Stretch for which the Segment Builder Parameters need to be set
	 * @param segmentCustomBuilderControl The Segment Builder Parameters
	 * 
	 * @return TRUE - The Segment Builder Control Parameters have been successfully set
	 */

	public boolean setStretchSegmentBuilderControl (
		final String stretchName,
		final SegmentCustomBuilderControl segmentCustomBuilderControl)
	{
		if (null == stretchName || stretchName.isEmpty() || null == segmentCustomBuilderControl) {
			return false;
		}

		_segmentCustomBuilderControlMap.put (stretchName, segmentCustomBuilderControl);

		return true;
	}

	/**
	 * Retrieve the Curve Smoothening Quantification Metric
	 * 
	 * @return The Curve Smoothening Quantification Metric
	 */

	public String smootheningQuantificationMetric()
	{
		return _smootheningQuantificationMetric;
	}

	/**
	 * Retrieve the Calibration Detail
	 * 
	 * @return The Calibration Detail
	 */

	public int calibrationDetail()
	{
		return _calibrationDetail;
	}

	/**
	 * Retrieve the Default Segment Builder Parameters
	 * 
	 * @return The Default Segment Builder Parameters
	 */

	public SegmentCustomBuilderControl defaultSegmentBuilderControl()
	{
		return _segmentCustomBuilderControlMap.get ("default");
	}

	/**
	 * Retrieve the Segment Builder Parameters
	 * 
	 * @param stretchName Name of the Stretch for which the Segment Builder Parameters are requested
	 * 
	 * @return The Segment Builder Parameters
	 */

	public SegmentCustomBuilderControl segmentBuilderControl (
		final String stretchName)
	{
		return _segmentCustomBuilderControlMap.containsKey (stretchName) ?
			_segmentCustomBuilderControlMap.get (stretchName) :
			_segmentCustomBuilderControlMap.get ("default");
	}

	/**
	 * Retrieve the Best Fit Weighted Response
	 * 
	 * @return The Best Fit Weighted Response
	 */

	public StretchBestFitResponse bestFitWeightedResponse()
	{
		return _stretchBestFitResponse;
	}

	/**
	 * Retrieve the Best Fit Weighted Response Sensitivity
	 * 
	 * @return The Best Fit Weighted Response Sensitivity
	 */

	public StretchBestFitResponse bestFitWeightedResponseSensitivity()
	{
		return _stretchBestFitResponseSensitivity;
	}
}
