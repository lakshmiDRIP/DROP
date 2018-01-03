
package org.drip.state.csa;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * MultilateralBasisCurve implements the CSA Cash Rate Curve as a Basis over an Overnight Curve.
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultilateralBasisCurve implements org.drip.state.csa.CashFlowEstimator
{
	private static final int NUM_DF_QUADRATURES = 5;

	private double _dblBasis = java.lang.Double.NaN;
	private org.drip.state.discount.MergedDiscountForwardCurve _mdfcOvernight = null;

	/**
	 * Retrieve the Overnight Curve
	 * 
	 * @return The Overnight Curve
	 */

	public org.drip.state.discount.MergedDiscountForwardCurve overnightCurve()
	{
		return _mdfcOvernight;
	}

	/**
	 * Retrieve the Basis to the Overnight Curve
	 * 
	 * @return The Basis to the Overnight Curve
	 */

	public double basis()
	{
		return _dblBasis;
	}

	@Override public org.drip.analytics.date.JulianDate epoch()
	{
		return _mdfcOvernight.epoch();
	}

	@Override public double df (
		final int iDate)
		throws java.lang.Exception
	{
		int iEpochDate = epoch().julian();

		if (iEpochDate >= iDate)
			throw new java.lang.Exception ("MultilateralBasisCurve::df => Invalid Inputs");

		return _mdfcOvernight.df (iDate) * java.lang.Math.exp (_dblBasis * (iEpochDate - iDate) / 365.25);
	}

	@Override public double df (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("MultilateralBasisCurve::df => Invalid Inputs");

		return df (dt.julian());
	}

	@Override public double df (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		return df (epoch().addTenor (strTenor));
	}

	@Override public double effectiveDF (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		if (epoch().julian() > iDate1 || iDate1 >= iDate2)
			throw new java.lang.Exception ("MultilateralFlatForwardCurve::effectiveDF => Invalid Inputs");

		int iNumQuadratures = 0;
		double dblEffectiveDF = 0.;
		int iQuadratureWidth = (iDate2 - iDate1) / NUM_DF_QUADRATURES;

		if (0 == iQuadratureWidth) iQuadratureWidth = 1;

		for (int iDate = iDate1; iDate <= iDate2; iDate += iQuadratureWidth) {
			++iNumQuadratures;

			dblEffectiveDF += (df (iDate) + df (iDate + iQuadratureWidth));
		}

		return dblEffectiveDF / (2. * iNumQuadratures);
	}

	@Override public double effectiveDF (
		final org.drip.analytics.date.JulianDate dt1,
		final org.drip.analytics.date.JulianDate dt2)
		throws java.lang.Exception
	{
		if (null == dt1 || null == dt2)
			throw new java.lang.Exception ("MultilateralFlatForwardCurve::effectiveDF => Invalid Inputs");

		return effectiveDF (
			dt1.julian(),
			dt2.julian()
		);
	}

	@Override public double effectiveDF (
		final java.lang.String strTenor1,
		final java.lang.String strTenor2)
		throws java.lang.Exception
	{
		org.drip.analytics.date.JulianDate dtEpoch = epoch();

		return effectiveDF (
			dtEpoch.addTenor (strTenor1),
			dtEpoch.addTenor (strTenor2)
		);
	}

	@Override public double rate (
		final int iDate)
		throws java.lang.Exception
	{
		int iEpochDate = epoch().julian();

		if (iEpochDate >= iDate)
			throw new java.lang.Exception ("MultilateralFlatForwardCurve::rate => Invalid Inputs");

		return 365.25 * java.lang.Math.log (df (iEpochDate) / df (iDate)) / (iEpochDate - iDate);
	}

	@Override public double rate (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt)
			throw new java.lang.Exception ("MultilateralFlatForwardCurve::rate => Invalid Inputs");

		return rate (dt.julian());
	}

	@Override public double rate (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		return rate (epoch().addTenor (strTenor));
	}

	@Override public double rate (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		int iEpochDate = epoch().julian();

		if (iEpochDate > iDate1 || iDate1 >= iDate2)
			throw new java.lang.Exception ("MultilateralFlatForwardCurve::rate => Invalid Inputs");

		return 365.25 * java.lang.Math.log (df (iDate1) / df (iDate2)) / (iDate2 - iDate1);
	}

	@Override public double rate (
		final org.drip.analytics.date.JulianDate dt1,
		final org.drip.analytics.date.JulianDate dt2)
		throws java.lang.Exception
	{
		if (null == dt1 || null == dt2)
			throw new java.lang.Exception ("MultilateralFlatForwardCurve::rate => Invalid Inputs");

		return rate (
			dt1.julian(),
			dt2.julian()
		);
	}

	@Override public double rate (
		final java.lang.String strTenor1,
		final java.lang.String strTenor2)
		throws java.lang.Exception
	{
		org.drip.analytics.date.JulianDate dtEpoch = epoch();

		return rate (
			dtEpoch.addTenor (strTenor1),
			dtEpoch.addTenor (strTenor2)
		);
	}
}
