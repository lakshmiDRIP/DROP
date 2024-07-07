
package org.drip.numerical.iterativesolver;

import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.linearalgebra.MatrixUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
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
 * <i>SuccessiveOverRelaxationConvergenceAnalyzer</i> implements the Convergence Analytics for SOR and the
 *  SSOR schemes. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Greenbaum, A. (1997): <i>Iterative Methods for Solving Linear Systems</i> <b>Society for
 * 				Industrial and Applied Mathematics</b> Philadelphia, PA
 * 		</li>
 * 		<li>
 * 			Hackbusch, W. (2016): <i>Iterative Solution of Large Sparse Systems of Equations</i>
 * 				<b>Spring-Verlag</b> Berlin, Germany
 * 		</li>
 * 		<li>
 * 			Wikipedia (2023): Symmetric Successive Over-Relaxation
 * 				https://en.wikipedia.org/wiki/Symmetric_successive_over-relaxation
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Successive Over-Relaxation
 * 				https://en.wikipedia.org/wiki/Successive_over-relaxation
 * 		</li>
 * 		<li>
 * 			Young, D. M. (1950): <i>Iterative methods for solving partial difference equations of elliptical
 * 				type</i> <b>Harvard University</b> Cambridge, MA
 * 		</li>
 * 	</ul>
 * 
 * It provides the following functionality:
 *
 *  <ul>
 * 		<li>Construct the R<sup>1</sup> To R<sup>1</sup> Bessel First Kind Frobenius Summation Series</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/iterativesolver/README.md">Linear System Iterative Solver Schemes</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SuccessiveOverRelaxationConvergenceAnalyzer
{
	private double[][] _squareMatrix = null;
	private double _relaxationParameter = Double.NaN;
	private double _jacobiIterationMatrixSpectralRadius = Double.NaN;

	/**
	 * Construct a Standard Instance of <i>SuccessiveOverRelaxationConvergenceAnalyzer</i>
	 * 
	 * @param squareMatrix Input Square Matrix
	 * @param jacobiIterationMatrixSpectralRadius Jacobi Iteration Matrix Spectral Radius
	 * 
	 * @return Standard Instance of <i>SuccessiveOverRelaxationConvergenceAnalyzer</i>
	 */

	public static final SuccessiveOverRelaxationConvergenceAnalyzer Standard (
		final double[][] squareMatrix,
		final double jacobiIterationMatrixSpectralRadius)
	{
		try {
			return new SuccessiveOverRelaxationConvergenceAnalyzer (
				squareMatrix,
				SuccessiveOverRelaxationIteratorSetting.RELAXATION_PARAMETER_DEFAULT,
				jacobiIterationMatrixSpectralRadius
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of <i>SuccessiveOverRelaxationConvergenceAnalyzer</i> from the Inputs
	 * 
	 * @param squareMatrix Input Square Matrix
	 * @param relaxationParameter Relaxation Parameter
	 * @param jacobiIterationMatrixSpectralRadius Jacobi Iteration Matrix Spectral Radius
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public SuccessiveOverRelaxationConvergenceAnalyzer (
		final double[][] squareMatrix,
		final double relaxationParameter,
		final double jacobiIterationMatrixSpectralRadius)
		throws Exception
	{
		if (!MatrixUtil.IsSquare (_squareMatrix = squareMatrix) ||
			!NumberUtil.IsValid (_relaxationParameter = relaxationParameter) ||
			!NumberUtil.IsValid (_jacobiIterationMatrixSpectralRadius = jacobiIterationMatrixSpectralRadius))
		{
			throw new Exception (
				"SuccessiveOverRelaxationConvergenceAnalyzer Construction => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Square Matrix
	 * 
	 * @return Square Matrix
	 */

	public double[][] squareMatrix()
	{
		return _squareMatrix;
	}

	/**
	 * Retrieve the Relaxation Parameter
	 * 
	 * @return Relaxation Parameter
	 */

	public double relaxationParameter()
	{
		return _relaxationParameter;
	}

	/**
	 * Retrieve the Jacobi Iteration Matrix Spectral Radius
	 * 
	 * @return Jacobi Iteration Matrix Spectral Radius
	 */

	public double jacobiIterationMatrixSpectralRadius()
	{
		return _jacobiIterationMatrixSpectralRadius;
	}

	/**
	 * Retrieve the Jacobi Iteration Matrix
	 * 
	 * @return Jacobi Iteration Matrix
	 */

	public double[][] jacobiIterationMatrix()
	{
		return MatrixUtil.JacobiIteration (_squareMatrix);
	}

	/**
	 * Indicate if the Jacobi Iteration Matrix has Real Eigenvalues
	 * 
	 * @return TRUE - Jacobi Iteration Matrix has Real Eigenvalues
	 */

	public boolean jacobiIterationMatrixRealEigenvalues()
	{
		return true;
	}

	/**
	 * Indicate if the Jacobi Spectral Radius satisfies Convergence Check
	 * 
	 * @return TRUE - Jacobi Spectral Radius satisfies Convergence Check
	 */

	public boolean jacobiSpectralRadiusVerification()
	{
		return 0. < _jacobiIterationMatrixSpectralRadius && 1. > _jacobiIterationMatrixSpectralRadius;
	}

	/**
	 * Indicate if the Relaxation Parameter Range satisfies Convergence Check
	 * 
	 * @return TRUE - Relaxation Parameter Range satisfies Convergence Check
	 */

	public boolean relaxationParameterRangeVerification()
	{
		return 0. < _relaxationParameter && 2. > _relaxationParameter;
	}

	/**
	 * Compute the Convergence Check Criteria Status
	 * 
	 * @return Convergence Check Criteria Status
	 */

	public SuccessiveOverRelaxationConvergenceCheck check()
	{
		return new SuccessiveOverRelaxationConvergenceCheck (
			jacobiIterationMatrixRealEigenvalues(),
			jacobiSpectralRadiusVerification(),
			relaxationParameterRangeVerification()
		);
	}

	/**
	 * Calculate the Optimal Relaxation Parameter from the Jacobi Iteration Matrix Spectral Radius
	 * 
	 * @return Optimal Relaxation Parameter
	 */

	public double optimalRelaxationParameter()
	{
		double spectralRadiusTransformer = _jacobiIterationMatrixSpectralRadius / (
			1. + Math.sqrt (1. - _jacobiIterationMatrixSpectralRadius * _jacobiIterationMatrixSpectralRadius)
		);

		return 1. + spectralRadiusTransformer * spectralRadiusTransformer;
	}

	/**
	 * Estimate the Gauss-Seidel Convergence Rate from the Relaxation Parameter and the Jacobi Iteration
	 * 	Matrix Spectral Radius
	 * 
	 * @return Gauss-Seidel Convergence Rate
	 */

	public double gaussSeidelRate()
	{
		return _jacobiIterationMatrixSpectralRadius * _jacobiIterationMatrixSpectralRadius;
	}

	/**
	 * Estimate the Convergence Rate from the Relaxation Parameter and the Jacobi Iteration Matrix Spectral
	 *  Radius
	 * 
	 * @return Convergence Rate
	 */

	public double rate()
	{
		double optimalRelaxationParameter = optimalRelaxationParameter();

		if (_relaxationParameter <= optimalRelaxationParameter) {
			double omegaMu = _relaxationParameter * _jacobiIterationMatrixSpectralRadius;

			double omegaMuManipulator = omegaMu + Math.sqrt (
				omegaMu * omegaMu - 4. * (_relaxationParameter - 1.)
			);

			return 0.25 * omegaMuManipulator * omegaMuManipulator;
		}

		return _relaxationParameter - 1.;
	}

	/**
	 * Compute the Convergence Rate corresponding to Optimal Relaxation Parameter
	 * 
	 * @return Convergence Rate corresponding to Optimal Relaxation Parameter
	 */

	public double optimalRate()
	{
		double sqrtOneMinusMuSquared =
			Math.sqrt (1. - _jacobiIterationMatrixSpectralRadius * _jacobiIterationMatrixSpectralRadius);

		return (1. - sqrtOneMinusMuSquared) / (1. + sqrtOneMinusMuSquared);
	}
}
