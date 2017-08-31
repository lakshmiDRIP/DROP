
package org.drip.dynamics.hullwhite;

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
 * TrinomialTreeSequenceMetrics records the Evolution Metrics of the Hull-White Model Trinomial Tree
 *  Sequence.
 *
 * @author Lakshmi Krishnamurthy
 */

public class TrinomialTreeSequenceMetrics {
	private java.util.Map<java.lang.String, java.lang.Double> _mapSourceTargetTransitionProbability = new
		java.util.HashMap<java.lang.String, java.lang.Double>();

	private java.util.Map<java.lang.String, java.lang.Double> _mapTargetSourceTransitionProbability = new
		java.util.HashMap<java.lang.String, java.lang.Double>();

	private java.util.Map<java.lang.String, org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics> _mapTTNM =
		new java.util.HashMap<java.lang.String, org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics>();

	private java.util.Map<java.lang.Long, org.drip.dynamics.hullwhite.TrinomialTreeTransitionMetrics>
		_mapTTTM = new java.util.HashMap<java.lang.Long,
			org.drip.dynamics.hullwhite.TrinomialTreeTransitionMetrics>();

	private static final java.lang.String NodeMetricsKey (
		final org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics hwnm)
	{
		return hwnm.timeIndex() + "," + hwnm.xStochasticIndex();
	}

	/**
	 * Empty TrinomialTreeSequenceMetrics Constructor
	 */

	public TrinomialTreeSequenceMetrics()
	{
	}

	/**
	 * Add a Path Transition Metrics Instance
	 * 
	 * @param hwtm The Path Transition Metrics Instance
	 * 
	 * @return TRUE - The Path Transition Metrics Instance successfully added
	 */

	public boolean addTransitionMetrics (
		final org.drip.dynamics.hullwhite.TrinomialTreeTransitionMetrics hwtm)
	{
		if (null == hwtm) return false;

		_mapTTTM.put (hwtm.treeTimeIndex(), hwtm);

		return true;
	}

	/**
	 * Retrieve the Transition Metrics associated with the specified Tree Time Index
	 * 
	 * @param lTreeTimeIndex The Tree Time Index
	 * 
	 * @return The Transition Metrics associated with the specified Tree Time Index
	 */

	public org.drip.dynamics.hullwhite.TrinomialTreeTransitionMetrics transitionMetrics (
		final long lTreeTimeIndex)
	{
		return _mapTTTM.containsKey (lTreeTimeIndex) ? _mapTTTM.get (lTreeTimeIndex) : null;
	}

	/**
	 * Retrieve the Transition Metrics Map
	 * 
	 * @return The Transition Metrics Map
	 */

	public java.util.Map<java.lang.Long, org.drip.dynamics.hullwhite.TrinomialTreeTransitionMetrics>
		transitionMetrics()
	{
		return _mapTTTM;
	}

	/**
	 * Add the Hull-White Node Metrics Instance
	 * 
	 * @param hwnm The Hull-White Node Metrics Instance
	 * 
	 * @return The Node Met5rics Instance successfully added
	 */

	public boolean addNodeMetrics (
		final org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics hwnm)
	{
		if (null == hwnm) return false;

		_mapTTNM.put (NodeMetricsKey (hwnm), hwnm);

		return true;
	}

	/**
	 * Retrieve the Node Metrics from the corresponding Tree Time/Space Indexes
	 * 
	 * @param lTreeTimeIndex The Tree Time Index
	 * @param lTreeStochasticIndex The Tree Space Index
	 * 
	 * @return The Node Metrics
	 */

	public org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics nodeMetrics (
		final long lTreeTimeIndex,
		final long lTreeStochasticIndex)
	{
		java.lang.String strKey = lTreeTimeIndex + "," + lTreeStochasticIndex;

		return _mapTTNM.containsKey (strKey) ? _mapTTNM.get (strKey) : null;
	}

	/**
	 * Retrieve the Node Metrics Map
	 * 
	 * @return The Node Metrics Map
	 */

