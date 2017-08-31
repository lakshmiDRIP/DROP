
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
 * NetLiabilityCashFlow holds the Investor Time Snap's Singular Liability Flow Details.
 *
 * @author Lakshmi Krishnamurthy
 */

public class NetLiabilityCashFlow {
	private boolean _bIsAlive = false;
	private boolean _bIsRetired = false;
	private double _dblAge = java.lang.Double.NaN;
	private double _dblHorizon = java.lang.Double.NaN;
	private double _dblAfterTaxIncome = java.lang.Double.NaN;
	private double _dblPensionBenefits = java.lang.Double.NaN;
	private double _dblBasicConsumption = java.lang.Double.NaN;
	private double _dblWorkingAgeIncome = java.lang.Double.NaN;
	private double _dblPensionBenefitsDF = java.lang.Double.NaN;
	private double _dblBasicConsumptionDF = java.lang.Double.NaN;
	private double _dblWorkingAgeIncomeDF = java.lang.Double.NaN;

	/**
	 * NetLiabilityCashFlow Constructor
	 * 
	 * @param dblAge The Investor Age
	 * @param bIsRetired The Retirement Indicator Flag
	 * @param bIsAlive The "Is Alive" Indicator Flag
	 * @param dblHorizon The Snapshot's Investment Horizon
	 * @param dblAfterTaxIncome The Basic After-Tax Income
	 * @param dblWorkingAgeIncome The Investor Working Age Income
	 * @param dblPensionBenefits The Investor Pension Benefits
	 * @param dblBasicConsumption The Investor Basic Consumption
	 * @param dblWorkingAgeIncomeDF The Investor Working Age Income Discount Factor
	 * @param dblPensionBenefitsDF The Investor Pension Benefits Discount Factor
	 * @param dblBasicConsumptionDF The Investor Basic Consumption Discount Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public NetLiabilityCashFlow (
		final double dblAge,
		final boolean bIsRetired,
		final boolean bIsAlive,
		final double dblHorizon,
		final double dblAfterTaxIncome,
		final double dblWorkingAgeIncome,
		final double dblPensionBenefits,
		final double dblBasicConsumption,
		final double dblWorkingAgeIncomeDF,
		final double dblPensionBenefitsDF,
		final double dblBasicConsumptionDF)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblAge = dblAge) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblHorizon = dblHorizon) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblAfterTaxIncome = dblAfterTaxIncome) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblWorkingAgeIncome = dblWorkingAgeIncome) ||
						!org.drip.quant.common.NumberUtil.IsValid (_dblPensionBenefits = dblPensionBenefits)
							|| !org.drip.quant.common.NumberUtil.IsValid (_dblBasicConsumption =
								dblBasicConsumption) || !org.drip.quant.common.NumberUtil.IsValid
									(_dblWorkingAgeIncomeDF = dblWorkingAgeIncomeDF) ||
										!org.drip.quant.common.NumberUtil.IsValid (_dblPensionBenefitsDF =
											dblPensionBenefitsDF) ||
												!org.drip.quant.common.NumberUtil.IsValid
													(_dblBasicConsumptionDF = dblBasicConsumptionDF))
			throw new java.lang.Exception ("NetLiabilityCashFlow Constructor => Invalid Inputs");

		_bIsAlive = bIsAlive;
		_bIsRetired = bIsRetired;
	}

	/**
	 * Retrieve the Investor Age
	 * 
	 * @return The Investor Age
	 */

	public double age()
	{
		return _dblAge;
	}

	/**
	 * Retrieve the Retirement Indicator Flag
	 * 
	 * @return The Retirement Indicator Flag
	 */

	public boolean isRetired()
	{
		return _bIsRetired;
	}

	/**
	 * Retrieve the "Is Alive" Indicator Flag
	 * 
	 * @return The "Is Alive" Indicator Flag
	 */

	public boolean isAlive()
	{
		return _bIsAlive;
	}

	/**
	 * Retrieve the Snapshot's Investment Horizon
	 * 
	 * @return The Snapshot's Investment Horizon
	 */

	public double horizon()
	{
		return _dblHorizon;
	}

	/**
	 * Retrieve the Basic After-Tax Income
	 * 
	 * @return The Basic After-Tax Income
	 */

	public double afterTaxIncome()
	{
		return _dblAfterTaxIncome;
	}

	/**
	 * Retrieve the Investor Working Age Income
	 * 
	 * @return The Investor Working Age Income
	 */

	public double workingAgeIncome()
	{
		return _dblWorkingAgeIncome;
	}

	/**
	 * Retrieve the Investor Pension Benefits
	 * 
	 * @return The Investor Pension Benefits
	 */

	public double pensionBenefits()
	{
		return _dblPensionBenefits;
	}

	/**
	 * Retrieve the Investor Basic Consumption
	 * 
	 * @return The Investor Basic Consumption
	 */

	public double basicConsumption()
	{
		return _dblBasicConsumption;
	}

	/**
	 * Retrieve the Investor Working Age Income Discount Factor
	 * 
	 * @return The Investor Working Age Income Discount Factor
	 */

	public double workingAgeIncomeDF()
	{
		return _dblWorkingAgeIncomeDF;
	}

	/**
	 * Retrieve the Investor Pension Benefits Discount Factor
	 * 
	 * @return The Investor Pension Benefits Discount Factor
	 */

	public double pensionBenefitsDF()
	{
		return _dblPensionBenefitsDF;
	}

	/**
	 * Retrieve the Investor Basic Consumption Discount Factor
	 * 
	 * @return The Investor Basic Consumption Discount Factor
	 */

	public double basicConsumptionDF()
	{
		return _dblBasicConsumptionDF;
	}
}
