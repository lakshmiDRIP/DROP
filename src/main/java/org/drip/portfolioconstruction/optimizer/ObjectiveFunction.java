
package org.drip.portfolioconstruction.optimizer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy0
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
 * <i>ObjectiveFunction</i> holds the Terms composing the Objective Function and their Weights.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer/README.md">Core Portfolio Construction Optimizer Suite</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ObjectiveFunction
	extends org.drip.function.definition.RdToR1
{
	private int _dimension = -1;

	private java.util.List<org.drip.portfolioconstruction.optimizer.ObjectiveTermUnit> _objectiveTermUnitList
		= new java.util.ArrayList<org.drip.portfolioconstruction.optimizer.ObjectiveTermUnit>();

	/**
	 * Empty Objective Function Constructor
	 */

	public ObjectiveFunction()
	{
		super (
			null
		);
	}

	/**
	 * Add the Objective Term Unit Instance
	 * 
	 * @param objectiveTermUnit The Objective Term Unit Instance
	 * 
	 * @return TRUE - The Objective Term Unit Instance successfully added
	 */

	public boolean addObjectiveTermUnit (
		final org.drip.portfolioconstruction.optimizer.ObjectiveTermUnit objectiveTermUnit)
	{
		if (null == objectiveTermUnit || _objectiveTermUnitList.contains (
			objectiveTermUnit
		))
		{
			return false;
		}

		_objectiveTermUnitList.add (
			objectiveTermUnit
		);

		return true;
	}

	/**
	 * Retrieve the List of Objective Terms
	 * 
	 * @return The List of Objective Terms
	 */

	public java.util.List<org.drip.portfolioconstruction.optimizer.ObjectiveTermUnit> list()
	{
		return _objectiveTermUnitList;
	}

	@Override public int dimension()
	{
		return _dimension;
	}

	@Override public double evaluate (
		final double[] variateArray)
		throws java.lang.Exception
	{
		double value = 0.;

		for (org.drip.portfolioconstruction.optimizer.ObjectiveTermUnit objectiveTermUnit :
			_objectiveTermUnitList)
		{
			if (objectiveTermUnit.isActive())
			{
				value += objectiveTermUnit.weight() * objectiveTermUnit.objectiveTerm().rdtoR1().evaluate (
					variateArray
				);
			}
		}

		return value;
	}

	@Override public double derivative (
		final double[] variateArray,
		final int variateIndex,
		final int order)
		throws java.lang.Exception
	{
		double derivative = 0.;

		for (org.drip.portfolioconstruction.optimizer.ObjectiveTermUnit objectiveTermUnit :
			_objectiveTermUnitList)
		{
			if (objectiveTermUnit.isActive())
			{
				derivative += objectiveTermUnit.weight() *
					objectiveTermUnit.objectiveTerm().rdtoR1().derivative (
						variateArray,
						variateIndex,
						order
					);
			}
		}

		return derivative;
	}
}
