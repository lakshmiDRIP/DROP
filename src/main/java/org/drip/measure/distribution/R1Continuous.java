
package org.drip.measure.distribution;

import org.drip.function.definition.R1ToR1;
import org.drip.function.r1tor1solver.FixedPointFinderBrent;
import org.drip.function.r1tor1solver.FixedPointFinderOutput;
import org.drip.measure.statistics.PopulationCentralMeasures;
import org.drip.numerical.common.Array2D;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.r1integration.GaussKronrodQuadratureGenerator;
import org.drip.numerical.r1integration.Integrator;
import org.drip.numerical.r1integration.NewtonCotesQuadratureGenerator;
import org.drip.numerical.r1integration.QuadratureEstimator;

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
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>R1Continuous</i> exposes the Base Abstract Class behind continuous R<sup>1</sup> Distributions. It
 * 	exports the Methods for incremental, cumulative, and inverse cumulative distribution densities. It
 * 	provides the following Functionality:
 *
 *  <ul>
 * 		<li>Retrieve the <i>QuadratureEstimator</i> Instance for the Support</li>
 * 		<li>Retrieve the Quantile Variate of the Distribution</li>
 * 		<li>Retrieve the Mode of the Distribution</li>
 * 		<li>Retrieve the Variance of the Distribution</li>
 * 		<li>Retrieve the Skewness of the Distribution</li>
 * 		<li>Retrieve the Excess Kurtosis of the Distribution</li>
 * 		<li>Retrieve the Differential Entropy of the Distribution</li>
 * 		<li>Construct the Probability Generating Function</li>
 * 		<li>Retrieve the Fisher Information of the Distribution</li>
 * 		<li>Compute the Kullback-Leibler Divergence against the other R<sup>1</sup> Distribution</li>
 * 		<li>Retrieve the Quantile CVaR (Conditional Value-at-Risk) of the Distribution</li>
 * 		<li>Retrieve the Quantile ES (Expected Shortfall) of the Distribution</li>
 * 		<li>Retrieve the Buffered Probability of Existence</li>
 * 		<li>Retrieve the n<sup>th</sup> Non-central Moment</li>
 * 		<li>Retrieve the n<sup>th</sup> Central Moment</li>
 * 		<li>Retrieve the Inter-quantile Range (IQR) of the Distribution</li>
 * 		<li>Retrieve the Tukey Criterion of the Distribution</li>
 * 		<li>Retrieve the Tukey Anomaly of the Distribution</li>
 * 		<li>Generate a Random Variable corresponding to the Distribution</li>
 * 		<li>Retrieve the Array of Generated Random Variables</li>
 * 		<li>Retrieve the Population Central Measures</li>
 * 		<li>Retrieve the Univariate Weighted Histogram</li>
 * 		<li>Lay out the Support of the PDF Range</li>
 * 		<li>Indicate if x is inside the Supported Range</li>
 * 		<li>Compute the Density under the Distribution at the given Variate</li>
 * 		<li>Compute the cumulative under the distribution to the given value</li>
 * 		<li>Compute the Incremental under the Distribution between the 2 variates</li>
 * 		<li>Compute the inverse cumulative under the distribution corresponding to the given value</li>
 * 		<li>Retrieve the Mean of the Distribution</li>
 * 		<li>Retrieve the Median of the Distribution</li>
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

public abstract class R1Continuous
{
	private static final int INTERMEDIATE_POINT_COUNT = 50;

	/**
	 * Lay out the Support of the PDF Range
	 * 
	 * @return Support of the PDF Range
	 */

	public abstract double[] support();

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
		if (Double.isNaN (x)) {
			return false;
		}

		double[] range = support();

		return range[0] <= x && x <= range[1];
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

	public abstract double density (
		final double x)
		throws Exception;

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
		double leftSupport = support()[0];

		QuadratureEstimator quadratureEstimator = Double.NEGATIVE_INFINITY == leftSupport ?
			NewtonCotesQuadratureGenerator.GaussLaguerreRightDefinite (x, INTERMEDIATE_POINT_COUNT) :
			GaussKronrodQuadratureGenerator.K15 (leftSupport, x);

