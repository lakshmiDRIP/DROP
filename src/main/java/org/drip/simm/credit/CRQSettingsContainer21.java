
package org.drip.simm.credit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.drip.measure.identifier.LabelCorrelation;

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
 * <i>CRQSettingsContainer21</i> holds the ISDA SIMM 2.1 Credit Qualifying Buckets. The References are:
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
 * 		<li>Initialize the Credit Qualifying Settings</li>
 * 		<li>Retrieve the Standard ISDA Credit Tenor Set</li>
 * 		<li>Retrieve the Set of Bucket Indexes available</li>
 * 		<li>Indicate if the Bucket denoted by the Number is available</li>
 * 		<li>Retrieve the Bucket denoted by the Number</li>
 * 		<li>Retrieve the Cross Bucket Correlation</li>
 * 		<li>Retrieve the Bucket Map</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/README.md">Credit Qualifying/Non-Qualifying Risk Factor Settings</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CRQSettingsContainer21
{
	private static LabelCorrelation s_CrossBucketCorrelation = null;

	private static final Map<Integer, CRBucket> s_BucketMap = new TreeMap<Integer, CRBucket>();

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

		try {
			s_CrossBucketCorrelation = new LabelCorrelation (
				bucketList,
				new double[][] {
					{1.00, 0.38, 0.36, 0.36, 0.39, 0.35, 0.34, 0.32, 0.34, 0.33, 0.34, 0.31}, // #01
					{0.38, 1.00, 0.41, 0.41, 0.43, 0.40, 0.29, 0.38, 0.38, 0.38, 0.38, 0.34}, // #02
					{0.36, 0.41, 1.00, 0.41, 0.42, 0.39, 0.30, 0.34, 0.39, 0.37, 0.38, 0.35}, // #03
					{0.36, 0.41, 0.41, 1.00, 0.43, 0.40, 0.28, 0.33, 0.37, 0.38, 0.38, 0.34}, // #04
					{0.39, 0.43, 0.42, 0.43, 1.00, 0.42, 0.31, 0.35, 0.38, 0.39, 0.41, 0.36}, // #05
					{0.35, 0.40, 0.39, 0.40, 0.42, 1.00, 0.27, 0.32, 0.34, 0.35, 0.36, 0.33}, // #06
					{0.34, 0.29, 0.30, 0.28, 0.31, 0.27, 1.00, 0.24, 0.28, 0.27, 0.27, 0.26}, // #07
					{0.32, 0.38, 0.34, 0.33, 0.35, 0.32, 0.24, 1.00, 0.33, 0.32, 0.32, 0.29}, // #08
					{0.34, 0.38, 0.39, 0.37, 0.38, 0.34, 0.28, 0.33, 1.00, 0.35, 0.35, 0.33}, // #09
					{0.33, 0.38, 0.37, 0.38, 0.39, 0.35, 0.27, 0.32, 0.35, 1.00, 0.36, 0.32}, // #10
					{0.34, 0.38, 0.38, 0.38, 0.41, 0.36, 0.27, 0.32, 0.35, 0.36, 1.00, 0.33}, // #11
					{0.31, 0.34, 0.35, 0.34, 0.36, 0.33, 0.26, 0.29, 0.33, 0.32, 0.33, 1.00}  // #12
				}
			);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Initial the Credit Qualifying Settings
	 * 
	 * @return TRUE - The Credit Qualifying Settings successfully initialized
	 */

	public static final boolean Init()
	{
		try {
			s_BucketMap.put (
				-1,
				new CRBucket (-1, CRSystemics.CREDIT_QUALITY_UNSPECIFIED, SectorSystemics.RESIDUAL, 187.)
			);

			s_BucketMap.put (
				1,
				new CRBucket (
					1,
					CRSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					SectorSystemics.SOVEREIGNS,
					69.
				)
			);

			s_BucketMap.put (
				2,
				new CRBucket (
					2,
					CRSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					SectorSystemics.FINANCIALS,
					107.
				)
			);

			s_BucketMap.put (
				3,
				new CRBucket (
					3,
					CRSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					SectorSystemics.BASIC_MATERIALS,
					72.
				)
			);

			s_BucketMap.put (
				4,
				new CRBucket (4, CRSystemics.CREDIT_QUALITY_INVESTMENT_GRADE, SectorSystemics.CONSUMER, 55.)
			);

			s_BucketMap.put (
				5,
				new CRBucket (5, CRSystemics.CREDIT_QUALITY_INVESTMENT_GRADE, SectorSystemics.TMT, 48.)
			);

			s_BucketMap.put (
				6,
				new CRBucket (
					6,
					CRSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					SectorSystemics.LOCAL_SERVICES,
					41.
				)
			);

			s_BucketMap.put (
				7,
				new CRBucket (
					7,
					CRSystemics.CREDIT_QUALITY_HIGH_YIELD,
					SectorSystemics.BASIC_MATERIALS,
					166.
				)
			);

			s_BucketMap.put (
				8,
				new CRBucket (8, CRSystemics.CREDIT_QUALITY_HIGH_YIELD, SectorSystemics.FINANCIALS, 187.)
			);

			s_BucketMap.put (
				9,
				new CRBucket (
					9,
					CRSystemics.CREDIT_QUALITY_HIGH_YIELD,
					SectorSystemics.BASIC_MATERIALS,
					177.
				)
			);

			s_BucketMap.put (
				10,
				new CRBucket (10, CRSystemics.CREDIT_QUALITY_HIGH_YIELD, SectorSystemics.CONSUMER, 187.)
			);

			s_BucketMap.put (
				11,
				new CRBucket (11, CRSystemics.CREDIT_QUALITY_HIGH_YIELD, SectorSystemics.TMT, 129.)
			);

			s_BucketMap.put (
				12,
				new CRBucket (
					12,
					CRSystemics.CREDIT_QUALITY_HIGH_YIELD,
					SectorSystemics.LOCAL_SERVICES,
					136.
				)
			);
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		return SetUpCrossBucketCorrelation();
	}

	/**
	 * Retrieve the Standard ISDA Credit Tenor Set
	 * 
	 * @return The Standard ISDA Credit Tenor Set
	 */

	public static final Set<String> TenorSet()
	{
		Set<String> tenorSet = new HashSet<String>();

		tenorSet.add ("1Y");

		tenorSet.add ("2Y");

		tenorSet.add ("3Y");

		tenorSet.add ("5Y");

		tenorSet.add ("10Y");

		return tenorSet;
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

	public static final CRBucket Bucket (
		final int bucketNumber)
	{
		return ContainsBucket (bucketNumber) ? s_BucketMap.get (bucketNumber) : null;
	}

	/**
	 * Retrieve the Cross Bucket Correlation
	 * 
	 * @return The Cross Bucket Correlation
	 */

	public static final LabelCorrelation CrossBucketCorrelation()
	{
		return s_CrossBucketCorrelation;
	}

	/**
	 * Retrieve the Bucket Map
	 * 
	 * @return The Bucket Map
	 */

	public static final Map<Integer, CRBucket> BucketMap()
	{
		return s_BucketMap;
	}
}
