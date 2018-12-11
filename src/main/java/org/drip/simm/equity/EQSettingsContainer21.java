
package org.drip.simm.equity;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>EQSettingsContainer21</i> holds the ISDA SIMM 2.1 Equity Buckets and their Correlations. The References
 * are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm">SIMM</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/equity">Equity</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EQSettingsContainer21
{
	private static org.drip.measure.stochastic.LabelCorrelation s_CrossBucketCorrelation = null;

	private static final java.util.Map<java.lang.Integer, org.drip.simm.equity.EQBucket> s_BucketMap =
		new java.util.TreeMap<java.lang.Integer, org.drip.simm.equity.EQBucket>();

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
					{1.00, 0.16, 0.16, 0.17, 0.13, 0.15, 0.15, 0.15, 0.13, 0.11, 0.19, 0.19}, // #01
					{0.16, 1.00, 0.20, 0.20, 0.14, 0.16, 0.16, 0.16, 0.15, 0.13, 0.20, 0.20}, // #02
					{0.16, 0.20, 1.00, 0.22, 0.15, 0.19, 0.22, 0.19, 0.16, 0.15, 0.25, 0.25}, // #03
					{0.17, 0.20, 0.22, 1.00, 0.17, 0.21, 0.21, 0.21, 0.17, 0.15, 0.27, 0.27}, // #04
					{0.13, 0.14, 0.15, 0.17, 1.00, 0.25, 0.23, 0.26, 0.14, 0.17, 0.32, 0.32}, // #05
					{0.15, 0.16, 0.19, 0.21, 0.25, 1.00, 0.30, 0.31, 0.16, 0.21, 0.38, 0.38}, // #06
					{0.15, 0.16, 0.22, 0.21, 0.23, 0.30, 1.00, 0.29, 0.16, 0.21, 0.38, 0.38}, // #07
					{0.15, 0.16, 0.19, 0.21, 0.26, 0.31, 0.29, 1.00, 0.17, 0.21, 0.39, 0.39}, // #08
					{0.13, 0.15, 0.16, 0.17, 0.14, 0.16, 0.16, 0.17, 1.00, 0.13, 0.21, 0.21}, // #09
					{0.11, 0.13, 0.15, 0.15, 0.17, 0.21, 0.21, 0.21, 0.13, 1.00, 0.25, 0.25}, // #10
					{0.19, 0.20, 0.25, 0.27, 0.32, 0.38, 0.38, 0.39, 0.21, 0.25, 1.00, 0.51}, // #11
					{0.19, 0.20, 0.25, 0.27, 0.32, 0.38, 0.38, 0.39, 0.21, 0.25, 0.51, 1.00}, // #12
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
	 * Initialize the Equity Settings Container
	 * 
	 * @return TRUE - Equity Settings Container successfully initialized
	 */

	public static final boolean Init()
	{
		try
		{
			s_BucketMap.put (
				-1,
				new org.drip.simm.equity.EQBucket (
					-1,
					org.drip.simm.equity.MarketCapitalizationSystemics.ALL,
					org.drip.simm.equity.RegionSystemics.ALL,
					org.drip.simm.credit.SectorSystemics.ALL,
					34.,
					0.00,
					0.63
				)
			);

			s_BucketMap.put (
				1,
				new org.drip.simm.equity.EQBucket (
					1,
					org.drip.simm.equity.MarketCapitalizationSystemics.LARGE,
					org.drip.simm.equity.RegionSystemics.EMERGING_MARKETS,
					org.drip.simm.credit.SectorSystemics.CONSUMER_SERVICES,
					24.,
					0.14,
					0.28
				)
			);

			s_BucketMap.put (
				2,
				new org.drip.simm.equity.EQBucket (
					2,
					org.drip.simm.equity.MarketCapitalizationSystemics.LARGE,
					org.drip.simm.equity.RegionSystemics.EMERGING_MARKETS,
					org.drip.simm.credit.SectorSystemics.TELECOMMUNICATIONS_INDUSTRIALS,
					30.,
					0.20,
					0.28
				)
			);

			s_BucketMap.put (
				3,
				new org.drip.simm.equity.EQBucket (
					3,
					org.drip.simm.equity.MarketCapitalizationSystemics.LARGE,
					org.drip.simm.equity.RegionSystemics.EMERGING_MARKETS,
					org.drip.simm.credit.SectorSystemics.HEAVY_INDUSTRIALS,
					31.,
					0.25,
					0.28
				)
			);

			s_BucketMap.put (
				4,
				new org.drip.simm.equity.EQBucket (
					4,
					org.drip.simm.equity.MarketCapitalizationSystemics.LARGE,
					org.drip.simm.equity.RegionSystemics.EMERGING_MARKETS,
					org.drip.simm.credit.SectorSystemics.INVESTMENT,
					25.,
					0.23,
					0.28
				)
			);

			s_BucketMap.put (
				5,
				new org.drip.simm.equity.EQBucket (
					5,
					org.drip.simm.equity.MarketCapitalizationSystemics.LARGE,
					org.drip.simm.equity.RegionSystemics.DEVELOPED_MARKETS,
					org.drip.simm.credit.SectorSystemics.CONSUMER_SERVICES,
					21.,
					0.23,
					0.28
				)
			);

			s_BucketMap.put (
				6,
				new org.drip.simm.equity.EQBucket (
					6,
					org.drip.simm.equity.MarketCapitalizationSystemics.LARGE,
					org.drip.simm.equity.RegionSystemics.DEVELOPED_MARKETS,
					org.drip.simm.credit.SectorSystemics.TELECOMMUNICATIONS_INDUSTRIALS,
					22.,
					0.32,
					0.28
				)
			);

			s_BucketMap.put (
				7,
				new org.drip.simm.equity.EQBucket (
					7,
					org.drip.simm.equity.MarketCapitalizationSystemics.LARGE,
					org.drip.simm.equity.RegionSystemics.DEVELOPED_MARKETS,
					org.drip.simm.credit.SectorSystemics.HEAVY_INDUSTRIALS,
					27.,
					0.35,
					0.28
				)
			);

			s_BucketMap.put (
				8,
				new org.drip.simm.equity.EQBucket (
					8,
					org.drip.simm.equity.MarketCapitalizationSystemics.LARGE,
					org.drip.simm.equity.RegionSystemics.DEVELOPED_MARKETS,
					org.drip.simm.credit.SectorSystemics.INVESTMENT,
					24.,
					0.32,
					0.28
				)
			);

			s_BucketMap.put (
				9,
				new org.drip.simm.equity.EQBucket (
					9,
					org.drip.simm.equity.MarketCapitalizationSystemics.SMALL,
					org.drip.simm.equity.RegionSystemics.EMERGING_MARKETS,
					org.drip.simm.credit.SectorSystemics.ALL,
					33.,
					0.17,
					0.28
				)
			);

			s_BucketMap.put (
				10,
				new org.drip.simm.equity.EQBucket (
					10,
					org.drip.simm.equity.MarketCapitalizationSystemics.SMALL,
					org.drip.simm.equity.RegionSystemics.DEVELOPED_MARKETS,
					org.drip.simm.credit.SectorSystemics.ALL,
					34.,
					0.16,
					0.28
				)
			);

			s_BucketMap.put (
				11,
				new org.drip.simm.equity.EQBucket (
					11,
					org.drip.simm.equity.MarketCapitalizationSystemics.ALL,
					org.drip.simm.equity.RegionSystemics.ALL,
					org.drip.simm.credit.SectorSystemics.INDEX_FUND_ETF,
					17.,
					0.51,
					0.28
				)
			);

			s_BucketMap.put (
				12,
				new org.drip.simm.equity.EQBucket (
					12,
					org.drip.simm.equity.MarketCapitalizationSystemics.ALL,
					org.drip.simm.equity.RegionSystemics.ALL,
					org.drip.simm.credit.SectorSystemics.VOLATILITY_INDEX,
					17.,
					0.51,
					0.28
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

	public static final org.drip.simm.equity.EQBucket Bucket (
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

	public static final java.util.Map<java.lang.Integer, org.drip.simm.equity.EQBucket> BucketMap()
	{
		return s_BucketMap;
	}

	/**
	 * Retrieve the Cross Bucket Co-variance Matrix
	 * 
	 * @return The Cross Bucket Co-variance Matrix
	 */

	public static final org.drip.simm.foundation.RiskGroupPrincipalCovariance CrossBucketPrincipalCovariance()
	{
		return org.drip.simm.foundation.RiskGroupPrincipalCovariance.Standard (
			s_CrossBucketCorrelation.matrix(),
			1.
		);
	}
}
