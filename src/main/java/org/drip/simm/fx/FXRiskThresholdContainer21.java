
package org.drip.simm.fx;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.drip.simm.foundation.RiskGroupPrincipalCovariance;

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
 * <i>FXRiskThresholdContainer21</i> holds the ISDA SIMM 2.1 FX Risk Thresholds - the FX Categories and the
 * 	Delta/Vega Limits defined for the Concentration Thresholds. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  			Calculations https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin
 *  			Requirements - A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167
 *  				<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
 *  			https://www.isda.org/a/oFiDE/isda-simm-v2.pdf
 *  	</li>
 *  </ul>
 *
 * 	It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Initialize the FX Risk Threshold Container</li>
 * 		<li>Retrieve the Category for the specified Currency</li>
 * 		<li>Retrieve the Category Set</li>
 * 		<li>Indicate if the Category identified by the Number is available in the Map</li>
 * 		<li>Retrieve the Risk Group identified by the Category Number</li>
 * 		<li>Retrieve the Delta Threshold for the Category specified</li>
 * 		<li>Retrieve the Vega Threshold for the Category Pair specified</li>
 * 		<li>Retrieve the FX Risk Group Map</li>
 * 		<li>Retrieve the Category Delta Concentration Threshold Map</li>
 * 		<li>Retrieve the Category Vega Concentration Threshold Map</li>
 * 		<li>Retrieve the Cross Risk Group Co-variance Matrix</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/fx/README.md">FX Risk Factor Calibration Settings</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FXRiskThresholdContainer21
{
	private static final Map<String, Double> s_CategoryVega = new HashMap<String, Double>();

	private static final Map<Integer, Double> s_CategoryDelta = new TreeMap<Integer, Double>();

	private static final Map<Integer, FXRiskGroup> s_FXRiskGroupMap = new TreeMap<Integer, FXRiskGroup>();

	/**
	 * Initialize the FX Risk Threshold Container
	 * 
	 * @return TRUE - The FX Risk Threshold Container
	 */

	public static final boolean Init()
	{
		try {
			s_FXRiskGroupMap.put (
				1,
				new FXRiskGroup (
					1,
					"Significantly Material",
					new String[] {
						"USD",
						"EUR",
						"JPY",
						"GBP",
						"AUD",
						"CHF",
						"CAD"
					}
				)
			);

			s_FXRiskGroupMap.put (
				2,
				new FXRiskGroup (
					2,
					"Frequently Traded",
					new String[] {
						"BRL",
						"CNY",
						"HKD",
						"INR",
						"KRW",
						"MXN",
						"NOK",
						"NZD",
						"RUB",
						"SEK",
						"SGD",
						"TRY",
						"ZAR"
					}
				)
			);

			s_FXRiskGroupMap.put (3, new FXRiskGroup (3, "Others", new String[] {"Other"}));

			s_CategoryDelta.put (1, 9700.);

			s_CategoryDelta.put (2, 2900.);

			s_CategoryDelta.put (3, 450.);

			s_CategoryVega.put ("1__1", 2000.);

			s_CategoryVega.put ("1__2", 1000.);

			s_CategoryVega.put ("1__3", 320.);

			s_CategoryVega.put ("2__1", 1000.);

			s_CategoryVega.put ("2__2", 410.);

			s_CategoryVega.put ("2__3",210.);

			s_CategoryVega.put ("3__1",320.);

			s_CategoryVega.put ("3__2", 210.);

			s_CategoryVega.put ("3__3", 150.);
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Category for the specified Currency
	 * 
	 * @param currency Currency
	 * 
	 * @return The Category
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public static final int CurrencyCategory (
		final String currency)
		throws Exception
	{
		if (null == currency || currency.isEmpty()) {
			throw new Exception ("FXRiskThresholdContainer::CurrencyCategory => Invalid Input");
		}

		for (Map.Entry<Integer, FXRiskGroup> fxRiskGroupEntry : s_FXRiskGroupMap.entrySet()) {
			for (String currencyEntry : fxRiskGroupEntry.getValue().currencyArray()) {
				if (currencyEntry.equalsIgnoreCase (currency)) {
					return fxRiskGroupEntry.getKey();
				}
			}
		}

		return s_FXRiskGroupMap.get (3).category();
	}

	/**
	 * Retrieve the Category Set
	 * 
	 * @return The Category Set
	 */

	public static final Set<Integer> CategorySet()
	{
		return s_FXRiskGroupMap.keySet();
	}

	/**
	 * Indicate if the Category identified by the Number is available in the Map
	 * 
	 * @param categoryNumber The Category Number
	 * 
	 * @return TRUE - The Category identified by the Number is available in the Map
	 */

	public static final boolean ContainsCategory (
		final int categoryNumber)
	{
		return s_FXRiskGroupMap.containsKey (categoryNumber);
	}

	/**
	 * Retrieve the Risk Group identified by the Category Number
	 * 
	 * @param categoryNumber The Category Number
	 * 
	 * @return The Risk Group identified by the Category Number
	 */

	public static final FXRiskGroup FXRiskGroup (
		final int categoryNumber)
	{
		return ContainsCategory (categoryNumber) ? s_FXRiskGroupMap.get (categoryNumber) : null;
	}

	/**
	 * Retrieve the Delta Threshold for the Category specified
	 * 
	 * @param categoryNumber The specified Category
	 * 
	 * @return Delta Threshold for the Category specified
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public static final double CategoryDeltaThreshold (
		final int categoryNumber)
		throws Exception
	{
		if (!s_CategoryDelta.containsKey (categoryNumber)) {
			throw new Exception ("FXRiskThresholdContainer::CategoryDeltaThreshold => Invalid Category");
		}

		return s_CategoryDelta.get (categoryNumber);
	}

	/**
	 * Retrieve the Vega Threshold for the Category Pair specified
	 * 
	 * @param categoryNumber1 The specified Category #1
	 * @param categoryNumber2 The specified Category #2
	 * 
	 * @return Vega Threshold for the Category Pair specified
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public static final double CategoryVegaThreshold (
		final int categoryNumber1,
		final int categoryNumber2)
		throws Exception
	{
		String categoryVegaThresholdKey = categoryNumber1 + "__" + categoryNumber2;

		if (!s_CategoryVega.containsKey (categoryVegaThresholdKey)) {
			throw new Exception ("FXRiskThresholdContainer::CategoryVegaThreshold => Invalid Category");
		}

		return s_CategoryVega.get (categoryVegaThresholdKey);
	}

	/**
	 * Retrieve the FX Risk Group Map
	 * 
	 * @return The FX Risk Group Map
	 */

	public static final Map<Integer, FXRiskGroup> FXRiskGroupMap()
	{
		return s_FXRiskGroupMap;
	}

	/**
	 * Retrieve the Category Delta Concentration Threshold Map
	 * 
	 * @return The Category Delta Concentration Threshold Map
	 */

	public static final Map<Integer, Double> CategoryDeltaMap()
	{
		return s_CategoryDelta;
	}

	/**
	 * Retrieve the Category Vega Concentration Threshold Map
	 * 
	 * @return The Category Vega Concentration Threshold Map
	 */

	public static final Map<String, Double> CategoryVegaMap()
	{
		return s_CategoryVega;
	}

	/**
	 * Retrieve the Cross Risk Group Co-variance Matrix
	 * 
	 * @return The Cross Risk Group Co-variance Matrix
	 */

	public static final RiskGroupPrincipalCovariance CrossGroupPrincipalCovariance()
	{
		Set<Integer> fxBucketSet = s_FXRiskGroupMap.keySet();

		int fxRiskGroupCount = fxBucketSet.size();

		double[][] crossGroupCorrelation = new double[fxRiskGroupCount][fxRiskGroupCount];

		for (int fxRiskGroupIndexI = 0; fxRiskGroupIndexI < fxRiskGroupCount; ++fxRiskGroupIndexI) {
			for (int fxRiskGroupIndexJ = 0; fxRiskGroupIndexJ < fxRiskGroupCount; ++fxRiskGroupIndexJ) {
				crossGroupCorrelation[fxRiskGroupIndexI][fxRiskGroupIndexJ] =
					fxRiskGroupIndexI == fxRiskGroupIndexJ ? 1. : FXSystemics21.CORRELATION;
			}
		}

		return RiskGroupPrincipalCovariance.Standard (crossGroupCorrelation, 1.);
	}
}
