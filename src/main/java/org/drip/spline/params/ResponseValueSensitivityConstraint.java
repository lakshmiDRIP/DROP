
package org.drip.spline.params;

import java.util.Set;

import org.drip.analytics.support.CaseInsensitiveHashMap;

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
 * <i>ResponseValueSensitivityConstraint</i> holds the <i>SegmentResponseValueConstraint</i> instances for
 *  the Base Calibration and one for each Manifest Measure Sensitivity.
 *
 * <br>
 *  <ul>
 * 		<li><i>ResponseValueSensitivityConstraint</i> constructor</li>
 * 		<li>Add the <i>SegmentResponseValueConstraint</i> Instance corresponding to the specified Manifest Measure</li>
 * 		<li>Return the Retrieve the base <i>SegmentResponseValueConstraint</i> Instance</li>
 * 		<li>Retrieve the <i>SegmentResponseValueConstraint</i> Instance Specified by the Manifest Measure</li>
 * 		<li>Return the Set of Available Manifest Measures (if any)</li>
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

public class ResponseValueSensitivityConstraint
{
	private SegmentResponseValueConstraint _baseSegmentResponseValueConstraint = null;

	private CaseInsensitiveHashMap<SegmentResponseValueConstraint> _segmentResponseValueManifestMeasureMap =
		new CaseInsensitiveHashMap<SegmentResponseValueConstraint>();

	/**
	 * <i>ResponseValueSensitivityConstraint</i> constructor
	 * 
	 * @param baseSegmentResponseValueConstraint The Base Calibration Instance of
	 *  <i>SegmentResponseValueConstraint</i>
	 * 
	 * @throws Exception Thrown if Inputs are Invalid
	 */

	public ResponseValueSensitivityConstraint (
		final SegmentResponseValueConstraint baseSegmentResponseValueConstraint)
		throws Exception
	{
		if (null == (_baseSegmentResponseValueConstraint = baseSegmentResponseValueConstraint)) {
			throw new Exception ("ResponseValueSensitivityConstraint ctr: Invalid Inputs");
		}
	}

	/**
	 * Add the <i>SegmentResponseValueConstraint</i> Instance corresponding to the specified Manifest Measure
	 * 
	 * @param manifestMeasure The Manifest Measure
	 * @param segmentResponseValueConstraint The <i>SegmentResponseValueConstraint</i> Instance
	 * 
	 * @return TRUE - The <i>SegmentResponseValueConstraint</i> Instance was successfully added
	 */

	public boolean addManifestMeasureSensitivity (
		final String manifestMeasure,
		final SegmentResponseValueConstraint segmentResponseValueConstraint)
	{
		if (null == manifestMeasure || manifestMeasure.isEmpty() || null == segmentResponseValueConstraint) {
			return false;
		}

		_segmentResponseValueManifestMeasureMap.put (manifestMeasure, segmentResponseValueConstraint);

		return true;
	}

	/**
	 * Retrieve the base <i>SegmentResponseValueConstraint</i> Instance
	 * 
	 * @return The Base <i>SegmentResponseValueConstraint</i> Instance
	 */

	public SegmentResponseValueConstraint base()
	{
		return _baseSegmentResponseValueConstraint;
	}

	/**
	 * Retrieve the <i>SegmentResponseValueConstraint</i> Instance Specified by the Manifest Measure
	 * 
	 * @param manifestMeasure The Manifest Measure
	 * 
	 * @return The <i>SegmentResponseValueConstraint</i> Instance Specified by the Manifest Measure
	 */

	public SegmentResponseValueConstraint manifestMeasureSensitivity (
		final String manifestMeasure)
	{
		return null != manifestMeasure &&
			_segmentResponseValueManifestMeasureMap.containsKey (manifestMeasure) ?
			_segmentResponseValueManifestMeasureMap.get (manifestMeasure) : null;
	}

	/**
	 * Return the Set of Available Manifest Measures (if any)
	 * 
	 * @return The Set of Available Manifest Measures
	 */

	public Set<String> manifestMeasures()
	{
		return _segmentResponseValueManifestMeasureMap.keySet();
	}
}
