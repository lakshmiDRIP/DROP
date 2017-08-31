
package org.drip.param.market;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * LatentStateFixingsContainer holds the explicit fixings for a specified Latent State Quantification along
 * 	the date ordinate.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateFixingsContainer {
	private java.util.Map<org.drip.analytics.date.JulianDate,
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>> _mmForwardFixing = new
			java.util.TreeMap<org.drip.analytics.date.JulianDate,
				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>();

	private java.util.Map<org.drip.analytics.date.JulianDate,
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>> _mmFXFixing = new
			java.util.TreeMap<org.drip.analytics.date.JulianDate,
				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>();

	/**
	 * Empty LatentStateFixingsContainer Instance Constructor
	 */

	public LatentStateFixingsContainer()
	{
	}

	/**
	 * Add the Fixing corresponding to the Date/Label Pair
	 * 
	 * @param dt The Fixing Date
	 * @param lsl The Latent State Label
	 * @param dblFixing The Fixing Amount
	 * 
	 * @return TRUE - Entry successfully added
	 */

	public boolean add (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.state.identifier.LatentStateLabel lsl,
		final double dblFixing)
	{
		if (null == dt || null == lsl || !org.drip.quant.common.NumberUtil.IsValid (dblFixing)) return false;

		if (lsl instanceof org.drip.state.identifier.ForwardLabel) {
			if (!_mmForwardFixing.containsKey (dt))
				_mmForwardFixing.put (dt, new
					org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>());

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapForwardFixing =
				_mmForwardFixing.get (dt);

			mapForwardFixing.put (lsl.fullyQualifiedName(), dblFixing);

			return true;
		}

		if (lsl instanceof org.drip.state.identifier.FXLabel) {
			if (!_mmFXFixing.containsKey (dt))
				_mmFXFixing.put (dt, new
					org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>());

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFXFixing = _mmFXFixing.get
				(dt);

			mapFXFixing.put (lsl.fullyQualifiedName(), dblFixing);

			return true;
		}

		return false;
	}

	/**
	 * Add the Latent State Fixing corresponding to the Date/Label Pair
	 * 
	 * @param iDate The Fixing Date
	 * @param lsl The Latent State Fixing Label
	 * @param dblFixing The Fixing Amount
	 * 
	 * @return TRUE - Entry successfully added
	 */

	public boolean add (
		final int iDate,
		final org.drip.state.identifier.LatentStateLabel lsl,
		final double dblFixing)
	{
		return add (new org.drip.analytics.date.JulianDate (iDate), lsl, dblFixing);
	}

	/**
	 * Remove the Latent State Fixing corresponding to the Date/Label Pair it if exists
	 * 
	 * @param dt The Fixing Date
	 * @param lsl The Latent State Fixing Label
	 * 
	 * @return TRUE - Entry successfully removed if it existed
	 */

	public boolean remove (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		if (null == dt || null == lsl) return false;

		if (lsl instanceof org.drip.state.identifier.ForwardLabel) {
			if (!_mmForwardFixing.containsKey (dt)) return true;

			_mmForwardFixing.get (dt).remove (lsl.fullyQualifiedName());

			return true;
		}

		if (lsl instanceof org.drip.state.identifier.FXLabel) {
			if (!_mmFXFixing.containsKey (dt)) return true;

			_mmFXFixing.get (dt).remove (lsl.fullyQualifiedName());

			return true;
		}

		return false;
	}

	/**
	 * Remove the Latent State Fixing corresponding to the Date/Label Pair it if exists
	 * 
	 * @param iDate The Fixing Date
	 * @param lsl The Latent State Fixing Label
	 * 
	 * @return TRUE - Entry successfully removed if it existed
	 */

	public boolean remove (
		final int iDate,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		return remove (new org.drip.analytics.date.JulianDate (iDate), lsl);
	}

	/**
	 * Retrieve the Latent State Fixing for the Specified Date/LSL Combination
	 * 
	 * @param dt Date
	 * @param lsl The Latent State Latent State Label
	 * 
	 * @return The Latent State Fixing for the Specified Date
	 * 
	 * @throws java.lang.Exception Thrown if the Fixing cannot be found
	 */

	public double fixing (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.state.identifier.LatentStateLabel lsl)
		throws java.lang.Exception
	{
		if (null == dt || null == lsl)
			throw new java.lang.Exception
				("LatentStateFixingsContainer::fixing => Cannot locate Latent State Fixing for the Date");

		if (lsl instanceof org.drip.state.identifier.ForwardLabel) {
			if (!_mmForwardFixing.containsKey (dt))
				throw new java.lang.Exception
					("LatentStateFixingsContainer::fixing => Cannot locate Forward Fixing for the Date");

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapForwardFixing =
				_mmForwardFixing.get (dt);

			java.lang.String strLabel = lsl.fullyQualifiedName();

			if (!mapForwardFixing.containsKey (strLabel))
				throw new java.lang.Exception
					("LatentStateFixingsContainer::fixing => Cannot locate the Forward Label Entry for the Date!");

			return mapForwardFixing.get (strLabel);
		}

		if (lsl instanceof org.drip.state.identifier.FXLabel) {
			if (!_mmFXFixing.containsKey (dt))
				throw new java.lang.Exception
					("LatentStateFixingsContainer::fixing => Cannot locate FX Fixing for the Date");

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFXFixing = _mmFXFixing.get
				(dt);

			java.lang.String strLabel = lsl.fullyQualifiedName();

			if (!mapFXFixing.containsKey (strLabel))
				throw new java.lang.Exception
					("LatentStateFixingsContainer::fixing => Cannot locate the FX Label Entry for the Date!");

			return mapFXFixing.get (strLabel);
		}

		throw new java.lang.Exception
			("LatentStateFixingsContainer::fixing => No Fixings available for the Latent State");
	}

	/**
	 * Retrieve the Latent State Fixing for the Specified Date
	 * 
	 * @param iDate Date
	 * @param lsl The Latent State Label
	 * 
	 * @return The Fixing for the Specified Date
	 * 
	 * @throws java.lang.Exception Thrown if the Fixing cannot be found
	 */

	public double fixing (
		final int iDate,
		final org.drip.state.identifier.LatentStateLabel lsl)
		throws java.lang.Exception
	{
		return fixing (new org.drip.analytics.date.JulianDate (iDate), lsl);
	}

	/**
	 * Indicate the Availability of the Fixing for the Specified LSL Label on the specified Date
	 * 
	 * @param dt The Date
	 * @param lsl The Label
	 * 
	 * @return TRUE - The Fixing for the Specified LSL Label on the specified Date 
	 */

	public boolean available (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		if (null == dt || null == lsl) return false;

		if (lsl instanceof org.drip.state.identifier.ForwardLabel) {
			if (!_mmForwardFixing.containsKey (dt)) return false;

			return _mmForwardFixing.get (dt).containsKey (lsl.fullyQualifiedName());
		}

		if (lsl instanceof org.drip.state.identifier.FXLabel) {
			if (!_mmFXFixing.containsKey (dt)) return false;

			return _mmFXFixing.get (dt).containsKey (lsl.fullyQualifiedName());
		}

		return false;
	}

	/**
	 * Indicate the Availability of the Fixing for the Specified LSL on the specified Date
	 * 
	 * @param iDate The Date
	 * @param lsl The Label
	 * 
	 * @return TRUE - The Fixing for the Specified LSL on the specified Date 
	 */

	public boolean available (
		final int iDate,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		return available (new org.drip.analytics.date.JulianDate (iDate), lsl);
	}
}
