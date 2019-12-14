
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
 * <i>FSPnLDecompositionContainer</i> holds the Series of Decomposed FS PnL's. The References are:
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

public class FSPnLDecompositionContainer
{
	private java.util.List<org.drip.capital.simulation.FSPnLDecomposition> _fsPnLDecompositionList = null;

	/**
	 * Generate a Standard Instance of FSPnLDecompositionContainer
	 * 
	 * @param notional The Notional
	 * @param count Count of the PnL List 
	 * 
	 * @return Standard Instance of FSPnLDecompositionContainer
	 */

	public static final FSPnLDecompositionContainer Standard (
		final double notional,
		final int count)
	{
		if (0 >= count)
		{
			return null;
		}

		java.util.List<org.drip.capital.simulation.FSPnLDecomposition> fsPnLDecompositionList = new
			java.util.ArrayList<org.drip.capital.simulation.FSPnLDecomposition>();

		for (int index = 0; index < count; ++index)
		{
			fsPnLDecompositionList.add (
				org.drip.capital.simulation.FSPnLDecomposition.Standard (notional)
			);
		}

		try
		{
			return new FSPnLDecompositionContainer (fsPnLDecompositionList);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * FSPnLDecompositionContainer Constructor
	 * 
	 * @param fsPnLDecompositionList List of FS PnL Decomposition
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FSPnLDecompositionContainer (
		final java.util.List<org.drip.capital.simulation.FSPnLDecomposition> fsPnLDecompositionList)
		throws java.lang.Exception
	{
		if (null == (_fsPnLDecompositionList = fsPnLDecompositionList))
		{
			throw new java.lang.Exception ("FSPnLDecompositionContainer Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the List of FS PnL Decomposition
	 * 
	 * @return List of FS PnL Decomposition
	 */

	public java.util.List<org.drip.capital.simulation.FSPnLDecomposition> fsPnLDecompositionList()
	{
		return _fsPnLDecompositionList;
	}

	/**
	 * Retrieve the Count of the PnL List
	 * 
	 * @return Count of the PnL List
	 */

	public int count()
	{
		return null == _fsPnLDecompositionList ? 0 : _fsPnLDecompositionList.size();
	}

	/**
	 * Apply the FS Type Specific Volatility Scaling to the PnL Decomposition
	 * 
	 * @param fsTypeAdjustmentMap FS Type Volatility Adjustment Map
	 * 
	 * @return FS Type Specific Volatility Adjusted List
	 */

	public java.util.List<java.util.Map<java.lang.String, java.lang.Double>> applyVolatilityAdjustment (
		final java.util.Map<java.lang.String, java.lang.Double> fsTypeAdjustmentMap)
	{
		java.util.List<java.util.Map<java.lang.String, java.lang.Double>>
			volatilityAdjustedFSPnLDecompositionList = new
				java.util.ArrayList<java.util.Map<java.lang.String, java.lang.Double>>();

		for (org.drip.capital.simulation.FSPnLDecomposition pnlFSDecomposition : _fsPnLDecompositionList)
		{
			java.util.Map<java.lang.String, java.lang.Double> volatilityAdjustedFSPnLDecomposition =
				pnlFSDecomposition.applyVolatilityAdjustment (
					fsTypeAdjustmentMap,
					1.
				);

			if (null == volatilityAdjustedFSPnLDecomposition)
			{
				return null;
			}

			volatilityAdjustedFSPnLDecompositionList.add (volatilityAdjustedFSPnLDecomposition);
		}

		return volatilityAdjustedFSPnLDecompositionList;
	}
}
