
package org.drip.simm.equity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.drip.measure.stochastic.LabelCorrelation;
import org.drip.simm.credit.SectorSystemics;
import org.drip.simm.foundation.RiskGroupPrincipalCovariance;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
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
 * <i>EQSettingsContainer24</i> holds the ISDA SIMM 2.4 Equity Buckets and their Correlations. The References
 * 	are:
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
 *  		International Swaps and Derivatives Association (2021): SIMM v2.4 Methodology
 *  			https://www.isda.org/a/CeggE/ISDA-SIMM-v2.4-PUBLIC.pdf
 *  	</li>
 *  </ul>
 *
 * 	It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Initialize the Equity Settings Container</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/equity/README.md">Equity Risk Factor Calibration Settings</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EQSettingsContainer24
{
	private static LabelCorrelation s_CrossBucketCorrelation = null;

	private static final Map<Integer, EQBucket> s_BucketMap = new TreeMap<Integer, EQBucket>();

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
					{1.00, 0.20, 0.20, 0.21, 0.15, 0.19, 0.19, 0.19, 0.18, 0.14, 0.24, 0.24}, // #01
					{0.20, 1.00, 0.25, 0.24, 0.16, 0.21, 0.22, 0.21, 0.21, 0.16, 0.27, 0.27}, // #02
					{0.21, 0.25, 1.00, 0.26, 0.17, 0.22, 0.24, 0.22, 0.23, 0.17, 0.28, 0.28}, // #03
					{0.21, 0.24, 0.26, 1.00, 0.18, 0.24, 0.25, 0.25, 0.23, 0.19, 0.31, 0.31}, // #04
					{0.15, 0.16, 0.17, 0.18, 1.00, 0.27, 0.27, 0.27, 0.15, 0.20, 0.32, 0.32}, // #05
					{0.19, 0.21, 0.22, 0.24, 0.27, 1.00, 0.36, 0.35, 0.20, 0.25, 0.42, 0.42}, // #06
					{0.19, 0.22, 0.24, 0.25, 0.27, 0.36, 1.00, 0.34, 0.20, 0.26, 0.43, 0.43}, // #07
					{0.19, 0.21, 0.22, 0.25, 0.27, 0.35, 0.34, 1.00, 0.20, 0.25, 0.41, 0.41}, // #08
					{0.18, 0.21, 0.23, 0.23, 0.15, 0.20, 0.20, 0.20, 1.00, 0.16, 0.26, 0.26}, // #09
					{0.14, 0.16, 0.17, 0.19, 0.20, 0.25, 0.26, 0.25, 0.16, 1.00, 0.29, 0.29}, // #10
					{0.24, 0.27, 0.28, 0.31, 0.32, 0.42, 0.43, 0.41, 0.26, 0.29, 1.00, 0.54}, // #11
					{0.24, 0.27, 0.28, 0.31, 0.32, 0.42, 0.43, 0.41, 0.26, 0.29, 0.54, 1.00}, // #12
				}
			);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Initialize the Equity Settings Container
	 * 
	 * @return TRUE - Equity Settings Container successfully initialized
	 */

	public static final boolean Init()
	{
		try {
			s_BucketMap.put (
				-1,
				new EQBucket (
					-1,
					MarketCapitalizationSystemics.ALL,
					RegionSystemics.ALL,
					SectorSystemics.ALL,
					33.,
					0.00,
					0.50
				)
			);

			s_BucketMap.put (
				1,
				new EQBucket (
					1,
					MarketCapitalizationSystemics.LARGE,
					RegionSystemics.EMERGING_MARKETS,
					SectorSystemics.CONSUMER_SERVICES,
					25.,
					0.18,
					0.50
				)
			);

			s_BucketMap.put (
				2,
				new EQBucket (
					2,
					MarketCapitalizationSystemics.LARGE,
					RegionSystemics.EMERGING_MARKETS,
					SectorSystemics.TELECOMMUNICATIONS_INDUSTRIALS,
					28.,
					0.23,
					0.50
				)
			);

			s_BucketMap.put (
				3,
				new EQBucket (
					3,
					MarketCapitalizationSystemics.LARGE,
					RegionSystemics.EMERGING_MARKETS,
					SectorSystemics.HEAVY_INDUSTRIALS,
					30.,
					0.28,
					0.50
				)
			);

			s_BucketMap.put (
				4,
				new EQBucket (
					4,
					MarketCapitalizationSystemics.LARGE,
					RegionSystemics.EMERGING_MARKETS,
					SectorSystemics.INVESTMENT,
					28.,
					0.27,
					0.50
				)
			);

			s_BucketMap.put (
				5,
				new EQBucket (
					5,
					MarketCapitalizationSystemics.LARGE,
					RegionSystemics.DEVELOPED_MARKETS,
					SectorSystemics.CONSUMER_SERVICES,
					23.,
					0.23,
					0.50
				)
			);

			s_BucketMap.put (
				6,
				new EQBucket (
					6,
					MarketCapitalizationSystemics.LARGE,
					RegionSystemics.DEVELOPED_MARKETS,
					SectorSystemics.TELECOMMUNICATIONS_INDUSTRIALS,
					24.,
					0.36,
					0.50
				)
			);

			s_BucketMap.put (
				7,
				new EQBucket (
					7,
					MarketCapitalizationSystemics.LARGE,
					RegionSystemics.DEVELOPED_MARKETS,
					SectorSystemics.HEAVY_INDUSTRIALS,
					29.,
					0.38,
					0.50
				)
			);

			s_BucketMap.put (
				8,
				new EQBucket (
					8,
					MarketCapitalizationSystemics.LARGE,
					RegionSystemics.DEVELOPED_MARKETS,
					SectorSystemics.INVESTMENT,
					27.,
					0.35,
					0.50
				)
			);

			s_BucketMap.put (
				9,
				new EQBucket (
					9,
					MarketCapitalizationSystemics.SMALL,
					RegionSystemics.EMERGING_MARKETS,
					SectorSystemics.ALL,
					31.,
					0.21,
					0.50
				)
			);

			s_BucketMap.put (
				10,
				new EQBucket (
					10,
					MarketCapitalizationSystemics.SMALL,
					RegionSystemics.DEVELOPED_MARKETS,
					SectorSystemics.ALL,
					33.,
					0.20,
					0.50
				)
			);

			s_BucketMap.put (
				11,
				new EQBucket (
					11,
					MarketCapitalizationSystemics.ALL,
					RegionSystemics.ALL,
					SectorSystemics.INDEX_FUND_ETF,
					19.,
					0.54,
					0.50
				)
			);

			s_BucketMap.put (
				12,
				new EQBucket (
					12,
					MarketCapitalizationSystemics.ALL,
					RegionSystemics.ALL,
					SectorSystemics.VOLATILITY_INDEX,
					19.,
					0.54,
					0.98
				)
			);
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

	public static final EQBucket Bucket (
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

	public static final Map<Integer, EQBucket> BucketMap()
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
