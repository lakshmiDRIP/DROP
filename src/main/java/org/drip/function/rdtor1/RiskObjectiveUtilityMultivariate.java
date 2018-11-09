
package org.drip.function.rdtor1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>RiskObjectiveUtilityMultivariate</i> implements the Risk Objective R<sup>d</sup> To R<sup>1</sup>
 * Multivariate Function used in Portfolio Allocation. It accommodates both the Risk Tolerance and Risk
 * Aversion Variants.
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function">Function</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1">R<sup>d</sup> To R<sup>1</sup></a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RiskObjectiveUtilityMultivariate extends org.drip.function.definition.RdToR1 {
	private double[] _adblExpectedReturns = null;
	private double[][] _aadblCovarianceMatrix = null;
	private double _dblRiskFreeRate = java.lang.Double.NaN;
	private double _dblRiskAversion = java.lang.Double.NaN;
	private double _dblRiskTolerance = java.lang.Double.NaN;

	/**
	 * RiskObjectiveUtilityMultivariate Constructor
	 * 
	 * @param aadblCovarianceMatrix The Co-variance Matrix Double Array
	 * @param adblExpectedReturns Array of Expected Returns
	 * @param dblRiskAversion The Risk Aversion Parameter
	 * @param dblRiskTolerance The Risk Tolerance Parameter
	 * @param dblRiskFreeRate The Risk Free Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskObjectiveUtilityMultivariate (
		final double[][] aadblCovarianceMatrix,
		final double[] adblExpectedReturns,
		final double dblRiskAversion,
		final double dblRiskTolerance,
		final double dblRiskFreeRate)
		throws java.lang.Exception
	{
		super (null);

		if (null == (_aadblCovarianceMatrix = aadblCovarianceMatrix) || null == (_adblExpectedReturns =
			adblExpectedReturns) || !org.drip.quant.common.NumberUtil.IsValid (_dblRiskAversion =
				dblRiskAversion) || !org.drip.quant.common.NumberUtil.IsValid (_dblRiskTolerance =
					dblRiskTolerance) || !org.drip.quant.common.NumberUtil.IsValid (_dblRiskFreeRate =
						dblRiskFreeRate))
			throw new java.lang.Exception ("RiskObjectiveUtilityMultivariate Constructor => Invalid Inputs");

		int iSize = _aadblCovarianceMatrix.length;

		if (0 == iSize || iSize != _adblExpectedReturns.length)
			throw new java.lang.Exception ("RiskObjectiveUtilityMultivariate Constructor => Invalid Inputs");

		for (int i = 0; i < iSize; ++i) {
			if (null == _aadblCovarianceMatrix[i] || iSize != _aadblCovarianceMatrix[i].length ||
				!org.drip.quant.common.NumberUtil.IsValid (_aadblCovarianceMatrix[i]) ||
					!org.drip.quant.common.NumberUtil.IsValid (_adblExpectedReturns[i]))
				throw new java.lang.Exception
					("RiskObjectiveUtilityMultivariate Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Input Variate Dimension
	 * 
	 * @return The Input Variate Dimension
	 */

	public int dimension()
	{
		return _aadblCovarianceMatrix.length;
	}

	/**
	 * Retrieve the Co-variance Matrix
	 * 
	 * @return The Co-variance Matrix
	 */

	public double[][] covariance()
	{
		return _aadblCovarianceMatrix;
	}

	/**
	 * Retrieve the Array of Expected Returns
	 * 
	 * @return The Array of Expected Returns
	 */

	public double[] expectedReturns()
	{
		return _adblExpectedReturns;
	}

	/**
	 * Retrieve the Risk Aversion Factor
	 * 
	 * @return The Risk Aversion Factor
	 */

	public double riskAversion()
	{
		return _dblRiskAversion;
	}

	/**
	 * Retrieve the Risk Tolerance Factor
	 * 
	 * @return The Risk Tolerance Factor
	 */

	public double riskTolerance()
	{
		return _dblRiskTolerance;
	}

	/**
	 * Retrieve the Risk Free Rate
	 * 
	 * @return The Risk Free Rate
	 */

	public double riskFreeRate()
	{
		return _dblRiskFreeRate;
	}

	@Override public double evaluate (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		if (null == adblVariate || !org.drip.quant.common.NumberUtil.IsValid (adblVariate))
			throw new java.lang.Exception ("RiskObjectiveUtilityMultivariate::evaluate => Invalid Inputs");

		double dblValue = 0.;
		int iDimension = adblVariate.length;

		if (iDimension != dimension())
			throw new java.lang.Exception ("RiskObjectiveUtilityMultivariate::evaluate => Invalid Inputs");

		for (int i = 0; i < iDimension; ++i) {
			dblValue -= _dblRiskTolerance * adblVariate[i] * (_adblExpectedReturns[i] - _dblRiskFreeRate);

			for (int j = 0; j < iDimension; ++j)
				dblValue += 0.5 * _dblRiskAversion * adblVariate[i] * _aadblCovarianceMatrix[i][j] *
					adblVariate[j];
		}

		return dblValue;
	}

	@Override public double[] jacobian (
		final double[] adblVariate)
	{
		if (null == adblVariate || !org.drip.quant.common.NumberUtil.IsValid (adblVariate)) return null;

		int iDimension = adblVariate.length;
		double[] adblJacobian = new double[iDimension];

		if (iDimension != dimension()) return null;

		for (int i = 0; i < iDimension; ++i) {
			adblJacobian[i] = -1. * _dblRiskTolerance * (_adblExpectedReturns[i] - _dblRiskFreeRate);

			for (int j = 0; j < iDimension; ++j)
				adblJacobian[i] += _dblRiskAversion * _aadblCovarianceMatrix[i][j] * adblVariate[j];
		}

		return adblJacobian;
	}

	@Override public double[][] hessian (
		final double[] adblVariate)
	{
		int iDimension = dimension();

		double[][] aadblHessian = new double[iDimension][iDimension];

		for (int i = 0; i < iDimension; ++i) {
			for (int j = 0; j < iDimension; ++j)
				aadblHessian[i][j] += _dblRiskAversion * _aadblCovarianceMatrix[i][j];
		}

		return aadblHessian;
	}
}
