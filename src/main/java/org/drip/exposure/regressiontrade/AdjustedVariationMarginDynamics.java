
package org.drip.exposure.regressiontrade;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>AdjustedVariationMarginDynamics</i> builds the Dynamics of the Sparse Path Adjusted Variation Margin.
 * The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of
 *  				Initial Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and
 *  				the Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955
 *  				<b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>
 *  		</li>
 *  		<li>
 *  			Pykhtin, M. (2009): Modeling Counter-party Credit Exposure in the Presence of Margin
 *  				Agreements http://www.risk-europe.com/protected/michael-pykhtin.pdf
 *  		</li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure">Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/regressiontrade">Regression Trade</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AdjustedVariationMarginDynamics
{
	private org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimate[]
		_pathAdjustedVariationMarginEstimate = null;

	/**
	 * AdjustedVariationMarginDynamics Constructor
	 * 
	 * @param pathAdjustedVariationMarginEstimate The Path-wise Adjusted Variation Margin Estimate Array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AdjustedVariationMarginDynamics (
		final org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimate[]
			pathAdjustedVariationMarginEstimate)
		throws java.lang.Exception
	{
		if (null == (_pathAdjustedVariationMarginEstimate = pathAdjustedVariationMarginEstimate))
		{
			throw new java.lang.Exception ("AdjustedVariationMarginDynamics Constructor");
		}

		int pathCount = _pathAdjustedVariationMarginEstimate.length;

		if (0 == pathCount)
		{
			throw new java.lang.Exception ("AdjustedVariationMarginDynamics Constructor");
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			if (null == _pathAdjustedVariationMarginEstimate[pathIndex])
			{
				throw new java.lang.Exception ("AdjustedVariationMarginDynamics Constructor");
			}
		}
	}

	/**
	 * Retrieve the Adjusted Variation Margin Estimate Array
	 * 
	 * @return The Adjusted Variation Margin Estimate Array
	 */

	public org.drip.exposure.regressiontrade.AdjustedVariationMarginEstimate[]
		adjustedVariationMarginEstimateArray()
	{
		return _pathAdjustedVariationMarginEstimate;
	}

	/**
	 * Generate the Dynamics of the Sparse Pillar a.k.a Pykhtin (2009)
	 * 
	 * @return The Pykhtin Pillar Dynamics Array
	 */

	public org.drip.exposure.regression.PykhtinPillarDynamics[] pillarDynamics()
	{
		int exposureDateCount =
			_pathAdjustedVariationMarginEstimate[0].adjustedVariationMarginEstimateArray().length;

		int pathCount = _pathAdjustedVariationMarginEstimate.length;
		double[][] pathAdjustedVariationMargin = new double[exposureDateCount][pathCount];
		org.drip.exposure.regression.PykhtinPillarDynamics[] pykhtinPillarDynamicsArray = new
			org.drip.exposure.regression.PykhtinPillarDynamics[exposureDateCount];

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			double[] pathAdjustedVariationMarginEstimateArray =
				_pathAdjustedVariationMarginEstimate[pathIndex].adjustedVariationMarginEstimateArray();

			for (int exposureDateIndex = 0; exposureDateIndex < exposureDateCount; ++exposureDateIndex)
			{
				pathAdjustedVariationMargin[exposureDateIndex][pathIndex] =
					pathAdjustedVariationMarginEstimateArray[exposureDateIndex];
			}
		}

		for (int exposureDateIndex = 0; exposureDateIndex < exposureDateCount; ++exposureDateIndex)
		{
			if (null == (pykhtinPillarDynamicsArray[exposureDateIndex] =
				org.drip.exposure.regression.PykhtinPillarDynamics.Standard
					(pathAdjustedVariationMargin[exposureDateIndex])))
			{
				return null;
			}
		}

		return pykhtinPillarDynamicsArray;
	}
}
