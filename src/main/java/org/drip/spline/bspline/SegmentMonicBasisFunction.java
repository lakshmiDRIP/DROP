
package org.drip.spline.bspline;

import org.drip.numerical.common.NumberUtil;

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
 * <i>SegmentMonicBasisFunction</i> implements the local monic B Spline that envelopes the predictor
 * 	ordinates, and the corresponding set of ordinates/basis functions. SegmentMonicBasisFunction uses the
 * 	left/right TensionBasisHat instances to achieve its implementation goals.
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/bspline/README.md">de Boor Rational/Exponential/Tension B-Splines</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SegmentMonicBasisFunction
	extends SegmentBasisFunction
{
	private TensionBasisHat _leftTensionBasisHat = null;
	private TensionBasisHat _rightTensionBasisHat = null;

	/**
	 * SegmentMonicBasisFunction constructor
	 * 
	 * @param leftTensionBasisHat Left Tension Basis Hat Function
	 * @param rightTensionBasisHat Right Tension Basis Hat Function
	 * 
	 * @throws Exception Thrown if Inputs are invalid
	 */

	public SegmentMonicBasisFunction (
		final TensionBasisHat leftTensionBasisHat,
		final TensionBasisHat rightTensionBasisHat)
		throws Exception
	{
		super (2, leftTensionBasisHat.left(), rightTensionBasisHat.left(), rightTensionBasisHat.right());

		if (null == (_leftTensionBasisHat = leftTensionBasisHat) ||
			null == (_rightTensionBasisHat = rightTensionBasisHat)) {
			throw new Exception ("SegmentMonicBasisFunction ctr: Invalid Inputs");
		}
	}

	@Override public double evaluate (
		final double predictorOrdinate)
		throws Exception
	{
		if (!NumberUtil.IsValid (predictorOrdinate)) {
			throw new Exception ("SegmentMonicBasisFunction::evaluate => Invalid Inputs");
		}

		if (predictorOrdinate < leading() || predictorOrdinate > trailing()) {
			return 0.;
		}

		return predictorOrdinate < following() ? _leftTensionBasisHat.evaluate (predictorOrdinate) :
			_rightTensionBasisHat.evaluate (predictorOrdinate);
	}

	@Override public double derivative (
		final double predictorOrdinate,
		final int order)
		throws Exception
	{
		if (!NumberUtil.IsValid (predictorOrdinate)) {
			throw new Exception ("SegmentMonicBasisFunction::derivative => Invalid Inputs");
		}

		if (predictorOrdinate < leading() || predictorOrdinate > trailing()) {
			return 0.;
		}

		return predictorOrdinate < following() ? _leftTensionBasisHat.derivative (predictorOrdinate, order) :
			_rightTensionBasisHat.derivative (predictorOrdinate, order);
	}

	@Override public double integrate (
		final double begin,
		final double end)
		throws Exception
	{
		if (!NumberUtil.IsValid (begin) || !NumberUtil.IsValid (end)) {
			throw new Exception ("SegmentMonicBasisFunction::integrate => Invalid Inputs");
		}

		if (begin >= end) {
			return 0.;
		}

		double leading = leading();

		double trailing = trailing();

		double following = following();

		if (begin <= leading) {
			return end <= leading ? 0. :
				_leftTensionBasisHat.integrate (leading, end <= following ? end : following) +
				_rightTensionBasisHat.integrate (following, end <= trailing ? end : trailing);
		}

		if (begin <= following) {
			return _leftTensionBasisHat.integrate (begin, end <= following ? end : following) +
				_rightTensionBasisHat.integrate (following, end <= trailing ? end : trailing);
		}

		return begin <= trailing ? 0. :
			_rightTensionBasisHat.integrate (following, end <= trailing ? end : trailing);
	}

	@Override public double normalizer()
		throws Exception
	{
		double following = following();

		return _leftTensionBasisHat.integrate (leading(), following) +
			_rightTensionBasisHat.integrate (following, trailing());
	}

	@Override public double normalizedCumulative (
		final double predictorOrdinate)
		throws Exception
	{
		if (!NumberUtil.IsValid (predictorOrdinate)) {
			throw new Exception ("SegmentMonicBasisFunction::normalizedCumulative => Invalid Inputs");
		}

		double leading = leading();

		double following = following();

		if (predictorOrdinate <= leading) {
			return 0.;
		}

		if (predictorOrdinate >= trailing()) {
			return 1.;
		}

		double normalizer = normalizer();

		return predictorOrdinate <= following ?
			_leftTensionBasisHat.integrate (leading, predictorOrdinate) / normalizer : (
				_leftTensionBasisHat.integrate (leading, following) +
				_rightTensionBasisHat.integrate (following, predictorOrdinate)
			) / normalizer;
	}
}
