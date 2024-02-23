
package org.drip.investing.investible;

import org.drip.portfolioconstruction.core.Asset;

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
 * <i>AssetSpecification</i> holds the Characteristics of Asset/Fund whose Behavior is Benchmarked to
 * 	Specific Factors. The References are:
 *
 *	<br><br>
 * <ul>
 * 	<li>
 *  	Baltussen, G., L. Swinkels, and P. van Vliet (2021): Global Factor Premiums <i>Journal of Financial
 *  		Economics</i> <b>142 (3)</b> 1128-1154
 * 	</li>
 * 	<li>
 *  	Blitz, D., and P. van Vliet (2007): The Volatility Effect: Lower Risk without Lower Return <i>Journal
 *  		of Portfolio Management</i> <b>34 (1)</b> 102-113
 * 	</li>
 * 	<li>
 *  	Fisher, G. S., R. Shah, and S. Titman (2017): Combining Value and Momentum
 *  		<i>https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2472936</i> <b>eSSRN</b>
 * 	</li>
 * 	<li>
 *  	Houweling, P., and J. van Zundert (2017): Factor Investing in the Corporate Bond Market <i>Financial
 *  		Analysts Journal</i> <b>73 (2)</b> 100-115
 * 	</li>
 * 	<li>
 *  	Wikipedia (2024): Factor Investing <i>https://en.wikipedia.org/wiki/Factor_investing</i>
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/investing/README.md">Factor/Style Based Quantitative Investing</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/investing/investible/README.md">Quantitative Description of Investible Assets</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class AssetSpecification extends Asset
{
	private int _type = Integer.MIN_VALUE;
	private int _termCategory = Integer.MIN_VALUE;
	private int _carryCategory = Integer.MIN_VALUE;
	private int _valueCategory = Integer.MIN_VALUE;
	private int _growthCategory = Integer.MIN_VALUE;
	private int _marketCategory = Integer.MIN_VALUE;
	private int _leverageCategory = Integer.MIN_VALUE;
	private int _momentumCategory = Integer.MIN_VALUE;
	private int _investingCategory = Integer.MIN_VALUE;
	private int _liquidityCategory = Integer.MIN_VALUE;
	private int _volatilityCategory = Integer.MIN_VALUE;
	private int _profitabilityCategory = Integer.MIN_VALUE;
	private int _capitalizationCategory = Integer.MIN_VALUE;

	/**
	 * AssetSpecification Constructor
	 * 
	 * @param name The Asset Name
	 * @param id The Asset ID
	 * @param description The Asset Description
	 * @param currency The Asset Currency
	 * @param sector The Asset Sector
	 * @param type The Asset Type
	 * @param marketCategory The Market Category
	 * @param capitalizationCategory The Capitalization Category
	 * @param volatilityCategory The Volatility Category
	 * @param valueCategory The Value Category
	 * @param momentumCategory The Momentum Category
	 * @param growthCategory The Growth Category
	 * @param profitabilityCategory The Profitability Category
	 * @param leverageCategory The Leverage Category
	 * @param liquidityCategory The Liquidity Category
	 * @param termCategory The Term Category
	 * @param carryCategory The Carry Category
	 * @param investingCategory The Investing Category
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public AssetSpecification (
		final String name,
		final String id,
		final String description,
		final String currency,
		final String sector,
		final int type,
		final int marketCategory,
		final int capitalizationCategory,
		final int volatilityCategory,
		final int valueCategory,
		final int momentumCategory,
		final int growthCategory,
		final int profitabilityCategory,
		final int leverageCategory,
		final int liquidityCategory,
		final int termCategory,
		final int carryCategory,
		final int investingCategory)
		throws Exception
	{
		super (
			name,
			id,
			description,
			currency,
			sector
		);

		_type = type;
		_termCategory = termCategory;
		_carryCategory = carryCategory;
		_valueCategory = valueCategory;
		_growthCategory = growthCategory;
		_marketCategory = marketCategory;
		_leverageCategory = leverageCategory;
		_momentumCategory = momentumCategory;
		_investingCategory = investingCategory;
		_liquidityCategory = liquidityCategory;
		_volatilityCategory = volatilityCategory;
		_profitabilityCategory = profitabilityCategory;
		_capitalizationCategory = capitalizationCategory;
	}

	/**
	 * Retrieve the Asset Type
	 * 
	 * @return The Asset Type
	 */

	public int type()
	{
		return _type;
	}

	/**
	 * Retrieve the Market Category
	 * 
	 * @return The Market Category
	 */

	public int marketCategory()
	{
		return _marketCategory;
	}

	/**
	 * Retrieve the Capitalization Category
	 * 
	 * @return The Capitalization Category
	 */

	public int capitalizationCategory()
	{
		return _capitalizationCategory;
	}

	/**
	 * Retrieve the Volatility Category
	 * 
	 * @return The Volatility Category
	 */

	public int volatilityCategory()
	{
		return _volatilityCategory;
	}

	/**
	 * Retrieve the Value Category
	 * 
	 * @return The Value Category
	 */

	public int valueCategory()
	{
		return _valueCategory;
	}

	/**
	 * Retrieve the Momentum Category
	 * 
	 * @return The Momentum Category
	 */

	public int momentumCategory()
	{
		return _momentumCategory;
	}

	/**
	 * Retrieve the Growth Category
	 * 
	 * @return The Growth Category
	 */

	public int growthCategory()
	{
		return _growthCategory;
	}

	/**
	 * Retrieve the Profitability Category
	 * 
	 * @return The Profitability Category
	 */

	public int profitabilityCategory()
	{
		return _profitabilityCategory;
	}

	/**
	 * Retrieve the Leverage Category
	 * 
	 * @return The Leverage Category
	 */

	public int leverageCategory()
	{
		return _leverageCategory;
	}

	/**
	 * Retrieve the Liquidity Category
	 * 
	 * @return The Liquidity Category
	 */

	public int liquidityCategory()
	{
		return _liquidityCategory;
	}

	/**
	 * Retrieve the Term Category
	 * 
	 * @return The Term Category
	 */

	public int termCategory()
	{
		return _termCategory;
	}

	/**
	 * Retrieve the Carry Category
	 * 
	 * @return The Carry Category
	 */

	public int carryCategory()
	{
		return _carryCategory;
	}

	/**
	 * Retrieve the Investing Category
	 * 
	 * @return The Investing Category
	 */

	public int investingCategory()
	{
		return _investingCategory;
	}
}
