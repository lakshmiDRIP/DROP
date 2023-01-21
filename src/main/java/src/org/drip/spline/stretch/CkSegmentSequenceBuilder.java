	
package org.drip.spline.stretch;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * sequence construction. Customization is applied at several levels:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Segment Calibration Boundary Setting/Segment Best Fit Response Settings
 *  	</li>
 *  	<li>
 * 			Segment Response Value Constraints for the starting and the subsequent Segments
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/README.md">Multi-Segment Sequence Spline Stretch</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CkSegmentSequenceBuilder implements org.drip.spline.stretch.SegmentSequenceBuilder {
	private org.drip.spline.stretch.BoundarySettings _bs = null;
	private org.drip.spline.stretch.MultiSegmentSequence _mss = null;
	private org.drip.spline.params.StretchBestFitResponse _rbfr = null;
	private org.drip.spline.params.SegmentResponseValueConstraint[] _aSRVC = null;
	private org.drip.spline.params.SegmentResponseValueConstraint _srvcLeading = null;

	/**
	 * CkSegmentSequenceBuilder constructor
	 * 
	 * @param srvcLeading Leading Segment Response Value Constraint
	 * @param aSRVC Array of Segment Response Value Constraints
	 * @param rbfr Sequence Best Fit Weighted Response
	 * @param bs The Calibration Boundary Condition
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public CkSegmentSequenceBuilder (
		final org.drip.spline.params.SegmentResponseValueConstraint srvcLeading,
		final org.drip.spline.params.SegmentResponseValueConstraint[] aSRVC,
		final org.drip.spline.params.StretchBestFitResponse rbfr,
		final org.drip.spline.stretch.BoundarySettings bs)
		throws java.lang.Exception
	{
		_rbfr = rbfr;
		_aSRVC = aSRVC;
		_srvcLeading = srvcLeading;

		if (null == _srvcLeading && (null == _aSRVC || 0 == _aSRVC.length) && null == _rbfr)
			throw new java.lang.Exception ("CkSegmentSequenceBuilder ctr: Invalid inputs!");

		if (null == (_bs = bs))
			throw new java.lang.Exception ("CkSegmentSequenceBuilder ctr: Invalid inputs!");
	}

	@Override public boolean setStretch (
		final org.drip.spline.stretch.MultiSegmentSequence mss)
	{
		if (null == mss) return false;

		_mss = mss;
		return true;
	}

	@Override public org.drip.spline.stretch.BoundarySettings getCalibrationBoundaryCondition()
	{
		return _bs;
	}

	@Override public boolean calibStartingSegment (
		final double dblLeftSlope)
	{
		if (null == _mss) return false;

		org.drip.spline.segment.LatentStateResponseModel[] aCS = _mss.segments();

		return null != aCS && 0 < aCS.length && aCS[0].calibrate (_srvcLeading, dblLeftSlope, null == _aSRVC
			? null : _aSRVC[0], null == _rbfr ? null : _rbfr.sizeToSegment (aCS[0]));
	}

	@Override public boolean calibSegmentSequence (
		final int iStartingSegment)
	{
		if (null == _mss) return false;

		org.drip.spline.segment.LatentStateResponseModel[] aCS = _mss.segments();

		int iNumSegment = aCS.length;

		for (int iSegment = iStartingSegment; iSegment < iNumSegment; ++iSegment) {
			if (!aCS[iSegment].calibrate (0 == iSegment ? null : aCS[iSegment - 1], null == _aSRVC ? null :
				_aSRVC[iSegment], null == _rbfr ? null : _rbfr.sizeToSegment (aCS[iSegment])))
				return false;
		}

		return true;
	}

	@Override public boolean manifestMeasureSensitivity (
		final double dblLeftSlopeSensitivity)
	{
		return true;
	}
}
