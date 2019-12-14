
package org.drip.capital.explain;

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
 * <i>AllocatedPnLAttribution</i> exposes the Path-Level Capital Component Attributions Post Allocation
 * 	Adjustments. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/explain/README.md">Economic Risk Capital Attribution Explain</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AllocatedPnLAttribution
	extends org.drip.capital.explain.PnLAttribution
{
	private org.drip.capital.explain.PnLAttribution _standalonePnLAttribution = null;
	private org.drip.capital.allocation.EntityComponentCapital _entityComponentCapital = null;

	/**
	 * AllocatedPnLAttribution Constructor
	 * 
	 * @param standalonePnLAttribution The Stand-alone PnL Attribution
	 * @param entityComponentCapital The Entity Component Capital
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public AllocatedPnLAttribution (
		final org.drip.capital.explain.PnLAttribution standalonePnLAttribution,
		final org.drip.capital.allocation.EntityComponentCapital entityComponentCapital)
		throws java.lang.Exception
	{
		if (null == (_standalonePnLAttribution = standalonePnLAttribution) ||
			null == (_entityComponentCapital = entityComponentCapital)
		)
		{
			throw new java.lang.Exception (
				"AllocatedPnLAttribution Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Entity Component Capital
	 * 
	 * @return The Entity Component Capital
	 */

	public org.drip.capital.allocation.EntityComponentCapital entityComponentCapital()
	{
		return _entityComponentCapital;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> fsPnLDecompositionExplainMap()
	{
		java.util.Map<java.lang.String, java.lang.Double> standaloneFSPnLDecompositionExplainMap =
			_standalonePnLAttribution.fsPnLDecompositionExplainMap();

		if (null == standaloneFSPnLDecompositionExplainMap ||
			0 == standaloneFSPnLDecompositionExplainMap.size())
		{
			return null;
		}

		double allocationScale = _entityComponentCapital.noStressStandaloneMultiplier();

		java.util.Map<java.lang.String, java.lang.Double> fsPnLDecompositionExplainMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double>
			standaloneFSPnLDecompositionExplainMapEntry : standaloneFSPnLDecompositionExplainMap.entrySet())
		{
			fsPnLDecompositionExplainMap.put (
				standaloneFSPnLDecompositionExplainMapEntry.getKey(),
				standaloneFSPnLDecompositionExplainMapEntry.getValue() * allocationScale
			);
		}

		return fsPnLDecompositionExplainMap;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> paaCategoryDecompositionExplainMap()
	{
		java.util.Map<java.lang.String, java.lang.Double> standalonePAACategoryDecompositionExplainMap =
			_standalonePnLAttribution.paaCategoryDecompositionExplainMap();

		if (null == standalonePAACategoryDecompositionExplainMap ||
			0 == standalonePAACategoryDecompositionExplainMap.size())
		{
			return null;
		}

		double allocationScale = _entityComponentCapital.gsstStandaloneMultiplier();

		java.util.Map<java.lang.String, java.lang.Double> paaCategoryDecompositionExplainMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double>
			standalonePAACategoryDecompositionExplainMapEntry :
				standalonePAACategoryDecompositionExplainMap.entrySet())
		{
			paaCategoryDecompositionExplainMap.put (
				standalonePAACategoryDecompositionExplainMapEntry.getKey(),
				standalonePAACategoryDecompositionExplainMapEntry.getValue() * allocationScale
			);
		}

		return paaCategoryDecompositionExplainMap;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> gsstPnLExplainMap()
	{
		java.util.Map<java.lang.String, java.lang.Double> standaloneGSSTPnLExplainMap =
			_standalonePnLAttribution.gsstPnLExplainMap();

		if (null == standaloneGSSTPnLExplainMap || 0 == standaloneGSSTPnLExplainMap.size())
		{
			return null;
		}

		double allocationScale = _entityComponentCapital.gsstStandaloneMultiplier();

		java.util.Map<java.lang.String, java.lang.Double> gsstPnLExplainMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> standaloneGSSTPnLExplainMapEntry :
			standaloneGSSTPnLExplainMap.entrySet())
		{
			gsstPnLExplainMap.put (
				standaloneGSSTPnLExplainMapEntry.getKey(),
				standaloneGSSTPnLExplainMapEntry.getValue() * allocationScale
			);
		}

		return gsstPnLExplainMap;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> gsstGrossPnLExplainMap()
	{
		return null;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> cBSSTPnLExplainMap()
	{
		java.util.Map<java.lang.String, java.lang.Double> standaloneCBSSTPnLExplainMap =
			_standalonePnLAttribution.cBSSTPnLExplainMap();

		if (null == standaloneCBSSTPnLExplainMap || 0 == standaloneCBSSTPnLExplainMap.size())
		{
			return null;
		}

		double allocationScale = _entityComponentCapital.cBSSTStandaloneMultiplier();

		java.util.Map<java.lang.String, java.lang.Double> cBSSTPnLExplainMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> standaloneCBSSTPnLExplainMapEntry :
			standaloneCBSSTPnLExplainMap.entrySet())
		{
			cBSSTPnLExplainMap.put (
				standaloneCBSSTPnLExplainMapEntry.getKey(),
				standaloneCBSSTPnLExplainMapEntry.getValue() * allocationScale
			);
		}

		return cBSSTPnLExplainMap;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> cBSSTPnLWorstMap()
	{
		java.util.Map<java.lang.String, java.lang.Double> standaloneCBSSTPnLWorstMap =
			_standalonePnLAttribution.cBSSTPnLWorstMap();

		if (null == standaloneCBSSTPnLWorstMap || 0 == standaloneCBSSTPnLWorstMap.size())
		{
			return null;
		}

		double allocationScale = _entityComponentCapital.cBSSTStandaloneMultiplier();

		java.util.Map<java.lang.String, java.lang.Double> cBSSTPnLWorstMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> standaloneCBSSTPnLWorstMapEntry :
			standaloneCBSSTPnLWorstMap.entrySet())
		{
			cBSSTPnLWorstMap.put (
				standaloneCBSSTPnLWorstMapEntry.getKey(),
				standaloneCBSSTPnLWorstMapEntry.getValue() * allocationScale
			);
		}

		return cBSSTPnLWorstMap;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> iBSSTPnLExplainMap()
	{
		java.util.Map<java.lang.String, java.lang.Double> standaloneIBSSTPnLExplainMap =
			_standalonePnLAttribution.iBSSTPnLExplainMap();

		if (null == standaloneIBSSTPnLExplainMap || 0 == standaloneIBSSTPnLExplainMap.size())
		{
			return null;
		}

		double allocationScale = _entityComponentCapital.iBSSTStandaloneMultiplier();

		java.util.Map<java.lang.String, java.lang.Double> iBSSTPnLExplainMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> standaloneIBSSTPnLExplainMapEntry :
			standaloneIBSSTPnLExplainMap.entrySet())
		{
			iBSSTPnLExplainMap.put (
				standaloneIBSSTPnLExplainMapEntry.getKey(),
				standaloneIBSSTPnLExplainMapEntry.getValue() * allocationScale
			);
		}

		return iBSSTPnLExplainMap;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> iBSSTPnLWorstMap()
	{
		java.util.Map<java.lang.String, java.lang.Double> standaloneIBSSTPnLWorstMap =
			_standalonePnLAttribution.iBSSTPnLWorstMap();

		if (null == standaloneIBSSTPnLWorstMap || 0 == standaloneIBSSTPnLWorstMap.size())
		{
			return null;
		}

		double allocationScale = _entityComponentCapital.iBSSTStandaloneMultiplier();

		java.util.Map<java.lang.String, java.lang.Double> iBSSTPnLWorstMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> standaloneIBSSTPnLWorstMapEntry :
			standaloneIBSSTPnLWorstMap.entrySet())
		{
			iBSSTPnLWorstMap.put (
				standaloneIBSSTPnLWorstMapEntry.getKey(),
				standaloneIBSSTPnLWorstMapEntry.getValue() * allocationScale
			);
		}

		return iBSSTPnLWorstMap;
	}

	@Override public double var()
	{
		return _entityComponentCapital.noStressStandaloneMultiplier() * super.var();
	}

	@Override public double expectedShortfall()
	{
		return gsstPnL() + cBSSTPnL() + iBSSTGrossPnL() + fsGrossPnL();
	}

	@Override public java.util.Map<java.lang.String, java.lang.Integer> gsstInstanceCountMap()
	{
		return _standalonePnLAttribution.gsstInstanceCountMap();
	}

	@Override public java.util.Map<java.lang.String, java.lang.Integer> cBSSTInstanceCountMap()
	{
		return _standalonePnLAttribution.cBSSTInstanceCountMap();
	}

	@Override public java.util.Map<java.lang.String, java.lang.Integer> iBSSTInstanceCountMap()
	{
		return _standalonePnLAttribution.iBSSTInstanceCountMap();
	}

	@Override public java.util.List<java.lang.Integer> pathIndexList()
	{
		return _standalonePnLAttribution.pathIndexList();
	}

	@Override public int pathCount()
	{
		return _standalonePnLAttribution.pathCount();
	}

	@Override public double gsstPnL()
	{
		return _entityComponentCapital.gsstStandaloneMultiplier() * _standalonePnLAttribution.gsstPnL();
	}

	@Override public double gsstGrossPnL()
	{
		return _entityComponentCapital.gsstStandaloneMultiplier() * _standalonePnLAttribution.gsstGrossPnL();
	}

	@Override public double cBSSTPnL()
	{
		return _entityComponentCapital.cBSSTStandaloneMultiplier() * _standalonePnLAttribution.cBSSTPnL();
	}

	@Override public double iBSSTGrossPnL()
	{
		return _entityComponentCapital.iBSSTStandaloneMultiplier() *
			_standalonePnLAttribution.iBSSTGrossPnL();
	}

	@Override public double fsGrossPnL()
	{
		return _entityComponentCapital.noStressStandaloneMultiplier() *
			_standalonePnLAttribution.fsGrossPnL();
	}
}
