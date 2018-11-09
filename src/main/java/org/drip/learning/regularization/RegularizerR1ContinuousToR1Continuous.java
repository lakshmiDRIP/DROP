
package org.drip.learning.regularization;

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
 * <i>RegularizerR1ContinuousToR1Continuous</i> computes the Structural Loss and Risk for the specified
 * Normed R<sup>1</sup> Continuous To Normed R<sup>1</sup> Continuous Learning Function.
 *  
 * <br><br>
 * <ul>
 * 	<li>
 *  	Alon, N., S. Ben-David, N. Cesa Bianchi, and D. Haussler (1997): Scale-sensitive Dimensions, Uniform
 *  		Convergence, and Learnability <i>Journal of Association of Computational Machinery</i> <b>44
 *  		(4)</b> 615-631
 * 	</li>
 * 	<li>
 *  	Anthony, M., and P. L. Bartlett (1999): <i>Artificial Neural Network Learning - Theoretical
 *  		Foundations</i> <b>Cambridge University Press</b> Cambridge, UK
 * 	</li>
 * 	<li>
 *  	Kearns, M. J., R. E. Schapire, and L. M. Sellie (1994): <i>Towards Efficient Agnostic Learning</i>
 *  		Machine Learning <b>17 (2)</b> 115-141
 * 	</li>
 * 	<li>
 *  	Lee, W. S., P. L. Bartlett, and R. C. Williamson (1998): The Importance of Convexity in Learning with
 *  		Squared Loss <i>IEEE Transactions on Information Theory</i> <b>44</b> 1974-1980
 * 	</li>
 * 	<li>
 *  	Vapnik, V. N. (1998): <i>Statistical learning Theory</i> <b>Wiley</b> New York
 * 	</li>
 * </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Learning</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/regularization">Regularization</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RegularizerR1ContinuousToR1Continuous extends
	org.drip.spaces.rxtor1.NormedR1ContinuousToR1Continuous implements
		org.drip.learning.regularization.RegularizerR1ToR1 {
	private double _dblLambda = java.lang.Double.NaN;

	/**
	 * RegularizerR1ContinuousToR1Continuous Function Space Constructor
	 * 
	 * @param funcRegularizerR1ToR1 The R^1 To R^1 Regularizer Function
	 * @param r1ContinuousInput The Continuous R^1 Input Metric Vector Space
	 * @param r1ContinuousOutput The Continuous R^1 Output Metric Vector Space
	 * @param dblLambda The Regularization Lambda
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RegularizerR1ContinuousToR1Continuous (
		final org.drip.function.definition.R1ToR1 funcRegularizerR1ToR1,
		final org.drip.spaces.metric.R1Continuous r1ContinuousInput,
		final org.drip.spaces.metric.R1Continuous r1ContinuousOutput,
		final double dblLambda)
		throws java.lang.Exception
	{
		super (r1ContinuousInput, r1ContinuousOutput, funcRegularizerR1ToR1);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblLambda = dblLambda) || 0 > _dblLambda)
			throw new java.lang.Exception
				("RegularizerR1ContinuousToR1Continuous Constructor => Invalid Inputs");
	}

	@Override public double lambda()
	{
		return _dblLambda;
	}

	@Override public double structuralLoss (
		final org.drip.function.definition.R1ToR1 funcR1ToR1,
		final double[] adblX)
		throws java.lang.Exception
	{
		if (null == funcR1ToR1 || null == adblX)
			throw new java.lang.Exception
				("RegularizerR1ContinuousToR1Continuous::structuralLoss => Invalid Inputs");

		double dblLoss = 0.;
		int iNumSample = adblX.length;

		if (0 == iNumSample)
			throw new java.lang.Exception
				("RegularizerR1ContinuousToR1Continuous::structuralLoss => Invalid Inputs");

		org.drip.function.definition.R1ToR1 funcRegularizerR1ToR1 = function();

		int iPNorm = outputMetricVectorSpace().pNorm();

		if (java.lang.Integer.MAX_VALUE == iPNorm) {
			double dblSupremum = 0.;

			for (int i = 0; i < iNumSample; ++i) {
				double dblNodeValue = java.lang.Math.abs (funcRegularizerR1ToR1.evaluate (adblX[i]) *
					funcR1ToR1.evaluate (adblX[i]));

				if (dblSupremum < dblNodeValue) dblSupremum = dblNodeValue;
			}

			return dblSupremum;
		}

		for (int i = 0; i < iNumSample; ++i)
			dblLoss += java.lang.Math.pow (java.lang.Math.abs (funcRegularizerR1ToR1.evaluate (adblX[i]) *
				funcR1ToR1.evaluate (adblX[i])), iPNorm);

		return dblLoss / iPNorm;
	}

	@Override public double structuralRisk (
		final org.drip.measure.continuous.R1R1 distR1R1,
		final org.drip.function.definition.R1ToR1 funcR1ToR1,
		final double[] adblX,
		final double[] adblY)
		throws java.lang.Exception
	{
		if (null == funcR1ToR1 || null == adblX || null == adblY)
			throw new java.lang.Exception
				("RegularizerR1ContinuousToR1Continuous::structuralRisk => Invalid Inputs");

		double dblLoss = 0.;
		double dblNormalizer = 0.;
		int iNumSample = adblX.length;

		if (0 == iNumSample || iNumSample != adblY.length)
			throw new java.lang.Exception
				("RegularizerR1ContinuousToR1Continuous::structuralRisk => Invalid Inputs");

		int iPNorm = outputMetricVectorSpace().pNorm();

		org.drip.function.definition.R1ToR1 funcRegularizerR1ToR1 = function();

		if (java.lang.Integer.MAX_VALUE == iPNorm) {
			double dblWeightedSupremum = 0.;
			double dblSupremumNodeValue = 0.;

			for (int i = 0; i < iNumSample; ++i) {
				double dblNodeValue = java.lang.Math.abs (funcRegularizerR1ToR1.evaluate (adblX[i]) *
					funcR1ToR1.evaluate (adblX[i]));

				double dblWeightedNodeValue = distR1R1.density (adblX[i], adblY[i]) * dblNodeValue;

				if (dblWeightedNodeValue > dblWeightedSupremum) {
					dblSupremumNodeValue = dblNodeValue;
					dblWeightedSupremum = dblWeightedNodeValue;
				}
			}

			return dblSupremumNodeValue;
		}

		for (int i = 0; i < iNumSample; ++i) {
			double dblDensity = distR1R1.density (adblX[i], adblY[i]);

			dblNormalizer += dblDensity;

			dblLoss += dblDensity * java.lang.Math.pow (java.lang.Math.abs (funcRegularizerR1ToR1.evaluate
				(adblX[i]) * funcR1ToR1.evaluate (adblX[i])), iPNorm);
		}

		return dblLoss / iPNorm / dblNormalizer;
	}
}
