
package org.drip.analytics.output;

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
 * This class encapsulates the parsimonius but complete set of the cash-flow oriented coupon measures
 * 	generated out of a full bond analytics run to a given work-out. These are:
 * 	- DV01
 * 	- PV Measures (Coupon PV, Index Coupon PV, PV)
 *
 * @author Lakshmi Krishnamurthy
 */

public class BondCouponMeasures {
	private double _dblDV01 = java.lang.Double.NaN;
	private double _dblIndexCouponPV = java.lang.Double.NaN;
	private double _dblCouponPV = java.lang.Double.NaN;
	private double _dblPV = java.lang.Double.NaN;

	/**
	 * BondCouponMeasures constructor
	 * 
	 * @param dblDV01 DV01
	 * @param dblIndexCouponPV Index Coupon PV
	 * @param dblCouponPV Coupon PV
	 * @param dblPV PV
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public BondCouponMeasures (
		final double dblDV01,
		final double dblIndexCouponPV,
		final double dblCouponPV,
		final double dblPV)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblDV01 = dblDV01) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblCouponPV = dblCouponPV) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblPV = dblPV))
			throw new java.lang.Exception ("BondCouponMeasures ctr: Invalid Inputs!");

		_dblIndexCouponPV = dblIndexCouponPV;
	}

	/**
	 * Adjust the bond coupon measures by a cash settlement discount factor
	 * 
	 * @param dblCashPayDF Cash Pay discount factor
	 * 
	 * @return TRUE - if the adjustment has been successfully applied
	 */

	public boolean adjustForSettlement (
		final double dblCashPayDF)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblCashPayDF)) return false;

		_dblDV01 /= dblCashPayDF;
		_dblIndexCouponPV /= dblCashPayDF;
		_dblCouponPV /= dblCashPayDF;
		_dblPV /= dblCashPayDF;
		return true;
	}

	/**
	 * Retrieve the DV01
	 * 
	 * @return DV01
	 */

	public double dv01()
	{
		return _dblDV01;
	}

	/**
	 * Retrieve the Index Coupon PV
	 * 
	 * @return Index Coupon PV
	 */

	public double indexCouponPV()
	{
		return _dblIndexCouponPV;
	}

	/**
	 * Retrieve the Coupon PV
	 * 
	 * @return Coupon PV
	 */

	public double couponPV()
	{
		return _dblCouponPV;
	}

	/**
	 * Retrieve the PV
	 * 
	 * @return PV
	 */

	public double pv()
	{
		return _dblPV;
	}

	/**
	 * Adjust Measures for accrued
	 * 
	 * @param dblAccrued01 Accrued 01
	 * @param dblCoupon Coupon during the accrued phase
	 * @param dblIndex Index Rate during the accrued phase
	 * @param bDirtyFromClean True - Change measures from Clean to Dirty
	 * 
	 * @return True - if the adjustment has been successfully applied
	 */

	public boolean adjustForAccrual (
		final double dblAccrued01,
		final double dblCoupon,
		final double dblIndex,
		final boolean bDirtyFromClean)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblAccrued01) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblCoupon))
			return false;

		if (bDirtyFromClean)
			_dblDV01 -= dblAccrued01;
		else
			_dblDV01 += dblAccrued01;

		if (bDirtyFromClean)
			_dblIndexCouponPV -= dblAccrued01 * dblIndex;
		else
			_dblIndexCouponPV += dblAccrued01 * dblIndex;

		if (bDirtyFromClean)
			_dblCouponPV -= dblAccrued01 * dblCoupon;
		else
			_dblCouponPV += dblAccrued01 * dblCoupon;

		if (bDirtyFromClean)
			_dblPV -= dblAccrued01 * dblCoupon;
		else
			_dblPV += dblAccrued01 * dblCoupon;

		return true;
	}

	/**
	 * Return the state as a named measure map
	 * 
	 * @param strPrefix Measure name prefix
	 * 
	 * @return Map of the measures
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> toMap (
		final java.lang.String strPrefix)
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapMeasures = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		mapMeasures.put (strPrefix + "DV01", _dblDV01);

		mapMeasures.put (strPrefix + "IndexCouponPV", _dblIndexCouponPV);

		mapMeasures.put (strPrefix + "CouponPV", _dblCouponPV);

		mapMeasures.put (strPrefix + "PV", _dblPV);

		return mapMeasures;
	}
}
