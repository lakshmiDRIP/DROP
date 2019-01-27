
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
 * <i>ResponseDistribution</i> contains the Distribution of the Response Instances.
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/core">Core Model Validation Support Utilities</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ResponseDistribution
{
	private java.util.Map<java.lang.Double, java.lang.Double> _pValueResponseMap = null;
	private java.util.Map<java.lang.Double, java.lang.Double> _responsePValueMap = null;

	/**
	 * ResponseDistribution Constructor
	 * 
	 * @param responsePValueMap Response - p Value Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ResponseDistribution (
		final java.util.Map<java.lang.Double, java.lang.Double> responsePValueMap)
		throws java.lang.Exception
	{
		if (null == (_responsePValueMap = responsePValueMap) || 0 == _responsePValueMap.size())
		{
			throw new java.lang.Exception ("ResponseDistribution Constructor => Invalid Inputs");
		}

		_pValueResponseMap = new java.util.TreeMap<java.lang.Double, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.Double, java.lang.Double> responsePValueMapEntry :
			_responsePValueMap.entrySet())
		{
			_pValueResponseMap.put (
				responsePValueMapEntry.getValue(),
				responsePValueMapEntry.getKey()
			);
		}
	}

	/**
	 * Retrieve the p Value - Response Map
	 * 
	 * @return The p Value - Response Map
	 */

	public java.util.Map<java.lang.Double, java.lang.Double> pValueResponseMap()
	{
		return _pValueResponseMap;
	}

	/**
	 * Retrieve the Response - p Value Map
	 * 
	 * @return The Response - p Value Map
	 */

	public java.util.Map<java.lang.Double, java.lang.Double> responsePValueMap()
	{
		return _responsePValueMap;
	}

	/**
	 * Compute the p-Value corresponding to the Response Instance
	 * 
	 * @param response The Response Instance
	 * 
	 * @return The p-Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double pValue (
		final double response)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (response))
		{
			throw new java.lang.Exception ("ResponseDistribution::pValue => Invalid Inputs");
		}

		java.util.Set<java.lang.Double> responseKeySet = _responsePValueMap.keySet();

		double responseKeyCurrent = java.lang.Double.NaN;
		double responseKeyPrevious = java.lang.Double.NaN;

		for (double responseKey : responseKeySet)
		{
			if (response == responseKey)
			{
				return _responsePValueMap.get (response);
			}

			if (response < responseKey)
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (responseKeyPrevious))
				{
					return 0.;
				}

				responseKeyCurrent = responseKey;
				break;
			}

			responseKeyPrevious = responseKey;
		}

		return response >= responseKeyCurrent ? 1. :
			((response - responseKeyPrevious) * _responsePValueMap.get (responseKeyCurrent) +
			(responseKeyCurrent - response) * _responsePValueMap.get (responseKeyPrevious)) /
			(responseKeyCurrent - responseKeyPrevious);
	}

	/**
	 * Compute the corresponding to the Response Instance p-Value
	 * 
	 * @param pValue The p-Value
	 * 
	 * @return The Response Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double response (
		final double pValue)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (pValue))
		{
			throw new java.lang.Exception ("ResponseDistribution::response => Invalid Inputs");
		}

		java.util.Set<java.lang.Double> pValueKeySet = _pValueResponseMap.keySet();

		double pValueKeyCurrent = java.lang.Double.NaN;
		double pValueKeyPrevious = java.lang.Double.NaN;

		for (double pValueKey : pValueKeySet)
		{
			if (pValue == pValueKey)
			{
				return _pValueResponseMap.get (pValue);
			}

			if (pValue < pValueKey)
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (pValueKeyPrevious))
				{
					return _pValueResponseMap.get (pValueKey);
				}

				pValueKeyCurrent = pValueKey;
				break;
			}

			pValueKeyPrevious = pValueKey;
		}

		return pValue >= pValueKeyCurrent ? _pValueResponseMap.get (pValueKeyCurrent) :
			((pValue - pValueKeyPrevious) * _pValueResponseMap.get (pValueKeyCurrent) +
			(pValueKeyCurrent - pValue) * _pValueResponseMap.get (pValueKeyPrevious)) /
			(pValueKeyCurrent - pValueKeyPrevious);
	}
}
