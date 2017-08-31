
package org.drip.param.definition;

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
 * ScenarioMarketParams is the place holder for the comprehensive suite of the market set of curves for the
 * 	given date. It exports the following functionality:
 * 	- add/remove/retrieve scenario discount curve
 * 	- add/remove/retrieve scenario forward curve
 * 	- add/remove/retrieve scenario zero curve
 * 	- add/remove/retrieve scenario credit curve
 * 	- add/remove/retrieve scenario recovery curve
 * 	- add/remove/retrieve scenario FXForward curve
 * 	- add/remove/retrieve scenario FXBasis curve
 * 	- add/remove/retrieve scenario fixings
 * 	- add/remove/retrieve Treasury/component quotes
 * 	- retrieve scenario Market Parameters
 * 	- retrieve map of flat rates/credit/recovery Market Parameters
 * 	- retrieve double map of tenor rates/credit/recovery Market Parameters
 *  - retrieve rates/credit scenario generator
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class ScenarioMarketParams {

	/**
	 * Add the named scenario DC
	 * 
	 * @param strName Name
	 * @param dcsc Corresponding DiscountCurveScenarioContainer instance
	 * 
	 * @return Added successfully (true)
	 */

	public abstract boolean addScenarioDiscountCurve (
		final java.lang.String strName,
		final org.drip.param.market.DiscountCurveScenarioContainer dcsc);

	/**
	 * Remove the named scenario DC
	 * 
	 * @param strName Name
	 * 
	 * @return Removed successfully (true)
	 */

	public abstract boolean removeScenarioDiscountCurve (
		final java.lang.String strName);

	/**
	 * Add the named scenario CC
	 * 
	 * @param strName Name
	 * @param scc ScenarioCreditCurve Instance
	 * 
	 * @return Added successfully (true)
	 */

	public abstract boolean addScenarioCreditCurve (
		final java.lang.String strName,
		final org.drip.param.market.CreditCurveScenarioContainer scc);

	/**
	 * Removes the named scenario CC
	 * 
	 * @param strName Name
	 * 
	 * @return Removed successfully (true)
	 */

	public abstract boolean removeScenarioCreditCurve (
		final java.lang.String strName);

	/**
	 * Add the named Treasury Quote
	 * 
	 * @param strBenchmark Name
	 * @param pqTSY Treasury Quote
	 * 
	 * @return Added successfully (true)
	 */

	public abstract boolean addTSYQuote (
		final java.lang.String strBenchmark,
		final org.drip.param.definition.ProductQuote pqTSY);

	/**
	 * Remove the named Treasury Quote
	 * 
	 * @param strBenchmark Name
	 * 
	 * @return Removed successfully (true)
	 */

	public abstract boolean removeTSYQuote (
		final java.lang.String strBenchmark);

	/**
	 * Set the full set of named Treasury Quote Map
	 * 
	 * @param mapCQTSY Named Treasury Quote Map
	 * 
	 * @return Set successfully (true)
	 */

	public abstract boolean setTSYQuotes (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>
			mapCQTSY);

	/**
	 * Get the named Treasury Quote Map corresponding to the desired benchmark
	 * 
	 * @param strBenchmark The treasury benchmark
	 * 
	 * @return Treasury Quote
	 */

	public abstract org.drip.param.definition.ProductQuote tsyQuote (
		final java.lang.String strBenchmark);

	/**
	 * Get the full set of named Treasury Quote Map
	 * 
	 * @return Named Treasury Quote Map
	 */

	public abstract org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>
		tsyQuotes();

	/**
	 * Add the fixing for the given Latent State Label and the given date
	 * 
	 * @param dtFix The fixing date
	 * @param lsl The Latent State Label
	 * @param dblFixing The fixing
	 * 
	 * @return Added successfully (true)
	 */

	public abstract boolean addFixing (
		final org.drip.analytics.date.JulianDate dtFix,
		final org.drip.state.identifier.LatentStateLabel lsl,
		final double dblFixing);

	/**
	 * Remove the fixing corresponding to the given date and the Latent State Label
	 * 
	 * @param dtFix Fixing date
	 * @param lsl The Latent State label
	 * 
	 * @return Successfully removed (true)
	 */

	public abstract boolean removeFixing (
		final org.drip.analytics.date.JulianDate dtFix,
		final org.drip.state.identifier.LatentStateLabel lsl);

	/**
	 * Retrieve the Latent State Fixings Container
	 * 
	 * @return The Latent State Fixings Container
	 */

	public abstract org.drip.param.market.LatentStateFixingsContainer fixings();

	/**
	 * Add the component quote
	 * 
	 * @param strComponentID Component ID
	 * @param cqComponent Component Quote
	 * 
	 * @return Added successfully (true)
	 */

	public abstract boolean addComponentQuote (
		final java.lang.String strComponentID,
		final org.drip.param.definition.ProductQuote cqComponent);

	/**
	 * Remove the component quote
	 * 
	 * @param strComponentID Component ID
	 * 
	 * @return Removed successfully (true)
	 */

	public abstract boolean removeComponentQuote (
		final java.lang.String strComponentID);

	/**
	 * Add the full map of component quotes
	 * 
	 * @param mapComponentQuote Map of Component Quotes
	 * 
	 * @return Added successfully (true)
	 */

	public abstract boolean addComponentQuote (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>
			mapComponentQuote);

	/**
	 * Retrieve the quote for the given component
	 * 
	 * @param strComponentID Component ID
	 * 
	 * @return ComponentQuote
	 */

	public abstract org.drip.param.definition.ProductQuote componentQuote (
		final java.lang.String strComponentID);

	/**
	 * Retrieve the full map of component quotes
	 * 
	 * @return The Map of Component Quotes
	 */

	public abstract org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote>
		componentQuotes();

	/**
	 * Add the named scenario Market Parameters
	 * 
	 * @param strScenarioName Scenario Name
	 * @param csqs Market Parameters
	 * 
	 * @return True - Added successfully
	 */

	public abstract boolean addScenarioMarketParams (
		final java.lang.String strScenarioName,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs);

	/**
	 * Retrieve the Named Scenario Market Parameters
	 * 
	 * @param strScenarioName Scenario Name
	 * 
	 * @return Named Market Parameters
	 */

	public abstract org.drip.param.market.CurveSurfaceQuoteContainer scenarioMarketParams (
		final java.lang.String strScenarioName);

	/**
	 * Get the Market Parameters corresponding to the component and the scenario
	 * 
	 * @param comp Component
	 * @param strScenario Scenario
	 * 
	 * @return The Market Parameters
	 */

	public abstract org.drip.param.market.CurveSurfaceQuoteContainer scenarioMarketParams (
		final org.drip.product.definition.Component comp,
		final java.lang.String strScenario);

	/**
	 * Get the Map of Funding Tenor Bumped Market Parameters corresponding to the Component
	 * 
	 * @param comp Component
	 * @param bBumpUp TRUE - Bump Up
	 * 
	 * @return Map of the Funding Tenor Bumped Market Parameters
	 */

	public abstract
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			fundingTenorMarketParams (
				final org.drip.product.definition.Component comp,
				final boolean bBumpUp);

	/**
	 * Get the map of tenor credit bumped Market Parameters corresponding to the component
	 *  
	 * @param comp Component
	 * @param bBumpUp Bump up (True)
	 * 
	 * @return Map of the tenor credit bumped Market Parameters
	 */

	public abstract
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			creditTenorMarketParams (
				final org.drip.product.definition.Component comp,
				final boolean bBumpUp);

	/**
	 * Get the Market Parameters for the given basket product and the scenario
	 * 
	 * @param bp BasketProduct
	 * @param strScenario Named Scenario
	 * 
	 * @return Market Parameters Instance
	 */

	public abstract org.drip.param.market.CurveSurfaceQuoteContainer scenarioMarketParams (
		final org.drip.product.definition.BasketProduct bp,
		final java.lang.String strScenario);

	/**
	 * Get the Map of Funding Parallel Bumped Curves for the given Basket Product
	 * 
	 * @param bp BasketProduct
	 * @param bBump True (Bump Up), False (Bump Down)
	 * 
	 * @return Map of the Funding Parallel Bumped curves
	 */

	public abstract
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			fundingFlatBump (
				final org.drip.product.definition.BasketProduct bp,
				final boolean bBump);

	/**
	 * Get the Map of credit Flat Bumped Curves for the given Basket Product
	 * 
	 * @param bp BasketProduct
	 * @param bBump True (Bump Up), False (Bump Down)
	 * 
	 * @return Map of the Credit Flat Bumped Curves
	 */

	public abstract
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			creditFlatBump (
				final org.drip.product.definition.BasketProduct bp,
				final boolean bBump);

	/**
	 * Get the map of Recovery Flat Bumped Curves for the given Basket Product
	 * 
	 * @param bp BasketProduct
	 * @param bBump True (Bump Up), False (Bump Down)
	 * 
	 * @return Map of the Recovery Flat Bumped Curves
	 */

	public abstract
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			recoveryFlatBump (
				final org.drip.product.definition.BasketProduct bp,
				final boolean bBump);

	/**
	 * Get the Double Map of Funding Tenor Bumped Curves for each Funding Curve for the given Basket Product
	 * 
	 * @param bp BasketProduct
	 * @param bBump True (Bump Up), False (Bump Down)
	 * 
	 * @return Double Map of the Funding Tenor Bumped Market Parameters
	 */

	public abstract
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>>
			fundingTenorBump (
				final org.drip.product.definition.BasketProduct bp,
				final boolean bBump);

	/**
	 * Get the double map of credit Tenor bumped curves for each credit curve for the given Basket Product
	 * 
	 * @param bp BasketProduct
	 * @param bBump True (Bump Up), False (Bump Down)
	 * 
	 * @return Double Map of the credit Tenor bumped Market Parameters
	 */

	public abstract
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>>
			creditTenorBump (
				final org.drip.product.definition.BasketProduct bp,
				final boolean bBump);

	/**
	 * Retrieve the Map of DiscountCurveScenarioContainer Instances
	 * 
	 * @return Map of DiscountCurveScenarioContainer Instances
	 */

	public abstract
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.DiscountCurveScenarioContainer>
			scenarioDiscountCurveMap();

	/**
	 * Retrieve the Map of ScenarioCreditCurve Instances
	 * 
	 * @return Map of ScenarioCreditCurve Instances
	 */

	public abstract
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CreditCurveScenarioContainer>
			scenarioCreditCurveMap();
}
