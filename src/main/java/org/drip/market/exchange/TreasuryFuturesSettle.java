
package org.drip.market.exchange;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>TreasuryFuturesSettle</i> contains the Settlement Details for the Futures Basket of the Exchange-Traded
 * Treasury Futures Contracts.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Static Market Fields - the Definitions, the OTC/Exchange Traded Products, and Treasury Settings</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/exchange">Deliverable Swap, STIR, Treasury Futures</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryFuturesSettle {

	/**
	 * Cash Settled Futures
	 */

	public static final int SETTLE_TYPE_CASH = 1;

	/**
	 * Physically Settled Futures
	 */

	public static final int SETTLE_TYPE_PHYSICAL_DELIVERY = 2;

	/**
	 * Settle Quote Type - AUD Bank Bill Type - Uses a Flat Reference Index
	 */

	public static final int QUOTE_REFERENCE_INDEX_FLAT = 1;

	/**
	 * Settle Quote Type - Uses a Reference Index Based off of Conversion Factor
	 */

	public static final int QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR = 2;

	/**
	 * Settle Quote Type - Uses a Reference Index Based off of Conversion Factor Computed AUD Bond Futures
	 *  Style
	 */

	public static final int QUOTE_REFERENCE_INDEX_AUD_BOND_FUTURES_STYLE = 3;

	private int _iSettleType = -1;
	private int _iSettleQuoteStyle = -1;
	private int[] _aiDeliveryMonth = null;
	private int _iExpiryLastTradingLag = -1;
	private boolean _bWildCardOption = false;
	private int _iExpiryFirstDeliveryLag = -1;
	private int _iExpiryFinalDeliveryLag = -1;
	private int _iExpiryDeliveryNoticeLag = -1;
	private double _dblReferenceCouponCurrent = java.lang.Double.NaN;
	private double _dblReferenceCouponOriginal = java.lang.Double.NaN;

	/**
	 * TreasuryFuturesSettle Constructor
	 * 
	 * @param iExpiryFirstDeliveryLag Lag Between the Expiry and the First Delivery Dates
	 * @param iExpiryFinalDeliveryLag Lag Between the Expiry and the Final Delivery Dates
	 * @param iExpiryDeliveryNoticeLag Lag between the Expiry and the Delivery Notice
	 * @param iExpiryLastTradingLag Lag between the Expiry and the Last Trading Day
	 * @param iSettleType Settlement Type
	 * @param iSettleQuoteStyle Settlement Quote Style
	 * @param bWildCardOption TRUE - Turn ON the Wild Card Option
	 * @param dblReferenceCouponCurrent The Current Reference Coupon
	 * @param dblReferenceCouponOriginal The Original Reference Coupon
	 * @param aiDeliveryMonth Array of the Delivery Months
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public TreasuryFuturesSettle (
		final int iExpiryFirstDeliveryLag,
		final int iExpiryFinalDeliveryLag,
		final int iExpiryDeliveryNoticeLag,
		final int iExpiryLastTradingLag,
		final int iSettleType,
		final int iSettleQuoteStyle,
		final boolean bWildCardOption,
		final double dblReferenceCouponCurrent,
		final double dblReferenceCouponOriginal,
		final int[] aiDeliveryMonth)
		throws java.lang.Exception
	{
		if ((_iExpiryFinalDeliveryLag = iExpiryFinalDeliveryLag) < (_iExpiryFirstDeliveryLag =
			iExpiryFirstDeliveryLag) || _iExpiryFirstDeliveryLag < 0)
			throw new java.lang.Exception ("TreasuryFuturesSettle ctr: Invalid Inputs");

		_iSettleType = iSettleType;
		_aiDeliveryMonth = aiDeliveryMonth;
		_bWildCardOption = bWildCardOption;
		_iSettleQuoteStyle = iSettleQuoteStyle;
		_iExpiryLastTradingLag = iExpiryLastTradingLag;
		_iExpiryFirstDeliveryLag = iExpiryFirstDeliveryLag;
		_iExpiryFinalDeliveryLag = iExpiryFinalDeliveryLag;
		_iExpiryDeliveryNoticeLag = iExpiryDeliveryNoticeLag;
		_dblReferenceCouponCurrent = dblReferenceCouponCurrent;
		_dblReferenceCouponOriginal = dblReferenceCouponOriginal;
	}

	/**
	 * Retrieve the Lag Between the Expiry and the First Delivery Dates
	 * 
	 * @return The Lag Between the Expiry and the First Delivery Dates
	 */

	public int expiryFirstDeliveryLag()
	{
		return _iExpiryFirstDeliveryLag;
	}

	/**
	 * Retrieve the Lag Between the Expiry and the Final Delivery Dates
	 * 
	 * @return The Lag Between the Expiry and the Final Delivery Dates
	 */

	public int expiryFinalDeliveryLag()
	{
		return _iExpiryFinalDeliveryLag;
	}

	/**
	 * Retrieve the Lag Between the Expiry and the Delivery Notice Dates
	 * 
	 * @return The Lag Between the Expiry and the Delivery Notice Dates
	 */

	public int expiryDeliveryNoticeLag()
	{
		return _iExpiryDeliveryNoticeLag;
	}

	/**
	 * Retrieve the Lag Between the Expiry and the Last Trading Dates
	 * 
	 * @return The Lag Between the Expiry and the Last Trading Dates
	 */

	public int expiryLastTradingLag()
	{
		return _iExpiryLastTradingLag;
	}

	/**
	 * Retrieve the Settle Type
	 * 
	 * @return The Settle Type
	 */

	public int settleType()
	{
		return _iSettleType;
	}

	/**
	 * Retrieve the Settle Quote Style
	 * 
	 * @return The Settle Quote Style
	 */

	public int settleQuoteStyle()
	{
		return _iSettleQuoteStyle;
	}

	/**
	 * Retrieve the Bond Futures Wild Card Option Setting
	 * 
	 * @return Bond Futures Wild Card Option Setting
	 */

	public boolean wildCardOption()
	{
		return _bWildCardOption;
	}

	/**
	 * Retrieve the Current Reference Coupon
	 * 
	 * @return The Current Reference Coupon
	 */

	public double currentReferenceYield()
	{
		return _dblReferenceCouponCurrent;
	}

	/**
	 * Retrieve the Original Reference Coupon
	 * 
	 * @return The Original Reference Coupon
	 */

	public double originalReferenceCoupon()
	{
		return _dblReferenceCouponOriginal;
	}

	/**
	 * Retrieve the Delivery Months
	 * 
	 * @return The Array of Delivery Months
	 */

	public int[] deliveryMonths()
	{
		return _aiDeliveryMonth;
	}

	@Override public java.lang.String toString()
	{
		java.lang.String strDeliveryMonths = "";
		int iNumDeliveryMonth = null == _aiDeliveryMonth ? 0 : _aiDeliveryMonth.length;

		if (0 != iNumDeliveryMonth) {
			for (int i = 0; i < iNumDeliveryMonth; ++i) {
				if (0 == i)
					strDeliveryMonths += " | Delivery Months: {";
				else
					strDeliveryMonths += ",";

				strDeliveryMonths += org.drip.analytics.date.DateUtil.MonthChar (_aiDeliveryMonth[i]);
			}

			strDeliveryMonths += "}";
		}

		return "[Futures Settle => Expiry To First Delivery Lag: " + _iExpiryFirstDeliveryLag +
			" | Expiry To Final Delivery Lag: " + _iExpiryFinalDeliveryLag +
				" | Expiry To Delivery Notice Lag: " + _iExpiryDeliveryNoticeLag +
					" | Expiry To Last Trading Lag: " + _iExpiryLastTradingLag + " | Settlement Type:  " +
						_iSettleType + " | Settlement Quote Style: " + _iSettleQuoteStyle + " | Wild Card: "
							+ _bWildCardOption + " | Current Reference Coupon: " + _dblReferenceCouponCurrent
								+ " | Original Reference Coupon: " + _dblReferenceCouponOriginal +
									strDeliveryMonths + "]";
	}
}
