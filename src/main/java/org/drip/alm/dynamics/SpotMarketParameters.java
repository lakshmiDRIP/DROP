
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
 * <i>SpotMarketParameters</i> contains the Spot Market Parameters. The References are:
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

public class SpotMarketParameters
{
	private double _maturingAssetPrice = java.lang.Double.NaN;
	private double _nonMaturingAssetPrice = java.lang.Double.NaN;
	private double _forwardYieldLowerBound = java.lang.Double.NaN;
	private double _nonMaturingAssetAnnualReturn = java.lang.Double.NaN;
	private double _maturingAssetAnnualVolatility = java.lang.Double.NaN;
	private double _nonMaturingAssetAnnualVolatility = java.lang.Double.NaN;

	/**
	 * SpotMarketParameters Constructor
	 * 
	 * @param maturingAssetPrice Maturing Asset Price
	 * @param maturingAssetAnnualVolatility Maturing Asset Annual Volatility
	 * @param forwardYieldLowerBound Forward Yield Lower Bound
	 * @param nonMaturingAssetPrice Non-maturing Asset Price
	 * @param nonMaturingAssetAnnualReturn Non-maturing Asset Annual Return
	 * @param nonMaturingAssetAnnualVolatility Non-maturing Asset Annual Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SpotMarketParameters (
		final double maturingAssetPrice,
		final double maturingAssetAnnualVolatility,
		final double forwardYieldLowerBound,
		final double nonMaturingAssetPrice,
		final double nonMaturingAssetAnnualReturn,
		final double nonMaturingAssetAnnualVolatility)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_maturingAssetPrice = maturingAssetPrice) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_maturingAssetAnnualVolatility =
				maturingAssetAnnualVolatility) || 0. > _maturingAssetAnnualVolatility ||
			!org.drip.numerical.common.NumberUtil.IsValid (_forwardYieldLowerBound = forwardYieldLowerBound) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_nonMaturingAssetPrice = nonMaturingAssetPrice) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_nonMaturingAssetAnnualReturn =
				nonMaturingAssetAnnualReturn) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_nonMaturingAssetAnnualVolatility =
				nonMaturingAssetAnnualVolatility) || 0. > _nonMaturingAssetAnnualVolatility)
		{
			throw new java.lang.Exception ("SpotMarketParameters Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Maturing Asset Price
	 * 
	 * @return Maturing Asset Price
	 */

	public double maturingAssetPrice()
	{
		return _maturingAssetPrice;
	}

	/**
	 * Retrieve the Maturing Asset Annual Volatility
	 * 
	 * @return Maturing Asset Annual Volatility
	 */

	public double maturingAssetAnnualVolatility()
	{
		return _maturingAssetAnnualVolatility;
	}

	/**
	 * Retrieve the Forward Yield Lower Bound
	 * 
	 * @return Forward Yield Lower Bound
	 */

	public double forwardYieldLowerBound()
	{
		return _forwardYieldLowerBound;
	}

	/**
	 * Retrieve the Non-maturing Asset Price
	 * 
	 * @return Non-maturing Asset Price
	 */

	public double nonMaturingAssetPrice()
	{
		return _nonMaturingAssetPrice;
	}

	/**
	 * Retrieve the Non-maturing Asset Annual Returns
	 * 
	 * @return Non-maturing Asset Annual Returns
	 */

	public double nonMaturingAssetAnnualReturn()
	{
		return _nonMaturingAssetAnnualReturn;
	}

	/**
	 * Retrieve the Non-maturing Asset Annual Volatility
	 * 
	 * @return Non-maturing Asset Annual Volatility
	 */

	public double nonMaturingAssetAnnualVolatility()
	{
		return _nonMaturingAssetAnnualVolatility;
	}
}
