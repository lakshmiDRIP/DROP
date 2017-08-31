
package org.drip.param.definition;

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
 * ManifestMeasureTweak contains the place holder for the scenario tweak parameters, for either a specific curve
 *  node, or the entire curve (flat). Parameter bumps can be parallel or proportional.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ManifestMeasureTweak {

	/**
	 * Flat Manifest Measure Tweak Mode
	 */

	public static final int FLAT = -1;

	private int _iNode = FLAT;
	private boolean _bIsProportional = false;
	private double _dblAmount = java.lang.Double.NaN;

	/**
	 * ManifestMeasureTweak constructor
	 * 
	 * @param iNode Node to be tweaked - Set to NODE_FLAT_TWEAK for flat curve tweak
	 * @param bIsProportional True - Tweak is proportional, False - parallel
	 * @param dblAmount Amount to be tweaked - proportional tweaks are represented as percent, parallel
	 * 	tweaks are absolute numbers
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public ManifestMeasureTweak (
		final int iNode,
		final boolean bIsProportional,
		final double dblAmount)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblAmount = dblAmount))
			throw new java.lang.Exception ("ManifestMeasureTweak ctr => Invalid Inputs");

		_iNode = iNode;
		_bIsProportional = bIsProportional;
	}

	/**
	 * Index of the Node to be tweaked
	 * 
	 * @return Index of the Node to be tweaked
	 */

	public int node()
	{
		return _iNode;
	}

	/**
	 * Amount to be tweaked by
	 * 
	 * @return Amount to be tweaked by
	 */

	public double amount()
	{
		return _dblAmount;
	}

	/**
	 * Is the Tweak Proportional
	 * 
	 * @return TRUE - The Tweak is Proportional
	 */

	public boolean isProportional()
	{
		return _bIsProportional;
	}
}
