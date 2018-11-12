
package org.drip.optimization.constrained;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization">Optimization</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/constrained">Constrained</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Analytics</a></li>
 *  </ul>
 * <br><br>
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
