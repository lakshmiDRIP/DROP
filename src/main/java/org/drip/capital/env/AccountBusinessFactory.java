
package org.drip.capital.env;

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
 * <i>AccountBusinessFactory</i> instantiates the Built-in Account To Business Mappings. The References are:
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

public class AccountBusinessFactory
{
	/**
	 * Instantiate the Pre-set AccountBusinessContext with the Account To Business Map Entries
	 * 
	 * @return TRUE - The AccountBusinessContext Instance
	 */

	public static org.drip.capital.shell.AccountBusinessContext Instantiate()
	{
		java.util.Map<java.lang.String, java.lang.String> accountBusinessMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.String>();

		accountBusinessMap.put (
			"AP_CASH_EQ",
			org.drip.capital.definition.Business.CASH
		);

		accountBusinessMap.put (
			"AP_COMMOD_CVA",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"AP_CONSUM",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"AP_CONVERT_BONDS",
			org.drip.capital.definition.Business.CONVERTS
			);

		accountBusinessMap.put (
			"AP_CORP_TREAS_CTI",
			org.drip.capital.definition.Business.CORPORATE_CENTER
		);

		accountBusinessMap.put (
			"AP_DVLP_EQ_CASH_TRDG",
			org.drip.capital.definition.Business.CASH
		);

		accountBusinessMap.put (
			"AP_ELECTR_EQ_CASH",
			org.drip.capital.definition.Business.CASH
		);

		accountBusinessMap.put (
			"AP_EQ_CAP_MKTS",
			org.drip.capital.definition.Business.EQUITY_UNDERWRITING
		);

		accountBusinessMap.put (
			"AP_EQ_CVA",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AP_EQ_DELTA1_ADMIN",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AP_EQ_DELTA1_CEFT",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AP_EQ_DELTA1_CLIENT_SOLUTION",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AP_EQ_DELTA1_ETF",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AP_EQ_DELTA1_INDEX",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AP_EQ_DELTA1_INVENTORY_MGMT",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AP_EQ_DELTA1_SINGLES",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AP_EQ_DERIV_ADMIN",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AP_EQ_DERIV_CORP",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AP_EQ_DERIV_EXOT",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AP_EQ_DERIV_FLOW",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AP_EQ_DERIV_FLOW_EM",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AP_EQ_FIN",
			org.drip.capital.definition.Business.PRIME_FINANCE
		);

		accountBusinessMap.put (
			"AP_FERROUS_FREIGHT",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"AP_FIN",
			org.drip.capital.definition.Business.FINANCE
		);

		accountBusinessMap.put (
			"AP_FI_EM_CR_FLOW",
			org.drip.capital.definition.Business.EM_CREDIT_TRADING
		);

		accountBusinessMap.put (
			"AP_FI_EM_CR_INDIA",
			org.drip.capital.definition.Business.EM_CREDIT_TRADING
		);

		accountBusinessMap.put (
			"AP_FI_EM_CR_LCL_CORP",
			org.drip.capital.definition.Business.EM_CREDIT_TRADING
		);

		accountBusinessMap.put (
			"AP_FI_EM_CR_STRUCT",
			org.drip.capital.definition.Business.EM_CREDIT_TRADING
		);

		accountBusinessMap.put (
			"AP_FXLM_CVA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"AP_GPM_LENDING",
			"Loan Portfolio Management"
		);

		accountBusinessMap.put (
			"AP_METALS",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"AP_OGM_CVA",
			org.drip.capital.definition.Business.OS_B
		);

		accountBusinessMap.put (
			"AP_OIL_TRDG",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"AP_PRECIOUS_METALS",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"AP_PRIVATE_BANK_CVA",
			org.drip.capital.definition.Business.GWM
		);

		accountBusinessMap.put (
			"AP_RATES_CVA",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"AP_SOFTS",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"AU_CASH_EQ",
			org.drip.capital.definition.Business.CASH
		);

		accountBusinessMap.put (
			"AU_COMMOD_CVA",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"AU_CONSUM",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"AU_CR_TRDG_SN",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"AU_EQ_DELTA1_CEFT",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AU_EQ_DELTA1_CLIENT_SOLUTION",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AU_EQ_DELTA1_ETF",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AU_EQ_DELTA1_INDEX",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AU_EQ_DELTA1_INVENTORY_MGMT",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AU_EQ_DELTA1_SINGLES",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AU_EQ_DERIV_WARRANTS",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"AU_EQ_FIN",
			org.drip.capital.definition.Business.PRIME_FINANCE
		);

		accountBusinessMap.put (
			"AU_FIN",
			org.drip.capital.definition.Business.FINANCE
		);

		accountBusinessMap.put (
			"AU_MARKETS_TREAS",
			org.drip.capital.definition.Business.RISK_TREASURY
		);

		accountBusinessMap.put (
			"AU_PRIVATE_BANK_CVA",
			org.drip.capital.definition.Business.GWM
		);

		accountBusinessMap.put (
			"AU_RATES_AUD_SWAPS",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"AU_RATES_BASIS",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"AU_RATES_CVA",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"AU_RATES_GOVT",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"AU_RATES_INFLATION",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"AU_RATES_MGMT_TRDG",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"AU_RATES_NZD",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"AU_RATES_VOL",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"AU_RATES_XGAMMA",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"AU_SPOT",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"AU_STIRT",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"BR_CASH_EQ",
			org.drip.capital.definition.Business.CASH
		);

		accountBusinessMap.put (
			"BR_COMMOD_ADMIN",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"CA_G10_MARKETS_TREAS",
			org.drip.capital.definition.Business.RISK_TREASURY
		);

		accountBusinessMap.put (
			"CA_SWAPS",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"CCA_CVCI",
			org.drip.capital.definition.Business.CAI
		);

		accountBusinessMap.put (
			"CCA_FX",
			org.drip.capital.definition.Business.CAI
		);

		accountBusinessMap.put (
			"CE_COMMOD_CVA",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"CE_FI_EM_CR_FLOW",
			org.drip.capital.definition.Business.EM_CREDIT_TRADING
		);

		accountBusinessMap.put (
			"CE_FI_EM_CR_STRUCT",
			org.drip.capital.definition.Business.EM_CREDIT_TRADING
		);

		accountBusinessMap.put (
			"CE_FXLM_CVA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"CE_PRIVATE_BANK_CVA",
			org.drip.capital.definition.Business.GWM
		);

		accountBusinessMap.put (
			"CFD_FXLM_CVA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"CFD_MUNI_CVA",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"CFD_RATES_CVA",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"CH_AI_CAI_SAP",
			org.drip.capital.definition.Business.AI
		);

		accountBusinessMap.put (
			"CH_AI_CITI_PRPTY_INV",
			org.drip.capital.definition.Business.AI
		);

		accountBusinessMap.put (
			"CH_EU_GPM",
			"Excluded - CLP"
		);

		accountBusinessMap.put (
			"CH_GLBL_CR_TRDG_SN",
			org.drip.capital.definition.Business.CREDIT_MARKETS
		);

		accountBusinessMap.put (
			"CH_GLBL_DIST_FLOW_TRDG",
			org.drip.capital.definition.Business.CREDIT_MARKETS
		);


		accountBusinessMap.put (
			"CH_GLBL_STRUC_CR_CDO",
			org.drip.capital.definition.Business.CREDIT_MARKETS
		);

		accountBusinessMap.put (
			"CH_GLBL_STRUC_CR_CORR",
			org.drip.capital.definition.Business.CREDIT_MARKETS
		);

		accountBusinessMap.put (
			"CH_GLBL_STRUC_CR_EXOTICS",
			org.drip.capital.definition.Business.CREDIT_MARKETS
		);

		accountBusinessMap.put (
			"CH_GSM_CONSUM_FIN",
			org.drip.capital.definition.Business.GLOBAL_SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"CH_GSM_RESID_MTG",
			org.drip.capital.definition.Business.GLOBAL_SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"CH_JP_CR_DERIV_STRUC",
			org.drip.capital.definition.Business.CREDIT_MARKETS
		);

		accountBusinessMap.put (
			"CH_JP_CR_TRDG_CVA",
			org.drip.capital.definition.Business.CREDIT_MARKETS
		);

		accountBusinessMap.put (
			"CH_LCL_RES_CMI",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"CH_LCL_RETAIL_BKG_LA",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"CH_LTAG_SUBPRIME_WRKG_GRP_RBC",
			org.drip.capital.definition.Business.LONG_TERM_ASSET_GROUP
		);

		accountBusinessMap.put (
			"CH_MUNI_CVA",
			org.drip.capital.definition.Business.MUNICIPAL_SECURITIES
		);

		accountBusinessMap.put (
			"CH_MUNI_FP",
			org.drip.capital.definition.Business.MUNICIPAL_SECURITIES
		);

		accountBusinessMap.put (
			"CH_MUNI_FVA",
			"Excluded - FVA"
		);

		accountBusinessMap.put (
			"CH_MUNI_ST",
			org.drip.capital.definition.Business.MUNICIPAL_SECURITIES
		);

		accountBusinessMap.put (
			"CH_NA_CR_TRDG_CVA",
			org.drip.capital.definition.Business.CREDIT_MARKETS
		);

		accountBusinessMap.put (
			"CH_NA_LTAG_SPG_CVA",
			org.drip.capital.definition.Business.LONG_TERM_ASSET_GROUP
		);

		accountBusinessMap.put (
			"CH_PECD_NAM_PRORATA",
			org.drip.capital.definition.Business.CREDIT_MARKETS
		);

		accountBusinessMap.put (
			"CH_RATES",
			org.drip.capital.definition.Business.RATES_AND_CURRENCIES
		);

		accountBusinessMap.put (
			"CH_SAP_CREDIT_RMH",
			org.drip.capital.definition.Business.CREDIT_MARKETS
		);

		accountBusinessMap.put (
			"CH_SAP_EQ_FIN",
			org.drip.capital.definition.Business.PRIME_FINANCE
		);

		accountBusinessMap.put (
			"CITI_PRIVATE_BANK_HK",
			org.drip.capital.definition.Business.GWM
		);

		accountBusinessMap.put (
			"CITI_PRIVATE_BANK_JERSEY",
			org.drip.capital.definition.Business.GWM
		);

		accountBusinessMap.put (
			"CITI_PRIVATE_BANK_LONDON",
			org.drip.capital.definition.Business.GWM
		);

		accountBusinessMap.put (
			"CITI_PRIVATE_BANK_SG",
			org.drip.capital.definition.Business.GWM
		);

		accountBusinessMap.put (
			"CITI_PRIVATE_BANK_ZURICH",
			org.drip.capital.definition.Business.GWM
		);

		accountBusinessMap.put (
			"CN_CONSUM_GCB_ASIA",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"CN_RATES_TRDG",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"COMMOD_AG_SOFTS_MX",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"COMMOD_DERIV_ADMIN",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"COMMOD_DERIV_METALS_MX",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"COMMOD_EXOTICS",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"COMMOD_FVO_DVA",
			"Excluded - CVA"
		);

		accountBusinessMap.put (
			"COMMOD_METALS_NON_REPORTABLE",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"CONSUM_BANAMEX",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"CONSUM_BKG_RES_CMI",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"CONSUM_EMEA_PL",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"CONSUM_MX_CARDS",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"CRI_CONSUM",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"EL_CONSUM",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"EMEA_RATES_CVA",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"ENERGY_MARKET_ACCESS",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"EQTY_FVO_DVA",
			"Excluded - CVA"
		);

		accountBusinessMap.put (
			"EQ_DERIV_CIS",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"EQ_DERIV_HYBRIDS_AND_FUNDS",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"EU_CASH_EQ",
			org.drip.capital.definition.Business.CASH
		);

		accountBusinessMap.put (
			"EU_CMO_IG_BONDS",
			org.drip.capital.definition.Business.OTHER_FI_UNDERWRITING
		);

		accountBusinessMap.put (
			"EU_CMO_LEV_FIN_HY_BONDS",
			org.drip.capital.definition.Business.OTHER_FI_UNDERWRITING
		);

		accountBusinessMap.put (
			"EU_COMMOD_CVA",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"EU_COMMOD_DERIV_PACKAGING",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"EU_CONVERT_BONDS",
			org.drip.capital.definition.Business.CONVERTS
		);

		accountBusinessMap.put (
			"EU_CORP_TREAS_CTI_FX_INTL",
			org.drip.capital.definition.Business.CORPORATE_CENTER
		);

		accountBusinessMap.put (
			"EU_CR_BETA_TRDG",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"EU_CR_IG_TRG",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"EU_CR_INDEX_TRDG",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"EU_CR_REPACK_EXOTICS",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"EU_CR_RMH_HUB_ACCT",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"EU_EQ_ADMIN",
			org.drip.capital.definition.Business.CASH
		);

		accountBusinessMap.put (
			"EU_EQ_CAP_MKTS",
			org.drip.capital.definition.Business.EQUITY_UNDERWRITING
		);

		accountBusinessMap.put (
			"EU_EQ_CVA",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"EU_EQ_DELTA1_ADMIN",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"EU_EQ_DELTA1_CEFT",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"EU_EQ_DELTA1_CLIENT_SOLUTION",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"EU_EQ_DELTA1_ETF",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"EU_EQ_DELTA1_FWD_TRDG",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"EU_EQ_DELTA1_INDEX",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"EU_EQ_DELTA1_SECTOR_SWAPS",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"EU_EQ_DELTA1_SINGLES",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"EU_EQ_DERIV_ADMIN",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"EU_EQ_DERIV_CORP",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"EU_EQ_DERIV_EXOT",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"EU_EQ_DERIV_FLOW",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"EU_EQ_DERIV_RV_FLOW",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"EU_EQ_DERIV_WARRANTS",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"EU_FIN",
			org.drip.capital.definition.Business.FINANCE
		);

		accountBusinessMap.put (
			"EU_FXLM_CVA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"EU_FX_COMM_SALES",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"EU_FX_MODEL",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"EU_FX_SALES_PB",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"EU_FX_TRD_E_TRDG",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"EU_GPM_LENDING",
			"Loan Portfolio Management"
		);

		accountBusinessMap.put (
			"EU_GSM_CVA",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"EU_OGM_CVA",
			org.drip.capital.definition.Business.OS_B
		);

		accountBusinessMap.put (
			"EU_OIL_TRDG",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"EU_OSB_NON_TREAS_OTH_ICG",
			org.drip.capital.definition.Business.OS_B
		);

		accountBusinessMap.put (
			"EU_POWER_DERIV_GAS",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"EU_POWER_DERIV_PWR",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"EU_POWER_EMISSION_MAG",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"EU_PRIVATE_BANK_CVA",
			org.drip.capital.definition.Business.GWM
		);

		accountBusinessMap.put (
			"EU_RATES_LINEAR_GOVT_SAS",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"EU_RATES_LINEAR_SWAPS",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"EU_RATES_NON_LINEAR_CORE",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"EU_RATES_NON_LINEAR_FUND",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"EU_RATES_NON_LINEAR_INFL",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"EU_RATES_NON_LINEAR_SOL",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"EU_SPOT",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"EU_STIRT",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"EU_STRUC_CR_TRS",
			org.drip.capital.definition.Business.PECD
		);

		accountBusinessMap.put (
			"EU_ST_FI",
			org.drip.capital.definition.Business.SHORT_TERM
		);

		accountBusinessMap.put (
			"FERROUS_FREIGHT",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"FRANK_G10_MARKETS_TREAS",
			org.drip.capital.definition.Business.RISK_TREASURY
		);

		accountBusinessMap.put (
			"FVO_CVA",
			"Excluded - CVA"
		);

		accountBusinessMap.put (
			"FVO_DEBT_ACCTS",
			"Excluded - CVA"
		);

		accountBusinessMap.put (
			"FXLM_FVO_DVA",
			"Excluded - CVA"
		);

		accountBusinessMap.put (
			"GLBL_CR_CNTR_PRTY_RM",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"GLBL_CR_CVA_EMCT",
			org.drip.capital.definition.Business.EM_CREDIT_TRADING
		);

		accountBusinessMap.put (
			"GLBL_CR_CVA_PECD",
			org.drip.capital.definition.Business.PECD
		);

		accountBusinessMap.put (
			"GLBL_CR_CVA_TRS",
			org.drip.capital.definition.Business.PECD
		);

		accountBusinessMap.put (
			"GLBL_CR_TRDG_CVA",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"GLBL_EQ_ADM_3RD_PARTY_INV",
			org.drip.capital.definition.Business.CASH
		);

		accountBusinessMap.put (
			"GLBL_STRUC_CR_CORR",
			org.drip.capital.definition.Business.PECD
		);

		accountBusinessMap.put (
			"GLOBAL_FX_OPT_MAG",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"GLOBAL_FX_OPT_TRDG",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"GSM_AGNCY_ADMIN",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_AGNCY_ARMS",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_AGNCY_CMOS",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_AGNCY_FI_RETAIL",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_AGNCY_LONG_TERM_CMOS",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_AGNCY_SPEC_POOLS",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_AGNCY_STRIPS",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_CMBS_TRDG",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_EU_ABS",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_EU_SECURITIZ",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_NON_AGNCY_ADMIN",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_NON_AGNCY_CMBS",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_NON_AGNCY_RMBS_TRDG",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_NON_AGNCY_SUBS",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_PECD_ABS_CDO_SEC_TRDG",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_RESID_LOANS",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_RESID_MTG_FIN",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_RESID_OTHER",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSP_ADMIN",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"HK_CONSUM",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"HK_MARKETS_TREAS",
			org.drip.capital.definition.Business.RISK_TREASURY
		);

		accountBusinessMap.put (
			"HK_RATES_TRDG",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"HYBD_FVO_DVA",
			"Excluded - CVA"
		);

		accountBusinessMap.put (
			"ID_CONSUM",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"INVESTMENT_FINANCE_GLBL",
			org.drip.capital.definition.Business.GWM
		);

		accountBusinessMap.put (
			"IN_CONSUM",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"IN_CONSUM_GCB_ASIA",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"IN_RATES_TRDG",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"JAPAN_NON-YEN_RATES",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"JAPAN_RATES_YEN_SOLUTION",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"JERSEY_G10_MARKETS_TREAS",
			org.drip.capital.definition.Business.RISK_TREASURY
		);

		accountBusinessMap.put (
			"JGB_CASH",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"JP_CASH_EQ",
			org.drip.capital.definition.Business.CASH
		);

		accountBusinessMap.put (
			"JP_COMMOD_CVA",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"JP_CR_REPACK_EXOTICS",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"JP_CR_TRDG_SEC",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"JP_EQ_DELTA1_CLIENT_SOLUTION",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"JP_EQ_DELTA1_ETF",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"JP_EQ_DELTA1_INDEX",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"JP_EQ_DELTA1_INVENTORY_MGMT",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"JP_EQ_DELTA1_SECTOR_SWAPS",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"JP_EQ_DELTA1_SINGLES",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"JP_EQ_FIN",
			org.drip.capital.definition.Business.PRIME_FINANCE
		);

		accountBusinessMap.put (
			"JP_EQ_PROG_AGNCY_ERROR",
			org.drip.capital.definition.Business.CASH
		);

		accountBusinessMap.put (
			"JP_EQ_PROG_TRDG",
			org.drip.capital.definition.Business.CASH
		);

		accountBusinessMap.put (
			"JP_FIN",
			org.drip.capital.definition.Business.FINANCE
		);

		accountBusinessMap.put (
			"JP_FI_FUND",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"JP_FI_MGMT",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"JP_FXLM_CVA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"JP_FX_CJL_COMM_SALES",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"JP_FX_SALES",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"JP_FX_TRDG_OTHER",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"JP_G10_MARKETS_TREAS",
			org.drip.capital.definition.Business.RISK_TREASURY
		);

		accountBusinessMap.put (
			"JP_RATES_CVA",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"JP_SAS",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"JP_SWAP",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"KR_CONSUM",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"KR_GTS",
			org.drip.capital.definition.Business.GTS
		);

		accountBusinessMap.put (
			"KR_RATES_TRDG",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"LA_CASH_EQ",
			org.drip.capital.definition.Business.CASH
		);

		accountBusinessMap.put (
			"LA_COMMOD_CVA",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"LA_EQ_CVA",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"LA_EQ_DELTA1_INDEX_EM_SWAP",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"LA_EQ_DELTA1_INVENTORY_MGMT",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"LA_EQ_DELTA1_SECTOR_SWAPS",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"LA_EQ_DERIV_ADMIN",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"LA_EQ_DERIV_CORP",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"LA_EQ_DERIV_EXOT",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"LA_EQ_DERIV_FLOW",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"LA_FI_EM_CR_FLOW",
			org.drip.capital.definition.Business.EM_CREDIT_TRADING
		);

		accountBusinessMap.put (
			"LA_FI_EM_CR_STRUCT",
			org.drip.capital.definition.Business.EM_CREDIT_TRADING
		);

		accountBusinessMap.put (
			"LA_FI_EM_LOCAL_PLATFORM",
			org.drip.capital.definition.Business.EM_CREDIT_TRADING
		);

		accountBusinessMap.put (
			"LA_FXLM_CVA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LA_GPM_LENDING",
			"Loan Portfolio Management"
		);

		accountBusinessMap.put (
			"LA_GSM_CVA",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"LA_OGM_CVA",
			org.drip.capital.definition.Business.OS_B
		);

		accountBusinessMap.put (
			"LA_PRIVATE_BANK_CVA",
			org.drip.capital.definition.Business.GWM
		);

		accountBusinessMap.put (
			"LA_RATES_CVA",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"LDN_EQ_FIN",
			org.drip.capital.definition.Business.PRIME_FINANCE
		);

		accountBusinessMap.put (
			"LDN_G10_MARKETS_TREAS_ICG",
			org.drip.capital.definition.Business.RISK_TREASURY
		);

		accountBusinessMap.put (
			"LM_ST_ALGERIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_ARGENTINA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_BAHAMAS",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_BAHRAIN",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_BARBADOS",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_BD_FX_TRADING",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_BRAZIL",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_BULGARIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_CAMEROON",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_CN_DEBT_INT_RATE_DR",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_CN_FX_ND_FWDS_TRDG",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_CN_FX_OPT_TRDG",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_CN_FX_SALES",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_COLOMBIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_CONGO",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_COSTA_RICA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_CZECH",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_DOMINICAN_REP",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_ECUADOR",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_EGYPT",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_EL_SALVADOR",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_GABON",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_GUATEMALA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_HAITI",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_HK_DEBT_INT_RATE_DR",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_HK_DERIV_TRADING",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_HK_FX_TRADING",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_HONDURAS",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_HUNGARY",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_ID_DEBT_INT_RATE_DR",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_ID_FX_ND_FWDS_TRDG",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_IN_DERIV_TRADING",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_IN_FX_TRADING",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_IN_TRDG_DEBT_TRAD",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_ISRAEL",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_IVORY_COAST",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_JAMAICA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_JORDAN",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_KAZAKHSTAN",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_KENYA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_KR_DEBT_INT_RATE_DR",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_KR_FX_ND_FWDS_TRDG",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_KR_FX_OPT_TRDG",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_KR_MANAGEMENT",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_KR_STRUC_RATES",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_KUWAIT",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_LDN_EM_HUB",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_LEBANON",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_LK_FX_TRADING",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_LK_TRDG_DEBT_TRAD",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_MOROCCO",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_MX",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_MY_DEBT_INT_RATE_DR",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_MY_FX_ND_FWDS_TRDG",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_MY_FX_OPT_TRDG",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_MY_STRUC_RATES",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_NIGERIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_NY_EM_HUB",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_PAKISTAN",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_PANAMA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_PARAGUAY",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_PERU",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_PH_DEBT_INT_RATES_DR",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_PH_FX_ND_FWDS_TRDG",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_POLAND",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_PUERTO_RICO",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_QATAR",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_ROMANIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_RUSSIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_SENEGAL",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_SG_DEBT_INT_RATE_DR",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_SG_DERIV_TRADING",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_SG_FX_ND_FWDS_TRDG",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_SG_FX_SALES",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_SG_FX_TRADING",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_SG_LCL_MKTS_TRDG",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_SG_STRUC_RATES",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_SLOVAKIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_SOUTH_AFRICA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_TANZANIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_TH_DEBT_INT_RATE_DR",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_TH_FX_ND_FWDS_TRDG",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_TH_TRDG_DEBT_TRAD",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_TRINIDAD",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_TUNISIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_TURKEY",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_TW_DEBT_INT_RATE_DR",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_TW_FX_ND_FWDS_TRDG",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_TW_FX_OPT_TRDG",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_UAE",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_UGANDA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_UKRAINE",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_URUGUAY",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_VENEZUELA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_VN_FX_TRADING",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_ST_ZAMBIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_ALGERIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_ARGENTINA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_BAHAMAS",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_BAHRAIN",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_BARBADOS",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_BRAZIL",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_BULGARIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_CAMEROON",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_COLOMBIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_CONGO",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_COSTA_RICA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_CZECH",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_DOMINICAN_REP",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_ECUADOR",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_EGYPT",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_EL_SALVADOR",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_GUATEMALA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_HONDURAS",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_HUNGARY",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_ISRAEL",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_IVORY_COAST",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_JAMAICA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_JORDAN",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_KAZAKHSTAN",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_KENYA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_KUWAIT",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_LDN_EM_HUB",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_LEBANON",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_MOROCCO",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_MX",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_NIGERIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_PAKISTAN",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_PANAMA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_PARAGUAY",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_PERU",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_POLAND",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_PUERTO_RICO",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_QATAR",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_ROMANIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_RUSSIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_SENEGAL",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_SLOVAKIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_SOUTH_AFRICA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_TANZANIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_TRINIDAD",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_TUNISIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_TURKEY",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_UAE",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_UGANDA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_UKRAINE",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_URUGUAY",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_VENEZUELA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TREAS_ZAMBIA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_BD_LM_TREASURY",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_BD_LM_VR_EX",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_CN_FI_LCL_MKTS",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_CN_LM_VR_EX",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_HK_FI_LCL_MKTS",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_HK_LM_TREASURY",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_HK_LM_VR_EX",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_ID_FI_LCL_MKTS",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_ID_LM_VR_EX",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_IN_FI_LCL_MKTS",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_IN_LM_VR_EX",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_KR_FI_LCL_MKTS",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_KR_LM_TREASURY",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_KR_LM_VR_EX",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_LK_LM_VR_EX",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_MY_FI_LCL_MKTS",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_MY_LM_TREASURY",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_MY_LM_VR_EX",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_PH_FI_LCL_MKTS",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_PH_LM_VR_EX",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_SG_FI_LCL_MKTS",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_SG_LM_VR_EX",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_TH_FI_LCL_MKTS",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_TH_LM_VR_EX",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_TW_FI_LCL_MKTS",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_TW_LM_VR_EX",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LM_TR_VN_LM_VR_EX",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"LPM_BANKING_FVO_LOAN",
			"Excluded - CVA"
		);

		accountBusinessMap.put (
			"LPM_BANKING_HDG",
			"Loan Portfolio Management"
		);

		accountBusinessMap.put (
			"LPM_BANKING_HDG_TRANCHE",
			"Loan Portfolio Management"
		);

		accountBusinessMap.put (
			"LPM_TRD",
			"Loan Portfolio Management"
		);

		accountBusinessMap.put (
			"METALS",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"MUNI_BOND_TAXABLE",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_CCC_AGENCY_BONDS",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_CCC_INVEST_GRD",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_CCC_OTH_REAL_EST",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_CCC_REAL_ESTATE_4P",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_CCC_REAL_ESTATE_9P",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_CCC_REAL_EST_SECUR",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_CCC_VAR_DEBT",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_CITI_TOB",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_CRA_DIRECT_FUNDING",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_CRA_INVEST",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_CR_OTH_PROD",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_CUST_DERIV_TRDG",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_CVA",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_FVA",
			"Excluded - FVA"
		);

		accountBusinessMap.put (
			"MUNI_INST_REG_BAB",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_INST_REG_HG",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_INST_REG_HY",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_INST_REG_LONG_HG",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_ISSUER_DERIV_TRDG",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_MODEL_HEDGE",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_NY_SECNDRY_TRDG",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_QUANT_CASH_TRDG",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_REGNL_TRDG",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_REINVEST",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_RV_TRDG",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_STRUCT_PROD",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_ST_NOTES",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_TAX_EXMPT_ST",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MUNI_ZERO_COUPON",
			org.drip.capital.definition.Business.MUNICIPAL
		);

		accountBusinessMap.put (
			"MX_CORP_ITEMS",
			org.drip.capital.definition.Business.CORPORATE_CENTER
		);

		accountBusinessMap.put (
			"MX_EQ_CASH",
			org.drip.capital.definition.Business.CASH
		);

		accountBusinessMap.put (
			"MX_POWER_DERIV",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"MY_CONSUM",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"MY_RATES_TRDG",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"NA_COMMOD_CVA",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"NA_EQ_ADM_PRIN_STRAT_LEG",
			org.drip.capital.definition.Business.CASH
		);

		accountBusinessMap.put (
			"NA_EQ_CVA",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"NA_EQ_DELTA1_ADMIN",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"NA_FXLM_CVA",
			org.drip.capital.definition.Business.LOCAL_MARKETS
		);

		accountBusinessMap.put (
			"NA_GSM_CVA",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"NA_OGM_CVA",
			org.drip.capital.definition.Business.OS_B
		);

		accountBusinessMap.put (
			"NA_PRIVATE_BANK_CVA",
			org.drip.capital.definition.Business.GWM
		);

		accountBusinessMap.put (
			"NA_RATES_CORP_STRUCT",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"NA_RATES_CVA",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"NA_RATES_FLOW_VOL",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"NA_RATES_FUNDING",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"NA_RATES_G10_SOL",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"NA_RATES_GAMMA_SHRT_VOL_TRD",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"NZ_MARKETS_TREAS",
			org.drip.capital.definition.Business.RISK_TREASURY
		);

		accountBusinessMap.put (
			"OCM_FVO_DVA",
			"Excluded - CVA"
		);

		accountBusinessMap.put (
			"OFF_THE_RUN_BOND_TRD",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"OGM_COMMOD_FVA",
			"Excluded - FVA"
		);

		accountBusinessMap.put (
			"OGM_FXLM_FVA",
			"Excluded - FVA"
		);

		accountBusinessMap.put (
			"OGM_GLBL_CRDT_FVA",
			"Excluded - FVA"
		);

		accountBusinessMap.put (
			"OGM_GSM_FVA",
			"Excluded - FVA"
		);

		accountBusinessMap.put (
			"OGM_MUNI_FVA",
			"Excluded - FVA"
		);

		accountBusinessMap.put (
			"OGM_RATES_FVA",
			"Excluded - FVA"
		);

		accountBusinessMap.put (
			"OICG_EUR_FIRM_FINANCE",
			org.drip.capital.definition.Business.OS_B
		);

		accountBusinessMap.put (
			"OICG_NAM_FIRM_FINANCE",
			org.drip.capital.definition.Business.OS_B
		);

		accountBusinessMap.put (
			"OTHER_CORP_ITEMS",
			org.drip.capital.definition.Business.CORPORATE_CENTER
		);

		accountBusinessMap.put (
			"OTHER_ICG_TREAS_OTH_CORE",
			org.drip.capital.definition.Business.CORPORATE_CENTER
		);

		accountBusinessMap.put (
			"PF_BROKERAGE_EMEA",
			org.drip.capital.definition.Business.PRIME_FINANCE
		);

		accountBusinessMap.put (
			"PF_BROKERAGE_NA",
			org.drip.capital.definition.Business.PRIME_FINANCE
		);

		accountBusinessMap.put (
			"PH_CONSUM",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"PH_RATES_TRDG",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"PIPELINE_LOANS",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"PRECIOUS_METALS",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"PRIVATE_BANK_FVA",
			"Excluded - FVA"
		);

		accountBusinessMap.put (
			"RATES_FVO_CVA",
			"Excluded - CVA"
		);

		accountBusinessMap.put (
			"SG_CONSUM",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"SG_CONSUM_GCB",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"SG_FX_SALES_PB",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"SG_MARKETS_TREAS",
			org.drip.capital.definition.Business.RISK_TREASURY
		);

		accountBusinessMap.put (
			"SG_RATES_TRDG",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"SG_SPOT",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"SG_STIRT",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"SOFTS_AGRICULTURE",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"THERMAL_COAL",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"TH_CONSUM",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"TH_RATES_TRDG",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"TTS_TRADE",
			"TTS"
		);

		accountBusinessMap.put (
			"TW_CONSUM",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"TW_RATES_TRDG",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"UK_FX_MGMT",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"US_CASH_EQ",
			org.drip.capital.definition.Business.CASH
		);

		accountBusinessMap.put (
			"US_CDO_CRP_SEC",
			org.drip.capital.definition.Business.PECD
		);

		accountBusinessMap.put (
			"US_CMO_BANK_FIN",
			org.drip.capital.definition.Business.OTHER_FI_UNDERWRITING
		);

		accountBusinessMap.put (
			"US_CMO_BANK_FIN_TAP",
			org.drip.capital.definition.Business.OTHER_FI_UNDERWRITING
		);

		accountBusinessMap.put (
			"US_CMO_IG_BONDS",
			org.drip.capital.definition.Business.OTHER_FI_UNDERWRITING
		);

		accountBusinessMap.put (
			"US_CONVERT_BONDS",
			org.drip.capital.definition.Business.CONVERTS
		);

		accountBusinessMap.put (
			"US_CORP_TREAS_CTI_CREDIT",
			org.drip.capital.definition.Business.CORPORATE_CENTER
		);

		accountBusinessMap.put (
			"US_CORP_TREAS_CTI_FX_INTL",
			org.drip.capital.definition.Business.CORPORATE_CENTER
		);

		accountBusinessMap.put (
			"US_CORP_TREAS_CTI_MTG",
			org.drip.capital.definition.Business.CORPORATE_CENTER
		);

		accountBusinessMap.put (
			"US_CORP_TREAS_CTI_RATES",
			org.drip.capital.definition.Business.CORPORATE_CENTER
		);

		accountBusinessMap.put (
			"US_CR_BETA_TRDG",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"US_CR_IG_TRDG",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"US_CR_INDEX_TRDG",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"US_CR_RMH_HUB_ACCT",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"US_ELECTR_EQ_CASH",
			org.drip.capital.definition.Business.CASH
		);

		accountBusinessMap.put (
			"US_EQ_DELTA1_CLIENT_SOLUTION",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"US_EQ_DELTA1_ETF",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"US_EQ_DELTA1_INDEX",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"US_EQ_DELTA1_INVENTORY_MGMT",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"US_EQ_DELTA1_SECTOR_SWAPS",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"US_EQ_DELTA1_SINGLES",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"US_EQ_DERIV_ADMIN",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"US_EQ_DERIV_CORP",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"US_EQ_DERIV_ELECT_EXEC_ROUT",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"US_EQ_DERIV_EXOT",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"US_EQ_DERIV_FLOW",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"US_EQ_DERIV_MKT_MKG",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"US_EQ_FIN",
			org.drip.capital.definition.Business.PRIME_FINANCE
		);

		accountBusinessMap.put (
			"US_FIN",
			org.drip.capital.definition.Business.FINANCE
		);

		accountBusinessMap.put (
			"US_FX_COMM_SALES",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"US_FX_MGMT",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"US_FX_OTHER_TRDG",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"US_FX_SALES_PB",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"US_G10_MARKETS_TREAS_RATE",
			org.drip.capital.definition.Business.RISK_TREASURY
		);

		accountBusinessMap.put (
			"US_GSP_RMH",
			"Credit Macro Hedge"
		);

		accountBusinessMap.put (
			"US_INFLATION",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"US_OIL_TRDG",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"US_OSB_NON_TREAS_OTH_ICG",
			org.drip.capital.definition.Business.OS_B
		);

		accountBusinessMap.put (
			"US_POWER_DERIV_EAST",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"US_POWER_DERIV_EMIS",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"US_POWER_DERIV_ERCOT",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"US_POWER_DERIV_NYMEX",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"US_POWER_DERIV_WEST",
			org.drip.capital.definition.Business.COMMODITIES_HOUSTON
		);

		accountBusinessMap.put (
			"US_RATES_AGENCIES",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"US_RATES_AGN_PASS_THRU",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"US_RATES_GOV_AGENCY_RETAIL",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"US_RATES_TRD_GOV",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"US_SECUR_SRVC_OTC_CLEAR",
			"Securities Service"
		);

		accountBusinessMap.put (
			"US_SPOT",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"US_STIRT",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"US_STRUC_CLO_PRIM",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"US_STRUC_CR_TRS",
			org.drip.capital.definition.Business.PECD
		);

		accountBusinessMap.put (
			"US_ST_FI_TAX",
			"Short Term"
		);

		accountBusinessMap.put (
			"US_SWAPS",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"VN_CONSUM",
			org.drip.capital.definition.Business.RETAIL_BANKING
		);

		accountBusinessMap.put (
			"VN_RATES_TRDG",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"YEN_DERIV_EXO",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"YEN_DERIV_FL_VOL",
			org.drip.capital.definition.Business.G10_RATES
		);

		accountBusinessMap.put (
			"EU_CR_DIST_TRDG",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"EU_CR_HY_LOAN_TRDG",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"EU_FX_TRDG_OTHER",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"GSM_CONSUM_FIN",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_CONSUM_FIN_ABS_TRDG",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"GSM_EU_RESIDENTIAL",
			org.drip.capital.definition.Business.SECURITIZED_MARKETS
		);

		accountBusinessMap.put (
			"JP_FX_CJL_INV_SALES",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"US_CR_DIST_TRDG",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"US_CR_HY_LOAN_TRDG",
			org.drip.capital.definition.Business.CREDIT_TRADING
		);

		accountBusinessMap.put (
			"US_EQ_DELTA1_CEFT",
			org.drip.capital.definition.Business.EQUITY_DERIVATIVES
		);

		accountBusinessMap.put (
			"US_FX_TRD_E_TRDG",
			org.drip.capital.definition.Business.G10_FX
		);

		accountBusinessMap.put (
			"US_GPM_LENDING",
			"Loan Portfolio Management"
		);

		try
		{
			return new org.drip.capital.shell.AccountBusinessContext (accountBusinessMap);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
