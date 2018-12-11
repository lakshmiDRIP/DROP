
package org.drip.simm.margin;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * <i>SensitivityAggregateIR</i> holds the IM Margin Sensitivity Co-variances within a single Currency for
 * each of the IR Risk Factors - OIS, LIBOR 1M, LIBOR 3M, LIBOR 6M LIBOR 12M, PRIME, and MUNICIPAL. The
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm">SIMM</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin">Margin</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SensitivityAggregateIR
{
	private double _marginCovariance_OIS_OIS = java.lang.Double.NaN;
	private double _marginCovariance_OIS_PRIME = java.lang.Double.NaN;
	private double _marginCovariance_OIS_LIBOR1M = java.lang.Double.NaN;
	private double _marginCovariance_OIS_LIBOR3M = java.lang.Double.NaN;
	private double _marginCovariance_OIS_LIBOR6M = java.lang.Double.NaN;
	private double _marginCovariance_OIS_LIBOR12M = java.lang.Double.NaN;
	private double _marginCovariance_OIS_MUNICIPAL = java.lang.Double.NaN;

	private double _marginCovariance_LIBOR1M_PRIME = java.lang.Double.NaN;
	private double _marginCovariance_LIBOR1M_LIBOR1M = java.lang.Double.NaN;
	private double _marginCovariance_LIBOR1M_LIBOR3M = java.lang.Double.NaN;
	private double _marginCovariance_LIBOR1M_LIBOR6M = java.lang.Double.NaN;
	private double _marginCovariance_LIBOR1M_LIBOR12M = java.lang.Double.NaN;
	private double _marginCovariance_LIBOR1M_MUNICIPAL = java.lang.Double.NaN;

	private double _marginCovariance_LIBOR3M_PRIME = java.lang.Double.NaN;
	private double _marginCovariance_LIBOR3M_LIBOR3M = java.lang.Double.NaN;
	private double _marginCovariance_LIBOR3M_LIBOR6M = java.lang.Double.NaN;
	private double _marginCovariance_LIBOR3M_LIBOR12M = java.lang.Double.NaN;
	private double _marginCovariance_LIBOR3M_MUNICIPAL = java.lang.Double.NaN;

	private double _marginCovariance_LIBOR6M_PRIME = java.lang.Double.NaN;
	private double _marginCovariance_LIBOR6M_LIBOR6M = java.lang.Double.NaN;
	private double _marginCovariance_LIBOR6M_LIBOR12M = java.lang.Double.NaN;
	private double _marginCovariance_LIBOR6M_MUNICIPAL = java.lang.Double.NaN;

	private double _marginCovariance_LIBOR12M_PRIME = java.lang.Double.NaN;
	private double _marginCovariance_LIBOR12M_LIBOR12M = java.lang.Double.NaN;
	private double _marginCovariance_LIBOR12M_MUNICIPAL = java.lang.Double.NaN;

	private double _marginCovariance_PRIME_PRIME = java.lang.Double.NaN;
	private double _marginCovariance_PRIME_MUNICIPAL = java.lang.Double.NaN;

	private double _marginCovariance_MUNICIPAL_MUNICIPAL = java.lang.Double.NaN;

	private double _cumulativeMarginSensitivity = java.lang.Double.NaN;

	/**
	 * SensitivityAggregateIR Constructor
	 * 
	 * @param marginCovariance_OIS_OIS The OIS - OIS Margin Co-variance
	 * @param marginCovariance_OIS_LIBOR1M The OIS - LIBOR1M Margin Co-variance
	 * @param marginCovariance_OIS_LIBOR3M The OIS - LIBOR3M Margin Co-variance
	 * @param marginCovariance_OIS_LIBOR6M The OIS - LIBOR6M Margin Co-variance
	 * @param marginCovariance_OIS_LIBOR12M The OIS - LIBOR12M Margin Co-variance
	 * @param marginCovariance_OIS_PRIME The OIS - PRIME Margin Co-variance
	 * @param marginCovariance_OIS_MUNICIPAL The OIS - MUNICIPAL Margin Co-variance
	 * @param marginCovariance_LIBOR1M_LIBOR1M The LIBOR1M - LIBOR1M Margin Co-variance
	 * @param marginCovariance_LIBOR1M_LIBOR3M The LIBOR1M - LIBOR3M Margin Co-variance
	 * @param marginCovariance_LIBOR1M_LIBOR6M The LIBOR1M - LIBOR6M Margin Co-variance
	 * @param marginCovariance_LIBOR1M_LIBOR12M The LIBOR1M - LIBOR12M Margin Co-variance
	 * @param marginCovariance_LIBOR1M_PRIME The LIBOR1M - PRIME Margin Co-variance
	 * @param marginCovariance_LIBOR1M_MUNICIPAL The LIBOR1M - MUNICIPAL Margin Co-variance
	 * @param marginCovariance_LIBOR3M_LIBOR3M The LIBOR3M - LIBOR3M Margin Co-variance
	 * @param marginCovariance_LIBOR3M_LIBOR6M The LIBOR3M - LIBOR6M Margin Co-variance
	 * @param marginCovariance_LIBOR3M_LIBOR12M The LIBOR3M - LIBOR12M Margin Co-variance
	 * @param marginCovariance_LIBOR3M_PRIME The LIBOR3M - PRIME Margin Co-variance
	 * @param marginCovariance_LIBOR3M_MUNICIPAL The LIBOR3M - MUNICIPAL Margin Co-variance
	 * @param marginCovariance_LIBOR6M_LIBOR6M The LIBOR6M - LIBOR6M Margin Co-variance
	 * @param marginCovariance_LIBOR6M_LIBOR12M The LIBOR6M - LIBOR12M Margin Co-variance
	 * @param marginCovariance_LIBOR6M_PRIME The LIBOR6M - PRIME Margin Co-variance
	 * @param marginCovariance_LIBOR6M_MUNICIPAL The LIBOR6M - MUNICIPAL Margin Co-variance
	 * @param marginCovariance_LIBOR12M_LIBOR12M The LIBOR12M - LIBOR12M Margin Co-variance
	 * @param marginCovariance_LIBOR12M_PRIME The LIBOR12M - PRIME Margin Co-variance
	 * @param marginCovariance_LIBOR12M_MUNICIPAL The LIBOR12M - MUNICIPAL Margin Co-variance
	 * @param marginCovariance_PRIME_PRIME The PRIME - PRIME Margin Co-variance
	 * @param marginCovariance_PRIME_MUNICIPAL The PRIME - MUNICIPAL Margin Co-variance
	 * @param marginCovariance_MUNICIPAL_MUNICIPAL The MUNICIPAL - MUNICIPAL Margin Co-variance
	 * @param cumulativeMarginSensitivity The Cumulative Margin Sensitivity
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SensitivityAggregateIR (
		final double marginCovariance_OIS_OIS,
		final double marginCovariance_OIS_LIBOR1M,
		final double marginCovariance_OIS_LIBOR3M,
		final double marginCovariance_OIS_LIBOR6M,
		final double marginCovariance_OIS_LIBOR12M,
		final double marginCovariance_OIS_PRIME,
		final double marginCovariance_OIS_MUNICIPAL,
		final double marginCovariance_LIBOR1M_LIBOR1M,
		final double marginCovariance_LIBOR1M_LIBOR3M,
		final double marginCovariance_LIBOR1M_LIBOR6M,
		final double marginCovariance_LIBOR1M_LIBOR12M,
		final double marginCovariance_LIBOR1M_PRIME,
		final double marginCovariance_LIBOR1M_MUNICIPAL,
		final double marginCovariance_LIBOR3M_LIBOR3M,
		final double marginCovariance_LIBOR3M_LIBOR6M,
		final double marginCovariance_LIBOR3M_LIBOR12M,
		final double marginCovariance_LIBOR3M_PRIME,
		final double marginCovariance_LIBOR3M_MUNICIPAL,
		final double marginCovariance_LIBOR6M_LIBOR6M,
		final double marginCovariance_LIBOR6M_LIBOR12M,
		final double marginCovariance_LIBOR6M_PRIME,
		final double marginCovariance_LIBOR6M_MUNICIPAL,
		final double marginCovariance_LIBOR12M_LIBOR12M,
		final double marginCovariance_LIBOR12M_PRIME,
		final double marginCovariance_LIBOR12M_MUNICIPAL,
		final double marginCovariance_PRIME_PRIME,
		final double marginCovariance_PRIME_MUNICIPAL,
		final double marginCovariance_MUNICIPAL_MUNICIPAL,
		final double cumulativeMarginSensitivity)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_OIS_OIS =
				marginCovariance_OIS_OIS) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_OIS_LIBOR1M =
				marginCovariance_OIS_LIBOR1M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_OIS_LIBOR3M =
				marginCovariance_OIS_LIBOR3M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_OIS_LIBOR6M =
				marginCovariance_OIS_LIBOR6M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_OIS_LIBOR12M =
				marginCovariance_OIS_LIBOR12M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_OIS_PRIME =
				marginCovariance_OIS_PRIME) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_OIS_MUNICIPAL =
				marginCovariance_OIS_MUNICIPAL) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR1M_LIBOR1M =
				marginCovariance_LIBOR1M_LIBOR1M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR1M_LIBOR3M =
				marginCovariance_LIBOR1M_LIBOR3M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR1M_LIBOR6M =
				marginCovariance_LIBOR1M_LIBOR6M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR1M_LIBOR12M =
				marginCovariance_LIBOR1M_LIBOR12M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR1M_PRIME =
				marginCovariance_LIBOR1M_PRIME) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR1M_MUNICIPAL =
				marginCovariance_LIBOR1M_MUNICIPAL) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR3M_LIBOR3M =
				marginCovariance_LIBOR3M_LIBOR3M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR3M_LIBOR6M =
				marginCovariance_LIBOR3M_LIBOR6M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR3M_LIBOR12M =
				marginCovariance_LIBOR3M_LIBOR12M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR3M_PRIME =
				marginCovariance_LIBOR3M_PRIME) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR3M_MUNICIPAL =
				marginCovariance_LIBOR3M_MUNICIPAL) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR6M_LIBOR6M =
				marginCovariance_LIBOR6M_LIBOR6M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR6M_LIBOR12M =
				marginCovariance_LIBOR6M_LIBOR12M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR6M_PRIME =
				marginCovariance_LIBOR6M_PRIME) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR6M_MUNICIPAL =
				marginCovariance_LIBOR6M_MUNICIPAL) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR12M_LIBOR12M =
				marginCovariance_LIBOR12M_LIBOR12M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR12M_PRIME =
				marginCovariance_LIBOR12M_PRIME) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_LIBOR12M_MUNICIPAL =
				marginCovariance_LIBOR12M_MUNICIPAL) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_PRIME_PRIME =
				marginCovariance_PRIME_PRIME) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_PRIME_MUNICIPAL =
				marginCovariance_PRIME_MUNICIPAL) ||
			!org.drip.quant.common.NumberUtil.IsValid (_marginCovariance_MUNICIPAL_MUNICIPAL =
				marginCovariance_MUNICIPAL_MUNICIPAL) ||
			!org.drip.quant.common.NumberUtil.IsValid (_cumulativeMarginSensitivity =
				cumulativeMarginSensitivity))
		{
			throw new java.lang.Exception ("SensitivityAggregateIR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the OIS - OIS Margin Co-variance
	 * 
	 * @return The OIS - OIS Margin Co-variance
	 */

	public double marginCovariance_OIS_OIS()
	{
		return _marginCovariance_OIS_OIS;
	}

	/**
	 * Retrieve the OIS - LIBOR1M Margin Co-variance
	 * 
	 * @return The OIS - LIBOR1M Margin Co-variance
	 */

	public double marginCovariance_OIS_LIBOR1M()
	{
		return _marginCovariance_OIS_LIBOR1M;
	}

	/**
	 * Retrieve the OIS - LIBOR3M Margin Co-variance
	 * 
	 * @return The OIS - LIBOR3M Margin Co-variance
	 */

	public double marginCovariance_OIS_LIBOR3M()
	{
		return _marginCovariance_OIS_LIBOR3M;
	}

	/**
	 * Retrieve the OIS - LIBOR6M Margin Co-variance
	 * 
	 * @return The OIS - LIBOR6M Margin Co-variance
	 */

	public double marginCovariance_OIS_LIBOR6M()
	{
		return _marginCovariance_OIS_LIBOR6M;
	}

	/**
	 * Retrieve the OIS - LIBOR12M Margin Co-variance
	 * 
	 * @return The OIS - LIBOR12M Margin Co-variance
	 */

	public double marginCovariance_OIS_LIBOR12M()
	{
		return _marginCovariance_OIS_LIBOR12M;
	}

	/**
	 * Retrieve the OIS - PRIME Margin Co-variance
	 * 
	 * @return The OIS - PRIME Margin Co-variance
	 */

	public double marginCovariance_OIS_PRIME()
	{
		return _marginCovariance_OIS_PRIME;
	}

	/**
	 * Retrieve the OIS - MUNICIPAL Margin Co-variance
	 * 
	 * @return The OIS - MUNICIPAL Margin Co-variance
	 */

	public double marginCovariance_OIS_MUNICIPAL()
	{
		return _marginCovariance_OIS_MUNICIPAL;
	}

	/**
	 * Retrieve the LIBOR1M - LIBOR1M Margin Co-variance
	 * 
	 * @return The LIBOR1M - LIBOR1M Margin Co-variance
	 */

	public double marginCovariance_LIBOR1M_LIBOR1M()
	{
		return _marginCovariance_LIBOR1M_LIBOR1M;
	}

	/**
	 * Retrieve the LIBOR1M - LIBOR3M Margin Co-variance
	 * 
	 * @return The LIBOR1M - LIBOR3M Margin Co-variance
	 */

	public double marginCovariance_LIBOR1M_LIBOR3M()
	{
		return _marginCovariance_LIBOR1M_LIBOR3M;
	}

	/**
	 * Retrieve the LIBOR1M - LIBOR6M Margin Co-variance
	 * 
	 * @return The LIBOR1M - LIBOR6M Margin Co-variance
	 */

	public double marginCovariance_LIBOR1M_LIBOR6M()
	{
		return _marginCovariance_LIBOR1M_LIBOR6M;
	}

	/**
	 * Retrieve the LIBOR1M - LIBOR12M Margin Co-variance
	 * 
	 * @return The LIBOR1M - LIBOR12M Margin Co-variance
	 */

	public double marginCovariance_LIBOR1M_LIBOR12M()
	{
		return _marginCovariance_LIBOR1M_LIBOR12M;
	}

	/**
	 * Retrieve the LIBOR1M - PRIME Margin Co-variance
	 * 
	 * @return The LIBOR1M - PRIME Margin Co-variance
	 */

	public double marginCovariance_LIBOR1M_PRIME()
	{
		return _marginCovariance_LIBOR1M_PRIME;
	}

	/**
	 * Retrieve the LIBOR1M - MUNICIPAL Margin Co-variance
	 * 
	 * @return The LIBOR1M - MUNICIPAL Margin Co-variance
	 */

	public double marginCovariance_LIBOR1M_MUNICIPAL()
	{
		return _marginCovariance_LIBOR1M_MUNICIPAL;
	}

	/**
	 * Retrieve the LIBOR3M - LIBOR3M Margin Co-variance
	 * 
	 * @return The LIBOR3M - LIBOR3M Margin Co-variance
	 */

	public double marginCovariance_LIBOR3M_LIBOR3M()
	{
		return _marginCovariance_LIBOR3M_LIBOR3M;
	}

	/**
	 * Retrieve the LIBOR3M - LIBOR6M Margin Co-variance
	 * 
	 * @return The LIBOR3M - LIBOR6M Margin Co-variance
	 */

	public double marginCovariance_LIBOR3M_LIBOR6M()
	{
		return _marginCovariance_LIBOR3M_LIBOR6M;
	}

	/**
	 * Retrieve the LIBOR3M - LIBOR12M Margin Co-variance
	 * 
	 * @return The LIBOR3M - LIBOR12M Margin Co-variance
	 */

	public double marginCovariance_LIBOR3M_LIBOR12M()
	{
		return _marginCovariance_LIBOR3M_LIBOR12M;
	}

	/**
	 * Retrieve the LIBOR3M - PRIME Margin Co-variance
	 * 
	 * @return The LIBOR3M - PRIME Margin Co-variance
	 */

	public double marginCovariance_LIBOR3M_PRIME()
	{
		return _marginCovariance_LIBOR3M_PRIME;
	}

	/**
	 * Retrieve the LIBOR3M - MUNICIPAL Margin Co-variance
	 * 
	 * @return The LIBOR3M - MUNICIPAL Margin Co-variance
	 */

	public double marginCovariance_LIBOR3M_MUNICIPAL()
	{
		return _marginCovariance_LIBOR3M_MUNICIPAL;
	}

	/**
	 * Retrieve the LIBOR6M - LIBOR6M Margin Co-variance
	 * 
	 * @return The LIBOR6M - LIBOR6M Margin Co-variance
	 */

	public double marginCovariance_LIBOR6M_LIBOR6M()
	{
		return _marginCovariance_LIBOR6M_LIBOR6M;
	}

	/**
	 * Retrieve the LIBOR6M - LIBOR12M Margin Co-variance
	 * 
	 * @return The LIBOR6M - LIBOR12M Margin Co-variance
	 */

	public double marginCovariance_LIBOR6M_LIBOR12M()
	{
		return _marginCovariance_LIBOR6M_LIBOR12M;
	}

	/**
	 * Retrieve the LIBOR6M - PRIME Margin Co-variance
	 * 
	 * @return The LIBOR6M - PRIME Margin Co-variance
	 */

	public double marginCovariance_LIBOR6M_PRIME()
	{
		return _marginCovariance_LIBOR6M_PRIME;
	}

	/**
	 * Retrieve the LIBOR6M - MUNICIPAL Margin Co-variance
	 * 
	 * @return The LIBOR6M - MUNICIPAL Margin Co-variance
	 */

	public double marginCovariance_LIBOR6M_MUNICIPAL()
	{
		return _marginCovariance_LIBOR6M_MUNICIPAL;
	}

	/**
	 * Retrieve the LIBOR12M - LIBOR12M Margin Co-variance
	 * 
	 * @return The LIBOR12M - LIBOR12M Margin Co-variance
	 */

	public double marginCovariance_LIBOR12M_LIBOR12M()
	{
		return _marginCovariance_LIBOR12M_LIBOR12M;
	}

	/**
	 * Retrieve the LIBOR12M - PRIME Margin Co-variance
	 * 
	 * @return The LIBOR12M - PRIME Margin Co-variance
	 */

	public double marginCovariance_LIBOR12M_PRIME()
	{
		return _marginCovariance_LIBOR12M_PRIME;
	}

	/**
	 * Retrieve the LIBOR12M - MUNICIPAL Margin Co-variance
	 * 
	 * @return The LIBOR12M - MUNICIPAL Margin Co-variance
	 */

	public double marginCovariance_LIBOR12M_MUNICIPAL()
	{
		return _marginCovariance_LIBOR12M_MUNICIPAL;
	}

	/**
	 * Retrieve the PRIME - PRIME Margin Co-variance
	 * 
	 * @return The PRIME - PRIME Margin Co-variance
	 */

	public double marginCovariance_PRIME_PRIME()
	{
		return _marginCovariance_PRIME_PRIME;
	}

	/**
	 * Retrieve the PRIME - MUNICIPAL Margin Co-variance
	 * 
	 * @return The PRIME - MUNICIPAL Margin Co-variance
	 */

	public double marginCovariance_PRIME_MUNICIPAL()
	{
		return _marginCovariance_PRIME_MUNICIPAL;
	}

	/**
	 * Retrieve the MUNICIPAL - MUNICIPAL Margin Co-variance
	 * 
	 * @return The MUNICIPAL - MUNICIPAL Margin Co-variance
	 */

	public double marginCovariance_MUNICIPAL_MUNICIPAL()
	{
		return _marginCovariance_MUNICIPAL_MUNICIPAL;
	}

	/**
	 * Compute the Cumulative Margin Covariance
	 * 
	 * @return The Cumulative Margin Covariance
	 */

	public double cumulativeMarginCovariance()
	{
		return _marginCovariance_OIS_OIS +
			_marginCovariance_OIS_PRIME +
			_marginCovariance_OIS_LIBOR1M +
			_marginCovariance_OIS_LIBOR3M +
			_marginCovariance_OIS_LIBOR6M + 
			_marginCovariance_OIS_LIBOR12M +
			_marginCovariance_OIS_MUNICIPAL +
			_marginCovariance_LIBOR1M_PRIME +
			_marginCovariance_LIBOR1M_LIBOR1M +
			_marginCovariance_LIBOR1M_LIBOR3M +
			_marginCovariance_LIBOR1M_LIBOR6M +
			_marginCovariance_LIBOR1M_LIBOR12M +
			_marginCovariance_LIBOR1M_MUNICIPAL +
			_marginCovariance_LIBOR3M_PRIME +
			_marginCovariance_LIBOR3M_LIBOR3M +
			_marginCovariance_LIBOR3M_LIBOR6M +
			_marginCovariance_LIBOR3M_LIBOR12M +
			_marginCovariance_LIBOR3M_MUNICIPAL +
			_marginCovariance_LIBOR6M_PRIME +
			_marginCovariance_LIBOR6M_LIBOR6M +
			_marginCovariance_LIBOR6M_LIBOR12M +
			_marginCovariance_LIBOR6M_MUNICIPAL +
			_marginCovariance_LIBOR12M_PRIME +
			_marginCovariance_LIBOR12M_LIBOR12M +
			_marginCovariance_LIBOR12M_MUNICIPAL +
			_marginCovariance_PRIME_PRIME +
			_marginCovariance_PRIME_MUNICIPAL +
			_marginCovariance_MUNICIPAL_MUNICIPAL;
	}

	/**
	 * Compute the Cumulative Sensitivity Margin
	 * 
	 * @return The Cumulative Sensitivity Margin
	 */

	public double cumulativeMargin()
	{
		return java.lang.Math.sqrt (cumulativeMarginCovariance());
	}

	/**
	 * Retrieve the Cumulative Margin Sensitivity
	 * 
	 * @return The Cumulative Margin Sensitivity
	 */

	public double cumulativeMarginSensitivity()
	{
		return _cumulativeMarginSensitivity;
	}
}
