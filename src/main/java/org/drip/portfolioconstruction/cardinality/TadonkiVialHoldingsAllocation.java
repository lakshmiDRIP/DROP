
package org.drip.portfolioconstruction.cardinality;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
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
 * <i>TadonkiVialHoldingsAllocation</i> holds the Results of the Allocation performed using the Tadonki and
 * 	Vial (2004) Heuristic Scheme. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Chang, T., J., N. Meade, J. E. Beasley, and Y. M. Sharaiha (2000): Heuristics for Cardinality
 * 				Constrained Portfolio Optimization <i>Computers and Operations Research</i> <b>27 (13)</b>
 * 				1271-1302
 *  	</li>
 *  	<li>
 * 			Chvatal, V. (1973): Edmonds Polytopes in a Hierarchy of Combinatorial Problems <i>Discrete
 * 				Mathematics</i> <b>4 (4)</b> 305-337
 *  	</li>
 *  	<li>
 * 			Jobst, N. J., M. D. Horniman, C. A. Lucas, and G. Mitra (2001): Computational Aspects of
 * 				Alternative Portfolio Selection Models in the Presence of Discrete Asset Choice Constraints
 * 				<i>Quantitative Finance</i> <b>1 (5)</b> 1-13
 *  	</li>
 *  	<li>
 * 			Letchford, A. N. and A. Lodi (2002): Strengthening Chvatal-Gomory Cuts and Gomory Fractional Cuts
 * 				<i>Operations Research Letters</i> <b>30 (2)</b> 74-82
 *  	</li>
 *  	<li>
 * 			Tadonki, C., and J. P. Vial (2004): Portfolio Selection with Cardinality and Bound Constraints
 * 				https://www.cri.ensmp.fr/~tadonki/PaperForWeb/Tadonki_PF.pdf
 *  	</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator/README.md">MVO Based Portfolio Allocation Construction</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TadonkiVialHoldingsAllocation
	extends org.drip.portfolioconstruction.allocator.HoldingsAllocation
{
	private org.drip.portfolioconstruction.allocator.HoldingsAllocation _floorPassHoldingsAllocation = null;
	private org.drip.portfolioconstruction.allocator.HoldingsAllocation _firstPrunePassHoldingsAllocation =
		null;
	private org.drip.portfolioconstruction.allocator.HoldingsAllocation _secondPrunePassHoldingsAllocation =
		null;

	/**
	 * Generate a Standard Instance of the Tadonki Vial Holdings Allocation
	 * 
	 * @param holdingsAllocation The Holdings Allocation
	 * 
	 * @return Tadonki Vial Holdings Allocation
	 */

	public static final TadonkiVialHoldingsAllocation Standard (
		final org.drip.portfolioconstruction.allocator.HoldingsAllocation holdingsAllocation)
	{
		try
		{
			return null == holdingsAllocation ? null :
				new org.drip.portfolioconstruction.cardinality.TadonkiVialHoldingsAllocation (
					holdingsAllocation.optimalPortfolio(),
					holdingsAllocation.optimalMetrics()
				);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * TadonkiVialHoldingsAllocation Constructor
	 * 
	 * @param optimalPortfolio The Optimal Portfolio
	 * @param optimalPortfolioMetrics The Optimal Portfolio Metrics
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TadonkiVialHoldingsAllocation (
		final org.drip.portfolioconstruction.asset.Portfolio optimalPortfolio,
		final org.drip.portfolioconstruction.asset.PortfolioMetrics optimalPortfolioMetrics)
		throws java.lang.Exception
	{
		super (
			optimalPortfolio,
			optimalPortfolioMetrics
		);
	}

	/**
	 * Retrieve the Floor Pass Holdings Allocation
	 * 
	 * @return The Floor Pass Holdings Allocation
	 */

	public org.drip.portfolioconstruction.allocator.HoldingsAllocation floorPassHoldingsAllocation()
	{
		return _floorPassHoldingsAllocation;
	}

	/**
	 * Retrieve the First Prune Pass Holdings Allocation
	 * 
	 * @return The First Prune Pass Holdings Allocation
	 */

	public org.drip.portfolioconstruction.allocator.HoldingsAllocation firstPrunePassHoldingsAllocation()
	{
		return _firstPrunePassHoldingsAllocation;
	}

	/**
	 * Retrieve the Second Prune Pass Holdings Allocation
	 * 
	 * @return The Second Prune Pass Holdings Allocation
	 */

	public org.drip.portfolioconstruction.allocator.HoldingsAllocation secondPrunePassHoldingsAllocation()
	{
		return _secondPrunePassHoldingsAllocation;
	}

	/**
	 * Set the Floor Pass Holdings Allocation
	 * 
	 * @param floorPassHoldingsAllocation The Floor Pass Holdings Allocation
	 * 
	 * @return TRUE - The Floor Pass Holdings Allocation successfully set
	 */

	public boolean setFloorPassHoldingsAllocation (
		final org.drip.portfolioconstruction.allocator.HoldingsAllocation floorPassHoldingsAllocation)
	{
		if (null == floorPassHoldingsAllocation)
		{
			return false;
		}

		_floorPassHoldingsAllocation = floorPassHoldingsAllocation;
		return true;
	}

	/**
	 * Set the First Prune Pass Holdings Allocation
	 * 
	 * @param firstPrunePassHoldingsAllocation The First Prune Pass Holdings Allocation
	 * 
	 * @return TRUE - The First Prune Pass Holdings Allocation successfully set
	 */

	public boolean setFirstPrunePassHoldingsAllocation (
		final org.drip.portfolioconstruction.allocator.HoldingsAllocation firstPrunePassHoldingsAllocation)
	{
		if (null == firstPrunePassHoldingsAllocation)
		{
			return false;
		}

		_firstPrunePassHoldingsAllocation = firstPrunePassHoldingsAllocation;
		return true;
	}

	/**
	 * Set the Second Prune Pass Holdings Allocation
	 * 
	 * @param secondPrunePassHoldingsAllocation The Second Prune Pass Holdings Allocation
	 * 
	 * @return TRUE - The Second Prune Pass Holdings Allocation successfully set
	 */

	public boolean setSecondPrunePassHoldingsAllocation (
		final org.drip.portfolioconstruction.allocator.HoldingsAllocation secondPrunePassHoldingsAllocation)
	{
		if (null == secondPrunePassHoldingsAllocation)
		{
			return false;
		}

		_secondPrunePassHoldingsAllocation = secondPrunePassHoldingsAllocation;
		return true;
	}
}
