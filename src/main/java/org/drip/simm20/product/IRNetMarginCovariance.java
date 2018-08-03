
package org.drip.simm20.product;

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
 * IRNetMarginCovariance holds the IM Co-variances within a single Currency for each of the IR Risk Factors -
 *  OIS, LIBOR 1M, LIBOR 3M, LIBOR 6M LIBOR 12M, PRIME, and MUNICIPAL. The References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  	Calculations, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488, eSSRN.
 *  
 *  - Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  	Framework for Forecasting Initial Margin Requirements,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279, eSSRN.
 *  
 *  - Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin Requirements
 *  	- A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167, eSSRN.
 *  
 *  - International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology,
 *  	https://www.isda.org/a/oFiDE/isda-simm-v2.pdf.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class IRNetMarginCovariance
{
	private double _ois_ois = java.lang.Double.NaN;
	private double _ois_prime = java.lang.Double.NaN;
	private double _ois_libor1M = java.lang.Double.NaN;
	private double _ois_libor3M = java.lang.Double.NaN;
	private double _ois_libor6M = java.lang.Double.NaN;
	private double _ois_libor12M = java.lang.Double.NaN;
	private double _ois_municipal = java.lang.Double.NaN;

	private double _libor1M_prime = java.lang.Double.NaN;
	private double _libor1M_libor1M = java.lang.Double.NaN;
	private double _libor1M_libor3M = java.lang.Double.NaN;
	private double _libor1M_libor6M = java.lang.Double.NaN;
	private double _libor1M_libor12M = java.lang.Double.NaN;
	private double _libor1M_municipal = java.lang.Double.NaN;

	private double _libor3M_prime = java.lang.Double.NaN;
	private double _libor3M_libor3M = java.lang.Double.NaN;
	private double _libor3M_libor6M = java.lang.Double.NaN;
	private double _libor3M_libor12M = java.lang.Double.NaN;
	private double _libor3M_municipal = java.lang.Double.NaN;

	private double _libor6M_prime = java.lang.Double.NaN;
	private double _libor6M_libor6M = java.lang.Double.NaN;
	private double _libor6M_libor12M = java.lang.Double.NaN;
	private double _libor6M_municipal = java.lang.Double.NaN;

	private double _libor12M_prime = java.lang.Double.NaN;
	private double _libor12M_libor12M = java.lang.Double.NaN;
	private double _libor12M_municipal = java.lang.Double.NaN;

	private double _prime_prime = java.lang.Double.NaN;
	private double _prime_municipal = java.lang.Double.NaN;

	private double _municipal_municipal = java.lang.Double.NaN;

	/**
	 * @param ois_ois The OIS - OIS IM Net Margin Co-variance Entry
	 * @param ois_libor1M The OIS - LIBOR1M IM Net Margin Co-variance Entry
	 * @param ois_libor3M The OIS - LIBOR3M IM Net Margin Co-variance Entry
	 * @param ois_libor6M The OIS - LIBOR6M IM Net Margin Co-variance Entry
	 * @param ois_libor12M The OIS - LIBOR12M IM Net Margin Co-variance Entry
	 * @param ois_prime The OIS - PRIME IM Net Margin Co-variance Entry
	 * @param ois_municipal The OIS - MUNICIPAL IM Net Margin Co-variance Entry
	 * @param libor1M_libor1M The LIBOR1M - LIBOR1M IM Net Margin Co-variance Entry
	 * @param libor1M_libor3M The LIBOR1M - LIBOR3M IM Net Margin Co-variance Entry
	 * @param libor1M_libor6M The LIBOR1M - LIBOR6M IM Net Margin Co-variance Entry
	 * @param libor1M_libor12M The LIBOR1M - LIBOR12M IM Net Margin Co-variance Entry
	 * @param libor1M_prime The LIBOR1M - PRIME IM Net Margin Co-variance Entry
	 * @param libor1M_municipal The LIBOR1M - MUNICIPAL IM Net Margin Co-variance Entry
	 * @param libor3M_libor3M The LIBOR3M - LIBOR3M IM Net Margin Co-variance Entry
	 * @param libor3M_libor6M The LIBOR3M - LIBOR6M IM Net Margin Co-variance Entry
	 * @param libor3M_libor12M The LIBOR3M - LIBOR12M IM Net Margin Co-variance Entry
	 * @param libor3M_prime The LIBOR3M - PRIME IM Net Margin Co-variance Entry
	 * @param libor3M_municipal The LIBOR3M - MUNICIPAL IM Net Margin Co-variance Entry
	 * @param libor6M_libor6M The LIBOR6M - LIBOR6M IM Net Margin Co-variance Entry
	 * @param libor6M_libor12M The LIBOR6M - LIBOR12M IM Net Margin Co-variance Entry
	 * @param libor6M_prime The LIBOR6M - PRIME IM Net Margin Co-variance Entry
	 * @param libor6M_municipal The LIBOR6M - MUNICIPAL IM Net Margin Co-variance Entry
	 * @param libor12M_libor12M The LIBOR12M - LIBOR12M IM Net Margin Co-variance Entry
	 * @param libor12M_prime The LIBOR12M - PRIME IM Net Margin Co-variance Entry
	 * @param libor12M_municipal The LIBOR12M - MUNICIPAL IM Net Margin Co-variance Entry
	 * @param prime_prime The PRIME - PRIME IM Net Margin Co-variance Entry
	 * @param prime_municipal The PRIME - MUNICIPAL IM Net Margin Co-variance Entry
	 * @param municipal_municipal The MUNICIPAL - MUNICIPAL IM Net Margin Co-variance Entry
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public IRNetMarginCovariance (
		final double ois_ois,
		final double ois_libor1M,
		final double ois_libor3M,
		final double ois_libor6M,
		final double ois_libor12M,
		final double ois_prime,
		final double ois_municipal,
		final double libor1M_libor1M,
		final double libor1M_libor3M,
		final double libor1M_libor6M,
		final double libor1M_libor12M,
		final double libor1M_prime,
		final double libor1M_municipal,
		final double libor3M_libor3M,
		final double libor3M_libor6M,
		final double libor3M_libor12M,
		final double libor3M_prime,
		final double libor3M_municipal,
		final double libor6M_libor6M,
		final double libor6M_libor12M,
		final double libor6M_prime,
		final double libor6M_municipal,
		final double libor12M_libor12M,
		final double libor12M_prime,
		final double libor12M_municipal,
		final double prime_prime,
		final double prime_municipal,
		final double municipal_municipal)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_ois_ois = ois_ois) ||
			!org.drip.quant.common.NumberUtil.IsValid (_ois_libor1M = ois_libor1M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_ois_libor3M = ois_libor3M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_ois_libor6M = ois_libor6M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_ois_libor12M = ois_libor12M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_ois_prime = ois_prime) ||
			!org.drip.quant.common.NumberUtil.IsValid (_ois_municipal = ois_municipal) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor1M_libor1M = libor1M_libor1M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor1M_libor3M = libor1M_libor3M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor1M_libor6M = libor1M_libor6M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor1M_libor12M = libor1M_libor12M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor1M_prime = libor1M_prime) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor1M_municipal = libor1M_municipal) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor3M_libor3M = libor3M_libor3M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor3M_libor6M = libor3M_libor6M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor3M_libor12M = libor3M_libor12M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor3M_prime = libor3M_prime) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor3M_municipal = libor3M_municipal) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor6M_libor6M = libor6M_libor6M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor6M_libor12M = libor6M_libor12M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor6M_prime = libor6M_prime) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor6M_municipal = libor6M_municipal) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor12M_libor12M = libor12M_libor12M) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor12M_prime = libor12M_prime) ||
			!org.drip.quant.common.NumberUtil.IsValid (_libor12M_municipal = libor12M_municipal) ||
			!org.drip.quant.common.NumberUtil.IsValid (_prime_prime = prime_prime) ||
			!org.drip.quant.common.NumberUtil.IsValid (_prime_municipal = prime_municipal) ||
			!org.drip.quant.common.NumberUtil.IsValid (_municipal_municipal = municipal_municipal))
		{
			throw new java.lang.Exception ("IRNetMarginCovariance Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the OIS - OIS IM Net Margin Co-variance Entry
	 * 
	 * @return The OIS - OIS IM Net Margin Co-variance Entry
	 */

	public double ois_ois()
	{
		return _ois_ois;
	}

	/**
	 * Retrieve the OIS - LIBOR1M IM Net Margin Co-variance Entry
	 * 
	 * @return The OIS - LIBOR1M IM Net Margin Co-variance Entry
	 */

	public double ois_libor1M()
	{
		return _ois_libor1M;
	}

	/**
	 * Retrieve the OIS - LIBOR3M IM Net Margin Co-variance Entry
	 * 
	 * @return The OIS - LIBOR3M IM Net Margin Co-variance Entry
	 */

	public double ois_libor3M()
	{
		return _ois_libor3M;
	}

	/**
	 * Retrieve the OIS - LIBOR6M IM Net Margin Co-variance Entry
	 * 
	 * @return The OIS - LIBOR6M IM Net Margin Co-variance Entry
	 */

	public double ois_libor6M()
	{
		return _ois_libor6M;
	}

	/**
	 * Retrieve the OIS - LIBOR12M IM Net Margin Co-variance Entry
	 * 
	 * @return The OIS - LIBOR12M IM Net Margin Co-variance Entry
	 */

	public double ois_libor12M()
	{
		return _ois_libor12M;
	}

	/**
	 * Retrieve the OIS - PRIME IM Net Margin Co-variance Entry
	 * 
	 * @return The OIS - PRIME IM Net Margin Co-variance Entry
	 */

	public double ois_prime()
	{
		return _ois_prime;
	}

	/**
	 * Retrieve the OIS - MUNICIPAL IM Net Margin Co-variance Entry
	 * 
	 * @return The OIS - MUNICIPAL IM Net Margin Co-variance Entry
	 */

	public double ois_municipal()
	{
		return _ois_municipal;
	}

	/**
	 * Retrieve the LIBOR1M - LIBOR1M IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR1M - LIBOR1M IM Net Margin Co-variance Entry
	 */

	public double libor1M_libor1M()
	{
		return _libor1M_libor1M;
	}

	/**
	 * Retrieve the LIBOR1M - LIBOR3M IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR1M - LIBOR3M IM Net Margin Co-variance Entry
	 */

	public double libor1M_libor3M()
	{
		return _libor1M_libor3M;
	}

	/**
	 * Retrieve the LIBOR1M - LIBOR6M IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR1M - LIBOR6M IM Net Margin Co-variance Entry
	 */

	public double libor1M_libor6M()
	{
		return _libor1M_libor6M;
	}

	/**
	 * Retrieve the LIBOR1M - LIBOR12M IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR1M - LIBOR12M IM Net Margin Co-variance Entry
	 */

	public double libor1M_libor12M()
	{
		return _libor1M_libor12M;
	}

	/**
	 * Retrieve the LIBOR1M - PRIME IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR1M - PRIME IM Net Margin Co-variance Entry
	 */

	public double libor1M_prime()
	{
		return _libor1M_prime;
	}

	/**
	 * Retrieve the LIBOR1M - MUNICIPAL IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR1M - MUNICIPAL IM Net Margin Co-variance Entry
	 */

	public double libor1M_municipal()
	{
		return _libor1M_municipal;
	}

	/**
	 * Retrieve the LIBOR3M - LIBOR3M IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR3M - LIBOR3M IM Net Margin Co-variance Entry
	 */

	public double libor3M_libor3M()
	{
		return _libor3M_libor3M;
	}

	/**
	 * Retrieve the LIBOR3M - LIBOR6M IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR3M - LIBOR6M IM Net Margin Co-variance Entry
	 */

	public double libor3M_libor6M()
	{
		return _libor3M_libor6M;
	}

	/**
	 * Retrieve the LIBOR3M - LIBOR12M IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR3M - LIBOR12M IM Net Margin Co-variance Entry
	 */

	public double libor3M_libor12M()
	{
		return _libor3M_libor12M;
	}

	/**
	 * Retrieve the LIBOR3M - PRIME IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR3M - PRIME IM Net Margin Co-variance Entry
	 */

	public double libor3M_prime()
	{
		return _libor3M_prime;
	}

	/**
	 * Retrieve the LIBOR3M - MUNICIPAL IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR3M - MUNICIPAL IM Net Margin Co-variance Entry
	 */

	public double libor3M_municipal()
	{
		return _libor3M_municipal;
	}

	/**
	 * Retrieve the LIBOR6M - LIBOR6M IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR6M - LIBOR6M IM Net Margin Co-variance Entry
	 */

	public double libor6M_libor6M()
	{
		return _libor6M_libor6M;
	}

	/**
	 * Retrieve the LIBOR6M - LIBOR12M IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR6M - LIBOR12M IM Net Margin Co-variance Entry
	 */

	public double libor6M_libor12M()
	{
		return _libor6M_libor12M;
	}

	/**
	 * Retrieve the LIBOR6M - PRIME IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR6M - PRIME IM Net Margin Co-variance Entry
	 */

	public double libor6M_prime()
	{
		return _libor6M_prime;
	}

	/**
	 * Retrieve the LIBOR6M - MUNICIPAL IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR6M - MUNICIPAL IM Net Margin Co-variance Entry
	 */

	public double libor6M_municipal()
	{
		return _libor6M_municipal;
	}

	/**
	 * Retrieve the LIBOR12M - LIBOR12M IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR12M - LIBOR12M IM Net Margin Co-variance Entry
	 */

	public double libor12M_libor12M()
	{
		return _libor12M_libor12M;
	}

	/**
	 * Retrieve the LIBOR12M - PRIME IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR12M - PRIME IM Net Margin Co-variance Entry
	 */

	public double libor12M_prime()
	{
		return _libor12M_prime;
	}

	/**
	 * Retrieve the LIBOR12M - MUNICIPAL IM Net Margin Co-variance Entry
	 * 
	 * @return The LIBOR12M - MUNICIPAL IM Net Margin Co-variance Entry
	 */

	public double libor12M_municipal()
	{
		return _libor12M_municipal;
	}

	/**
	 * Retrieve the PRIME - PRIME IM Net Margin Co-variance Entry
	 * 
	 * @return The PRIME - PRIME IM Net Margin Co-variance Entry
	 */

	public double prime_prime()
	{
		return _prime_prime;
	}

	/**
	 * Retrieve the PRIME - MUNICIPAL IM Net Margin Co-variance Entry
	 * 
	 * @return The PRIME - MUNICIPAL IM Net Margin Co-variance Entry
	 */

	public double prime_municipal()
	{
		return _prime_municipal;
	}

	/**
	 * Retrieve the MUNICIPAL - MUNICIPAL IM Net Margin Co-variance Entry
	 * 
	 * @return The MUNICIPAL - MUNICIPAL IM Net Margin Co-variance Entry
	 */

	public double municipal_municipal()
	{
		return _municipal_municipal;
	}
}
