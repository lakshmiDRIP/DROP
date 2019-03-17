
package org.drip.portfolioconstruction.bayesian;

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
 * <i>ProjectionExposure</i> holds the Projection Exposure Loadings that Weight the Exposure to the
 * Projection Pick Portfolio. The Reference is:
 *  
 *  <br><br>
 *  	<ul>
 *  		<li>
 *  			He. G., and R. Litterman (1999): <i>The Intuition behind the Black-Litterman Model
 *  				Portfolios</i> <b>Goldman Sachs Asset Management</b>
 *  		<li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/bayesian">Bayesian</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ProjectionExposure {
	private double[] _adblIntraViewComponent = null;
	private double[] _adblInterViewComponent = null;
	private double[] _adblPriorViewComponent = null;
	private double[][] _aadblCompositeConfidenceCovariance = null;

	/**
	 * ProjectionExposure Constructor
	 * 
	 * @param adblIntraViewComponent Array of Per-View View-Specific Exposure Component
	 * @param adblInterViewComponent Array of Per-View Exposure Contribution from other Views
	 * @param adblPriorViewComponent Array of View-Specific Per-View Components
	 * @param aadblCompositeConfidenceCovariance Composite Confidence Co-variance Matrix
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ProjectionExposure (
		final double[] adblIntraViewComponent,
		final double[] adblInterViewComponent,
		final double[] adblPriorViewComponent,
		final double[][] aadblCompositeConfidenceCovariance)
		throws java.lang.Exception
	{
		if (null == (_adblIntraViewComponent = adblIntraViewComponent) || null == (_adblInterViewComponent =
			adblInterViewComponent) || null == (_adblPriorViewComponent = adblPriorViewComponent) || null ==
				(_aadblCompositeConfidenceCovariance = aadblCompositeConfidenceCovariance))
			throw new java.lang.Exception ("ProjectionExposure Constructor => Invalid Inputs");

		int iNumView = _adblIntraViewComponent.length;

		if (0 == iNumView || iNumView != _adblInterViewComponent.length || iNumView !=
			_adblPriorViewComponent.length || iNumView != _aadblCompositeConfidenceCovariance.length)
			throw new java.lang.Exception ("ProjectionExposure Constructor => Invalid Inputs");

		for (int i = 0; i < iNumView; ++i) {
			if (!org.drip.numerical.common.NumberUtil.IsValid (_adblIntraViewComponent[i]) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_adblInterViewComponent[i]) ||
					!org.drip.numerical.common.NumberUtil.IsValid (_adblPriorViewComponent[i]) || null ==
						_aadblCompositeConfidenceCovariance[i] || iNumView !=
							_aadblCompositeConfidenceCovariance[i].length)
				throw new java.lang.Exception ("ProjectionExposure Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Single View Joint Contribution Component
	 * 
	 * @return The Single View Joint Contribution Component
	 */

	public double[] intraViewComponent()
	{
		return _adblIntraViewComponent;
	}

	/**
	 * Retrieve the View/View Joint Contribution Component
	 * 
	 * @return The View/View Joint Contribution Component
	 */

	public double[] interViewComponent()
	{
		return _adblInterViewComponent;
	}

	/**
	 * Retrieve the Prior/View Joint Contribution Component
	 * 
	 * @return The Prior/View Joint Contribution Component
	 */

	public double[] priorViewComponent()
	{
		return _adblPriorViewComponent;
	}

	/**
	 * Retrieve the Composite Confidence Co-variance
	 * 
	 * @return The Composite Confidence Co-variance
	 */

	public double[][] compositeConfidenceCovariance()
	{
		return _aadblCompositeConfidenceCovariance;
	}

	/**
	 * Compute the Array of Cumulative View Loading Components
	 * 
	 * @return The Array of Cumulative View Loading Components
	 */

	public double[] cumulativeViewComponent()
	{
		int iNumView = _adblIntraViewComponent.length;
		double[] adblViewLoading = new double[iNumView];

		for (int i = 0; i < iNumView; ++i)
			adblViewLoading[i] = _adblIntraViewComponent[i] + _adblInterViewComponent[i] +
				_adblPriorViewComponent[i];

		return adblViewLoading;
	}
}
