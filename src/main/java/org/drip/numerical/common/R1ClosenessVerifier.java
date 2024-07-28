
package org.drip.numerical.common;

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
 * <i>R1ClosenessVerifier</i> tells if a Pair of Valid R<sup>1</sup>'s match to within the Absolute/Relative
 * 	Tolerance. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Golub, G. H., and C. F. van Loan (1996): <i>Matrix Computations 3<sup>rd</sup> Edition</i>
 * 				<b>Johns Hopkins University Press</b> Baltimore MD
 * 		</li>
 * 		<li>
 * 			Horn, R. A., and C. R. Johnson (2013): <i>Matrix Analysis 2<sup>nd</sup> Edition</i> <b>Cambridge
 * 				University Press</b> Cambridge UK
 * 		</li>
 * 		<li>
 * 			Li, C. K., and F. Zhang (2019): Eigenvalue Continuity and Gershgorin’s Theorem <i>Electronic
 * 				Journal of Linear Algebra</i> <b>35</b> 619-625
 * 		</li>
 * 		<li>
 * 			Trefethen, L. N., and D. Bau III (1997): <i>Numerical Linear Algebra</i> <b>Society for
 * 				Industrial and Applied Mathematics</b> Philadelphia PA
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Gershgorin Circle Theorem
 * 				https://en.wikipedia.org/wiki/Gershgorin_circle_theorem
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/linearalgebra/README.md">Linear Algebra Matrix Transform Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1ClosenessVerifier
{

	/**
	 * Default Absolute Tolerance
	 */

	public static final double ABSOLUTE_TOLERANCE_DEFAULT = 1.0e-08;

	/**
	 * Default Relative Tolerance
	 */

	public static final double RELATIVE_TOLERANCE_DEFAULT = 1.0e-05;

	private double _absoluteTolerance = Double.NaN;
	private double _relativeTolerance = Double.NaN;

	/**
	 * Construct the Default <i>R1ClosenessVerifier</i>
	 * 
	 * @return Default <i>R1ClosenessVerifier</i>
	 */

	public static final R1ClosenessVerifier Standard()
	{
		try {
			return new R1ClosenessVerifier (RELATIVE_TOLERANCE_DEFAULT, ABSOLUTE_TOLERANCE_DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>R1ClosenessVerifier</i> Constructor
	 * 
	 * @param relativeTolerance Relative Tolerance
	 * @param absoluteTolerance Absolute Tolerance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public R1ClosenessVerifier (
		final double relativeTolerance,
		final double absoluteTolerance)
		throws Exception
	{
		if (!NumberUtil.IsValid (_relativeTolerance = relativeTolerance) || 0. >= _relativeTolerance ||
			!NumberUtil.IsValid (_absoluteTolerance = absoluteTolerance) || 0. >= _absoluteTolerance)
		{
			throw new Exception ("R1ClosenessVerifier Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Absolute Tolerance
	 * 
	 * @return Absolute Tolerance
	 */

	public double absoluteTolerance()
	{
		return _absoluteTolerance;
	}

	/**
	 * Retrieve the Relative Tolerance
	 * 
	 * @return Relative Tolerance
	 */

	public double relativeTolerance()
	{
		return _relativeTolerance;
	}

	/**
	 * Indicate if the Pair of Valid R<sup>1</sup>'s match to within the Absolute/Relative Tolerance
	 * 
	 * @param r1a First R<sup>1</sup>
	 * @param r1b Second R<sup>1</sup>
	 * 
	 * @return TRUE - The Pair of Valid R<sup>1</sup>'s match to within the Absolute/Relative Tolerance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public boolean match (
		final double r1a,
		final double r1b)
		throws Exception
	{
		if (!NumberUtil.IsValid (r1a) || !NumberUtil.IsValid (r1b)) {
			throw new Exception ("R1ClosenessVerifier::match => Invalid Inputs");
		}

		double r1Difference = Math.abs (r1a - r1b);

		double r1Mid = Math.abs (0.5 * (r1a + r1b));

		return (0. == r1Mid && r1Difference / r1Mid < _absoluteTolerance) ||
			r1Difference < _relativeTolerance;
	}
}
