
package org.drip.param.market;

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
 * DiscountCurveScenarioContainer implements the RatesScenarioCurve abstract class that exposes the interface
 *  the constructs scenario discount curves. The following curve construction scenarios are supported:
 *  - Base, flat/tenor up/down by arbitrary bumps
 *  - Tenor bumped discount curve set - keyed using the tenor.
 *	- NTP-based custom scenario curves.
 *
 * @author Lakshmi Krishnamurthy
 */

public class DiscountCurveScenarioContainer {

	/**
	 * Base Discount Curve
	 */

	public static final int DC_BASE = 0;

	/**
	 * Discount Curve Parallel Bump Up
	 */

	public static final int DC_FLAT_UP = 1;

	/**
	 * Discount Curve Parallel Bump Down
	 */

	public static final int DC_FLAT_DN = 2;

	/**
	 * Discount Curve Tenor Bump Up
	 */

	public static final int DC_TENOR_UP = 4;

	/**
	 * Discount Curve Tenor Bump Down
	 */

	public static final int DC_TENOR_DN = 8;

	private org.drip.state.discount.MergedDiscountForwardCurve _dcBase = null;
	private org.drip.state.discount.MergedDiscountForwardCurve _dcBumpUp = null;
	private org.drip.state.discount.MergedDiscountForwardCurve _dcBumpDn = null;
	private org.drip.product.definition.CalibratableComponent[] _aCalibInst = null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
		_mapDCCustom = null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
		_mapDCBumpUp = null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
		_mapDCBumpDn = null;

	/**
	 * Constructs an DiscountCurveScenarioContainer instance from the corresponding
	 * 	DiscountCurveScenarioGenerator
	 * 
	 * @param aCalibInst Array of calibration instruments
	 * 
	 * @throws java.lang.Exception Thrown if the IRCurveScenarioGenerator instance is invalid
	 */

	public DiscountCurveScenarioContainer (
		final org.drip.product.definition.CalibratableComponent[] aCalibInst)
		throws java.lang.Exception
	{
		if (null == (_aCalibInst = aCalibInst) || 0 == _aCalibInst.length)
			throw new java.lang.Exception ("DiscountCurveScenarioContainer ctr => Invalid Inputs");
	}

	/**
	 * Generate the set of discount curves from the scenario specified, and the instrument quotes
	 * 
	 * @param valParams Valuation Parameters
	 * @param gc The Govvie Curve
	 * @param adblCalibQuote Matched array of the calibration instrument quotes
	 * @param astrCalibMeasure Matched array of the calibration instrument measures
	 * @param dblBump Amount of bump to be applied
	 * @param lsfc Latent State Fixings Container
	 * @param vcp Valuation Customization Parameters
	 * @param iDCMode One of the values in the DC_ enum listed above.
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean cookScenarioDC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.state.govvie.GovvieCurve gc,
		final double[] adblCalibQuote,
		final java.lang.String[] astrCalibMeasure,
		final double dblBump,
		final org.drip.param.market.LatentStateFixingsContainer lsfc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iDCMode)
	{
		if (null == (_dcBase = org.drip.state.boot.DiscountCurveScenario.Standard (valParams, _aCalibInst,
			adblCalibQuote, astrCalibMeasure, 0., gc, lsfc, vcp)))
			return false;

		if (0 != (org.drip.param.market.DiscountCurveScenarioContainer.DC_FLAT_UP & iDCMode)) {
			if (null == (_dcBumpUp = org.drip.state.boot.DiscountCurveScenario.Standard (valParams,
				_aCalibInst, adblCalibQuote, astrCalibMeasure, dblBump, gc, lsfc, vcp)))
				return false;
		}

		if (0 != (org.drip.param.market.DiscountCurveScenarioContainer.DC_FLAT_DN & iDCMode)) {
			if (null == (_dcBumpDn = org.drip.state.boot.DiscountCurveScenario.Standard (valParams,
				_aCalibInst, adblCalibQuote, astrCalibMeasure, -dblBump, gc, lsfc, vcp)))
				return false;
		}

		if (0 != (org.drip.param.market.DiscountCurveScenarioContainer.DC_TENOR_UP & iDCMode)) {
			if (null == (_mapDCBumpUp = org.drip.state.boot.DiscountCurveScenario.TenorMap (valParams,
				_aCalibInst, adblCalibQuote, astrCalibMeasure, dblBump, gc, lsfc, vcp)))
				return false;
		}

		if (0 != (org.drip.param.market.DiscountCurveScenarioContainer.DC_TENOR_DN & iDCMode)) {
			if (null == (_mapDCBumpDn = org.drip.state.boot.DiscountCurveScenario.TenorMap (valParams,
				_aCalibInst, adblCalibQuote, astrCalibMeasure, -dblBump, gc, lsfc, vcp)))
				return false;
		}

		return true;
	}

	/**
	 * Return the base Discount Curve
	 * 
	 * @return The base Discount Curve
	 */

	public org.drip.state.discount.MergedDiscountForwardCurve base()
	{
		return _dcBase;
	}

	/**
	 * Return the Bump Up Discount Curve
	 * 
	 * @return The Bump Up Discount Curve
	 */

	public org.drip.state.discount.MergedDiscountForwardCurve bumpUp()
	{
		return _dcBumpUp;
	}

	/**
	 * Return the Bump Down Discount Curve
	 * 
	 * @return The Bump Down Discount Curve
	 */

	public org.drip.state.discount.MergedDiscountForwardCurve bumpDown()
	{
		return _dcBumpDn;
	}

	/**
	 * Return the map of the tenor Bump Up Discount Curve
	 * 
	 * @return The map of the tenor Bump Up Discount Curve
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
		tenorBumpUp()
	{
		return _mapDCBumpUp;
	}

	/**
	 * Return the map of the tenor Bump Down Discount Curve
	 * 
	 * @return The map of the tenor Bump Down Discount Curve
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve>
		tenorBumpDown()
	{
		return _mapDCBumpDn;
	}

	/**
	 * Return the Custom Discount curve map
	 * 
	 * @return The Custom Discount curve Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.discount.MergedDiscountForwardCurve> custom()
	{
		return _mapDCCustom;
	}
}
