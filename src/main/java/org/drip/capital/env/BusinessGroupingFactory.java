
package org.drip.capital.env;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>BusinessGroupingFactory</i> instantiates the Built-in Business Groupings. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision(2005): Stress Testing at Major Financial Institutions: Survey
 * 				Results and Practice https://www.bis.org/publ/cgfs24.htm
 * 		</li>
 * 		<li>
 * 			Glasserman, P. (2004): <i>Monte Carlo Methods in Financial Engineering</i> <b>Springer</b>
 * 		</li>
 * 		<li>
 * 			Kupiec, P. H. (2000): Stress Tests and Risk Capital <i>Risk</i> <b>2 (4)</b> 27-39
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md">Basel Market Risk and Operational Capital</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/env/README.md">Economic Risk Capital Parameter Factories</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BusinessGroupingFactory
{

	private static final boolean AddBusinessGrouping (
		final java.util.Map<java.lang.String, org.drip.capital.label.BusinessGrouping>
			businessGroupingMap,
		final org.drip.capital.label.BusinessGrouping businessGrouping)
	{
		businessGroupingMap.put (
			businessGrouping.business(),
			businessGrouping
		);

		return true;
	}

	/**
	 * Instantiate the Built-in BusinessGroupingContext
	 * 
	 * @return TRUE - The BusinessGroupingContext Instance
	 */

	public static org.drip.capital.shell.BusinessGroupingContext Instantiate()
	{
		java.util.Map<java.lang.String, org.drip.capital.label.BusinessGrouping> businessGroupingMap =
			new org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.label.BusinessGrouping>();

		try
		{
			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.G10_RATES,
					org.drip.capital.definition.Product.G10_RATES,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.G10_FX,
					org.drip.capital.definition.Product.FX_LOCAL_MARKETS,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.LOCAL_MARKETS,
					org.drip.capital.definition.Product.FX_LOCAL_MARKETS,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.RISK_TREASURY,
					org.drip.capital.definition.Product.G10_RISK_TREASURY_RV_FINANCE,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.FINANCE,
					org.drip.capital.definition.Product.G10_RISK_TREASURY_RV_FINANCE,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.FIMA,
					org.drip.capital.definition.Product.G10_RISK_TREASURY_RV_FINANCE,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.PECD,
					org.drip.capital.definition.Product.GSP,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.CREDIT_TRADING,
					org.drip.capital.definition.Product.GSP,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.EM_CREDIT_TRADING,
					org.drip.capital.definition.Product.GSP,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.SHORT_TERM,
					org.drip.capital.definition.Product.GSP,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.CREDIT_MACRO_HEDGE,
					org.drip.capital.definition.Product.GSP,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.DISTRESSED,
					org.drip.capital.definition.Product.GSP,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.SECURITIZED_MARKETS,
					org.drip.capital.definition.Product.GSP,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.GSSG_WEST,
					org.drip.capital.definition.Product.GSP,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.MUNICIPAL,
					org.drip.capital.definition.Product.GSP,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.COMMODITIES_HOUSTON,
					org.drip.capital.definition.Product.COMMODITIES,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.CASH,
					org.drip.capital.definition.Product.EQUITIES,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.EQUITY_DERIVATIVES,
					org.drip.capital.definition.Product.EQUITIES,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.CONVERTS,
					org.drip.capital.definition.Product.EQUITIES,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.PRIME_FINANCE,
					org.drip.capital.definition.Product.PRIME_FINANCE,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.OTHER_GLOBAL_MARKETS,
					org.drip.capital.definition.Product.OTHER_GLOBAL_MARKETS,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.NIKKO_INVESTMENTS,
					org.drip.capital.definition.Product.NIKKO_INVESTMENTS,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.EQUITY_UNDERWRITING,
					org.drip.capital.definition.Product.EQUITY_UNDERWRITING,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.IG_PRIMARY_LOANS,
					org.drip.capital.definition.Product.FIXED_INCOME_UNDERWRITING,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.IG_BONDS,
					org.drip.capital.definition.Product.FIXED_INCOME_UNDERWRITING,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.LEVERAGED_FINANCE,
					org.drip.capital.definition.Product.FIXED_INCOME_UNDERWRITING,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.EM_PRIMARY_LOANS,
					org.drip.capital.definition.Product.FIXED_INCOME_UNDERWRITING,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.EM_BONDS,
					org.drip.capital.definition.Product.FIXED_INCOME_UNDERWRITING,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.PROJECT_FINANCE,
					org.drip.capital.definition.Product.FIXED_INCOME_UNDERWRITING,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.OTHER_FI_UNDERWRITING,
					org.drip.capital.definition.Product.FIXED_INCOME_UNDERWRITING,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.EM_ABF,
					org.drip.capital.definition.Product.EM_ABF,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.CAI,
					org.drip.capital.definition.Product.CAI,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.ADVISORY,
					org.drip.capital.definition.Product.ADVISORY,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.GWM,
					org.drip.capital.definition.Product.GWM,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.GTS,
					org.drip.capital.definition.Product.GTS,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.CONSUMER_OTHER,
					org.drip.capital.definition.Product.CONSUMER,
					org.drip.capital.definition.Group.BHCCORP_CONSUMER
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.RETAIL_BANKING,
					org.drip.capital.definition.Product.CONSUMER,
					org.drip.capital.definition.Group.BHCCORP_CONSUMER
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.CONSUMER_CARDS,
					org.drip.capital.definition.Product.CONSUMER,
					org.drip.capital.definition.Group.BHCCORP_CONSUMER
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.REAL_ESTATE_LENDING,
					org.drip.capital.definition.Product.HOLDINGS_CONSUMER,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.STUDENT_LOANS,
					org.drip.capital.definition.Product.HOLDINGS_CONSUMER,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.RETAIL_PARTNER_CARDS,
					org.drip.capital.definition.Product.HOLDINGS_CONSUMER,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.RETAIL_AUTO_LENDING,
					org.drip.capital.definition.Product.HOLDINGS_CONSUMER,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.PRIMERICA_FINANCIAL_SERVICES,
					org.drip.capital.definition.Product.HOLDINGS_CONSUMER,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.COMMERCIAL_REAL_ESTATE,
					org.drip.capital.definition.Product.HOLDINGS_CONSUMER,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.BHCFINANCIAL,
					org.drip.capital.definition.Product.HOLDINGS_CONSUMER,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.INTERNATIONAL_CARDS,
					org.drip.capital.definition.Product.HOLDINGS_CONSUMER,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.INTERNATIONAL_RETAIL_BANKING,
					org.drip.capital.definition.Product.HOLDINGS_CONSUMER,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.OTHER_CONSUMER,
					org.drip.capital.definition.Product.HOLDINGS_CONSUMER,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.SMITH_BARNEY_BAM,
					org.drip.capital.definition.Product.BAM,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.OTHER_BAM,
					org.drip.capital.definition.Product.BAM,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.AI,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.CAPITAL_MARKETS_ORGANIZATION,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.CAPITAL_MARKETS_ORIGINATION_LENDING,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.CENTRAL_AMERICA_MORTGAGES,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.COMMODITIES,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.CREDIT_MARKETS,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.EM_ASSET_BACKED_FINANCE,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.EQUITIES,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.GLOBAL_SECURITIZED_MARKETS,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.GLOBAL_CREDIT_MARKETS,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.GTS_HOLDINGS_TRADE,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.LONG_TERM_ASSET_GROUP,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.MUNICIPAL_SECURITIES_BHC_COMMUNITY,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.MUNICIPAL_SECURITIES,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.OTHER_SPECIAL_ASSET_POOL,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.PRIVATE_BANKING,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.RATES_AND_CURRENCIES,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.RUBICON_INDIA,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.SAP_ADMIN,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.US_COMMERCIAL_BANKING,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.US_CONSUMER_INSTALLMENT_LOANS,
					org.drip.capital.definition.Product.SAP,
					org.drip.capital.definition.Group.HOLDINGS
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.CORPORATE_CENTER,
					org.drip.capital.definition.Product.CORPORATE_CENTER,
					org.drip.capital.definition.Group.CORPORATE_CENTER
				)
			);

			AddBusinessGrouping (
				businessGroupingMap,
				new org.drip.capital.label.BusinessGrouping (
					org.drip.capital.definition.Business.OS_B,
					org.drip.capital.definition.Product.OS_B,
					org.drip.capital.definition.Group.BHCCORP_ICG
				)
			);

			return new org.drip.capital.shell.BusinessGroupingContext (
				businessGroupingMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
