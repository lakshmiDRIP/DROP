
package org.drip.measure.dynamics;

import org.drip.measure.gaussian.NormalQuadrature;
import org.drip.measure.realization.JumpDiffusionEdge;
import org.drip.measure.realization.JumpDiffusionEdgeUnit;
import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>OrnsteinUhlenbeckPair</i> guides the Random Variable Evolution according to 2D Ornstein-Uhlenbeck Mean
 * 	Reverting Process. The References are:
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
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>OrnsteinUhlenbeckPair</i> Constructor</li>
 * 		<li>Retrieve the Reference R<sup>1</sup> Ornstein-Uhlenbeck Evaluator</li>
 * 		<li>Retrieve the Derived R<sup>1</sup> Ornstein-Uhlenbeck Evaluator</li>
 * 		<li>Retrieve the Correlation between the Ornstein-Uhlenbeck Processes</li>
 * 		<li>Generate the Adjacent Jump Diffusion Edge Increment Array from the specified Ornstein Uhlenbeck Random Variate Pair</li>
 * 		<li>Generate the Weiner Based JumpDiffusionEdge Increment Sequence from the Current Ornstein Uhlenbeck Random Variate</li>
 * 		<li>Retrieve the Reference Relaxation Time Scale</li>
 * 		<li>Retrieve the Reference Burstiness Scale</li>
 * 		<li>Retrieve the Reference Mean Reversion Level Scale</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/dynamics/README.md">Jump Diffusion Evolution Evaluator Variants</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OrnsteinUhlenbeckPair
	implements OrnsteinUhlenbeck
{
	private double _correlation = Double.NaN;
	private DiffusionEvaluatorOrnsteinUhlenbeck _derivedDiffusionEvaluator = null;
	private DiffusionEvaluatorOrnsteinUhlenbeck _referenceDiffusionEvaluator = null;

	/**
	 * <i>OrnsteinUhlenbeckPair</i> Constructor
	 * 
	 * @param referenceDiffusionEvaluator The Reference R<sup>1</sup> Ornstein-Uhlenbeck Evaluator
	 * @param derivedDiffusionEvaluator The Derived R<sup>1</sup> Ornstein-Uhlenbeck Evaluator
	 * @param correlation The Correlation between the Two Ornstein-Uhlenbeck Processes
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public OrnsteinUhlenbeckPair (
		final DiffusionEvaluatorOrnsteinUhlenbeck referenceDiffusionEvaluator,
		final DiffusionEvaluatorOrnsteinUhlenbeck derivedDiffusionEvaluator,
		final double correlation)
		throws Exception
	{
		if (null == (_referenceDiffusionEvaluator = referenceDiffusionEvaluator) ||
			null == (_derivedDiffusionEvaluator = derivedDiffusionEvaluator) ||
			!NumberUtil.IsValid (_correlation = correlation) || _correlation < -1. || _correlation > 1.)
		{
			throw new Exception ("OrnsteinUhlenbeckPair Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Reference R<sup>1</sup> Ornstein-Uhlenbeck Evaluator
	 * 
	 * @return The Reference R<sup>1</sup> Ornstein-Uhlenbeck Evaluator
	 */

	public DiffusionEvaluatorOrnsteinUhlenbeck reference()
	{
		return _referenceDiffusionEvaluator;
	}

	/**
	 * Retrieve the Derived R<sup>1</sup> Ornstein-Uhlenbeck Evaluator
	 * 
	 * @return The Derived R<sup>1</sup> Ornstein-Uhlenbeck Evaluator
	 */

	public DiffusionEvaluatorOrnsteinUhlenbeck derived()
	{
		return _derivedDiffusionEvaluator;
	}

	/**
	 * Retrieve the Correlation between the Ornstein-Uhlenbeck Processes
	 * 
	 * @return The Correlation between the Ornstein-Uhlenbeck Processes
	 */

	public double correlation()
	{
		return _correlation;
	}

	/**
	 * Generate the Adjacent Jump Diffusion Edge Increment Array from the specified Ornstein Uhlenbeck Random
	 * 	Variate Pair
	 * 
	 * @param variatePair The Pair of the Ornstein Uhlenbeck Random Variates
	 * @param diffusionPair The Pair of Diffusion Realization
	 * @param timeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Adjacent Jump Diffusion Edge Increment Array
	 */

	public JumpDiffusionEdge[] increment (
		final double[] variatePair,
		final double[] diffusionPair,
		final double timeIncrement)
	{
		if (null == variatePair || 2 != variatePair.length || !NumberUtil.IsValid (variatePair) ||
			null == diffusionPair || 2 != diffusionPair.length || !NumberUtil.IsValid (diffusionPair) ||
			!NumberUtil.IsValid (timeIncrement) || 0. >= timeIncrement)
		{
			return null;
		}

		double referenceRelaxationTime = _referenceDiffusionEvaluator.relaxationTime();

		double derivedRelaxationTime = _derivedDiffusionEvaluator.relaxationTime();

		try {
			return new JumpDiffusionEdge[] {
				JumpDiffusionEdge.Standard (
					variatePair[0],
					-1. * variatePair[0] / referenceRelaxationTime * timeIncrement,
					_referenceDiffusionEvaluator.burstiness() * diffusionPair[0] *
						Math.sqrt (timeIncrement / referenceRelaxationTime),
					null,
					new JumpDiffusionEdgeUnit (timeIncrement, diffusionPair[0], 0.)
				),
				JumpDiffusionEdge.Standard (
					variatePair[1],
					-1. * variatePair[1] / derivedRelaxationTime * timeIncrement,
					_derivedDiffusionEvaluator.burstiness() * diffusionPair[1] *
						Math.sqrt (timeIncrement / derivedRelaxationTime),
					null,
					new JumpDiffusionEdgeUnit (timeIncrement, diffusionPair[1], 0.)
				)
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Weiner Based JumpDiffusionEdge Increment Sequence from the Current Ornstein Uhlenbeck
	 *  Random Variate
	 * 
	 * @param variatePair The Ornstein Uhlenbeck Random Variate Pair
	 * @param timeIncrement The Time Increment
	 * 
	 * @return The Weiner Based JumpDiffusionEdge Increment Sequence from the Current Ornstein-Uhlenbeck
	 * 	Random Variate
	 */

	public JumpDiffusionEdge[] weinerIncrement (
		final double[] variatePair,
		final double timeIncrement)
	{
		try {
			double firstWeiner = NormalQuadrature.Random();

			return increment (
				variatePair,
				new double[]
				{
					firstWeiner,
					firstWeiner * _correlation +
						NormalQuadrature.Random() * Math.sqrt (1. - _correlation * _correlation)
				},
				timeIncrement
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Reference Relaxation Time Scale
	 * 
	 * @return The Reference Relaxation Time Scale
	 */

	@Override public double referenceRelaxationTime()
	{
		return _referenceDiffusionEvaluator.relaxationTime();
	}

	/**
	 * Retrieve the Reference Burstiness Scale
	 * 
	 * @return The Reference Burstiness Scale
	 */

	@Override public double referenceBurstiness()
	{
		return _referenceDiffusionEvaluator.burstiness();
	}

	/**
	 * Retrieve the Reference Mean Reversion Level Scale
	 * 
	 * @return The Reference Mean Reversion Level Scale
	 */

	@Override public double referenceMeanReversionLevel()
	{
		return _referenceDiffusionEvaluator.meanReversionLevel();
	}
}
