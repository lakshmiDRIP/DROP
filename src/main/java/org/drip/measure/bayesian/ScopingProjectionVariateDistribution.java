
package org.drip.measure.bayesian;

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
 * <i>ScopingProjectionVariateDistribution</i> holds the Scoping Variate Distribution, the Projection Variate
 * Distributions, and the Projection Variate Loadings based off of the Scoping Variates.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/README.md">Prior, Conditional, Posterior Theil Bayesian</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ScopingProjectionVariateDistribution {
	private org.drip.measure.continuous.MetaRdDistribution _r1mScopingDistribution = null;

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
		final org.drip.measure.continuous.MetaRdDistribution r1mScopingDistribution)
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

	public org.drip.measure.continuous.MetaRdDistribution scopingDistribution()
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
