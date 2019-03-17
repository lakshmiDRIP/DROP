
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
 * <i>CouponSetting</i> contains the coupon type, schedule, and the coupon amount for the component. If
 * available floor and/or ceiling may also be applied to the coupon, in a pre-determined order of precedence.
 * It exports serialization into and de-serialization out of byte arrays.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product">Product</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params">Params</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CouponSetting implements org.drip.product.params.Validatable {
	private double _dblCouponRateExtension = 0.;
	private java.lang.String _strCouponType = "";
	private org.drip.numerical.common.Array2D _fs = null;
	private double _dblCouponRate = java.lang.Double.NaN;
	private double _dblCouponFloorRate = java.lang.Double.NaN;
	private double _dblCouponCeilingRate = java.lang.Double.NaN;

	/**
	 * Construct the CouponSetting from the coupon schedule, coupon type, and the coupon amount
	 * 
	 * @param fs Coupon schedule
	 * @param strCouponType Coupon Type
	 * @param dblCouponRate Coupon Rate
	 * @param dblCouponCeilingRate Coupon Ceiling Rate
	 * @param dblCouponFloorRate Coupon Floor Rate
	 */

	public CouponSetting (
		final org.drip.numerical.common.Array2D fs,
		final java.lang.String strCouponType,
		final double dblCouponRate,
		final double dblCouponCeilingRate,
		final double dblCouponFloorRate)
	{
		_fs = fs;
		_dblCouponRate = dblCouponRate;
		_strCouponType = strCouponType;
		_dblCouponFloorRate = dblCouponFloorRate;
		_dblCouponCeilingRate = dblCouponCeilingRate;
	}

	/**
	 * Construct the CouponSetting from the coupon schedule, coupon type, the coupon rate, and its extension
	 * 
	 * @param fs Coupon schedule
	 * @param strCouponType Coupon Type
	 * @param dblCouponRate Coupon Rate
	 * @param dblCouponRateExtension Coupon Rate Extension
	 * @param dblCouponCeilingRate Coupon Ceiling Rate
	 * @param dblCouponFloorRate Coupon Floor Rate
	 */

	public CouponSetting (
		final org.drip.numerical.common.Array2D fs,
		final java.lang.String strCouponType,
		final double dblCouponRate,
		final double dblCouponRateExtension,
		final double dblCouponCeilingRate,
		final double dblCouponFloorRate)
	{
		_fs = fs;
		_dblCouponRate = dblCouponRate;
		_strCouponType = strCouponType;
		_dblCouponFloorRate = dblCouponFloorRate;
		_dblCouponCeilingRate = dblCouponCeilingRate;
		_dblCouponRateExtension = dblCouponRateExtension;
	}

	/**
	 * Trim the component coupon if it falls outside the (optionally) specified coupon window. Note that
	 * 	trimming the coupon ceiling takes precedence over hiking the coupon floor.
	 * 
	 * @param dblCouponRate Input Coupon Rate
	 * @param dblDate Input Date representing the period that the coupon belongs to
	 * 
	 * @return The "trimmed" coupon Rate
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public double processCouponWindow (
		final double dblCouponRate,
		final double dblDate)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblCouponRate) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblDate))
			throw new java.lang.Exception ("CouponSetting::processCouponWindow => Invalid Inputs");

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblCouponCeilingRate) &&
			!org.drip.numerical.common.NumberUtil.IsValid (_dblCouponFloorRate))
			return dblCouponRate;

		if (!!org.drip.numerical.common.NumberUtil.IsValid (_dblCouponCeilingRate) && dblCouponRate >
			_dblCouponCeilingRate)
			return _dblCouponCeilingRate;

		if (!!org.drip.numerical.common.NumberUtil.IsValid (_dblCouponFloorRate) && dblCouponRate <
			_dblCouponFloorRate)
			return _dblCouponFloorRate;

		return dblCouponRate;
	}

	@Override public boolean validate()
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblCouponRate) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblCouponRateExtension))
			return false;

		if (null == _fs) _fs = org.drip.numerical.common.Array2D.BulletSchedule();

		if (org.drip.numerical.common.NumberUtil.IsValid (_dblCouponCeilingRate) &&
			org.drip.numerical.common.NumberUtil.IsValid (_dblCouponFloorRate) && _dblCouponCeilingRate <
				_dblCouponFloorRate)
			return false;

		return true;
	}

	/**
	 * Retrieve the Factor Schedule
	 * 
	 * @return The Factor Schedule
	 */

	public org.drip.numerical.common.Array2D factorSchedule()
	{
		return _fs;
	}

	/**
	 * Retrieve the Coupon Type
	 * 
	 * @return The Coupon Type
	 */

	public java.lang.String couponType()
	{
		return _strCouponType;
	}

	/**
	 * Retrieve the Coupon Rate
	 * 
	 * @return The Coupon Rate
	 */

	public double couponRate()
	{
		return _dblCouponRate;
	}

	/**
	 * Retrieve the Coupon Rate Extension
	 * 
	 * @return The Coupon Rate Extension
	 */

	public double couponRateExtension()
	{
		return _dblCouponRateExtension;
	}

	/**
	 * Retrieve the Coupon Ceiling Rate
	 * 
	 * @return The Coupon Ceiling Rate
	 */

	public double couponCeilingRate()
	{
		return _dblCouponCeilingRate;
	}

	/**
	 * Retrieve the Coupon Floor Rate
	 * 
	 * @return The Coupon Floor Rate
	 */

	public double couponFloorRate()
	{
		return _dblCouponFloorRate;
	}
}
