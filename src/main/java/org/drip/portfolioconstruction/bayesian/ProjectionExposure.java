
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

public class ProjectionExposure
{
	private double[] _interViewComponentArray = null;
	private double[] _intraViewComponentArray = null;
	private double[] _priorViewComponentArray = null;
	private double[][] _compositeConfidenceCovarianceMatrix = null;

	/**
	 * ProjectionExposure Constructor
	 * 
	 * @param intraViewComponentArray Array of Per-View View-Specific Exposure Component Array
	 * @param interViewComponentArray Array of Per-View Exposure Contribution Array from other Views
	 * @param priorViewComponentArray Array of View-Specific Per-View Components Array
	 * @param compositeConfidenceCovarianceMatrix Composite Confidence Co-variance Matrix
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ProjectionExposure (
		final double[] intraViewComponentArray,
		final double[] interViewComponentArray,
		final double[] priorViewComponentArray,
		final double[][] compositeConfidenceCovarianceMatrix)
		throws java.lang.Exception
	{
		if (null == (_intraViewComponentArray = intraViewComponentArray) ||
			null == (_interViewComponentArray = interViewComponentArray) ||
			null == (_priorViewComponentArray = priorViewComponentArray) ||
			null == (_compositeConfidenceCovarianceMatrix = compositeConfidenceCovarianceMatrix))
		{
			throw new java.lang.Exception ("ProjectionExposure Constructor => Invalid Inputs");
		}

		int viewCount = _intraViewComponentArray.length;

		if (0 == viewCount ||
			viewCount != _interViewComponentArray.length ||
			viewCount != _priorViewComponentArray.length ||
			viewCount != _compositeConfidenceCovarianceMatrix.length)
		{
			throw new java.lang.Exception ("ProjectionExposure Constructor => Invalid Inputs");
		}

		for (int viewIndex = 0; viewIndex < viewCount; ++viewIndex)
		{
			if (!org.drip.numerical.common.NumberUtil.IsValid (_intraViewComponentArray[viewIndex]) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_interViewComponentArray[viewIndex]) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_priorViewComponentArray[viewIndex]) ||
				null == _compositeConfidenceCovarianceMatrix[viewIndex] ||
				viewCount != _compositeConfidenceCovarianceMatrix[viewIndex].length)
			{
				throw new java.lang.Exception ("ProjectionExposure Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Single View Joint Contribution Component Array
	 * 
	 * @return The Single View Joint Contribution Component Array
	 */

	public double[] intraViewComponentArray()
	{
		return _intraViewComponentArray;
	}

	/**
	 * Retrieve the View/View Joint Contribution Component Array
	 * 
	 * @return The View/View Joint Contribution Component Array
	 */

	public double[] interViewComponentArray()
	{
		return _interViewComponentArray;
	}

	/**
	 * Retrieve the Prior/View Joint Contribution Component Array
	 * 
	 * @return The Prior/View Joint Contribution Component Array
	 */

	public double[] priorViewComponentArray()
	{
		return _priorViewComponentArray;
	}

	/**
	 * Retrieve the Composite Confidence Co-variance Matrix
	 * 
	 * @return The Composite Confidence Co-variance Matrix
	 */

	public double[][] compositeConfidenceCovarianceMatrix()
	{
		return _compositeConfidenceCovarianceMatrix;
	}

	/**
	 * Compute the Array of Cumulative View Loading Component Array
	 * 
	 * @return The Array of Cumulative View Loading Component Array
	 */

	public double[] cumulativeViewComponentLoadingArray()
	{
		int viewCount = _intraViewComponentArray.length;
		double[] cumulativeViewComponentLoadingArray = new double[viewCount];

		for (int viewIndex = 0; viewIndex < viewCount; ++viewIndex)
		{
			cumulativeViewComponentLoadingArray[viewIndex] = _intraViewComponentArray[viewIndex] +
				_interViewComponentArray[viewIndex] + _priorViewComponentArray[viewIndex];
		}

		return cumulativeViewComponentLoadingArray;
	}
}
