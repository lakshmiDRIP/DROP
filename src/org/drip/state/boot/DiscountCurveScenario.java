
package org.drip.state.boot;

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
 * DiscountCurveScenario uses the interest rate calibration instruments along with the component calibrator
 *  to produce scenario interest rate curves.
 *
 * DiscountCurveScenario typically first constructs the actual curve calibrator instance to localize the
 * 	intelligence around curve construction. It then uses this curve calibrator instance to build individual
 *  curves or the sequence of node bumped scenario curves. The curves in the set may be an array, or
 *  tenor-keyed.
 *
 * @author Lakshmi Krishnamurthy
 */

public class DiscountCurveScenario {

	/**
	 * Calibrate a discount curve
	 * 
	 * @param valParams ValuationParams
	 * @param aCalibInst Array of Calibratable Components
	 * @param adblCalibQuote Array of component quotes
	 * @param astrCalibMeasure Array of the calibration measures
	 * @param dblBump Quote bump
	 * @param gc Govvie Curve
	 * @param lsfc Latent State Fixings Container
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return DiscountCurve Instance
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve Standard (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.product.definition.CalibratableComponent[] aCalibInst,
		final double[] adblCalibQuote,
		final java.lang.String[] astrCalibMeasure,
		final double dblBump,
		final org.drip.state.govvie.GovvieCurve gc,
		final org.drip.param.market.LatentStateFixingsContainer lsfc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == valParams || null == aCalibInst || null == adblCalibQuote || null == astrCalibMeasure)
			return null;

		int iNumComp = aCalibInst.length;
		int aiDate[] = new int[iNumComp];
		double adblRate[] = new double[iNumComp];

		if (0 == iNumComp || adblCalibQuote.length != iNumComp || astrCalibMeasure.length != iNumComp)
			return null;

		java.lang.String strCurrency = aCalibInst[0].payCurrency();

		for (int i = 0; i < iNumComp; ++i) {
			if (null == aCalibInst[i] || !strCurrency.equalsIgnoreCase (aCalibInst[i].payCurrency()))
				return null;

			adblRate[i] = 0.02;

			aiDate[i] = aCalibInst[i].maturityDate().julian();
		}

		org.drip.state.discount.ExplicitBootDiscountCurve ebdc =
			org.drip.state.creator.ScenarioDiscountCurveBuilder.PiecewiseForward (new
				org.drip.analytics.date.JulianDate (valParams.valueDate()), strCurrency, aiDate, adblRate);

		if (!org.drip.state.nonlinear.NonlinearCurveBuilder.DiscountCurve (valParams, aCalibInst,
			adblCalibQuote, astrCalibMeasure, dblBump, false, ebdc, gc, lsfc, vcp))
			return null;

		ebdc.setCCIS (org.drip.analytics.input.BootCurveConstructionInput.Create (valParams, vcp, aCalibInst,
			adblCalibQuote, astrCalibMeasure, lsfc));

		return ebdc;
	}

	/**
	 * Calibrate an array of tenor bumped discount curves
	 * 
	 * @param valParams Valuation Parameters
	 * @param aCalibInst Array of Calibratable Components
	 * @param adblCalibQuote Array of component quotes
	 * @param astrCalibMeasure Array of the calibration measures
	 * @param dblBump Quote bump
	 * @param gc Govvie Curve
	 * @param lsfc Latent State Fixings Container
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return Array of tenor bumped discount curves
	 */

	public static final org.drip.state.discount.MergedDiscountForwardCurve[] Tenor (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.product.definition.CalibratableComponent[] aCalibInst,
		final double[] adblCalibQuote,
		final java.lang.String[] astrCalibMeasure,
		final double dblBump,
		final org.drip.state.govvie.GovvieCurve gc,
		final org.drip.param.market.LatentStateFixingsContainer lsfc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == aCalibInst || !org.drip.quant.common.NumberUtil.IsValid (dblBump)) return null;

		int iNumComp = aCalibInst.length;
		org.drip.state.discount.MergedDiscountForwardCurve[] aDiscountCurve = new
			org.drip.state.discount.MergedDiscountForwardCurve[iNumComp];

		if (0 == iNumComp || adblCalibQuote.length != iNumComp || astrCalibMeasure.length != iNumComp)
			return null;

		for (int i = 0; i < iNumComp; ++i) {
			double[] adblTenorQuote = new double [iNumComp];

			for (int j = 0; j < iNumComp; ++j)
				adblTenorQuote[j] = adblCalibQuote[j] + (j == i ? dblBump : 0.);

			if (null == (aDiscountCurve[i] = Standard (valParams, aCalibInst, adblTenorQuote,
				astrCalibMeasure, 0., gc, lsfc, vcp)))
				return null;
		}

		return aDiscountCurve;
	}

	/**
	 * Calibrate a tenor map of tenor bumped discount curves
	 * 
	 * @param valParams ValuationParams
	 * @param aCalibInst Array of Calibratable Components
	 * @param adblCalibQuote Array of component quotes
	 * @param astrCalibMeasure Array of the calibration measures
	 * @param dblBump Quote bump
	 * @param gc Govvie Curve
	 * @param lsfc Latent State Fixings Container
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return Tenor map of tenor bumped discount curves
	 */

	public static final
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve> TenorMap (
			final org.drip.param.valuation.ValuationParams valParams,
			final org.drip.product.definition.CalibratableComponent[] aCalibInst,
			final double[] adblCalibQuote,
			final java.lang.String[] astrCalibMeasure,
			final double dblBump,
			final org.drip.state.govvie.GovvieCurve gc,
			final org.drip.param.market.LatentStateFixingsContainer lsfc,
			final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == aCalibInst || null == adblCalibQuote || !org.drip.quant.common.NumberUtil.IsValid
			(dblBump))
			return null;

		int iNumComp = aCalibInst.length;

		if (0 == iNumComp || adblCalibQuote.length != iNumComp) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
			mapTenorDiscountCurve = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>();

		for (int i = 0; i < iNumComp; ++i) {
			double[] adblTenorQuote = new double [iNumComp];

			for (int j = 0; j < iNumComp; ++j)
				adblTenorQuote[j] = adblCalibQuote[j] + (j == i ? dblBump : 0.);

			mapTenorDiscountCurve.put (org.drip.analytics.date.DateUtil.YYYYMMDD
				(aCalibInst[i].maturityDate().julian()), Standard (valParams, aCalibInst, adblTenorQuote,
					astrCalibMeasure, 0., gc, lsfc, vcp));
		}

		return mapTenorDiscountCurve;
	}
}
