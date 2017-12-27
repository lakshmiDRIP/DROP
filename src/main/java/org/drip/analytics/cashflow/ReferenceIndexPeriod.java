
package org.drip.analytics.cashflow;

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
 *  - DRIP Asset Allocation: Library for models for MPT framework, Black Litterman Strategy Incorporator,
 *  	Holdings Constraint, and Transaction Costs.
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
 * ReferenceIndexPeriod contains the Cash Flow Period Details. Currently it holds the Start Date, the End
 *  Date, the Fixing Date, and the Reference Latent State Label.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class ReferenceIndexPeriod
{
	private double _dblDCF = java.lang.Double.NaN;
	private int _iEndDate = java.lang.Integer.MIN_VALUE;
	private int _iStartDate = java.lang.Integer.MIN_VALUE;
	private int _iFixingDate = java.lang.Integer.MIN_VALUE;
	private org.drip.state.identifier.LatentStateLabel _latentStateLabel = null;

	protected ReferenceIndexPeriod (
		final int iStartDate,
		final int iEndDate,
		final int iFixingDate,
		final double dblDCF,
		final org.drip.state.identifier.LatentStateLabel latentStateLabel)
		throws java.lang.Exception
	{
		if ((_iEndDate = iEndDate) <= (_iStartDate = iStartDate) ||
			(_iFixingDate = iFixingDate) > _iStartDate ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblDCF = dblDCF) ||
			null == (_latentStateLabel = latentStateLabel))
			throw new java.lang.Exception ("ReferenceIndexPeriod ctr: Invalid Inputs");
	}

	/**
	 * Reference Period Start Date
	 * 
	 * @return The Reference Period Start Date
	 */

	public int startDate()
	{
		return _iStartDate;
	}

	/**
	 * Reference Period End Date
	 * 
	 * @return The Reference Period End Date
	 */

	public int endDate()
	{
		return _iEndDate;
	}

	/**
	 * Reference Period Fixing Date
	 * 
	 * @return The Reference Period Fixing Date
	 */

	public int fixingDate()
	{
		return _iFixingDate;
	}

	/**
	 * Retrieve the Latent State Label
	 * 
	 * @return The Latent State Label
	 */

	public org.drip.state.identifier.LatentStateLabel latentStateLabel()
	{
		return _latentStateLabel;
	}

	/**
	 * Retrieve the Reference Period Day Count Fraction
	 * 
	 * @return The Reference Period Day Count Fraction
	 */

	public double dcf()
	{
		return _dblDCF;
	}
}
