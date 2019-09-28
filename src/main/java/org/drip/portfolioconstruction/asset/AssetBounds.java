
package org.drip.portfolioconstruction.asset;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>AssetBounds</i> holds the Upper/Lower Bounds on an Asset.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/asset">Asset</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class AssetBounds
{
	private double _lower = java.lang.Double.NaN;
	private double _upper = java.lang.Double.NaN;

	/**
	 * AssetBounds Constructor
	 * 
	 * @param lower The Asset Lower Bound
	 * @param upper The Asset Upper Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AssetBounds (
		final double lower,
		final double upper)
		throws java.lang.Exception
	{
		_lower = lower;
		_upper = upper;

		if (org.drip.numerical.common.NumberUtil.IsValid (_lower) &&
			org.drip.numerical.common.NumberUtil.IsValid (_upper) && _lower >= _upper)
		{
			throw new java.lang.Exception ("AssetBounds Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Lower Bound
	 * 
	 * @return The Lower Bound
	 */

	public double lower()
	{
		return _lower;
	}

	/**
	 * Retrieve the Upper Bound
	 * 
	 * @return The Upper Bound
	 */

	public double upper()
	{
		return _upper;
	}

	/**
	 * Retrieve a Viable Feasible Starting Point
	 * 
	 * @return A Viable Feasible Starting Point
	 */

	public double feasibleStart()
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_lower) &&
			!org.drip.numerical.common.NumberUtil.IsValid (_upper))
		{
			return 0.;
		}

		if (!org.drip.numerical.common.NumberUtil.IsValid (_lower))
		{
			return 0.5 * _upper;
		}

		if (!org.drip.numerical.common.NumberUtil.IsValid (_upper))
		{
			return 2.0 * _lower;
		}

		return 0.5 * (_lower + _upper);
	}

	/**
	 * Localize the Variate Value to within the Bounds
	 * 
	 * @param variate The Variate Value
	 * 
	 * @return The Localized Variate Value
	 * 
	 * @throws java.lang.Exception Thrown if the Input is Invalid
	 */

	public double localize (
		final double variate)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (variate))
		{
			throw new java.lang.Exception ("AssetBounds::localize => Invalid Inputs");
		}

		if (!org.drip.numerical.common.NumberUtil.IsValid (_lower) &&
			!org.drip.numerical.common.NumberUtil.IsValid (_upper))
		{
			return variate;
		}

		if (org.drip.numerical.common.NumberUtil.IsValid (_lower) && variate < _lower)
		{
			return _lower;
		}

		if (org.drip.numerical.common.NumberUtil.IsValid (_upper) && variate > _upper)
		{
			return _upper;
		}

		return variate;
	}
}
