
package org.drip.measure.crng;

import org.drip.numerical.linearalgebra.R1MatrixUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>RdRandomSequence</i> generates 1D and 2D random arrays.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/README.md">Continuous Random Number Stream Generator</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RdRandomSequence
{

	/**
	 * Construct a 1D Array of Random Elements up to the Maximum Value
	 * 
	 * @param elementCount Number of Elements in the Array
	 * @param maximumElement Maximum Element
	 * @param isEntryInteger TRUE - Entry is an Integer
	 * 
	 * @return 1D Array of Random Elements up to the Maximum Value
	 */

	public static final double[] OneD (
		final int elementCount,
		final double maximumElement,
		final boolean isEntryInteger)
	{
		if (0 >= elementCount || 0 >= maximumElement) {
			return null;
		}

		double[] oneDArray = new double[elementCount];

		for (int i = 0; i < elementCount; ++i) {
			oneDArray[i] = Math.random() * maximumElement;

			if (isEntryInteger) {
				oneDArray[i] = (int) (oneDArray[i] + 0.5);
			}
		}

		return oneDArray;
	}

	/**
	 * Construct a 2D Matrix of Random Elements up to the Maximum Value
	 * 
	 * @param elementCount Number of Elements in the Array
	 * @param maximumElement Maximum Element
	 * @param isEntryInteger TRUE - Entry is an Integer
	 * 
	 * @return 2D Matrix of Random Elements up to the Maximum Value
	 */

	public static final double[][] TwoD (
		final int elementCount,
		final double maximumElement,
		final boolean isEntryInteger)
	{
		double[][] twoDArray = new double[elementCount][elementCount];

		for (int i = 0; i < elementCount; ++i) {
			if (null == (twoDArray[i] = OneD (elementCount, maximumElement, isEntryInteger))) {
				return null;
			}
		}

		return twoDArray;
	}

	/**
	 * Construct a Tridiagonal Matrix of Random Elements up to the Maximum Value
	 * 
	 * @param elementCount Number of Elements in the Array
	 * @param maximumElement Maximum Element
	 * @param isEntryInteger TRUE - Entry is an Integer
	 * 
	 * @return Tridiagonal Matrix of Random Elements up to the Maximum Value
	 */

	public static final double[][] Tridiagonal (
		final int elementCount,
		final double maximumElement,
		final boolean isEntryInteger)
	{
		double[][] tridiagonalMatrix = TwoD (elementCount, maximumElement, isEntryInteger);

		if (null == tridiagonalMatrix) {
			return null;
		}

    	for (int i = 0; i < tridiagonalMatrix.length; ++i) {
        	for (int j = 0; j < tridiagonalMatrix.length; ++j) {
        		if (j <= i - 2 || j >= i + 2) {
        			tridiagonalMatrix[i][j] = 0.;
        		}
        	}
    	}

		return tridiagonalMatrix;
	}

	/**
	 * Construct a Periodic Tridiagonal Matrix of Random Elements up to the Maximum Value
	 * 
	 * @param elementCount Number of Elements in the Array
	 * @param maximumElement Maximum Element
	 * @param isEntryInteger TRUE - Entry is an Integer
	 * 
	 * @return Periodic Tridiagonal Matrix of Random Elements up to the Maximum Value
	 */

	public static final double[][] PeriodicTridiagonal (
		final int elementCount,
		final double maximumElement,
		final boolean isEntryInteger)
	{
		double[][] tridiagonalMatrix = TwoD (elementCount, maximumElement, isEntryInteger);

		if (null == tridiagonalMatrix) {
			return null;
		}

    	for (int i = 0; i < tridiagonalMatrix.length; ++i) {
        	for (int j = 0; j < tridiagonalMatrix.length; ++j) {
        		if (j <= i - 2 || j >= i + 2) {
        			if (!R1MatrixUtil.TopRight (i, j, elementCount) && !R1MatrixUtil.BottomLeft (i, j, elementCount)) {
	        			tridiagonalMatrix[i][j] = 0.;
        			}
        		}
        	}
    	}

		return tridiagonalMatrix;
	}
}
