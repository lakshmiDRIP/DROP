
package org.drip.simm.parameters;

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
 *  		International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
 *  			https://www.isda.org/a/oFiDE/isda-simm-v2.pdf
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm">SIMM</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters">Parameters</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RiskMeasureSensitivitySettingsIR
{
	private org.drip.measure.stochastic.LabelCorrelation _crossBucketCorrelation = null;
	private java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsIR>
		_bucketSensitivitySettingsMap = null;

	/**
	 * Generate the Standard ISDA 2.0 DELTA Instance of RiskMeasureSensitivitySettingsIR
	 * 
	 * @param currencyList The Currency List
	 * 
	 * @return The Standard ISDA 2.0 DELTA Instance of RiskMeasureSensitivitySettingsIR
	 */

	public static final RiskMeasureSensitivitySettingsIR ISDA_DELTA_20 (
		final java.util.List<java.lang.String> currencyList)
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

		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsIR>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettingsIR>();

		for (int currencyListIndex = 0; currencyListIndex < currencyListSize; ++currencyListIndex)
		{
			java.lang.String currency = currencyList.get (currencyListIndex);

			bucketDeltaSettingsMap.put (
				currency,
				org.drip.simm.parameters.BucketSensitivitySettingsIR.ISDA_DELTA_20 (currency)
			);

			for (int currencyListInnerIndex = 0;
				currencyListInnerIndex < currencyListSize;
				++currencyListInnerIndex)
			{
				crossCurrencyCorrelation[currencyListIndex][currencyListInnerIndex] =
					currencyListIndex == currencyListInnerIndex ? 1. :
						org.drip.simm.rates.IRSystemics20.CROSS_CURRENCY_CORRELATION;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsIR (
				bucketDeltaSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					currencyList,
					crossCurrencyCorrelation
				)
			);
		}
		catch (java.lang.Exception e)
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
		final java.util.List<java.lang.String> currencyList)
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

		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsIR>
			bucketDeltaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettingsIR>();

		for (int currencyListIndex = 0; currencyListIndex < currencyListSize; ++currencyListIndex)
		{
			java.lang.String currency = currencyList.get (currencyListIndex);

			bucketDeltaSettingsMap.put (
				currency,
				org.drip.simm.parameters.BucketSensitivitySettingsIR.ISDA_DELTA_21 (currency)
			);

			for (int currencyListInnerIndex = 0;
				currencyListInnerIndex < currencyListSize;
				++currencyListInnerIndex)
			{
				crossCurrencyCorrelation[currencyListIndex][currencyListInnerIndex] =
					currencyListIndex == currencyListInnerIndex ? 1. :
						org.drip.simm.rates.IRSystemics21.CROSS_CURRENCY_CORRELATION;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsIR (
				bucketDeltaSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					currencyList,
					crossCurrencyCorrelation
				)
			);
		}
		catch (java.lang.Exception e)
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
		final java.util.List<java.lang.String> currencyList)
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

		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsIR>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettingsIR>();

		for (int currencyListIndex = 0; currencyListIndex < currencyListSize; ++currencyListIndex)
		{
			java.lang.String currency = currencyList.get (currencyListIndex);

			bucketVegaSettingsMap.put (
				currency,
				org.drip.simm.parameters.BucketVegaSettingsIR.ISDA_20 (currency)
			);

			for (int currencyListInnerIndex = 0;
				currencyListInnerIndex < currencyListSize;
				++currencyListInnerIndex)
			{
				crossCurrencyCorrelation[currencyListIndex][currencyListInnerIndex] =
					currencyListIndex == currencyListInnerIndex ? 1. :
						org.drip.simm.rates.IRSystemics20.CROSS_CURRENCY_CORRELATION;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsIR (
				bucketVegaSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					currencyList,
					crossCurrencyCorrelation
				)
			);
		}
		catch (java.lang.Exception e)
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
		final java.util.List<java.lang.String> currencyList)
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

		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsIR>
			bucketVegaSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettingsIR>();

		for (int currencyListIndex = 0; currencyListIndex < currencyListSize; ++currencyListIndex)
		{
			java.lang.String currency = currencyList.get (currencyListIndex);

			bucketVegaSettingsMap.put (
				currency,
				org.drip.simm.parameters.BucketVegaSettingsIR.ISDA_21 (currency)
			);

			for (int currencyListInnerIndex = 0;
				currencyListInnerIndex < currencyListSize;
				++currencyListInnerIndex)
			{
				crossCurrencyCorrelation[currencyListIndex][currencyListInnerIndex] =
					currencyListIndex == currencyListInnerIndex ? 1. :
						org.drip.simm.rates.IRSystemics21.CROSS_CURRENCY_CORRELATION;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsIR (
				bucketVegaSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					currencyList,
					crossCurrencyCorrelation
				)
			);
		}
		catch (java.lang.Exception e)
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
		final java.util.List<java.lang.String> currencyList)
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

		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsIR>
			bucketCurvatureSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettingsIR>();

		for (int currencyListIndex = 0; currencyListIndex < currencyListSize; ++currencyListIndex)
		{
			java.lang.String currency = currencyList.get (currencyListIndex);

			bucketCurvatureSettingsMap.put (
				currency,
				org.drip.simm.parameters.BucketCurvatureSettingsIR.ISDA_20 (currency)
			);

			for (int currencyListInnerIndex = 0;
				currencyListInnerIndex < currencyListSize;
				++currencyListInnerIndex)
			{
				crossCurrencyCorrelation[currencyListIndex][currencyListInnerIndex] =
					currencyListIndex == currencyListInnerIndex ? 1. :
						org.drip.simm.rates.IRSystemics20.CROSS_CURRENCY_CORRELATION;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsIR (
				bucketCurvatureSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					currencyList,
					crossCurrencyCorrelation
				)
			);
		}
		catch (java.lang.Exception e)
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
		final java.util.List<java.lang.String> currencyList)
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

		java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsIR>
			bucketCurvatureSettingsMap = new java.util.HashMap<java.lang.String,
				org.drip.simm.parameters.BucketSensitivitySettingsIR>();

		for (int currencyListIndex = 0; currencyListIndex < currencyListSize; ++currencyListIndex)
		{
			java.lang.String currency = currencyList.get (currencyListIndex);

			bucketCurvatureSettingsMap.put (
				currency,
				org.drip.simm.parameters.BucketCurvatureSettingsIR.ISDA_21 (currency)
			);

			for (int currencyListInnerIndex = 0;
				currencyListInnerIndex < currencyListSize;
				++currencyListInnerIndex)
			{
				crossCurrencyCorrelation[currencyListIndex][currencyListInnerIndex] =
					currencyListIndex == currencyListInnerIndex ? 1. :
						org.drip.simm.rates.IRSystemics21.CROSS_CURRENCY_CORRELATION;
			}
		}

		try
		{
			return new RiskMeasureSensitivitySettingsIR (
				bucketCurvatureSettingsMap,
				new org.drip.measure.stochastic.LabelCorrelation (
					currencyList,
					crossCurrencyCorrelation
				)
			);
		}
		catch (java.lang.Exception e)
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
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskMeasureSensitivitySettingsIR (
		final java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsIR>
			bucketSensitivitySettingsMap,
		final org.drip.measure.stochastic.LabelCorrelation crossBucketCorrelation)
		throws java.lang.Exception
	{
		if (null == (_bucketSensitivitySettingsMap = bucketSensitivitySettingsMap) ||
				0 == _bucketSensitivitySettingsMap.size() ||
			null == (_crossBucketCorrelation = crossBucketCorrelation))
		{
			throw new java.lang.Exception ("RiskMeasureSensitivitySettingsIR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Cross Bucket Correlation
	 * 
	 * @return The Cross Bucket Correlation
	 */

	public org.drip.measure.stochastic.LabelCorrelation crossBucketCorrelation()
	{
		return _crossBucketCorrelation;
	}

	/**
	 * Retrieve the IR Bucket Sensitivity Settings Map
	 * 
	 * @return The IR Bucket Sensitivity Settings Map
	 */

	public java.util.Map<java.lang.String, org.drip.simm.parameters.BucketSensitivitySettingsIR>
		bucketSensitivitySettingsMap()
	{
		return _bucketSensitivitySettingsMap;
	}
}
