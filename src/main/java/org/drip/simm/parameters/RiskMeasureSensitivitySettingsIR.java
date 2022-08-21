
package org.drip.simm.parameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drip.measure.stochastic.LabelCorrelation;
import org.drip.simm.rates.IRSystemics20;
import org.drip.simm.rates.IRSystemics21;
import org.drip.simm.rates.IRSystemics24;

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
 * <i>RiskMeasureSensitivitySettingsIR</i> holds the Settings that govern the Generation of the ISDA SIMM
 * Bucket Sensitivities across Individual IR Class Risk Measure Buckets. The References are:
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
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/README.md">ISDA SIMM Risk Factor Parameters</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RiskMeasureSensitivitySettingsIR
{
	private LabelCorrelation _crossBucketCorrelation = null;
	private Map<String, BucketSensitivitySettingsIR> _bucketSensitivitySettingsMap = null;

	/**
	 * Generate the Standard ISDA 2.0 DELTA Instance of RiskMeasureSensitivitySettingsIR
	 * 
	 * @param currencyList The Currency List
	 * 
	 * @return The Standard ISDA 2.0 DELTA Instance of RiskMeasureSensitivitySettingsIR
	 */

	public static final RiskMeasureSensitivitySettingsIR ISDA_DELTA_20 (
		final List<String> currencyList)
	{
		if (null == currencyList)
		{
			return null;
		}

		int currencyListSize = currencyList.size();

		if (0 == currencyListSize)
		{
			return null;
		}

		double[][] crossCurrencyCorrelation = new double[currencyListSize][currencyListSize];

		Map<String, BucketSensitivitySettingsIR> bucketDeltaSettingsMap =
			new HashMap<String, BucketSensitivitySettingsIR>();

		for (int currencyListIndex = 0;
			currencyListIndex < currencyListSize;
			++currencyListIndex
		)
		{
			String currency = currencyList.get (
				currencyListIndex
			);

			bucketDeltaSettingsMap.put (
				currency,
				BucketSensitivitySettingsIR.ISDA_DELTA_20 (
					currency
				)
			);

			for (int currencyListInnerIndex = 0;
				currencyListInnerIndex < currencyListSize;
				++currencyListInnerIndex)
			{
				crossCurrencyCorrelation[currencyListIndex][currencyListInnerIndex] =
					currencyListIndex == currencyListInnerIndex ? 1. :
						IRSystemics20.CROSS_CURRENCY_CORRELATION;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsIR (
				bucketDeltaSettingsMap,
				new LabelCorrelation (
					currencyList,
					crossCurrencyCorrelation
				)
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Standard ISDA 2.1 DELTA Instance of RiskMeasureSensitivitySettingsIR
	 * 
	 * @param currencyList The Currency List
	 * 
	 * @return The Standard ISDA 2.1 DELTA Instance of RiskMeasureSensitivitySettingsIR
	 */

	public static final RiskMeasureSensitivitySettingsIR ISDA_DELTA_21 (
		final List<String> currencyList)
	{
		if (null == currencyList)
		{
			return null;
		}

		int currencyListSize = currencyList.size();

		if (0 == currencyListSize)
		{
			return null;
		}

		double[][] crossCurrencyCorrelation = new double[currencyListSize][currencyListSize];

		Map<String, BucketSensitivitySettingsIR> bucketDeltaSettingsMap =
			new HashMap<String, BucketSensitivitySettingsIR>();

		for (int currencyListIndex = 0;
			currencyListIndex < currencyListSize;
			++currencyListIndex
		)
		{
			String currency = currencyList.get (
				currencyListIndex
			);

			bucketDeltaSettingsMap.put (
				currency,
				BucketSensitivitySettingsIR.ISDA_DELTA_21 (
					currency
				)
			);

			for (int currencyListInnerIndex = 0;
				currencyListInnerIndex < currencyListSize;
				++currencyListInnerIndex
			)
			{
				crossCurrencyCorrelation[currencyListIndex][currencyListInnerIndex] =
					currencyListIndex == currencyListInnerIndex ? 1. :
						IRSystemics21.CROSS_CURRENCY_CORRELATION;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsIR (
				bucketDeltaSettingsMap,
				new LabelCorrelation (
					currencyList,
					crossCurrencyCorrelation
				)
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Standard ISDA 2.4 DELTA Instance of RiskMeasureSensitivitySettingsIR
	 * 
	 * @param currencyList The Currency List
	 * 
	 * @return The Standard ISDA 2.4 DELTA Instance of RiskMeasureSensitivitySettingsIR
	 */

	public static final RiskMeasureSensitivitySettingsIR ISDA_DELTA_24 (
		final List<String> currencyList)
	{
		if (null == currencyList)
		{
			return null;
		}

		int currencyListSize = currencyList.size();

		if (0 == currencyListSize)
		{
			return null;
		}

		double[][] crossCurrencyCorrelation = new double[currencyListSize][currencyListSize];

		Map<String, BucketSensitivitySettingsIR> bucketDeltaSettingsMap =
			new HashMap<String, BucketSensitivitySettingsIR>();

		for (int currencyListIndex = 0;
			currencyListIndex < currencyListSize;
			++currencyListIndex
		)
		{
			String currency = currencyList.get (
				currencyListIndex
			);

			bucketDeltaSettingsMap.put (
				currency,
				BucketSensitivitySettingsIR.ISDA_DELTA_24 (
					currency
				)
			);

			for (int currencyListInnerIndex = 0;
				currencyListInnerIndex < currencyListSize;
				++currencyListInnerIndex
			)
			{
				crossCurrencyCorrelation[currencyListIndex][currencyListInnerIndex] =
					currencyListIndex == currencyListInnerIndex ? 1. :
					IRSystemics24.CROSS_CURRENCY_CORRELATION;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsIR (
				bucketDeltaSettingsMap,
				new LabelCorrelation (
					currencyList,
					crossCurrencyCorrelation
				)
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Standard ISDA 2.0 VEGA Instance of RiskMeasureSensitivitySettingsIR
	 * 
	 * @param currencyList The Currency List
	 * 
	 * @return The Standard ISDA 2.0 VEGA Instance of RiskMeasureSensitivitySettingsIR
	 */

	public static final RiskMeasureSensitivitySettingsIR ISDA_VEGA_20 (
		final List<String> currencyList)
	{
		if (null == currencyList)
		{
			return null;
		}

		int currencyListSize = currencyList.size();

		if (0 == currencyListSize)
		{
			return null;
		}

		double[][] crossCurrencyCorrelation = new double[currencyListSize][currencyListSize];

		Map<String, BucketSensitivitySettingsIR> bucketVegaSettingsMap =
			new HashMap<String, BucketSensitivitySettingsIR>();

		for (int currencyListIndex = 0;
			currencyListIndex < currencyListSize;
			++currencyListIndex
		)
		{
			String currency = currencyList.get (
				currencyListIndex
			);

			bucketVegaSettingsMap.put (
				currency,
				BucketVegaSettingsIR.ISDA_20 (
					currency
				)
			);

			for (int currencyListInnerIndex = 0;
				currencyListInnerIndex < currencyListSize;
				++currencyListInnerIndex
			)
			{
				crossCurrencyCorrelation[currencyListIndex][currencyListInnerIndex] =
					currencyListIndex == currencyListInnerIndex ? 1. :
					IRSystemics20.CROSS_CURRENCY_CORRELATION;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsIR (
				bucketVegaSettingsMap,
				new LabelCorrelation (
					currencyList,
					crossCurrencyCorrelation
				)
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Standard ISDA 2.1 VEGA Instance of RiskMeasureSensitivitySettingsIR
	 * 
	 * @param currencyList The Currency List
	 * 
	 * @return The Standard ISDA 2.1 VEGA Instance of RiskMeasureSensitivitySettingsIR
	 */

	public static final RiskMeasureSensitivitySettingsIR ISDA_VEGA_21 (
		final List<String> currencyList)
	{
		if (null == currencyList)
		{
			return null;
		}

		int currencyListSize = currencyList.size();

		if (0 == currencyListSize)
		{
			return null;
		}

		double[][] crossCurrencyCorrelation = new double[currencyListSize][currencyListSize];

		Map<String, BucketSensitivitySettingsIR> bucketVegaSettingsMap =
			new HashMap<String, BucketSensitivitySettingsIR>();

		for (int currencyListIndex = 0;
			currencyListIndex < currencyListSize;
			++currencyListIndex
		)
		{
			String currency = currencyList.get (
				currencyListIndex
			);

			bucketVegaSettingsMap.put (
				currency,
				BucketVegaSettingsIR.ISDA_21 (
					currency
				)
			);

			for (int currencyListInnerIndex = 0;
				currencyListInnerIndex < currencyListSize;
				++currencyListInnerIndex)
			{
				crossCurrencyCorrelation[currencyListIndex][currencyListInnerIndex] =
					currencyListIndex == currencyListInnerIndex ? 1. :
					IRSystemics21.CROSS_CURRENCY_CORRELATION;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsIR (
				bucketVegaSettingsMap,
				new LabelCorrelation (
					currencyList,
					crossCurrencyCorrelation
				)
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Standard ISDA 2.4 VEGA Instance of RiskMeasureSensitivitySettingsIR
	 * 
	 * @param currencyList The Currency List
	 * 
	 * @return The Standard ISDA 2.4 VEGA Instance of RiskMeasureSensitivitySettingsIR
	 */

	public static final RiskMeasureSensitivitySettingsIR ISDA_VEGA_24 (
		final List<String> currencyList)
	{
		if (null == currencyList)
		{
			return null;
		}

		int currencyListSize = currencyList.size();

		if (0 == currencyListSize)
		{
			return null;
		}

		double[][] crossCurrencyCorrelation = new double[currencyListSize][currencyListSize];

		Map<String, BucketSensitivitySettingsIR> bucketVegaSettingsMap =
			new HashMap<String, BucketSensitivitySettingsIR>();

		for (int currencyListIndex = 0;
			currencyListIndex < currencyListSize;
			++currencyListIndex
		)
		{
			String currency = currencyList.get (
				currencyListIndex
			);

			bucketVegaSettingsMap.put (
				currency,
				BucketVegaSettingsIR.ISDA_24 (
					currency
				)
			);

			for (int currencyListInnerIndex = 0;
				currencyListInnerIndex < currencyListSize;
				++currencyListInnerIndex)
			{
				crossCurrencyCorrelation[currencyListIndex][currencyListInnerIndex] =
					currencyListIndex == currencyListInnerIndex ? 1. :
					IRSystemics24.CROSS_CURRENCY_CORRELATION;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsIR (
				bucketVegaSettingsMap,
				new LabelCorrelation (
					currencyList,
					crossCurrencyCorrelation
				)
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Standard ISDA 2.0 CURVATURE Instance of RiskMeasureSensitivitySettingsIR
	 * 
	 * @param currencyList The Currency List
	 * 
	 * @return The Standard ISDA 2.0 CURVATURE Instance of RiskMeasureSensitivitySettingsIR
	 */

	public static final RiskMeasureSensitivitySettingsIR ISDA_CURVATURE_20 (
		final List<String> currencyList)
	{
		if (null == currencyList)
		{
			return null;
		}

		int currencyListSize = currencyList.size();

		if (0 == currencyListSize)
		{
			return null;
		}

		double[][] crossCurrencyCorrelation = new double[currencyListSize][currencyListSize];

		Map<String, BucketSensitivitySettingsIR> bucketCurvatureSettingsMap =
			new HashMap<String, BucketSensitivitySettingsIR>();

		for (int currencyListIndex = 0;
			currencyListIndex < currencyListSize;
			++currencyListIndex
		)
		{
			String currency = currencyList.get (
				currencyListIndex
			);

			bucketCurvatureSettingsMap.put (
				currency,
				BucketCurvatureSettingsIR.ISDA_20 (
					currency
				)
			);

			for (int currencyListInnerIndex = 0;
				currencyListInnerIndex < currencyListSize;
				++currencyListInnerIndex
			)
			{
				crossCurrencyCorrelation[currencyListIndex][currencyListInnerIndex] =
					currencyListIndex == currencyListInnerIndex ? 1. :
					IRSystemics20.CROSS_CURRENCY_CORRELATION;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsIR (
				bucketCurvatureSettingsMap,
				new LabelCorrelation (
					currencyList,
					crossCurrencyCorrelation
				)
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Standard ISDA 2.1 CURVATURE Instance of RiskMeasureSensitivitySettingsIR
	 * 
	 * @param currencyList The Currency List
	 * 
	 * @return The Standard ISDA 2.1 CURVATURE Instance of RiskMeasureSensitivitySettingsIR
	 */

	public static final RiskMeasureSensitivitySettingsIR ISDA_CURVATURE_21 (
		final List<String> currencyList)
	{
		if (null == currencyList)
		{
			return null;
		}

		int currencyListSize = currencyList.size();

		if (0 == currencyListSize)
		{
			return null;
		}

		double[][] crossCurrencyCorrelation = new double[currencyListSize][currencyListSize];

		Map<String, BucketSensitivitySettingsIR> bucketCurvatureSettingsMap =
			new HashMap<String, BucketSensitivitySettingsIR>();

		for (int currencyListIndex = 0;
			currencyListIndex < currencyListSize;
			++currencyListIndex
		)
		{
			String currency = currencyList.get (
				currencyListIndex
			);

			bucketCurvatureSettingsMap.put (
				currency,
				BucketCurvatureSettingsIR.ISDA_21 (
					currency
				)
			);

			for (int currencyListInnerIndex = 0;
				currencyListInnerIndex < currencyListSize;
				++currencyListInnerIndex
			)
			{
				crossCurrencyCorrelation[currencyListIndex][currencyListInnerIndex] =
					currencyListIndex == currencyListInnerIndex ? 1. :
					IRSystemics21.CROSS_CURRENCY_CORRELATION;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsIR (
				bucketCurvatureSettingsMap,
				new LabelCorrelation (
					currencyList,
					crossCurrencyCorrelation
				)
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Standard ISDA 2.4 CURVATURE Instance of RiskMeasureSensitivitySettingsIR
	 * 
	 * @param currencyList The Currency List
	 * 
	 * @return The Standard ISDA 2.4 CURVATURE Instance of RiskMeasureSensitivitySettingsIR
	 */

	public static final RiskMeasureSensitivitySettingsIR ISDA_CURVATURE_24 (
		final List<String> currencyList)
	{
		if (null == currencyList)
		{
			return null;
		}

		int currencyListSize = currencyList.size();

		if (0 == currencyListSize)
		{
			return null;
		}

		double[][] crossCurrencyCorrelation = new double[currencyListSize][currencyListSize];

		Map<String, BucketSensitivitySettingsIR> bucketCurvatureSettingsMap =
			new HashMap<String, BucketSensitivitySettingsIR>();

		for (int currencyListIndex = 0;
			currencyListIndex < currencyListSize;
			++currencyListIndex
		)
		{
			String currency = currencyList.get (
				currencyListIndex
			);

			bucketCurvatureSettingsMap.put (
				currency,
				BucketCurvatureSettingsIR.ISDA_24 (
					currency
				)
			);

			for (int currencyListInnerIndex = 0;
				currencyListInnerIndex < currencyListSize;
				++currencyListInnerIndex
			)
			{
				crossCurrencyCorrelation[currencyListIndex][currencyListInnerIndex] =
					currencyListIndex == currencyListInnerIndex ? 1. :
					IRSystemics24.CROSS_CURRENCY_CORRELATION;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsIR (
				bucketCurvatureSettingsMap,
				new LabelCorrelation (
					currencyList,
					crossCurrencyCorrelation
				)
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RiskMeasureSensitivitySettingsIR Constructor
	 * 
	 * @param bucketSensitivitySettingsMap The IR Bucket Sensitivity Settings Map
	 * @param crossBucketCorrelation The Cross Bucket Correlation
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RiskMeasureSensitivitySettingsIR (
		final Map<String, BucketSensitivitySettingsIR> bucketSensitivitySettingsMap,
		final LabelCorrelation crossBucketCorrelation)
		throws Exception
	{
		if (null == (_bucketSensitivitySettingsMap = bucketSensitivitySettingsMap) ||
				0 == _bucketSensitivitySettingsMap.size() ||
			null == (_crossBucketCorrelation = crossBucketCorrelation)
		)
		{
			throw new Exception (
				"RiskMeasureSensitivitySettingsIR Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Cross Bucket Correlation
	 * 
	 * @return The Cross Bucket Correlation
	 */

	public LabelCorrelation crossBucketCorrelation()
	{
		return _crossBucketCorrelation;
	}

	/**
	 * Retrieve the IR Bucket Sensitivity Settings Map
	 * 
	 * @return The IR Bucket Sensitivity Settings Map
	 */

	public Map<String, BucketSensitivitySettingsIR> bucketSensitivitySettingsMap()
	{
		return _bucketSensitivitySettingsMap;
	}
}
