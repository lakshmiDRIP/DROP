
package org.drip.product.govvie;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>TreasuryFutures</i> implements the Treasury Futures Product Contract Details.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/README.md">Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option AssetClasses</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/govvie/README.md">Treasury Bills, Notes, Bonds, Futures</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryFutures extends org.drip.product.definition.Component {

	/*
	 * Bond Futures Valuation Settings
	 */

	public static final int FORWARD_PRICE_OAS = 0;
	public static final int FORWARD_PRICE_YIELD = 1;
	public static final int FORWARD_PRICE_ZSPREAD = 2;
	public static final int FORWARD_PRICE_CREDIT_BASIS = 3;

	private double[] _adblConversionFactor = null;
	private org.drip.product.definition.Bond[] _aBond = null;
	private org.drip.analytics.date.JulianDate _dtExpiry = null;
	private org.drip.param.valuation.CashSettleParams _csp = null;

	/*
	 * Bond Futures Contract Details
	 */

	private int[] _aiDeliveryMonth = null;
	private java.lang.String _strType = "";
	private double _dblTickValue = java.lang.Double.NaN;
	private double _dblNotionalValue = java.lang.Double.NaN;
	private double _dblReferenceCoupon = java.lang.Double.NaN;
	private int _iLastTradingDayLag = java.lang.Integer.MIN_VALUE;
	private double _dblMinimumPriceMovement = java.lang.Double.NaN;
	private java.lang.String _strDeliverableGradeMaximumMaturity = "";
	private java.lang.String _strDeliverableGradeMinimumMaturity = "";

	/**
	 * BondFutures Constructor
	 * 
	 * @param aBond Array of the Bonds on the Basket
	 * @param adblConversionFactor The Bond Conversion Factor
	 * @param csp Cash Settlement Parameters
	 * 
	 * @throws java.lang.Exception thrown if the Inputs are Invalid
	 */

	public TreasuryFutures (
		final org.drip.product.definition.Bond[] aBond,
		final double[] adblConversionFactor,
		final org.drip.param.valuation.CashSettleParams csp)
		throws java.lang.Exception
	{
		if (null == (_aBond = aBond) || null == (_adblConversionFactor = adblConversionFactor))
			throw new java.lang.Exception ("BondFutures ctr: Invalid Inputs");

		_csp = csp;
		int iNumBond = _aBond.length;

		if (0 == iNumBond || iNumBond != _adblConversionFactor.length)
			throw new java.lang.Exception ("BondFutures ctr: Invalid Inputs");

		for (int i = 0; i < iNumBond; ++i) {
			if (null == _aBond[i] || !org.drip.numerical.common.NumberUtil.IsValid (_adblConversionFactor[i]))
				throw new java.lang.Exception ("BondFutures ctr: Invalid Inputs");
		}
	}

	/**
	 * Set the Futures Type
	 * 
	 * @param strType The Futures Type
	 * 
	 * @return TRUE - Futures Type Successfully Set
	 */

	public boolean setType (
		final java.lang.String strType)
	{
		return null == (_strType = strType) || _strType.isEmpty() ? false : true;
	}

	/**
	 * Retrieve the Futures Type
	 * 
	 * @return The Futures Type
	 */

	public java.lang.String type()
	{
		return _strType;
	}

	/**
	 * Retrieve the Notional Value
	 * 
	 * @param dblNotionalValue The Notional Value
	 * 
	 * @return TRUE - The Notional Value successfully retrieved
	 */

	public boolean setNotionalValue (
		final double dblNotionalValue)
	{
		return org.drip.numerical.common.NumberUtil.IsValid (_dblNotionalValue = dblNotionalValue);
	}

	/**
	 * Retrieve the Notional Value
	 * 
	 * @return The Notional Value
	 */

	public double notionalValue()
	{
		return _dblNotionalValue;
	}

	/**
	 * Set the Reference Coupon Rate
	 * 
	 * @param dblReferenceCoupon The Reference Coupon Rate
	 * 
	 * @return TRUE - The Reference Coupon Rate successfully set
	 */

	public boolean setReferenceCoupon (
		final double dblReferenceCoupon)
	{
		return org.drip.numerical.common.NumberUtil.IsValid (_dblReferenceCoupon = dblReferenceCoupon);
	}

	/**
	 * Retrieve the Reference Coupon Rate
	 * 
	 * @return The Reference Coupon Rate
	 */

	public double referenceCoupon()
	{
		return _dblReferenceCoupon;
	}

	/**
	 * Retrieve the Deliverable Grade Minimum Maturity
	 * 
	 * @param strDeliverableGradeMinimumMaturity Minimum Maturity of the Deliverable Grade
	 * 
	 * @return TRUE - Minimum Maturity Successfully set
	 */

	public boolean setMinimumMaturity (
		final java.lang.String strDeliverableGradeMinimumMaturity)
	{
		return null == (_strDeliverableGradeMinimumMaturity = strDeliverableGradeMinimumMaturity) ||
			_strDeliverableGradeMinimumMaturity.isEmpty() ? false : true;
	}

	/**
	 * Retrieve the Minimum Maturity of the Contract
	 * 
	 * @return The Minimum Maturity of the Contract
	 */

	public java.lang.String minimumMaturity()
	{
		return _strDeliverableGradeMinimumMaturity;
	}

	/**
	 * Retrieve the Deliverable Grade Maximum Maturity
	 * 
	 * @param strDeliverableGradeMaximumMaturity Maximum Maturity of the Deliverable Grade
	 * 
	 * @return TRUE - Maximum Maturity Successfully set
	 */

	public boolean setMaximumMaturity (
		final java.lang.String strDeliverableGradeMaximumMaturity)
	{
		return null == (_strDeliverableGradeMaximumMaturity = strDeliverableGradeMaximumMaturity) ||
			_strDeliverableGradeMaximumMaturity.isEmpty() ? false : true;
	}

	/**
	 * Retrieve the Maximum Maturity of the Contract
	 * 
	 * @return The Maximum Maturity of the Contract
	 */

	public java.lang.String maximumMaturity()
	{
		return _strDeliverableGradeMaximumMaturity;
	}

	/**
	 * Set the Delivery Months
	 * 
	 * @param aiDeliveryMonth Array of Delivery Months
	 * 
	 * @return TRUE - Delivery Months successfully set
	 */

	public boolean setDeliveryMonths (
		final int[] aiDeliveryMonth)
	{
		return null == (_aiDeliveryMonth = aiDeliveryMonth) || 0 == _aiDeliveryMonth.length ? false : true;
	}

	/**
	 * Retrieve the Array of Delivery Months
	 * 
	 * @return Array of Delivery Months
	 */

	public int[] deliveryMonths()
	{
		return _aiDeliveryMonth;
	}

	/**
	 * Set the Last Trading Day Lag
	 * 
	 * @param iLastTradingDayLag The Last Trading Day Lag
	 * 
	 * @return TRUE - Last Trading Day Lag Successfully Set
	 */

	public boolean setLastTradingDayLag (
		final int iLastTradingDayLag)
	{
		return 0 > (_iLastTradingDayLag = iLastTradingDayLag) ? false : true;
	}

	/**
	 * Retrieve the Last Trading Day Lag
	 * 
	 * @return Last Trading Day Lag
	 */

	public int lastTradingDayLag()
	{
		return _iLastTradingDayLag;
	}

	/**
	 * Retrieve the Minimum Price Movement
	 * 
	 * @param dblMinimumPriceMovement The Minimum Price Movement
	 * 
	 * @return TRUE - The Minimum Price Movement Successfully Set
	 */

	public boolean setMinimumPriceMovement (
		final double dblMinimumPriceMovement)
	{
		return org.drip.numerical.common.NumberUtil.IsValid (_dblMinimumPriceMovement = dblMinimumPriceMovement);
	}

	/**
	 * Retrieve the Minimum Price Movement
	 * 
	 * @return The Minimum Price Movement
	 */

	public double minimumPriceMovement()
	{
		return _dblMinimumPriceMovement;
	}

	/**
	 * Retrieve the Tick Value
	 * 
	 * @param dblTickValue The Tick Value
	 * 
	 * @return TRUE - The Tick Value Successfully Set
	 */

	public boolean setTickValue (
		final double dblTickValue)
	{
		return org.drip.numerical.common.NumberUtil.IsValid (_dblTickValue = dblTickValue);
	}

	/**
	 * Retrieve the Tick Value
	 * 
	 * @return The Tick Value
	 */

	public double tickValue()
	{
		return _dblTickValue;
	}

	/**
	 * Retrieve the Bond Basket Array
	 * 
	 * @return The Bond Basket Array
	 */

	public org.drip.product.definition.Bond[] basket()
	{
		return _aBond;
	}

	/**
	 * Retrieve the Conversion Factor Array
	 * 
	 * @return The Conversion Factor Array
	 */

	public double[] conversionFactor()
	{
		return _adblConversionFactor;
	}

	/**
	 * Set the Futures Expiration Date
	 * 
	 * @param dtExpiry The Futures Expiration Date
	 * 
	 * @return TRUE - The Futures Expiration Date Successfully Set
	 */

	public boolean setExpiry (
		final org.drip.analytics.date.JulianDate dtExpiry)
	{
		if (null == dtExpiry) return false;

		_dtExpiry = dtExpiry;
		return true;
	}

	/**
	 * Retrieve the Futures Expiration Date
	 * 
	 * @return The Futures Expiration Date
	 */

	public org.drip.analytics.date.JulianDate expiry()
	{
		return _dtExpiry;
	}

	/**
	 * Extract the Cheapest-to-deliver Entry in the Basket Using the Current Market Parameters
	 * 
	 * @param iValueDate The Valuation Date
	 * @param csqc The Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param adblCleanPrice Array of the Bond Clean Prices
	 * @param iForwardPriceMethod Forward Price Calculation Method
	 * 
	 * @return The Cheapest-to-deliver Entry in the Basket Using the Current Market Parameters
	 */

	public org.drip.product.params.CTDEntry cheapestToDeliver (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double[] adblCleanPrice,
		final int iForwardPriceMethod)
	{
		if (null == adblCleanPrice) return null;

		int iStartIndex = 0;
		int iBasketSize = adblCleanPrice.length;

		if (iBasketSize != _aBond.length) return null;

		for (int i = 0; i < iBasketSize; ++i) {
			if (org.drip.numerical.common.NumberUtil.IsValid (adblCleanPrice[i])) {
				iStartIndex = i;
				break;
			}
		}

		if (iBasketSize <= iStartIndex) return null;

		int iCTDIndex = iStartIndex;
		double dblMinimumScaledPrice = adblCleanPrice[iStartIndex] / _adblConversionFactor[iStartIndex];

		int iExpiryDate = _dtExpiry.julian();

		if (iExpiryDate <= iValueDate) return null;

		org.drip.param.valuation.ValuationParams valParamsSpot =
			org.drip.param.valuation.ValuationParams.Spot (iValueDate);

		org.drip.param.valuation.ValuationParams valParamsExpiry =
			org.drip.param.valuation.ValuationParams.Spot (iExpiryDate);

		for (int i = iStartIndex; i < iBasketSize; ++i) {
			if (!org.drip.numerical.common.NumberUtil.IsValid (adblCleanPrice[i])) continue;

			double dblForwardPrice = java.lang.Double.NaN;

			try {
				if (FORWARD_PRICE_YIELD == iForwardPriceMethod)
					dblForwardPrice = org.drip.analytics.support.FuturesHelper.ForwardBondYieldPrice
						(_aBond[i], valParamsSpot, valParamsExpiry, csqc, vcp, adblCleanPrice[i]);
				else if (FORWARD_PRICE_OAS == iForwardPriceMethod)
					dblForwardPrice = org.drip.analytics.support.FuturesHelper.ForwardBondOASPrice
						(_aBond[i], valParamsSpot, valParamsExpiry, csqc, vcp, adblCleanPrice[i]);
				else if (FORWARD_PRICE_ZSPREAD == iForwardPriceMethod)
					dblForwardPrice = org.drip.analytics.support.FuturesHelper.ForwardBondZSpreadPrice
						(_aBond[i], valParamsSpot, valParamsExpiry, csqc, vcp, adblCleanPrice[i]);
				else if (FORWARD_PRICE_CREDIT_BASIS == iForwardPriceMethod)
					dblForwardPrice = org.drip.analytics.support.FuturesHelper.ForwardBondCreditPrice
						(_aBond[i], valParamsSpot, valParamsExpiry, csqc, vcp, adblCleanPrice[i]);
				else
					return null;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			double dblScaledPrice = dblForwardPrice / _adblConversionFactor[i];

			if (dblScaledPrice < dblMinimumScaledPrice) {
				iCTDIndex = i;
				dblMinimumScaledPrice = dblScaledPrice;
			}
		}

		try {
			return new org.drip.product.params.CTDEntry (_aBond[iCTDIndex], _adblConversionFactor[iCTDIndex],
				dblMinimumScaledPrice * _adblConversionFactor[iCTDIndex]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Extract the Cheapest-to-deliver Entry in the Basket Using the Current Market Prices Alone
	 * 
	 * @param iValueDate The Valuation Date
	 * @param adblCleanPrice Array of the Bond Clean Prices
	 * 
	 * @return The Cheapest-to-deliver Entry in the Basket Using the Current Market Prices Alone
	 */

	public org.drip.product.params.CTDEntry cheapestToDeliverYield (
		final int iValueDate,
		final double[] adblCleanPrice)
	{
		return cheapestToDeliver (iValueDate, null, null, adblCleanPrice, FORWARD_PRICE_YIELD);
	}

	/**
	 * Extract the Cheapest-to-deliver Entry in the Basket Using Bond OAS Metric
	 * 
	 * @param iValueDate The Valuation Date
	 * @param csqc The Market Parameters
	 * @param adblCleanPrice Array of the Bond Clean Prices
	 * 
	 * @return The Cheapest-to-deliver Entry in the Basket Using Bond OAS Metric
	 */

	public org.drip.product.params.CTDEntry cheapestToDeliverOAS (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final double[] adblCleanPrice)
	{
		return cheapestToDeliver (iValueDate, csqc, null, adblCleanPrice, FORWARD_PRICE_OAS);
	}

	/**
	 * Extract the Cheapest-to-deliver Entry in the Basket Using Bond Z Spread Metric
	 * 
	 * @param iValueDate The Valuation Date
	 * @param csqc The Market Parameters
	 * @param adblCleanPrice Array of the Bond Clean Prices
	 * 
	 * @return The Cheapest-to-deliver Entry in the Basket Using Bond Z Spread Metric
	 */

	public org.drip.product.params.CTDEntry cheapestToDeliverZSpread (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final double[] adblCleanPrice)
	{
		return cheapestToDeliver (iValueDate, csqc, null, adblCleanPrice, FORWARD_PRICE_ZSPREAD);
	}

	/**
	 * Extract the Cheapest-to-deliver Entry in the Basket Using Bond Credit Basis Metric
	 * 
	 * @param iValueDate The Valuation Date
	 * @param csqc The Market Parameters
	 * @param adblCleanPrice Array of the Bond Clean Prices
	 * 
	 * @return The Cheapest-to-deliver Entry in the Basket Using Bond Credit Basis Metric
	 */

	public org.drip.product.params.CTDEntry cheapestToDeliverCreditBasis (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final double[] adblCleanPrice)
	{
		return cheapestToDeliver (iValueDate, csqc, null, adblCleanPrice, FORWARD_PRICE_CREDIT_BASIS);
	}

	@Override public java.lang.String name()
	{
		return (null == _strType || _strType.isEmpty() ? _aBond[0].currency() : _strType) +
			(null == _strDeliverableGradeMaximumMaturity || _strDeliverableGradeMaximumMaturity.isEmpty() ?
				_aBond[0].tenor() : _strDeliverableGradeMaximumMaturity);
	}

	@Override public org.drip.param.valuation.CashSettleParams cashSettleParams()
	{
		return _csp;
	}

	@Override public org.drip.analytics.output.CompositePeriodCouponMetrics couponMetrics (
		final int iAccrualEndDate,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
	{
		return null;
	}

	@Override public java.util.List<org.drip.analytics.cashflow.CompositePeriod> couponPeriods()
	{
		return null;
	}

	@Override public org.drip.analytics.date.JulianDate effectiveDate()
	{
		return _dtExpiry.subtractTenor ("3M");
	}

	@Override public org.drip.analytics.date.JulianDate firstCouponDate()
	{
		return null;
	}

	@Override public int freq()
	{
		return _aBond[0].freq();
	}

	@Override public double initialNotional()
	{
		return _dblNotionalValue;
	}

	@Override public double notional (
		final int iDate)
		throws java.lang.Exception
	{
		return _dblNotionalValue;
	}

	@Override public double notional (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		return _dblNotionalValue;
	}

	@Override public org.drip.analytics.date.JulianDate maturityDate()
	{
		return _dtExpiry;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> couponCurrency()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> mapCouponCurrency = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String>();

		for (org.drip.product.definition.Bond bond : _aBond)
			mapCouponCurrency.putAll (bond.couponCurrency());

		return 0 == mapCouponCurrency.size() ? null : mapCouponCurrency;
	}

	@Override public java.lang.String principalCurrency()
	{
		return _aBond[0].principalCurrency();
	}

	@Override public java.lang.String payCurrency()
	{
		return _aBond[0].payCurrency();
	}

	@Override public org.drip.state.identifier.EntityCDSLabel creditLabel()
	{
		return _aBond[0].creditLabel();
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
			forwardLabel()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
			mapForwardLabel = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>();

		for (org.drip.product.definition.Bond bond : _aBond)
			mapForwardLabel.putAll (bond.forwardLabel());

		return 0 == mapForwardLabel.size() ? null : mapForwardLabel;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.OTCFixFloatLabel>
			otcFixFloatLabel()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.OTCFixFloatLabel>
			mapOTCFixFloatLabel = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.OTCFixFloatLabel>();

		for (org.drip.product.definition.Bond bond : _aBond)
			mapOTCFixFloatLabel.putAll (bond.otcFixFloatLabel());

		return 0 == mapOTCFixFloatLabel.size() ? null : mapOTCFixFloatLabel;
	}

	@Override public org.drip.state.identifier.FundingLabel fundingLabel()
	{
		return _aBond[0].fundingLabel();
	}

	@Override public org.drip.state.identifier.GovvieLabel govvieLabel()
	{
		return _aBond[0].govvieLabel();
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel>
		fxLabel()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel> mapFXLabel = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel>();

		for (org.drip.product.definition.Bond bond : _aBond)
			mapFXLabel.putAll (bond.fxLabel());

		return 0 == mapFXLabel.size() ? null : mapFXLabel;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.VolatilityLabel>
			volatilityLabel()
	{
		return null;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		int iNumBond = _aBond.length;
		double dblCTDRepoRate = java.lang.Double.NaN;
		double[] adblCleanPrice = new double[iNumBond];
		double dblCTDTreasuryYield = java.lang.Double.NaN;

		int iValueDate = valParams.valueDate();

		for (int i = 0; i < iNumBond; ++i) {
			org.drip.param.definition.ProductQuote pqBond = csqc.productQuote (_aBond[i].name());

			if (null == pqBond || !pqBond.containsQuote ("Price") ||
				!org.drip.numerical.common.NumberUtil.IsValid (adblCleanPrice[i] = pqBond.quote ("Price").value
					("mid")))
				return null;
		}

		org.drip.product.params.CTDEntry ctdEntry = cheapestToDeliverYield (iValueDate, adblCleanPrice);

		if (null == ctdEntry || null == csqc) return null;

		org.drip.product.definition.Bond bondCTD = ctdEntry.bond();

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapBondFuturesMeasure =
			bondCTD.value (valParams, pricerParams, csqc, vcp);

		if (null == mapBondFuturesMeasure || null == (mapBondFuturesMeasure =
			org.drip.service.common.CollectionUtil.PrefixKeys (bondCTD.value (valParams, pricerParams, csqc,
				vcp), "CTD::")))
			return null;

		double dblCTDForwardPrice = ctdEntry.forwardPrice();

		double dblCTDConversionFactor = ctdEntry.conversionFactor();

		mapBondFuturesMeasure.put ("CTDConversionFactor", dblCTDConversionFactor);

		mapBondFuturesMeasure.put ("CTDForwardPrice", dblCTDForwardPrice);

		org.drip.state.repo.RepoCurve repoState = csqc.repoState
			(org.drip.state.identifier.RepoLabel.Standard (bondCTD.name()));

		org.drip.state.govvie.GovvieCurve gc = csqc.govvieState (govvieLabel());

		int iExpiryDate = _dtExpiry.julian();

		try {
			if (null != repoState)
				mapBondFuturesMeasure.put ("CTDRepoRate", dblCTDRepoRate = repoState.repo (iExpiryDate));

			if (null != gc)
				mapBondFuturesMeasure.put ("CTDTreasuryYield", dblCTDTreasuryYield = gc.yield (iExpiryDate));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		double dblCTDSpotPrice = csqc.productQuote (bondCTD.name()).quote ("Price").value ("mid");

		mapBondFuturesMeasure.put ("CTDSpotPrice", dblCTDSpotPrice);

		if (org.drip.numerical.common.NumberUtil.IsValid (dblCTDRepoRate) &&
			org.drip.numerical.common.NumberUtil.IsValid (dblCTDTreasuryYield))
			mapBondFuturesMeasure.put ("CostOfCarry", dblCTDRepoRate - dblCTDTreasuryYield);

		org.drip.param.definition.ProductQuote pqFutures = csqc.productQuote (name());

		if (null != pqFutures && pqFutures.containsQuote ("Price")) {
			double dblFuturesQuote = pqFutures.quote ("Price").value ("mid");

			if (org.drip.numerical.common.NumberUtil.IsValid (dblFuturesQuote)) {
				if (iExpiryDate > iValueDate) {
					double dblImpliedRepoRate = 365.25 * (((dblFuturesQuote - dblCTDSpotPrice) /
						dblCTDSpotPrice) - 1.) / (iExpiryDate - iValueDate);

					mapBondFuturesMeasure.put ("ImpliedRepo", dblImpliedRepoRate);

					mapBondFuturesMeasure.put ("ImpliedRepoRate", dblImpliedRepoRate);

					if (org.drip.numerical.common.NumberUtil.IsValid (dblCTDRepoRate))
						mapBondFuturesMeasure.put ("NetBasis", dblImpliedRepoRate - dblCTDRepoRate);
				}

				double dblSettlementAmount = dblFuturesQuote * dblCTDConversionFactor - dblCTDForwardPrice;

				mapBondFuturesMeasure.put ("PV", dblSettlementAmount);

				mapBondFuturesMeasure.put ("SettlementAmount", dblSettlementAmount);

				mapBondFuturesMeasure.put ("SettlePV", dblSettlementAmount);

				org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqc.fundingState
					(fundingLabel());

				if (null != dcFunding) {
					try {
						mapBondFuturesMeasure.put ("SpotPV", dblSettlementAmount * dcFunding.df
							(iExpiryDate));
					} catch (java.lang.Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		return mapBondFuturesMeasure;
	}

	@Override public java.util.Set<java.lang.String> measureNames()
	{
		java.util.Set<java.lang.String> setMeasure = _aBond[0].measureNames();

		setMeasure.add ("CostOfCarry");

		setMeasure.add ("CTDConversionFactor");

		setMeasure.add ("CTDForwardPrice");

		setMeasure.add ("CTDRepoRate");

		setMeasure.add ("CTDSpotPrice");

		setMeasure.add ("CTDTreasuryYield");

		setMeasure.add ("ImpliedRepo");

		setMeasure.add ("ImpliedRepoRate");

		setMeasure.add ("NetBasis");

		setMeasure.add ("PV");

		setMeasure.add ("SettlementAmount");

		setMeasure.add ("SettlePV");

		setMeasure.add ("SpotPV");

		return setMeasure;
	}

	@Override public double pv (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
		throws java.lang.Exception
	{
		int iNumBond = _aBond.length;
		double[] adblCleanPrice = new double[iNumBond];

		int iValueDate = valParams.valueDate();

		for (int i = 0; i < iNumBond; ++i) {
			org.drip.param.definition.ProductQuote pqBond = csqc.productQuote (_aBond[i].name());

			if (null == pqBond || !pqBond.containsQuote ("Price") ||
				!org.drip.numerical.common.NumberUtil.IsValid (adblCleanPrice[i] = pqBond.quote ("Price").value
					("mid")))
				throw new java.lang.Exception ("BondFutures::pv - Invalid Inputs");
		}

		org.drip.product.params.CTDEntry ctdEntry = cheapestToDeliverYield (iValueDate, adblCleanPrice);

		if (null == ctdEntry || null == csqc)
			throw new java.lang.Exception ("BondFutures::pv - Invalid Inputs");

		double dblFuturesQuote = java.lang.Double.NaN;

		double dblCTDForwardPrice = ctdEntry.forwardPrice();

		double dblCTDConversionFactor = ctdEntry.conversionFactor();

		org.drip.param.definition.ProductQuote pqFutures = csqc.productQuote (name());

		if (null != pqFutures && pqFutures.containsQuote ("Price"))
			dblFuturesQuote = pqFutures.quote ("Price").value ("mid");

		double dblAccrued = ctdEntry.bond().accrued (iValueDate, csqc);

		return org.drip.numerical.common.NumberUtil.IsValid (dblFuturesQuote) ? dblFuturesQuote *
			dblCTDConversionFactor + dblAccrued : dblCTDForwardPrice + dblAccrued;
	}
}
