
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
 * FritzJohnMultipliers holds the Array of the Fritz John/KKT Multipliers for the Array of the Equality and
 *  the Inequality Constraints, one per each Constraint. The References are:
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

public class FritzJohnMultipliers {
	private double[] _adblEquality = null;
	private double[] _adblInequality = null;
	private double _dblObjectiveCoefficient = java.lang.Double.NaN;

	/**
	 * Construct a Standard KarushKuhnTucker (KKT) Instance of the Fritz John Multipliers
	 * 
	 * @param adblEquality Array of the Equality Constraint Coefficients
	 * @param adblInequality Array of the Inequality Constraint Coefficients
	 * 
	 * @return The KKT Instance of Fritz John Multipliers
	 */

	public static final FritzJohnMultipliers KarushKuhnTucker (
		final double[] adblEquality,
		final double[] adblInequality)
	{
		try {
			return new FritzJohnMultipliers (1., adblEquality, adblInequality);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * FritzJohnMultipliers Constructor
	 * 
	 * @param dblObjectiveCoefficient The Objective Function Coefficient
	 * @param adblEquality Array of the Equality Constraint Coefficients
	 * @param adblInequality Array of the Inequality Constraint Coefficients
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FritzJohnMultipliers (
		final double dblObjectiveCoefficient,
		final double[] adblEquality,
		final double[] adblInequality)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblObjectiveCoefficient = dblObjectiveCoefficient))
			throw new java.lang.Exception ("FritzJohnMultipliers Constructor => Invalid Inputs");

		_adblEquality = adblEquality;
		_adblInequality = adblInequality;
	}

	/**
	 * Retrieve the Fritz John Objective Function Multiplier
	 * 
	 * @return The Fritz John Objective Function Multiplier
	 */

	public double objectiveCoefficient()
	{
		return _dblObjectiveCoefficient;
	}

	/**
	 * Retrieve the Array of the Equality Constraint Coefficients
	 * 
	 * @return The Array of the Equality Constraint Coefficients
	 */

	public double[] equalityConstraintCoefficient()
	{
		return _adblEquality;
	}

	/**
	 * Retrieve the Array of the Inequality Constraint Coefficients
	 * 
	 * @return The Array of the Inequality Constraint Coefficients
	 */

	public double[] inequalityConstraintCoefficient()
	{
		return _adblInequality;
	}

	/**
	 * Retrieve the Number of Equality Multiplier Coefficients
	 * 
	 * @return The Number of Equality Multiplier Coefficients
	 */

	public int numEqualityCoefficients()
	{
		return null == _adblEquality ? 0 : _adblEquality.length;
	}

	/**
	 * Retrieve the Number of Inequality Multiplier Coefficients
	 * 
	 * @return The Number of Inequality Multiplier Coefficients
	 */

	public int numInequalityCoefficients()
	{
		return null == _adblInequality ? 0 : _adblInequality.length;
	}

	/**
	 * Retrieve the Number of Total KKT Multiplier Coefficients
	 * 
	 * @return The Number of Total KKT Multiplier Coefficients
	 */

	public int numTotalCoefficients()
	{
		return numEqualityCoefficients() + numInequalityCoefficients();
	}

	/**
	 * Indicate of the Multipliers constitute Valid Dual Feasibility
	 * 
	 * @return TRUE - The Multipliers constitute Valid Dual Feasibility
	 */

	public boolean dualFeasibilityCheck()
	{
		int iNumInequalityCoefficient = numInequalityCoefficients();

		for (int i = 0; i < iNumInequalityCoefficient; ++i) {
			if (0. > _adblInequality[i]) return false;
		}

		return true;
	}
}
