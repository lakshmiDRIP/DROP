
package org.drip.simm.parameters;

import java.util.HashMap;
import java.util.Map;

import org.drip.measure.gaussian.NormalQuadrature;
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
 * <i>BucketVegaSettingsIR</i> holds the Vega Risk Weights, Concentration Thresholds, and
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
 * 		<li>Retrieve the ISDA 2.0 Credit Qualifying Bucket Vega Settings</li>
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

public class BucketVegaSettingsIR
	extends BucketSensitivitySettingsIR
{
	private double _vegaScaler = Double.NaN;
	private double _historicalVolatilityRatio = Double.NaN;
	private Map<String, Double> _oisTenorDeltaRiskWeight = null;
	private Map<String, Double> _primeTenorDeltaRiskWeight = null;
	private Map<String, Double> _libor1MTenorDeltaRiskWeight = null;
	private Map<String, Double> _libor3MTenorDeltaRiskWeight = null;
	private Map<String, Double> _libor6MTenorDeltaRiskWeight = null;
	private Map<String, Double> _libor12MTenorDeltaRiskWeight = null;
	private Map<String, Double> _municipalTenorDeltaRiskWeight = null;

	/**
	 * Construct the ISDA 2.0 Standard IR Vega Sensitivity Settings for the Currency
	 * 
	 * @param currency Currency
	 * 
	 * @return The ISDA 2.0 Standard IR Vega Sensitivity Settings for the Currency
	 */

	public static BucketVegaSettingsIR ISDA_20 (
		final String currency)
	{
		IRConcentrationThreshold irThreshold = IRConcentrationThresholdContainer20.Threshold (
			currency
		);

		IRWeight oisRiskWeight = IRSettingsContainer20.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_OIS
		);

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

		BucketSensitivitySettingsIR bucketSensitivitySettingsIR = BucketSensitivitySettingsIR.ISDA_DELTA_20 (
			currency
		);

		try
		{
			return null == irThreshold ||
				null == libor1MRiskWeight ||
				null == libor1MRiskWeight ||
				null == libor3MRiskWeight ||
				null == libor6MRiskWeight ||
				null == libor12MRiskWeight ||
				null == primeRiskWeight ||
				null == municipalRiskWeight ||
				null == bucketSensitivitySettingsIR ? null : new BucketVegaSettingsIR (
					oisRiskWeight.tenorVega(),
					libor1MRiskWeight.tenorVega(),
					libor3MRiskWeight.tenorVega(),
					libor6MRiskWeight.tenorVega(),
					libor12MRiskWeight.tenorVega(),
					primeRiskWeight.tenorVega(),
					municipalRiskWeight.tenorVega(),
					IRSettingsContainer20.SingleCurveTenorCorrelation(),
					IRSystemics20.SINGLE_CURRENCY_CROSS_CURVE_CORRELATION,
					irThreshold.deltaVega().vega(),
					Math.sqrt (
						365. / 14.
					) / NormalQuadrature.InverseCDF (
						0.99
					),
					1.,
					bucketSensitivitySettingsIR.oisTenorRiskWeight(),
					bucketSensitivitySettingsIR.libor1MTenorRiskWeight(),
					bucketSensitivitySettingsIR.libor3MTenorRiskWeight(),
					bucketSensitivitySettingsIR.libor6MTenorRiskWeight(),
					bucketSensitivitySettingsIR.libor12MTenorRiskWeight(),
					bucketSensitivitySettingsIR.primeTenorRiskWeight(),
					bucketSensitivitySettingsIR.municipalTenorRiskWeight()
				);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the ISDA 2.1 Standard IR Vega Sensitivity Settings for the Currency
	 * 
	 * @param currency Currency
	 * 
	 * @return The ISDA 2.1 Standard IR Vega Sensitivity Settings for the Currency
	 */

	public static BucketVegaSettingsIR ISDA_21 (
		final String currency)
	{
		IRConcentrationThreshold irThreshold = IRConcentrationThresholdContainer21.Threshold (
			currency
		);

		IRWeight oisRiskWeight = IRSettingsContainer21.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_OIS
		);

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

		BucketSensitivitySettingsIR bucketSensitivitySettingsIR = BucketSensitivitySettingsIR.ISDA_DELTA_21 (
			currency
		);

		try
		{
			return null == irThreshold ||
				null == libor1MRiskWeight ||
				null == libor1MRiskWeight ||
				null == libor3MRiskWeight ||
				null == libor6MRiskWeight ||
				null == libor12MRiskWeight ||
				null == primeRiskWeight ||
				null == municipalRiskWeight ||
				null == bucketSensitivitySettingsIR ? null : new BucketVegaSettingsIR (
					oisRiskWeight.tenorVega(),
					libor1MRiskWeight.tenorVega(),
					libor3MRiskWeight.tenorVega(),
					libor6MRiskWeight.tenorVega(),
					libor12MRiskWeight.tenorVega(),
					primeRiskWeight.tenorVega(),
					municipalRiskWeight.tenorVega(),
					IRSettingsContainer21.SingleCurveTenorCorrelation(),
					IRSystemics21.SINGLE_CURRENCY_CROSS_CURVE_CORRELATION,
					irThreshold.deltaVega().vega(),
					Math.sqrt (
						365. / 14.
					) / NormalQuadrature.InverseCDF (
						0.99
					),
					1.,
					bucketSensitivitySettingsIR.oisTenorRiskWeight(),
					bucketSensitivitySettingsIR.libor1MTenorRiskWeight(),
					bucketSensitivitySettingsIR.libor3MTenorRiskWeight(),
					bucketSensitivitySettingsIR.libor6MTenorRiskWeight(),
					bucketSensitivitySettingsIR.libor12MTenorRiskWeight(),
					bucketSensitivitySettingsIR.primeTenorRiskWeight(),
					bucketSensitivitySettingsIR.municipalTenorRiskWeight()
				);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the ISDA 2.4 Standard IR Vega Sensitivity Settings for the Currency
	 * 
	 * @param currency Currency
	 * 
	 * @return The ISDA 2.4 Standard IR Vega Sensitivity Settings for the Currency
	 */

	public static BucketVegaSettingsIR ISDA_24 (
		final String currency)
	{
		IRConcentrationThreshold irThreshold = IRConcentrationThresholdContainer24.Threshold (
			currency
		);

		IRWeight oisRiskWeight = IRSettingsContainer24.RiskWeight (
			currency,
			IRSystemics.SUB_CURVE_OIS
		);

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

		BucketSensitivitySettingsIR bucketSensitivitySettingsIR = BucketSensitivitySettingsIR.ISDA_DELTA_24 (
			currency
		);

		try
		{
			return null == irThreshold ||
				null == libor1MRiskWeight ||
				null == libor1MRiskWeight ||
				null == libor3MRiskWeight ||
				null == libor6MRiskWeight ||
				null == libor12MRiskWeight ||
				null == primeRiskWeight ||
				null == municipalRiskWeight ||
				null == bucketSensitivitySettingsIR ? null : new BucketVegaSettingsIR (
					oisRiskWeight.tenorVega(),
					libor1MRiskWeight.tenorVega(),
					libor3MRiskWeight.tenorVega(),
					libor6MRiskWeight.tenorVega(),
					libor12MRiskWeight.tenorVega(),
					primeRiskWeight.tenorVega(),
					municipalRiskWeight.tenorVega(),
					IRSettingsContainer24.SingleCurveTenorCorrelation(),
					IRSystemics24.SINGLE_CURRENCY_CROSS_CURVE_CORRELATION,
					irThreshold.deltaVega().vega(),
					Math.sqrt (
						365. / 14.
					) / NormalQuadrature.InverseCDF (
						0.99
					),
					1.,
					bucketSensitivitySettingsIR.oisTenorRiskWeight(),
					bucketSensitivitySettingsIR.libor1MTenorRiskWeight(),
					bucketSensitivitySettingsIR.libor3MTenorRiskWeight(),
					bucketSensitivitySettingsIR.libor6MTenorRiskWeight(),
					bucketSensitivitySettingsIR.libor12MTenorRiskWeight(),
					bucketSensitivitySettingsIR.primeTenorRiskWeight(),
					bucketSensitivitySettingsIR.municipalTenorRiskWeight()
				);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BucketVegaSettingsIR Constructor
	 * 
	 * @param oisTenorVegaRiskWeight The OIS Tenor Vega Risk Weight
	 * @param libor1MTenorVegaRiskWeight The LIBOR 1M Tenor Vega Risk Weight
	 * @param libor3MTenorVegaRiskWeight The LIBOR 3M Tenor Vega Risk Weight
	 * @param libor6MTenorVegaRiskWeight The LIBOR 6M Tenor Vega Risk Weight
	 * @param libor12MTenorVegaRiskWeight The LIBOR 12M Tenor Vega Risk Weight
	 * @param primeTenorVegaRiskWeight The PRIME Tenor Vega Risk Weight
	 * @param municipalTenorVegaRiskWeight The MUNICIPAL Tenor Vega Risk Weight
	 * @param crossTenorCorrelation Single Curve Cross-Tenor Correlation
	 * @param crossCurveCorrelation Cross Curve Correlation
	 * @param concentrationThreshold The Concentration Threshold
	 * @param vegaScaler The Vega Scaler
	 * @param historicalVolatilityRatio The Historical Volatility Ratio
	 * @param oisTenorDeltaRiskWeight The OIS Tenor Delta Risk Weight
	 * @param libor1MTenorDeltaRiskWeight The LIBOR 1M Tenor Delta Risk Weight
	 * @param libor3MTenorDeltaRiskWeight The LIBOR 3M Tenor Delta Risk Weight
	 * @param libor6MTenorDeltaRiskWeight The LIBOR 6M Tenor Delta Risk Weight
	 * @param libor12MTenorDeltaRiskWeight The LIBOR 12M Tenor Delta Risk Weight
	 * @param primeTenorDeltaRiskWeight The PRIME Tenor Delta Risk Weight
	 * @param municipalTenorDeltaRiskWeight The MUNICIPAL Tenor Delta Risk Weight
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BucketVegaSettingsIR (
		final Map<String, Double> oisTenorVegaRiskWeight,
		final Map<String, Double> libor1MTenorVegaRiskWeight,
		final Map<String, Double> libor3MTenorVegaRiskWeight,
		final Map<String, Double> libor6MTenorVegaRiskWeight,
		final Map<String, Double> libor12MTenorVegaRiskWeight,
		final Map<String, Double> primeTenorVegaRiskWeight,
		final Map<String, Double> municipalTenorVegaRiskWeight,
		final org.drip.measure.identifier.LabelledVertexCorrelation crossTenorCorrelation,
		final double crossCurveCorrelation,
		final double concentrationThreshold,
		final double vegaScaler,
		final double historicalVolatilityRatio,
		final Map<String, Double> oisTenorDeltaRiskWeight,
		final Map<String, Double> libor1MTenorDeltaRiskWeight,
		final Map<String, Double> libor3MTenorDeltaRiskWeight,
		final Map<String, Double> libor6MTenorDeltaRiskWeight,
		final Map<String, Double> libor12MTenorDeltaRiskWeight,
		final Map<String, Double> primeTenorDeltaRiskWeight,
		final Map<String, Double> municipalTenorDeltaRiskWeight)
		throws Exception
	{
		super (
			oisTenorVegaRiskWeight,
			libor1MTenorVegaRiskWeight,
			libor3MTenorVegaRiskWeight,
			libor6MTenorVegaRiskWeight,
			libor12MTenorVegaRiskWeight,
			primeTenorVegaRiskWeight,
			municipalTenorVegaRiskWeight,
			crossTenorCorrelation,
			crossCurveCorrelation,
			concentrationThreshold
		);

		if (!NumberUtil.IsValid (
				_vegaScaler = vegaScaler
			) ||
			!NumberUtil.IsValid (
				_historicalVolatilityRatio = historicalVolatilityRatio
			) ||
			null == (_oisTenorDeltaRiskWeight = oisTenorDeltaRiskWeight) ||
			null == (_libor1MTenorDeltaRiskWeight = libor1MTenorDeltaRiskWeight) ||
			null == (_libor3MTenorDeltaRiskWeight = libor3MTenorDeltaRiskWeight) ||
			null == (_libor6MTenorDeltaRiskWeight = libor6MTenorDeltaRiskWeight) ||
			null == (_libor12MTenorDeltaRiskWeight = libor12MTenorDeltaRiskWeight) ||
			null == (_primeTenorDeltaRiskWeight = primeTenorDeltaRiskWeight) ||
			null == (_municipalTenorDeltaRiskWeight = municipalTenorDeltaRiskWeight))
		{
			throw new Exception (
				"BucketVegaSettingsIR Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Vega Scaler
	 * 
	 * @return The Vega Scaler
	 */

	public double vegaScaler()
	{
		return _vegaScaler;
	}

	/**
	 * Retrieve the Historical Volatility Ratio
	 * 
	 * @return The Historical Volatility Ratio
	 */

	public double historicalVolatilityRatio()
	{
		return _historicalVolatilityRatio;
	}

	/**
	 * Retrieve the OIS Tenor Delta Risk Weight
	 * 
	 * @return The OIS Tenor Delta Risk Weight
	 */

	public Map<String, Double> oisTenorDeltaRiskWeight()
	{
		return _oisTenorDeltaRiskWeight;
	}

	/**
	 * Retrieve the OIS Tenor Vega Risk Weight
	 * 
	 * @return The OIS Tenor Vega Risk Weight
	 */

	public Map<String, Double> oisTenorVegaRiskWeight()
	{
		return super.oisTenorRiskWeight();
	}

	/**
	 * Retrieve the LIBOR 1M Tenor Delta Risk Weight
	 * 
	 * @return The LIBOR 1M Tenor Delta Risk Weight
	 */

	public Map<String, Double> libor1MTenorDeltaRiskWeight()
	{
		return _libor1MTenorDeltaRiskWeight;
	}

	/**
	 * Retrieve the LIBOR1M Tenor Vega Risk Weight
	 * 
	 * @return The LIBOR1M Tenor Vega Risk Weight
	 */

	public Map<String, Double> libor1MTenorVegaRiskWeight()
	{
		return super.libor1MTenorRiskWeight();
	}

	/**
	 * Retrieve the LIBOR 3M Tenor Delta Risk Weight
	 * 
	 * @return The LIBOR 3M Tenor Delta Risk Weight
	 */

	public Map<String, Double> libor3MTenorDeltaRiskWeight()
	{
		return _libor3MTenorDeltaRiskWeight;
	}

	/**
	 * Retrieve the LIBOR3M Tenor Vega Risk Weight
	 * 
	 * @return The LIBOR3M Tenor Vega Risk Weight
	 */

	public Map<String, Double> libor3MTenorVegaRiskWeight()
	{
		return super.libor3MTenorRiskWeight();
	}

	/**
	 * Retrieve the LIBOR 6M Tenor Delta Risk Weight
	 * 
	 * @return The LIBOR 6M Tenor Delta Risk Weight
	 */

	public Map<String, Double> libor6MTenorDeltaRiskWeight()
	{
		return _libor6MTenorDeltaRiskWeight;
	}

	/**
	 * Retrieve the LIBOR6M Tenor Vega Risk Weight
	 * 
	 * @return The LIBOR6M Tenor Vega Risk Weight
	 */

	public Map<String, Double> libor6MTenorVegaRiskWeight()
	{
		return super.libor6MTenorRiskWeight();
	}

	/**
	 * Retrieve the LIBOR 12M Tenor Delta Risk Weight
	 * 
	 * @return The LIBOR 12M Tenor Delta Risk Weight
	 */

	public Map<String, Double> libor12MTenorDeltaRiskWeight()
	{
		return _libor12MTenorDeltaRiskWeight;
	}

	/**
	 * Retrieve the LIBOR 12M Tenor Vega Risk Weight
	 * 
	 * @return The LIBOR 12M Tenor Vega Risk Weight
	 */

	public Map<String, Double> libor12MTenorVegaRiskWeight()
	{
		return super.libor12MTenorRiskWeight();
	}

	/**
	 * Retrieve the PRIME Tenor Delta Risk Weight
	 * 
	 * @return The PRIME Tenor Delta Risk Weight
	 */

	public Map<String, Double> primeTenorDeltaRiskWeight()
	{
		return _primeTenorDeltaRiskWeight;
	}

	/**
	 * Retrieve the PRIME Tenor Vega Risk Weight
	 * 
	 * @return The PRIME Tenor Vega Risk Weight
	 */

	public Map<String, Double> primeTenorVegaRiskWeight()
	{
		return super.primeTenorRiskWeight();
	}

	/**
	 * Retrieve the MUNICIPAL Tenor Delta Risk Weight
	 * 
	 * @return The MUNICIPAL Tenor Delta Risk Weight
	 */

	public Map<String, Double> municipalTenorDeltaRiskWeight()
	{
		return _municipalTenorDeltaRiskWeight;
	}

	/**
	 * Retrieve the MUNICIPAL Tenor Vega Risk Weight
	 * 
	 * @return The MUNICIPAL Tenor Vega Risk Weight
	 */

	public Map<String, Double> municipalTenorVegaRiskWeight()
	{
		return super.municipalTenorRiskWeight();
	}

	@Override public Map<String, Double> oisTenorRiskWeight()
	{
		Map<String, Double> oisTenorVegaRiskWeight = oisTenorVegaRiskWeight();

		Map<String, Double> oisTenorRiskWeight = new HashMap<String, Double>();

		for (Map.Entry<String, Double> oisTenorVegaRiskWeightEntry : oisTenorVegaRiskWeight.entrySet())
		{
			String tenor = oisTenorVegaRiskWeightEntry.getKey();

			if (!_oisTenorDeltaRiskWeight.containsKey (
				tenor
			))
			{
				return null;
			}

			oisTenorRiskWeight.put (
				tenor,
				oisTenorVegaRiskWeightEntry.getValue() * _oisTenorDeltaRiskWeight.get (
					tenor
				) * _vegaScaler * _historicalVolatilityRatio
			);
		}

		return oisTenorRiskWeight;
	}

	@Override public Map<String, Double> libor1MTenorRiskWeight()
	{
		Map<String, Double> libor1MTenorVegaRiskWeight = libor1MTenorVegaRiskWeight();

		Map<String, Double> libor1MTenorRiskWeight = new HashMap<String, Double>();

		for (Map.Entry<String, Double> libor1MTenorVegaRiskWeightEntry :
			libor1MTenorVegaRiskWeight.entrySet()
		)
		{
			String tenor = libor1MTenorVegaRiskWeightEntry.getKey();

			if (!_libor1MTenorDeltaRiskWeight.containsKey (
				tenor
			))
			{
				return null;
			}

			libor1MTenorRiskWeight.put (
				tenor,
				libor1MTenorVegaRiskWeightEntry.getValue() * _libor1MTenorDeltaRiskWeight.get (
					tenor
				) * _vegaScaler * _historicalVolatilityRatio
			);
		}

		return libor1MTenorRiskWeight;
	}

	@Override public Map<String, Double> libor3MTenorRiskWeight()
	{
		Map<String, Double> libor3MTenorVegaRiskWeight = libor3MTenorVegaRiskWeight();

		Map<String, Double> libor3MTenorRiskWeight = new HashMap<String, Double>();

		for (Map.Entry<String, Double> libor3MTenorVegaRiskWeightEntry :
			libor3MTenorVegaRiskWeight.entrySet())
		{
			String tenor = libor3MTenorVegaRiskWeightEntry.getKey();

			if (!_libor3MTenorDeltaRiskWeight.containsKey (
				tenor
			))
			{
				return null;
			}

			libor3MTenorRiskWeight.put (
				tenor,
				libor3MTenorVegaRiskWeightEntry.getValue() * _libor3MTenorDeltaRiskWeight.get (
					tenor
				) * _vegaScaler * _historicalVolatilityRatio
			);
		}

		return libor3MTenorRiskWeight;
	}

	@Override public Map<String, Double> libor6MTenorRiskWeight()
	{
		Map<String, Double> libor6MTenorVegaRiskWeight = libor6MTenorVegaRiskWeight();

		Map<String, Double> libor6MTenorRiskWeight = new HashMap<String, Double>();

		for (Map.Entry<String, Double> libor6MTenorVegaRiskWeightEntry :
			libor6MTenorVegaRiskWeight.entrySet())
		{
			String tenor = libor6MTenorVegaRiskWeightEntry.getKey();

			if (!_libor6MTenorDeltaRiskWeight.containsKey (
				tenor
			))
			{
				return null;
			}

			libor6MTenorRiskWeight.put (
				tenor,
				libor6MTenorVegaRiskWeightEntry.getValue() * _libor6MTenorDeltaRiskWeight.get (
					tenor
				) * _vegaScaler *_historicalVolatilityRatio
			);
		}

		return libor6MTenorRiskWeight;
	}

	@Override public Map<String, Double> libor12MTenorRiskWeight()
	{
		Map<String, Double> libor12MTenorVegaRiskWeight = libor12MTenorVegaRiskWeight();

		Map<String, Double> libor12MTenorRiskWeight = new HashMap<String, Double>();

		for (Map.Entry<String, Double> libor12MTenorVegaRiskWeightEntry :
			libor12MTenorVegaRiskWeight.entrySet()
		)
		{
			String tenor = libor12MTenorVegaRiskWeightEntry.getKey();

			if (!_libor12MTenorDeltaRiskWeight.containsKey (
				tenor
			))
			{
				return null;
			}

			libor12MTenorRiskWeight.put (
				tenor,
				libor12MTenorVegaRiskWeightEntry.getValue() * _libor12MTenorDeltaRiskWeight.get (
					tenor
				) * _vegaScaler *_historicalVolatilityRatio
			);
		}

		return libor12MTenorRiskWeight;
	}

	@Override public Map<String, Double> primeTenorRiskWeight()
	{
		Map<String, Double> primeTenorVegaRiskWeight = primeTenorVegaRiskWeight();

		Map<String, Double> primeTenorRiskWeight = new HashMap<String, Double>();

		for (Map.Entry<String, Double> primeTenorVegaRiskWeightEntry : primeTenorVegaRiskWeight.entrySet())
		{
			String tenor = primeTenorVegaRiskWeightEntry.getKey();

			if (!_primeTenorDeltaRiskWeight.containsKey (
				tenor
			))
			{
				return null;
			}

			primeTenorRiskWeight.put (
				tenor,
				primeTenorVegaRiskWeightEntry.getValue() * _primeTenorDeltaRiskWeight.get (
					tenor
				) * _vegaScaler *_historicalVolatilityRatio
			);
		}

		return primeTenorRiskWeight;
	}

	@Override public Map<String, Double> municipalTenorRiskWeight()
	{
		Map<String, Double> municipalTenorVegaRiskWeight = super.municipalTenorRiskWeight();

		Map<String, Double> municipalTenorRiskWeight = new HashMap<String, Double>();

		for (Map.Entry<String, Double> municipalTenorVegaRiskWeightEntry :
			municipalTenorVegaRiskWeight.entrySet()
		)
		{
			String tenor = municipalTenorVegaRiskWeightEntry.getKey();

			if (!_municipalTenorDeltaRiskWeight.containsKey (
				tenor
			))
			{
				return null;
			}

			municipalTenorRiskWeight.put (
				tenor,
				municipalTenorVegaRiskWeightEntry.getValue() * _municipalTenorDeltaRiskWeight.get (
					tenor
				) * _vegaScaler *_historicalVolatilityRatio
			);
		}

		return municipalTenorRiskWeight;
	}
}
