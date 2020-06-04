
package org.drip.service.common;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>FormatUtil</i> implements formatting utility functions. Currently it just exports functions to pad and
 * 	format.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/common">Assorted Data Structures Support Utilities</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FormatUtil {

	/**
	 * Pre-pad a single digit integer with zeros
	 * 
	 * @param i Integer representing the input
	 * 
	 * @return String representing the padded output
	 */

	public static final java.lang.String PrePad (
		final int i)
	{
		if (i > 9) return "" + i;

		return "0" + i;
	}

	/**
	 * Format the double input by multiplying, and then adding left and right adjustments
	 * 
	 * @param dblValue Double representing the input
	 * @param iNumLeft Integer representing the number of left justifying zeros
	 * @param iNumRight Integer representing the number of right justifying zeros
	 * @param dblMultiplier Double representing the multiplier
	 * @param bLeadingSpaceForPositive TRUE - A Leading Space will be emitted for Adjusted Positive Numbers.
	 * 		For Adjusted Negatives this will be the '-' sign.
	 * 
	 * @return String representing the formatted input
	 */

	public static final java.lang.String FormatDouble (
		final double dblValue,
		final int iNumLeft,
		final int iNumRight,
		final double dblMultiplier,
		final boolean bLeadingSpaceForPositive)
	{
		java.lang.String strFormat = "#";
		java.lang.String strLeading = "";
		double dblAdjustedValue = dblMultiplier * dblValue;

		if (0 <= dblAdjustedValue && bLeadingSpaceForPositive) strLeading = " ";

		for (int i = 0; i < iNumLeft; ++i)
			strFormat += "0";

		if (0 != iNumRight) {
			strFormat += ".";

			for (int i = 0; i < iNumRight; ++i)
				strFormat += "0";
		}

		return strLeading + new java.text.DecimalFormat (strFormat).format (dblAdjustedValue);
	}

	/**
	 * Format the double input by multiplying, and then adding left and right adjustments
	 * 
	 * @param dblValue Double representing the input
	 * @param iNumLeft Integer representing the number of left justifying zeros
	 * @param iNumRight Integer representing the number of right justifying zeros
	 * @param dblMultiplier Double representing the multiplier
	 * 
	 * @return String representing the formatted input
	 */

	public static final java.lang.String FormatDouble (
		final double dblValue,
		final int iNumLeft,
		final int iNumRight,
		final double dblMultiplier)
	{
		return FormatDouble (dblValue, iNumLeft, iNumRight, dblMultiplier, true);
	}
}
