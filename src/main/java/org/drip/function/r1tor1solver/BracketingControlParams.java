
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
 * <i>BracketingControlParams</i> implements the control parameters for bracketing solutions.
 * <br>
 * BracketingControlParams provides the following parameters:
 * <ul>
 * 	<li>
 * 			The starting variate from which the search for bracketing begins
 * 	</li>
 * 	<li>
 * 			The initial width for the brackets
 * 	</li>
 * 	<li>
 * 			The factor by which the width expands with each iterative search
 * 	</li>
 * 	<li>
 * 			The number of such iterations.
 * 	</li>
 * </ul>
 * <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function">Function</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver">R<sup>1</sup> To R<sup>1</sup></a> Solver</li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BracketingControlParams {

	/*
	 * Bracket Determination Parameters
	 */

	private int _iNumExpansions = 0;
	private double _dblVariateStart = java.lang.Double.NaN;
	private double _dblBracketStartingWidth = java.lang.Double.NaN;
	private double _dblBracketWidthExpansionFactor = java.lang.Double.NaN;

	/**
	 * Default BracketingControlParams constructor
	 */

	public BracketingControlParams()
	{
		_dblVariateStart = 0.;
		_iNumExpansions = 100;
		_dblBracketStartingWidth = 1.e-06;
		_dblBracketWidthExpansionFactor = 2.;
	}

	/**
	 * BracketingControlParams constructor
	 * 
	 * @param iNumExpansions Number of bracket expansions to determine the bracket
	 * @param dblVariateStart Variate start for the bracket determination
	 * @param dblBracketStartingWidth Base Bracket Width
	 * @param dblBracketWidthExpansionFactor Bracket width expansion factor
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public BracketingControlParams (
		final int iNumExpansions,
		final double dblVariateStart,
		final double dblBracketStartingWidth,
		final double dblBracketWidthExpansionFactor)
		throws java.lang.Exception
	{
		if (0 >= (_iNumExpansions = iNumExpansions) || !org.drip.quant.common.NumberUtil.IsValid
			(_dblVariateStart = dblVariateStart) || !org.drip.quant.common.NumberUtil.IsValid
				(_dblBracketStartingWidth = dblBracketStartingWidth) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblBracketWidthExpansionFactor =
						dblBracketWidthExpansionFactor))
			throw new java.lang.Exception ("BracketingControlParams constructor: Invalid inputs!");
	}

	/**
	 * Return the number of expansions
	 * 
	 * @return Number of expansions
	 */

	public int getNumExpansions()
	{
		return _iNumExpansions;
	}

	/**
	 * Return the starting point of bracketing determination
	 * 
	 * @return Starting point of bracketing determination
	 */

	public double getVariateStart()
	{
		return _dblVariateStart;
	}

	/**
	 * Return the initial bracket width
	 * 
	 * @return Initial bracket width
	 */

	public double getStartingBracketWidth()
	{
		return _dblBracketStartingWidth;
	}

	/**
	 * Return the bracket width expansion factor
	 * 
	 * @return Bracket width expansion factor
	 */

	public double getBracketWidthExpansionFactor()
	{
		return _dblBracketWidthExpansionFactor;
	}
}
