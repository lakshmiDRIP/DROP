
package org.drip.param.period;

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
 * <i>CompositePeriodSetting</i> implements the custom setting parameters for the composite coupon period.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param">Param</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/period">Period</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CompositePeriodSetting {
	private int _iFreq = -1;
	private java.lang.String _strTenor = "";
	private java.lang.String _strPayCurrency = "";
	private double _dblBaseNotional = java.lang.Double.NaN;
	private org.drip.quant.common.Array2D _fsCoupon = null;
	private org.drip.quant.common.Array2D _fsNotional = null;
	private org.drip.state.identifier.EntityCDSLabel _creditLabel = null;
	private org.drip.param.period.FixingSetting _fxFixingSetting = null;
	private org.drip.analytics.daycount.DateAdjustParams _dapPay = null;

	/**
	 * CompositePeriodSetting Constructor
	 * 
	 * @param iFreq The Frequency
	 * @param strTenor The Period Tenor
	 * @param strPayCurrency The Pay Currency
	 * @param dapPay Composite Pay Date Adjust Parameters
	 * @param dblBaseNotional The Period Base Notional
	 * @param fsCoupon The Period Coupon Schedule
	 * @param fsNotional The Period Notional Schedule
	 * @param fxFixingSetting The FX Fixing Setting
	 * @param creditLabel The Period Credit Label
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public CompositePeriodSetting (
		final int iFreq,
		final java.lang.String strTenor,
		final java.lang.String strPayCurrency,
		final org.drip.analytics.daycount.DateAdjustParams dapPay,
		final double dblBaseNotional,
		final org.drip.quant.common.Array2D fsCoupon,
		final org.drip.quant.common.Array2D fsNotional,
		final org.drip.param.period.FixingSetting fxFixingSetting,
		final org.drip.state.identifier.EntityCDSLabel creditLabel)
		throws java.lang.Exception
	{
		if (0 >= (_iFreq = iFreq) || null == (_strTenor = strTenor) || _strTenor.isEmpty() || null ==
			(_strPayCurrency = strPayCurrency) || _strPayCurrency.isEmpty() ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblBaseNotional = dblBaseNotional))
			throw new java.lang.Exception ("CompositePeriodSetting ctr: Invalid Inputs");

		_dapPay = dapPay;
		_creditLabel = creditLabel;
		_fxFixingSetting = fxFixingSetting;

		if (null == (_fsCoupon = fsCoupon)) _fsCoupon = org.drip.quant.common.Array2D.BulletSchedule();

		if (null == (_fsNotional = fsNotional))
			_fsNotional = org.drip.quant.common.Array2D.BulletSchedule();
	}

	/**
	 * Retrieve the Frequency
	 * 
	 * @return The Frequency
	 */

	public int freq()
	{
		return _iFreq;
	}

	/**
	 * Retrieve the Tenor
	 * 
	 * @return The Tenor
	 */

	public java.lang.String tenor()
	{
		return _strTenor;
	}

	/**
	 * Retrieve the Pay Currency
	 * 
	 * @return The Pay Currency
	 */

	public java.lang.String payCurrency()
	{
		return _strPayCurrency;
	}

	/**
	 * Retrieve the Pay DAP
	 * 
	 * @return The Pay DAP
	 */

	public org.drip.analytics.daycount.DateAdjustParams dapPay()
	{
		return _dapPay;
	}

	/**
	 * Retrieve the Base Notional
	 * 
	 * @return The Base Notional
	 */

	public double baseNotional()
	{
		return _dblBaseNotional;
	}

	/**
	 * Retrieve the Notional Schedule
	 * 
	 * @return The Notional Schedule
	 */

	public org.drip.quant.common.Array2D notionalSchedule()
	{
		return _fsNotional;
	}

	/**
	 * Retrieve the Coupon Schedule
	 * 
	 * @return The Coupon Schedule
	 */

	public org.drip.quant.common.Array2D couponSchedule()
	{
		return _fsCoupon;
	}

	/**
	 * Retrieve the FX Fixing Setting
	 * 
	 * @return The FX Fixing Setting
	 */

	public org.drip.param.period.FixingSetting fxFixingSetting()
	{
		return _fxFixingSetting;
	}

	/**
	 * Retrieve the Credit Label
	 * 
	 * @return The Credit Label
	 */

	public org.drip.state.identifier.EntityCDSLabel creditLabel()
	{
		return _creditLabel;
	}
}
