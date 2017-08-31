
package org.drip.historical.state;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * CreditCurveMetrics holds the computed Metrics associated the Credit Curve State.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CreditCurveMetrics {
	private org.drip.analytics.date.JulianDate _dtClose = null;

	private java.util.TreeMap<org.drip.analytics.date.JulianDate, java.lang.Double> _mapSurvivalProbability =
		new java.util.TreeMap<org.drip.analytics.date.JulianDate, java.lang.Double>();

	private java.util.TreeMap<org.drip.analytics.date.JulianDate, java.lang.Double> _mapRecoveryRate = new
		java.util.TreeMap<org.drip.analytics.date.JulianDate, java.lang.Double>();

	/**
	 * CreditCurveMetrics Constructor
	 * 
	 * @param dtClose The Closing Date
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public CreditCurveMetrics (
		final org.drip.analytics.date.JulianDate dtClose)
		throws java.lang.Exception
	{
		if (null == (_dtClose = dtClose))
			throw new java.lang.Exception ("CreditCurveMetrics Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Closing Date
	 * 
	 * @return The Closing Date
	 */

	public org.drip.analytics.date.JulianDate close()
	{
		return _dtClose;
	}

	/**
	 * Add the Survival Probability corresponding to the specified Date
	 * 
	 * @param dt The Date
	 * @param dblSurvivalProbability The Survival Probability
	 * 
	 * @return TRUE - The Dated Survival Probability successfully added
	 */

	public boolean addSurvivalProbability (
		final org.drip.analytics.date.JulianDate dt,
		final double dblSurvivalProbability)
	{
		if (null == dt || !org.drip.quant.common.NumberUtil.IsValid (dblSurvivalProbability)) return false;

		_mapSurvivalProbability.put (dt, dblSurvivalProbability);

		return true;
	}

	/**
	 * Add the Recovery Rate corresponding to the specified Date
	 * 
	 * @param dt The Date
	 * @param dblRecoveryRate The Recovery Rate
	 * 
	 * @return TRUE - The Dated Recovery Rate successfully added
	 */

	public boolean addRecoveryRate (
		final org.drip.analytics.date.JulianDate dt,
		final double dblRecoveryRate)
	{
		if (null == dt || !org.drip.quant.common.NumberUtil.IsValid (dblRecoveryRate)) return false;

		_mapRecoveryRate.put (dt, dblRecoveryRate);

		return true;
	}

	/**
	 * Retrieve the Survival Probability corresponding to the specified Date
	 * 
	 * @param dt The Specified Date
	 * 
	 * @return The corresponding Survival Probability
	 * 
	 * @throws java.lang.Exception Thrown if the Survival Probability cannot be retrieved
	 */

	public double survivalProbability (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt || !_mapSurvivalProbability.containsKey (dt))
			throw new java.lang.Exception ("CreditCurveMetrics::survivalProbability => Invalid Inputs");

		return _mapSurvivalProbability.get (dt);
	}

	/**
	 * Retrieve the Recovery Rate corresponding to the specified Date
	 * 
	 * @param dt The Specified Date
	 * 
	 * @return The corresponding Recovery Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Recovery Rate cannot be retrieved
	 */

	public double recoveryRate (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt || !_mapRecoveryRate.containsKey (dt))
			throw new java.lang.Exception ("CreditCurveMetrics::recoveryRate => Invalid Inputs");

		return _mapRecoveryRate.get (dt);
	}
}
