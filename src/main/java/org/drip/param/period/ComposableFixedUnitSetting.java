
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
 * <i>ComposableFixedUnitSetting</i> contains the fixed unit details. Currently it holds the coupon currency,
 * the fixed coupon, and the basis.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param">Param</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/period">Period</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ComposableFixedUnitSetting extends org.drip.param.period.ComposableUnitBuilderSetting {
	private double _dblBasis = java.lang.Double.NaN;
	private java.lang.String _strCouponCurrency = "";
	private double _dblFixedCoupon = java.lang.Double.NaN;

	/**
	 * ComposableFixedUnitSetting constructor
	 * 
	 * @param strTenor Unit Tenor
	 * @param iEdgeDateSequenceScheme Edge Date Generation Scheme
	 * @param dapEdge Date Adjust Parameter Settings for the Edge Dates
	 * @param dblFixedCoupon Fixed Coupon (Annualized)
	 * @param dblBasis Basis over the Fixed Coupon in the same units
	 * @param strCouponCurrency Coupon Currency
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public ComposableFixedUnitSetting (
		final java.lang.String strTenor,
		final int iEdgeDateSequenceScheme,
		final org.drip.analytics.daycount.DateAdjustParams dapEdge,
		final double dblFixedCoupon,
		final double dblBasis,
		final java.lang.String strCouponCurrency)
		throws java.lang.Exception
	{
		super (strTenor, iEdgeDateSequenceScheme, dapEdge);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblFixedCoupon = dblFixedCoupon) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblBasis = dblBasis) || null ==
				(_strCouponCurrency = strCouponCurrency) || _strCouponCurrency.isEmpty())
			throw new java.lang.Exception ("ComposableFixedUnitSetting ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Fixed Coupon
	 * 
	 * @return The Fixed Coupon
	 */

	public double fixedCoupon()
	{
		return _dblFixedCoupon;
	}

	/**
	 * Retrieve the Fixed Coupon Basis
	 * 
	 * @return The Fixed Coupon Basis
	 */

	public double basis()
	{
		return _dblBasis;
	}

	/**
	 * Retrieve the Fixed Coupon Currency
	 * 
	 * @return The Fixed Coupon Currency
	 */

	public java.lang.String couponCurrency()
	{
		return _strCouponCurrency;
	}
}
