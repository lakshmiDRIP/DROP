
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
 * <i>SignificanceTestSetting</i> contains the Control Settings that determine the Success/Failure of the
 * specified Statistical Hypothesis p-Test.
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Anfuso, F., D. Karyampas, and A. Nawroth (2017): A Sound Basel III Compliant Framework for
 *  			Back-testing Credit Exposure Models
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2264620 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Diebold, F. X., T. A. Gunther, and A. S. Tay (1998): Evaluating Density Forecasts with
 *  			Applications to Financial Risk Management, International Economic Review 39 (4) 863-883
 *  	</li>
 *  	<li>
 *  		Kenyon, C., and R. Stamm (2012): Discounting, LIBOR, CVA, and Funding: Interest Rate and Credit
 *  			Pricing, Palgrave Macmillan
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018): Probability Integral Transform
 *  			https://en.wikipedia.org/wiki/Probability_integral_transform
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/hypothesis">Statistical Hypothesis Validation Test Suite</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SignificanceTestSetting
{

	/**
	 * Left Tail Significance Test
	 */

	public static final int LEFT_TAIL_CHECK = 0;

	/**
	 * Right Tail Significance Test
	 */

	public static final int RIGHT_TAIL_CHECK = 1;

	/**
	 * Double Tail Significance Test
	 */

	public static final int DOUBLE_TAIL_CHECK = 2;

	/**
	 * Fisher Significance Test Threshold
	 */

	public static final double FISHER_P_TEST_THRESHOLD = 0.05;

	private int _tailCheck = RIGHT_TAIL_CHECK;
	private double _threshold = java.lang.Double.NaN;

	/**
	 * Construct Right Tail Check Significance Test Setting using the Fisher Threshold
	 * 
	 * @return The Right Tail Check Significance Test Setting
	 */

	public static final SignificanceTestSetting FisherRightTail()
	{
		try
		{
			return new SignificanceTestSetting (
				FISHER_P_TEST_THRESHOLD,
				RIGHT_TAIL_CHECK
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct Left Tail Check Significance Test Setting using the Fisher Threshold
	 * 
	 * @return The Left Tail Check Significance Test Setting
	 */

	public static final SignificanceTestSetting FisherLeftTail()
	{
		try
		{
			return new SignificanceTestSetting (
				FISHER_P_TEST_THRESHOLD,
				LEFT_TAIL_CHECK
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct Double Tail Check Significance Test Setting using the Fisher Threshold
	 * 
	 * @return The Double Tail Check Significance Test Setting
	 */

	public static final SignificanceTestSetting FisherDoubleTail()
	{
		try
		{
			return new SignificanceTestSetting (
				2. * FISHER_P_TEST_THRESHOLD,
				DOUBLE_TAIL_CHECK
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * SignificanceTestSetting Constructor
	 * 
	 * @param threshold The Test Threshold
	 * @param tailCheck Test Tail Check Flag
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SignificanceTestSetting (
		final double threshold,
		final int tailCheck)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_threshold = threshold))
		{
			throw new java.lang.Exception ("SignificanceTestSetting Constructor => Invalid Inputs");
		}

		_tailCheck = tailCheck;
	}

	/**
	 * Retrieve the Test Tail Check
	 * 
	 * @return The Test Tail Check
	 */

	public int tailCheck()
	{
		return _tailCheck;
	}

	/**
	 * Retrieve the Test Tail Threshold
	 * 
	 * @return The Test Tail Threshold
	 */

	public double threshold()
	{
		return _threshold;
	}
}
