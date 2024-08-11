
package org.drip.numerical.complex;

import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.matrix.R1SquareRotation2x2;

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
 * <i>C1CartesianPhiPsiThetaDelta</i> implements the type and Functionality associated with a C<sup>1</sup>
 *  Square Matrix parameterized by <code>phi</code>, <code>psi</code>, <code>theta</code>, and
 *  <code>delta</code> Fields. The References are:
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

public class C1CartesianPhiPsiThetaDelta extends C1Square
{
	private double _phi = Double.NaN;
	private double _psi = Double.NaN;
	private double _delta = Double.NaN;
	private double _theta = Double.NaN;
	private C1Square _jordanNormalLeft = null;
	private C1Square _jordanNormalRight = null;
	private R1SquareRotation2x2 _jordanNormalCenter = null;

	public static C1CartesianPhiPsiThetaDelta Standard (
		final double phi,
		final double psi,
		final double theta,
		final double delta)
	{
		if (!NumberUtil.IsValid (phi) || !NumberUtil.IsValid (psi) || !NumberUtil.IsValid (theta) ||
			!NumberUtil.IsValid (delta))
		{
			return null;
		}

		R1SquareRotation2x2 jordanNormalCenter = R1SquareRotation2x2.Standard (theta);

		C1Square jordanNormalRight = C1Square.Rotation2x2 (delta);

		C1Square jordanNormalLeft = C1Square.Rotation2x2 (psi);

		C1Cartesian[][] c1Grid = new C1Cartesian[2][2];

		return new C1CartesianPhiPsiThetaDelta (
			c1Grid,
			phi,
			psi,
			theta,
			delta,
			jordanNormalLeft,
			jordanNormalCenter,
			jordanNormalRight
		);
	}

	private C1CartesianPhiPsiThetaDelta (
		final C1Cartesian[][] c1Grid,
		final double phi,
		final double psi,
		final double theta,
		final double delta,
		final C1Square jordanNormalLeft,
		final R1SquareRotation2x2 jordanNormalCenter,
		final C1Square jordanNormalRight)
	{
		super (c1Grid);

		_phi = phi;
		_psi = psi;
		_delta = delta;
		_theta = theta;
		_jordanNormalLeft = jordanNormalLeft;
		_jordanNormalRight = jordanNormalRight;
		_jordanNormalCenter = jordanNormalCenter;
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
	 * Retrieve <code>Psi</code>
	 * 
	 * @return <code>Psi</code>
	 */

	public double psi()
	{
		return _psi;
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
	 * Retrieve <code>Delta</code>
	 * 
	 * @return <code>Delta</code>
	 */

	public double delta()
	{
		return _delta;
	}

	/**
	 * Retrieve the Jordan Normal Left Part of <i>C1CartesianPhiPsiThetaDelta</i>
	 * 
	 * @return Jordan Normal Left Part of <i>C1CartesianPhiPsiThetaDelta</i>
	 */

	public C1Square jordanNormalLeft()
	{
		return _jordanNormalLeft;
	}

	/**
	 * Retrieve the Jordan Normal Center Part of <i>C1CartesianPhiPsiThetaDelta</i>
	 * 
	 * @return Jordan Normal Center Part of <i>C1CartesianPhiPsiThetaDelta</i>
	 */

	public R1SquareRotation2x2 jordanNormalCenter()
	{
		return _jordanNormalCenter;
	}

	/**
	 * Retrieve the Jordan Normal Right Part of <i>C1CartesianPhiPsiThetaDelta</i>
	 * 
	 * @return Jordan Normal Right Part of <i>C1CartesianPhiPsiThetaDelta</i>
	 */

	public C1Square jordanNormalRight()
	{
		return _jordanNormalRight;
	}
}
