
package org.drip.analytics.support;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>FuturesHelper</i> contains the Collection of the Futures Valuation related Utility Functions.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Analytics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/support/README.md">Support</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FuturesHelper {

	/**
	 * Compute the Forward Bond Price Using the Implied Bond Yield
	 * 
	 * @param bond The Bond Instance
	 * @param valParamsSpot The Spot Valuation Parameters
	 * @param valParamsForward The Forward Valuation Parameters
	 * @param csqc The Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCleanPrice The Clean Bond Price
	 * 
	 * @return The Forward Bond Price Using the Implied Bond Yield
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double ForwardBondYieldPrice (
		final org.drip.product.definition.Bond bond,
		final org.drip.param.valuation.ValuationParams valParamsSpot,
		final org.drip.param.valuation.ValuationParams valParamsForward,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCleanPrice)
		throws java.lang.Exception
	{
		if (null == bond)
			throw new java.lang.Exception ("FuturesHelper::ForwardBondYieldPrice => Invalid Inputs");

		return bond.priceFromYield (valParamsForward, csqc, vcp, bond.yieldFromPrice (valParamsSpot, csqc,
			vcp, dblCleanPrice));
	}

	/**
	 * Compute the Forward Bond Price Using the Implied Bond Z Spread
	 * 
	 * @param bond The Bond Instance
	 * @param valParamsSpot The Spot Valuation Parameters
	 * @param valParamsForward The Forward Valuation Parameters
	 * @param csqc The Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCleanPrice The Clean Bond Price
	 * 
	 * @return The Forward Bond Price Using the Implied Bond Z Spread
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double ForwardBondZSpreadPrice (
		final org.drip.product.definition.Bond bond,
		final org.drip.param.valuation.ValuationParams valParamsSpot,
		final org.drip.param.valuation.ValuationParams valParamsForward,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCleanPrice)
		throws java.lang.Exception
	{
		if (null == bond)
			throw new java.lang.Exception ("FuturesHelper::ForwardBondZSpreadPrice => Invalid Inputs");

		return bond.priceFromZSpread (valParamsForward, csqc, vcp, bond.zSpreadFromPrice (valParamsSpot,
			csqc, vcp, dblCleanPrice));
	}

	/**
	 * Compute the Forward Bond Price Using the Implied Bond OAS
	 * 
	 * @param bond The Bond Instance
	 * @param valParamsSpot The Spot Valuation Parameters
	 * @param valParamsForward The Forward Valuation Parameters
	 * @param csqc The Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCleanPrice The Clean Bond Price
	 * 
	 * @return The Forward Bond Price Using the Implied Bond OAS
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double ForwardBondOASPrice (
		final org.drip.product.definition.Bond bond,
		final org.drip.param.valuation.ValuationParams valParamsSpot,
		final org.drip.param.valuation.ValuationParams valParamsForward,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCleanPrice)
		throws java.lang.Exception
	{
		if (null == bond)
			throw new java.lang.Exception ("FuturesHelper::ForwardBondOASPrice => Invalid Inputs");

		return bond.priceFromOAS (valParamsForward, csqc, vcp, bond.oasFromPrice (valParamsSpot, csqc, vcp,
			dblCleanPrice));
	}

	/**
	 * Compute the Forward Bond Price Using the Implied Bond Credit Basis
	 * 
	 * @param bond The Bond Instance
	 * @param valParamsSpot The Spot Valuation Parameters
	 * @param valParamsForward The Forward Valuation Parameters
	 * @param csqc The Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCleanPrice The Clean Bond Price
	 * 
	 * @return The Forward Bond Price Using the Implied Bond Credit Basis
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double ForwardBondCreditPrice (
		final org.drip.product.definition.Bond bond,
		final org.drip.param.valuation.ValuationParams valParamsSpot,
		final org.drip.param.valuation.ValuationParams valParamsForward,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCleanPrice)
		throws java.lang.Exception
	{
		if (null == bond)
			throw new java.lang.Exception ("FuturesHelper::ForwardBondCreditPrice => Invalid Inputs");

		return bond.priceFromCreditBasis (valParamsForward, csqc, vcp, bond.creditBasisFromPrice
			(valParamsSpot, csqc, vcp, dblCleanPrice));
	}

	/**
	 * Compute the Forward Bond Price Using the Implied Bond Yield
	 * 
	 * @param bond The Bond Instance
	 * @param dtSpot The Spot Date
	 * @param dtForward The Forward Date
	 * @param csqc The Market Parameters
	 * @param dblCleanPrice The Clean Bond Price
	 * 
	 * @return The Forward Bond Price Using the Implied Bond Yield
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double ForwardBondYieldPrice (
		final org.drip.product.definition.Bond bond,
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.analytics.date.JulianDate dtForward,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final double dblCleanPrice)
		throws java.lang.Exception
	{
		if (null == bond || null == dtSpot || null == dtForward)
			throw new java.lang.Exception ("FuturesHelper::ForwardBondYieldPrice => Invalid Inputs");

		return bond.priceFromYield (org.drip.param.valuation.ValuationParams.Spot (dtForward.julian()), csqc,
			null, bond.yieldFromPrice (org.drip.param.valuation.ValuationParams.Spot (dtSpot.julian()), csqc,
				null, dblCleanPrice));
	}

	/**
	 * Compute the Forward Bond Price Using the Implied Bond Z Spread
	 * 
	 * @param bond The Bond Instance
	 * @param dtSpot The Spot Date
	 * @param dtForward The Forward Date
	 * @param csqc The Market Parameters
	 * @param dblCleanPrice The Clean Bond Price
	 * 
	 * @return The Forward Bond Price Using the Implied Bond Z Spread
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double ForwardBondZSpreadPrice (
		final org.drip.product.definition.Bond bond,
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.analytics.date.JulianDate dtForward,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final double dblCleanPrice)
		throws java.lang.Exception
	{
		if (null == bond || null == dtSpot || null == dtForward)
			throw new java.lang.Exception ("FuturesHelper::ForwardBondZSpreadPrice => Invalid Inputs");

		return bond.priceFromZSpread (org.drip.param.valuation.ValuationParams.Spot (dtForward.julian()),
			csqc, null, bond.zSpreadFromPrice (org.drip.param.valuation.ValuationParams.Spot
				(dtSpot.julian()), csqc, null, dblCleanPrice));
	}

	/**
	 * Compute the Forward Bond Price Using the Implied Bond OAS
	 * 
	 * @param bond The Bond Instance
	 * @param dtSpot The Spot Date
	 * @param dtForward The Forward Date
	 * @param csqc The Market Parameters
	 * @param dblCleanPrice The Clean Bond Price
	 * 
	 * @return The Forward Bond Price Using the Implied Bond OAS
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double ForwardBondOASPrice (
		final org.drip.product.definition.Bond bond,
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.analytics.date.JulianDate dtForward,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final double dblCleanPrice)
		throws java.lang.Exception
	{
		if (null == bond || null == dtSpot || null == dtForward)
			throw new java.lang.Exception ("FuturesHelper::ForwardBondOASPrice => Invalid Inputs");

		return bond.priceFromOAS (org.drip.param.valuation.ValuationParams.Spot (dtForward.julian()), csqc,
			null, bond.oasFromPrice (org.drip.param.valuation.ValuationParams.Spot (dtSpot.julian()), csqc,
				null, dblCleanPrice));
	}

	/**
	 * Compute the Forward Bond Price Using the Implied Bond Credit Basis
	 * 
	 * @param bond The Bond Instance
	 * @param dtSpot The Spot Date
	 * @param dtForward The Forward Date
	 * @param csqc The Market Parameters
	 * @param dblCleanPrice The Clean Bond Price
	 * 
	 * @return The Forward Bond Price Using the Implied Bond Credit Basis
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double ForwardBondCreditPrice (
		final org.drip.product.definition.Bond bond,
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.analytics.date.JulianDate dtForward,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final double dblCleanPrice)
		throws java.lang.Exception
	{
		if (null == bond || null == dtSpot || null == dtForward)
			throw new java.lang.Exception ("FuturesHelper::ForwardBondCreditPrice => Invalid Inputs");

		return bond.priceFromCreditBasis (org.drip.param.valuation.ValuationParams.Spot (dtForward.julian()),
			csqc, null, bond.creditBasisFromPrice (org.drip.param.valuation.ValuationParams.Spot
				(dtSpot.julian()), csqc, null, dblCleanPrice));
	}
}
