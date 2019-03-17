
package org.drip.exposure.universe;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>LatentStateWeiner</i> generates the Edge Latent State Weiner Increments across Trajectory Vertexes
 * needed for computing the Valuation Adjustment. The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies <i>Risk</i> <b>23
 *  				(12)</b> 82-87
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-
 *  				party Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  		</li>
 *  		<li>
 *  			Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  				86-90
 *  		</li>
 *  		<li>
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  		<li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/universe/README.md">Universe</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateWeiner
{
	private java.util.Map<java.lang.String, double[]> _latentStateWeinerMap = new
		org.drip.analytics.support.CaseInsensitiveHashMap<double[]>();

	/**
	 * Construct an Instance of LatentStateWeiner from the Arrays of Latent State and their Weiner Increments
	 * 
	 * @param latentStateLabelList Latent State Label List
	 * @param latentStateWeinerIncrementArray Latent State Weiner Increment Array
	 * 
	 * @return Instance of LatentStateWeiner
	 */

	public static final LatentStateWeiner FromUnitRandom (
		final java.util.List<org.drip.state.identifier.LatentStateLabel> latentStateLabelList,
		final double[][] latentStateWeinerIncrementArray)
	{
		if (null == latentStateLabelList || null == latentStateWeinerIncrementArray)
		{
			return null;
		}

		int latentStateCount = latentStateLabelList.size();

		if (0 == latentStateCount || latentStateCount != latentStateWeinerIncrementArray.length)
		{
			return null;
		}

		LatentStateWeiner latentStateWeiner = new LatentStateWeiner();

		for (int latentStateIndex = 0; latentStateIndex < latentStateCount; ++latentStateIndex)
		{
			if (!latentStateWeiner.add (
				latentStateLabelList.get (latentStateIndex),
				latentStateWeinerIncrementArray[latentStateIndex]))
			{
				return null;
			}
		}

		return latentStateWeiner;
	}

	/**
	 * Empty LatentStateWeiner Constructor
	 */

	public LatentStateWeiner()
	{
	}

	/**
	 * Retrieve the Count of the Latent States Available
	 * 
	 * @return The Count of the Latent States Available
	 */

	public int stateCount()
	{
		return _latentStateWeinerMap.size();
	}

	/**
	 * Add the Weiner Increment corresponding to the Specified Latent State Label
	 * 
	 * @param latentStateLabel The Latent State Label
	 * @param weinerIncrementArray The Weiner Increment Array
	 * 
	 * @return TRUE -The Weiner Increment corresponding to the Specified Latent State Label
	 */

	public boolean add (
		final org.drip.state.identifier.LatentStateLabel latentStateLabel,
		final double[] weinerIncrementArray)
	{
		if (null == latentStateLabel ||
			null == weinerIncrementArray ||
			0 == weinerIncrementArray.length ||
			!org.drip.numerical.common.NumberUtil.IsValid (weinerIncrementArray))
		{
			return false;
		}

		_latentStateWeinerMap.put (
			latentStateLabel.fullyQualifiedName(),
			weinerIncrementArray
		);

		return true;
	}

	/**
	 * Retrieve the Latent State Weiner Increment Map
	 * 
	 * @return The Latent State Weiner Increment Map
	 */

	public java.util.Map<java.lang.String, double[]> latentStateWeinerMap()
	{
		return _latentStateWeinerMap;
	}

	/**
	 * Indicate if the specified Latent State is available in the Weiner Increment Map
	 * 
	 * @param latentStateLabel Latent State Label
	 * 
	 * @return TRUE - The specified Latent State is available in the Weiner Increment Map
	 */

	public boolean containsLatentState (
		final org.drip.state.identifier.LatentStateLabel latentStateLabel)
	{
		return null != latentStateLabel && _latentStateWeinerMap.containsKey
			(latentStateLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Weiner Increment Array for the Specified Latent State
	 * 
	 * @param latentStateLabel Latent State Label
	 * 
	 * @return The Weiner Increment Array for the Specified Latent State
	 */

	public double[] incrementArray (
		final org.drip.state.identifier.LatentStateLabel latentStateLabel)
	{
		return containsLatentState (latentStateLabel) ? _latentStateWeinerMap.get
			(latentStateLabel.fullyQualifiedName()) : null;
	}
}
