
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
 * BondProduct interface implements the product static data behind bonds of all kinds. Bond static data is
 * 		captured in a set of 11 container classes – BondTSYParams, BondCouponParams, BondNotionalParams,
 * 		BondFloaterParams, BondCurrencyParams, BondIdentifierParams, ComponentValuationParams,
 * 		ComponentRatesValuationParams, ComponentCreditValuationParams, ComponentTerminationEvent,
 *  	BondFixedPeriodParams, and one EmbeddedOptionSchedule object instance each for the call and the put
 *  	objects. Each of these parameter sets can be set separately.
 *
 * @author Lakshmi Krishnamurthy
 */

public interface BondProduct {

	/**
	 * Set the bond treasury benchmark Set
	 * 
	 * @param tsyBmkSet Bond treasury benchmark Set
	 * 
	 * @return TRUE if succeeded
	 */

	public abstract boolean setTreasuryBenchmark (
		final org.drip.product.params.TreasuryBenchmarks tsyBmkSet
	);

	/**
	 * Retrieve the bond treasury benchmark Set
	 * 
	 * @return Bond treasury benchmark Set
	 */

	public abstract org.drip.product.params.TreasuryBenchmarks treasuryBenchmark();

	/**
	 * Set the bond identifier set
	 * 
	 * @param idSet Bond identifier set
	 * 
	 * @return True if succeeded
	 */

	public abstract boolean setIdentifierSet (
		final org.drip.product.params.IdentifierSet idSet
	);

	/**
	 * Retrieve the bond identifier set
	 * 
	 * @return Bond identifier set
	 */

	public abstract org.drip.product.params.IdentifierSet identifierSet();

	/**
	 * Set the bond coupon setting
	 * 
	 * @param cpnSetting Bond coupon setting
	 * 
	 * @return True if succeeded
	 */

	public abstract boolean setCouponSetting (
		final org.drip.product.params.CouponSetting cpnSetting
	);

	/**
	 * Retrieve the bond coupon setting
	 * 
	 * @return Bond Coupon setting
	 */

	public abstract org.drip.product.params.CouponSetting couponSetting();

	/**
	 * Set the bond floater setting
	 * 
	 * @param fltSetting Bond floater setting
	 * 
	 * @return True if succeeded
	 */

	public abstract boolean setFloaterSetting (
		final org.drip.product.params.FloaterSetting fltSetting
	);

	/**
	 * Retrieve the bond floater setting
	 * 
	 * @return Bond Floater setting
	 */

	public abstract org.drip.product.params.FloaterSetting floaterSetting();

	/**
	 * Set the Bond's Market Convention
	 * 
	 * @param mktConv Bond's Market Convention
	 * 
	 * @return True if succeeded
	 */

	public abstract boolean setMarketConvention (
		final org.drip.product.params.QuoteConvention mktConv
	);

	/**
	 * Retrieve the Bond's Market Convention
	 * 
	 * @return Bond's Market Convention
	 */

	public abstract org.drip.product.params.QuoteConvention marketConvention();

	/**
	 * Set the bond Credit Setting
	 * 
	 * @param creditSetting Bond credit Setting
	 * 
	 * @return True if succeeded
	 */

	public abstract boolean setCreditSetting (
		final org.drip.product.params.CreditSetting creditSetting
	);

	/**
	 * Retrieve the bond credit Setting
	 * 
	 * @return Bond credit Setting
	 */

	public abstract org.drip.product.params.CreditSetting creditSetting();

	/**
	 * Set the bond termination setting
	 * 
	 * @param termSetting Bond termination setting
	 * 
	 * @return True if succeeded
	 */

	public abstract boolean setTerminationSetting (
		final org.drip.product.params.TerminationSetting termSetting
	);

	/**
	 * Retrieve the bond termination setting
	 * 
	 * @return Bond termination setting
	 */

	public abstract org.drip.product.params.TerminationSetting terminationSetting();

	/**
	 * Set the bond Stream
	 * 
	 * @param stream Bond Stream
	 * 
	 * @return True if succeeded
	 */

	public abstract boolean setStream (
		final org.drip.product.params.BondStream stream
	);

	/**
	 * Retrieve the Bond Stream
	 * 
	 * @return Bond Stream
	 */

	public abstract org.drip.product.params.BondStream stream();

	/**
	 * Set the bond notional Setting
	 * 
	 * @param notlSetting Bond Notional Setting
	 * 
	 * @return True if succeeded
	 */

	public abstract boolean setNotionalSetting (
		final org.drip.product.params.NotionalSetting notlSetting
	);

	/**
	 * Retrieve the bond notional Setting
	 * 
	 * @return Bond notional Setting
	 */

	public abstract org.drip.product.params.NotionalSetting notionalSetting();

	/**
	 * Set the bond's embedded call schedule
	 * 
	 * @param eos Bond's embedded call schedule
	 */

	public abstract void setEmbeddedCallSchedule (
		final org.drip.product.params.EmbeddedOptionSchedule eos
	);

	/**
	 * Set the bond's embedded put schedule
	 * 
	 * @param eos Bond's embedded put schedule
	 */

	public abstract void setEmbeddedPutSchedule (
		final org.drip.product.params.EmbeddedOptionSchedule eos
	);
}
