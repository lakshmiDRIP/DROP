
package org.drip.spaces.rxtor1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>NormedRdContinuousToR1Continuous</i> implements the f : Validated Normed R<sup>d</sup> Continuous To
 * Validated Normed R<sup>1</sup> Continuous Function Spaces. The Reference we've used is:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/rxtor1">R<sup>x</sup> To R<sup>1</sup></a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NormedRdContinuousToR1Continuous extends org.drip.spaces.rxtor1.NormedRdToNormedR1 {

	/**
	 * NormedRdContinuousToR1Continuous Function Space Constructor
	 * 
	 * @param rdContinuousInput The Continuous R^d Input Banach Metric Vector Space
	 * @param r1ContinuousOutput The Continuous R^1 Output Metric Vector Space
	 * @param funcRdToR1 The R^d To R^1 Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NormedRdContinuousToR1Continuous (
		final org.drip.spaces.metric.RdContinuousBanach rdContinuousInput,
		final org.drip.spaces.metric.R1Continuous r1ContinuousOutput,
		final org.drip.function.definition.RdToR1 funcRdToR1)
		throws java.lang.Exception
	{
		super (rdContinuousInput, r1ContinuousOutput, funcRdToR1);
	}

	@Override public double populationMetricNorm()
		throws java.lang.Exception
	{
		final int iPNorm = outputMetricVectorSpace().pNorm();

		if (java.lang.Integer.MAX_VALUE == iPNorm) return populationSupremumMetricNorm();

		org.drip.spaces.metric.RdContinuousBanach rdContinuousInput =
			(org.drip.spaces.metric.RdContinuousBanach) inputMetricVectorSpace();

		final org.drip.measure.continuous.Rd distRd = rdContinuousInput.borelSigmaMeasure();

		final org.drip.function.definition.RdToR1 funcRdToR1 = function();

		if (null == distRd || null == funcRdToR1)
			throw new java.lang.Exception
				("NormedRdContinuousToR1Continuous::populationMetricNorm => Measure/Function not specified");

		org.drip.function.definition.RdToR1 am = new
			org.drip.function.definition.RdToR1 (null) {
			@Override public int dimension()
			{
				return org.drip.function.definition.RdToR1.DIMENSION_NOT_FIXED;
			}

			@Override public double evaluate (
				final double[] adblX)
				throws java.lang.Exception
			{
				return java.lang.Math.pow (java.lang.Math.abs (funcRdToR1.evaluate (adblX)), iPNorm) *
					distRd.density (adblX);
			}
		};

		double[] adblLeft = rdContinuousInput.leftDimensionEdge();

		double[] adblRight = rdContinuousInput.rightDimensionEdge();

		return java.lang.Math.pow (am.integrate (adblLeft, adblRight) / distRd.incremental(adblLeft,
			adblRight), 1. / iPNorm);
	}
}
