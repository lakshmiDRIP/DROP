
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/alm">ALM</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NetLiabilityStream
{
	private double _afterTaxIncome = java.lang.Double.NaN;
	private org.drip.portfolioconstruction.alm.InvestorCliffSettings _investorCliffSettings = null;
	private org.drip.portfolioconstruction.alm.ExpectedBasicConsumption _expectedBasicConsumption = null;
	private org.drip.portfolioconstruction.alm.ExpectedNonFinancialIncome _expectedNonFinancialIncome = null;

	/**
	 * NetLiabilityStream Constructor
	 * 
	 * @param investorCliffSettings The Investor's Time Cliff Settings
	 * @param expectedNonFinancialIncome The Investor's Non-Financial Income Settings
	 * @param expectedBasicConsumption The Investor's Basic Consumption Settings
	 * @param afterTaxIncome The Basic After-Tax Income
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NetLiabilityStream (
		final org.drip.portfolioconstruction.alm.InvestorCliffSettings investorCliffSettings,
		final org.drip.portfolioconstruction.alm.ExpectedNonFinancialIncome expectedNonFinancialIncome,
		final org.drip.portfolioconstruction.alm.ExpectedBasicConsumption expectedBasicConsumption,
		final double afterTaxIncome)
		throws java.lang.Exception
	{
		if (null == (_investorCliffSettings = investorCliffSettings) ||
			null == (_expectedNonFinancialIncome = expectedNonFinancialIncome) ||
			null == (_expectedBasicConsumption = expectedBasicConsumption) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_afterTaxIncome = afterTaxIncome))
		{
			throw new java.lang.Exception ("NetLiabilityStream Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Investor's Time Horizon Settings
	 * 
	 * @return The Investor's Time Horizon Settings
	 */

	public org.drip.portfolioconstruction.alm.InvestorCliffSettings investorCliffSettings()
	{
		return _investorCliffSettings;
	}

	/**
	 * Retrieve the Investor's Basic Consumption Settings
	 * 
	 * @return The Investor's Basic Consumption Settings
	 */

	public org.drip.portfolioconstruction.alm.ExpectedBasicConsumption basicConsumption()
	{
		return _expectedBasicConsumption;
	}

	/**
	 * Retrieve the Investor's Non-Financial Income Settings
	 * 
	 * @return The Investor's Non-Financial Income Settings
	 */

	public org.drip.portfolioconstruction.alm.ExpectedNonFinancialIncome nonFinancialIncome()
	{
		return _expectedNonFinancialIncome;
	}

	/**
	 * Retrieve the Basic After-Tax Income
	 * 
	 * @return The Basic After-Tax Income
	 */

	public double afterTaxIncome()
	{
		return _afterTaxIncome;
	}

	/**
	 * Generate the NetLiabilityMetrics Instance
	 * 
	 * @param startAge The Starting Age
	 * @param endAge The Ending Age
	 * @param discountRate The Discount Rate Instance
	 * 
	 * @return The NetLiabilityMetrics Instance
	 */

	public org.drip.portfolioconstruction.alm.NetLiabilityMetrics generateMetrics (
		final double startAge,
		final double endAge,
		final org.drip.portfolioconstruction.alm.DiscountRate discountRate)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (startAge) ||
			!org.drip.numerical.common.NumberUtil.IsValid (endAge) ||
			startAge >= endAge ||
			null == discountRate)
		{
			return null;
		}

		java.util.List<org.drip.portfolioconstruction.alm.NetLiabilityCashFlow> netLiabilityCashFlowList =
			new java.util.ArrayList<org.drip.portfolioconstruction.alm.NetLiabilityCashFlow>();

		double basicConsumptionPV = 0.;
		double workingAgeIncomePV = 0.;
		double pensionBenefitsIncomePV = 0.;

		for (double currentAge = startAge + 1.; currentAge <= endAge; ++currentAge)
		{
			double horizon = currentAge - startAge;

			try
			{
				boolean isAlive = _investorCliffSettings.isAlive (currentAge);

				double basicConsumptionDF = discountRate.basicConsumptionDF (horizon);

				double workingAgeIncomeDF = discountRate.workingAgeIncomeDF (horizon);

				boolean isRetirement = _investorCliffSettings.retirementIndicator (currentAge);

				double pensionBenefitsIncomeDF = discountRate.pensionBenefitsIncomeDF (horizon);

				double basicConsumption = _afterTaxIncome * _expectedBasicConsumption.rate (
					currentAge,
					_investorCliffSettings
				);

				double workingAgeIncome = !isRetirement && isAlive ?
					_afterTaxIncome * _expectedNonFinancialIncome.rate (
						currentAge,
						_investorCliffSettings
					) : 0.;

				double pensionBenefitsIncome = isRetirement && isAlive ?
					_afterTaxIncome * _expectedNonFinancialIncome.rate (
						currentAge,
						_investorCliffSettings
					) : 0.;

				netLiabilityCashFlowList.add (
					new org.drip.portfolioconstruction.alm.NetLiabilityCashFlow (
						currentAge,
						isRetirement,
						isAlive,
						horizon,
						_afterTaxIncome,
						workingAgeIncome,
						pensionBenefitsIncome,
						basicConsumption,
						workingAgeIncomeDF,
						pensionBenefitsIncomeDF,
						basicConsumptionDF
					)
				);

				basicConsumptionPV += basicConsumption * basicConsumptionDF;
				workingAgeIncomePV += workingAgeIncome * workingAgeIncomeDF;
				pensionBenefitsIncomePV += pensionBenefitsIncome * pensionBenefitsIncomeDF;
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		try
		{
			return new org.drip.portfolioconstruction.alm.NetLiabilityMetrics (
				netLiabilityCashFlowList,
				workingAgeIncomePV,
				pensionBenefitsIncomePV,
				basicConsumptionPV
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
