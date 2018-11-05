
package org.drip.analytics.input;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>BootCurveConstructionInput</i> contains the Parameters needed for the Curve Calibration/Estimation. It
 * 	contains the following:
 * 	<ul>
 *  	<li>Calibration Valuation Parameters</li>
 *  	<li>Calibration Quoting Parameters</li>
 *  	<li>Array of Calibration Instruments</li>
 *  	<li>Map of Calibration Quotes</li>
 *  	<li>Map of Calibration Measures</li>
 *  	<li>Latent State Fixings Container</li>
 * 	</ul>
 *  <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics">Analytics</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/input">Input</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income</a></li>
 *  </ul>
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
