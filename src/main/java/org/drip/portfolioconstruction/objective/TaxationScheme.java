
package org.drip.portfolioconstruction.objective;

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
 * <i>TaxationScheme</i> exposes Taxation related Functionality.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective/README.md">Portfolio Construction Objective Term Suite</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public interface TaxationScheme
{

	/**
	 * Compute the Standard Net US Tax Gain
	 * 
	 * @param initialHoldingsArray The Initial Holdings Array
	 * @param finalHoldingsArray The Final Holdings Array
	 * 
	 * @return The Net Standard US Tax Gain
	 * 
	 * @throws java.lang.Exception Thrown if the Standard Net Tax Gain cannot be calculated
	 */

	public abstract double standardNetTaxGainUS (
		final double[] initialHoldingsArray,
		final double[] finalHoldingsArray)
		throws java.lang.Exception;

	/**
	 * Compute the Custom Net Tax Gain
	 * 
	 * @param initialHoldingsArray The Initial Holdings Array
	 * @param finalHoldingsArray The Final Holdings Array
	 * 
	 * @return The Custom Net Tax Gain
	 * 
	 * @throws java.lang.Exception Thrown if the Custom Net Tax Gain cannot be calculated
	 */

	public abstract double customNetTaxGain (
		final double[] initialHoldingsArray,
		final double[] finalHoldingsArray)
		throws java.lang.Exception;

	/**
	 * Compute the Custom Gross Tax Gain
	 * 
	 * @param initialHoldingsArray The Initial Holdings Array
	 * @param finalHoldingsArray The Final Holdings Array
	 * 
	 * @return The Custom Net Tax Gain
	 * 
	 * @throws java.lang.Exception Thrown if the Custom Gross Tax Gain cannot be calculated
	 */

	public abstract double customGrossTaxGain (
		final double[] initialHoldingsArray,
		final double[] finalHoldingsArray)
		throws java.lang.Exception;

	/**
	 * Compute the Custom Net Tax Loss
	 * 
	 * @param initialHoldingsArray The Initial Holdings Array
	 * @param finalHoldingsArray The Final Holdings Array
	 * 
	 * @return The Custom Net Tax Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Custom Net Tax Loss cannot be calculated
	 */

	public abstract double customNetTaxLoss (
		final double[] initialHoldingsArray,
		final double[] finalHoldingsArray)
		throws java.lang.Exception;

	/**
	 * Compute the Custom Gross Tax Loss
	 * 
	 * @param initialHoldingsArray The Initial Holdings Array
	 * @param finalHoldingsArray The Final Holdings Array
	 * 
	 * @return The Custom Net Tax Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Custom Gross Tax Loss cannot be calculated
	 */

	public abstract double customGrossTaxLoss (
		final double[] initialHoldingsArray,
		final double[] finalHoldingsArray)
		throws java.lang.Exception;

	/**
	 * Compute the Long Term Tax Gain
	 * 
	 * @param initialHoldingsArray The Initial Holdings Array
	 * @param finalHoldingsArray The Final Holdings Array
	 * 
	 * @return The Long Term Tax Gain
	 * 
	 * @throws java.lang.Exception Thrown if the Long Term Tax Gain cannot be calculated
	 */

	public abstract double longTermTaxGain (
		final double[] initialHoldingsArray,
		final double[] finalHoldingsArray)
		throws java.lang.Exception;

	/**
	 * Compute the Tax Liability
	 * 
	 * @param initialHoldingsArray The Initial Holdings Array
	 * @param finalHoldingsArray The Final Holdings Array
	 * 
	 * @return The Tax Liability
	 * 
	 * @throws java.lang.Exception Thrown if the Custom Net Tax Gain cannot be calculated
	 */

	public abstract double taxLiability (
		final double[] initialHoldingsArray,
		final double[] finalHoldingsArray)
		throws java.lang.Exception;
}
