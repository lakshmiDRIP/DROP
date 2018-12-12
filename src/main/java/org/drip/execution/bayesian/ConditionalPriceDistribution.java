
package org.drip.execution.bayesian;

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
 * <i>ConditionalPriceDistribution</i> holds the Price Distribution Conditional on a given Drift. The
 * References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 * 				Markets</i> <b>1</b> 1-50
 * 		</li>
 * 		<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Brunnermeier, L. K., and L. H. Pedersen (2005): Predatory Trading <i>Journal of Finance</i> <b>60
 * 				(4)</b> 1825-1863
 * 		</li>
 * 		<li>
 * 			Almgren, R., and J. Lorenz (2006): Bayesian Adaptive Trading with a Daily Cycle <i>Journal of
 * 				Trading</i> <b>1 (4)</b> 38-46
 * 		</li>
 * 		<li>
 * 			Kissell, R., and R. Malamut (2007): Algorithmic Decision Making Framework <i>Journal of
 * 				Trading</i> <b>1 (1)</b> 12-21
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Execution</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/bayesian/README.md">Bayesian</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ConditionalPriceDistribution extends org.drip.measure.gaussian.R1UnivariateNormal {
	private double _dblTime = java.lang.Double.NaN;
	private double _dblPriceVolatility = java.lang.Double.NaN;
	private double _dblConditionalDrift = java.lang.Double.NaN;

	/**
	 * ConditionalPriceDistribution Constructor
	 * 
	 * @param dblConditionalDrift The Conditional Drift
	 * @param dblPriceVolatility The Price Volatility
	 * @param dblTime The Distribution Time Horizon
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ConditionalPriceDistribution (
		final double dblConditionalDrift,
		final double dblPriceVolatility,
		final double dblTime)
		throws java.lang.Exception
	{
		super (dblConditionalDrift * dblTime, dblPriceVolatility * java.lang.Math.sqrt (dblTime));

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblTime = dblTime) || 0. >= _dblTime ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblConditionalDrift = dblConditionalDrift) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblPriceVolatility = dblPriceVolatility))
			throw new java.lang.Exception ("ConditionalPriceDistribution Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Distribution Time Horizon
	 * 
	 * @return The Distribution Time Horizon
	 */

	public double time()
	{
		return _dblTime;
	}

	/**
	 * Retrieve the Distribution Price Volatility
	 * 
	 * @return The Distribution Price Volatility
	 */

	public double priceVolatility()
	{
		return _dblPriceVolatility;
	}

	/**
	 * Retrieve the Distribution Conditional Drift
	 * 
	 * @return The Distribution Conditional Drift
	 */

	public double conditionalDrift()
	{
		return _dblConditionalDrift;
	}

	/**
	 * Generate s Single Price Volatility Swings
	 * 
	 * @return The Price Volatility Swings
	 * 
	 * @throws java.lang.Exception Thrown if the Swing cannot be generated
	 */

	public double priceVolatilitySwing()
		throws java.lang.Exception
	{
		return _dblPriceVolatility * org.drip.measure.gaussian.NormalQuadrature.InverseCDF
			(java.lang.Math.random()) * java.lang.Math.sqrt (_dblTime);
	}

	/**
	 * Generate the given Number of Price Volatility Swings
	 * 
	 * @param iNumRealization The Number of Swings to be generated
	 * 
	 * @return Array of the Price Volatility Swings
	 */

	public double[] priceVolatilitySwings (
		final int iNumRealization)
	{
		if (0 >= iNumRealization) return null;

		double[] adblVolatilitySwings = new double[iNumRealization];

		double dblVolatilityTimeSQRT = _dblPriceVolatility * java.lang.Math.sqrt (_dblTime);

		for (int i = 0; i < iNumRealization; ++i) {
			try {
				adblVolatilitySwings[i] = org.drip.measure.gaussian.NormalQuadrature.InverseCDF
					(java.lang.Math.random()) * dblVolatilityTimeSQRT;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adblVolatilitySwings;
	}
}
