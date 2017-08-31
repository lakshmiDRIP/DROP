
package org.drip.product.fx;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * ForeignCollateralizedDomesticForward contains the Foreign Currency Collateralized Domestic Payout FX
 * 	forward product contract details.
 *  
 * @author Lakshmi Krishnamurthy
 */

public class ForeignCollateralizedDomesticForward {
	private java.lang.String _strCode = "";
	private org.drip.product.params.CurrencyPair _cp = null;
	private int _iMaturityDate = java.lang.Integer.MIN_VALUE;
	private double _dblForexForwardStrike = java.lang.Double.NaN;

	/**
	 * Create an ForeignCollateralizedDomesticForward from the currency pair, the strike, and the maturity
	 * 	dates
	 * 
	 * @param cp Currency Pair
	 * @param dblForexForwardStrike Forex Forward Strike
	 * @param dtMaturity Maturity Date
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public ForeignCollateralizedDomesticForward (
		final org.drip.product.params.CurrencyPair cp,
		final double dblForexForwardStrike,
		final org.drip.analytics.date.JulianDate dtMaturity)
		throws java.lang.Exception
	{
		if (null == (_cp = cp) || !org.drip.quant.common.NumberUtil.IsValid (_dblForexForwardStrike =
			dblForexForwardStrike) || null == dtMaturity)
			throw new java.lang.Exception ("ForeignCollateralizedDomesticForward ctr: Invalid Inputs");

		_iMaturityDate = dtMaturity.julian();
	}

	public java.lang.String getPrimaryCode()
	{
		return _strCode;
	}

	public void setPrimaryCode (
		final java.lang.String strCode)
	{
		_strCode = strCode;
	}

	public java.lang.String[] getSecondaryCode()
	{
		java.lang.String strPrimaryCode = getPrimaryCode();

		int iNumTokens = 0;
		java.lang.String astrCodeTokens[] = new java.lang.String[2];

		java.util.StringTokenizer stCodeTokens = new java.util.StringTokenizer (strPrimaryCode, ".");

		while (stCodeTokens.hasMoreTokens())
			astrCodeTokens[iNumTokens++] = stCodeTokens.nextToken();

		System.out.println (astrCodeTokens[0]);

		return new java.lang.String[] {astrCodeTokens[0]};
	}

	public org.drip.analytics.date.JulianDate getMaturityDate()
	{
		try {
			return new org.drip.analytics.date.JulianDate (_iMaturityDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public org.drip.product.params.CurrencyPair getCcyPair()
	{
		return _cp;
	}

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
	{
		if (null == valParams || null == csqs) return null;

		long lStart = System.nanoTime();

		int iValueDate = valParams.valueDate();

		if (iValueDate > _iMaturityDate) return null;

		org.drip.state.fx.FXCurve fxfc = csqs.fxState (org.drip.state.identifier.FXLabel.Standard (_cp));

		if (null == fxfc) return null;

		java.lang.String strForeignCurrency = _cp.numCcy();

		org.drip.state.discount.MergedDiscountForwardCurve dcForeignCollateral =
			csqs.payCurrencyCollateralCurrencyCurve (strForeignCurrency, strForeignCurrency);

		if (null == dcForeignCollateral) return null;

		org.drip.state.discount.MergedDiscountForwardCurve dcDomesticCurrencyForeignCollateral =
			csqs.payCurrencyCollateralCurrencyCurve (_cp.denomCcy(), strForeignCurrency);

		if (null == dcDomesticCurrencyForeignCollateral) return null;

		double dblPrice = java.lang.Double.NaN;
		double dblSpotFX = java.lang.Double.NaN;
		double dblParForward = java.lang.Double.NaN;
		double dblForeignCollateralDF = java.lang.Double.NaN;
		double dblDomesticCurrencyForeignCollateralDF = java.lang.Double.NaN;

		try {
			dblPrice = (dblSpotFX = fxfc.fx (iValueDate)) * (dblForeignCollateralDF =
				dcForeignCollateral.df (_iMaturityDate)) - ((dblDomesticCurrencyForeignCollateralDF =
					dcDomesticCurrencyForeignCollateral.df (_iMaturityDate)) * _dblForexForwardStrike);

			dblParForward = dblSpotFX * dblForeignCollateralDF / dblDomesticCurrencyForeignCollateralDF;
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapResult = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		mapResult.put ("CalcTime", (System.nanoTime() - lStart) * 1.e-09);

		mapResult.put ("DomesticCurrencyForeignCollateralDF", dblDomesticCurrencyForeignCollateralDF);

		mapResult.put ("ForeignCollateralDF", dblForeignCollateralDF);

		mapResult.put ("ParForward", dblParForward);

		mapResult.put ("Price", dblPrice);

		mapResult.put ("SpotFX", dblSpotFX);

		return mapResult;
	}

	public java.util.Set<java.lang.String> getMeasureNames()
	{
		java.util.Set<java.lang.String> setstrMeasureNames = new java.util.TreeSet<java.lang.String>();

		setstrMeasureNames.add ("CalcTime");

		setstrMeasureNames.add ("DomesticCurrencyForeignCollateralDF");

		setstrMeasureNames.add ("ForeignCollateralDF");

		setstrMeasureNames.add ("ParForward");

		setstrMeasureNames.add ("Price");

		setstrMeasureNames.add ("SpotFX");

		return setstrMeasureNames;
	}
}
