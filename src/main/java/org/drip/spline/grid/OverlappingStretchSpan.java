
package org.drip.spline.grid;

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
 * <i>OverlappingStretchSpan</i> implements the Span interface, and the collection functionality of
 * overlapping Stretches. In addition to providing a custom implementation of all the Span interface stubs,
 * it also converts the Overlapping Stretch Span to a non-overlapping Stretch Span. Overlapping Stretches are
 * clipped from the Left.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/grid/README.md">Aggregated/Overlapping Stretch/Span Grids</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OverlappingStretchSpan implements org.drip.spline.grid.Span {
	private java.util.List<org.drip.spline.stretch.MultiSegmentSequence> _lsMSS = new
		java.util.ArrayList<org.drip.spline.stretch.MultiSegmentSequence>();

	/**
	 * OverlappingStretchSpan constructor
	 * 
	 * @param mss The Initial Stretch in the Span
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public OverlappingStretchSpan (
		final org.drip.spline.stretch.MultiSegmentSequence mss)
		throws java.lang.Exception
	{
		if (null == mss) throw new java.lang.Exception ("OverlappingStretchSpan ctr: Invalid Inputs");

		_lsMSS.add (mss);
	}

	@Override public boolean addStretch (
		final org.drip.spline.stretch.MultiSegmentSequence mss)
	{
		if (null == mss) return false;

		_lsMSS.add (mss);

		return true;
	}

	@Override public org.drip.spline.stretch.MultiSegmentSequence getContainingStretch (
		final double dblPredictorOrdinate)
	{
		if (null == _lsMSS || 0 == _lsMSS.size()) return null;

		for (org.drip.spline.stretch.MultiSegmentSequence mss : _lsMSS) {
			try {
				if (mss.in (dblPredictorOrdinate)) return mss;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return null;
	}

	@Override public org.drip.spline.stretch.MultiSegmentSequence getStretch (
		final java.lang.String strName)
	{
		if (null == strName) return null;

		for (org.drip.spline.stretch.MultiSegmentSequence mss : _lsMSS) {
			if (strName.equalsIgnoreCase (mss.name())) return mss;
		}

		return null;
	}

	@Override public double left()
		throws java.lang.Exception
	{
		if (0 == _lsMSS.size())
			throw new java.lang.Exception ("OverlappingStretchSpan::left => No valid Stretches found");

		return _lsMSS.get (0).getLeftPredictorOrdinateEdge();
	}

	@Override public double right()
		throws java.lang.Exception
	{
		if (0 == _lsMSS.size())
			throw new java.lang.Exception ("OverlappingStretchSpan::right => No valid Stretches found");

		return _lsMSS.get (_lsMSS.size() - 1).getRightPredictorOrdinateEdge();
	}

	@Override public double calcResponseValue (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		for (org.drip.spline.stretch.MultiSegmentSequence mss : _lsMSS) {
			if (mss.in (dblPredictorOrdinate)) return mss.responseValue (dblPredictorOrdinate);
		}

		throw new java.lang.Exception ("OverlappingStretchSpan::calcResponseValue => Cannot Calculate!");
	}

	@Override public double calcResponseValueDerivative (
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception
	{
		for (org.drip.spline.stretch.MultiSegmentSequence mss : _lsMSS) {
			if (mss.in (dblPredictorOrdinate))
				return mss.responseValueDerivative (dblPredictorOrdinate, iOrder);
		}

		throw new java.lang.Exception
			("OverlappingStretchSpan::calcResponseValueDerivative => Cannot Calculate!");
	}

	@Override public boolean isMergeState (
		final double dblPredictorOrdinate,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		try {
			for (org.drip.spline.stretch.MultiSegmentSequence mss : _lsMSS) {
				if (mss.in (dblPredictorOrdinate)) {
					org.drip.state.representation.MergeSubStretchManager msm = mss.msm();

					return null == msm ? false : msm.partOfMergeState (dblPredictorOrdinate, lsl);
				}
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override public org.drip.numerical.differentiation.WengertJacobian jackDResponseDManifestMeasure (
		final java.lang.String strManifestMeasure,
		final double dblPredictorOrdinate,
		final int iOrder)
	{
		if (0 == _lsMSS.size()) return null;

		java.util.List<org.drip.numerical.differentiation.WengertJacobian> lsWJ = new
			java.util.ArrayList<org.drip.numerical.differentiation.WengertJacobian>();

		boolean bPredictorOrdinateCovered = false;

		for (org.drip.spline.stretch.MultiSegmentSequence mss : _lsMSS) {
			if (null == mss) continue;

			try {
				org.drip.numerical.differentiation.WengertJacobian wj = null;

				if (!bPredictorOrdinateCovered && mss.in (dblPredictorOrdinate)) {
					wj = mss.jackDResponseDManifestMeasure (strManifestMeasure, dblPredictorOrdinate,
						iOrder);

					bPredictorOrdinateCovered = true;
				} else
					wj = new org.drip.numerical.differentiation.WengertJacobian (1, mss.segments().length);

				if (null != wj) lsWJ.add (wj);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return org.drip.service.common.CollectionUtil.AppendWengert (lsWJ);
	}

	@Override public boolean in (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		for (org.drip.spline.stretch.MultiSegmentSequence mss : _lsMSS) {
			if (mss.in (dblPredictorOrdinate)) return true;
		}

		return false;
	}

	/**
	 * Convert the Overlapping Stretch Span to a non-overlapping Stretch Span. Overlapping Stretches are
	 *  clipped from the Left.
	 *  
	 * @return The Non-overlapping Stretch Span Instance
	 */

	public org.drip.spline.grid.Span toNonOverlapping()
	{
		if (0 == _lsMSS.size()) return null;

		org.drip.spline.grid.OverlappingStretchSpan oss = null;
		org.drip.spline.stretch.MultiSegmentSequence mssPrev = null;

		for (org.drip.spline.stretch.MultiSegmentSequence mss : _lsMSS) {
			if (null == mss) continue;

			if (null == oss) {
				try {
					oss = new org.drip.spline.grid.OverlappingStretchSpan (mssPrev = mss);
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			} else {
				double dblPrevRightPredictorOrdinateEdge = mssPrev.getRightPredictorOrdinateEdge();

				double dblCurrentLeftPredictorOrdinateEdge = mss.getLeftPredictorOrdinateEdge();

				if (dblCurrentLeftPredictorOrdinateEdge >= dblPrevRightPredictorOrdinateEdge)
					oss.addStretch (mss);
				else
					oss.addStretch (mss.clipLeft (mss.name(), dblPrevRightPredictorOrdinateEdge));
			}
		}

		return oss;
	}

	@Override public java.lang.String displayString()
	{
		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		for (org.drip.spline.stretch.MultiSegmentSequence mss : _lsMSS)
			sb.append (mss.name() + " | " + new org.drip.analytics.date.JulianDate ((int)
				mss.getLeftPredictorOrdinateEdge()) + " => " + new org.drip.analytics.date.JulianDate ((int)
					mss.getRightPredictorOrdinateEdge()) + "\n");

		return sb.toString();
	}
}
