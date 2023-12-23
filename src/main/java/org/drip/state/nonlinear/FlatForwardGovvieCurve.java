
package org.drip.state.nonlinear;

import org.drip.analytics.daycount.ActActDCParams;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.support.Helper;
import org.drip.state.govvie.ExplicitBootGovvieCurve;

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
 * <i>FlatForwardGovvieCurve</i> manages the Govvie Latent State, using the Flat Forward Rate as the State
 * Response Representation.
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear/README.md">Nonlinear (i.e., Boot) Latent State Construction</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FlatForwardGovvieCurve extends ExplicitBootGovvieCurve
{
	private int[] _dateArray = null;
	private double[] _forwardYieldArray = null;

	private double yearFraction (
		final int startDate,
		final int endDate,
		final ActActDCParams actActDCParams,
		final String dayCount)
		throws Exception
	{
		return Convention.YearFraction (startDate, endDate, dayCount, false, actActDCParams, currency());
	}

	/**
	 * Construct a Govvie Curve from an Array of Dates and Flat Forward Yields
	 * 
	 * @param epochDate Epoch Date
	 * @param treasuryCode Treasury Code
	 * @param currency Currency
	 * @param dateArray Array of Dates
	 * @param forwardYieldArray Array of Forward Yields
	 * 
	 * @throws Exception Thrown if the curve cannot be created
	 */

	public FlatForwardGovvieCurve (
		final int epochDate,
		final String treasuryCode,
		final String currency,
		final int[] dateArray,
		final double[] forwardYieldArray)
		throws Exception
	{
		super (epochDate, treasuryCode, currency);

		if (null == (_dateArray = dateArray) || null == (_forwardYieldArray = forwardYieldArray)) {
			throw new Exception ("FlatForwardGovvieCurve Constructor => Invalid Inputs!");
		}

		int nodeCount = _dateArray.length;

		if (0 == nodeCount || nodeCount != _forwardYieldArray.length) {
			throw new Exception ("FlatForwardGovvieCurve Constructor => Invalid Inputs!");
		}
	}

	@Override public double yld (
		final int date)
		throws Exception
	{
		if (date <= _iEpochDate) {
			return 1.;
		}

		int i = 0;
		double discountFactor = 1.;
		int startDate = _iEpochDate;
		int dateArrayCount = _dateArray.length;

		int frequency = freq();

		String dayCount = dayCount();

		ActActDCParams actActDCParams = ActActDCParams.FromFrequency (frequency);

		while (i < dateArrayCount && (int) date >= (int) _dateArray[i]) {
			discountFactor *= Math.pow (
				1. + (_forwardYieldArray[i] / frequency),
				-1. * yearFraction (startDate, _dateArray[i], actActDCParams, dayCount) * frequency
			);

			startDate = _dateArray[i++];
		}

		if (i >= dateArrayCount) {
			i = dateArrayCount - 1;
		}

		return Helper.DF2Yield (
			frequency,
			discountFactor * Math.pow (1. + (_forwardYieldArray[i] / frequency),
			-1. * yearFraction (startDate, date, actActDCParams, dayCount) * frequency),
			yearFraction (_iEpochDate, date, actActDCParams, dayCount)
		);
	}

	@Override public boolean setNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblValue)) return false;

		int iNumDate = _dateArray.length;

		if (iNodeIndex > iNumDate) return false;

		for (int i = iNodeIndex; i < iNumDate; ++i)
			_forwardYieldArray[i] = dblValue;

		return true;
	}

	@Override public boolean bumpNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblValue)) return false;

		int iNumDate = _dateArray.length;

		if (iNodeIndex > iNumDate) return false;

		for (int i = iNodeIndex; i < iNumDate; ++i)
			_forwardYieldArray[i] += dblValue;

		return true;
	}

	@Override public boolean setFlatValue (
		final double dblValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblValue)) return false;

		int iNumDate = _dateArray.length;

		for (int i = 0; i < iNumDate; ++i)
			_forwardYieldArray[i] = dblValue;

		return true;
	}

	@Override public org.drip.numerical.differentiation.WengertJacobian jackDForwardDManifestMeasure (
		final java.lang.String strManifestMeasure,
		final int iDate)
	{
		return null;
	}
}
