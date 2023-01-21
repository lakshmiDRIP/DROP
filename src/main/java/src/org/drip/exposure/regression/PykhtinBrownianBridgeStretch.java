
package org.drip.exposure.regression;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
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
 * <i>PykhtinBrownianBridgeStretch</i> generates the Regression Based Path Exposures off of the Pillar
 * Vertexes using the Pykhtin (2009) Scheme. The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of
 *  				Initial Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and
 *  				the Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955
 *  				<b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure Group Level Collateralized/Uncollateralized Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regression/README.md">Regression Based Path Exposure Generation</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PykhtinBrownianBridgeStretch
{
	private java.util.Map<java.lang.Integer, java.lang.Double> _sparseVertexExposureTrajectory = null;
	private java.util.Map<java.lang.Integer, org.drip.function.definition.R1ToR1> _localVolatilityTrajectory
		= null;

	/**
	 * PykhtinBrownianBridgeStretch Constructor
	 * 
	 * @param sparseVertexExposureTrajectory The Sparse Vertex Exposure Amount Trajectory
	 * @param localVolatilityTrajectory The R^1 To R^1 Local Volatility Trajectory
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PykhtinBrownianBridgeStretch (
		final java.util.Map<java.lang.Integer, java.lang.Double> sparseVertexExposureTrajectory,
		final java.util.Map<java.lang.Integer, org.drip.function.definition.R1ToR1>
			localVolatilityTrajectory)
		throws java.lang.Exception
	{
		if (null == (_sparseVertexExposureTrajectory = sparseVertexExposureTrajectory) ||
			null == (_localVolatilityTrajectory = localVolatilityTrajectory))
		{
			throw new java.lang.Exception ("PykhtinBrownianBridgeStretch Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Path Sparse Vertex Exposure Trajectory
	 * 
	 * @return The Path Sparse Vertex Exposure Trajectory
	 */

	public java.util.Map<java.lang.Integer, java.lang.Double> sparseVertexExposureTrajectory()
	{
		return _sparseVertexExposureTrajectory;
	}

	/**
	 * Retrieve the Path Sparse Vertex Local Volatility Trajectory
	 * 
	 * @return The Path Sparse Vertex Local Volatility Trajectory
	 */

	public java.util.Map<java.lang.Integer, org.drip.function.definition.R1ToR1> localVolatilityTrajectory()
	{
		return _localVolatilityTrajectory;
	}

	/**
	 * Generate the Dense (Complete) Segment Exposures
	 * 
	 * @param wanderTrajectory The Wander Date Trajectory
	 * 
	 * @return The Dense (Complete) Segment Exposures
	 */

	public java.util.Map<java.lang.Integer, java.lang.Double> denseExposure (
		final java.util.Map<java.lang.Integer, java.lang.Double> wanderTrajectory)
	{
		int sparseLeftPillarDate = -1;

		java.util.Map<java.lang.Integer, java.lang.Double> denseExposureTrajectory = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> sparseExposureTrajectoryEntry :
			_sparseVertexExposureTrajectory.entrySet())
		{
			int sparseRightPillarDate = sparseExposureTrajectoryEntry.getKey();

			if (-1 == sparseLeftPillarDate)
			{
				sparseLeftPillarDate = sparseRightPillarDate;
				continue;
			}

			try
			{
				new PykhtinBrownianBridgeSegment (
					new org.drip.exposure.regression.PillarVertex (
						sparseLeftPillarDate,
						_sparseVertexExposureTrajectory.get (sparseLeftPillarDate)
					),
					new org.drip.exposure.regression.PillarVertex (
						sparseRightPillarDate,
						_sparseVertexExposureTrajectory.get (sparseRightPillarDate)
					),
					_localVolatilityTrajectory.get (sparseRightPillarDate)
				).denseExposureTrajectoryUpdate (
					denseExposureTrajectory,
					wanderTrajectory
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}

			sparseLeftPillarDate = sparseRightPillarDate;
		}

		return denseExposureTrajectory;
	}
}
