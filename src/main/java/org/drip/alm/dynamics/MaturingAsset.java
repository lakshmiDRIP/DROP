
package org.drip.alm.dynamics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>MaturingAsset</i> implements the Maturing Asset and its Evolution. The References are:
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ALMAnalyticsLibrary.md">Asset Liability Management Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/alm/dynamics/README.md">ALM Dynamics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/alm/dynamics/README.md">ALM Portfolio Allocation and Evolution</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MaturingAsset extends org.drip.alm.dynamics.EvolvableAsset
{
	private java.lang.String _maturityTenor = "";

	/**
	 * MaturingAsset Constructor
	 * 
	 * @param id Asset ID
	 * @param amount Asset Amount
	 * @param maturityTenor Maturity Tenor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MaturingAsset (
		final java.lang.String id,
		final double amount,
		final java.lang.String maturityTenor)
		throws java.lang.Exception
	{
		super (
			id,
			amount
		);

		if (null == (_maturityTenor = maturityTenor))
		{
			throw new java.lang.Exception ("MaturingAsset Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Maturity Tenor
	 * 
	 * @return The Maturity Tenor
	 */

	public java.lang.String maturityTenor()
	{
		return _maturityTenor;
	}

	@Override public double[] realizePath (
		final org.drip.alm.dynamics.SpotMarketParameters spotMarketParameters,
		final int horizonTenorInMonths,
		final int evolutionTenorInMonths)
	{
		if (null == spotMarketParameters || horizonTenorInMonths < evolutionTenorInMonths)
		{
			return null;
		}

		int maturityInMonths = -1;
		int horizonPeriod = horizonTenorInMonths / evolutionTenorInMonths;

		try
		{
			maturityInMonths = org.drip.analytics.support.Helper.TenorToMonths (_maturityTenor);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		if (maturityInMonths < horizonTenorInMonths)
		{
			return null;
		}

		double firstPeriodPriceVolatility = spotMarketParameters.maturingAssetAnnualVolatility() *
			java.lang.Math.sqrt (evolutionTenorInMonths / 12.);

		double initialLogPrice = java.lang.Math.log (spotMarketParameters.maturingAssetPrice());

		double forwardYieldLowerBound = spotMarketParameters.forwardYieldLowerBound();

		double forwardYield = -1. * initialLogPrice / horizonPeriod;
		double[] priceTrajectory = new double[horizonPeriod + 1];
		priceTrajectory[0] = initialLogPrice;

		double holdings = amount();

		for (int periodIndex = 1; periodIndex <= horizonPeriod; ++periodIndex)
		{
			int periodsToMaturity = horizonPeriod - periodIndex;

			priceTrajectory[periodIndex] = priceTrajectory[periodIndex - 1] + forwardYield +
				firstPeriodPriceVolatility * java.lang.Math.sqrt (periodsToMaturity) *
				(java.lang.Math.random() - 0.5);

			double forwardPriceUpperBound = -1. * forwardYieldLowerBound * periodsToMaturity;

			if (priceTrajectory[periodIndex] > forwardPriceUpperBound)
			{
				priceTrajectory[periodIndex] = forwardPriceUpperBound;
			}

			if (horizonPeriod != periodIndex)
			{
				forwardYield = -1. * priceTrajectory[periodIndex] / periodsToMaturity;
			}
		}

		for (int periodIndex = 0; periodIndex <= horizonPeriod; ++periodIndex)
		{
			priceTrajectory[periodIndex] = holdings * java.lang.Math.exp (priceTrajectory[periodIndex]);
		}

		return priceTrajectory;
	}
}
