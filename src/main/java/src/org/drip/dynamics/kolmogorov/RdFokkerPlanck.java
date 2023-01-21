
package org.drip.dynamics.kolmogorov;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>RdFokkerPlanck</i> exposes the R<sup>d</sup> Fokker-Planck Probability Density Function Evolution
 * 	Equation. The References are:
 *  
 * 	<br><br>
 *  <ul>
 * 		<li>
 * 			Bogoliubov, N. N., and D. P. Sankevich (1994): N. N. Bogoliubov and Statistical Mechanics
 * 				<i>Russian Mathematical Surveys</i> <b>49 (5)</b> 19-49
 * 		</li>
 * 		<li>
 * 			Holubec, V., K. Kroy, and S. Steffenoni (2019): Physically Consistent Numerical Solver for
 * 				Time-dependent Fokker-Planck Equations <i>Physical Review E</i> <b>99 (4)</b> 032117
 * 		</li>
 * 		<li>
 * 			Kadanoff, L. P. (2000): <i>Statistical Physics: Statics, Dynamics, and Re-normalization</i>
 * 				<b>World Scientific</b>
 * 		</li>
 * 		<li>
 * 			Ottinger, H. C. (1996): <i>Stochastic Processes in Polymeric Fluids</i> <b>Springer-Verlag</b>
 * 				Berlin-Heidelberg
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Fokker-Planck Equation
 * 				https://en.wikipedia.org/wiki/Fokker%E2%80%93Planck_equation
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">HJM, Hull White, LMM, and SABR Dynamic Evolution Models</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/kolmogorov/README.md">Fokker Planck Kolmogorov Forward/Backward</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RdFokkerPlanck
{
	private org.drip.dynamics.ito.DiffusionTensor _diffusionTensor = null;
	private org.drip.dynamics.ito.RdToR1Drift[] _driftFunctionArray = null;
	private org.drip.dynamics.kolmogorov.RiskenOmegaEstimator _riskenOmegaEstimator = null;

	/**
	 * RdFokkerPlanck Constructor
	 * 
	 * @param driftFunctionArray Drift Function Array
	 * @param diffusionTensor Diffusion Tensor
	 * @param riskenOmegaEstimator Risken Omega Estimator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RdFokkerPlanck (
		final org.drip.dynamics.ito.RdToR1Drift[] driftFunctionArray,
		final org.drip.dynamics.ito.DiffusionTensor diffusionTensor,
		final org.drip.dynamics.kolmogorov.RiskenOmegaEstimator riskenOmegaEstimator)
		throws java.lang.Exception
	{
		if (null == (_driftFunctionArray = driftFunctionArray) ||
			null == (_diffusionTensor = diffusionTensor) ||
			null == (_riskenOmegaEstimator = riskenOmegaEstimator)
		)
		{
			throw new java.lang.Exception (
				"RdFokkerPlanck Constructor => Invalid Inputs"
			);
		}

		int dimension = _driftFunctionArray.length;

		if (dimension != _diffusionTensor.dimension())
		{
			throw new java.lang.Exception (
				"RdFokkerPlanck Constructor => Invalid Inputs"
			);
		}

		for (int dimensionIndex = 0;
			dimensionIndex < dimension;
			++dimensionIndex)
		{
			if (null == _driftFunctionArray[dimensionIndex])
			{
				throw new java.lang.Exception (
					"RdFokkerPlanck Constructor => Invalid Inputs"
				);
			}
		}
	}

	/**
	 * Retrieve the Drift Function Array
	 * 
	 * @return The Drift Function Array
	 */

	public org.drip.dynamics.ito.RdToR1Drift[] driftFunctionArray()
	{
		return _driftFunctionArray;
	}

	/**
	 * Retrieve the Diffusion Tensor
	 * 
	 * @return The Diffusion Tensor
	 */

	public org.drip.dynamics.ito.DiffusionTensor diffusionTensor()
	{
		return _diffusionTensor;
	}

	/**
	 * Retrieve the Risken Omega Estimator
	 * 
	 * @return The Risken Omega Estimator
	 */

	public org.drip.dynamics.kolmogorov.RiskenOmegaEstimator riskenOmegaEstimator()
	{
		return _riskenOmegaEstimator;
	}

	/**
	 * Compute the Next Incremental Time Derivative of the PDF
	 * 
	 * @param probabilityDensityFunction The PDF
	 * @param timeRdVertex The R<sup>d</sup> Time Vertex
	 * 
	 * @return Next Incremental Time Derivative of the PDF
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double pdfDot (
		final org.drip.dynamics.process.RdProbabilityDensityFunction probabilityDensityFunction,
		final org.drip.dynamics.ito.TimeRdVertex timeRdVertex)
		throws java.lang.Exception
	{
		if (null == probabilityDensityFunction ||
			null == timeRdVertex)
		{
			throw new java.lang.Exception (
				"RdFokkerPlanck::pdfDot => Invalid Inputs"
			);
		}

		final int dimension = _diffusionTensor.dimension();

		final double time = timeRdVertex.t();

		double pdfDot = 0.;

		for (int dimensionIndex = 0;
			dimensionIndex < dimension;
			++dimensionIndex)
		{
			final int index = dimensionIndex;

			pdfDot = pdfDot - new org.drip.function.definition.RdToR1 (
				null
			)
			{
				@Override public int dimension()
				{
					return dimension;
				}

				@Override public double evaluate (
					final double[] xArray)
					throws java.lang.Exception
				{
					org.drip.dynamics.ito.TimeRdVertex localTimeRdVertex =
						new org.drip.dynamics.ito.TimeRdVertex (
							time,
							xArray
						);

					return _driftFunctionArray[index].drift (
						localTimeRdVertex
					) * probabilityDensityFunction.density (
						localTimeRdVertex
					);
				}
			}.derivative (
				timeRdVertex.xArray(),
				dimensionIndex,
				1
			);
		}

		for (int dimensionIndexI = 0;
			dimensionIndexI < dimension;
			++dimensionIndexI)
		{
			final int indexI = dimensionIndexI;

			for (int dimensionIndexJ = 0;
				dimensionIndexJ < dimension;
				++dimensionIndexJ)
			{
				final int indexJ = dimensionIndexJ;

				pdfDot = pdfDot + new org.drip.function.definition.RdToR1 (
					null
				)
				{
					@Override public int dimension()
					{
						return dimension;
					}

					@Override public double evaluate (
						final double[] xArray)
						throws java.lang.Exception
					{
						org.drip.dynamics.ito.TimeRdVertex localTimeRdVertex =
							new org.drip.dynamics.ito.TimeRdVertex (
								time,
								xArray
							);

						return _diffusionTensor.diffusionCoefficient (
							localTimeRdVertex,
							indexI,
							indexJ
						) * probabilityDensityFunction.density (
							localTimeRdVertex
						);
					}
				}.hessian (
					timeRdVertex.xArray()
				)[indexI][indexJ];
			};
		}

		return pdfDot;
	}

	/**
	 * Compute the Temporal Probability Distribution Function, if any
	 * 
	 * @param intialProbabilityDensityFunction The Initial Probability Density Function
	 * 
	 * @return The Temporal Probability Distribution Function
	 */

	public org.drip.dynamics.process.RdProbabilityDensityFunction temporalPDF (
		final org.drip.function.definition.RdToR1 intialProbabilityDensityFunction)
	{
		return null;
	}

	/**
	 * Compute the Steady-State Probability Distribution Function, if any
	 * 
	 * @return The Steady-State Probability Distribution Function
	 */

	public org.drip.function.definition.RdToR1 steadyStatePDF()
	{
		double[][] omega = _riskenOmegaEstimator.estimateOmega (
			_diffusionTensor,
			_driftFunctionArray
		);

		final double[][] omegaInverse =
			org.drip.numerical.linearalgebra.Matrix.InvertUsingGaussianElimination (
				omega
			);

		if (null == omegaInverse)
		{
			return null;
		}

		final int dimension = _diffusionTensor.dimension();

		double rdNormalizer = java.lang.Double.NaN;

		try
		{
			rdNormalizer = java.lang.Math.sqrt (
				java.lang.Math.pow (
					2. * java.lang.Math.PI,
					-1. * dimension
				) / new org.drip.function.matrix.Square (
					omega
				).determinant()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		final double rdNormalizerFinal = rdNormalizer;

		final org.drip.function.definition.R1ToR1 r1ToR1Exponential =
			new org.drip.function.definition.R1ToR1 (
				null
			)
		{
			@Override public double evaluate (
				final double x)
				throws java.lang.Exception
			{
				return java.lang.Math.exp (
					-0.5 * x
				);
			}
		};

		return new org.drip.function.definition.RdToR1 (
			null
		)
		{
			@Override public int dimension()
			{
				return dimension;
			}

			@Override public double evaluate (
				final double[] xArray)
				throws java.lang.Exception
			{
				return rdNormalizerFinal * r1ToR1Exponential.evaluate (
					org.drip.numerical.linearalgebra.Matrix.DotProduct (
						xArray,
						org.drip.numerical.linearalgebra.Matrix.Product (
							omegaInverse,
							xArray
						)
					)
				);
			}
		};
	}
}
