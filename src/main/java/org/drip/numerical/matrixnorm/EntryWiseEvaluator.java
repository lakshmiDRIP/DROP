
package org.drip.numerical.matrixnorm;

import org.drip.numerical.matrix.R1Square;

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
 * <i>EntryWiseEvaluator</i> computes the Entry-wise Norm of a R<sup>1</sup>Square Matrix. The References
 * 	are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Alon, N., and A. Naor (2004): Approximating the Cut-norm via Grothendieck Inequality
 * 				<i>Proceedings of the 36<sup>th</sup> Annual ACM Symposium on Theory of Computing STOC�04</i>
 * 				<b>ACM</b> Chicago IL
 * 		</li>
 * 		<li>
 * 			Golub, G. H., and C. F. van Loan (1996): <i>Matrix Computations 3<sup>rd</sup> Edition</i>
 * 				<b>Johns Hopkins University Press</b> Baltimore MD
 * 		</li>
 * 		<li>
 * 			Horn, R. A., and C. R. Johnson (2013): <i>Matrix Analysis 2<sup>nd</sup> Edition</i> <b>Cambridge
 * 				University Press</b> Cambridge UK
 * 		</li>
 * 		<li>
 * 			Lazslo, L. (2012): <i>Large Networks and Graph Limits</i> <b>American Mathematical Society</b>
 * 				Providence RI
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Matrix Norm https://en.wikipedia.org/wiki/Matrix_norm
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/matrixnorm/README.md">Implementation of Matrix Norm Variants</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class EntryWiseEvaluator extends R1SquareEvaluator
{
	private int _p = Integer.MIN_VALUE;
	private int _q = Integer.MIN_VALUE;

	/**
	 * Construct a L<sub>2, 1</sub> Instance of <i>EntryWiseEvaluator</i>
	 * 
	 * @return L<sub>2, 1</sub> Instance of <i>EntryWiseEvaluator</i>
	 */

	public static final EntryWiseEvaluator L2_1()
	{
		try {
			return new EntryWiseEvaluator (2, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a L<sub>p, p</sub> Instance of <i>EntryWiseEvaluator</i>
	 * 
	 * @param p p Norm
	 * 
	 * @return L<sub>p, p</sub> Instance of <i>EntryWiseEvaluator</i>
	 */

	public static final EntryWiseEvaluator Lp_p (
		final int p)
	{
		try {
			return new EntryWiseEvaluator (p, p);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>EntryWiseEvaluator</i> Constructor
	 * 
	 * @param p p Norm
	 * @param q q Norm
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public EntryWiseEvaluator (
		final int p,
		final int q)
		throws Exception
	{
		if (0 >= (_p = p) || 0 >= (_q = q)) {
			throw new Exception ("EntryWiseEvaluator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the p Norm
	 * 
	 * @return p Norm
	 */

	public int p()
	{
		return _p;
	}

	/**
	 * Retrieve the q Norm
	 * 
	 * @return q Norm
	 */

	public int q()
	{
		return _q;
	}

	/**
	 * Compute the Norm of the R<sup>1</sup>Square Matrix
	 * 
	 * @param r1Square R<sup>1</sup>Square Matrix
	 * 
	 * @return Norm of the R<sup>1</sup>Square Matrix
	 * 
	 * @throws Exception
	 */

	@Override public double norm (
		final R1Square r1Square)
		throws Exception
	{
		if (null == r1Square) {
			throw new Exception ("EntryWiseEvaluator::norm => Invalid Inputs");
		}

		double[][] r1Grid = r1Square.transpose().r1Grid();

		double pqNormCumulative = Integer.MIN_VALUE;
		double qOverP = _q  /_p;

		for (int i = 0; i < r1Grid.length; ++i) {
			double pNormCumulative = 0.;

			for (int j = 0; j < r1Grid[i].length; ++j) {
				pNormCumulative += Math.pow (Math.abs (r1Grid[i][j]), _p);
			}

			pqNormCumulative += Math.pow (pNormCumulative, qOverP);
		}

		return Math.pow (pqNormCumulative, 1. / _q);
	}
}