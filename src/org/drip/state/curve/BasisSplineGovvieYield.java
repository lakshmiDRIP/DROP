
package org.drip.state.curve;

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
 * BasisSplineGovvieYield manages the Basis Spline Latent State, using the Basis as the State Response
 *  Representation, for the Govvie Curve with Yield Quantification Metric.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BasisSplineGovvieYield extends org.drip.state.govvie.GovvieCurve {
	private org.drip.spline.grid.Span _span = null;

	/**
	 * BasisSplineGovvieYield Constructor
	 * 
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Currency
	 * @param span Govvie Curve Span
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BasisSplineGovvieYield (
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency,
		final org.drip.spline.grid.Span span)
		throws java.lang.Exception
	{
		super ((int) span.left(), strTreasuryCode, strCurrency);

		_span = span;
	}

	@Override public double yield (
		final int iDate)
		throws java.lang.Exception
	{
		double dblSpanLeft = _span.left();

		if (iDate <= dblSpanLeft) return _span.calcResponseValue (dblSpanLeft);

		double dblSpanRight = _span.right();

		if (iDate >= dblSpanRight) return _span.calcResponseValue (dblSpanRight);

		return _span.calcResponseValue (iDate);
	}

	@Override public org.drip.quant.calculus.WengertJacobian jackDForwardDManifestMeasure (
		final java.lang.String strManifestMeasure,
		final int iDate)
	{
		return _span.jackDResponseDManifestMeasure (strManifestMeasure, iDate, 1);
	}

	/**
	 * Construct a Flat Forward Instance of the Curve at the specified Date Nodes
	 * 
	 * @param aiDate Array of Date Nodes
	 * 
	 * @return The Flat Forward Instance
	 */

	public org.drip.state.nonlinear.FlatForwardDiscountCurve flatForward (
		final int[] aiDate)
	{
		return flatForward (dayCount(), freq(), aiDate);
	}

	/**
	 * Construct a Flat Forward Instance of the Curve at the specified Date Node Tenors
	 * 
	 * @param astrTenor Array of Date Node Tenors
	 * 
	 * @return The Flat Forward Instance
	 */

	public org.drip.state.nonlinear.FlatForwardDiscountCurve flatForward (
		final java.lang.String[] astrTenor)
	{
		if (null == astrTenor) return null;

		int iNumTenor = astrTenor.length;
		int[] aiDate = 0 == iNumTenor ? null : new int[iNumTenor];

		org.drip.analytics.date.JulianDate dtEpoch = epoch();

		for (int i = 0; i < iNumTenor; ++i) {
			try {
				aiDate[i] = dtEpoch.addTenor (astrTenor[i]).julian();
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return flatForward (dayCount(), freq(), aiDate);
	}
}
