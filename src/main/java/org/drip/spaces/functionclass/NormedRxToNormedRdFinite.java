
package org.drip.spaces.functionclass;

import org.drip.numerical.common.NumberUtil;
import org.drip.spaces.cover.FunctionClassCoveringBounds;
import org.drip.spaces.instance.GeneralizedValidatedVector;
import org.drip.spaces.metric.GeneralizedMetricVectorSpace;
import org.drip.spaces.metric.RdNormed;
import org.drip.spaces.rxtord.NormedRxToNormedRd;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>NormedRxToNormedRdFinite</i> implements the Class F with f E f : Normed R<sup>x</sup> To Normed
 * 	R<sup>d</sup> Space of Finite Functions. The References are:
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
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>NormedRxToNormedRdFinite</i> Constructor</li>
 * 		<li>Retrieve the Array of Function Spaces in the Class</li>
 * 		<li>Estimate for the Function Class Population Covering Number Array, one for each dimension</li>
 * 		<li>Estimate for the Function Class Population Supremum Covering Number Array, one for each dimension</li>
 * 		<li>Estimate for the Scale-Sensitive Sample Covering Number Array for the specified Cover Size</li>
 * 		<li>Estimate for the Scale-Sensitive Sample Supremum Covering Number for the specified Cover Size</li>
 * 		<li>Compute the Population R<sup>d</sup> Metric Norm</li>
 * 		<li>Compute the Population R<sup>d</sup> Supremum Norm</li>
 * 		<li>Compute the Sample R<sup>d</sup> Metric Norm</li>
 * 		<li>Compute the Sample R<sup>d</sup> Supremum Norm</li>
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

public class NormedRxToNormedRdFinite extends NormedRxToNormedRxFinite
{
	private NormedRxToNormedRd[] _normedRxToNormedRdArray = null;

	/**
	 * <i>NormedRxToNormedRdFinite</i> Constructor
	 * 
	 * @param maureyConstant Maurey Constant
	 * @param normedRxToNormedRdArray Array of the Normed R<sup>x</sup> To Normed R<sup>d</sup> Spaces
	 *  
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public NormedRxToNormedRdFinite (
		final double maureyConstant,
		final NormedRxToNormedRd[] normedRxToNormedRdArray)
		throws Exception
	{
		super (maureyConstant);

		int classSize = null == (_normedRxToNormedRdArray = normedRxToNormedRdArray) ? 0 :
			_normedRxToNormedRdArray.length;

		if (null != _normedRxToNormedRdArray && 0 == classSize) {
			throw new java.lang.Exception ("NormedRxToNormedRdFinite ctr: Invalid Inputs");
		}

		for (int i = 0; i < classSize; ++i) {
			if (null == _normedRxToNormedRdArray[i])
				throw new Exception ("NormedRxToNormedRdFinite ctr: Invalid Inputs");
		}
	}

	@Override public FunctionClassCoveringBounds agnosticCoveringNumberBounds()
	{
		return null;
	}

	@Override public GeneralizedMetricVectorSpace inputMetricVectorSpace()
	{
		return null == _normedRxToNormedRdArray ? null :
			_normedRxToNormedRdArray[0].inputMetricVectorSpace();
	}

	@Override public RdNormed outputMetricVectorSpace()
	{
		return null == _normedRxToNormedRdArray ?
			null : _normedRxToNormedRdArray[0].outputMetricVectorSpace();
	}

	/**
	 * Retrieve the Array of Function Spaces in the Class
	 * 
	 * @return The Array of Function Spaces in the Class
	 */

	public NormedRxToNormedRd[] functionSpaces()
	{
		return _normedRxToNormedRdArray;
	}

	/**
	 * Estimate for the Function Class Population Covering Number Array, one for each dimension
	 * 
	 * @param coverArray The Size of the Cover Array
	 * 
	 * @return Function Class Population Covering Number Estimate Array, one for each dimension
	 */

