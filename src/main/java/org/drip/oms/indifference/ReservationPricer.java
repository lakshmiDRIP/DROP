
package org.drip.oms.indifference;

import org.drip.function.definition.R1ToR1;
import org.drip.measure.continuous.R1Univariate;
import org.drip.measure.discrete.R1Distribution;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.integration.R1ToR1Integrator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
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
 * <i>ReservationPricer</i> holds the main Private Reservation Pricer and its Parameters. The References are:
 *  
 * 	<br><br>
 *  <ul>
 * 		<li>
 * 			Birge, J. R. (2008): <i>Financial Engineering</i> <b>Elsevier</b> Amsterdam Netherlands
 * 		</li>
 * 		<li>
 * 			Carmona, R. (2009): <i>Indifference Pricing: Theory and Applications</i> <b>Princeton
 * 				University Press</b> Princeton NJ
 * 		</li>
 * 		<li>
 * 			Vassilis, P. (2005): Slow and Fast Markets <i>Journal of Economics and Business</i> <b>57
 * 				(6)</b> 576-593
 * 		</li>
 * 		<li>
 * 			Weiss, D. (2006): <i>After the Trade is Made: Processing Securities Transactions</i> <b>Portfolio
 * 				Publishing</b> London UK
 * 		</li>
 * 		<li>
 * 			Wikipedia (2021): Indifference Price https://en.wikipedia.org/wiki/Indifference_price
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/oms/README.md">R<sup>d</sup> Order Specification, Handling, and Management</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/oms/indifference/README.md">Reservation Price Good-deal Bounds</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ReservationPricer
{
	private double _endowmentValue = Double.NaN;
	private R1ToR1 _privateValuationUtilityFunction = null;
	private BidAskClaimsHandler _bidAskClaimsHandler = null;

	private double expectedTerminalUtilityPrice (
		final R1Distribution terminalDistribution)
		throws Exception
	{
		if (null == terminalDistribution)
		{
			throw new Exception (
				"ReservationPricer::expectedTerminalUtilityPrice => Invalid Terminal Distribution"
			);
		}

		double expectedTerminalUtilityPrice = 0.;

		for (double terminalInstance : terminalDistribution.probabilityMap().keySet())
		{
			expectedTerminalUtilityPrice += _privateValuationUtilityFunction.evaluate (terminalInstance) *
				terminalDistribution.probability (terminalInstance);
		}

		return expectedTerminalUtilityPrice;
	}

	public double expectedTerminalUtilityPrice (
		final R1Univariate terminalDistribution)
		throws Exception
	{
		if (null == terminalDistribution)
		{
			throw new Exception (
				"ReservationPricer::expectedTerminalUtilityPrice => Invalid Terminal Distribution"
			);
		}

		double[] terminalSupportArray = terminalDistribution.support();

		return R1ToR1Integrator.Boole (
			new R1ToR1 (null) {
				@Override public double evaluate (
					double terminalInstance)
					throws Exception
				{
					return _privateValuationUtilityFunction.evaluate (terminalInstance) *
						terminalDistribution.density (terminalInstance);
				}
			},
			terminalSupportArray[0],
			terminalSupportArray[1]
		);
	}

	/**
	 * Retrieve the Private Valuation Utility Function
	 * 
	 * @return The Private Valuation Utility Function
	 */

	public R1ToR1 privateValuationUtilityFunction()
	{
		return _privateValuationUtilityFunction;
	}

	/**
	 * Retrieve the Endowment Value
	 * 
	 * @return The Endowment Value
	 */

	public double endowmentValue()
	{
		return _endowmentValue;
	}

	/**
	 * Retrieve the Bid/Ask Claims Handler
	 * 
	 * @return The Bid/Ask Claims Handler
	 */

	public BidAskClaimsHandler bidAskClaimsHandler()
	{
		return _bidAskClaimsHandler;
	}

	/**
	 * Evaluate the Expected Terminal Endowment Utility Price
	 * 
	 * @param terminalEndowmentDistribution The Terminal Endowment Distribution
	 * 
	 * @return The Expected Terminal Endowment Utility Price
	 * 
	 * @throws Exception The Expected Terminal Endowment Utility Price cannot be estimated
	 */

	public double expectedTerminalEndowmentUtilityPrice (
		final R1Distribution terminalEndowmentDistribution)
		throws Exception
	{
		return expectedTerminalUtilityPrice (terminalEndowmentDistribution);
	}

	/**
	 * Evaluate the Expected Terminal Endowment Utility Price
	 * 
	 * @param terminalEndowmentDistribution The Terminal Endowment Distribution
	 * 
	 * @return The Expected Terminal Endowment Utility Price
	 * 
	 * @throws Exception The Expected Terminal Endowment Utility Price cannot be estimated
	 */

	public double expectedTerminalEndowmentUtilityPrice (
		final R1Univariate terminalEndowmentDistribution)
		throws Exception
	{
		return expectedTerminalUtilityPrice (terminalEndowmentDistribution);
	}

	/**
	 * Evaluate the Expected Terminal Claims Utility Price
	 * 
	 * @param terminalClaimsDistribution The Terminal Claims Distribution
	 * 
	 * @return The Expected Terminal Claims Utility Price
	 * 
	 * @throws Exception The Expected Terminal Claims Utility Price cannot be estimated
	 */

	public double expectedTerminalClaimsUtilityPrice (
		final R1Distribution terminalClaimsDistribution)
		throws Exception
	{
		return expectedTerminalUtilityPrice (terminalClaimsDistribution);
	}

	/**
	 * Evaluate the Expected Terminal Claims Utility Price
	 * 
	 * @param terminalClaimsDistribution The Terminal Claims Distribution
	 * 
	 * @return The Expected Terminal Claims Utility Price
	 * 
	 * @throws Exception The Expected Terminal Claims Utility Price cannot be estimated
	 */

	public double expectedTerminalClaimsUtilityPrice (
		final R1Univariate terminalClaimsDistribution)
		throws Exception
	{
		return expectedTerminalUtilityPrice (terminalClaimsDistribution);
	}

	/**
	 * Generate the Function to calculate the Riskless Security Units from the Endowment Value
	 * 
	 * @param risklessPrice Riskless Security Unit Price
	 * @param underlierPrice Underlier Security Unit Price
	 * 
	 * @return Riskless Units Constraint Function
	 */

	public R1ToR1 risklessUnitsConstraintFunction (
		final double risklessPrice,
		final double underlierPrice)
	{
		return !NumberUtil.IsValid (risklessPrice) || !NumberUtil.IsValid (underlierPrice) ?
			null : new R1ToR1 (null)
		{
			@Override public double evaluate (
				double underlierUnits)
				throws Exception
			{
				return (_endowmentValue - underlierPrice * underlierUnits) / risklessPrice;
			}
		};
	}

	public UtilityOptimizationRun baselineIndifferenceRun (
		final R1ToR1 risklessUnitsConstraintFunction)
	{
		return null;
	}
}
