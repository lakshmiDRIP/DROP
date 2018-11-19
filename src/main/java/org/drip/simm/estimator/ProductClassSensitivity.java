
package org.drip.simm.estimator;

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
 * <i>ProductClassSensitivity</i> holds the multiple Risk Class Sensitivities for a single Product Class. The
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
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/estimator">Estimator</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ProductClassSensitivity
{
	private org.drip.simm.product.RiskClassSensitivity _fxRiskClassSensitivity = null;
	private org.drip.simm.product.RiskClassSensitivityIR _irRiskClassSensitivity = null;
	private org.drip.simm.product.RiskClassSensitivity _equityRiskClassSensitivity = null;
	private org.drip.simm.product.RiskClassSensitivity _commodityRiskClassSensitivity = null;
	private org.drip.simm.product.RiskClassSensitivityCR _creditQualifyingRiskClassSensitivity = null;
	private org.drip.simm.product.RiskClassSensitivityCR _creditNonQualifyingRiskClassSensitivity = null;

	/**
	 * ProductClassSensitivity Constructor
	 * 
	 * @param equityRiskClassSensitivity Equity Risk Class Sensitivity
	 * @param commodityRiskClassSensitivity Commodity Risk Class Sensitivity
	 * @param fxRiskClassSensitivity FX Risk Class Sensitivity
	 * @param irRiskClassSensitivity IR Risk Class Sensitivity
	 * @param creditQualifyingRiskClassSensitivity Credit Qualifying Risk Class Sensitivity
	 * @param creditNonQualifyingRiskClassSensitivity Credit Non-Qualifying Risk Class Sensitivity
	 * 
	 * @throws java.lang.Exception Thrownm if the Inputs are Invalid
	 */

	public ProductClassSensitivity (
		final org.drip.simm.product.RiskClassSensitivity equityRiskClassSensitivity,
		final org.drip.simm.product.RiskClassSensitivity commodityRiskClassSensitivity,
		final org.drip.simm.product.RiskClassSensitivity fxRiskClassSensitivity,
		final org.drip.simm.product.RiskClassSensitivityIR irRiskClassSensitivity,
		final org.drip.simm.product.RiskClassSensitivityCR creditQualifyingRiskClassSensitivity,
		final org.drip.simm.product.RiskClassSensitivityCR creditNonQualifyingRiskClassSensitivity)
		throws java.lang.Exception
	{
		_fxRiskClassSensitivity = fxRiskClassSensitivity;
		_irRiskClassSensitivity = irRiskClassSensitivity;
		_equityRiskClassSensitivity = equityRiskClassSensitivity;
		_commodityRiskClassSensitivity = commodityRiskClassSensitivity;
		_creditQualifyingRiskClassSensitivity = creditQualifyingRiskClassSensitivity;
		_creditNonQualifyingRiskClassSensitivity = creditNonQualifyingRiskClassSensitivity;

		if (null == _equityRiskClassSensitivity &&
			null == _commodityRiskClassSensitivity &&
			null == _fxRiskClassSensitivity &&
			null == _irRiskClassSensitivity &&
			null == _creditQualifyingRiskClassSensitivity &&
			null == _creditNonQualifyingRiskClassSensitivity)
		{
			throw new java.lang.Exception ("ProductClassSensitivity Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Equity Risk Class Sensitivity
	 * 
	 * @return The Equity Risk Class Sensitivity
	 */

	public org.drip.simm.product.RiskClassSensitivity equityRiskClassSensitivity()
	{
		return _equityRiskClassSensitivity;
	}

	/**
	 * Retrieve the Commodity Risk Class Sensitivity
	 * 
	 * @return The Commodity Risk Class Sensitivity
	 */

	public org.drip.simm.product.RiskClassSensitivity commodityRiskClassSensitivity()
	{
		return _commodityRiskClassSensitivity;
	}

	/**
	 * Retrieve the FX Risk Class Sensitivity
	 * 
	 * @return The FX Risk Class Sensitivity
	 */

	public org.drip.simm.product.RiskClassSensitivity fxRiskClassSensitivity()
	{
		return _fxRiskClassSensitivity;
	}

	/**
	 * Retrieve the IR Risk Class Sensitivity
	 * 
	 * @return The IR Risk Class Sensitivity
	 */

	public org.drip.simm.product.RiskClassSensitivityIR irRiskClassSensitivity()
	{
		return _irRiskClassSensitivity;
	}

	/**
	 * Retrieve the Credit Qualifying Risk Class Sensitivity
	 * 
	 * @return The Credit Qualifying Risk Class Sensitivity
	 */

	public org.drip.simm.product.RiskClassSensitivityCR creditQualifyingRiskClassSensitivity()
	{
		return _creditQualifyingRiskClassSensitivity;
	}

	/**
	 * Retrieve the Credit Non-Qualifying Risk Class Sensitivity
	 * 
	 * @return The Credit Non-Qualifying Risk Class Sensitivity
	 */

	public org.drip.simm.product.RiskClassSensitivityCR creditNonQualifyingRiskClassSensitivity()
	{
		return _creditNonQualifyingRiskClassSensitivity;
	}

	/**
	 * Generate the Margin for the Product Class
	 * 
	 * @param productClassSettings The Product Class Settings
	 * @param marginEstimationSettings Margin Estimation Settings
	 * 
	 * @return The Margin for the Product Class
	 */

	public org.drip.simm.estimator.ProductClassMargin estimate (
		final org.drip.simm.estimator.ProductClassSettings productClassSettings,
		final org.drip.simm.foundation.MarginEstimationSettings marginEstimationSettings)
	{
		if (null == productClassSettings)
		{
			return null;
		}

		try
		{
			return new ProductClassMargin (
				null == _irRiskClassSensitivity ? null : _irRiskClassSensitivity.aggregate (
					productClassSettings.irRiskClassSensitivitySettings(),
					marginEstimationSettings
				),
				null == _creditQualifyingRiskClassSensitivity ? null :
					_creditQualifyingRiskClassSensitivity.aggregate (
						productClassSettings.creditQualifyingRiskClassSensitivitySettings(),
						marginEstimationSettings
					),
				null == _creditNonQualifyingRiskClassSensitivity ? null :
					_creditNonQualifyingRiskClassSensitivity.aggregate (
						productClassSettings.creditNonQualifyingRiskClassSensitivitySettings(),
						marginEstimationSettings
					),
				null == _equityRiskClassSensitivity ? null : _equityRiskClassSensitivity.aggregate (
					productClassSettings.equityRiskClassSensitivitySettings(),
					marginEstimationSettings
				),
				null == _fxRiskClassSensitivity ? null : _fxRiskClassSensitivity.aggregate (
					productClassSettings.fxRiskClassSensitivitySettings(),
					marginEstimationSettings
				),
				null == _commodityRiskClassSensitivity ? null : _commodityRiskClassSensitivity.aggregate (
					productClassSettings.commodityRiskClassSensitivitySettings(),
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
