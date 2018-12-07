
package org.drip.spaces.metric;

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
 * <i>RdCombinatorialBanach</i> implements the Bounded/Unbounded Combinatorial l<sub>p</sub> R<sup>d</sup>
 * Spaces. The Reference we've used is:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/metric">Metric</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RdCombinatorialBanach extends org.drip.spaces.tensor.RdCombinatorialVector implements
	org.drip.spaces.metric.RdNormed {
	private int _iPNorm = -1;
	private org.drip.measure.continuous.Rd _distRd = null;

	/**
	 * RdCombinatorialBanach Space Constructor
	 * 
	 * @param aR1CV Array of Combinatorial R^1 Vector Spaces
	 * @param distRd The R^d Borel Sigma Measure
	 * @param iPNorm The p-norm of the Space
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RdCombinatorialBanach (
		final org.drip.spaces.tensor.R1CombinatorialVector[] aR1CV,
		final org.drip.measure.continuous.Rd distRd,
		final int iPNorm)
		throws java.lang.Exception
	{
		super (aR1CV);

		if (0 > (_iPNorm = iPNorm))
			throw new java.lang.Exception ("RdCombinatorialBanach Constructor: Invalid p-norm");

		_distRd = distRd;
	}

	@Override public int pNorm()
	{
		return _iPNorm;
	}

	@Override public org.drip.measure.continuous.Rd borelSigmaMeasure()
	{
		return _distRd;
	}

	@Override public double sampleSupremumNorm (
		final double[] adblX)
		throws java.lang.Exception
	{
		if (!validateInstance (adblX))
			throw new java.lang.Exception ("RdCombinatorialBanach::sampleSupremumNorm => Invalid Inputs");

		int iDimension = adblX.length;

		double dblNorm = java.lang.Math.abs (adblX[0]);

		for (int i = 1; i < iDimension; ++i) {
			double dblAbsoluteX = java.lang.Math.abs (adblX[i]);

			dblNorm = dblNorm > dblAbsoluteX ? dblNorm : dblAbsoluteX;
		}

		return dblNorm;
	}

	@Override public double sampleMetricNorm (
		final double[] adblX)
		throws java.lang.Exception
	{
		if (!validateInstance (adblX))
			throw new java.lang.Exception ("RdCombinatorialBanach::sampleMetricNorm => Invalid Inputs");

		if (java.lang.Integer.MAX_VALUE == _iPNorm) return sampleSupremumNorm (adblX);

		double dblNorm = 0.;
		int iDimension = adblX.length;

		for (int i = 0; i < iDimension; ++i)
			dblNorm += java.lang.Math.pow (java.lang.Math.abs (adblX[i]), _iPNorm);

		return java.lang.Math.pow (dblNorm, 1. / _iPNorm);
	}

	@Override public double[] populationMode()
	{
		if (null == _distRd) return null;

		org.drip.spaces.iterator.RdSpanningCombinatorialIterator crmi = iterator();

		double[] adblVariate = crmi.cursorVariates();

		int iDimension = adblVariate.length;
		double dblModeProbabilityDensity = 0.;
		double[] adblModeVariate = new double[iDimension];
		double dblProbabilityDensity = java.lang.Double.NaN;

		while (null != adblVariate) {
			try {
				dblProbabilityDensity = _distRd.density (adblVariate);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (dblProbabilityDensity > dblModeProbabilityDensity) {
				for (int i = 0; i < iDimension; ++i)
					adblModeVariate[i] = adblVariate[i];

				dblModeProbabilityDensity = dblProbabilityDensity;
			}

			adblVariate = crmi.nextVariates();
		}

		return adblModeVariate;
	}

	@Override public double populationSupremumNorm()
		throws java.lang.Exception
	{
		if (null == _distRd)
			throw new java.lang.Exception
				("RdCombinatorialBanach::populationSupremumNorm => Invalid Inputs");

		return sampleSupremumNorm (populationMode());
	}

	@Override public double populationMetricNorm()
		throws java.lang.Exception
	{
		if (java.lang.Integer.MAX_VALUE == _iPNorm) return sampleSupremumNorm (populationMode());

		if (null == _distRd)
			throw new java.lang.Exception
				("RdCombinatorialBanach::populationMetricNorm => No Multivariate Distribution");

		org.drip.spaces.iterator.RdSpanningCombinatorialIterator crmi = iterator();

		double[] adblVariate = crmi.cursorVariates();

		double dblNormalizer = 0.;
		double dblPopulationMetricNorm  = 0.;
		int iDimension = adblVariate.length;

		while (null != adblVariate) {
			double dblProbabilityDensity = _distRd.density (adblVariate);

			dblNormalizer += dblProbabilityDensity;

			for (int i = 0; i < iDimension; ++i)
				dblPopulationMetricNorm += dblProbabilityDensity * java.lang.Math.pow (java.lang.Math.abs
					(adblVariate[i]), _iPNorm);

			adblVariate = crmi.nextVariates();
		}

		return java.lang.Math.pow (dblPopulationMetricNorm / dblNormalizer, 1. / _iPNorm);
	}

	@Override public double borelMeasureSpaceExpectation (
		final org.drip.function.definition.RdToR1 funcRdToR1)
		throws java.lang.Exception
	{
		if (null == _distRd || null == funcRdToR1)
			throw new java.lang.Exception
				("RdCombinatorialBanach::borelMeasureSpaceExpectation => Invalid Inputs");

		org.drip.spaces.iterator.RdSpanningCombinatorialIterator crmi = iterator();

		double[] adblVariate = crmi.cursorVariates();

		double dblBorelMeasureSpaceExpectation = 0.;
		double dblNormalizer = 0.;

		while (null != adblVariate) {
			double dblProbabilityDensity = _distRd.density (adblVariate);

			dblNormalizer += dblProbabilityDensity;

			dblBorelMeasureSpaceExpectation += dblProbabilityDensity * funcRdToR1.evaluate (adblVariate);

			adblVariate = crmi.nextVariates();
		}

		return dblBorelMeasureSpaceExpectation / dblNormalizer;
	}
}
