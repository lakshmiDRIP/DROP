
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
 * <i>SymmetricRdToNormedRdKernel</i> exposes the Functionality behind the Kernel that is Normed
 * R<sup>d</sup> X Normed R<sup>d</sup> To Normed R<sup>d</sup>, that is, a Kernel that symmetric in the
 * Input Metric Vector Space in terms of both the Metric and the Dimensionality.
 *  
 * <br><br>
 *  The References are:
 * <br><br>
 * <ul>
 * 	<li>
 *  	Ash, R. (1965): <i>Information Theory</i> <b>Inter-science</b> New York
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

public abstract class SymmetricRdToNormedRdKernel {
	private org.drip.spaces.metric.RdNormed _rdContinuousInput = null;
	private org.drip.spaces.metric.RdNormed _rdContinuousOutput = null;

	/**
	 * SymmetricRdToNormedRdKernel Constructor
	 * 
	 * @param rdContinuousInput The Symmetric Input R^d Metric Vector Space
	 * @param rdContinuousOutput The Output R^d Metric Vector Space
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SymmetricRdToNormedRdKernel (
		final org.drip.spaces.metric.RdNormed rdContinuousInput,
		final org.drip.spaces.metric.RdNormed rdContinuousOutput)
		throws java.lang.Exception
	{
		if (null == (_rdContinuousInput = rdContinuousInput) || null == (_rdContinuousOutput =
			rdContinuousOutput))
			throw new java.lang.Exception ("SymmetricRdToNormedR1Kernel ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Symmetric Input Metric R^d Vector Space
	 * 
	 * @return The Symmetric Input Metric R^d Vector Space
	 */

	public org.drip.spaces.metric.RdNormed inputMetricVectorSpace()
	{
		return _rdContinuousInput;
	}

	/**
	 * Retrieve the Output R^d Metric Vector Space
	 * 
	 * @return The Output R^d Metric Vector Space
	 */

	public org.drip.spaces.metric.RdNormed outputMetricVectorSpace()
	{
		return _rdContinuousOutput;
	}

	/**
	 * Compute the Feature Space Input Dimension
	 * 
	 * @return The Feature Space Input Dimension
	 */

	public int featureSpaceDimension()
	{
		return _rdContinuousOutput.dimension();
	}

	/**
	 * Compute the Kernel's R^d X R^d To R^1 Dot-Product Value
	 * 
	 * @param adblX Validated Vector Instance X
	 * @param adblY Validated Vector Instance Y
	 * 
	 * @return The Kernel's R^d X R^d To R^1 Dot-Product Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public abstract double evaluate (
		final double[] adblX,
		final double[] adblY)
		throws java.lang.Exception;
}
