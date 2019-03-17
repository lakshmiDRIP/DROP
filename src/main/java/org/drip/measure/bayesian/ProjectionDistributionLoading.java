
package org.drip.measure.bayesian;

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
 * <i>ProjectionDistributionLoading</i> contains the Projection Distribution and its Loadings to the Scoping
 * Distribution.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian">Bayesian</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ProjectionDistributionLoading {
	private double[][] _aadblScopingLoading = null;
	private org.drip.measure.continuous.R1Multivariate _r1mDistribution = null;

	/**
	 * Generate the Projection Co-variance Matrix from the Confidence Level
	 * 
	 * @param aadblScopingCovariance The Scoping Co-variance Matrix
	 * @param aadblScopingLoading The Projection-Scoping Variate Loadings
	 * @param dblTau The Tau Parameter
	 * 
	 * @return The Projection Co-variance Matrix
	 */

	public static final double[][] ProjectionCovariance (
		final double[][] aadblScopingCovariance,
		final double[][] aadblScopingLoading,
		final double dblTau)
	{
		if (null == aadblScopingCovariance || null == aadblScopingLoading ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblTau))
			return null;

		int iNumProjection = aadblScopingLoading.length;
		double[][] aadblProjectionCovariance = 0 == iNumProjection ? null : new
			double[iNumProjection][iNumProjection];

		if (0 == iNumProjection || iNumProjection != aadblScopingLoading.length) return null;

		for (int i = 0; i < iNumProjection; ++i) {
			for (int j = 0; j < iNumProjection; ++j) {
				try {
					aadblProjectionCovariance[i][j] = i != j ? 0. : dblTau *
						org.drip.numerical.linearalgebra.Matrix.DotProduct (aadblScopingLoading[i],
							org.drip.numerical.linearalgebra.Matrix.Product (aadblScopingCovariance,
								aadblScopingLoading[j]));
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		return aadblProjectionCovariance;
	}

	/**
	 * Generate the ProjectionDistributionLoading Instance from the Confidence Level
	 * 
	 * @param meta The R^1 Multivariate Meta Headers
	 * @param adblMean Array of the Univariate Means
	 * @param aadblScopingCovariance The Scoping Co-variance Matrix
	 * @param aadblScopingLoading The Projection-Scoping Variate Loadings
	 * @param dblTau The Tau Parameter
	 * 
	 * @return The ProjectionDistributionLoading Instance
	 */

	public static final ProjectionDistributionLoading FromConfidence (
		final org.drip.measure.continuous.MultivariateMeta meta,
		final double[] adblMean,
		final double[][] aadblScopingCovariance,
		final double[][] aadblScopingLoading,
		final double dblTau)
	{
		try {
			return new ProjectionDistributionLoading (new org.drip.measure.gaussian.R1MultivariateNormal
				(meta, adblMean, new org.drip.measure.gaussian.Covariance (ProjectionCovariance
					(aadblScopingCovariance, aadblScopingLoading, dblTau))), aadblScopingLoading);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ProjectionDistributionLoading Constructor
	 * 
	 * @param r1mDistribution The Projection Distribution Instance
	 * @param aadblScopingLoading The Projection-Scoping Variate Loadings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ProjectionDistributionLoading (
		final org.drip.measure.continuous.R1Multivariate r1mDistribution,
		final double[][] aadblScopingLoading)
		throws java.lang.Exception
	{
		if (null == (_r1mDistribution = r1mDistribution) || null == (_aadblScopingLoading =
			aadblScopingLoading))
			throw new java.lang.Exception ("ProjectionDistributionLoading Constructor => Invalid Inputs!");

		int iNumProjectionView = _r1mDistribution.meta().numVariable();

		if (iNumProjectionView != _aadblScopingLoading.length)
			throw new java.lang.Exception ("ProjectionDistributionLoading Constructor => Invalid Inputs!");

		for (int i = 0; i < iNumProjectionView; ++i) {
			if (null == _aadblScopingLoading[i] || 0 == _aadblScopingLoading[i].length ||
				!org.drip.numerical.common.NumberUtil.IsValid (_aadblScopingLoading[i]))
				throw new java.lang.Exception
					("ProjectionDistributionLoading Constructor => Invalid Inputs!");
		}
	}

	/**
	 * Retrieve the Projection Distribution
	 * 
	 * @return The Projection Distribution
	 */

	public org.drip.measure.continuous.R1Multivariate distribution()
	{
		return _r1mDistribution;
	}

	/**
	 * Retrieve the Matrix of the Scoping Loadings
	 * 
	 * @return The Matrix of the Scoping Loadings
	 */

	public double[][] scopingLoading()
	{
		return _aadblScopingLoading;
	}

	/**
	 * Retrieve the Number of the Projection Variates
	 * 
	 * @return The Number of the Projection Variates
	 */

	public int numberOfProjectionVariate()
	{
		return _aadblScopingLoading.length;
	}

	/**
	 * Retrieve the Number of the Scoping Variate
	 * 
	 * @return The Number of the Scoping Variate
	 */

	public int numberOfScopingVariate()
	{
		return _aadblScopingLoading[0].length;
	}
}
