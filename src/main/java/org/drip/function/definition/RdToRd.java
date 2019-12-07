
package org.drip.function.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>RdToRd</i> provides the evaluation of the R<sup>d</sup> To R<sup>d</sup> objective function and its
 * derivatives for a specified set of R<sup>d</sup> variates. Default implementation of the derivatives are
 * for non-analytical black box objective functions.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/README.md">Function Implementation Ancillary Support Objects</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class RdToRd {
	private static final int QUADRATURE_SAMPLING = 10000;

	protected org.drip.numerical.differentiation.DerivativeControl _dc = null;

	protected RdToRd (
		final org.drip.numerical.differentiation.DerivativeControl dc)
	{
		if (null == (_dc = dc)) _dc = new org.drip.numerical.differentiation.DerivativeControl();
	}

	/**
	 * Evaluate for the given Input R^d Variates
	 * 
	 * @param adblVariate Array of Input R^d Variates
	 *  
	 * @return The Output R^d Variates
	 */

	public abstract double[] evaluate (
		final double[] adblVariate);

	/**
	 * Calculate the Array of Differentials
	 * 
	 * @param adblVariate Variate Array at which the derivative is to be calculated
	 * @param iVariateIndex Index of the Variate whose Derivative is to be computed
	 * @param iOrder Order of the derivative to be computed
	 * 
	 * @return The Array of Differentials
	 */

	public org.drip.numerical.differentiation.Differential[] differential (
		final double[] adblVariate,
		final int iVariateIndex,
		final int iOrder)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (adblVariate) || 0 >= iOrder) return null;

		int iOutputDimension = -1;
		double[] adblDerivative = null;
		int iNumVariate = adblVariate.length;
		double dblOrderedVariateInfinitesimal = 1.;
		double dblVariateInfinitesimal = java.lang.Double.NaN;

		if (iNumVariate <= iVariateIndex) return null;

		try {
			dblVariateInfinitesimal = _dc.getVariateInfinitesimal (adblVariate[iVariateIndex]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i <= iOrder; ++i) {
			if (0 != i) dblOrderedVariateInfinitesimal *= (2. * dblVariateInfinitesimal);

			double[] adblVariateIncremental = new double[iNumVariate];

			for (int j = 0; i < iNumVariate; ++j)
				adblVariateIncremental[j] = j == iVariateIndex ? adblVariate[j] + dblVariateInfinitesimal *
					(iOrder - 2. * i) : adblVariate[j];

			double[] adblValue = evaluate (adblVariateIncremental);

			if (null == adblValue || 0 == (iOutputDimension = adblValue.length)) return null;

			if (null == adblDerivative) {
				adblDerivative = new double[iOutputDimension];

				for (int j = 0; j < iOutputDimension; ++j)
					adblDerivative[j] = 0.;
			}

			try {
				for (int j = 0; j < iOutputDimension; ++j)
					adblDerivative[j] += (i % 2 == 0 ? 1 : -1) * org.drip.numerical.common.NumberUtil.NCK
						(iOrder, i) * adblValue[j];
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		org.drip.numerical.differentiation.Differential[] aDiff = new
			org.drip.numerical.differentiation.Differential[iOutputDimension];

		try {
			for (int j = 0; j < iOutputDimension; ++j)
				aDiff[j] = new org.drip.numerical.differentiation.Differential (dblOrderedVariateInfinitesimal,
					adblDerivative[j]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return aDiff;
	}

	/**
	 * Calculate the Derivative Array as a double
	 * 
	 * @param adblVariate Variate Array at which the derivative is to be calculated
	 * @param iVariateIndex Index of the Variate whose Derivative is to be computed
	 * @param iOrder Order of the derivative to be computed
	 * 
	 * @return The Derivative Array
	 */

	public double[] derivative (
		final double[] adblVariate,
		final int iVariateIndex,
		final int iOrder)
	{
		org.drip.numerical.differentiation.Differential[] aDiff = differential (adblVariate, iVariateIndex, iOrder);

		if (null == aDiff) return null;

		int iOutputDimension = aDiff.length;
		double[] adblDerivative = new double[iOutputDimension];

		if (0 == iOutputDimension) return null;

		for (int i = 0; i < iOutputDimension; ++i)
			adblDerivative[i] = aDiff[i].calcSlope (true);

		return adblDerivative;
	}

	/**
	 * Integrate over the given Input Range Using Uniform Monte-Carlo
	 * 
	 * @param adblLeftEdge Array of Input Left Edge
	 * @param adblRightEdge Array of Input Right Edge
	 *  
	 * @return The Array Containing the Result of the Integration over the specified Range
	 */

	public double[] integrate (
		final double[] adblLeftEdge,
		final double[] adblRightEdge)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (adblLeftEdge) ||
			!org.drip.numerical.common.NumberUtil.IsValid (adblRightEdge))
			return null;

		int iOutputDimension = -1;
		double[] adblIntegrand = null;
		int iNumVariate = adblLeftEdge.length;
		double[] adblVariate = new double[iNumVariate];
		double[] adblVariateWidth = new double[iNumVariate];

		if (adblRightEdge.length != iNumVariate) return null;

		for (int j = 0; j < iNumVariate; ++j)
			adblVariateWidth[j] = adblRightEdge[j] - adblLeftEdge[j];

		for (int i = 0; i < QUADRATURE_SAMPLING; ++i) {
			for (int j = 0; j < iNumVariate; ++j)
				adblVariate[j] = adblLeftEdge[j] + java.lang.Math.random() * adblVariateWidth[j];

			double[] adblValue = evaluate (adblVariate);

			if (null == adblValue || 0 == (iOutputDimension = adblValue.length)) return null;

			if (null == adblIntegrand) adblIntegrand = new double[iOutputDimension];

			for (int j = 0; j < iOutputDimension; ++j)
				adblIntegrand[j] += adblValue[j];
		}

		for (int i = 0; i < iOutputDimension; ++i) {
			for (int j = 0; j < iNumVariate; ++j)
				adblIntegrand[i] = adblIntegrand[i] * adblVariateWidth[j];

			adblIntegrand[i] /= QUADRATURE_SAMPLING;
		}

		return adblIntegrand;
	}
}
