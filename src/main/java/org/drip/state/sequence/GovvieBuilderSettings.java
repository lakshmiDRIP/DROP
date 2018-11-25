
package org.drip.state.sequence;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>GovvieBuilderSettings</i> exposes the Functionality to generate a Sequence of Govvie Curve Realizations
 * across Multiple Paths.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/sequence">Sequence</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class GovvieBuilderSettings {
	private double[] _adblTreasuryYield = null;
	private double[] _adblTreasuryCoupon = null;
	private java.lang.String[] _astrTenor = null;
	private java.lang.String _strTreasuryCode = "";
	private double[] _adblForwardYieldGround = null;
	private org.drip.analytics.date.JulianDate _dtSpot = null;
	private org.drip.state.curve.BasisSplineGovvieYield _bsgyGround = null;

	protected static final org.drip.state.curve.BasisSplineGovvieYield GovvieCurve (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCode,
		final java.lang.String[] astrTenor,
		final double[] adblCoupon,
		final double[] adblYield)
		throws Exception
	{
		int iNumInstrument = astrTenor.length;
		org.drip.analytics.date.JulianDate[] adtMaturity = new
			org.drip.analytics.date.JulianDate[iNumInstrument];
		org.drip.analytics.date.JulianDate[] adtEffective = new
			org.drip.analytics.date.JulianDate[iNumInstrument];

		for (int i = 0; i < iNumInstrument; ++i)
			adtMaturity[i] = (adtEffective[i] = dtSpot).addTenor (astrTenor[i]);

		return (org.drip.state.curve.BasisSplineGovvieYield)
			org.drip.service.template.LatentMarketStateBuilder.GovvieCurve (strCode, dtSpot, adtEffective,
				adtMaturity, adblCoupon, adblYield, "Yield",
					org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING);
	}

	/**
	 * GovvieBuilderSettings Constructor
	 * 
	 * @param dtSpot The Spot Date
	 * @param strTreasuryCode The Treasury Code
	 * @param astrTenor Array of Maturity Tenors
	 * @param adblTreasuryCoupon Array of Treasury Coupon
	 * @param adblTreasuryYield Array of Treasury Yield
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public GovvieBuilderSettings (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strTreasuryCode,
		final java.lang.String[] astrTenor,
		final double[] adblTreasuryCoupon,
		final double[] adblTreasuryYield)
		throws java.lang.Exception
	{
		if (null == (_bsgyGround = GovvieCurve (_dtSpot = dtSpot, _strTreasuryCode = strTreasuryCode,
			_astrTenor = astrTenor, _adblTreasuryCoupon = adblTreasuryCoupon, _adblTreasuryYield =
				adblTreasuryYield)))
			throw new java.lang.Exception ("GovvieBuilderSettings Constructor => Invalid Inputs");

		org.drip.state.nonlinear.FlatForwardDiscountCurve ffdcGround = _bsgyGround.flatForward (_astrTenor);

		if (null == ffdcGround || null == (_adblForwardYieldGround = ffdcGround.nodeValues()))
			throw new java.lang.Exception ("GovvieBuilderSettings Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Spot Date
	 * 
	 * @return The Spot Date
	 */

	public org.drip.analytics.date.JulianDate spot()
	{
		return _dtSpot;
	}

	/**
	 * Retrieve the Treasury Code
	 * 
	 * @return The Treasury Code
	 */

	public java.lang.String code()
	{
		return _strTreasuryCode;
	}

	/**
	 * Retrieve the Treasury Maturity Tenor Array
	 * 
	 * @return The Treasury Maturity Tenor Array
	 */

	public java.lang.String[] tenors()
	{
		return _astrTenor;
	}

	/**
	 * Retrieve the Calibration Treasury Coupon Array
	 * 
	 * @return The Calibration Treasury Coupon Array
	 */

	public double[] coupon()
	{
		return _adblTreasuryCoupon;
	}

	/**
	 * Retrieve the Calibration Treasury Yield Array
	 * 
	 * @return The Calibration Treasury Yield Array
	 */

	public double[] yield()
	{
		return _adblTreasuryYield;
	}

	/**
	 * Retrieve the Ground State Govvie Curve
	 * 
	 * @return The Ground State Govvie Curve
	 */

	public org.drip.state.curve.BasisSplineGovvieYield groundState()
	{
		return _bsgyGround;
	}

	/**
	 * Retrieve the Ground Forward Yield Array
	 * 
	 * @return The Ground Forward Yield Array
	 */

	public double[] groundForwardYield()
	{
		return _adblForwardYieldGround;
	}

	/**
	 * Retrieve the Calibration Instrument Dimension
	 * 
	 * @return The Calibration Instrument Dimension
	 */

	public int dimension()
	{
		return _astrTenor.length;
	}
}
