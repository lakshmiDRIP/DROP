
package org.drip.oms.indifference;

import org.drip.function.definition.R1ToR1;
import org.drip.measure.continuous.R1Univariate;
import org.drip.numerical.common.NumberUtil;

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
 * <i>ReservationPricerProcessShell</i> holds the main Private Reservation Pricer and its Parameters. The
 *  References are:
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

public abstract class ReservationPricerProcessShell
{
	private double _endowmentValue = Double.NaN;
	private ReservationPricer _reservationPricer = null;

	protected abstract double indifferenceUnderlierUnits (
		final R1ToR1 risklessUnitsFunction,
		final R1Univariate terminalUnderlierDistribution,
		final double terminalRisklessPrice)
		throws Exception;

	protected abstract ClaimsAdjustedOptimizationRun claimsAdjustedOptimizationRun (
		final R1ToR1 risklessUnitsFunction,
		final R1Univariate terminalUnderlierDistribution,
		final double terminalRisklessPrice,
		final double claimsUnits
	);

	/**
	 * ReservationPricerProcessShell Constructor
	 * 
	 * @param endowmentValue Endowment Value
	 * @param reservationPricer Reservation Pricer
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public ReservationPricerProcessShell (
		final double endowmentValue,
		final ReservationPricer reservationPricer)
		throws Exception
	{
		if (!NumberUtil.IsValid (_endowmentValue = endowmentValue) ||
			null == (_reservationPricer = reservationPricer)) {
			throw new Exception ("ReservationPricerProcessShell Constructor => Invalid Inputs");
		}
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
	 * Retrieve the Reservation Pricer
	 * 
	 * @return The Reservation Pricer
	 */

	public ReservationPricer reservationPricer()
	{
		return _reservationPricer;
	}

	/**
	 * Generate the Function to calculate the Riskless Security Units from the Endowment Value
	 * 
	 * @param initialRisklessPrice Initial Riskless Security Unit Price
	 * @param initialUnderlierPrice Initial Underlier Security Unit Price
	 * 
	 * @return Riskless Units Function
	 */

	public R1ToR1 risklessUnitsFunction (
		final double initialRisklessPrice,
		final double initialUnderlierPrice)
	{
		return !NumberUtil.IsValid (initialRisklessPrice) || !NumberUtil.IsValid (initialUnderlierPrice) ?
			null : new R1ToR1 (null)
		{
			@Override public double evaluate (
				final double underlierUnits)
				throws Exception
			{
				return (_endowmentValue - initialUnderlierPrice * underlierUnits) / initialRisklessPrice;
			}
		};
	}

	/**
	 * Run a Reservation Pricing Flow
	 * 
	 * @param initialRisklessPrice Initial Riskless Security Unit Price
	 * @param initialUnderlierPrice Initial Underlier Security Unit Price
	 * @param terminalRisklessPrice Riskless Entity Terminal Price
	 * @param terminalUnderlierPrice Risky Underlier Terminal Price
	 * @param terminalUnderlierDistribution Terminal Underlier Distribution
	 * @param claimsUnitArray Array of the Claims Unit
	 * 
	 * @return The Reservation Pricing Run
	 */

	public ReservationPriceRun flow (
		final double initialRisklessPrice,
		final double initialUnderlierPrice,
		final double terminalRisklessPrice,
		final double terminalUnderlierPrice,
		final R1Univariate terminalUnderlierDistribution,
		final double[] claimsUnitArray)
	{
		if (null == claimsUnitArray || !NumberUtil.IsValid (claimsUnitArray)) {
			return null;
		}

		int claimsUnitCount = claimsUnitArray.length;

		if (0 == claimsUnitCount) {
			return null;
		}

		R1ToR1 risklessUnitsFunction = risklessUnitsFunction (
			initialRisklessPrice,
			initialUnderlierPrice
		);

		if (null == risklessUnitsFunction) {
			return null;
		}

		try {
			double indifferenceUnderlierUnits = indifferenceUnderlierUnits (
				risklessUnitsFunction,
				terminalUnderlierDistribution,
				terminalRisklessPrice
			);

			double indifferenceValue = _reservationPricer.claimsUnadjustedUtilityValue (
				risklessUnitsFunction,
				terminalRisklessPrice,
				terminalUnderlierPrice,
				indifferenceUnderlierUnits
			);

			ReservationPriceRun reservationPriceRun = new ReservationPriceRun (
				new UtilityOptimizationRun (
					indifferenceValue,
					indifferenceUnderlierUnits
				)
			);

			for (int claimsUnitIndex = 0; claimsUnitIndex < claimsUnitCount; ++claimsUnitIndex) {
				ClaimsAdjustedOptimizationRun claimsAdjustedOptimizationRun = claimsAdjustedOptimizationRun (
					risklessUnitsFunction,
					terminalUnderlierDistribution,
					terminalRisklessPrice,
					claimsUnitArray[claimsUnitIndex]
				);

				if (null == claimsAdjustedOptimizationRun) {
					return null;
				}

				double claimsAdjustedIndifferenceValue = _reservationPricer.claimsAdjustedPrice (
					claimsAdjustedOptimizationRun.optimalUtilityExpectationFunction(),
					indifferenceValue
				);

				reservationPriceRun.addClaimsAdjustedIndifferenceUtility (
					claimsUnitArray[claimsUnitIndex],
					new UtilityOptimizationRun (
						claimsAdjustedIndifferenceValue,
						claimsAdjustedOptimizationRun.optimalUnderlierUnitsFunction().evaluate (
							claimsAdjustedIndifferenceValue
						)
					)
				);
			}

			return reservationPriceRun;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
