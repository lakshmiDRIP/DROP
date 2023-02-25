
package org.drip.xva.gross;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>BaselExposureDigest</i> holds the Conservative Exposure Measures generated using the Standardized Basel
 * Approach. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K Loukopoulus (2017): A Sound Modeling and Back testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		BCBS (2015): <i>Margin Requirements for Non-centrally Cleared Derivatives</i>
 *  			https://www.bis.org/bcbs/publ/d317.pdf
 *  	</li>
 *  	<li>
 *  		Pykhtin, M. (2009): Modeling Credit Exposure for Collateralized Counter-parties <i>Journal of
 *  			Credit Risk</i> <b>5 (4)</b> 3-27
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md">Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/gross/README.md">XVA Gross Adiabat Exposure Aggregation</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BaselExposureDigest
{
	private double _expectedExposure = java.lang.Double.NaN;
	private double _exposureAtDefault = java.lang.Double.NaN;
	private double _expectedPositiveExposure = java.lang.Double.NaN;
	private double _effectiveExpectedExposure = java.lang.Double.NaN;
	private double _effectiveExpectedPositiveExposure = java.lang.Double.NaN;

	/**
	 * BaselExposureDigest Constructor
	 * 
	 * @param expectedExposure The Expected Exposure
	 * @param expectedPositiveExposure The Expected Positive Exposure
	 * @param effectiveExpectedExposure The Effective Expected Exposure
	 * @param effectiveExpectedPositiveExposure The Effective Expected Positive Exposure
	 * @param exposureAtDefault The Exposure At Default
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BaselExposureDigest (
		final double expectedExposure,
		final double expectedPositiveExposure,
		final double effectiveExpectedExposure,
		final double effectiveExpectedPositiveExposure,
		final double exposureAtDefault)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_expectedExposure = expectedExposure) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_expectedPositiveExposure = expectedPositiveExposure)
				||
			!org.drip.numerical.common.NumberUtil.IsValid (_effectiveExpectedExposure =
				effectiveExpectedExposure) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_effectiveExpectedPositiveExposure =
				effectiveExpectedPositiveExposure) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_exposureAtDefault = exposureAtDefault))
		{
			throw new java.lang.Exception ("BaselExposureDigest Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Expected Exposure
	 * 
	 * @return The Expected Exposure
	 */

	public double expectedExposure()
	{
		return _expectedExposure;
	}

	/**
	 * Retrieve the Expected Positive Exposure
	 * 
	 * @return The Expected Positive Exposure
	 */

	public double expectedPositiveExposure()
	{
		return _expectedPositiveExposure;
	}

	/**
	 * Retrieve the Effective Expected Exposure
	 * 
	 * @return The Effective Expected Exposure
	 */

	public double effectiveExpectedExposure()
	{
		return _effectiveExpectedExposure;
	}

	/**
	 * Retrieve the Effective Expected Positive Exposure
	 * 
	 * @return The Effective Expected Positive Exposure
	 */

	public double effectiveExpectedPositiveExposure()
	{
		return _effectiveExpectedPositiveExposure;
	}

	/**
	 * Retrieve the Exposure At Default
	 * 
	 * @return The Exposure At Default
	 */

	public double exposureAtDefault()
	{
		return _exposureAtDefault;
	}
}
