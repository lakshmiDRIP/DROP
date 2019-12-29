
package org.drip.dynamics.meanreverting;

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
 * <i>R1CIRStochasticEvolver</i> implements the R<sup>1</sup> Cos-Ingersoll-Ross Stochastic Evolver. The
 * 	References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/meanreverting/README.md">Mean Reverting Stochastic Process Dynamics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1CIRStochasticEvolver
	extends org.drip.dynamics.meanreverting.R1CKLSStochasticEvolver
{

	/**
	 * Construct a Weiner Instance of R1CIRStochasticEvolver Process
	 * 
	 * @param meanReversionSpeed The Mean Reversion Speed
	 * @param meanReversionLevel The Mean Reversion Level
	 * @param volatility The Volatility
	 * @param timeWidth Wiener Time Width
	 * 
	 * @return Weiner Instance of R1CIRStochasticEvolver Process
	 */

	public static R1CIRStochasticEvolver Wiener (
		final double meanReversionSpeed,
		final double meanReversionLevel,
		final double volatility,
		final double timeWidth)
	{
		try
		{
			return new R1CIRStochasticEvolver (
				meanReversionSpeed,
				meanReversionLevel,
				volatility,
				new org.drip.dynamics.ito.R1WienerDriver (
					timeWidth
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * R1CIRStochasticEvolver Constructor
	 * 
	 * @param meanReversionSpeed The Mean Reversion Speed
	 * @param meanReversionLevel The Mean Reversion Level
	 * @param volatilityCoefficient The Volatility Coefficient
	 * @param r1StochasticDriver The Stochastic Driver
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1CIRStochasticEvolver (
		final double meanReversionSpeed,
		final double meanReversionLevel,
		final double volatilityCoefficient,
		final org.drip.dynamics.ito.R1StochasticDriver r1StochasticDriver)
		throws java.lang.Exception
	{
		super (
			org.drip.dynamics.meanreverting.CKLSParameters.CoxIngersollRoss (
				meanReversionSpeed,
				meanReversionLevel,
				volatilityCoefficient
			),
			r1StochasticDriver
		);
	}

	/**
	 * Indicate it the Evolution includes Zero, or is strictly Positive
	 * 
	 * @return TRUE - Evoltuion is Strictly Positive
	 */

	public boolean evolutionStrictlyPositive()
	{
		org.drip.dynamics.meanreverting.CKLSParameters cklsParameters = cklsParameters();

		double volatilityCoefficient = cklsParameters.volatilityCoefficient();

		return 2. * cklsParameters.meanReversionSpeed() * cklsParameters.meanReversionLevel() >=
			volatilityCoefficient * volatilityCoefficient;
	}

	/**
	 * Compute the Expected Value of x at a time t from a Starting Value x0
	 * 
	 * @param x0 Starting Variate
	 * @param t Time
	 * 
	 * @return Expected Value of x
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double mean (
		final double x0,
		final double t)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
				x0
			) || !org.drip.numerical.common.NumberUtil.IsValid (
				t
			) || 0. > t
		)
		{
			throw new java.lang.Exception (
				"R1CIRStochasticEvolver::mean => Invalid Inputs"
			);
		}

		double timeDecayFactor = java.lang.Math.exp (
			-1. * cklsParameters().meanReversionSpeed() * t
		);

		return x0 * timeDecayFactor + cklsParameters().meanReversionLevel() * (1. - timeDecayFactor);
	}

	/**
	 * Compute the Time Variance of x across at a Time Value t
	 * 
	 * @param x0 Starting Variate
	 * @param t Time t
	 * 
	 * @return Time Variance of x
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double timeVariance (
		final double x0,
		final double t)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
				t
			) || 0. > t
		)
		{
			throw new java.lang.Exception (
				"R1VasicekStochasticEvolver::timeCovariance => Invalid Inputs"
			);
		}

		double volatilityCoefficient = cklsParameters().volatilityCoefficient();

		double meanReversionSpeed = cklsParameters().meanReversionSpeed();

		double timeDecayFactor = java.lang.Math.exp (
			-1. * meanReversionSpeed * t
		);

		double oneMinusTimeDecayFactor = 1. - timeDecayFactor;

		return volatilityCoefficient * volatilityCoefficient / meanReversionSpeed * oneMinusTimeDecayFactor *
		(
			x0 * timeDecayFactor + 0.5 * cklsParameters().meanReversionLevel() * oneMinusTimeDecayFactor
		);
	}

	@Override public org.drip.measure.statistics.PopulationCentralMeasures
		temporalPopulationCentralMeasures (
			final double x0,
			final double t)
	{
		try
		{
			return new org.drip.measure.statistics.PopulationCentralMeasures (
				mean (
					x0,
					t
				),
				timeVariance (
					x0,
					t
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.measure.statistics.PopulationCentralMeasures
		steadyStatePopulationCentralMeasures (
			final double x0)
	{
		double volatility = cklsParameters().volatilityCoefficient();

		try
		{
			return new org.drip.measure.statistics.PopulationCentralMeasures (
				cklsParameters().meanReversionLevel(),
				0.5 * volatility * volatility / cklsParameters().meanReversionSpeed()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.measure.chisquare.R1NonCentral futureValueDistribution (
		final double r0,
		final double t)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
				r0
			) || !org.drip.numerical.common.NumberUtil.IsValid (
				t
			) || 0. > t
		)
		{
			return null;
		}

		int digammaTermCount = 1000;
		int besselFirstTermCount = 20;

		org.drip.dynamics.meanreverting.CKLSParameters cklsParameters = cklsParameters();

		double ePowerMinusAT = java.lang.Math.exp (
			-1. * cklsParameters.meanReversionSpeed() * t
		);

		org.drip.function.definition.R1ToR1 gammaEstimator =
			new org.drip.specialfunction.gamma.EulerIntegralSecondKind (
				null
			);

		try
		{
			return new org.drip.measure.chisquare.R1NonCentral (
				new org.drip.measure.chisquare.R1NonCentralParameters (
					cklsParameters.meanReversionLevel() * (1. - ePowerMinusAT),
					r0 * ePowerMinusAT
				),
				gammaEstimator,
				org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (
					digammaTermCount
				),
				new org.drip.function.definition.R2ToR1()
				{
					@Override public double evaluate (
						final double s,
						final double t)
						throws Exception
					{
						return new org.drip.specialfunction.incompletegamma.LowerEulerIntegral (
							null,
							t
						).evaluate (
							s
						);
					}
				},
				org.drip.specialfunction.bessel.ModifiedFirstFrobeniusSeriesEstimator.Standard (
					gammaEstimator,
					besselFirstTermCount
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.dynamics.kolmogorov.R1FokkerPlanckCIR fokkerPlanckGenerator()
	{
		try
		{
			return new org.drip.dynamics.kolmogorov.R1FokkerPlanckCIR (
				cklsParameters()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
