
package org.drip.numerical.iterativesolver;

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
 * <i>SuccessiveOverRelaxationConvergenceCheck</i> contains Results of the Convergence Analysis for SOR and
 *  the SSOR schemes. The References are:
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

public class SuccessiveOverRelaxationConvergenceCheck
{
	private boolean _jacobiSpectralRadiusVerification = false;
	private boolean _jacobiIterationMatrixRealEigenvalues = false;
	private boolean _relaxationParameterRangeVerification = false;

	/**
	 * Constructor an Instance of <i>SuccessiveOverRelaxationConvergenceCheck</i> from the Status
	 * 
	 * @param jacobiIterationMatrixRealEigenvalues TRUE - Jacobi Iteration Matrix has Real Eigenvalues
	 * @param jacobiSpectralRadiusVerification TRUE - Jacobi Spectral Radius satisfies Convergence Check
	 * @param relaxationParameterRangeVerification TRUE - Relaxation Parameter Range satisfies Convergence
	 *  Check
	 */

	public SuccessiveOverRelaxationConvergenceCheck (
		final boolean jacobiIterationMatrixRealEigenvalues,
		final boolean jacobiSpectralRadiusVerification,
		final boolean relaxationParameterRangeVerification)
	{
		_jacobiSpectralRadiusVerification = jacobiSpectralRadiusVerification;
		_jacobiIterationMatrixRealEigenvalues = jacobiIterationMatrixRealEigenvalues;
		_relaxationParameterRangeVerification = relaxationParameterRangeVerification;
	}

	/**
	 * Indicate if the Jacobi Iteration Matrix has Real Eigenvalues
	 * 
	 * @return TRUE - Jacobi Iteration Matrix has Real Eigenvalues
	 */

	public boolean jacobiIterationMatrixRealEigenvalues()
	{
		return _jacobiIterationMatrixRealEigenvalues;
	}

	/**
	 * Indicate if the Jacobi Spectral Radius satisfies Convergence Check
	 * 
	 * @return TRUE - Jacobi Spectral Radius satisfies Convergence Check
	 */

	public boolean jacobiSpectralRadiusVerification()
	{
		return _jacobiSpectralRadiusVerification;
	}

	/**
	 * Indicate if the Relaxation Parameter Range satisfies Convergence Check
	 * 
	 * @return TRUE - Relaxation Parameter Range satisfies Convergence Check
	 */

	public boolean relaxationParameterRangeVerification()
	{
		return _relaxationParameterRangeVerification;
	}

	/**
	 * Indicate if the Convergence Check Criteria are satisfied
	 * 
	 * @return TRUE - Convergence Check Criteria are satisfied
	 */

	public boolean satisfied()
	{
		return _jacobiIterationMatrixRealEigenvalues && _jacobiSpectralRadiusVerification &&
			_jacobiSpectralRadiusVerification;
	}
}
