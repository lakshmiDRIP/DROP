
package org.drip.measure.process;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>OrnsteinUhlenbeckPair</i> guides the Random Variable Evolution according to 2D Ornstein-Uhlenbeck Mean
 * Reverting Process. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 * 				https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf
 * 		</li>
 * 		<li>
 * 			Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility <i>SIAM Journal
 * 				of Financial Mathematics</i> <b>3 (1)</b> 163-181
 * 		</li>
 * 		<li>
 * 			Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes <i>Mathematical
 * 				Finance</i> <b>11 (1)</b> 79-96
 * 		</li>
 * 		<li>
 * 			Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility <i>Review of
 * 				Financial Studies</i> <b>7 (4)</b> 631-651
 * 		</li>
 * 		<li>
 * 			Walia, N. (2006): <i>Optimal Trading - Dynamic Stock Liquidation Strategies</i> <b>Princeton
 * 				University</b>
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/process">Process</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OrnsteinUhlenbeckPair implements org.drip.measure.process.OrnsteinUhlenbeck {
	private double _dblCorrelation = java.lang.Double.NaN;
	private org.drip.measure.dynamics.DiffusionEvaluatorOrnsteinUhlenbeck _deouDerived = null;
	private org.drip.measure.dynamics.DiffusionEvaluatorOrnsteinUhlenbeck _deouReference = null;

	/**
	 * OrnsteinUhlenbeckPair Constructor
	 * 
	 * @param deouReference The Reference R^1 Ornstein-Uhlenbeck Evaluator
	 * @param deouDerived The Derived R^1 Ornstein-Uhlenbeck Evaluator
	 * @param dblCorrelation The Correlation between the Two Ornstein-Uhlenbeck Processes
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public OrnsteinUhlenbeckPair (
		final org.drip.measure.dynamics.DiffusionEvaluatorOrnsteinUhlenbeck deouReference,
		final org.drip.measure.dynamics.DiffusionEvaluatorOrnsteinUhlenbeck deouDerived,
		final double dblCorrelation)
		throws java.lang.Exception
	{
		if (null == (_deouReference = deouReference) || null == (_deouDerived = deouDerived) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblCorrelation = dblCorrelation) || _dblCorrelation <
				-1. || _dblCorrelation > 1.)
			throw new java.lang.Exception ("OrnsteinUhlenbeckPair Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Reference R^1 Ornstein-Uhlenbeck Evaluator
	 * 
	 * @return The Reference R^1 Ornstein-Uhlenbeck Evaluator
	 */

	public org.drip.measure.dynamics.DiffusionEvaluatorOrnsteinUhlenbeck reference()
	{
		return _deouReference;
	}

	/**
	 * Retrieve the Derived R^1 Ornstein-Uhlenbeck Evaluator
	 * 
	 * @return The Derived R^1 Ornstein-Uhlenbeck Evaluator
	 */

	public org.drip.measure.dynamics.DiffusionEvaluatorOrnsteinUhlenbeck derived()
	{
		return _deouDerived;
	}

	/**
	 * Retrieve the Correlation between the Ornstein-Uhlenbeck Processes
	 * 
	 * @return The Correlation between the Ornstein-Uhlenbeck Processes
	 */

	public double correlation()
	{
		return _dblCorrelation;
	}

	/**
	 * Generate the Adjacent JumpDiffusionEdge Increment Array from the specified Ornstein Uhlenbeck Random
	 * 		Variate Pair
	 * 
	 * @param adblVariatePair The Pair of the Ornstein Uhlenbeck Random Variates
	 * @param adblDiffusionPair The Pair of Diffusion Realizations
	 * @param dblTimeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Adjacent JumpDiffusionEdge Increment Array
	 */

	public org.drip.measure.realization.JumpDiffusionEdge[] increment (
		final double[] adblVariatePair,
		final double[] adblDiffusionPair,
		final double dblTimeIncrement)
	{
		if (null == adblVariatePair || 2 != adblVariatePair.length ||
			!org.drip.quant.common.NumberUtil.IsValid (adblVariatePair) || null == adblDiffusionPair || 2 !=
				adblDiffusionPair.length || !org.drip.quant.common.NumberUtil.IsValid (adblDiffusionPair) ||
					!org.drip.quant.common.NumberUtil.IsValid (dblTimeIncrement) || 0. >= dblTimeIncrement)
			return null;

		double dblRelaxationTime0 = _deouReference.relaxationTime();

		double dblRelaxationTime1 = _deouDerived.relaxationTime();

		try {
			return new org.drip.measure.realization.JumpDiffusionEdge[] {
				org.drip.measure.realization.JumpDiffusionEdge.Standard (
					adblVariatePair[0],
					-1. * adblVariatePair[0] / dblRelaxationTime0 * dblTimeIncrement,
					_deouReference.burstiness() * adblDiffusionPair[0] * java.lang.Math.sqrt (dblTimeIncrement / dblRelaxationTime0),
					null,
					new org.drip.measure.realization.JumpDiffusionEdgeUnit (
						dblTimeIncrement,
						adblDiffusionPair[0],
						0.
					)
				),
				org.drip.measure.realization.JumpDiffusionEdge.Standard (
					adblVariatePair[1],
					-1. * adblVariatePair[1] / dblRelaxationTime1 * dblTimeIncrement,
					_deouDerived.burstiness() * adblDiffusionPair[1] * java.lang.Math.sqrt (dblTimeIncrement / dblRelaxationTime1),
					null,
					new org.drip.measure.realization.JumpDiffusionEdgeUnit (
						dblTimeIncrement,
						adblDiffusionPair[1],
						0.
					)
				)
			};
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Weiner Based JumpDiffusionEdge Increment Sequence from the Current Ornstein Uhlenbeck
	 * 		Random Variate
	 * 
	 * @param adblVariatePair The Ornstein Uhlenbeck Random Variate Pair
	 * @param dblTimeIncrement The Time Increment
	 * 
	 * @return The Weiner Based JumpDiffusionEdge Increment Sequence from the Current Ornstein Uhlenbeck
	 * 		Random Variate
	 */

	public org.drip.measure.realization.JumpDiffusionEdge[] weinerIncrement (
		final double[] adblVariatePair,
		final double dblTimeIncrement)
	{
		try {
			double dblFirstWeiner = org.drip.measure.gaussian.NormalQuadrature.Random();

			return increment (adblVariatePair, new double[] {dblFirstWeiner, dblFirstWeiner * _dblCorrelation
				+ org.drip.measure.gaussian.NormalQuadrature.Random() * java.lang.Math.sqrt (1. -
					_dblCorrelation * _dblCorrelation)}, dblTimeIncrement);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public double referenceRelaxationTime()
	{
		return _deouReference.relaxationTime();
	}

	@Override public double referenceBurstiness()
	{
		return _deouReference.burstiness();
	}

	@Override public double referenceMeanReversionLevel()
	{
		return _deouReference.meanReversionLevel();
	}
}
