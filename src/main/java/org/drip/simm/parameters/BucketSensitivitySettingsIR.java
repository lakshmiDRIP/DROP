
package org.drip.simm.parameters;

import java.util.Map;

import org.drip.measure.stochastic.LabelCorrelation;
import org.drip.numerical.common.NumberUtil;
import org.drip.simm.rates.IRSettingsContainer20;
import org.drip.simm.rates.IRSettingsContainer21;
import org.drip.simm.rates.IRSettingsContainer24;
import org.drip.simm.rates.IRSystemics;
import org.drip.simm.rates.IRSystemics20;
import org.drip.simm.rates.IRSystemics21;
import org.drip.simm.rates.IRSystemics24;
import org.drip.simm.rates.IRConcentrationThreshold;
import org.drip.simm.rates.IRConcentrationThresholdContainer20;
import org.drip.simm.rates.IRConcentrationThresholdContainer21;
import org.drip.simm.rates.IRConcentrationThresholdContainer24;
import org.drip.simm.rates.IRWeight;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>BucketSensitivitySettingsIR</i> holds the Delta Risk Weights, Concentration Thresholds, and
 * 	Cross-Tenor/Cross-Curve Correlations for each Currency Curve and its Tenor. The References are:
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
 * 	It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Construct the ISDA 2.0 Standard IR Delta Sensitivity Settings for the Currency</li>
 * 		<li>Construct the ISDA 2.1 Standard IR Delta Sensitivity Settings for the Currency</li>
 * 		<li>Construct the ISDA 2.4 Standard IR Delta Sensitivity Settings for the Currency</li>
 * 		<li><i>BucketSensitivitySettingsIR</i> Constructor</li>
 * 		<li>Retrieve the OIS Tenor Risk Weight</li>
 * 		<li>Retrieve the LIBOR 1M Tenor Risk Weight</li>
 * 		<li>Retrieve the LIBOR 3M Tenor Risk Weight</li>
 * 		<li>Retrieve the LIBOR 6M Tenor Risk Weight</li>
 * 		<li>Retrieve the LIBOR 12M Tenor Risk Weight</li>
 * 		<li>Retrieve the PRIME Tenor Risk Weight</li>
 * 		<li>Retrieve the MUNICIPAL Curve Tenor Risk Weight</li>
 * 		<li>Retrieve the Cross Curve Correlation</li>
 * 		<li>Retrieve the Single Curve Cross Tenor Correlation</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/MarginAnalyticsLibrary.md">Initial and Variation Margin Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/README.md">Initial Margin Analytics based on ISDA SIMM and its Variants</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters/README.md">ISDA SIMM Risk Factor Parameters</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BucketSensitivitySettingsIR
	extends LiquiditySettings
{
	private double _crossCurveCorrelation = Double.NaN;
	private LabelCorrelation _crossTenorCorrelation = null;
	private Map<String, Double> _oisTenorRiskWeight = null;
	private Map<String, Double> _primeTenorRiskWeight = null;
	private Map<String, Double> _libor1MTenorRiskWeight = null;
	private Map<String, Double> _libor3MTenorRiskWeight = null;
	private Map<String, Double> _libor6MTenorRiskWeight = null;
	private Map<String, Double> _libor12MTenorRiskWeight = null;
	private Map<String, Double> _municipalTenorRiskWeight = null;

	/**
	 * Construct the ISDA 2.0 Standard IR Delta Sensitivity Settings for the Currency
	 * 
	 * @param currency Currency
	 * 
	 * @return The ISDA 2.0 Standard IR Delta Sensitivity Settings for the Currency
	 */

	public static final BucketSensitivitySettingsIR ISDA_DELTA_20 (
		final String currency)
	{
		IRConcentrationThreshold irThreshold = IRConcentrationThresholdContainer20.Threshold (currency);

		IRWeight oisRiskWeight = IRSettingsContainer20.RiskWeight (currency, IRSystemics.SUB_CURVE_OIS);

		IRWeight libor1MRiskWeight = IRSettingsContainer20.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_LIBOR_1M
		);

		IRWeight libor3MRiskWeight = IRSettingsContainer20.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_LIBOR_3M
		);

		IRWeight libor6MRiskWeight = IRSettingsContainer20.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_LIBOR_6M
		);

		IRWeight libor12MRiskWeight = IRSettingsContainer20.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_LIBOR_12M
		);

		IRWeight primeRiskWeight = IRSettingsContainer20.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_PRIME
		);

		IRWeight municipalRiskWeight = IRSettingsContainer20.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_MUNICIPAL
		);

		try {
			return null == irThreshold ||
				null == libor1MRiskWeight ||
				null == libor1MRiskWeight ||
				null == libor3MRiskWeight ||
				null == libor6MRiskWeight ||
				null == libor12MRiskWeight ||
				null == primeRiskWeight ||
				null == municipalRiskWeight ? null : new BucketSensitivitySettingsIR (
					oisRiskWeight.tenorDelta(),
					libor1MRiskWeight.tenorDelta(),
					libor3MRiskWeight.tenorDelta(),
					libor6MRiskWeight.tenorDelta(),
					libor12MRiskWeight.tenorDelta(),
					primeRiskWeight.tenorDelta(),
					municipalRiskWeight.tenorDelta(),
					IRSettingsContainer20.SingleCurveTenorCorrelation(),
					IRSystemics20.SINGLE_CURRENCY_CROSS_CURVE_CORRELATION,
					irThreshold.deltaVega().delta()
				);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the ISDA 2.1 Standard IR Delta Sensitivity Settings for the Currency
	 * 
	 * @param currency Currency
	 * 
	 * @return The ISDA 2.1 Standard IR Delta Sensitivity Settings for the Currency
	 */

	public static final BucketSensitivitySettingsIR ISDA_DELTA_21 (
		final String currency)
	{
		IRConcentrationThreshold irThreshold = IRConcentrationThresholdContainer21.Threshold (currency);

		IRWeight oisRiskWeight = IRSettingsContainer21.RiskWeight (currency, IRSystemics.SUB_CURVE_OIS);

		IRWeight libor1MRiskWeight = IRSettingsContainer21.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_LIBOR_1M
		);

		IRWeight libor3MRiskWeight = IRSettingsContainer21.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_LIBOR_3M
		);

		IRWeight libor6MRiskWeight = IRSettingsContainer21.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_LIBOR_6M
		);

		IRWeight libor12MRiskWeight = IRSettingsContainer21.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_LIBOR_12M
		);

		IRWeight primeRiskWeight = IRSettingsContainer21.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_PRIME
		);

		IRWeight municipalRiskWeight = IRSettingsContainer21.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_MUNICIPAL
		);

		try {
			return null == irThreshold ||
				null == libor1MRiskWeight ||
				null == libor1MRiskWeight ||
				null == libor3MRiskWeight ||
				null == libor6MRiskWeight ||
				null == libor12MRiskWeight ||
				null == primeRiskWeight ||
				null == municipalRiskWeight ? null : new BucketSensitivitySettingsIR (
					oisRiskWeight.tenorDelta(),
					libor1MRiskWeight.tenorDelta(),
					libor3MRiskWeight.tenorDelta(),
					libor6MRiskWeight.tenorDelta(),
					libor12MRiskWeight.tenorDelta(),
					primeRiskWeight.tenorDelta(),
					municipalRiskWeight.tenorDelta(),
					IRSettingsContainer21.SingleCurveTenorCorrelation(),
					IRSystemics21.SINGLE_CURRENCY_CROSS_CURVE_CORRELATION,
					irThreshold.deltaVega().delta()
				);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the ISDA 2.4 Standard IR Delta Sensitivity Settings for the Currency
	 * 
	 * @param currency Currency
	 * 
	 * @return The ISDA 2.4 Standard IR Delta Sensitivity Settings for the Currency
	 */

	public static final BucketSensitivitySettingsIR ISDA_DELTA_24 (
		final String currency)
	{
		IRConcentrationThreshold irThreshold = IRConcentrationThresholdContainer24.Threshold (currency);

		IRWeight oisRiskWeight = IRSettingsContainer24.RiskWeight (currency, IRSystemics.SUB_CURVE_OIS);

		IRWeight libor1MRiskWeight = IRSettingsContainer24.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_LIBOR_1M
		);

		IRWeight libor3MRiskWeight = IRSettingsContainer24.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_LIBOR_3M
		);

		IRWeight libor6MRiskWeight = IRSettingsContainer24.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_LIBOR_6M
		);

		IRWeight libor12MRiskWeight = IRSettingsContainer24.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_LIBOR_12M
		);

		IRWeight primeRiskWeight = IRSettingsContainer24.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_PRIME
		);

		IRWeight municipalRiskWeight = IRSettingsContainer24.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_MUNICIPAL
		);

		try {
			return null == irThreshold ||
				null == libor1MRiskWeight ||
				null == libor1MRiskWeight ||
				null == libor3MRiskWeight ||
				null == libor6MRiskWeight ||
				null == libor12MRiskWeight ||
				null == primeRiskWeight ||
				null == municipalRiskWeight ? null : new BucketSensitivitySettingsIR (
					oisRiskWeight.tenorDelta(),
					libor1MRiskWeight.tenorDelta(),
					libor3MRiskWeight.tenorDelta(),
					libor6MRiskWeight.tenorDelta(),
					libor12MRiskWeight.tenorDelta(),
					primeRiskWeight.tenorDelta(),
					municipalRiskWeight.tenorDelta(),
					IRSettingsContainer24.SingleCurveTenorCorrelation(),
					IRSystemics24.SINGLE_CURRENCY_CROSS_CURVE_CORRELATION,
					irThreshold.deltaVega().delta()
				);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>BucketSensitivitySettingsIR</i> Constructor
	 * 
	 * @param oisTenorRiskWeight The OIS Tenor Risk Weight
	 * @param libor1MTenorRiskWeight The LIBOR 1M Tenor Risk Weight
	 * @param libor3MTenorRiskWeight The LIBOR 3M Tenor Risk Weight
	 * @param libor6MTenorRiskWeight The LIBOR 6M Tenor Risk Weight
	 * @param libor12MTenorRiskWeight The LIBOR 12M Tenor Risk Weight
	 * @param primeTenorRiskWeight The PRIME Tenor Risk Weight
	 * @param municipalTenorRiskWeight The MUNICIPAL Tenor Risk Weight
	 * @param crossTenorCorrelation Single Curve Cross-Tenor Correlation
	 * @param crossCurveCorrelation Cross Curve Correlation
	 * @param concentrationThreshold The Concentration Threshold
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BucketSensitivitySettingsIR (
		final Map<String, Double> oisTenorRiskWeight,
		final Map<String, Double> libor1MTenorRiskWeight,
		final Map<String, Double> libor3MTenorRiskWeight,
		final Map<String, Double> libor6MTenorRiskWeight,
		final Map<String, Double> libor12MTenorRiskWeight,
		final Map<String, Double> primeTenorRiskWeight,
		final Map<String, Double> municipalTenorRiskWeight,
		final LabelCorrelation crossTenorCorrelation,
		final double crossCurveCorrelation,
		final double concentrationThreshold)
		throws Exception
	{
		super (concentrationThreshold);

		if (null == (_oisTenorRiskWeight = oisTenorRiskWeight) ||
			null == (_libor1MTenorRiskWeight = libor1MTenorRiskWeight) ||
			null == (_libor3MTenorRiskWeight = libor3MTenorRiskWeight) ||
			null == (_libor6MTenorRiskWeight = libor6MTenorRiskWeight) ||
			null == (_libor12MTenorRiskWeight = libor12MTenorRiskWeight) ||
			null == (_primeTenorRiskWeight = primeTenorRiskWeight) ||
			null == (_municipalTenorRiskWeight = municipalTenorRiskWeight) ||
			null == (_crossTenorCorrelation = crossTenorCorrelation) ||
			!NumberUtil.IsValid (_crossCurveCorrelation = crossCurveCorrelation) ||
			-1. > _crossCurveCorrelation || 1. < _crossCurveCorrelation)
		{
			throw new Exception ("BucketSensitivitySettingsIR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the OIS Tenor Risk Weight
	 * 
	 * @return The OIS Tenor Risk Weight
	 */

	public Map<String, Double> oisTenorRiskWeight()
	{
		return _oisTenorRiskWeight;
	}

	/**
	 * Retrieve the LIBOR 1M Tenor Risk Weight
	 * 
	 * @return The LIBOR 1M Tenor Risk Weight
	 */

	public Map<String, Double> libor1MTenorRiskWeight()
	{
		return _libor1MTenorRiskWeight;
	}

	/**
	 * Retrieve the LIBOR 3M Tenor Risk Weight
	 * 
	 * @return The LIBOR 3M Tenor Risk Weight
	 */

	public Map<String, Double> libor3MTenorRiskWeight()
	{
		return _libor3MTenorRiskWeight;
	}

	/**
	 * Retrieve the LIBOR 6M Tenor Risk Weight
	 * 
	 * @return The LIBOR 6M Tenor Risk Weight
	 */

	public Map<String, Double> libor6MTenorRiskWeight()
	{
		return _libor6MTenorRiskWeight;
	}

	/**
	 * Retrieve the LIBOR 12M Tenor Risk Weight
	 * 
	 * @return The LIBOR 12M Tenor Risk Weight
	 */

	public Map<String, Double> libor12MTenorRiskWeight()
	{
		return _libor12MTenorRiskWeight;
	}

	/**
	 * Retrieve the PRIME Tenor Risk Weight
	 * 
	 * @return The PRIME Tenor Risk Weight
	 */

	public Map<String, Double> primeTenorRiskWeight()
	{
		return _primeTenorRiskWeight;
	}

	/**
	 * Retrieve the MUNICIPAL Curve Tenor Risk Weight
	 * 
	 * @return The MUNICIPAL Curve Tenor Risk Weight
	 */

	public Map<String, Double> municipalTenorRiskWeight()
	{
		return _municipalTenorRiskWeight;
	}

	/**
	 * Retrieve the Cross Curve Correlation
	 * 
	 * @return The Cross Curve Correlation
	 */

	public double crossCurveCorrelation()
	{
		return _crossCurveCorrelation;
	}

	/**
	 * Retrieve the Single Curve Cross Tenor Correlation
	 * 
	 * @return The Single Curve Cross Tenor Correlation
	 */

	public LabelCorrelation crossTenorCorrelation()
	{
		return _crossTenorCorrelation;
	}
}
