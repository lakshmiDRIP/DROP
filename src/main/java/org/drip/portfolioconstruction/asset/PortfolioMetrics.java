
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
 * <i>PortfolioMetrics</i> holds the Expected Portfolio Returns and the Standard Deviation.
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

public class PortfolioMetrics
{
	private double[] _impliedBetaArray = null;
	private double _sharpeRatio = java.lang.Double.NaN;
	private double _excessReturnsMean = java.lang.Double.NaN;
	private double _excessReturnsVariance = java.lang.Double.NaN;
	private double _excessReturnsStandardDeviation = java.lang.Double.NaN;

	/**
	 * PortfolioMetrics Constructor
	 * 
	 * @param excessReturnsMean The Expected Portfolio Excess Returns Mean
	 * @param excessReturnsVariance The Portfolio Excess Returns Variance
	 * @param excessReturnsStandardDeviation The Excess Returns Portfolio Standard Deviation
	 * @param sharpeRatio Portfolio Sharpe Ratio
	 * @param impliedBetaArray Portfolio Implied Beta Vector
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PortfolioMetrics (
		final double excessReturnsMean,
		final double excessReturnsVariance,
		final double excessReturnsStandardDeviation,
		final double sharpeRatio,
		final double[] impliedBetaArray)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_excessReturnsMean = excessReturnsMean) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_excessReturnsVariance = excessReturnsVariance) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_excessReturnsStandardDeviation =
				excessReturnsStandardDeviation) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_sharpeRatio = sharpeRatio) ||
			null == (_impliedBetaArray = impliedBetaArray) || 0 == _impliedBetaArray.length)
		{
			throw new java.lang.Exception ("PortfolioMetrics Constructor => Invalid Inputs!");
		}
	}

	/**
	 * Retrieve the Portfolio Expected Excess Returns
	 * 
	 * @return The Portfolio Expected Excess Returns
	 */

	public double excessReturnsMean()
	{
		return _excessReturnsMean;
	}

	/**
	 * Retrieve the Portfolio Excess Returns Variance
	 * 
	 * @return The Portfolio Excess Returns Variance
	 */

	public double excessReturnsVariance()
	{
		return _excessReturnsVariance;
	}

	/**
	 * Retrieve the Portfolio Excess Returns Standard Deviation
	 * 
	 * @return The Portfolio Excess Returns Standard Deviation
	 */

	public double excessReturnsStandardDeviation()
	{
		return _excessReturnsStandardDeviation;
	}

	/**
	 * Retrieve the Portfolio Sharpe Ratio
	 * 
	 * @return The Portfolio Sharpe Ratio
	 */

	public double sharpeRatio()
	{
		return _sharpeRatio;
	}

	/**
	 * Retrieve the Portfolio Implied Beta Vector
	 * 
	 * @return The Portfolio Implied Beta Vector
	 */

	public double[] impliedBeta()
	{
		return _impliedBetaArray;
	}
}
