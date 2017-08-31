
package org.drip.product.calib;

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
 * CompositePeriodQuoteSet implements the composite period's calibration quote set functionality.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CompositePeriodQuoteSet extends org.drip.product.calib.ProductQuoteSet {

	/**
	 * CompositePeriodQuoteSet constructor
	 * 
	 * @param aLSS Array of Latent State Specification
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public CompositePeriodQuoteSet (
		final org.drip.state.representation.LatentStateSpecification[] aLSS)
		throws java.lang.Exception
	{
		super (aLSS);
	}

	/**
	 * Set the Base Rate
	 * 
	 * @param dblBaseRate The Base Rate
	 * 
	 * @return TRUE - Base Rate successfully set
	 */

	public boolean setBaseRate (
		final double dblBaseRate)
	{
		return set ("BaseRate", dblBaseRate);
	}

	/**
	 * Indicate if the Base Rate Field exists
	 * 
	 * @return TRUE - Base Rate Field Exists
	 */

	public boolean containsBaseRate()
	{
		return contains ("BaseRate");
	}

	/**
	 * Get the Period Base Coupon Rate
	 * 
	 * @return The Period Base Coupon Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Base Rate Field does not exist
	 */

	public double baseRate()
		throws java.lang.Exception
	{
		return get ("BaseRate");
	}

	/**
	 * Set the Basis
	 * 
	 * @param dblBasis The Basis
	 * 
	 * @return TRUE - Basis successfully set
	 */

	public boolean setBasis (
		final double dblBasis)
	{
		return set ("Basis", dblBasis);
	}

	/**
	 * Indicate if the Basis Field exists
	 * 
	 * @return TRUE - Basis Field Exists
	 */

	public boolean containsBasis()
	{
		return contains ("Basis");
	}

	/**
	 * Get the Period Coupon Basis
	 * 
	 * @return The Period Coupon Basis
	 * 
	 * @throws java.lang.Exception Thrown if the Basis Field does not exist
	 */

	public double basis()
		throws java.lang.Exception
	{
		return get ("Basis");
	}
}
