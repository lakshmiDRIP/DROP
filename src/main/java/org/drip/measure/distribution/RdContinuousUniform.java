
package org.drip.measure.distribution;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
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
 * <i>RdContinuousUniform</i> contains the R<sup>d</sup> Continuous Uniform Distribution inside a Fixed
 * 	Support. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>RdContinuousUniform Constructor</li>
 * 		<li>Lay out the Left Support of the PDF Range</li>
 * 		<li>Lay out the Right Support of the PDF Range</li>
 * 		<li>Compute the Density under the Distribution at the given Variate Array</li>
 * 		<li>Compute the Cumulative under the Distribution to the given Variate Array</li>
 * 		<li>Compute the Incremental under the Distribution to the given Variate Array</li>
 * 		<li>Generate a Random Variable corresponding to the Distribution</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/distribution/README.md">R<sup>1</sup> and R<sup>d</sup> Continuous Random Measure</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RdContinuousUniform
	extends RdContinuous
{
	private double[] _leftSupportArray = null;
	private double[] _rightSupportArray = null;

	/**
	 * RdContinuousUniform Constructor
	 * 
	 * @param leftSupportArray Left Support of the PDF Range
	 * @param rightSupportArray Right Support of the PDF Range
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RdContinuousUniform (
		final double[] leftSupportArray,
		final double[] rightSupportArray)
		throws Exception
	{
		if (null == leftSupportArray || null == rightSupportArray ||
			0 == leftSupportArray.length || leftSupportArray.length != rightSupportArray.length)
		{
			throw new Exception ("RdContinuousUniform Constructor => Invalid Inputs");
		}

		_leftSupportArray = new double[leftSupportArray.length];
		_rightSupportArray = new double[rightSupportArray.length];

		for (int variateIndex = 0; variateIndex < rightSupportArray.length; ++variateIndex) {
			_leftSupportArray[variateIndex] = leftSupportArray[variateIndex];
			_rightSupportArray[variateIndex] = rightSupportArray[variateIndex];
		}
	}

	/**
	 * Lay out the Left Support of the PDF Range
	 * 
	 * @return Left Support of the PDF Range
	 */

	@Override public double[] leftSupport()
	{
		return _leftSupportArray;
	}

	/**
	 * Lay out the Right Support of the PDF Range
	 * 
	 * @return Right Support of the PDF Range
	 */

	@Override public double[] rightSupport()
	{
		return _rightSupportArray;
	}

	/**
	 * Compute the Density under the Distribution at the given Variate Array
	 * 
	 * @param variateArray Variate Array at which the Density needs to be computed
	 * 
	 * @return The Density
	 * 
	 * @throws Exception Thrown if the input is invalid
	 */

	@Override public double density (
		final double[] variateArray)
		throws Exception
	{
		if (!supported (variateArray)) {
			throw new Exception ("RdContinuousUniform::density => Invalid Inputs");
		}

		double density = 1.;

		for (int dimensionIndex = 0; dimensionIndex < dimension(); ++dimensionIndex) {
			density /= (_rightSupportArray[dimensionIndex] - _leftSupportArray[dimensionIndex]);
		}

		return density;
	}

	/**
	 * Compute the Cumulative under the Distribution to the given Variate Array
	 * 
	 * @param variateArray Variate Array to which the Cumulative is to be computed
	 * 
	 * @return The Cumulative
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	public double cumulative (
		final double[] variateArray)
		throws Exception
	{
		if (!supported (variateArray)) {
			throw new Exception ("RdContinuousUniform::cumulative => Invalid Inputs");
		}

		double cumulative = 1.;

		for (int dimensionIndex = 0; dimensionIndex < dimension(); ++dimensionIndex) {
			cumulative /= (variateArray[dimensionIndex] - _leftSupportArray[dimensionIndex]);
		}

		return cumulative;
	}

	/**
	 * Compute the Incremental under the Distribution to the given Variate Array
	 * 
	 * @param variateArrayFrom Variate Array from which the Incremental is to be computed
	 * @param variateArrayTo Variate Array to which the Incremental is to be computed
	 * 
	 * @return The Cumulative
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	public double incremental (
		final double[] variateArrayFrom,
		final double[] variateArrayTo)
		throws Exception
	{
		if (!supported (variateArrayFrom) || !supported (variateArrayTo)) {
			throw new Exception ("RdContinuousUniform::incremental => Invalid Inputs");
		}

		double cumulative = 1.;

		for (int dimensionIndex = 0; dimensionIndex < dimension(); ++dimensionIndex) {
			cumulative /= Math.abs (variateArrayFrom[dimensionIndex] - variateArrayTo[dimensionIndex]);
		}

		return cumulative;
	}

	/**
	 * Generate a Random Variable corresponding to the Distribution
	 * 
	 * @return Random Variable corresponding to the Distribution
	 */

	public double[] random()
	{
		double[] randomVariateArray = new double[dimension()];

		for (int dimensionIndex = 0; dimensionIndex < randomVariateArray.length; ++dimensionIndex) {
			randomVariateArray[dimensionIndex] = _leftSupportArray[dimensionIndex] +
				Math.random() * (_rightSupportArray[dimensionIndex] - _leftSupportArray[dimensionIndex]);
		}

		return randomVariateArray;
	}
}
