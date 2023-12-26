
package org.drip.state.csa;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.Helper;
import org.drip.state.nonlinear.FlatForwardDiscountCurve;

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
 * <i>MultilateralFlatForwardCurve</i> implements the CSA Cash Rate Curve using a Flat Forward CSA Rate.
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/csa/README.md">Credit Support Annex Latent State</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultilateralFlatForwardCurve extends FlatForwardDiscountCurve implements CashFlowEstimator
{

	/**
	 * <i>MultilateralFlatForwardCurve</i> Constructor
	 * 
	 * @param epochDate Epoch Date
	 * @param currency Currency
	 * @param dateArray Array of Dates
	 * @param forwardRateArray Array of Forward Rates
	 * @param discreteCompounding TRUE - Compounding is Discrete
	 * @param compoundingDayCountConvention Day Count Convention to be used for Discrete Compounding
	 * @param compoundingFrequency Frequency to be used for Discrete Compounding
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public MultilateralFlatForwardCurve (
		final JulianDate epochDate,
		final String currency,
		final int[] dateArray,
		final double[] forwardRateArray,
		final boolean discreteCompounding,
		final String compoundingDayCountConvention,
		final int compoundingFrequency)
		throws Exception
	{
		super (
			epochDate,
			currency,
			dateArray,
			forwardRateArray,
			discreteCompounding,
			compoundingDayCountConvention,
			compoundingFrequency
		);
	}

	@Override public double rate (
		final int date)
		throws Exception
	{
		int epochDate = epoch().julian();

		if (epochDate >= date) {
			throw new Exception ("MultilateralFlatForwardCurve::rate => Invalid Inputs");
		}

		return discreteCompounding() ? ((1. / df (date)) - 1.) / yearFraction (epochDate, date) :
			Helper.DF2Yield (compoundingFrequency(), df (date), yearFraction (epochDate, date));
	}

	@Override public double rate (
		final JulianDate date)
		throws Exception
	{
		if (null == date) {
			throw new Exception ("MultilateralFlatForwardCurve::rate => Invalid Inputs");
		}

		return rate (date.julian());
	}

	@Override public double rate (
		final String tenor)
		throws Exception
	{
		return rate (epoch().addTenor (tenor));
	}

	@Override public double rate (
		final int date1,
		final int date2)
		throws Exception
	{
		int iEpochDate = epoch().julian();

		if (iEpochDate > date1 || date1 >= date2) {
			throw new Exception ("MultilateralFlatForwardCurve::rate => Invalid Inputs");
		}

		return discreteCompounding() ? ((df (date1) / df (date2)) - 1.) / yearFraction (date1, date2) :
			Helper.DF2Yield (compoundingFrequency(), df (date1) / df (date2), yearFraction (date1, date2));
	}

	@Override public double rate (
		final JulianDate date1,
		final JulianDate date2)
		throws Exception
	{
		if (null == date1 || null == date2) {
			throw new Exception ("MultilateralFlatForwardCurve::rate => Invalid Inputs");
		}

		return rate (date1.julian(), date2.julian());
	}

	@Override public double rate (
		final String tenor1,
		final String tenor2)
		throws Exception
	{
		JulianDate epochDate = epoch();

		return rate (epochDate.addTenor (tenor1), epochDate.addTenor (tenor2));
	}
}
