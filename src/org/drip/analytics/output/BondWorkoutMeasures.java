
package org.drip.analytics.output;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * BondWorkoutMeasures encapsulates the parsimonius yet complete set of measures generated out of a full bond
 * 	analytics run to a given work-out. It contains the following:
 * 	- Credit Risky/Credit Riskless Clean/Dirty Coupon Measures
 * 	- Credit Risky/Credit Riskless Par/Principal PV
 * 	- Loss Measures such as expected Recovery, Loss on instantaneous default, and default exposure
 * 		with/without recovery
 * 	- Unit Coupon measures such as Accrued 01, first coupon/index rate
 *
 * @author Lakshmi Krishnamurthy
 */

public class BondWorkoutMeasures {
	private double _dblAccrued01 = java.lang.Double.NaN;
	private double _dblRecoveryPV = java.lang.Double.NaN;
	private BondCouponMeasures _bcmCreditRiskyClean = null;
	private BondCouponMeasures _bcmCreditRiskyDirty = null;
	private double _dblFirstIndexRate = java.lang.Double.NaN;
	private double _dblFirstCouponRate = java.lang.Double.NaN;
	private BondCouponMeasures _bcmCreditRisklessClean = null;
	private BondCouponMeasures _bcmCreditRisklessDirty = null;
	private double _dblDefaultExposure = java.lang.Double.NaN;
	private double _dblExpectedRecovery = java.lang.Double.NaN;
	private double _dblCreditRiskyParPV = java.lang.Double.NaN;
	private double _dblCreditRisklessParPV = java.lang.Double.NaN;
	private double _dblDefaultExposureNoRec = java.lang.Double.NaN;
	private double _dblCreditRiskyPrincipalPV = java.lang.Double.NaN;
	private double _dblCreditRisklessPrincipalPV = java.lang.Double.NaN;
	private double _dblLossOnInstantaneousDefault = java.lang.Double.NaN;