		return quadratureEstimator.integrate (
			new R1ToR1 (null) {
				@Override public double evaluate (
					final double u)
					throws Exception
				{
					return density (u);
				}
			}
		);
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
		return GaussKronrodQuadratureGenerator.K15 (xLeft, xRight).integrate (
			new R1ToR1 (null) {
				@Override public double evaluate (
					final double u)
					throws Exception
				{
					return density (u);
				}
			}
		);
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
		if (!NumberUtil.IsValid (p) || 0. > p || 1. < p) {
			throw new Exception ("R1Continuous::invCumulative => Invalid Inputs");
		}

		FixedPointFinderOutput fixedPointFinderOutput = new FixedPointFinderBrent (
			0.,
			new R1ToR1 (null) {
				@Override public double evaluate (
					final double u)
					throws Exception
				{
					return cumulative (u) - p;
				}
			},
			true
		).findRoot();

		if (null == fixedPointFinderOutput) {
			throw new Exception ("R1Continuous::invCumulative => Cannot find Root");
		}

		return fixedPointFinderOutput.getRoot();
	}

	/**
	 * Retrieve the <i>QuadratureEstimator</i> Instance for the Support
	 * 
	 * @return <i>QuadratureEstimator</i> Instance for the Support
	 */

	public QuadratureEstimator quadratureEstimator()
	{
		double[] leftRightSupportArray = support();

		double leftSupport = leftRightSupportArray[0];
		double rightSupport = leftRightSupportArray[1];

		if (Double.NEGATIVE_INFINITY == leftSupport && Double.POSITIVE_INFINITY == rightSupport) {
			return NewtonCotesQuadratureGenerator.GaussHermite (INTERMEDIATE_POINT_COUNT);
		}

		if (Double.NEGATIVE_INFINITY == leftSupport && Double.isFinite (rightSupport)) {
			return NewtonCotesQuadratureGenerator.GaussLaguerreRightDefinite (
				rightSupport,
				INTERMEDIATE_POINT_COUNT
			);
		}

		if (Double.isFinite (leftSupport) && Double.POSITIVE_INFINITY == rightSupport) {
			return NewtonCotesQuadratureGenerator.GaussLaguerreLeftDefinite (
				leftSupport,
				INTERMEDIATE_POINT_COUNT
			);
		}

		if (Double.NEGATIVE_INFINITY == leftSupport && Double.POSITIVE_INFINITY == rightSupport) {
			return NewtonCotesQuadratureGenerator.GaussHermite (INTERMEDIATE_POINT_COUNT);
		}

		return GaussKronrodQuadratureGenerator.K15 (leftSupport, rightSupport);
	}

	/**
	 * Retrieve the n<sup>th</sup> Non-central Moment
	 * 
	 * @param n Moment Number
	 * 
	 * @return The n<sup>th</sup> Non-central Moment
	 * 
	 * @throws Exception Thrown if the n<sup>th</sup> Non-central Moment cannot be estimated
	 */

	public double nonCentralMoment (
		final int n)
		throws Exception
	{
		return quadratureEstimator().integrate (
			new R1ToR1 (null) {
				@Override public double evaluate (
					final double t)
					throws Exception
				{
					return density (t) * Math.pow (t, n);
				}
			}
		);
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
		return nonCentralMoment (1);
	}

	/**
	 * Retrieve the n<sup>th</sup> Central Moment
	 * 
	 * @param n Moment Number
	 * 
	 * @return The n<sup>th</sup> Central Moment
	 * 
	 * @throws Exception Thrown if the n<sup>th</sup> Central Moment cannot be estimated
	 */

	public double centralMoment (
		final int n)
		throws Exception
	{
		final double mean = nonCentralMoment (1);

		return quadratureEstimator().integrate (
			new R1ToR1 (null) {
				@Override public double evaluate (
					final double t)
					throws Exception
				{
					return density (t) * Math.pow (t - mean, n);
				}
			}
		);
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
		return invCumulative (0.5);
	}

	/**
	 * Retrieve the Quantile Variate of the Distribution
	 * 
	 * @param p The Quantile Fraction
	 * 
	 * @return The Quantile Variate of the Distribution
	 * 
	 * @throws Exception Thrown if the Quantile Variate cannot be estimated
	 */

	public double quantile (
		final double p)
		throws Exception
	{
		return invCumulative (p);
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
		final R1ToR1 densityFunction = new R1ToR1 (null) {
			@Override public double evaluate (
				final double u)
				throws Exception
			{
				return density (u);
			}
		};

		FixedPointFinderOutput fixedPointFinderOutput = new FixedPointFinderBrent (
			0.,
			new R1ToR1 (null) {
				@Override public double evaluate (
					final double u)
					throws Exception
				{
					return densityFunction.derivative (u, 1);
				}
			},
			true
		).findRoot();

		if (null == fixedPointFinderOutput) {
			throw new Exception ("R1Continuous::invCumulative => Cannot find Root");
		}

		return fixedPointFinderOutput.getRoot();
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
		return centralMoment (2);
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
		throw new Exception ("R1Continuous::skewness => Not implemented");
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
		throw new Exception ("R1Continuous::excessKurtosis => Not implemented");
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
		return NewtonCotesQuadratureGenerator.GaussLaguerreLeftDefinite (0., 10000).integrate (
			new R1ToR1 (null) {
				@Override public double evaluate (
					final double t)
					throws Exception
				{
					double density = density (t);

					return density * Math.log (density);
				}
			}
		);
	}

	/**
	 * Construct the Probability Generating Function
	 * 
	 * @return The Probability Generating Function
	 */

	public R1ToR1 probabilityGeneratingFunction()
	{
		return null;
	}

	/**
	 * Retrieve the Fisher Information of the Distribution
	 * 
	 * @return The Fisher Information of the Distribution
	 * 
	 * @throws Exception Thrown if the Fisher Information cannot be estimated
	 */

	public double fisherInformation()
		throws Exception
	{
		throw new Exception ("R1Continuous::fisherInformation => Not implemented");
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
		if (null == r1UnivariateOther) {
			throw new Exception ("R1Continuous::kullbackLeiblerDivergence => Invalid Inputs");
		}

		R1ToR1 pdfDifferentialFunction = new R1ToR1 (null) {
			@Override public double evaluate (
				final double t)
				throws Exception
			{
				return Math.log (density (t) / r1UnivariateOther.density (t));
			}
		};

		double[] leftRight = support();

		if (Double.isFinite (leftRight[0]) && Double.isFinite (leftRight[1])) {
			return Integrator.Boole (pdfDifferentialFunction, leftRight[0], leftRight[1]);
		}

		if (Double.isFinite (leftRight[0])) {
			return NewtonCotesQuadratureGenerator.GaussLaguerreLeftDefinite (leftRight[0], 1000).integrate (
				pdfDifferentialFunction
			);
		}

		if (Double.isFinite (leftRight[1])) {
			return NewtonCotesQuadratureGenerator.GaussLaguerreRightDefinite (leftRight[1],1000).integrate (
				pdfDifferentialFunction
			);
		}

		return NewtonCotesQuadratureGenerator.GaussHermite (1000).integrate (pdfDifferentialFunction);
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
		return Integrator.Boole (
			new R1ToR1 (null) {
				@Override public double evaluate (
					final double t)
					throws Exception
				{
					return quantile (t);
				}
			},
			p,
			1.
		) / (1. - p);
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
		return cvar (p);
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
		FixedPointFinderOutput fixedPointFinderOutput = new FixedPointFinderBrent (
			0.,
			new R1ToR1 (null) {
				@Override public double evaluate (
					final double u)
					throws Exception
				{
					return cvar (u) - x;
				}
			},
			true
		).findRoot();

		if (null == fixedPointFinderOutput) {
			throw new Exception ("R1Continuous::bPOE => Cannot find Root");
		}

		return 1. - fixedPointFinderOutput.getRoot();
	}

	/**
	 * Retrieve the Inter-quantile Range (IQR) of the Distribution
	 * 
	 * @return The Inter-quantile Range of the Distribution
	 * 
	 * @throws Exception Thrown if the Inter-quantile Range cannot be estimated
	 */

	public double iqr()
		throws Exception
	{
		throw new Exception ("R1Continuous::iqr => Not implemented");
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
		return invCumulative (0.75) + 1.5 * iqr();
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
		return 1. - tukeyCriterion();
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
		return invCumulative (Math.random());
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
			try {
				randomArray[index] = random();
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return randomArray;
	}

	/**
	 * Retrieve the Population Central Measures
	 * 
	 * @return The Population Central Measures
	 */

	public PopulationCentralMeasures populationCentralMeasures()
	{
		try {
			return new PopulationCentralMeasures (mean(), variance());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Univariate Weighted Histogram
	 * 
	 * @return The Univariate Weighted Histogram
	 */

	public Array2D histogram()
	{
		return null;
	}
}
