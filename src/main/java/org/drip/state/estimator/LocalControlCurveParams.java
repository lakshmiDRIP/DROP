	
package org.drip.state.estimator;

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
 * <i>LocalControlCurveParams</i> enhances the SmoothingCurveStretchParams to produce locally customized
 * curve smoothing. Flags implemented by <i>LocalControlCurveParams</i> control the following:
 *
 *  <ul>
 *  	<li><i>LocalControlCurveParams</i> constructor</li>
 *  	<li>Retrieve the Apply Monotone Filter Flag</li>
 *  	<li>Retrieve the Eliminate Spurious Extrema Flag</li>
 *  	<li>Retrieve the C1 Generator Scheme</li>
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

public class LocalControlCurveParams extends SmoothingCurveStretchParams
{
	private String _c1GeneratorScheme = "";
	private boolean _applyMonotoneFilter = false;
	private boolean _eliminateSpuriousExtrema = false;

	/**
	 * <i>LocalControlCurveParams</i> constructor
	 * 
	 * @param c1GeneratorScheme C1 Generator Stretch
	 * @param smootheningQuantificationMetric Curve Smoothening Quantification Metric
	 * @param segmentCustomBuilderControl Segment Builder Parameters
	 * @param calibrationDetail The Calibration Detail
	 * @param stretchBestFitResponse Curve Fitness Weighted Response
	 * @param stretchBestFitResponseSensitivity Curve Fitness Weighted Response Sensitivity
	 * @param eliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param applyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public LocalControlCurveParams (
		final String c1GeneratorScheme,
		final String smootheningQuantificationMetric,
		final SegmentCustomBuilderControl segmentCustomBuilderControl,
		final int calibrationDetail,
		final StretchBestFitResponse stretchBestFitResponse,
		final StretchBestFitResponse stretchBestFitResponseSensitivity,
		final boolean eliminateSpuriousExtrema,
		final boolean applyMonotoneFilter)
		throws Exception
	{
		super (
			smootheningQuantificationMetric,
			segmentCustomBuilderControl,
			calibrationDetail,
			stretchBestFitResponse,
			stretchBestFitResponseSensitivity
		);

		if (null == (_c1GeneratorScheme = c1GeneratorScheme)) {
			throw new Exception ("LocalControlCurveParams ctr: Invalid Inputs!");
		}

		_applyMonotoneFilter = applyMonotoneFilter;
		_eliminateSpuriousExtrema = eliminateSpuriousExtrema;
	}

	/**
	 * Retrieve the Apply Monotone Filter Flag
	 * 
	 * @return The Apply Monotone Filter Flag
	 */

	public boolean applyMonotoneFilter()
	{
		return _applyMonotoneFilter;
	}

	/**
	 * Retrieve the Eliminate Spurious Extrema Flag
	 * 
	 * @return The Eliminate Spurious Extrema Flag
	 */

	public boolean eliminateSpuriousExtrema()
	{
		return _eliminateSpuriousExtrema;
	}

	/**
	 * Retrieve the C1 Generator Scheme
	 * 
	 * @return The C1 Generator Scheme
	 */

	public String C1GeneratorScheme()
	{
		return _c1GeneratorScheme;
	}
}
