
package org.drip.analytics.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * In ExplicitBootCurve, the segment boundaries explicitly line up with the instrument maturity boundaries.
 * 	This feature is exploited in building a boot-strappable curve. Functionality is provides set the Latent
 * 	State at the Explicit Node, adjust the Latent State at the given Node, or set a common Flat Value across
 * 	all Nodes.
 *
 * @author Lakshmi Krishnamurthy
 */

public interface ExplicitBootCurve extends org.drip.analytics.definition.Curve {

	/**
	 * Set the Value/Slope at the Node specified by the Index
	 * 
	 * @param iIndex Node Index
	 * @param dblValue Node Value
	 * 
	 * @return Success (true), failure (false)
	 */

	public abstract boolean setNodeValue (
		final int iIndex,
		final double dblValue);

	/**
	 * Bump the node value at the node specified the index by the value
	 * 
	 * @param iIndex node index
	 * @param dblValue node bump value
	 * 
	 * @return Success (true), failure (false)
	 */

	public abstract boolean bumpNodeValue (
		final int iIndex,
		final double dblValue);

	/**
	 * Set the flat value across all the nodes
	 * 
	 * @param dblValue node value
	 * 
	 * @return Success (true), failure (false)
	 */

	public abstract boolean setFlatValue (
		final double dblValue);
}
