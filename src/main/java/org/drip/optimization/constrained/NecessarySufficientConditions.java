
package org.drip.optimization.constrained;

import org.drip.optimization.necessary.ConditionQualifierComplementarySlackness;
import org.drip.optimization.necessary.ConditionQualifierDualFeasibility;
import org.drip.optimization.necessary.ConditionQualifierFONC;
import org.drip.optimization.necessary.ConditionQualifierPrimalFeasibility;
import org.drip.optimization.necessary.ConditionQualifierSOSC;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 * <i>NecessarySufficientConditions</i> holds the Results of the Verification of the Necessary and the
 * 	Sufficient Conditions at the specified (possibly) Optimal Variate and the corresponding Fritz John
 * 	Multiplier Suite. It provides the following Functions:
 * 	<ul>
 * 		<li>Create a Standard Instance of <i>NecessarySufficientConditions</i></li>
 * 		<li><i>NecessarySufficientConditions</i> Constructor</li>
 * 		<li>Retrieve the Candidate Variate Array</li>
 * 		<li>Retrieve the Fritz John Mutipliers</li>
 * 		<li>Retrieve if the Check corresponds to Local Minima</li>
 * 		<li>Retrieve the Primal Feasibility Necessary Condition</li>
 * 		<li>Retrieve the Dual Feasibility Necessary Condition</li>
 * 		<li>Retrieve the Complementary Slackness Necessary Condition</li>
 * 		<li>Retrieve the First Order Necessary Condition</li>
 * 		<li>Retrieve the Second Order Sufficiency Condition</li>
 * 		<li>Indicate the Necessary/Sufficient Validity across all the Condition Qualifiers</li>
 * 		<li>Retrieve the Array of Condition Orders</li>
 * 	</ul>
 * 
 * The References are:
 * <br>
 * 	<ul>
 * 		<li>
 * 			Boyd, S., and L. van den Berghe (2009): <i>Convex Optimization</i> <b>Cambridge University
 * 				Press</b> Cambridge UK
 * 		</li>
 * 		<li>
 * 			Eustaquio, R., E. Karas, and A. Ribeiro (2008): <i>Constraint Qualification for Nonlinear
 * 				Programming</i> <b>Federal University of Parana</b>
 * 		</li>
 * 		<li>
 * 			Karush, A. (1939): <i>Minima of Functions of Several Variables with Inequalities as Side
 * 			Constraints</i> <b>University of Chicago</b> Chicago IL
 * 		</li>
 * 		<li>
 * 			Kuhn, H. W., and A. W. Tucker (1951): Nonlinear Programming <i>Proceedings of the Second Berkeley
 * 				Symposium</i> <b>University of California</b> Berkeley CA 481-492
 * 		</li>
 * 		<li>
 * 			Ruszczynski, A. (2006): <i>Nonlinear Optimization</i> <b>Princeton University Press</b> Princeton
 * 				NJ
 * 		</li>
 * 	</ul>
 *
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md">Necessary, Sufficient, and Regularity Checks for Gradient Descent and LP/MILP/MINLP Schemes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/constrained/README.md">KKT Fritz-John Constrained Optimizer</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class NecessarySufficientConditions
{
	private boolean _checkForMinima = false;
	private double[] _candidateVariateArray = null;
	private FritzJohnMultipliers _fritzJohnMultipliers = null;
	private ConditionQualifierFONC _foncConditionQualifier = null;
	private ConditionQualifierSOSC _soscConditionQualifier = null;
	private ConditionQualifierDualFeasibility _dualFeasibilityConditionQualifier = null;
	private ConditionQualifierPrimalFeasibility _primalFeasibilityConditionQualifier = null;
	private ConditionQualifierComplementarySlackness _complementarySlacknessConditionQualifier = null;

	/**
	 * Create a Standard Instance of <i>NecessarySufficientConditions</i>
	 * 
	 * @param candidateVariateArray The Candidate Variate Array
	 * @param fritzJohnMultipliers The Fritz John Multipliers
	 * @param checkForMinima TRUE - Check For Minima
	 * @param primalFeasibilityValidity The Primal Feasibility Validity Indicator
	 * @param dualFeasibilityValidity The Dual Feasibility Validity Indicator
	 * @param complementarySlacknessValidity The Complementary Slackness Validity Indicator
	 * @param foncValidity The FONC Validity Indicator
	 * @param soscValidity The SOSC Validity Indicator
	 * 
	 * @return The Standard <i>NecessarySufficientConditions</i> Instance
	 */

	public static final NecessarySufficientConditions Standard (
		final double[] candidateVariateArray,
		final FritzJohnMultipliers fritzJohnMultipliers,
		final boolean checkForMinima,
		final boolean primalFeasibilityValidity,
		final boolean dualFeasibilityValidity,
		final boolean complementarySlacknessValidity,
		final boolean foncValidity,
		final boolean soscValidity)
	{
		try {
			return new NecessarySufficientConditions (
				candidateVariateArray,
				fritzJohnMultipliers,
				checkForMinima,
				new
				ConditionQualifierPrimalFeasibility (primalFeasibilityValidity),
				new ConditionQualifierDualFeasibility (dualFeasibilityValidity),
				new ConditionQualifierComplementarySlackness (complementarySlacknessValidity),
				new ConditionQualifierFONC (foncValidity),
				new ConditionQualifierSOSC (soscValidity)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>NecessarySufficientConditions</i> Constructor
	 * 
	 * @param candidateVariateArray The Candidate Variate Array
	 * @param fritzJohnMultipliers The Fritz John Multipliers
	 * @param checkForMinima TRUE - Check For Minima
	 * @param primalFeasibilityConditionQualifier The Primal Feasibility Necessary Condition
	 * @param dualFeasibilityConditionQualifier The Dual Feasibility Necessary Condition
	 * @param complementarySlacknessConditionQualifier The Complementary Slackness Necessary Condition
	 * @param foncConditionQualifier The First Order Necessary Condition
	 * @param soscConditionQualifier The Second Order Sufficiency Condition
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public NecessarySufficientConditions (
		final double[] candidateVariateArray,
		final FritzJohnMultipliers fritzJohnMultipliers,
		final boolean checkForMinima,
		final ConditionQualifierPrimalFeasibility primalFeasibilityConditionQualifier,
		final ConditionQualifierDualFeasibility dualFeasibilityConditionQualifier,
		final ConditionQualifierComplementarySlackness complementarySlacknessConditionQualifier,
		final ConditionQualifierFONC foncConditionQualifier,
		final ConditionQualifierSOSC soscConditionQualifier)
		throws Exception
	{
		if (null == (_candidateVariateArray = candidateVariateArray) || 0 == _candidateVariateArray.length ||
			null == (_fritzJohnMultipliers = fritzJohnMultipliers) ||
			null == (_primalFeasibilityConditionQualifier = primalFeasibilityConditionQualifier) ||
			null == (_dualFeasibilityConditionQualifier = dualFeasibilityConditionQualifier) ||
			null == (_complementarySlacknessConditionQualifier = complementarySlacknessConditionQualifier) ||
			null == (_foncConditionQualifier = foncConditionQualifier) ||
			null == (_soscConditionQualifier = soscConditionQualifier))
		{
			throw new Exception ("NecessarySufficientConditions Constructor => Invalid Inputs");
		}

		_checkForMinima = checkForMinima;
	}

	/**
	 * Retrieve the Candidate Variate Array
	 * 
	 * @return The Candidate Variate Array
	 */

	public double[] candidateVariateArray()
	{
		return _candidateVariateArray;
	}

	/**
	 * Retrieve the Fritz John Mutipliers
	 * 
	 * @return The Fritz John Mutipliers
	 */

	public FritzJohnMultipliers fritzJohnMultipliers()
	{
		return _fritzJohnMultipliers;
	}

	/**
	 * Retrieve if the Check corresponds to Local Minima
	 * 
	 * @return TRUE - The Check corresponds to Local Minima
	 */

	public boolean checkForMinima()
	{
		return _checkForMinima;
	}

	/**
	 * Retrieve the Primal Feasibility Necessary Condition
	 * 
	 * @return The Primal Feasibility Necessary Condition
	 */

	public ConditionQualifierPrimalFeasibility primalFeasibility()
	{
		return _primalFeasibilityConditionQualifier;
	}

	/**
	 * Retrieve the Dual Feasibility Necessary Condition
	 * 
	 * @return The Dual Feasibility Necessary Condition
	 */

	public ConditionQualifierDualFeasibility dualFeasibility()
	{
		return _dualFeasibilityConditionQualifier;
	}

	/**
	 * Retrieve the Complementary Slackness Necessary Condition
	 * 
	 * @return The Complementary Slackness Necessary Condition
	 */

	public ConditionQualifierComplementarySlackness complementarySlackness()
	{
		return _complementarySlacknessConditionQualifier;
	}

	/**
	 * Retrieve the First Order Necessary Condition
	 * 
	 * @return The First Order Necessary Condition
	 */

	public ConditionQualifierFONC fonc()
	{
		return _foncConditionQualifier;
	}

	/**
	 * Retrieve the Second Order Sufficiency Condition
	 * 
	 * @return The Second Order Sufficiency Condition
	 */

	public ConditionQualifierSOSC sosc()
	{
		return _soscConditionQualifier;
	}

	/**
	 * Indicate the Necessary/Sufficient Validity across all the Condition Qualifiers
	 * 
	 * @return TRUE - The Necessary/Sufficient Criteria is satisfied across all the Condition Qualifiers
	 */

	public boolean valid()
	{
		return _primalFeasibilityConditionQualifier.valid() &&
			_dualFeasibilityConditionQualifier.valid() &&
			_complementarySlacknessConditionQualifier.valid() &&
			_foncConditionQualifier.valid() &&
			_soscConditionQualifier.valid();
	}

	/**
	 * Retrieve the Array of Condition Orders
	 * 
	 * @return The Array of Condition Orders
	 */

	public String[] conditionOrder()
	{
		return new String[] {
			"ZERO ORDER: " + _primalFeasibilityConditionQualifier.display() + " >> " +
				_dualFeasibilityConditionQualifier.display() + " >> " +
				_complementarySlacknessConditionQualifier.display(),
			"FIRST ORDER: " + _foncConditionQualifier.display(),
			"SECOND ORDER: " + _soscConditionQualifier.display()
		};
	}
}
