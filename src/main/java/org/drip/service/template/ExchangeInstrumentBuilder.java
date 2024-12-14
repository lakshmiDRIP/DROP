
package org.drip.service.template;

import org.drip.analytics.date.JulianDate;
import org.drip.market.exchange.TreasuryFuturesContract;
import org.drip.market.exchange.TreasuryFuturesContractContainer;
import org.drip.market.exchange.TreasuryFuturesConvention;
import org.drip.market.exchange.TreasuryFuturesConventionContainer;
import org.drip.market.exchange.TreasuryFuturesEligibility;
import org.drip.market.exchange.TreasuryFuturesSettle;
import org.drip.product.creator.SingleStreamComponentBuilder;
import org.drip.product.govvie.TreasuryFutures;
import org.drip.product.rates.SingleStreamComponent;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>ExchangeInstrumentBuilder</i> contains static Helper API to facilitate Construction of Exchange-traded
 * 	Instruments. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Generate a Forward Rate Futures Contract corresponding to the Spot Date</li>
 * 		<li>Generate a Forward Rate Futures Pack corresponding to the Spot Date and the Specified Number of Contracts</li>
 * 		<li>Generate an Instance of Treasury Futures given the Inputs</li>
 * 		<li>Generate the Treasury Futures Instance #1</li>
 * 		<li>Generate the Treasury Futures Instance #2</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/template/README.md">Curve Construction Product Builder Templates</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ExchangeInstrumentBuilder
{

	/**
	 * Generate a Forward Rate Futures Contract corresponding to the Spot Date
	 * 
	 * @param spotDate Spot date specifying the contract issue
	 * @param currency Contract Currency
	 * 
	 * @return Forward Rate Futures Component
	 */

	public static SingleStreamComponent ForwardRateFutures (
		final JulianDate spotDate,
		final String currency)
	{
		if (null == spotDate) {
			return null;
		}

		SingleStreamComponent[] futuresComponentArray = SingleStreamComponentBuilder.ForwardRateFuturesPack (
			spotDate.addBusDays (0, currency),
			1,
			currency
		);

		return null == futuresComponentArray || 1 != futuresComponentArray.length ?
			null : futuresComponentArray[0];
	}

	/**
	 * Generate a Forward Rate Futures Pack corresponding to the Spot Date and the Specified Number of
	 *  Contracts
	 * 
	 * @param spotDate Spot date specifying the contract issue
	 * @param contractCount Number of contracts
	 * @param strCurrency Contract currency
	 * 
	 * @return Array containing the Forward Rate Futures Pack
	 */

	public static SingleStreamComponent[] ForwardRateFuturesPack (
		final JulianDate spotDate,
		final int contractCount,
		final String strCurrency)
	{
		return null == spotDate ? null : SingleStreamComponentBuilder.ForwardRateFuturesPack (
			spotDate.addBusDays (0, strCurrency),
			contractCount,
			strCurrency
		);
	}

	/**
	 * Generate an Instance of Treasury Futures given the Inputs
	 * 
	 * @param spotDate The Futures Spot Date
	 * @param code The Treasury Code
	 * @param effectiveDateArray Array of Effective Dates
	 * @param maturityDateArray Array of Maturity Dates
	 * @param couponArray Array of Coupons
	 * @param conversionFactorArray The Bond Conversion Factor
	 * @param underlierType The Underlier Type, e.g., TREASURY
	 * @param underlierSubtype The Futures Underlier Sub-type, i.e., BONDS
	 * @param maturityTenor The Futures Maturity Tenor
	 * 
	 * @return The Treasury Futures Instance
	 */

	public static TreasuryFutures TreasuryFutures (
		final JulianDate spotDate,
		final String code,
		final JulianDate[] effectiveDateArray,
		final JulianDate[] maturityDateArray,
		final double[] couponArray,
		final double[] conversionFactorArray,
		final String underlierType,
		final String underlierSubtype,
		final String maturityTenor)
	{
		if (null == spotDate) {
			return null;
		}

		try {
			TreasuryFutures treasuryFutures = new TreasuryFutures (
				TreasuryBuilder.FromCode (code, effectiveDateArray, maturityDateArray, couponArray),
				conversionFactorArray,
				null
			);

			String currency = treasuryFutures.basket()[0].currency();

			if (!treasuryFutures.setExpiry (
				spotDate.addBusDays (0, currency).nextBondFuturesIMM (3, currency)
			))
			{
				return null;
			}

			treasuryFutures.setType (code);

			TreasuryFuturesConvention treasuryFuturesConvention =
				TreasuryFuturesConventionContainer.FromJurisdictionTypeMaturity (
					currency,
					underlierType,
					underlierSubtype,
					maturityTenor
				);

			if (null == treasuryFuturesConvention) {
				return treasuryFutures;
			}

			double basketNotional = treasuryFuturesConvention.basketNotional();

			double minimumPriceMovement = treasuryFuturesConvention.minimumPriceMovement();

			treasuryFutures.setNotionalValue (basketNotional);

			treasuryFutures.setMinimumPriceMovement (minimumPriceMovement);

			treasuryFutures.setTickValue (basketNotional * minimumPriceMovement);

			TreasuryFuturesEligibility treasuryFuturesEligibility = treasuryFuturesConvention.eligibility();

			if (null != treasuryFuturesEligibility) {
				treasuryFutures.setMaximumMaturity (treasuryFuturesEligibility.maturityCeiling());

				treasuryFutures.setMinimumMaturity (treasuryFuturesEligibility.maturityFloor());
			}

			TreasuryFuturesSettle treasuryFuturesSettle = treasuryFuturesConvention.settle();

			if (null != treasuryFuturesSettle) {
				treasuryFutures.setReferenceCoupon (treasuryFuturesSettle.currentReferenceYield());

				treasuryFutures.setLastTradingDayLag (treasuryFuturesSettle.expiryLastTradingLag());

				treasuryFutures.setDeliveryMonths (treasuryFuturesSettle.deliveryMonths());
			}

			return treasuryFutures;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Treasury Futures Instance
	 * 
	 * @param spotDate The Spot Date Instance
	 * @param futuresCode The Treasury Futures Code
	 * @param futuresComponentTreasuryEffectiveDateArray Array of Treasury Futures Component Effective Date
	 * @param futuresComponentTreasuryMaturityDateArray Array of the Treasury Futures Component Maturity Date
	 * @param futuresComponentTreasuryCouponArray Array of the Treasury Futures Component Coupon
	 * @param futuresComponentConversionFactorArray Array of the Treasury Futures Component Conversion Factor
	 * @param futuresComponentUnderlierSubtype Treasury Futures Component Underlier SubType (BILL/BOND)
	 * @param futuresReferenceMaturityTenor Treasury Futures Component Reference Maturity Tenor
	 * 
	 * @return The Treasury Futures Instance
	 */

	public static final TreasuryFutures TreasuryFutures (
		final JulianDate spotDate,
		final String futuresCode,
		final int[] futuresComponentTreasuryEffectiveDateArray,
		final int[] futuresComponentTreasuryMaturityDateArray,
		final double[] futuresComponentTreasuryCouponArray,
		final double[] futuresComponentConversionFactorArray,
		final String futuresComponentUnderlierSubtype,
		final String futuresReferenceMaturityTenor)
	{
		if (null == spotDate ||
			null == futuresComponentTreasuryMaturityDateArray ||
			null == futuresComponentTreasuryEffectiveDateArray)
		{
			return null;
		}

		JulianDate[] futuresComponentTreasuryMaturityArray = null;
		JulianDate[] futuresComponentTreasuryEffectiveArray = null;
		int futuresComponentMaturityCount = futuresComponentTreasuryMaturityDateArray.length;
		int futuresComponentEffectiveCount = futuresComponentTreasuryEffectiveDateArray.length;

		if (0 != futuresComponentMaturityCount) {
			futuresComponentTreasuryMaturityArray = new JulianDate[futuresComponentMaturityCount];
		}

		if (0 != futuresComponentEffectiveCount) {
			futuresComponentTreasuryEffectiveArray = new JulianDate[futuresComponentEffectiveCount];
		}

		try {
			for (int i = 0; i < futuresComponentMaturityCount; ++i) {
				futuresComponentTreasuryMaturityArray[i] = new JulianDate (
					futuresComponentTreasuryMaturityDateArray[i]
				);
			}

			for (int i = 0; i < futuresComponentEffectiveCount; ++i) {
				futuresComponentTreasuryEffectiveArray[i] = new JulianDate (
					futuresComponentTreasuryEffectiveDateArray[i]
				);
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return TreasuryFutures (
			spotDate,
			futuresCode,
			futuresComponentTreasuryEffectiveArray,
			futuresComponentTreasuryMaturityArray,
			futuresComponentTreasuryCouponArray,
			futuresComponentConversionFactorArray,
			"TREASURY",
			futuresComponentUnderlierSubtype,
			futuresReferenceMaturityTenor
		);
	}

	/**
	 * Generate the Treasury Futures Instance
	 * 
	 * @param spotDate The Spot Date Instance
	 * @param futuresCode The Treasury Futures Code
	 * @param futuresComponentTreasuryEffectiveDateArray Array of Treasury Futures Component Effective Date
	 * @param futuresComponentTreasuryMaturityDateArray Array of Treasury Futures Component Maturity Date
	 * @param futuresComponentTreasuryCouponArray Array of Treasury Futures Component Coupon
	 * @param futuresComponentConversionFactorArray Array of Treasury Futures Component Conversion Factor
	 * 
	 * @return The Treasury Futures Instance
	 */

	public static final TreasuryFutures TreasuryFutures (
		final JulianDate spotDate,
		final String futuresCode,
		final int[] futuresComponentTreasuryEffectiveDateArray,
		final int[] futuresComponentTreasuryMaturityDateArray,
		final double[] futuresComponentTreasuryCouponArray,
		final double[] futuresComponentConversionFactorArray)
	{
		TreasuryFuturesContract treasuryFuturesContract =
			TreasuryFuturesContractContainer.TreasuryFuturesContract (futuresCode);

		return null == treasuryFuturesContract ? null : TreasuryFutures (
			spotDate,
			treasuryFuturesContract.code(),
			futuresComponentTreasuryEffectiveDateArray,
			futuresComponentTreasuryMaturityDateArray,
			futuresComponentTreasuryCouponArray,
			futuresComponentConversionFactorArray,
			treasuryFuturesContract.type(),
			treasuryFuturesContract.tenor()
		);
	}
}
