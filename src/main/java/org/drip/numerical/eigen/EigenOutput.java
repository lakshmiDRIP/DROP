
package org.drip.numerical.eigen;

import java.util.HashMap;
import java.util.Map;

import org.drip.function.definition.R1ToR1;

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
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>EigenOutput</i> holds the results of the Eigenization Operation - the Eigenvectors and the Eigenvalues.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/eigen">QR PICE Eigen-Component Extraction Methodologies</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class EigenOutput
{
	private double[] _eigenValueArray = null;
	private double[][] _eigenVectorArray = null;

	/**
	 * EigenOutput Constructor
	 * 
	 * @param eigenVectorArray Array of Eigenvectors
	 * @param eigenValueArray Array of Eigenvalues
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EigenOutput (
		final double[][] eigenVectorArray,
		final double[] eigenValueArray)
		throws java.lang.Exception
	{
		if (null == (_eigenVectorArray = eigenVectorArray) ||
			null == (_eigenValueArray = eigenValueArray)
		)
		{
			throw new java.lang.Exception (
				"EigenOutput ctr: Invalid Inputs"
			);
		}

		int eigenComponentCount = _eigenValueArray.length;

		if (0 == eigenComponentCount || eigenComponentCount != _eigenVectorArray.length ||
			null == _eigenVectorArray[0] || eigenComponentCount != _eigenVectorArray[0].length
		)
		{
			throw new java.lang.Exception (
				"EigenOutput ctr: Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Array of Eigenvectors
	 * 
	 * @return The Array of Eigenvectors
	 */

	public double[][] eigenVectorArray()
	{
		return _eigenVectorArray;
	}

	/**
	 * Retrieve the Array of Eigenvalues
	 * 
	 * @return The Array of Eigenvalues
	 */

	public double[] eigenValueArray()
	{
		return _eigenValueArray;
	}

	/**
	 * Retrieve the Eigen-Dimension
	 * 
	 * @return The Eigen-Dimension
	 */

	public int dimension()
	{
		return _eigenValueArray.length;
	}

	/**
	 * Retrieve the Eigenvalue Multiplicity Map
	 * 
	 * @return Eigenvalue Multiplicity Map
	 */

	public Map<Double, Integer> eigenValueMultiplicityMap()
	{
		Map<Double, Integer> eigenValueMultiplicityMap = new HashMap<Double, Integer>();

		for (double eigenValue : _eigenValueArray) {
			eigenValueMultiplicityMap.put (
				eigenValue,
				eigenValueMultiplicityMap.containsKey (eigenValue) ?
					eigenValueMultiplicityMap.get (eigenValue) + 1 : 1
			);
		}

		return eigenValueMultiplicityMap;
	}

	/**
	 * Compute the Determinant of the Matrix
	 * 
	 * @return Determinant of the Matrix
	 */

	public double determinant()
	{
		double determinant = 1.;

		for (double eigenValue : _eigenValueArray) {
			determinant *= eigenValue;
		}

		return determinant;
	}

	/**
	 * Retrieve the Characteristic Polynomial of the Eigenvalues
	 * 
	 * @return Characteristic Polynomial of the Eigenvalues
	 */

	public R1ToR1 characteristicPolynomial()
	{
		return new R1ToR1 (null)
		{
			@Override public double evaluate (
				final double x)
				throws Exception
			{
				double value = 1.;

				for (double eigenValue : _eigenValueArray) {
					value *= (x - eigenValue);
				}

				return value;
			}
		};
	}

	/**
	 * Compute the Condition Number using the Eigenvalue Array
	 * 
	 * @return Condition Number
	 */

	public double conditionNumber()
	{
		double firstAbsoluteEigenvalue = Math.abs (_eigenValueArray[0]);

		double minimumEigenvalue = firstAbsoluteEigenvalue;
		double maximumEigenvalue = firstAbsoluteEigenvalue;

		for (int i = 1; i < _eigenValueArray.length; ++i) {
			double absoluteEigenvalue = Math.abs (_eigenValueArray[i]);

			if (minimumEigenvalue > absoluteEigenvalue) {
				minimumEigenvalue = absoluteEigenvalue;
			}

			if (maximumEigenvalue < absoluteEigenvalue) {
				maximumEigenvalue = absoluteEigenvalue;
			}
		}

		return maximumEigenvalue / minimumEigenvalue;
	}

	/**
	 * Compute the Maximum Eigenvalue
	 * 
	 * @return Maximum Eigenvalue
	 */

	public double maximumEigenvalue()
	{
		double maximumEigenvalue = Math.abs (_eigenValueArray[0]);

		for (int i = 1; i < _eigenValueArray.length; ++i) {
			double absoluteEigenvalue = Math.abs (_eigenValueArray[i]);

			if (maximumEigenvalue < absoluteEigenvalue) {
				maximumEigenvalue = absoluteEigenvalue;
			}
		}

		return maximumEigenvalue;
	}

	/**
	 * Compute the Spectral Radius using the Eigenvalue Array
	 * 
	 * @return Spectral Radius
	 */

	public double spectralRadius()
	{
		return maximumEigenvalue();
	}

	/**
	 * Compute the Spectral Norm using the Eigenvalue Array
	 * 
	 * @return Spectral Norm
	 */

	public double spectralNorm()
	{
		return maximumEigenvalue();
	}
}
