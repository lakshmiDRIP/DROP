
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
 * <i>ConvergenceControlParams</i> holds the fields needed for the controlling the execution of Newton's
 * method.
 * <br>
 * ConvergenceControlParams does that using the following parameters:
 * <ul>
 * 	<li>
 * 		The determinant limit below which the convergence zone is deemed to have been reached.
 * 	</li>
 * 	<li>
 * 		Starting variate from where the convergence search is kicked off.
 * 	</li>
 * 	<li>
 * 		The factor by which the variate expands across each iterative search.
 * 	</li>
 * 	<li>
 * 		The number of search iterations.
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

public class ConvergenceControlParams {
	/*
	 * Convergence Zone Locator Determination
	 */

	private int _iFixedPointConvergenceIterations = 0;
	private double _dblConvergenceZoneEdgeLimit = java.lang.Double.NaN;
	private double _dblConvergenceZoneVariateBegin = java.lang.Double.NaN;
	private double _dblConvergenceZoneVariateBumpFactor = java.lang.Double.NaN;

	/**
	 * Default Convergence Control Parameters constructor
	 */

	public ConvergenceControlParams()
	{
		/*
		 * Convergence Zone Locator Determination Initialization
		 */

		_iFixedPointConvergenceIterations = 100;
		_dblConvergenceZoneEdgeLimit = 0.01;
		_dblConvergenceZoneVariateBegin = 1.0e-30;
		_dblConvergenceZoneVariateBumpFactor = 3.;
	}

	/**
	 * ConvergenceControlParams constructor
	 * 
	 * @param iFixedPointConvergenceIterations Iterations to locate a variate inside the convergence zone
	 * @param dblConvergenceZoneVariateBegin Starting variate for convergence zone determination
	 * @param dblConvergenceZoneEdgeLimit Convergence zone edge limit
	 * @param dblConvergenceZoneVariateBumpFactor Convergence Zone Variate Bump Factor
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public ConvergenceControlParams (
		final int iFixedPointConvergenceIterations,
		final double dblConvergenceZoneVariateBegin,
		final double dblConvergenceZoneEdgeLimit,
		final double dblConvergenceZoneVariateBumpFactor)
		throws java.lang.Exception
	{
		if (0 >= (_iFixedPointConvergenceIterations = iFixedPointConvergenceIterations) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblConvergenceZoneVariateBegin =
				dblConvergenceZoneVariateBegin) || !org.drip.numerical.common.NumberUtil.IsValid
					(_dblConvergenceZoneEdgeLimit = dblConvergenceZoneEdgeLimit) ||
						!org.drip.numerical.common.NumberUtil.IsValid (_dblConvergenceZoneVariateBumpFactor =
							dblConvergenceZoneVariateBumpFactor))
			throw new java.lang.Exception ("ConvergenceControlParams constructor: Invalid inputs");
	}

	/**
	 * Return the number of fixed point convergence iterations
	 * 
	 * @return Number of fixed point convergence iterations
	 */

	public int getFixedPointConvergenceIterations()
	{
		return _iFixedPointConvergenceIterations;
	}

	/**
	 * Return the limit of the fixed point convergence zone edge
	 * 
	 * @return Limit of fixed point convergence zone edge
	 */

	public double getConvergenceZoneEdgeLimit()
	{
		return _dblConvergenceZoneEdgeLimit;
	}

	/**
	 * Return the start of the fixed point convergence variate
	 * 
	 * @return Start of the fixed point convergence variate
	 */

	public double getConvergenceZoneVariateBegin()
	{
		return _dblConvergenceZoneVariateBegin;
	}

	/**
	 * Return the bump factor for the fixed point convergence variate iteration
	 * 
	 * @return Bump factor for the fixed point convergence variate iteration
	 */

	public double getConvergenceZoneVariateBumpFactor()
	{
		return _dblConvergenceZoneVariateBumpFactor;
	}
}
