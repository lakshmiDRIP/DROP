
package org.drip.spaces.functionclass;

import org.drip.numerical.common.NumberUtil;
import org.drip.spaces.cover.FunctionClassCoveringBounds;
import org.drip.spaces.instance.GeneralizedValidatedVector;
import org.drip.spaces.metric.GeneralizedMetricVectorSpace;
import org.drip.spaces.metric.R1Normed;
import org.drip.spaces.rxtor1.NormedRxToNormedR1;

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
 * <i>NormedRxToNormedR1Finite</i> implements the Class F with f E f : Normed R<sup>x</sup> To Normed
 * 	R<sup>1</sup> Space of Finite Functions. The Reference we've used is:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  </ul>
 *
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Retrieve the Array of Function Spaces in the Class</li>
 * 		<li>Estimate for the Function Class Population Covering Number</li>
 * 		<li>Estimate for the Function Class Population Supremum Covering Number</li>
 * 		<li>Estimate for the Scale-Sensitive Sample Covering Number for the specified Cover Size</li>
 * 		<li>Estimate for the Scale-Sensitive Sample Supremum Covering Number for the specified Cover Size</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/functionclass/README.md">Normed Finite Spaces Function Class</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NormedRxToNormedR1Finite extends NormedRxToNormedRxFinite
{
	private NormedRxToNormedR1[] _normedRxToNormedR1Array = null;

	protected NormedRxToNormedR1Finite (
		final double maureyConstant,
		final NormedRxToNormedR1[] normedRxToNormedR1Array)
		throws Exception
	{
		super (maureyConstant);

		int classSize = null == (_normedRxToNormedR1Array = normedRxToNormedR1Array) ? 0 :
			_normedRxToNormedR1Array.length;

		if (null != _normedRxToNormedR1Array && 0 == classSize) {
			throw new Exception ("NormedRxToNormedR1Finite ctr: Invalid Inputs");
		}

		for (int i = 0; i < classSize; ++i) {
			if (null == _normedRxToNormedR1Array[i]) {
				throw new Exception ("NormedRxToNormedR1Finite ctr: Invalid Inputs");
			}
		}
	}

	@Override public FunctionClassCoveringBounds agnosticCoveringNumberBounds()
	{
		return null;
	}

	@Override public GeneralizedMetricVectorSpace inputMetricVectorSpace()
	{
		return null == _normedRxToNormedR1Array ? null :
		_normedRxToNormedR1Array[0].inputMetricVectorSpace();
	}

	@Override public R1Normed outputMetricVectorSpace()
	{
		return null == _normedRxToNormedR1Array ? null :
			_normedRxToNormedR1Array[0].outputMetricVectorSpace();
	}

	/**
	 * Retrieve the Array of Function Spaces in the Class
	 * 
	 * @return The Array of Function Spaces in the Class
	 */

	public NormedRxToNormedR1[] functionSpaces()
	{
		return _normedRxToNormedR1Array;
	}

	/**
	 * Estimate for the Function Class Population Covering Number
	 * 
	 * @param cover The Size of the Cover
	 * 
	 * @return Function Class Population Covering Number Estimate
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double populationCoveringNumber (
		final double cover)
		throws Exception
	{
		if (null == _normedRxToNormedR1Array) {
			throw new Exception (
				"NormedRxToNormedR1Finite::populationCoveringNumber => Finite Function Set Unspecified"
			);
		}

		int functionSpaceSize = _normedRxToNormedR1Array.length;

		double populationCoveringNumber = _normedRxToNormedR1Array[0].populationCoveringNumber (cover);

		if (!NumberUtil.IsValid (populationCoveringNumber)) {
			throw new Exception (
				"NormedRxToNormedR1Finite::populationCoveringNumber => Cannot Compute Population Covering Number"
			);
		}

		for (int i = 1; i < functionSpaceSize; ++i) {
			double functionPopulationCoveringNumber =
				_normedRxToNormedR1Array[i].populationCoveringNumber (cover);

			if (!NumberUtil.IsValid (functionPopulationCoveringNumber)) {
				throw new Exception (
					"NormedRxToNormedR1Finite::populationCoveringNumber => Cannot Compute Population Covering Number"
				);
			}

			if (populationCoveringNumber < functionPopulationCoveringNumber) {
				populationCoveringNumber = functionPopulationCoveringNumber;
			}
		}

		return populationCoveringNumber;
	}

	/**
	 * Estimate for the Function Class Population Supremum Covering Number
	 * 
	 * @param cover The Size of the Cover
	 * 
	 * @return Function Class Population Supremum Covering Number Estimate
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double populationSupremumCoveringNumber (
		final double cover)
		throws Exception
	{
		if (null == _normedRxToNormedR1Array) {
			throw new Exception (
				"NormedRxToNormedR1Finite::populationSupremumCoveringNumber => Finite Function Set Unspecified"
			);
		}

		int functionSpaceSize = _normedRxToNormedR1Array.length;

		double populationSupremumCoveringNumber =
			_normedRxToNormedR1Array[0].populationSupremumCoveringNumber (cover);

		if (!NumberUtil.IsValid (populationSupremumCoveringNumber)) {
			throw new Exception (
				"NormedRxToNormedR1Finite::populationSupremumCoveringNumber => Cannot Compute Population Supremum Covering Number"
			);
		}

		for (int i = 1; i < functionSpaceSize; ++i) {
			double functionPopulationSupremumCoveringNumber =
				_normedRxToNormedR1Array[i].populationSupremumCoveringNumber (cover);

			if (!NumberUtil.IsValid (functionPopulationSupremumCoveringNumber)) {
				throw new Exception (
					"NormedRxToNormedR1Finite::populationSupremumCoveringNumber => Cannot Compute Population Supremum Covering Number"
				);
			}

			if (populationSupremumCoveringNumber < functionPopulationSupremumCoveringNumber) {
				populationSupremumCoveringNumber = functionPopulationSupremumCoveringNumber;
			}
		}

		return populationSupremumCoveringNumber;
	}

	/**
	 * Estimate for the Scale-Sensitive Sample Covering Number for the specified Cover Size
	 * 
	 * @param generalizedValidatedVector The Validated Instance Vector Sequence
	 * @param cover The Size of the Cover
	 * 
	 * @return The Scale-Sensitive Sample Covering Number for the specified Cover Size
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double sampleCoveringNumber (
		final GeneralizedValidatedVector generalizedValidatedVector,
		final double cover)
		throws Exception
	{
		if (null == _normedRxToNormedR1Array) {
			throw new Exception (
				"NormedRxToNormedR1Finite::sampleCoveringNumber => Finite Function Set Unspecified"
			);
		}

		int functionSpaceSize = _normedRxToNormedR1Array.length;

		double sampleCoveringNumber = _normedRxToNormedR1Array[0].sampleCoveringNumber (
			generalizedValidatedVector,
			cover
		);

		if (!NumberUtil.IsValid (sampleCoveringNumber)) {
			throw new Exception (
				"NormedRxToNormedR1Finite::sampleCoveringNumber => Cannot Compute Sample Covering Number"
			);
		}

		for (int i = 1; i < functionSpaceSize; ++i) {
			double functionSampleCoveringNumber = _normedRxToNormedR1Array[i].sampleCoveringNumber (
				generalizedValidatedVector,
				cover
			);

			if (!NumberUtil.IsValid (functionSampleCoveringNumber)) {
				throw new Exception (
					"NormedRxToNormedR1Finite::sampleCoveringNumber => Cannot Compute Sample Covering Number"
				);
			}

			if (sampleCoveringNumber < functionSampleCoveringNumber) {
				sampleCoveringNumber = functionSampleCoveringNumber;
			}
		}

		return sampleCoveringNumber;
	}

	/**
	 * Estimate for the Scale-Sensitive Sample Supremum Covering Number for the specified Cover Size
	 * 
	 * @param generalizedValidatedVector The Validated Instance Vector Sequence
	 * @param cover The Size of the Cover
	 * 
	 * @return The Scale-Sensitive Sample Supremum Covering Number for the specified Cover Size
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double sampleSupremumCoveringNumber (
		final GeneralizedValidatedVector generalizedValidatedVector,
		final double cover)
		throws Exception
	{
		if (null == _normedRxToNormedR1Array) {
			throw new Exception (
				"NormedRxToNormedR1Finite::sampleSupremumCoveringNumber => Finite Function Set Unspecified"
			);
		}

		int functionSpaceSize = _normedRxToNormedR1Array.length;

		double sampleSupremumCoveringNumber = _normedRxToNormedR1Array[0].sampleSupremumCoveringNumber (
			generalizedValidatedVector,
			cover
		);

		if (!NumberUtil.IsValid (sampleSupremumCoveringNumber)) {
			throw new Exception (
				"NormedRxToNormedR1Finite::sampleSupremumCoveringNumber => Cannot Compute Sample Supremum Covering Number"
			);
		}

		for (int i = 1; i < functionSpaceSize; ++i) {
			double functionSampleSupremumCoveringNumber =
				_normedRxToNormedR1Array[i].sampleSupremumCoveringNumber (generalizedValidatedVector, cover);

			if (!NumberUtil.IsValid (functionSampleSupremumCoveringNumber)) {
				throw new Exception (
					"NormedRxToNormedR1Finite::sampleSupremumCoveringNumber => Cannot Compute Sample Supremum Covering Number"
				);
			}

			if (sampleSupremumCoveringNumber < functionSampleSupremumCoveringNumber) {
				sampleSupremumCoveringNumber = functionSampleSupremumCoveringNumber;
			}
		}

		return sampleSupremumCoveringNumber;
	}

	@Override public double operatorPopulationMetricNorm()
		throws Exception
	{
		if (null == _normedRxToNormedR1Array) {
			throw new Exception (
				"NormedRxToNormedR1Finite::operatorPopulationMetricNorm => Finite Function Set Unspecified"
			);
		}

		int functionCount = _normedRxToNormedR1Array.length;

		double operatorPopulationMetricNorm = _normedRxToNormedR1Array[0].populationMetricNorm();

		if (!NumberUtil.IsValid (operatorPopulationMetricNorm)) {
			throw new Exception (
				"NormedRxToNormedR1Finite::operatorPopulationMetricNorm => Cannot compute Population Metric Norm for Function #"
					+ 0
			);
		}

		for (int i = 1; i < functionCount; ++i) {
			double populationMetricNorm = _normedRxToNormedR1Array[i].populationMetricNorm();

			if (!NumberUtil.IsValid (populationMetricNorm)) {
				throw new Exception (
					"NormedRxToNormedR1Finite::operatorPopulationMetricNorm => Cannot compute Population Metric Norm for Function #"
						+ i
				);
			}

			if (operatorPopulationMetricNorm > populationMetricNorm) {
				operatorPopulationMetricNorm = populationMetricNorm;
			}
		}

		return operatorPopulationMetricNorm;
	}

	@Override public double operatorPopulationSupremumNorm()
		throws Exception
	{
		if (null == _normedRxToNormedR1Array) {
			throw new Exception (
				"NormedRxToNormedR1Finite::operatorPopulationSupremumNorm => Finite Function Set Unspecified"
			);
		}

		int functionCount = _normedRxToNormedR1Array.length;

		double operatorPopulationSupremumNorm = _normedRxToNormedR1Array[0].populationESS();

		if (!NumberUtil.IsValid (operatorPopulationSupremumNorm)) {
			throw new Exception (
				"NormedRxToNormedR1Finite::operatorPopulationSupremumNorm => Cannot compute Population Supremum Norm for Function #"
					+ 0
			);
		}

		for (int i = 1; i < functionCount; ++i) {
			double populationSupremumNorm = _normedRxToNormedR1Array[i].populationESS();

			if (!NumberUtil.IsValid (populationSupremumNorm)) {
				throw new Exception (
					"NormedRxToNormedR1Finite::operatorPopulationSupremumNorm => Cannot compute Population Supremum Norm for Function #"
						+ i
				);
			}

			if (operatorPopulationSupremumNorm > populationSupremumNorm) {
				operatorPopulationSupremumNorm = populationSupremumNorm;
			}
		}

		return operatorPopulationSupremumNorm;
	}

	@Override public double operatorSampleMetricNorm (
		final GeneralizedValidatedVector generalizedValidatedVector)
		throws Exception
	{
		if (null == _normedRxToNormedR1Array) {
			throw new Exception (
				"NormedRxToNormedR1Finite::operatorSampleMetricNorm => Finite Function Set Unspecified"
			);
		}

		int functionCount = _normedRxToNormedR1Array.length;

		double operatorSampleMetricNorm =
			_normedRxToNormedR1Array[0].sampleMetricNorm (generalizedValidatedVector);

		if (!NumberUtil.IsValid (operatorSampleMetricNorm)) {
			throw new Exception (
				"NormedRxToNormedR1Finite::operatorSampleMetricNorm => Cannot compute Sample Metric Norm for Function #"
					+ 0
			);
		}

		for (int i = 1; i < functionCount; ++i) {
			double sampleMetricNorm =
				_normedRxToNormedR1Array[i].sampleMetricNorm (generalizedValidatedVector);

			if (!NumberUtil.IsValid (sampleMetricNorm)) {
				throw new Exception (
					"NormedRxToNormedR1Finite::operatorSampleMetricNorm => Cannot compute Sample Metric Norm for Function #"
						+ i
				);
			}

			if (operatorSampleMetricNorm > sampleMetricNorm) {
				operatorSampleMetricNorm = sampleMetricNorm;
			}
		}

		return operatorSampleMetricNorm;
	}

	@Override public double operatorSampleSupremumNorm (
		final GeneralizedValidatedVector generalizedValidatedVector)
		throws Exception
	{
		if (null == _normedRxToNormedR1Array) {
			throw new Exception (
				"NormedRxToNormedR1Finite::operatorSampleSupremumNorm => Finite Function Set Unspecified"
			);
		}

		int functionCount = _normedRxToNormedR1Array.length;

		double operatorSampleSupremumNorm =
			_normedRxToNormedR1Array[0].sampleSupremumNorm (generalizedValidatedVector);

		if (!NumberUtil.IsValid (operatorSampleSupremumNorm)) {
			throw new Exception (
				"NormedRxToNormedR1Finite::operatorSampleSupremumNorm => Cannot compute Sample Supremum Norm for Function #"
					+ 0
			);
		}

		for (int i = 1; i < functionCount; ++i) {
			double sampleSupremumNorm =
				_normedRxToNormedR1Array[i].sampleSupremumNorm (generalizedValidatedVector);

			if (!NumberUtil.IsValid (sampleSupremumNorm)) {
				throw new Exception (
					"NormedRxToNormedR1Finite::operatorSampleSupremumNorm => Cannot compute Sample Supremum Norm for Function #"
						+ i
				);
			}

			if (operatorSampleSupremumNorm > sampleSupremumNorm) {
				operatorSampleSupremumNorm = sampleSupremumNorm;
			}
		}

		return operatorSampleSupremumNorm;
	}
}
