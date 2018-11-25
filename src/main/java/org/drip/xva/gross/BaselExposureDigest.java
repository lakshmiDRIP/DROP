
package org.drip.xva.gross;

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
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva">XVA</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/gross">Gross</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/XVA">XVA Analytics Library</a></li>
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
		if (!org.drip.quant.common.NumberUtil.IsValid (_expectedExposure = expectedExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (_expectedPositiveExposure = expectedPositiveExposure)
				||
			!org.drip.quant.common.NumberUtil.IsValid (_effectiveExpectedExposure =
				effectiveExpectedExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (_effectiveExpectedPositiveExposure =
				effectiveExpectedPositiveExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (_exposureAtDefault = exposureAtDefault))
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
