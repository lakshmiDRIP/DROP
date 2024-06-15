
package org.drip.numerical.linearalgebra;

import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
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
 * <i>SuccessiveOverRelaxation</i> implements the SOR and the SSOR schemes. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Greenbaum, A. (1997): <i>Iterative Methods for Solving Linear Systems</i> <b>Society for
 * 				Industrial and Applied Mathematics</b> Philadelphia, PA
 * 		</li>
 * 		<li>
 * 			Hackbusch, W. (2016): <i>Iterative Solution of Large Sparse Systems of Equations</i>
 * 				<b>Spring-Verlag</b> Berlin, Germany
 * 		</li>
 * 		<li>
 * 			Wikipedia (2023): Symmetric Successive Over-Relaxation
 * 				https://en.wikipedia.org/wiki/Symmetric_successive_over-relaxation
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Successive Over-Relaxation
 * 				https://en.wikipedia.org/wiki/Successive_over-relaxation
 * 		</li>
 * 		<li>
 * 			Young, D. M. (1950): <i>Iterative methods for solving partial difference equations of elliptical
 * 				type</i> <b>Harvard University</b> Cambridge, MA
 * 		</li>
 * 	</ul>
 * 
 * It provides the following functionality:
 *
 *  <ul>
 * 		<li>Construct the R<sup>1</sup> To R<sup>1</sup> Bessel First Kind Frobenius Summation Series</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/linearalgebra/README.md">Linear Algebra Matrix Transform Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SuccessiveOverRelaxationSolverSetting
{
	private static final int SOR_ITERATION_LIMIT = 100;
	private static final double RELAXATION_PARAMETER = 0.5;
	private static final double ABSOLUTE_TOLERANCE = 1.0e-08;
	private static final double RELATIVE_TOLERANCE = 1.0e-05;
	private static final double ABSOLUTE_LEVEL_THRESOLD = 1.0e-06;

	private double _absoluteTolerance = Double.NaN;
	private double _relativeTolerance = Double.NaN;
	private double _relaxationParameter = Double.NaN;
	private double _iterationLimit = Integer.MIN_VALUE;
	private double _absoluteLevelThreshold = Double.NaN;

	/**
	 * Construct a Factory Standard Instance of <i>SuccessiveOverRelaxationSolverSetting</i>
	 * 
	 * @return Factory Standard Instance of <i>SuccessiveOverRelaxationSolverSetting</i>
	 */

	public static final SuccessiveOverRelaxationSolverSetting Standard()
	{
		try {
			return new SuccessiveOverRelaxationSolverSetting (
				ABSOLUTE_TOLERANCE,
				RELATIVE_TOLERANCE,
				ABSOLUTE_LEVEL_THRESOLD,
				RELAXATION_PARAMETER,
				SOR_ITERATION_LIMIT
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of <i>SuccessiveOverRelaxationSolverSetting</i> from the Inputs
	 * 
	 * @param absoluteTolerance Absolute Tolerance for Convergence
	 * @param relativeTolerance Relative Tolerance for Convergence
	 * @param absoluteLevelThreshold Threshold for Absolute Convergence Check
	 * @param relaxationParameter SOR Relaxation Parameter
	 * @param iterationLimit Iteration Limit for SOR Convergence
	 * 
	 * @throws Exception Thrown if the Parameters are Invalid
	 */

	public SuccessiveOverRelaxationSolverSetting (
		final double absoluteTolerance,
		final double relativeTolerance,
		final double absoluteLevelThreshold,
		final double relaxationParameter,
		final int iterationLimit)
		throws Exception
	{
		if (!NumberUtil.IsValid (_absoluteTolerance = absoluteTolerance) || 0. >= _absoluteTolerance ||
			!NumberUtil.IsValid (_relativeTolerance = relativeTolerance) || 0. >= _relativeTolerance ||
			!NumberUtil.IsValid (_absoluteLevelThreshold = absoluteLevelThreshold) ||
				0. >= _absoluteLevelThreshold ||
			!NumberUtil.IsValid (_relaxationParameter = relaxationParameter) ||
				0. >= _relaxationParameter || 2. <= _relaxationParameter ||
			0 >= (_iterationLimit = iterationLimit)
			)
		{
			throw new Exception ("SuccessiveOverRelaxationSolverSetting Constructor => Invalid Parameters");
		}
	}

	/**
	 * Retrieve the Absolute Tolerance for Convergence
	 * 
	 * @return Absolute Tolerance for Convergence
	 */

	public double absoluteTolerance()
	{
		return _absoluteTolerance;
	}

	/**
	 * Retrieve the Relative Tolerance for Convergence
	 * 
	 * @return Relative Tolerance for Convergence
	 */

	public double relativeTolerance()
	{
		return _relativeTolerance;
	}

	/**
	 * Retrieve the Threshold for Absolute Convergence Check
	 * 
	 * @return Threshold for Absolute Convergence Check
	 */

	public double absoluteLevelThreshold()
	{
		return _absoluteLevelThreshold;
	}

	/**
	 * Retrieve the SOR Relaxation Parameter
	 * 
	 * @return SOR Relaxation Parameter
	 */

	public double relaxationParameter()
	{
		return _relaxationParameter;
	}

	/**
	 * Retrieve the Iteration Limit for SOR Convergence
	 * 
	 * @return Iteration Limit for SOR Convergence
	 */

	public double iterationLimit()
	{
		return _iterationLimit;
	}
}
