
package org.drip.simm.margin;

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
 * <i>BucketAggregate</i> holds the Single Bucket Sensitivity Margin, the Cumulative Bucket Risk Factor
 * Sensitivity Margin, as well as the Aggregate Risk Factor Maps. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  			Calculations https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  			Framework for Forecasting Initial Margin Requirements
 *  				https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin
 *  			Requirements - A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167
 *  				<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology
 *  			https://www.isda.org/a/oFiDE/isda-simm-v2.pdf
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm">SIMM</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin">Margin</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BucketAggregate
{
	private double _sensitivityMarginVariance = java.lang.Double.NaN;
	private double _cumulativeSensitivityMargin = java.lang.Double.NaN;
	private java.util.Map<java.lang.String, org.drip.simm.margin.RiskFactorAggregate>
		_riskFactorAggregateMap = null;

	/**
	 * BucketAggregate Constructor
	 * 
	 * @param riskFactorAggregateMap The Risk Factor Aggregate Map
	 * @param sensitivityMarginVariance The Bucket's Sensitivity Margin Variance
	 * @param cumulativeSensitivityMargin The Cumulative Risk Factor Sensitivity Margin
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketAggregate (
		final java.util.Map<java.lang.String, org.drip.simm.margin.RiskFactorAggregate>
			riskFactorAggregateMap,
		final double sensitivityMarginVariance,
		final double cumulativeSensitivityMargin)
		throws java.lang.Exception
	{
		if (null == (_riskFactorAggregateMap = riskFactorAggregateMap) || 0 == _riskFactorAggregateMap.size()
			|| !org.drip.numerical.common.NumberUtil.IsValid (_sensitivityMarginVariance =
				sensitivityMarginVariance) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_cumulativeSensitivityMargin =
				cumulativeSensitivityMargin))
		{
			throw new java.lang.Exception ("BucketAggregate Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Risk Factor Aggregate Map
	 * 
	 * @return The Risk Factor Aggregate Map
	 */

	public java.util.Map<java.lang.String, org.drip.simm.margin.RiskFactorAggregate> riskFactorAggregateMap()
	{
		return _riskFactorAggregateMap;
	}

	/**
	 * Retrieve the Bucket's Sensitivity Margin Variance
	 * 
	 * @return The Bucket's Sensitivity Margin Variance
	 */

	public double sensitivityMarginVariance()
	{
		return _sensitivityMarginVariance;
	}

	/**
	 * Retrieve the Bucket's Cumulative Risk Factor Sensitivity Margin
	 * 
	 * @return The Bucket's Cumulative Risk Factor Sensitivity Margin
	 */

	public double cumulativeSensitivityMargin()
	{
		return _cumulativeSensitivityMargin;
	}

	/**
	 * Compute the ISDA SIMM Position Principal Component Co-variance
	 * 
	 * @return The ISDA SIMM Position Principal Component Co-variance
	 */

	public double positionPrincipalComponentCovarianceISDA()
	{
		double sensitivityMargin = java.lang.Math.sqrt (_sensitivityMarginVariance);

		return java.lang.Math.min (
			java.lang.Math.max (
				_cumulativeSensitivityMargin,
				-1. * sensitivityMargin
			),
			sensitivityMargin
		);
	}

	/**
	 * Compute the FRTB SBA-C Position Principal Component Co-variance
	 * 
	 * @return The FRTB SBA-C Position Principal Component Co-variance
	 */

	public double positionPrincipalComponentCovarianceFRTB()
	{
		return _cumulativeSensitivityMargin;
	}
}
