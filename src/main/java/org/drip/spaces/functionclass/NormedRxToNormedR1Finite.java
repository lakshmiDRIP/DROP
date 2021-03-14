
package org.drip.spaces.functionclass;

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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>NormedRxToNormedR1Finite</i> implements the Class F with f E f : Normed R<sup>x</sup> To Normed
 * R<sup>1</sup> Space of Finite Functions. The Reference we've used is:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/functionclass/README.md">Normed Finite Spaces Function Class</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NormedRxToNormedR1Finite extends org.drip.spaces.functionclass.NormedRxToNormedRxFinite {
	private org.drip.spaces.rxtor1.NormedRxToNormedR1[] _aNormedRxToNormedR1 = null;

	protected NormedRxToNormedR1Finite (
		final double dblMaureyConstant,
		final org.drip.spaces.rxtor1.NormedRxToNormedR1[] aNormedRxToNormedR1)
		throws java.lang.Exception
	{
		super (dblMaureyConstant);

		int iClassSize = null == (_aNormedRxToNormedR1 = aNormedRxToNormedR1) ? 0 :
			_aNormedRxToNormedR1.length;

		if (null != _aNormedRxToNormedR1 && 0 == iClassSize)
			throw new java.lang.Exception ("NormedRxToNormedR1Finite ctr: Invalid Inputs");

		for (int i = 0; i < iClassSize; ++i) {
			if (null == _aNormedRxToNormedR1[i])
				throw new java.lang.Exception ("NormedRxToNormedR1Finite ctr: Invalid Inputs");
		}
	}

	@Override public org.drip.spaces.cover.FunctionClassCoveringBounds agnosticCoveringNumberBounds()
	{
		return null;
	}

	@Override public org.drip.spaces.metric.GeneralizedMetricVectorSpace inputMetricVectorSpace()
	{
		return null == _aNormedRxToNormedR1 ? null : _aNormedRxToNormedR1[0].inputMetricVectorSpace();
	}

	@Override public org.drip.spaces.metric.R1Normed outputMetricVectorSpace()
	{
		return null == _aNormedRxToNormedR1 ? null : _aNormedRxToNormedR1[0].outputMetricVectorSpace();
	}

	/**
	 * Retrieve the Array of Function Spaces in the Class
	 * 
	 * @return The Array of Function Spaces in the Class
	 */

	public org.drip.spaces.rxtor1.NormedRxToNormedR1[] functionSpaces()
	{
		return _aNormedRxToNormedR1;
	}

	/**
	 * Estimate for the Function Class Population Covering Number
	 * 
	 * @param dblCover The Size of the Cover
	 * 
	 * @return Function Class Population Covering Number Estimate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double populationCoveringNumber (
		final double dblCover)
		throws java.lang.Exception
	{
		if (null == _aNormedRxToNormedR1)
			throw new java.lang.Exception
				("NormedRxToNormedR1Finite::populationCoveringNumber => Finite Function Set Unspecified");

		int iFunctionSpaceSize = _aNormedRxToNormedR1.length;

		double dblPopulationCoveringNumber = _aNormedRxToNormedR1[0].populationCoveringNumber (dblCover);

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblPopulationCoveringNumber))
			throw new java.lang.Exception
				("NormedRxToNormedR1Finite::populationCoveringNumber => Cannot Compute Population Covering Number");

		for (int i = 1; i < iFunctionSpaceSize; ++i) {
			double dblFunctionPopulationCoveringNumber = _aNormedRxToNormedR1[i].populationCoveringNumber
				(dblCover);

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblFunctionPopulationCoveringNumber))
				throw new java.lang.Exception
					("NormedRxToNormedR1Finite::populationCoveringNumber => Cannot Compute Population Covering Number");

			if (dblPopulationCoveringNumber < dblFunctionPopulationCoveringNumber)
				dblPopulationCoveringNumber = dblFunctionPopulationCoveringNumber;
		}

		return dblPopulationCoveringNumber;
	}

	/**
	 * Estimate for the Function Class Population Supremum Covering Number
	 * 
	 * @param dblCover The Size of the Cover
	 * 
	 * @return Function Class Population Supremum Covering Number Estimate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double populationSupremumCoveringNumber (
		final double dblCover)
		throws java.lang.Exception
	{
		if (null == _aNormedRxToNormedR1)
			throw new java.lang.Exception
				("NormedRxToNormedR1Finite::populationSupremumCoveringNumber => Finite Function Set Unspecified");

		int iFunctionSpaceSize = _aNormedRxToNormedR1.length;

		double dblPopulationSupremumCoveringNumber = _aNormedRxToNormedR1[0].populationSupremumCoveringNumber
			(dblCover);

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblPopulationSupremumCoveringNumber))
			throw new java.lang.Exception
				("NormedRxToNormedR1Finite::populationSupremumCoveringNumber => Cannot Compute Population Supremum Covering Number");

		for (int i = 1; i < iFunctionSpaceSize; ++i) {
			double dblFunctionPopulationSupremumCoveringNumber =
				_aNormedRxToNormedR1[i].populationSupremumCoveringNumber (dblCover);

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblFunctionPopulationSupremumCoveringNumber))
				throw new java.lang.Exception
					("NormedRxToNormedR1Finite::populationSupremumCoveringNumber => Cannot Compute Population Supremum Covering Number");

			if (dblPopulationSupremumCoveringNumber < dblFunctionPopulationSupremumCoveringNumber)
				dblPopulationSupremumCoveringNumber = dblFunctionPopulationSupremumCoveringNumber;
		}

		return dblPopulationSupremumCoveringNumber;
	}

	/**
	 * Estimate for the Scale-Sensitive Sample Covering Number for the specified Cover Size
	 * 
	 * @param gvvi The Validated Instance Vector Sequence
	 * @param dblCover The Size of the Cover
	 * 
	 * @return The Scale-Sensitive Sample Covering Number for the specified Cover Size
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double sampleCoveringNumber (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi,
		final double dblCover)
		throws java.lang.Exception
	{
		if (null == _aNormedRxToNormedR1)
			throw new java.lang.Exception
				("NormedRxToNormedR1Finite::sampleCoveringNumber => Finite Function Set Unspecified");

		int iFunctionSpaceSize = _aNormedRxToNormedR1.length;

		double dblSampleCoveringNumber = _aNormedRxToNormedR1[0].sampleCoveringNumber (gvvi, dblCover);

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblSampleCoveringNumber))
			throw new java.lang.Exception
				("NormedRxToNormedR1Finite::sampleCoveringNumber => Cannot Compute Sample Covering Number");

		for (int i = 1; i < iFunctionSpaceSize; ++i) {
			double dblFunctionSampleCoveringNumber = _aNormedRxToNormedR1[i].sampleCoveringNumber (gvvi,
				dblCover);

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblFunctionSampleCoveringNumber))
				throw new java.lang.Exception
					("NormedRxToNormedR1Finite::sampleCoveringNumber => Cannot Compute Sample Covering Number");

			if (dblSampleCoveringNumber < dblFunctionSampleCoveringNumber)
				dblSampleCoveringNumber = dblFunctionSampleCoveringNumber;
		}

		return dblSampleCoveringNumber;
	}

	/**
	 * Estimate for the Scale-Sensitive Sample Supremum Covering Number for the specified Cover Size
	 * 
	 * @param gvvi The Validated Instance Vector Sequence
	 * @param dblCover The Size of the Cover
	 * 
	 * @return The Scale-Sensitive Sample Supremum Covering Number for the specified Cover Size
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double sampleSupremumCoveringNumber (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi,
		final double dblCover)
		throws java.lang.Exception
	{
		if (null == _aNormedRxToNormedR1)
			throw new java.lang.Exception
				("NormedRxToNormedR1Finite::sampleSupremumCoveringNumber => Finite Function Set Unspecified");

		int iFunctionSpaceSize = _aNormedRxToNormedR1.length;

		double dblSampleSupremumCoveringNumber = _aNormedRxToNormedR1[0].sampleSupremumCoveringNumber (gvvi,
			dblCover);

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblSampleSupremumCoveringNumber))
			throw new java.lang.Exception
				("NormedRxToNormedR1Finite::sampleSupremumCoveringNumber => Cannot Compute Sample Supremum Covering Number");

		for (int i = 1; i < iFunctionSpaceSize; ++i) {
			double dblFunctionSampleSupremumCoveringNumber =
				_aNormedRxToNormedR1[i].sampleSupremumCoveringNumber (gvvi, dblCover);

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblFunctionSampleSupremumCoveringNumber))
				throw new java.lang.Exception
					("NormedRxToNormedR1Finite::sampleSupremumCoveringNumber => Cannot Compute Sample Supremum Covering Number");

			if (dblSampleSupremumCoveringNumber < dblFunctionSampleSupremumCoveringNumber)
				dblSampleSupremumCoveringNumber = dblFunctionSampleSupremumCoveringNumber;
		}

		return dblSampleSupremumCoveringNumber;
	}

	@Override public double operatorPopulationMetricNorm()
		throws java.lang.Exception
	{
		if (null == _aNormedRxToNormedR1)
			throw new java.lang.Exception
				("NormedRxToNormedR1Finite::operatorPopulationMetricNorm => Finite Function Set Unspecified");

		int iNumFunction = _aNormedRxToNormedR1.length;

		double dblOperatorPopulationMetricNorm = _aNormedRxToNormedR1[0].populationMetricNorm();

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblOperatorPopulationMetricNorm))
			throw new java.lang.Exception
				("NormedRxToNormedR1Finite::operatorPopulationMetricNorm => Cannot compute Population Metric Norm for Function #"
					+ 0);

		for (int i = 1; i < iNumFunction; ++i) {
			double dblPopulationMetricNorm = _aNormedRxToNormedR1[i].populationMetricNorm();

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblPopulationMetricNorm))
				throw new java.lang.Exception
					("NormedRxToNormedR1Finite::operatorPopulationMetricNorm => Cannot compute Population Metric Norm for Function #"
						+ i);

			if (dblOperatorPopulationMetricNorm > dblPopulationMetricNorm)
				dblOperatorPopulationMetricNorm = dblPopulationMetricNorm;
		}

		return dblOperatorPopulationMetricNorm;
	}

	@Override public double operatorPopulationSupremumNorm()
		throws java.lang.Exception
	{
		if (null == _aNormedRxToNormedR1)
			throw new java.lang.Exception
				("NormedRxToNormedR1Finite::operatorPopulationSupremumNorm => Finite Function Set Unspecified");

		int iNumFunction = _aNormedRxToNormedR1.length;

		double dblOperatorPopulationSupremumNorm = _aNormedRxToNormedR1[0].populationESS();

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblOperatorPopulationSupremumNorm))
			throw new java.lang.Exception
				("NormedRxToNormedR1Finite::operatorPopulationSupremumNorm => Cannot compute Population Supremum Norm for Function #"
					+ 0);

		for (int i = 1; i < iNumFunction; ++i) {
			double dblPopulationSupremumNorm = _aNormedRxToNormedR1[i].populationESS();

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblPopulationSupremumNorm))
				throw new java.lang.Exception
					("NormedRxToNormedR1Finite::operatorPopulationSupremumNorm => Cannot compute Population Supremum Norm for Function #"
						+ i);

			if (dblOperatorPopulationSupremumNorm > dblPopulationSupremumNorm)
				dblOperatorPopulationSupremumNorm = dblPopulationSupremumNorm;
		}

		return dblOperatorPopulationSupremumNorm;
	}

	@Override public double operatorSampleMetricNorm (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
		throws java.lang.Exception
	{
		if (null == _aNormedRxToNormedR1)
			throw new java.lang.Exception
				("NormedRxToNormedR1Finite::operatorSampleMetricNorm => Finite Function Set Unspecified");

		int iNumFunction = _aNormedRxToNormedR1.length;

		double dblOperatorSampleMetricNorm = _aNormedRxToNormedR1[0].sampleMetricNorm (gvvi);

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblOperatorSampleMetricNorm))
			throw new java.lang.Exception
				("NormedRxToNormedR1Finite::operatorSampleMetricNorm => Cannot compute Sample Metric Norm for Function #"
					+ 0);

		for (int i = 1; i < iNumFunction; ++i) {
			double dblSampleMetricNorm = _aNormedRxToNormedR1[i].sampleMetricNorm (gvvi);

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblSampleMetricNorm))
				throw new java.lang.Exception
					("NormedRxToNormedR1Finite::operatorSampleMetricNorm => Cannot compute Sample Metric Norm for Function #"
						+ i);

			if (dblOperatorSampleMetricNorm > dblSampleMetricNorm)
				dblOperatorSampleMetricNorm = dblSampleMetricNorm;
		}

		return dblOperatorSampleMetricNorm;
	}

	@Override public double operatorSampleSupremumNorm (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
		throws java.lang.Exception
	{
		if (null == _aNormedRxToNormedR1)
			throw new java.lang.Exception
				("NormedRxToNormedR1Finite::operatorSampleSupremumNorm => Finite Function Set Unspecified");

		int iNumFunction = _aNormedRxToNormedR1.length;

		double dblOperatorSampleSupremumNorm = _aNormedRxToNormedR1[0].sampleSupremumNorm (gvvi);

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblOperatorSampleSupremumNorm))
			throw new java.lang.Exception
				("NormedRxToNormedR1Finite::operatorSampleSupremumNorm => Cannot compute Sample Supremum Norm for Function #"
					+ 0);

		for (int i = 1; i < iNumFunction; ++i) {
			double dblSampleSupremumNorm = _aNormedRxToNormedR1[i].sampleSupremumNorm (gvvi);

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblSampleSupremumNorm))
				throw new java.lang.Exception
					("NormedRxToNormedR1Finite::operatorSampleSupremumNorm => Cannot compute Sample Supremum Norm for Function #"
						+ i);

			if (dblOperatorSampleSupremumNorm > dblSampleSupremumNorm)
				dblOperatorSampleSupremumNorm = dblSampleSupremumNorm;
		}

		return dblOperatorSampleSupremumNorm;
	}
}
