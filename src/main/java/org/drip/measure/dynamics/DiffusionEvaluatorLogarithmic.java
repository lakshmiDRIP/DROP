
package org.drip.measure.dynamics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>DiffusionEvaluatorLogarithmic</i> evaluates the Drift/Volatility of the Diffusion Random Variable
 * Evolution according to R<sup>1</sup> Logarithmic Process.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/dynamics/README.md">Jump Diffusion Evolution Evaluator Variants</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DiffusionEvaluatorLogarithmic extends org.drip.measure.dynamics.DiffusionEvaluator {
	private double _dblDrift = java.lang.Double.NaN;
	private double _dblVolatility = java.lang.Double.NaN;

	/**
	 * Generate a Standard Instance of DiffusionEvaluatorLogarithmic
	 * 
	 * @param dblDrift The Drift
	 * @param dblVolatility The Volatility
	 * 
	 * @return The Standard Instance of DiffusionEvaluatorLogarithmic
	 */

	public static final DiffusionEvaluatorLogarithmic Standard (
		final double dblDrift,
		final double dblVolatility)
	{
		org.drip.measure.dynamics.LocalEvaluator leDrift = new org.drip.measure.dynamics.LocalEvaluator() {
			@Override public double value (
				final org.drip.measure.realization.JumpDiffusionVertex jdv)
				throws java.lang.Exception
			{
				if (null == jdv)
					throw new java.lang.Exception
						("DiffusionEvaluatorLogarithmic::DriftEvaluator::value => Invalid Inputs");

				return jdv.value() * dblDrift;
			}
		};

		org.drip.measure.dynamics.LocalEvaluator leVolatility = new
			org.drip.measure.dynamics.LocalEvaluator() {
			@Override public double value (
				final org.drip.measure.realization.JumpDiffusionVertex jdv)
				throws java.lang.Exception
			{
				if (null == jdv)
					throw new java.lang.Exception
						("DiffusionEvaluatorLogarithmic::volatilityEvaluator::value => Invalid Inputs");

				return jdv.value() * dblVolatility;
			}
		};

		try {
			return new DiffusionEvaluatorLogarithmic (dblDrift, dblVolatility, leDrift, leVolatility);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private DiffusionEvaluatorLogarithmic (
		final double dblDrift,
		final double dblVolatility,
		final org.drip.measure.dynamics.LocalEvaluator leDrift,
		final org.drip.measure.dynamics.LocalEvaluator leVolatility)
		throws java.lang.Exception
	{
		super (leDrift, leVolatility);

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblDrift = dblDrift) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblVolatility = dblVolatility))
			throw new java.lang.Exception ("DiffusionEvaluatorLogarithmic Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Logarithmic Drift Value
	 * 
	 * @return The Logarithmic Drift Value
	 */

	public double driftValue()
	{
		return _dblDrift;
	}

	/**
	 * Retrieve the Logarithmic Volatility Value
	 * 
	 * @return The Logarithmic Volatility Value
	 */

	public double volatilityValue()
	{
		return _dblVolatility;
	}
}
