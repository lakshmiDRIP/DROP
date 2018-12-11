
package org.drip.xva.topology;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
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
 * <i>CollateralGroup</i> represents an Aggregation of Position Groups over a common Collateral
 * Specification. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Albanese, C., L. Andersen, and, S. Iabichino (2015): The FVA Puzzle: Accounting, Risk Management,
 *  			and Collateral Trading https://papers.ssrn.com/sol3/paper.cfm?abstract_id_2517301
 *  			<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva">XVA</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/topology">Topology</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CollateralGroup extends org.drip.xva.proto.ObjectSpecification
{
	private org.drip.xva.proto.CollateralGroupSpecification _collateralGroupSpecification = null;
	private java.util.Map<java.lang.String, org.drip.xva.topology.PositionGroup> _positionGroupMap = null;

	/**
	 * CollateralGroup Constructor
	 * 
	 * @param id The Collateral Group ID
	 * @param name The Collateral Group Name
	 * @param collateralGroupSpecification The Collateral Group Specification
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CollateralGroup (
		final java.lang.String id,
		final java.lang.String name,
		final org.drip.xva.proto.CollateralGroupSpecification collateralGroupSpecification)
		throws java.lang.Exception
	{
		super (
			id,
			name
		);

		if (null == (_collateralGroupSpecification = collateralGroupSpecification))
		{
			throw new java.lang.Exception ("CollateralGroup Contructor => Invalid Inputs");
		}

		_positionGroupMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.topology.PositionGroup>();
	}

	/**
	 * Retrieve the Collateral Group Specification
	 * 
	 * @return The Collateral Group Specification
	 */

	public org.drip.xva.proto.CollateralGroupSpecification collateralGroupSpecification()
	{
		return _collateralGroupSpecification;
	}

	/**
	 * Retrieve the Position Group Map
	 * 
	 * @return The Position Group Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.topology.PositionGroup> positionGroupMap()
	{
		return _positionGroupMap;
	}

	/**
	 * Add the specified Position Group
	 * 
	 * @param positionGroup The Position Group
	 * 
	 * @return TRUE - The Position Group successfully added
	 */

	public boolean addPositionGroup (
		final org.drip.xva.topology.PositionGroup positionGroup)
	{
		if (null == positionGroup)
		{
			return false;
		}

		_positionGroupMap.put (
			positionGroup.id(),
			positionGroup
		);

		return true;
	}

	/**
	 * Indicates if the Position Group identified by the specified ID
	 * 
	 * @param positionGroupID The Position Group ID
	 * 
	 * @return TRUE - The Position Group Exists
	 */

	public boolean containsPositionGroup (
		final java.lang.String positionGroupID)
	{
		return null == positionGroupID || positionGroupID.isEmpty() ? false : _positionGroupMap.containsKey
			(positionGroupID);
	}

	/**
	 * Retrieve the Position Group identified by the specified ID
	 * 
	 * @param positionGroupID The Position Group ID
	 * 
	 * @return The Position Group
	 */

	public org.drip.xva.topology.PositionGroup positionGroup (
		final java.lang.String positionGroupID)
	{
		return containsPositionGroup (positionGroupID) ? _positionGroupMap.get (positionGroupID) : null;
	}

	/**
	 * Retrieve the Overnight Label
	 * 
	 * @return The Overnight Label
	 */

	public org.drip.state.identifier.OvernightLabel overnightLabel()
	{
		return _collateralGroupSpecification.overnightLabel();
	}

	/**
	 * Retrieve the CSA Label
	 * 
	 * @return The CSA Label
	 */

	public org.drip.state.identifier.CSALabel csaLabel()
	{
		return _collateralGroupSpecification.csaLabel();
	}
}
