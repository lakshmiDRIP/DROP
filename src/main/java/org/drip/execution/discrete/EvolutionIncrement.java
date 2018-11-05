
package org.drip.execution.discrete;

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
 * <i>EvolutionIncrement</i> contains the Realized Stochastic Evolution Increments of the Price/Short-fall
 *  exhibited by an Asset owing to the Volatility and the Market Impact Factors over the Slice Time Interval.
 *  It is composed of Stochastic and Deterministic Price Increment Components. The References are:
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
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/discrete">Discrete</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/TransactionCost">Transaction Cost Analytics</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EvolutionIncrement extends org.drip.execution.evolution.MarketImpactComposite {

	/**
	 * EvolutionIncrement Constructor
	 * 
	 * @param micDeterministic The Deterministic Market Impact Component Instance
	 * @param micStochastic The Stochastic Market Impact Component Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EvolutionIncrement (
		final org.drip.execution.evolution.MarketImpactComponent micDeterministic,
		final org.drip.execution.evolution.MarketImpactComponent micStochastic)
		throws java.lang.Exception
	{
		super (micDeterministic, micStochastic);
	}

	/**
	 * Retrieve the Change induced by Deterministic Asset Price Market Dynamic Drivers
	 * 
	 * @return The Change induced by Deterministic Asset Price Market Dynamic Drivers
	 */

	public double marketDynamicDrift()
	{
		org.drip.execution.evolution.MarketImpactComponent micDeterministic = deterministic();

		return micDeterministic.previousStep() + micDeterministic.currentStep();
	}

	/**
	 * Retrieve the Change induced by Stochastic Asset Price Market Dynamic Drivers
	 * 
	 * @return The Change induced by Stochastic Asset Price Market Dynamic Drivers
	 */

	public double marketDynamicWander()
	{
		org.drip.execution.evolution.MarketImpactComponent micStochastic = stochastic();

		return micStochastic.previousStep() + micStochastic.currentStep();
	}

	/**
	 * Retrieve the Change induced by the Deterministic Asset Price Permanent Market Impact Drivers
	 * 
	 * @return The Change induced by the Deterministic Asset Price Permanent Market Impact Drivers
	 */

	public double permanentImpactDrift()
	{
		return deterministic().permanentImpact();
	}

	/**
	 * Retrieve the Change induced by the Stochastic Asset Price Permanent Market Impact Drivers
	 * 
	 * @return The Change induced by the Stochastic Asset Price Permanent Market Impact Drivers
	 */

	public double permanentImpactWander()
	{
		return stochastic().permanentImpact();
	}

	/**
	 * Retrieve the Change induced by the Deterministic Asset Price Temporary Market Impact Drivers
	 * 
	 * @return The Change induced by the Deterministic Asset Price Temporary Market Impact Drivers
	 */

	public double temporaryImpactDrift()
	{
		return deterministic().temporaryImpact();
	}

	/**
	 * Retrieve the Change induced by the Stochastic Asset Price Temporary Market Impact Drivers
	 * 
	 * @return The Change induced by the Stochastic Asset Price Temporary Market Impact Drivers
	 */

	public double temporaryImpactWander()
	{
		return stochastic().temporaryImpact();
	}
}
