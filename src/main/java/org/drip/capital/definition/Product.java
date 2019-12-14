
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
 * <i>Product</i> maintains the C<sup>1</sup> Fixings for the Product Categorical Variate. The References are:
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

public class Product
{

	/**
	 * Advisory Product
	 */

	public static final java.lang.String ADVISORY = "Advisory";

	/**
	 * BAM Product
	 */

	public static final java.lang.String BAM = "BAM";

	/**
	 * CAI Product
	 */

	public static final java.lang.String CAI = "CAI";

	/**
	 * Commodities Product
	 */

	public static final java.lang.String COMMODITIES = "Commodities";

	/**
	 * Consumer Product
	 */

	public static final java.lang.String CONSUMER = "Consumer";

	/**
	 * CorpCtr Product
	 */

	public static final java.lang.String CORPORATE_CENTER = "CorpCtr";

	/**
	 * EM ABF Product
	 */

	public static final java.lang.String EM_ABF = "EM ABF";

	/**
	 * Equities Product
	 */

	public static final java.lang.String EQUITIES = "Equities";

	/**
	 * Equity_Underwriting Product
	 */

	public static final java.lang.String EQUITY_UNDERWRITING = "Equity_Underwriting";

	/**
	 * Fixed_Income_Underwriting Product
	 */

	public static final java.lang.String FIXED_INCOME_UNDERWRITING = "Fixed_Income_Underwriting";

	/**
	 * FX_Local_Markets Product
	 */

	public static final java.lang.String FX_LOCAL_MARKETS = "FX_Local_Markets";

	/**
	 * G10_Rates Product
	 */

	public static final java.lang.String G10_RATES = "G10_Rates";

	/**
	 * G10_Risk_Treasury_RV_Finance Product
	 */

	public static final java.lang.String G10_RISK_TREASURY_RV_FINANCE = "G10_Risk_Treasury_RV_Finance";

	/**
	 * GSP Product
	 */

	public static final java.lang.String GSP = "GSP";

	/**
	 * GTS Product
	 */

	public static final java.lang.String GTS = "GTS";

	/**
	 * GWM Product
	 */

	public static final java.lang.String GWM = "GWM";

	/**
	 * Holdings_Consumer Product
	 */

	public static final java.lang.String HOLDINGS_CONSUMER = "Holdings_Consumer";

	/**
	 * Nikko Investments Product
	 */

	public static final java.lang.String NIKKO_INVESTMENTS = "Nikko Investments";

	/**
	 * OS and B Product
	 */

	public static final java.lang.String OS_B = "OS&B";

	/**
	 * Other_Global_Markets Product
	 */

	public static final java.lang.String OTHER_GLOBAL_MARKETS = "Other_Global_Markets";

	/**
	 * Prime Finance Product
	 */

	public static final java.lang.String PRIME_FINANCE = "Prime Finance";

	/**
	 * SAP Product
	 */

	public static final java.lang.String SAP = "SAP";

}
