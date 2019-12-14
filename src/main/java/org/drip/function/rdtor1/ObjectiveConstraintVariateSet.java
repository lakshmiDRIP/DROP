
package org.drip.function.rdtor1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>ObjectiveConstraintVariateSet</i> holds a R<sup>d</sup> To R<sup>1</sup> Variates corresponding to the
 * Objective Function and the Constraint Function respectively.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1/README.md">Built-in R<sup>d</sup> To R<sup>1</sup> Functions</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ObjectiveConstraintVariateSet {
	private double[] _adblObjectiveVariate = null;
	private double[] _adblConstraintVariate = null;

	/**
	 * Make a Unitary Variate Set
	 * 
	 * @param iNumVariate Number of Variates
	 * 
	 * @return Unitary Variate Set
	 */

	public static final double[] Unitary (
		final int iNumVariate)
	{
		if (0 >= iNumVariate) return null;

		double[] adblVariate = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i)
			adblVariate[i] = 1.;

		return adblVariate;
	}

	/**
	 * Make a Variate Set with/without Constraint
	 * 
	 * @param iNumObjectiveFunctionVariate Number of the Objective Function Variates
	 * @param iNumConstraintFunctionVariate Number of the Constraint Function Variates
	 * 
	 * @return Variate Set with/without Constraint
	 */

	public static final double[] Uniform (
		final int iNumObjectiveFunctionVariate,
		final int iNumConstraintFunctionVariate)
	{
		if (0 >= iNumObjectiveFunctionVariate) return null;

		double[] adblVariate = new double[iNumObjectiveFunctionVariate + iNumConstraintFunctionVariate];

		for (int i = 0; i < iNumObjectiveFunctionVariate; ++i)
			adblVariate[i] = 1. / iNumObjectiveFunctionVariate;

		for (int i = 0; i < iNumConstraintFunctionVariate; ++i)
			adblVariate[i + iNumObjectiveFunctionVariate] = 0.;

		return adblVariate;
	}

	/**
	 * Make a Variate Set using a Pre-set Objective Variate Array with/without Constraint
	 * 
	 * @param adblObjectiveFunctionVariate Array of Pre-set Objective Variates
	 * @param iNumConstraintFunctionVariate Number of the Constraint Function Variates
	 * 
	 * @return Variate Set using a Pre-set Objective Variate Array with/without Constraint
	 */

	public static final double[] Preset (
		final double[] adblObjectiveFunctionVariate,
		final int iNumConstraintFunctionVariate)
	{
		if (null == adblObjectiveFunctionVariate) return null;

		int iNumObjectiveFunctionVariate = adblObjectiveFunctionVariate.length;

		if (0 >= iNumObjectiveFunctionVariate) return null;

		double[] adblVariate = new double[iNumObjectiveFunctionVariate + iNumConstraintFunctionVariate];

		for (int i = 0; i < iNumObjectiveFunctionVariate; ++i)
			adblVariate[i] = adblObjectiveFunctionVariate[i];

		for (int i = 0; i < iNumConstraintFunctionVariate; ++i)
			adblVariate[i + iNumObjectiveFunctionVariate] = 0.;

		return adblVariate;
	}

	/**
	 * Partition the Variate Array into the Objective Function Input Variates and the Constraint Variate
	 * 
	 * @param adblVariate The Input Variate Array
	 * @param iNumObjectiveFunctionVariate Number of the Objective Function Variates
	 * 
	 * @return The ObjectiveConstraintVariateSet Instance
	 */

	public static final ObjectiveConstraintVariateSet Partition (
		final double[] adblVariate,
		final int iNumObjectiveFunctionVariate)
	{
		if (null == adblVariate || 0 == iNumObjectiveFunctionVariate) return null;

		int iNumVariate = adblVariate.length;
		double[] adblObjectiveVariate = new double[iNumObjectiveFunctionVariate];
		double[] adblConstraintVariate = new double[iNumVariate - iNumObjectiveFunctionVariate];

		if (iNumObjectiveFunctionVariate >= iNumVariate) return null;

		for (int i = 0; i < iNumObjectiveFunctionVariate; ++i)
			adblObjectiveVariate[i] = adblVariate[i];

		for (int i = iNumObjectiveFunctionVariate; i < iNumVariate; ++i)
			adblConstraintVariate[i - iNumObjectiveFunctionVariate] = adblVariate[i];

		try {
			return new ObjectiveConstraintVariateSet (adblObjectiveVariate, adblConstraintVariate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ObjectiveConstraintVariate Constructor
	 * 
	 * @param adblObjectiveVariate Array of the Objective Function Variates
	 * @param adblConstraintVariate Array of the Constraint Function Variates
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ObjectiveConstraintVariateSet (
		final double[] adblObjectiveVariate,
		final double[] adblConstraintVariate)
		throws java.lang.Exception
	{
		if (null == (_adblObjectiveVariate = adblObjectiveVariate) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_adblObjectiveVariate) || null ==
				(_adblConstraintVariate = adblConstraintVariate) || !org.drip.numerical.common.NumberUtil.IsValid
					(adblConstraintVariate))
			throw new java.lang.Exception ("ObjectiveConstraintVariateSet Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Array of the Objective Function Variates
	 * 
	 * @return The Array of the Objective Function Variates
	 */

	public double[] objectiveVariates()
	{
		return _adblObjectiveVariate;
	}

	/**
	 * Retrieve the Array of the Constraint Function Variates
	 * 
	 * @return The Array of the Constraint Function Variates
	 */

	public double[] constraintVariates()
	{
		return _adblConstraintVariate;
	}

	/**
	 * Unify the Objective Function and the Constraint Function Input Variate Set
	 * 
	 * @return The Unified Objective Function and the Constraint Function Input Variate Set
	 */

	public double[] unify()
	{
		int iNumObjectiveFunctionVariate = _adblObjectiveVariate.length;
		int iNumConstraintFunctionVariate = _adblConstraintVariate.length;
		int iNumVariate = iNumObjectiveFunctionVariate + iNumConstraintFunctionVariate;
		double[] adblVariate = new double[iNumVariate];

		for (int i = 0; i < iNumObjectiveFunctionVariate; ++i)
			adblVariate[i] = _adblObjectiveVariate[i];

		for (int i = 0; i < iNumConstraintFunctionVariate; ++i)
			adblVariate[iNumObjectiveFunctionVariate + i] = _adblConstraintVariate[i];

		return adblVariate;
	}
}
