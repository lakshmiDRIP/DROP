
package org.drip.simm.product;

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
 * <i>RiskClassSensitivityCR</i> holds the Risk Class Bucket Sensitivities for a single CR Class. The
 * References are:
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
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm">SIMM</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/product">Product</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RiskClassSensitivityCR
{
	private org.drip.simm.product.RiskMeasureSensitivityCR _vega = null;
	private org.drip.simm.product.RiskMeasureSensitivityCR _delta = null;
	private org.drip.simm.product.RiskMeasureSensitivityCR _curvature = null;

	/**
	 * RiskClassSensitivityCR Constructor
	 * 
	 * @param delta The CR Delta Tenor Sensitivity
	 * @param vega The CR Vega Tenor Sensitivity
	 * @param curvature The CR Curvature Tenor Sensitivity
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskClassSensitivityCR (
		final org.drip.simm.product.RiskMeasureSensitivityCR delta,
		final org.drip.simm.product.RiskMeasureSensitivityCR vega,
		final org.drip.simm.product.RiskMeasureSensitivityCR curvature)
		throws java.lang.Exception
	{
		if (null == (_delta = delta) ||
			null == (_vega = vega) ||
			null == (_curvature = curvature))
		{
			throw new java.lang.Exception ("RiskClassSensitivityCR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the CR Delta Risk Measure Sensitivity
	 * 
	 * @return The CR Delta Risk Measure Sensitivity
	 */

	public org.drip.simm.product.RiskMeasureSensitivityCR delta()
	{
		return _delta;
	}

	/**
	 * Retrieve the CR Vega Risk Measure Sensitivity
	 * 
	 * @return The CR Vega Risk Measure Sensitivity
	 */

	public org.drip.simm.product.RiskMeasureSensitivityCR vega()
	{
		return _vega;
	}

	/**
	 * Retrieve the CR Curvature Risk Measure Sensitivity
	 * 
	 * @return The CR Curvature Risk Measure Sensitivity
	 */

	public org.drip.simm.product.RiskMeasureSensitivityCR curvature()
	{
		return _curvature;
	}

	/**
	 * Compute the Risk Class Sensitivity Aggregate
	 * 
	 * @param riskClassSensitivitySettingsCR The CR Risk Class Sensitivity Settings
	 * @param marginEstimationSettings Margin Estimation Settings
	 * 
	 * @return The Risk Class Sensitivity Aggregate
	 */

	public org.drip.simm.margin.RiskClassAggregateCR aggregate (
		final org.drip.simm.parameters.RiskClassSensitivitySettingsCR riskClassSensitivitySettingsCR,
		final org.drip.simm.foundation.MarginEstimationSettings marginEstimationSettings)
	{
		if (null == riskClassSensitivitySettingsCR)
		{
			return null;
		}

		try
		{
			return new org.drip.simm.margin.RiskClassAggregateCR (
				_delta.linearAggregate (
					riskClassSensitivitySettingsCR.delta(),
					marginEstimationSettings
				),
				_vega.linearAggregate (
					riskClassSensitivitySettingsCR.vega(),
					marginEstimationSettings
				),
				_curvature.curvatureAggregate (
					riskClassSensitivitySettingsCR.curvature(),
					marginEstimationSettings
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
