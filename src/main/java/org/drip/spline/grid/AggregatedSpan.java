
package org.drip.spline.grid;

import java.util.List;

import org.drip.spline.stretch.MultiSegmentSequence;

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
 * <i>AggregatedSpan</i> implements the Span interface. Here response from an array of spans whose responses
 * 	are aggregated by their weights.
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

public class AggregatedSpan
	implements Span
{
	private List<Span> _spanList = null;
	private List<Double> _weightList = null;

	/**
	 * <i>AggregatedSpan</i> Constructor
	 * 
	 * @param spanList List of Spans
	 * @param weightList List of Weights
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public AggregatedSpan (
		final List<Span> spanList,
		final List<Double> weightList)
		throws Exception
	{
		if (null == (_spanList = spanList) || null == (_weightList = weightList)) {
			throw new Exception ("AggregatedSpan ctr: Invalid Inputs");
		}

		int spanCount = _spanList.size();

		if (0 == spanCount || spanCount != _weightList.size()) {
			throw new Exception ("AggregatedSpan ctr: Invalid Inputs");
		}

		for (Span span : _spanList) {
			if (null == span) {
				throw new Exception ("AggregatedSpan ctr: Invalid Inputs");
			}
		}
	}

	@Override public boolean addStretch (
		final MultiSegmentSequence multiSegmentSequence)
	{
		return false;
	}

	@Override public MultiSegmentSequence getContainingStretch (
		final double predictorOrdinate)
	{
		for (Span span : _spanList) {
			MultiSegmentSequence multiSegmentSequence = span.getContainingStretch (predictorOrdinate);

			if (null != multiSegmentSequence) {
				return multiSegmentSequence;
			}
		}

		return null;
	}

	@Override public MultiSegmentSequence getStretch (
		final String name)
	{
		if (null == name || name.isEmpty()) {
			return null;
		}

		for (Span span : _spanList) {
			MultiSegmentSequence multiSegmentSequence = span.getStretch (name);

			if (null != multiSegmentSequence) {
				return multiSegmentSequence;
			}
		}

		return null;
	}

	@Override public double left()
		throws Exception
	{
		return _spanList.get (0).left();
	}

	@Override public double right()
		throws Exception
	{
		return _spanList.get (_spanList.size() - 1).right();
	}

	@Override public double calcResponseValue (
		final double predictorOrdinate)
		throws Exception
	{
		int i = 0;
		double responseValue = 0.;

		for (Span span : _spanList) {
			responseValue += span.calcResponseValue (predictorOrdinate) * _weightList.get (i++);
		}

		return responseValue;
	}

	@Override public double calcResponseValueDerivative (
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception
	{
		int i = 0;
		double dblResponseValueDerivative = 0.;

		for (org.drip.spline.grid.Span span : _spanList)
			dblResponseValueDerivative += span.calcResponseValueDerivative (dblPredictorOrdinate,iOrder) *
				_weightList.get (i++);

		return dblResponseValueDerivative;
	}

	@Override public boolean isMergeState (
		final double dblPredictorOrdinate,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		for (org.drip.spline.grid.Span span : _spanList) {
			if (span.isMergeState (dblPredictorOrdinate, lsl)) return true;
		}

		return false;
	}

	@Override public org.drip.numerical.differentiation.WengertJacobian jackDResponseDManifestMeasure (
		final java.lang.String strManifestMeasure,
		final double dblPredictorOrdinate,
		final int iOrder)
	{
		int i = 0;
		org.drip.numerical.differentiation.WengertJacobian wjAggregate = null;

		for (org.drip.spline.grid.Span span : _spanList) {
			org.drip.numerical.differentiation.WengertJacobian wj = span.jackDResponseDManifestMeasure
				(strManifestMeasure, dblPredictorOrdinate, iOrder);

			if (null == wj) return null;

			if (null == wjAggregate) {
				if (!(wjAggregate = wj).scale (_weightList.get (i++))) return null;
			} else {
				if (!wjAggregate.cumulativeMerge (wj, _weightList.get (i++))) return null;
			}
		}

		return wjAggregate;
	}

	@Override public boolean in (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		for (org.drip.spline.grid.Span span : _spanList) {
			if (span.in (dblPredictorOrdinate)) return true;
		}

		return false;
	}

	@Override public java.lang.String displayString()
	{
		int i = 0;

		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		for (org.drip.spline.grid.Span span : _spanList)
			sb.append (span.displayString() + " | " + _weightList.get (i++));

		return sb.toString();
	}
}
