
package org.drip.learning.kernel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>DiagonalScalingOperator</i> implements the Scaling Operator that is used to determine the Bounds of the
 * R<sup>x</sup> L<sub>2</sub> To R<sup>x</sup> L<sub>2</sub> Kernel Linear Integral Operator defined by:
 * 
 * 		T_k [f(.)] := Integral Over Input Space {k (., y) * f(y) * d[Prob(y)]}
 *  
 * <br><br>
 *  The References are:
 * <br><br>
 * <ul>
 * 	<li>
 *  	Ash, R. (1965): <i>Information Theory</i> <b>Inter-science</b> New York
 * 	</li>
 * 	<li>
 * 		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and Approximation of Operators</i>
 * 			<b>Cambridge University Press</b> Cambridge UK
 * 	</li>
 * 	<li>
 *  	Gordon, Y., H. Konig, and C. Schutt (1987): Geometric and Probabilistic Estimates of Entropy and
 *  		Approximation Numbers of Operators <i>Journal of Approximation Theory</i> <b>49</b> 219-237
 * 	</li>
 * 	<li>
 *  	Konig, H. (1986): <i>Eigenvalue Distribution of Compact Operators</i> <b>Birkhauser</b> Basel,
 *  		Switzerland
 * 	</li>
 * 	<li>
 *  	Smola, A. J., A. Elisseff, B. Scholkopf, and R. C. Williamson (2000): Entropy Numbers for Convex
 *  		Combinations and mlps, in: <i>Advances in Large Margin Classifiers, A. Smola, P. Bartlett, B.
 *  		Scholkopf, and D. Schuurmans - editors</i> <b>MIT Press</b> Cambridge, MA
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Learning</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/kernel">Kernel</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class DiagonalScalingOperator implements org.drip.spaces.cover.OperatorClassCoveringBounds {
	private double[] _adblDiagonalScaler = null;
	private double _dblScalingProductSupremumBound = java.lang.Double.NaN;

	/**
	 * DiagonalScalingOperator Constructor
	 * 
	 * @param adblDiagonalScaler The Diagonal Scaling Multiplier Array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DiagonalScalingOperator (
		final double[] adblDiagonalScaler)
		throws java.lang.Exception
	{
		if (null == (_adblDiagonalScaler = adblDiagonalScaler))
			throw new java.lang.Exception ("DiagonalScalingOperator Constructor: Invalid Inputs");

		double dblScalingProduct = 1.;
		int iScalingSize = _adblDiagonalScaler.length;

		if (0 == iScalingSize)
			throw new java.lang.Exception ("DiagonalScalingOperator Constructor: Invalid Inputs");

		for (int i = 0; i < iScalingSize; ++i) {
			if (!org.drip.numerical.common.NumberUtil.IsValid (_adblDiagonalScaler[i]) || 0. >
				_adblDiagonalScaler[i])
				throw new java.lang.Exception ("DiagonalScalingOperator Constructor: Invalid Inputs");

			if (0 == i) _dblScalingProductSupremumBound = _adblDiagonalScaler[i];

			if (i > 0) {
				if (_adblDiagonalScaler[i - 1] < _adblDiagonalScaler[i])
					throw new java.lang.Exception ("DiagonalScalingOperator Constructor: Invalid Inputs");

				double dblCurrentSupremumBound = java.lang.Math.pow ((dblScalingProduct *=
					_adblDiagonalScaler[i]) / iScalingSize, 1. / i);

				if (_dblScalingProductSupremumBound < dblCurrentSupremumBound)
					_dblScalingProductSupremumBound = dblCurrentSupremumBound;
			}
		}
	}

	/**
	 * Retrieve the Diagonal Scaling Multiplier Array
	 * 
	 * @return The Diagonal Scaling Multiplier Array
	 */

	public double[] scaler()
	{
		return _adblDiagonalScaler;
	}

	@Override public int entropyNumberIndex()
	{
		return _adblDiagonalScaler.length;
	}

	@Override public double entropyNumberLowerBound()
	{
		return _dblScalingProductSupremumBound;
	}

	@Override public double entropyNumberUpperBound()
	{
		return 6. * _dblScalingProductSupremumBound;
	}
}
