
package org.drip.capital.simulation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>CapitalSegmentPathEnsemble</i> generates the Ensemble of Capital Paths from the Simulation PnL
 * Realizations for the Capital Units under the specified Capital Segments. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision(2005): Stress Testing at Major Financial Institutions: Survey
 * 				Results and Practice https://www.bis.org/publ/cgfs24.htm
 * 		</li>
 * 		<li>
 * 			Glasserman, P. (2004): <i>Monte Carlo Methods in Financial Engineering</i> <b>Springer</b>
 * 		</li>
 * 		<li>
 * 			Kupiec, P. H. (2000): Stress Tests and Risk Capital <i>Risk</i> <b>2 (4)</b> 27-39
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md">Basel Market Risk and Operational Capital</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/simulation/README.md">Economic Risk Capital Simulation Ensemble</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CapitalSegmentPathEnsemble
	extends org.drip.capital.simulation.CapitalUnitPathEnsemble
{
	private java.util.Map<java.lang.String, org.drip.capital.simulation.PathEnsemble> _pathEnsembleMap =
		null;

	/**
	 * CapitalSegmentPathEnsemble Constructor
	 * 
	 * @param pathEnsembleMap Map of Path Ensemble
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CapitalSegmentPathEnsemble (
		final java.util.Map<java.lang.String, org.drip.capital.simulation.PathEnsemble> pathEnsembleMap)
		throws java.lang.Exception
	{
		if (null == (_pathEnsembleMap = pathEnsembleMap))
		{
			throw new java.lang.Exception ("CapitalSegmentPathEnsemble Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Map of Path Ensembles
	 * 
	 * @return The Map of Path Ensembles
	 */

	public java.util.Map<java.lang.String, org.drip.capital.simulation.PathEnsemble> pathEnsembleMap()
	{
		return _pathEnsembleMap;
	}

	/**
	 * Construct the Contributing Marginal PnL Attribution given the Confidence Level by Count
	 * 
	 * @param confidenceCount Confidence Level by Count
	 * 
	 * @return The Contributing Marginal PnL Attribution
	 */

	public org.drip.capital.explain.CapitalSegmentStandaloneMarginal marginalPnLAttribution (
		final int confidenceCount)
	{
		org.drip.capital.explain.CapitalUnitPnLAttribution segmentCapitalUnitPnLAttribution =
			super.pnlAttribution (confidenceCount);

		if (null == segmentCapitalUnitPnLAttribution)
		{
			return null;
		}

		java.util.List<java.lang.Integer> pathIndexList = segmentCapitalUnitPnLAttribution.pathIndexList();

		java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution>
			marginalPnLAttributionMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.explain.PnLAttribution>();

		java.util.Set<java.lang.String> capitalUnitCoordinateSet = _pathEnsembleMap.keySet();

		for (java.lang.String capitalUnitCoordinate : capitalUnitCoordinateSet)
		{
			org.drip.capital.explain.CapitalUnitPnLAttribution capitalUnitPnLAttribution =
				_pathEnsembleMap.get (capitalUnitCoordinate).pnlAttribution (
					pathIndexList
				);

			if (null == capitalUnitPnLAttribution)
			{
				return null;
			}

			marginalPnLAttributionMap.put (
				capitalUnitCoordinate,
				capitalUnitPnLAttribution
			);
		}

		try
		{
			return new org.drip.capital.explain.CapitalSegmentStandaloneMarginal (
				segmentCapitalUnitPnLAttribution.pathPnLRealizationList(),
				marginalPnLAttributionMap,
				null
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Contributing Marginal PnL Attribution given the Confidence Level by Percentage
	 * 
	 * @param confidenceLevel Confidence Level by Percentage
	 * 
	 * @return The Contributing Marginal PnL Attribution
	 */

	public org.drip.capital.explain.CapitalSegmentStandaloneMarginal marginalPnLAttribution (
		final double confidenceLevel)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (confidenceLevel) ||
			0. >= confidenceLevel || 1. <= confidenceLevel)
		{
			return null;
		}

		return marginalPnLAttribution ((int) (count() * (1. - confidenceLevel)));
	}

	/**
	 * Construct the Capital Segment Stand-alone PnL Attribution given the Confidence Level by Count
	 * 
	 * @param confidenceCount Confidence Level by Count
	 * 
	 * @return The Capital Segment Stand-alone PnL Attribution
	 */

	public org.drip.capital.explain.CapitalSegmentPnLAttribution standalonePnLAttribution (
		final int confidenceCount)
	{
		int capitalUnitIndex = 0;

		org.drip.capital.explain.CapitalUnitPnLAttribution[] capitalUnitPathAttributionArray =
			new org.drip.capital.explain.CapitalUnitPnLAttribution[_pathEnsembleMap.size()];

		java.util.Set<java.lang.String> capitalUnitCoordinateSet = _pathEnsembleMap.keySet();

		for (java.lang.String capitalUnitCoordinate : capitalUnitCoordinateSet)
		{
			if (null == (
				capitalUnitPathAttributionArray[capitalUnitIndex++] =
					_pathEnsembleMap.get (capitalUnitCoordinate).pnlAttribution (confidenceCount)
			))
			{
				return null;
			}
		}

		try
		{
			return new org.drip.capital.explain.CapitalSegmentPnLAttribution (
				capitalUnitPathAttributionArray
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Capital Segment Stand-alone PnL Attribution given the Confidence Level by Percentage
	 * 
	 * @param confidenceLevel Confidence Level by Percentage
	 * 
	 * @return The Capital Segment Stand-alone PnL Attribution
	 */

	public org.drip.capital.explain.CapitalSegmentPnLAttribution standalonePnLAttribution (
		final double confidenceLevel)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (confidenceLevel) ||
			0. >= confidenceLevel || 1. <= confidenceLevel)
		{
			return null;
		}

		return standalonePnLAttribution ((int) (count() * (1. - confidenceLevel)));
	}

	/**
	 * Construct the Contributing Marginal and Stand-alone PnL Attribution given the Confidence Level by
	 * 	Count
	 * 
	 * @param confidenceCount Confidence Level by Count
	 * 
	 * @return The Contributing Marginal and Stand-alone PnL Attribution
	 */

	public org.drip.capital.explain.CapitalSegmentStandaloneMarginal marginalStandalonePnLAttribution (
		final int confidenceCount)
	{
		org.drip.capital.explain.CapitalUnitPnLAttribution segmentCapitalUnitPnLAttribution =
			super.pnlAttribution (confidenceCount);

		if (null == segmentCapitalUnitPnLAttribution)
		{
			return null;
		}

		java.util.List<java.lang.Integer> pathIndexList = segmentCapitalUnitPnLAttribution.pathIndexList();

		java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution> marginalPnLAttributionMap
			= new org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.explain.PnLAttribution>();

		java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution>
			standalonePnLAttributionMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.explain.PnLAttribution>();

		java.util.Set<java.lang.String> capitalUnitCoordinateSet = _pathEnsembleMap.keySet();

		for (java.lang.String capitalUnitCoordinate : capitalUnitCoordinateSet)
		{
			org.drip.capital.explain.CapitalUnitPnLAttribution capitalUnitMarginalAttribution =
				_pathEnsembleMap.get (
					capitalUnitCoordinate
				).pnlAttribution (
					pathIndexList
				);

			if (null == capitalUnitMarginalAttribution)
			{
				return null;
			}

			marginalPnLAttributionMap.put (
				capitalUnitCoordinate,
				capitalUnitMarginalAttribution
			);

			org.drip.capital.explain.CapitalUnitPnLAttribution capitalUnitStandaloneAttribution =
				_pathEnsembleMap.get (capitalUnitCoordinate).pnlAttribution (confidenceCount);

			if (null == capitalUnitStandaloneAttribution)
			{
				return null;
			}

			standalonePnLAttributionMap.put (
				capitalUnitCoordinate,
				capitalUnitStandaloneAttribution
			);
		}

		try
		{
			return new org.drip.capital.explain.CapitalSegmentStandaloneMarginal (
				segmentCapitalUnitPnLAttribution.pathPnLRealizationList(),
				marginalPnLAttributionMap,
				standalonePnLAttributionMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Contributing Marginal and Stand-alone PnL Attribution given the Confidence Level by
	 * 	Percentage
	 * 
	 * @param confidenceLevel Confidence Level by Count
	 * 
	 * @return The Contributing Marginal and Stand-alone PnL Attribution
	 */

	public org.drip.capital.explain.CapitalSegmentStandaloneMarginal marginalStandalonePnLAttribution (
		final double confidenceLevel)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (confidenceLevel) ||
			0. >= confidenceLevel || 1. <= confidenceLevel)
		{
			return null;
		}

		return marginalStandalonePnLAttribution ((int) (count() * (1. - confidenceLevel)));
	}
}
