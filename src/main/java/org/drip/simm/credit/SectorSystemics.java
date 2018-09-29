
package org.drip.simm.credit;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * SectorSystemics contains the Systemic Settings that hold Sector-related Information. The References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  	Calculations, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488, eSSRN.
 *  
 *  - Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  	Framework for Forecasting Initial Margin Requirements,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279, eSSRN.
 *  
 *  - Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin Requirements
 *  	- A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167, eSSRN.
 *  
 *  - International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology,
 *  	https://www.isda.org/a/oFiDE/isda-simm-v2.pdf.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SectorSystemics
{

	/**
	 * The Sovereigns Sector
	 */

	public static final java.lang.String[] SOVEREIGNS = new java.lang.String[]
	{
		"SOVEREIGN",
		"CENTRAL_BANK"
	};

	/**
	 * The Financials Sector
	 */

	public static final java.lang.String[] FINANCIALS = new java.lang.String[]
	{
		"FINANCIAL",
		"GOVERNMENT_BACKED_FINANCIAL"
	};

	/**
	 * The Basic Materials Sector
	 */

	public static final java.lang.String[] BASIC_MATERIALS = new java.lang.String[]
	{
		"BASIC_MATERIALS",
		"ENERGY",
		"INDUSTRIALS"
	};

	/**
	 * The Consumer Sector
	 */

	public static final java.lang.String[] CONSUMER = new java.lang.String[]
	{
		"CONSUMER"
	};

	/**
	 * The Technology/Media/Telecommunications Sector
	 */

	public static final java.lang.String[] TMT = new java.lang.String[]
	{
		"TECHNOLOGY",
		"MEDIA",
		"TELECOMMUNICATIONS"
	};

	/**
	 * The Local Services Sector
	 */

	public static final java.lang.String[] LOCAL_SERVICES = new java.lang.String[]
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

	public static final java.lang.String[] RMBS_CMBS = new java.lang.String[]
	{
		"RMBS",
		"CMBS"
	};

	/**
	 * The "Residual" Sector
	 */

	public static final java.lang.String[] RESIDUAL = new java.lang.String[]
	{
		"RESIDUAL"
	};

	/**
	 * The Consumer Services Sector
	 */

	public static final java.lang.String[] CONSUMER_SERVICES = new java.lang.String[]
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

	public static final java.lang.String[] TELECOMMUNICATIONS_INDUSTRIALS = new java.lang.String[]
	{
		"TELECOMMUNICATIONS",
		"INDUSTRIALS"
	};

	/**
	 * The Heavy Industrials Sector
	 */

	public static final java.lang.String[] HEAVY_INDUSTRIALS = new java.lang.String[]
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

	public static final java.lang.String[] INVESTMENT = new java.lang.String[]
	{
		"FINANCIAL",
		"GOVERNMENT_BACKED_FINANCIAL",
		"REAL_ESTATE_ACTIVITIES",
		"TECHNOLOGY"
	};

	/**
	 * The "All" Sector
	 */

	public static final java.lang.String[] ALL = new java.lang.String[]
	{
		"ALL"
	};

	/**
	 * The Indexes/Funds/ETF's Sector
	 */

	public static final java.lang.String[] INDEX_FUND_ETF = new java.lang.String[]
	{
		"INDEX",
		"FUND",
		"ETF"
	};

	/**
	 * The Volatility Index Sector
	 */

	public static final java.lang.String[] VOLATILITY_INDEX = new java.lang.String[]
	{
		"VOLATILITY_INDEX"
	};
}
