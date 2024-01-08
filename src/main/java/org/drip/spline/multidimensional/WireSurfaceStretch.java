
package org.drip.spline.multidimensional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.drip.numerical.common.NumberUtil;
import org.drip.service.common.StringUtil;
import org.drip.spline.grid.AggregatedSpan;
import org.drip.spline.grid.OverlappingStretchSpan;
import org.drip.spline.grid.Span;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.stretch.BoundarySettings;
import org.drip.spline.stretch.MultiSegmentSequence;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;

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
 * <i>WireSurfaceStretch</i> implements a 2D spline surface stretch. It synthesizes this from an array of 1D
 * 	Span instances, each of which is referred to as wire spline in this case.
 *
 * <br>
 *  <ul>
 * 		<li><i>WireSurfaceStretch</i> Constructor</li>
 * 		<li>Compute the Bivariate Surface Response Value</li>
 * 		<li>Retrieve the Surface Span Stretch that corresponds to the given Y Anchor</li>
 * 		<li>Retrieve the Surface Span Stretch that corresponds to the given X Anchor</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/multidimensional/README.md">Multi-dimensional Wire Surface Stretch</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class WireSurfaceStretch
{
	private TreeMap<Double, Span> _wireSpanMap = null;
	private SegmentCustomBuilderControl _segmentCustomBuilderControl = null;

	/**
	 * <i>WireSurfaceStretch</i> Constructor
	 * 
	 * @param name Name
	 * @param segmentCustomBuilderControl Segment Custom Builder Control Parameters
	 * @param wireSpanMap X-mapped Array of Y Basis Spline Wire Spans
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	public WireSurfaceStretch (
		final String name,
		final SegmentCustomBuilderControl segmentCustomBuilderControl,
		final TreeMap<Double, Span> wireSpanMap)
		throws Exception
	{
		if (null == (_wireSpanMap = wireSpanMap) || 0 == _wireSpanMap.size() ||
			null == (_segmentCustomBuilderControl = segmentCustomBuilderControl)) {
			throw new Exception ("WireSurfaceStretch ctr: Invalid Inputs");
		}
	}

	/**
	 * Compute the Bivariate Surface Response Value
	 * 
	 * @param x X
	 * @param y Y
	 * 
	 * @return The Bivariate Surface Response Value
	 * 
	 * @throws Exception Thrown if Inputs are Invalid
	 */

	public double responseValue (
		final double x,
		final double y)
		throws Exception
	{
		int wireSpanMapSize = _wireSpanMap.size();

		int i = 0;
		double[] xArray = new double[wireSpanMapSize];
		double[] zArray = new double[wireSpanMapSize];
		SegmentCustomBuilderControl[] segmentCustomBuilderControlArray =
			new SegmentCustomBuilderControl[wireSpanMapSize - 1];

		for (Map.Entry<Double, Span> wireSpanMapEntry : _wireSpanMap.entrySet()) {
			if (null == wireSpanMapEntry) {
				throw new Exception ("WireSurfaceStretch::responseValue => Invalid Wire Span Map");
			}

			if (0 != i) {
				segmentCustomBuilderControlArray[i - 1] = _segmentCustomBuilderControl;
			}

			xArray[i] = wireSpanMapEntry.getKey();

			Span wireSpan = wireSpanMapEntry.getValue();

			if (null == wireSpan) {
				throw new Exception ("WireSurfaceStretch::responseValue => Invalid Wire Span Map");
			}

			double leftY = wireSpan.left();

			double rightY = wireSpan.right();

			if (y <= leftY) {
				zArray[i++] = wireSpan.calcResponseValue (leftY);
			} else if (y >= rightY) {
				zArray[i++] = wireSpan.calcResponseValue (rightY);
			} else {
				zArray[i++] = wireSpan.calcResponseValue (y);
			}
		}

		MultiSegmentSequence multiSegmentSequence =
			MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
				"org.drip.spline.multidimensional.WireSurfaceStretch@" + StringUtil.GUID(),
				xArray,
				zArray,
				segmentCustomBuilderControlArray,
				null,
				BoundarySettings.NaturalStandard(),
				MultiSegmentSequence.CALIBRATE
			);

		if (null == multiSegmentSequence) {
			throw new Exception ("WireSurfaceStretch::responseValue => Cannot extract MSS");
		}

		double leftX = multiSegmentSequence.getLeftPredictorOrdinateEdge();

		if (x <= leftX) {
			return multiSegmentSequence.responseValue (leftX);
		}

		double rightX = multiSegmentSequence.getRightPredictorOrdinateEdge();

		if (x >= rightX) {
			return multiSegmentSequence.responseValue (rightX);
		}

		return multiSegmentSequence.responseValue (x);
	}

	/**
	 * Retrieve the Surface Span Stretch that corresponds to the given Y Anchor
	 * 
	 * @param yAnchor Y Anchor
	 * 
	 * @return The Surface Span Stretch Instance
	 */

	public Span wireSpanYAnchor (
		final double yAnchor)
	{
		if (!NumberUtil.IsValid (yAnchor)) {
			return null;
		}

		int wireSpanMapSize = _wireSpanMap.size();

		int i = 0;
		double[] xArray = new double[wireSpanMapSize];
		double[] zArray = new double[wireSpanMapSize];
		SegmentCustomBuilderControl[] segmentCustomBuilderControlArray =
			new SegmentCustomBuilderControl[wireSpanMapSize - 1];

		for (Map.Entry<Double, Span> wireSpanMapEntry : _wireSpanMap.entrySet()) {
			if (null == wireSpanMapEntry) {
				return null;
			}

			if (0 != i) {
				segmentCustomBuilderControlArray[i - 1] = _segmentCustomBuilderControl;
			}

			xArray[i] = wireSpanMapEntry.getKey();

			Span wireSpan = wireSpanMapEntry.getValue();

			if (null == wireSpan) {
				return null;
			}

			try {
				double leftY = wireSpan.left();

				double rightY = wireSpan.right();

				if (yAnchor <= leftY) {
					zArray[i++] = wireSpan.calcResponseValue (leftY);
				} else if (yAnchor >= rightY) {
					zArray[i++] = wireSpan.calcResponseValue (rightY);
				} else {
					zArray[i++] = wireSpan.calcResponseValue (yAnchor);
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		try {
			return new OverlappingStretchSpan (
				MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
					"org.drip.spline.multidimensional.WireSurfaceStretch@" + StringUtil.GUID(),
					xArray,
					zArray,
					segmentCustomBuilderControlArray,
					null,
					BoundarySettings.NaturalStandard(),
					MultiSegmentSequence.CALIBRATE
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Surface Span Stretch that corresponds to the given X Anchor
	 * 
	 * @param xAnchor X Anchor
	 * 
	 * @return The Surface Span Stretch Instance
	 */

	public Span wireSpanXAnchor (
		final double xAnchor)
	{
		if (!NumberUtil.IsValid (xAnchor)) {
			return null;
		}

		Span currentSpan = null;
		Span previousSpan = null;
		double currentXAnchor = Double.NaN;
		double previousXAnchor = Double.NaN;

		for (Map.Entry<Double, Span> wireSpanMapEntry : _wireSpanMap.entrySet()) {
			if (null == wireSpanMapEntry) {
				return null;
			}

			currentXAnchor = wireSpanMapEntry.getKey();

			currentSpan = wireSpanMapEntry.getValue();

			if (!NumberUtil.IsValid (previousXAnchor)) {
				if (xAnchor <= (previousXAnchor = currentXAnchor)) {
					return currentSpan;
				}

				previousSpan = currentSpan;
				continue;
			}

			if (xAnchor > previousXAnchor && xAnchor <= currentXAnchor) {
				double leftWeight = (currentXAnchor - xAnchor) / (currentXAnchor - previousXAnchor);

				List<Double> weightList = new ArrayList<Double>();

				List<Span> spanList = new ArrayList<Span>();

				spanList.add (previousSpan);

				spanList.add (currentSpan);

				weightList.add (leftWeight);

				weightList.add (1. - leftWeight);

				try {
					return new AggregatedSpan (spanList, weightList);
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			}

			previousSpan = currentSpan;
			previousXAnchor = currentXAnchor;
		}

		return currentSpan;
	}
}
