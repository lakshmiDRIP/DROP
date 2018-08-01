
package org.drip.simm20.rates;

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
 * IRSystemics contains the Systemic Settings of the Interest Rate Risk Factors. The References are:
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

public class IRSystemics
{

	/**
	 * Interest Rate Type - Regular Volatility
	 */

	public static final java.lang.String VOLATILITY_TYPE_REGULAR = "REGULAR";

	/**
	 * Interest Rate Type - Low Volatility
	 */

	public static final java.lang.String VOLATILITY_TYPE_LOW = "LOW";

	/**
	 * Interest Rate Type - High Volatility
	 */

	public static final java.lang.String VOLATILITY_TYPE_HIGH = "HIGH";

	/**
	 * Interest Rate Type - NULL Volatility
	 */

	public static final java.lang.String VOLATILITY_TYPE_NULL = "NULL";

	/**
	 * Interest Rate Type - Trade Frequency Type Well Traded
	 */

	public static final java.lang.String TRADE_FREQUENCY_WELL_TRADED = "WELL_TRADED";

	/**
	 * Interest Rate Type - Trade Frequency Type Less Well Traded
	 */

	public static final java.lang.String TRADE_FREQUENCY_LESS_WELL_TRADED = "LESS_WELL_TRADED";

	/**
	 * Same Currency Curve Inflation Rate Risk Weight
	 */

	public static final double SINGLE_CURRENCY_CURVE_INFLATION_RISK_WEIGHT = 46.;

	/**
	 * Single Currency Single Curve Basis Swap Spread
	 */

	public static final double SINGLE_CURRENCY_CURVE_BASIS_SWAP_SPREAD_RISK_WEIGHT = 20.;

	/**
	 * Interest Rate Vega Risk Weight
	 */

	public static final double VEGA_RISK_WEIGHT = 0.21;

	/**
	 * Single Currency Cross-Curve Correlation
	 */

	public static final double SINGLE_CURRENCY_CROSS_CURVE_CORRELATION = 0.98;

	/**
	 * Single Currency Curve Inflation Correlation
	 */

	public static final double SINGLE_CURRENCY_CURVE_INFLATION_CORRELATION = 0.29;

	/**
	 * Single Currency Curve Volatility Inflation Volatility Correlation
	 */

	public static final double SINGLE_CURRENCY_CURVE_VOLATILITY_INFLATION_VOLATILITY_CORRELATION = 0.29;

	/**
	 * Single Currency Curve Basis Swap Spread Correlation
	 */

	public static final double SINGLE_CURRENCY_CURVE_BASIS_SWAP_SPREAD_CORRELATION = 0.20;

	/**
	 * Single Currency Basis Swap Spread Inflation Correlation
	 */

	public static final double SINGLE_CURRENCY_BASIS_SWAP_SPREAD_INFLATION_CORRELATION = 0.20;

	/**
	 * Cross Currency Curve Correlation
	 */

	public static final double CROSS_CURRENCY_CORRELATION = 0.23;

	/**
	 * Sub Curve OIS
	 */

	public static final java.lang.String SUB_CURVE_OIS = "OIS";

	/**
	 * Sub Curve LIBOR-1M
	 */

	public static final java.lang.String SUB_CURVE_LIBOR_1M = "LIBOR_1M";

	/**
	 * Sub Curve LIBOR-3M
	 */

	public static final java.lang.String SUB_CURVE_LIBOR_3M = "LIBOR_3M";

	/**
	 * Sub Curve LIBOR-6M
	 */

	public static final java.lang.String SUB_CURVE_LIBOR_6M = "LIBOR_6M";

	/**
	 * Sub Curve - LIBOR-12M
	 */

	public static final java.lang.String SUB_CURVE_LIBOR_12M = "LIBOR_12M";

	/**
	 * Sub Curve - PRIME
	 */

	public static final java.lang.String SUB_CURVE_PRIME = "PRIME";

	/**
	 * Sub Curve - MUNICIPAL
	 */

	public static final java.lang.String SUB_CURVE_MUNICIPAL = "MUNICIPAL";
}
