
package org.drip.spaces.functionclass;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>NormedRxToNormedRxFinite</i> exposes the Space of Functions that are a Transform from the Normed
 * R<sup>x</sup> To Normed R<sup>d</sup> Spaces. The References are:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B. (1985): Inequalities of the Bernstein-Jackson type and the Degree of Compactness of
 *  			Operators in Banach Spaces <i>Annals of the Fourier Institute</i> <b>35 (3)</b> 79-118
 *  	</li>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  	<li>
 *  		Williamson, R. C., A. J. Smola, and B. Scholkopf (2000): Entropy Numbers of Linear Function
 *  			Classes, in: <i>Proceedings of the 13th Annual Conference on Computational Learning
 *  				Theory</i> <b>ACM</b> New York
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/functionclass/README.md">Normed Finite Spaces Function Class</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class NormedRxToNormedRxFinite {
	private double _dblMaureyConstant = java.lang.Double.NaN;

	protected NormedRxToNormedRxFinite (
		final double dblMaureyConstant)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblMaureyConstant = dblMaureyConstant) || 0. >=
			_dblMaureyConstant)
			throw new java.lang.Exception ("NormedRxToNormedRxFinite ctr => Invalid Inputs");
	}

	/**
	 * Retrieve the Input Vector Space
	 * 
	 * @return The Input Vector Space
	 */

	public abstract org.drip.spaces.metric.GeneralizedMetricVectorSpace inputMetricVectorSpace();

	/**
	 * Retrieve the Output Vector Space
	 * 
	 * @return The Output Vector Space
	 */

	public abstract org.drip.spaces.metric.GeneralizedMetricVectorSpace outputMetricVectorSpace();

	/**
	 * Compute the Operator Population Metric Norm
	 * 
	 * @return The Operator Population Metric Norm
	 * 
	 * @throws java.lang.Exception Thrown if the Operator Norm cannot be computed
	 */

	public abstract double operatorPopulationMetricNorm()
		throws java.lang.Exception;

	/**
	 * Compute the Operator Population Supremum Norm
	 * 
	 * @return The Operator Population Supremum Norm
	 * 
	 * @throws java.lang.Exception Thrown if the Operator Population Supremum Norm cannot be computed
	 */

	public abstract double operatorPopulationSupremumNorm()
		throws java.lang.Exception;

	/**
	 * Compute the Operator Sample Metric Norm
	 * 
	 * @param gvvi The Validated Vector Space Instance
	 * 
	 * @return The Operator Sample Metric Norm
	 * 
	 * @throws java.lang.Exception Thrown if the Operator Norm cannot be computed
	 */

	public abstract double operatorSampleMetricNorm (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
		throws java.lang.Exception;

	/**
	 * Compute the Operator Sample Supremum Norm
	 * 
	 * @param gvvi The Validated Vector Space Instance
	 * 
	 * @return The Operator Sample Supremum Norm
	 * 
	 * @throws java.lang.Exception Thrown if the Operator Sample Supremum Norm cannot be computed
	 */

	public abstract double operatorSampleSupremumNorm (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
		throws java.lang.Exception;

	/**
	 * Retrieve the Agnostic Covering Number Upper/Lower Bounds for the Function Class
	 * 
	 * @return The Agnostic Covering Number Upper/Lower Bounds for the Function Class
	 */

	public abstract org.drip.spaces.cover.FunctionClassCoveringBounds agnosticCoveringNumberBounds();

	/**
	 * Retrieve the Maurey Constant
	 * 
	 * @return The Maurey Constant
	 */

	public double maureyConstant()
	{
		return _dblMaureyConstant;
	}

	/**
	 * Retrieve the Scale-Sensitive Covering Number Upper/Lower Bounds given the Specified Sample for the
	 *  Function Class
	 * 
	 * @param gvvi The Validated Instance Vector Sequence
	 * @param funcR1ToR1FatShatter The Cover Fat Shattering Coefficient R^1 To R^1
	 * 
	 * @return The Scale-Sensitive Covering Number Upper/Lower Bounds given the Specified Sample for the
	 *  Function Class
	 */

	public org.drip.spaces.cover.FunctionClassCoveringBounds scaleSensitiveCoveringBounds (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi,
		final org.drip.function.definition.R1ToR1 funcR1ToR1FatShatter)
	{
		if (null == gvvi || null == funcR1ToR1FatShatter) return null;

		int iSampleSize = -1;

		if (gvvi instanceof org.drip.spaces.instance.ValidatedR1) {
			double[] adblInstance = ((org.drip.spaces.instance.ValidatedR1) gvvi).instance();

			if (null == adblInstance) return null;

			iSampleSize = adblInstance.length;
		} else if (gvvi instanceof org.drip.spaces.instance.ValidatedRd) {
			double[][] aadblInstance = ((org.drip.spaces.instance.ValidatedRd) gvvi).instance();

			if (null == aadblInstance) return null;

			iSampleSize = aadblInstance.length;
		}

		try {
			return new org.drip.spaces.cover.ScaleSensitiveCoveringBounds (funcR1ToR1FatShatter,
				iSampleSize);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Output Dimension
	 * 
	 * @return The Output Dimension
	 * 
	 * @throws java.lang.Exception Thrown if the Output Dimension is Invalid
	 */

	public int outputDimension()
		throws java.lang.Exception
	{
		org.drip.spaces.metric.GeneralizedMetricVectorSpace gmvsOutput = outputMetricVectorSpace();

		if (!(gmvsOutput instanceof org.drip.spaces.metric.R1Continuous) && !(gmvsOutput instanceof
			org.drip.spaces.metric.RdContinuousBanach))
			throw new java.lang.Exception ("NormedRxToNormedRxFinite::dimension => Invalid Inputs");

		return gmvsOutput instanceof org.drip.spaces.metric.R1Continuous ? 1 :
			((org.drip.spaces.metric.RdContinuousBanach) gmvsOutput).dimension();
	}

	/**
	 * Compute the Maurey Covering Number Upper Bounds for Operator Population Metric Norm
	 * 
	 * @return The Maurey Operator Covering Number Upper Bounds Instance Corresponding to the Operator
	 *  Population Metric Norm
	 */

	public org.drip.spaces.cover.MaureyOperatorCoveringBounds populationMetricCoveringBounds()
	{
		try {
			return new org.drip.spaces.cover.MaureyOperatorCoveringBounds (_dblMaureyConstant,
				outputDimension(), operatorPopulationMetricNorm());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Maurey Covering Number Upper Bounds for Operator Population Supremum Norm
	 * 
	 * @return The Maurey Operator Covering Number Upper Bounds Instance Corresponding to the Operator
	 *  Population Supremum Norm
	 */

	public org.drip.spaces.cover.MaureyOperatorCoveringBounds populationSupremumCoveringBounds()
	{
		try {
			return new org.drip.spaces.cover.MaureyOperatorCoveringBounds (_dblMaureyConstant,
				outputDimension(), operatorPopulationSupremumNorm());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Maurey Covering Number Upper Bounds for Operator Sample Metric Norm
	 * 
	 * @param gvvi The Validated Vector Space Instance
	 * 
	 * @return The Maurey Operator Covering Number Upper Bounds Instance Corresponding to the Operator Sample
	 *  Metric Norm
	 */

	public org.drip.spaces.cover.MaureyOperatorCoveringBounds sampleMetricCoveringBounds (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
	{
		try {
			return new org.drip.spaces.cover.MaureyOperatorCoveringBounds (_dblMaureyConstant,
				outputDimension(), operatorSampleMetricNorm (gvvi));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Maurey Covering Number Upper Bounds for Operator Sample Supremum Norm
	 * 
	 * @param gvvi The Validated Vector Space Instance
	 * 
	 * @return The Maurey Operator Covering Number Upper Bounds Instance Corresponding to the Operator Sample
	 *  Supremum Norm
	 */

	public org.drip.spaces.cover.MaureyOperatorCoveringBounds sampleSupremumCoveringBounds (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
	{
		try {
			return new org.drip.spaces.cover.MaureyOperatorCoveringBounds (_dblMaureyConstant,
				outputDimension(), operatorSampleSupremumNorm (gvvi));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
