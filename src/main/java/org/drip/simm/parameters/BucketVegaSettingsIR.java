
package org.drip.simm.parameters;

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
 * <i>BucketVegaSettingsIR</i> holds the Vega Risk Weights, Concentration Thresholds, and
 * Cross-Tenor/Cross-Curve Correlations for each Currency Curve and its Tenor. The References are:
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
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters">Parameters</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BucketVegaSettingsIR extends org.drip.simm.parameters.BucketSensitivitySettingsIR
{
	private double _vegaScaler = java.lang.Double.NaN;
	private double _historicalVolatilityRatio = java.lang.Double.NaN;
	private java.util.Map<java.lang.String, java.lang.Double> _oisTenorDeltaRiskWeight = null;
	private java.util.Map<java.lang.String, java.lang.Double> _primeTenorDeltaRiskWeight = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor1MTenorDeltaRiskWeight = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor3MTenorDeltaRiskWeight = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor6MTenorDeltaRiskWeight = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor12MTenorDeltaRiskWeight = null;
	private java.util.Map<java.lang.String, java.lang.Double> _municipalTenorDeltaRiskWeight = null;

	/**
	 * Construct the ISDA 2.0 Standard IR Vega Sensitivity Settings for the Currency
	 * 
	 * @param currency Currency
	 * 
	 * @return The ISDA 2.0 Standard IR Vega Sensitivity Settings for the Currency
	 */

	public static BucketVegaSettingsIR ISDA_20 (
		final java.lang.String currency)
	{
		org.drip.simm.rates.IRThreshold irThreshold = org.drip.simm.rates.IRThresholdContainer20.Threshold
			(currency);

		org.drip.simm.rates.IRWeight oisRiskWeight = org.drip.simm.rates.IRSettingsContainer20.RiskWeight (
			currency,
			org.drip.simm.rates.IRSystemics.SUB_CURVE_OIS
		);

		org.drip.simm.rates.IRWeight libor1MRiskWeight =
			org.drip.simm.rates.IRSettingsContainer20.RiskWeight (
				currency,
				org.drip.simm.rates.IRSystemics.SUB_CURVE_LIBOR_1M
			);

		org.drip.simm.rates.IRWeight libor3MRiskWeight =
			org.drip.simm.rates.IRSettingsContainer20.RiskWeight (
				currency,
				org.drip.simm.rates.IRSystemics.SUB_CURVE_LIBOR_3M
			);

		org.drip.simm.rates.IRWeight libor6MRiskWeight =
			org.drip.simm.rates.IRSettingsContainer20.RiskWeight (
				currency,
				org.drip.simm.rates.IRSystemics.SUB_CURVE_LIBOR_6M
			);

		org.drip.simm.rates.IRWeight libor12MRiskWeight =
			org.drip.simm.rates.IRSettingsContainer20.RiskWeight (
				currency,
				org.drip.simm.rates.IRSystemics.SUB_CURVE_LIBOR_12M
			);

		org.drip.simm.rates.IRWeight primeRiskWeight =
			org.drip.simm.rates.IRSettingsContainer20.RiskWeight (
				currency,
				org.drip.simm.rates.IRSystemics.SUB_CURVE_PRIME
			);

		org.drip.simm.rates.IRWeight municipalRiskWeight =
			org.drip.simm.rates.IRSettingsContainer20.RiskWeight (
				currency,
				org.drip.simm.rates.IRSystemics.SUB_CURVE_MUNICIPAL
			);

		BucketSensitivitySettingsIR bucketSensitivitySettingsIR =
			org.drip.simm.parameters.BucketSensitivitySettingsIR.ISDA_DELTA_20 (currency);

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
					org.drip.simm.rates.IRSettingsContainer20.SingleCurveTenorCorrelation(),
					org.drip.simm.rates.IRSystemics20.SINGLE_CURRENCY_CROSS_CURVE_CORRELATION,
					irThreshold.deltaVega().vega(),
					java.lang.Math.sqrt (365. / 14.) /
						org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
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
		catch (java.lang.Exception e)
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
		final java.lang.String currency)
	{
		org.drip.simm.rates.IRThreshold irThreshold = org.drip.simm.rates.IRThresholdContainer21.Threshold
			(currency);

		org.drip.simm.rates.IRWeight oisRiskWeight = org.drip.simm.rates.IRSettingsContainer21.RiskWeight (
			currency,
			org.drip.simm.rates.IRSystemics.SUB_CURVE_OIS
		);

		org.drip.simm.rates.IRWeight libor1MRiskWeight =
			org.drip.simm.rates.IRSettingsContainer21.RiskWeight (
				currency,
				org.drip.simm.rates.IRSystemics.SUB_CURVE_LIBOR_1M
			);

		org.drip.simm.rates.IRWeight libor3MRiskWeight =
			org.drip.simm.rates.IRSettingsContainer21.RiskWeight (
				currency,
				org.drip.simm.rates.IRSystemics.SUB_CURVE_LIBOR_3M
			);

		org.drip.simm.rates.IRWeight libor6MRiskWeight =
			org.drip.simm.rates.IRSettingsContainer21.RiskWeight (
				currency,
				org.drip.simm.rates.IRSystemics.SUB_CURVE_LIBOR_6M
			);

		org.drip.simm.rates.IRWeight libor12MRiskWeight =
			org.drip.simm.rates.IRSettingsContainer21.RiskWeight (
				currency,
				org.drip.simm.rates.IRSystemics.SUB_CURVE_LIBOR_12M
			);

		org.drip.simm.rates.IRWeight primeRiskWeight =
			org.drip.simm.rates.IRSettingsContainer21.RiskWeight (
				currency,
				org.drip.simm.rates.IRSystemics.SUB_CURVE_PRIME
			);

		org.drip.simm.rates.IRWeight municipalRiskWeight =
			org.drip.simm.rates.IRSettingsContainer21.RiskWeight (
				currency,
				org.drip.simm.rates.IRSystemics.SUB_CURVE_MUNICIPAL
			);

		BucketSensitivitySettingsIR bucketSensitivitySettingsIR =
			org.drip.simm.parameters.BucketSensitivitySettingsIR.ISDA_DELTA_21 (currency);

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
					org.drip.simm.rates.IRSettingsContainer20.SingleCurveTenorCorrelation(),
					org.drip.simm.rates.IRSystemics21.SINGLE_CURRENCY_CROSS_CURVE_CORRELATION,
					irThreshold.deltaVega().vega(),
					java.lang.Math.sqrt (365. / 14.) /
						org.drip.measure.gaussian.NormalQuadrature.InverseCDF (0.99),
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
		catch (java.lang.Exception e)
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
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketVegaSettingsIR (
		final java.util.Map<java.lang.String, java.lang.Double> oisTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor1MTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor3MTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor6MTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor12MTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> primeTenorVegaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> municipalTenorVegaRiskWeight,
		final org.drip.measure.stochastic.LabelCorrelation crossTenorCorrelation,
		final double crossCurveCorrelation,
		final double concentrationThreshold,
		final double vegaScaler,
		final double historicalVolatilityRatio,
		final java.util.Map<java.lang.String, java.lang.Double> oisTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor1MTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor3MTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor6MTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor12MTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> primeTenorDeltaRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> municipalTenorDeltaRiskWeight)
		throws java.lang.Exception
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

		if (!org.drip.quant.common.NumberUtil.IsValid (_vegaScaler = vegaScaler) ||
			!org.drip.quant.common.NumberUtil.IsValid (_historicalVolatilityRatio =
				historicalVolatilityRatio) ||
			null == (_oisTenorDeltaRiskWeight = oisTenorDeltaRiskWeight) ||
			null == (_libor1MTenorDeltaRiskWeight = libor1MTenorDeltaRiskWeight) ||
			null == (_libor3MTenorDeltaRiskWeight = libor3MTenorDeltaRiskWeight) ||
			null == (_libor6MTenorDeltaRiskWeight = libor6MTenorDeltaRiskWeight) ||
			null == (_libor12MTenorDeltaRiskWeight = libor12MTenorDeltaRiskWeight) ||
			null == (_primeTenorDeltaRiskWeight = primeTenorDeltaRiskWeight) ||
			null == (_municipalTenorDeltaRiskWeight = municipalTenorDeltaRiskWeight))
		{
			throw new java.lang.Exception ("BucketVegaSettingsIR Constructor => Invalid Inputs");
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

	public java.util.Map<java.lang.String, java.lang.Double> oisTenorDeltaRiskWeight()
	{
		return _oisTenorDeltaRiskWeight;
	}

	/**
	 * Retrieve the OIS Tenor Vega Risk Weight
	 * 
	 * @return The OIS Tenor Vega Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> oisTenorVegaRiskWeight()
	{
		return super.oisTenorRiskWeight();
	}

	/**
	 * Retrieve the LIBOR 1M Tenor Delta Risk Weight
	 * 
	 * @return The LIBOR 1M Tenor Delta Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor1MTenorDeltaRiskWeight()
	{
		return _libor1MTenorDeltaRiskWeight;
	}

	/**
	 * Retrieve the LIBOR1M Tenor Vega Risk Weight
	 * 
	 * @return The LIBOR1M Tenor Vega Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor1MTenorVegaRiskWeight()
	{
		return super.libor1MTenorRiskWeight();
	}

	/**
	 * Retrieve the LIBOR 3M Tenor Delta Risk Weight
	 * 
	 * @return The LIBOR 3M Tenor Delta Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor3MTenorDeltaRiskWeight()
	{
		return _libor3MTenorDeltaRiskWeight;
	}

	/**
	 * Retrieve the LIBOR3M Tenor Vega Risk Weight
	 * 
	 * @return The LIBOR3M Tenor Vega Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor3MTenorVegaRiskWeight()
	{
		return super.libor3MTenorRiskWeight();
	}

	/**
	 * Retrieve the LIBOR 6M Tenor Delta Risk Weight
	 * 
	 * @return The LIBOR 6M Tenor Delta Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor6MTenorDeltaRiskWeight()
	{
		return _libor6MTenorDeltaRiskWeight;
	}

	/**
	 * Retrieve the LIBOR6M Tenor Vega Risk Weight
	 * 
	 * @return The LIBOR6M Tenor Vega Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor6MTenorVegaRiskWeight()
	{
		return super.libor6MTenorRiskWeight();
	}

	/**
	 * Retrieve the LIBOR 12M Tenor Delta Risk Weight
	 * 
	 * @return The LIBOR 12M Tenor Delta Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor12MTenorDeltaRiskWeight()
	{
		return _libor12MTenorDeltaRiskWeight;
	}

	/**
	 * Retrieve the LIBOR 12M Tenor Vega Risk Weight
	 * 
	 * @return The LIBOR 12M Tenor Vega Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor12MTenorVegaRiskWeight()
	{
		return super.libor12MTenorRiskWeight();
	}

	/**
	 * Retrieve the PRIME Tenor Delta Risk Weight
	 * 
	 * @return The PRIME Tenor Delta Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> primeTenorDeltaRiskWeight()
	{
		return _primeTenorDeltaRiskWeight;
	}

	/**
	 * Retrieve the PRIME Tenor Vega Risk Weight
	 * 
	 * @return The PRIME Tenor Vega Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> primeTenorVegaRiskWeight()
	{
		return super.primeTenorRiskWeight();
	}

	/**
	 * Retrieve the MUNICIPAL Tenor Delta Risk Weight
	 * 
	 * @return The MUNICIPAL Tenor Delta Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> municipalTenorDeltaRiskWeight()
	{
		return _municipalTenorDeltaRiskWeight;
	}

	/**
	 * Retrieve the MUNICIPAL Tenor Vega Risk Weight
	 * 
	 * @return The MUNICIPAL Tenor Vega Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> municipalTenorVegaRiskWeight()
	{
		return super.municipalTenorRiskWeight();
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> oisTenorRiskWeight()
	{
		java.util.Map<java.lang.String, java.lang.Double> oisTenorVegaRiskWeight = oisTenorVegaRiskWeight();

		java.util.Map<java.lang.String, java.lang.Double> oisTenorRiskWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisTenorVegaRiskWeightEntry :
			oisTenorVegaRiskWeight.entrySet())
		{
			java.lang.String tenor = oisTenorVegaRiskWeightEntry.getKey();

			if (!_oisTenorDeltaRiskWeight.containsKey (tenor))
			{
				return null;
			}

			oisTenorRiskWeight.put (
				tenor,
				oisTenorVegaRiskWeightEntry.getValue() * _oisTenorDeltaRiskWeight.get (tenor) * _vegaScaler *
					_historicalVolatilityRatio
			);
		}

		return oisTenorRiskWeight;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> libor1MTenorRiskWeight()
	{
		java.util.Map<java.lang.String, java.lang.Double> libor1MTenorVegaRiskWeight =
			libor1MTenorVegaRiskWeight();

		java.util.Map<java.lang.String, java.lang.Double> libor1MTenorRiskWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MTenorVegaRiskWeightEntry :
			libor1MTenorVegaRiskWeight.entrySet())
		{
			java.lang.String tenor = libor1MTenorVegaRiskWeightEntry.getKey();

			if (!_libor1MTenorDeltaRiskWeight.containsKey (tenor))
			{
				return null;
			}

			libor1MTenorRiskWeight.put (
				tenor,
				libor1MTenorVegaRiskWeightEntry.getValue() * _libor1MTenorDeltaRiskWeight.get (tenor) *
					_vegaScaler * _historicalVolatilityRatio
			);
		}

		return libor1MTenorRiskWeight;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> libor3MTenorRiskWeight()
	{
		java.util.Map<java.lang.String, java.lang.Double> libor3MTenorVegaRiskWeight =
			libor3MTenorVegaRiskWeight();

		java.util.Map<java.lang.String, java.lang.Double> libor3MTenorRiskWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MTenorVegaRiskWeightEntry :
			libor3MTenorVegaRiskWeight.entrySet())
		{
			java.lang.String tenor = libor3MTenorVegaRiskWeightEntry.getKey();

			if (!_libor3MTenorDeltaRiskWeight.containsKey (tenor))
			{
				return null;
			}

			libor3MTenorRiskWeight.put (
				tenor,
				libor3MTenorVegaRiskWeightEntry.getValue() * _libor3MTenorDeltaRiskWeight.get (tenor) *
					_vegaScaler * _historicalVolatilityRatio
			);
		}

		return libor3MTenorRiskWeight;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> libor6MTenorRiskWeight()
	{
		java.util.Map<java.lang.String, java.lang.Double> libor6MTenorVegaRiskWeight =
			libor6MTenorVegaRiskWeight();

		java.util.Map<java.lang.String, java.lang.Double> libor6MTenorRiskWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MTenorVegaRiskWeightEntry :
			libor6MTenorVegaRiskWeight.entrySet())
		{
			java.lang.String tenor = libor6MTenorVegaRiskWeightEntry.getKey();

			if (!_libor6MTenorDeltaRiskWeight.containsKey (tenor))
			{
				return null;
			}

			libor6MTenorRiskWeight.put (
				tenor,
				libor6MTenorVegaRiskWeightEntry.getValue() * _libor6MTenorDeltaRiskWeight.get (tenor) *
					_vegaScaler *_historicalVolatilityRatio
			);
		}

		return libor6MTenorRiskWeight;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> libor12MTenorRiskWeight()
	{
		java.util.Map<java.lang.String, java.lang.Double> libor12MTenorVegaRiskWeight =
			libor12MTenorVegaRiskWeight();

		java.util.Map<java.lang.String, java.lang.Double> libor12MTenorRiskWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MTenorVegaRiskWeightEntry :
			libor12MTenorVegaRiskWeight.entrySet())
		{
			java.lang.String tenor = libor12MTenorVegaRiskWeightEntry.getKey();

			if (!_libor12MTenorDeltaRiskWeight.containsKey (tenor))
			{
				return null;
			}

			libor12MTenorRiskWeight.put (
				tenor,
				libor12MTenorVegaRiskWeightEntry.getValue() * _libor12MTenorDeltaRiskWeight.get (tenor) *
					_vegaScaler *_historicalVolatilityRatio
			);
		}

		return libor12MTenorRiskWeight;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> primeTenorRiskWeight()
	{
		java.util.Map<java.lang.String, java.lang.Double> primeTenorVegaRiskWeight =
			primeTenorVegaRiskWeight();

		java.util.Map<java.lang.String, java.lang.Double> primeTenorRiskWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeTenorVegaRiskWeightEntry :
			primeTenorVegaRiskWeight.entrySet())
		{
			java.lang.String tenor = primeTenorVegaRiskWeightEntry.getKey();

			if (!_primeTenorDeltaRiskWeight.containsKey (tenor))
			{
				return null;
			}

			primeTenorRiskWeight.put (
				tenor,
				primeTenorVegaRiskWeightEntry.getValue() * _primeTenorDeltaRiskWeight.get (tenor) *
					_vegaScaler *_historicalVolatilityRatio
			);
		}

		return primeTenorRiskWeight;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> municipalTenorRiskWeight()
	{
		java.util.Map<java.lang.String, java.lang.Double> municipalTenorVegaRiskWeight =
			super.municipalTenorRiskWeight();

		java.util.Map<java.lang.String, java.lang.Double> municipalTenorRiskWeight = new
			java.util.HashMap<java.lang.String, java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalTenorVegaRiskWeightEntry :
			municipalTenorVegaRiskWeight.entrySet())
		{
			java.lang.String tenor = municipalTenorVegaRiskWeightEntry.getKey();

			if (!_municipalTenorDeltaRiskWeight.containsKey (tenor))
			{
				return null;
			}

			municipalTenorRiskWeight.put (
				tenor,
				municipalTenorVegaRiskWeightEntry.getValue() * _municipalTenorDeltaRiskWeight.get (tenor) *
					_vegaScaler *_historicalVolatilityRatio
			);
		}

		return municipalTenorRiskWeight;
	}
}
