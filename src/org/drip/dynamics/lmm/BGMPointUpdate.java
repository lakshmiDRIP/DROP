
package org.drip.dynamics.lmm;

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
 * BGMPointUpdate contains the Instantaneous Snapshot of the Evolving Discount Point Latent State
 *  Quantification Metrics Updated using the BGM LIBOR Update Dynamics.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BGMPointUpdate extends org.drip.dynamics.evolution.LSQMPointUpdate {
	private org.drip.state.identifier.ForwardLabel _lslForward = null;
	private org.drip.state.identifier.FundingLabel _lslFunding = null;
	private double _dblLognormalLIBORVolatility = java.lang.Double.NaN;
	private double _dblContinuouslyCompoundedForwardVolatility = java.lang.Double.NaN;

	/**
	 * Construct an Instance of BGMPointUpdate
	 * 
	 * @param lslFunding The Funding Latent State Label
	 * @param lslForward The Forward Latent State Label
	 * @param iInitialDate The Initial Date
	 * @param iFinalDate The Final Date
	 * @param iTargetPointDate The Target Point Date
	 * @param dblLIBOR The LIBOR Rate
	 * @param dblLIBORIncrement The LIBOR Rate Increment
	 * @param dblContinuousForwardRate The Continuously Compounded Forward Rate
	 * @param dblContinuousForwardRateIncrement The Continuously Compounded Forward Rate Increment
	 * @param dblSpotRate The Spot Rate
	 * @param dblSpotRateIncrement The Spot Rate Increment
	 * @param dblDiscountFactor The Discount Factor
	 * @param dblDiscountFactorIncrement The Discount Factor Increment
	 * @param dblInstantaneousEffectiveForwardRate Instantaneous Effective Annual Forward Rate
	 * @param dblInstantaneousNominalForwardRate Instantaneous Nominal Annual Forward Rate
	 * @param dblLognormalLIBORVolatility The Log-normal LIBOR Rate Volatility
	 * @param dblContinuouslyCompoundedForwardVolatility The Continuously Compounded Forward Rate Volatility
	 * 
	 * @return Instance of BGMPointUpdate
	 */

	public static final BGMPointUpdate Create (
		final org.drip.state.identifier.FundingLabel lslFunding,
		final org.drip.state.identifier.ForwardLabel lslForward,
		final int iInitialDate,
		final int iFinalDate,
		final int iTargetPointDate,
		final double dblLIBOR,
		final double dblLIBORIncrement,
		final double dblContinuousForwardRate,
		final double dblContinuousForwardRateIncrement,
		final double dblSpotRate,
		final double dblSpotRateIncrement,
		final double dblDiscountFactor,
		final double dblDiscountFactorIncrement,
		final double dblInstantaneousEffectiveForwardRate,
		final double dblInstantaneousNominalForwardRate,
		final double dblLognormalLIBORVolatility,
		final double dblContinuouslyCompoundedForwardVolatility)
	{
		org.drip.dynamics.evolution.LSQMPointRecord lrSnapshot = new
			org.drip.dynamics.evolution.LSQMPointRecord();

		if (!lrSnapshot.setQM (lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_LIBOR_RATE, dblLIBOR))
			return null;

		if (!lrSnapshot.setQM (lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_CONTINUOUSLY_COMPOUNDED_FORWARD_RATE,
				dblContinuousForwardRate))
			return null;

		if (!lrSnapshot.setQM (lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_INSTANTANEOUS_EFFECTIVE_FORWARD_RATE,
				dblInstantaneousEffectiveForwardRate))
			return null;

		if (!lrSnapshot.setQM (lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_INSTANTANEOUS_NOMINAL_FORWARD_RATE,
				dblInstantaneousNominalForwardRate))
			return null;

		if (!lrSnapshot.setQM (lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE, dblSpotRate))
			return null;

		if (!lrSnapshot.setQM (lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR, dblDiscountFactor))
			return null;

		org.drip.dynamics.evolution.LSQMPointRecord lrIncrement = new
			org.drip.dynamics.evolution.LSQMPointRecord();

		if (!lrIncrement.setQM (lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_LIBOR_RATE, dblLIBORIncrement))
			return null;

		if (!lrIncrement.setQM (lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_CONTINUOUSLY_COMPOUNDED_FORWARD_RATE,
				dblContinuousForwardRateIncrement))
			return null;

		if (!lrIncrement.setQM (lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE, dblSpotRateIncrement))
			return null;

		if (!lrIncrement.setQM (lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR,
				dblDiscountFactorIncrement))
			return null;

		try {
			return new BGMPointUpdate (lslFunding, lslForward, iInitialDate, iFinalDate, iTargetPointDate,
				lrSnapshot, lrIncrement, dblLognormalLIBORVolatility,
					dblContinuouslyCompoundedForwardVolatility);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private BGMPointUpdate (
		final org.drip.state.identifier.FundingLabel lslFunding,
		final org.drip.state.identifier.ForwardLabel lslForward,
		final int iInitialDate,
		final int iFinalDate,
		final int iViewDate,
		final org.drip.dynamics.evolution.LSQMPointRecord lrSnapshot,
		final org.drip.dynamics.evolution.LSQMPointRecord lrIncrement,
		final double dblLognormalLIBORVolatility,
		final double dblContinuouslyCompoundedForwardVolatility)
		throws java.lang.Exception
	{
		super (iInitialDate, iFinalDate, iViewDate, lrSnapshot, lrIncrement);

		if (null == (_lslFunding = lslFunding) || null == (_lslForward = lslForward) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblLognormalLIBORVolatility =
				dblLognormalLIBORVolatility) || !org.drip.quant.common.NumberUtil.IsValid
					(_dblContinuouslyCompoundedForwardVolatility =
						dblContinuouslyCompoundedForwardVolatility))
			throw new java.lang.Exception ("BGMPointUpdate ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the LIBOR Rate
	 * 
	 * @return The LIBOR Rate
	 * 
	 * @throws java.lang.Exception Thrown if the LIBOR Rate is not available
	 */

	public double libor()
		throws java.lang.Exception
	{
		return snapshot().qm (_lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_LIBOR_RATE);
	}

	/**
	 * Retrieve the LIBOR Rate Increment
	 * 
	 * @return The LIBOR Rate Increment
	 * 
	 * @throws java.lang.Exception Thrown if the LIBOR Rate Increment is not available
	 */

	public double liborIncrement()
		throws java.lang.Exception
	{
		return increment().qm (_lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_LIBOR_RATE);
	}

	/**
	 * Retrieve the Continuously Compounded Forward Rate
	 * 
	 * @return The Continuously Compounded Forward Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Continuously Compounded Forward Rate is not available
	 */

	public double continuousForwardRate()
		throws java.lang.Exception
	{
		return snapshot().qm (_lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_CONTINUOUSLY_COMPOUNDED_FORWARD_RATE);
	}

	/**
	 * Retrieve the Continuously Compounded Forward Rate Increment
	 * 
	 * @return The Continuously Compounded Forward Rate Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Continuously Compounded Forward Rate Increment is not
	 *  available
	 */

	public double continuousForwardRateIncrement()
		throws java.lang.Exception
	{
		return increment().qm (_lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_CONTINUOUSLY_COMPOUNDED_FORWARD_RATE);
	}

	/**
	 * Retrieve the Instantaneous Effective Annual Forward Rate
	 * 
	 * @return The Instantaneous Effective Annual Forward Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Instantaneous Effective Annual Forward Rate is not available
	 */

	public double instantaneousEffectiveForwardRate()
		throws java.lang.Exception
	{
		return snapshot().qm (_lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_INSTANTANEOUS_EFFECTIVE_FORWARD_RATE);
	}

	/**
	 * Retrieve the Instantaneous Nominal Annual Forward Rate
	 * 
	 * @return The Instantaneous Nominal Annual Forward Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Instantaneous Nominal Annual Forward Rate is not available
	 */

	public double instantaneousNominalForwardRate()
		throws java.lang.Exception
	{
		return snapshot().qm (_lslForward,
			org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_INSTANTANEOUS_NOMINAL_FORWARD_RATE);
	}

	/**
	 * Retrieve the Spot Rate
	 * 
	 * @return The Spot Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Spot Rate is not available
	 */

	public double spotRate()
		throws java.lang.Exception
	{
		return snapshot().qm (_lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE);
	}

	/**
	 * Retrieve the Spot Rate Increment
	 * 
	 * @return The Spot Rate Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Spot Rate Increment is not available
	 */

	public double spotRateIncrement()
		throws java.lang.Exception
	{
		return increment().qm (_lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE);
	}

	/**
	 * Retrieve the Discount Factor
	 * 
	 * @return The Discount Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Factor is not available
	 */

	public double discountFactor()
		throws java.lang.Exception
	{
		return snapshot().qm (_lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR);
	}

	/**
	 * Retrieve the Discount Factor Increment
	 * 
	 * @return The Discount Factor Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Factor Increment is not available
	 */

	public double discountFactorIncrement()
		throws java.lang.Exception
	{
		return increment().qm (_lslFunding,
			org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR);
	}

	/**
	 * Retrieve the Log-normal LIBOR Volatility
	 * 
	 * @return The Log-normal LIBOR Volatility
	 */

	public double lognormalLIBORVolatility()
	{
		return _dblLognormalLIBORVolatility;
	}

	/**
	 * Retrieve the Continuously Compounded Forward Rate Volatility
	 * 
	 * @return The Continuously Compounded Forward Rate Volatility
	 */

	public double continuouslyCompoundedForwardVolatility()
	{
		return _dblContinuouslyCompoundedForwardVolatility;
	}
}
