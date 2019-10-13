
package org.drip.alm.dynamics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>AssetEvolver</i> implements the Monte Carlo Evolution of the Specified Asset. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Judd, K., L., F. Kubler, and K. Schmedders (2011): Bond Ladders and Optimal Portfolios
 * 				https://pdfs.semanticscholar.org/7c4e/3704ad9af6fbeca27c915b5f69eb0f717396.pdf <b>Schematic
 * 				Scholar</b>
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ALMAnalyticsLibrary.md">Asset Liability Management Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/alm/README.md">Asset Liability Management Analytics Functionality</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/alm/dynamics/README.md">ALM Portfolio Allocation and Evolution</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AssetEvolver
{
	private int _pathCount = -1;
	private java.lang.String _timeStepInTenor = "";
	private java.lang.String _timeHorizonInTenor = "";

	/**
	 * AssetEvolver Constructor
	 * 
	 * @param pathCount Count of the Number Paths
	 * @param timeStepInTenor Time Step Size in Tenor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AssetEvolver (
		final int pathCount,
		final java.lang.String timeStepInTenor,
		final java.lang.String timeHorizonInTenor)
		throws java.lang.Exception
	{
		if (0 >= (_pathCount = pathCount) ||
			null == (_timeStepInTenor = timeStepInTenor) || _timeStepInTenor.isEmpty() ||
			null == (_timeHorizonInTenor = timeHorizonInTenor) || _timeHorizonInTenor.isEmpty())
		{
			throw new java.lang.Exception ("AssetEvolver Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Count of the Number Paths
	 * 
	 * @return Count of the Number Paths
	 */

	public int pathCount()
	{
		return _pathCount;
	}

	/**
	 * Retrieve the Time Step Size in Tenor
	 * 
	 * @return Time Step Size in Tenor
	 */

	public java.lang.String timeStepInTenor()
	{
		return _timeStepInTenor;
	}

	/**
	 * Retrieve the Time Horizon in Tenor
	 * 
	 * @return Time Horizon in Tenor
	 */

	public java.lang.String timeHorizonInTenor()
	{
		return _timeHorizonInTenor;
	}

	/**
	 * Generate the Array of Evolution Tenors
	 * 
	 * @param evolutionTenorInMonths Evolution Tenor in Months
	 * 
	 * @return Array of Evolution Tenors
	 */

	public java.lang.String[] evolutionTenorArray (
		final int evolutionTenorInMonths)
	{
		int timeHorizonInMonths = -1;

		try
		{
			timeHorizonInMonths = org.drip.analytics.support.Helper.TenorToMonths (_timeHorizonInTenor) /
				evolutionTenorInMonths;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		java.lang.String[] evolutionTenorArray = new java.lang.String[timeHorizonInMonths + 1];
		evolutionTenorArray[0] = "0M";

		for (int periodIndex = 1; periodIndex <= timeHorizonInMonths; ++periodIndex)
		{
			evolutionTenorArray[periodIndex] = (periodIndex * evolutionTenorInMonths) + "M";
		}

		return evolutionTenorArray;
	}

	/**
	 * Simulate the Forward Price Path of the Asset
	 * 
	 * @param asset The Asset
	 * @param spotMarketParameters The Spot Market Parameters
	 * 
	 * @return Forward Price Path of the Asset
	 */

	public org.drip.alm.dynamics.EvolutionDigest simulate (
		final org.drip.alm.dynamics.EvolvableAsset asset,
		final org.drip.alm.dynamics.SpotMarketParameters spotMarketParameters)
	{
		if (null == asset || null == spotMarketParameters)
		{
			return null;
		}

		int timeStepInMonths = -1;
		int timeHorizonInMonths = -1;

		try
		{
			timeStepInMonths = org.drip.analytics.support.Helper.TenorToMonths (_timeStepInTenor);

			timeHorizonInMonths = org.drip.analytics.support.Helper.TenorToMonths (_timeHorizonInTenor);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		java.lang.String[] pathForwardTenorArray = evolutionTenorArray (timeStepInMonths);

		if (null == pathForwardTenorArray)
		{
			return null;
		}

		double[][] pathMarketValueGrid = new double[pathForwardTenorArray.length][_pathCount];

		for (int pathIndex = 0; pathIndex < _pathCount; ++pathIndex)
		{
			int forwardPriceIndex = 0;

			double[] pathMarketValueArray = asset.realizePath (
				spotMarketParameters,
				timeHorizonInMonths,
				timeStepInMonths
			);

			if (null == pathMarketValueArray)
			{
				return null;
			}

			for (double forwardMarketValue : pathMarketValueArray)
			{
				pathMarketValueGrid[forwardPriceIndex++][pathIndex] = forwardMarketValue;
			}
		}

		try
		{
			return new org.drip.alm.dynamics.EvolutionDigest (
				pathForwardTenorArray,
				pathMarketValueGrid
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
