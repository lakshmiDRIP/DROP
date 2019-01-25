
package org.drip.validation.core;

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
 * <i>ProbabilityIntegralTransform</i> holds the Functionality associated with a Probability Integral
 * Transform (PIT) - generating the Cumulative Distribution from the Sorted Predictor/Response Map.
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Anfuso, F. Karyampas, D., and A. Nawroth (2013): A Sound Basel III Compliant Framework for
 *  			Back-testing Credit Exposure Models
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2264620 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018): Probability Integral Transform
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2264620 <b>eSSRN</b>
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template">Template</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/probabilityintegraltransform">Probability Integral Transform</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ProbabilityIntegralTransform
{
	private int _totalResponseCount = 0;
	private java.util.Map<java.lang.Double, java.lang.Integer> _responseCount = null;

	/**
	 * Empty ProbabilityIntegralTransform Constructor
	 */

	public ProbabilityIntegralTransform()
	{
	}

	/**
	 * Retrieve the Response Counter Map
	 * 
	 * @return The Response Counter Map
	 */

	public java.util.Map<java.lang.Double, java.lang.Integer> responseCount()
	{
		return _responseCount;
	}

	/**
	 * Retrieve the Total Response Count
	 * 
	 * @return The Total Response Count
	 */

	public int totalResponseCount()
	{
		return _totalResponseCount;
	}
	/**
	 * Add the specified Response Entry
	 * 
	 * @param response The Response
	 * 
	 * @return TRUE - The Response Entry successfully added
	 */

	public boolean addResponse (
		final double response)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (response))
		{
			return false;
		}

		if (null == _responseCount)
		{
			_responseCount = new java.util.TreeMap<java.lang.Double, java.lang.Integer>();
		}

		_responseCount.put (
			response,
			_responseCount.containsKey (response) ? _responseCount.get (response) + 1 : 1
		);

		++_totalResponseCount;
		return true;
	}

	/**
	 * Extract the Empirical Response Cumulative Probability
	 * 
	 * @param response The Response
	 * 
	 * @return The Empirical Response Cumulative Probability
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double empiricalCumulative (
		final double response)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (response))
		{
			throw new java.lang.Exception
				("ProbabilityIntegralTransform::empiricalCumulative => Invalid Inputs");
		}

		double empiricalCumulative = 0;

		for (java.util.Map.Entry<java.lang.Double, java.lang.Integer> responseCountMap :
			_responseCount.entrySet())
		{
			double responseKey = responseCountMap.getKey();

			if (responseKey > response)
			{
				break;
			}

			empiricalCumulative += responseCountMap.getValue();
		}

		return empiricalCumulative / _totalResponseCount;
	}
}
