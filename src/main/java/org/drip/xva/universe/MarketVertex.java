
package org.drip.xva.universe;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * MarketVertex holds the Market Realizations at a Market Trajectory Vertex needed for computing the
 *  Valuation Adjustment. The References are:<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b>
 *  	82-87.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75.<br><br>
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  	86-90.<br><br>
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  	<i>Risk</i> <b>21 (2)</b> 97-102.<br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MarketVertex
{
	private double _dblCSASpread = java.lang.Double.NaN;
	public double _dblPortfolioValue = java.lang.Double.NaN;
	private double _dblOvernightRate = java.lang.Double.NaN;
	private org.drip.analytics.date.JulianDate _dtAnchor = null;
	private org.drip.xva.universe.EntityMarketVertex _emvBank = null;
	private org.drip.xva.universe.EntityMarketVertex _emvCounterParty = null;
	private org.drip.xva.universe.LatentStateMarketVertex _lsmvCSANumeraire = null;
	private org.drip.xva.universe.LatentStateMarketVertex _lsmvOvernightNumeraire = null;

	/**
	 * MarketVertex Constructor
	 * 
	 * @param dtAnchor The Vertex Date Anchor
	 * @param dblPortfolioValue The Realized Portfolio Value
	 * @param dblOvernightRate The Realized Overnight Rate
	 * @param lsmvOvernightNumeraire The Realized Overnight Numeraire
	 * @param dblCSASpread The Realized CSA Spread
	 * @param lsmvCSANumeraire The Realized CSA Numeraire
	 * @param emvBank Bank Entity Market Vertex Instance
	 * @param emvCounterParty Counter Party Market Vertex Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarketVertex (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final double dblPortfolioValue,
		final double dblOvernightRate,
		final org.drip.xva.universe.LatentStateMarketVertex lsmvOvernightNumeraire,
		final double dblCSASpread,
		final org.drip.xva.universe.LatentStateMarketVertex lsmvCSANumeraire,
		final org.drip.xva.universe.EntityMarketVertex emvBank,
		final org.drip.xva.universe.EntityMarketVertex emvCounterParty)
		throws java.lang.Exception
	{
		if (null == (_dtAnchor = dtAnchor) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblOvernightRate = dblOvernightRate) ||
			null == (_lsmvOvernightNumeraire = lsmvOvernightNumeraire) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblCSASpread = dblCSASpread) ||
			null == (_lsmvCSANumeraire = lsmvCSANumeraire) ||
			null == (_emvBank = emvBank) ||
			null == (_emvCounterParty = emvCounterParty))
			throw new java.lang.Exception ("MarketVertex Constructor => Invalid Inputs");

		_dblPortfolioValue = dblPortfolioValue;
	}

	/**
	 * Retrieve the Date Anchor
	 * 
	 * @return The Date Anchor
	 */

	public org.drip.analytics.date.JulianDate anchor()
	{
		return _dtAnchor;
	}

	/**
	 * Retrieve the Realized Portfolio Value
	 * 
	 * @return The Portfolio Value
	 */

	public double portfolioValue()
	{
		return _dblPortfolioValue;
	}

	/**
	 * Retrieve the Realized Overnight Index Rate
	 * 
	 * @return The Realized Overnight Index Rate
	 */

	public double overnightRate()
	{
		return _dblOvernightRate;
	}

	/**
	 * Retrieve the Realized Overnight Index Numeraire
	 * 
	 * @return The Realized Overnight Index Numeraire
	 */

	public org.drip.xva.universe.LatentStateMarketVertex overnightNumeraire()
	{
		return _lsmvOvernightNumeraire;
	}

	/**
	 * Retrieve the Realized Spread over the Overnight Policy Rate corresponding to the CSA Scheme
	 * 
	 * @return The Realized Spread over the Overnight Policy Rate corresponding to the CSA Scheme
	 */

	public double csaSpread()
	{
		return _dblCSASpread;
	}

	/**
	 * Retrieve the Realized CSA Scheme Numeraire
	 * 
	 * @return The Realized CSA Scheme Numeraire
	 */

	public org.drip.xva.universe.LatentStateMarketVertex csaNumeraire()
	{
		return _lsmvCSANumeraire;
	}

	/**
	 * Retrieve the Realized CSA Scheme Rate
	 * 
	 * @return The Realized CSA Scheme Rate
	 */

	public double csaRate()
	{
		return _dblOvernightRate + _dblCSASpread;
	}

	/**
	 * Retrieve the Realized Bank Senior Market Vertex
	 * 
	 * @return The Realized Bank Senior Market Vertex
	 */

	public org.drip.xva.universe.EntityMarketVertex bank()
	{
		return _emvBank;
	}

	/**
	 * Retrieve the Realized Counter Party Market Vertex
	 * 
	 * @return The Realized Counter Party Market Vertex
	 */

	public org.drip.xva.universe.EntityMarketVertex counterParty()
	{
		return _emvCounterParty;
	}
}
