
package org.drip.service.common;

import java.text.DecimalFormat;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 * <i>FormatUtil</i> implements formatting utility functions. Currently it just exports functions to pad and
 * 	format. It implements the following Functions:
 * <ul>
 * 		<li>Pre-pad a single digit integer with zeros</li>
 * 		<li>Format the double input by multiplying, and then adding left and right adjustments</li>
 * 		<li>Format the double input by multiplying, and then adding left and right adjustments with leading Space</li>
 * </ul>
 * 
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/common/README.md">Assorted Data Structures Support Utilities</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FormatUtil
{

	/**
	 * Pre-pad a single digit integer with zeros
	 * 
	 * @param i Integer representing the input
	 * 
	 * @return String representing the padded output
	 */

	public static final String PrePad (
		final int i)
	{
		return 9 < i ? "" + i : "0" + i;
	}

	/**
	 * Format the double input by multiplying, and then adding left and right adjustments
	 * 
	 * @param doubleValue Double representing the input
	 * @param leftJustificationZeroes Integer representing the number of left justifying zeros
	 * @param rightJustificationZeroes Integer representing the number of right justifying zeros
	 * @param multiplier Double representing the multiplier
	 * @param leadingSpaceForPositive TRUE - A Leading Space will be emitted for Adjusted Positive Numbers.
	 * 	For Adjusted Negatives this will be the '-' sign.
	 * 
	 * @return String representing the formatted input
	 */

	public static final String FormatDouble (
		final double doubleValue,
		final int leftJustificationZeroes,
		final int rightJustificationZeroes,
		final double multiplier,
		final boolean leadingSpaceForPositive)
	{
		String formatString = "#";
		String leadingString = "";
		double adjustedValue = multiplier * doubleValue;

		if (0 <= adjustedValue && leadingSpaceForPositive) {
			leadingString = " ";
		}

		for (int i = 0; i < leftJustificationZeroes; ++i) {
			formatString += "0";
		}

		if (0 != rightJustificationZeroes) {
			formatString += ".";

			for (int i = 0; i < rightJustificationZeroes; ++i) {
				formatString += "0";
			}
		}

		return leadingString + new DecimalFormat (formatString).format (adjustedValue);
	}

	/**
	 * Format the R<sup>d</sup> Array into a Delimited String
	 * 
	 * @param rdArray R<sup>d</sup> Array
	 * @param leftJustificationZeroes Integer representing the number of left justifying zeros
	 * @param rightJustificationZeroes Integer representing the number of right justifying zeros
	 * @param multiplier Double representing the multiplier
	 * @param leadingSpaceForPositive TRUE - A Leading Space will be emitted for Adjusted Positive Numbers.
	 * 	For Adjusted Negatives this will be the '-' sign.
	 * 
	 * @return R<sup>d</sup> Array into a Delimited String
	 */

	public static final String FormatRd (
		final double[] rdArray,
		final int leftJustificationZeroes,
		final int rightJustificationZeroes,
		final double multiplier,
		final String delimiter,
		final boolean leadingSpaceForPositive)
	{
		String formattedRd = "";

		for (int i = 0; i < rdArray.length; ++i) {
			if (0 != i) {
				formattedRd += delimiter;
			}

			formattedRd += FormatDouble (
				rdArray[i],
				leftJustificationZeroes,
				rightJustificationZeroes,
				multiplier,
				leadingSpaceForPositive
			);
		}

		return formattedRd;
	}

	/**
	 * Format the double input by multiplying, and then adding left and right adjustments
	 * 
	 * @param doubleValue Double representing the input
	 * @param leftJustificationZeroes Integer representing the number of left justifying zeros
	 * @param rightJustificationZeroes Integer representing the number of right justifying zeros
	 * @param multiplier Double representing the multiplier
	 * 
	 * @return String representing the formatted input
	 */

	public static final String FormatDouble (
		final double doubleValue,
		final int leftJustificationZeroes,
		final int rightJustificationZeroes,
		final double multiplier)
	{
		return FormatDouble (
			doubleValue,
			leftJustificationZeroes,
			rightJustificationZeroes,
			multiplier,
			true
		);
	}

	/**
	 * Format the R<sup>d</sup> Array into a Delimited String
	 * 
	 * @param rdArray R<sup>d</sup> Array
	 * @param leftJustificationZeroes Integer representing the number of left justifying zeros
	 * @param rightJustificationZeroes Integer representing the number of right justifying zeros
	 * @param multiplier Double representing the multiplier
	 * 
	 * @return R<sup>d</sup> Array into a Delimited String
	 */

	public static final String FormatRd (
		final double[] rdArray,
		final int leftJustificationZeroes,
		final int rightJustificationZeroes,
		final double multiplier,
		final String delimiter)
	{
		String formattedRd = "";

		for (int i = 0; i < rdArray.length; ++i) {
			if (0 != i) {
				formattedRd += delimiter;
			}

			formattedRd += FormatDouble (
				rdArray[i],
				leftJustificationZeroes,
				rightJustificationZeroes,
				multiplier,
				true
			);
		}

		return formattedRd;
	}
}
