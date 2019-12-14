
package org.drip.sample.businessspec;

import java.util.Set;

import org.drip.capital.definition.Business;
import org.drip.capital.env.CapitalEstimationContextManager;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>BusinessHierarchy</i> zeds the Accounts belonging to a Business. The References are:
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
 * @author Lakshmi Krishnamurthy
 */

public class BusinessHierarchy
{

	private static final void ListAccountSet (
		final String business)
		throws Exception
	{
		Set<String> accountset = CapitalEstimationContextManager.ContextContainer().accountBusinessContext().accountSet (business);

		System.out.println (
			"\t| "+ business + " => " + accountset.size() + " | " + accountset
		);
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] businessArray =
		{
			Business.ADVISORY,
			Business.AI,
			Business.CAI,
			Business.CAPITAL_MARKETS_ORGANIZATION,
			Business.CAPITAL_MARKETS_ORIGINATION_LENDING,
			Business.CASH,
			Business.CENTRAL_AMERICA_MORTGAGES,
			Business.CITIFINANCIAL,
			Business.COMMERCIAL_REAL_ESTATE,
			Business.COMMODITIES,
			Business.COMMODITIES_HOUSTON,
			Business.CONSUMER_CARDS,
			Business.CONSUMER_OTHER,
			Business.CONVERTS,
			Business.CORPORATE_CENTER,
			Business.CREDIT_MACRO_HEDGE,
			Business.CREDIT_MARKETS,
			Business.CREDIT_TRADING,
			Business.DISTRESSED,
			Business.EM_ABF,
			Business.EM_ASSET_BACKED_FINANCE,
			Business.EM_BONDS,
			Business.EM_CREDIT_TRADING,
			Business.EM_PRIMARY_LOANS,
			Business.EQUITIES,
			Business.EQUITY_DERIVATIVES,
			Business.EQUITY_UNDERWRITING,
			Business.FIMA,
			Business.FINANCE,
			Business.G10_FX,
			Business.G10_RATES,
			Business.GLOBAL_CREDIT_MARKETS,
			Business.GLOBAL_SECURITIZED_MARKETS,
			Business.GSSG_WEST,
			Business.GTS,
			Business.GTS_HOLDINGS_TRADE,
			Business.GWM,
			Business.IG_BONDS,
			Business.IG_PRIMARY_LOANS,
			Business.INTERNATIONAL_CARDS,
			Business.INTERNATIONAL_RETAIL_BANKING,
			Business.LEVERAGED_FINANCE,
			Business.LOCAL_MARKETS,
			Business.LONG_TERM_ASSET_GROUP,
			Business.MUNICIPAL,
			Business.MUNICIPAL_SECURITIES,
			Business.MUNICIPAL_SECURITIES_CITI_COMMUNITY,
			Business.NIKKO_INVESTMENTS,
			Business.OS_B,
			Business.OTHER_BAM,
			Business.OTHER_CONSUMER,
			Business.OTHER_FI_UNDERWRITING,
			Business.OTHER_GLOBAL_MARKETS,
			Business.OTHER_SPECIAL_ASSET_POOL,
			Business.PECD,
			Business.PRIME_FINANCE,
			Business.PRIMERICA_FINANCIAL_SERVICES,
			Business.PRIVATE_BANKING,
			Business.PROJECT_FINANCE, // Start
			Business.RATES_AND_CURRENCIES,
			Business.REAL_ESTATE_LENDING,
			Business.RETAIL_AUTO_LENDING,
			Business.RETAIL_BANKING,
			Business.RETAIL_PARTNER_CARDS,
			Business.RISK_TREASURY,
			Business.RUBICON_INDIA,
			Business.SAP_ADMIN,
			Business.SECURITIZED_MARKETS,
			Business.SHORT_TERM,
			Business.SMITH_BARNEY_BAM,
			Business.STUDENT_LOANS,
			Business.US_COMMERCIAL_BANKING,
			Business.US_CONSUMER_INSTALLMENT_LOANS,
		};

		System.out.println ("\t|-----------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                           BUSINESS HIERARCHY - ACCOUNTS BELONGING TO A BUSINESS                           ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                                                            ||");

		System.out.println ("\t|                - Business                                                                                 ||");

		System.out.println ("\t|                - Account Set Count                                                                        ||");

		System.out.println ("\t|                - Account Set                                                                              ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------------------||");

		for (String business : businessArray)
		{
			ListAccountSet (business);
		}

		System.out.println ("\t|-----------------------------------------------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
