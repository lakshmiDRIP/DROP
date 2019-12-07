
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
 * <i>LangevinEvolver</i> implements the Noisy Elastic Relaxation Process in a Friction-Thermal Background.
 * 	The References are:
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

public class LangevinEvolver
	extends org.drip.dynamics.meanreverting.R1VasicekStochasticEvolver
{
	private double _restLength = java.lang.Double.NaN;
	private double _temperature = java.lang.Double.NaN;
	private double _dampingCoefficient = java.lang.Double.NaN;
	private double _elasticityCoefficient = java.lang.Double.NaN;

	/**
	 * R1NoisyRelaxationDrift Constructor
	 * 
	 * @param elasticityCoefficient Elastic Coefficient
	 * @param dampingCoefficient Damping Coefficient
	 * @param restLength Rest Length
	 * @param temperature The Temperature
	 * @param r1StochasticDriver The Stochastic Driver
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LangevinEvolver (
		final double elasticityCoefficient,
		final double dampingCoefficient,
		final double restLength,
		final double temperature,
		final org.drip.dynamics.ito.R1StochasticDriver r1StochasticDriver)
		throws java.lang.Exception
	{
		super (
			elasticityCoefficient / dampingCoefficient,
			restLength,
			java.lang.Math.sqrt (
				2. * org.drip.dynamics.physical.FundamentalConstants.BOLTZMANN * temperature /
					dampingCoefficient
			),
			r1StochasticDriver
		);

		if (!org.drip.numerical.common.NumberUtil.IsValid (
				_elasticityCoefficient = elasticityCoefficient
			) || 0. >= _elasticityCoefficient || !org.drip.numerical.common.NumberUtil.IsValid (
				_dampingCoefficient = dampingCoefficient
			) || 0. >= _dampingCoefficient || !org.drip.numerical.common.NumberUtil.IsValid (
				_restLength = restLength
			) || 0. >= _restLength || !org.drip.numerical.common.NumberUtil.IsValid (
				_temperature = temperature
			) || 0. >= _temperature
		)
		{
			throw new java.lang.Exception (
				"R1NoisyRelaxationDrift Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Elasticity Coefficient
	 * 
	 * @return The Elasticity Coefficient
	 */

	public double elasticityCoefficient()
	{
		return _elasticityCoefficient;
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
	 * Retrieve the Rest Length
	 * 
	 * @return The Rest Length
	 */

	public double restLength()
	{
		return _restLength;
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
	 * Retrieve the Equi-Partition Energy
	 * 
	 * @return The Equi-Partition Energy
	 */

	public double equiPartitionEnergy()
	{
		return 0.5 * org.drip.dynamics.physical.FundamentalConstants.BOLTZMANN * _temperature;
	}

	/**
	 * Retrieve the Correlation Time
	 * 
	 * @return The Correlation Time
	 */

	public double correlationTime()
	{
		return _dampingCoefficient / _elasticityCoefficient;
	}

	/**
	 * Retrieve the Stokes-Einstein Effective Diffusion Coefficient
	 * 
	 * @return The Stokes-Einstein Effective Diffusion Coefficient
	 */

	public double stokesEinsteinEffectiveDiffusionCoefficient()
	{
		return org.drip.dynamics.physical.FundamentalConstants.BOLTZMANN * _temperature /
			_dampingCoefficient;
	}

	/**
	 * Retrieve the Fluctuation Co-variance
	 * 
	 * @param t The Time Snapshot
	 * 
	 * @return The Fluctuation Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double fluctuationCovariance (
		final double t)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
			t
		))
		{
			throw new java.lang.Exception (
				"LangevinEvolver::fluctuationCovariance => Invalid Inputs"
			);
		}

		return org.drip.dynamics.physical.FundamentalConstants.BOLTZMANN * _temperature /
			_elasticityCoefficient * java.lang.Math.exp (
				-1. * java.lang.Math.abs (
					t
				) / correlationTime()
			);
	}

	/**
	 * Retrieve the Fluctuation Correlation
	 * 
	 * @param t The Time Snapshot
	 * 
	 * @return The Fluctuation Correlation
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double fluctuationCorrelation (
		final double t)
		throws java.lang.Exception
	{
		return fluctuationCovariance (
			t
		) / _restLength / _restLength;
	}
}
