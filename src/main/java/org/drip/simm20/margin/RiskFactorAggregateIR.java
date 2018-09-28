
package org.drip.simm20.margin;

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
 * RiskFactorAggregateIR holds the Sensitivity Margin Aggregates for each of the IR Risk Factors - OIS, LIBOR
 *  1M, LIBOR 3M, LIBOR 6M LIBOR 12M, PRIME, and MUNICIPAL. The References are:
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

public class RiskFactorAggregateIR
{
	private double _concentrationRiskFactor = java.lang.Double.NaN;
	private java.util.Map<java.lang.String, java.lang.Double> _oisSensitivityMargin = null;
	private java.util.Map<java.lang.String, java.lang.Double> _primeSensitivityMargin = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor1MSensitivityMargin = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor3MSensitivityMargin = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor6MSensitivityMargin = null;
	private java.util.Map<java.lang.String, java.lang.Double> _libor12MSensitivityMargin = null;
	private java.util.Map<java.lang.String, java.lang.Double> _municipalSensitivityMargin = null;

	/**
	 * RiskFactorAggregateIR Constructor
	 * 
	 * @param oisSensitivityMargin The OIS Sensitivity Margin
	 * @param libor1MSensitivityMargin The LIBOR 1M Sensitivity Margin
	 * @param libor3MSensitivityMargin The LIBOR 3M Sensitivity Margin
	 * @param libor6MSensitivityMargin The LIBOR 6M Sensitivity Margin
	 * @param libor12MSensitivityMargin The LIBOR 12M Sensitivity Margin
	 * @param primeSensitivityMargin The PRIME Sensitivity Margin
	 * @param municipalSensitivityMargin The Municipal Sensitivity Margin
	 // * @param sensitivityMargin The Bucket Sensitivity Margin
	 * @param concentrationRiskFactor The Currency's Concentration Risk Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskFactorAggregateIR (
		final java.util.Map<java.lang.String, java.lang.Double> oisSensitivityMargin,
		final java.util.Map<java.lang.String, java.lang.Double> libor1MSensitivityMargin,
		final java.util.Map<java.lang.String, java.lang.Double> libor3MSensitivityMargin,
		final java.util.Map<java.lang.String, java.lang.Double> libor6MSensitivityMargin,
		final java.util.Map<java.lang.String, java.lang.Double> libor12MSensitivityMargin,
		final java.util.Map<java.lang.String, java.lang.Double> primeSensitivityMargin,
		final java.util.Map<java.lang.String, java.lang.Double> municipalSensitivityMargin,
		final double concentrationRiskFactor)
		throws java.lang.Exception
	{
		if (null == (_oisSensitivityMargin = oisSensitivityMargin) || 0 == _oisSensitivityMargin.size() ||
			null == (_libor1MSensitivityMargin = libor1MSensitivityMargin) ||
				0 == _libor1MSensitivityMargin.size() ||
			null == (_libor3MSensitivityMargin = libor3MSensitivityMargin) ||
				0 == _libor3MSensitivityMargin.size() ||
			null == (_libor6MSensitivityMargin = libor6MSensitivityMargin) ||
				0 == _libor6MSensitivityMargin.size() ||
			null == (_libor12MSensitivityMargin = libor12MSensitivityMargin) ||
				0 == _libor12MSensitivityMargin.size() ||
			null == (_municipalSensitivityMargin = municipalSensitivityMargin) ||
				0 == _municipalSensitivityMargin.size() ||
			null == (_primeSensitivityMargin = primeSensitivityMargin) ||
				0 == _primeSensitivityMargin.size() ||
			!org.drip.quant.common.NumberUtil.IsValid (_concentrationRiskFactor = concentrationRiskFactor))
		 {
			 throw new java.lang.Exception ("RiskFactorAggregateIR Constructor => Invalid Inputs");
		 }
	}

	/**
	 * Retrieve the OIS Sensitivity Margin Map
	 * 
	 * @return The OIS Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> oisSensitivityMargin()
	{
		return _oisSensitivityMargin;
	}

	/**
	 * Retrieve the LIBOR 1M Sensitivity Margin Map
	 * 
	 * @return The LIBOR 1M Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor1MSensitivityMargin()
	{
		return _libor1MSensitivityMargin;
	}

	/**
	 * Retrieve the LIBOR 3M Sensitivity Margin Map
	 * 
	 * @return The LIBOR 3M Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor3MSensitivityMargin()
	{
		return _libor3MSensitivityMargin;
	}

	/**
	 * Retrieve the LIBOR 6M Sensitivity Margin Map
	 * 
	 * @return The LIBOR 6M Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor6MSensitivityMargin()
	{
		return _libor6MSensitivityMargin;
	}

	/**
	 * Retrieve the LIBOR 12M Sensitivity Margin Map
	 * 
	 * @return The LIBOR 12M Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> libor12MSensitivityMargin()
	{
		return _libor12MSensitivityMargin;
	}

	/**
	 * Retrieve the PRIME Sensitivity Margin Map
	 * 
	 * @return The PRIME Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> primeSensitivityMargin()
	{
		return _primeSensitivityMargin;
	}

	/**
	 * Retrieve the MUNICIPAL Sensitivity Margin Map
	 * 
	 * @return The MUNICIPAL Sensitivity Margin Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> municipalSensitivityMargin()
	{
		return _municipalSensitivityMargin;
	}

	/**
	 * Retrieve the Bucket Concentration Risk Factor
	 * 
	 * @return The Bucket Concentration Risk Factor
	 */

	public double concentrationRiskFactor()
	{
		return _concentrationRiskFactor;
	}

	/**
	 * Compute the Cumulative OIS Sensitivity Margin
	 * 
	 * @return The Cumulative OIS Sensitivity Margin
	 */

	public double cumulativeOISSensitivityMargin()
	{
		double cumulativeOISSensitivityMargin = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet())
		{
			cumulativeOISSensitivityMargin = cumulativeOISSensitivityMargin +
				oisSensitivityMarginEntry.getValue();
		}

