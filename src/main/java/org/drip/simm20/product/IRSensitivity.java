
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
 * IRSensitivity holds the ISDA SIMM 2.0 Risk Factor Tenor Bucket Sensitivities across IR Factor Sub Curves.
 *  USD Exposures enhanced with the USD specific Sub-Curve Factors - PRIME and MUNICIPAL. The References are:
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

public class IRSensitivity
{
	private org.drip.simm20.product.RiskFactorTenorSensitivity _ois = null;
	private org.drip.simm20.product.RiskFactorTenorSensitivity _prime = null;
	private org.drip.simm20.product.RiskFactorTenorSensitivity _libor1M = null;
	private org.drip.simm20.product.RiskFactorTenorSensitivity _libor3M = null;
	private org.drip.simm20.product.RiskFactorTenorSensitivity _libor6M = null;
	private org.drip.simm20.product.RiskFactorTenorSensitivity _libor12M = null;
	private org.drip.simm20.product.RiskFactorTenorSensitivity _municipal = null;

	/**
	 * IRSensitivity Constructor
	 * 
	 * @param ois The OIS Risk Factor Sensitivity
	 * @param libor1M The LIBOR-1M Risk Factor Sensitivity
	 * @param libor3M The LIBOR-3M Risk Factor Sensitivity
	 * @param libor6M The LIBOR-6M Risk Factor Sensitivity
	 * @param libor12M The LIBOR-12M Risk Factor Sensitivity
	 * @param prime The PRIME Risk Factor Sensitivity
	 * @param municipal The MUNICIPAL Risk Factor Sensitivity
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public IRSensitivity (
		final org.drip.simm20.product.RiskFactorTenorSensitivity ois,
		final org.drip.simm20.product.RiskFactorTenorSensitivity libor1M,
		final org.drip.simm20.product.RiskFactorTenorSensitivity libor3M,
		final org.drip.simm20.product.RiskFactorTenorSensitivity libor6M,
		final org.drip.simm20.product.RiskFactorTenorSensitivity libor12M,
		final org.drip.simm20.product.RiskFactorTenorSensitivity prime,
		final org.drip.simm20.product.RiskFactorTenorSensitivity municipal)
		throws java.lang.Exception
	{
		if (null == (_ois = ois) ||
			null == (_libor1M = libor1M) ||
			null == (_libor3M = libor3M) ||
			null == (_libor6M = libor6M) ||
			null == (_libor12M = libor12M) ||
			null == (_prime = prime) ||
			null == (_municipal = municipal))
		{
			throw new java.lang.Exception ("IRSensitivity Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the OIS Risk Factor Sensitivity
	 * 
	 * @return The OIS Risk Factor Sensitivity
	 */

	public org.drip.simm20.product.RiskFactorTenorSensitivity ois()
	{
		return _ois;
	}

	/**
	 * Retrieve the LIBOR-1M Risk Factor Sensitivity
	 * 
	 * @return The LIBOR-1M Risk Factor Sensitivity
	 */

	public org.drip.simm20.product.RiskFactorTenorSensitivity libor1M()
	{
		return _libor1M;
	}

	/**
	 * Retrieve the LIBOR-3M Risk Factor Sensitivity
	 * 
	 * @return The LIBOR-3M Risk Factor Sensitivity
	 */

	public org.drip.simm20.product.RiskFactorTenorSensitivity libor3M()
	{
		return _libor3M;
	}

	/**
	 * Retrieve the LIBOR-6M Risk Factor Sensitivity
	 * 
	 * @return The LIBOR-6M Risk Factor Sensitivity
	 */

	public org.drip.simm20.product.RiskFactorTenorSensitivity libor6M()
	{
		return _libor6M;
	}

	/**
	 * Retrieve the LIBOR-12M Risk Factor Sensitivity
	 * 
	 * @return The LIBOR-12M Risk Factor Sensitivity
	 */

	public org.drip.simm20.product.RiskFactorTenorSensitivity libor12M()
	{
		return _libor12M;
	}

	/**
	 * Retrieve the PRIME Risk Factor Sensitivity
	 * 
	 * @return The PRIME Risk Factor Sensitivity
	 */

	public org.drip.simm20.product.RiskFactorTenorSensitivity prime()
	{
		return _prime;
	}

	/**
	 * Retrieve the MUNICIPAL Risk Factor Sensitivity
	 * 
	 * @return The MUNICIPAL Risk Factor Sensitivity
	 */

	public org.drip.simm20.product.RiskFactorTenorSensitivity municipal()
	{
		return _municipal;
	}

	/**
	 * Generate the Cumulative Delta
	 * 
	 * @return The Cumulative Delta
	 */

	public double cumulative()
	{
		return _ois.cumulative() +
			_libor1M.cumulative() +
			_libor3M.cumulative() +
			_libor6M.cumulative() +
			_libor12M.cumulative() +
			_prime.cumulative() +
			_municipal.cumulative();
	}

