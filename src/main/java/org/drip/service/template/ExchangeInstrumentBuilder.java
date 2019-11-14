
package org.drip.service.template;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>ExchangeInstrumentBuilder</i> contains static Helper API to facilitate Construction of Exchange-traded
 * Instruments.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/template/README.md">Curve Construction Product Builder Templates</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ExchangeInstrumentBuilder {

	/**
	 * Generate a Forward Rate Futures Contract corresponding to the Spot Date
	 * 
	 * @param dtSpot Spot date specifying the contract issue
	 * @param strCurrency Contract Currency
	 * 
	 * @return Forward Rate Futures Component
	 */

	public static org.drip.product.rates.SingleStreamComponent ForwardRateFutures (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCurrency)
	{
		if (null == dtSpot) return null;

		org.drip.product.rates.SingleStreamComponent[] aFutures =
			org.drip.product.creator.SingleStreamComponentBuilder.ForwardRateFuturesPack (dtSpot.addBusDays
				(0, strCurrency), 1, strCurrency);

		return null == aFutures || 1 != aFutures.length ? null : aFutures[0];
	}

	/**
	 * Generate a Forward Rate Futures Pack corresponding to the Spot Date and the Specified Number of
	 *  Contracts
	 * 
	 * @param dtSpot Spot date specifying the contract issue
	 * @param iNumContract Number of contracts
	 * @param strCurrency Contract currency
	 * 
	 * @return Array containing the Forward Rate Futures Pack
	 */

	public static org.drip.product.rates.SingleStreamComponent[] ForwardRateFuturesPack (
		final org.drip.analytics.date.JulianDate dtSpot,
		final int iNumContract,
		final java.lang.String strCurrency)
	{
		return null == dtSpot ? null :
			org.drip.product.creator.SingleStreamComponentBuilder.ForwardRateFuturesPack (dtSpot.addBusDays
				(0, strCurrency), iNumContract, strCurrency);
	}

	/**
	 * Generate an Instance of Treasury Futures given the Inputs
	 * 
	 * @param dtSpot The Futures Spot Date
	 * @param strCode The Treasury Code
	 * @param adtEffective Array of Effective Dates
	 * @param adtMaturity Array of Maturity Dates
	 * @param adblCoupon Array of Coupons
	 * @param adblConversionFactor The Bond Conversion Factor
	 * @param strUnderlierType The Underlier Type, e.g., TREASURY
	 * @param strUnderlierSubtype The Futures Underlier Sub-type, i.e., BONDS
	 * @param strMaturityTenor The Futures Maturity Tenor
	 * 
	 * @return The Treasury Futures Instance
	 */

	public static org.drip.product.govvie.TreasuryFutures TreasuryFutures (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCode,
		final org.drip.analytics.date.JulianDate[] adtEffective,
		final org.drip.analytics.date.JulianDate[] adtMaturity,
		final double[] adblCoupon,
		final double[] adblConversionFactor,
		final java.lang.String strUnderlierType,
		final java.lang.String strUnderlierSubtype,
		final java.lang.String strMaturityTenor)
	{
		if (null == dtSpot) return null;

		try {
			org.drip.product.govvie.TreasuryFutures tsyFutures = new org.drip.product.govvie.TreasuryFutures
				(org.drip.service.template.TreasuryBuilder.FromCode (strCode, adtEffective, adtMaturity,
					adblCoupon), adblConversionFactor, null);

			java.lang.String strCurrency = tsyFutures.basket()[0].currency();

			if (!tsyFutures.setExpiry (dtSpot.addBusDays (0, strCurrency).nextBondFuturesIMM (3,
				strCurrency)))
				return null;

			tsyFutures.setType (strCode);

			org.drip.market.exchange.TreasuryFuturesConvention bfc =
				org.drip.market.exchange.TreasuryFuturesConventionContainer.FromJurisdictionTypeMaturity
					(strCurrency, strUnderlierType, strUnderlierSubtype, strMaturityTenor);

			if (null == bfc) return tsyFutures;

			double dblBasketNotional = bfc.basketNotional();

			double dblMinimumPriceMovement = bfc.minimumPriceMovement();

			tsyFutures.setNotionalValue (dblBasketNotional);

			tsyFutures.setMinimumPriceMovement (dblMinimumPriceMovement);

			tsyFutures.setTickValue (dblBasketNotional * dblMinimumPriceMovement);

			org.drip.market.exchange.TreasuryFuturesEligibility bfe = bfc.eligibility();

			if (null != bfe) {
				tsyFutures.setMaximumMaturity (bfe.maturityCeiling());

				tsyFutures.setMinimumMaturity (bfe.maturityFloor());
			}

			org.drip.market.exchange.TreasuryFuturesSettle bfs = bfc.settle();

			if (null != bfs) {
				tsyFutures.setReferenceCoupon (bfs.currentReferenceYield());

				tsyFutures.setLastTradingDayLag (bfs.expiryLastTradingLag());

				tsyFutures.setDeliveryMonths (bfs.deliveryMonths());
			}

			return tsyFutures;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Treasury Futures Instance
	 * 
	 * @param dtSpot The Spot Date Instance
	 * @param strFuturesCode The Treasury Futures Code
	 * @param aiFuturesComponentTreasuryEffectiveDate Array of the Treasury Futures Component Effective Date
	 * @param aiFuturesComponentTreasuryMaturityDate Array of the Treasury Futures Component Maturity Date
	 * @param adblFuturesComponentTreasuryCoupon Array of the Treasury Futures Component Coupon
	 * @param adblFuturesComponentConversionFactor Array of the Treasury Futures Component Conversion Factor
	 * @param strFuturesComponentUnderlierSubtype Treasury Futures Component Underlier SubType (BILL/BOND)
	 * @param strFuturesReferenceMaturityTenor Treasury Futures Component Reference Maturity Tenor
	 * 
	 * @return The Treasury Futures Instance
	 */

	public static final org.drip.product.govvie.TreasuryFutures TreasuryFutures (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strFuturesCode,
		final int[] aiFuturesComponentTreasuryEffectiveDate,
		final int[] aiFuturesComponentTreasuryMaturityDate,
		final double[] adblFuturesComponentTreasuryCoupon,
		final double[] adblFuturesComponentConversionFactor,
		final java.lang.String strFuturesComponentUnderlierSubtype,
		final java.lang.String strFuturesReferenceMaturityTenor)
	{
		if (null == dtSpot || null == aiFuturesComponentTreasuryMaturityDate || null ==
			aiFuturesComponentTreasuryEffectiveDate)
			return null;

		int iNumFuturesComponentMaturity = aiFuturesComponentTreasuryMaturityDate.length;
		int iNumFuturesComponentEffective = aiFuturesComponentTreasuryEffectiveDate.length;
		org.drip.analytics.date.JulianDate[] adtFuturesComponentTreasuryMaturity = null;
		org.drip.analytics.date.JulianDate[] adtFuturesComponentTreasuryEffective = null;

		if (0 != iNumFuturesComponentMaturity)
			adtFuturesComponentTreasuryMaturity = new
				org.drip.analytics.date.JulianDate[iNumFuturesComponentMaturity];

		if (0 != iNumFuturesComponentEffective)
			adtFuturesComponentTreasuryEffective = new
				org.drip.analytics.date.JulianDate[iNumFuturesComponentEffective];

		try {
			for (int i = 0; i < iNumFuturesComponentMaturity; ++i)
				adtFuturesComponentTreasuryMaturity[i] = new org.drip.analytics.date.JulianDate
					(aiFuturesComponentTreasuryMaturityDate[i]);

			for (int i = 0; i < iNumFuturesComponentEffective; ++i)
				adtFuturesComponentTreasuryEffective[i] = new org.drip.analytics.date.JulianDate
					(aiFuturesComponentTreasuryEffectiveDate[i]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return TreasuryFutures (dtSpot, strFuturesCode, adtFuturesComponentTreasuryEffective,
			adtFuturesComponentTreasuryMaturity, adblFuturesComponentTreasuryCoupon,
				adblFuturesComponentConversionFactor, "TREASURY", strFuturesComponentUnderlierSubtype,
					strFuturesReferenceMaturityTenor);
	}

	/**
	 * Generate the Treasury Futures Instance
	 * 
	 * @param dtSpot The Spot Date Instance
	 * @param strFuturesCode The Treasury Futures Code
	 * @param aiFuturesComponentTreasuryEffectiveDate Array of the Treasury Futures Component Effective Date
	 * @param aiFuturesComponentTreasuryMaturityDate Array of the Treasury Futures Component Maturity Date
	 * @param adblFuturesComponentTreasuryCoupon Array of the Treasury Futures Component Coupon
	 * @param adblFuturesComponentConversionFactor Array of the Treasury Futures Component Conversion Factor
	 * 
	 * @return The Treasury Futures Instance
	 */

	public static final org.drip.product.govvie.TreasuryFutures TreasuryFutures (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strFuturesCode,
		final int[] aiFuturesComponentTreasuryEffectiveDate,
		final int[] aiFuturesComponentTreasuryMaturityDate,
		final double[] adblFuturesComponentTreasuryCoupon,
		final double[] adblFuturesComponentConversionFactor)
	{
		org.drip.market.exchange.TreasuryFuturesContract tfc =
			org.drip.market.exchange.TreasuryFuturesContractContainer.TreasuryFuturesContract
				(strFuturesCode);

		return null == tfc ? null : TreasuryFutures (dtSpot, tfc.code(),
			aiFuturesComponentTreasuryEffectiveDate, aiFuturesComponentTreasuryMaturityDate,
				adblFuturesComponentTreasuryCoupon, adblFuturesComponentConversionFactor, tfc.type(),
					tfc.tenor());
	}
}
