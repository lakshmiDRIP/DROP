
package org.drip.dynamics.hullwhite;

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
 * ShortRateUpdate records the Metrics associated with the Evolution of the Instantaneous Short Rate from a
 *  Starting to the Terminal Date.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ShortRateUpdate extends org.drip.dynamics.evolution.LSQMPointUpdate {
	private double _dblExpectedFinalShortRate = java.lang.Double.NaN;
	private double _dblFinalShortRateVariance = java.lang.Double.NaN;
	private org.drip.state.identifier.FundingLabel _lslFunding = null;

	/**
	 * Construct an Instance of ShortRateUpdate
	 * 
	 * @param lslFunding The Funding Latent State Label
	 * @param iInitialDate The Initial Date
	 * @param iFinalDate The Final Date
	 * @param iTargetPointDate The Target Point Date
	 * @param dblInitialShortRate The Initial Short Rate
	 * @param dblRealizedFinalShortRate The Realized Final Short Rate
	 * @param dblExpectedFinalShortRate The Expected Final Short Rate
	 * @param dblFinalShortRateVariance The Final Variance of the Short Rate
	 * @param dblZeroCouponBondPrice The Zero Coupon Bond Price
	 * 
	 * @return The ShortRateUpdate Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final ShortRateUpdate Create (
		final org.drip.state.identifier.FundingLabel lslFunding,
		final int iInitialDate,
		final int iFinalDate,
		final int iTargetPointDate,
		final double dblInitialShortRate,
		final double dblRealizedFinalShortRate,
		final double dblExpectedFinalShortRate,
		final double dblFinalShortRateVariance,
		final double dblZeroCouponBondPrice)
		throws java.lang.Exception
	{
		org.drip.dynamics.evolution.LSQMPointRecord lrSnapshot = new
			org.drip.dynamics.evolution.LSQMPointRecord();

		if (!lrSnapshot.setQM (lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE,
				dblRealizedFinalShortRate))
			return null;

		if (!lrSnapshot.setQM (lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR,
				dblZeroCouponBondPrice))
			return null;

		org.drip.dynamics.evolution.LSQMPointRecord lrIncrement = new
			org.drip.dynamics.evolution.LSQMPointRecord();

		if (!lrIncrement.setQM (lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE,
				dblRealizedFinalShortRate - dblInitialShortRate))
			return null;

		try {
			return new ShortRateUpdate (lslFunding, iInitialDate, iFinalDate, iTargetPointDate, lrSnapshot,
				lrIncrement, dblExpectedFinalShortRate, dblFinalShortRateVariance);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private ShortRateUpdate (
		final org.drip.state.identifier.FundingLabel lslFunding,
		final int iInitialDate,
		final int iFinalDate,
		final int iViewDate,
		final org.drip.dynamics.evolution.LSQMPointRecord lrSnapshot,
		final org.drip.dynamics.evolution.LSQMPointRecord lrIncrement,
		final double dblExpectedFinalShortRate,
		final double dblFinalShortRateVariance)
		throws java.lang.Exception
	{
		super (iInitialDate, iFinalDate, iViewDate, lrSnapshot, lrIncrement);

		if (null == (_lslFunding = lslFunding) || !org.drip.quant.common.NumberUtil.IsValid
			(_dblExpectedFinalShortRate = dblExpectedFinalShortRate) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblFinalShortRateVariance =
					dblFinalShortRateVariance)) {
			System.out.println (_lslFunding.fullyQualifiedName());

			System.out.println ("Final Short Rate: " + _dblExpectedFinalShortRate);

			System.out.println ("Final Short Rate Variance: " + _dblFinalShortRateVariance);

			throw new java.lang.Exception ("ShortRateUpdate ctr: Invalid Inputs!");
		}
	}

	/**
	 * Retrieve the Initial Short Rate
	 * 
	 * @return The Initial Short Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Initial Short Rate is not available
	 */

	public double initialShortRate()
		throws java.lang.Exception
	{
		return realizedFinalShortRate() - shortRateIncrement();
	}

	/**
	 * Retrieve the Realized Final Short Rate
	 * 
	 * @return The Realized Final Short Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Realized Final Short Rate is not available
	 */

	public double realizedFinalShortRate()
		throws java.lang.Exception
	{
		return snapshot().qm (_lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE);
	}

	/**
	 * Retrieve the Short Rate Increment
	 * 
	 * @return The Short Rate Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Short Rate Increment is not available
	 */

	public double shortRateIncrement()
		throws java.lang.Exception
	{
		return increment().qm (_lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE);
	}

	/**
	 * Retrieve the Expected Final Short Rate
	 * 
	 * @return The Expected Final Short Rate
	 */

	public double expectedFinalShortRate()
	{
		return _dblExpectedFinalShortRate;
	}

	/**
	 * Retrieve the Final Short Rate Variance
	 * 
	 * @return The Final Short Rate Variance
	 */

	public double finalShortRateVariance()
	{
		return _dblFinalShortRateVariance;
	}

	/**
	 * Compute the Zero Coupon Bond Price
	 * 
	 * @param dblFinalInitialZeroRatio The Final-to-Initial Zero-Coupon Bond Price Ratio
	 * 
	 * @return The Zero Coupon Bond Price
	 * 
	 * @throws java.lang.Exception Thrown if the Zero Coupon Bond Price cannot be computed
	 */

	public double zeroCouponBondPrice (
		final double dblFinalInitialZeroRatio)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblFinalInitialZeroRatio))
			throw new java.lang.Exception ("ShortRateUpdate::zeroCouponBondPrice => Invalid Inputs");

		return dblFinalInitialZeroRatio * snapshot().qm (_lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR);
	}
}
