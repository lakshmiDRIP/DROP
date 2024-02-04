
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
 * <i>ReservationPricer</i> implements the Expectation of the Utility Function using the Endowment and at
 * 	Payoff on the Underlying Asset. The References are:
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
	private InventoryVertex _inventoryVertex = null;
	private UtilityFunction _utilityFunction = null;
	private ClaimsPositionPricer _askClaimsPositionPricer = null;
	private ClaimsPositionPricer _bidClaimsPositionPricer = null;

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
	 * Retrieve the Utility Function
	 * 
	 * @return The Utility Function
	 */

	public UtilityFunction utilityFunction()
	{
		return _utilityFunction;
	}

	/**
	 * Retrieve the Bid Claims Position Pricer
	 * 
	 * @return The Bid Claims Position Pricer
	 */

	public ClaimsPositionPricer bidClaimsPositionPricer()
	{
		return _bidClaimsPositionPricer;
	}

	/**
	 * Retrieve the Ask Claims Position Pricer
	 * 
	 * @return The Ask Claims Position Pricer
	 */

	public ClaimsPositionPricer askClaimsPositionPricer()
	{
		return _askClaimsPositionPricer;
	}

	/**
	 * Retrieve the Optimal No-claims Inventory Vertex
	 * 
	 * @return Optimal No-claims Inventory Vertex
	 */

	public InventoryVertex optimalNoClaimsInventoryVertex()
	{
		return _inventoryVertex;
	}

	/**
	 * Retrieve the Optimal Bid Claims Based Inventory Vertex
	 * 
	 * @return Optimal Bid Claims Based Inventory Vertex
	 */

	public InventoryVertex optimalBidClaimsInventoryVertex()
	{
		return _inventoryVertex;
	}

	/**
	 * Retrieve the Optimal Ask Claims Based Inventory Vertex
	 * 
	 * @return Optimal Ask Claims Based Inventory Vertex
	 */

	public InventoryVertex optimalAskClaimsInventoryVertex()
	{
		return _inventoryVertex;
	}

	/**
	 * Compute the No-Claims Inventory-based Optimal Utility Value
	 * 
	 * @param underlierPriceDistribution Discrete Underlier Price Distribution
	 * @param moneyMarketPrice Price of Money Market Entity
	 * 
	 * @return The No-Claims Inventory-based Optimal Utility Value
	 * 
	 * @throws Exception Thrown if the No-Claims Inventory-based Optimal Utility Value cannot be calculated
	 */

	public double noClaimsInventoryUtilityExpectation (
		final R1Univariate underlierPriceDistribution,
		final double moneyMarketPrice)
		throws Exception
	{
		return new UtilityFunctionExpectation (
			_utilityFunction,
			null,
			optimalNoClaimsInventoryVertex(),
			moneyMarketPrice
		).optimizationRun (underlierPriceDistribution, 0.).optimalValue();
	}

	/**
	 * Compute the No-Claims Inventory-based Optimal Utility Value
	 * 
	 * @param underlierPriceDistribution Discrete Underlier Price Distribution
	 * @param underlierPriceArray Underlier Price Array
	 * @param moneyMarketPrice Price of Money Market Entity
	 * 
	 * @return The No-Claims Inventory-based Optimal Utility Value
	 * 
	 * @throws Exception Thrown if the No-Claims Inventory-based Optimal Utility Value cannot be calculated
	 */

	public double noClaimsInventoryUtilityExpectation (
		final R1Distribution underlierPriceDistribution,
		final double[] underlierPriceArray,
		final double moneyMarketPrice)
		throws Exception
	{
		return new UtilityFunctionExpectation (
			_utilityFunction,
			null,
			optimalNoClaimsInventoryVertex(),
			moneyMarketPrice
		).optimizationRun (underlierPriceDistribution, underlierPriceArray, 0.).optimalValue();
	}

	/**
	 * Compute the Bid Claims Inventory-based Position Value Adjustment
	 * 
	 * @param underlierPriceDistribution Discrete Underlier Price Distribution
	 * @param moneyMarketPrice Price of Money Market Entity
	 * @param noClaimsInventoryUtilityExpectation No-Claims Inventory Utility Expectation
	 * 
	 * @return The Bid Claims Inventory-based Position Value Adjustment
	 * 
	 * @throws Exception Thrown if the Bid Claims Inventory-based Position Value Adjustment cannot be
	 *  calculated
	 */

	public double bidClaimsPositionValueAdjustment (
		final R1Univariate underlierPriceDistribution,
		final double moneyMarketPrice,
		final double noClaimsInventoryUtilityExpectation)
		throws Exception
	{
		ClaimsUtilityExpectationInferenceRun claimsUtilityExpectationInferenceRun =
			new UtilityFunctionExpectation (
				_utilityFunction,
				_bidClaimsPositionPricer,
				optimalBidClaimsInventoryVertex(),
				moneyMarketPrice
			).inferPositionAdjustment (
				underlierPriceDistribution,
				noClaimsInventoryUtilityExpectation
			);

		if (null == claimsUtilityExpectationInferenceRun) {
			throw new Exception (" Cannot generate Claims Utility Expectation Inference Run");
		}

		return claimsUtilityExpectationInferenceRun.optimalValue();
	}

	/**
	 * Compute the Bid Claims Inventory-based Position Value Adjustment
	 * 
	 * @param underlierPriceDistribution Discrete Underlier Price Distribution
	 * @param underlierPriceArray Underlier Price Array
	 * @param moneyMarketPrice Price of Money Market Entity
	 * @param noClaimsInventoryUtilityExpectation No-Claims Inventory Utility Expectation
	 * 
	 * @return The Bid Claims Inventory-based Position Value Adjustment
	 * 
	 * @throws Exception Thrown if the Bid Claims Inventory-based Position Value Adjustment cannot be
	 *  calculated
	 */

	public double bidClaimsPositionValueAdjustment (
		final R1Distribution underlierPriceDistribution,
		final double[] underlierPriceArray,
		final double moneyMarketPrice,
		final double noClaimsInventoryUtilityExpectation)
		throws Exception
	{
		ClaimsUtilityExpectationInferenceRun claimsUtilityExpectationInferenceRun =
			new UtilityFunctionExpectation (
				_utilityFunction,
				_bidClaimsPositionPricer,
				optimalBidClaimsInventoryVertex(),
				moneyMarketPrice
			).inferPositionAdjustment (
				underlierPriceDistribution,
				underlierPriceArray,
				noClaimsInventoryUtilityExpectation
			);

		if (null == claimsUtilityExpectationInferenceRun) {
			throw new Exception (" Cannot generate Claims Utility Expectation Inference Run");
		}

		return claimsUtilityExpectationInferenceRun.optimalValue();
	}

	/**
	 * Compute the Ask Claims Inventory-based Position Value Adjustment
	 * 
	 * @param underlierPriceDistribution Discrete Underlier Price Distribution
	 * @param moneyMarketPrice Price of Money Market Entity
	 * @param noClaimsInventoryUtilityExpectation No-Claims Inventory Utility Expectation
	 * 
	 * @return The Ask Claims Inventory-based Position Value Adjustment
	 * 
	 * @throws Exception Thrown if the Ask Claims Inventory-based Position Value Adjustment cannot be
	 *  calculated
	 */

	public double askClaimsPositionValueAdjustment (
		final R1Univariate underlierPriceDistribution,
		final double moneyMarketPrice,
		final double noClaimsInventoryUtilityExpectation)
		throws Exception
	{
		ClaimsUtilityExpectationInferenceRun claimsUtilityExpectationInferenceRun =
			new UtilityFunctionExpectation (
				_utilityFunction,
				_bidClaimsPositionPricer,
				optimalBidClaimsInventoryVertex(),
				moneyMarketPrice
			).inferPositionAdjustment (
				underlierPriceDistribution,
				noClaimsInventoryUtilityExpectation
			);

		if (null == claimsUtilityExpectationInferenceRun) {
			throw new Exception (" Cannot generate Claims Utility Expectation Inference Run");
		}

		return claimsUtilityExpectationInferenceRun.optimalValue();
	}

	/**
	 * Compute the Ask Claims Inventory-based Position Value Adjustment
	 * 
	 * @param underlierPriceDistribution Discrete Underlier Price Distribution
	 * @param underlierPriceArray Underlier Price Array
	 * @param moneyMarketPrice Price of Money Market Entity
	 * @param noClaimsInventoryUtilityExpectation No-Claims Inventory Utility Expectation
	 * 
	 * @return The Ask Claims Inventory-based Position Value Adjustment
	 * 
	 * @throws Exception Thrown if the Bid Claims Inventory-based Position Value Adjustment cannot be
	 *  calculated
	 */

	public double askClaimsPositionValueAdjustment (
		final R1Distribution underlierPriceDistribution,
		final double[] underlierPriceArray,
		final double moneyMarketPrice,
		final double noClaimsInventoryUtilityExpectation)
		throws Exception
	{
		ClaimsUtilityExpectationInferenceRun claimsUtilityExpectationInferenceRun =
			new UtilityFunctionExpectation (
				_utilityFunction,
				_askClaimsPositionPricer,
				optimalAskClaimsInventoryVertex(),
				moneyMarketPrice
			).inferPositionAdjustment (
				underlierPriceDistribution,
				underlierPriceArray,
				noClaimsInventoryUtilityExpectation
			);

		if (null == claimsUtilityExpectationInferenceRun) {
			throw new Exception (" Cannot generate Claims Utility Expectation Inference Run");
		}

		return claimsUtilityExpectationInferenceRun.optimalValue();
	}

	/**
	 * Run a Reservation Pricing Flow
	 * 
	 * @param underlierPriceDistribution Discrete Underlier Price Distribution
	 * @param moneyMarketPrice Price of Money Market Entity
	 * 
	 * @return Reservation Pricing Flow
	 */

	public ReservationPricingRun reservationPricingRun (
		final R1Univariate underlierPriceDistribution,
		final double moneyMarketPrice)
	{
		try {
			double noClaimsInventoryUtilityExpectation = noClaimsInventoryUtilityExpectation (
				underlierPriceDistribution,
				moneyMarketPrice
			);

			return new ReservationPricingRun (
				bidClaimsPositionValueAdjustment (
					underlierPriceDistribution,
					moneyMarketPrice,
					noClaimsInventoryUtilityExpectation
				),
				askClaimsPositionValueAdjustment (
					underlierPriceDistribution,
					moneyMarketPrice,
					noClaimsInventoryUtilityExpectation
				),
				noClaimsInventoryUtilityExpectation
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Run a Reservation Pricing Flow
	 * 
	 * @param underlierPriceDistribution Discrete Underlier Price Distribution
	 * @param underlierPriceArray Underlier Price Array
	 * @param moneyMarketPrice Price of Money Market Entity
	 * 
	 * @return Reservation Pricing Flow
	 */

	public ReservationPricingRun reservationPricingRun (
		final R1Distribution underlierPriceDistribution,
		final double[] underlierPriceArray,
		final double moneyMarketPrice)
	{
		try {
			double noClaimsInventoryUtilityExpectation = noClaimsInventoryUtilityExpectation (
				underlierPriceDistribution,
				underlierPriceArray,
				moneyMarketPrice
			);

			return new ReservationPricingRun (
				bidClaimsPositionValueAdjustment (
					underlierPriceDistribution,
					underlierPriceArray,
					moneyMarketPrice,
					noClaimsInventoryUtilityExpectation
				),
				askClaimsPositionValueAdjustment (
					underlierPriceDistribution,
					underlierPriceArray,
					moneyMarketPrice,
					noClaimsInventoryUtilityExpectation
				),
				noClaimsInventoryUtilityExpectation
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
