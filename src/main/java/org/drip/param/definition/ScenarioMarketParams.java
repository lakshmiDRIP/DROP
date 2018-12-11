
package org.drip.param.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>ScenarioMarketParams</i> is the place holder for the comprehensive suite of the market set of curves
 * for the given date. It exports the following functionality:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Add/remove/retrieve scenario discount curve
 * 		</li>
 * 		<li>
 * 			Add/remove/retrieve scenario forward curve
 * 		</li>
 * 		<li>
 * 			Add/remove/retrieve scenario zero curve
 * 		</li>
 * 		<li>
 * 			Add/remove/retrieve scenario credit curve
 * 		</li>
 * 		<li>
 * 			Add/remove/retrieve scenario recovery curve
 * 		</li>
 * 		<li>
 * 			Add/remove/retrieve scenario FXForward curve
 * 		</li>
 * 		<li>
 * 			Add/remove/retrieve scenario FXBasis curve
 * 		</li>
 * 		<li>
 * 			Add/remove/retrieve scenario fixings
 * 		</li>
 * 		<li>
 * 			Add/remove/retrieve Treasury/component quotes
 * 		</li>
 * 		<li>
 * 			Retrieve scenario Market Parameters
 * 		</li>
 * 		<li>
 * 			Retrieve map of flat rates/credit/recovery Market Parameters
 * 		</li>
 * 		<li>
 * 			Retrieve double map of tenor rates/credit/recovery Market Parameters
 * 		</li>
 * 		<li>
 *  		Retrieve rates/credit scenario generator
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param">Param</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/definition">Definition</a></li>
 *  </ul>
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
