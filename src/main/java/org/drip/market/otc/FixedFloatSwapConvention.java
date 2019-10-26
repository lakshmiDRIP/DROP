
package org.drip.market.otc;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>FixedFloatSwapConvention</i> contains the Details of the Fixed-Float Swap Component of an OTC contact.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Static Market Fields - the Definitions, the OTC/Exchange Traded Products, and Treasury Settings</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/otc">OTC Dual Stream Option Container</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixedFloatSwapConvention {
	private int _iSpotLag = -1;
	private org.drip.market.otc.FixedStreamConvention _fixedConv = null;
	private org.drip.market.otc.FloatStreamConvention _floatConv = null;

	/**
	 * FixedFloatSwapConvention Constructor
	 * 
	 * @param fixedConv Fixed Stream Convention
	 * @param floatConv Float Stream Convention
	 * @param iSpotLag Spot Lag
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public FixedFloatSwapConvention (
		final org.drip.market.otc.FixedStreamConvention fixedConv,
		final org.drip.market.otc.FloatStreamConvention floatConv,
		final int iSpotLag)
		throws java.lang.Exception
	{
		if (null == (_fixedConv = fixedConv) || null == (_floatConv = floatConv) || 0 > (_iSpotLag =
			iSpotLag))
			throw new java.lang.Exception ("FixedFloatSwapConvention ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Fixed Stream Convention
	 * 
	 * @return The Fixed Stream Convention
	 */

	public org.drip.market.otc.FixedStreamConvention fixedStreamConvention()
	{
		return _fixedConv;
	}

	/**
	 * Retrieve the Float Stream Convention
	 * 
	 * @return The Float Stream Convention
	 */

	public org.drip.market.otc.FloatStreamConvention floatStreamConvention()
	{
		return _floatConv;
	}

	/**
	 * Retrieve the Spot Lag
	 * 
	 * @return The Spot Lag
	 */

	public int spotLag()
	{
		return _iSpotLag;
	}

	/**
	 * Create a Standardized Fixed-Float Component Instance from the Inputs
	 * 
	 * @param dtSpot The Spot Date
	 * @param strMaturityTenor The Maturity Tenor
	 * @param dblFixedCoupon The Fixed Coupon
	 * @param dblFloatBasis The Float Basis
	 * @param dblNotional Notional
	 * 
	 * @return The Standardized Fixed-Float Component Instance
	 */

	public org.drip.product.rates.FixFloatComponent createFixFloatComponent (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strMaturityTenor,
		final double dblFixedCoupon,
		final double dblFloatBasis,
		final double dblNotional)
	{
		if (null == dtSpot) return null;

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (_iSpotLag,
			_fixedConv.calendar());

		try {
			org.drip.product.rates.FixFloatComponent ffc = new org.drip.product.rates.FixFloatComponent
				(_fixedConv.createStream (dtEffective, strMaturityTenor, dblFixedCoupon, dblNotional),
					_floatConv.createStream (dtEffective, strMaturityTenor, dblFloatBasis, -1. *
						dblNotional), null);

			ffc.setPrimaryCode ("IRS::" + ffc.forwardLabel().get ("DERIVED").fullyQualifiedName() + "." +
				strMaturityTenor);

			return ffc;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public java.lang.String toString()
	{
		return "[SPOT LAG: " + _iSpotLag + "]  " + _fixedConv + "  " + _floatConv;
	}
}
