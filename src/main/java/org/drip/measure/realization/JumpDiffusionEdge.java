
package org.drip.measure.realization;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>JumpDiffusionEdge</i> implements the Deterministic and the Stochastic Components of a R<sup>d</sup>
 * Marginal Random Increment Edge as well the Original Marginal Random Variate. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
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
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/realization/README.md">Stochastic Jump Diffusion Vertex Edge</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class JumpDiffusionEdge {
	private double _dblStart = java.lang.Double.NaN;
	private double _dblDeterministic = java.lang.Double.NaN;
	private org.drip.measure.realization.StochasticEdgeJump _sej = null;
	private org.drip.measure.realization.JumpDiffusionEdgeUnit _jdeu = null;
	private org.drip.measure.realization.StochasticEdgeDiffusion _sed = null;

	/**
	 * Construct the Standard JumpDiffusionEdge Instance
	 * 
	 * @param dblStart The Starting Random Variable Realization
	 * @param dblDeterministic The Deterministic Increment Component
	 * @param dblDiffusionStochastic The Diffusion Stochastic Edge Change Amount
	 * @param bJumpOccurred TRUE - The Jump Occurred in this Edge Period
	 * @param dblHazardRate The Hazard Rate
	 * @param dblHazardIntegral The Level Hazard Integral
	 * @param dblJumpTarget The Jump Target
	 * @param dblTimeIncrement The Time Increment
	 * @param dblUnitDiffusion The Diffusion Random Variable
	 * @param dblUnitJump The Jump Random Variable
	 * 
	 * @return The JumpDiffusionEdge Instance
	 */

	public static final JumpDiffusionEdge Standard (
		final double dblStart,
		final double dblDeterministic,
		final double dblDiffusionStochastic,
		final boolean bJumpOccurred,
		final double dblHazardRate,
		final double dblHazardIntegral,
		final double dblJumpTarget,
		final double dblTimeIncrement,
		final double dblUnitDiffusion,
		final double dblUnitJump)
	{
		try {
			return new JumpDiffusionEdge (dblStart, dblDeterministic, new
				org.drip.measure.realization.StochasticEdgeDiffusion (dblDiffusionStochastic), new
					org.drip.measure.realization.StochasticEdgeJump (bJumpOccurred, dblHazardRate,
						dblHazardIntegral, dblJumpTarget), new
							org.drip.measure.realization.JumpDiffusionEdgeUnit (dblTimeIncrement,
								dblUnitDiffusion, dblUnitJump));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard JumpDiffusionEdge Instance
	 * 
	 * @param dblStart The Starting Random Variable Realization
	 * @param dblDeterministic The Deterministic Increment Component
	 * @param dblDiffusionStochastic The Diffusion Stochastic Edge Change Amount
	 * @param sej The Stochastic Jump Edge Instance
	 * @param jdeu The Random Unit Realization
	 * 
	 * @return The JumpDiffusionEdge Instance
	 */

	public static final JumpDiffusionEdge Standard (
		final double dblStart,
		final double dblDeterministic,
		final double dblDiffusionStochastic,
		final org.drip.measure.realization.StochasticEdgeJump sej,
		final org.drip.measure.realization.JumpDiffusionEdgeUnit jdeu)
	{
		try {
			return new JumpDiffusionEdge (dblStart, dblDeterministic, new
				org.drip.measure.realization.StochasticEdgeDiffusion (dblDiffusionStochastic), sej, jdeu);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * JumpDiffusionEdge Constructor
	 * 
	 * @param dblStart The Starting Random Variable Realization
	 * @param dblDeterministic The Deterministic Increment Component
	 * @param sed The Stochastic Diffusion Edge Instance
	 * @param sej The Stochastic Jump Edge Instance
	 * @param jdeu The Random Unit Realization
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public JumpDiffusionEdge (
		final double dblStart,
		final double dblDeterministic,
		final org.drip.measure.realization.StochasticEdgeDiffusion sed,
		final org.drip.measure.realization.StochasticEdgeJump sej,
		final org.drip.measure.realization.JumpDiffusionEdgeUnit jdeu)
		throws java.lang.Exception
	{
		_sed = sed;
		_sej = sej;

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblStart = dblStart) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblDeterministic = dblDeterministic) || (null == _sed
				&& null == _sej) || null == (_jdeu = jdeu))
			throw new java.lang.Exception ("JumpDiffusionEdge Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Edge Time Increment
	 * 
	 * @return The Edge Time Increment
	 */

	public double timeIncrement()
	{
		return _jdeu.timeIncrement();
	}

	/**
	 * Retrieve the Start Realization
	 * 
	 * @return The Start Realization
	 */

	public double start()
	{
		return _dblStart;
	}

	/**
	 * Retrieve the Deterministic Component
	 * 
	 * @return The Deterministic Component
	 */

	public double deterministic()
	{
		return _dblDeterministic;
	}

	/**
	 * Retrieve the Diffusion Stochastic Component
	 * 
	 * @return The Diffusion Stochastic Component
	 */

	public double diffusionStochastic()
	{
		return null == _sed ? 0. : _sed.change();
	}

	/**
	 * Retrieve the Diffusion Wander Realization
	 * 
	 * @return The Diffusion Wander Realization
	 */

	public double diffusionWander()
	{
		return _jdeu.diffusion();
	}

	/**
	 * Retrieve the Jump Stochastic Component
	 * 
	 * @return The Jump Stochastic Component
	 */

	public double jumpStochastic()
	{
		return null == _sej ? 0. : _sej.target();
	}

	/**
	 * Retrieve the Jump Wander Realization
	 * 
	 * @return The Jump Wander Realization
	 */

	public double jumpWander()
	{
		return _jdeu.jump();
	}

	/**
	 * Retrieve the Finish Realization
	 * 
	 * @return The Finish Realization
	 */

	public double finish()
	{
		return null == _sej || !_sej.jumpOccurred() ? _dblStart + _dblDeterministic + diffusionStochastic() :
			_sej.target();
	}

	/**
	 * Retrieve the Gross Change
	 * 
	 * @return The Gross Change
	 */

	public double grossChange()
	{
		return finish() - _dblStart;
	}

	/**
	 * Retrieve the Stochastic Diffusion Edge Instance
	 * 
	 * @return The Stochastic Diffusion Edge Instance
	 */

	public org.drip.measure.realization.StochasticEdgeDiffusion stochasticDiffusionEdge()
	{
		return _sed;
	}

	/**
	 * Retrieve the Stochastic Jump Edge Instance
	 * 
	 * @return The Stochastic Jump Edge Instance
	 */

	public org.drip.measure.realization.StochasticEdgeJump stochasticJumpEdge()
	{
		return _sej;
	}
}
