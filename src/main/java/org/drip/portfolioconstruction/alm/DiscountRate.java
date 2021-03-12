
package org.drip.portfolioconstruction.alm;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ALMAnalyticsLibrary.md">Asset Liability Management Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/alm/README.md">Sharpe-Tint Asset Liability Manager</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DiscountRate
{
	private double _yield = java.lang.Double.NaN;
	private double _basicConsumptionSpread = java.lang.Double.NaN;
	private double _workingAgeIncomeSpread = java.lang.Double.NaN;
	private double _pensionBenefitsIncomeSpread = java.lang.Double.NaN;

	/**
	 * DiscountRate Constructor
	 * 
	 * @param yield The Base Discounting Yield
	 * @param workingAgeIncomeSpread The Working Age Income Spread
	 * @param pensionBenefitsIncomeSpread The Pension Benefits Income Spread
	 * @param basicConsumptionSpread The Basic Consumption Spread
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DiscountRate (
		final double yield,
		final double workingAgeIncomeSpread,
		final double pensionBenefitsIncomeSpread,
		final double basicConsumptionSpread)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_yield = yield) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_workingAgeIncomeSpread =
				workingAgeIncomeSpread) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_basicConsumptionSpread =
				basicConsumptionSpread) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_pensionBenefitsIncomeSpread =
				pensionBenefitsIncomeSpread))
		{
			throw new java.lang.Exception ("DiscountRate Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Base Discounting Yield
	 * 
	 * @return The Base Discounting Yield
	 */

	public double yield()
	{
		return _yield;
	}

	/**
	 * Retrieve the Working Age Income Spread
	 * 
	 * @return The Working Age Income Spread
	 */

	public double workingAgeIncomeSpread()
	{
		return _workingAgeIncomeSpread;
	}

	/**
	 * Retrieve the Working Age Income Discount Rate
	 * 
	 * @return The Working Age Income Discount Rate
	 */

	public double workingAgeIncomeRate()
	{
		return _yield + _workingAgeIncomeSpread;
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblHorizon))
		{
			throw new java.lang.Exception ("DiscountRate::workingAgeIncomeDF => Invalid Inputs");
		}

		return java.lang.Math.exp (-1. * dblHorizon * (_yield + _workingAgeIncomeSpread));
	}

	/**
	 * Retrieve the Pension Benefits Income Spread
	 * 
	 * @return The Pension Benefits Income Spread
	 */

	public double pensionBenefitsIncomeSpread()
	{
		return _pensionBenefitsIncomeSpread;
	}

	/**
	 * Retrieve the Pension Benefits Income Discount Rate
	 * 
	 * @return The Pension Benefits Income Discount Rate
	 */

	public double pensionBenefitsIncomeRate()
	{
		return _yield + _pensionBenefitsIncomeSpread;
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblHorizon))
		{
			throw new java.lang.Exception ("DiscountRate::pensionBenefitsIncomeDF => Invalid Inputs");
		}

		return java.lang.Math.exp (-1. * dblHorizon * (_yield + _pensionBenefitsIncomeSpread));
	}

	/**
	 * Retrieve the Basic Consumption Spread
	 * 
	 * @return The Basic Consumption Spread
	 */

	public double basicConsumptionSpread()
	{
		return _basicConsumptionSpread;
	}

	/**
	 * Retrieve the Basic Consumption Discount Rate
	 * 
	 * @return The Basic Consumption Discount Rate
	 */

	public double basicConsumptionRate()
	{
		return _yield + _basicConsumptionSpread;
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblHorizon))
		{
			throw new java.lang.Exception ("DiscountRate::basicConsumptionDF => Invalid Inputs");
		}

		return java.lang.Math.exp (-1. * dblHorizon * (_yield + _basicConsumptionSpread));
	}
}
