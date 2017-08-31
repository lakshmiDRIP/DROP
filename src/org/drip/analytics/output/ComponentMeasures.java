
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
 * ComponentMeasures is the place holder for analytical single component output measures, optionally across
 * 	scenarios. It contains measure maps for the following scenarios:
 * 	- Unadjusted Base IR/credit curves
 *	- Flat delta/gamma bump measure maps for IR/credit bump curves
 *	- Tenor bump double maps for IR/credit curves
 *	- Flat/recovery bumped measure maps for recovery bumped credit curves
 *	- Measure Maps generated for Custom Scenarios
 *	- Accessor Functions for the above fields
 *	- Serialize into and de-serialize out of byte arrays
 *
 * @author Lakshmi Krishnamurthy
 */

public class ComponentMeasures {
	private double _dblCalcTime = java.lang.Double.NaN;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapBaseMeasures = null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapFlatIRDeltaMeasures =
		null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapFlatIRGammaMeasures =
		null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapFlatRRDeltaMeasures =
		null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapFlatRRGammaMeasures =
		null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapFlatCreditDeltaMeasures =
		null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapFlatCreditGammaMeasures =
		null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmTenorIRDeltaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmTenorIRGammaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmTenorCreditDeltaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmTenorCreditGammaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmTenorRRDeltaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmTenorRRGammaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmCustomMeasures = null;

	/**
	 * Empty constructor - all members initialized to NaN or null
	 */

	public ComponentMeasures()
	{
	}

	/**
	 * Retrieve the Calculation Time
	 * 
	 * @return The Calculation Time
	 */

	public double calcTime()
	{
		return _dblCalcTime;
	}

	/**
	 * Set the Calculation Time
	 * 
	 * @param dblCalcTime The Calculation Time
	 * 
	 * @return TRUE - The Calculation Time Successfully Set
	 */

