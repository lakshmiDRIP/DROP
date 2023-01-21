
package org.drip.simm.credit;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.drip.simm.common.DeltaVegaThreshold;

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
 * <i>CRThresholdContainer21</i> holds the ISDA SIMM 2.1 Credit Risk Thresholds - the Credit Risk Buckets and
 * the Delta/Vega Limits defined for the Concentration Thresholds. The References are:
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

public class CRThresholdContainer21
{
	private static final Map<Integer, DeltaVegaThreshold> s_CRQThresholdMap =
		new TreeMap<Integer, DeltaVegaThreshold>();

	private static final Map<Integer, DeltaVegaThreshold> s_CRNQThresholdMap =
		new TreeMap<Integer, DeltaVegaThreshold>();

	/**
	 * Initialize the Credit Risk Threshold Container
	 * 
	 * @return TRUE - The Credit Risk Threshold Container successfully initialized
	 */

	public static final boolean Init()
	{
		try
		{
			s_CRQThresholdMap.put (
				-1,
				new DeltaVegaThreshold (
					0.24,
					250.
				)
			);

			s_CRQThresholdMap.put (
				1,
				new DeltaVegaThreshold (
					1.00,
					250.
				)
			);

			s_CRQThresholdMap.put (
				2,
				new DeltaVegaThreshold (
					0.24,
					250.
				)
			);

			s_CRQThresholdMap.put (
				3,
				new DeltaVegaThreshold (
					0.24,
					250.
				)
			);

			s_CRQThresholdMap.put (
				4,
				new DeltaVegaThreshold (
					0.24,
					250.
				)
			);

			s_CRQThresholdMap.put (
				5,
				new DeltaVegaThreshold (
					0.24,
					250.
				)
			);

			s_CRQThresholdMap.put (
				6,
				new DeltaVegaThreshold (
					0.24,
					250.
				)
			);

			s_CRQThresholdMap.put (
				7,
				new DeltaVegaThreshold (
					1.00,
					250.
				)
			);

			s_CRQThresholdMap.put (
				8,
				new DeltaVegaThreshold (
					0.24,
					250.
				)
			);

			s_CRQThresholdMap.put (
				9,
				new DeltaVegaThreshold (
					0.24,
					250.
				)
			);

			s_CRQThresholdMap.put (
				10,
				new DeltaVegaThreshold (
					0.24,
					250.
				)
			);

			s_CRQThresholdMap.put (
				11,
				new DeltaVegaThreshold (
					0.24,
					250.
				)
			);

			s_CRQThresholdMap.put (
				12,
				new DeltaVegaThreshold (
					0.24,
					250.
				)
			);

			s_CRNQThresholdMap.put (
				-1,
				new DeltaVegaThreshold (
					0.5,
					54.
				)
			);

			s_CRNQThresholdMap.put (
				1,
				new DeltaVegaThreshold (
					9.5,
					54.
				)
			);

			s_CRNQThresholdMap.put (
				2,
				new DeltaVegaThreshold (
					0.5,
					54.
				)
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Credit Risk Qualifying Threshold Bucket Set
	 * 
	 * @return The Credit Risk Qualifying Threshold Bucket Set
	 */

	public static final Set<Integer> QualifyingBucketSet()
	{
		return s_CRQThresholdMap.keySet();
	}

	/**
	 * Retrieve the Credit Risk Non-Qualifying Threshold Bucket Set
	 * 
	 * @return The Credit Risk Non-Qualifying Threshold Bucket Set
	 */

	public static final Set<Integer> NonQualifyingBucketSet()
	{
		return s_CRNQThresholdMap.keySet();
	}

	/**
	 * Indicate if the Qualifying Bucket specified by the Number is available
	 * 
	 * @param bucketNumber The Qualifying Bucket Number
	 * 
	 * @return TRUE - The Qualifying Bucket specified by the Number is available
	 */

	public static final boolean ContainsQualifyingBucket (
		final int bucketNumber)
	{
		return s_CRQThresholdMap.containsKey (
			bucketNumber
		);
	}

	/**
	 * Indicate if the Non-Qualifying Bucket specified by the Number is available
	 * 
	 * @param bucketNumber The Non-Qualifying Bucket Number
	 * 
	 * @return TRUE - The Non-Qualifying Bucket specified by the Number is available
	 */

	public static final boolean ContainsNonQualifyingBucket (
		final int bucketNumber)
	{
		return s_CRNQThresholdMap.containsKey (
			bucketNumber
		);
	}

	/**
	 * Retrieve the Credit Risk Qualifying Threshold Instance identified by the Bucket Number
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The Credit Risk Qualifying Threshold Instance identified by the Bucket Number
	 */

	public static final DeltaVegaThreshold QualifyingThreshold (
		final int bucketNumber)
	{
		return ContainsQualifyingBucket (
			bucketNumber
		) ? s_CRQThresholdMap.get (
			bucketNumber
		) : null;
	}

	/**
	 * Retrieve the Credit Risk Non-Qualifying Threshold Instance identified by the Bucket Number
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The Credit Risk Non-Qualifying Threshold Instance identified by the Bucket Number
	 */

	public static final DeltaVegaThreshold NonQualifyingThreshold (
		final int bucketNumber)
	{
		return ContainsNonQualifyingBucket (
			bucketNumber
		) ? s_CRNQThresholdMap.get (
			bucketNumber
		) : null;
	}

	/**
	 * Retrieve the Credit Risk Qualifying Threshold Map
	 * 
	 * @return The Credit Risk Qualifying Threshold Map
	 */

	public static final Map<Integer, DeltaVegaThreshold> CreditRiskQualifyingThresholdMap()
	{
		return s_CRQThresholdMap;
	}

	/**
	 * Retrieve the Credit Risk Non-Qualifying Threshold Map
	 * 
	 * @return The Credit Risk Non-Qualifying Threshold Map
	 */

	public static final Map<Integer, DeltaVegaThreshold> CreditRiskNonQualifyingThresholdMap()
	{
		return s_CRNQThresholdMap;
	}
}
