
package org.drip.execution.adaptive;

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
 * <i>CoordinatedVariationTrajectoryDeterminant</i> contains the HJB-based MultiStep Optimal Cost Dynamic
 * Trajectory Generation Metrics using the Coordinated Variation Version of the Stochastic Volatility and the
 * Transaction Function arising from the Realization of the Market State Variable as described in the
 * "Trading Time" Model. The References are:
 * 
 * 	<br><br>
 *  <ul>
 * 		<li>
 * 			Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 * 				https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf
 * 		</li>
 * 		<li>
 * 			Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility <i>SIAM Journal
 * 			of Financial Mathematics</i> <b>3 (1)</b> 163-181
 * 		</li>
 * 		<li>
 * 			Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes <i>Mathematical
 * 				Finance</i> <b>11 (1)</b> 79-96
 * 		</li>
 * 		<li>
 * 			Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility <i>Review of
 * 				Financial Studies</i> <b>7 (4)</b> 631-651
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Execution</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/adaptive/README.md">Adaptive</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CoordinatedVariationTrajectoryDeterminant {
	private double _dblOrderSize = java.lang.Double.NaN;
	private double _dblCostScale = java.lang.Double.NaN;
	private double _dblTimeScale = java.lang.Double.NaN;
	private double _dblMarketPower = java.lang.Double.NaN;
	private double _dblTradeRateScale = java.lang.Double.NaN;
	private double _dblMeanMarketUrgency = java.lang.Double.NaN;
	private double _dblNonDimensionalRiskAversion = java.lang.Double.NaN;

	/**
	 * CoordinatedVariationTrajectoryDeterminant Constructor
	 * 
	 * @param dblOrderSize The Order Size
	 * @param dblTimeScale The Time Scale
	 * @param dblCostScale The Cost Scale
	 * @param dblTradeRateScale The Trade Rate Scale
	 * @param dblMeanMarketUrgency The Mean Market Urgency
	 * @param dblNonDimensionalRiskAversion The Non Dimensional Risk Aversion Parameter
	 * @param dblMarketPower The Preference-free "Market Power" Parameter
	 * 
	 * @throws java.lang.Exception Thrown if the the Inputs are Invalid
	 */

	public CoordinatedVariationTrajectoryDeterminant (
		final double dblOrderSize,
		final double dblTimeScale,
		final double dblCostScale,
		final double dblTradeRateScale,
		final double dblMeanMarketUrgency,
		final double dblNonDimensionalRiskAversion,
		final double dblMarketPower)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblOrderSize = dblOrderSize) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblTimeScale = dblTimeScale) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblCostScale = dblCostScale) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblTradeRateScale = dblTradeRateScale) ||
						!org.drip.quant.common.NumberUtil.IsValid (_dblMeanMarketUrgency =
							dblMeanMarketUrgency) || !org.drip.quant.common.NumberUtil.IsValid
								(_dblNonDimensionalRiskAversion = dblNonDimensionalRiskAversion) ||
									!org.drip.quant.common.NumberUtil.IsValid (_dblMarketPower =
										dblMarketPower))
			throw new java.lang.Exception
				("CoordinatedVariationTrajectoryDeterminant Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Order Size
	 * 
	 * @return The Order Size
	 */

	public double orderSize()
	{
		return _dblOrderSize;
	}

	/**
	 * Retrieve the Time Scale
	 * 
	 * @return The Time Scale
	 */

	public double timeScale()
	{
		return _dblTimeScale;
	}

	/**
	 * Retrieve the Cost Scale
	 * 
	 * @return The Cost Scale
	 */

	public double costScale()
	{
		return _dblCostScale;
	}

	/**
	 * Retrieve the Trade Rate Scale
	 * 
	 * @return The Trade Rate Scale
	 */

	public double tradeRateScale()
	{
		return _dblTradeRateScale;
	}

	/**
	 * Retrieve the Mean Market Urgency
	 * 
	 * @return The Mean Market Urgency
	 */

	public double meanMarketUrgency()
	{
		return _dblMeanMarketUrgency;
	}

	/**
	 * Retrieve the Non Dimensional Risk Aversion Parameter
	 * 
	 * @return The Non Dimensional Risk Aversion Parameter
	 */

	public double nonDimensionalRiskAversion()
	{
		return _dblNonDimensionalRiskAversion;
	}

	/**
	 * Retrieve the Preference-free "Market Power" Parameter
	 * 
	 * @return The Preference-free "Market Power" Parameter
	 */

	public double marketPower()
	{
		return _dblMarketPower;
	}
}