	/**
	 * 
	 * BondWorkoutMeasures constructor
	 * 
	 * @param bcmCreditRiskyDirty Dirty credit risky BondMeasuresCoupon
	 * @param bcmCreditRisklessDirty Dirty credit risk-less BondMeasuresCoupon
	 * @param dblCreditRiskyParPV Credit risky Par PV
	 * @param dblCreditRisklessParPV Credit risk-less par PV
	 * @param dblCreditRiskyPrincipalPV Credit Risky Principal PV
	 * @param dblCreditRisklessPrincipalPV Credit Risk-less Principal PV
	 * @param dblRecoveryPV Recovery PV
	 * @param dblExpectedRecovery Expected Recovery
	 * @param dblDefaultExposure PV on instantaneous default
	 * @param dblDefaultExposureNoRec PV on instantaneous default with zero recovery
	 * @param dblLossOnInstantaneousDefault Loss On Instantaneous Default
	 * @param dblAccrued01 Accrued01
	 * @param dblFirstCouponRate First Coupon Rate
	 * @param dblFirstIndexRate First Index Rate
	 * @param dblCashPayDF Cash Pay Discount Factor
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public BondWorkoutMeasures (
		final BondCouponMeasures bcmCreditRiskyDirty,
		final BondCouponMeasures bcmCreditRisklessDirty,
		final double dblCreditRiskyParPV,
		final double dblCreditRisklessParPV,
		final double dblCreditRiskyPrincipalPV,
		final double dblCreditRisklessPrincipalPV,
		final double dblRecoveryPV,
		final double dblExpectedRecovery,
		final double dblDefaultExposure,
		final double dblDefaultExposureNoRec,
		final double dblLossOnInstantaneousDefault,
		final double dblAccrued01,
		final double dblFirstCouponRate,
		final double dblFirstIndexRate,
		final double dblCashPayDF)
		throws java.lang.Exception
	{
		if (null == (_bcmCreditRisklessDirty = bcmCreditRisklessDirty) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblCreditRisklessParPV = dblCreditRisklessParPV) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblCreditRisklessPrincipalPV =
					dblCreditRisklessPrincipalPV) || !org.drip.quant.common.NumberUtil.IsValid (_dblAccrued01
						= dblAccrued01) || !org.drip.quant.common.NumberUtil.IsValid (_dblFirstCouponRate =
							dblFirstCouponRate))
			throw new java.lang.Exception ("BondWorkoutMeasures ctr: Invalid Inputs!");

		_dblRecoveryPV = dblRecoveryPV;
		_dblFirstIndexRate = dblFirstIndexRate;
		_dblDefaultExposure = dblDefaultExposure;
		_dblExpectedRecovery = dblExpectedRecovery;
		_bcmCreditRiskyDirty = bcmCreditRiskyDirty;
		_dblCreditRiskyParPV = dblCreditRiskyParPV;
		_dblDefaultExposureNoRec = dblDefaultExposureNoRec;
		_dblCreditRiskyPrincipalPV = dblCreditRiskyPrincipalPV;
		_dblLossOnInstantaneousDefault = dblLossOnInstantaneousDefault;

		if (!(_bcmCreditRisklessClean = new org.drip.analytics.output.BondCouponMeasures
			(_bcmCreditRisklessDirty.dv01(), _bcmCreditRisklessDirty.indexCouponPV(),
				_bcmCreditRisklessDirty.couponPV(), _bcmCreditRisklessDirty.pv())).adjustForSettlement
					(dblCashPayDF))
			throw new java.lang.Exception
				("BondWorkoutMeasures ctr: Cannot successfully set up BCM CreditRisklessClean");

		if (!_bcmCreditRisklessClean.adjustForAccrual (_dblAccrued01, _dblFirstCouponRate, dblFirstIndexRate,
			false))
			throw new java.lang.Exception
				("BondWorkoutMeasures ctr: Cannot successfully set up BCM CreditRisklessClean");

		if (null != _bcmCreditRiskyDirty && ((!(_bcmCreditRiskyClean = new BondCouponMeasures
			(_bcmCreditRiskyDirty.dv01(), _bcmCreditRiskyDirty.indexCouponPV(),
				_bcmCreditRiskyDirty.couponPV(), _bcmCreditRiskyDirty.pv())).adjustForSettlement
					(dblCashPayDF)) || !_bcmCreditRiskyClean.adjustForAccrual (_dblAccrued01,
						_dblFirstCouponRate, _dblFirstCouponRate, false)))
			throw new java.lang.Exception
				("BondWorkoutMeasures ctr: Cannot successfully set up BCM CreditRiskyClean");
	}

	/**
	 * Retrieve the Credit Risky Clean Bond Coupon Measures
	 * 
	 * @return Credit Risky Clean Bond Coupon Measures
	 */

	public org.drip.analytics.output.BondCouponMeasures creditRiskyCleanbcm()
	{
		return _bcmCreditRiskyClean;
	}

	/**
	 * Retrieve the Credit Risk-less Clean Bond Coupon Measures
	 * 
	 * @return Credit Risk-less Clean Bond Coupon Measures
	 */

	public org.drip.analytics.output.BondCouponMeasures creditRisklessCleanbcm()
	{
		return _bcmCreditRisklessClean;
	}

	/**
	 * Retrieve the Credit Risky Dirty Bond Coupon Measures
	 * 
	 * @return Credit Risky Dirty Bond Coupon Measures
	 */

	public org.drip.analytics.output.BondCouponMeasures creditRiskyDirtybcm()
	{
		return _bcmCreditRiskyDirty;
	}

	/**
	 * Retrieve the Credit Risk-less Dirty Bond Coupon Measures
	 * 
	 * @return Credit Risk-less Dirty Bond Coupon Measures
	 */

	public org.drip.analytics.output.BondCouponMeasures creditRisklessDirtybcm()
	{
		return _bcmCreditRisklessDirty;
	}

	/**
	 * Retrieve the Accrued01
	 * 
	 * @return Accrued01
	 */

	public double accrued01()
	{
		return _dblAccrued01;
	}