	public boolean setCalcTime (
		final double dblCalcTime)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblCalcTime)) return false;

		_dblCalcTime = dblCalcTime;
		return true;
	}

	/**
	 * Retrieve the Base Measure Map
	 * 
	 * @return The Base Measure Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> baseMeasures()
	{
		return _mapBaseMeasures;
	}

	/**
	 * Set the Base Measures Map
	 * 
	 * @param mapBaseMeasures The Base Measures Map
	 * 
	 * @return TRUE - The Base Measures Map Successfully Set
	 */

	public boolean setBaseMeasures (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapBaseMeasures)
	{
		if (null == mapBaseMeasures || 0 == mapBaseMeasures.size()) return false;

		_mapBaseMeasures = mapBaseMeasures;
		return true;
	}

	/**
	 * Retrieve the Flat IR Delta Measure Map
	 * 
	 * @return The Flat IR Delta Measure Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> flatIRDeltaMeasures()
	{
		return _mapFlatIRDeltaMeasures;
	}

	/**
	 * Set the Flat IR Delta Measures Map
	 * 
	 * @param mapFlatIRDeltaMeasures The Flat IR Delta Measures Map
	 * 
	 * @return TRUE - The Flat IR Delta Measures Map Successfully Set
	 */

	public boolean setFlatIRDeltaMeasures (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFlatIRDeltaMeasures)
	{
		if (null == mapFlatIRDeltaMeasures || 0 == mapFlatIRDeltaMeasures.size()) return false;

		_mapFlatIRDeltaMeasures = mapFlatIRDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Flat IR Gamma Measure Map
	 * 
	 * @return The Flat IR Gamma Measure Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> flatIRGammaMeasures()
	{
		return _mapFlatIRGammaMeasures;
	}

	/**
	 * Set the Flat IR Gamma Measures Map
	 * 
	 * @param mapFlatIRGammaMeasures The Flat IR Gamma Measures Map
	 * 
	 * @return TRUE - The Flat IR Gamma Measures Map Successfully Set
	 */

	public boolean setFlatIRGammaMeasures (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFlatIRGammaMeasures)
	{
		if (null == mapFlatIRGammaMeasures || 0 == mapFlatIRGammaMeasures.size()) return false;

		_mapFlatIRGammaMeasures = mapFlatIRGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Flat RR Delta Measure Map
	 * 
	 * @return The Flat RR Delta Measure Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> flatRRDeltaMeasures()
	{
		return _mapFlatRRDeltaMeasures;
	}

	/**
	 * Set the Flat RR Delta Measures Map
	 * 
	 * @param mapFlatRRDeltaMeasures The Flat RR Delta Measures Map
	 * 
	 * @return TRUE - The Flat RR Delta Measures Map Successfully Set
	 */

	public boolean setFlatRRDeltaMeasures (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFlatRRDeltaMeasures)
	{
		if (null == mapFlatRRDeltaMeasures || 0 == mapFlatRRDeltaMeasures.size()) return false;

		_mapFlatRRDeltaMeasures = mapFlatRRDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Flat RR Gamma Measure Map
	 * 
	 * @return The Flat RR Gamma Measure Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> flatRRGammaMeasures()
	{
		return _mapFlatRRGammaMeasures;
	}

	/**
	 * Set the Flat RR Gamma Measures Map
	 * 
	 * @param mapFlatRRGammaMeasures The Flat RR Gamma Measures Map
	 * 
	 * @return TRUE - The Flat RR Gamma Measures Map Successfully Set
	 */

	public boolean setFlatRRGammaMeasures (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFlatRRGammaMeasures)
	{
		if (null == mapFlatRRGammaMeasures || 0 == mapFlatRRGammaMeasures.size()) return false;

		_mapFlatRRGammaMeasures = mapFlatRRGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Flat Credit Delta Measure Map
	 * 
	 * @return The Flat Credit Delta Measure Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> flatCreditDeltaMeasures()
	{
		return _mapFlatCreditDeltaMeasures;
	}

	/**
	 * Set the Flat Credit Delta Measures Map
	 * 
	 * @param mapFlatCreditDeltaMeasures The Flat Credit Delta Measures Map
	 * 
	 * @return TRUE - The Flat Credit Delta Measures Map Successfully Set
	 */

	public boolean setFlatCreditDeltaMeasures (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFlatCreditDeltaMeasures)
	{
		if (null == mapFlatCreditDeltaMeasures || 0 == mapFlatCreditDeltaMeasures.size()) return false;

		_mapFlatCreditDeltaMeasures = mapFlatCreditDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Flat Credit Gamma Measure Map
	 * 
	 * @return The Flat Credit Gamma Measure Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> flatCreditGammaMeasures()
	{
		return _mapFlatCreditGammaMeasures;
	}

	/**
	 * Set the Flat Credit Gamma Measures Map
	 * 
	 * @param mapFlatCreditGammaMeasures The Flat Credit Gamma Measures Map
	 * 
	 * @return TRUE - The Flat Credit Gamma Measures Map Successfully Set
	 */

	public boolean setFlatCreditGammaMeasures (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFlatCreditGammaMeasures)
	{
		if (null == mapFlatCreditGammaMeasures || 0 == mapFlatCreditGammaMeasures.size()) return false;

		_mapFlatCreditGammaMeasures = mapFlatCreditGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Tenor IR Delta Double Measure Map
	 * 
	 * @return The Tenor IR Delta Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			tenorIRDeltaMeasures()
	{
		return _mmTenorIRDeltaMeasures;
	}

	/**
	 * Set the Tenor IR Delta Double Measures Map
	 * 
	 * @param mmTenorIRDeltaMeasures The Tenor IR Delta Double Measures Map
	 * 
	 * @return TRUE - The Tenor IR Delta Double Measures Map Successfully Set
	 */

	public boolean setTenorIRDeltaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmTenorIRDeltaMeasures)
	{
		if (null == mmTenorIRDeltaMeasures || 0 == mmTenorIRDeltaMeasures.size()) return false;

		_mmTenorIRDeltaMeasures = mmTenorIRDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Tenor IR Gamma Double Measure Map
	 * 
	 * @return The Tenor IR Gamma Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			tenorIRGammaMeasures()
	{
		return _mmTenorIRGammaMeasures;
	}

	/**
	 * Set the Tenor IR Gamma Double Measures Map
	 * 
	 * @param mmTenorIRGammaMeasures The Tenor IR Gamma Double Measures Map
	 * 
	 * @return TRUE - The Tenor IR Gamma Double Measures Map Successfully Set
	 */

	public boolean setTenorIRGammaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmTenorIRGammaMeasures)
	{
		if (null == mmTenorIRGammaMeasures || 0 == mmTenorIRGammaMeasures.size()) return false;

		_mmTenorIRGammaMeasures = mmTenorIRGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Tenor Credit Delta Double Measure Map
	 * 
	 * @return The Tenor Credit Delta Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			tenorCreditDeltaMeasures()
	{
		return _mmTenorCreditDeltaMeasures;
	}

	/**
	 * Set the Tenor Credit Delta Double Measures Map
	 * 
	 * @param mmTenorCreditDeltaMeasures The Tenor Credit Delta Double Measures Map
	 * 
	 * @return TRUE - The Tenor Credit Delta Double Measures Map Successfully Set
	 */

	public boolean setTenorCreditDeltaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmTenorCreditDeltaMeasures)
	{
		if (null == mmTenorCreditDeltaMeasures || 0 == mmTenorCreditDeltaMeasures.size()) return false;

		_mmTenorCreditDeltaMeasures = mmTenorCreditDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Tenor Credit Gamma Double Measure Map
	 * 
	 * @return The Tenor Credit Gamma Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			tenorCreditGammaMeasures()
	{
		return _mmTenorCreditGammaMeasures;
	}

	/**
	 * Set the Tenor Credit Gamma Double Measures Map
	 * 
	 * @param mmTenorCreditGammaMeasures The Tenor Credit Gamma Double Measures Map
	 * 
	 * @return TRUE - The Tenor Credit Gamma Double Measures Map Successfully Set
	 */

	public boolean setTenorCreditGammaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmTenorCreditGammaMeasures)
	{
		if (null == mmTenorCreditGammaMeasures || 0 == mmTenorCreditGammaMeasures.size()) return false;

		_mmTenorCreditGammaMeasures = mmTenorCreditGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Tenor RR Delta Double Measure Map
	 * 
	 * @return The Tenor RR Delta Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			tenorRRDeltaMeasures()
	{
		return _mmTenorRRDeltaMeasures;
	}

	/**
	 * Set the Tenor RR Delta Double Measures Map
	 * 
	 * @param mmTenorRRDeltaMeasures The Tenor RR Delta Double Measures Map
	 * 
	 * @return TRUE - The Tenor RR Delta Double Measures Map Successfully Set
	 */

	public boolean setTenorRRDeltaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmTenorRRDeltaMeasures)
	{
		if (null == mmTenorRRDeltaMeasures || 0 == mmTenorRRDeltaMeasures.size()) return false;

		_mmTenorRRDeltaMeasures = mmTenorRRDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Tenor RR Gamma Double Measure Map
	 * 
	 * @return The Tenor RR Gamma Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			tenorRRGammaMeasures()
	{
		return _mmTenorRRGammaMeasures;
	}

	/**
	 * Set the Tenor RR Gamma Double Measures Map
	 * 
	 * @param mmTenorRRGammaMeasures The Tenor IR Gamma Double Measures Map
	 * 
	 * @return TRUE - The Tenor RR Gamma Double Measures Map Successfully Set
	 */

	public boolean setTenorRRGammaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmTenorRRGammaMeasures)
	{
		if (null == mmTenorRRGammaMeasures || 0 == mmTenorRRGammaMeasures.size()) return false;

		_mmTenorRRGammaMeasures = mmTenorRRGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Custom Double Measure Map
	 * 
	 * @return The Custom Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			customMeasures()
	{
		return _mmCustomMeasures;
	}

	/**
	 * Set the Custom Double Measures Map
	 * 
	 * @param mmCustomMeasures The Custom Double Measures Map
	 * 
	 * @return TRUE - The Custom Double Measures Map Successfully Set
	 */

	public boolean setCustomMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmCustomMeasures)
	{
		if (null == mmCustomMeasures || 0 == mmCustomMeasures.size()) return false;

		_mmCustomMeasures = mmCustomMeasures;
		return true;
	}
}
