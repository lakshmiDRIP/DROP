
package org.drip.spline.segment;

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
 * <i>LatentStateInelastic</i> contains the spline segment in-elastic fields - in this case the start/end
 * 	ranges. It exports the following functions:
 *
 * <br>
 *  <ul>
 * 		<li><i>LatentStateInelastic</i> constructor</li>
 * 		<li>Retrieve the Segment Left Predictor Ordinate</li>
 * 		<li>Retrieve the Segment Right Predictor Ordinate</li>
 * 		<li>Find out if the Predictor Ordinate is inside the segment - inclusive of left/right</li>
 * 		<li>Get the Width of the Predictor Ordinate in this Segment</li>
 * 		<li>Transform the Predictor Ordinate to the Local Segment Predictor Ordinate</li>
 * 		<li>Transform the Local Predictor Ordinate to the Segment Ordinate</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment/README.md">Flexure Penalizing Best Fit Segment</a></td></tr>
 *  </table>
 *  <br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateInelastic
	implements Comparable<LatentStateInelastic>
{
	private double _predictorOrdinateLeft = Double.NaN;
	private double _predictorOrdinateRight = Double.NaN;

	/**
	 * <i>LatentStateInelastic</i> constructor
	 * 
	 * @param predictorOrdinateLeft Segment Predictor Ordinate Left
	 * @param predictorOrdinateRight Segment Predictor Ordinate Right
	 * 
	 * @throws Exception Thrown if inputs are invalid
	 */

	public LatentStateInelastic (
		final double predictorOrdinateLeft,
		final double predictorOrdinateRight)
		throws Exception
	{
		if (!NumberUtil.IsValid (_predictorOrdinateLeft = predictorOrdinateLeft) ||
			!NumberUtil.IsValid (_predictorOrdinateRight = predictorOrdinateRight) ||
			_predictorOrdinateLeft >= _predictorOrdinateRight) {
			throw new Exception ("LatentStateInelastic ctr: Invalid inputs!");
		}
	}

	/**
	 * Retrieve the Segment Left Predictor Ordinate
	 * 
	 * @return Segment Left Predictor Ordinate
	 */

	public double left()
	{
		return _predictorOrdinateLeft;
	}

	/**
	 * Retrieve the Segment Right Predictor Ordinate
	 * 
	 * @return Segment Right Predictor Ordinate
	 */

	public double right()
	{
		return _predictorOrdinateRight;
	}

	/**
	 * Find out if the Predictor Ordinate is inside the segment - inclusive of left/right.
	 * 
	 * @param predictorOrdinate Predictor Ordinate
	 * 
	 * @return TRUE - Predictor Ordinate is inside the segment
	 * 
	 * @throws Exception Thrown if the input is invalid
	 */

	public boolean in (
		final double predictorOrdinate)
		throws Exception
	{
		if (!NumberUtil.IsValid (predictorOrdinate)) {
			throw new Exception ("LatentStateInelastic::in => Invalid Inputs");
		}

		return _predictorOrdinateLeft <= predictorOrdinate && _predictorOrdinateRight >= predictorOrdinate;
	}

	/**
	 * Get the Width of the Predictor Ordinate in this Segment
	 * 
	 * @return Segment Width
	 */

	public double width()
	{
		return _predictorOrdinateRight - _predictorOrdinateLeft;
	}

	/**
	 * Transform the Predictor Ordinate to the Local Segment Predictor Ordinate
	 * 
	 * @param predictorOrdinate The Global Predictor Ordinate
	 * 
	 * @return Local Segment Predictor Ordinate
	 * 
	 * @throws Exception Thrown if the input is invalid
	 */

	public double localize (
		final double predictorOrdinate)
		throws Exception
	{
		if (!in (predictorOrdinate)) {
			throw new Exception ("LatentStateInelastic::localize: Invalid inputs!");
		}

		return (predictorOrdinate - _predictorOrdinateLeft) /
			(_predictorOrdinateRight - _predictorOrdinateLeft);
	}

	/**
	 * Transform the Local Predictor Ordinate to the Segment Ordinate
	 * 
	 * @param localPredictorOrdinate The Local Segment Predictor Ordinate
	 * 
	 * @return The Segment Ordinate
	 * 
	 * @throws Exception Thrown if the input is invalid
	 */

	public double delocalize (
		final double localPredictorOrdinate)
		throws Exception
	{
		if (!NumberUtil.IsValid (localPredictorOrdinate)) {
			throw new Exception ("LatentStateInelastic::delocalize => Invalid Inputs");
		}

		return _predictorOrdinateLeft + localPredictorOrdinate * (
			_predictorOrdinateRight - _predictorOrdinateLeft
		);
	}

	@Override public int hashCode()
	{
		long bits = Double.doubleToLongBits ((int) _predictorOrdinateLeft);

		return (int) (bits ^ (bits >>> 32));
	}

	@Override public int compareTo (
		final LatentStateInelastic other)
	{
		if (_predictorOrdinateLeft > other._predictorOrdinateLeft) {
			return 1;
		}

		if (_predictorOrdinateLeft < other._predictorOrdinateLeft) {
			return -1;
		}

		return 0;
	}
}
