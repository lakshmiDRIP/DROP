
package org.drip.simm.parameters;

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
 * <i>RiskClassSensitivitySettings</i> holds the Settings that govern the Generation of the ISDA SIMM Bucket
 * Sensitivities across Individual Risk Class Buckets. The References are:
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
 *  		International Swaps and Derivatives Association (2021): SIMM v2.4 Methodology
 *  			https://www.isda.org/a/CeggE/ISDA-SIMM-v2.4-PUBLIC.pdf
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

public class RiskClassSensitivitySettings
{
	private RiskMeasureSensitivitySettings _vega = null;
	private RiskMeasureSensitivitySettings _delta = null;
	private RiskMeasureSensitivitySettings _curvature = null;

	/**
	 * Generate the ISDA 2.0 Standard Commodity Sensitivity Settings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return The ISDA 2.0 Standard Commodity Sensitivity Settings
	 */

	public static final RiskClassSensitivitySettings ISDA_EQ_20 (
		final int vegaDurationDays)
	{
		try
		{
			return new RiskClassSensitivitySettings (
				RiskMeasureSensitivitySettings.ISDA_EQ_DELTA_20(),
				RiskMeasureSensitivitySettings.ISDA_EQ_VEGA_20(),
				RiskMeasureSensitivitySettings.ISDA_EQ_CURVATURE_20 (
					vegaDurationDays
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
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return The ISDA 2.1 Standard Commodity Sensitivity Settings
	 */

	public static final RiskClassSensitivitySettings ISDA_EQ_21 (
		final int vegaDurationDays)
	{
		try
		{
			return new RiskClassSensitivitySettings (
				RiskMeasureSensitivitySettings.ISDA_EQ_DELTA_21(),
				RiskMeasureSensitivitySettings.ISDA_EQ_VEGA_21(),
				RiskMeasureSensitivitySettings.ISDA_EQ_CURVATURE_21 (
					vegaDurationDays
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
	 * Generate the ISDA 2.4 Standard Commodity Sensitivity Settings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return The ISDA 2.4 Standard Commodity Sensitivity Settings
	 */

	public static final RiskClassSensitivitySettings ISDA_EQ_24 (
		final int vegaDurationDays)
	{
		try
		{
			return new RiskClassSensitivitySettings (
				RiskMeasureSensitivitySettings.ISDA_EQ_DELTA_24(),
				RiskMeasureSensitivitySettings.ISDA_EQ_VEGA_24(),
				RiskMeasureSensitivitySettings.ISDA_EQ_CURVATURE_24 (
					vegaDurationDays
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
	 * Generate the ISDA 2.0 Standard Commodity Sensitivity Settings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return The ISDA 2.0 Standard Commodity Sensitivity Settings
	 */

	public static final RiskClassSensitivitySettings ISDA_CT_20 (
		final int vegaDurationDays)
	{
		try
		{
			return new RiskClassSensitivitySettings (
				RiskMeasureSensitivitySettings.ISDA_CT_DELTA_20(),
				RiskMeasureSensitivitySettings.ISDA_CT_VEGA_20(),
				RiskMeasureSensitivitySettings.ISDA_CT_CURVATURE_20 (
					vegaDurationDays
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
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return The ISDA 2.1 Standard Commodity Sensitivity Settings
	 */

	public static final RiskClassSensitivitySettings ISDA_CT_21 (
		final int vegaDurationDays)
	{
		try
		{
			return new RiskClassSensitivitySettings (
				RiskMeasureSensitivitySettings.ISDA_CT_DELTA_21(),
				RiskMeasureSensitivitySettings.ISDA_CT_VEGA_21(),
				RiskMeasureSensitivitySettings.ISDA_CT_CURVATURE_21 (
					vegaDurationDays
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
	 * Generate the ISDA 2.4 Standard Commodity Sensitivity Settings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return The ISDA 2.4 Standard Commodity Sensitivity Settings
	 */

	public static final RiskClassSensitivitySettings ISDA_CT_24 (
		final int vegaDurationDays)
	{
		try
		{
			return new RiskClassSensitivitySettings (
				RiskMeasureSensitivitySettings.ISDA_CT_DELTA_24(),
				RiskMeasureSensitivitySettings.ISDA_CT_VEGA_24(),
				RiskMeasureSensitivitySettings.ISDA_CT_CURVATURE_24 (
					vegaDurationDays
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
	 * Generate the ISDA 2.0 Standard FX Sensitivity Settings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return The ISDA 2.0 Standard FX Sensitivity Settings
	 */

	public static final RiskClassSensitivitySettings ISDA_FX_20 (
		final int vegaDurationDays)
	{
		try
		{
			return new RiskClassSensitivitySettings (
				RiskMeasureSensitivitySettings.ISDA_FX_DELTA_20(),
				RiskMeasureSensitivitySettings.ISDA_FX_VEGA_20(),
				RiskMeasureSensitivitySettings.ISDA_FX_CURVATURE_20 (
					vegaDurationDays
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
	 * Generate the ISDA 2.1 Standard FX Sensitivity Settings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * 
	 * @return The ISDA 2.1 Standard FX Sensitivity Settings
	 */

	public static final RiskClassSensitivitySettings ISDA_FX_21 (
		final int vegaDurationDays)
	{
		try
		{
			return new RiskClassSensitivitySettings (
				RiskMeasureSensitivitySettings.ISDA_FX_DELTA_21(),
				RiskMeasureSensitivitySettings.ISDA_FX_VEGA_21(),
				RiskMeasureSensitivitySettings.ISDA_FX_CURVATURE_21 (
					vegaDurationDays
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
	 * Generate the ISDA 2.4 Standard FX Sensitivity Settings
	 * 
	 * @param vegaDurationDays The Vega Duration Days
	 * @param givenCurrency Given Currency
	 * @param calculationCurrency Calculation Currency
	 * 
	 * @return The ISDA 2.4 Standard FX Sensitivity Settings
	 */

	public static final RiskClassSensitivitySettings ISDA_FX_24 (
		final int vegaDurationDays,
		final String givenCurrency,
		final String calculationCurrency)
	{
		try
		{
			return new RiskClassSensitivitySettings (
				RiskMeasureSensitivitySettings.ISDA_FX_DELTA_24 (
					givenCurrency,
					calculationCurrency
				),
				RiskMeasureSensitivitySettings.ISDA_FX_VEGA_24 (
					givenCurrency,
					calculationCurrency
				),
				RiskMeasureSensitivitySettings.ISDA_FX_CURVATURE_24 (
					vegaDurationDays,
					givenCurrency,
					calculationCurrency
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
	 * RiskClassSensitivitySettings Constructor
	 * 
	 * @param delta Delta Risk Measure Sensitivity Settings
	 * @param vega Vega Risk Measure Sensitivity Settings
	 * @param curvature Curvature Risk Measure Sensitivity Settings
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RiskClassSensitivitySettings (
		final RiskMeasureSensitivitySettings delta,
		final RiskMeasureSensitivitySettings vega,
		final RiskMeasureSensitivitySettings curvature)
		throws Exception
	{
		if (null == (_delta = delta) ||
			null == (_vega = vega) ||
			null == (_curvature = curvature))
		{
			throw new Exception (
				"RiskClassSensitivitySettings Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Delta Risk Measure Sensitivity Settings
	 * 
	 * @return Delta Risk Measure Sensitivity Settings
	 */

	public RiskMeasureSensitivitySettings delta()
	{
		return _delta;
	}

	/**
	 * Vega Risk Measure Sensitivity Settings
	 * 
	 * @return Vega Risk Measure Sensitivity Settings
	 */

	public RiskMeasureSensitivitySettings vega()
	{
		return _vega;
	}

	/**
	 * Curvature Risk Measure Sensitivity Settings
	 * 
	 * @return Curvature Risk Measure Sensitivity Settings
	 */

	public RiskMeasureSensitivitySettings curvature()
	{
		return _curvature;
	}
}
