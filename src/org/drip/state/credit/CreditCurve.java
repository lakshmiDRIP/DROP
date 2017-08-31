
package org.drip.state.credit;

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
 * CreditCurve is the stub for the survival curve functionality. It extends the Curve object by exposing the
 * 	following functions:
 * 	- Set of curve and market identifiers
 * 	- Recovery to a specific date/tenor, and effective recovery between a date interval
 * 	- Hazard Rate to a specific date/tenor, and effective hazard rate between a date interval
 * 	- Survival to a specific date/tenor, and effective survival between a date interval
 *  - Set/unset date of specific default
 *  - Generate scenario curves from the base credit curve (flat/parallel/custom)
 *  - Set/unset the Curve Construction Inputs, Latent State, and the Manifest Metrics
 *  - Serialization/De-serialization to and from Byte Arrays
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class CreditCurve implements org.drip.analytics.definition.Curve {
	private static final int NUM_DF_QUADRATURES = 5;

	protected java.lang.String _strCurrency = "";
	protected int _iEpochDate = java.lang.Integer.MIN_VALUE;
	protected org.drip.state.identifier.CreditLabel _label = null;
	protected int _iSpecificDefaultDate = java.lang.Integer.MIN_VALUE;

	/*
	 * Manifest Measure Inputs that go into building the Curve Span
	 */

	protected boolean _bFlat = false;
	protected double[] _adblCalibQuote = null;
	protected java.lang.String[] _astrCalibMeasure = null;
	protected org.drip.state.govvie.GovvieCurve _gc = null;
	protected org.drip.state.discount.MergedDiscountForwardCurve _dc = null;
	protected org.drip.param.valuation.ValuationParams _valParam = null;
	protected org.drip.param.pricer.CreditPricerParams _pricerParam = null;
	protected org.drip.param.market.LatentStateFixingsContainer _lsfc = null;
	protected org.drip.product.definition.CalibratableComponent[] _aCalibInst = null;
	protected org.drip.param.valuation.ValuationCustomizationParams _quotingParams = null;
	protected org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> _mapMeasure = null;
	protected org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
		_mapQuote = null;

	protected CreditCurve (
		final int iEpochDate,
		final org.drip.state.identifier.CreditLabel label,
		final java.lang.String strCurrency)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_iEpochDate = iEpochDate) || null == (_label =
			label) || null == (_strCurrency = strCurrency) || _strCurrency.isEmpty())
			throw new java.lang.Exception ("CreditCurve ctr: Invalid Inputs");
	}

	@Override public org.drip.state.identifier.LatentStateLabel label()
	{
		return _label;
	}

	@Override public java.lang.String currency()
	{
		return _strCurrency;
	}

	@Override public org.drip.analytics.date.JulianDate epoch()
	{
		try {
			return new org.drip.analytics.date.JulianDate (_iEpochDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Set the Specific Default Date
	 * 
	 * @param iSpecificDefaultDate Date of Specific Default
	 * 
	 * @return TRUE if successful
	 */

	public boolean setSpecificDefault (
		final int iSpecificDefaultDate)
	{
		_iSpecificDefaultDate = iSpecificDefaultDate;
		return true;
	}

	/**
	 * Remove the Specific Default Date
	 * 
	 * @return TRUE if successful
	 */

	public boolean unsetSpecificDefault()
	{
		_iSpecificDefaultDate = java.lang.Integer.MIN_VALUE;
		return true;
	}

	/**
	 * Calculate the survival to the given date
	 * 
	 * @param iDate Date
	 * 
	 * @return Survival Probability
	 * 
	 * @throws java.lang.Exception Thrown if the survival probability cannot be calculated
	 */

	public abstract double survival (
		final int iDate)
		throws java.lang.Exception;

	/**
	 * Calculate the survival to the given date
	 * 
	 * @param dt Date
	 * 
	 * @return Survival Probability
	 * 
	 * @throws java.lang.Exception Thrown if the survival probability cannot be calculated
	 */

	public double survival (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("CreditCurve::survival => Invalid Date");

		return survival (dt.julian());
	}

	/**
	 * Calculate the survival to the given tenor
	 * 
	 * @param strTenor Tenor
	 * 
	 * @return Survival Probability
	 * 
	 * @throws java.lang.Exception Thrown if the survival probability cannot be calculated
	 */

	public double survival (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("CreditCurve::survival => Bad tenor");

		return survival (new org.drip.analytics.date.JulianDate (_iEpochDate).addTenor (strTenor));
	}

	/**
	 * Calculate the time-weighted survival between a pair of 2 dates
	 * 
	 * @param iDate1 First Date
	 * @param iDate2 Second Date
	 * 
	 * @return Survival Probability
	 * 
	 * @throws java.lang.Exception Thrown if the survival probability cannot be calculated
	 */

	public double effectiveSurvival (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		if (iDate1 == iDate2) return survival (iDate1);

		int iNumQuadratures = 0;
		double dblEffectiveSurvival = 0.;
		int iQuadratureWidth = (iDate2 - iDate1) / NUM_DF_QUADRATURES;

		for (int iDate = iDate1; iDate <= iDate2; iDate += iQuadratureWidth) {
			++iNumQuadratures;

			dblEffectiveSurvival += (survival (iDate) + survival (iDate + iQuadratureWidth));
		}

		return dblEffectiveSurvival / (2. * iNumQuadratures);
	}

	/**
	 * Calculate the time-weighted survival between a pair of 2 dates
	 * 
	 * @param dt1 First Date
	 * @param dt2 Second Date
	 * 
	 * @return Survival Probability
	 * 
	 * @throws java.lang.Exception Thrown if the survival probability cannot be calculated
	 */

	public double effectiveSurvival (
		final org.drip.analytics.date.JulianDate dt1,
		final org.drip.analytics.date.JulianDate dt2)
		throws java.lang.Exception
	{
		if (null == dt1 || null == dt2)
			throw new java.lang.Exception ("CreditCurve::effectiveSurvival => Invalid date");

		return effectiveSurvival (dt1.julian(), dt2.julian());
	}

	/**
	 * Calculate the time-weighted survival between a pair of 2 tenors
	 * 
	 * @param strTenor1 First tenor
	 * @param strTenor2 Second tenor
	 * 
	 * @return Survival Probability
	 * 
	 * @throws java.lang.Exception Thrown if the survival probability cannot be calculated
	 */

	public double effectiveSurvival (
		final java.lang.String strTenor1,
		final java.lang.String strTenor2)
		throws java.lang.Exception
	{
		if (null == strTenor1 || strTenor1.isEmpty() || null == strTenor2 || strTenor2.isEmpty())
			throw new java.lang.Exception ("CreditCurve::effectiveSurvival => bad tenor");

		return effectiveSurvival (new org.drip.analytics.date.JulianDate (_iEpochDate).addTenor
			(strTenor1), new org.drip.analytics.date.JulianDate (_iEpochDate).addTenor (strTenor2));
	}

	/**
	 * Calculate the recovery rate to the given date
	 * 
	 * @param iDate Date
	 * 
	 * @return Recovery Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Recovery rate cannot be calculated
	 */

	public abstract double recovery (
		final int iDate)
		throws java.lang.Exception;

	/**
	 * Calculate the recovery rate to the given date
	 * 
	 * @param dt Date
	 * 
	 * @return Recovery Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Recovery rate cannot be calculated
	 */

	public double recovery (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("CreditCurve::recovery => Invalid Date");

		return recovery (dt.julian());
	}

	/**
	 * Calculate the recovery rate to the given tenor
	 * 
	 * @param strTenor Tenor
	 * 
	 * @return Recovery Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Recovery rate cannot be calculated
	 */

	public double recovery (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("CreditCurve::recovery => Invalid Tenor");

		return recovery (new org.drip.analytics.date.JulianDate (_iEpochDate).addTenor (strTenor));
	}

	/**
	 * Calculate the time-weighted recovery between a pair of dates
	 * 
	 * @param iDate1 First Date
	 * @param iDate2 Second Date
	 * 
	 * @return Time-weighted recovery
	 * 
	 * @throws java.lang.Exception Thrown if the recovery cannot be calculated
	 */

	public double effectiveRecovery (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		if (iDate1 == iDate2) return recovery (iDate1);

		int iNumQuadratures = 0;
		double dblEffectiveRecovery = 0.;
		int iQuadratureWidth = (iDate2 - iDate1) / NUM_DF_QUADRATURES;

		if (0 == iQuadratureWidth) iQuadratureWidth = 1;

		for (int iDate = iDate1; iDate <= iDate2; iDate += iQuadratureWidth) {
			++iNumQuadratures;

			dblEffectiveRecovery += (recovery (iDate) + recovery (iDate + iQuadratureWidth));
		}

		return dblEffectiveRecovery / (2. * iNumQuadratures);
	}

	/**
	 * Calculate the time-weighted recovery between a pair of dates
	 * 
	 * @param dt1 First Date
	 * @param dt2 Second Date
	 * 
	 * @return Time-weighted recovery
	 * 
	 * @throws java.lang.Exception Thrown if the recovery cannot be calculated
	 */

	public double effectiveRecovery (
		final org.drip.analytics.date.JulianDate dt1,
		final org.drip.analytics.date.JulianDate dt2)
		throws java.lang.Exception
	{
		if (null == dt1 || null == dt2)
			throw new java.lang.Exception ("CreditCurve::effectiveRecovery => Invalid date");

		return effectiveRecovery (dt1.julian(), dt2.julian());
	}

	/**
	 * Calculate the time-weighted recovery between a pair of tenors
	 * 
	 * @param strTenor1 First Tenor
	 * @param strTenor2 Second Tenor
	 * 
	 * @return Time-weighted recovery
	 * 
	 * @throws java.lang.Exception Thrown if the recovery cannot be calculated
	 */

	public double effectiveRecovery (
		final java.lang.String strTenor1,
		final java.lang.String strTenor2)
		throws java.lang.Exception
	{
		if (null == strTenor1 || strTenor1.isEmpty() || null == strTenor2 || strTenor2.isEmpty())
			throw new java.lang.Exception ("CreditCurve::effectiveRecovery => Invalid tenor");

		return effectiveRecovery (new org.drip.analytics.date.JulianDate (_iEpochDate).addTenor
			(strTenor1), new org.drip.analytics.date.JulianDate (_iEpochDate).addTenor (strTenor2));
	}

	/**
	 * Calculate the hazard rate between a pair of forward dates
	 * 
	 * @param dt1 First Date
	 * @param dt2 Second Date
	 * 
	 * @return Hazard Rate
	 * 
	 * @throws java.lang.Exception Thrown if the hazard rate cannot be calculated
	 */

	public double hazard (
		final org.drip.analytics.date.JulianDate dt1,
		final org.drip.analytics.date.JulianDate dt2)
		throws java.lang.Exception
	{
		if (null == dt1 || null == dt2)
			throw new java.lang.Exception ("CreditCurve::hazard => Invalid dates");

		if (dt1.julian() < _iEpochDate || dt2.julian() < _iEpochDate) return 0.;

		return 365.25 / (dt2.julian() - dt1.julian()) * java.lang.Math.log (survival (dt1) / survival (dt2));
	}

	/**
	 * Calculate the hazard rate to the given date
	 * 
	 * @param dt Date
	 * 
	 * @return Hazard Rate
	 * 
	 * @throws java.lang.Exception Thrown if the hazard rate cannot be calculated
	 */

	public double hazard (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		return hazard (dt, new org.drip.analytics.date.JulianDate (_iEpochDate));
	}

	/**
	 * Calculate the hazard rate to the given tenor
	 * 
	 * @param strTenor Tenor
	 * 
	 * @return Hazard Rate
	 * 
	 * @throws java.lang.Exception Thrown if the hazard rate cannot be calculated
	 */

	public double hazard (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("CreditCurve::hazard => Bad Tenor");

		return hazard (new org.drip.analytics.date.JulianDate (_iEpochDate).addTenor (strTenor));
	}

	/**
	 * Create a flat hazard curve from the inputs
	 * 
	 * @param dblFlatNodeValue Flat hazard node value
	 * @param bSingleNode Uses a single node for Calibration (True)
	 * @param dblRecovery (Optional) Recovery to be used in creation of the flat curve
	 * 
	 * @return New CreditCurve instance
	 */

	public abstract CreditCurve flatCurve (
		final double dblFlatNodeValue,
		final boolean bSingleNode,
		final double dblRecovery);

	/**
	 * Set the calibration inputs for the CreditCurve
	 * 
	 * @param valParam ValuationParams
	 * @param bFlat Flat calibration desired (True)
	 * @param dc Base Discount Curve
	 * @param gc Govvie Curve
	 * @param pricerParam PricerParams
	 * @param aCalibInst Array of calibration instruments
	 * @param adblCalibQuote Array of calibration quotes
	 * @param astrCalibMeasure Array of calibration measures
	 * @param lsfc Latent State Fixings Container
	 * @param quotingParams Quoting Parameters
	 */

	public void setInstrCalibInputs (
		final org.drip.param.valuation.ValuationParams valParam,
		final boolean bFlat,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.govvie.GovvieCurve gc,
		final org.drip.param.pricer.CreditPricerParams pricerParam,
		final org.drip.product.definition.CalibratableComponent[] aCalibInst,
		final double[] adblCalibQuote,
		final java.lang.String[] astrCalibMeasure,
		final org.drip.param.market.LatentStateFixingsContainer lsfc,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
	{
		_dc = dc;
		_gc = gc;
		_lsfc = lsfc;
		_bFlat = bFlat;
		_valParam = valParam;
		_aCalibInst = aCalibInst;
		_pricerParam = pricerParam;
		_quotingParams = quotingParams;
		_adblCalibQuote = adblCalibQuote;
		_astrCalibMeasure = astrCalibMeasure;

		_mapQuote = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>();

		_mapMeasure = new org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String>();

		for (int i = 0; i < aCalibInst.length; ++i) {
			_mapMeasure.put (_aCalibInst[i].primaryCode(), astrCalibMeasure[i]);

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapManifestMeasureCalibQuote
				= new org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

			mapManifestMeasureCalibQuote.put (_astrCalibMeasure[i], adblCalibQuote[i]);

			_mapQuote.put (_aCalibInst[i].primaryCode(), mapManifestMeasureCalibQuote);

			java.lang.String[] astrSecCode = _aCalibInst[i].secondaryCode();

			if (null != astrSecCode) {
				for (int j = 0; j < astrSecCode.length; ++j)
					_mapQuote.put (astrSecCode[j], mapManifestMeasureCalibQuote);
			}
		}
	}

	@Override public boolean setCCIS (
		final org.drip.analytics.input.CurveConstructionInputSet ccis)
	{
		return false;
	}

	@Override public org.drip.product.definition.CalibratableComponent[] calibComp()
	{
		return _aCalibInst;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> manifestMeasure (
		final java.lang.String strInstr)
	{
		if (null == _mapQuote || 0 == _mapQuote.size() || null == strInstr || strInstr.isEmpty() ||
			!_mapQuote.containsKey (strInstr))
			return null;

		return _mapQuote.get (strInstr);
	}
}
