
package org.drip.analytics.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * LatentStateStatic contains the Analytics Latent STate Static/Textual Identifiers.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateStatic {

	/**
	 * Forward Latent State
	 */

	public static final java.lang.String LATENT_STATE_FORWARD = "LATENT_STATE_FORWARD";

	/**
	 * Forward Latent State Quantification Metric - Forward Rate
	 */

	public static final java.lang.String FORWARD_QM_FORWARD_RATE = "FORWARD_QM_FORWARD_RATE";

	/**
	 * Forward Latent State Quantification Metric - LIBOR Rate
	 */

	public static final java.lang.String FORWARD_QM_LIBOR_RATE = "FORWARD_QM_LIBOR_RATE";

	/**
	 * Forward Latent State Quantification Metric - Shifted Forward Rate
	 */

	public static final java.lang.String FORWARD_QM_SHIFTED_FORWARD_RATE = "FORWARD_QM_SHIFTED_FORWARD_RATE";

	/**
	 * Forward Latent State Quantification Metric - Instantaneous Forward Rate
	 */

	public static final java.lang.String FORWARD_QM_INSTANTANEOUS_FORWARD_RATE =
		"FORWARD_QM_INSTANTANEOUS_FORWARD_RATE";

	/**
	 * Forward Latent State Quantification Metric - Continuously Compounded Forward Rate
	 */

	public static final java.lang.String FORWARD_QM_CONTINUOUSLY_COMPOUNDED_FORWARD_RATE =
		"FORWARD_QM_CONTINUOUSLY_COMPOUNDED_FORWARD_RATE";

	/**
	 * Forward Latent State Quantification Metric - Instantaneous Effective Annual Forward Rate
	 */

	public static final java.lang.String FORWARD_QM_INSTANTANEOUS_EFFECTIVE_FORWARD_RATE =
		"FORWARD_QM_EFFECTIVE_ANNUAL_FORWARD_RATE";

	/**
	 * Forward Latent State Quantification Metric - Instantaneous Nominal Annual Forward Rate
	 */

	public static final java.lang.String FORWARD_QM_INSTANTANEOUS_NOMINAL_FORWARD_RATE =
		"FORWARD_QM_NOMINAL_ANNUAL_FORWARD_RATE";

	/**
	 * Funding Latent State
	 */

	public static final java.lang.String LATENT_STATE_FUNDING = "LATENT_STATE_FUNDING";

	/**
	 * Discount Latent State Quantification Metric - Discount Factor
	 */

	public static final java.lang.String DISCOUNT_QM_DISCOUNT_FACTOR = "DISCOUNT_QM_DISCOUNT_FACTOR";

	/**
	 * Discount Latent State Quantification Metric - Zero Rate
	 */

	public static final java.lang.String DISCOUNT_QM_ZERO_RATE = "DISCOUNT_QM_ZERO_RATE";

	/**
	 * Discount Latent State Quantification Metric - Compounded Short Rate
	 */

	public static final java.lang.String DISCOUNT_QM_COMPOUNDED_SHORT_RATE =
		"DISCOUNT_QM_COMPOUNDED_SHORT_RATE";

	/**
	 * Discount Latent State Quantification Metric - Forward Rate
	 */

	public static final java.lang.String DISCOUNT_QM_FORWARD_RATE = "DISCOUNT_QM_FORWARD_RATE";

	/**
	 * Govvie Latent State
	 */

	public static final java.lang.String LATENT_STATE_GOVVIE = "LATENT_STATE_GOVVIE";

	/**
	 * Govvie Latent State Quantification Metric - Treasury Benchmark Yield
	 */

	public static final java.lang.String GOVVIE_QM_YIELD = "GOVVIE_QM_YIELD";

	/**
	 * FX Latent State
	 */

	public static final java.lang.String LATENT_STATE_FX = "LATENT_STATE_FX";

	/**
	 * FX Latent State Quantification Metric - FX Forward Outright
	 */

	public static final java.lang.String FX_QM_FORWARD_OUTRIGHT = "FX_QM_FORWARD_OUTRIGHT";

	/**
	 * Volatility Latent State
	 */

	public static final java.lang.String LATENT_STATE_VOLATILITY = "LATENT_STATE_VOLATILITY";

	/**
	 * Volatility Latent State Quantification Metric - SABR Volatility
	 */

	public static final java.lang.String VOLATILITY_QM_SABR_VOLATILITY = "VOLATILITY_QM_SABR_VOLATILITY";

	/**
	 * Volatility Latent State Quantification Metric - Lognormal Volatility
	 */

	public static final java.lang.String VOLATILITY_QM_LOGNORMAL_VOLATILITY =
		"VOLATILITY_QM_LOGNORMAL_VOLATILITY";

	/**
	 * Volatility Latent State Quantification Metric - Normal Volatility
	 */

	public static final java.lang.String VOLATILITY_QM_NORMAL_VOLATILITY = "VOLATILITY_QM_NORMAL_VOLATILITY";
}
