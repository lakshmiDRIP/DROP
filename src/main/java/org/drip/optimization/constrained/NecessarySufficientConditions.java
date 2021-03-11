
package org.drip.optimization.constrained;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * Sufficient Conditions at the specified (possibly) Optimal Variate and the corresponding Fritz John
 * Multiplier Suite. The References are:
 * 
 * <br><br>
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md">Necessary, Sufficient, and Regularity Checks for Gradient Descent and LP/MILP/MINLP Schemes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/constrained/README.md">KKT Fritz-John Constrained Optimizer</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class NecessarySufficientConditions {
	private double[] _adblVariate = null;
	private boolean _bCheckForMinima = false;
	private org.drip.optimization.constrained.FritzJohnMultipliers _fjm = null;
	private org.drip.optimization.necessary.ConditionQualifierFONC _cqFONC = null;
	private org.drip.optimization.necessary.ConditionQualifierSOSC _cqSOSC = null;
	private org.drip.optimization.necessary.ConditionQualifierDualFeasibility _cqDualFeasibility = null;
	private org.drip.optimization.necessary.ConditionQualifierPrimalFeasibility _cqPrimalFeasibility =
		null;
	private org.drip.optimization.necessary.ConditionQualifierComplementarySlackness
		_cqComplementarySlackness = null;

	/**
	 * Create a Standard Instance of NecessarySufficientConditions
	 * 
	 * @param adblVariate The Candidate Variate Array
	 * @param fjm The Fritz John Multipliers
	 * @param bCheckForMinima TRUE - Check For Minima
	 * @param bPrimalFeasibilityValidity The Primal Feasibility Validity
	 * @param bDualFeasibilityValidity The Dual Feasibility Validity
	 * @param bComplementarySlacknessValidity The Complementary Slackness Validity
	 * @param bFONCValidity The FONC Validity
	 * @param bSOSCValidity The SOSC Validity
	 * 
	 * @return The Standard NecessarySufficientConditions Instance
	 */

	public static final NecessarySufficientConditions Standard (
		final double[] adblVariate,
		final org.drip.optimization.constrained.FritzJohnMultipliers fjm,
		final boolean bCheckForMinima,
		final boolean bPrimalFeasibilityValidity,
		final boolean bDualFeasibilityValidity,
		final boolean bComplementarySlacknessValidity,
		final boolean bFONCValidity,
		final boolean bSOSCValidity)
	{
		try {
			return new NecessarySufficientConditions (adblVariate, fjm, bCheckForMinima, new
				org.drip.optimization.necessary.ConditionQualifierPrimalFeasibility
					(bPrimalFeasibilityValidity), new
						org.drip.optimization.necessary.ConditionQualifierDualFeasibility
							(bDualFeasibilityValidity), new
								org.drip.optimization.necessary.ConditionQualifierComplementarySlackness
									(bComplementarySlacknessValidity), new
										org.drip.optimization.necessary.ConditionQualifierFONC
											(bFONCValidity), new
												org.drip.optimization.necessary.ConditionQualifierSOSC
													(bSOSCValidity));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * NecessarySufficientConditions Constructor
	 * 
	 * @param adblVariate The Candidate Variate Array
	 * @param fjm The Fritz John Multipliers
	 * @param bCheckForMinima TRUE - Check For Minima
	 * @param cqPrimalFeasibility The Primal Feasibility Necessary Condition
	 * @param cqDualFeasibility The Dual Feasibility Necessary Condition
	 * @param cqComplementarySlackness The Complementary Slackness Necessary Condition
	 * @param cqFONC The First Order Necessary Condition
	 * @param cqSOSC The Second Order Sufficiency Condition
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NecessarySufficientConditions (
		final double[] adblVariate,
		final org.drip.optimization.constrained.FritzJohnMultipliers fjm,
		final boolean bCheckForMinima,
		final org.drip.optimization.necessary.ConditionQualifierPrimalFeasibility cqPrimalFeasibility,
		final org.drip.optimization.necessary.ConditionQualifierDualFeasibility cqDualFeasibility,
		final org.drip.optimization.necessary.ConditionQualifierComplementarySlackness
			cqComplementarySlackness,
		final org.drip.optimization.necessary.ConditionQualifierFONC cqFONC,
		final org.drip.optimization.necessary.ConditionQualifierSOSC cqSOSC)
		throws java.lang.Exception
	{
		if (null == (_adblVariate = adblVariate) || 0 == _adblVariate.length || null == (_fjm = fjm) || null
			== (_cqPrimalFeasibility = cqPrimalFeasibility) || null == (_cqDualFeasibility =
				cqDualFeasibility) || null == (_cqComplementarySlackness = cqComplementarySlackness) || null
					== (_cqFONC = cqFONC) || null == (_cqSOSC = cqSOSC))
			throw new java.lang.Exception ("NecessarySufficientConditions Constructor => Invalid Inputs");

		_bCheckForMinima = bCheckForMinima;
	}

	/**
	 * Retrieve the Candidate Variate Array
	 * 
	 * @return The Candidate Variate Array
	 */

	public double[] variate()
	{
		return _adblVariate;
	}

	/**
	 * Retrieve the Fritz John Mutipliers
	 * 
	 * @return The Fritz John Mutipliers
	 */

	public org.drip.optimization.constrained.FritzJohnMultipliers fritzJohnMultipliers()
	{
		return _fjm;
	}

	/**
	 * Retrieve if the Check corresponds to Local Minima
	 * 
	 * @return TRUE - The Check corresponds to Local Minima
	 */

	public boolean checkFroMinima()
	{
		return _bCheckForMinima;
	}

	/**
	 * Retrieve the Primal Feasibility Necessary Condition
	 * 
	 * @return The Primal Feasibility Necessary Condition
	 */

	public org.drip.optimization.necessary.ConditionQualifierPrimalFeasibility primalFeasibility()
	{
		return _cqPrimalFeasibility;
	}

	/**
	 * Retrieve the Dual Feasibility Necessary Condition
	 * 
	 * @return The Dual Feasibility Necessary Condition
	 */

	public org.drip.optimization.necessary.ConditionQualifierDualFeasibility dualFeasibility()
	{
		return _cqDualFeasibility;
	}

	/**
	 * Retrieve the Complementary Slackness Necessary Condition
	 * 
	 * @return The Complementary Slackness Necessary Condition
	 */

	public org.drip.optimization.necessary.ConditionQualifierComplementarySlackness
		complementarySlackness()
	{
		return _cqComplementarySlackness;
	}

	/**
	 * Retrieve the First Order Necessary Condition
	 * 
	 * @return The First Order Necessary Condition
	 */

	public org.drip.optimization.necessary.ConditionQualifierFONC fonc()
	{
		return _cqFONC;
	}

	/**
	 * Retrieve the Second Order Sufficiency Condition
	 * 
	 * @return The Second Order Sufficiency Condition
	 */

	public org.drip.optimization.necessary.ConditionQualifierSOSC sosc()
	{
		return _cqSOSC;
	}

	/**
	 * Indicate the Necessary/Sufficient Validity across all the Condition Qualifiers
	 * 
	 * @return TRUE - The Necessary/Sufficient Criteria is satisfied across all the Condition Qualifiers
	 */

	public boolean valid()
	{
		return _cqPrimalFeasibility.valid() && _cqDualFeasibility.valid() &&
			_cqComplementarySlackness.valid() && _cqFONC.valid() && _cqSOSC.valid();
	}

	/**
	 * Retrieve the Array of Condition Orders
	 * 
	 * @return The Array of Condition Orders
	 */

	public java.lang.String[] conditionOrder()
	{
		return new java.lang.String[] {"ZERO ORDER: " + _cqPrimalFeasibility.display() + " >> " +
			_cqDualFeasibility.display() + " >> " + _cqComplementarySlackness.display(), "FIRST ORDER: " +
				_cqFONC.display(), "SECOND ORDER: " + _cqSOSC.display()};
	}
}
