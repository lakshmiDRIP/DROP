
package org.drip.state.sequence;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * PathGovvie exposes the Functionality to generate a Sequence of Govvie Curve Realizations across Multiple
 *  Paths.
 *
 * @author Lakshmi Krishnamurthy
 */

public class PathGovvie extends org.drip.state.sequence.PathRd {
	private org.drip.state.sequence.GovvieBuilderSettings _gbs = null;

	/**
	 * PathGovvie Constructor
	 * 
	 * @param gbs Govvie Builder Settings Instance
	 * @param dblVolatility Volatility
	 * @param bLogNormal TRUE - The Generated Random Numbers are Log Normal
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PathGovvie (
		final org.drip.state.sequence.GovvieBuilderSettings gbs,
		final double dblVolatility,
		final boolean bLogNormal)
		throws java.lang.Exception
	{
		super (gbs.groundForwardYield(), dblVolatility, bLogNormal);

		if (null == (_gbs = gbs)) throw new java.lang.Exception ("PathGovvie Constructor => Invalid Inputs");
	}

	/**
	 * Generate the Govvie Builder Settings Instance
	 * 
	 * @return The Govvie Builder Settings Instance
	 */

	public org.drip.state.sequence.GovvieBuilderSettings govvieBuilderSettings()
	{
		return _gbs;
	}

	/**
	 * Generate the R^d Path Govvie Curves using the Initial R^d and the Evolution Time Width
	 * 
	 * @param iNumPath Number of Paths
	 * 
	 * @return The R^d Path//Vertex Govvie Curves
	 */

	public org.drip.state.govvie.GovvieCurve[] curveSequence (
		final int iNumPath)
	{
		java.lang.String strCurrency = _gbs.groundState().currency();

		org.drip.analytics.date.JulianDate dtSpot = _gbs.spot();

		double[][] aadblPathSequence = sequence (iNumPath);

		java.lang.String strTreasuryCode = _gbs.code();

		java.lang.String[] astrTenor = _gbs.tenors();

		if (null == aadblPathSequence) return null;

		int iEpochDate = dtSpot.julian();

		int iNumTenor = astrTenor.length;
		int[] aiDate = new int[iNumTenor];
		org.drip.state.nonlinear.FlatForwardGovvieCurve[] aFFGC = new
			org.drip.state.nonlinear.FlatForwardGovvieCurve[iNumPath];

		for (int iTenor = 0; iTenor < iNumTenor; ++iTenor) {
			org.drip.analytics.date.JulianDate dtTenor = dtSpot.addTenor (astrTenor[iTenor]);

			if (null == dtTenor) return null;

			aiDate[iTenor] = dtTenor.julian();
		}

		for (int iPath = 0; iPath < iNumPath; ++iPath) {
			try {
				if (null == (aFFGC[iPath] = new org.drip.state.nonlinear.FlatForwardGovvieCurve (iEpochDate,
					strTreasuryCode, strCurrency, aiDate, aadblPathSequence[iPath])))
					return null;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return aFFGC;
	}
}
