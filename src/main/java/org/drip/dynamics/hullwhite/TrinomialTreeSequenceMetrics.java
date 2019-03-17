
package org.drip.dynamics.hullwhite;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>TrinomialTreeSequenceMetrics</i> records the Evolution Metrics of the Hull-White Model Trinomial Tree
 * Sequence.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">Dynamics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/hullwhite/README.md">Hull White</a></li>
 *  </ul>
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
		if (null == hwnmSource || null == hwnmTarget || !org.drip.numerical.common.NumberUtil.IsValid
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
