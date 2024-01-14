	
package org.drip.spline.stretch;

import org.drip.spline.params.SegmentResponseValueConstraint;
import org.drip.spline.params.StretchBestFitResponse;
import org.drip.spline.segment.LatentStateResponseModel;

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
 * <i>CkSegmentSequenceBuilder</i> implements the SegmentSequenceBuilder interface to customize segment
 * 	sequence construction. Customization is applied at several levels:
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/README.md">Multi-Segment Sequence Spline Stretch</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CkSegmentSequenceBuilder
	implements SegmentSequenceBuilder
{
	private BoundarySettings _boundarySettings = null;
	private MultiSegmentSequence _multiSegmentSequence = null;
	private StretchBestFitResponse _stretchBestFitResponse = null;
	private SegmentResponseValueConstraint _leadingSegmentResponseValueConstraint = null;
	private SegmentResponseValueConstraint[] _segmentResponseValueConstraintArray = null;

	/**
	 * <i>CkSegmentSequenceBuilder</i> constructor
	 * 
	 * @param leadingSegmentResponseValueConstraint Leading Segment Response Value Constraint
	 * @param segmentResponseValueConstraintArray Array of Segment Response Value Constraints
	 * @param stretchBestFitResponse Sequence Best Fit Weighted Response
	 * @param boundarySettings The Calibration Boundary Condition
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	public CkSegmentSequenceBuilder (
		final SegmentResponseValueConstraint leadingSegmentResponseValueConstraint,
		final SegmentResponseValueConstraint[] segmentResponseValueConstraintArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final BoundarySettings boundarySettings)
		throws Exception
	{
		_stretchBestFitResponse = stretchBestFitResponse;
		_segmentResponseValueConstraintArray = segmentResponseValueConstraintArray;
		_leadingSegmentResponseValueConstraint = leadingSegmentResponseValueConstraint;

		if (null == _leadingSegmentResponseValueConstraint && (
				null == _segmentResponseValueConstraintArray ||
				0 == _segmentResponseValueConstraintArray.length
			) && null == _stretchBestFitResponse
		) {
			throw new Exception ("CkSegmentSequenceBuilder ctr: Invalid inputs!");
		}

		if (null == (_boundarySettings = boundarySettings)) {
			throw new Exception ("CkSegmentSequenceBuilder ctr: Invalid inputs!");
		}
	}

	@Override public boolean setStretch (
		final MultiSegmentSequence multiSegmentSequence)
	{
		if (null == multiSegmentSequence) {
			return false;
		}

		_multiSegmentSequence = multiSegmentSequence;
		return true;
	}

	@Override public BoundarySettings getCalibrationBoundaryCondition()
	{
		return _boundarySettings;
	}

	@Override public boolean calibStartingSegment (
		final double leftSlope)
	{
		if (null == _multiSegmentSequence) {
			return false;
		}

		LatentStateResponseModel[] latentStateResponseModelArray = _multiSegmentSequence.segments();

		return null != latentStateResponseModelArray && 0 < latentStateResponseModelArray.length &&
			latentStateResponseModelArray[0].calibrate (
				_leadingSegmentResponseValueConstraint,
				leftSlope,
				null == _segmentResponseValueConstraintArray ?
					null : _segmentResponseValueConstraintArray[0],
				null == _stretchBestFitResponse ? null : _stretchBestFitResponse.sizeToSegment (
					latentStateResponseModelArray[0]
				)
			);
	}

	@Override public boolean calibSegmentSequence (
		final int startingSegment)
	{
		if (null == _multiSegmentSequence) {
			return false;
		}

		LatentStateResponseModel[] latentStateResponseModelArray = _multiSegmentSequence.segments();

		int segmentCount = latentStateResponseModelArray.length;

		for (int segmentIndex = startingSegment; segmentIndex < segmentCount; ++segmentIndex) {
			if (!latentStateResponseModelArray[segmentIndex].calibrate (
				0 == segmentIndex ? null : latentStateResponseModelArray[segmentIndex - 1],
				null == _segmentResponseValueConstraintArray ?
					null : _segmentResponseValueConstraintArray[segmentIndex],
				null == _stretchBestFitResponse ? null : _stretchBestFitResponse.sizeToSegment (
					latentStateResponseModelArray[segmentIndex]
				)
			)) {
				return false;
			}
		}

		return true;
	}

	@Override public boolean manifestMeasureSensitivity (
		final double leftSlopeSensitivity)
	{
		return true;
	}
}
