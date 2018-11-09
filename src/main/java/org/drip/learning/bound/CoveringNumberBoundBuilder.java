
package org.drip.learning.bound;

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
 * <i>CoveringNumberBoundBuilder</i> constructs the CoveringNumberProbabilityBound Instances for specific
 * Learning Situations. The References are:
 * <br><br>
 * <ul>
 * 	<li>
 *  	Alon, N., S. Ben-David, N. Cesa Bianchi, and D. Haussler (1997): Scale-sensitive Dimensions, Uniform
 *  		Convergence, and Learnability <i>Journal of Association of Computational Machinery</i> <b>44
 *  		(4)</b> 615-631
 * 	</li>
 * 	<li>
 *  	Anthony, M., and P. L. Bartlett (1999): <i>Artificial Neural Network Learning - Theoretical
 *  		Foundations</i> <b>Cambridge University Press</b> Cambridge, UK
 * 	</li>
 * 	<li>
 *  	Kearns, M. J., R. E. Schapire, and L. M. Sellie (1994): <i>Towards Efficient Agnostic Learning</i>
 *  		Machine Learning <b>17 (2)</b> 115-141
 * 	</li>
 * 	<li>
 *  	Lee, W. S., P. L. Bartlett, and R. C. Williamson (1998): The Importance of Convexity in Learning with
 *  		Squared Loss <i>IEEE Transactions on Information Theory</i> <b>44</b> 1974-1980
 * 	</li>
 * 	<li>
 *  	Vapnik, V. N. (1998): <i>Statistical learning Theory</i> <b>Wiley</b> New York
 * 	</li>
 * </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Learning</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/bound">Bound</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CoveringNumberBoundBuilder {

	/**
	 * Epsilon Exponent for Regression Learning
	 */

	public static final double EPSILON_EXPONENT_REGRESSION_LEARNING = 1.;

	/**
	 * Epsilon Exponent for Agnostic Learning
	 */

	public static final double EPSILON_EXPONENT_AGNOSTIC_LEARNING = 2.;

	/**
	 * Epsilon Exponent for Agnostic Learning with Convex Functions
	 */

	public static final double EPSILON_EXPONENT_AGNOSTIC_CONVEX_LEARNING = 1.;

	/**
	 * Construct the Regression Learning CoveringNumberProbabilityBound Instance
	 * 
	 * @param funcSampleCoefficient The Sample Coefficient Function
	 * @param dblExponentScaler The Exponent Scaler
	 * 
	 * @return The Regression Learning CoveringNumberProbabilityBound Instance
	 */

	public static final org.drip.learning.bound.CoveringNumberLossBound
		RegressionLearning (
			final org.drip.function.definition.R1ToR1 funcSampleCoefficient,
			final double dblExponentScaler)
	{
		try {
			return new org.drip.learning.bound.CoveringNumberLossBound (funcSampleCoefficient,
				EPSILON_EXPONENT_REGRESSION_LEARNING, dblExponentScaler);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Agnostic Learning CoveringNumberProbabilityBound Instance
	 * 
	 * @param funcSampleCoefficient The Sample Coefficient Function
	 * @param dblExponentScaler The Exponent Scaler
	 * 
	 * @return The Agnostic Learning CoveringNumberProbabilityBound Instance
	 */

	public static final org.drip.learning.bound.CoveringNumberLossBound
		AgnosticLearning (
			final org.drip.function.definition.R1ToR1 funcSampleCoefficient,
			final double dblExponentScaler)
	{
		try {
			return new org.drip.learning.bound.CoveringNumberLossBound (funcSampleCoefficient,
				EPSILON_EXPONENT_AGNOSTIC_LEARNING, dblExponentScaler);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Agnostic Convex Learning CoveringNumberProbabilityBound Instance
	 * 
	 * @param funcSampleCoefficient The Sample Coefficient Function
	 * @param dblExponentScaler The Exponent Scaler
	 * 
	 * @return The Agnostic Convex Learning CoveringNumberProbabilityBound Instance
	 */

	public static final org.drip.learning.bound.CoveringNumberLossBound
		AgnosticConvexLearning (
			final org.drip.function.definition.R1ToR1 funcSampleCoefficient,
			final double dblExponentScaler)
	{
		try {
			return new org.drip.learning.bound.CoveringNumberLossBound (funcSampleCoefficient,
				EPSILON_EXPONENT_AGNOSTIC_CONVEX_LEARNING, dblExponentScaler);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
