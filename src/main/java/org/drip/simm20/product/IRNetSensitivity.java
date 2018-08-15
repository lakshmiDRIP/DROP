
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
 * IRNetSensitivity holds the Weighted Net Sensitivities for each of the IR Risk Factors - OIS, LIBOR 1M,
 *  LIBOR 3M, LIBOR 6M LIBOR 12M, PRIME, and MUNICIPAL. The References are:
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

public class IRNetSensitivity
{

	private double _concentrationRiskFactor = java.lang.Double.NaN;
	private java.util.Map<java.lang.String, java.lang.Double> _oisThresholded = null;
	private java.util.Map<java.lang.String, java.lang.Double> _primeThresholded = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor1MThresholded = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor3MThresholded = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor6MThresholded = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor12MThresholded = null;
	private java.util.Map<java.lang.String, java.lang.Double> _municipalThresholded = null;

	/**
	 * IRNetSensitivity Constructor
	 * 
	 * @param oisThresholded The Thresholded OIS Weighted/Unweighted Sensitivity
	 * @param libor1MThresholded The Thresholded LIBOR 1M Weighted/Unweighted Sensitivity
	 * @param libor3MThresholded The Thresholded LIBOR 3M Weighted/Unweighted Sensitivity
	 * @param libor6MThresholded The Thresholded LIBOR 6M Weighted/Unweighted Sensitivity
	 * @param libor12MThresholded The Thresholded LIBOR 12M Weighted/Unweighted Sensitivity
	 * @param primeThresholded The Thresholded Prime Weighted/Unweighted Sensitivity
	 * @param municipalThresholded The Thresholded Municipal Weighted/Unweighted Sensitivity
	 * @param concentrationRiskFactor The Currency's Concentration Risk Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public IRNetSensitivity (
		final java.util.Map<java.lang.String, java.lang.Double> oisThresholded,
		final java.util.Map<java.lang.String, java.lang.Double> libor1MThresholded,
		final java.util.Map<java.lang.String, java.lang.Double> libor3MThresholded,
		final java.util.Map<java.lang.String, java.lang.Double> libor6MThresholded,
		final java.util.Map<java.lang.String, java.lang.Double> libor12MThresholded,
		final java.util.Map<java.lang.String, java.lang.Double> primeThresholded,
		final java.util.Map<java.lang.String, java.lang.Double> municipalThresholded,
		final double concentrationRiskFactor)
		throws java.lang.Exception
	{
		 if (null == (_oisThresholded = oisThresholded) || 0 == _oisThresholded.size() ||
			null == (_libor1MThresholded = libor1MThresholded) || 0 == _libor1MThresholded.size() ||
			null == (_libor3MThresholded = libor3MThresholded) || 0 == _libor3MThresholded.size() ||
			null == (_libor6MThresholded = libor6MThresholded) || 0 == _libor6MThresholded.size() ||
			null == (_libor12MThresholded = libor12MThresholded) || 0 == _libor12MThresholded.size() ||
			null == (_municipalThresholded = municipalThresholded) || 0 == _municipalThresholded.size() ||
			null == (_primeThresholded = primeThresholded) || 0 == _primeThresholded.size() ||
			!org.drip.quant.common.NumberUtil.IsValid (_concentrationRiskFactor = concentrationRiskFactor))
		 {
			 throw new java.lang.Exception ("IRNetSensitivity Constructor => Invalid Inputs");
		 }
	}

	/**
	 * Retrieve the OIS Net Tenor Sensitivity Map
	 * 
	 * @return The OIS Net Tenor Sensitivity Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> oisThresholded()
	{
		return _oisThresholded;
	}

	/**
	 * Retrieve the Thresholded LIBOR 1M Net Tenor Sensitivity Map
	 * 
	 * @return The Thresholded LIBOR 1M Net Tenor Sensitivity Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor1MThresholded()
	{
		return _libor1MThresholded;
	}

	/**
	 * Retrieve the Thresholded LIBOR 3M Net Tenor Sensitivity Map
	 * 
	 * @return The Thresholded LIBOR 3M Net Tenor Sensitivity Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor3MThresholded()
	{
		return _libor3MThresholded;
	}

	/**
	 * Retrieve the Thresholded LIBOR 6M Net Tenor Sensitivity Map
	 * 
	 * @return The Thresholded LIBOR 6M Net Tenor Sensitivity Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor6M()
	{
		return _libor6MThresholded;
	}

	/**
	 * Retrieve the LIBOR 12M Net Tenor Sensitivity Map
	 * 
	 * @return The LIBOR 12M Net Tenor Sensitivity Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor12M()
	{
		return _libor12MThresholded;
	}

	/**
	 * Retrieve the Thresholded PRIME Net Tenor Sensitivity Map
	 * 
	 * @return The Thresholded PRIME Net Tenor Sensitivity Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> primeThresholded()
	{
		return _primeThresholded;
	}

	/**
	 * Retrieve the Thresholded MUNICIPAL Net Tenor Sensitivity Map
	 * 
	 * @return The Thresholded MUNICIPAL Net Tenor Sensitivity Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> municipal()
	{
		return _municipalThresholded;
	}

	/**
	 * Compute the Cumulative OIS Thresholded Sensitivity
	 * 
	 * @return The Cumulative OIS Thresholded Sensitivity
	 */

