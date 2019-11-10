
package org.drip.product.option;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>EuropeanCallPut</i> implements a simple European Call/Put Option, and its Black Scholes Price.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/README.md">Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option AssetClasses</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/option/README.md">Options on Fixed Income Components</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EuropeanCallPut {
	private double _dblStrike = java.lang.Double.NaN;
	private org.drip.analytics.date.JulianDate _dtMaturity = null;

	/**
	 * EuropeanCallPut constructor
	 * 
	 * @param dtMaturity Option Maturity
	 * @param dblStrike Option Strike
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public EuropeanCallPut (
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblStrike)
		throws java.lang.Exception
	{
		if (null == (_dtMaturity = dtMaturity) || !org.drip.numerical.common.NumberUtil.IsValid (_dblStrike =
			dblStrike) || 0. >= _dblStrike)
			throw new java.lang.Exception ("EuropeanCallPut ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Option Maturity
	 * 
	 * @return The Option Maturity
	 */

	public org.drip.analytics.date.JulianDate maturity()
	{
		return _dtMaturity;
	}

	/**
	 * Retrieve the Option Strike
	 * 
	 * @return The Option Strike
	 */

	public double strike()
	{
		return _dblStrike;
	}

	/**
	 * Generate the Measure Set for the Option
	 * 
	 * @param valParams The Valuation Parameters
	 * @param dblUnderlier The Underlier
	 * @param bIsForward TRUE - The Underlier represents the Forward, FALSE - it represents Spot
	 * @param dc Discount Curve
	 * @param auVolatility The Option Volatility Function
	 * @param fpg The Fokker Planck-based Option Pricer
	 * 
	 * @return The Map of the Measures
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final double dblUnderlier,
		final boolean bIsForward,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.function.definition.R1ToR1 auVolatility,
		final org.drip.pricer.option.FokkerPlanckGenerator fpg)
	{
		if (null == valParams || null == dc || null == auVolatility || null == fpg) return null;

		int iValueDate = valParams.valueDate();

		int iMaturityDate = _dtMaturity.julian();

		if (iValueDate >= iMaturityDate) return null;

		long lStartTime = System.nanoTime();

		double dblRiskFreeRate = java.lang.Double.NaN;
		double dblTTE = (iMaturityDate - iValueDate) / 365.25;
		double dblImpliedPutVolatility = java.lang.Double.NaN;
		double dblImpliedCallVolatility = java.lang.Double.NaN;
		double dblTimeAveragedVolatility = java.lang.Double.NaN;
		double dblBlackScholesPutVolatility = java.lang.Double.NaN;
		double dblBlackScholesCallVolatility = java.lang.Double.NaN;

		try {
			dblRiskFreeRate = dc.zero (iMaturityDate);

			dblTimeAveragedVolatility = auVolatility.integrate (iValueDate, iMaturityDate) / (iMaturityDate -
				iValueDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.pricer.option.Greeks callGreeks = fpg.greeks (_dblStrike, dblTTE, dblRiskFreeRate,
			dblUnderlier, false, bIsForward, dblTimeAveragedVolatility);

		org.drip.pricer.option.PutGreeks putGreeks = (org.drip.pricer.option.PutGreeks) fpg.greeks
			(_dblStrike, dblTTE, dblRiskFreeRate, dblUnderlier, true, bIsForward, dblTimeAveragedVolatility);

		if (null == callGreeks || null == putGreeks) return null;

		double dblCallPrice = callGreeks.price();

		double dblPutPrice = putGreeks.price();

		try {
			dblBlackScholesCallVolatility = fpg.impliedBlackScholesVolatility (_dblStrike, dblTTE,
				dblRiskFreeRate, dblUnderlier, false, bIsForward, dblCallPrice);

			dblBlackScholesPutVolatility = fpg.impliedBlackScholesVolatility (_dblStrike, dblTTE,
				dblRiskFreeRate, dblUnderlier, true, bIsForward, dblPutPrice);

			dblImpliedCallVolatility = fpg.impliedVolatilityFromPrice (_dblStrike, dblTTE, dblRiskFreeRate,
				dblUnderlier, false, bIsForward, dblCallPrice);

			dblImpliedPutVolatility = fpg.impliedVolatilityFromPrice (_dblStrike, dblTTE, dblRiskFreeRate,
				dblUnderlier, true, bIsForward, dblPutPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapMeasure = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		mapMeasure.put ("BlackScholesCallVolatility", dblBlackScholesCallVolatility);

		mapMeasure.put ("BlackScholesPutVolatility", dblBlackScholesPutVolatility);

		mapMeasure.put ("CalcTime", (System.nanoTime() - lStartTime) * 1.e-09);

		mapMeasure.put ("CallCharm", callGreeks.charm());

		mapMeasure.put ("CallColor", callGreeks.color());

		mapMeasure.put ("CallDelta", callGreeks.delta());

		mapMeasure.put ("CallGamma", callGreeks.gamma());

		mapMeasure.put ("CallPrice", callGreeks.price());

		mapMeasure.put ("CallProb1", callGreeks.prob1());

		mapMeasure.put ("CallProb2", callGreeks.prob2());

		mapMeasure.put ("CallRho", callGreeks.rho());

		mapMeasure.put ("CallSpeed", callGreeks.speed());

		mapMeasure.put ("CallTheta", callGreeks.theta());

		mapMeasure.put ("CallUltima", callGreeks.ultima());

		mapMeasure.put ("CallVanna", callGreeks.vanna());

		mapMeasure.put ("CallVega", callGreeks.vega());

		mapMeasure.put ("CallVeta", callGreeks.veta());

		mapMeasure.put ("CallVomma", callGreeks.vomma());

		mapMeasure.put ("DF", callGreeks.df());

		mapMeasure.put ("EffectiveVolatility", callGreeks.effectiveVolatility());

		mapMeasure.put ("ImpliedCallVolatility", dblImpliedCallVolatility);

		mapMeasure.put ("ImpliedPutVolatility", dblImpliedPutVolatility);

		mapMeasure.put ("PutCharm", putGreeks.charm());

		mapMeasure.put ("PutColor", putGreeks.color());

		mapMeasure.put ("PutDelta", putGreeks.delta());

		mapMeasure.put ("PutGamma", putGreeks.gamma());

		mapMeasure.put ("PutPrice", putGreeks.price());

		mapMeasure.put ("PutPriceFromParity", putGreeks.putPriceFromParity());

		mapMeasure.put ("PutProb1", putGreeks.prob1());

		mapMeasure.put ("PutProb2", putGreeks.prob2());

		mapMeasure.put ("PutRho", putGreeks.rho());

		mapMeasure.put ("PutSpeed", putGreeks.speed());

		mapMeasure.put ("PutTheta", putGreeks.theta());

		mapMeasure.put ("PutUltima", putGreeks.ultima());

		mapMeasure.put ("PutVanna", putGreeks.vanna());

		mapMeasure.put ("PutVega", putGreeks.vega());

		mapMeasure.put ("PutVeta", putGreeks.veta());

		mapMeasure.put ("PutVomma", putGreeks.vomma());

		mapMeasure.put ("TTE", dblTTE);

		return mapMeasure;
	}

	/**
	 * Imply the Option Volatility given the Call Price
	 * 
	 * @param valParams The Valuation Parameters
	 * @param dblUnderlier The Underlier
	 * @param bIsForward TRUE - The Underlier represents the Forward, FALSE - it represents Spot
	 * @param dc Discount Curve
	 * @param dblCallPrice The Option Call Price
	 * 
	 * @return The Option's Implied Volatility
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public double implyVolatilityFromCallPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final double dblUnderlier,
		final boolean bIsForward,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final double dblCallPrice)
		throws java.lang.Exception
	{
		if (null == valParams || null == dc)
			throw new java.lang.Exception ("EuropeanCallPut::implyVolatilityFromCallPrice => Invalid Inputs");

		int iValueDate = valParams.valueDate();

		int iMaturityDate = _dtMaturity.julian();

		if (iValueDate >= iMaturityDate)
			throw new java.lang.Exception ("EuropeanCallPut::implyVolatilityFromCallPrice => Invalid Inputs");

		double dblTTE = 1. * (iMaturityDate - iValueDate) / 365.25;

		return new org.drip.pricer.option.BlackScholesAlgorithm().impliedVolatilityFromPrice (_dblStrike,
			dblTTE, dc.zero (iMaturityDate), dblUnderlier, false, bIsForward, dblCallPrice);
	}

	/**
	 * Imply the Option Volatility given the Put Price
	 * 
	 * @param valParams The Valuation Parameters
	 * @param dblUnderlier The Underlier
	 * @param bIsForward TRUE - The Underlier represents the Forward, FALSE - it represents Spot
	 * @param dc Discount Curve
	 * @param dblPutPrice The Option Put Price
	 * 
	 * @return The Option's Implied Volatility
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public double implyVolatilityFromPutPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final double dblUnderlier,
		final boolean bIsForward,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final double dblPutPrice)
		throws java.lang.Exception
	{
		if (null == valParams || null == dc)
			throw new java.lang.Exception ("EuropeanCallPut::implyVolatilityFromPutPrice => Invalid Inputs");

		int iValueDate = valParams.valueDate();

		int iMaturityDate = _dtMaturity.julian();

		if (iValueDate >= iMaturityDate)
			throw new java.lang.Exception ("EuropeanCallPut::implyVolatilityFromPutPrice => Invalid Inputs");

		double dblTTE = 1. * (iMaturityDate - iValueDate) / 365.25;

		return new org.drip.pricer.option.BlackScholesAlgorithm().impliedVolatilityFromPrice (_dblStrike,
			dblTTE, dc.zero (iMaturityDate), dblUnderlier, true, bIsForward, dblPutPrice);
	}

	/**
	 * Retrieve the Set of the Measure Names
	 * 
	 * @return The Set of the Measure Names
	 */

	public java.util.Set<java.lang.String> getMeasureNames()
	{
		java.util.Set<java.lang.String> setstrMeasureNames = new java.util.TreeSet<java.lang.String>();

		setstrMeasureNames.add ("BlackScholesCallVolatility");

		setstrMeasureNames.add ("BlackScholesPutVolatility");

		setstrMeasureNames.add ("CalcTime");

		setstrMeasureNames.add ("CallCharm");

		setstrMeasureNames.add ("CallColor");

		setstrMeasureNames.add ("CallDelta");

		setstrMeasureNames.add ("CallGamma");

		setstrMeasureNames.add ("CallPrice");

		setstrMeasureNames.add ("CallProb1");

		setstrMeasureNames.add ("CallProb2");

		setstrMeasureNames.add ("CallRho");

		setstrMeasureNames.add ("CallSpeed");

		setstrMeasureNames.add ("CallTheta");

		setstrMeasureNames.add ("CallUltima");

		setstrMeasureNames.add ("CallVanna");

		setstrMeasureNames.add ("CallVega");

		setstrMeasureNames.add ("CallVeta");

		setstrMeasureNames.add ("CallVomma");

		setstrMeasureNames.add ("DF");

		setstrMeasureNames.add ("EffectiveVolatility");

		setstrMeasureNames.add ("ImpliedCallVolatility");

		setstrMeasureNames.add ("ImpliedPutVolatility");

		setstrMeasureNames.add ("PutCharm");

		setstrMeasureNames.add ("PutColor");

		setstrMeasureNames.add ("PutDelta");

		setstrMeasureNames.add ("PutGamma");

		setstrMeasureNames.add ("PutPrice");

		setstrMeasureNames.add ("PutPriceFromParity");

		setstrMeasureNames.add ("PutProb1");

		setstrMeasureNames.add ("PutProb2");

		setstrMeasureNames.add ("PutRho");

		setstrMeasureNames.add ("PutSpeed");

		setstrMeasureNames.add ("PutTheta");

		setstrMeasureNames.add ("PutUltima");

		setstrMeasureNames.add ("PutVanna");

		setstrMeasureNames.add ("PutVega");

		setstrMeasureNames.add ("PutVeta");

		setstrMeasureNames.add ("PutVomma");

		setstrMeasureNames.add ("TTE");

		return setstrMeasureNames;
	}
}