	/**
	 * Compute the Concentration Risk Factor
	 * 
	 * @param concentrationThreshold The Concentration Threshold
	 * 
	 * @return The Concentration Risk Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double concentrationRiskFactor (
		final double concentrationThreshold)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (concentrationThreshold))
		{
			throw new java.lang.Exception ("IRSensitivity::concentrationRiskFactor => Invalid Inputs");
		}

		return java.lang.Math.max (
			java.lang.Math.sqrt (
				java.lang.Math.max (
					cumulative(),
					0.
				) / concentrationThreshold
			),
			1.
		);
	}

	/**
	 * Generate the Weighted/Unweighted Net OIS Sensitivity Map
	 * 
	 * @param curveTenorSensitivitySettings The Curve Teno Sensitivity Settings
	 * @param weighted TRUE - Generate the Weighted Sensitivity Map
	 * 
	 * @return The Weighted/Unweighted Net OIS Sensitivity Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> oisNet (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings,
		final boolean weighted)
	{
		if (null == curveTenorSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> oisNet = _ois.netRiskWeight
			(curveTenorSensitivitySettings.ois());

		if (null == oisNet || !weighted)
		{
			return oisNet;
		}

		double concentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			concentrationRiskFactor = concentrationRiskFactor
				(curveTenorSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisNetEntry : oisNet.entrySet())
		{
			java.lang.String tenor = oisNetEntry.getKey();

			oisNet.put (
				tenor,
				oisNet.get (tenor) * concentrationRiskFactor
			);
		}

		return oisNet;
	}

	/**
	 * Generate the Weighted/Unweighted Net LIBOR 1M Sensitivity Map
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * @param weighted TRUE - Generate the Weighted Sensitivity Map
	 * 
	 * @return The Weighted/Unweighted Net LIBOR 1M Sensitivity Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor1MNet (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings,
		final boolean weighted)
	{
		if (null == curveTenorSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> libor1MNet = _libor1M.netRiskWeight
			(curveTenorSensitivitySettings.libor1M());

		if (null == libor1MNet || !weighted)
		{
			return libor1MNet;
		}

		double concentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			concentrationRiskFactor = concentrationRiskFactor
				(curveTenorSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MNetEntry : libor1MNet.entrySet())
		{
			java.lang.String tenor = libor1MNetEntry.getKey();

			libor1MNet.put (
				tenor,
				libor1MNet.get (tenor) * concentrationRiskFactor
			);
		}

		return libor1MNet;
	}

	/**
	 * Generate the Weighted/Unweighted Net LIBOR 3M Sensitivity Map
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * @param weighted TRUE - Generate the Weighted Sensitivity Map
	 * 
	 * @return The Weighted/Unweighted Net LIBOR 3M Sensitivity Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor3MNet (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings,
		final boolean weighted)
	{
		if (null == curveTenorSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> libor3MNet = _libor3M.netRiskWeight
			(curveTenorSensitivitySettings.libor3M());

		if (null == libor3MNet || !weighted)
		{
			return libor3MNet;
		}

		double concentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			concentrationRiskFactor = concentrationRiskFactor
				(curveTenorSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MNetEntry : libor3MNet.entrySet())
		{
			java.lang.String tenor = libor3MNetEntry.getKey();

			libor3MNet.put (
				tenor,
				libor3MNet.get (tenor) * concentrationRiskFactor
			);
		}

		return libor3MNet;
	}

	/**
	 * Generate the Weighted/Unweighted Net LIBOR 6M Sensitivity Map
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * @param weighted TRUE - Generate the Weighted Sensitivity Map
	 * 
	 * @return The Weighted/Unweighted Net LIBOR 6M Sensitivity Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor6MNet (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings,
		final boolean weighted)
	{
		if (null == curveTenorSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> libor6MNet = _libor6M.netRiskWeight
			(curveTenorSensitivitySettings.libor6M());

		if (null == libor6MNet || !weighted)
		{
			return libor6MNet;
		}

		double concentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			concentrationRiskFactor = concentrationRiskFactor
				(curveTenorSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MNetEntry : libor6MNet.entrySet())
		{
			java.lang.String tenor = libor6MNetEntry.getKey();

			libor6MNet.put (
				tenor,
				libor6MNet.get (tenor) * concentrationRiskFactor
			);
		}

		return libor6MNet;
	}

	/**
	 * Generate the Weighted/Unweighted Net LIBOR 12M Sensitivity Map
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * @param weighted TRUE - Generate the Weighted Sensitivity Map
	 * 
	 * @return The Weighted/Unweighted Net LIBOR 12M Sensitivity Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor12MNet (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings,
		final boolean weighted)
	{
		if (null == curveTenorSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> libor12MNet = _libor12M.netRiskWeight
			(curveTenorSensitivitySettings.libor12M());

		if (null == libor12MNet || !weighted)
		{
			return libor12MNet;
		}

		double concentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			concentrationRiskFactor = concentrationRiskFactor
				(curveTenorSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MNetEntry : libor12MNet.entrySet())
		{
			java.lang.String tenor = libor12MNetEntry.getKey();

			libor12MNet.put (
				tenor,
				libor12MNet.get (tenor) * concentrationRiskFactor
			);
		}

		return libor12MNet;
	}

	/**
	 * Generate the Weighted/Unweighted Net PRIME Sensitivity Map
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * @param weighted TRUE - Generate the Weighted Sensitivity Map
	 * 
	 * @return The Weighted/Unweighted Net PRIME Sensitivity Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> primeNet (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings,
		final boolean weighted)
	{
		if (null == curveTenorSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> primeNet = _prime.netRiskWeight
			(curveTenorSensitivitySettings.prime());

		if (null == primeNet || !weighted)
		{
			return primeNet;
		}

		double concentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			concentrationRiskFactor = concentrationRiskFactor
				(curveTenorSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeNetEntry : primeNet.entrySet())
		{
			java.lang.String tenor = primeNetEntry.getKey();

			primeNet.put (
				tenor,
				primeNet.get (tenor) * concentrationRiskFactor
			);
		}

		return primeNet;
	}

	/**
	 * Generate the Weighted/Unweighted Net MUNICIPAL Sensitivity Map
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * @param weighted TRUE - Generate the Weighted Sensitivity Map
	 * 
	 * @return The Weighted/Unweighted Net MUNICIPAL Sensitivity Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> municipalNet (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings,
		final boolean weighted)
	{
		if (null == curveTenorSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> municipalNet = _municipal.netRiskWeight
			(curveTenorSensitivitySettings.municipal());

		if (null == municipalNet || !weighted)
		{
			return municipalNet;
		}

		double concentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			concentrationRiskFactor = concentrationRiskFactor
				(curveTenorSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalNetEntry : municipalNet.entrySet())
		{
			java.lang.String tenor = municipalNetEntry.getKey();

			municipalNet.put (
				tenor,
				municipalNet.get (tenor) * concentrationRiskFactor
			);
		}

		return municipalNet;
	}

	/**
	 * Generate the Weighted/Unweighted Net Curve Sensitivity Map
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * @param weighted TRUE - Generate the Weighted Sensitivity Map
	 * 
	 * @return The Weighted/Unweighted Net Curve Sensitivity Map
	 */

