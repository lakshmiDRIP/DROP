
package org.drip.validation.hypothesis;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>Test</i> implements a Single Statistical Hypothesis Test. It holds the PIT Distribution CDF of the
 * Test-Statistic Response Instances.
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Bhattacharya, B., and D. Habtzghi (2002): Median of the p-value under the Alternate Hypothesis
 *  			American Statistician 56 (3) 202-206
 *  	</li>
 *  	<li>
 *  		Head, M. L., L. Holman, R, Lanfear, A. T. Kahn, and M. D. Jennions (2015): The Extent and
 *  			Consequences of p-Hacking in Science PLoS Biology 13 (3) e1002106
 *  	</li>
 *  	<li>
 *  		Wasserstein, R. L., and N. A. Lazar (2016): The ASA’s Statement on p-values: Context, Process,
 *  			and Purpose American Statistician 70 (2) 129-133
 *  	</li>
 *  	<li>
 *  		Wetzels, R., D. Matzke, M. D. Lee, J. N. Rouder, G, J, Iverson, and E. J. Wagenmakers (2011):
 *  		Statistical Evidence in Experimental Psychology: An Empirical Comparison using 855 t-Tests
 *  		Perspectives in Psychological Science 6 (3) 291-298
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): p-value https://en.wikipedia.org/wiki/P-value
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation">Model Validation Suite</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/core">Core Model Validation Support Utilities</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Test
{
	private java.util.Map<java.lang.Double, java.lang.Double> _pValueTestStatisticMap = null;
	private java.util.Map<java.lang.Double, java.lang.Double> _testStatisticPValueMap = null;

	/**
	 * Test Constructor
	 * 
	 * @param testStatisticPValueMap Test Statistic - p Value Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Test (
		final java.util.Map<java.lang.Double, java.lang.Double> testStatisticPValueMap)
		throws java.lang.Exception
	{
		if (null == (_testStatisticPValueMap = testStatisticPValueMap) ||
			0 == _testStatisticPValueMap.size())
		{
			throw new java.lang.Exception ("Test Constructor => Invalid Inputs");
		}

		_pValueTestStatisticMap = new java.util.TreeMap<java.lang.Double, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.Double, java.lang.Double> testStatisticPValueMapEntry :
			_testStatisticPValueMap.entrySet())
		{
			_pValueTestStatisticMap.put (
				testStatisticPValueMapEntry.getValue(),
				testStatisticPValueMapEntry.getKey()
			);
		}
	}

	/**
	 * Retrieve the p Value - Test Statistic Map
	 * 
	 * @return The p Value - Test Statistic Map
	 */

	public java.util.Map<java.lang.Double, java.lang.Double> pValueResponseMap()
	{
		return _pValueTestStatisticMap;
	}

	/**
	 * Retrieve the Test Statistic - p Value Map
	 * 
	 * @return The Test Statistic - p Value Map
	 */

	public java.util.Map<java.lang.Double, java.lang.Double> testStatisticPValueMap()
	{
		return _testStatisticPValueMap;
	}

	/**
	 * Compute the p-Value corresponding to the Test Statistic Instance
	 * 
	 * @param testStatistic The Test Statistic Instance
	 * 
	 * @return The p-Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double pValue (
		final double testStatistic)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (testStatistic))
		{
			throw new java.lang.Exception ("Test::pValue => Invalid Inputs");
		}

		java.util.Set<java.lang.Double> testStatisticKeySet = _testStatisticPValueMap.keySet();

		double testStatisticKeyCurrent = java.lang.Double.NaN;
		double testStatisticKeyPrevious = java.lang.Double.NaN;

		for (double testStatisticKey : testStatisticKeySet)
		{
			if (testStatistic == testStatisticKey)
			{
				return _testStatisticPValueMap.get (testStatistic);
			}

			if (testStatistic < testStatisticKey)
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (testStatisticKeyPrevious))
				{
					return 0.;
				}

				testStatisticKeyCurrent = testStatisticKey;
				break;
			}

			testStatisticKeyPrevious = testStatisticKey;
		}

		return !org.drip.quant.common.NumberUtil.IsValid (testStatisticKeyCurrent) ||
			testStatistic >= testStatisticKeyCurrent ? 1. :
			((testStatistic - testStatisticKeyPrevious) * _testStatisticPValueMap.get
				(testStatisticKeyCurrent) +
			(testStatisticKeyCurrent - testStatistic) * _testStatisticPValueMap.get
				(testStatisticKeyPrevious)) /
			(testStatisticKeyCurrent - testStatisticKeyPrevious);
	}

	/**
	 * Compute the Test Statistic Instance corresponding to the p-Value
	 * 
	 * @param pValue The p-Value
	 * 
	 * @return The Response Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double testStatistic (
		final double pValue)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (pValue))
		{
			throw new java.lang.Exception ("Test::testStatistic => Invalid Inputs");
		}

		java.util.Set<java.lang.Double> pValueKeySet = _pValueTestStatisticMap.keySet();

		double pValueKeyCurrent = java.lang.Double.NaN;
		double pValueKeyPrevious = java.lang.Double.NaN;

		for (double pValueKey : pValueKeySet)
		{
			if (pValue == pValueKey)
			{
				return _pValueTestStatisticMap.get (pValue);
			}

			if (pValue < pValueKey)
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (pValueKeyPrevious))
				{
					return _pValueTestStatisticMap.get (pValueKey);
				}

				pValueKeyCurrent = pValueKey;
				break;
			}

			pValueKeyPrevious = pValueKey;
		}

		return pValue >= pValueKeyCurrent ? _pValueTestStatisticMap.get (pValueKeyCurrent) :
			((pValue - pValueKeyPrevious) * _pValueTestStatisticMap.get (pValueKeyCurrent) +
			(pValueKeyCurrent - pValue) * _pValueTestStatisticMap.get (pValueKeyPrevious)) /
			(pValueKeyCurrent - pValueKeyPrevious);
	}

	/**
	 * Run the Significance Test for the Realized Test Statistic
	 * 
	 * @param testStatistic The Realized Test Statistic
	 * @param pTestSetting The P-Test Setting
	 * 
	 * @return The Significance Test Result for the Realized Test Statistic
	 */

	public org.drip.validation.hypothesis.SignificanceTest significanceTest (
		final double testStatistic,
		final org.drip.validation.hypothesis.SignificanceTestSetting pTestSetting)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (testStatistic) || null == pTestSetting)
		{
			return null;
		}

		double pValue = java.lang.Double.NaN;

		try
		{
			pValue = pValue (testStatistic);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		int tailCheck = pTestSetting.tailCheck();

		double threshold = pTestSetting.threshold();

		if (org.drip.validation.hypothesis.SignificanceTestSetting.LEFT_TAIL_CHECK == tailCheck)
		{
			try
			{
				return new SignificanceTest (
					testStatistic,
					1. - pValue,
					pValue,
					pValue > threshold
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		if (org.drip.validation.hypothesis.SignificanceTestSetting.RIGHT_TAIL_CHECK == tailCheck)
		{
			try
			{
				return new SignificanceTest (
					testStatistic,
					1. - pValue,
					pValue,
					1. - pValue > threshold
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		try
		{
			return new SignificanceTest (
				testStatistic,
				1. - pValue,
				pValue,
				2. * java.lang.Math.min (
					pValue,
					1. - pValue
				) > threshold
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
