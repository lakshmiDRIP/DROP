
package org.drip.function.r1tor1solver;

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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>ExecutionControlParams</i> holds the parameters needed for controlling the execution of the fixed point
 * finder.
 * <br><br>
 * ExecutionControlParams fields control the fixed point search in one of the following ways:
 * <br>
 * <ul>
 * 	<li>
 * 		Number of iterations after which the search is deemed to have failed
 * 	</li>
 * 	<li>
 * 		Relative Objective Function Tolerance Factor which, when reached by the objective function, will
 * 			indicate that the fixed point has been reached
 * 	</li>
 * 	<li>
 * 		Variate Convergence Factor, factor applied to the initial variate to determine the absolute
 * 			convergence
 * 	</li>
 * 	<li>
 * 		Absolute Tolerance fall-back, which is used to determine that the fixed point has been reached when
 * 			the relative tolerance factor becomes zero
 * 	</li>
 * 	<li>
 * 		Absolute Variate Convergence Fall-back, fall-back used to determine if the variate has converged.
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/README.md">Built-in R<sup>1</sup> To R<sup>1</sup> Solvers</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ExecutionControlParams {
	private int _iNumIterations = 0;
	private boolean _bIsVariateConvergenceCheckEnabled = false;
	private double _dblOFGoalToleranceFactor = java.lang.Double.NaN;
	private double _dblVariateConvergenceFactor = java.lang.Double.NaN;
	private double _dblAbsoluteOFToleranceFallback = java.lang.Double.NaN;
	private double _dblAbsoluteVariateConvergenceFallback = java.lang.Double.NaN;

	/**
	 * Default Execution Control Parameters constructor
	 */

	public ExecutionControlParams()
	{
		_iNumIterations = 200;
		_dblOFGoalToleranceFactor = 1.0e-07;
		_dblVariateConvergenceFactor = 1.0e-07;
		_dblAbsoluteOFToleranceFallback = 1.0e-08;
		_dblAbsoluteVariateConvergenceFallback = 1.0e-08;
	}

	/**
	 * Execution Control Parameters constructor
	 * 
	 * @param iNumIterations Number of Iterations
	 * @param bIsVariateConvergenceCheckEnabled Flag indicating if the variate convergence check is on
	 * @param dblOFGoalToleranceFactor Tolerance factor for the OF Goal
	 * @param dblVariateConvergenceFactor Variate Convergence Factor
	 * @param dblAbsoluteOFToleranceFallback Absolute Tolerance Fall-back
	 * @param dblAbsoluteVariateConvergenceFallback Absolute Variate Convergence fall-back
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public ExecutionControlParams (
		final int iNumIterations,
		final boolean bIsVariateConvergenceCheckEnabled,
		final double dblOFGoalToleranceFactor,
		final double dblVariateConvergenceFactor,
		final double dblAbsoluteOFToleranceFallback,
		final double dblAbsoluteVariateConvergenceFallback)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblOFGoalToleranceFactor = dblOFGoalToleranceFactor)
			|| !org.drip.numerical.common.NumberUtil.IsValid (_dblVariateConvergenceFactor =
				dblVariateConvergenceFactor) || !org.drip.numerical.common.NumberUtil.IsValid
					(_dblAbsoluteOFToleranceFallback = dblAbsoluteOFToleranceFallback) ||
						!org.drip.numerical.common.NumberUtil.IsValid (_dblAbsoluteVariateConvergenceFallback =
							dblAbsoluteVariateConvergenceFallback) || 0 >= (_iNumIterations =
								iNumIterations))
			throw new java.lang.Exception ("ExecutionControlParams constructor: Invalid inputs");

		_bIsVariateConvergenceCheckEnabled = bIsVariateConvergenceCheckEnabled;
	}

	/**
	 * Return the number of iterations allowed
	 * 
	 * @return Number of iterations
	 */

	public int getNumIterations()
	{
		return _iNumIterations;
	}

	/**
	 * Return the tolerance factor for the OF Goal
	 * 
	 * @return Tolerance factor for the OF Goal
	 */

	public double getOFGoalToleranceFactor()
	{
		return _dblOFGoalToleranceFactor;
	}

	/**
	 * Return the Variate Convergence Factor
	 * 
	 * @return Variate Convergence Factor
	 */

	public double getVariateConvergenceFactor()
	{
		return _dblVariateConvergenceFactor;
	}

	/**
	 * Return the Fall-back absolute tolerance for the OF
	 * 
	 * @return Fall-back absolute tolerance for the OF
	 */

	public double getAbsoluteOFToleranceFallback()
	{
		return _dblAbsoluteOFToleranceFallback;
	}

	/**
	 * Return the fall-back absolute variate convergence
	 * 
	 * @return Fall-back absolute variate convergence
	 */

	public double getAbsoluteVariateConvergenceFallback()
	{
		return _dblAbsoluteVariateConvergenceFallback;
	}

	/**
	 * Indicate if the variate convergence check has been turned on
	 * 
	 * @return TRUE - Variate convergence check has been turned on
	 */

	public boolean isVariateConvergenceCheckEnabled()
	{
		return _bIsVariateConvergenceCheckEnabled;
	}
}
