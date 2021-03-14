
package org.drip.simm.credit;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/README.md">Credit Qualifying/Non-Qualifying Risk Factor Settings</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CRQSettingsContainer20
{
	private static org.drip.measure.stochastic.LabelCorrelation s_CrossBucketCorrelation = null;

	private static final java.util.Map<java.lang.Integer, org.drip.simm.credit.CRBucket> s_BucketMap = new
		java.util.TreeMap<java.lang.Integer, org.drip.simm.credit.CRBucket>();

	private static final boolean SetUpCrossBucketCorrelation()
	{
		java.util.List<java.lang.String> bucketList = new java.util.ArrayList<java.lang.String>();

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

		try
		{
			s_CrossBucketCorrelation = new org.drip.measure.stochastic.LabelCorrelation (
				bucketList,
				new double[][]
				{
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
		}
		catch (java.lang.Exception e)
		{
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
		try
		{
			s_BucketMap.put (
				-1,
				new org.drip.simm.credit.CRBucket (
					-1,
					org.drip.simm.credit.CRSystemics.CREDIT_QUALITY_UNSPECIFIED,
					org.drip.simm.credit.SectorSystemics.RESIDUAL,
					238.
				)
			);

			s_BucketMap.put (
				1,
				new org.drip.simm.credit.CRBucket (
					1,
					org.drip.simm.credit.CRSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					org.drip.simm.credit.SectorSystemics.SOVEREIGNS,
					85.
				)
			);

			s_BucketMap.put (
				2,
				new org.drip.simm.credit.CRBucket (
					2,
					org.drip.simm.credit.CRSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					org.drip.simm.credit.SectorSystemics.FINANCIALS,
					85.
				)
			);

			s_BucketMap.put (
				3,
				new org.drip.simm.credit.CRBucket (
					3,
					org.drip.simm.credit.CRSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					org.drip.simm.credit.SectorSystemics.BASIC_MATERIALS,
					73.
				)
			);

			s_BucketMap.put (
				4,
				new org.drip.simm.credit.CRBucket (
					4,
					org.drip.simm.credit.CRSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					org.drip.simm.credit.SectorSystemics.CONSUMER,
					49.
				)
			);

			s_BucketMap.put (
				5,
				new org.drip.simm.credit.CRBucket (
					5,
					org.drip.simm.credit.CRSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					org.drip.simm.credit.SectorSystemics.TMT,
					48.
				)
			);

			s_BucketMap.put (
				6,
				new org.drip.simm.credit.CRBucket (
					6,
					org.drip.simm.credit.CRSystemics.CREDIT_QUALITY_INVESTMENT_GRADE,
					org.drip.simm.credit.SectorSystemics.LOCAL_SERVICES,
					43.
				)
			);

			s_BucketMap.put (
				7,
				new org.drip.simm.credit.CRBucket (
					7,
					org.drip.simm.credit.CRSystemics.CREDIT_QUALITY_HIGH_YIELD,
					org.drip.simm.credit.SectorSystemics.BASIC_MATERIALS,
					161.
				)
			);

			s_BucketMap.put (
				8,
				new org.drip.simm.credit.CRBucket (
					8,
					org.drip.simm.credit.CRSystemics.CREDIT_QUALITY_HIGH_YIELD,
					org.drip.simm.credit.SectorSystemics.FINANCIALS,
					238.
				)
			);

			s_BucketMap.put (
				9,
				new org.drip.simm.credit.CRBucket (
					9,
					org.drip.simm.credit.CRSystemics.CREDIT_QUALITY_HIGH_YIELD,
					org.drip.simm.credit.SectorSystemics.BASIC_MATERIALS,
					151.
				)
			);

			s_BucketMap.put (
				10,
				new org.drip.simm.credit.CRBucket (
					10,
					org.drip.simm.credit.CRSystemics.CREDIT_QUALITY_HIGH_YIELD,
					org.drip.simm.credit.SectorSystemics.CONSUMER,
					210.
				)
			);

			s_BucketMap.put (
				11,
				new org.drip.simm.credit.CRBucket (
					11,
					org.drip.simm.credit.CRSystemics.CREDIT_QUALITY_HIGH_YIELD,
					org.drip.simm.credit.SectorSystemics.TMT,
					141.
				)
			);

			s_BucketMap.put (
				12,
				new org.drip.simm.credit.CRBucket (
					12,
					org.drip.simm.credit.CRSystemics.CREDIT_QUALITY_HIGH_YIELD,
					org.drip.simm.credit.SectorSystemics.LOCAL_SERVICES,
					102.
				)
			);
		}
		catch (java.lang.Exception e)
		{
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

	public static final java.util.Set<java.lang.String> TenorSet()
	{
		java.util.Set<java.lang.String> tenorSet = new java.util.HashSet<java.lang.String>();

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

	public static final java.util.Set<java.lang.Integer> BucketSet()
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

	public static final org.drip.simm.credit.CRBucket Bucket (
		final int bucketNumber)
	{
		return ContainsBucket (bucketNumber) ? s_BucketMap.get (bucketNumber) : null;
	}

	/**
	 * Retrieve the Cross Bucket Correlation
	 * 
	 * @return The Cross Bucket Correlation
	 */

	public static final org.drip.measure.stochastic.LabelCorrelation CrossBucketCorrelation()
	{
		return s_CrossBucketCorrelation;
	}

	/**
	 * Retrieve the Bucket Map
	 * 
	 * @return The Bucket Map
	 */

	public static final java.util.Map<java.lang.Integer, org.drip.simm.credit.CRBucket> BucketMap()
	{
		return s_BucketMap;
	}
}
