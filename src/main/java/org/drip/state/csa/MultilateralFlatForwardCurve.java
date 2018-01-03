
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
 * MultilateralFlatForwardCurve implements the CSA Cash Rate Curve using a Flat Forward CSA Rate.
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultilateralFlatForwardCurve extends org.drip.state.nonlinear.FlatForwardDiscountCurve
	implements org.drip.state.csa.CashFlowEstimator
{

	/**
	 * MultilateralFlatForwardCurve Constructor
	 * 
	 * @param dtEpoch Epoch Date
	 * @param strCurrency Currency
	 * @param aiDate Array of Dates
	 * @param adblForwardRate Array of Forward Rates
	 * @param bDiscreteCompounding TRUE - Compounding is Discrete
	 * @param strCompoundingDayCount Day Count Convention to be used for Discrete Compounding
	 * @param iCompoundingFreq Frequency to be used for Discrete Compounding
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MultilateralFlatForwardCurve (
		final org.drip.analytics.date.JulianDate dtEpoch,
		final java.lang.String strCurrency,
		final int[] aiDate,
		final double[] adblForwardRate,
		final boolean bDiscreteCompounding,
		final java.lang.String strCompoundingDayCount,
		final int iCompoundingFreq)
		throws java.lang.Exception
	{
		super (
			dtEpoch,
			strCurrency,
			aiDate,
			adblForwardRate,
			bDiscreteCompounding,
			strCompoundingDayCount,
			iCompoundingFreq
		);
	}

	@Override public double rate (
		final int iDate)
		throws java.lang.Exception
	{
		int iEpochDate = epoch().julian();

		if (iEpochDate >= iDate)
			throw new java.lang.Exception ("MultilateralFlatForwardCurve::rate => Invalid Inputs");

		return discreteCompounding() ? ((1. / df (iDate)) - 1.) / yearFract (
			iEpochDate,
			iDate
		) : org.drip.analytics.support.Helper.DF2Yield (
			compoundingFrequency(),
			df (iDate),
			yearFract (
				iEpochDate,
				iDate
			)
		);
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

		return discreteCompounding() ? ((df (iDate1) / df (iDate2)) - 1.) / yearFract (
			iDate1,
			iDate2
		) : org.drip.analytics.support.Helper.DF2Yield (
			compoundingFrequency(),
			df (iDate1) / df (iDate2),
			yearFract (
				iDate1,
				iDate2
			)
		);
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
