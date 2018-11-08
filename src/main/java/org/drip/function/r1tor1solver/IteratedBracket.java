
package org.drip.function.r1tor1solver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * 	<i>IteratedBracket</i> holds the left/right bracket variates and the corresponding values for the
 * objective function during each iteration.
 * <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function">Function</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver">R<sup>1</sup> To R<sup>1</sup></a> Solver</li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariateLeft)) return false;

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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariateRight)) return false;

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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblOFLeft)) return false;

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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblOFRight)) return false;

		_dblOFRight = dblOFRight;
		return true;
	}
}
