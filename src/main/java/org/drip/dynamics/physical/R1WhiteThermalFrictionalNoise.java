
package org.drip.dynamics.physical;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
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
 * <i>R1WhiteThermalFrictionalNoise</i> implements the Volatility Function induced by the Background Thermal
 * 	Noise in a Friction-Elastic System. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">Dynamics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm/README.md">LIBOR Market Model</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1WhiteThermalFrictionalNoise
	extends org.drip.dynamics.ito.R1ToR1Volatility
{
	private double _volatility = java.lang.Double.NaN;
	private double _temperature = java.lang.Double.NaN;
	private double _dampingCoefficient = java.lang.Double.NaN;

	/**
	 * R1WhiteThermalFrictionalNoise Constructor
	 * 
	 * @param temperature The Temperature
	 * @param dampingCoefficient The Damping Coefficient
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1WhiteThermalFrictionalNoise (
		final double temperature,
		final double dampingCoefficient)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
				_temperature = temperature
			) || 0. >= _temperature || !org.drip.numerical.common.NumberUtil.IsValid (
				_dampingCoefficient = dampingCoefficient
			) || 0. >= _dampingCoefficient
		)
		{
			throw new java.lang.Exception (
				"R1WhiteThermalFrictionalNoise Constructor => Invalid Inputs"
			);
		}

		_volatility = java.lang.Math.sqrt (
			2. * org.drip.dynamics.physical.FundamentalConstants.BOLTZMANN * _temperature /
				_dampingCoefficient
		);
	}

	/**
	 * Retrieve the Temperature
	 * 
	 * @return The Temperature
	 */

	public double temperature()
	{
		return _temperature;
	}

	/**
	 * Retrieve the Damping Coefficient
	 * 
	 * @return The Damping Coefficient
	 */

	public double dampingCoefficient()
	{
		return _dampingCoefficient;
	}

	/**
	 * Retrieve the Volatility
	 * 
	 * @return The Volatility
	 */

	public double volatility()
	{
		return _volatility;
	}

	@Override public double volatility (
		final org.drip.dynamics.ito.TimeR1Vertex r1TimeVertex)
		throws java.lang.Exception
	{
		if (null == r1TimeVertex)
		{
			throw new java.lang.Exception (
				"R1WhiteThermalFrictionalNoise::volatility => Invalid Inputs"
			);
		}

		return _volatility;
	}
}
