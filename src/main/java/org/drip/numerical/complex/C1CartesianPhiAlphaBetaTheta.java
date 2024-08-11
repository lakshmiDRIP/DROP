
package org.drip.numerical.complex;

import org.drip.numerical.common.NumberUtil;

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
 * <i>C1CartesianPhiAlphaBetaTheta</i> implements the type and Functionality associated with a C<sup>1</sup>
 *  Square Matrix parameterized by <code>alpha</code>, <code>beta</code>, <code>theta</code>, and
 *  <code>phi</code> Fields. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Fuhr, H., and Z. Rzeszotnik (2018): A Note on Factoring Unitary Matrices <i>Linear Algebra and
 * 				its Applications</i> <b>547</b> 32-44
 * 		</li>
 * 		<li>
 * 			Horn, R. A., and C. R. Johnson (2013): <i>Matrix Analysis</i> <b>Cambridge University Press</b>
 * 				Cambridge UK
 * 		</li>
 * 		<li>
 * 			Li, C. K., and E. Poon (2002): Additive Decomposition of Real Matrices <i>Linear and Multilinear
 * 				Algebra</i> <b>50 (4)</b> 321-326
 * 		</li>
 * 		<li>
 * 			Marvian, I. (2022): Restrictions on realizable Unitary Operations imposed by Symmetry and
 * 				Locality <i>Nature Science</i> <b>18 (3)</b> 283-289
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Unitary Matrix https://en.wikipedia.org/wiki/Unitary_matrix
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/complex/README.md">Implementation of Complex Number Suite</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class C1CartesianPhiAlphaBetaTheta extends C1Square
{
	private double _phi = Double.NaN;
	private double _beta = Double.NaN;
	private double _alpha = Double.NaN;
	private double _theta = Double.NaN;

	/**
	 * Construct a Standard Instance of <i>C1CartesianPhiAlphaBetaTheta</i>
	 * 
	 * @param alpha "alpha"
	 * @param beta "beta"
	 * @param theta "theta"
	 * @param phi "Phi"
	 * 
	 * @return <i>C1CartesianPhiAlphaBetaTheta</i> Instance
	 */

	public static C1CartesianPhiAlphaBetaTheta Standard (
		final double alpha,
		final double beta,
		final double theta,
		final double phi)
	{
		if (!NumberUtil.IsValid (alpha) || !NumberUtil.IsValid (beta) || !NumberUtil.IsValid (theta) ||
			!NumberUtil.IsValid (phi))
		{
			return null;
		}

		C1Cartesian ePowerIPhi = C1Cartesian.UnitImaginary().scale (0.5 * phi).exponentiate();

		C1Cartesian ePowerIAlpha = C1Cartesian.UnitImaginary().scale (alpha).exponentiate();

		C1Cartesian ePowerIBeta = C1Cartesian.UnitImaginary().scale (beta).exponentiate();

		C1Cartesian[][] c1Grid = new C1Cartesian[2][2];

		double sinTheta = Math.sin (theta);

		double cosTheta = Math.cos (theta);

		c1Grid[1][1] = ePowerIPhi.divide (ePowerIAlpha).scale (cosTheta);

		c1Grid[0][1] = ePowerIPhi.product (ePowerIBeta).scale (sinTheta);

		c1Grid[0][0] = ePowerIPhi.product (ePowerIAlpha).scale (cosTheta);

		c1Grid[1][0] = ePowerIPhi.divide (ePowerIBeta).scale (-1. * sinTheta);

		return new C1CartesianPhiAlphaBetaTheta (c1Grid, alpha, beta, theta, phi);
	}

	private C1CartesianPhiAlphaBetaTheta (
		final C1Cartesian[][] c1Grid,
		final double alpha,
		final double beta,
		final double theta,
		final double phi)
	{
		super (c1Grid);

		_phi = phi;
		_beta = beta;
		_alpha = alpha;
		_theta = theta;
	}

	/**
	 * Retrieve <code>Alpha</code>
	 * 
	 * @return <code>Alpha</code>
	 */

	public double alpha()
	{
		return _alpha;
	}

	/**
	 * Retrieve <code>Beta</code>
	 * 
	 * @return <code>Beta</code>
	 */

	public double beta()
	{
		return _beta;
	}

	/**
	 * Retrieve <code>Theta</code>
	 * 
	 * @return <code>Theta</code>
	 */

	public double theta()
	{
		return _theta;
	}

	/**
	 * Retrieve <code>Phi</code>
	 * 
	 * @return <code>Phi</code>
	 */

	public double phi()
	{
		return _phi;
	}

	/**
	 * Retrieve the <code>a</code> Parameter
	 * 
	 * @return <code>a</code> Parameter
	 */

	public C1Cartesian a()
	{
		return C1Cartesian.UnitImaginary().scale (_alpha).exponentiate().scale (Math.cos (_theta));
	}

	/**
	 * Retrieve the <code>b</code> Parameter
	 * 
	 * @return <code>b</code> Parameter
	 */

	public C1Cartesian b()
	{
		return C1Cartesian.UnitImaginary().scale (_beta).exponentiate().scale (Math.sin (_theta));
	}

	/**
	 * Construct the Instance of <i>C1CartesianPhiAB</i>
	 * 
	 * @return Instance of <i>C1CartesianPhiAB</i>
	 */

	public C1CartesianPhiAB phiAB()
	{
		return C1CartesianPhiAB.Standard (a(), b(), _phi);
	}
}
