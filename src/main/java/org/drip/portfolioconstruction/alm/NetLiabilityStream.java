
package org.drip.portfolioconstruction.alm;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>NetLiabilityStream</i> holds the Investor's Horizon, Consumption, and Income Settings needed to
 * generate and value the Net Liability Cash Flow Stream.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/alm">Asset Liability Management</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/AssetAllocation">Asset Allocation Library</a></li>
 *  </ul>
 * <br><br>
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
