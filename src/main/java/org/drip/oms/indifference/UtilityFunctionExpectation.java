
package org.drip.oms.indifference;

import org.drip.measure.continuous.R1Univariate;
import org.drip.measure.discrete.R1Distribution;

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
 * <i>UtilityFunctionExpectation</i> implements the Expectation of Utility Function across Realized Underlier
 *  Values using its Terminal Measure. The References are:
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

public class UtilityFunctionExpectation
{
	private double _moneyMarketPrice = Double.NaN;
	private UtilityFunction _agentOptimizer = null;
	private InventoryVertex _inventoryVertex = null;

	/**
	 * Retrieve the Agent Optimization Utility Function
	 * 
	 * @return Agent Optimization Utility Function
	 */

	public UtilityFunction agentOptimizer()
	{
		return _agentOptimizer;
	}

	/**
	 * Retrieve the Inventory Vertex
	 * 
	 * @return The Inventory Vertex
	 */

	public InventoryVertex inventoryVertex()
	{
		return _inventoryVertex;
	}

	/**
	 * Retrieve the Price of Money Market Entity
	 * 
	 * @return Number of Money Market Entity
	 */

	public double moneyMarketPrice()
	{
		return _moneyMarketPrice;
	}

	/**
	 * Compute the Agent's Objective Function Value For the Underlier Price
	 * 
	 * @param underlierPrice The Underlier Price
	 * 
	 * @return The Agent's Objective Function Value For the Underlier Price
	 * 
	 * @throws Exception Thrown if the Agent's Objective Function Value cannot be calculated
	 */

	public double objectiveValue (
		final double underlierPrice)
		throws Exception
	{
		return _agentOptimizer.evaluate (
			new PositionVertex (
				_inventoryVertex,
				new RealizationVertex (_moneyMarketPrice, underlierPrice),
				_agentOptimizer.privateValuationObjective()
			)
		);
	}

	/**
	 * Compute the Expectation of the Agent Utility Function given the Underlier Price Array and Discrete
	 * 	Distribution
	 * 
	 * @param underlierPriceDistribution Discrete Underlier Price Distribution
	 * @param underlierPriceArray Underlier Price Array
	 * 
	 * @return Expectation of the Agent Utility Function
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double evaluate (
		final R1Distribution underlierPriceDistribution,
		final double[] underlierPriceArray)
		throws Exception
	{
		if (null == underlierPriceDistribution ||
			null == underlierPriceArray || 0 == underlierPriceArray.length) {
			throw new Exception ("UtilityFunctionExpectation::evaluate => Invalid Inputs");
		}

		double utilityFunctionExpectationValue = 0.;

		for (double underlierPrice : underlierPriceArray) {
			utilityFunctionExpectationValue += underlierPriceDistribution.probability (underlierPrice) *
				_agentOptimizer.evaluate (
					new PositionVertex (
						_inventoryVertex,
						new RealizationVertex (_moneyMarketPrice, underlierPrice),
						_agentOptimizer.privateValuationObjective()
					)
				);
		}

		return utilityFunctionExpectationValue;
	}

	public double evaluate (
		final R1Univariate underlierPriceDistribution)
		throws Exception
	{
		if (null == underlierPriceDistribution) {
			throw new Exception ("UtilityFunctionExpectation::evaluate => Invalid Inputs");
		}

		double utilityFunctionExpectationValue = 0.;

		return utilityFunctionExpectationValue;
	}
}
