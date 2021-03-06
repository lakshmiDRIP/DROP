
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
 * <i>IteratedBracket</i> holds the left/right bracket variates and the corresponding values for the
 * objective function during each iteration.
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

public class IteratedBracket {
	private double _dblOFLeft = java.lang.Double.NaN;
	private double _dblOFRight = java.lang.Double.NaN;
	private double _dblVariateLeft = java.lang.Double.NaN;
	private double _dblVariateRight = java.lang.Double.NaN;

	/**
	 * BracketingVariateIterator constructor
	 * 
	 * @param bop Bracketing Output
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public IteratedBracket (
		final org.drip.function.r1tor1solver.BracketingOutput bop)
		throws java.lang.Exception
	{
		if (null == bop) throw new java.lang.Exception ("IteratedBracket constructor: Invalid inputs");

		_dblOFLeft = bop.getOFLeft();

		_dblOFRight = bop.getOFRight();

		_dblVariateLeft = bop.getVariateLeft();

		_dblVariateRight = bop.getVariateRight();
	}

	/**
	 * Retrieve the left variate
	 * 
	 * @return Left Variate
	 */

	public double getVariateLeft()
	{
		return _dblVariateLeft;
	}

	/**
	 * Set the left variate
	 * 
	 * @param dblVariateLeft Left Variate
	 * 
	 * @return TRUE - Left Variate set successfully
	 */

	public boolean setVariateLeft (
		final double dblVariateLeft)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblVariateLeft)) return false;

		_dblVariateLeft = dblVariateLeft;
		return true;
	}

	/**
	 * Retrieve the right variate
	 * 
	 * @return Right Variate
	 */

	public double getVariateRight()
	{
		return _dblVariateRight;
	}

	/**
	 * Set the right variate
	 * 
	 * @param dblVariateRight Right Variate
	 * 
	 * @return TRUE - Right Variate set successfully
	 */

	public boolean setVariateRight (
		final double dblVariateRight)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblVariateRight)) return false;

		_dblVariateRight = dblVariateRight;
		return true;
	}

	/**
	 * Retrieve the left objective function value
	 * 
	 * @return Left Objective Function Value
	 */

	public double getOFLeft()
	{
		return _dblOFLeft;
	}

	/**
	 * Set the left objective function value
	 * 
	 * @param dblOFLeft Left Objective Function Value
	 * 
	 * @return TRUE - Left Objective Function set successfully
	 */

	public boolean setOFLeft (
		final double dblOFLeft)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblOFLeft)) return false;

		_dblOFLeft = dblOFLeft;
		return true;
	}

	/**
	 * Retrieve the right objective function value
	 * 
	 * @return Right objective function value
	 */

	public double getOFRight()
	{
		return _dblOFRight;
	}

	/**
	 * Set the right objective function value
	 * 
	 * @param dblOFRight Right Objective Function Value
	 * 
	 * @return TRUE - Right Objective Function set successfully
	 */

	public boolean setOFRight (
		final double dblOFRight)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblOFRight)) return false;

		_dblOFRight = dblOFRight;
		return true;
	}
}
