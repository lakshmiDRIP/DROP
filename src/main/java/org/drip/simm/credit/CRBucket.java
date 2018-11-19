
package org.drip.simm.credit;

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
 * <i>CRBucket</i> holds the ISDA SIMM Credit Quality, Sector List, and Risk Weights for a given Credit
 * Qualifying/Non-Qualifying Issuer Exposure Bucket. The References are:
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
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit">Credit</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CRBucket
{
	private int _number = -1;
	private java.lang.String _quality = "";
	private java.lang.String[] _sectorArray = null;
	private double _riskWeight = java.lang.Double.NaN;

	/**
	 * CRBucket Constructor
	 * 
	 * @param number The Bucket Number
	 * @param quality The Credit Quality
	 * @param sectorArray The Sector Array
	 * @param riskWeight The Risk Weight
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CRBucket (
		final int number,
		final java.lang.String quality,
		final java.lang.String[] sectorArray,
		final double riskWeight)
		throws java.lang.Exception
	{
		if (null == (_quality = quality) || _quality.isEmpty() ||
			null == (_sectorArray = sectorArray) || 0 == _sectorArray.length ||
			!org.drip.quant.common.NumberUtil.IsValid (_riskWeight = riskWeight))
		{
			throw new java.lang.Exception ("CRBucket Constructor => Invalid Inputs");
		}

		_number = number;
	}

	/**
	 * Retrieve the SIMM 2.0 Bucket Number
	 * 
	 * @return The Bucket Number
	 */

	public int number()
	{
		return _number;
	}

	/**
	 * Retrieve the SIMM 2.0 Credit Quality
	 * 
	 * @return The Credit Quality
	 */

	public java.lang.String quality()
	{
		return _quality;
	}

	/**
	 * Retrieve the SIMM 2.0 Sector Array
	 * 
	 * @return The Sector Array
	 */

	public java.lang.String[] sectorArray()
	{
		return _sectorArray;
	}

	/**
	 * Retrieve the Risk Weight
	 * 
	 * @return The Risk Weight
	 */

	public double riskWeight()
	{
		return _riskWeight;
	}

	/**
	 * Retrieve the Credit Tenor Risk Weight Map
	 * 
	 * @param tenorSet The Tenor Set
	 * 
	 * @return The Credit Tenor Risk Weight Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> tenorWeightMap (
		final java.util.Set<java.lang.String> tenorSet)
	{
		if (null == tenorSet || 0 == tenorSet.size())
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> tenorWeightMap = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> tenorWeightEntry :
			tenorWeightMap.entrySet())
		{
			tenorWeightMap.put (
				tenorWeightEntry.getKey(),
				_riskWeight
			);
		}

		return tenorWeightMap;
	}
}
