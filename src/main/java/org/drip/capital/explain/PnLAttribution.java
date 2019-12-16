
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
 * <i>PnLAttribution</i> exposes the Path-Level Capital Component Attributions. The References are:
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

public abstract class PnLAttribution
{
	protected double _var = java.lang.Double.NaN;
	protected double _expectedShortfall = java.lang.Double.NaN;
	protected java.util.Map<java.lang.String, java.lang.Double> _correlatedPnLWorstMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _systemicPnLExplainMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _correlatedPnLExplainMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _idiosyncraticPnLWorstMap = null;
	protected java.util.Map<java.lang.String, java.lang.Integer> _systemicInstanceCountMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _idiosyncraticPnLExplainMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _systemicGrossPnLExplainMap = null;
	protected java.util.Map<java.lang.String, java.lang.Integer> _correlatedInstanceCountMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _fsPnLDecompositionExplainMap = null;
	protected java.util.Map<java.lang.String, java.lang.Integer> _idiosyncraticInstanceCountMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _paaCategoryDecompositionExplainMap = null;

	/**
	 * Retrieve the FS PnL Decomposition Explain Map
	 * 
	 * @return The FS PnL Decomposition Explain Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> fsPnLDecompositionExplainMap()
	{
		return _fsPnLDecompositionExplainMap;
	}

	/**
	 * Retrieve the PAA Category Decomposition Explain Map
	 * 
	 * @return The PAA Category Decomposition Explain Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> paaCategoryDecompositionExplainMap()
	{
		return _paaCategoryDecompositionExplainMap;
	}

	/**
	 * Retrieve the Systemic PnL Explain Map
	 * 
	 * @return The Systemic PnL Explain Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> systemicPnLExplainMap()
	{
		return _systemicPnLExplainMap;
	}

	/**
	 * Retrieve the Systemic Gross PnL Explain Map
	 * 
	 * @return The Systemic Gross PnL Explain Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> systemicGrossPnLExplainMap()
	{
		return _systemicGrossPnLExplainMap;
	}

	/**
	 * Retrieve the Correlated PnL Explain Map
	 * 
	 * @return The Correlated PnL Explain Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> correlatedPnLExplainMap()
	{
		return _correlatedPnLExplainMap;
	}

	/**
	 * Retrieve the Correlated Worst PnL Map
	 * 
	 * @return The Correlated Worst PnL Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> correlatedPnLWorstMap()
	{
		return _correlatedPnLWorstMap;
	}

	/**
	 * Retrieve the Idiosyncratic PnL Explain Map
	 * 
	 * @return The Idiosyncratic PnL Explain Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> idiosyncraticPnLExplainMap()
	{
		return _idiosyncraticPnLExplainMap;
	}

	/**
	 * Retrieve the Idiosyncratic Worst PnL Map
	 * 
	 * @return The Idiosyncratic Worst PnL Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> idiosyncraticPnLWorstMap()
	{
		return _idiosyncraticPnLWorstMap;
	}

	/**
	 * Retrieve the VaR
	 * 
	 * @return VaR
	 */

	public double var()
	{
		return _var;
	}

	/**
	 * Retrieve the Expected Short-fall
	 * 
	 * @return Expected Short-fall
	 */

	public double expectedShortfall()
	{
		return _expectedShortfall;
	}

	/**
	 * Retrieve the Systemic Instance Count Map
	 * 
	 * @return The Systemic Instance Count Map
	 */

	public java.util.Map<java.lang.String, java.lang.Integer> systemicInstanceCountMap()
	{
		return _systemicInstanceCountMap;
	}

	/**
	 * Retrieve the Correlated Instance Count Map
	 * 
	 * @return The Correlated Instance Count Map
	 */

	public java.util.Map<java.lang.String, java.lang.Integer> correlatedInstanceCountMap()
	{
		return _correlatedInstanceCountMap;
	}

	/**
	 * Retrieve the Idiosyncratic Instance Count Map
	 * 
	 * @return The Idiosyncratic Instance Count Map
	 */

	public java.util.Map<java.lang.String, java.lang.Integer> idiosyncraticInstanceCountMap()
	{
		return _idiosyncraticInstanceCountMap;
	}

	/**
	 * Generate the Contributing Path Index List
	 * 
	 * @return Contributing Path Index List
	 */

	public abstract java.util.List<java.lang.Integer> pathIndexList();

	/**
	 * Retrieve the Number of Contributing Paths
	 * 
	 * @return The Number of Contributing Paths
	 */

	public abstract int pathCount();

	/**
	 * Retrieve the Systemic PnL
	 * 
	 * @return The Systemic PnL
	 */

	public double systemicPnL()
	{
		if (null == _systemicPnLExplainMap || 0 == _systemicPnLExplainMap.size())
		{
			return 0.;
		}

		double systemicPnL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> systemicPnLExplainEntry :
			_systemicPnLExplainMap.entrySet())
		{
			systemicPnL = systemicPnL + systemicPnLExplainEntry.getValue();
		}

		return systemicPnL;
	}

	/**
	 * Retrieve the Systemic Gross PnL
	 * 
	 * @return The Systemic Gross PnL
	 */

	public double systemicGrossPnL()
	{
		if (null == _systemicGrossPnLExplainMap || 0 == _systemicGrossPnLExplainMap.size())
		{
			return 0.;
		}

		double systemicGrossPnL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> systemicGrossPnLExplainEntry :
			_systemicGrossPnLExplainMap.entrySet())
		{
			systemicGrossPnL = systemicGrossPnL + systemicGrossPnLExplainEntry.getValue();
		}

		return systemicGrossPnL;
	}

	/**
	 * Retrieve the Correlated PnL
	 * 
	 * @return The Correlated PnL
	 */

	public double correlatedPnL()
	{
		if (null == _correlatedPnLExplainMap || 0 == _correlatedPnLExplainMap.size())
		{
			return 0.;
		}

		double correlatedPnL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> correlatedPnLExplainEntry :
			_correlatedPnLExplainMap.entrySet())
		{
			correlatedPnL = correlatedPnL + correlatedPnLExplainEntry.getValue();
		}

		return correlatedPnL;
	}

	/**
	 * Retrieve the Idiosyncratic Gross PnL
	 * 
	 * @return The Idiosyncratic Gross PnL
	 */

	public double idiosyncraticGrossPnL()
	{
		if (null == _idiosyncraticPnLExplainMap || 0 == _idiosyncraticPnLExplainMap.size())
		{
			return 0.;
		}

		double idiosyncraticGrossPnL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> idiosyncraticPnLExplainEntry :
			_idiosyncraticPnLExplainMap.entrySet())
		{
			idiosyncraticGrossPnL = idiosyncraticGrossPnL + idiosyncraticPnLExplainEntry.getValue();
		}

		return idiosyncraticGrossPnL;
	}

	/**
	 * Retrieve the Gross VaR FS PnL
	 * 
	 * @return The Gross VaR FS PnL
	 */

	public double fsGrossPnL()
	{
		if (null == _fsPnLDecompositionExplainMap || 0 == _fsPnLDecompositionExplainMap.size())
		{
			return 0.;
		}

		double fsGrossPnL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> fsPnLDecompositionExplainEntry :
			_fsPnLDecompositionExplainMap.entrySet())
		{
			fsGrossPnL = fsGrossPnL + fsPnLDecompositionExplainEntry.getValue();
		}

		return fsGrossPnL;
	}
}
