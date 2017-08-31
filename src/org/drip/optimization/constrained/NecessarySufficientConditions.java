
package org.drip.optimization.constrained;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * NecessarySufficientConditions holds the Results of the Verification of the Necessary and the Sufficient
 * 	Conditions at the specified (possibly) Optimal Variate and the corresponding Fritz John Multiplier Suite.
 *  The References are:
 * 
 * 	- Boyd, S., and L. van den Berghe (2009): Convex Optimization, Cambridge University Press, Cambridge UK.
 * 
 * 	- Eustaquio, R., E. Karas, and A. Ribeiro (2008): Constraint Qualification for Nonlinear Programming,
 * 		Technical Report, Federal University of Parana.
 * 
 * 	- Karush, A. (1939): Minima of Functions of Several Variables with Inequalities as Side Constraints,
 * 		M. Sc., University of Chicago, Chicago IL.
 * 
 * 	- Kuhn, H. W., and A. W. Tucker (1951): Nonlinear Programming, Proceedings of the Second Berkeley
 * 		Symposium, University of California, Berkeley CA 481-492.
 * 
 * 	- Ruszczynski, A. (2006): Nonlinear Optimization, Princeton University Press, Princeton NJ.
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
