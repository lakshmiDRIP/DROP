
package org.drip.xva.universe;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * MarketEdge holds the Vertex Realizations of the Market States of the Reference Universe along an
 * 	Evolution Edge. The References are:
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option <i>eSSRN</i>
 *  		<b>https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955</b>.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies <i>Risk</i> <b>23 (12)</b>
 *  	82-87.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accountingb <b>eSSRN</b>
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011.<br><br>
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  	<i>Risk</i> <b>21 (2)</b> 97-102.<br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MarketEdge {
	private org.drip.xva.universe.MarketVertex _mvStart = null;
	private org.drip.xva.universe.MarketVertex _mvFinish = null;

	/**
	 * MarketEdge Constructor
	 * 
	 * @param mvStart The Starting Market Vertex Instance
	 * @param mvFinish The Finishing Market Vertex Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarketEdge (
		final org.drip.xva.universe.MarketVertex mvStart,
		final org.drip.xva.universe.MarketVertex mvFinish)
		throws java.lang.Exception
	{
		if (null == (_mvStart = mvStart) || null == (_mvFinish = mvFinish))
			throw new java.lang.Exception ("MarketEdge Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Market Vertex Increment
	 * 
	 * @return The Market Vertex Increment
	 */

	public int vertexIncrement()
	{
		return _mvFinish.anchor().julian() - _mvStart.anchor().julian();
	}

	/**
	 * Retrieve the Market State Vertex Start
	 * 
	 * @return The Market State Vertex Start
	 */

	public org.drip.xva.universe.MarketVertex start()
	{
		return _mvStart;
	}

	/**
	 * Retrieve the Market State Vertex Finish
	 * 
	 * @return The Market State Vertex Finish
	 */

	public org.drip.xva.universe.MarketVertex finish()
	{
		return _mvFinish;
	}
}
