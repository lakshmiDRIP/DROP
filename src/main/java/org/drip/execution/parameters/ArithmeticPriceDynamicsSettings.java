
package org.drip.execution.parameters;

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
 * <i>ArithmeticPriceDynamicsSettings</i> contains the Arithmetic Price Evolution Dynamics Parameters used in
 * the Almgren and Chriss (2000) Optimal Trajectory Generation Scheme. The References are:
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
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 * 				Markets</i> <b>1</b> 1-50
 * 		</li>
 * 		<li>
 * 			Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional
 * 				Trades <i>Journal of Finance</i> <b>50</b> 1147-1174
 * 		</li>
 * 		<li>
 * 			Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange
 * 				Analysis of Institutional Equity Trades <i>Journal of Financial Economics</i> <b>46</b>
 * 				265-292
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Execution</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/parameters/README.md">Parameters</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ArithmeticPriceDynamicsSettings {
	private double _dblDrift = java.lang.Double.NaN;
	private double _dblSerialCorrelation = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _r1ToR1Volatility = null;

	/**
	 * Construct the Asset Dynamics Settings from the Annual Returns Parameters
	 * 
	 * @param dblAnnualReturnsExpectation The Asset Annual Expected Returns
	 * @param dblAnnualReturnsVolatility The Asset Annual Returns Volatility
	 * @param dblSerialCorrelation The Asset Serial Correlation
	 * @param dblPrice The Asset Price
	 * 
	 * @return The Asset Dynamics Settings from the Annual Returns Parameters
	 */

	public static final ArithmeticPriceDynamicsSettings FromAnnualReturnsSettings (
		final double dblAnnualReturnsExpectation,
		final double dblAnnualReturnsVolatility,
		final double dblSerialCorrelation,
		final double dblPrice)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblAnnualReturnsExpectation) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblAnnualReturnsVolatility) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblPrice))
			return null;

		try {
			return new ArithmeticPriceDynamicsSettings (dblPrice * dblAnnualReturnsExpectation / 250., new
				org.drip.function.r1tor1.FlatUnivariate (dblPrice * dblAnnualReturnsVolatility /
					java.lang.Math.sqrt (250.)), dblSerialCorrelation);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ArithmeticPriceDynamicsSettings Constructor
	 * 
	 * @param dblDrift The Asset Daily Arithmetic Drift
	 * @param r1ToR1Volatility The R^1 To R^1 Volatility Function
	 * @param dblSerialCorrelation The Asset Serial Correlation
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ArithmeticPriceDynamicsSettings (
		final double dblDrift,
		final org.drip.function.definition.R1ToR1 r1ToR1Volatility,
		final double dblSerialCorrelation)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblDrift = dblDrift) || null == (_r1ToR1Volatility =
			r1ToR1Volatility)|| !org.drip.quant.common.NumberUtil.IsValid (_dblSerialCorrelation =
				dblSerialCorrelation) || 1. < _dblSerialCorrelation || -1. > _dblSerialCorrelation)
			throw new java.lang.Exception ("ArithmeticPriceDynamicsSettings Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Asset Annual Logarithmic Drift
	 *  
	 * @return The Asset Annual Logarithmic Drift
	 */

	public double drift()
	{
		return _dblDrift;
	}

	/**
	 * Retrieve the Asset Annual Volatility
	 *  
	 * @return The Asset Annual Volatility
	 * 
	 * @throws java.lang.Exception - Thrown if the Inputs are Invalid
	 */

	public double epochVolatility()
		throws java.lang.Exception
	{
		return _r1ToR1Volatility.evaluate (0.);
	}

	/**
	 * Retrieve the Asset Annual Volatility Function
	 *  
	 * @return The Asset Annual Volatility Function
	 */

	public org.drip.function.definition.R1ToR1 volatilityFunction()
	{
		return _r1ToR1Volatility;
	}

	/**
	 * Retrieve the Asset Serial Correlation
	 *  
	 * @return The Asset Serial Correlation
	 */

	public double serialCorrelation()
	{
		return _dblSerialCorrelation;
	}
}
