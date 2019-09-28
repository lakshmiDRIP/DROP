
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
 * <i>EvolutionDigest</i> holds the generated Measures of a Portfolio Evolution. The References are:
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

public class EvolutionDigest
{
	private double[][] _pathForwardPriceGrid = null;
	private java.lang.String[] _pathForwardTenorArray = null;

	/**
	 * EvolutionDigest Constructor
	 * 
	 * @param pathForwardTenorArray Array of Path Forward Evolution Tenors
	 * @param pathForwardPriceGrid Grid of Path Forward Prices
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EvolutionDigest (
		final java.lang.String[] pathForwardTenorArray,
		final double[][] pathForwardPriceGrid)
		throws java.lang.Exception
	{
		if (null == (_pathForwardTenorArray = pathForwardTenorArray) ||
			null == (_pathForwardPriceGrid = pathForwardPriceGrid))
		{
			throw new java.lang.Exception ("EvolutionDigest Constructor => Invalid Inputs");
		}

		int forwardTenorCount = _pathForwardTenorArray.length;

		if (0 == forwardTenorCount || forwardTenorCount != _pathForwardPriceGrid.length)
		{
			throw new java.lang.Exception ("EvolutionDigest Constructor => Invalid Inputs");
		}

		for (int forwardTenorIndex = 0; forwardTenorIndex < forwardTenorCount; ++forwardTenorIndex)
		{
			if (null == pathForwardTenorArray[forwardTenorIndex] ||
				pathForwardTenorArray[forwardTenorIndex].isEmpty() ||
				null == _pathForwardPriceGrid[forwardTenorIndex] ||
				!org.drip.numerical.common.NumberUtil.IsValid (_pathForwardPriceGrid[forwardTenorIndex]))
			{
				throw new java.lang.Exception ("EvolutionDigest Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of Path Forward Evolution Tenors
	 * 
	 * @return Array of Path Forward Evolution Tenors
	 */

	public java.lang.String[] pathForwardTenorArray()
	{
		return _pathForwardTenorArray;
	}

	/**
	 * Retrieve the Grid of Path Forward Prices
	 * 
	 * @return Grid of Path Forward Prices
	 */

	public double[][] pathForwardPriceGrid()
	{
		return _pathForwardPriceGrid;
	}

	/**
	 * Generate the Thin Statistics corresponding to each Forward Tenor
	 * 
	 * @return Thin Statistics corresponding to each Forward Tenor
	 */

	public org.drip.measure.statistics.UnivariateDiscreteThin[] thinStatisticsArray()
	{
		int forwardTenorCount = _pathForwardTenorArray.length;
		org.drip.measure.statistics.UnivariateDiscreteThin[] thinStatisticsArray =
			new org.drip.measure.statistics.UnivariateDiscreteThin[forwardTenorCount];

		for (int forwardTenorIndex = 0; forwardTenorIndex < forwardTenorCount; ++forwardTenorIndex)
		{
			try
			{
				thinStatisticsArray[forwardTenorIndex] =
					new org.drip.measure.statistics.UnivariateDiscreteThin (
						_pathForwardPriceGrid[forwardTenorIndex]
					);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return thinStatisticsArray;
	}
}
