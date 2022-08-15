
package org.drip.simm.parameters;

import java.util.List;

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
 * <i>RiskClassSensitivitySettingsIR</i> holds the Settings that govern the Generation of the ISDA SIMM
 * Bucket Sensitivities across Individual IR Risk Class Buckets. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/README.md">ISDA SIMM Risk Factor Parameters</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RiskClassSensitivitySettingsIR
{
	private RiskMeasureSensitivitySettingsIR _vega = null;
	private RiskMeasureSensitivitySettingsIR _delta = null;
	private RiskMeasureSensitivitySettingsIR _curvature = null;

	/**
	 * Generate the ISDA 2.0 Standard Commodity Sensitivity Settings
	 * 
	 * @param currencyList The Currency List
	 * 
	 * @return The ISDA 2.0 Standard Commodity Sensitivity Settings
	 */

	public static final RiskClassSensitivitySettingsIR ISDA_20 (
		final List<String> currencyList)
	{
		try
		{
			return new RiskClassSensitivitySettingsIR (
				RiskMeasureSensitivitySettingsIR.ISDA_DELTA_20 (
					currencyList
				),
				RiskMeasureSensitivitySettingsIR.ISDA_VEGA_20 (
					currencyList
				),
				RiskMeasureSensitivitySettingsIR.ISDA_CURVATURE_20 (
					currencyList
				)
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the ISDA 2.1 Standard Commodity Sensitivity Settings
	 * 
	 * @param currencyList The Currency List
	 * 
	 * @return The ISDA 2.1 Standard Commodity Sensitivity Settings
	 */

	public static final RiskClassSensitivitySettingsIR ISDA_21 (
		final List<String> currencyList)
	{
		try
		{
			return new RiskClassSensitivitySettingsIR (
				RiskMeasureSensitivitySettingsIR.ISDA_DELTA_21 (
					currencyList
				),
				RiskMeasureSensitivitySettingsIR.ISDA_VEGA_21 (
					currencyList
				),
				RiskMeasureSensitivitySettingsIR.ISDA_CURVATURE_21 (
					currencyList
				)
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RiskClassSensitivitySettingsIR Constructor
	 * 
	 * @param delta The IR Risk Class Delta Sensitivity Settings
	 * @param vega The IR Risk Class Vega Sensitivity Settings
	 * @param curvature Curvature Risk Measure Sensitivity Settings
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RiskClassSensitivitySettingsIR (
		final RiskMeasureSensitivitySettingsIR delta,
		final RiskMeasureSensitivitySettingsIR vega,
		final RiskMeasureSensitivitySettingsIR curvature)
		throws Exception
	{
		if (null == (_delta = delta) ||
			null == (_vega = vega) ||
			null == (_curvature = curvature))
		{
			throw new Exception (
				"RiskClassSensitivitySettingsIR Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the IR Risk Class Delta Sensitivity Settings
	 * 
	 * @return The IR Risk Class Delta Sensitivity Settings
	 */

	public RiskMeasureSensitivitySettingsIR delta()
	{
		return _delta;
	}

	/**
	 * Retrieve the IR Risk Class Vega Sensitivity Settings
	 * 
	 * @return The IR Risk Class Vega Sensitivity Settings
	 */

	public RiskMeasureSensitivitySettingsIR vega()
	{
		return _vega;
	}

	/**
	 * Curvature IR Risk Measure Sensitivity Settings
	 * 
	 * @return IR Curvature Risk Measure Sensitivity Settings
	 */

	public RiskMeasureSensitivitySettingsIR curvature()
	{
		return _curvature;
	}
}
