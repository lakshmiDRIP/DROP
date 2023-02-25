
package org.drip.product.fx;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>ForeignCollateralizedDomesticForward</i> contains the Foreign Currency Collateralized Domestic Payout
 * FX forward product contract details.
 * 
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/README.md">Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option AssetClasses</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/fx/README.md">FX Forwards, Cross Currency Swaps</a></li>
 *  </ul>
 * <br><br>
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
		if (null == (_cp = cp) || !org.drip.numerical.common.NumberUtil.IsValid (_dblForexForwardStrike =
			dblForexForwardStrike) || null == dtMaturity)
			throw new java.lang.Exception ("ForeignCollateralizedDomesticForward ctr: Invalid Inputs");

		_iMaturityDate = dtMaturity.julian();
	}

	/**
	 * Set the Primary Code
	 * 
	 * @return The Primary Code
	 */

	public java.lang.String getPrimaryCode()
	{
		return _strCode;
	}

	/**
	 * Set the Primary Code
	 * 
	 * @param strCode The Primary Code
	 */

	public void setPrimaryCode (
		final java.lang.String strCode)
	{
		_strCode = strCode;
	}

	/**
	 * Retrieve the Array of Secondary Codes
	 * 
	 * @return The Array of Secondary Codes
	 */

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

	/**
	 * Retrieve the Maturity Date
	 * 
	 * @return The Maturity Date
	 */

	public org.drip.analytics.date.JulianDate getMaturityDate()
	{
		try {
			return new org.drip.analytics.date.JulianDate (_iMaturityDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Currency Pair
	 * 
	 * @return The Currency Pair
	 */

	public org.drip.product.params.CurrencyPair getCcyPair()
	{
		return _cp;
	}

	/**
	 * Value the Product
	 * 
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs Curve Surface Container Parameters
	 * @param quotingParams Valuation Customization Parameters
	 * 
	 * @return The Computed Product Map
	 */

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

	/**
	 * Retrieve the Measure Names
	 * 
	 * @return Set of Measure Names
	 */

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
