
package org.drip.investing.factors;

import java.util.Map;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2024 Lakshmi Krishnamurthy
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
 * <i>FactorPortfolio</i> has the Portfolio Details that constitute a Factor. The References are:
 *
 *	<br><br>
 * <ul>
 * 	<li>
 *  	Blitz, D., M. X. Hanauer, M. Vidojevic, and P. van Vliet (2018): Five-Factors with the Five-Factor
 *  		Model <i>Journal of Portfolio Management</i> <b>44 (4)</b> 71-78
 * 	</li>
 * 	<li>
 *  	Fama, E. F., and K. R. French (1992): The Cross-section of Expected Stock Returns <i>Journal of
 *  		Finance</i> <b>47 (2)</b> 427-465
 * 	</li>
 * 	<li>
 *  	Fama, E. F., and K. R. French (2015): A Five-Factor Asset Pricing Model <i>Journal of Financial
 *  		Economics</i> <b>116 (1)</b> 1-22
 * 	</li>
 * 	<li>
 *  	Foye, J. (2018): Testing Alternative Versions of the Fama-French Five-Factor Model in the UK <i>Risk
 *  		Management</i> <b>20 (2)</b> 167-183
 * 	</li>
 * 	<li>
 *  	Wikipedia (2024): Fama�French three-factor model
 *  		<i>https://en.wikipedia.org/wiki/Fama%E2%80%93French_three-factor_model</i>
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/investing/README.md">Factor/Style Based Quantitative Investing</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/investing/factors/README.md">Factor Types, Characteristics, and Constitution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FactorPortfolio
{
	private String _country = "";
	private boolean _isGlobal = false;
	private int _financingScheme = Integer.MIN_VALUE;
	private Map<String, FactorComponentLoading> _factorComponentLoadingMasterUniverseMap = null;

	/**
	 * FactorPortfolio Constructor
	 * 
	 * @param factorComponentLoadingMasterUniverseMap Factor Component Loading Master Universe Map
	 * @param country Country
	 * @param isGlobal TRUE - The Portfolio is Global
	 * @param financingScheme Financing Scheme
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public FactorPortfolio (
		final Map<String, FactorComponentLoading> factorComponentLoadingMasterUniverseMap,
		final String country,
		final boolean isGlobal,
		final int financingScheme)
		throws Exception
	{
		if (null == (_country = country) || _country.isEmpty() ||
			null == (_factorComponentLoadingMasterUniverseMap = factorComponentLoadingMasterUniverseMap))
		{
			throw new Exception ("FactorPortfolio Constructor => Invalid Inputs");
		}

		_isGlobal = isGlobal;
		_financingScheme = financingScheme;
	}

	/**
	 * Retrieve the Factor Component Loading Master Universe Map
	 * 
	 * @return The Factor Component Loading Master Universe Map
	 */

	public Map<String, FactorComponentLoading> factorComponentLoadingMasterUniverseMap()
	{
		return _factorComponentLoadingMasterUniverseMap;
	}

	/**
	 * Retrieve the Country
	 * 
	 * @return The Country
	 */

	public String country()
	{
		return _country;
	}

	/**
	 * Indicate if the Portfolio is Global
	 * 
	 * @return TRUE - The Portfolio is Global
	 */

	public boolean isGlobal()
	{
		return _isGlobal;
	}

	/**
	 * Retrieve the Financing Scheme
	 * 
	 * @return The Financing Scheme
	 */

	public int financingScheme()
	{
		return _financingScheme;
	}
}
