
package org.drip.state.discount;

import org.drip.analytics.date.JulianDate;

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
 * <i>DiscountFactorEstimator</i> is the interface that exposes the calculation of the Discount Factor for a
 * specific Sovereign/Jurisdiction Span. It exposes the following functionality:
 * 
 *  <ul>
 *  	<li>Retrieve the Starting (Epoch) Date</li>
 *  	<li>Calculate the Discount Factor to the given Date</li>
 *  	<li>Calculate the Discount Factor to the given Tenor</li>
 *  	<li>Compute the time-weighted discount factor between 2 dates</li>
 *  	<li>Compute the time-weighted discount factor between 2 tenors</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/discount/README.md">Discount Curve Spline Latent State</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public interface DiscountFactorEstimator
{

	/**
	 * Retrieve the Starting (Epoch) Date
	 * 
	 * @return The Starting Date
	 */

	public abstract JulianDate epoch();

	/**
	 * Calculate the Discount Factor to the given Date
	 * 
	 * @param date Date
	 * 
	 * @return Discount Factor
	 * 
	 * @throws Exception Thrown if the Discount Factor cannot be calculated
	 */

	public abstract double df (
		final int date)
		throws Exception;

	/**
	 * Calculate the discount factor to the given date
	 * 
	 * @param date Date
	 * 
	 * @return Discount factor
	 * 
	 * @throws Exception Thrown if the discount factor cannot be calculated
	 */

	public abstract double df (
		final JulianDate date)
		throws Exception;

	/**
	 * Calculate the Discount Factor to the given Tenor
	 * 
	 * @param tenor Tenor
	 * 
	 * @return Discount factor
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Factor cannot be calculated
	 */

	public abstract double df (
		final String tenor)
		throws Exception;

	/**
	 * Compute the time-weighted discount factor between 2 dates
	 * 
	 * @param date1 First Date
	 * @param date2 Second Date
	 * 
	 * @return Discount Factor
	 * 
	 * @throws Exception Thrown if the discount factor cannot be calculated
	 */

	public abstract double effectiveDF (
		final int date1,
		final int date2)
		throws Exception;

	/**
	 * Compute the time-weighted discount factor between 2 dates
	 * 
	 * @param date1 First Date
	 * @param date2 Second Date
	 * 
	 * @return Discount Factor
	 * 
	 * @throws Exception Thrown if the discount factor cannot be calculated
	 */

	public abstract double effectiveDF (
		final JulianDate date1,
		final JulianDate date2)
		throws Exception;

	/**
	 * Compute the time-weighted discount factor between 2 tenors
	 * 
	 * @param tenor1 First Date
	 * @param tenor2 Second Date
	 * 
	 * @return Discount Factor
	 * 
	 * @throws Exception Thrown if the discount factor cannot be calculated
	 */

	public abstract double effectiveDF (
		final String tenor1,
		final String tenor2)
		throws Exception;
}
