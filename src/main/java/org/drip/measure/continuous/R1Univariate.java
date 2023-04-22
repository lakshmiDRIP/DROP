
package org.drip.measure.continuous;

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
 * <i>R1Univariate</i> exposes the Base Abstract Class behind Univariate R<sup>1</sup> Distributions. It
 * 	exports the Methods for incremental, cumulative, and inverse cumulative distribution densities.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/README.md">R<sup>1</sup> and R<sup>d</sup> Continuous Random Measure</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class R1Univariate {

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
		if (java.lang.Double.isNaN (x))
		{
			return false;
		}

		double[] range = support();

		return range[0] <= x && x <= range[1];
	}

	/**
	 * Compute the Density under the Distribution at the given Variate
	 * 
	 * @param dblX Variate at which the Density needs to be computed
	 * 
	 * @return The Density
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public abstract double density (
		final double dblX)
		throws java.lang.Exception;

	/**
	 * Compute the cumulative under the distribution to the given value
	 * 
	 * @param dblX Variate to which the cumulative is to be computed
	 * 
	 * @return The cumulative
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public abstract double cumulative (
		final double dblX)
		throws java.lang.Exception;

	/**
	 * Compute the Incremental under the Distribution between the 2 variates
	 * 
	 * @param dblXLeft Left Variate to which the cumulative is to be computed
	 * @param dblXRight Right Variate to which the cumulative is to be computed
	 * 
	 * @return The Incremental under the Distribution between the 2 variates
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public double incremental (
		final double dblXLeft,
		final double dblXRight)
		throws java.lang.Exception
	{
		return cumulative (dblXRight) - cumulative (dblXLeft);
	}

	/**
	 * Compute the inverse cumulative under the distribution corresponding to the given value
	 * 
	 * @param p Value corresponding to which the inverse cumulative is to be computed
	 * 
	 * @return The inverse cumulative
	 * 
	 * @throws java.lang.Exception Thrown if the Input is invalid
	 */

	public double invCumulative (
		final double p)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (p) || 0. > p || 1. < p)
		{
			throw new java.lang.Exception ("R1Univariate::invCumulative => Invalid Inputs");
		}

		org.drip.function.r1tor1solver.FixedPointFinderOutput fixedPointFinderOutput =
			new org.drip.function.r1tor1solver.FixedPointFinderBrent (
				0.,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double u)
						throws java.lang.Exception
					{
						return cumulative (u) - p;
					}
				},
				true
			).findRoot();

		if (null == fixedPointFinderOutput)
		{
			throw new java.lang.Exception ("R1Univariate::invCumulative => Cannot find Root");
		}

		return fixedPointFinderOutput.getRoot();
	}

	/**
	 * Retrieve the Mean of the Distribution
	 * 
	 * @return The Mean of the Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Mean cannot be estimated
	 */

	public abstract double mean()
		throws java.lang.Exception;

	/**
	 * Retrieve the Median of the Distribution
	 * 
	 * @return The Median of the Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Median cannot be estimated
	 */

	public double median()
		throws java.lang.Exception
	{
		return invCumulative (0.50);
	}

	/**
	 * Retrieve the Quantile Variate of the Distribution
	 * 
	 * @param p The Quantile Fraction
	 * 
	 * @return The Quantile Variate of the Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Quantile Variate cannot be estimated
	 */

	public double quantile (
		final double p)
		throws java.lang.Exception
	{
		return invCumulative (p);
	}

	/**
	 * Retrieve the Mode of the Distribution
	 * 
	 * @return The Mode of the Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Mode cannot be estimated
	 */

	public double mode()
		throws java.lang.Exception
	{
		final org.drip.function.definition.R1ToR1 densityFunction =
			new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double u)
				throws java.lang.Exception
			{
				return density (u);
			}
		};

		org.drip.function.r1tor1solver.FixedPointFinderOutput fixedPointFinderOutput =
			new org.drip.function.r1tor1solver.FixedPointFinderBrent (
				0.,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double u)
						throws java.lang.Exception
					{
						return densityFunction.derivative (
							u,
							1
						);
					}
				},
				true
			).findRoot();

		if (null == fixedPointFinderOutput)
		{
			throw new java.lang.Exception ("R1Univariate::invCumulative => Cannot find Root");
		}

		return fixedPointFinderOutput.getRoot();
	}

	/**
	 * Retrieve the Variance of the Distribution
	 * 
	 * @return The Variance of the Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Variance cannot be estimated
	 */

	public abstract double variance()
		throws java.lang.Exception;

	/**
	 * Retrieve the Skewness of the Distribution
	 * 
	 * @return The Skewness of the Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Skewness cannot be estimated
	 */

	public double skewness()
		throws java.lang.Exception
	{
		throw new java.lang.Exception ("R1Univariate::skewness => Not implemented");
	}

	/**
	 * Retrieve the Excess Kurtosis of the Distribution
	 * 
	 * @return The Excess Kurtosis of the Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Skewness cannot be estimated
	 */

	public double excessKurtosis()
		throws java.lang.Exception
	{
		throw new java.lang.Exception ("R1Univariate::excessKurtosis => Not implemented");
	}

	/**
	 * Retrieve the Differential Entropy of the Distribution
	 * 
	 * @return The Differential Entropy of the Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Entropy cannot be estimated
	 */

	public double differentialEntropy()
		throws java.lang.Exception
	{
		return org.drip.numerical.integration.NewtonCotesQuadratureGenerator.GaussLaguerreLeftDefinite (
			0.,
			10000
		).integrate (
			new org.drip.function.definition.R1ToR1 (null)
			{
				@Override public double evaluate (
					final double t)
					throws java.lang.Exception
				{
					double density = density (t);

					return density * java.lang.Math.log (density);
				}
			}
		);
	}

	/**
	 * Construct the Moment Generating Function
	 * 
	 * @return The Moment Generating Function
	 */

	public org.drip.function.definition.R1ToR1 momentGeneratingFunction()
	{
		return null;
	}

	/**
	 * Construct the Probability Generating Function
	 * 
	 * @return The Probability Generating Function
	 */

	public org.drip.function.definition.R1ToR1 probabilityGeneratingFunction()
	{
		return null;
	}

	/**
	 * Retrieve the Fisher Information of the Distribution
	 * 
	 * @return The Fisher Information of the Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Fisher Information cannot be estimated
	 */

	public double fisherInformation()
		throws java.lang.Exception
	{
		throw new java.lang.Exception ("R1Univariate::fisherInformation => Not implemented");
	}

	/**
	 * Compute the Kullback-Leibler Divergence against the other R<sup>1</sup> Distribution
	 * 
	 * @param r1UnivariateOther Other R<sup>1</sup> Distribution
	 * 
	 * @return Kullback-Leibler Divergence against the other R<sup>1</sup> Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Kullback-Leibler Divergence cannot be estimated
	 */

	public double kullbackLeiblerDivergence (
		final R1Univariate r1UnivariateOther)
		throws java.lang.Exception
	{
		throw new java.lang.Exception ("R1Univariate::kullbackLeiblerDivergence => Not implemented");
	}

	/**
	 * Retrieve the Quantile CVaR (Conditional Value-at-Risk) of the Distribution
	 * 
	 * @param p The Quantile
	 * 
	 * @return The Quantile CVaR of the Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Quantile CVaR cannot be estimated
	 */

	public double cvar (
		final double p)
		throws java.lang.Exception
	{
		throw new java.lang.Exception ("R1Univariate::cvar => Not implemented");
	}

	/**
	 * Retrieve the Quantile ES (Expected Shortfall) of the Distribution
	 * 
	 * @param p The Quantile
	 * 
	 * @return The Quantile ES  of the Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Quantile ES cannot be estimated
	 */

	public double expectedShortfall (
		final double p)
		throws java.lang.Exception
	{
		return cvar (p);
	}

	/**
	 * Generate a Random Variable corresponding to the Distribution
	 * 
	 * @return Random Variable corresponding to the Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Random Instance cannot be estimated
	 */

	public double random()
		throws java.lang.Exception
	{
		return invCumulative (java.lang.Math.random());
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
		if (0 >= arrayCount)
		{
			return null;
		}

		double[] randomArray = new double[arrayCount];

		for (int index = 0; index < arrayCount; ++index)
		{
			try
			{
				randomArray[index] = random();
			}
			catch (java.lang.Exception e)
			{
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

	public org.drip.measure.statistics.PopulationCentralMeasures populationCentralMeasures()
	{
		try
		{
			return new org.drip.measure.statistics.PopulationCentralMeasures (
				mean(),
				variance()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Univariate Weighted Histogram
	 * 
	 * @return The Univariate Weighted Histogram
	 */

	public org.drip.numerical.common.Array2D histogram()
	{
		return null;
	}
}
