
package org.drip.portfolioconstruction.alm;

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
 * InvestorCliffSettings contains the Investor's Time Cliff Settings Parameters such as the Retirement and
 *  the Mortality Ages.
 *
 * @author Lakshmi Krishnamurthy
 */

public class InvestorCliffSettings {

	/**
	 * Date Phase - Before Retirement
	 */

	public static final int DATE_PHASE_BEFORE_RETIREMENT = 0;

	/**
	 * Date Phase - After Retirement
	 */

	public static final int DATE_PHASE_AFTER_RETIREMENT = 1;

	/**
	 * Date Phase - After Death
	 */

	public static final int DATE_PHASE_AFTER_MORTALITY = 2;

	private double _dblMaximumAge = java.lang.Double.NaN;
	private double _dblRetirementAge = java.lang.Double.NaN;

	/**
	 * InvestorCliffSettings Constructor
	 * 
	 * @param dblRetirementAge The Investor Retirement Age
	 * @param dblMaximumAge The Investor Maximum Age
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public InvestorCliffSettings (
		final double dblRetirementAge,
		final double dblMaximumAge)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblRetirementAge = dblRetirementAge) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblMaximumAge = dblMaximumAge) || _dblRetirementAge
				>= _dblMaximumAge)
			throw new java.lang.Exception ("InvestorCliffSettings Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Investor Retirement Age
	 * 
	 * @return The Investor Retirement Age
	 */

	public double retirementAge()
	{
		return _dblRetirementAge;
	}

	/**
	 * Retrieve the Investor Maximum Age
	 * 
	 * @return The Investor Maximum Age
	 */

	public double maximumAge()
	{
		return _dblMaximumAge;
	}

	/**
	 * Retrieve the Investment Phase corresponding to the specified Age
	 * 
	 * @param dblAge The Age whose Investment Phase is needed
	 * 
	 * @return The Investment Phase
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public int phase (
		final double dblAge)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblAge))
			throw new java.lang.Exception ("InvestorHorizon::phase => Invalid Inputs");

		if (dblAge <= _dblRetirementAge) return DATE_PHASE_BEFORE_RETIREMENT;

		if (dblAge <= _dblMaximumAge) return DATE_PHASE_AFTER_RETIREMENT;

		return DATE_PHASE_AFTER_MORTALITY;
	}

	/**
	 * Retrieve the Investor Retirement Indicator Flag corresponding to the specified Age
	 * 
	 * @param dblAge The Age whose Retirement Indicator is needed
	 * 
	 * @return TRUE - The Age indicates that the Investor has retired
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public boolean retirementIndicator (
		final double dblAge)
		throws java.lang.Exception
	{
		return DATE_PHASE_BEFORE_RETIREMENT != phase (dblAge);
	}

	/**
	 * Retrieve the Investor "Is Alive" Indicator Flag corresponding to the specified Age
	 * 
	 * @param dblAge The Age whose "Is Alive" Indicator is needed
	 * 
	 * @return TRUE - The Age indicates that the Investor "Is Alive"
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public boolean isAlive (
		final double dblAge)
		throws java.lang.Exception
	{
		return DATE_PHASE_AFTER_MORTALITY != phase (dblAge);
	}
}
