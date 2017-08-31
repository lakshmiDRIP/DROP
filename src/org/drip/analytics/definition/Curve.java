
package org.drip.analytics.definition;

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
 * Curve extends the Latent State to abstract the functionality required among all financial curve. It
 *  exposes the following functionality:
 *  - Set the Epoch and the Identifiers
 *  - Set up/retrieve the Calibration Inputs
 *  - Retrieve the Latent State Metric Measures
 *
 * @author Lakshmi Krishnamurthy
 */

public interface Curve extends org.drip.state.representation.LatentState {

	/**
	 * Get the Curve Latent State Identifier Label
	 * 
	 * @return The Curve Latent State Identifier Label
	 */

	public abstract org.drip.state.identifier.LatentStateLabel label();

	/**
	 * Get the Epoch Date
	 * 
	 * @return The Epoch Date
	 */

	public abstract org.drip.analytics.date.JulianDate epoch();

	/**
	 * Get the Currency
	 * 
	 * @return Currency
	 */

	public abstract java.lang.String currency();

	/**
	 * Set the Curve Construction Input Set Parameters
	 * 
	 * @param ccis The Curve Construction Input Set Parameters
	 * 
	 * @return TRUE - Inputs successfully Set
	 */

	public boolean setCCIS (
		final org.drip.analytics.input.CurveConstructionInputSet ccis);

	/**
	 * Retrieve the Calibration Components
	 * 
	 * @return Array of Calibration Components
	 */

	public abstract org.drip.product.definition.CalibratableComponent[] calibComp();

	/**
	 * Retrieve the Manifest Measure Map of the given Instrument used to construct the Curve
	 * 
	 * @param strInstrumentCode The Calibration Instrument's Code whose Manifest Measure Map is sought
	 * 
	 * @return The Manifest Measure Map of the given Instrument used to construct the Curve
	 */

	public abstract org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> manifestMeasure (
		final java.lang.String strInstrumentCode);
}
