
package org.drip.state.curve;

import org.drip.analytics.definition.Curve;
import org.drip.analytics.definition.LatentStateStatic;
import org.drip.analytics.input.CurveConstructionInputSet;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.analytics.support.Helper;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.differentiation.WengertJacobian;
import org.drip.param.definition.ManifestMeasureTweak;
import org.drip.product.definition.CalibratableComponent;
import org.drip.spline.grid.Span;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.forward.ForwardRateEstimator;
import org.drip.state.identifier.ForwardLabel;

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
 * <i>ZeroRateDiscountCurve</i> manages the Discounting Latent State, using the Zero Rate as the State
 * 	Response Representation. It exports the following functionality:
 *
 *  <br><br>
 *  <ul>
 *  	<li><i>ZeroRateDiscountCurve</i> Constructor</li>
 *  	<li>Compute the discount factor, forward rate, or the zero rate from the Zero Rate Latent State</li>
 *  	<li>Create a ForwardRateEstimator instance for the given Index</li>
 *  	<li>Retrieve Array of the Calibration Components</li>
 *  	<li>Retrieve the Curve Construction Input Set</li>
 *  	<li>Compute the Jacobian of the Discount Factor Latent State to the input Quote</li>
 *  	<li>Synthesize scenario Latent State by parallel shifting/custom tweaking the quantification metric</li>
 *  	<li>Synthesize scenario Latent State by parallel/custom shifting/custom tweaking the manifest measure</li>
 *  	<li>Serialize into and de-serialize out of byte array</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve/README.md">Basis Spline Based Latent States</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ZeroRateDiscountCurve extends MergedDiscountForwardCurve
{
	private Span _span = null;
	private double _rightFlatForwardRate = Double.NaN;
	private CurveConstructionInputSet _curveConstructionInputSet = null;

	private ZeroRateDiscountCurve shiftManifestMeasure (
		final double[] shiftedManifestMeasureArray)
	{
		return null;
	}

	/**
	 * <i>ZeroRateDiscountCurve</i> constructor
	 * 
	 * @param currency Currency
	 * @param span The Span Instance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public ZeroRateDiscountCurve (
		final String currency,
		final Span span)
		throws Exception
	{
		super ((int) span.left(), currency, null);

		_rightFlatForwardRate = (_span = span).calcResponseValue (_span.right());
	}

	@Override public double df (
		final int date)
		throws Exception
	{
		int startDate = epoch().julian();

		return date <= startDate ? 1. : Math.exp (-1. * zero (date) * (date - startDate) / 365.25) *
			turnAdjust (startDate, date);
	}

	public double forward (
		final int date1,
		final int date2)
		throws Exception
	{
		int startDate = epoch().julian();

		return date1 < startDate || date2 < startDate ? 0. :
			365.25 / (date2 - date1) * Math.log (df (date1) / df (date2));
	}

	@Override public double zero (
		final int date)
		throws Exception
	{
		return date <= _span.left() ? 1. : date <= _span.right() ?
			_span.calcResponseValue (date) : _rightFlatForwardRate;
	}

	@Override public ForwardRateEstimator forwardRateEstimator (
		final int date,
		final ForwardLabel forwardLabel)
	{
		return null;
	}

	@Override public String latentStateQuantificationMetric()
	{
		return LatentStateStatic.DISCOUNT_QM_ZERO_RATE;
	}

	@Override public ZeroRateDiscountCurve parallelShiftManifestMeasure (
		final String manifestMeasure,
		final double shift)
	{
		if (!NumberUtil.IsValid (shift)) {
			return null;
		}

		CalibratableComponent[] calibratableComponentArray = calibComp();

		if (null == calibratableComponentArray) {
			return null;
		}

		int componentCount = calibratableComponentArray.length;
		double[] shiftedManifestMeasureArray = new double[componentCount];

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			shiftedManifestMeasureArray[componentIndex] += shift;
		}

		return shiftManifestMeasure (shiftedManifestMeasureArray);
	}

	@Override public ZeroRateDiscountCurve shiftManifestMeasure (
		final int spanIndex,
		final String manifestMeasure,
		final double shift)
	{
		if (!NumberUtil.IsValid (shift)) {
			return null;
		}

		CalibratableComponent[] calibratableComponentArray = calibComp();

		if (null == calibratableComponentArray) {
			return null;
		}

		int componentCount = calibratableComponentArray.length;
		double[] shiftedManifestMeasureArray = new double[componentCount];

		if (spanIndex >= componentCount) {
			return null;
		}

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			shiftedManifestMeasureArray[componentIndex] += (componentIndex == spanIndex ? shift : 0.);
		}

		return shiftManifestMeasure (shiftedManifestMeasureArray);
	}

	@Override public MergedDiscountForwardCurve customTweakManifestMeasure (
		final String manifestMeasure,
		final ManifestMeasureTweak manifestMeasureTweak)
	{
		if (null == manifestMeasureTweak) {
			return null;
		}

		CalibratableComponent[] calibratableComponentArray = calibComp();

		if (null == calibratableComponentArray) {
			return null;
		}

		CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>> doubleQuoteMap =
			_curveConstructionInputSet.quoteMap();

		if (null == doubleQuoteMap || 0 == doubleQuoteMap.size()) {
			return null;
		}

		int componentCount = calibratableComponentArray.length;
		double[] quoteArray = new double[componentCount];

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			quoteArray[componentIndex] = doubleQuoteMap.get (
				calibratableComponentArray[componentIndex].primaryCode()
			).get (manifestMeasure);
		}

		return shiftManifestMeasure (Helper.TweakManifestMeasure (quoteArray, manifestMeasureTweak));
	}

	@Override public ZeroRateDiscountCurve parallelShiftQuantificationMetric (
		final double shift)
	{
		return null;
	}

	@Override public Curve customTweakQuantificationMetric (
		final ManifestMeasureTweak manifestMeasureTweak)
	{
		return null;
	}

	@Override public WengertJacobian jackDDFDManifestMeasure (
		final int date,
		final String manifestMeasure)
	{
		return null;
	}

	@Override public CalibratableComponent[] calibComp()
	{
		return null == _curveConstructionInputSet ? null : _curveConstructionInputSet.components();
	}

	@Override public CaseInsensitiveTreeMap<Double> manifestMeasure (
		final String instrumentCode)
	{
		return null;
	}
}
