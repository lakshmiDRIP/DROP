
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
 * <i>NormedRdCombinatorialToR1Continuous</i> implements the f : Validated Normed R<sup>d</sup> Combinatorial
 * To Validated Normed R<sup>1</sup> Continuous Function Spaces. The Reference we've used is:
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
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/rxtord">R<sup>x</sup> To R<sup>d</sup></a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NormedRdCombinatorialToRdContinuous extends org.drip.spaces.rxtord.NormedRdToNormedRd {

	/**
	 * NormedRdCombinatorialToRdContinuous Function Space Constructor
	 * 
	 * @param rdCombinatorialInput The Combinatorial R^d Input Metric Vector Space
	 * @param rdContinuousOutput The Continuous R^d Output Metric Vector Space
	 * @param funcRdToRd The RdToRd Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NormedRdCombinatorialToRdContinuous (
		final org.drip.spaces.metric.RdCombinatorialBanach rdCombinatorialInput,
		final org.drip.spaces.metric.RdContinuousBanach rdContinuousOutput,
		final org.drip.function.definition.RdToRd funcRdToRd)
		throws java.lang.Exception
	{
		super (rdCombinatorialInput, rdContinuousOutput, funcRdToRd);
	}

	@Override public double[] populationMetricNorm()
	{
		int iPNorm = outputMetricVectorSpace().pNorm();

		if (java.lang.Integer.MAX_VALUE == iPNorm) return populationSupremumNorm();

		org.drip.spaces.metric.RdCombinatorialBanach rdCombinatorialInput =
			(org.drip.spaces.metric.RdCombinatorialBanach) inputMetricVectorSpace();

		org.drip.measure.continuous.Rd distRd = rdCombinatorialInput.borelSigmaMeasure();

		org.drip.spaces.iterator.RdSpanningCombinatorialIterator ciRd = rdCombinatorialInput.iterator();

		org.drip.function.definition.RdToRd funcRdToRd = function();

		if (null == distRd || null == funcRdToRd) return null;

		double[] adblVariate = ciRd.cursorVariates();

		double dblProbabilityDensity = java.lang.Double.NaN;
		double[] adblPopulationMetricNorm = null;
		int iOutputDimension = -1;
		double dblNormalizer = 0.;

		while (null != adblVariate) {
			try {
				dblProbabilityDensity = distRd.density (adblVariate);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			double[] adblValue = funcRdToRd.evaluate (adblVariate);

			if (null == adblValue || 0 == (iOutputDimension = adblValue.length)) return null;

			dblNormalizer += dblProbabilityDensity;

			if (null == adblPopulationMetricNorm) {
				adblPopulationMetricNorm = new double[iOutputDimension];

				for (int i = 0; i < iOutputDimension; ++i)
					adblPopulationMetricNorm[i] = 0;
			}

			for (int i = 0; i < iOutputDimension; ++i)
				adblPopulationMetricNorm[i] += dblProbabilityDensity * java.lang.Math.pow (java.lang.Math.abs
					(adblValue[i]), iPNorm);

			adblVariate = ciRd.nextVariates();
		}

		for (int i = 0; i < iOutputDimension; ++i)
			adblPopulationMetricNorm[i] += java.lang.Math.pow (adblPopulationMetricNorm[i] / dblNormalizer,
				1. / iPNorm);

		return adblPopulationMetricNorm;
	}
}