	public double[] populationCoveringNumber (
		final double[] coverArray)
	{
		if (null == _normedRxToNormedRdArray || null == coverArray) {
			return null;
		}

		int functionSpaceSize = _normedRxToNormedRdArray.length;

		if (functionSpaceSize != coverArray.length) {
			return null;
		}

		double[] populationCoveringNumberArray = _normedRxToNormedRdArray[0].populationCoveringNumber
			(coverArray[0]);

		if (!NumberUtil.IsValid (populationCoveringNumberArray)) {
			return null;
		}

		for (int i = 1; i < functionSpaceSize; ++i) {
			double[] functionPopulationCoveringNumberArray =
				_normedRxToNormedRdArray[i].populationCoveringNumber (coverArray[i]);

			if (!NumberUtil.IsValid (functionPopulationCoveringNumberArray)) {
				return null;
			}

			int dimension = functionPopulationCoveringNumberArray.length;

			for (int j = 0; j < dimension; ++j) {
				if (populationCoveringNumberArray[j] < functionPopulationCoveringNumberArray[j])
					populationCoveringNumberArray[j] = functionPopulationCoveringNumberArray[j];
			}
		}

		return populationCoveringNumberArray;
	}

	/**
	 * Estimate for the Function Class Population Covering Number Array, one for each dimension
	 * 
	 * @param cover The Cover
	 * 
	 * @return Function Class Population Covering Number Estimate Array, one for each dimension
	 */

	public double[] populationCoveringNumber (
		final double cover)
	{
		int dimension = outputMetricVectorSpace().dimension();

		double[] coverArray = new double[dimension];

		for (int i = 0; i < dimension; ++i) {
			coverArray[i] = cover;
		}

		return populationCoveringNumber (cover);
	}

	/**
	 * Estimate for the Function Class Population Supremum Covering Number Array, one for each dimension
	 * 
	 * @param coverArray The Size of the Cover Array
	 * 
	 * @return Function Class Population Supremum Covering Number Estimate Array, one for each dimension
	 */

	public double[] populationSupremumCoveringNumber (
		final double[] coverArray)
	{
		if (null == _normedRxToNormedRdArray || null == coverArray) {
			return null;
		}

		int functionSpaceSize = _normedRxToNormedRdArray.length;

		if (functionSpaceSize != coverArray.length) {
			return null;
		}

		double[] populationSupremumCoveringNumberArray =
			_normedRxToNormedRdArray[0].populationSupremumCoveringNumber (coverArray[0]);

		if (!NumberUtil.IsValid (populationSupremumCoveringNumberArray)) {
			return null;
		}

		for (int i = 1; i < functionSpaceSize; ++i) {
			double[] functionPopulationSupremumCoveringNumberArray =
				_normedRxToNormedRdArray[i].populationSupremumCoveringNumber (coverArray[i]);

			if (!NumberUtil.IsValid (functionPopulationSupremumCoveringNumberArray)) {
				return null;
			}

			int dimension = functionPopulationSupremumCoveringNumberArray.length;

			for (int j = 0; j < dimension; ++j) {
				if (populationSupremumCoveringNumberArray[j] <
					functionPopulationSupremumCoveringNumberArray[j])
				{
					populationSupremumCoveringNumberArray[j] =
						functionPopulationSupremumCoveringNumberArray[j];
				}
			}
		}

		return populationSupremumCoveringNumberArray;
	}

	/**
	 * Estimate for the Function Class Population Supremum Covering Number Array, one for each dimension
	 * 
	 * @param cover The Cover
	 * 
	 * @return Function Class Population Covering Supremum Number Estimate Array, one for each dimension
	 */

	public double[] populationSupremumCoveringNumber (
		final double cover)
	{
		int dimension = outputMetricVectorSpace().dimension();

		double[] coverArray = new double[dimension];

		for (int i = 0; i < dimension; ++i) {
			coverArray[i] = cover;
		}

		return populationSupremumCoveringNumber (coverArray);
	}

	/**
	 * Estimate for the Scale-Sensitive Sample Covering Number Array for the specified Cover Size
	 * 
	 * @param generalizedValidatedVector The Validated Instance Vector Sequence
	 * @param coverArray The Size of the Cover Array
	 * 
	 * @return The Scale-Sensitive Sample Covering Number Array for the specified Cover Size
	 */