	/**
	 * Retrieve the First Coupon Rate
	 * 
	 * @return First Coupon Rate
	 */

	public double firstCouponRate()
	{
		return _dblFirstCouponRate;
	}

	/**
	 * Retrieve the First Index Rate
	 * 
	 * @return First Index Rate
	 */

	public double firstIndexRate()
	{
		return _dblFirstIndexRate;
	}

	/**
	 * Retrieve the Credit Risky Par PV
	 * 
	 * @return The Credit Risky Par PV
	 */

	public double creditRiskyParPV()
	{
		return _dblCreditRiskyParPV;
	}

	/**
	 * Retrieve the Credit Risk-less Par PV
	 * 
	 * @return The Credit Risk-less Par PV
	 */

	public double creditRisklessParPV()
	{
		return _dblCreditRisklessParPV;
	}

	/**
	 * Retrieve the Credit Risky Principal PV
	 * 
	 * @return The Credit Risky Principal PV
	 */

	public double creditRiskyPrincipalPV()
	{
		return _dblCreditRiskyPrincipalPV;
	}

	/**
	 * Retrieve the Credit Risk-less Principal PV
	 * 
	 * @return The Credit Risk-less Principal PV
	 */

	public double creditRisklessPrincipalPV()
	{
		return _dblCreditRisklessPrincipalPV;
	}

	/**
	 * Retrieve the Recovery PV
	 * 
	 * @return The Recovery PV
	 */

	public double recoveryPV()
	{
		return _dblRecoveryPV;
	}

	/**
	 * Retrieve the Expected Recovery
	 * 
	 * @return The Expected Recovery
	 */

	public double expectedRecovery()
	{
		return _dblExpectedRecovery;
	}

	/**
	 * Retrieve Default Exposure - Same as PV on instantaneous default
	 * 
	 * @return The Default Exposure
	 */

	public double defaultExposure()
	{
		return _dblDefaultExposure;
	}

	/**
	 * Retrieve the Default Exposure without recovery - Same as PV on instantaneous default without recovery
	 * 
	 * @return The Default Exposure without recovery
	 */

	public double defaultExposureNoRec()
	{
		return _dblDefaultExposureNoRec;
	}

	/**
	 * Retrieve the Loss On Instantaneous Default
	 * 
	 * @return Loss On Instantaneous Default
	 */

	public double lossOnInstantaneousDefault()
	{
		return _dblLossOnInstantaneousDefault;
	}

