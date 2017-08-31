
package org.drip.analytics.input;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * BootCurveConstructionInput contains the Parameters needed for the Curve Calibration/Estimation. It
 * 	contains the following:
 *  - Calibration Valuation Parameters
 *  - Calibration Quoting Parameters
 *  - Array of Calibration Instruments
 *  - Map of Calibration Quotes
 *  - Map of Calibration Measures
 *  - Latent State Fixings Container
 *
 * @author Lakshmi Krishnamurthy
 */

public class BootCurveConstructionInput implements org.drip.analytics.input.CurveConstructionInputSet {
	private org.drip.param.valuation.ValuationParams _valParam = null;
	private org.drip.param.market.LatentStateFixingsContainer _lsfc = null;
	private org.drip.param.valuation.ValuationCustomizationParams _quotingParam = null;
	private org.drip.product.definition.CalibratableComponent[] _aCalibInst = null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String[]> _mapMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mapQuote = null;

	/**
	 * Create an Instance of BootCurveConstructionInput from the given Calibration Inputs
	 * 
	 * @param valParam Valuation Parameters
	 * @param quotingParam Quoting Parameters
	 * @param aCalibInst Array of the Calibration Instruments
	 * @param adblCalibQuote Array of the Calibration Quotes
	 * @param astrCalibMeasure Array of the Calibration Measures
	 * @param lsfc Latent State Fixings Container
	 * 
	 * @return Instance of BootCurveConstructionInput
	 */

	public static final BootCurveConstructionInput Create (
		final org.drip.param.valuation.ValuationParams valParam,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParam,
		final org.drip.product.definition.CalibratableComponent[] aCalibInst,
		final double[] adblCalibQuote,
		final java.lang.String[] astrCalibMeasure,
		final org.drip.param.market.LatentStateFixingsContainer lsfc)
	{
		if (null == aCalibInst || null == adblCalibQuote || null == astrCalibMeasure) return null;

		int iNumInst = aCalibInst.length;

		if (0 == iNumInst || adblCalibQuote.length != iNumInst || astrCalibMeasure.length != iNumInst)
			return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			mapQuote = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>();

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String[]> mapMeasures = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String[]>();

		for (int i = 0; i < iNumInst; ++i) {
			if (null == aCalibInst[i]) return null;

			java.lang.String strInstrumentCode = aCalibInst[i].primaryCode();

			if (null == strInstrumentCode || strInstrumentCode.isEmpty() || null == astrCalibMeasure[i] ||
				astrCalibMeasure[i].isEmpty() || !org.drip.quant.common.NumberUtil.IsValid
					(adblCalibQuote[i]))
				return null;

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCalibManifestMeasureQuote
				= new org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

			mapCalibManifestMeasureQuote.put (astrCalibMeasure[i], adblCalibQuote[i]);

			mapMeasures.put (strInstrumentCode, new java.lang.String[] {astrCalibMeasure[i]});

			mapQuote.put (strInstrumentCode, mapCalibManifestMeasureQuote);

			java.lang.String[] astrSecCode = aCalibInst[i].secondaryCode();

			if (null != astrSecCode) {
				int iNumSecCode = astrSecCode.length;

				for (int j = 0; j < iNumSecCode; ++j) {
					java.lang.String strSecCode = astrSecCode[j];

					if (null == strSecCode || strSecCode.isEmpty())
						mapQuote.put (strSecCode, mapCalibManifestMeasureQuote);
				}
			}
		}

		try {
			return new BootCurveConstructionInput (valParam, quotingParam, aCalibInst, mapQuote, mapMeasures,
				lsfc);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BootCurveConstructionInput constructor
	 * 
	 * @param valParam Valuation Parameter
	 * @param quotingParam Quoting Parameter
	 * @param aCalibInst Array of Calibration Instruments
	 * @param mapQuote Map of the Calibration Instrument Quotes
	 * @param mapMeasures Map containing the Array of the Calibration Instrument Measures
	 * @param lsfc Latent State Fixings Container
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public BootCurveConstructionInput (
		final org.drip.param.valuation.ValuationParams valParam,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParam,
		final org.drip.product.definition.CalibratableComponent[] aCalibInst,
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mapQuote,
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String[]> mapMeasures,
		final org.drip.param.market.LatentStateFixingsContainer lsfc)
		throws java.lang.Exception
	{
		if (null == (_valParam = valParam) || null == (_aCalibInst = aCalibInst) || null == (_mapQuote =
			mapQuote) || null == (_mapMeasures = mapMeasures))
			throw new java.lang.Exception ("BootCurveConstructionInput ctr: Invalid Inputs");

		_lsfc = lsfc;
		_quotingParam = quotingParam;
		int iNumInst = _aCalibInst.length;

		if (0 == iNumInst || iNumInst > _mapQuote.size() || iNumInst > _mapMeasures.size())
			throw new java.lang.Exception ("BootCurveConstructionInput ctr: Invalid Inputs");
	}

	@Override public org.drip.param.valuation.ValuationParams valuationParameter()
	{
		return _valParam;
	}

	@Override public org.drip.param.pricer.CreditPricerParams pricerParameter()
	{
		return null;
	}

	@Override public org.drip.param.market.CurveSurfaceQuoteContainer marketParameters()
	{
		return null;
	}

	@Override public org.drip.param.valuation.ValuationCustomizationParams quotingParameter()
	{
		return _quotingParam;
	}

	@Override public org.drip.product.definition.CalibratableComponent[] components()
	{
		return _aCalibInst;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			quoteMap()
	{
		return _mapQuote;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String[]> measures()
	{
		return _mapMeasures;
	}

	@Override public org.drip.param.market.LatentStateFixingsContainer fixing()
	{
		return _lsfc;
	}
}
