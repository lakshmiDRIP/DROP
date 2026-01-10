
package org.drip.measure.crng;

import org.drip.numerical.linearalgebra.R1MatrixUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 * <i>QuadraticResampler</i> Quadratically Re-samples the Input Points to Convert it to a Standard Normal. It
 * 	provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>QuadraticResampler</i> Constructor</li>
 * 		<li>Indicate if the Sequence is to be Mean Centered</li>
 * 		<li>Indicate if the Sampling Bias needs to be Removed</li>
 * 		<li>Transform the Input R<sup>1</sup> Sequence by applying Quadratic Sampling</li>
 * 		<li>Transform the Array of Input R<sup>d</sup> Sequence by applying Quadratic Sampling</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/discontinuous/README.md">Antithetic, Quadratically Re-sampled, De-biased Distribution</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class QuadraticResampler
{
	private boolean _debias = false;
	private boolean _meanCenter = false;

	/**
	 * <i>QuadraticResampler</i> Constructor
	 * 
	 * @param meanCenter TRUE - The Sequence is to be Mean Centered
	 * @param debias TRUE - Remove the Sampling Bias
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public QuadraticResampler (
		final boolean meanCenter,
		final boolean debias)
		throws Exception
	{
		_debias = debias;
		_meanCenter = meanCenter;
	}

	/**
	 * Indicate if the Sequence is to be Mean Centered
	 * 
	 * @return TRUE - The Sequence is to be Mean Centered
	 */

	public boolean meanCenter()
	{
		return _meanCenter;
	}

	/**
	 * Indicate if the Sampling Bias needs to be Removed
	 * 
	 * @return TRUE - The Sampling Bias needs to be Removed
	 */

	public boolean debias()
	{
		return _debias;
	}

	/**
	 * Transform the Input R<sup>1</sup> Sequence by applying Quadratic Sampling
	 * 
	 * @param sequence The Input R<sup>1</sup> Sequence
	 * 
	 * @return The Transformed Sequence
	 */

	public double[] transform (
		final double[] sequence)
	{
		if (null == sequence) {
			return null;
		}

		double mean = 0.;
		double variance = 0.;
		double[] transfomedSequence = 0 == sequence.length ? null : new double[sequence.length];

		if (0 == sequence.length) {
			return null;
		}

		if (_meanCenter) {
			for (int sequenceIndex = 0; sequenceIndex < sequence.length; ++sequenceIndex) {
				mean += sequence[sequenceIndex];
			}

			mean = mean / sequence.length;
		}

		for (int sequenceIndex = 0; sequenceIndex < sequence.length; ++sequenceIndex) {
			double offset = sequence[sequenceIndex] - mean;
			variance += offset * offset;
		}

		double standardDeviation = Math.sqrt (variance / (_debias ? sequence.length - 1 : sequence.length));

		for (int sequenceIndex = 0; sequenceIndex < sequence.length; ++sequenceIndex) {
			transfomedSequence[sequenceIndex] = sequence[sequenceIndex] / standardDeviation;
		}

		return transfomedSequence;
	}

	/**
	 * Transform the Array of Input R<sup>d</sup> Sequence by applying Quadratic Sampling
	 * 
	 * @param sequenceArray The Input R<sup>d</sup> Sequence Array
	 * 
	 * @return The Transformed Sequence
	 */

	public double[][] transform (
		final double[][] sequenceArray)
	{
		double[][] flippedSequenceArray = R1MatrixUtil.Transpose (sequenceArray);

		if (null == flippedSequenceArray) {
			return null;
		}

		double[][] transformedFlippedSequenceArray = new double[flippedSequenceArray.length][];

		for (int flippedSequenceIndex = 0;
			flippedSequenceIndex < flippedSequenceArray.length;
			++flippedSequenceIndex)
		{
			transformedFlippedSequenceArray[flippedSequenceIndex] =
				transform (flippedSequenceArray[flippedSequenceIndex]);
		}
		
		return R1MatrixUtil.Transpose (transformedFlippedSequenceArray);
	}
}
