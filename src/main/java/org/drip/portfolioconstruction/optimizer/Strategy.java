
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
 * <i>Strategy</i> holds the Details of a given Strategy.
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

public class Strategy
	extends org.drip.portfolioconstruction.core.Block
{
	private boolean _allowCrossOver = false;
	private boolean _ignoreCompliance = false;
	private boolean _allowGrandFathering = false;
	private org.drip.portfolioconstruction.optimizer.ObjectiveFunction _objectiveFunction = null;
	private org.drip.portfolioconstruction.optimizer.ConstraintHierarchy _constraintHierarchy = null;

	/**
	 * Strategy Constructor
	 * 
	 * @param name The Constraint Name
	 * @param id The Constraint ID
	 * @param description The Constraint Description
	 * @param objectiveFunction The Objective Function
	 * @param constraintHierarchy Constraint Hierarchy
	 * @param allowGrandFathering TRUE - Grand-fathering of the "Previous" is to be performed
	 * @param allowCrossOver TRUE - Cross-Over is allowed
	 * @param ignoreCompliance TRUE - Ignore Compliance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Strategy (
		final java.lang.String name,
		final java.lang.String id,
		final java.lang.String description,
		final org.drip.portfolioconstruction.optimizer.ObjectiveFunction objectiveFunction,
		final org.drip.portfolioconstruction.optimizer.ConstraintHierarchy constraintHierarchy,
		final boolean allowGrandFathering,
		final boolean allowCrossOver,
		final boolean ignoreCompliance)
		throws java.lang.Exception
	{
		super (
			name,
			id,
			description
		);

		_constraintHierarchy = constraintHierarchy;
		_allowCrossOver = allowCrossOver;
		_ignoreCompliance = ignoreCompliance;
		_allowGrandFathering = allowGrandFathering;

		if (null == (_objectiveFunction = objectiveFunction))
		{
			throw new java.lang.Exception (
				"Strategy Construtor => Invalid Inputs"
			);
		}
	}

	/**
	 * Indicate if Grand-fathering of the "Previous" is to be performed
	 * 
	 * @return TRUE - Grand-fathering of the "Previous" is to be performed
	 */

	public boolean allowGrandFathering()
	{
		return _allowGrandFathering;
	}

	/**
	 * Indicate if Cross Over is allowed
	 * 
	 * @return TRUE - Cross-Over is allowed
	 */

	public boolean allowCrossOver()
	{
		return _allowCrossOver;
	}

	/**
	 * Indicate if Compliance Checks are to be ignored
	 * 
	 * @return TRUE - Compliance Checks are to be ignored
	 */

	public boolean ignoreCompliance()
	{
		return _ignoreCompliance;
	}

	/**
	 * Retrieve the Objective Function
	 * 
	 * @return The Objective Function
	 */

	public org.drip.portfolioconstruction.optimizer.ObjectiveFunction objectiveFunction()
	{
		return _objectiveFunction;
	}

	/**
	 * Retrieve the Constraint Hierarchy
	 * 
	 * @return The Constraint Hierarchy
	 */

	public org.drip.portfolioconstruction.optimizer.ConstraintHierarchy constraintHierarchy()
	{
		return _constraintHierarchy;
	}

	/**
	 * Retrieve the Array of Constraint Attributes
	 * 
	 * @return The Array of Constraint Attributes
	 */

	public double[] constraintAttributes()
	{
		return null;
	}
}
