
package org.drip.simm.product;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.drip.numerical.common.NumberUtil;

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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product/README.md">ISDA SIMM Risk Factor Sensitivities</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RiskFactorTenorSensitivity
{
	private Map<String, Double> _sensitivityMap = null;

	/**
	 * RiskFactorTenorSensitivity Constructor
	 * 
	 * @param sensitivityMap The Tenor Sensitivity Map
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RiskFactorTenorSensitivity (
		final Map<String, Double> sensitivityMap)
		throws Exception
	{
		if (null == (_sensitivityMap = sensitivityMap) || 0 == _sensitivityMap.size())
		{
			throw new Exception (
				"RiskFactorTenorSensitivity Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Set of Tenors
	 * 
	 * @return The Set of Tenors
	 */

	public Set<String> tenorSet()
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
		final String tenor,
		final double sensitivity)
	{
		if (null == tenor ||
			!NumberUtil.IsValid (
				sensitivity
			)
		)
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
		final String tenor)
	{
		return null != tenor && _sensitivityMap.containsKey (
			tenor
		);
	}

	/**
	 * Retrieve the Sensitivity for the Bucket Tenor
	 * 
	 * @param tenor The Tenor
	 * 
	 * @return The Sensitivity corresponding to the Tenor
	 * 
	 * @throws Exception Thrown if the Input is Invalid
	 */

	public double sensitivity (
		final String tenor)
		throws Exception
	{
		if (!tenorExists (
			tenor
		))
		{
			throw new Exception (
				"RiskFactorTenorSensitivity::sensitivity => Invalid Inputs"
			);
		}

		return _sensitivityMap.get (
			tenor
		);
	}

	/**
	 * Retrieve the Map of Tenor Sensitivities
	 * 
	 * @return The Map of Tenor Sensitivities
	 */

	public Map<String, Double> sensitivityMap()
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

		for (Map.Entry<String, Double> sensitivityEntry : _sensitivityMap.entrySet())
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

	public Map<String, Double> sensitivityMargin (
		final Map<String, Double> sensitivityRiskWeightMap)
	{
		if (null == sensitivityRiskWeightMap || 0 == sensitivityRiskWeightMap.size())
		{
			return null;
		}

		Map<String, Double> sensitivityMargin = new HashMap<String, Double>();

		for (Map.Entry<String, Double> sensitivityEntry : _sensitivityMap.entrySet())
		{
			String tenor = sensitivityEntry.getKey();

			if (!sensitivityRiskWeightMap.containsKey (
				tenor
			))
			{
				return null;
			}

			sensitivityMargin.put (
				tenor,
				sensitivityEntry.getValue() * sensitivityRiskWeightMap.get (
					tenor
				)
			);
		}

		return sensitivityMargin;
	}
}