	/**
	 * Return the state as a measure map
	 * 
	 * @param strPrefix Measure name prefix
	 * 
	 * @return Map of the measures
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> toMap (
		final java.lang.String strPrefix)
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapMeasures = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		mapMeasures.put (strPrefix + "Accrued", _dblAccrued01 * _dblFirstCouponRate);

		mapMeasures.put (strPrefix + "Accrued01", _dblAccrued01);

		mapMeasures.put (strPrefix + "CleanCouponPV", _bcmCreditRisklessClean.couponPV());

		mapMeasures.put (strPrefix + "CleanDV01", _bcmCreditRisklessClean.dv01());

		mapMeasures.put (strPrefix + "CleanIndexCouponPV", _bcmCreditRisklessClean.indexCouponPV());

		mapMeasures.put (strPrefix + "CleanPrice", _bcmCreditRisklessClean.pv());

		mapMeasures.put (strPrefix + "CleanPV", _bcmCreditRisklessClean.pv());

		mapMeasures.put (strPrefix + "CreditRisklessParPV", _dblCreditRisklessParPV);

		mapMeasures.put (strPrefix + "CreditRisklessPrincipalPV", _dblCreditRisklessPrincipalPV);

		mapMeasures.put (strPrefix + "CreditRiskyParPV", _dblCreditRiskyParPV);

		mapMeasures.put (strPrefix + "CreditRiskyPrincipalPV", _dblCreditRiskyPrincipalPV);

		mapMeasures.put (strPrefix + "DefaultExposure", _dblDefaultExposure);

		mapMeasures.put (strPrefix + "DefaultExposureNoRec", _dblDefaultExposureNoRec);

		mapMeasures.put (strPrefix + "DirtyCouponPV", _bcmCreditRisklessDirty.couponPV());

		mapMeasures.put (strPrefix + "DirtyDV01", _bcmCreditRisklessDirty.dv01());

		mapMeasures.put (strPrefix + "DirtyIndexCouponPV", _bcmCreditRisklessDirty.indexCouponPV());

		mapMeasures.put (strPrefix + "DirtyPrice", _bcmCreditRisklessDirty.pv());

		mapMeasures.put (strPrefix + "DirtyPV", _bcmCreditRisklessDirty.pv());

		mapMeasures.put (strPrefix + "DV01", _bcmCreditRisklessClean.dv01());

		mapMeasures.put (strPrefix + "ExpectedRecovery", _dblExpectedRecovery);

		mapMeasures.put (strPrefix + "FirstCouponRate", _dblFirstCouponRate);

		mapMeasures.put (strPrefix + "FirstIndexRate", _dblFirstIndexRate);

		mapMeasures.put (strPrefix + "LossOnInstantaneousDefault", _dblLossOnInstantaneousDefault);

		mapMeasures.put (strPrefix + "ParPV", _dblCreditRisklessParPV);

		mapMeasures.put (strPrefix + "PrincipalPV", _dblCreditRisklessPrincipalPV);

		mapMeasures.put (strPrefix + "PV", _bcmCreditRisklessClean.pv());

		mapMeasures.put (strPrefix + "RecoveryPV", _dblRecoveryPV);

		org.drip.quant.common.CollectionUtil.MergeWithMain (mapMeasures, _bcmCreditRisklessDirty.toMap
			(strPrefix + "RisklessDirty"));

		org.drip.quant.common.CollectionUtil.MergeWithMain (mapMeasures, _bcmCreditRisklessClean.toMap
			(strPrefix + "RisklessClean"));

		if (null != _bcmCreditRiskyDirty) {
			mapMeasures.put (strPrefix + "CleanCouponPV", _bcmCreditRiskyClean.couponPV());

			mapMeasures.put (strPrefix + "CleanDV01", _bcmCreditRiskyClean.dv01());

			mapMeasures.put (strPrefix + "CleanIndexCouponPV", _bcmCreditRiskyClean.indexCouponPV());

			mapMeasures.put (strPrefix + "CleanPrice", _bcmCreditRiskyClean.pv());

			mapMeasures.put (strPrefix + "CleanPV", _bcmCreditRiskyClean.pv());

			mapMeasures.put (strPrefix + "DirtyCouponPV", _bcmCreditRiskyDirty.couponPV());

			mapMeasures.put (strPrefix + "DirtyDV01", _bcmCreditRiskyDirty.dv01());

			mapMeasures.put (strPrefix + "DirtyIndexCouponPV", _bcmCreditRiskyDirty.indexCouponPV());

			mapMeasures.put (strPrefix + "DirtyPrice", _bcmCreditRiskyDirty.pv());

			mapMeasures.put (strPrefix + "DirtyPV", _bcmCreditRiskyDirty.pv());

			mapMeasures.put (strPrefix + "DV01", _bcmCreditRiskyClean.dv01());

			mapMeasures.put (strPrefix + "ParPV", _dblCreditRiskyParPV);

			mapMeasures.put (strPrefix + "PrincipalPV", _dblCreditRiskyPrincipalPV);

			mapMeasures.put (strPrefix + "PV", _bcmCreditRiskyClean.pv());

			org.drip.quant.common.CollectionUtil.MergeWithMain (mapMeasures, _bcmCreditRiskyDirty.toMap
				(strPrefix + "RiskyDirty"));

			org.drip.quant.common.CollectionUtil.MergeWithMain (mapMeasures, _bcmCreditRiskyClean.toMap
				(strPrefix + "RiskyClean"));
		}

		return mapMeasures;
	}
}
