
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
 * BasketMeasures is the place holder for the analytical basket measures, optionally across scenarios. It
 * 	contains the following scenario measure maps:
 * 	- Unadjusted Base Measures
 *	- Flat delta/gamma bump measure maps for IR/credit/RR bump curves
 *	- Component/tenor bump double maps for IR/credit/RR curves
 *	- Flat/component recovery bumped measure maps for recovery bumped credit curves
 *	- Custom scenario measure map
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BasketMeasures extends org.drip.analytics.output.ComponentMeasures {
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmComponentIRDeltaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmComponentIRGammaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmComponentCreditDeltaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmComponentCreditGammaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmComponentRRDeltaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmComponentRRGammaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			_mmmComponentTenorIRDeltaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			_mmmComponentTenorIRGammaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			_mmmComponentTenorCreditDeltaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			_mmmComponentTenorCreditGammaMeasures = null;
	private
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			_mmComponentCustomMeasures = null;

	/**
	 * Empty constructor - all members initialized to NaN or null
	 */

	public BasketMeasures()
	{
	}

	/**
	 * Retrieve the Component IR Delta Double Measure Map
	 * 
	 * @return The Component IR Delta Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			componentIRDeltaMeasures()
	{
		return _mmComponentIRDeltaMeasures;
	}

	/**
	 * Set the Component IR Delta Double Measures Map
	 * 
	 * @param mmComponentIRDeltaMeasures The Component IR Delta Double Measures Map
	 * 
	 * @return TRUE - The Component IR Delta Double Measures Map Successfully Set
	 */

	public boolean setComponentIRDeltaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmComponentIRDeltaMeasures)
	{
		if (null == mmComponentIRDeltaMeasures || 0 == mmComponentIRDeltaMeasures.size()) return false;

		_mmComponentIRDeltaMeasures = mmComponentIRDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component IR Gamma Double Measure Map
	 * 
	 * @return The Component IR Gamma Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			componentIRGammaMeasures()
	{
		return _mmComponentIRGammaMeasures;
	}

	/**
	 * Set the Component IR Gamma Double Measures Map
	 * 
	 * @param mmComponentIRGammaMeasures The Component IR Gamma Double Measures Map
	 * 
	 * @return TRUE - The Component IR Gamma Double Measures Map Successfully Set
	 */

	public boolean setComponentIRGammaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmComponentIRGammaMeasures)
	{
		if (null == mmComponentIRGammaMeasures || 0 == mmComponentIRGammaMeasures.size()) return false;

		_mmComponentIRGammaMeasures = mmComponentIRGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component Credit Delta Double Measure Map
	 * 
	 * @return The ComponentCredit Delta Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			componentCreditDeltaMeasures()
	{
		return _mmComponentCreditDeltaMeasures;
	}

	/**
	 * Set the Component Credit Delta Double Measures Map
	 * 
	 * @param mmComponentCreditDeltaMeasures The Component Credit Delta Double Measures Map
	 * 
	 * @return TRUE - The Component Credit Delta Double Measures Map Successfully Set
	 */

	public boolean setComponentCreditDeltaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmComponentCreditDeltaMeasures)
	{
		if (null == mmComponentCreditDeltaMeasures || 0 == mmComponentCreditDeltaMeasures.size())
			return false;

		_mmComponentCreditDeltaMeasures = mmComponentCreditDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component Credit Gamma Double Measure Map
	 * 
	 * @return The Component Credit Gamma Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			componentCreditGammaMeasures()
	{
		return _mmComponentCreditGammaMeasures;
	}

	/**
	 * Set the Component Credit Gamma Double Measures Map
	 * 
	 * @param mmComponentCreditGammaMeasures The Component Credit Gamma Double Measures Map
	 * 
	 * @return TRUE - The Component Credit Gamma Double Measures Map Successfully Set
	 */

	public boolean setComponentCreditGammaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmComponentCreditGammaMeasures)
	{
		if (null == mmComponentCreditGammaMeasures || 0 == mmComponentCreditGammaMeasures.size()) return false;

		_mmComponentCreditGammaMeasures = mmComponentCreditGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component RR Delta Double Measure Map
	 * 
	 * @return The Component RR Delta Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			componentRRDeltaMeasures()
	{
		return _mmComponentRRDeltaMeasures;
	}

	/**
	 * Set the Component RR Delta Double Measures Map
	 * 
	 * @param mmComponentRRDeltaMeasures The RR Delta Double Measures Map
	 * 
	 * @return TRUE - The Component RR Delta Double Measures Map Successfully Set
	 */

	public boolean setComponentRRDeltaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmComponentRRDeltaMeasures)
	{
		if (null == mmComponentRRDeltaMeasures || 0 == mmComponentRRDeltaMeasures.size()) return false;

		_mmComponentRRDeltaMeasures = mmComponentRRDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component RR Gamma Double Measure Map
	 * 
	 * @return The Component RR Gamma Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			componentRRGammaMeasures()
	{
		return _mmComponentRRGammaMeasures;
	}

	/**
	 * Set the Component RR Gamma Double Measures Map
	 * 
	 * @param mmComponentRRGammaMeasures The RR Gamma Double Measures Map
	 * 
	 * @return TRUE - The Component RR Gamma Double Measures Map Successfully Set
	 */

	public boolean setComponentRRGammaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmComponentRRGammaMeasures)
	{
		if (null == mmComponentRRGammaMeasures || 0 == mmComponentRRGammaMeasures.size()) return false;

		_mmComponentRRGammaMeasures = mmComponentRRGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component/Tenor IR Delta Triple Measure Map
	 * 
	 * @return The Component/Tenor IR Delta Triple Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			componentTenorIRDeltaMeasures()
	{
		return _mmmComponentTenorIRDeltaMeasures;
	}

	/**
	 * Set the Component/Tenor IR Delta Triple Measures Map
	 * 
	 * @param mmmComponentTenorIRDeltaMeasures The Component/Tenor IR Delta Triple Measures Map
	 * 
	 * @return TRUE - The Component/Tenor IR Delta Triple Measures Map Successfully Set
	 */

	public boolean setComponentTenorIRDeltaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
				mmmComponentTenorIRDeltaMeasures)
	{
		if (null == mmmComponentTenorIRDeltaMeasures || 0 == mmmComponentTenorIRDeltaMeasures.size())
			return false;

		_mmmComponentTenorIRDeltaMeasures = mmmComponentTenorIRDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component/Tenor IR Gamma Triple Measure Map
	 * 
	 * @return The Component/Tenor IR Gamma Triple Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			componentTenorIRGammaMeasures()
	{
		return _mmmComponentTenorIRGammaMeasures;
	}

	/**
	 * Set the Component/Tenor IR Gamma Triple Measures Map
	 * 
	 * @param mmmComponentTenorIRGammaMeasures The Component/Tenor IR Gamma Triple Measures Map
	 * 
	 * @return TRUE - The Component/Tenor IR Gamma Triple Measures Map Successfully Set
	 */

	public boolean setComponentTenorIRGammaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
				mmmComponentTenorIRGammaMeasures)
	{
		if (null == mmmComponentTenorIRGammaMeasures || 0 == mmmComponentTenorIRGammaMeasures.size())
			return false;

		_mmmComponentTenorIRGammaMeasures = mmmComponentTenorIRGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component/Tenor Credit Delta Triple Measure Map
	 * 
	 * @return The Component/Tenor Credit Delta Triple Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			componentTenorCreditDeltaMeasures()
	{
		return _mmmComponentTenorCreditDeltaMeasures;
	}

	/**
	 * Set the Component/Tenor Credit Delta Triple Measures Map
	 * 
	 * @param mmmComponentTenorCreditDeltaMeasures The Component/Tenor Credit Delta Triple Measures Map
	 * 
	 * @return TRUE - The Component/Tenor Credit Delta Triple Measures Map Successfully Set
	 */

	public boolean setComponentTenorCreditDeltaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
				mmmComponentTenorCreditDeltaMeasures)
	{
		if (null == mmmComponentTenorCreditDeltaMeasures || 0 == mmmComponentTenorCreditDeltaMeasures.size())
			return false;

		_mmmComponentTenorCreditDeltaMeasures = mmmComponentTenorCreditDeltaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component/Tenor Credit Gamma Triple Measure Map
	 * 
	 * @return The Component/Tenor Credit Gamma Triple Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			componentTenorCreditGammaMeasures()
	{
		return _mmmComponentTenorCreditGammaMeasures;
	}

	/**
	 * Set the Component/Tenor Credit Gamma Triple Measures Map
	 * 
	 * @param mmmComponentTenorCreditGammaMeasures The Component/Tenor Credit Gamma Triple Measures Map
	 * 
	 * @return TRUE - The Component/Tenor Credit Gamma Triple Measures Map Successfully Set
	 */

	public boolean setComponentTenorCreditGammaMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
				mmmComponentTenorCreditGammaMeasures)
	{
		if (null == mmmComponentTenorCreditGammaMeasures || 0 == mmmComponentTenorCreditGammaMeasures.size())
			return false;

		_mmmComponentTenorCreditGammaMeasures = mmmComponentTenorCreditGammaMeasures;
		return true;
	}

	/**
	 * Retrieve the Component Custom Double Measure Map
	 * 
	 * @return The Component Custom Double Measure Map
	 */

	public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			componentCustomMeasures()
	{
		return _mmComponentCustomMeasures;
	}

	/**
	 * Set the Component Custom Double Measures Map
	 * 
	 * @param mmComponentCustomMeasures The Component Custom Double Measures Map
	 * 
	 * @return TRUE - The Component Custom Double Measures Map Successfully Set
	 */

	public boolean setComponentCustomMeasures (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				mmComponentCustomMeasures)
	{
		if (null == mmComponentCustomMeasures || 0 == mmComponentCustomMeasures.size()) return false;

		_mmComponentCustomMeasures = mmComponentCustomMeasures;
		return true;
	}
}
