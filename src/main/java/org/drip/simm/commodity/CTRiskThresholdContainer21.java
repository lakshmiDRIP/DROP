
package org.drip.simm.commodity;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.drip.simm.common.DeltaVegaThreshold;

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
 * <i>CTRiskThresholdContainer21</i> holds the ISDA SIMM 2.1 Commodity Risk Thresholds - the Commodity
 * 	Buckets and the Delta/Vega Limits defined for the Concentration Thresholds. The References are:
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
 * 		<li>Initialize the Commodity Risk Threshold Container</li>
 * 		<li>Retrieve the Commodity Risk Threshold Bucket Set</li>
 * 		<li>Indicate if the Bucket Number is available in the Commodity Risk Threshold Container</li>
 * 		<li>Retrieve the Threshold indicated by the Bucket Number</li>
 * 		<li>Retrieve the Delta Vega Threshold Map</li>
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

public class CTRiskThresholdContainer21
{
	private static final Map<Integer, DeltaVegaThreshold> s_DeltaVegaThresholdMap =
		new TreeMap<Integer, DeltaVegaThreshold>();

	/**
	 * Initialize the Commodity Risk Threshold Container
	 * 
	 * @return TRUE - The Commodity Risk Threshold Container successfully initialized
	 */

	public static final boolean Init()
	{
		try {
			s_DeltaVegaThresholdMap.put (1, new DeltaVegaThreshold (700., 250.));

			s_DeltaVegaThresholdMap.put (2, new DeltaVegaThreshold (3600., 1800.));

			s_DeltaVegaThresholdMap.put (3, new DeltaVegaThreshold (2700., 320.));

			s_DeltaVegaThresholdMap.put (4, new DeltaVegaThreshold (2700., 320.));

			s_DeltaVegaThresholdMap.put (5, new DeltaVegaThreshold (2700., 320.));

			s_DeltaVegaThresholdMap.put (6, new DeltaVegaThreshold (2600., 2200.));

			s_DeltaVegaThresholdMap.put (7, new DeltaVegaThreshold (2600., 2200.));

			s_DeltaVegaThresholdMap.put (8, new DeltaVegaThreshold (1900., 780.));

			s_DeltaVegaThresholdMap.put (9, new DeltaVegaThreshold (1900., 780.));

			s_DeltaVegaThresholdMap.put (10, new DeltaVegaThreshold (52., 99.));

			s_DeltaVegaThresholdMap.put (11, new DeltaVegaThreshold (2000., 420.));

			s_DeltaVegaThresholdMap.put (12, new DeltaVegaThreshold (3200., 650.));

			s_DeltaVegaThresholdMap.put (13, new DeltaVegaThreshold (100., 570.));

			s_DeltaVegaThresholdMap.put (14, new DeltaVegaThreshold (1100., 570.));

			s_DeltaVegaThresholdMap.put (15, new DeltaVegaThreshold (1100., 570.));

			s_DeltaVegaThresholdMap.put (16, new DeltaVegaThreshold (52., 99.));

			s_DeltaVegaThresholdMap.put (17, new DeltaVegaThreshold (5200., 330.));
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	
		return true;
	}

	/**
	 * Retrieve the Commodity Risk Threshold Bucket Set
	 * 
	 * @return The Commodity Risk Threshold Bucket Set
	 */

	public static final Set<Integer> BucketSet()
	{
		return s_DeltaVegaThresholdMap.keySet();
	}

	/**
	 * Indicate if the Bucket Number is available in the Commodity Risk Threshold Container
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return TRUE - The Bucket Number is available in the Commodity Risk Threshold Container
	 */

	public static final boolean ContainsBucket (
		final int bucketNumber)
	{
		return s_DeltaVegaThresholdMap.containsKey (bucketNumber);
	}

	/**
	 * Retrieve the Threshold indicated by the Bucket Number
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The Threshold indicated by the Bucket Number
	 */

	public static final DeltaVegaThreshold Threshold (
		final int bucketNumber)
	{
		return ContainsBucket (bucketNumber) ? s_DeltaVegaThresholdMap.get (bucketNumber) : null;
	}

	/**
	 * Retrieve the Delta Vega Threshold Map
	 * 
	 * @return The Delta Vega Threshold Map
	 */

	public static final Map<Integer, DeltaVegaThreshold> DeltaVegaThresholdMap()
	{
		return s_DeltaVegaThresholdMap;
	}
}
