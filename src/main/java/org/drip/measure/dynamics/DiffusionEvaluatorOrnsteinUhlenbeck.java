
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
 * <i>DiffusionEvaluatorOrnsteinUhlenbeck</i> evaluates the Drift/Volatility of the Diffusion Random Variable
 * Evolution according to R<sup>1</sup> Ornstein Uhlenbeck Process.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/dynamics">Jump Diffusion Evolution Evaluator Variants</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DiffusionEvaluatorOrnsteinUhlenbeck extends org.drip.measure.dynamics.DiffusionEvaluator
	implements org.drip.measure.process.OrnsteinUhlenbeck {
	private double _dblBurstiness = java.lang.Double.NaN;
	private double _dblRelaxationTime = java.lang.Double.NaN;
	private double _dblMeanReversionLevel = java.lang.Double.NaN;

	/**
	 * Construct a Standard Instance of DiffusionEvaluatorOrnsteinUhlenbeck
	 * 
	 * @param dblMeanReversionLevel The Mean Reversion Level
	 * @param dblBurstiness The Burstiness Parameter
	 * @param dblRelaxationTime The Relaxation Time
	 * 
	 * @return The Standard Instance of DiffusionEvaluatorOrnsteinUhlenbeck
	 */

	public static final DiffusionEvaluatorOrnsteinUhlenbeck Standard (
		final double dblMeanReversionLevel,
		final double dblBurstiness,
		final double dblRelaxationTime)
	{
		org.drip.measure.dynamics.LocalEvaluator leDrift = new org.drip.measure.dynamics.LocalEvaluator() {
			@Override public double value (
				final org.drip.measure.realization.JumpDiffusionVertex jdv)
				throws java.lang.Exception
			{
				if (null == jdv)
					throw new java.lang.Exception
						("DiffusionEvaluatorOrnsteinUhlenbeck::DriftLDEV::value => Invalid Inputs");

				return -1. * jdv.value() / dblRelaxationTime;
			}
		};

		org.drip.measure.dynamics.LocalEvaluator leVolatility = new
			org.drip.measure.dynamics.LocalEvaluator() {
			@Override public double value (
				final org.drip.measure.realization.JumpDiffusionVertex jdv)
				throws java.lang.Exception
			{
				return dblBurstiness * java.lang.Math.sqrt (1. / dblRelaxationTime);
			}
		};

		try {
			return new DiffusionEvaluatorOrnsteinUhlenbeck (dblMeanReversionLevel, dblBurstiness,
				dblRelaxationTime, leDrift, leVolatility);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Zero-Mean Instance of DiffusionEvaluatorOrnsteinUhlenbeck
	 * 
	 * @param dblBurstiness The Burstiness Parameter
	 * @param dblRelaxationTime The Relaxation Time
	 * 
	 * @return The Zero-Mean Instance of DiffusionEvaluatorOrnsteinUhlenbeck
	 */

	public static final DiffusionEvaluatorOrnsteinUhlenbeck ZeroMean (
		final double dblBurstiness,
		final double dblRelaxationTime)
	{
		return Standard (0., dblBurstiness, dblRelaxationTime);
	}

	private DiffusionEvaluatorOrnsteinUhlenbeck (
		final double dblMeanReversionLevel,
		final double dblBurstiness,
		final double dblRelaxationTime,
		final org.drip.measure.dynamics.LocalEvaluator leDrift,
		final org.drip.measure.dynamics.LocalEvaluator leVolatility)
		throws java.lang.Exception
	{
		super (leDrift, leVolatility);

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblMeanReversionLevel = dblMeanReversionLevel) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblBurstiness = dblBurstiness) || 0. >=
				_dblBurstiness || !org.drip.numerical.common.NumberUtil.IsValid (_dblRelaxationTime =
					dblRelaxationTime) || 0. >= _dblRelaxationTime)
			throw new java.lang.Exception
				("DiffusionEvaluatorOrnsteinUhlenbeck Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Mean Reversion Level
	 * 
	 * @return The Mean Reversion Level
	 */

	public double meanReversionLevel()
	{
		return _dblMeanReversionLevel;
	}

	/**
	 * Retrieve the Burstiness Parameter
	 * 
	 * @return The Burstiness Parameter
	 */

	public double burstiness()
	{
		return _dblBurstiness;
	}

	/**
	 * Retrieve the Relaxation Time
	 * 
	 * @return The Relaxation Time
	 */

	public double relaxationTime()
	{
		return _dblRelaxationTime;
	}

	@Override public double referenceRelaxationTime()
	{
		return relaxationTime();
	}

	@Override public double referenceBurstiness()
	{
		return burstiness();
	}

	@Override public double referenceMeanReversionLevel()
	{
		return meanReversionLevel();
	}
}
