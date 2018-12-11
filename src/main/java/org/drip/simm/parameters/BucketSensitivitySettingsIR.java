
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
 * <i>BucketSensitivitySettingsIR</i> holds the Delta Risk Weights, Concentration Thresholds, and
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm">SIMM</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/parameters">Parameters</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BucketSensitivitySettingsIR extends org.drip.simm.parameters.LiquiditySettings
{
	private double _crossCurveCorrelation = java.lang.Double.NaN;
	private org.drip.measure.stochastic.LabelCorrelation _crossTenorCorrelation = null;
	private java.util.Map<java.lang.String, java.lang.Double> _oisTenorRiskWeight = null;
	private java.util.Map<java.lang.String, java.lang.Double> _primeTenorRiskWeight = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor1MTenorRiskWeight = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor3MTenorRiskWeight = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor6MTenorRiskWeight = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor12MTenorRiskWeight = null;
	private java.util.Map<java.lang.String, java.lang.Double> _municipalTenorRiskWeight = null;

	/**
	 * Construct the ISDA 2.0 Standard IR Delta Sensitivity Settings for the Currency
	 * 
	 * @param currency Currency
	 * 
	 * @return The ISDA 2.0 Standard IR Delta Sensitivity Settings for the Currency
	 */

	public static final BucketSensitivitySettingsIR ISDA_DELTA_20 (
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

		try
		{
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
					org.drip.simm.rates.IRSettingsContainer20.SingleCurveTenorCorrelation(),
					org.drip.simm.rates.IRSystemics20.SINGLE_CURRENCY_CROSS_CURVE_CORRELATION,
					irThreshold.deltaVega().delta()
				);
		}
		catch (java.lang.Exception e)
		{
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

		try
		{
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
					org.drip.simm.rates.IRSettingsContainer21.SingleCurveTenorCorrelation(),
					org.drip.simm.rates.IRSystemics21.SINGLE_CURRENCY_CROSS_CURVE_CORRELATION,
					irThreshold.deltaVega().delta()
				);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BucketSensitivitySettingsIR Constructor
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
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketSensitivitySettingsIR (
		final java.util.Map<java.lang.String, java.lang.Double> oisTenorRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor1MTenorRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor3MTenorRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor6MTenorRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> libor12MTenorRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> primeTenorRiskWeight,
		final java.util.Map<java.lang.String, java.lang.Double> municipalTenorRiskWeight,
		final org.drip.measure.stochastic.LabelCorrelation crossTenorCorrelation,
		final double crossCurveCorrelation,
		final double concentrationThreshold)
		throws java.lang.Exception
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
			!org.drip.quant.common.NumberUtil.IsValid (_crossCurveCorrelation = crossCurveCorrelation) ||
				-1. > _crossCurveCorrelation || 1. < _crossCurveCorrelation)
		{
			throw new java.lang.Exception ("BucketSensitivitySettingsIR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the OIS Tenor Risk Weight
	 * 
	 * @return The OIS Tenor Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> oisTenorRiskWeight()
	{
		return _oisTenorRiskWeight;
	}

	/**
	 * Retrieve the LIBOR 1M Tenor Risk Weight
	 * 
	 * @return The LIBOR 1M Tenor Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor1MTenorRiskWeight()
	{
		return _libor1MTenorRiskWeight;
	}

	/**
	 * Retrieve the LIBOR 3M Tenor Risk Weight
	 * 
	 * @return The LIBOR 3M Tenor Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor3MTenorRiskWeight()
	{
		return _libor3MTenorRiskWeight;
	}

	/**
	 * Retrieve the LIBOR 6M Tenor Risk Weight
	 * 
	 * @return The LIBOR 6M Tenor Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor6MTenorRiskWeight()
	{
		return _libor6MTenorRiskWeight;
	}

	/**
	 * Retrieve the LIBOR 12M Tenor Risk Weight
	 * 
	 * @return The LIBOR 12M Tenor Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor12MTenorRiskWeight()
	{
		return _libor12MTenorRiskWeight;
	}

	/**
	 * Retrieve the PRIME Tenor Risk Weight
	 * 
	 * @return The PRIME Tenor Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> primeTenorRiskWeight()
	{
		return _primeTenorRiskWeight;
	}

	/**
	 * Retrieve the MUNICIPAL Curve Tenor Risk Weight
	 * 
	 * @return The MUNICIPAL Curve Tenor Risk Weight
	 */

	public java.util.Map<java.lang.String, java.lang.Double> municipalTenorRiskWeight()
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

	public org.drip.measure.stochastic.LabelCorrelation crossTenorCorrelation()
	{
		return _crossTenorCorrelation;
	}
}
