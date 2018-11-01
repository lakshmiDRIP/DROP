
package org.drip.simm.common;

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
 * RiskPairPrincipalCovariance contains the Cross Risk-Factor Principal Component Based Co-variance. The
 *  References are:
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

public class RiskPairPrincipalCovariance
{
	private double _crossCorrelation = java.lang.Double.NaN;
	private org.drip.quant.eigen.EigenComponent _principalEigenComponent = null;

	/**
	 * Construct the Standard RiskPairPrincipalCovariance Instance from the Bucket Correlation Matrix and the
	 *  Cross Correlation Entry
	 * 
	 * @param bucketCorrelationMatrix The Intra-Bucket Correlation Matrix
	 * @param crossCorrelation Cross Bucket Correlation
	 * 
	 * @return The Standard RiskPairPrincipalCovariance Instance
	 */

	public static final RiskPairPrincipalCovariance Standard (
		final double[][] bucketCorrelationMatrix,
		final double crossCorrelation)
	{
		try
		{
			return new RiskPairPrincipalCovariance (
				new org.drip.quant.eigen.PowerIterationComponentExtractor (
					30,
					0.000001,
					false
				).principalComponent (bucketCorrelationMatrix),
				crossCorrelation
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RiskPairPrincipalCovariance Constructor
	 * 
	 * @param principalEigenComponent Bucket's Principal Eigen-Component
	 * @param crossCorrelation Cross Bucket Correlation
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskPairPrincipalCovariance (
		final org.drip.quant.eigen.EigenComponent principalEigenComponent,
		final double crossCorrelation)
		throws java.lang.Exception
	{
		if (null == (_principalEigenComponent = principalEigenComponent) ||
			!org.drip.quant.common.NumberUtil.IsValid (_crossCorrelation = crossCorrelation) ||
				-1. > _crossCorrelation || 1. < _crossCorrelation)
		{
			throw new java.lang.Exception ("RiskPairPrincipalCovariance Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Principal Eigen-Component
	 * 
	 * @return The Principal Eigen-Component
	 */

	public org.drip.quant.eigen.EigenComponent principalEigenComponent()
	{
		return _principalEigenComponent;
	}

	/**
	 * Retrieve the Bucket's Cross Correlation
	 * 
	 * @return The Bucket's Cross Correlation
	 */

	public double crossCorrelation()
	{
		return _crossCorrelation;
	}

	/**
	 * Retrieve the Scaled Principal Eigen-vector
	 * 
	 * @return The Scaled Principal Eigen-vector
	 */

	public double[] scaledPrincipalEigenvector()
	{
		double scaleFactor = java.lang.Math.sqrt (_principalEigenComponent.eigenvalue());

		double[] principalEigenvector = _principalEigenComponent.eigenvector();

		int componentCount = principalEigenvector.length;
		double[] scaledPrincipalEigenvector = new double[componentCount];

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex)
		{
			scaledPrincipalEigenvector[componentIndex] = principalEigenvector[componentIndex] * scaleFactor;
		}

		return scaledPrincipalEigenvector;
	}

	/**
	 * Retrieve the Unadjusted Cross-Bucket Co-variance
	 * 
	 * @return The Unadjusted Cross-Bucket Co-variance
	 */

	public double[][] unadjustedCovariance()
	{
		double[] scaledPrincipalEigenvector = scaledPrincipalEigenvector();

		return org.drip.quant.linearalgebra.Matrix.CrossProduct (
			scaledPrincipalEigenvector,
			scaledPrincipalEigenvector
		);
	}

	/**
	 * Retrieve the Adjusted Cross-Bucket Co-variance
	 * 
	 * @return The Adjusted Cross-Bucket Co-variance
	 */

	public double[][] adjustedCovariance()
	{
		return org.drip.quant.linearalgebra.Matrix.Scale2D (
			unadjustedCovariance(),
			_crossCorrelation
		);
	}
}
