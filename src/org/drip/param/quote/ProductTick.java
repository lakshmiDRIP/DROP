
package org.drip.param.quote;

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
 * ProductTick holds the tick related product parameters - it contains the product ID, the quote composite,
 *  the source, the counter party, and whether the quote can be treated as a mark.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ProductTick {
	private boolean _bIsMark = false;
	private java.lang.String _strSource = "";
	private java.lang.String _strProductID = "";
	private java.lang.String _strCounterParty = "";
	private org.drip.param.definition.ProductQuote _pq = null;

	/**
	 * Empty ProductTick constructor
	 */

	public ProductTick()
	{
	}

	/**
	 * ProductTick constructor
	 * 
	 * @param strProductID Product ID
	 * @param pq Product Quote
	 * @param strCounterParty Counter Party
	 * @param strSource Quote Source
	 * @param bIsMark TRUE - This Quote may be treated as a Mark
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public ProductTick (
		final java.lang.String strProductID,
		final org.drip.param.definition.ProductQuote pq,
		final java.lang.String strCounterParty,
		final java.lang.String strSource,
		final boolean bIsMark)
		throws java.lang.Exception
	{
		if (null == (_strProductID = strProductID) || _strProductID.isEmpty() || null == (_pq = pq))
			throw new java.lang.Exception ("ProductTick ctr: Invalid Inputs");

		_bIsMark = bIsMark;
		_strSource = strSource;
		_strCounterParty = strCounterParty;
	}

	/**
	 * Retrieve the Product ID
	 * 
	 * @return Product ID
	 */

	public java.lang.String productID()
	{
		return _strProductID;
	}

	/**
	 * Retrieve the Product Quote
	 * 
	 * @return Product Quote
	 */

	public org.drip.param.definition.ProductQuote productQuote()
	{
		return _pq;
	}

	/**
	 * Retrieve the Quote Source
	 * 
	 * @return Quote Source
	 */

	public java.lang.String source()
	{
		return _strSource;
	}

	/**
	 * Retrieve the Counter Party
	 * 
	 * @return Counter Party
	 */

	public java.lang.String counterParty()
	{
		return _strCounterParty;
	}

	/**
	 * Indicate whether the quote may be treated as a mark
	 * 
	 * @return TRUE - Treat the Quote as a Mark
	 */

	public boolean isMark()
	{
		return _bIsMark;
	}
}