	public double cumulativeOISThresholded()
	{
		double cumulativeOISThresholdedSensitivity = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisEntry : _oisThresholded.entrySet())
		{
			cumulativeOISThresholdedSensitivity = cumulativeOISThresholdedSensitivity + oisEntry.getValue();
		}

		return cumulativeOISThresholdedSensitivity;
	}

	/**
	 * Compute the Cumulative LIBOR1M Thresholded Sensitivity
	 * 
	 * @return The Cumulative LIBOR1M Thresholded Sensitivity
	 */

	public double cumulativeLIBOR1MThresholded()
	{
		double cumulativeLIBOR1MThresholdedSensitivity = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MEntry :
			_libor1MThresholded.entrySet())
		{
			cumulativeLIBOR1MThresholdedSensitivity = cumulativeLIBOR1MThresholdedSensitivity +
				libor1MEntry.getValue();
		}

		return cumulativeLIBOR1MThresholdedSensitivity;
	}

	/**
	 * Compute the Cumulative LIBOR3M Thresholded Sensitivity
	 * 
	 * @return The Cumulative LIBOR3M Thresholded Sensitivity
	 */

	public double cumulativeLIBOR3MThresholded()
	{
		double cumulativeLIBOR3MThresholded = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MEntry :
			_libor3MThresholded.entrySet())
		{
			cumulativeLIBOR3MThresholded = cumulativeLIBOR3MThresholded + libor3MEntry.getValue();
		}

		return cumulativeLIBOR3MThresholded;
	}

	/**
	 * Compute the Cumulative LIBOR6M Thresholded Sensitivity
	 * 
	 * @return The Cumulative LIBOR6M Thresholded Sensitivity
	 */

	public double cumulativeLIBOR6MThresholded()
	{
		double cumulativeLIBOR6MThresholded = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MEntry :
			_libor6MThresholded.entrySet())
		{
			cumulativeLIBOR6MThresholded = cumulativeLIBOR6MThresholded + libor6MEntry.getValue();
		}

		return cumulativeLIBOR6MThresholded;
	}

	/**
	 * Compute the Cumulative LIBOR12M Thresholded Sensitivity
	 * 
	 * @return The Cumulative LIBOR12M Thresholded Sensitivity
	 */

	public double cumulativeLIBOR12MThresholded()
	{
		double cumulativeLIBOR12MThresholded = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MEntry :
			_libor12MThresholded.entrySet())
		{
			cumulativeLIBOR12MThresholded = cumulativeLIBOR12MThresholded + libor12MEntry.getValue();
		}

		return cumulativeLIBOR12MThresholded;
	}

	/**
	 * Compute the Cumulative PRIME Thresholded Sensitivity
	 * 
	 * @return The Cumulative PRIME Thresholded Sensitivity
	 */

	public double cumulativePRIMEThresholded()
	{
		double cumulativePRIMEThresholded = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeEntry :
			_primeThresholded.entrySet())
		{
			cumulativePRIMEThresholded = cumulativePRIMEThresholded + primeEntry.getValue();
		}

		return cumulativePRIMEThresholded;
	}

	/**
	 * Compute the Cumulative MUNICIPAL Thresholded Sensitivity
	 * 
	 * @return The Cumulative MUNICIPAL Thresholded Sensitivity
	 */

	public double cumulativeMUNICIPALThresholded()
	{
		double cumulativeMUNICIPALThresholded = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalEntry :
			_municipalThresholded.entrySet())
		{
			cumulativeMUNICIPALThresholded = cumulativeMUNICIPALThresholded + municipalEntry.getValue();
		}

		return cumulativeMUNICIPALThresholded;
	}

	/**
	 * Compute the Cumulative Gross Thresholded Sensitivity
	 * 
	 * @return The Cumulative Gross Thresholded Sensitivity
	 */

	public double cumulativeGrossThresholded()
	{
		return cumulativeOISThresholded() +
			cumulativeLIBOR1MThresholded() +
			cumulativeLIBOR3MThresholded() +
			cumulativeLIBOR6MThresholded() +
			cumulativeLIBOR12MThresholded() +
			cumulativePRIMEThresholded() +
			cumulativeMUNICIPALThresholded();
	}

	/**
	 * Compute the OIS-OIS Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The OIS-OIS Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_OIS_OIS (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_OIS_OIS => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_OIS_OIS = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisOuterEntry :
			_oisThresholded.entrySet())
		{
			double outerSensitivity = oisOuterEntry.getValue();

			java.lang.String outerTenor = oisOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisInnerEntry :
				_oisThresholded.entrySet())
			{
				java.lang.String innerTenor = oisInnerEntry.getKey();

				covariance_OIS_OIS = covariance_OIS_OIS + outerSensitivity * oisInnerEntry.getValue() * (
					outerTenor.equalsIgnoreCase (innerTenor) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return covariance_OIS_OIS;
	}

	/**
	 * Compute the LIBOR1M-LIBOR1M Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR1M-LIBOR1M Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR1M_LIBOR1M (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_LIBOR1M_LIBOR1M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR1M_LIBOR1M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MOuterEntry :
			_libor1MThresholded.entrySet())
		{
			double outerSensitivity = libor1MOuterEntry.getValue();

			java.lang.String outerTenor = libor1MOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MInnerEntry :
				_libor1MThresholded.entrySet())
			{
				java.lang.String innerTenor = libor1MInnerEntry.getKey();

				covariance_LIBOR1M_LIBOR1M = covariance_LIBOR1M_LIBOR1M + outerSensitivity *
					libor1MInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (innerTenor) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return covariance_LIBOR1M_LIBOR1M;
	}

	/**
	 * Compute the LIBOR3M-LIBOR3M Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR3M-LIBOR3M Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR3M_LIBOR3M (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_LIBOR3M_LIBOR3M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR3M_LIBOR3M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MOuterEntry :
			_libor3MThresholded.entrySet())
		{
			double outerSensitivity = libor3MOuterEntry.getValue();

			java.lang.String outerTenor = libor3MOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MInnerEntry :
				_libor3MThresholded.entrySet())
			{
				java.lang.String innerTenor = libor3MInnerEntry.getKey();

				covariance_LIBOR3M_LIBOR3M = covariance_LIBOR3M_LIBOR3M + outerSensitivity *
					libor3MInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (innerTenor) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return covariance_LIBOR3M_LIBOR3M;
	}

	/**
	 * Compute the LIBOR6M-LIBOR6M Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR6M-LIBOR6M Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR6M_LIBOR6M (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_LIBOR6M_LIBOR6M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR6M_LIBOR6M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MOuterEntry :
			_libor6MThresholded.entrySet())
		{
			double outerSensitivity = libor6MOuterEntry.getValue();

			java.lang.String outerTenor = libor6MOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MInnerEntry :
				_libor3MThresholded.entrySet())
			{
				java.lang.String innerTenor = libor6MInnerEntry.getKey();

				covariance_LIBOR6M_LIBOR6M = covariance_LIBOR6M_LIBOR6M + outerSensitivity *
					libor6MInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (innerTenor) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return covariance_LIBOR6M_LIBOR6M;
	}

	/**
	 * Compute the LIBOR12M-LIBOR12M Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR12M-LIBOR12M Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR12M_LIBOR12M (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception
				("IRNetSensitivity::covariance_LIBOR12M_LIBOR12M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR12M_LIBOR12M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MOuterEntry :
			_libor12MThresholded.entrySet())
		{
			double outerSensitivity = libor12MOuterEntry.getValue();

			java.lang.String outerTenor = libor12MOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MInnerEntry :
				_libor12MThresholded.entrySet())
			{
				java.lang.String innerTenor = libor12MInnerEntry.getKey();

				covariance_LIBOR12M_LIBOR12M = covariance_LIBOR12M_LIBOR12M + outerSensitivity *
					libor12MInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (innerTenor) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return covariance_LIBOR12M_LIBOR12M;
	}

	/**
	 * Compute the PRIME-PRIME Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The PRIME-PRIME Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_PRIME_PRIME (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_PRIME_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_PRIME_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeOuterEntry :
			_primeThresholded.entrySet())
		{
			double outerSensitivity = primeOuterEntry.getValue();

			java.lang.String outerTenor = primeOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeInnerEntry :
				_primeThresholded.entrySet())
			{
				java.lang.String innerTenor = primeInnerEntry.getKey();

				covariance_PRIME_PRIME = covariance_PRIME_PRIME + outerSensitivity *
					primeInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (innerTenor) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return covariance_PRIME_PRIME;
	}

	/**
	 * Compute the MUNICIPAL-MUNICIPAL Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The MUNICIPAL-MUNICIPAL Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_MUNICIPAL_MUNICIPAL (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception
				("IRNetSensitivity::covariance_MUNICIPAL_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_MUNICIPAL_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalOuterEntry :
			_municipalThresholded.entrySet())
		{
			double outerSensitivity = municipalOuterEntry.getValue();

			java.lang.String outerTenor = municipalOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalInnerEntry :
				_municipalThresholded.entrySet())
			{
				java.lang.String innerTenor = municipalInnerEntry.getKey();

				covariance_MUNICIPAL_MUNICIPAL = covariance_MUNICIPAL_MUNICIPAL + outerSensitivity *
					municipalInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (innerTenor) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return covariance_MUNICIPAL_MUNICIPAL;
	}

	/**
	 * Compute the OIS-LIBOR1M Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The OIS-LIBOR1M Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_OIS_LIBOR1M (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_OIS_LIBOR1M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_OIS_LIBOR1M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisEntry : _oisThresholded.entrySet())
		{
			double oisSensitivity = oisEntry.getValue();

			java.lang.String oisTenor = oisEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MEntry :
				_libor1MThresholded.entrySet())
			{
				java.lang.String libor1MTenor = libor1MEntry.getKey();

				covariance_OIS_LIBOR1M = covariance_OIS_LIBOR1M + oisSensitivity *
					libor1MEntry.getValue() * (
						oisTenor.equalsIgnoreCase (libor1MTenor) ? 1. : tenorCorrelation.entry (
							oisTenor,
							libor1MTenor
						)
					);
			}
		}

		return covariance_OIS_LIBOR1M * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the OIS-LIBOR3M Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The OIS-LIBOR3M Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_OIS_LIBOR3M (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_OIS_LIBOR3M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_OIS_LIBOR3M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisEntry : _oisThresholded.entrySet())
		{
			double oisSensitivity = oisEntry.getValue();

			java.lang.String oisTenor = oisEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MEntry :
				_libor3MThresholded.entrySet())
			{
				java.lang.String libor3MTenor = libor3MEntry.getKey();

				covariance_OIS_LIBOR3M = covariance_OIS_LIBOR3M + oisSensitivity *
					libor3MEntry.getValue() * (
						oisTenor.equalsIgnoreCase (libor3MTenor) ? 1. : tenorCorrelation.entry (
							oisTenor,
							libor3MTenor
						)
					);
			}
		}

		return covariance_OIS_LIBOR3M * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the OIS-LIBOR6M Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The OIS-LIBOR6M Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_OIS_LIBOR6M (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_OIS_LIBOR6M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_OIS_LIBOR6M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisEntry : _oisThresholded.entrySet())
		{
			double oisSensitivity = oisEntry.getValue();

			java.lang.String oisTenor = oisEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MEntry :
				_libor6MThresholded.entrySet())
			{
				java.lang.String libor6MTenor = libor6MEntry.getKey();

				covariance_OIS_LIBOR6M = covariance_OIS_LIBOR6M + oisSensitivity *
					libor6MEntry.getValue() * (
						oisTenor.equalsIgnoreCase (libor6MTenor) ? 1. : tenorCorrelation.entry (
							oisTenor,
							libor6MTenor
						)
					);
			}
		}

		return covariance_OIS_LIBOR6M * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the OIS-LIBOR12M Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The OIS-LIBOR12M Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_OIS_LIBOR12M (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_OIS_LIBOR12M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_OIS_LIBOR12M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisEntry : _oisThresholded.entrySet())
		{
			double oisSensitivity = oisEntry.getValue();

			java.lang.String oisTenor = oisEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MEntry :
				_libor12MThresholded.entrySet())
			{
				java.lang.String libor12MTenor = libor12MEntry.getKey();

				covariance_OIS_LIBOR12M = covariance_OIS_LIBOR12M + oisSensitivity *
					libor12MEntry.getValue() * (
						oisTenor.equalsIgnoreCase (libor12MTenor) ? 1. : tenorCorrelation.entry (
							oisTenor,
							libor12MTenor
						)
					);
			}
		}

		return covariance_OIS_LIBOR12M * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the OIS-PRIME Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The OIS-PRIME Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_OIS_PRIME (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_OIS_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_OIS_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisEntry : _oisThresholded.entrySet())
		{
			double oisSensitivity = oisEntry.getValue();

			java.lang.String oisTenor = oisEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeEntry :
				_primeThresholded.entrySet())
			{
				java.lang.String primeTenor = primeEntry.getKey();

				covariance_OIS_PRIME = covariance_OIS_PRIME + oisSensitivity * primeEntry.getValue() * (
					oisTenor.equalsIgnoreCase (primeTenor) ? 1. : tenorCorrelation.entry (
						oisTenor,
						primeTenor
					)
				);
			}
		}

		return covariance_OIS_PRIME * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the OIS-MUNICIPAL Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The OIS-MUNICIPAL Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_OIS_MUNICIPAL (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_OIS_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_OIS_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisEntry : _oisThresholded.entrySet())
		{
			double oisSensitivity = oisEntry.getValue();

			java.lang.String oisTenor = oisEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalEntry :
				_municipalThresholded.entrySet())
			{
				java.lang.String municipalTenor = municipalEntry.getKey();

				covariance_OIS_MUNICIPAL = covariance_OIS_MUNICIPAL + oisSensitivity *
					municipalEntry.getValue() * (
						oisTenor.equalsIgnoreCase (municipalTenor) ? 1. : tenorCorrelation.entry (
							oisTenor,
							municipalTenor
						)
					);
			}
		}

		return covariance_OIS_MUNICIPAL * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the LIBOR1M-LIBOR3M Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR1M-LIBOR3M Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR1M_LIBOR3M (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_LIBOR1M_LIBOR3M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR1M_LIBOR3M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MEntry :
			_libor1MThresholded.entrySet())
		{
			double libor1MSensitivity = libor1MEntry.getValue();

			java.lang.String libor1MTenor = libor1MEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MEntry :
				_libor3MThresholded.entrySet())
			{
				java.lang.String libor3MTenor = libor3MEntry.getKey();

				covariance_LIBOR1M_LIBOR3M = covariance_LIBOR1M_LIBOR3M + libor1MSensitivity *
					libor3MEntry.getValue() * (
						libor1MTenor.equalsIgnoreCase (libor3MTenor) ? 1. : tenorCorrelation.entry (
							libor1MTenor,
							libor3MTenor
						)
					);
			}
		}

		return covariance_LIBOR1M_LIBOR3M * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the LIBOR1M-LIBOR6M Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR1M-LIBOR6M Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR1M_LIBOR6M (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_LIBOR1M_LIBOR6M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR1M_LIBOR6M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MEntry :
			_libor1MThresholded.entrySet())
		{
			double libor1MSensitivity = libor1MEntry.getValue();

			java.lang.String libor1MTenor = libor1MEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MEntry :
				_libor6MThresholded.entrySet())
			{
				java.lang.String libor6MTenor = libor6MEntry.getKey();

				covariance_LIBOR1M_LIBOR6M = covariance_LIBOR1M_LIBOR6M + libor1MSensitivity *
					libor6MEntry.getValue() * (
						libor1MTenor.equalsIgnoreCase (libor6MTenor) ? 1. : tenorCorrelation.entry (
							libor1MTenor,
							libor6MTenor
						)
					);
			}
		}

		return covariance_LIBOR1M_LIBOR6M * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the LIBOR1M-LIBOR12M Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR1M-LIBOR12M Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR1M_LIBOR12M (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_LIBOR1M_LIBOR12M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR1M_LIBOR12M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MEntry :
			_libor1MThresholded.entrySet())
		{
			double libor1MSensitivity = libor1MEntry.getValue();

			java.lang.String libor1MTenor = libor1MEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MEntry :
				_libor12MThresholded.entrySet())
			{
				java.lang.String libor12MTenor = libor12MEntry.getKey();

				covariance_LIBOR1M_LIBOR12M = covariance_LIBOR1M_LIBOR12M + libor1MSensitivity *
					libor12MEntry.getValue() * (
						libor1MTenor.equalsIgnoreCase (libor12MTenor) ? 1. : tenorCorrelation.entry (
							libor1MTenor,
							libor12MTenor
						)
					);
			}
		}

		return covariance_LIBOR1M_LIBOR12M * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the LIBOR1M-PRIME Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR1M-PRIME Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR1M_PRIME (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_LIBOR1M_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR1M_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MEntry :
			_libor1MThresholded.entrySet())
		{
			double libor1MSensitivity = libor1MEntry.getValue();

			java.lang.String libor1MTenor = libor1MEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeEntry :
				_primeThresholded.entrySet())
			{
				java.lang.String primeTenor = primeEntry.getKey();

				covariance_LIBOR1M_PRIME = covariance_LIBOR1M_PRIME + libor1MSensitivity *
					primeEntry.getValue() * (
						libor1MTenor.equalsIgnoreCase (primeTenor) ? 1. : tenorCorrelation.entry (
							libor1MTenor,
							primeTenor
						)
					);
			}
		}

		return covariance_LIBOR1M_PRIME * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the LIBOR1M-MUNICIPAL Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR1M-MUNICIPAL Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR1M_MUNICIPAL (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception
				("IRNetSensitivity::covariance_LIBOR1M_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR1M_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MEntry :
			_libor1MThresholded.entrySet())
		{
			double libor1MSensitivity = libor1MEntry.getValue();

			java.lang.String libor1MTenor = libor1MEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalEntry :
				_municipalThresholded.entrySet())
			{
				java.lang.String municipalTenor = municipalEntry.getKey();

				covariance_LIBOR1M_MUNICIPAL = covariance_LIBOR1M_MUNICIPAL + libor1MSensitivity *
					municipalEntry.getValue() * (
						libor1MTenor.equalsIgnoreCase (municipalTenor) ? 1. : tenorCorrelation.entry (
							libor1MTenor,
							municipalTenor
						)
					);
			}
		}

		return covariance_LIBOR1M_MUNICIPAL * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the LIBOR3M-LIBOR6M Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR3M-LIBOR6M Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR3M_LIBOR6M (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_LIBOR3M_LIBOR6M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR3M_LIBOR6M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MEntry :
			_libor3MThresholded.entrySet())
		{
			double libor3MSensitivity = libor3MEntry.getValue();

			java.lang.String libor3MTenor = libor3MEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MEntry :
				_libor6MThresholded.entrySet())
			{
				java.lang.String libor6MTenor = libor6MEntry.getKey();

				covariance_LIBOR3M_LIBOR6M = covariance_LIBOR3M_LIBOR6M + libor3MSensitivity *
					libor6MEntry.getValue() * (
						libor3MTenor.equalsIgnoreCase (libor6MTenor) ? 1. : tenorCorrelation.entry (
							libor3MTenor,
							libor6MTenor
						)
					);
			}
		}

		return covariance_LIBOR3M_LIBOR6M * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the LIBOR3M-LIBOR12M Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR3M-LIBOR12M Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR3M_LIBOR12M (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_LIBOR3M_LIBOR12M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR3M_LIBOR12M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MEntry :
			_libor3MThresholded.entrySet())
		{
			double libor3MSensitivity = libor3MEntry.getValue();

			java.lang.String libor3MTenor = libor3MEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MEntry :
				_libor12MThresholded.entrySet())
			{
				java.lang.String libor12MTenor = libor12MEntry.getKey();

				covariance_LIBOR3M_LIBOR12M = covariance_LIBOR3M_LIBOR12M + libor3MSensitivity *
					libor12MEntry.getValue() * (
						libor3MTenor.equalsIgnoreCase (libor12MTenor) ? 1. : tenorCorrelation.entry (
							libor3MTenor,
							libor12MTenor
						)
					);
			}
		}

		return covariance_LIBOR3M_LIBOR12M * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the LIBOR3M-PRIME Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR3M-PRIME Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR3M_PRIME (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_LIBOR3M_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR3M_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MEntry :
			_libor3MThresholded.entrySet())
		{
			double libor3MSensitivity = libor3MEntry.getValue();

			java.lang.String libor3MTenor = libor3MEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeEntry :
				_primeThresholded.entrySet())
			{
				java.lang.String primeTenor = primeEntry.getKey();

				covariance_LIBOR3M_PRIME = covariance_LIBOR3M_PRIME + libor3MSensitivity *
					primeEntry.getValue() * (
						libor3MTenor.equalsIgnoreCase (primeTenor) ? 1. : tenorCorrelation.entry (
							libor3MTenor,
							primeTenor
						)
					);
			}
		}

		return covariance_LIBOR3M_PRIME * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the LIBOR3M-MUNICIPAL Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR3M-MUNICIPAL Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR3M_MUNICIPAL (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception
				("IRNetSensitivity::covariance_LIBOR3M_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR3M_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MEntry :
			_libor3MThresholded.entrySet())
		{
			double libor3MSensitivity = libor3MEntry.getValue();

			java.lang.String libor3MTenor = libor3MEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalEntry :
				_municipalThresholded.entrySet())
			{
				java.lang.String municipalTenor = municipalEntry.getKey();

				covariance_LIBOR3M_MUNICIPAL = covariance_LIBOR3M_MUNICIPAL + libor3MSensitivity *
					municipalEntry.getValue() * (
						libor3MTenor.equalsIgnoreCase (municipalTenor) ? 1. : tenorCorrelation.entry (
							libor3MTenor,
							municipalTenor
						)
					);
			}
		}

		return covariance_LIBOR3M_MUNICIPAL * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the LIBOR6M-LIBOR12M Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR6M-LIBOR12M Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR6M_LIBOR12M (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_LIBOR6M_LIBOR12M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR6M_LIBOR12M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MEntry :
			_libor6MThresholded.entrySet())
		{
			double libor6MSensitivity = libor6MEntry.getValue();

			java.lang.String libor6MTenor = libor6MEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MEntry :
				_libor12MThresholded.entrySet())
			{
				java.lang.String libor12MTenor = libor12MEntry.getKey();

				covariance_LIBOR6M_LIBOR12M = covariance_LIBOR6M_LIBOR12M + libor6MSensitivity *
					libor12MEntry.getValue() * (
						libor6MTenor.equalsIgnoreCase (libor12MTenor) ? 1. : tenorCorrelation.entry (
							libor6MTenor,
							libor12MTenor
						)
					);
			}
		}

		return covariance_LIBOR6M_LIBOR12M * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the LIBOR6M-PRIME Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR6M-PRIME Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR6M_PRIME (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_LIBOR6M_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR6M_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MEntry :
			_libor6MThresholded.entrySet())
		{
			double libor6MSensitivity = libor6MEntry.getValue();

			java.lang.String libor6MTenor = libor6MEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeEntry :
				_primeThresholded.entrySet())
			{
				java.lang.String primeTenor = primeEntry.getKey();

				covariance_LIBOR6M_PRIME = covariance_LIBOR6M_PRIME + libor6MSensitivity *
					primeEntry.getValue() * (
						libor6MTenor.equalsIgnoreCase (primeTenor) ? 1. : tenorCorrelation.entry (
							libor6MTenor,
							primeTenor
						)
					);
			}
		}

		return covariance_LIBOR6M_PRIME * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the LIBOR6M-MUNICIPAL Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR6M-MUNICIPAL Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR6M_MUNICIPAL (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception
				("IRNetSensitivity::covariance_LIBOR6M_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR6M_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MEntry :
			_libor6MThresholded.entrySet())
		{
			double libor6MSensitivity = libor6MEntry.getValue();

			java.lang.String libor6MTenor = libor6MEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalEntry :
				_municipalThresholded.entrySet())
			{
				java.lang.String municipalTenor = municipalEntry.getKey();

				covariance_LIBOR6M_MUNICIPAL = covariance_LIBOR6M_MUNICIPAL + libor6MSensitivity *
					municipalEntry.getValue() * (
						libor6MTenor.equalsIgnoreCase (municipalTenor) ? 1. : tenorCorrelation.entry (
							libor6MTenor,
							municipalTenor
						)
					);
			}
		}

		return covariance_LIBOR6M_MUNICIPAL * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the LIBOR12M-PRIME Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR12M-PRIME Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR12M_PRIME (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_LIBOR12M_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR12M_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MEntry :
			_libor12MThresholded.entrySet())
		{
			double libor12MSensitivity = libor12MEntry.getValue();

			java.lang.String libor12MTenor = libor12MEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeEntry :
				_primeThresholded.entrySet())
			{
				java.lang.String primeTenor = primeEntry.getKey();

				covariance_LIBOR12M_PRIME = covariance_LIBOR12M_PRIME + libor12MSensitivity *
					primeEntry.getValue() * (
						libor12MTenor.equalsIgnoreCase (primeTenor) ? 1. : tenorCorrelation.entry (
							libor12MTenor,
							primeTenor
						)
					);
			}
		}

		return covariance_LIBOR12M_PRIME * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the LIBOR12M-MUNICIPAL Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The LIBOR12M-MUNICIPAL Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_LIBOR12M_MUNICIPAL (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception
				("IRNetSensitivity::covariance_LIBOR12M_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_LIBOR12M_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MEntry :
			_libor12MThresholded.entrySet())
		{
			double libor12MSensitivity = libor12MEntry.getValue();

			java.lang.String libor12MTenor = libor12MEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalEntry :
				_municipalThresholded.entrySet())
			{
				java.lang.String municipalTenor = municipalEntry.getKey();

				covariance_LIBOR12M_MUNICIPAL = covariance_LIBOR12M_MUNICIPAL + libor12MSensitivity *
					municipalEntry.getValue() * (
						libor12MTenor.equalsIgnoreCase (municipalTenor) ? 1. : tenorCorrelation.entry (
							libor12MTenor,
							municipalTenor
						)
					);
			}
		}

		return covariance_LIBOR12M_MUNICIPAL * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the PRIME-MUNICIPAL Net Sensitivity Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The PRIME-MUNICIPAL Net Sensitivity Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double covariance_PRIME_MUNICIPAL (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
		throws java.lang.Exception
	{
		if (null == curveTenorSensitivitySettings)
		{
			throw new java.lang.Exception ("IRNetSensitivity::covariance_PRIME_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			curveTenorSensitivitySettings.singleCurveTenorCorrelation();

		double covariance_PRIME_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeEntry :
			_primeThresholded.entrySet())
		{
			double primeSensitivity = primeEntry.getValue();

			java.lang.String primeTenor = primeEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalEntry :
				_municipalThresholded.entrySet())
			{
				java.lang.String municipalTenor = municipalEntry.getKey();

				covariance_PRIME_MUNICIPAL = covariance_PRIME_MUNICIPAL + primeSensitivity *
					municipalEntry.getValue() * (
						primeTenor.equalsIgnoreCase (municipalTenor) ? 1. : tenorCorrelation.entry (
							primeTenor,
							municipalTenor
						)
					);
			}
		}

		return covariance_PRIME_MUNICIPAL * curveTenorSensitivitySettings.crossSubCurveCorrelation();
	}

	/**
	 * Compute the Complete Net Sensitivity Margin Co-variance
	 * 
	 * @param curveTenorSensitivitySettings The Curve Tenor Sensitivity Settings
	 * 
	 * @return The Complete Net Sensitivity Margin Co-variance
	 */

	public org.drip.simm20.product.IRMarginCovariance marginCovariance (
		final org.drip.simm20.product.IRCurveTenorSettings curveTenorSensitivitySettings)
	{
		try
		{
			return new org.drip.simm20.product.IRMarginCovariance (
				covariance_OIS_OIS (curveTenorSensitivitySettings),
				covariance_OIS_LIBOR1M (curveTenorSensitivitySettings),
				covariance_OIS_LIBOR3M (curveTenorSensitivitySettings),
				covariance_OIS_LIBOR6M (curveTenorSensitivitySettings),
				covariance_OIS_LIBOR12M (curveTenorSensitivitySettings),
				covariance_OIS_PRIME (curveTenorSensitivitySettings),
				covariance_OIS_MUNICIPAL (curveTenorSensitivitySettings),
				covariance_LIBOR1M_LIBOR1M (curveTenorSensitivitySettings),
				covariance_LIBOR1M_LIBOR3M (curveTenorSensitivitySettings),
				covariance_LIBOR1M_LIBOR6M (curveTenorSensitivitySettings),
				covariance_LIBOR1M_LIBOR12M (curveTenorSensitivitySettings),
				covariance_LIBOR1M_PRIME (curveTenorSensitivitySettings),
				covariance_LIBOR1M_MUNICIPAL (curveTenorSensitivitySettings),
				covariance_LIBOR3M_LIBOR3M (curveTenorSensitivitySettings),
				covariance_LIBOR3M_LIBOR6M (curveTenorSensitivitySettings),
				covariance_LIBOR3M_LIBOR12M (curveTenorSensitivitySettings),
				covariance_LIBOR3M_PRIME (curveTenorSensitivitySettings),
				covariance_LIBOR3M_MUNICIPAL (curveTenorSensitivitySettings),
				covariance_LIBOR6M_LIBOR6M (curveTenorSensitivitySettings),
				covariance_LIBOR6M_LIBOR12M (curveTenorSensitivitySettings),
				covariance_LIBOR6M_PRIME (curveTenorSensitivitySettings),
				covariance_LIBOR6M_MUNICIPAL (curveTenorSensitivitySettings),
				covariance_LIBOR12M_LIBOR12M (curveTenorSensitivitySettings),
				covariance_LIBOR12M_PRIME (curveTenorSensitivitySettings),
				covariance_LIBOR12M_MUNICIPAL (curveTenorSensitivitySettings),
				covariance_PRIME_PRIME (curveTenorSensitivitySettings),
				covariance_PRIME_MUNICIPAL (curveTenorSensitivitySettings),
				covariance_MUNICIPAL_MUNICIPAL (curveTenorSensitivitySettings),
				_concentrationRiskFactor,
				cumulativeGrossThresholded()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Concentration Risk Factor
	 * 
	 * @return The Concentration Risk Factor
	 */

	public double concentrationRiskFactor()
	{
		return _concentrationRiskFactor;
	}
}
