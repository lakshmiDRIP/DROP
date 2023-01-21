
package org.drip.function.rdtor1;

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
 * <i>AffineMultivariate</i> implements a Planar Linear R<sup>d</sup> To R<sup>1</sup> Function using a
 * Multivariate Vector.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1/README.md">Built-in R<sup>d</sup> To R<sup>1</sup> Functions</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class AffineMultivariate extends org.drip.function.definition.RdToR1 implements
	org.drip.function.rdtor1.ConvexMultivariate {
	private double[] _adblCoefficient = null;
	private double _dblConstant = java.lang.Double.NaN;

	/**
	 * AffineMultivariate Constructor
	 * 
	 * @param adblCoefficient Array of Variate Coefficients
	 * @param dblConstant The Constant Offset
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AffineMultivariate (
		final double[] adblCoefficient,
		final double dblConstant)
		throws java.lang.Exception
	{
		super (null);

		if (null == (_adblCoefficient = adblCoefficient) || 0 == _adblCoefficient.length ||
			!org.drip.numerical.common.NumberUtil.IsValid (_adblCoefficient) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblConstant = dblConstant))
			throw new java.lang.Exception ("AffineMultivariate Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Array of the Coefficients
	 * 
	 * @return The Array of the Coefficients
	 */

	public double[] coefficients()
	{
		return _adblCoefficient;
	}

	/**
	 * Retrieve the Constant
	 * 
	 * @return The Constant
	 */

	public double constant()
	{
		return _dblConstant;
	}

	@Override public int dimension()
	{
		return _adblCoefficient.length;
	}

	@Override public double evaluate (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		if (null == adblVariate || !org.drip.numerical.common.NumberUtil.IsValid (adblVariate))
			throw new java.lang.Exception ("AffineMultivariate::evaluate => Invalid Inputs");

		double dblValue = 0.;
		int iDimension = adblVariate.length;

		if (iDimension != dimension())
			throw new java.lang.Exception ("AffineMultivariate::evaluate => Invalid Inputs");

		for (int i = 0; i < iDimension; ++i)
			dblValue += adblVariate[i] * _adblCoefficient[i];

		return dblValue + _dblConstant;
	}

	@Override public double[] jacobian (
		final double[] adblVariate)
	{
		return _adblCoefficient;
	}

	@Override public double[][] hessian (
		final double[] adblVariate)
	{
		int iDimension = dimension();

		double[][] aadblHessian = new double[iDimension][iDimension];

		for (int i = 0; i < iDimension; ++i) {
			for (int j = 0; j < iDimension; ++j)
				aadblHessian[i][j] = 0.;
		}

		return aadblHessian;
	}
}