	public java.util.Map<java.lang.String, org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics>
		nodeMetrics()
	{
		return _mapTTNM;
	}

	/**
	 * Set the Transition Probability for the specified Pair of Nodes
	 * 
	 * @param hwnmSource Source Node
	 * @param hwnmTarget Target Node
	 * @param dblTransitionProbability The Transition Probability
	 * 
	 * @return TRUE - The Transition Probability Successfully set
	 */

	public boolean setTransitionProbability (
		final org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics hwnmSource,
		final org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics hwnmTarget,
		final double dblTransitionProbability)
	{
		if (null == hwnmSource || null == hwnmTarget || !org.drip.quant.common.NumberUtil.IsValid
			(dblTransitionProbability) || 0. >= dblTransitionProbability || 1. < dblTransitionProbability)
			return false;

		java.lang.String strSourceNodeKey = NodeMetricsKey (hwnmSource);

		java.lang.String strTargetNodeKey = NodeMetricsKey (hwnmTarget);

		_mapSourceTargetTransitionProbability.put (strSourceNodeKey + "#" + strTargetNodeKey,
			dblTransitionProbability);

		_mapTargetSourceTransitionProbability.put (strTargetNodeKey + "#" + strSourceNodeKey,
			dblTransitionProbability);

		return true;
	}

	/**
	 * Retrieve the Source-To-Target Transition Probability
	 * 
	 * @param hwnmSource Source Node
	 * @param hwnmTarget Target Node
	 * 
	 * @return The Source-To-Target Transition Probability
	 * 
	 * @throws java.lang.Exception Thrown if the Source-To-Target Transition Probability cannot be computed
	 */

	public double sourceTargetTransitionProbability (
		final org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics hwnmSource,
		final org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics hwnmTarget)
		throws java.lang.Exception
	{
		if (null == hwnmSource || null == hwnmTarget)
			throw new java.lang.Exception
				("TrinomialTreeSequenceMetrics::sourceTargetTransitionProbability => Invalid Inputs!");

		java.lang.String strKey = NodeMetricsKey (hwnmSource) + "#" + NodeMetricsKey (hwnmTarget);

		if (!_mapSourceTargetTransitionProbability.containsKey (strKey))
			throw new java.lang.Exception
				("TrinomialTreeSequenceMetrics::sourceTargetTransitionProbability => No Transition Entry!");

		return _mapSourceTargetTransitionProbability.get (strKey);
	}

	/**
	 * Retrieve the FULL Source-Target Transition Probability Map
	 * 
	 * @return The Source-Target Transition Probability Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> sourceTargetTransitionProbability()
	{
		return _mapSourceTargetTransitionProbability;
	}

	/**
	 * Retrieve the Target-From-Source Transition Probability
	 * 
	 * @param hwnmTarget Target Node
	 * @param hwnmSource Source Node
	 * 
	 * @return The Target-From-Source Transition Probability
	 * 
	 * @throws java.lang.Exception Thrown if the Target-From-Source Transition Probability cannot be computed
	 */

	public double targetSourceTransitionProbability (
		final org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics hwnmTarget,
		final org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics hwnmSource)
		throws java.lang.Exception
	{
		if (null == hwnmSource || null == hwnmTarget)
			throw new java.lang.Exception
				("TrinomialTreeSequenceMetrics::targetSourceTransitionProbability => Invalid Inputs!");

		java.lang.String strKey = NodeMetricsKey (hwnmTarget) + "#" + NodeMetricsKey (hwnmSource);

		if (!_mapTargetSourceTransitionProbability.containsKey (strKey))
			throw new java.lang.Exception
				("TrinomialTreeSequenceMetrics::targetSourceTransitionProbability => No Transition Entry!");

		return _mapTargetSourceTransitionProbability.get (strKey);
	}

	/**
	 * Retrieve the FULL Target-Source Transition Probability Map
	 * 
	 * @return The Target-Source Transition Probability Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> targetSourceTransitionProbability()
	{
		return _mapTargetSourceTransitionProbability;
	}
}
