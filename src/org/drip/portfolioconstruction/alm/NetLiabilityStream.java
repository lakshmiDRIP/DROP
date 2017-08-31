
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
 * NetLiabilityStream holds the Investor's Horizon, Consumption, and Income Settings needed to generate and
 * 	value the Net Liability Cash Flow Stream.
 *
 * @author Lakshmi Krishnamurthy
 */

public class NetLiabilityStream {
	private double _dblAfterTaxIncome = java.lang.Double.NaN;
	private org.drip.portfolioconstruction.alm.InvestorCliffSettings _ics = null;
	private org.drip.portfolioconstruction.alm.ExpectedBasicConsumption _ebc = null;
	private org.drip.portfolioconstruction.alm.ExpectedNonFinancialIncome _enfi = null;

	/**
	 * NetLiabilityStream Constructor
	 * 
	 * @param ics The Investor's Time Cliff Settings
	 * @param enfi The Investor's Non-Financial Income Settings
	 * @param ebc The Investor's Basic Consumption Settings
	 * @param dblAfterTaxIncome The Basic After-Tax Income
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NetLiabilityStream (
		final org.drip.portfolioconstruction.alm.InvestorCliffSettings ics,
		final org.drip.portfolioconstruction.alm.ExpectedNonFinancialIncome enfi,
		final org.drip.portfolioconstruction.alm.ExpectedBasicConsumption ebc,
		final double dblAfterTaxIncome)
		throws java.lang.Exception
	{
		if (null == (_ics = ics) || null == (_enfi = enfi) || null == (_ebc = ebc) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblAfterTaxIncome = dblAfterTaxIncome))
			throw new java.lang.Exception ("NetLiabilityStream Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Investor's Time Horizon Settings
	 * 
	 * @return The Investor's Time Horizon Settings
	 */

	public org.drip.portfolioconstruction.alm.InvestorCliffSettings investorCliffSettings()
	{
		return _ics;
	}

	/**
	 * Retrieve the Investor's Basic Consumption Settings
	 * 
	 * @return The Investor's Basic Consumption Settings
	 */

	public org.drip.portfolioconstruction.alm.ExpectedBasicConsumption basicConsumption()
	{
		return _ebc;
	}

	/**
	 * Retrieve the Investor's Non-Financial Income Settings
	 * 
	 * @return The Investor's Non-Financial Income Settings
	 */

	public org.drip.portfolioconstruction.alm.ExpectedNonFinancialIncome nonFinancialIncome()
	{
		return _enfi;
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
	 * Generate the NetLiabilityMetrics Instance
	 * 
	 * @param dblStartAge The Starting Age
	 * @param dblEndAge The Ending Age
	 * @param dr The Discount Rate Instance
	 * 
	 * @return The NetLiabilityMetrics Instance
	 */

	public org.drip.portfolioconstruction.alm.NetLiabilityMetrics metrics (
		final double dblStartAge,
		final double dblEndAge,
		final org.drip.portfolioconstruction.alm.DiscountRate dr)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblStartAge) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblEndAge) || dblStartAge >= dblEndAge || null == dr)
			return null;

		java.util.List<org.drip.portfolioconstruction.alm.NetLiabilityCashFlow> lsNLCF = new
			java.util.ArrayList<org.drip.portfolioconstruction.alm.NetLiabilityCashFlow>();

		double dblBasicConsumptionPV = 0.;
		double dblWorkingAgeIncomePV = 0.;
		double dblPensionBenefitsIncomePV = 0.;

		for (double dblCurrentAge = dblStartAge + 1.; dblCurrentAge <= dblEndAge; ++dblCurrentAge) {
			try {
				double dblHorizon = dblCurrentAge - dblStartAge;

				boolean bIsAlive = _ics.isAlive (dblCurrentAge);

				boolean bIsRetirement = _ics.retirementIndicator (dblCurrentAge);

				double dblBasicConsumptionDF = dr.basicConsumptionDF (dblHorizon);

				double dblWorkingAgeIncomeDF = dr.workingAgeIncomeDF (dblHorizon);

				double dblPensionBenefitsIncomeDF = dr.pensionBenefitsIncomeDF (dblHorizon);

				double dblBasicConsumption = _dblAfterTaxIncome * _ebc.rate (dblCurrentAge, _ics);

				double dblWorkingAgeIncome = !bIsRetirement && bIsAlive ? _dblAfterTaxIncome * _enfi.rate
					(dblCurrentAge, _ics) : 0.;

				double dblPensionBenefitsIncome = bIsRetirement && bIsAlive ? _dblAfterTaxIncome * _enfi.rate
					(dblCurrentAge, _ics) : 0.;

				lsNLCF.add (new org.drip.portfolioconstruction.alm.NetLiabilityCashFlow (dblCurrentAge,
					bIsRetirement, bIsAlive, dblHorizon, _dblAfterTaxIncome, dblWorkingAgeIncome,
						dblPensionBenefitsIncome, dblBasicConsumption, dblWorkingAgeIncomeDF,
							dblPensionBenefitsIncomeDF, dblBasicConsumptionDF));

				dblBasicConsumptionPV += dblBasicConsumption * dblBasicConsumptionDF;
				dblWorkingAgeIncomePV += dblWorkingAgeIncome * dblWorkingAgeIncomeDF;
				dblPensionBenefitsIncomePV += dblPensionBenefitsIncome * dblPensionBenefitsIncomeDF;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		try {
			return new org.drip.portfolioconstruction.alm.NetLiabilityMetrics (lsNLCF, dblWorkingAgeIncomePV,
				dblPensionBenefitsIncomePV, dblBasicConsumptionPV);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
