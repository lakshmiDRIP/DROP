
package org.drip.product.params;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>NotionalSetting</i> contains the product's notional schedule and the amount. It also incorporates hints
 * on how the notional factors are to be interpreted - off of the original or the current notional. Further
 * flags tell whether the notional factor is to be applied at the start/end/average of the coupon period. It
 * exports serialization into and de-serialization out of byte arrays.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product">Product</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params">Params</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NotionalSetting implements org.drip.product.params.Validatable {

	/**
	 * Period amortization proxies to the period start factor
	 */

	public static final int PERIOD_AMORT_AT_START = 1;

	/**
	 * Period amortization proxies to the period end factor
	 */

	public static final int PERIOD_AMORT_AT_END = 2;

	/**
	 * Period amortization proxies to the period effective factor
	 */

	public static final int PERIOD_AMORT_EFFECTIVE = 3;

	private boolean _bPriceOffOriginalNotional = false;
	private java.lang.String _strDenominationCurrency = "";
	private double _dblNotionalAmount = java.lang.Double.NaN;
	private int _iPeriodAmortizationMode = PERIOD_AMORT_AT_START;
	private org.drip.quant.common.Array2D _fsOutstanding = null;

	/**
	 * Construct the NotionalSetting from the notional schedule and the amount.
	 * 
	 * @param fsOutstanding Outstanding Factor Schedule
	 * @param dblNotionalAmount Notional Amount
	 * @param strDenominationCurrency The Currency of Denomination
	 * @param iPeriodAmortizationMode Period Amortization Proxy Mode
	 * @param bPriceOffOriginalNotional Indicates whether the price is based off of the original notional
	 */

	public NotionalSetting (
		final double dblNotionalAmount,
		final java.lang.String strDenominationCurrency,
		final org.drip.quant.common.Array2D fsOutstanding,
		final int iPeriodAmortizationMode,
		final boolean bPriceOffOriginalNotional)
	{
		_fsOutstanding = fsOutstanding;
		_dblNotionalAmount = dblNotionalAmount;
		_iPeriodAmortizationMode = iPeriodAmortizationMode;
		_strDenominationCurrency = strDenominationCurrency;
		_bPriceOffOriginalNotional = bPriceOffOriginalNotional;
	}

	@Override public boolean validate()
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblNotionalAmount) || null ==
			_strDenominationCurrency || _strDenominationCurrency.isEmpty())
			return false;

		if (null == _fsOutstanding) _fsOutstanding = org.drip.quant.common.Array2D.BulletSchedule();

		return true;
	}

	/**
	 * Retrieve the Notional Amount
	 * 
	 * @return The Notional Amount
	 */

	public double notionalAmount()
	{
		return _dblNotionalAmount;
	}

	/**
	 * Retrieve "Price Off Of Original Notional" Flag
	 * 
	 * @return TRUE - Price Quote is based off of the original notional
	 */

	public boolean priceOffOfOriginalNotional()
	{
		return _bPriceOffOriginalNotional;
	}

	/**
	 * Retrieve the Period Amortization Mode
	 * 
	 * @return The Period Amortization Mode
	 */

	public int periodAmortizationMode()
	{
		return _iPeriodAmortizationMode;
	}

	/**
	 * Retrieve the Outstanding Factor Schedule
	 * 
	 * @return The Outstanding Factor Schedule
	 */

	public org.drip.quant.common.Array2D outstandingFactorSchedule()
	{
		return _fsOutstanding;
	}

	/**
	 * Currency in which the Notional is specified
	 * 
	 * @return The Currency of Denomination
	 */

	public java.lang.String denominationCurrency()
	{
		return _strDenominationCurrency;
	}
}
