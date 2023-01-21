
package org.drip.dynamics.meanreverting;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>R1VasicekStochasticEvolver</i> implements the R<sup>1</sup> Vasicek Stochastic Evolver. The References
 * 	are:
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

public class R1VasicekStochasticEvolver
	extends org.drip.dynamics.meanreverting.R1CKLSStochasticEvolver
{

	/**
	 * Construct a Weiner Instance of R1VasicekStochasticEvolver Process
	 * 
	 * @param meanReversionSpeed The Mean Reversion Speed
	 * @param meanReversionLevel The Mean Reversion Level
	 * @param volatility The Volatility
	 * @param timeWidth Wiener Time Width
	 * 
	 * @return Weiner Instance of R1VasicekStochasticEvolver Process
	 */

	public static R1VasicekStochasticEvolver Wiener (
		final double meanReversionSpeed,
		final double meanReversionLevel,
		final double volatility,
		final double timeWidth)
	{
		try
		{
			return new R1VasicekStochasticEvolver (
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
	 * R1VasicekStochasticEvolver Constructor
	 * 
	 * @param meanReversionSpeed The Mean Reversion Speed
	 * @param meanReversionLevel The Mean Reversion Level
	 * @param volatility The Volatility
	 * @param r1StochasticDriver The Stochastic Driver
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1VasicekStochasticEvolver (
		final double meanReversionSpeed,
		final double meanReversionLevel,
		final double volatility,
		final org.drip.dynamics.ito.R1StochasticDriver r1StochasticDriver)
		throws java.lang.Exception
	{
		super (
			org.drip.dynamics.meanreverting.CKLSParameters.Vasicek (
				meanReversionSpeed,
				meanReversionLevel,
				volatility
			),
			r1StochasticDriver
		);
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
				"R1VasicekStochasticEvolver::mean => Invalid Inputs"
			);
		}

		double timeDecayFactor = java.lang.Math.exp (
			-1. * cklsParameters().meanReversionSpeed() * t
		);

		return x0 * timeDecayFactor + cklsParameters().meanReversionLevel() * (1. - timeDecayFactor);
	}

	/**
	 * Compute the Time Co-variance of x across Time Values t and s
	 * 
	 * @param x0 Starting Variate
	 * @param s Time s
	 * @param t Time t
	 * 
	 * @return Time Co-variance of x
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double timeCovariance (
		final double x0,
		final double s,
		final double t)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
				s
			) || 0. > s || !org.drip.numerical.common.NumberUtil.IsValid (
				t
			) || 0. > t
		)
		{
			throw new java.lang.Exception (
				"R1VasicekStochasticEvolver::timeCovariance => Invalid Inputs"
			);
		}

		double volatility = cklsParameters().volatilityCoefficient();

		double meanReversionSpeed = cklsParameters().meanReversionSpeed();

		return 0.5 * volatility * volatility / meanReversionSpeed *
		(
			(
				java.lang.Math.exp (
					-1. * meanReversionSpeed * java.lang.Math.abs (
						s - t
					)
				) - java.lang.Math.exp (
					-1. * meanReversionSpeed * (s + t)
				)
			)
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
				timeCovariance (
					x0,
					t,
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

	/**
	 * Construct the Ait-Sahalia Maximum Likelihood Estimation Sampling Interval Discreteness Error
	 * 
	 * @param intervalWidth Sampling Interval Width
	 * 
	 * @return The Ait-Sahalia Maximum Likelihood Estimation Sampling Interval Discreteness Error
	 */

	public double[][] aitSahaliaMLEAsymptote (
		final double intervalWidth)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
				intervalWidth
			) || 0. >= intervalWidth
		)
		{
			return null;
		}

		double volatility = cklsParameters().volatilityCoefficient();

		double meanReversionSpeed = cklsParameters().meanReversionSpeed();

		double tTheta = intervalWidth * meanReversionSpeed;
		double tSquaredThetaSquared = tTheta * tTheta;

		double ePower_TTheta_ = java.lang.Math.exp (
			tTheta
		);

		double ePower_TwoTTheta_ = ePower_TTheta_ * ePower_TTheta_;
		double ePower_TwoTTheta_MinusOne = ePower_TwoTTheta_ - 1.;
		double sigmaSquared = volatility * volatility;
		double[][] aitSahaliaMLEAsymptote = new double[3][3];
		double tSquared = intervalWidth * intervalWidth;
		aitSahaliaMLEAsymptote[0][0] = ePower_TwoTTheta_MinusOne / tSquared;
		aitSahaliaMLEAsymptote[0][1] = 0.;
		aitSahaliaMLEAsymptote[0][2] = sigmaSquared * ePower_TwoTTheta_MinusOne *
			(ePower_TwoTTheta_MinusOne - 2. * intervalWidth) / tSquared / meanReversionSpeed;
		aitSahaliaMLEAsymptote[1][0] = 0.;
		aitSahaliaMLEAsymptote[1][1] = 0.5 * sigmaSquared * (ePower_TTheta_ + 1.) / (ePower_TTheta_ - 1.) /
			meanReversionSpeed;
		aitSahaliaMLEAsymptote[1][2] = 0.;
		aitSahaliaMLEAsymptote[2][0] = aitSahaliaMLEAsymptote[0][2];
		aitSahaliaMLEAsymptote[2][1] = 0.;
		aitSahaliaMLEAsymptote[2][2] = sigmaSquared * sigmaSquared * (
			ePower_TwoTTheta_MinusOne * ePower_TwoTTheta_MinusOne +
			2. * tSquaredThetaSquared * (ePower_TwoTTheta_ + 1.) +
			4. * tTheta * ePower_TwoTTheta_MinusOne
		) / (ePower_TwoTTheta_MinusOne * tSquaredThetaSquared);
		return aitSahaliaMLEAsymptote;
	}
}
