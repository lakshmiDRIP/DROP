
package org.drip.analytics.output;

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
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * <i>BondCouponMeasures</i> encapsulates the parsimonious but complete set of the cash-flow oriented coupon
 * measures generated out of a full bond analytics run to a given work-out. These are:
 *
 *	<br><br>
 *  <ul>
 * 		<li>
 * 			DV01
 * 		</li>
 * 		<li>
 * 			PV Measures (Coupon PV, Index Coupon PV, PV)
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Analytics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output/README.md">Output</a></li>
 *  </ul>
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblDV01 = dblDV01) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblCouponPV = dblCouponPV) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblPV = dblPV))
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblCashPayDF)) return false;

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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblAccrued01) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblCoupon))
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
