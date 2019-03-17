
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
 * <i>FritzJohnMultipliers</i> holds the Array of the Fritz John/KKT Multipliers for the Array of the
 * Equality and the Inequality Constraints, one per each Constraint. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization">Optimization</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/constrained">Constrained</a></li>
 *  </ul>
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblObjectiveCoefficient = dblObjectiveCoefficient))
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
