
package org.drip.portfolioconstruction.objective;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>TaxationScheme</i> exposes Taxation related Functionality.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/objective">Objective</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/AssetAllocation">Asset Allocation Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public interface TaxationScheme {

	/**
	 * Compute the Standard Net US Tax Gain
	 * 
	 * @param adblInitialHoldings The Initial Holdings Array
	 * @param adblFinalHoldings The Final Holdings Array
	 * 
	 * @return The Net Standard US Tax Gain
	 * 
	 * @throws java.lang.Exception Thrown if the Standard Net Tax Gain cannot be calculated
	 */

	public abstract double standardNetTaxGainUS (
		final double[] adblInitialHoldings,
		final double[] adblFinalHoldings)
		throws java.lang.Exception;

	/**
	 * Compute the Custom Net Tax Gain
	 * 
	 * @param adblInitialHoldings The Initial Holdings Array
	 * @param adblFinalHoldings The Final Holdings Array
	 * 
	 * @return The Custom Net Tax Gain
	 * 
	 * @throws java.lang.Exception Thrown if the Custom Net Tax Gain cannot be calculated
	 */

	public abstract double customNetTaxGain (
		final double[] adblInitialHoldings,
		final double[] adblFinalHoldings)
		throws java.lang.Exception;

	/**
	 * Compute the Custom Gross Tax Gain
	 * 
	 * @param adblInitialHoldings The Initial Holdings Array
	 * @param adblFinalHoldings The Final Holdings Array
	 * 
	 * @return The Custom Net Tax Gain
	 * 
	 * @throws java.lang.Exception Thrown if the Custom Gross Tax Gain cannot be calculated
	 */

	public abstract double customGrossTaxGain (
		final double[] adblInitialHoldings,
		final double[] adblFinalHoldings)
		throws java.lang.Exception;

	/**
	 * Compute the Custom Net Tax Loss
	 * 
	 * @param adblInitialHoldings The Initial Holdings Array
	 * @param adblFinalHoldings The Final Holdings Array
	 * 
	 * @return The Custom Net Tax Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Custom Net Tax Loss cannot be calculated
	 */

	public abstract double customNetTaxLoss (
		final double[] adblInitialHoldings,
		final double[] adblFinalHoldings)
		throws java.lang.Exception;

	/**
	 * Compute the Custom Gross Tax Loss
	 * 
	 * @param adblInitialHoldings The Initial Holdings Array
	 * @param adblFinalHoldings The Final Holdings Array
	 * 
	 * @return The Custom Net Tax Loss
	 * 
	 * @throws java.lang.Exception Thrown if the Custom Gross Tax Loss cannot be calculated
	 */

	public abstract double customGrossTaxLoss (
		final double[] adblInitialHoldings,
		final double[] adblFinalHoldings)
		throws java.lang.Exception;

	/**
	 * Compute the Long Term Tax Gain
	 * 
	 * @param adblInitialHoldings The Initial Holdings Array
	 * @param adblFinalHoldings The Final Holdings Array
	 * 
	 * @return The Long Term Tax Gain
	 * 
	 * @throws java.lang.Exception Thrown if the Long Term Tax Gain cannot be calculated
	 */

	public abstract double longTermTaxGain (
		final double[] adblInitialHoldings,
		final double[] adblFinalHoldings)
		throws java.lang.Exception;

	/**
	 * Compute the Tax Liability
	 * 
	 * @param adblInitialHoldings The Initial Holdings Array
	 * @param adblFinalHoldings The Final Holdings Array
	 * 
	 * @return The Tax Liability
	 * 
	 * @throws java.lang.Exception Thrown if the Custom Net Tax Gain cannot be calculated
	 */

	public abstract double taxLiability (
		final double[] adblInitialHoldings,
		final double[] adblFinalHoldings)
		throws java.lang.Exception;
}
