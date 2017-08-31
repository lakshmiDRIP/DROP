
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
 * LSQMPointRecord contains the Record of the Evolving Point Latent State Quantification Metrics.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LSQMPointRecord {
	private java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.Double>> _mmLSQMValue =
		new java.util.HashMap<java.lang.String, java.util.Map<java.lang.String, java.lang.Double>>();

	/**
	 * Empty LSQMPointRecord Constructor
	 */

	public LSQMPointRecord()
	{
	}

	/**
	 * Retrieve the Latent State Labels
	 * 
	 * @return The Latent State Labels
	 */

	public java.util.Set<java.lang.String> latentStateLabel()
	{
		return _mmLSQMValue.keySet();
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
		return null == lsl ? false : _mmLSQMValue.containsKey (lsl.fullyQualifiedName());
	}

	/**
	 * Set the LSQM Value
	 * 
	 * @param lsl The Latent State Label
	 * @param strQM The Quantification Metric
	 * @param dblValue The QM's Value
	 * 
	 * @return TRUE - The QM successfully set
	 */

	public boolean setQM (
		final org.drip.state.identifier.LatentStateLabel lsl,
		final java.lang.String strQM,
		final double dblValue)
	{
		if (null == lsl || null == strQM || strQM.isEmpty() || !org.drip.quant.common.NumberUtil.IsValid
			(dblValue))
			return false;

		java.lang.String strLatentStateLabel = lsl.fullyQualifiedName();

		java.util.Map<java.lang.String, java.lang.Double> mapLSQM = _mmLSQMValue.containsKey
			(strLatentStateLabel) ? _mmLSQMValue.get (strLatentStateLabel) : new
				java.util.HashMap<java.lang.String, java.lang.Double>();

		mapLSQM.put (strQM, dblValue);

		_mmLSQMValue.put (lsl.fullyQualifiedName(), mapLSQM);

		return true;
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

		java.lang.String strLatentStateLabel = lsl.fullyQualifiedName();

		return _mmLSQMValue.containsKey (strLatentStateLabel) && _mmLSQMValue.get
			(strLatentStateLabel).containsKey (strQM);
	}

	/**
	 * Retrieve the specified Quantification Metric Value
	 * 
	 * @param lsl The Latent State Label
	 * @param strQM The Quantification Metric
	 * 
	 * @return The Quantification Metric Value
	 * 
	 * @throws java.lang.Exception Thrown if the Quantification Metric is not available
	 */

	public double qm (
		final org.drip.state.identifier.LatentStateLabel lsl,
		final java.lang.String strQM)
		throws java.lang.Exception
	{
		if (null == lsl || null == strQM || strQM.isEmpty())
			throw new java.lang.Exception ("LSQMPointRecord::qm => Invalid Inputs");

		java.lang.String strLatentStateLabel = lsl.fullyQualifiedName();

		if (!_mmLSQMValue.containsKey (strLatentStateLabel))
			throw new java.lang.Exception ("LSQMPointRecord::qm => Invalid Inputs");

		java.util.Map<java.lang.String, java.lang.Double> mapLSQM = _mmLSQMValue.get (strLatentStateLabel);

		if (!mapLSQM.containsKey (strQM))
			throw new java.lang.Exception ("LSQMPointRecord::qm => No LSQM Entry");

		return mapLSQM.get (strQM);
	}
}
