
package org.drip.simm.product;

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
 * <i>RiskFactorTenorSensitivity</i> holds the ISDA SIMM 2.0 Risk Factor Tenor Bucket Sensitivities. The
 * References are:
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
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product">Product</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RiskFactorTenorSensitivity
{
	private java.util.Map<java.lang.String, java.lang.Double> _sensitivityMap = null;

	/**
	 * RiskFactorTenorSensitivity Constructor
	 * 
	 * @param sensitivityMap The Tenor Sensitivity Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskFactorTenorSensitivity (
		java.util.Map<java.lang.String, java.lang.Double> sensitivityMap)
		throws java.lang.Exception
	{
		if (null == (_sensitivityMap = sensitivityMap) || 0 == _sensitivityMap.size())
		{
			throw new java.lang.Exception ("RiskFactorTenorSensitivity Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Set of Tenors
	 * 
	 * @return The Set of Tenors
	 */

	public java.util.Set<java.lang.String> tenorSet()
	{
		return _sensitivityMap.keySet();
	}

	/**
	 * Add the Tenor Sensitivity
	 * 
	 * @param tenor The Tenor
	 * @param sensitivity Sensitivity for the given Tenor
	 * 
	 * @return TRUE - The Tenor Sensitivity successfully set
	 */

	public boolean addTenorDelta (
		final java.lang.String tenor,
		final double sensitivity)
	{
		if (null == tenor || !org.drip.quant.common.NumberUtil.IsValid (sensitivity))
		{
			return false;
		}

		_sensitivityMap.put (
			tenor,
			sensitivity
		);

		return true;
	}

	/**
	 * Indicate of the Sensitivity exists for the specified Tenor
	 * 
	 * @param tenor The Tenor
	 * 
	 * @return TRUE - Sensitivity exists for the specified Tenor
	 */

	public boolean tenorExists (
		final java.lang.String tenor)
	{
		return null != tenor && _sensitivityMap.containsKey (tenor);
	}

	/**
	 * Retrieve the Sensitivity for the Bucket Tenor
	 * 
	 * @param tenor The Tenor
	 * 
	 * @return The Sensitivity corresponding to the Tenor
	 * 
	 * @throws java.lang.Exception Thrown if the Input is Invalid
	 */

	public double sensitivity (
		final java.lang.String tenor)
		throws java.lang.Exception
	{
		if (!tenorExists (tenor))
		{
			throw new java.lang.Exception ("RiskFactorTenorSensitivity::sensitivity => Invalid Inputs");
		}

		return _sensitivityMap.get (tenor);
	}

	/**
	 * Retrieve the Map of Tenor Sensitivities
	 * 
	 * @return The Map of Tenor Sensitivities
	 */

	public java.util.Map<java.lang.String, java.lang.Double> sensitivityMap()
	{
		return _sensitivityMap;
	}

	/**
	 * Generate the Cumulative Tenor Sensitivity
	 * 
	 * @return The Cumulative Tenor Sensitivity
	 */

	public double cumulative()
	{
		double cumulative = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> sensitivityEntry : _sensitivityMap.entrySet())
		{
			cumulative = cumulative + sensitivityEntry.getValue();
		}

		return cumulative;
	}

	/**
	 * Generate the Tenor Sensitivity Margin Map
	 * 
	 * @param sensitivityRiskWeightMap The Tenor Sensitivity Risk Weight Map
	 * 
	 * @return The Tenor Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> sensitivityMargin (
		final java.util.Map<java.lang.String, java.lang.Double> sensitivityRiskWeightMap)
	{
		if (null == sensitivityRiskWeightMap || 0 == sensitivityRiskWeightMap.size())
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> sensitivityMargin = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> sensitivityEntry :
			_sensitivityMap.entrySet())
		{
			java.lang.String tenor = sensitivityEntry.getKey();

			if (!sensitivityRiskWeightMap.containsKey (tenor))
			{
				return null;
			}

			sensitivityMargin.put (
				tenor,
				sensitivityEntry.getValue() * sensitivityRiskWeightMap.get (tenor)
			);
		}

		return sensitivityMargin;
	}
}