		return cumulativeOISSensitivityMargin;
	}

	/**
	 * Compute the Cumulative LIBOR1M Sensitivity Margin
	 * 
	 * @return The Cumulative LIBOR1M Sensitivity Margin
	 */

	public double cumulativeLIBOR1MSensitivityMargin()
	{
		double cumulativeLIBOR1MSensitivityMargin = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet())
		{
			cumulativeLIBOR1MSensitivityMargin = cumulativeLIBOR1MSensitivityMargin +
				libor1MSensitivityMarginEntry.getValue();
		}

		return cumulativeLIBOR1MSensitivityMargin;
	}

	/**
	 * Compute the Cumulative LIBOR3M Sensitivity Margin
	 * 
	 * @return The Cumulative LIBOR3M Sensitivity Margin
	 */

	public double cumulativeLIBOR3MSensitivityMargin()
	{
		double cumulativeLIBOR3MSensitivityMargin = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MSensitivityMarginEntry :
			_libor3MSensitivityMargin.entrySet())
		{
			cumulativeLIBOR3MSensitivityMargin = cumulativeLIBOR3MSensitivityMargin +
				libor3MSensitivityMarginEntry.getValue();
		}

		return cumulativeLIBOR3MSensitivityMargin;
	}

	/**
	 * Compute the Cumulative LIBOR6M Sensitivity Margin
	 * 
	 * @return The Cumulative LIBOR6M Sensitivity Margin
	 */

	public double cumulativeLIBOR6MSensitivityMargin()
	{
		double cumulativeLIBOR6MSensitivityMargin = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MSensitivityMarginEntry :
			_libor6MSensitivityMargin.entrySet())
		{
			cumulativeLIBOR6MSensitivityMargin = cumulativeLIBOR6MSensitivityMargin +
				libor6MSensitivityMarginEntry.getValue();
		}

		return cumulativeLIBOR6MSensitivityMargin;
	}

	/**
	 * Compute the Cumulative LIBOR12M Sensitivity Margin
	 * 
	 * @return The Cumulative LIBOR12M Sensitivity Margin
	 */

	public double cumulativeLIBOR12MSensitivityMargin()
	{
		double cumulativeLIBOR12MSensitivityMargin = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MSensitivityMarginEntry :
			_libor12MSensitivityMargin.entrySet())
		{
			cumulativeLIBOR12MSensitivityMargin = cumulativeLIBOR12MSensitivityMargin +
				libor12MSensitivityMarginEntry.getValue();
		}

		return cumulativeLIBOR12MSensitivityMargin;
	}

	/**
	 * Compute the Cumulative PRIME Sensitivity Margin
	 * 
	 * @return The Cumulative PRIME Sensitivity Margin
	 */

	public double cumulativePRIMESensitivityMargin()
	{
		double cumulativePRIMESensitivityMargin = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeSensitivityMarginEntry :
			_primeSensitivityMargin.entrySet())
		{
			cumulativePRIMESensitivityMargin = cumulativePRIMESensitivityMargin +
				primeSensitivityMarginEntry.getValue();
		}

		return cumulativePRIMESensitivityMargin;
	}

	/**
	 * Compute the Cumulative MUNICIPAL Sensitivity Margin
	 * 
	 * @return The Cumulative MUNICIPAL Sensitivity Margin
	 */

	public double cumulativeMUNICIPALSensitivityMargin()
	{
		double cumulativeMUNICIPALSensitivityMargin = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalSensitivityMarginEntry :
			_municipalSensitivityMargin.entrySet())
		{
			cumulativeMUNICIPALSensitivityMargin = cumulativeMUNICIPALSensitivityMargin +
				municipalSensitivityMarginEntry.getValue();
		}

		return cumulativeMUNICIPALSensitivityMargin;
	}

	/**
	 * Compute the Cumulative Sensitivity Margin
	 * 
	 * @return The Cumulative Sensitivity Margin
	 */

	public double cumulativeSensitivityMargin()
	{
		return cumulativeOISSensitivityMargin() +
			cumulativeLIBOR1MSensitivityMargin() +
			cumulativeLIBOR3MSensitivityMargin() +
			cumulativeLIBOR6MSensitivityMargin() +
			cumulativeLIBOR12MSensitivityMargin() +
			cumulativePRIMESensitivityMargin() +
			cumulativeMUNICIPALSensitivityMargin();
	}

	/**
	 * Compute the Linear OIS-OIS Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear OIS-OIS Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_OIS_OIS (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_OIS_OIS => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_OIS_OIS = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisSensitivityMarginOuterEntry :
			_oisSensitivityMargin.entrySet())
		{
			java.lang.String outerTenor = oisSensitivityMarginOuterEntry.getKey();

			double outerSensitivityMargin = oisSensitivityMarginOuterEntry.getValue();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisSensitivityMarginInnerEntry :
				_oisSensitivityMargin.entrySet())
			{
				java.lang.String innerTenor = oisSensitivityMarginInnerEntry.getKey();

				linearMarginCovariance_OIS_OIS = linearMarginCovariance_OIS_OIS + outerSensitivityMargin *
					oisSensitivityMarginInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (innerTenor) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return linearMarginCovariance_OIS_OIS;
	}

	/**
	 * Compute the Curvature OIS-OIS Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature OIS-OIS Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_OIS_OIS (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_OIS_OIS => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_OIS_OIS = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisSensitivityMarginOuterEntry :
			_oisSensitivityMargin.entrySet())
		{
			java.lang.String outerTenor = oisSensitivityMarginOuterEntry.getKey();

			double outerSensitivityMargin = oisSensitivityMarginOuterEntry.getValue();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisSensitivityMarginInnerEntry :
				_oisSensitivityMargin.entrySet())
			{
				java.lang.String innerTenor = oisSensitivityMarginInnerEntry.getKey();

				double crossTenorCorrelation = outerTenor.equalsIgnoreCase (innerTenor) ? 1. :
					tenorCorrelation.entry (
						outerTenor,
						innerTenor
					);

				curvatureMarginCovariance_OIS_OIS = curvatureMarginCovariance_OIS_OIS +
					outerSensitivityMargin * oisSensitivityMarginInnerEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_OIS_OIS;
	}

	/**
	 * Compute the Linear LIBOR1M-LIBOR1M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR1M-LIBOR1M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR1M_LIBOR1M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_LIBOR1M_LIBOR1M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR1M_LIBOR1M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MSensitivityMarginOuterEntry :
			_libor1MSensitivityMargin.entrySet())
		{
			double outerSensitivityMargin = libor1MSensitivityMarginOuterEntry.getValue();

			java.lang.String outerTenor = libor1MSensitivityMarginOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MSensitivityMarginInnerEntry :
				_libor1MSensitivityMargin.entrySet())
			{
				java.lang.String innerTenor = libor1MSensitivityMarginInnerEntry.getKey();

				linearMarginCovariance_LIBOR1M_LIBOR1M = linearMarginCovariance_LIBOR1M_LIBOR1M +
					outerSensitivityMargin * libor1MSensitivityMarginInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (innerTenor) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR1M_LIBOR1M;
	}

	/**
	 * Compute the Curvature LIBOR1M-LIBOR1M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR1M-LIBOR1M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR1M_LIBOR1M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR1M_LIBOR1M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR1M_LIBOR1M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MSensitivityMarginOuterEntry :
			_libor1MSensitivityMargin.entrySet())
		{
			double outerSensitivityMargin = libor1MSensitivityMarginOuterEntry.getValue();

			java.lang.String outerTenor = libor1MSensitivityMarginOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MSensitivityMarginInnerEntry :
				_libor1MSensitivityMargin.entrySet())
			{
				java.lang.String innerTenor = libor1MSensitivityMarginInnerEntry.getKey();

				double crossTenorCorrelation = outerTenor.equalsIgnoreCase (innerTenor) ? 1. :
					tenorCorrelation.entry (
						outerTenor,
						innerTenor
					);

				linearMarginCovariance_LIBOR1M_LIBOR1M = linearMarginCovariance_LIBOR1M_LIBOR1M +
					outerSensitivityMargin * libor1MSensitivityMarginInnerEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return linearMarginCovariance_LIBOR1M_LIBOR1M;
	}

	/**
	 * Compute the Linear LIBOR3M-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR3M-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR3M_LIBOR3M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_LIBOR3M_LIBOR3M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR3M_LIBOR3M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MSensitivityMarginOuterEntry :
			_libor3MSensitivityMargin.entrySet())
		{
			double outerSensitivityMargin = libor3MSensitivityMarginOuterEntry.getValue();

			java.lang.String outerTenor = libor3MSensitivityMarginOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MSensitivityMarginInnerEntry :
				_libor3MSensitivityMargin.entrySet())
			{
				java.lang.String innerTenor = libor3MSensitivityMarginInnerEntry.getKey();

				linearMarginCovariance_LIBOR3M_LIBOR3M = linearMarginCovariance_LIBOR3M_LIBOR3M +
					outerSensitivityMargin * libor3MSensitivityMarginInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (innerTenor) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR3M_LIBOR3M;
	}

	/**
	 * Compute the Curvature LIBOR3M-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR3M-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR3M_LIBOR3M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR3M_LIBOR3M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR3M_LIBOR3M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MSensitivityMarginOuterEntry :
			_libor3MSensitivityMargin.entrySet())
		{
			double outerSensitivityMargin = libor3MSensitivityMarginOuterEntry.getValue();

			java.lang.String outerTenor = libor3MSensitivityMarginOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MSensitivityMarginInnerEntry :
				_libor3MSensitivityMargin.entrySet())
			{
				java.lang.String innerTenor = libor3MSensitivityMarginInnerEntry.getKey();

				double crossTenorCorrelation = outerTenor.equalsIgnoreCase (innerTenor) ? 1. :
					tenorCorrelation.entry (
						outerTenor,
						innerTenor
					);

				curvatureMarginCovariance_LIBOR3M_LIBOR3M = curvatureMarginCovariance_LIBOR3M_LIBOR3M +
					outerSensitivityMargin * libor3MSensitivityMarginInnerEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR3M_LIBOR3M;
	}

	/**
	 * Compute the Linear LIBOR6M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR6M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR6M_LIBOR6M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_LIBOR6M_LIBOR6M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR6M_LIBOR6M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MSensitivityMarginOuterEntry :
			_libor6MSensitivityMargin.entrySet())
		{
			double outerSensitivityMargin = libor6MSensitivityMarginOuterEntry.getValue();

			java.lang.String outerTenor = libor6MSensitivityMarginOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MSensitivityMarginInnerEntry :
				_libor6MSensitivityMargin.entrySet())
			{
				java.lang.String innerTenor = libor6MSensitivityMarginInnerEntry.getKey();

				linearMarginCovariance_LIBOR6M_LIBOR6M = linearMarginCovariance_LIBOR6M_LIBOR6M +
					outerSensitivityMargin * libor6MSensitivityMarginInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (innerTenor) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR6M_LIBOR6M;
	}

	/**
	 * Compute the Curvature LIBOR6M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR6M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR6M_LIBOR6M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR6M_LIBOR6M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR6M_LIBOR6M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MSensitivityMarginOuterEntry :
			_libor6MSensitivityMargin.entrySet())
		{
			double outerSensitivityMargin = libor6MSensitivityMarginOuterEntry.getValue();

			java.lang.String outerTenor = libor6MSensitivityMarginOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MSensitivityMarginInnerEntry :
				_libor6MSensitivityMargin.entrySet())
			{
				java.lang.String innerTenor = libor6MSensitivityMarginInnerEntry.getKey();

				double crossTenorCorrelation = outerTenor.equalsIgnoreCase (innerTenor) ? 1. :
					tenorCorrelation.entry (
						outerTenor,
						innerTenor
					);

				curvatureMarginCovariance_LIBOR6M_LIBOR6M = curvatureMarginCovariance_LIBOR6M_LIBOR6M +
					outerSensitivityMargin * libor6MSensitivityMarginInnerEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR6M_LIBOR6M;
	}

	/**
	 * Compute the Linear LIBOR12M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR12M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR12M_LIBOR12M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::marginCovariance_LIBOR12M_LIBOR12M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR12M_LIBOR12M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MSensitivityMarginOuterEntry :
			_libor12MSensitivityMargin.entrySet())
		{
			double outerSensitivityMargin = libor12MSensitivityMarginOuterEntry.getValue();

			java.lang.String outerTenor = libor12MSensitivityMarginOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MSensitivityMarginInnerEntry
				: _libor12MSensitivityMargin.entrySet())
			{
				java.lang.String innerTenor = libor12MSensitivityMarginInnerEntry.getKey();

				linearMarginCovariance_LIBOR12M_LIBOR12M = linearMarginCovariance_LIBOR12M_LIBOR12M +
					outerSensitivityMargin * libor12MSensitivityMarginInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (innerTenor) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR12M_LIBOR12M;
	}

	/**
	 * Compute the Curvature LIBOR12M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR12M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR12M_LIBOR12M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR12M_LIBOR12M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR12M_LIBOR12M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MSensitivityMarginOuterEntry :
			_libor12MSensitivityMargin.entrySet())
		{
			double outerSensitivityMargin = libor12MSensitivityMarginOuterEntry.getValue();

			java.lang.String outerTenor = libor12MSensitivityMarginOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MSensitivityMarginInnerEntry
				: _libor12MSensitivityMargin.entrySet())
			{
				java.lang.String innerTenor = libor12MSensitivityMarginInnerEntry.getKey();

				double crossTenorCorrelation = outerTenor.equalsIgnoreCase (innerTenor) ? 1. :
					tenorCorrelation.entry (
						outerTenor,
						innerTenor
					);

				curvatureMarginCovariance_LIBOR12M_LIBOR12M = curvatureMarginCovariance_LIBOR12M_LIBOR12M +
					outerSensitivityMargin * libor12MSensitivityMarginInnerEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR12M_LIBOR12M;
	}

	/**
	 * Compute the Linear PRIME-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear PRIME-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_PRIME_PRIME (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_PRIME_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_PRIME_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeSensitivityMarginOuterEntry :
			_primeSensitivityMargin.entrySet())
		{
			double outerSensitivityMargin = primeSensitivityMarginOuterEntry.getValue();

			java.lang.String outerTenor = primeSensitivityMarginOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeSensitivityMarginInnerEntry :
				_primeSensitivityMargin.entrySet())
			{
				java.lang.String innerTenor = primeSensitivityMarginInnerEntry.getKey();

				linearMarginCovariance_PRIME_PRIME = linearMarginCovariance_PRIME_PRIME +
					outerSensitivityMargin * primeSensitivityMarginInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (innerTenor) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return linearMarginCovariance_PRIME_PRIME;
	}

	/**
	 * Compute the Curvature PRIME-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature PRIME-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_PRIME_PRIME (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_PRIME_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_PRIME_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeSensitivityMarginOuterEntry :
			_primeSensitivityMargin.entrySet())
		{
			double outerSensitivityMargin = primeSensitivityMarginOuterEntry.getValue();

			java.lang.String outerTenor = primeSensitivityMarginOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeSensitivityMarginInnerEntry :
				_primeSensitivityMargin.entrySet())
			{
				java.lang.String innerTenor = primeSensitivityMarginInnerEntry.getKey();

				double crossTenorCorrelation = outerTenor.equalsIgnoreCase (innerTenor) ? 1. :
					tenorCorrelation.entry (
						outerTenor,
						innerTenor
					);

				curvatureMarginCovariance_PRIME_PRIME = curvatureMarginCovariance_PRIME_PRIME +
					outerSensitivityMargin * primeSensitivityMarginInnerEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_PRIME_PRIME;
	}

	/**
	 * Compute the Linear MUNICIPAL-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear MUNICIPAL-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_MUNICIPAL_MUNICIPAL (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_MUNICIPAL_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_MUNICIPAL_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalSensitivityMarginOuterEntry :
			_municipalSensitivityMargin.entrySet())
		{
			double outerSensitivityMargin = municipalSensitivityMarginOuterEntry.getValue();

			java.lang.String outerTenor = municipalSensitivityMarginOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalSensitivityMarginInnerEntry
				: _municipalSensitivityMargin.entrySet())
			{
				java.lang.String innerTenor = municipalSensitivityMarginInnerEntry.getKey();

				linearMarginCovariance_MUNICIPAL_MUNICIPAL = linearMarginCovariance_MUNICIPAL_MUNICIPAL +
					outerSensitivityMargin * municipalSensitivityMarginInnerEntry.getValue() * (
						outerTenor.equalsIgnoreCase (innerTenor) ? 1. : tenorCorrelation.entry (
							outerTenor,
							innerTenor
						)
					);
			}
		}

		return linearMarginCovariance_MUNICIPAL_MUNICIPAL;
	}

	/**
	 * Compute the Curvature MUNICIPAL-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature MUNICIPAL-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_MUNICIPAL_MUNICIPAL (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_MUNICIPAL_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_MUNICIPAL_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalSensitivityMarginOuterEntry :
			_municipalSensitivityMargin.entrySet())
		{
			double outerSensitivityMargin = municipalSensitivityMarginOuterEntry.getValue();

			java.lang.String outerTenor = municipalSensitivityMarginOuterEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalSensitivityMarginInnerEntry
				: _municipalSensitivityMargin.entrySet())
			{
				java.lang.String innerTenor = municipalSensitivityMarginInnerEntry.getKey();

				double crossTenorCorrelation = outerTenor.equalsIgnoreCase (innerTenor) ? 1. :
					tenorCorrelation.entry (
						outerTenor,
						innerTenor
					);

				curvatureMarginCovariance_MUNICIPAL_MUNICIPAL = curvatureMarginCovariance_MUNICIPAL_MUNICIPAL
					+ outerSensitivityMargin * municipalSensitivityMarginInnerEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_MUNICIPAL_MUNICIPAL;
	}

	/**
	 * Compute the Linear OIS-LIBOR1M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear OIS-LIBOR1M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_OIS_LIBOR1M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_OIS_LIBOR1M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_OIS_LIBOR1M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet())
		{
			java.lang.String oisTenor = oisSensitivityMarginEntry.getKey();

			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MSensitivityMarginEntry :
				_libor1MSensitivityMargin.entrySet())
			{
				java.lang.String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

				linearMarginCovariance_OIS_LIBOR1M = linearMarginCovariance_OIS_LIBOR1M +
					oisSensitivityMargin * libor1MSensitivityMarginEntry.getValue() * (
						oisTenor.equalsIgnoreCase (libor1MTenor) ? 1. : tenorCorrelation.entry (
							oisTenor,
							libor1MTenor
						)
					);
			}
		}

		return linearMarginCovariance_OIS_LIBOR1M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature OIS-LIBOR1M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature OIS-LIBOR1M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_OIS_LIBOR1M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_OIS_LIBOR1M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_OIS_LIBOR1M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet())
		{
			java.lang.String oisTenor = oisSensitivityMarginEntry.getKey();

			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MSensitivityMarginEntry :
				_libor1MSensitivityMargin.entrySet())
			{
				java.lang.String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = oisTenor.equalsIgnoreCase (libor1MTenor) ? 1. :
					tenorCorrelation.entry (
						oisTenor,
						libor1MTenor
					);

				curvatureMarginCovariance_OIS_LIBOR1M = curvatureMarginCovariance_OIS_LIBOR1M +
					oisSensitivityMargin * libor1MSensitivityMarginEntry.getValue() * crossTenorCorrelation *
						crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_OIS_LIBOR1M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear OIS-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear OIS-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_OIS_LIBOR3M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_OIS_LIBOR3M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_OIS_LIBOR3M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet())
		{
			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			java.lang.String oisTenor = oisSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MSensitivityMarginEntry :
				_libor3MSensitivityMargin.entrySet())
			{
				java.lang.String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

				linearMarginCovariance_OIS_LIBOR3M = linearMarginCovariance_OIS_LIBOR3M +
					oisSensitivityMargin * libor3MSensitivityMarginEntry.getValue() * (
						oisTenor.equalsIgnoreCase (libor3MTenor) ? 1. : tenorCorrelation.entry (
							oisTenor,
							libor3MTenor
						)
					);
			}
		}

		return linearMarginCovariance_OIS_LIBOR3M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature OIS-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature OIS-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_OIS_LIBOR3M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_OIS_LIBOR3M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_OIS_LIBOR3M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet())
		{
			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			java.lang.String oisTenor = oisSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MSensitivityMarginEntry :
				_libor3MSensitivityMargin.entrySet())
			{
				java.lang.String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = oisTenor.equalsIgnoreCase (libor3MTenor) ? 1. :
					tenorCorrelation.entry (
						oisTenor,
						libor3MTenor
					);

				curvatureMarginCovariance_OIS_LIBOR3M = curvatureMarginCovariance_OIS_LIBOR3M +
					oisSensitivityMargin * libor3MSensitivityMarginEntry.getValue() * crossTenorCorrelation *
						crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_OIS_LIBOR3M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear OIS-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear OIS-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_OIS_LIBOR6M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_OIS_LIBOR6M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_OIS_LIBOR6M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet())
		{
			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			java.lang.String oisTenor = oisSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MSensitivityMarginEntry :
				_libor6MSensitivityMargin.entrySet())
			{
				java.lang.String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

				linearMarginCovariance_OIS_LIBOR6M = linearMarginCovariance_OIS_LIBOR6M +
					oisSensitivityMargin * libor6MSensitivityMarginEntry.getValue() * (
						oisTenor.equalsIgnoreCase (libor6MTenor) ? 1. : tenorCorrelation.entry (
							oisTenor,
							libor6MTenor
						)
					);
			}
		}

		return linearMarginCovariance_OIS_LIBOR6M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature OIS-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature OIS-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_OIS_LIBOR6M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_OIS_LIBOR6M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_OIS_LIBOR6M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet())
		{
			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			java.lang.String oisTenor = oisSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MSensitivityMarginEntry :
				_libor6MSensitivityMargin.entrySet())
			{
				java.lang.String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = oisTenor.equalsIgnoreCase (libor6MTenor) ? 1. :
					tenorCorrelation.entry (
						oisTenor,
						libor6MTenor
					);

				curvatureMarginCovariance_OIS_LIBOR6M = curvatureMarginCovariance_OIS_LIBOR6M +
					oisSensitivityMargin * libor6MSensitivityMarginEntry.getValue() * crossTenorCorrelation *
						crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_OIS_LIBOR6M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear OIS-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear OIS-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_OIS_LIBOR12M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_OIS_LIBOR12M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_OIS_LIBOR12M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet())
		{
			double oisSensitivity = oisSensitivityMarginEntry.getValue();

			java.lang.String oisTenor = oisSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MSensitivityMarginEntry :
				_libor12MSensitivityMargin.entrySet())
			{
				java.lang.String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

				linearMarginCovariance_OIS_LIBOR12M = linearMarginCovariance_OIS_LIBOR12M +
					oisSensitivity * libor12MSensitivityMarginEntry.getValue() * (
						oisTenor.equalsIgnoreCase (libor12MTenor) ? 1. : tenorCorrelation.entry (
							oisTenor,
							libor12MTenor
						)
					);
			}
		}

		return linearMarginCovariance_OIS_LIBOR12M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature OIS-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature OIS-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_OIS_LIBOR12M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_OIS_LIBOR12M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_OIS_LIBOR12M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet())
		{
			double oisSensitivity = oisSensitivityMarginEntry.getValue();

			java.lang.String oisTenor = oisSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MSensitivityMarginEntry :
				_libor12MSensitivityMargin.entrySet())
			{
				java.lang.String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = oisTenor.equalsIgnoreCase (libor12MTenor) ? 1. :
					tenorCorrelation.entry (
						oisTenor,
						libor12MTenor
					);

				curvatureMarginCovariance_OIS_LIBOR12M = curvatureMarginCovariance_OIS_LIBOR12M +
					oisSensitivity * libor12MSensitivityMarginEntry.getValue() * crossTenorCorrelation *
						crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_OIS_LIBOR12M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear OIS-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear OIS-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_OIS_PRIME (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_OIS_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_OIS_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet())
		{
			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			java.lang.String oisTenor = oisSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeSensitivityMarginEntry :
				_primeSensitivityMargin.entrySet())
			{
				java.lang.String primeTenor = primeSensitivityMarginEntry.getKey();

				linearMarginCovariance_OIS_PRIME = linearMarginCovariance_OIS_PRIME + oisSensitivityMargin *
					primeSensitivityMarginEntry.getValue() * (
						oisTenor.equalsIgnoreCase (primeTenor) ? 1. : tenorCorrelation.entry (
							oisTenor,
							primeTenor
						)
					);
			}
		}

		return linearMarginCovariance_OIS_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature OIS-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature OIS-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_OIS_PRIME (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_OIS_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_OIS_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet())
		{
			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			java.lang.String oisTenor = oisSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeSensitivityMarginEntry :
				_primeSensitivityMargin.entrySet())
			{
				java.lang.String primeTenor = primeSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = oisTenor.equalsIgnoreCase (primeTenor) ? 1. :
					tenorCorrelation.entry (
						oisTenor,
						primeTenor
					);

				curvatureMarginCovariance_OIS_PRIME = curvatureMarginCovariance_OIS_PRIME +
					oisSensitivityMargin * primeSensitivityMarginEntry.getValue() * crossTenorCorrelation *
						crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_OIS_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear OIS-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear OIS-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_OIS_MUNICIPAL (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_OIS_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_OIS_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet())
		{
			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			java.lang.String oisTenor = oisSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet())
			{
				java.lang.String municipalTenor = municipalSensitivityMarginEntry.getKey();

				linearMarginCovariance_OIS_MUNICIPAL = linearMarginCovariance_OIS_MUNICIPAL +
					oisSensitivityMargin * municipalSensitivityMarginEntry.getValue() * (
						oisTenor.equalsIgnoreCase (municipalTenor) ? 1. : tenorCorrelation.entry (
							oisTenor,
							municipalTenor
						)
					);
			}
		}

		return linearMarginCovariance_OIS_MUNICIPAL * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature OIS-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature OIS-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_OIS_MUNICIPAL (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_OIS_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_OIS_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> oisSensitivityMarginEntry :
			_oisSensitivityMargin.entrySet())
		{
			double oisSensitivityMargin = oisSensitivityMarginEntry.getValue();

			java.lang.String oisTenor = oisSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet())
			{
				java.lang.String municipalTenor = municipalSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = oisTenor.equalsIgnoreCase (municipalTenor) ? 1. :
					tenorCorrelation.entry (
						oisTenor,
						municipalTenor
					);

				curvatureMarginCovariance_OIS_MUNICIPAL = curvatureMarginCovariance_OIS_MUNICIPAL +
					oisSensitivityMargin * municipalSensitivityMarginEntry.getValue() * crossTenorCorrelation
						* crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_OIS_MUNICIPAL * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR1M-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR1M-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR1M_LIBOR3M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_LIBOR1M_LIBOR3M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR1M_LIBOR3M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet())
		{
			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			java.lang.String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MSensitivityMarginEntry :
				_libor3MSensitivityMargin.entrySet())
			{
				java.lang.String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR1M_LIBOR3M = linearMarginCovariance_LIBOR1M_LIBOR3M +
					libor1MSensitivityMargin * libor3MSensitivityMarginEntry.getValue() * (
						libor1MTenor.equalsIgnoreCase (libor3MTenor) ? 1. : tenorCorrelation.entry (
							libor1MTenor,
							libor3MTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR1M_LIBOR3M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR1M-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR1M-LIBOR3M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR1M_LIBOR3M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR1M_LIBOR3M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR1M_LIBOR3M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet())
		{
			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			java.lang.String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MSensitivityMarginEntry :
				_libor3MSensitivityMargin.entrySet())
			{
				java.lang.String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor1MTenor.equalsIgnoreCase (libor3MTenor) ? 1. :
					tenorCorrelation.entry (
						libor1MTenor,
						libor3MTenor
					);

				curvatureMarginCovariance_LIBOR1M_LIBOR3M = curvatureMarginCovariance_LIBOR1M_LIBOR3M +
					libor1MSensitivityMargin * libor3MSensitivityMarginEntry.getValue() * 
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR1M_LIBOR3M *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR1M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR1M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR1M_LIBOR6M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_LIBOR1M_LIBOR6M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR1M_LIBOR6M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet())
		{
			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			java.lang.String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MSensitivityMarginEntry :
				_libor6MSensitivityMargin.entrySet())
			{
				java.lang.String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR1M_LIBOR6M = linearMarginCovariance_LIBOR1M_LIBOR6M +
					libor1MSensitivityMargin * libor6MSensitivityMarginEntry.getValue() * (
						libor1MTenor.equalsIgnoreCase (libor6MTenor) ? 1. : tenorCorrelation.entry (
							libor1MTenor,
							libor6MTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR1M_LIBOR6M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR1M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR1M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR1M_LIBOR6M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR1M_LIBOR6M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR1M_LIBOR6M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet())
		{
			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			java.lang.String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MSensitivityMarginEntry :
				_libor6MSensitivityMargin.entrySet())
			{
				java.lang.String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor1MTenor.equalsIgnoreCase (libor6MTenor) ? 1. :
					tenorCorrelation.entry (
						libor1MTenor,
						libor6MTenor
					);

				curvatureMarginCovariance_LIBOR1M_LIBOR6M = curvatureMarginCovariance_LIBOR1M_LIBOR6M +
					libor1MSensitivityMargin * libor6MSensitivityMarginEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR1M_LIBOR6M *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR1M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR1M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR1M_LIBOR12M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_LIBOR1M_LIBOR12M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR1M_LIBOR12M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet())
		{
			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			java.lang.String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MSensitivityMarginEntry :
				_libor12MSensitivityMargin.entrySet())
			{
				java.lang.String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR1M_LIBOR12M = linearMarginCovariance_LIBOR1M_LIBOR12M +
					libor1MSensitivityMargin * libor12MSensitivityMarginEntry.getValue() * (
						libor1MTenor.equalsIgnoreCase (libor12MTenor) ? 1. : tenorCorrelation.entry (
							libor1MTenor,
							libor12MTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR1M_LIBOR12M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR1M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR1M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR1M_LIBOR12M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR1M_LIBOR12M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR1M_LIBOR12M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet())
		{
			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			java.lang.String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MSensitivityMarginEntry :
				_libor12MSensitivityMargin.entrySet())
			{
				java.lang.String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor1MTenor.equalsIgnoreCase (libor12MTenor) ? 1. :
					tenorCorrelation.entry (
						libor1MTenor,
						libor12MTenor
					);

				curvatureMarginCovariance_LIBOR1M_LIBOR12M = curvatureMarginCovariance_LIBOR1M_LIBOR12M +
					libor1MSensitivityMargin * libor12MSensitivityMarginEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR1M_LIBOR12M *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR1M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR1M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR1M_PRIME (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_LIBOR1M_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR1M_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet())
		{
			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			java.lang.String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeSensitivityMarginEntry :
				_primeSensitivityMargin.entrySet())
			{
				java.lang.String primeTenor = primeSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR1M_PRIME = linearMarginCovariance_LIBOR1M_PRIME +
					libor1MSensitivityMargin * primeSensitivityMarginEntry.getValue() * (
						libor1MTenor.equalsIgnoreCase (primeTenor) ? 1. : tenorCorrelation.entry (
							libor1MTenor,
							primeTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR1M_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR1M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR1M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR1M_PRIME (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR1M_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR1M_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet())
		{
			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			java.lang.String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeSensitivityMarginEntry :
				_primeSensitivityMargin.entrySet())
			{
				java.lang.String primeTenor = primeSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor1MTenor.equalsIgnoreCase (primeTenor) ? 1. :
					tenorCorrelation.entry (
						libor1MTenor,
						primeTenor
					);

				curvatureMarginCovariance_LIBOR1M_PRIME = curvatureMarginCovariance_LIBOR1M_PRIME +
					libor1MSensitivityMargin * primeSensitivityMarginEntry.getValue() * crossTenorCorrelation
						* crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR1M_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR1M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR1M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR1M_MUNICIPAL (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_LIBOR1M_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR1M_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet())
		{
			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			java.lang.String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet())
			{
				java.lang.String municipalTenor = municipalSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR1M_MUNICIPAL = linearMarginCovariance_LIBOR1M_MUNICIPAL +
					libor1MSensitivityMargin * municipalSensitivityMarginEntry.getValue() * (
						libor1MTenor.equalsIgnoreCase (municipalTenor) ? 1. : tenorCorrelation.entry (
							libor1MTenor,
							municipalTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR1M_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR1M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR1M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR1M_MUNICIPAL (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR1M_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR1M_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor1MSensitivityMarginEntry :
			_libor1MSensitivityMargin.entrySet())
		{
			double libor1MSensitivityMargin = libor1MSensitivityMarginEntry.getValue();

			java.lang.String libor1MTenor = libor1MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet())
			{
				java.lang.String municipalTenor = municipalSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor1MTenor.equalsIgnoreCase (municipalTenor) ? 1. :
					tenorCorrelation.entry (
						libor1MTenor,
						municipalTenor
					);

				curvatureMarginCovariance_LIBOR1M_MUNICIPAL = curvatureMarginCovariance_LIBOR1M_MUNICIPAL +
					libor1MSensitivityMargin * municipalSensitivityMarginEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR1M_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR3M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR3M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR3M_LIBOR6M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_LIBOR3M_LIBOR6M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR3M_LIBOR6M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MSensitivityMarginEntry :
			_libor3MSensitivityMargin.entrySet())
		{
			double libor3MSensitivityMargin = libor3MSensitivityMarginEntry.getValue();

			java.lang.String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MSensitivityMarginEntry :
				_libor6MSensitivityMargin.entrySet())
			{
				java.lang.String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR3M_LIBOR6M = linearMarginCovariance_LIBOR3M_LIBOR6M +
					libor3MSensitivityMargin * libor6MSensitivityMarginEntry.getValue() * (
						libor3MTenor.equalsIgnoreCase (libor6MTenor) ? 1. : tenorCorrelation.entry (
							libor3MTenor,
							libor6MTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR3M_LIBOR6M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR3M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR3M-LIBOR6M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR3M_LIBOR6M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR3M_LIBOR6M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR3M_LIBOR6M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MSensitivityMarginEntry :
			_libor3MSensitivityMargin.entrySet())
		{
			double libor3MSensitivityMargin = libor3MSensitivityMarginEntry.getValue();

			java.lang.String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MSensitivityMarginEntry :
				_libor6MSensitivityMargin.entrySet())
			{
				java.lang.String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor3MTenor.equalsIgnoreCase (libor6MTenor) ? 1. :
					tenorCorrelation.entry (
						libor3MTenor,
						libor6MTenor
					);

				curvatureMarginCovariance_LIBOR3M_LIBOR6M = curvatureMarginCovariance_LIBOR3M_LIBOR6M +
					libor3MSensitivityMargin * libor6MSensitivityMarginEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR3M_LIBOR6M *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR3M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR3M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR3M_LIBOR12M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_LIBOR3M_LIBOR12M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR3M_LIBOR12M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MSensitivityMarginEntry :
			_libor3MSensitivityMargin.entrySet())
		{
			double libor3MSensitivityMargin = libor3MSensitivityMarginEntry.getValue();

			java.lang.String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MSensitivityMarginEntry :
				_libor12MSensitivityMargin.entrySet())
			{
				java.lang.String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR3M_LIBOR12M = linearMarginCovariance_LIBOR3M_LIBOR12M +
					libor3MSensitivityMargin * libor12MSensitivityMarginEntry.getValue() * (
						libor3MTenor.equalsIgnoreCase (libor12MTenor) ? 1. : tenorCorrelation.entry (
							libor3MTenor,
							libor12MTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR3M_LIBOR12M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR3M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR3M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR3M_LIBOR12M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR3M_LIBOR12M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR3M_LIBOR12M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MSensitivityMarginEntry :
			_libor3MSensitivityMargin.entrySet())
		{
			double libor3MSensitivityMargin = libor3MSensitivityMarginEntry.getValue();

			java.lang.String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MSensitivityMarginEntry :
				_libor12MSensitivityMargin.entrySet())
			{
				java.lang.String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor3MTenor.equalsIgnoreCase (libor12MTenor) ? 1. :
					tenorCorrelation.entry (
						libor3MTenor,
						libor12MTenor
					);

				curvatureMarginCovariance_LIBOR3M_LIBOR12M = curvatureMarginCovariance_LIBOR3M_LIBOR12M +
					libor3MSensitivityMargin * libor12MSensitivityMarginEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR3M_LIBOR12M *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR3M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR3M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR3M_PRIME (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_LIBOR3M_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR3M_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MSensitivityMarginEntry :
			_libor3MSensitivityMargin.entrySet())
		{
			double libor3MSensitivityMargin = libor3MSensitivityMarginEntry.getValue();

			java.lang.String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeSensitivityMarginEntry :
				_primeSensitivityMargin.entrySet())
			{
				java.lang.String primeTenor = primeSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR3M_PRIME = linearMarginCovariance_LIBOR3M_PRIME +
					libor3MSensitivityMargin * primeSensitivityMarginEntry.getValue() * (
						libor3MTenor.equalsIgnoreCase (primeTenor) ? 1. : tenorCorrelation.entry (
							libor3MTenor,
							primeTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR3M_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR3M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR3M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR3M_PRIME (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR3M_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR3M_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MSensitivityMarginEntry :
			_libor3MSensitivityMargin.entrySet())
		{
			double libor3MSensitivityMargin = libor3MSensitivityMarginEntry.getValue();

			java.lang.String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeSensitivityMarginEntry :
				_primeSensitivityMargin.entrySet())
			{
				java.lang.String primeTenor = primeSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor3MTenor.equalsIgnoreCase (primeTenor) ? 1. :
					tenorCorrelation.entry (
						libor3MTenor,
						primeTenor
					);

				curvatureMarginCovariance_LIBOR3M_PRIME = curvatureMarginCovariance_LIBOR3M_PRIME +
					libor3MSensitivityMargin * primeSensitivityMarginEntry.getValue() * crossTenorCorrelation
						* crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR3M_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR3M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR3M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR3M_MUNICIPAL (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_LIBOR3M_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR3M_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MSensitivityMarginEntry :
			_libor3MSensitivityMargin.entrySet())
		{
			double libor3MSensitivityMargin = libor3MSensitivityMarginEntry.getValue();

			java.lang.String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet())
			{
				java.lang.String municipalTenor = municipalSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR3M_MUNICIPAL = linearMarginCovariance_LIBOR3M_MUNICIPAL +
					libor3MSensitivityMargin * municipalSensitivityMarginEntry.getValue() * (
						libor3MTenor.equalsIgnoreCase (municipalTenor) ? 1. : tenorCorrelation.entry (
							libor3MTenor,
							municipalTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR3M_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR3M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR3M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR3M_MUNICIPAL (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR3M_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR3M_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor3MSensitivityMarginEntry :
			_libor3MSensitivityMargin.entrySet())
		{
			double libor3MSensitivityMargin = libor3MSensitivityMarginEntry.getValue();

			java.lang.String libor3MTenor = libor3MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet())
			{
				java.lang.String municipalTenor = municipalSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor3MTenor.equalsIgnoreCase (municipalTenor) ? 1. :
					tenorCorrelation.entry (
						libor3MTenor,
						municipalTenor
					);

				curvatureMarginCovariance_LIBOR3M_MUNICIPAL = curvatureMarginCovariance_LIBOR3M_MUNICIPAL +
					libor3MSensitivityMargin * municipalSensitivityMarginEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR3M_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR6M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR6M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR6M_LIBOR12M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_LIBOR6M_LIBOR12M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR6M_LIBOR12M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MSensitivityMarginEntry :
			_libor6MSensitivityMargin.entrySet())
		{
			double libor6MSensitivityMargin = libor6MSensitivityMarginEntry.getValue();

			java.lang.String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MSensitivityMarginEntry :
				_libor12MSensitivityMargin.entrySet())
			{
				java.lang.String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR6M_LIBOR12M = linearMarginCovariance_LIBOR6M_LIBOR12M +
					libor6MSensitivityMargin * libor12MSensitivityMarginEntry.getValue() * (
						libor6MTenor.equalsIgnoreCase (libor12MTenor) ? 1. : tenorCorrelation.entry (
							libor6MTenor,
							libor12MTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR6M_LIBOR12M * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR6M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR6M-LIBOR12M Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR6M_LIBOR12M (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR6M_LIBOR12M => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR6M_LIBOR12M = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MSensitivityMarginEntry :
			_libor6MSensitivityMargin.entrySet())
		{
			double libor6MSensitivityMargin = libor6MSensitivityMarginEntry.getValue();

			java.lang.String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MSensitivityMarginEntry :
				_libor12MSensitivityMargin.entrySet())
			{
				java.lang.String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor6MTenor.equalsIgnoreCase (libor12MTenor) ? 1. :
					tenorCorrelation.entry (
						libor6MTenor,
						libor12MTenor
					);

				curvatureMarginCovariance_LIBOR6M_LIBOR12M = curvatureMarginCovariance_LIBOR6M_LIBOR12M +
					libor6MSensitivityMargin * libor12MSensitivityMarginEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR6M_LIBOR12M *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR6M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR6M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR6M_PRIME (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_LIBOR6M_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR6M_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MSensitivityMarginEntry :
			_libor6MSensitivityMargin.entrySet())
		{
			double libor6MSensitivityMargin = libor6MSensitivityMarginEntry.getValue();

			java.lang.String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeSensitivityMarginEntry :
				_primeSensitivityMargin.entrySet())
			{
				java.lang.String primeTenor = primeSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR6M_PRIME = linearMarginCovariance_LIBOR6M_PRIME +
					libor6MSensitivityMargin * primeSensitivityMarginEntry.getValue() * (
						libor6MTenor.equalsIgnoreCase (primeTenor) ? 1. : tenorCorrelation.entry (
							libor6MTenor,
							primeTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR6M_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR6M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR6M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR6M_PRIME (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR6M_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR6M_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MSensitivityMarginEntry :
			_libor6MSensitivityMargin.entrySet())
		{
			double libor6MSensitivityMargin = libor6MSensitivityMarginEntry.getValue();

			java.lang.String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeSensitivityMarginEntry :
				_primeSensitivityMargin.entrySet())
			{
				java.lang.String primeTenor = primeSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor6MTenor.equalsIgnoreCase (primeTenor) ? 1. :
					tenorCorrelation.entry (
						libor6MTenor,
						primeTenor
					);

				curvatureMarginCovariance_LIBOR6M_PRIME = curvatureMarginCovariance_LIBOR6M_PRIME +
					libor6MSensitivityMargin * primeSensitivityMarginEntry.getValue() * crossTenorCorrelation
						* crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR6M_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR6M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR6M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR6M_MUNICIPAL (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_LIBOR6M_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR6M_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MSensitivityMarginEntry :
			_libor6MSensitivityMargin.entrySet())
		{
			double libor6MSensitivityMargin = libor6MSensitivityMarginEntry.getValue();

			java.lang.String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet())
			{
				java.lang.String municipalTenor = municipalSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR6M_MUNICIPAL = linearMarginCovariance_LIBOR6M_MUNICIPAL +
					libor6MSensitivityMargin * municipalSensitivityMarginEntry.getValue() * (
						libor6MTenor.equalsIgnoreCase (municipalTenor) ? 1. : tenorCorrelation.entry (
							libor6MTenor,
							municipalTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR6M_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR6M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR6M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR6M_MUNICIPAL (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR6M_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR6M_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor6MSensitivityMarginEntry :
			_libor6MSensitivityMargin.entrySet())
		{
			double libor6MSensitivityMargin = libor6MSensitivityMarginEntry.getValue();

			java.lang.String libor6MTenor = libor6MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet())
			{
				java.lang.String municipalTenor = municipalSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor6MTenor.equalsIgnoreCase (municipalTenor) ? 1. :
					tenorCorrelation.entry (
						libor6MTenor,
						municipalTenor
					);

				curvatureMarginCovariance_LIBOR6M_MUNICIPAL = curvatureMarginCovariance_LIBOR6M_MUNICIPAL +
					libor6MSensitivityMargin * municipalSensitivityMarginEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR6M_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR12M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR12M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR12M_PRIME (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_LIBOR12M_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR12M_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MSensitivityMarginEntry :
			_libor12MSensitivityMargin.entrySet())
		{
			double libor12MSensitivityMargin = libor12MSensitivityMarginEntry.getValue();

			java.lang.String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeSensitivityMarginEntry :
				_primeSensitivityMargin.entrySet())
			{
				java.lang.String primeTenor = primeSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR12M_PRIME = linearMarginCovariance_LIBOR12M_PRIME +
					libor12MSensitivityMargin * primeSensitivityMarginEntry.getValue() * (
						libor12MTenor.equalsIgnoreCase (primeTenor) ? 1. : tenorCorrelation.entry (
							libor12MTenor,
							primeTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR12M_PRIME * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR12M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR12M-PRIME Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR12M_PRIME (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR12M_PRIME => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR12M_PRIME = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MSensitivityMarginEntry :
			_libor12MSensitivityMargin.entrySet())
		{
			double libor12MSensitivityMargin = libor12MSensitivityMarginEntry.getValue();

			java.lang.String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeSensitivityMarginEntry :
				_primeSensitivityMargin.entrySet())
			{
				java.lang.String primeTenor = primeSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor12MTenor.equalsIgnoreCase (primeTenor) ? 1. :
					tenorCorrelation.entry (
						libor12MTenor,
						primeTenor
					);

				curvatureMarginCovariance_LIBOR12M_PRIME = curvatureMarginCovariance_LIBOR12M_PRIME +
					libor12MSensitivityMargin * primeSensitivityMarginEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR12M_PRIME *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear LIBOR12M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear LIBOR12M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_LIBOR12M_MUNICIPAL (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_LIBOR12M_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_LIBOR12M_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MSensitivityMarginEntry :
			_libor12MSensitivityMargin.entrySet())
		{
			double libor12MSensitivityMargin = libor12MSensitivityMarginEntry.getValue();

			java.lang.String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet())
			{
				java.lang.String municipalTenor = municipalSensitivityMarginEntry.getKey();

				linearMarginCovariance_LIBOR12M_MUNICIPAL = linearMarginCovariance_LIBOR12M_MUNICIPAL +
					libor12MSensitivityMargin * municipalSensitivityMarginEntry.getValue() * (
						libor12MTenor.equalsIgnoreCase (municipalTenor) ? 1. : tenorCorrelation.entry (
							libor12MTenor,
							municipalTenor
						)
					);
			}
		}

		return linearMarginCovariance_LIBOR12M_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature LIBOR12M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature LIBOR12M-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_LIBOR12M_MUNICIPAL (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_LIBOR12M_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_LIBOR12M_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> libor12MSensitivityMarginEntry :
			_libor12MSensitivityMargin.entrySet())
		{
			double libor12MSensitivityMargin = libor12MSensitivityMarginEntry.getValue();

			java.lang.String libor12MTenor = libor12MSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet())
			{
				java.lang.String municipalTenor = municipalSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = libor12MTenor.equalsIgnoreCase (municipalTenor) ? 1. :
					tenorCorrelation.entry (
						libor12MTenor,
						municipalTenor
					);

				curvatureMarginCovariance_LIBOR12M_MUNICIPAL = curvatureMarginCovariance_LIBOR12M_MUNICIPAL +
					libor12MSensitivityMargin * municipalSensitivityMarginEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_LIBOR12M_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear PRIME-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear PRIME-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double linearMarginCovariance_PRIME_MUNICIPAL (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::linearMarginCovariance_PRIME_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double linearMarginCovariance_PRIME_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeSensitivityMarginEntry :
			_primeSensitivityMargin.entrySet())
		{
			double primeSensitivityMargin = primeSensitivityMarginEntry.getValue();

			java.lang.String primeTenor = primeSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet())
			{
				java.lang.String municipalTenor = municipalSensitivityMarginEntry.getKey();

				linearMarginCovariance_PRIME_MUNICIPAL = linearMarginCovariance_PRIME_MUNICIPAL +
					primeSensitivityMargin * municipalSensitivityMarginEntry.getValue() * (
						primeTenor.equalsIgnoreCase (municipalTenor) ? 1. : tenorCorrelation.entry (
							primeTenor,
							municipalTenor
						)
					);
			}
		}

		return linearMarginCovariance_PRIME_MUNICIPAL * bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Curvature PRIME-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature PRIME-MUNICIPAL Sensitivity Margin Co-variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double curvatureMarginCovariance_PRIME_MUNICIPAL (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
		throws java.lang.Exception
	{
		if (null == bucketSensitivitySettingsIR)
		{
			throw new java.lang.Exception
				("IRFactorAggregate::curvatureMarginCovariance_PRIME_MUNICIPAL => Invalid Inputs");
		}

		org.drip.measure.stochastic.LabelCorrelation tenorCorrelation =
			bucketSensitivitySettingsIR.crossTenorCorrelation();

		double curvatureMarginCovariance_PRIME_MUNICIPAL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> primeSensitivityMarginEntry :
			_primeSensitivityMargin.entrySet())
		{
			double primeSensitivityMargin = primeSensitivityMarginEntry.getValue();

			java.lang.String primeTenor = primeSensitivityMarginEntry.getKey();

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> municipalSensitivityMarginEntry :
				_municipalSensitivityMargin.entrySet())
			{
				java.lang.String municipalTenor = municipalSensitivityMarginEntry.getKey();

				double crossTenorCorrelation = primeTenor.equalsIgnoreCase (municipalTenor) ? 1. :
					tenorCorrelation.entry (
						primeTenor,
						municipalTenor
					);

				curvatureMarginCovariance_PRIME_MUNICIPAL = curvatureMarginCovariance_PRIME_MUNICIPAL +
					primeSensitivityMargin * municipalSensitivityMarginEntry.getValue() *
						crossTenorCorrelation * crossTenorCorrelation;
			}
		}

		return curvatureMarginCovariance_PRIME_MUNICIPAL *
			bucketSensitivitySettingsIR.crossCurveCorrelation();
	}

	/**
	 * Compute the Linear Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Linear Margin Co-variance
	 */

	public org.drip.simm20.margin.IRSensitivityAggregate linearMargin (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
	{
		try
		{
			return new org.drip.simm20.margin.IRSensitivityAggregate (
				linearMarginCovariance_OIS_OIS (bucketSensitivitySettingsIR),
				linearMarginCovariance_OIS_LIBOR1M (bucketSensitivitySettingsIR),
				linearMarginCovariance_OIS_LIBOR3M (bucketSensitivitySettingsIR),
				linearMarginCovariance_OIS_LIBOR6M (bucketSensitivitySettingsIR),
				linearMarginCovariance_OIS_LIBOR12M (bucketSensitivitySettingsIR),
				linearMarginCovariance_OIS_PRIME (bucketSensitivitySettingsIR),
				linearMarginCovariance_OIS_MUNICIPAL (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR1M_LIBOR1M (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR1M_LIBOR3M (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR1M_LIBOR6M (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR1M_LIBOR12M (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR1M_PRIME (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR1M_MUNICIPAL (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR3M_LIBOR3M (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR3M_LIBOR6M (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR3M_LIBOR12M (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR3M_PRIME (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR3M_MUNICIPAL (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR6M_LIBOR6M (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR6M_LIBOR12M (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR6M_PRIME (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR6M_MUNICIPAL (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR12M_LIBOR12M (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR12M_PRIME (bucketSensitivitySettingsIR),
				linearMarginCovariance_LIBOR12M_MUNICIPAL (bucketSensitivitySettingsIR),
				linearMarginCovariance_PRIME_PRIME (bucketSensitivitySettingsIR),
				linearMarginCovariance_PRIME_MUNICIPAL (bucketSensitivitySettingsIR),
				linearMarginCovariance_MUNICIPAL_MUNICIPAL (bucketSensitivitySettingsIR),
				cumulativeSensitivityMargin()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Curvature Margin Co-variance
	 * 
	 * @param bucketSensitivitySettingsIR The IR Currency Bucket Curve Tenor Sensitivity Settings
	 * 
	 * @return The Curvature Margin Co-variance
	 */

	public org.drip.simm20.margin.IRSensitivityAggregate curvatureMargin (
		final org.drip.simm20.parameters.BucketSensitivitySettingsIR bucketSensitivitySettingsIR)
	{
		try
		{
			return new org.drip.simm20.margin.IRSensitivityAggregate (
				curvatureMarginCovariance_OIS_OIS (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_OIS_LIBOR1M (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_OIS_LIBOR3M (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_OIS_LIBOR6M (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_OIS_LIBOR12M (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_OIS_PRIME (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_OIS_MUNICIPAL (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR1M_LIBOR1M (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR1M_LIBOR3M (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR1M_LIBOR6M (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR1M_LIBOR12M (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR1M_PRIME (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR1M_MUNICIPAL (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR3M_LIBOR3M (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR3M_LIBOR6M (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR3M_LIBOR12M (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR3M_PRIME (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR3M_MUNICIPAL (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR6M_LIBOR6M (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR6M_LIBOR12M (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR6M_PRIME (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR6M_MUNICIPAL (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR12M_LIBOR12M (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR12M_PRIME (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_LIBOR12M_MUNICIPAL (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_PRIME_PRIME (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_PRIME_MUNICIPAL (bucketSensitivitySettingsIR),
				curvatureMarginCovariance_MUNICIPAL_MUNICIPAL (bucketSensitivitySettingsIR),
				cumulativeSensitivityMargin()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
