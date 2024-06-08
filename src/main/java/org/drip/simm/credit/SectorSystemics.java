
package org.drip.simm.credit;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>SectorSystemics</i> contains the Systemic Settings that hold Sector-related Information. The References
 * 	are:
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
 * 	It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Initialize the Credit Risk Threshold Container</li>
 * 		<li>Retrieve the Credit Risk Qualifying Threshold Bucket Set</li>
 * 		<li>Retrieve the Credit Risk Non-Qualifying Threshold Bucket Set</li>
 * 		<li>Indicate if the Qualifying Bucket specified by the Number is available</li>
 * 		<li>Indicate if the Non-Qualifying Bucket specified by the Number is available</li>
 * 		<li>Retrieve the Credit Risk Qualifying Threshold Instance identified by the Bucket Number</li>
 * 		<li>Retrieve the Credit Risk Non-Qualifying Threshold Instance identified by the Bucket Number</li>
 * 		<li>Retrieve the Credit Risk Qualifying Threshold Map</li>
 * 		<li>Retrieve the Credit Risk Non-Qualifying Threshold Map</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/credit/README.md">Credit Qualifying/Non-Qualifying Risk Factor Settings</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SectorSystemics
{

	/**
	 * The Sovereigns Sector
	 */

	public static final String[] SOVEREIGNS = new String[]
	{
		"SOVEREIGN",
		"CENTRAL_BANK"
	};

	/**
	 * The Financials Sector
	 */

	public static final String[] FINANCIALS = new String[]
	{
		"FINANCIAL",
		"GOVERNMENT_BACKED_FINANCIAL"
	};

	/**
	 * The Basic Materials Sector
	 */

	public static final String[] BASIC_MATERIALS = new String[]
	{
		"BASIC_MATERIALS",
		"ENERGY",
		"INDUSTRIALS"
	};

	/**
	 * The Consumer Sector
	 */

	public static final String[] CONSUMER = new String[]
	{
		"CONSUMER"
	};

	/**
	 * The Technology/Media/Telecommunications Sector
	 */

	public static final String[] TMT = new String[]
	{
		"TECHNOLOGY",
		"MEDIA",
		"TELECOMMUNICATIONS"
	};

	/**
	 * The Local Services Sector
	 */

	public static final String[] LOCAL_SERVICES = new String[]
	{
		"NON_FINANCIAL",
		"HEALTH_CARE",
		"UTILITIES",
		"LOCAL_GOVERNMENT",
		"GOVERNMENT_BACKED_CORPORATES",
		"NON_FINANCIAL"
	};

	/**
	 * The RMBS/CMBS Sector
	 */

	public static final String[] RMBS_CMBS = new String[]
	{
		"RMBS",
		"CMBS"
	};

	/**
	 * The "Residual" Sector
	 */

	public static final String[] RESIDUAL = new String[]
	{
		"RESIDUAL"
	};

	/**
	 * The Consumer Services Sector
	 */

	public static final String[] CONSUMER_SERVICES = new String[]
	{
		"CONSUMER_GOODS",
		"CONSUMER_SERVICES",
		"TRANSPORTATION_STORAGE",
		"ADMINISTRATIVE_AND_SUPPORT_SERVICE_ACTIVITIES",
		"UTILITIES"
	};

	/**
	 * The Telecommunications/Industrials Sector
	 */

	public static final String[] TELECOMMUNICATIONS_INDUSTRIALS = new String[]
	{
		"TELECOMMUNICATIONS",
		"INDUSTRIALS"
	};

	/**
	 * The Heavy Industrials Sector
	 */

	public static final String[] HEAVY_INDUSTRIALS = new String[]
	{
		"BASIC_MATERIALS",
		"ENERGY",
		"AGRICULTURE",
		"MANUFACTURING",
		"MINING",
		"QUARRYING"
	};

	/**
	 * The Investment Sector
	 */

	public static final String[] INVESTMENT = new String[]
	{
		"FINANCIAL",
		"GOVERNMENT_BACKED_FINANCIAL",
		"REAL_ESTATE_ACTIVITIES",
		"TECHNOLOGY"
	};

	/**
	 * The "All" Sector
	 */

	public static final String[] ALL = new String[]
	{
		"ALL"
	};

	/**
	 * The Indexes/Funds/ETF's Sector
	 */

	public static final String[] INDEX_FUND_ETF = new String[]
	{
		"INDEX",
		"FUND",
		"ETF"
	};

	/**
	 * The Volatility Index Sector
	 */

	public static final String[] VOLATILITY_INDEX = new String[]
	{
		"VOLATILITY_INDEX"
	};
}
