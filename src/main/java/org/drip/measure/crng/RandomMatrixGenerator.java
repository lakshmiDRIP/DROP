
package org.drip.measure.crng;

import org.drip.numerical.eigenization.EigenOutput;
import org.drip.numerical.matrix.R1SquareEigenized;
import org.drip.numerical.matrix.R1Triangular;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
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
 * <i>RandomMatrixGenerator</i> provides Functionality for generating different Kinds of Random Matrices. The
 *  References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Axler, S. J. (1997): <i>Linear Algebra Done Right 2<sup>nd</sup> Edition</i> <b>Springer</b>
 * 				New York NY
 * 		</li>
 * 		<li>
 * 			Bernstein, D. S. (2009): <i>Matrix Mathematics: Theory, Facts, and Formulas 2<sup>nd</sup>
 * 				Edition</i> <b>Princeton University Press</b> Princeton NJ
 * 		</li>
 * 		<li>
 * 			Herstein, I. N. (1975): <i>Topics in Algebra 2<sup>nd</sup> Edition</i> <b>Wiley</b> New York NY
 * 		</li>
 * 		<li>
 * 			Prasolov, V. V. (1994): <i>Topics in Algebra</i> <b>American Mathematical Society</b> Providence
 * 				RI
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Triangular Matrix https://en.wikipedia.org/wiki/Triangular_matrix
 * 		</li>
 * 	</ul>
 * 
 * It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Construct a Lower Triangular Matrix of Random Elements up to the Maximum Value</li>
 * 		<li>Construct an Upper Triangular Matrix of Random Elements up to the Maximum Value</li>
 * 		<li>Construct a Diagonal Matrix of Random Elements up to the Maximum Value</li>
 *		<li>Construct a Lower Unitriangular Matrix of Random Elements up to the Maximum Value</li>
 * 		<li>Construct an Upper Unitriangular Matrix of Random Elements up to the Maximum Value</li>
 * 		<li>Construct a Strictly Lower Triangular Matrix of Random Elements up to the Maximum Value</li>
 * 		<li>Construct a Strictly Upper Triangular Matrix of Random Elements up to the Maximum Value</li>
 * 		<li>Construct an Atomic Lower Triangular Matrix of Random Elements up to the Maximum Value</li>
 * 		<li>Construct an Atomic Upper Triangular Matrix of Random Elements up to the Maximum Value</li>
 * 		<li>Construct an Eigenized Square Matrix of Random Elements up to the Maximum Value</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/crng/README.md">Continuous Random Number Stream Generator</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RandomMatrixGenerator
{

	/**
	 * Construct a Lower Triangular Matrix of Random Elements up to the Maximum Value
	 * 
	 * @param elementCount Number of Elements in the Array
	 * @param maximumElement Maximum Element
	 * @param isEntryInteger TRUE - Entry is an Integer
	 * 
	 * @return Lower Triangular Matrix of Random Elements up to the Maximum Value
	 */

	public static final R1Triangular LowerTriangular (
		final int elementCount,
		final double maximumElement,
		final boolean isEntryInteger)
	{
		double[][] r2Array = RdRandomSequence.TwoD (elementCount, maximumElement, isEntryInteger);

    	for (int i = 0; i < r2Array.length; ++i) {
        	for (int j = i + 1; j < r2Array.length; ++j) {
    			r2Array[i][j] = 0.;
        	}
    	}

		return R1Triangular.Standard (r2Array);
	}

	/**
	 * Construct an Upper Triangular Matrix of Random Elements up to the Maximum Value
	 * 
	 * @param elementCount Number of Elements in the Array
	 * @param maximumElement Maximum Element
	 * @param isEntryInteger TRUE - Entry is an Integer
	 * 
	 * @return Upper Triangular Matrix of Random Elements up to the Maximum Value
	 */

	public static final R1Triangular UpperTriangular (
		final int elementCount,
		final double maximumElement,
		final boolean isEntryInteger)
	{
		double[][] r2Array = RdRandomSequence.TwoD (elementCount, maximumElement, isEntryInteger);

    	for (int i = 0; i < r2Array.length; ++i) {
        	for (int j = 0; j < i; ++j) {
    			r2Array[i][j] = 0.;
        	}
    	}

		return R1Triangular.Standard (r2Array);
	}

	/**
	 * Construct a Diagonal Matrix of Random Elements up to the Maximum Value
	 * 
	 * @param elementCount Number of Elements in the Array
	 * @param maximumElement Maximum Element
	 * @param isEntryInteger TRUE - Entry is an Integer
	 * 
	 * @return Diagonal Matrix of Random Elements up to the Maximum Value
	 */

	public static final R1Triangular Diagonal (
		final int elementCount,
		final double maximumElement,
		final boolean isEntryInteger)
	{
		double[][] r2Array = RdRandomSequence.TwoD (elementCount, maximumElement, isEntryInteger);

    	for (int i = 0; i < r2Array.length; ++i) {
        	for (int j = 0; j < r2Array.length; ++j) {
        		if (i != j) {
	    			r2Array[i][j] = 0.;
        		}
        	}
    	}

		return R1Triangular.Standard (r2Array);
	}

	/**
	 * Construct a Lower Unitriangular Matrix of Random Elements up to the Maximum Value
	 * 
	 * @param elementCount Number of Elements in the Array
	 * @param maximumElement Maximum Element
	 * @param isEntryInteger TRUE - Entry is an Integer
	 * 
	 * @return Lower Unitriangular Matrix of Random Elements up to the Maximum Value
	 */

	public static final R1Triangular LowerUnitriangular (
		final int elementCount,
		final double maximumElement,
		final boolean isEntryInteger)
	{
		double[][] r2Array = RdRandomSequence.TwoD (elementCount, maximumElement, isEntryInteger);

    	for (int i = 0; i < r2Array.length; ++i) {
        	for (int j = i + 1; j < r2Array.length; ++j) {
    			r2Array[i][j] = 0.;
        	}
    	}

    	for (int i = 0; i < r2Array.length; ++i) {
			r2Array[i][i] = 1.;
    	}

		return R1Triangular.Standard (r2Array);
	}

	/**
	 * Construct an Upper Unitriangular Matrix of Random Elements up to the Maximum Value
	 * 
	 * @param elementCount Number of Elements in the Array
	 * @param maximumElement Maximum Element
	 * @param isEntryInteger TRUE - Entry is an Integer
	 * 
	 * @return Upper Unitriangular Matrix of Random Elements up to the Maximum Value
	 */

	public static final R1Triangular UpperUnitriangular (
		final int elementCount,
		final double maximumElement,
		final boolean isEntryInteger)
	{
		double[][] r2Array = RdRandomSequence.TwoD (elementCount, maximumElement, isEntryInteger);

    	for (int i = 0; i < r2Array.length; ++i) {
        	for (int j = 0; j < i; ++j) {
    			r2Array[i][j] = 0.;
        	}
    	}

    	for (int i = 0; i < r2Array.length; ++i) {
			r2Array[i][i] = 1.;
    	}

		return R1Triangular.Standard (r2Array);
	}

	/**
	 * Construct a Strictly Lower Triangular Matrix of Random Elements up to the Maximum Value
	 * 
	 * @param elementCount Number of Elements in the Array
	 * @param maximumElement Maximum Element
	 * @param isEntryInteger TRUE - Entry is an Integer
	 * 
	 * @return Strictly Lower Triangular Matrix of Random Elements up to the Maximum Value
	 */

	public static final R1Triangular StrictlyLowerTriangular (
		final int elementCount,
		final double maximumElement,
		final boolean isEntryInteger)
	{
		double[][] r2Array = RdRandomSequence.TwoD (elementCount, maximumElement, isEntryInteger);

    	for (int i = 0; i < r2Array.length; ++i) {
        	for (int j = i + 1; j < r2Array.length; ++j) {
    			r2Array[i][j] = 0.;
        	}
    	}

    	for (int i = 0; i < r2Array.length; ++i) {
			r2Array[i][i] = 0.;
    	}

		return R1Triangular.Standard (r2Array);
	}

	/**
	 * Construct a Strictly Upper Triangular Matrix of Random Elements up to the Maximum Value
	 * 
	 * @param elementCount Number of Elements in the Array
	 * @param maximumElement Maximum Element
	 * @param isEntryInteger TRUE - Entry is an Integer
	 * 
	 * @return Strictly Upper Triangular Matrix of Random Elements up to the Maximum Value
	 */

	public static final R1Triangular StrictlyUpperTriangular (
		final int elementCount,
		final double maximumElement,
		final boolean isEntryInteger)
	{
		double[][] r2Array = RdRandomSequence.TwoD (elementCount, maximumElement, isEntryInteger);

    	for (int i = 0; i < r2Array.length; ++i) {
        	for (int j = 0; j < i; ++j) {
    			r2Array[i][j] = 0.;
        	}
    	}

    	for (int i = 0; i < r2Array.length; ++i) {
			r2Array[i][i] = 0.;
    	}

		return R1Triangular.Standard (r2Array);
	}

	/**
	 * Construct an Atomic Lower Triangular Matrix of Random Elements up to the Maximum Value
	 * 
	 * @param elementCount Number of Elements in the Array
	 * @param maximumElement Maximum Element
	 * @param isEntryInteger TRUE - Entry is an Integer
	 * 
	 * @return Atomic Lower Triangular Matrix of Random Elements up to the Maximum Value
	 */

	public static final R1Triangular AtomicLowerTriangular (
		final int elementCount,
		final double maximumElement,
		final boolean isEntryInteger)
	{
		double[][] r2Array = RdRandomSequence.TwoD (elementCount, maximumElement, isEntryInteger);

    	for (int i = 0; i < r2Array.length; ++i) {
        	for (int j = 0; j < r2Array.length; ++j) {
    			r2Array[i][j] = 0.;
        	}
    	}

    	for (int i = 0; i < r2Array.length; ++i) {
			r2Array[i][i] = 1.;
    	}

    	int columnIndex = (int) (Math.random() * elementCount);

		if (((int) maximumElement) - 1 == columnIndex) {
			columnIndex = ((int) maximumElement) - 2;
		} else if (0 == columnIndex) {
			columnIndex = 1;
		}

		for (int i = 0; i < r2Array.length; ++i) {
    		if (columnIndex < i) {
				r2Array[i][columnIndex] = 1.;
    		}
    	}

		return R1Triangular.Standard (r2Array);
	}

	/**
	 * Construct an Atomic Upper Triangular Matrix of Random Elements up to the Maximum Value
	 * 
	 * @param elementCount Number of Elements in the Array
	 * @param maximumElement Maximum Element
	 * @param isEntryInteger TRUE - Entry is an Integer
	 * 
	 * @return Atomic Upper Triangular Matrix of Random Elements up to the Maximum Value
	 */

	public static final R1Triangular AtomicUpperTriangular (
		final int elementCount,
		final double maximumElement,
		final boolean isEntryInteger)
	{
		double[][] r2Array = RdRandomSequence.TwoD (elementCount, maximumElement, isEntryInteger);

    	for (int i = 0; i < r2Array.length; ++i) {
        	for (int j = 0; j < r2Array.length; ++j) {
    			r2Array[i][j] = 0.;
        	}
    	}

    	for (int i = 0; i < r2Array.length; ++i) {
			r2Array[i][i] = 1.;
    	}

    	int columnIndex = (int) (Math.random() * elementCount);

		if (((int) maximumElement) - 1 == columnIndex) {
			columnIndex = ((int) maximumElement) - 2;
		} else if (0 == columnIndex) {
			columnIndex = 1;
		}

		for (int i = 0; i < r2Array.length; ++i) {
    		if (columnIndex > i) {
				r2Array[i][columnIndex] = 1.;
    		}
    	}

		return R1Triangular.Standard (r2Array);
	}

	/**
	 * Construct an Eigenized Square Matrix of Random Elements up to the Maximum Value
	 * 
	 * @param elementCount Number of Elements in the Array
	 * @param maximumElement Maximum Element
	 * @param isEntryInteger TRUE - Entry is an Integer
	 * 
	 * @return Eigenized Square Matrix of Random Elements up to the Maximum Value
	 */

	public static final R1SquareEigenized EigenizedR1Square (
		final int elementCount,
		final double maximumElement,
		final boolean isEntryInteger)
	{
		try {
			return R1SquareEigenized.Standard (
				new EigenOutput (
					RdRandomSequence.TwoD (elementCount, maximumElement, isEntryInteger),
					RdRandomSequence.OneD (elementCount, maximumElement, isEntryInteger)
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
