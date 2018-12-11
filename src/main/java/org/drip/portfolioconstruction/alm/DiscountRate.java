
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
 * <i>DiscountRate</i> holds the Cash Flow Discount Rate Parameters for each Type, i.e., Discount Rates for
 * Working Age Income, Pension Benefits, and Basic Consumption.
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
