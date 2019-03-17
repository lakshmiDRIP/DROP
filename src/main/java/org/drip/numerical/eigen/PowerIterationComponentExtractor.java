
package org.drip.numerical.eigen;

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
 * <i>PowerIterationComponentExtractor</i> extracts the Linear System Components using the Power Iteration
 * Method.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical">Numerical Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/eigen">Eigen Component Extraction Methodologies</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PowerIterationComponentExtractor implements org.drip.numerical.eigen.ComponentExtractor {
	private int _iMaxIteration = -1;
	private boolean _bToleranceAbsolute = false;
	private double _dblTolerance = java.lang.Double.NaN;

	/**
	 * PowerIterationComponentExtractor Constructor
	 * 
	 * @param iMaxIteration Maximum Number of Iterations
	 * @param dblTolerance Tolerance
	 * @param bToleranceAbsolute Is Tolerance Absolute
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PowerIterationComponentExtractor (
		final int iMaxIteration,
		final double dblTolerance,
		final boolean bToleranceAbsolute)
		throws java.lang.Exception
	{
		if (0 >= (_iMaxIteration = iMaxIteration) || !org.drip.numerical.common.NumberUtil.IsValid (_dblTolerance
			= dblTolerance) || 0. == _dblTolerance)
			throw new java.lang.Exception ("PowerIterationComponentExtractor ctr: Invalid Inputs!");

		_bToleranceAbsolute = bToleranceAbsolute;
	}

	/**
	 * Retrieve the Maximum Number of Iterations
	 * 
	 * @return The Maximum Number of Iterations
	 */

	public int maxIterations()
	{
		return _iMaxIteration;
	}

	/**
	 * Retrieve the Tolerance Level
	 * 
	 * @return The Tolerance Level
	 */

	public double tolerance()
	{
		return _dblTolerance;
	}

	/**
	 * Indicate if the specified Tolerance is Absolute
	 * 
	 * @return TRUE - The specified Tolerance is Absolute
	 */

	public boolean isToleranceAbsolute()
	{
		return _bToleranceAbsolute;
	}

	@Override public org.drip.numerical.eigen.EigenComponent principalComponent (
		final double[][] aadblA)
	{
		if (null == aadblA) return null;

		int iIter = 0;
		int iSize = aadblA.length;
		double dblEigenvalue = iSize;
		double[] adblEigenvector = new double[iSize];
		double[] adblUpdatedEigenvector = new double[iSize];

		if (0 == iSize || null == aadblA[0] || iSize != aadblA[0].length) return null;

		for (int i = 0; i < iSize; ++i) {
			adblEigenvector[i] = 1.;
		}

		adblEigenvector = org.drip.numerical.linearalgebra.Matrix.Normalize (adblEigenvector);

		double dblEigenvalueOld = dblEigenvalue;
		double dblAbsoluteTolerance = _bToleranceAbsolute ? _dblTolerance : dblEigenvalue * _dblTolerance;
		dblAbsoluteTolerance = dblAbsoluteTolerance > _dblTolerance ? dblAbsoluteTolerance : _dblTolerance;

		while (iIter < _iMaxIteration) {
			for (int i = 0; i < iSize; ++i) {
				adblUpdatedEigenvector[i] = 0.;

				for (int j = 0; j < iSize; ++j)
					adblUpdatedEigenvector[i] += aadblA[i][j] * adblEigenvector[j];
			}

			adblUpdatedEigenvector = org.drip.numerical.linearalgebra.Matrix.Normalize (adblUpdatedEigenvector);

			try {
				dblEigenvalue = org.drip.numerical.linearalgebra.Matrix.RayleighQuotient (
					aadblA,
					adblUpdatedEigenvector
				);
			} catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}

			if (dblAbsoluteTolerance > java.lang.Math.abs (dblEigenvalue - dblEigenvalueOld)) break;

			adblEigenvector = adblUpdatedEigenvector;
			dblEigenvalueOld = dblEigenvalue;
			++iIter;
		}

		if (iIter >= _iMaxIteration) return null;

		try {
			return new org.drip.numerical.eigen.EigenComponent (adblUpdatedEigenvector, dblEigenvalue);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.numerical.eigen.EigenOutput eigenize (
		final double[][] aadblA)
	{
		return null;
	}
}
