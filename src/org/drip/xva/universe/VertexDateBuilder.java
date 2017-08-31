
package org.drip.xva.universe;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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

public class VertexDateBuilder {

	/**
	 * Construct an Array of JulianDate from the Spot Date and the Vertex Tenor Array
	 * 
	 * @param iSpotDate The Spot Date
	 * @param astrVertexTenor The Vertex Tenor Array
	 * 
	 * @return The Array of JulianDate
	 */

	public static final int[] FromStartTenorArray (
		final int iSpotDate,
		final java.lang.String[] astrVertexTenor)
	{
		if (0 >= iSpotDate || null == astrVertexTenor) return null;

		int iNumVertex = astrVertexTenor.length;
		int[] aiDate = 0 == iNumVertex ? null : new int[iNumVertex];

		if (0 == iNumVertex) return null;

		org.drip.analytics.date.JulianDate dtSpot = new org.drip.analytics.date.JulianDate (iSpotDate);

		for (int iVertex = 0; iVertex < iNumVertex; ++iVertex) {
			org.drip.analytics.date.JulianDate dtVertex = dtSpot.addTenor (astrVertexTenor[iVertex]);

			if (null == dtVertex) return null;

			aiDate[iVertex] = dtVertex.julian();
		}

		return aiDate;
	}

	/**
	 * Construct an Array of Vertex Dates from the Spot Date, Tenor Spacing Width, and the Vertex Count
	 * 
	 * @param iSpotDate The Spot Date
	 * @param strTenorWidth The Tenor Spacing Width
	 * @param iNumVertex The Number of Vertexes
	 * 
	 * @return The Array of Vertex Dates
	 */

	public static final int[] FromTenorWidth (
		final int iSpotDate,
		final java.lang.String strTenorWidth,
		final int iNumVertex)
	{
		if (0 >= iSpotDate || 0 >= iNumVertex) return null;

		int[] aiDate = new int[iNumVertex];

		org.drip.analytics.date.JulianDate dtPrevious = new org.drip.analytics.date.JulianDate (iSpotDate);

		for (int iVertex = 0; iVertex < iNumVertex; ++iVertex) {
			org.drip.analytics.date.JulianDate dtVertex = dtPrevious.addTenor (strTenorWidth);

			if (null == dtVertex) return null;

			aiDate[iVertex] = dtVertex.julian();

			dtPrevious = dtVertex;
		}

		return aiDate;
	}

	/**
	 * Generate Equal Width Vertex Dates from the specified Spot Date
	 * 
	 * @param iSpotDate The Spot Date
	 * @param iFinalDate The Final Date
	 * @param iNumVertex The Number of Vertexes
	 * 
	 * @return Array of Equal Width Vertex Dates
	 */

	public static final int[] EqualWidth (
		final int iSpotDate,
		final int iFinalDate,
		final int iNumVertex)
	{
		if (0 >= iSpotDate || iFinalDate <= iSpotDate || 0 >= iNumVertex) return null;

		int[] aiDate = new int[iNumVertex];
		aiDate[iNumVertex - 1] = iFinalDate;
		double dblWidth = ((double) (iFinalDate - iSpotDate)) / iNumVertex;

		for (int iVertex = 0; iVertex < iNumVertex - 1; ++iVertex) {
			aiDate[iVertex] = iSpotDate + (int) ((iVertex + 1) * dblWidth);
		}

		return aiDate;
	}
}
