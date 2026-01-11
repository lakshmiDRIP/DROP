
package org.drip.simm.credit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.drip.measure.state.LabelledRdCorrelation;

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
 * <i>CRQSettingsContainer20</i> holds the ISDA SIMM 2.0 Credit Qualifying Buckets. The References are:
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

public class CRQSettingsContainer20
{
	private static LabelledRdCorrelation s_CrossBucketCorrelation = null;

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
			s_CrossBucketCorrelation = new LabelledRdCorrelation (
				bucketList,
				new double[][] {
					{1.00, 0.42, 0.39, 0.39, 0.40, 0.38, 0.39, 0.34, 0.37, 0.39, 0.37, 0.31},
					{0.42, 1.00, 0.44, 0.45, 0.47, 0.45, 0.33, 0.40, 0.41, 0.44, 0.43, 0.47},
					{0.39, 0.44, 1.00, 0.43, 0.45, 0.43, 0.32, 0.35, 0.41, 0.42, 0.40, 0.36},
					{0.39, 0.45, 0.43, 1.00, 0.47, 0.44, 0.30, 0.34, 0.39, 0.43, 0.39, 0.36},
					{0.40, 0.47, 0.45, 0.47, 1.00, 0.47, 0.31, 0.35, 0.40, 0.44, 0.42, 0.37},
					{0.38, 0.45, 0.43, 0.44, 0.47, 1.00, 0.30, 0.34, 0.38, 0.40, 0.39, 0.38},
					{0.39, 0.33, 0.32, 0.30, 0.31, 0.30, 1.00, 0.28, 0.31, 0.31, 0.30, 0.26},
					{0.34, 0.40, 0.35, 0.34, 0.35, 0.34, 0.28, 1.00, 0.34, 0.35, 0.33, 0.30},
					{0.37, 0.41, 0.41, 0.39, 0.40, 0.38, 0.31, 0.34, 1.00, 0.40, 0.37, 0.32},
					{0.39, 0.44, 0.42, 0.43, 0.44, 0.40, 0.31, 0.35, 0.40, 1.00, 0.40, 0.35},
					{0.37, 0.43, 0.40, 0.39, 0.42, 0.39, 0.30, 0.33, 0.37, 0.40, 1.00, 0.34},
					{0.31, 0.37, 0.36, 0.36, 0.37, 0.38, 0.26, 0.30, 0.32, 0.35, 0.34, 1.00}
				}
			);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Initialize the Credit Qualifying Settings
	 * 
	 * @return TRUE - The Credit Qualifying Settings successfully initialized
	 */

	public static final boolean Init()
	{
		try {
			s_BucketMap.put (
				-1,
				new CRBucket (-1, CRSystemics.CREDIT_QUALITY_UNSPECIFIED, SectorSystemics.RESIDUAL, 238.)
			);

			s_BucketMap.put (
				1,
				new CRBucket (
					1,
					CRSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					SectorSystemics.SOVEREIGNS,
					85.
				)
			);

			s_BucketMap.put (
				2,
				new CRBucket (
					2,
					CRSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					SectorSystemics.FINANCIALS,
					85.
				)
			);

			s_BucketMap.put (
				3,
				new CRBucket (
					3,
					CRSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					SectorSystemics.BASIC_MATERIALS,
					73.
				)
			);

			s_BucketMap.put (
				4,
				new CRBucket (4, CRSystemics.CREDIT_QUALITY_INVESTMENT_GRADE, SectorSystemics.CONSUMER, 49.)
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
					43.
				)
			);

			s_BucketMap.put (
				7,
				new CRBucket (
					7,
					CRSystemics.CREDIT_QUALITY_HIGH_YIELD,
					SectorSystemics.BASIC_MATERIALS,
					161.
				)
			);

			s_BucketMap.put (
				8,
				new CRBucket (8, CRSystemics.CREDIT_QUALITY_HIGH_YIELD, SectorSystemics.FINANCIALS, 238.)
			);

			s_BucketMap.put (
				9,
				new CRBucket (
					9,
					CRSystemics.CREDIT_QUALITY_HIGH_YIELD,
					SectorSystemics.BASIC_MATERIALS,
					151.
				)
			);

			s_BucketMap.put (
				10,
				new CRBucket (10, CRSystemics.CREDIT_QUALITY_HIGH_YIELD, SectorSystemics.CONSUMER, 210.)
			);

			s_BucketMap.put (
				11,
				new CRBucket (11, CRSystemics.CREDIT_QUALITY_HIGH_YIELD, SectorSystemics.TMT, 141.)
			);

			s_BucketMap.put (
				12,
				new CRBucket (
					12,
					CRSystemics.CREDIT_QUALITY_HIGH_YIELD,
					SectorSystemics.LOCAL_SERVICES,
					102.
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

	public static final LabelledRdCorrelation CrossBucketCorrelation()
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
