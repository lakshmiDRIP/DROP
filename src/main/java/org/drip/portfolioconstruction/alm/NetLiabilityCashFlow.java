
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
 * <i>NetLiabilityCashFlow</i> holds the Investor Time Snap's Singular Liability Flow Details.
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblAge = dblAge) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblHorizon = dblHorizon) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblAfterTaxIncome = dblAfterTaxIncome) ||
					!org.drip.numerical.common.NumberUtil.IsValid (_dblWorkingAgeIncome = dblWorkingAgeIncome) ||
						!org.drip.numerical.common.NumberUtil.IsValid (_dblPensionBenefits = dblPensionBenefits)
							|| !org.drip.numerical.common.NumberUtil.IsValid (_dblBasicConsumption =
								dblBasicConsumption) || !org.drip.numerical.common.NumberUtil.IsValid
									(_dblWorkingAgeIncomeDF = dblWorkingAgeIncomeDF) ||
										!org.drip.numerical.common.NumberUtil.IsValid (_dblPensionBenefitsDF =
											dblPensionBenefitsDF) ||
												!org.drip.numerical.common.NumberUtil.IsValid
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
