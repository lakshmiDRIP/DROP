
package org.drip.execution.impact;

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
 * <i>ParticipationRatePower</i> implements a Power-Law Based Temporary/Permanent Market Impact Function
 * where the Price Change scales as a Power of the Trade Rate. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 * 		</li>
 * 		<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Almgren, R., and N. Chriss (2003): Optimal Execution with Nonlinear Impact Functions and Trading-
 * 				Enhanced Risk <i>Applied Mathematical Finance</i> <b>10 (1)</b> 1-18
 * 		</li>
 * 		<li>
 * 			Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102
 * 		</li>
 * 		<li>
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 * 				Markets</i> <b>1</b> 1-50
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution">Execution</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/impact">Impact</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ParticipationRatePower extends org.drip.execution.impact.TransactionFunctionPower {
	private double _dblConstant = java.lang.Double.NaN;
	private double _dblExponent = java.lang.Double.NaN;

	/**
	 * ParticipationRatePower Constructor
	 * 
	 * @param dblConstant The Market Impact Constant Parameter
	 * @param dblExponent The Market Impact Power Law Exponent
	 * 
	 * @throws java.lang.Exception Propagated up from R1ToR1
	 */

	public ParticipationRatePower (
		final double dblConstant,
		final double dblExponent)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblConstant = dblConstant) || 0. > _dblConstant ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblExponent = dblExponent) || 0. > _dblExponent)
			throw new java.lang.Exception ("ParticipationRatePower Constructor => Invalid Inputs");
	}

	@Override public double constant()
	{
		return _dblConstant;
	}

	@Override public double exponent()
	{
		return _dblExponent;
	}

	@Override public double regularize (
		final double dblTradeInterval)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblTradeInterval) || 0 >= dblTradeInterval)
			throw new java.lang.Exception ("ParticipationRatePower::regularize => Invalid Inputs");

		return 1. / dblTradeInterval;
	}

	@Override public double modulate (
		final double dblTradeInterval)
		throws java.lang.Exception
	{
		return 1.;
	}

	@Override public double evaluate  (
		final double dblTradeRate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblTradeRate))
			throw new java.lang.Exception ("ParticipationRatePower::evaluate => Invalid Inputs");

		return (dblTradeRate < 0. ? -1. : 1.) * _dblConstant * java.lang.Math.pow (java.lang.Math.abs
			(dblTradeRate), _dblExponent);
	}

	@Override public double derivative (
		final double dblTradeRate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (0 >= iOrder || !org.drip.quant.common.NumberUtil.IsValid (dblTradeRate))
			throw new java.lang.Exception ("ParticipationRatePower::derivative => Invalid Inputs");

		double dblCoefficient = 1.;

		for (int i = 0; i < iOrder; ++i)
			dblCoefficient = dblCoefficient * (_dblExponent - i);

		return (dblTradeRate < 0. ? -1. : 1.) * dblCoefficient * _dblConstant * java.lang.Math.pow
			(java.lang.Math.abs (dblTradeRate), _dblExponent - iOrder);
	}
}
