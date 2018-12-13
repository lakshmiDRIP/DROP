
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
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>VariateIterationSelectorParams</i> implements the control parameters for the compound variate selector
 * scheme used in Brent's method.
 * <br><br>
 * Brent's method uses the following fields in VariateIterationSelectorParams to generate the next variate:
 * <br>
 * <ul>
 * 	<li>
 * 		The Variate Primitive that is regarded as the "fast" method
 * 	</li>
 * 	<li>
 * 		The Variate Primitive that is regarded as the "robust" method
 * 	</li>
 * 	<li>
 * 		The relative variate shift that determines when the "robust" method is to be invoked over the "fast"
 * 	</li>
 * 	<li>
 * 		The lower bound on the variate shift between iterations that serves as the fall-back to the "robust"
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/r1tor1solver/README.md">R<sup>1</sup> To R<sup>1</sup> Solver</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class VariateIterationSelectorParams {
	private int _iFastIteratorPrimitive = -1;
	private int _iRobustIteratorPrimitive = -1;
	private double _dblRelativeVariateShift = java.lang.Double.NaN;
	private double _dblVariateShiftLowerBound = java.lang.Double.NaN;

	/**
	 * Default VariateIterationSelectorParams constructor
	 */

	public VariateIterationSelectorParams()
	{
		_dblRelativeVariateShift = 0.5;
		_dblVariateShiftLowerBound = 0.01;
		_iRobustIteratorPrimitive = org.drip.function.r1tor1solver.VariateIteratorPrimitive.BISECTION;
		_iFastIteratorPrimitive =
			org.drip.function.r1tor1solver.VariateIteratorPrimitive.INVERSE_QUADRATIC_INTERPOLATION;
	}

	/**
	 * VariateIterationSelectorParams constructor
	 * 
	 * @param dblRelativeVariateShift Relative Variate Shift
	 * @param dblVariateShiftLowerBound Variant Shift Lower Bound
	 * @param iFastIteratorPrimitive Fast Iterator Primitive
	 * @param iRobustIteratorPrimitive Robust Iterator Primitive
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public VariateIterationSelectorParams (
		final double dblRelativeVariateShift,
		final double dblVariateShiftLowerBound,
		final int iFastIteratorPrimitive,
		final int iRobustIteratorPrimitive)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblRelativeVariateShift = dblRelativeVariateShift) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblVariateShiftLowerBound =
				dblVariateShiftLowerBound))
			throw new java.lang.Exception ("VariateIterationSelectorParams constructor: Invalid inputs!");
	}

	/**
	 * Retrieve the relative variate Shift
	 * 
	 * @return Relative variate Shift
	 */

	public double getRelativeVariateShift()
	{
		return _dblRelativeVariateShift;
	}

	/**
	 * Retrieve the Variate Shift lower bound
	 * 
	 * @return Variate Shift lower bound
	 */

	public double getVariateShiftLowerBound()
	{
		return _dblVariateShiftLowerBound;
	}

	/**
	 * Retrieve the variate iterator primitive meant for speed
	 * 
	 * @return variate iterator primitive meant for speed
	 */

	public int getFastVariateIteratorPrimitive()
	{
		return _iFastIteratorPrimitive;
	}

	/**
	 * Retrieve the variate iterator primitive meant for robustness
	 * 
	 * @return variate iterator primitive meant for robustness
	 */

	public int getRobustVariateIteratorPrimitive()
	{
		return _iRobustIteratorPrimitive;
	}
}
