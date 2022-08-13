
package org.drip.simm.equity;

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
 * <i>EQBucket</i> holds the ISDA SIMM Region, Sector, Member Correlation, and Risk Weights for a given
 * Equity Issuer Exposure Bucket. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/equity/README.md">Equity Risk Factor Calibration Settings</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EQBucket
{
	private int _number = -1;
	private String _size = "";
	private String _region = "";
	private String[] _sectorArray = null;
	private double _vegaRiskWeight = Double.NaN;
	private double _deltaRiskWeight = Double.NaN;
	private double _memberCorrelation = Double.NaN;

	/**
	 * EQBucket Constructor
	 * 
	 * @param number Bucket Number
	 * @param size Bucket Equity Market Capitalization Size
	 * @param region Buket Region
	 * @param sectorArray Bucket Sector Array
	 * @param deltaRiskWeight Bucket Delta Risk Weight
	 * @param memberCorrelation Bucket Member Correlation
	 * @param vegaRiskWeight The Bucket Vega Risk Weight
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public EQBucket (
		final int number,
		final String size,
		final String region,
		final String[] sectorArray,
		final double deltaRiskWeight,
		final double memberCorrelation,
		final double vegaRiskWeight)
		throws Exception
	{
		if (null == (_size = size) || _size.isEmpty() ||
			null == (_region = region) || _region.isEmpty() ||
			null == (_sectorArray = sectorArray) || 0 == _sectorArray.length ||
			!NumberUtil.IsValid (
				_deltaRiskWeight = deltaRiskWeight
			) ||
			!NumberUtil.IsValid (
				_memberCorrelation = memberCorrelation
			) ||
			!NumberUtil.IsValid (
				_vegaRiskWeight = vegaRiskWeight
			)
		)
		{
			throw new Exception (
				"EQBucket Constructor => Invalid Inputs"
			);
		}

		_number = number;
	}

	/**
	 * Retrieve the Bucket Number
	 * 
	 * @return The Bucket Number
	 */

	public int number()
	{
		return _number;
	}

	/**
	 * Retrieve the Bucket Size
	 * 
	 * @return The Bucket Size
	 */

	public String size()
	{
		return _size;
	}

	/**
	 * Retrieve the Bucket Region
	 * 
	 * @return The Bucket Region
	 */

	public String region()
	{
		return _region;
	}

	/**
	 * Retrieve the Bucket Sector Array
	 * 
	 * @return The Bucket Sector Array
	 */

	public String[] sectorArray()
	{
		return _sectorArray;
	}

	/**
	 * Retrieve the Bucket Delta Risk Weight
	 * 
	 * @return The Bucket Delta Risk Weight
	 */

	public double deltaRiskWeight()
	{
		return _deltaRiskWeight;
	}

	/**
	 * Retrieve the Correlation between the Bucket Members
	 * 
	 * @return Correlation between the Bucket Members
	 */

	public double memberCorrelation()
	{
		return _memberCorrelation;
	}

	/**
	 * Retrieve the Bucket Vega Risk Weight
	 * 
	 * @return The Bucket Vega Risk Weight
	 */

	public double vegaRiskWeight()
	{
		return _vegaRiskWeight;
	}
}
