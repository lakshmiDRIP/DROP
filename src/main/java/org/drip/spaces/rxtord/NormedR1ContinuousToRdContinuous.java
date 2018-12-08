
package org.drip.spaces.rxtord;

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
 * <i>NormedR1ContinuousToRdContinuous</i> implements the f : Validated Normed R<sup>1</sup> Continuous To
 * Validated Normed R<sup>d</sup> Continuous Function Spaces. The Reference we've used is:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/rxtord">R<sup>x</sup> To R<sup>d</sup></a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NormedR1ContinuousToRdContinuous extends org.drip.spaces.rxtord.NormedR1ToNormedRd {

	/**
	 * NormedR1ContinuousToRdContinuous Function Space Constructor
	 * 
	 * @param r1ContinuousInput The R^1 Input Metric Vector Space
	 * @param rdContinuousOutput The R^d Output Metric Vector Space
	 * @param funcR1ToRd The R1ToRd Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NormedR1ContinuousToRdContinuous (
		final org.drip.spaces.metric.R1Continuous r1ContinuousInput,
		final org.drip.spaces.metric.RdContinuousBanach rdContinuousOutput,
		final org.drip.function.definition.R1ToRd funcR1ToRd)
		throws java.lang.Exception
	{
		super (r1ContinuousInput, rdContinuousOutput, funcR1ToRd);
	}

	@Override public double[] populationMetricNorm()
	{
		final int iPNorm = outputMetricVectorSpace().pNorm();

		if (java.lang.Integer.MAX_VALUE == iPNorm) return populationSupremumNorm();

		org.drip.spaces.metric.R1Combinatorial r1ContinuousInput = (org.drip.spaces.metric.R1Combinatorial)
			inputMetricVectorSpace();

		final org.drip.measure.continuous.R1 distR1 = r1ContinuousInput.borelSigmaMeasure();

		final org.drip.function.definition.R1ToRd funcR1ToRd = function();

		if (null == distR1 || null == funcR1ToRd) return null;

		org.drip.function.definition.R1ToRd funcR1ToRdPointNorm = new org.drip.function.definition.R1ToRd
			(null) {
			@Override public double[] evaluate (
				final double dblX)
			{
				double[] adblNorm = funcR1ToRd.evaluate (dblX);

				if (null == adblNorm) return null;

				int iOutputDimension = adblNorm.length;
				double dblProbabilityDensity = java.lang.Double.NaN;

				if (0 == iOutputDimension) return null;

				try {
					dblProbabilityDensity = distR1.density (dblX);
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}

				for (int j = 0; j < iOutputDimension; ++j)
					adblNorm[j] = dblProbabilityDensity * java.lang.Math.pow (java.lang.Math.abs
						(adblNorm[j]), iPNorm);

				return adblNorm;
			}
		};

		double[] adblPopulationRdMetricNorm = funcR1ToRdPointNorm.integrate (r1ContinuousInput.leftEdge(),
			r1ContinuousInput.rightEdge());

		if (null == adblPopulationRdMetricNorm) return null;

		int iOutputDimension = adblPopulationRdMetricNorm.length;

		if (0 == iOutputDimension) return null;

		for (int i = 0; i < iOutputDimension; ++i)
			adblPopulationRdMetricNorm[i] = java.lang.Math.pow (adblPopulationRdMetricNorm[i], 1. / iPNorm);

		return adblPopulationRdMetricNorm;
	}
}