	public org.drip.simm20.product.IRNetSensitivity curveNet (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings,
		final boolean weighted)
	{
		if (null == curveTenorSensitivitySettings)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> oisNet = _ois.netRiskWeight
			(curveTenorSensitivitySettings.ois());

		java.util.Map<java.lang.String, java.lang.Double> libor1MNet = _libor1M.netRiskWeight
			(curveTenorSensitivitySettings.libor1M());

		java.util.Map<java.lang.String, java.lang.Double> libor3MNet = _libor3M.netRiskWeight
			(curveTenorSensitivitySettings.libor3M());

		java.util.Map<java.lang.String, java.lang.Double> libor6MNet = _libor6M.netRiskWeight
			(curveTenorSensitivitySettings.libor6M());

		java.util.Map<java.lang.String, java.lang.Double> libor12MNet = _libor12M.netRiskWeight
			(curveTenorSensitivitySettings.libor12M());

		java.util.Map<java.lang.String, java.lang.Double> primeNet = _prime.netRiskWeight
			(curveTenorSensitivitySettings.prime());

		java.util.Map<java.lang.String, java.lang.Double> municipalNet = _municipal.netRiskWeight
			(curveTenorSensitivitySettings.municipal());

		if (null == oisNet || null == libor1MNet || null == libor3MNet || null == libor6MNet ||
			null == libor12MNet || null == primeNet || null == municipalNet)
		{
			return null;
		}

		if (!weighted)
		{
			try
			{
				return new org.drip.simm20.product.IRNetSensitivity (
					oisNet,
					libor1MNet,
					libor3MNet,
					libor6MNet,
					libor12MNet,
					primeNet,
					municipalNet
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}
		double concentrationRiskFactor = java.lang.Double.NaN;

		try
		{
			concentrationRiskFactor = concentrationRiskFactor
				(curveTenorSensitivitySettings.concentrationThreshold());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalNetEntry : municipalNet.entrySet())
		{
			java.lang.String tenor = municipalNetEntry.getKey();

			oisNet.put (
				tenor,
				oisNet.get (tenor) * concentrationRiskFactor
			);

			libor1MNet.put (
				tenor,
				libor1MNet.get (tenor) * concentrationRiskFactor
			);

			libor3MNet.put (
				tenor,
				libor3MNet.get (tenor) * concentrationRiskFactor
			);

			libor6MNet.put (
				tenor,
				libor6MNet.get (tenor) * concentrationRiskFactor
			);

			libor12MNet.put (
				tenor,
				libor3MNet.get (tenor) * concentrationRiskFactor
			);

			primeNet.put (
				tenor,
				primeNet.get (tenor) * concentrationRiskFactor
			);

			municipalNet.put (
				tenor,
				municipalNet.get (tenor) * concentrationRiskFactor
			);
		}

		try
		{
			return new org.drip.simm20.product.IRNetSensitivity (
				oisNet,
				libor1MNet,
				libor3MNet,
				libor6MNet,
				libor12MNet,
				primeNet,
				municipalNet
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
