
package org.drip.measure.pdf;

import org.drip.function.definition.R1ToR1;
import org.drip.measure.distribution.R1Continuous;

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
 * <i>R1DegenerateDistribution</i> implements the Degenerate Probability Distribution Function. The
 * 	References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Wikipedia (2025): Degenerate Distribution https://en.wikipedia.org/wiki/Degenerate_distribution
 * 		</li>
 * 	</ul>
 * 
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>R1DegenerateDistribution</i> Constructor</li>
 * 		<li>Retrieve the <i>a</i> Parameter</li>
 * 		<li>Lay out the Support of the PDF Range</li>
 * 		<li>Indicate if x is inside the Supported Range</li>
 * 		<li>Compute the Density under the Distribution at the given Variate</li>
 * 		<li>Compute the cumulative under the distribution to the given value</li>
 * 		<li>Compute the Incremental under the Distribution between the 2 variates</li>
 * 		<li>Compute the inverse cumulative under the distribution corresponding to the given value</li>
 * 		<li>Retrieve the Mean of the Distribution</li>
 * 		<li>Retrieve the Median of the Distribution</li>
 * 		<li>Retrieve the Mode of the Distribution</li>
 * 		<li>Retrieve the Variance of the Distribution</li>
 * 		<li>Retrieve the Skewness of the Distribution</li>
 * 		<li>Retrieve the Excess Kurtosis of the Distribution</li>
 * 		<li>Retrieve the Differential Entropy of the Distribution</li>
 * 		<li>Construct the Probability Generating Function</li>
 * 		<li>Compute the Kullback-Leibler Divergence against the other R<sup>1</sup> Distribution</li>
 * 		<li>Retrieve the Quantile CVaR (Conditional Value-at-Risk) of the Distribution</li>
 * 		<li>Retrieve the Quantile ES (Expected Shortfall) of the Distribution</li>
 * 		<li>Retrieve the Buffered Probability of Existence</li>
 * 		<li>Retrieve the Tukey Criterion of the Distribution</li>
 * 		<li>Retrieve the Tukey Anomaly of the Distribution</li>
 * 		<li>Generate a Random Variable corresponding to the Distribution</li>
 * 		<li>Retrieve the Array of Generated Random Variables</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/r1pdf/README.md">Explicit R<sup>1</sup> and R<sup>d</sup> PDF's</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1DegenerateDistribution
	extends R1Continuous
{
	private double _a = Double.NaN;

	/**
	 * R1DegenerateDistribution Constructor
	 * 
	 * @param a "a"
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public R1DegenerateDistribution (
		final double a)
		throws Exception
	{
		if (Double.isNaN (_a = a)) {
			throw new Exception ("R1DegenerateDistribution Constructor => Invalid a");
		}
	}

	/**
	 * Retrieve the <i>a</i> Parameter
	 * 
	 * @return <i>a</i> Parameter
	 */

	public double a()
	{
		return _a;
	}

	/**
	 * Lay out the Support of the PDF Range
	 * 
	 * @return Support of the PDF Range
	 */

	@Override public double[] support()
	{
		return new double[] {
			_a,
			_a
		};
	}

	/**
	 * Indicate if x is inside the Supported Range
	 * 
	 * @param x X
	 * 
	 * @return TRUE - x is inside of the Supported Range
	 */

	public boolean supported (
		final double x)
	{
		return !Double.isNaN (x) && _a == x;
	}

	/**
	 * Compute the Density under the Distribution at the given Variate
	 * 
	 * @param x Variate at which the Density needs to be computed
	 * 
	 * @return The Density
	 * 
	 * @throws Exception Thrown if the input is invalid
	 */

	public double density (
		final double x)
		throws Exception
	{
		if (Double.isNaN (x)) {
			throw new Exception ("R1DegenerateDistribution::density => Invalid x");
		}

		return _a == x ? 1. : 0.;
	}

	/**
	 * Compute the cumulative under the distribution to the given value
	 * 
	 * @param x Variate to which the cumulative is to be computed
	 * 
	 * @return The cumulative
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	public double cumulative (
		final double x)
		throws Exception
	{
		if (Double.isNaN (x)) {
			throw new Exception ("R1DegenerateDistribution::cumulative => Invalid x");
		}

		return _a <= x ? 1. : 0.;
	}

	/**
	 * Compute the Incremental under the Distribution between the 2 variates
	 * 
	 * @param xLeft Left Variate to which the cumulative is to be computed
	 * @param xRight Right Variate to which the cumulative is to be computed
	 * 
	 * @return The Incremental under the Distribution between the 2 variates
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	public double incremental (
		final double xLeft,
		final double xRight)
		throws Exception
	{
		return cumulative (xRight) - cumulative (xLeft);
	}

	/**
	 * Compute the inverse cumulative under the distribution corresponding to the given value
	 * 
	 * @param p Value corresponding to which the inverse cumulative is to be computed
	 * 
	 * @return The inverse cumulative
	 * 
	 * @throws Exception Thrown if the Input is invalid
	 */

	public double invCumulative (
		final double p)
		throws Exception
	{
		throw new Exception ("R1DegenerateDistribution::invCumulative => Not Uniquely Defined");
	}

	/**
	 * Retrieve the Mean of the Distribution
	 * 
	 * @return The Mean of the Distribution
	 * 
	 * @throws Exception Thrown if the Mean cannot be estimated
	 */

	public double mean()
		throws Exception
	{
		return _a;
	}

	/**
	 * Retrieve the Median of the Distribution
	 * 
	 * @return The Median of the Distribution
	 * 
	 * @throws Exception Thrown if the Median cannot be estimated
	 */

	public double median()
		throws Exception
	{
		return _a;
	}

	/**
	 * Retrieve the Mode of the Distribution
	 * 
	 * @return The Mode of the Distribution
	 * 
	 * @throws Exception Thrown if the Mode cannot be estimated
	 */

	public double mode()
		throws Exception
	{
		return _a;
	}

	/**
	 * Retrieve the Variance of the Distribution
	 * 
	 * @return The Variance of the Distribution
	 * 
	 * @throws Exception Thrown if the Variance cannot be estimated
	 */

	public double variance()
		throws Exception
	{
		return 0.;
	}

	/**
	 * Retrieve the Skewness of the Distribution
	 * 
	 * @return The Skewness of the Distribution
	 * 
	 * @throws Exception Thrown if the Skewness cannot be estimated
	 */

	public double skewness()
		throws Exception
	{
		throw new Exception ("R1DegenerateDistribution::skewness => Not defined");
	}

	/**
	 * Retrieve the Excess Kurtosis of the Distribution
	 * 
	 * @return The Excess Kurtosis of the Distribution
	 * 
	 * @throws Exception Thrown if the Skewness cannot be estimated
	 */

	public double excessKurtosis()
		throws Exception
	{
		throw new Exception ("R1DegenerateDistribution::excessKurtosis => Not defined");
	}

	/**
	 * Retrieve the Differential Entropy of the Distribution
	 * 
	 * @return The Differential Entropy of the Distribution
	 * 
	 * @throws Exception Thrown if the Entropy cannot be estimated
	 */

	public double differentialEntropy()
		throws Exception
	{
		return 0.;
	}

	/**
	 * Construct the Probability Generating Function
	 * 
	 * @return The Probability Generating Function
	 */

	public R1ToR1 probabilityGeneratingFunction()
	{
		return new R1ToR1 (null)
		{
			@Override public double evaluate (
				final double z)
				throws Exception
			{
				return Math.pow (z,  _a);
			}
		};
	}

	/**
	 * Compute the Kullback-Leibler Divergence against the other R<sup>1</sup> Distribution
	 * 
	 * @param r1UnivariateOther Other R<sup>1</sup> Distribution
	 * 
	 * @return Kullback-Leibler Divergence against the other R<sup>1</sup> Distribution
	 * 
	 * @throws Exception Thrown if the Kullback-Leibler Divergence cannot be estimated
	 */

	public double kullbackLeiblerDivergence (
		final R1Continuous r1UnivariateOther)
		throws Exception
	{
		throw new Exception ("R1DegenerateDistribution::fisherInformation => Not implemented");
	}

	/**
	 * Retrieve the Quantile CVaR (Conditional Value-at-Risk) of the Distribution
	 * 
	 * @param p The Quantile
	 * 
	 * @return The Quantile CVaR of the Distribution
	 * 
	 * @throws Exception Thrown if the Quantile CVaR cannot be estimated
	 */

	public double cvar (
		final double p)
		throws Exception
	{
		throw new Exception ("R1DegenerateDistribution::cvar => Not Uniquely Defined");
	}

	/**
	 * Retrieve the Quantile ES (Expected Shortfall) of the Distribution
	 * 
	 * @param p The Quantile
	 * 
	 * @return The Quantile ES  of the Distribution
	 * 
	 * @throws Exception Thrown if the Quantile ES cannot be estimated
	 */

	public double expectedShortfall (
		final double p)
		throws Exception
	{
		throw new Exception ("R1DegenerateDistribution::expectedShortfall => Not Uniquely Defined");
	}

	/**
	 * Retrieve the Buffered Probability of Existence
	 * 
	 * @param x The Variate
	 * 
	 * @return The Buffered Probability of Existence
	 * 
	 * @throws Exception Thrown if the Buffered Probability of Existence cannot be estimated
	 */

	public double bPOE (
		final double x)
		throws Exception
	{
		throw new Exception ("R1DegenerateDistribution::bPOE => Not Uniquely Defined");
	}

	/**
	 * Retrieve the Tukey Criterion of the Distribution
	 * 
	 * @return The Tukey Criterion of the Distribution
	 * 
	 * @throws Exception Thrown if the Tukey Criterion cannot be estimated
	 */

	public double tukeyCriterion()
		throws Exception
	{
		throw new Exception ("R1DegenerateDistribution::tukeyCriterion => Not Uniquely Defined");
	}

	/**
	 * Retrieve the Tukey Anomaly of the Distribution
	 * 
	 * @return The Tukey Anomaly of the Distribution
	 * 
	 * @throws Exception Thrown if the Tukey Anomaly cannot be estimated
	 */

	public double tukeyAnomaly()
		throws Exception
	{
		throw new Exception ("R1DegenerateDistribution::tukeyAnomaly => Not Uniquely Defined");
	}

	/**
	 * Generate a Random Variable corresponding to the Distribution
	 * 
	 * @return Random Variable corresponding to the Distribution
	 * 
	 * @throws Exception Thrown if the Random Instance cannot be estimated
	 */

	public double random()
		throws Exception
	{
		return _a;
	}

	/**
	 * Retrieve the Array of Generated Random Variables
	 * 
	 * @param arrayCount Number of Elements
	 * 
	 * @return Array of Generated Random Variables
	 */

	public double[] randomArray (
		final int arrayCount)
	{
		if (0 >= arrayCount) {
			return null;
		}

		double[] randomArray = new double[arrayCount];

		for (int index = 0; index < arrayCount; ++index) {
			randomArray[index] = _a;
		}

		return randomArray;
	}
}
