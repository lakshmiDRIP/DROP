
package org.drip.product.creator;

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
 * <i>CDSBasketBuilder</i> contains the suite of helper functions for creating the CDS Basket Product from
 * different kinds of inputs and byte streams.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product">Product</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator">Creator</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CDSBasketBuilder {

	/**
	 * Create the named CDX from effective, maturity, coupon, IR curve name, credit curve name set, and their
	 * 	weights.
	 * 
	 * @param dtEffective JulianDate Effective
	 * @param dtMaturity JulianDate Maturity
	 * @param dblCoupon Coupon
	 * @param strIR IR curve name
	 * @param astrCC credit curve name
	 * @param adblWeight Credit Component Weights
	 * @param strName CDX name
	 * 
	 * @return BasketDefaultSwap
	 */

	public static final org.drip.product.definition.BasketProduct MakeCDX (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon,
		final java.lang.String strIR,
		final java.lang.String[] astrCC,
		final double[] adblWeight,
		final java.lang.String strName)
	{
		if (null == dtEffective || null == dtMaturity || !org.drip.quant.common.NumberUtil.IsValid (dblCoupon)
			|| null == strIR || strIR.isEmpty() || null == strName || strName.isEmpty() || null == astrCC ||
				0 == astrCC.length || null == adblWeight || 0 == adblWeight.length || adblWeight.length !=
					astrCC.length)
			return null;

		org.drip.product.definition.CreditDefaultSwap aCDS[] = new
			org.drip.product.definition.CreditDefaultSwap[astrCC.length];

		for (int i = 0; i < astrCC.length; ++i) {
			try {
				aCDS[i] = org.drip.product.creator.CDSBuilder.CreateCDS (dtEffective, dtMaturity, dblCoupon,
					strIR, 0.40, astrCC[i], strIR, true);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		try {
			return new org.drip.product.credit.CDSBasket (aCDS, adblWeight, strName);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create the named CDX from effective, maturity, coupon, IR curve name, credit curve name set.
	 * 
	 * @param dtEffective JulianDate Effective
	 * @param dtMaturity JulianDate Maturity
	 * @param dblCoupon Coupon
	 * @param strIR IR curve name
	 * @param astrCC credit curve name
	 * @param strName CDX name
	 * 
	 * @return BasketDefaultSwap
	 */

	public static final org.drip.product.definition.BasketProduct MakeCDX (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon,
		final java.lang.String strIR,
		final java.lang.String[] astrCC,
		final java.lang.String strName)
	{
		if (null == dtEffective || null == dtMaturity || !org.drip.quant.common.NumberUtil.IsValid (dblCoupon)
			|| null == strIR || strIR.isEmpty() || null == strName || strName.isEmpty() || null == astrCC ||
				0 == astrCC.length)
			return null;

		org.drip.product.definition.CreditDefaultSwap aCDS[] = new
			org.drip.product.definition.CreditDefaultSwap[astrCC.length];

		for (int i = 0; i < astrCC.length; ++i) {
			try {
				aCDS[i] = org.drip.product.creator.CDSBuilder.CreateCDS (dtEffective, dtMaturity, dblCoupon,
					strIR, 0.40, astrCC[i], strIR, true);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		try {
			double adblWeight[] = new double[aCDS.length];

			for (int i = 0; i < aCDS.length; ++i)
				adblWeight[i] = 1.;

			return new org.drip.product.credit.CDSBasket (aCDS, adblWeight, strName);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create the basket default swap from an array of the credit components.
	 * 
	 * @param aComp Array of the credit components
	 * 
	 * @return BasketDefaultSwap object
	 */

	public static final org.drip.product.definition.BasketProduct MakeBasketDefaultSwap (
		final org.drip.product.definition.Component[] aComp)
	{
		try {
			double adblWeight[] = new double[aComp.length];

			for (int i = 0; i < aComp.length; ++i)
				adblWeight[i] = 1.;

			return new org.drip.product.credit.CDSBasket (aComp, adblWeight, "");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
