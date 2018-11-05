
package org.drip.execution.tradingtime;

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
 * <i>CoordinatedParticipationRateLinear</i> implements the Coordinated Variation Version of the Linear
 *  Participation Rate Transaction Function as described in the "Trading Time" Model. The References are:
 * 
 * 	<br>
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
 * 	<br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution">Execution</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/tradingtime">Trading Time</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/TransactionCost">Transaction Cost Analytics</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CoordinatedParticipationRateLinear implements
	org.drip.execution.profiletime.BackgroundParticipationRateLinear {
	private org.drip.function.definition.R1ToR1 _r1ToR1Volatility = null;
	private org.drip.execution.tradingtime.CoordinatedVariation _cv = null;

	/**
	 * CoordinatedParticipationRateLinear Constructor
	 * 
	 * @param cv The Coordinated Volatility/Liquidity Variation
	 * @param r1ToR1Volatility The R^1 To R^1 Volatility Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CoordinatedParticipationRateLinear (
		final org.drip.execution.tradingtime.CoordinatedVariation cv,
		final org.drip.function.definition.R1ToR1 r1ToR1Volatility)
		throws java.lang.Exception
	{
		if (null == (_cv = cv) || null == (_r1ToR1Volatility = r1ToR1Volatility))
			throw new java.lang.Exception
				("CoordinatedParticipationRateLinear Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Coordinated Variation Constraint
	 * 
	 * @return The Coordinated Variation Constraint
	 */

	public org.drip.execution.tradingtime.CoordinatedVariation variationConstraint()
	{
		return _cv;
	}

	@Override public org.drip.execution.impact.ParticipationRateLinear liquidityFunction (
		final double dblTime)
	{
		try {
			double dblVolatility = _r1ToR1Volatility.evaluate (dblTime);

			return org.drip.execution.impact.ParticipationRateLinear.SlopeOnly (_cv.invariant() /
				(dblVolatility * dblVolatility));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.execution.impact.TransactionFunction impactFunction (
		final double dblTime)
	{
		return liquidityFunction (dblTime);
	}

	@Override public org.drip.execution.impact.ParticipationRateLinear epochLiquidityFunction()
	{
		return liquidityFunction (0.);
	}

	@Override public org.drip.execution.impact.TransactionFunction epochImpactFunction()
	{
		return epochLiquidityFunction();
	}

	/**
	 * Compute the Volatility Function from the Liquidity Function
	 * 
	 * @return The R^1 To R^1 Volatility Function
	 */

	public org.drip.function.definition.R1ToR1 volatilityFunction()
	{
		return new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				org.drip.execution.impact.TransactionFunctionLinear tfl = liquidityFunction (dblTime);

				if (null == tfl)
					throw new java.lang.Exception
						("CoordinatedParticipationRateLinear::volatilityFunction::evaluate => Invalid Inputs");

				return java.lang.Math.sqrt (_cv.invariant() / tfl.slope());
			}
		};
	}
}
