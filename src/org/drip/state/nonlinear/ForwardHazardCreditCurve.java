
package org.drip.state.nonlinear;

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
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * ForwardHazardCreditCurve manages the Survival Latent State, using the Hazard Rate as the State Response
 *  Representation. It exports the following functionality:
 *  - Boot Methods - Set/Bump Specific Node Quantification Metric, or Set Flat Value
 *  - Boot Calibration - Initialize Run, Compute Calibration Metric
 *  - Compute the survival probability, recovery rate, or the hazard rate from the Hazard Rate Latent State
 *  - Retrieve Array of the Calibration Components
 *  - Retrieve the Curve Construction Input Set
 *  - Synthesize scenario Latent State by parallel shifting/custom tweaking the quantification metric
 *  - Synthesize scenario Latent State by parallel/custom shifting/custom tweaking the manifest measure
 *
 * @author Lakshmi Krishnamurthy
 */

public class ForwardHazardCreditCurve extends org.drip.state.credit.ExplicitBootCreditCurve {
	private int[] _aiHazardDate = null;
	private int[] _aiRecoveryDate = null;
	private double[] _adblHazardRate = null;
	private double[] _adblRecoveryRate = null;

	private org.drip.state.credit.CreditCurve createFromBaseMMTP (
		final org.drip.param.definition.ManifestMeasureTweak mmtp)
	{
		double[] adblHazardBumped = org.drip.analytics.support.Helper.TweakManifestMeasure
			(_adblHazardRate, mmtp);

		if (null == adblHazardBumped || _adblHazardRate.length != adblHazardBumped.length) return null;

		try {
			return new ForwardHazardCreditCurve (_iEpochDate, _label, _strCurrency, adblHazardBumped,
				_aiHazardDate, _adblRecoveryRate, _aiRecoveryDate, _iSpecificDefaultDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a credit curve from hazard rate and recovery rate term structures
	 * 
	 * @param iStartDate Curve Epoch date
	 * @param label Credit Curve Label
	 * @param strCurrency Currency
	 * @param adblHazardRate Matched array of hazard rates
	 * @param aiHazardDate Matched array of hazard dates
	 * @param adblRecoveryRate Matched array of recovery rates
	 * @param aiRecoveryDate Matched array of recovery dates
	 * @param iSpecificDefaultDate (Optional) Specific Default Date
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public ForwardHazardCreditCurve (
		final int iStartDate,
		final org.drip.state.identifier.CreditLabel label,
		final java.lang.String strCurrency,
		final double adblHazardRate[],
		final int aiHazardDate[],
		final double[] adblRecoveryRate,
		final int[] aiRecoveryDate,
		final int iSpecificDefaultDate)
		throws java.lang.Exception
	{
		super (iStartDate, label, strCurrency);

		if (null == adblHazardRate || 0 == adblHazardRate.length || null == aiHazardDate || 0 ==
			aiHazardDate.length || adblHazardRate.length != aiHazardDate.length || null ==
				adblRecoveryRate || 0 == adblRecoveryRate.length || null == aiRecoveryDate || 0 ==
					aiRecoveryDate.length || adblRecoveryRate.length != aiRecoveryDate.length)
			throw new java.lang.Exception ("ForwardHazardCreditCurve ctr: Invalid Params!");

		_iSpecificDefaultDate = iSpecificDefaultDate;
		_adblHazardRate = new double[adblHazardRate.length];
		_adblRecoveryRate = new double[adblRecoveryRate.length];
		_aiHazardDate = new int[aiHazardDate.length];
		_aiRecoveryDate = new int[aiRecoveryDate.length];

		for (int i = 0; i < adblHazardRate.length; ++i)
			_adblHazardRate[i] = adblHazardRate[i];

		for (int i = 0; i < _aiHazardDate.length; ++i)
			_aiHazardDate[i] = aiHazardDate[i];

		for (int i = 0; i < adblRecoveryRate.length; ++i)
			_adblRecoveryRate[i] = adblRecoveryRate[i];

		for (int i = 0; i < aiRecoveryDate.length; ++i)
			_aiRecoveryDate[i] = aiRecoveryDate[i];
	}

	@Override public double survival (
		final int iDate)
		throws java.lang.Exception
	{
		if (iDate <= _iEpochDate) return 1.;

		if (java.lang.Integer.MIN_VALUE != _iSpecificDefaultDate && iDate >= _iSpecificDefaultDate)
			return 0.;

		int i = 0;
		double dblExpArg = 0.;
		int iStartDate = _iEpochDate;

		while (i < _adblHazardRate.length && iDate > _aiHazardDate[i]) {
			dblExpArg -= _adblHazardRate[i] * (_aiHazardDate[i] - iStartDate);
			iStartDate = _aiHazardDate[i++];
		}

		if (i >= _adblHazardRate.length) i = _adblHazardRate.length - 1;

		dblExpArg -= _adblHazardRate[i] * (iDate - iStartDate);

		return java.lang.Math.exp (dblExpArg / 365.25);
	}

	@Override public double recovery (
		final int iDate)
		throws java.lang.Exception
	{
		for (int i = 0; i < _aiRecoveryDate.length; ++i) {
			if (iDate < _aiRecoveryDate[i]) return _adblRecoveryRate[i];
		}

		return _adblRecoveryRate[_aiRecoveryDate.length - 1];
	}

	@Override public ForwardHazardCreditCurve parallelShiftQuantificationMetric (
		final double dblShift)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblShift)) return null;

		double[] adblHazard = new double[_adblHazardRate.length];

		for (int i = 0; i < _adblHazardRate.length; ++i)
			adblHazard[i] = _adblHazardRate[i] + dblShift;

		try {
			return new ForwardHazardCreditCurve (_iEpochDate, _label, _strCurrency, adblHazard,
				_aiHazardDate, _adblRecoveryRate, _aiRecoveryDate, _iSpecificDefaultDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.analytics.definition.Curve customTweakQuantificationMetric (
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		return null;
	}

	@Override public ForwardHazardCreditCurve parallelShiftManifestMeasure (
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblShift)) return null;

		if (null == _valParam || null == _aCalibInst || 0 == _aCalibInst.length || null == _adblCalibQuote ||
			0 == _adblCalibQuote.length || null == _astrCalibMeasure || 0 == _astrCalibMeasure.length ||
				_astrCalibMeasure.length != _adblCalibQuote.length || _adblCalibQuote.length !=
					_aCalibInst.length)
			return parallelShiftQuantificationMetric (dblShift);

		ForwardHazardCreditCurve cc = null;
		double[] adblCalibQuote = new double[_adblCalibQuote.length];

		try {
			cc = new ForwardHazardCreditCurve (_iEpochDate, _label, _strCurrency, _adblHazardRate,
				_aiHazardDate, _adblRecoveryRate, _aiRecoveryDate, _iSpecificDefaultDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i < _adblCalibQuote.length; ++i) {
			try {
				org.drip.state.nonlinear.NonlinearCurveBuilder.CreditCurve (_valParam, _aCalibInst[i],
					adblCalibQuote[i] = _adblCalibQuote[i] + dblShift, _astrCalibMeasure[i], _bFlat, i, cc,
						_dc, _gc, _pricerParam, _lsfc, _quotingParams);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		cc.setInstrCalibInputs (_valParam, _bFlat, _dc, _gc, _pricerParam, _aCalibInst, adblCalibQuote,
			_astrCalibMeasure, _lsfc, _quotingParams);

		return cc;
	}

	@Override public ForwardHazardCreditCurve shiftManifestMeasure (
		final int iSpanIndex,
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblShift)) return null;

		if (null == _valParam || null == _aCalibInst || 0 == _aCalibInst.length || null == _adblCalibQuote ||
			0 == _adblCalibQuote.length || null == _astrCalibMeasure || 0 == _astrCalibMeasure.length ||
				_astrCalibMeasure.length != _adblCalibQuote.length || _adblCalibQuote.length !=
					_aCalibInst.length)
			return parallelShiftQuantificationMetric (dblShift);

		ForwardHazardCreditCurve cc = null;
		double[] adblCalibQuote = new double[_adblCalibQuote.length];

		if (iSpanIndex >= _adblCalibQuote.length) return null;

		try {
			cc = new ForwardHazardCreditCurve (_iEpochDate, _label, _strCurrency, _adblHazardRate,
				_aiHazardDate, _adblRecoveryRate, _aiRecoveryDate, _iSpecificDefaultDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i < _adblCalibQuote.length; ++i) {
			try {
				org.drip.state.nonlinear.NonlinearCurveBuilder.CreditCurve (_valParam, _aCalibInst[i],
					adblCalibQuote[i] = _adblCalibQuote[i] + (i == iSpanIndex ? dblShift : 0.),
						_astrCalibMeasure[i], _bFlat, i, cc, _dc, _gc, _pricerParam, _lsfc,
							_quotingParams);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		cc.setInstrCalibInputs (_valParam, _bFlat, _dc, _gc, _pricerParam, _aCalibInst, adblCalibQuote,
			_astrCalibMeasure, _lsfc, _quotingParams);

		return cc;
	}

	@Override public org.drip.state.credit.CreditCurve flatCurve (
		final double dblFlatNodeValue,
		final boolean bSingleNode,
		final double dblRecovery)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblFlatNodeValue) || 0. >= dblFlatNodeValue || null ==
			_valParam || null == _aCalibInst || 0 == _aCalibInst.length || null == _adblCalibQuote || 0 ==
				_adblCalibQuote.length || null == _astrCalibMeasure || 0 == _astrCalibMeasure.length ||
					_astrCalibMeasure.length != _adblCalibQuote.length || _adblCalibQuote.length !=
						_aCalibInst.length)
			return null;

		org.drip.state.credit.ExplicitBootCreditCurve cc = null;

		try {
			if (bSingleNode)
				cc = org.drip.state.creator.ScenarioCreditCurveBuilder.Hazard (_iEpochDate,
					_label.fullyQualifiedName(), _strCurrency, _adblHazardRate[0], _aiHazardDate[0],
						!org.drip.quant.common.NumberUtil.IsValid (dblRecovery) ? _adblRecoveryRate[0] :
							dblRecovery);
			else
				cc = new ForwardHazardCreditCurve (_iEpochDate, _label, _strCurrency, _adblHazardRate,
					_aiHazardDate, _adblRecoveryRate, _aiRecoveryDate, _iSpecificDefaultDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i < _adblCalibQuote.length; ++i) {
			try {
				org.drip.state.nonlinear.NonlinearCurveBuilder.CreditCurve (_valParam, _aCalibInst[i],
					dblFlatNodeValue, _astrCalibMeasure[i], true, i, cc, _dc, _gc, _pricerParam, _lsfc,
						_quotingParams);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		if (bSingleNode)
			cc.setInstrCalibInputs (_valParam, true, _dc, _gc, _pricerParam, new
				org.drip.product.definition.CalibratableComponent[] {_aCalibInst[0]}, new double[]
					{dblFlatNodeValue}, _astrCalibMeasure, _lsfc, _quotingParams);
		else {
			double[] adblCalibValue = new double[_adblCalibQuote.length];

			for (int i = 0; i < _adblCalibQuote.length; ++i)
				adblCalibValue[i] = dblFlatNodeValue;

			cc.setInstrCalibInputs (_valParam, true, _dc, _gc, _pricerParam, _aCalibInst, adblCalibValue,
				_astrCalibMeasure, _lsfc, _quotingParams);
		}

		return cc;
	}

	@Override  public org.drip.state.credit.CreditCurve customTweakManifestMeasure (
		final java.lang.String strManifestMeasure,
		final org.drip.param.definition.ManifestMeasureTweak mmtp)
	{
		if (null == mmtp) return null;

		if (!(mmtp instanceof org.drip.param.definition.CreditManifestMeasureTweak))
			return createFromBaseMMTP (mmtp);

		org.drip.param.definition.CreditManifestMeasureTweak cmmt =
			(org.drip.param.definition.CreditManifestMeasureTweak) mmtp;

		if (org.drip.param.definition.CreditManifestMeasureTweak.CREDIT_TWEAK_NODE_PARAM_RECOVERY.equalsIgnoreCase
			(cmmt.paramType())) {
			double[] adblRecoveryRateBumped = null;

			if (null == (adblRecoveryRateBumped =
				org.drip.analytics.support.Helper.TweakManifestMeasure (_adblRecoveryRate, cmmt)) ||
					adblRecoveryRateBumped.length != _adblRecoveryRate.length)
				return null;

			try {
				return new ForwardHazardCreditCurve (_iEpochDate, _label, _strCurrency, _adblHazardRate,
					_aiHazardDate, adblRecoveryRateBumped, _aiRecoveryDate, _iSpecificDefaultDate);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		} else if
			(org.drip.param.definition.CreditManifestMeasureTweak.CREDIT_TWEAK_NODE_PARAM_QUOTE.equalsIgnoreCase
				(cmmt.paramType())) {
			if (org.drip.param.definition.CreditManifestMeasureTweak.CREDIT_TWEAK_NODE_MEASURE_HAZARD.equalsIgnoreCase
				(cmmt.measureType())) {
				double[] adblHazardBumped = null;

				if (null == (adblHazardBumped =
					org.drip.analytics.support.Helper.TweakManifestMeasure (_adblHazardRate, cmmt))
						|| adblHazardBumped.length != _adblHazardRate.length)
					return null;

				try {
					return new ForwardHazardCreditCurve (_iEpochDate, _label, _strCurrency,
						adblHazardBumped, _aiHazardDate, _adblRecoveryRate, _aiRecoveryDate,
							_iSpecificDefaultDate);
				} catch (java.lang.Exception e) {
					e.printStackTrace();
				}
			} else if
				(org.drip.param.definition.CreditManifestMeasureTweak.CREDIT_TWEAK_NODE_MEASURE_QUOTE.equalsIgnoreCase
					(cmmt.measureType())) {
				double[] adblQuoteBumped = null;

				if (null == (adblQuoteBumped =
					org.drip.analytics.support.Helper.TweakManifestMeasure (_adblHazardRate, cmmt))
						|| adblQuoteBumped.length != _adblHazardRate.length)
					return null;

				org.drip.state.credit.ExplicitBootCreditCurve cc = null;

				try {
					if (cmmt.singleNodeCalib())
						cc = org.drip.state.creator.ScenarioCreditCurveBuilder.Hazard (_iEpochDate,
							_strCurrency, _label.fullyQualifiedName(), _adblHazardRate[0],
								_aiHazardDate[0], _adblRecoveryRate[0]);
					else
						cc = new ForwardHazardCreditCurve (_iEpochDate, _label, _strCurrency,
							_adblHazardRate, _aiHazardDate, _adblRecoveryRate, _aiRecoveryDate,
								_iSpecificDefaultDate);
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}

				for (int i = 0; i < adblQuoteBumped.length; ++i) {
					try {
						org.drip.state.nonlinear.NonlinearCurveBuilder.CreditCurve (_valParam,
							_aCalibInst[i], adblQuoteBumped[i], _astrCalibMeasure[i], _bFlat, i, cc, _dc,
								_gc, _pricerParam, _lsfc, _quotingParams);
					} catch (java.lang.Exception e) {
						e.printStackTrace();

						return null;
					}
				}

				cc.setInstrCalibInputs (_valParam, _bFlat, _dc, _gc, _pricerParam, _aCalibInst,
					adblQuoteBumped, _astrCalibMeasure, _lsfc, _quotingParams);

				return cc;
			}
		}

		return null;
	}

	@Override public boolean setNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblValue) || iNodeIndex > _adblHazardRate.length)
			return false;

		for (int i = iNodeIndex; i < _adblHazardRate.length; ++i)
			_adblHazardRate[i] = dblValue;

		return true;
	}

	@Override public boolean bumpNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblValue) || iNodeIndex > _adblHazardRate.length)
			return false;

		for (int i = iNodeIndex; i < _adblHazardRate.length; ++i)
			_adblHazardRate[i] += dblValue;

		return true;
	}

	@Override public boolean setFlatValue (
		final double dblValue)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblValue)) return false;

		for (int i = 0; i < _adblHazardRate.length; ++i)
			_adblHazardRate[i] = dblValue;

		return true;
	}
}