	public double[] sampleCoveringNumber (
		final GeneralizedValidatedVector generalizedValidatedVector,
		final double[] coverArray)
	{
		if (null == _normedRxToNormedRdArray || null == coverArray) {
			return null;
		}

		int functionSpaceSize = _normedRxToNormedRdArray.length;

		if (functionSpaceSize != coverArray.length) {
			return null;
		}

		double[] sampleCoveringNumberArray = _normedRxToNormedRdArray[0].sampleCoveringNumber (
			generalizedValidatedVector,
			coverArray[0]
		);

		if (!NumberUtil.IsValid (sampleCoveringNumberArray)) {
			return null;
		}

		for (int i = 1; i < functionSpaceSize; ++i) {
			double[] functionSampleCoveringNumberArray = _normedRxToNormedRdArray[i].sampleCoveringNumber (
				generalizedValidatedVector,
				coverArray[i]
			);

			if (!NumberUtil.IsValid (functionSampleCoveringNumberArray)) {
				return null;
			}

			int dimension = functionSampleCoveringNumberArray.length;

			for (int j = 0; j < dimension; ++j) {
				if (sampleCoveringNumberArray[j] < functionSampleCoveringNumberArray[j])
					sampleCoveringNumberArray[j] = functionSampleCoveringNumberArray[j];
			}
		}

		return sampleCoveringNumberArray;
	}

	/**
	 * Estimate for the Scale-Sensitive Sample Covering Number Array for the specified Cover Size
	 * 
	 * @param generalizedValidatedVector The Validated Instance Vector Sequence
	 * @param cover The Size of the Cover Array
	 * 
	 * @return The Scale-Sensitive Sample Covering Number Array for the specified Cover Size
	 */

	public double[] sampleCoveringNumber (
		final GeneralizedValidatedVector generalizedValidatedVector,
		final double cover)
	{
		int dimension = outputMetricVectorSpace().dimension();

		double[] coverArray = new double[dimension];

		for (int i = 0; i < dimension; ++i) {
			coverArray[i] = cover;
		}

		return sampleCoveringNumber (generalizedValidatedVector, coverArray);
	}

	/**
	 * Estimate for the Scale-Sensitive Sample Supremum Covering Number for the specified Cover Size
	 * 
	 * @param generalizedValidatedVector The Validated Instance Vector Sequence
	 * @param coverArray The Cover Array
	 * 
	 * @return The Scale-Sensitive Sample Supremum Covering Number for the specified Cover Size
	 */

	public double[] sampleSupremumCoveringNumber (
		final GeneralizedValidatedVector generalizedValidatedVector,
		final double[] coverArray)
	{
		if (null == _normedRxToNormedRdArray || null == coverArray) {
			return null;
		}

		int functionSpaceSize = _normedRxToNormedRdArray.length;

		if (functionSpaceSize != coverArray.length) {
			return null;
		}

		double[] sampleSupremumCoveringNumberArray = _normedRxToNormedRdArray[0].sampleSupremumCoveringNumber
			(generalizedValidatedVector, coverArray[0]);

		if (!NumberUtil.IsValid (sampleSupremumCoveringNumberArray)) {
			return null;
		}

		for (int i = 1; i < functionSpaceSize; ++i) {
			double[] functionSampleSupremumCoveringNumberArray =
				_normedRxToNormedRdArray[i].sampleSupremumCoveringNumber (
					generalizedValidatedVector,
					coverArray[i]
				);

			if (!NumberUtil.IsValid (functionSampleSupremumCoveringNumberArray)) {
				return null;
			}

			int dimension = functionSampleSupremumCoveringNumberArray.length;

			for (int j = 0; j < dimension; ++j) {
				if (sampleSupremumCoveringNumberArray[j] < functionSampleSupremumCoveringNumberArray[j]) {
					sampleSupremumCoveringNumberArray[j] = functionSampleSupremumCoveringNumberArray[j];
				}
			}
		}

		return sampleSupremumCoveringNumberArray;
	}

	/**
	 * Estimate for the Scale-Sensitive Sample Supremum Covering Number for the specified Cover Size
	 * 
	 * @param generalizedValidatedVector The Validated Instance Vector Sequence
	 * @param cover The Cover
	 * 
	 * @return The Scale-Sensitive Sample Supremum Covering Number for the specified Cover Size
	 */

	public double[] sampleSupremumCoveringNumber (
		final GeneralizedValidatedVector generalizedValidatedVector,
		final double cover)
	{
		int dimension = outputMetricVectorSpace().dimension();

		double[] coverArray = new double[dimension];

		for (int i = 0; i < dimension; ++i) {
			coverArray[i] = cover;
		}

		return sampleSupremumCoveringNumber (generalizedValidatedVector, coverArray);
	}

	/**
	 * Compute the Population R<sup>d</sup> Metric Norm
	 * 
	 * @return The Population R<sup>d</sup> Metric Norm
	 */

