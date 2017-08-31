
package org.drip.product.params;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * CouponSetting contains the coupon type, schedule, and the coupon amount for the component. If available
 *  floor and/or ceiling may also be applied to the coupon, in a pre-determined order of precedence. It
 *  exports serialization into and de-serialization out of byte arrays.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CouponSetting implements org.drip.product.params.Validatable {
	private double _dblCouponRateExtension = 0.;
	private java.lang.String _strCouponType = "";
	private org.drip.quant.common.Array2D _fs = null;
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
		final org.drip.quant.common.Array2D fs,
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
		final org.drip.quant.common.Array2D fs,
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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblCouponRate) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblDate))
			throw new java.lang.Exception ("CouponSetting::processCouponWindow => Invalid Inputs");

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblCouponCeilingRate) &&
			!org.drip.quant.common.NumberUtil.IsValid (_dblCouponFloorRate))
			return dblCouponRate;

		if (!!org.drip.quant.common.NumberUtil.IsValid (_dblCouponCeilingRate) && dblCouponRate >
			_dblCouponCeilingRate)
			return _dblCouponCeilingRate;

		if (!!org.drip.quant.common.NumberUtil.IsValid (_dblCouponFloorRate) && dblCouponRate <
			_dblCouponFloorRate)
			return _dblCouponFloorRate;

		return dblCouponRate;
	}

	@Override public boolean validate()
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblCouponRate) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblCouponRateExtension))
			return false;

		if (null == _fs) _fs = org.drip.quant.common.Array2D.BulletSchedule();

		if (org.drip.quant.common.NumberUtil.IsValid (_dblCouponCeilingRate) &&
			org.drip.quant.common.NumberUtil.IsValid (_dblCouponFloorRate) && _dblCouponCeilingRate <
				_dblCouponFloorRate)
			return false;

		return true;
	}

	/**
	 * Retrieve the Factor Schedule
	 * 
	 * @return The Factor Schedule
	 */

	public org.drip.quant.common.Array2D factorSchedule()
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
