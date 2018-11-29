
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
 * <i>ScopingProjectionVariateContainer</i> holds the Scoping Variate Distribution, the Projection Variate
 * Distributions, and the Projection Variate Loadings based off of the Scoping Variates.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/bayesian">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian">Bayesian</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ScopingProjectionVariateDistribution {
	private org.drip.measure.continuous.R1Multivariate _r1mScopingDistribution = null;

	private java.util.Map<java.lang.String, org.drip.measure.bayesian.ProjectionDistributionLoading> _mapPDL
		= new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.measure.bayesian.ProjectionDistributionLoading>();

	/**
	 * ScopingProjectionVariateDistribution Constructor
	 * 
	 * @param r1mScopingDistribution The Multivariate R^1 Scoping Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ScopingProjectionVariateDistribution (
		final org.drip.measure.continuous.R1Multivariate r1mScopingDistribution)
		throws java.lang.Exception
	{
		if (null == (_r1mScopingDistribution = r1mScopingDistribution))
			throw new java.lang.Exception
				("ScopingProjectionVariateDistribution Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Scoping Distribution
	 * 
	 * @return The Scoping Distribution
	 */

	public org.drip.measure.continuous.R1Multivariate scopingDistribution()
	{
		return _r1mScopingDistribution;
	}

	/**
	 * Generate Loadings Native to the Scoping Distribution
	 * 
	 * @return  The Matrix of Loadings Native to the Scoping Distribution
	 */

	public double[][] nativeLoading()
	{
		int iNumScopingVariate = _r1mScopingDistribution.meta().numVariable();

		double[][] aadblNativeLoading = new double[iNumScopingVariate][iNumScopingVariate];

		for (int i = 0; i < iNumScopingVariate; ++i) {
			for (int j = 0; j < iNumScopingVariate; ++j)
				aadblNativeLoading[i][j] = i == j ? 1. : 0.;
		}

		return aadblNativeLoading;
	}

	/**
	 * Add the Named Projection Distribution Loading
	 * 
	 * @param strName The Projection Distribution Name
	 * @param pdl The Projection Distribution Loading
	 * 
	 * @return TRUE - The Projection Distribution Loading successfully added
	 */

	public boolean addProjectionDistributionLoading (
		final java.lang.String strName,
		final org.drip.measure.bayesian.ProjectionDistributionLoading pdl)
	{
		if (null == strName || strName.isEmpty() || null == pdl) return false;

		_mapPDL.put (strName, pdl);

		return true;
	}

	/**
	 * Retrieve the Named Projection Distribution Loading
	 * 
	 * @param strName The Projection Distribution Name
	 * 
	 * @return The Projection Distribution Loading
	 */

	public org.drip.measure.bayesian.ProjectionDistributionLoading projectionDistributionLoading (
		final java.lang.String strName)
	{
		if (null == strName || strName.isEmpty()) return null;

		if (strName.equalsIgnoreCase ("NATIVE")) {
			try {
				return new org.drip.measure.bayesian.ProjectionDistributionLoading (_r1mScopingDistribution,
					nativeLoading());
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return _mapPDL.containsKey (strName) ? _mapPDL.get (strName) : null;
	}
}