	public double[] populationRdMetricNorm()
	{
		if (null == _normedRxToNormedRdArray) {
			return null;
		}

		int functionCount = _normedRxToNormedRdArray.length;

		double[] populationRdMetricNormArray = _normedRxToNormedRdArray[0].populationMetricNorm();

		if (!NumberUtil.IsValid (populationRdMetricNormArray)) {
			return null;
		}

		for (int i = 1; i < functionCount; ++i) {
			double[] populationMetricNormArray = _normedRxToNormedRdArray[i].populationMetricNorm();

			if (!org.drip.numerical.common.NumberUtil.IsValid (populationMetricNormArray)) {
				return null;
			}

			int dimension = populationMetricNormArray.length;

			for (int j = 0; j < dimension; ++j) {
				if (populationRdMetricNormArray[j] < populationMetricNormArray[j]) {
					populationRdMetricNormArray[j] = populationMetricNormArray[j];
				}
			}
		}

		return populationRdMetricNormArray;
	}

	/**
	 * Compute the Population R<sup>d</sup> Supremum Norm
	 * 
	 * @return The Population R<sup>d</sup> Supremum Norm
	 */

	public double[] populationRdSupremumNorm()
	{
		if (null == _normedRxToNormedRdArray) {
			return null;
		}

		int functionCount = _normedRxToNormedRdArray.length;

		double[] populationRdSupremumNormArray = _normedRxToNormedRdArray[0].populationESS();

		if (!NumberUtil.IsValid (populationRdSupremumNormArray)) {
			return null;
		}

		for (int i = 1; i < functionCount; ++i) {
			double[] populationSupremumNormArray = _normedRxToNormedRdArray[i].populationESS();

			if (!NumberUtil.IsValid (populationSupremumNormArray)) {
				return null;
			}

			int dimension = populationSupremumNormArray.length;

			for (int j = 0; j < dimension; ++j) {
				if (populationRdSupremumNormArray[j] < populationSupremumNormArray[j]) {
					populationRdSupremumNormArray[j] = populationSupremumNormArray[j];
				}
			}
		}

		return populationRdSupremumNormArray;
	}

	/**
	 * Compute the Sample R<sup>d</sup> Metric Norm
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * 
	 * @return The Sample R<sup>d</sup> Metric Norm
	 */

	public double[] sampleRdMetricNorm (
		final GeneralizedValidatedVector generalizedValidatedVector)
	{
		if (null == _normedRxToNormedRdArray) {
			return null;
		}

		int functionCount = _normedRxToNormedRdArray.length;

		double[] sampleRdMetricNormArray = _normedRxToNormedRdArray[0].sampleMetricNorm (
			generalizedValidatedVector
		);

		if (!NumberUtil.IsValid (sampleRdMetricNormArray)) {
			return null;
		}

		for (int i = 1; i < functionCount; ++i) {
			double[] sampleMetricNormArray = _normedRxToNormedRdArray[i].sampleMetricNorm (
				generalizedValidatedVector
			);

			if (!NumberUtil.IsValid (sampleMetricNormArray)) {
				return null;
			}

			int dimension = sampleMetricNormArray.length;

			for (int j = 0; j < dimension; ++j) {
				if (sampleRdMetricNormArray[j] < sampleMetricNormArray[j]) {
					sampleRdMetricNormArray[j] = sampleMetricNormArray[j];
				}
			}
		}

		return sampleRdMetricNormArray;
	}

	/**
	 * Compute the Sample R<sup>d</sup> Supremum Norm
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * 
	 * @return The Sample R<sup>d</sup> Supremum Norm
	 */

	public double[] sampleRdSupremumNorm (
		final GeneralizedValidatedVector generalizedValidatedVector)
	{
		if (null == _normedRxToNormedRdArray) {
			return null;
		}

		int functionCount = _normedRxToNormedRdArray.length;

		double[] sampleRdSupremumNormArray = _normedRxToNormedRdArray[0].sampleSupremumNorm (
			generalizedValidatedVector
		);

		if (!NumberUtil.IsValid (sampleRdSupremumNormArray)) {
			return null;
		}

		for (int i = 1; i < functionCount; ++i) {
			double[] sampleSupremumNormArray = _normedRxToNormedRdArray[i].sampleSupremumNorm (
				generalizedValidatedVector
			);

			if (!NumberUtil.IsValid (sampleSupremumNormArray)) {
				return null;
			}

			int dimension = sampleSupremumNormArray.length;

			for (int j = 0; j < dimension; ++j) {
				if (sampleRdSupremumNormArray[j] < sampleSupremumNormArray[j]) {
					sampleRdSupremumNormArray[j] = sampleSupremumNormArray[j];
				}
			}
		}

		return sampleRdSupremumNormArray;
	}

