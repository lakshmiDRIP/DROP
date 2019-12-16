
package org.drip.capital.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>Business</i> maintains the C<sup>1</sup> Fixings for the Business Categorical Variate. The References
 * 	are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/definition/README.md">Economic Risk Capital Categorical Definitions</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Business
{

	/**
	 * Advisory Business
	 */

	public static final java.lang.String ADVISORY = "Advisory";

	/**
	 * AI Business
	 */

	public static final java.lang.String AI = "AI";

	/**
	 * BHC Financial Business
	 */

	public static final java.lang.String BHCFINANCIAL = "BHCFinancial";

	/**
	 * CAI Business
	 */

	public static final java.lang.String CAI = "CAI";

	/**
	 * Capital Markets Organization Business
	 */

	public static final java.lang.String CAPITAL_MARKETS_ORGANIZATION = "Capital Markets Organization";

	/**
	 * Capital Markets Origination Lending Business
	 */

	public static final java.lang.String CAPITAL_MARKETS_ORIGINATION_LENDING =
		"Capital Markets Origination Lending";

	/**
	 * Cards Business
	 */

	public static final java.lang.String CARDS = "Cards";

	/**
	 * Cash Business
	 */

	public static final java.lang.String CASH = "Cash";

	/**
	 * Central America Mortgages Business
	 */

	public static final java.lang.String CENTRAL_AMERICA_MORTGAGES = "Central America Mortgages";

	/**
	 * CLP Business
	 */

	public static final java.lang.String CLP = "CLP";

	/**
	 * Commercial Real Estate Business
	 */

	public static final java.lang.String COMMERCIAL_REAL_ESTATE = "Commercial Real Estate";

	/**
	 * Commodities Business
	 */

	public static final java.lang.String COMMODITIES = "Commodities";

	/**
	 * Houston Commodities Business
	 */

	public static final java.lang.String COMMODITIES_HOUSTON = "Commodts Houston";

	/**
	 * Consumer Cards Business
	 */

	public static final java.lang.String CONSUMER_CARDS = "Consumer_Cards";

	/**
	 * Consumer Other Business
	 */

	public static final java.lang.String CONSUMER_OTHER = "Consumer_Other";

	/**
	 * Converts Business
	 */

	public static final java.lang.String CONVERTS = "Converts";

	/**
	 * Corporate Center Business
	 */

	public static final java.lang.String CORPORATE_CENTER = "CorpCtr";

	/**
	 * Credit Macro Hedge Business
	 */

	public static final java.lang.String CREDIT_MACRO_HEDGE = "Credit Macro Hedge";

	/**
	 * Credit Markets Business
	 */

	public static final java.lang.String CREDIT_MARKETS = "Credit Markets";

	/**
	 * Credit Trading Business
	 */

	public static final java.lang.String CREDIT_TRADING = "Credit Trading";

	/**
	 * Distressed Business
	 */

	public static final java.lang.String DISTRESSED = "Distressed";

	/**
	 * EM ABF Business
	 */

	public static final java.lang.String EM_ABF = "EM ABF";

	/**
	 * EM Asset Backed Finance Business
	 */

	public static final java.lang.String EM_ASSET_BACKED_FINANCE = "EM Asset Backed Finance";

	/**
	 * EM Bonds Business
	 */

	public static final java.lang.String EM_BONDS = "EM Bonds";

	/**
	 * EM Credit Trading Business
	 */

	public static final java.lang.String EM_CREDIT_TRADING = "EM Credit Trading";

	/**
	 * EM Prm Loans Business
	 */

	public static final java.lang.String EM_PRIMARY_LOANS = "EM Prm Loans";

	/**
	 * Equities Business
	 */

	public static final java.lang.String EQUITIES = "Equities";

	/**
	 * Equity Derivatives Business
	 */

	public static final java.lang.String EQUITY_DERIVATIVES = "Equity Derivative";

	/**
	 * Equity Underwriting Business
	 */

	public static final java.lang.String EQUITY_UNDERWRITING = "Equity Undwrt";

	/**
	 * FIMA Business
	 */

	public static final java.lang.String FIMA = "FIMA";

	/**
	 * Finance Business
	 */

	public static final java.lang.String FINANCE = "Finance";

	/**
	 * G10 FX Business
	 */

	public static final java.lang.String G10_FX = "G10 FX";

	/**
	 * G10 Rates Business
	 */

	public static final java.lang.String G10_RATES = "G10 Rates";

	/**
	 * Glbl Securitized Markets Business
	 */

	public static final java.lang.String GLOBAL_SECURITIZED_MARKETS = "Glbl Securitized Markets";

	/**
	 * Global Credit Markets Business
	 */

	public static final java.lang.String GLOBAL_CREDIT_MARKETS = "Global Credit Markets";

	/**
	 * GSSG West Business
	 */

	public static final java.lang.String GSSG_WEST = "GSSG West";

	/**
	 * GTS Business
	 */

	public static final java.lang.String GTS = "GTS";

	/**
	 * GTS Holdings-Trade Business
	 */

	public static final java.lang.String GTS_HOLDINGS_TRADE = "GTS Holdings-Trade";

	/**
	 * GWM Business
	 */

	public static final java.lang.String GWM = "GWM";

	/**
	 * IG Bonds Business
	 */

	public static final java.lang.String IG_BONDS = "IG Bonds";

	/**
	 * IG Prmry Loans Business
	 */

	public static final java.lang.String IG_PRIMARY_LOANS = "IG Prmry Loans";

	/**
	 * International Cards Business
	 */

	public static final java.lang.String INTERNATIONAL_CARDS = "International Cards";

	/**
	 * International Retail Banking Business
	 */

	public static final java.lang.String INTERNATIONAL_RETAIL_BANKING = "International Retail Banking";

	/**
	 * Lev Fin Business
	 */

	public static final java.lang.String LEVERAGED_FINANCE = "Lev Fin";

	/**
	 * Local Markets Business
	 */

	public static final java.lang.String LOAN_PORTFOLIO_MANAGEMENT = "Loan Portfolio Management";

	/**
	 * Local Markets Business
	 */

	public static final java.lang.String LOCAL_MARKETS = "Local Mkts";

	/**
	 * Long Term Asset Group Business
	 */

	public static final java.lang.String LONG_TERM_ASSET_GROUP = "Long Term Asset Group";

	/**
	 * Municipal Securities Business
	 */

	public static final java.lang.String MUNICIPAL_SECURITIES = "Municipal Securities";

	/**
	 * Municipal Securities - Community Business
	 */

	public static final java.lang.String MUNICIPAL_SECURITIES_BHC_COMMUNITY =
		"Municipal Securities - BHC Community";

	/**
	 * Municipal Business
	 */

	public static final java.lang.String MUNICIPAL = "Munis";

	/**
	 * Nikko Investments Business
	 */

	public static final java.lang.String NIKKO_INVESTMENTS = "Nikko Investments";

	/**
	 * OS and B Business
	 */

	public static final java.lang.String OS_B = "OS&B";

	/**
	 * Other FI Undwrtng Business
	 */

	public static final java.lang.String OTHER_FI_UNDERWRITING = "Other FI Undwrtng";

	/**
	 * Other FI Glbl Mkts Business
	 */

	public static final java.lang.String OTHER_GLOBAL_MARKETS = "Other Glbl Mkts";

	/**
	 * Other Special Asset Pool Business
	 */

	public static final java.lang.String OTHER_SPECIAL_ASSET_POOL = "Other Special Asset Pool";

	/**
	 * Other_BAM Business
	 */

	public static final java.lang.String OTHER_BAM = "Other_BAM";

	/**
	 * PECD Business
	 */

	public static final java.lang.String PECD = "PECD";

	/**
	 * Other_Consumer Business
	 */

	public static final java.lang.String OTHER_CONSUMER = "Other_Consumer";

	/**
	 * Prime Finance Business
	 */

	public static final java.lang.String PRIME_FINANCE = "Prime Finance";

	/**
	 * Primerica Financial Services Business
	 */

	public static final java.lang.String PRIMERICA_FINANCIAL_SERVICES = "Primerica Financial Services";

	/**
	 * Private Banking Business
	 */

	public static final java.lang.String PRIVATE_BANKING = "Private Banking";

	/**
	 * Project Finance Business
	 */

	public static final java.lang.String PROJECT_FINANCE = "Project Finance";

	/**
	 * Rates and Currencies Business
	 */

	public static final java.lang.String RATES_AND_CURRENCIES = "Rates and Currencies";

	/**
	 * Real Estate Lending Business
	 */

	public static final java.lang.String REAL_ESTATE_LENDING = "Real Estate Lending";

	/**
	 * Retail Auto Lending Business
	 */

	public static final java.lang.String RETAIL_AUTO_LENDING = "Retail Auto Lending";

	/**
	 * Retail Banking Business
	 */

	public static final java.lang.String RETAIL_BANKING = "Retail Banking";

	/**
	 * Retail Partner Cards Business
	 */

	public static final java.lang.String RETAIL_PARTNER_CARDS = "Retail Partner Cards";

	/**
	 * Risk Treasury Business
	 */

	public static final java.lang.String RISK_TREASURY = "Risk Treasury";

	/**
	 * Rubicon - India Business
	 */

	public static final java.lang.String RUBICON_INDIA = "Rubicon - India";

	/**
	 * SAP Admin Business
	 */

	public static final java.lang.String SAP_ADMIN = "SAP Admin";

	/**
	 * Securitized Markets Business
	 */

	public static final java.lang.String SECURITIZED_MARKETS = "Securitized Mkts";

	/**
	 * Short Term Business
	 */

	public static final java.lang.String SHORT_TERM = "Short Term";

	/**
	 * Smith_Barney_BAM Business
	 */

	public static final java.lang.String SMITH_BARNEY_BAM = "Smith_Barney_BAM";

	/**
	 * Student Loans Business
	 */

	public static final java.lang.String STUDENT_LOANS = "Student Loans";

	/**
	 * US Commercial Banking Business
	 */

	public static final java.lang.String US_COMMERCIAL_BANKING = "US Commercial Banking";

	/**
	 * US Consumer Installment Loans Business
	 */

	public static final java.lang.String US_CONSUMER_INSTALLMENT_LOANS = "US Consumer Installment Loans";

}
