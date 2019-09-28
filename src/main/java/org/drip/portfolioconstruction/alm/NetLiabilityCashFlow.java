
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

public class NetLiabilityCashFlow
{
	private boolean _isAlive = false;
	private boolean _isRetired = false;
	private double _age = java.lang.Double.NaN;
	private double _horizon = java.lang.Double.NaN;
	private double _afterTaxIncome = java.lang.Double.NaN;
	private double _pensionBenefits = java.lang.Double.NaN;
	private double _basicConsumption = java.lang.Double.NaN;
	private double _workingAgeIncome = java.lang.Double.NaN;
	private double _pensionBenefitsDF = java.lang.Double.NaN;
	private double _basicConsumptionDF = java.lang.Double.NaN;
	private double _workingAgeIncomeDF = java.lang.Double.NaN;

	/**
	 * NetLiabilityCashFlow Constructor
	 * 
	 * @param age The Investor Age
	 * @param isRetired The Retirement Indicator Flag
	 * @param isAlive The "Is Alive" Indicator Flag
	 * @param horizon The Snapshot's Investment Horizon
	 * @param afterTaxIncome The Basic After-Tax Income
	 * @param workingAgeIncome The Investor Working Age Income
	 * @param pensionBenefits The Investor Pension Benefits
	 * @param basicConsumption The Investor Basic Consumption
	 * @param workingAgeIncomeDF The Investor Working Age Income Discount Factor
	 * @param pensionBenefitsDF The Investor Pension Benefits Discount Factor
	 * @param basicConsumptionDF The Investor Basic Consumption Discount Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public NetLiabilityCashFlow (
		final double age,
		final boolean isRetired,
		final boolean isAlive,
		final double horizon,
		final double afterTaxIncome,
		final double workingAgeIncome,
		final double pensionBenefits,
		final double basicConsumption,
		final double workingAgeIncomeDF,
		final double pensionBenefitsDF,
		final double basicConsumptionDF)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_age = age) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_horizon = horizon) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_afterTaxIncome = afterTaxIncome) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_workingAgeIncome = workingAgeIncome) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_pensionBenefits = pensionBenefits) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_basicConsumption = basicConsumption) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_workingAgeIncomeDF = workingAgeIncomeDF) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_pensionBenefitsDF = pensionBenefitsDF) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_basicConsumptionDF = basicConsumptionDF))
		{
			throw new java.lang.Exception ("NetLiabilityCashFlow Constructor => Invalid Inputs");
		}

		_isAlive = isAlive;
		_isRetired = isRetired;
	}

	/**
	 * Retrieve the Investor Age
	 * 
	 * @return The Investor Age
	 */

	public double age()
	{
		return _age;
	}

	/**
	 * Retrieve the Retirement Indicator Flag
	 * 
	 * @return The Retirement Indicator Flag
	 */

	public boolean isRetired()
	{
		return _isRetired;
	}

	/**
	 * Retrieve the "Is Alive" Indicator Flag
	 * 
	 * @return The "Is Alive" Indicator Flag
	 */

	public boolean isAlive()
	{
		return _isAlive;
	}

	/**
	 * Retrieve the Snapshot's Investment Horizon
	 * 
	 * @return The Snapshot's Investment Horizon
	 */

	public double horizon()
	{
		return _horizon;
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
	 * Retrieve the Investor Working Age Income
	 * 
	 * @return The Investor Working Age Income
	 */

	public double workingAgeIncome()
	{
		return _workingAgeIncome;
	}

	/**
	 * Retrieve the Investor Pension Benefits
	 * 
	 * @return The Investor Pension Benefits
	 */

	public double pensionBenefits()
	{
		return _pensionBenefits;
	}

	/**
	 * Retrieve the Investor Basic Consumption
	 * 
	 * @return The Investor Basic Consumption
	 */

	public double basicConsumption()
	{
		return _basicConsumption;
	}

	/**
	 * Retrieve the Investor Working Age Income Discount Factor
	 * 
	 * @return The Investor Working Age Income Discount Factor
	 */

	public double workingAgeIncomeDF()
	{
		return _workingAgeIncomeDF;
	}

	/**
	 * Retrieve the Investor Pension Benefits Discount Factor
	 * 
	 * @return The Investor Pension Benefits Discount Factor
	 */

	public double pensionBenefitsDF()
	{
		return _pensionBenefitsDF;
	}

	/**
	 * Retrieve the Investor Basic Consumption Discount Factor
	 * 
	 * @return The Investor Basic Consumption Discount Factor
	 */

	public double basicConsumptionDF()
	{
		return _basicConsumptionDF;
	}
}
