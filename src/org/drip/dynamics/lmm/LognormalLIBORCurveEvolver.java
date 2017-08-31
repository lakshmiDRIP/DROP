
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
 * LognormalLIBORCurveEvolver sets up and implements the Multi-Factor No-arbitrage Dynamics of the full Curve
 * 	Rates State Quantifiers traced from the Evolution of the LIBOR Forward Rate as formulated in:
 * 
 *  1) Goldys, B., M. Musiela, and D. Sondermann (1994): Log-normality of Rates and Term Structure Models,
 *  	The University of New South Wales.
 * 
 *  2) Musiela, M. (1994): Nominal Annual Rates and Log-normal Volatility Structure, The University of New
 *   	South Wales.
 * 
 * 	3) Brace, A., D. Gatarek, and M. Musiela (1997): The Market Model of Interest Rate Dynamics, Mathematical
 * 		Finance 7 (2), 127-155.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LognormalLIBORCurveEvolver implements org.drip.dynamics.evolution.CurveStateEvolver {
	private int _iNumForwardTenor = -1;
	private org.drip.state.identifier.ForwardLabel _lslForward = null;
	private org.drip.state.identifier.FundingLabel _lslFunding = null;
	private org.drip.spline.params.SegmentCustomBuilderControl[] _aSCBCLIBOR = null;
	private org.drip.spline.params.SegmentCustomBuilderControl[] _aSCBCDiscountFactor = null;
	private org.drip.spline.params.SegmentCustomBuilderControl[] _aSCBCLIBORIncrement = null;
	private org.drip.spline.params.SegmentCustomBuilderControl[] _aSCBCSpotRateIncrement = null;
	private org.drip.spline.params.SegmentCustomBuilderControl[] _aSCBCDiscountFactorIncrement = null;
	private org.drip.spline.params.SegmentCustomBuilderControl[] _aSCBCContinuousForwardIncrement = null;
	private org.drip.spline.params.SegmentCustomBuilderControl[] _aSCBCInstantaneousNominalForward = null;
	private org.drip.spline.params.SegmentCustomBuilderControl[] _aSCBCInstantaneousEffectiveForward = null;

	/**
	 * Create a LognormalLIBORCurveEvolver Instance
	 * 
	 * @param lslFunding The Funding Latent State Label
	 * @param lslForward The Forward Latent State Label
	 * @param iNumForwardTenor Number of Forward Tenors to Build the Span
	 * @param scbc The Common Span Segment Custom Builder Control Instance
	 * 
	 * @return The LognormalLIBORCurveEvolver Instance
	 */

	public static final LognormalLIBORCurveEvolver Create (
		final org.drip.state.identifier.FundingLabel lslFunding,
		final org.drip.state.identifier.ForwardLabel lslForward,
		final int iNumForwardTenor,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc)
	{
		try {
			return new LognormalLIBORCurveEvolver (lslFunding, lslForward, iNumForwardTenor, scbc, scbc,
				scbc, scbc, scbc, scbc, scbc, scbc);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private double forwardDerivative (
		final org.drip.state.forward.ForwardCurve fc,
		final int iTargetPointDate)
		throws java.lang.Exception
	{
		org.drip.function.definition.R1ToR1 freR1ToR1 = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblDate)
				throws java.lang.Exception
			{
				return fc.forward ((int) dblDate);
			}
		};

		return freR1ToR1.derivative (iTargetPointDate, 1);
	}

	private double continuousForwardRateIncrement (
		final int iViewDate,
		final double dblAnnualizedIncrement,
		final double dblAnnualizedIncrementSQRT,
		final org.drip.state.forward.ForwardCurve fc,
		final double[] adblMultivariateRandom,
		final org.drip.dynamics.lmm.LognormalLIBORVolatility llv)
		throws java.lang.Exception
	{
		final int iNumFactor = adblMultivariateRandom.length;

		org.drip.function.definition.R1ToR1 continuousForwardRateR1ToR1 = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblDate)
				throws java.lang.Exception
			{
				double dblForwardPointVolatilityModulus = 0.;
				double dblPointVolatilityMultifactorRandom = 0.;

				double[] adblContinuousForwardVolatility = llv.continuousForwardVolatility ((int) dblDate,
					fc);

				if (null != adblContinuousForwardVolatility) {
					for (int i = 0; i < iNumFactor; ++i) {
						dblForwardPointVolatilityModulus += adblContinuousForwardVolatility[i] *
							adblContinuousForwardVolatility[i];
						dblPointVolatilityMultifactorRandom += adblContinuousForwardVolatility[i] *
							adblMultivariateRandom[i];
					}
				}

				return (fc.forward ((int) dblDate) + 0.5 * dblForwardPointVolatilityModulus) *
					dblAnnualizedIncrement + dblPointVolatilityMultifactorRandom *
						dblAnnualizedIncrementSQRT;
			}
		};

		return continuousForwardRateR1ToR1.derivative (iViewDate, 1);
	}

	private double spotRateIncrement (
		final int iViewDate,
		final double dblAnnualizedIncrement,
		final double dblAnnualizedIncrementSQRT,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final double[] adblMultivariateRandom,
		final org.drip.dynamics.lmm.LognormalLIBORVolatility llv)
		throws java.lang.Exception
	{
		final int iNumFactor = adblMultivariateRandom.length;

		org.drip.function.definition.R1ToR1 spotRateR1ToR1 = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblDate)
				throws java.lang.Exception
			{
				int iDate = (int) dblDate;
				double dblPointVolatilityMultifactorRandom = 0.;

				double[] adblContinuousForwardVolatility = llv.continuousForwardVolatility (iDate, dc);

				if (null != adblContinuousForwardVolatility) {
					for (int i = 0; i < iNumFactor; ++i)
						dblPointVolatilityMultifactorRandom += adblContinuousForwardVolatility[i] *
							adblMultivariateRandom[i];
				}

				return dc.forward (iDate, iDate + 1) * dblAnnualizedIncrement +
					dblPointVolatilityMultifactorRandom * dblAnnualizedIncrementSQRT;
			}
		};

		return spotRateR1ToR1.derivative (iViewDate, 1);
	}

	private org.drip.dynamics.lmm.BGMForwardTenorSnap timeSnap (
		final int iSpotDate,
		final int iTargetPointDate,
		final double dblAnnualizedIncrement,
		final double dblAnnualizedIncrementSQRT,
		final java.lang.String strForwardTenor,
		final org.drip.state.forward.ForwardCurve fc,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.dynamics.lmm.LognormalLIBORVolatility llv)
	{
		double[] adblLognormalFactorPointVolatility = llv.factorPointVolatility (iSpotDate,
			iTargetPointDate);

		double[] adblContinuousForwardVolatility = llv.continuousForwardVolatility (iTargetPointDate, fc);

		double[] adblMultivariateRandom = llv.msg().random();

		double dblCrossVolatilityDotProduct = 0.;
		double dblLognormalPointVolatilityModulus = 0.;
		double dblLIBORVolatilityMultiFactorRandom = 0.;
		double dblContinuousForwardVolatilityModulus = 0.;
		double dblForwardVolatilityMultiFactorRandom = 0.;
		int iNumFactor = adblLognormalFactorPointVolatility.length;

		for (int i = 0; i < iNumFactor; ++i) {
			dblLognormalPointVolatilityModulus += adblLognormalFactorPointVolatility[i] *
				adblLognormalFactorPointVolatility[i];
			dblCrossVolatilityDotProduct += adblLognormalFactorPointVolatility[i] *
				adblContinuousForwardVolatility[i];
			dblLIBORVolatilityMultiFactorRandom += adblLognormalFactorPointVolatility[i] *
				adblMultivariateRandom[i] * dblAnnualizedIncrementSQRT;
			dblContinuousForwardVolatilityModulus += adblContinuousForwardVolatility[i] *
				adblContinuousForwardVolatility[i];
			dblForwardVolatilityMultiFactorRandom += adblContinuousForwardVolatility[i] *
				adblMultivariateRandom[i] * dblAnnualizedIncrementSQRT;
		}

		try {
			double dblLIBOR = fc.forward (iTargetPointDate);

			double dblDiscountFactor = dc.df (iTargetPointDate);

			double dblSpotRate = dc.forward (iSpotDate, iSpotDate + 1);

			double dblContinuousForwardRate = fc.forward (iTargetPointDate);

			double dblDCF = org.drip.analytics.support.Helper.TenorToYearFraction (strForwardTenor);

			double dblLIBORDCF = dblDCF * dblLIBOR;

			double dblLIBORIncrement = dblAnnualizedIncrement * (forwardDerivative (fc, iTargetPointDate) +
				dblLIBOR * dblCrossVolatilityDotProduct + (dblLognormalPointVolatilityModulus * dblLIBOR *
					dblLIBORDCF / (1. + dblLIBORDCF))) + dblLIBOR * dblLIBORVolatilityMultiFactorRandom;

			double dblDiscountFactorIncrement = dblDiscountFactor * (dblSpotRate - dblContinuousForwardRate)
				* dblAnnualizedIncrement - dblForwardVolatilityMultiFactorRandom;

			double dblContinuousForwardRateIncrement = continuousForwardRateIncrement (iTargetPointDate,
				dblAnnualizedIncrement, dblAnnualizedIncrementSQRT, fc, adblMultivariateRandom, llv);

			double dblSpotRateIncrement = spotRateIncrement (iTargetPointDate, dblAnnualizedIncrement,
				dblAnnualizedIncrementSQRT, dc, adblMultivariateRandom, llv);

			double dblContinuousForwardRateEvolved = dblContinuousForwardRate +
				dblContinuousForwardRateIncrement;

			return new org.drip.dynamics.lmm.BGMForwardTenorSnap (iTargetPointDate, dblLIBOR +
				dblLIBORIncrement, dblLIBORIncrement, dblDiscountFactor + dblDiscountFactorIncrement,
					dblDiscountFactorIncrement, dblContinuousForwardRateIncrement, dblSpotRateIncrement,
						java.lang.Math.exp (dblContinuousForwardRateEvolved) - 1., (java.lang.Math.exp
							(dblDCF * dblContinuousForwardRateEvolved) - 1.) / dblDCF, java.lang.Math.sqrt
								(dblLognormalPointVolatilityModulus), java.lang.Math.sqrt
									(dblContinuousForwardVolatilityModulus));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private org.drip.dynamics.lmm.PathwiseQMRealization simulateLIBOR (
		final int iEvolutionDate,
		final int iViewDate,
		final double dblAnnualizedIncrement,
		final double dblAnnualizedIncrementSQRT,
		final org.drip.state.forward.ForwardCurve fc,
		final java.lang.String strForwardTenor,
		final double dblForwardDCF,
		final org.drip.dynamics.lmm.LognormalLIBORVolatility llv)
	{
		int[] aiTenorDate = new int[_iNumForwardTenor + 1];
		double[] adblLIBOR = new double[_iNumForwardTenor + 1];

		double[] adblMultivariateRandom = llv.msg().random();

		org.drip.analytics.date.JulianDate dtTargetPoint = new org.drip.analytics.date.JulianDate
			(iViewDate);

		try {
			for (int i = 0; i <= _iNumForwardTenor; ++i) {
				int iTargetPointDate = dtTargetPoint.julian();

				double[] adblLognormalFactorPointVolatility = llv.factorPointVolatility (iEvolutionDate,
					iTargetPointDate);

				double[] adblContinuousForwardVolatility = llv.continuousForwardVolatility (iTargetPointDate,
					fc);

				double dblLIBOR = fc.forward (iTargetPointDate);

				aiTenorDate[i] = iTargetPointDate;
				double dblCrossVolatilityDotProduct = 0.;
				double dblLIBORDCF = dblForwardDCF * dblLIBOR;
				double dblLognormalPointVolatilityModulus = 0.;
				double dblLIBORVolatilityMultiFactorRandom = 0.;
				int iNumFactor = adblLognormalFactorPointVolatility.length;

				for (int j = 0; j < iNumFactor; ++j) {
					dblLognormalPointVolatilityModulus += adblLognormalFactorPointVolatility[j] *
						adblLognormalFactorPointVolatility[j];
					dblCrossVolatilityDotProduct += adblLognormalFactorPointVolatility[j] *
						adblContinuousForwardVolatility[j];
					dblLIBORVolatilityMultiFactorRandom += adblLognormalFactorPointVolatility[j] *
						adblMultivariateRandom[j] * dblAnnualizedIncrementSQRT;
				}

				adblLIBOR[i] = dblLIBOR + dblAnnualizedIncrement * (forwardDerivative (fc, iTargetPointDate)
					+ dblLIBOR * dblCrossVolatilityDotProduct + (dblLognormalPointVolatilityModulus *
						dblLIBOR * dblLIBORDCF / (1. + dblLIBORDCF))) + dblLIBOR *
							dblLIBORVolatilityMultiFactorRandom;

				dtTargetPoint = dtTargetPoint.addTenor (strForwardTenor);
			}

			return new org.drip.dynamics.lmm.PathwiseQMRealization (aiTenorDate, adblLIBOR);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * LognormalLIBORCurveEvolver Constructor
	 * 
	 * @param lslFunding The Funding Latent State Label
	 * @param lslForward The Forward Latent State Label
	 * @param iNumForwardTenor Number of Forward Tenors to Build the Span
	 * @param scbcLIBOR LIBOR Span Segment Custom Builder Control Instance
	 * @param scbcDiscountFactor Discount Factor Span Segment Custom Builder Control Instance
	 * @param scbcLIBORIncrement LIBOR Increment Span Segment Custom Builder Control Instance
	 * @param scbcDiscountFactorIncrement Discount Factor Increment Span Segment Custom Builder Control
	 * 		Instance
	 * @param scbcContinuousForwardIncrement Instantaneous Continuously Compounded Forward Rate Increment
	 *  	Span Segment Custom Builder Control Instance
	 * @param scbcSpotRateIncrement Spot Rate Increment Span Segment Custom Builder Control Instance
	 * @param scbcInstantaneousEffectiveForward Instantaneous Effective Annual Forward Rate Span Segment
	 * 		Custom Builder Control Instance
	 * @param scbcInstantaneousNominalForward Instantaneous Nominal Annual Forward Rate Span Segment Custom
	 * 		Builder Control Instance
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public LognormalLIBORCurveEvolver (
		final org.drip.state.identifier.FundingLabel lslFunding,
		final org.drip.state.identifier.ForwardLabel lslForward,
		final int iNumForwardTenor,
		final org.drip.spline.params.SegmentCustomBuilderControl scbcLIBOR,
		final org.drip.spline.params.SegmentCustomBuilderControl scbcDiscountFactor,
		final org.drip.spline.params.SegmentCustomBuilderControl scbcLIBORIncrement,
		final org.drip.spline.params.SegmentCustomBuilderControl scbcDiscountFactorIncrement,
		final org.drip.spline.params.SegmentCustomBuilderControl scbcContinuousForwardIncrement,
		final org.drip.spline.params.SegmentCustomBuilderControl scbcSpotRateIncrement,
		final org.drip.spline.params.SegmentCustomBuilderControl scbcInstantaneousEffectiveForward,
		final org.drip.spline.params.SegmentCustomBuilderControl scbcInstantaneousNominalForward)
		throws java.lang.Exception
	{
		if (null == (_lslFunding = lslFunding) || null == (_lslForward = lslForward) || 1 >=
			(_iNumForwardTenor = iNumForwardTenor) || null == scbcLIBOR || null == scbcLIBORIncrement || null
				== scbcDiscountFactor || null == scbcDiscountFactorIncrement || null ==
					scbcContinuousForwardIncrement || null == scbcSpotRateIncrement || null ==
						scbcInstantaneousEffectiveForward)
			throw new java.lang.Exception ("LognormalLIBORCurveEvolver ctr: Invalid Inputs");

		_aSCBCLIBOR = new org.drip.spline.params.SegmentCustomBuilderControl[iNumForwardTenor];
		_aSCBCDiscountFactor = new org.drip.spline.params.SegmentCustomBuilderControl[iNumForwardTenor];
		_aSCBCLIBORIncrement = new org.drip.spline.params.SegmentCustomBuilderControl[iNumForwardTenor];
		_aSCBCDiscountFactorIncrement = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumForwardTenor];
		_aSCBCContinuousForwardIncrement = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumForwardTenor];
		_aSCBCSpotRateIncrement = new org.drip.spline.params.SegmentCustomBuilderControl[iNumForwardTenor];
		_aSCBCInstantaneousNominalForward = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumForwardTenor];
		_aSCBCInstantaneousEffectiveForward = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumForwardTenor];

		for (int i = 0; i < iNumForwardTenor; ++i) {
			_aSCBCLIBOR[i] = scbcLIBOR;
			_aSCBCDiscountFactor[i] = scbcDiscountFactor;
			_aSCBCLIBORIncrement[i] = scbcLIBORIncrement;
			_aSCBCDiscountFactorIncrement[i] = scbcDiscountFactorIncrement;
			_aSCBCContinuousForwardIncrement[i] = scbcContinuousForwardIncrement;
			_aSCBCSpotRateIncrement[i] = scbcSpotRateIncrement;
			_aSCBCInstantaneousEffectiveForward[i] = scbcInstantaneousEffectiveForward;
			_aSCBCInstantaneousNominalForward[i] = scbcInstantaneousNominalForward;
		}
	}

	/**
	 * Retrieve the Funding Label
	 * 
	 * @return The Funding Label
	 */

	public org.drip.state.identifier.FundingLabel fundingLabel()
	{
		return _lslFunding;
	}

	/**
	 * Retrieve the Forward Label
	 * 
	 * @return The Forward Label
	 */

	public org.drip.state.identifier.ForwardLabel forwardLabel()
	{
		return _lslForward;
	}

	/**
	 * Retrieve the Number of Forward Tenors comprising the Span Tenor
	 * 
	 * @return Number of Forward Tenors comprising the Span Tenor
	 */

	public int spanTenor()
	{
		return _iNumForwardTenor;
	}

	/**
	 * Retrieve the LIBOR Curve Segment Custom Builder Control Instance
	 * 
	 * @return The LIBOR Curve Segment Custom Builder Control Instance
	 */

	public org.drip.spline.params.SegmentCustomBuilderControl scbcLIBOR()
	{
		return _aSCBCLIBOR[0];
	}

	/**
	 * Retrieve the Discount Factor Segment Custom Builder Control Instance
	 * 
	 * @return The Discount Factor Segment Custom Builder Control Instance
	 */

	public org.drip.spline.params.SegmentCustomBuilderControl scbcDiscountFactor()
	{
		return _aSCBCDiscountFactor[0];
	}

	/**
	 * Retrieve the LIBOR Increment Segment Custom Builder Control Instance
	 * 
	 * @return The LIBOR Increment Segment Custom Builder Control Instance
	 */

	public org.drip.spline.params.SegmentCustomBuilderControl scbcLIBORIncrement()
	{
		return _aSCBCLIBORIncrement[0];
	}

	/**
	 * Retrieve the Discount Factor Increment Segment Custom Builder Control Instance
	 * 
	 * @return The Discount Factor Increment Segment Custom Builder Control Instance
	 */

	public org.drip.spline.params.SegmentCustomBuilderControl scbcDiscountFactorIncrement()
	{
		return _aSCBCDiscountFactorIncrement[0];
	}

	/**
	 * Retrieve the Instantaneous Continuously Compounded Forward Rate Increment Segment Custom Builder
	 *  Control Instance
	 * 
	 * @return The Instantaneous Continuously Compounded Forward Rate Increment Segment Custom Builder
	 *  Control Instance
	 */

	public org.drip.spline.params.SegmentCustomBuilderControl scbcContinuousForwardIncrement()
	{
		return _aSCBCContinuousForwardIncrement[0];
	}

	/**
	 * Retrieve the Spot Rate Increment Segment Custom Builder Control Instance
	 * 
	 * @return The Spot Rate Increment Segment Custom Builder Control Instance
	 */

	public org.drip.spline.params.SegmentCustomBuilderControl scbcSpotRateIncrement()
	{
		return _aSCBCSpotRateIncrement[0];
	}

	/**
	 * Retrieve the Instantaneous Effective Annual Forward Rate Increment Segment Custom Builder Control
	 *  Instance
	 * 
	 * @return The Instantaneous Effective Annual Forward Rate Increment Segment Custom Builder Control
	 *  Instance
	 */

	public org.drip.spline.params.SegmentCustomBuilderControl scbcInstantaneousEffectiveForward()
	{
		return _aSCBCInstantaneousEffectiveForward[0];
	}

	/**
	 * Retrieve the Instantaneous Nominal Annual Forward Rate Increment Segment Custom Builder Control
	 *  Instance
	 * 
	 * @return The Instantaneous Nominal Annual Forward Rate Increment Segment Custom Builder Control
	 *  Instance
	 */

	public org.drip.spline.params.SegmentCustomBuilderControl scbcInstantaneousNominalForward()
	{
		return _aSCBCInstantaneousNominalForward[0];
	}

	@Override public org.drip.dynamics.lmm.BGMCurveUpdate evolve (
		final int iSpotDate,
		final int iViewDate,
		final int iSpotTimeIncrement,
		final org.drip.dynamics.evolution.LSQMCurveUpdate lsqmPrev)
	{
		if (iSpotDate > iViewDate || null == lsqmPrev || !(lsqmPrev instanceof
			org.drip.dynamics.lmm.BGMCurveUpdate))
			return null;

		org.drip.dynamics.lmm.BGMCurveUpdate bgmPrev = (org.drip.dynamics.lmm.BGMCurveUpdate) lsqmPrev;
		org.drip.dynamics.lmm.BGMForwardTenorSnap[] aBGMTS = new
			org.drip.dynamics.lmm.BGMForwardTenorSnap[_iNumForwardTenor + 1];
		double dblAnnualizedIncrement = 1. * iSpotTimeIncrement / 365.25;

		double dblAnnualizedIncrementSQRT = java.lang.Math.sqrt (dblAnnualizedIncrement);

		org.drip.state.forward.ForwardCurve fc = bgmPrev.forwardCurve();

		org.drip.state.discount.MergedDiscountForwardCurve dc = bgmPrev.discountCurve();

		org.drip.dynamics.lmm.LognormalLIBORVolatility llv = bgmPrev.lognormalLIBORVolatility();

		java.lang.String strForwardTenor = _lslForward.tenor();

		org.drip.analytics.date.JulianDate dtTargetPoint = new org.drip.analytics.date.JulianDate
			(iViewDate);

		try {
			for (int i = 0; i <= _iNumForwardTenor; ++i) {
				if (null == (aBGMTS[i] = timeSnap (iSpotDate, dtTargetPoint.julian(), iSpotTimeIncrement,
					dblAnnualizedIncrementSQRT, strForwardTenor, fc, dc, llv)) || null == (dtTargetPoint =
						dtTargetPoint.addTenor (strForwardTenor)))
					return null;
			}

			org.drip.dynamics.lmm.BGMTenorNodeSequence btns = new org.drip.dynamics.lmm.BGMTenorNodeSequence
				(aBGMTS);

			org.drip.spline.stretch.BoundarySettings bs =
				org.drip.spline.stretch.BoundarySettings.NaturalStandard();

			java.lang.String strForwardLabelName = _lslForward.fullyQualifiedName();

			java.lang.String strFundingLabelName = _lslFunding.fullyQualifiedName();

			int[] aiTenorDate = btns.dates();

			org.drip.state.curve.BasisSplineForwardRate fcLIBOR = new
				org.drip.state.curve.BasisSplineForwardRate (_lslForward, new
					org.drip.spline.grid.OverlappingStretchSpan
						(org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
							(strForwardLabelName + "_QM_LIBOR", aiTenorDate, btns.liborRates(), _aSCBCLIBOR,
								null, bs, org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE)));

			org.drip.state.curve.DiscountFactorDiscountCurve dcDiscountFactor = new
				org.drip.state.curve.DiscountFactorDiscountCurve (_lslForward.currency(), new
					org.drip.spline.grid.OverlappingStretchSpan
						(org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
							(strFundingLabelName + "_QM_DISCOUNTFACTOR", aiTenorDate, btns.discountFactors(),
								_aSCBCDiscountFactor, null, bs,
									org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE)));

			org.drip.spline.stretch.MultiSegmentSequence mssDiscountFactorIncrement =
				org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
					(strFundingLabelName + "_INCREMENT", aiTenorDate, btns.discountFactorIncrements(),
						_aSCBCDiscountFactorIncrement, null, bs,
							org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE);

			org.drip.spline.stretch.MultiSegmentSequence mssContinuousForwardRateIncrement =
				org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
					(strForwardLabelName + "_CONT_FWD_INCREMENT", aiTenorDate,
						btns.continuousForwardRateIncrements(), _aSCBCContinuousForwardIncrement, null, bs,
							org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE);

			org.drip.spline.stretch.MultiSegmentSequence mssSpotRateIncrement =
				org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
					(strForwardLabelName + "_SPOT_RATE_INCREMENT", aiTenorDate, btns.spotRateIncrements(),
						_aSCBCSpotRateIncrement, null, bs,
							org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE);

			org.drip.spline.stretch.MultiSegmentSequence mssInstantaneousEffectiveForwardRate =
				org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
					(strForwardLabelName + "_EFFECTIVE_ANNUAL_FORWARD", aiTenorDate,
						btns.instantaneousEffectiveForwardRates(), _aSCBCInstantaneousEffectiveForward, null,
							bs, org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE);

			org.drip.spline.stretch.MultiSegmentSequence mssInstantaneousNominalForwardRate =
				org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
					(strForwardLabelName + "_NOMINAL_ANNUAL_FORWARD", aiTenorDate,
						btns.instantaneousNominalForwardRates(), _aSCBCInstantaneousNominalForward, null, bs,
							org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE);

			return org.drip.dynamics.lmm.BGMCurveUpdate.Create (_lslFunding, _lslForward, iSpotDate,
				iSpotDate + iSpotTimeIncrement, fcLIBOR, new org.drip.spline.grid.OverlappingStretchSpan
					(org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
						(strForwardLabelName + "_INCREMENT", aiTenorDate, btns.liborRateIncrements(),
							_aSCBCLIBORIncrement, null, bs,
								org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE)), dcDiscountFactor,
									new org.drip.spline.grid.OverlappingStretchSpan
										(mssDiscountFactorIncrement), new
											org.drip.spline.grid.OverlappingStretchSpan
												(mssContinuousForwardRateIncrement), new
													org.drip.spline.grid.OverlappingStretchSpan
														(mssSpotRateIncrement), new
															org.drip.spline.grid.OverlappingStretchSpan
																(mssInstantaneousEffectiveForwardRate), new
																	org.drip.spline.grid.OverlappingStretchSpan
				(mssInstantaneousNominalForwardRate), bgmPrev.lognormalLIBORVolatility());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public double[][] simulatePrincipalMetric (
		final int iEvolutionStartDate,
		final int iEvolutionFinishDate,
		final int iEvolutionIncrement,
		final int iViewDate,
		final org.drip.dynamics.evolution.LSQMCurveUpdate lsqmStart,
		final int iNumSimulation)
	{
		if (iEvolutionStartDate > iViewDate || iEvolutionFinishDate <= iEvolutionStartDate ||
			iEvolutionFinishDate > iViewDate || iEvolutionIncrement <= 0. || null == lsqmStart || !(lsqmStart
				instanceof org.drip.dynamics.lmm.BGMCurveUpdate) || 1 >= iNumSimulation)
			return null;

		org.drip.dynamics.lmm.BGMCurveUpdate bgmMetrics = (org.drip.dynamics.lmm.BGMCurveUpdate) lsqmStart;

		org.drip.dynamics.lmm.LognormalLIBORVolatility llv = bgmMetrics.lognormalLIBORVolatility();

		java.lang.String strForwardLabel = _lslForward.fullyQualifiedName() + "_QM_LIBOR";

		org.drip.state.forward.ForwardCurve fc = bgmMetrics.forwardCurve();

		java.lang.String strForwardTenor = _lslForward.tenor();

		int iNumTimeStep = ((iEvolutionFinishDate - iEvolutionStartDate) / iEvolutionIncrement) + 1;
		double[][] aadblTenorLIBOR = new double[iNumTimeStep][_iNumForwardTenor + 1];
		double dblAnnualizedIncrement = 1. * iEvolutionIncrement / 365.25;
		double dblForwardDCF = java.lang.Double.NaN;

		double dblAnnualizedIncrementSQRT = java.lang.Math.sqrt (dblAnnualizedIncrement);

		org.drip.spline.stretch.BoundarySettings bs =
			org.drip.spline.stretch.BoundarySettings.NaturalStandard();

		try {
			dblForwardDCF = org.drip.analytics.support.Helper.TenorToYearFraction (strForwardTenor);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0 ; i < iNumTimeStep; ++i) {
			for (int j = 0 ; j <= _iNumForwardTenor; ++j)
				aadblTenorLIBOR[i][j] = 0.;
		}

		for (int iSimulationIndex = 0; iSimulationIndex < iNumSimulation; ++iSimulationIndex) {
			int iEvolutionTimeIndex = 0;
			org.drip.state.forward.ForwardCurve fcLIBOR = fc;

			for (int iEvolutionDate = iEvolutionStartDate; iEvolutionDate <= iEvolutionFinishDate;
				iEvolutionDate += iEvolutionIncrement) {
				org.drip.dynamics.lmm.PathwiseQMRealization pqmr = simulateLIBOR (iEvolutionDate, iViewDate,
					dblAnnualizedIncrement, dblAnnualizedIncrementSQRT, fcLIBOR, strForwardTenor,
						dblForwardDCF, llv);

				if (null == pqmr) return null;

				double[] adblSimulatedLIBOR = pqmr.realizedQM();

				try {
					fcLIBOR = new org.drip.state.curve.BasisSplineForwardRate (_lslForward, new
						org.drip.spline.grid.OverlappingStretchSpan
							(org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
						(strForwardLabel + "_QM_LIBOR", pqmr.targetDate(), adblSimulatedLIBOR, _aSCBCLIBOR,
							null, bs, org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE)));
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}

				for (int j = 0 ; j <= _iNumForwardTenor; ++j)
					aadblTenorLIBOR[iEvolutionTimeIndex][j] += adblSimulatedLIBOR[j];

				iEvolutionTimeIndex++;
			}
		}

		for (int i = 0 ; i < iNumTimeStep; ++i) {
			for (int j = 0 ; j <= _iNumForwardTenor; ++j)
				aadblTenorLIBOR[i][j] /= iNumSimulation;
		}

		return aadblTenorLIBOR;
	}

	/**
	 * Construct an Array of Forward Curves that Result from the Simulation
	 * 
	 * @param iEvolutionStartDate The Start Date of the Simulation
	 * @param iEvolutionFinishDate The Finish Date of the Simulation
	 * @param iEvolutionIncrement The Simulation Evolution Increment
	 * @param iViewDate The Forward View Date
	 * @param lsqmStart The Initial/Starting LSQM State
	 * @param iNumSimulation Number of Simulations
	 * 
	 * @return The Array of Forward Curves that Result from the Simulation
	 */

	public org.drip.state.forward.ForwardCurve[] simulateTerminalLatentState (
		final int iEvolutionStartDate,
		final int iEvolutionFinishDate,
		final int iEvolutionIncrement,
		final int iViewDate,
		final org.drip.dynamics.evolution.LSQMCurveUpdate lsqmStart,
		final int iNumSimulation)
	{
		if (iEvolutionStartDate > iViewDate || iEvolutionFinishDate <= iEvolutionStartDate ||
			iEvolutionFinishDate > iViewDate || iEvolutionIncrement <= 0. || null == lsqmStart || !(lsqmStart
				instanceof org.drip.dynamics.lmm.BGMCurveUpdate) || 1 >= iNumSimulation)
			return null;

		org.drip.dynamics.lmm.BGMCurveUpdate bgmMetrics = (org.drip.dynamics.lmm.BGMCurveUpdate) lsqmStart;

		org.drip.dynamics.lmm.LognormalLIBORVolatility llv = bgmMetrics.lognormalLIBORVolatility();

		java.lang.String strForwardLabel = _lslForward.fullyQualifiedName() + "_QM_LIBOR";

		org.drip.state.forward.ForwardCurve fc = bgmMetrics.forwardCurve();

		java.lang.String strForwardTenor = _lslForward.tenor();

		org.drip.state.forward.ForwardCurve[] aFCLIBOR = new
			org.drip.state.forward.ForwardCurve[iNumSimulation];
		double dblAnnualizedIncrement = 1. * iEvolutionIncrement / 365.25;
		double dblForwardDCF = java.lang.Double.NaN;

		double dblAnnualizedIncrementSQRT = java.lang.Math.sqrt (dblAnnualizedIncrement);

		org.drip.spline.stretch.BoundarySettings bs =
			org.drip.spline.stretch.BoundarySettings.NaturalStandard();

		try {
			dblForwardDCF = org.drip.analytics.support.Helper.TenorToYearFraction (strForwardTenor);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int iSimulationIndex = 0; iSimulationIndex < iNumSimulation; ++iSimulationIndex) {
			System.out.println ("\t\tSimulation #" + (iSimulationIndex + 1));

			org.drip.state.forward.ForwardCurve fcLIBOR = fc;

			for (int iEvolutionDate = iEvolutionStartDate; iEvolutionDate <= iEvolutionFinishDate;
				iEvolutionDate += iEvolutionIncrement) {
				org.drip.dynamics.lmm.PathwiseQMRealization pqmr = simulateLIBOR (iEvolutionDate, iViewDate,
					dblAnnualizedIncrement, dblAnnualizedIncrementSQRT, fcLIBOR, strForwardTenor,
						dblForwardDCF, llv);

				if (null == pqmr) return null;

				try {
					fcLIBOR = new org.drip.state.curve.BasisSplineForwardRate (_lslForward, new
						org.drip.spline.grid.OverlappingStretchSpan
							(org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
						(strForwardLabel + "_QM_LIBOR", pqmr.targetDate(), pqmr.realizedQM(), _aSCBCLIBOR,
							null, bs, org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE)));
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}

			aFCLIBOR[iSimulationIndex] = fcLIBOR;
		}

		return aFCLIBOR;
	}
}
