
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
 * DiscountRate holds the Cash Flow Discount Rate Parameters for each Type, i.e., Discount Rates for Working
 *  Age Income, Pension Benefits, and Basic Consumption.
 *
 * @author Lakshmi Krishnamurthy
 */

public class DiscountRate {
	private double _dblYield = java.lang.Double.NaN;
	private double _dblBasicConsumptionSpread = java.lang.Double.NaN;
	private double _dblWorkingAgeIncomeSpread = java.lang.Double.NaN;
	private double _dblPensionBenefitsIncomeSpread = java.lang.Double.NaN;

	/**
	 * DiscountRate Constructor
	 * 
	 * @param dblYield The Base Discounting Yield
	 * @param dblWorkingAgeIncomeSpread The Working Age Income Spread
	 * @param dblPensionBenefitsIncomeSpread The Pension Benefits Income Spread
	 * @param dblBasicConsumptionSpread The Basic Consumption Spread
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DiscountRate (
		final double dblYield,
		final double dblWorkingAgeIncomeSpread,
		final double dblPensionBenefitsIncomeSpread,
		final double dblBasicConsumptionSpread)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblYield = dblYield) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblWorkingAgeIncomeSpread =
				dblWorkingAgeIncomeSpread) || !org.drip.quant.common.NumberUtil.IsValid
					(_dblBasicConsumptionSpread = dblBasicConsumptionSpread) ||
						!org.drip.quant.common.NumberUtil.IsValid (_dblPensionBenefitsIncomeSpread =
							dblPensionBenefitsIncomeSpread))
			throw new java.lang.Exception ("DiscountRate Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Base Discounting Yield
	 * 
	 * @return The Base Discounting Yield
	 */

	public double yield()
	{
		return _dblYield;
	}

	/**
	 * Retrieve the Working Age Income Spread
	 * 
	 * @return The Working Age Income Spread
	 */

	public double workingAgeIncomeSpread()
	{
		return _dblWorkingAgeIncomeSpread;
	}

	/**
	 * Retrieve the Working Age Income Discount Rate
	 * 
	 * @return The Working Age Income Discount Rate
	 */

	public double workingAgeIncomeRate()
	{
		return _dblYield + _dblWorkingAgeIncomeSpread;
	}

	/**
	 * Retrieve the Working Age Income Discount Factor
	 * 
	 * @param dblHorizon The Horizon to which the Discount Factor is to be computed
	 * 
	 * @return The Working Age Income Discount Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double workingAgeIncomeDF (
		final double dblHorizon)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblHorizon))
			throw new java.lang.Exception ("DiscountRate::workingAgeIncomeDF => Invalid Inputs");

		return java.lang.Math.exp (-1. * dblHorizon * (_dblYield + _dblWorkingAgeIncomeSpread));
	}

	/**
	 * Retrieve the Pension Benefits Income Spread
	 * 
	 * @return The Pension Benefits Income Spread
	 */

	public double pensionBenefitsIncomeSpread()
	{
		return _dblPensionBenefitsIncomeSpread;
	}

	/**
	 * Retrieve the Pension Benefits Income Discount Rate
	 * 
	 * @return The Pension Benefits Income Discount Rate
	 */

	public double pensionBenefitsIncomeRate()
	{
		return _dblYield + _dblPensionBenefitsIncomeSpread;
	}

	/**
	 * Retrieve the Pension Benefits Income Discount Factor
	 * 
	 * @param dblHorizon The Horizon to which the Discount Factor is to be computed
	 * 
	 * @return The Pension Benefits Income Discount Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double pensionBenefitsIncomeDF (
		final double dblHorizon)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblHorizon))
			throw new java.lang.Exception ("DiscountRate::pensionBenefitsIncomeDF => Invalid Inputs");

		return java.lang.Math.exp (-1. * dblHorizon * (_dblYield + _dblPensionBenefitsIncomeSpread));
	}

	/**
	 * Retrieve the Basic Consumption Spread
	 * 
	 * @return The Basic Consumption Spread
	 */

	public double basicConsumptionSpread()
	{
		return _dblBasicConsumptionSpread;
	}

	/**
	 * Retrieve the Basic Consumption Discount Rate
	 * 
	 * @return The Basic Consumption Discount Rate
	 */

	public double basicConsumptionRate()
	{
		return _dblYield + _dblBasicConsumptionSpread;
	}

	/**
	 * Retrieve the Basic Consumption Discount Factor
	 * 
	 * @param dblHorizon The Horizon to which the Discount Factor is to be computed
	 * 
	 * @return The Basic Consumption Discount Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double basicConsumptionDF (
		final double dblHorizon)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblHorizon))
			throw new java.lang.Exception ("DiscountRate::basicConsumptionDF => Invalid Inputs");

		return java.lang.Math.exp (-1. * dblHorizon * (_dblYield + _dblBasicConsumptionSpread));
	}
}
