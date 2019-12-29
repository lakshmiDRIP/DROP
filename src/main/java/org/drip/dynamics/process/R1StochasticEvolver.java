
package org.drip.dynamics.process;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>R1StochasticEvolver</i> implements the R<sup>1</sup> Stochastic Evolver. The References are:
 *  
 * 	<br><br>
 *  <ul>
 * 		<li>
 * 			Doob, J. L. (1942): The Brownian Movement and Stochastic Equations <i>Annals of Mathematics</i>
 * 				<b>43 (2)</b> 351-369
 * 		</li>
 * 		<li>
 * 			Gardiner, C. W. (2009): <i>Stochastic Methods: A Handbook for the Natural and Social Sciences
 * 				4<sup>th</sup> Edition</i> <b>Springer-Verlag</b>
 * 		</li>
 * 		<li>
 * 			Kadanoff, L. P. (2000): <i>Statistical Physics: Statics, Dynamics, and Re-normalization</i>
 * 				<b>World Scientific</b>
 * 		</li>
 * 		<li>
 * 			Karatzas, I., and S. E. Shreve (1991): <i>Brownian Motion and Stochastic Calculus 2<sup>nd</sup>
 * 				Edition</i> <b>Springer-Verlag</b>
 * 		</li>
 * 		<li>
 * 			Risken, H., and F. Till (1996): <i>The Fokker-Planck Equation – Methods of Solution and
 * 				Applications</i> <b>Springer</b>
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">HJM, Hull White, LMM, and SABR Dynamic Evolution Models</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/process/README.md">Ito-Dynamics Based Stochastic Process</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1StochasticEvolver
{
	private org.drip.dynamics.ito.R1ToR1Drift _driftFunction = null;
	private org.drip.dynamics.ito.R1ToR1Volatility _volatilityFunction = null;
	private org.drip.dynamics.ito.R1StochasticDriver _stochasticDriver = null;

	/**
	 * R1StochasticEvolver Constructor
	 * 
	 * @param driftFunction Drift Function
	 * @param volatilityFunction Volatility Function
	 * @param stochasticDriver Stochastic Driver
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1StochasticEvolver (
		final org.drip.dynamics.ito.R1ToR1Drift driftFunction,
		final org.drip.dynamics.ito.R1ToR1Volatility volatilityFunction,
		final org.drip.dynamics.ito.R1StochasticDriver stochasticDriver)
		throws java.lang.Exception
	{
		if (null == (_driftFunction = driftFunction) ||
			null == (_volatilityFunction = volatilityFunction) ||
			null == (_stochasticDriver = stochasticDriver))
		{
			throw new java.lang.Exception (
				"R1StochasticEvolver Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Drift Function
	 * 
	 * @return The Drift Function
	 */

	public org.drip.dynamics.ito.R1ToR1Drift driftFunction()
	{
		return _driftFunction;
	}

	/**
	 * Retrieve the Volatility Function
	 * 
	 * @return The Volatility Function
	 */

	public org.drip.dynamics.ito.R1ToR1Volatility volatilityFunction()
	{
		return _volatilityFunction;
	}

	/**
	 * Retrieve the Stochastic Driver
	 * 
	 * @return The Stochastic Driver
	 */

	public org.drip.dynamics.ito.R1StochasticDriver stochasticDriver()
	{
		return _stochasticDriver;
	}

	/**
	 * Generate the Next Vertex in the Iteration
	 * 
	 * @param currentVertex The Current Vertex
	 * @param timeIncrement The Time Increment
	 * 
	 * @return The Next Vertex
	 */

	public org.drip.dynamics.ito.TimeR1Vertex evolve (
		final org.drip.dynamics.ito.TimeR1Vertex currentVertex,
		final double timeIncrement)
	{
		if (null == currentVertex ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				timeIncrement
			)
		)
		{
			return null;
		}

		try
		{
			return new org.drip.dynamics.ito.TimeR1Vertex (
				currentVertex.t() + timeIncrement,
				currentVertex.x() + _driftFunction.drift (
					currentVertex
				) * timeIncrement + _volatilityFunction.volatility (
					currentVertex
				) * _stochasticDriver.emitSingle()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Estimate the Temporal Central Measures for the Underlier given the Delta 0 Starting PDF
	 * 
	 * @param x0 The X Anchor for the Delta Function
	 * @param t The Forward Time
	 * 
	 * @return The Temporal Central Measures for the Underlier
	 */

	public org.drip.measure.statistics.PopulationCentralMeasures temporalPopulationCentralMeasures (
		final double x0,
		final double t)
	{
		return null;
	}

	/**
	 * Generate the Steady State Population Central Measures
	 * 
	 * @param x0 Starting Variate
	 * 
	 * @return The Steady State Population Central Measures
	 */

	public org.drip.measure.statistics.PopulationCentralMeasures steadyStatePopulationCentralMeasures (
		final double x0)
	{
		return null;
	}

	/**
	 * Construct the Fokker Planck PDF Generator corresponding to R<sup>1</sup> Stochastic Evolver
	 * 
	 * @return The Fokker Planck PDF Generator corresponding to R<sup>1</sup> Stochastic Evolver
	 */

	public org.drip.dynamics.kolmogorov.R1FokkerPlanck fokkerPlanckGenerator()
	{
		try
		{
			new org.drip.dynamics.kolmogorov.R1FokkerPlanck (
				_driftFunction,
				_volatilityFunction
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Future Value Distribution at Time t
	 * 
	 * @param x0 Starting Variate
	 * @param t Time
	 * 
	 * @return The Future Value Distribution
	 */

	public org.drip.measure.continuous.R1Univariate futureValueDistribution (
		final double x0,
		final double t)
	{
		return null;
	}
}
