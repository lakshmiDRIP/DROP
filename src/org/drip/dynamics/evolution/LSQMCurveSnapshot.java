
package org.drip.dynamics.evolution;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * LSQMCurveSnapshot contains the Snapshot of the Evolving Term Structure of the Latent State Quantification
 * 	Metrics.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LSQMCurveSnapshot {
	private java.util.Map<java.lang.String, java.util.Map<java.lang.String,
		org.drip.analytics.definition.Curve>> _mmCurve = new java.util.HashMap<java.lang.String,
			java.util.Map<java.lang.String, org.drip.analytics.definition.Curve>>();

	/**
	 * Empty LSQMCurveSnapshot Constructor
	 */

	public LSQMCurveSnapshot()
	{
	}

	/**
	 * Retrieve the Latent State Labels
	 * 
	 * @return The Latent State Labels
	 */

	public java.util.Set<java.lang.String> latentStateLabel()
	{
		return _mmCurve.keySet();
	}

	/**
	 * Indicate if Quantification Metrics are available for the specified Latent State
	 * 
	 * @param lsl The Latent State Label
	 * 
	 * @return TRUE - Quantification Metrics are available for the specified Latent State
	 */

	public boolean containsLatentState (
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		return null == lsl ? false : _mmCurve.containsKey (lsl.fullyQualifiedName());
	}

	/**
	 * Indicate if the Value for the specified Quantification Metric is available
	 * 
	 * @param lsl The Latent State Label
	 * @param strQM The Quantification Metric
	 * 
	 * @return TRUE - The Requested Value is available
	 */

	public boolean containsQM (
		final org.drip.state.identifier.LatentStateLabel lsl,
		final java.lang.String strQM)
	{
		if (null == lsl || null == strQM || strQM.isEmpty()) return false;

		java.lang.String strLabel = lsl.fullyQualifiedName();

		return _mmCurve.containsKey (strLabel) && _mmCurve.get (strLabel).containsKey (strQM);
	}

	/**
	 * Set the LSQM Curve
	 * 
	 * @param strQM The Quantification Metric
	 * @param curve The QM Curve
	 * 
	 * @return TRUE - The QM successfully set
	 */

	public boolean setQMCurve (
		final java.lang.String strQM,
		final org.drip.analytics.definition.Curve curve)
	{
		if (null == strQM || strQM.isEmpty() || null == curve) return false;

		java.lang.String strLabel = curve.label().fullyQualifiedName();

		java.util.Map<java.lang.String, org.drip.analytics.definition.Curve> mapCurve = _mmCurve.containsKey
			(strLabel) ? _mmCurve.get (strLabel) : new java.util.HashMap<java.lang.String,
				org.drip.analytics.definition.Curve>();

		mapCurve.put (strQM, curve);

		_mmCurve.put (strLabel, mapCurve);

		return true;
	}

	/**
	 * Retrieve the specified Latent State Quantification Metric Curve
	 * 
	 * @param lsl The Latent State Label
	 * @param strQM The Quantification Metric
	 * 
	 * @return The Latent State Quantification Metric Curve
	 */

	public org.drip.analytics.definition.Curve qm (
		final org.drip.state.identifier.LatentStateLabel lsl,
		final java.lang.String strQM)
	{
		if (null == lsl || null == strQM || strQM.isEmpty()) return null;

		java.lang.String strLabel = lsl.fullyQualifiedName();

		java.util.Map<java.lang.String, org.drip.analytics.definition.Curve> mapCurve = _mmCurve.get
			(strLabel);

		return mapCurve.containsKey (strQM) ? mapCurve.get (strQM) : null;
	}
}
