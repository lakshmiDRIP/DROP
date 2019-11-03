
package org.drip.portfolioconstruction.alm;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>InvestorCliffSettings</i> contains the Investor's Time Cliff Settings Parameters such as the Retirement
 * and the Mortality Ages.
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

public class InvestorCliffSettings
{

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

	private double _maximumAge = java.lang.Double.NaN;
	private double _retirementAge = java.lang.Double.NaN;

	/**
	 * InvestorCliffSettings Constructor
	 * 
	 * @param retirementAge The Investor Retirement Age
	 * @param maximumAge The Investor Maximum Age
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public InvestorCliffSettings (
		final double retirementAge,
		final double maximumAge)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_retirementAge = retirementAge) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_maximumAge = maximumAge) ||
			_retirementAge >= _maximumAge)
		{
			throw new java.lang.Exception ("InvestorCliffSettings Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Investor Retirement Age
	 * 
	 * @return The Investor Retirement Age
	 */

	public double retirementAge()
	{
		return _retirementAge;
	}

	/**
	 * Retrieve the Investor Maximum Age
	 * 
	 * @return The Investor Maximum Age
	 */

	public double maximumAge()
	{
		return _maximumAge;
	}

	/**
	 * Retrieve the Investment Phase corresponding to the specified Age
	 * 
	 * @param age The Age whose Investment Phase is needed
	 * 
	 * @return The Investment Phase
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public int phase (
		final double age)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (age))
		{
			throw new java.lang.Exception ("InvestorHorizon::phase => Invalid Inputs");
		}

		if (age <= _retirementAge)
		{
			return DATE_PHASE_BEFORE_RETIREMENT;
		}

		if (age <= _maximumAge)
		{
			return DATE_PHASE_AFTER_RETIREMENT;
		}

		return DATE_PHASE_AFTER_MORTALITY;
	}

	/**
	 * Retrieve the Investor Retirement Indicator Flag corresponding to the specified Age
	 * 
	 * @param age The Age whose Retirement Indicator is needed
	 * 
	 * @return TRUE - The Age indicates that the Investor has retired
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public boolean retirementIndicator (
		final double age)
		throws java.lang.Exception
	{
		return DATE_PHASE_BEFORE_RETIREMENT != phase (age);
	}

	/**
	 * Retrieve the Investor "Is Alive" Indicator Flag corresponding to the specified Age
	 * 
	 * @param age The Age whose "Is Alive" Indicator is needed
	 * 
	 * @return TRUE - The Age indicates that the Investor "Is Alive"
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public boolean isAlive (
		final double age)
		throws java.lang.Exception
	{
		return DATE_PHASE_AFTER_MORTALITY != phase (age);
	}
}
