
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
 * <i>IntegralOperatorEigenContainer</i> holds the Group of Eigen-Components that result from the
 * Eigenization of the R<sup>x</sup> L<sub>2</sub> To R<sup>x</sup> L<sub>2</sub> Kernel Linear Integral
 * Operator defined by:
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
 * <br><br>
 * 	<ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Learning</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/kernel">Kernel</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class IntegralOperatorEigenContainer {
	private org.drip.learning.kernel.IntegralOperatorEigenComponent[] _aIOEC = null;

	/**
	 * IntegralOperatorEigenContainer Constructor
	 * 
	 * @param aIOEC Array of the Integral Operator Eigen-Components
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public IntegralOperatorEigenContainer (
		final org.drip.learning.kernel.IntegralOperatorEigenComponent[] aIOEC)
		throws java.lang.Exception
	{
		if (null == (_aIOEC = aIOEC) || 0 == _aIOEC.length)
			throw new java.lang.Exception ("IntegralOperatorEigenContainer ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Array of the Integral Operator Eigen-Components
	 * 
	 * @return The Array of the Integral Operator Eigen-Components
	 */

	public org.drip.learning.kernel.IntegralOperatorEigenComponent[] eigenComponents()
	{
		return _aIOEC;
	}

	/**
	 * Retrieve the Eigen Input Space
	 * 
	 * @return The Eigen Input Space
	 */

	public org.drip.spaces.metric.RdNormed inputMetricVectorSpace()
	{
		return _aIOEC[0].eigenFunction().inputMetricVectorSpace();
	}

	/**
	 * Retrieve the Eigen Output Space
	 * 
	 * @return The Eigen Output Space
	 */

	public org.drip.spaces.metric.R1Normed outputMetricVectorSpace()
	{
		return _aIOEC[0].eigenFunction().outputMetricVectorSpace();
	}

	/**
	 * Generate the Diagonally Scaled Normed Vector Space of the RKHS Feature Space Bounds that results on
	 *  applying the Diagonal Scaling Operator
	 * 
	 * @param dso The Diagonal Scaling Operator
	 * 
	 * @return The Diagonally Scaled Normed Vector Space of the RKHS Feature Space
	 */

	public org.drip.spaces.metric.R1Combinatorial diagonallyScaledFeatureSpace (
		final org.drip.learning.kernel.DiagonalScalingOperator dso)
	{
		if (null == dso) return null;

		double[] adblDiagonalScalingOperator = dso.scaler();

		int iDimension = adblDiagonalScalingOperator.length;

		if (iDimension != _aIOEC.length) return null;

		java.util.List<java.lang.Double> lsElementSpace = new java.util.ArrayList<java.lang.Double>();

		for (int i = 0; i < iDimension; ++i)
			lsElementSpace.add (0.5 * _aIOEC[i].rkhsFeatureParallelepipedLength() /
				adblDiagonalScalingOperator[i]);

		try {
			return new org.drip.spaces.metric.R1Combinatorial (lsElementSpace, null, 2);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Operator Class Covering Number Bounds of the RKHS Feature Space Bounds that result on the
	 *  Application of the Diagonal Scaling Operator
	 * 
	 * @param dso The Diagonal Scaling Operator
	 * 
	 * @return The Operator Class Covering Number Bounds of the RKHS Feature Space
	 */

	public org.drip.spaces.cover.OperatorClassCoveringBounds scaledCoveringNumberBounds (
		final org.drip.learning.kernel.DiagonalScalingOperator dso)
	{
		final org.drip.spaces.metric.R1Combinatorial r1ContinuousScaled = diagonallyScaledFeatureSpace (dso);

		if (null == r1ContinuousScaled) return null;

		try {
			final double dblPopulationMetricNorm = r1ContinuousScaled.populationMetricNorm();

			org.drip.spaces.cover.OperatorClassCoveringBounds occb = new
				org.drip.spaces.cover.OperatorClassCoveringBounds() {
				@Override public double entropyNumberLowerBound()
					throws java.lang.Exception
				{
					return dso.entropyNumberLowerBound() * dblPopulationMetricNorm;
				}

				@Override public double entropyNumberUpperBound()
					throws java.lang.Exception
				{
					return dso.entropyNumberUpperBound() * dblPopulationMetricNorm;
				}

				@Override public int entropyNumberIndex()
				{
					return dso.entropyNumberIndex();
				}

				@Override public double norm()
					throws java.lang.Exception
				{
					return dso.norm() * dblPopulationMetricNorm;
				}

				@Override public org.drip.learning.bound.DiagonalOperatorCoveringBound
					entropyNumberAsymptote()
				{
					return dso.entropyNumberAsymptote();
				}
			};

			return occb;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
