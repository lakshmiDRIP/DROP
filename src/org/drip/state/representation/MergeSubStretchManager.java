
package org.drip.state.representation;

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
 * MergeSubStretchManager manages the different discount-forward merge stretches. It provides functionality
 *  to create, expand, or contract the merge stretches.
 *
 * @author Lakshmi Krishnamurthy
 */

public class MergeSubStretchManager {
	private java.util.List<org.drip.state.representation.LatentStateMergeSubStretch> _lsLSMS = null;

	/**
	 * Empty MergeSubStretchManager constructor
	 */

	public MergeSubStretchManager()
	{
		_lsLSMS = new java.util.ArrayList<org.drip.state.representation.LatentStateMergeSubStretch>();
	}

	/**
	 * Indicates whether the specified Latent State Label is Part of the Merge Stretch
	 * 
	 * @param dblDate The  Date Node
	 * @param lsl The Latent State Label
	 * 
	 * @return TRUE - The specified Latent State Label is Part of the Merge Stretch
	 */

	public boolean partOfMergeState (
		final double dblDate,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblDate) || null == lsl || null == _lsLSMS)
			return false;

		for (org.drip.state.representation.LatentStateMergeSubStretch lsms : _lsLSMS) {
			if (null != lsms && lsms.in (dblDate) && lsms.label().match (lsl)) return true;
		}

		return false;
	}

	/**
	 * Add the Specified Merge Stretch
	 * 
	 * @param lsms The Merge Stretch
	 * 
	 * @return TRUE - Successfully added
	 */

	public boolean addMergeStretch (
		final org.drip.state.representation.LatentStateMergeSubStretch lsms)
	{
		if (null == lsms) return false;

		for (org.drip.state.representation.LatentStateMergeSubStretch lsmsConstituent : _lsLSMS) {
			if (null == lsmsConstituent) continue;

			org.drip.state.representation.LatentStateMergeSubStretch lsmsCoalesced = lsmsConstituent.coalesce
				(lsms);

			if (null == lsmsCoalesced) continue;

			_lsLSMS.remove (lsmsConstituent);

			_lsLSMS.add (lsmsCoalesced);

			return true;
		}

		_lsLSMS.add (lsms);

		return true;
	}
}
