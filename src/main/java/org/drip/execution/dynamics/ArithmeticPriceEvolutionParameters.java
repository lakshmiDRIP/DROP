
package org.drip.execution.dynamics;

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
 * <i>ArithmeticPriceEvolutionParameters</i> contains the Exogenous Parameters that determine the Dynamics of
 *  the Arithmetic Price Movements exhibited by an Asset owing to the Volatility and the Market Impact
 *  Factors. The References are:
 * 
 * <br>
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
 * <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution">Execution</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/dynamics">Dynamics</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/TransactionCost">Transaction Cost Analytics</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ArithmeticPriceEvolutionParameters {
	private org.drip.execution.parameters.ArithmeticPriceDynamicsSettings _apds = null;
	private org.drip.execution.profiletime.BackgroundParticipationRate _bprPermanentVolatility = null;
	private org.drip.execution.profiletime.BackgroundParticipationRate _bprTemporaryVolatility = null;
	private org.drip.execution.profiletime.BackgroundParticipationRate _bprPermanentExpectation = null;
	private org.drip.execution.profiletime.BackgroundParticipationRate _bprTemporaryExpectation = null;

	/**
	 * ArithmeticPriceEvolutionParameters Constructor
	 * 
	 * @param apds The Asset Price Dynamics Settings
	 * @param bprPermanentExpectation The Background Participation Permanent Market Impact Expectation
	 * 		Function
	 * @param bprTemporaryExpectation The Background Participation Temporary Market Impact Expectation
	 * 		Function
	 * @param bprPermanentVolatility The Background Participation Permanent Market Impact Volatility Function
	 * @param bprTemporaryVolatility The Background Participation Temporary Market Impact Volatility Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ArithmeticPriceEvolutionParameters (
		final org.drip.execution.parameters.ArithmeticPriceDynamicsSettings apds,
		final org.drip.execution.profiletime.BackgroundParticipationRate bprPermanentExpectation,
		final org.drip.execution.profiletime.BackgroundParticipationRate bprTemporaryExpectation,
		final org.drip.execution.profiletime.BackgroundParticipationRate bprPermanentVolatility,
		final org.drip.execution.profiletime.BackgroundParticipationRate bprTemporaryVolatility)
		throws java.lang.Exception
	{
		if (null == (_apds = apds) || null == (_bprPermanentExpectation = bprPermanentExpectation) || null ==
			(_bprTemporaryExpectation = bprTemporaryExpectation) || null == (_bprPermanentVolatility =
				bprPermanentVolatility) || null == (_bprTemporaryVolatility = bprTemporaryVolatility))
			throw new java.lang.Exception
				("ArithmeticPriceEvolutionParameters Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Arithmetic Price Dynamics Settings Instance
	 * 
	 * @return The Arithmetic Price Dynamics Settings Instance
	 */

	public org.drip.execution.parameters.ArithmeticPriceDynamicsSettings arithmeticPriceDynamicsSettings()
	{
		return _apds;
	}

	/**
	 * Retrieve the Background Participation Permanent Market Impact Expectation Function
	 * 
	 * @return The Background Participation Permanent Market Impact Expectation Function
	 */

	public org.drip.execution.profiletime.BackgroundParticipationRate permanentExpectation()
	{
		return _bprPermanentExpectation;
	}

	/**
	 * Retrieve the Background Participation Temporary Market Impact Expectation Function
	 * 
	 * @return The Background Participation Temporary Market Impact Expectation Function
	 */

	public org.drip.execution.profiletime.BackgroundParticipationRate temporaryExpectation()
	{
		return _bprTemporaryExpectation;
	}

	/**
	 * Retrieve the Background Participation Permanent Market Impact Volatility Function
	 * 
	 * @return The Background Participation Permanent Market Impact Volatility Function
	 */

	public org.drip.execution.profiletime.BackgroundParticipationRate permanentVolatility()
	{
		return _bprPermanentVolatility;
	}

	/**
	 * Retrieve the Background Participation Temporary Market Impact Volatility Function
	 * 
	 * @return The Background Participation Temporary Market Impact Volatility Function
	 */

	public org.drip.execution.profiletime.BackgroundParticipationRate temporaryVolatility()
	{
		return _bprTemporaryVolatility;
	}
}
