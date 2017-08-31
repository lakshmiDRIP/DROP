
package org.drip.product.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * BasketMarketParamRef interface provides stubs for basket name, IR curve, forward curve, credit curve, TSY
 * 	curve, and needed to value the component.
 *
 * @author Lakshmi Krishnamurthy
 */

public interface BasketMarketParamRef {

	/**
	 * Get the component name
	 * 
	 * @return The component name
	 */

	public abstract java.lang.String name();

	/**
	 * Get the Coupon Currency
	 * 
	 * @return The Coupon Currency
	 */

	public abstract java.lang.String[] couponCurrency();

	/**
	 * Get the Pay Currency
	 * 
	 * @return The Pay Currency
	 */

	public abstract java.lang.String[] payCurrency();

	/**
	 * Get the Principal Currency
	 * 
	 * @return The Principal Currency
	 */

	public abstract java.lang.String[] principalCurrency();

	/**
	 * Get the Array of Credit Curve Latent State Identifier Labels
	 * 
	 * @return The Array of Credit Curve Latent State Identifier Labels
	 */

	public abstract org.drip.state.identifier.CreditLabel[] creditLabel();

	/**
	 * Get the Array of Forward Curve Latent State Labels
	 * 
	 * @return Array of the Forward Curve Latent State Labels
	 */

	public abstract org.drip.state.identifier.ForwardLabel[] forwardLabel();

	/**
	 * Get the Array of Funding Curve Latent State Labels
	 * 
	 * @return Array of the Funding Curve Latent State Labels
	 */

	public abstract org.drip.state.identifier.FundingLabel[] fundingLabel();

	/**
	 * Get the Array of the FX Latent State Identifier Labels
	 * 
	 * @return The Array of the FX Latent State Identifier Labels
	 */

	public abstract org.drip.state.identifier.FXLabel[] fxLabel();
}
