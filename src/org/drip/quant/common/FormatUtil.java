
package org.drip.quant.common;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * FormatUtil implements formatting utility functions. Currently it just exports functions to pad and format.
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
