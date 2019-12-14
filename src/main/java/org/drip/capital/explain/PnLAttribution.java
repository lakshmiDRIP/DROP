
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
	protected java.util.Map<java.lang.String, java.lang.Double> _cBSSTPnLWorstMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _iBSSTPnLWorstMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _gsstPnLExplainMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _cBSSTPnLExplainMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _iBSSTPnLExplainMap = null;
	protected java.util.Map<java.lang.String, java.lang.Integer> _gsstInstanceCountMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _gsstGrossPnLExplainMap = null;
	protected java.util.Map<java.lang.String, java.lang.Integer> _cBSSTInstanceCountMap = null;
	protected java.util.Map<java.lang.String, java.lang.Integer> _iBSSTInstanceCountMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _fsPnLDecompositionExplainMap = null;
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
	 * Retrieve the GSST PnL Explain Map
	 * 
	 * @return The GSST PnL Explain Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> gsstPnLExplainMap()
	{
		return _gsstPnLExplainMap;
	}

	/**
	 * Retrieve the GSST Gross PnL Explain Map
	 * 
	 * @return The GSST Gross PnL Explain Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> gsstGrossPnLExplainMap()
	{
		return _gsstGrossPnLExplainMap;
	}

	/**
	 * Retrieve the cBSST PnL Explain Map
	 * 
	 * @return The cBSST PnL Explain Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> cBSSTPnLExplainMap()
	{
		return _cBSSTPnLExplainMap;
	}

	/**
	 * Retrieve the cBSST Worst PnL Map
	 * 
	 * @return The cBSST Worst PnL Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> cBSSTPnLWorstMap()
	{
		return _cBSSTPnLWorstMap;
	}

	/**
	 * Retrieve the iBSST PnL Explain Map
	 * 
	 * @return The iBSST PnL Explain Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> iBSSTPnLExplainMap()
	{
		return _iBSSTPnLExplainMap;
	}

	/**
	 * Retrieve the iBSST Worst PnL Map
	 * 
	 * @return The iBSST Worst PnL Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> iBSSTPnLWorstMap()
	{
		return _iBSSTPnLWorstMap;
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
	 * Retrieve the GSST Instance Count Map
	 * 
	 * @return The GSST Instance Count Map
	 */

	public java.util.Map<java.lang.String, java.lang.Integer> gsstInstanceCountMap()
	{
		return _gsstInstanceCountMap;
	}

	/**
	 * Retrieve the cBSST Instance Count Map
	 * 
	 * @return The cBSST Instance Count Map
	 */

	public java.util.Map<java.lang.String, java.lang.Integer> cBSSTInstanceCountMap()
	{
		return _cBSSTInstanceCountMap;
	}

	/**
	 * Retrieve the iBSST Instance Count Map
	 * 
	 * @return The iBSST Instance Count Map
	 */

	public java.util.Map<java.lang.String, java.lang.Integer> iBSSTInstanceCountMap()
	{
		return _iBSSTInstanceCountMap;
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
	 * Retrieve the GSST PnL
	 * 
	 * @return The GSST PnL
	 */

	public double gsstPnL()
	{
		if (null == _gsstPnLExplainMap || 0 == _gsstPnLExplainMap.size())
		{
			return 0.;
		}

		double gsstPnL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> gsstPnLExplainEntry :
			_gsstPnLExplainMap.entrySet())
		{
			gsstPnL = gsstPnL + gsstPnLExplainEntry.getValue();
		}

		return gsstPnL;
	}

	/**
	 * Retrieve the GSST Gross PnL
	 * 
	 * @return The GSST Gross PnL
	 */

	public double gsstGrossPnL()
	{
		if (null == _gsstGrossPnLExplainMap || 0 == _gsstGrossPnLExplainMap.size())
		{
			return 0.;
		}

		double gsstGrossPnL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> gsstGrossPnLExplainEntry :
			_gsstGrossPnLExplainMap.entrySet())
		{
			gsstGrossPnL = gsstGrossPnL + gsstGrossPnLExplainEntry.getValue();
		}

		return gsstGrossPnL;
	}

	/**
	 * Retrieve the cBSST PnL
	 * 
	 * @return The cBSST PnL
	 */

	public double cBSSTPnL()
	{
		if (null == _cBSSTPnLExplainMap || 0 == _cBSSTPnLExplainMap.size())
		{
			return 0.;
		}

		double cBSSTPnL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> cBSSTPnLExplainEntry :
			_cBSSTPnLExplainMap.entrySet())
		{
			cBSSTPnL = cBSSTPnL + cBSSTPnLExplainEntry.getValue();
		}

		return cBSSTPnL;
	}

	/**
	 * Retrieve the iBSST Gross PnL
	 * 
	 * @return The iBSST Gross PnL
	 */

	public double iBSSTGrossPnL()
	{
		if (null == _iBSSTPnLExplainMap || 0 == _iBSSTPnLExplainMap.size())
		{
			return 0.;
		}

		double iBSSTGrossPnL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> iBSSTPnLExplainEntry :
			_iBSSTPnLExplainMap.entrySet())
		{
			iBSSTGrossPnL = iBSSTGrossPnL + iBSSTPnLExplainEntry.getValue();
		}

		return iBSSTGrossPnL;
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
