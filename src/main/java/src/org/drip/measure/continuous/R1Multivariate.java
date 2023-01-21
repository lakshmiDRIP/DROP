
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
 * <i>R1Multivariate</i> contains the Generalized R<sup>1</sup> Multivariate Distributions.
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

public abstract class R1Multivariate {
	private org.drip.measure.continuous.MultivariateMeta _meta = null;

	protected R1Multivariate (
		final org.drip.measure.continuous.MultivariateMeta meta)
		throws java.lang.Exception
	{
		if (null == (_meta = meta))
			throw new java.lang.Exception ("R1Multivariate Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Multivariate Meta Instance
	 * 
	 * @return The Multivariate Meta Instance
	 */

	public org.drip.measure.continuous.MultivariateMeta meta()
	{
		return _meta;
	}

	/**
	 * Retrieve the Left Edge Bounding Multivariate
	 * 
	 * @return The Left Edge Bounding Multivariate
	 */

	public double[] leftEdge()
	{
		int iNumVariate = _meta.numVariable();

		double[] adblLeftEdge = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i)
			adblLeftEdge[i] = java.lang.Double.MIN_NORMAL;

		return adblLeftEdge;
	}

	/**
	 * Retrieve the Right Edge Bounding Multivariate
	 * 
	 * @return The Right Edge Bounding Multivariate
	 */

	public double[] rightEdge()
	{
		int iNumVariate = _meta.numVariable();

		double[] adblRightEdge = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i)
			adblRightEdge[i] = java.lang.Double.MAX_VALUE;

		return adblRightEdge;
	}

	/**
	 * Compute the Density under the Distribution at the given Multivariate
	 * 
	 * @param adblVariate The Multivariate at which the Density needs to be computed
	 * 
	 * @return The Density
	 * 
	 * @throws java.lang.Exception Thrown if the Density cannot be computed
	 */

	public abstract double density (
		final double[] adblVariate)
		throws java.lang.Exception;

	/**
	 * Convert the Multivariate Density into an RdToR1 Functions Instance
	 * 
	 * @return The Multivariate Density converted into an RdToR1 Functions Instance
	 */

	public org.drip.function.definition.RdToR1 densityRdToR1()
	{
		return new org.drip.function.definition.RdToR1 (null) {
			@Override public int dimension()
			{
				return _meta.numVariable();
			}

			@Override public double evaluate (
				final double[] adblVariate)
				throws java.lang.Exception
			{
				return density (adblVariate);
			}
		};
	}

	/**
	 * Compute the Cumulative under the Distribution to the given Variate Values
	 * 
	 * @param adblVariate Array of Variate Values to which the Cumulative is to be computed
	 * 
	 * @return The Cumulative
	 * 
	 * @throws java.lang.Exception Thrown if the Cumulative cannot be computed
	 */

	public double cumulative (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		return densityRdToR1().integrate (leftEdge(), adblVariate);
	}

	/**
	 * Compute the Incremental under the Distribution between the 2 Multivariate Instances
	 * 
	 * @param adblVariateLeft Left Multivariate Instance to which the Cumulative is to be computed
	 * @param adblVariateRight Right Multivariate Instance to which the Cumulative is to be computed
	 * 
	 * @return The Incremental
	 * 
	 * @throws java.lang.Exception Thrown if the Incremental cannot be computed
	 */

	public double incremental (
		final double[] adblVariateLeft,
		final double[] adblVariateRight)
		throws java.lang.Exception
	{
		return densityRdToR1().integrate (adblVariateLeft, adblVariateRight);
	}

	/**
	 * Compute the Expectation of the Specified R^d To R^1 Function Instance
	 * 
	 * @param funcRdToR1 The R^d To R^1 Function Instance
	 * 
	 * @return The Expectation of the Specified R^d To R^1 Function Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double expectation (
		final org.drip.function.definition.RdToR1 funcRdToR1)
		throws java.lang.Exception
	{
		if (null == funcRdToR1)
			throw new java.lang.Exception ("R1Multivariate::expectation => Invalid Inputs");

		return new org.drip.function.definition.RdToR1 (null) {
			@Override public int dimension()
			{
				return _meta.numVariable();
			}

			@Override public double evaluate (
				final double[] adblVariate)
				throws java.lang.Exception
			{
				return density (adblVariate) * funcRdToR1.evaluate (adblVariate);
			}
		}.integrate (leftEdge(), rightEdge());
	}

	/**
	 * Compute the Mean of the Distribution
	 * 
	 * @return The Mean of the Distribution
	 */

	public double[] mean()
	{
		int iNumVariate = _meta.numVariable();

		double[] adblMean = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i) {
			final int iVariate = i;

			try {
				adblMean[i] = expectation (new org.drip.function.definition.RdToR1 (null) {
					@Override public int dimension()
					{
						return _meta.numVariable();
					}

					@Override public double evaluate (
						final double[] adblVariate)
						throws java.lang.Exception
					{
						return density (adblVariate) * adblVariate[iVariate];
					}
				});
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adblMean;
	}

	/**
	 * Compute the Variance of the Distribution
	 * 
	 * @return The Variance of the Distribution
	 */

	public double[] variance()
	{
		final double[] adblMean = mean();

		if (null == adblMean) return null;

		final int iNumVariate = adblMean.length;
		double[] adblVariance = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i) {
			final int iVariate = i;

			try {
				adblVariance[i] = expectation (new org.drip.function.definition.RdToR1 (null) {
					@Override public int dimension()
					{
						return _meta.numVariable();
					}

					@Override public double evaluate (
						final double[] adblVariate)
						throws java.lang.Exception
					{
						double dblSecondMoment = 0.;

						for (int i = 0; i < iNumVariate; ++i) {
							double dblOffset = adblVariate[iVariate] - adblMean[iVariate];
							dblSecondMoment = dblSecondMoment + dblOffset * dblOffset;
						}

						return density (adblVariate) * dblSecondMoment;
					}
				});
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adblVariance;
	}
}
