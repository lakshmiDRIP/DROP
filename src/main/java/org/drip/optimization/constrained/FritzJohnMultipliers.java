
package org.drip.optimization.constrained;

import org.drip.numerical.common.NumberUtil;

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
 * <i>FritzJohnMultipliers</i> holds the Array of the Fritz John/KKT Multipliers for the Array of the
 * 	Equality and the Inequality Constraints, one per each Constraint. It provides the following Functions:
 * 	<ul>
 * 		<li>Construct a Standard KarushKuhnTucker (KKT) Instance of the Fritz John Multipliers</li>
 * 		<li><i>FritzJohnMultipliers</i> Constructor</li>
 * 		<li>Retrieve the Fritz John Objective Function Multiplier</li>
 * 		<li>Retrieve the Array of the Equality Constraint Coefficients</li>
 * 		<li>Retrieve the Array of the Inequality Constraint Coefficients</li>
 * 		<li>Retrieve the Number of Equality Multiplier Coefficients</li>
 * 		<li>Retrieve the Number of Inequality Multiplier Coefficients</li>
 * 		<li>Retrieve the Number of Total KKT Multiplier Coefficients</li>
 * 		<li>Indicate of the Multipliers constitute Valid Dual Feasibility</li>
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

public class FritzJohnMultipliers
{
	private double _objectiveCoefficient = Double.NaN;
	private double[] _equalityConstraintCoefficientArray = null;
	private double[] _inequalityConstraintCoefficientArray = null;

	/**
	 * Construct a Standard KarushKuhnTucker (KKT) Instance of the Fritz John Multipliers
	 * 
	 * @param equalityConstraintCoefficientArray Array of the Equality Constraint Coefficients
	 * @param inequalityConstraintCoefficientArray Array of the Inequality Constraint Coefficients
	 * 
	 * @return The KKT Instance of Fritz John Multipliers
	 */

	public static final FritzJohnMultipliers KarushKuhnTucker (
		final double[] equalityConstraintCoefficientArray,
		final double[] inequalityConstraintCoefficientArray)
	{
		try {
			return new FritzJohnMultipliers (
				1.,
				equalityConstraintCoefficientArray,
				inequalityConstraintCoefficientArray
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>FritzJohnMultipliers</i> Constructor
	 * 
	 * @param objectiveCoefficient The Objective Function Coefficient
	 * @param equalityConstraintCoefficientArray Array of the Equality Constraint Coefficients
	 * @param inequalityConstraintCoefficientArray Array of the Inequality Constraint Coefficients
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public FritzJohnMultipliers (
		final double objectiveCoefficient,
		final double[] equalityConstraintCoefficientArray,
		final double[] inequalityConstraintCoefficientArray)
		throws Exception
	{
		if (!NumberUtil.IsValid (_objectiveCoefficient = objectiveCoefficient)) {
			throw new Exception ("FritzJohnMultipliers Constructor => Invalid Inputs");
		}

		_equalityConstraintCoefficientArray = equalityConstraintCoefficientArray;
		_inequalityConstraintCoefficientArray = inequalityConstraintCoefficientArray;
	}

	/**
	 * Retrieve the Fritz John Objective Function Multiplier
	 * 
	 * @return The Fritz John Objective Function Multiplier
	 */

	public double objectiveCoefficient()
	{
		return _objectiveCoefficient;
	}

	/**
	 * Retrieve the Array of the Equality Constraint Coefficients
	 * 
	 * @return The Array of the Equality Constraint Coefficients
	 */

	public double[] equalityConstraintCoefficientArray()
	{
		return _equalityConstraintCoefficientArray;
	}

	/**
	 * Retrieve the Array of the Inequality Constraint Coefficients
	 * 
	 * @return The Array of the Inequality Constraint Coefficients
	 */

	public double[] inequalityConstraintCoefficientArray()
	{
		return _inequalityConstraintCoefficientArray;
	}

	/**
	 * Retrieve the Number of Equality Multiplier Coefficients
	 * 
	 * @return The Number of Equality Multiplier Coefficients
	 */

	public int numEqualityCoefficients()
	{
		return null == _equalityConstraintCoefficientArray ? 0 : _equalityConstraintCoefficientArray.length;
	}

	/**
	 * Retrieve the Number of Inequality Multiplier Coefficients
	 * 
	 * @return The Number of Inequality Multiplier Coefficients
	 */

	public int numInequalityCoefficients()
	{
		return null == _inequalityConstraintCoefficientArray ?
			0 : _inequalityConstraintCoefficientArray.length;
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
		for (int inequalityCoefficient = 0;
			inequalityCoefficient < numInequalityCoefficients();
			++inequalityCoefficient)
		{
			if (0. > _inequalityConstraintCoefficientArray[inequalityCoefficient]) {
				return false;
			}
		}

		return true;
	}
}
