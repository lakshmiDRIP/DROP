
package org.drip.simm.commodity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.drip.measure.state.LabelledRdCorrelation;
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
 * <i>CTSettingsContainer20</i> holds the ISDA SIMM 2.0 Commodity Buckets and their Correlations. The
 * 	References are:
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
 * 		<li>Initialize the Commodity Settings Container</li>
 * 		<li>Retrieve the Set of Bucket Indexes available</li>
 * 		<li>Indicate if the Bucket denoted by the Number is available</li>
 * 		<li>Retrieve the Bucket denoted by the Number</li>
 * 		<li>Retrieve the Cross Bucket Correlation</li>
 * 		<li>Retrieve the Bucket Map</li>
 * 		<li>Retrieve the Cross Bucket Co-variance Matrix</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/commodity/README.md">Commodity Risk Factor Calibration Settings</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CTSettingsContainer20
{
	private static LabelledRdCorrelation s_CrossBucketCorrelation = null;

	private static final Map<Integer, CTBucket> s_BucketMap = new TreeMap<Integer, CTBucket>();

	private static final boolean SetUpCrossBucketCorrelation()
	{
		List<String> bucketList = new ArrayList<String>();

		bucketList.add ("1");

		bucketList.add ("2");

		bucketList.add ("3");

		bucketList.add ("4");

		bucketList.add ("5");

		bucketList.add ("6");

		bucketList.add ("7");

		bucketList.add ("8");

		bucketList.add ("9");

		bucketList.add ("10");

		bucketList.add ("11");

		bucketList.add ("12");

		bucketList.add ("13");

		bucketList.add ("14");

		bucketList.add ("15");

		bucketList.add ("16");

		bucketList.add ("17");

		try {
			s_CrossBucketCorrelation = new LabelledRdCorrelation (
				bucketList,
				new double[][] {
					{ 1.00, 0.18, 0.15, 0.20, 0.25, 0.08, 0.19, 0.01, 0.27, 0.00, 0.15, 0.02, 0.06, 0.07,-0.04, 0.00, 0.06}, // #01
					{ 0.18, 1.00, 0.89, 0.94, 0.93, 0.32, 0.22, 0.27, 0.24, 0.09, 0.45, 0.21, 0.32, 0.28, 0.17, 0.00, 0.37}, // #02
					{ 0.15, 0.89, 1.00, 0.87, 0.88, 0.25, 0.16, 0.19, 0.12, 0.10, 0.26,-0.01, 0.19, 0.17, 0.10, 0.00, 0.27}, // #03
					{ 0.20, 0.92, 0.87, 1.00, 0.92, 0.29, 0.22, 0.26, 0.19, 0.00, 0.32, 0.05, 0.20, 0.22, 0.13, 0.00, 0.28}, // #04
					{ 0.25, 0.93, 0.88, 0.92, 1.00, 0.30, 0.26, 0.22, 0.28, 0.12, 0.42, 0.23, 0.28, 0.29, 0.17, 0.00, 0.34}, // #05
					{ 0.08, 0.32, 0.25, 0.29, 0.30, 1.00, 0.13, 0.57, 0.05, 0.14, 0.15,-0.02, 0.13, 0.17, 0.01, 0.00, 0.26}, // #06
					{ 0.19, 0.22, 0.16, 0.22, 0.26, 0.13, 1.00, 0.07, 0.80, 0.19, 0.16, 0.05, 0.17, 0.18, 0.00, 0.00, 0.18}, // #07
					{ 0.01, 0.27, 0.19, 0.26, 0.22, 0.57, 0.07, 1.00, 0.13, 0.06, 0.16, 0.03, 0.10, 0.12, 0.06, 0.00, 0.23}, // #08
					{ 0.27, 0.24, 0.12, 0.19, 0.28, 0.05, 0.80, 0.13, 1.00, 0.15, 0.17, 0.05, 0.15, 0.13,-0.03, 0.00, 0.13}, // #09
					{ 0.00, 0.09, 0.10, 0.00, 0.12, 0.14, 0.19, 0.06, 0.15, 1.00, 0.07, 0.07, 0.17, 0.10, 0.02, 0.00, 0.11}, // #10
					{ 0.15, 0.45, 0.26, 0.32, 0.42, 0.15, 0.16, 0.16, 0.17, 0.07, 1.00, 0.34, 0.20, 0.21, 0.16, 0.00, 0.27}, // #11
					{ 0.02, 0.21,-0.01, 0.05, 0.23,-0.02, 0.05, 0.03, 0.05, 0.07, 0.34, 1.00, 0.17, 0.26, 0.11, 0.00, 0.14}, // #12
					{ 0.06, 0.32, 0.19, 0.20, 0.28, 0.13, 0.17, 0.10, 0.15, 0.17, 0.20, 0.17, 1.00, 0.35, 0.09, 0.00, 0.22}, // #13
					{ 0.07, 0.28, 0.17, 0.22, 0.29, 0.17, 0.18, 0.12, 0.13, 0.10, 0.21, 0.26, 0.35, 1.00, 0.06, 0.00, 0.20}, // #14
					{-0.04, 0.17, 0.10, 0.13, 0.17, 0.01, 0.00, 0.06,-0.03, 0.02, 0.16, 0.11, 0.09, 0.06, 1.00, 0.00, 0.16}, // #15
					{ 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 1.00, 0.00}, // #16
					{ 0.06, 0.27, 0.27, 0.28, 0.34, 0.26, 0.18, 0.23, 0.13, 0.11, 0.27, 0.14, 0.22, 0.20, 0.16, 0.00, 1.00}  // #17
				}
			);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Initialize the Commodity Settings Container
	 * 
	 * @return TRUE - Commodity Settings Container successfully initialized
	 */

	public static final boolean Init()
	{
		try {
			s_BucketMap.put (1, new CTBucket (1, "Coal", 19., 0.30));

			s_BucketMap.put (2, new CTBucket (2, "Crude", 20., 0.97));

			s_BucketMap.put (3, new CTBucket (3, "Light Ends", 17., 0.93));

			s_BucketMap.put (4, new CTBucket (4, "Middle Distillates", 18., 0.98));

			s_BucketMap.put (5, new CTBucket (5, "Heavy Distillates", 24., 0.99));

			s_BucketMap.put (6, new CTBucket (6, "North American Natural Gas",20., 0.92));

			s_BucketMap.put (7, new CTBucket (7, "European Natural Gas", 24., 0.999));

			s_BucketMap.put (8, new CTBucket (8, "North American Power", 41., 0.58));

			s_BucketMap.put (9, new CTBucket (9, "European Power", 25., 0.999));

			s_BucketMap.put (10, new CTBucket (10, "Freight", 91., 0.10));

			s_BucketMap.put (11, new CTBucket (11, "Base Metals", 20., 0.55));

			s_BucketMap.put (12, new CTBucket (12, "Precious Metals", 19., 0.64));

			s_BucketMap.put (13, new CTBucket (13, "Grains", 16., 0.71));

			s_BucketMap.put (14, new CTBucket (14, "Softs", 15., 0.22));

			s_BucketMap.put (15, new CTBucket (15, "Livestock", 10., 0.29));

			s_BucketMap.put (16, new CTBucket (16, "Other",91., 0.));

			s_BucketMap.put (17, new CTBucket (17, "Indexes", 17., 0.21));
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		return SetUpCrossBucketCorrelation();
	}

	/**
	 * Retrieve the Set of Bucket Indexes available
	 * 
	 * @return The Set of Bucket Indexes available
	 */

	public static final Set<Integer> BucketSet()
	{
		return s_BucketMap.keySet();
	}

	/**
	 * Indicate if the Bucket denoted by the Number is available
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return TRUE - The Bucket denoted by the Number is available
	 */

	public static final boolean ContainsBucket (
		final int bucketNumber)
	{
		return s_BucketMap.containsKey (bucketNumber);
	}

	/**
	 * Retrieve the Bucket denoted by the Number
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The Bucket denoted by the Number
	 */

	public static final CTBucket Bucket (
		final int bucketNumber)
	{
		return ContainsBucket (bucketNumber) ? s_BucketMap.get (bucketNumber) : null;
	}

	/**
	 * Retrieve the Cross Bucket Correlation
	 * 
	 * @return The Cross Bucket Correlation
	 */

	public static final LabelledRdCorrelation CrossBucketCorrelation()
	{
		return s_CrossBucketCorrelation;
	}

	/**
	 * Retrieve the Bucket Map
	 * 
	 * @return The Bucket Map
	 */

	public static final Map<Integer, CTBucket> BucketMap()
	{
		return s_BucketMap;
	}

	/**
	 * Retrieve the Cross Bucket Co-variance Matrix
	 * 
	 * @return The Cross Bucket Co-variance Matrix
	 */

	public static final RiskGroupPrincipalCovariance CrossBucketPrincipalCovariance()
	{
		return RiskGroupPrincipalCovariance.Standard (s_CrossBucketCorrelation.matrix(), 1.);
	}
}
