
package org.drip.spline.grid;

import java.util.ArrayList;
import java.util.List;

import org.drip.analytics.date.JulianDate;
import org.drip.numerical.differentiation.WengertJacobian;
import org.drip.service.common.CollectionUtil;
import org.drip.spline.stretch.MultiSegmentSequence;
import org.drip.state.identifier.LatentStateLabel;
import org.drip.state.representation.MergeSubStretchManager;

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
 * 	overlapping Stretches. In addition to providing a custom implementation of all the Span interface stubs,
 * 	it also converts the Overlapping Stretch Span to a non-overlapping Stretch Span. Overlapping Stretches
 * 	are clipped from the Left.
 *
 * <ul>
 * 		<li><i>OverlappingStretchSpan</i> constructor</li>
 * 		<li>Convert the Overlapping Stretch Span to a non-overlapping Stretch Span. Overlapping Stretches are clipped from the Left</li>
 * </ul>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/grid/README.md">Aggregated/Overlapping Stretch/Span Grids</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OverlappingStretchSpan
	implements Span
{
	private List<MultiSegmentSequence> _multiSegmentSequenceList = new ArrayList<MultiSegmentSequence>();

	/**
	 * <i>OverlappingStretchSpan</i> constructor
	 * 
	 * @param multiSegmentSequence The Initial Stretch in the Span
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	public OverlappingStretchSpan (
		final MultiSegmentSequence multiSegmentSequence)
		throws Exception
	{
		if (null == multiSegmentSequence) {
			throw new Exception ("OverlappingStretchSpan ctr: Invalid Inputs");
		}

		_multiSegmentSequenceList.add (multiSegmentSequence);
	}

	@Override public boolean addStretch (
		final MultiSegmentSequence multiSegmentSequence)
	{
		if (null == multiSegmentSequence) {
			return false;
		}

		_multiSegmentSequenceList.add (multiSegmentSequence);

		return true;
	}

	@Override public MultiSegmentSequence getContainingStretch (
		final double predictorOrdinate)
	{
		if (null == _multiSegmentSequenceList || 0 == _multiSegmentSequenceList.size()) {
			return null;
		}

		for (MultiSegmentSequence multiSegmentSequence : _multiSegmentSequenceList) {
			try {
				if (multiSegmentSequence.in (predictorOrdinate)) {
					return multiSegmentSequence;
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return null;
	}

	@Override public MultiSegmentSequence getStretch (
		final String name)
	{
		if (null == name) {
			return null;
		}

		for (MultiSegmentSequence multiSegmentSequence : _multiSegmentSequenceList) {
			if (name.equalsIgnoreCase (multiSegmentSequence.name())) {
				return multiSegmentSequence;
			}
		}

		return null;
	}

	@Override public double left()
		throws Exception
	{
		if (0 == _multiSegmentSequenceList.size()) {
			throw new Exception ("OverlappingStretchSpan::left => No valid Stretches found");
		}

		return _multiSegmentSequenceList.get (0).getLeftPredictorOrdinateEdge();
	}

	@Override public double right()
		throws Exception
	{
		if (0 == _multiSegmentSequenceList.size()) {
			throw new Exception ("OverlappingStretchSpan::right => No valid Stretches found");
		}

		return _multiSegmentSequenceList.get (
			_multiSegmentSequenceList.size() - 1
		).getRightPredictorOrdinateEdge();
	}

	@Override public double calcResponseValue (
		final double predictorOrdinate)
		throws Exception
	{
		for (MultiSegmentSequence multiSegmentSequence : _multiSegmentSequenceList) {
			if (multiSegmentSequence.in (predictorOrdinate)) {
				return multiSegmentSequence.responseValue (predictorOrdinate);
			}
		}

		throw new Exception ("OverlappingStretchSpan::calcResponseValue => Cannot Calculate!");
	}

	@Override public double calcResponseValueDerivative (
		final double predictorOrdinate,
		final int order)
		throws java.lang.Exception
	{
		for (MultiSegmentSequence multiSegmentSequence : _multiSegmentSequenceList) {
			if (multiSegmentSequence.in (predictorOrdinate)) {
				return multiSegmentSequence.responseValueDerivative (predictorOrdinate, order);
			}
		}

		throw new Exception ("OverlappingStretchSpan::calcResponseValueDerivative => Cannot Calculate!");
	}

	@Override public boolean isMergeState (
		final double predictorOrdinate,
		final LatentStateLabel latentStateLabel)
	{
		try {
			for (MultiSegmentSequence multiSegmentSequence : _multiSegmentSequenceList) {
				if (multiSegmentSequence.in (predictorOrdinate)) {
					MergeSubStretchManager mergeSubStretchManager = multiSegmentSequence.msm();

					return null == mergeSubStretchManager ? false :
						mergeSubStretchManager.partOfMergeState (predictorOrdinate, latentStateLabel);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override public WengertJacobian jackDResponseDManifestMeasure (
		final String manifestMeasure,
		final double predictorOrdinate,
		final int order)
	{
		if (0 == _multiSegmentSequenceList.size()) {
			return null;
		}

		List<WengertJacobian> wengertJacobianList = new ArrayList<WengertJacobian>();

		boolean predictorOrdinateCovered = false;

		for (MultiSegmentSequence multiSegmentSequence : _multiSegmentSequenceList) {
			if (null == multiSegmentSequence) {
				continue;
			}

			try {
				WengertJacobian wengertJacobian = null;

				if (!predictorOrdinateCovered && multiSegmentSequence.in (predictorOrdinate)) {
					wengertJacobian = multiSegmentSequence.jackDResponseDManifestMeasure (
						manifestMeasure,
						predictorOrdinate,
						order
					);

					predictorOrdinateCovered = true;
				} else {
					wengertJacobian = new WengertJacobian (1, multiSegmentSequence.segments().length);
				}

				if (null != wengertJacobian) {
					wengertJacobianList.add (wengertJacobian);
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return CollectionUtil.AppendWengert (wengertJacobianList);
	}

	@Override public boolean in (
		final double predictorOrdinate)
		throws Exception
	{
		for (MultiSegmentSequence multiSegmentSequence : _multiSegmentSequenceList) {
			if (multiSegmentSequence.in (predictorOrdinate)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Convert the Overlapping Stretch Span to a non-overlapping Stretch Span. Overlapping Stretches are
	 *  clipped from the Left.
	 *  
	 * @return The Non-overlapping Stretch Span Instance
	 */

	public Span toNonOverlapping()
	{
		if (0 == _multiSegmentSequenceList.size()) {
			return null;
		}

		OverlappingStretchSpan overlappingStretchSpan = null;
		MultiSegmentSequence previousMultiSegmentSequence = null;

		for (MultiSegmentSequence multiSegmentSequence : _multiSegmentSequenceList) {
			if (null == multiSegmentSequence) {
				continue;
			}

			if (null == overlappingStretchSpan) {
				try {
					overlappingStretchSpan = new OverlappingStretchSpan (
						previousMultiSegmentSequence = multiSegmentSequence
					);
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			} else {
				double previousRightPredictorOrdinateEdge =
					previousMultiSegmentSequence.getRightPredictorOrdinateEdge();

				double currentLeftPredictorOrdinateEdge =
					multiSegmentSequence.getLeftPredictorOrdinateEdge();

				if (currentLeftPredictorOrdinateEdge >= previousRightPredictorOrdinateEdge) {
					overlappingStretchSpan.addStretch (multiSegmentSequence);
				} else {
					overlappingStretchSpan.addStretch (
						multiSegmentSequence.clipLeft (
							multiSegmentSequence.name(),
							previousRightPredictorOrdinateEdge
						)
					);
				}
			}
		}

		return overlappingStretchSpan;
	}

	@Override public String displayString()
	{
		StringBuffer stringBuffer = new java.lang.StringBuffer();

		for (MultiSegmentSequence multiSegmentSequence : _multiSegmentSequenceList) {
			stringBuffer.append (
				multiSegmentSequence.name() + " | " +
					new JulianDate ((int) multiSegmentSequence.getLeftPredictorOrdinateEdge()) + " => " +
					new JulianDate ((int) multiSegmentSequence.getRightPredictorOrdinateEdge()) + "\n"
			);
		}

		return stringBuffer.toString();
	}
}