	@Override public double operatorPopulationMetricNorm()
		throws Exception
	{
		double[] populationMetricNormArray = populationRdMetricNorm();

		if (null == populationMetricNormArray) {
			throw new Exception ("NormedRxToNormedRdFinite::operatorPopulationMetricNorm => Invalid Inputs");
		}

		double operatorPopulationMetricNorm = Double.NaN;
		int dimension = populationMetricNormArray.length;

		if (0 == dimension) {
			throw new Exception ("NormedRxToNormedRdFinite::operatorPopulationMetricNorm => Invalid Inputs");
		}

		for (int j = 0; j < dimension; ++j) {
			if (0 == j) {
				operatorPopulationMetricNorm = populationMetricNormArray[j];
			} else {
				if (operatorPopulationMetricNorm < populationMetricNormArray[j]) {
					operatorPopulationMetricNorm = populationMetricNormArray[j];
				}
			}
		}

		return operatorPopulationMetricNorm;
	}

	@Override public double operatorPopulationSupremumNorm()
		throws Exception
	{
		double[] populationSupremumNormArray = populationRdSupremumNorm();

		if (null == populationSupremumNormArray) {
			throw new Exception (
				"NormedRxToNormedRdFinite::operatorPopulationSupremumNorm => Invalid Inputs"
			);
		}

		double operatorPopulationSupremumNorm = Double.NaN;
		int dimension = populationSupremumNormArray.length;

		if (0 == dimension) {
			throw new Exception (
				"NormedRxToNormedRdFinite::operatorPopulationSupremumNorm => Invalid Inputs"
			);
		}

		for (int j = 0; j < dimension; ++j) {
			if (0 == j) {
				operatorPopulationSupremumNorm = populationSupremumNormArray[j];
			} else {
				if (operatorPopulationSupremumNorm < populationSupremumNormArray[j]) {
					operatorPopulationSupremumNorm = populationSupremumNormArray[j];
				}
			}
		}

		return operatorPopulationSupremumNorm;
	}

	@Override public double operatorSampleMetricNorm (
		final GeneralizedValidatedVector generalizedValidatedVector)
		throws Exception
	{
		double[] sampleMetricNormArray = sampleRdMetricNorm (generalizedValidatedVector);

		if (null == sampleMetricNormArray) {
			throw new Exception ("NormedRxToNormedRdFinite::operatorSampleMetricNorm => Invalid Inputs");
		}

		double operatorSampleMetricNorm = Double.NaN;
		int dimension = sampleMetricNormArray.length;

		if (0 == dimension) {
			throw new Exception ("NormedRxToNormedRdFinite::operatorSampleMetricNorm => Invalid Inputs");
		}

		for (int j = 0; j < dimension; ++j) {
			if (0 == j) {
				operatorSampleMetricNorm = sampleMetricNormArray[j];
			} else {
				if (operatorSampleMetricNorm < sampleMetricNormArray[j]) {
					operatorSampleMetricNorm = sampleMetricNormArray[j];
				}
			}
		}

		return operatorSampleMetricNorm;
	}

	@Override public double operatorSampleSupremumNorm (
		final GeneralizedValidatedVector generalizedValidatedVector)
		throws Exception
	{
		double[] sampleSupremumNormArray = sampleRdSupremumNorm (generalizedValidatedVector);

		if (null == sampleSupremumNormArray) {
			throw new Exception ("NormedRxToNormedRdFinite::operatorSampleSupremumNorm => Invalid Inputs");
		}

		double operatorSampleSupremumNorm = Double.NaN;
		int dimension = sampleSupremumNormArray.length;

		if (0 == dimension) {
			throw new Exception ("NormedRxToNormedRdFinite::operatorSampleSupremumNorm => Invalid Inputs");
		}

		for (int j = 0; j < dimension; ++j) {
			if (0 == j) {
				operatorSampleSupremumNorm = sampleSupremumNormArray[j];
			} else {
				if (operatorSampleSupremumNorm < sampleSupremumNormArray[j]) {
					operatorSampleSupremumNorm = sampleSupremumNormArray[j];
				}
			}
		}

		return operatorSampleSupremumNorm;
	}
}
