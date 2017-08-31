
package org.drip.historical.state;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * FundingCurveMetrics holds the computed Metrics associated the Funding Curve State.
 *
 * @author Lakshmi Krishnamurthy
 */

public class FundingCurveMetrics {
	private org.drip.analytics.date.JulianDate _dtClose = null;

	private
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>>
			_mapInForNativeLIBOR = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>>();

	/**
	 * FundingCurveMetrics Constructor
	 * 
	 * @param dtClose The Closing Date
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public FundingCurveMetrics (
		final org.drip.analytics.date.JulianDate dtClose)
		throws java.lang.Exception
	{
		if (null == (_dtClose = dtClose))
			throw new java.lang.Exception ("FundingCurveMetrics Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Closing Date
	 * 
	 * @return The Closing Date
	 */

	public org.drip.analytics.date.JulianDate close()
	{
		return _dtClose;
	}

	/**
	 * Add the Native Forward Rate for the specified In/For Start/Forward Tenors
	 * 
	 * @param strInTenor "In" Start Tenor
	 * @param strForTenor "For" Forward Tenor
	 * @param dblForwardRate Forward Rate
	 * 
	 * @return TRUE - The Native Forward Rate successfully added
	 */

	public boolean addNativeForwardRate (
		final java.lang.String strInTenor,
		final java.lang.String strForTenor,
		final double dblForwardRate)
	{
		if (null == strInTenor || strInTenor.isEmpty() || null == strForTenor || strForTenor.isEmpty() ||
			!org.drip.quant.common.NumberUtil.IsValid (dblForwardRate))
			return false;

		if (!_mapInForNativeLIBOR.containsKey (strInTenor)) {
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> mapForwardRate = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

			mapForwardRate.put (strForTenor, dblForwardRate);

			_mapInForNativeLIBOR.put (strInTenor, mapForwardRate);
		} else
			_mapInForNativeLIBOR.get (strInTenor).put (strForTenor, dblForwardRate);

		return true;
	}

	/**
	 * Retrieve the Native Forward Rate given the In/For Tenors
	 * 
	 * @param strInTenor "In" Start Tenor
	 * @param strForTenor "For" Forward Tenor
	 * 
	 * @return The Native Forward Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double nativeForwardRate (
		final java.lang.String strInTenor,
		final java.lang.String strForTenor)
		throws java.lang.Exception
	{
		if (null == strInTenor || strInTenor.isEmpty() || null == strForTenor || strForTenor.isEmpty() ||
			!_mapInForNativeLIBOR.containsKey (strInTenor))
			throw new java.lang.Exception ("FundingCurveMetrics::forwardRate => Invalid Inputs");

		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> mapForwardRate =
			_mapInForNativeLIBOR.get (strInTenor);

		if (null == mapForwardRate || !mapForwardRate.containsKey (strForTenor))
			throw new java.lang.Exception ("FundingCurveMetrics::forwardRate => Invalid Inputs");

		return mapForwardRate.get (strForTenor);
	}
}
