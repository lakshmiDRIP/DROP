
package org.drip.measure.dynamics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>DiffusionEvaluatorLinear</i> implements the Linear Drift and Volatility Evaluators for R<sup>1</sup>
 * Random Diffusion Process.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/dynamics">Dynamics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DiffusionEvaluatorLinear extends org.drip.measure.dynamics.DiffusionEvaluator {
	private double _dblDrift = java.lang.Double.NaN;
	private double _dblVolatility = java.lang.Double.NaN;

	/**
	 * Generate a Standard Instance of DiffusionEvaluatorLinear
	 * 
	 * @param dblDrift The Drift
	 * @param dblVolatility The Volatility
	 * 
	 * @return The Standard Instance of DiffusionEvaluatorLinear
	 */

	public static final DiffusionEvaluatorLinear Standard (
		final double dblDrift,
		final double dblVolatility)
	{
		org.drip.measure.dynamics.LocalEvaluator leDrift = new org.drip.measure.dynamics.LocalEvaluator() {
			@Override public double value (
				final org.drip.measure.realization.JumpDiffusionVertex jdv)
				throws java.lang.Exception
			{
				return dblDrift;
			}
		};

		org.drip.measure.dynamics.LocalEvaluator leVolatility = new
			org.drip.measure.dynamics.LocalEvaluator() {
			@Override public double value (
				final org.drip.measure.realization.JumpDiffusionVertex jdv)
				throws java.lang.Exception
			{
				return dblVolatility;
			}
		};

		try {
			return new DiffusionEvaluatorLinear (dblDrift, dblVolatility, leDrift, leVolatility);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private DiffusionEvaluatorLinear (
		final double dblDrift,
		final double dblVolatility,
		final org.drip.measure.dynamics.LocalEvaluator leDrift,
		final org.drip.measure.dynamics.LocalEvaluator leVolatility)
		throws java.lang.Exception
	{
		super (leDrift, leVolatility);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblDrift = dblDrift) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblVolatility = dblVolatility))
			throw new java.lang.Exception ("DiffusionEvaluatorLinear Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Linear Drift Value
	 * 
	 * @return The Linear Drift Value
	 */

	public double driftValue()
	{
		return _dblDrift;
	}

	/**
	 * Retrieve the Linear Volatility Value
	 * 
	 * @return The Linear Volatility Value
	 */

	public double volatilityValue()
	{
		return _dblVolatility;
	}
}
