
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
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/asset">Asset Component</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/AssetAllocation">Asset Allocation Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PortfolioMetrics {
	private double[] _adblImpliedBeta = null;
	private double _dblSharpeRatio = java.lang.Double.NaN;
	private double _dblExcessReturnsMean = java.lang.Double.NaN;
	private double _dblExcessReturnsVariance = java.lang.Double.NaN;
	private double _dblExcessReturnsStandardDeviation = java.lang.Double.NaN;

	/**
	 * PortfolioMetrics Constructor
	 * 
	 * @param dblExcessReturnsMean The Expected Portfolio Excess Returns Mean
	 * @param dblExcessReturnsVariance The Portfolio Excess Returns Variance
	 * @param dblExcessReturnsStandardDeviation The Excess Returns Portfolio Standard Deviation
	 * @param dblSharpeRatio Portfolio Sharpe Ratio
	 * @param adblImpliedBeta Portfolio Implied Beta Vector
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PortfolioMetrics (
		final double dblExcessReturnsMean,
		final double dblExcessReturnsVariance,
		final double dblExcessReturnsStandardDeviation,
		final double dblSharpeRatio,
		final double[] adblImpliedBeta)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblExcessReturnsMean = dblExcessReturnsMean) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblExcessReturnsVariance = dblExcessReturnsVariance)
				|| !org.drip.quant.common.NumberUtil.IsValid (_dblExcessReturnsStandardDeviation =
					dblExcessReturnsStandardDeviation)|| !org.drip.quant.common.NumberUtil.IsValid
						(_dblSharpeRatio = dblSharpeRatio) || null == (_adblImpliedBeta = adblImpliedBeta) ||
							0 == _adblImpliedBeta.length)
			throw new java.lang.Exception ("PortfolioMetrics Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Portfolio Expected Excess Returns
	 * 
	 * @return The Portfolio Expected Excess Returns
	 */

	public double excessReturnsMean()
	{
		return _dblExcessReturnsMean;
	}

	/**
	 * Retrieve the Portfolio Excess Returns Variance
	 * 
	 * @return The Portfolio Excess Returns Variance
	 */

	public double excessReturnsVariance()
	{
		return _dblExcessReturnsVariance;
	}

	/**
	 * Retrieve the Portfolio Excess Returns Standard Deviation
	 * 
	 * @return The Portfolio Excess Returns Standard Deviation
	 */

	public double excessReturnsStandardDeviation()
	{
		return _dblExcessReturnsStandardDeviation;
	}

	/**
	 * Retrieve the Portfolio Sharpe Ratio
	 * 
	 * @return The Portfolio Sharpe Ratio
	 */

	public double sharpeRatio()
	{
		return _dblSharpeRatio;
	}

	/**
	 * Retrieve the Portfolio Implied Beta Vector
	 * 
	 * @return The Portfolio Implied Beta Vector
	 */

	public double[] impliedBeta()
	{
		return _adblImpliedBeta;
	}
}
