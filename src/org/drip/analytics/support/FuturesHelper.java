
package org.drip.analytics.support;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * FuturesHelper contains the Collection of the Futures Valuation related Utility Functions.
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
