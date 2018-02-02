
package org.drip.analytics.support;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * VertexDateBuilder exports Static Functions that create Vertex Dates using different Schemes. The
 *  References are:
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs, Risk, 24 (12) 82-87.
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class VertexDateBuilder
{

	/**
	 * Construct an Array of Dates from the Spot Date and the Vertex Tenor Array
	 * 
	 * @param spotDate The Spot Date
	 * @param vertexTenorArray The Vertex Tenor Array
	 * 
	 * @return The Array of Dates
	 */

	public static final int[] SpotDateVertexTenor (
		final int spotDate,
		final java.lang.String[] vertexTenorArray)
	{
		if (0 >= spotDate || null == vertexTenorArray) return null;

		int vertexCount = vertexTenorArray.length;
		int[] vertexDateArray = 0 == vertexCount ? null : new int[vertexCount];

		if (0 == vertexCount) return null;

		org.drip.analytics.date.JulianDate spotDateJulian = new org.drip.analytics.date.JulianDate
			(spotDate);

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			org.drip.analytics.date.JulianDate vertexDateJulian = spotDateJulian.addTenor
				(vertexTenorArray[vertexIndex]);

			if (null == vertexDateJulian)
			{
				return null;
			}

			vertexDateArray[vertexIndex] = vertexDateJulian.julian();
		}

		return vertexDateArray;
	}

	/**
	 * Construct an Array of Vertex Dates from the Spot Date, Tenor Spacing Width, and the Vertex Count
	 * 
	 * @param spotDate The Spot Date
	 * @param periodTenor The Tenor Spacing Width
	 * @param vertexCount The Number of Vertexes
	 * 
	 * @return The Array of Vertex Dates
	 */

	public static final int[] SpotDatePeriodTenor (
		final int spotDate,
		final java.lang.String periodTenor,
		final int vertexCount)
	{
		if (0 >= spotDate || 0 >= vertexCount)
		{
			return null;
		}

		int[] vertexDateArray = new int[vertexCount];

		org.drip.analytics.date.JulianDate previousDateJulian = new org.drip.analytics.date.JulianDate
			(spotDate);

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			org.drip.analytics.date.JulianDate vertexDateJulian = previousDateJulian.addTenor (periodTenor);

			if (null == vertexDateJulian)
			{
				return null;
			}

			vertexDateArray[vertexIndex] = vertexDateJulian.julian();

			previousDateJulian = vertexDateJulian;
		}

		return vertexDateArray;
	}

	/**
	 * Generate Equal Width Vertex Dates from the specified Spot Date and the Terminal Date
	 * 
	 * @param spotDate The Spot Date
	 * @param terminalDate The Terminal Date
	 * @param vertexCount The Number of Vertexes
	 * 
	 * @return Array of Equal Width Vertex Dates
	 */

	public static final int[] EqualWidth (
		final int spotDate,
		final int terminalDate,
		final int vertexCount)
	{
		if (0 >= spotDate || terminalDate <= spotDate || 0 >= vertexCount)
		{
			return null;
		}

		int[] vertexDateArray = new int[vertexCount];
		vertexDateArray[vertexCount - 1] = terminalDate;
		double periodWidth = ((double) (terminalDate - spotDate)) / vertexCount;

		for (int vertexIndex = 0; vertexIndex < vertexCount - 1; ++vertexIndex)
		{
			vertexDateArray[vertexIndex] = spotDate + (int) ((vertexIndex + 1) * periodWidth);
		}

		return vertexDateArray;
	}
}
