
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
 * BucketAggregateIR holds the Single Bucket IR Sensitivity Margin, the Cumulative Bucket Risk Factor
 *  Sensitivity Margin, as well as the IR Aggregate Risk Factor Maps. The References are:
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

public class BucketAggregateIR
{
	private double _sensitivityMarginVariance = java.lang.Double.NaN;
	private double _cumulativeRiskFactorSensitivityMargin = java.lang.Double.NaN;
	private org.drip.simm20.margin.RiskFactorAggregateIR _riskFactorAggregateIR = null;
	private org.drip.simm20.margin.IRSensitivityAggregate _irSensitivityAggregate = null;

	/**
	 * BucketAggregateIR Constructor
	 * 
	 * @param riskFactorAggregateIR The Risk Factor Aggregate IR
	 * @param irSensitivityAggregate The IR Sensitivity Aggregate
	 * @param sensitivityMarginVariance The Bucket's Sensitivity Margin Variance
	 * @param cumulativeRiskFactorSensitivityMargin The Cumulative Risk Factor Sensitivity Margin
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketAggregateIR (
		final org.drip.simm20.margin.RiskFactorAggregateIR riskFactorAggregateIR,
		final org.drip.simm20.margin.IRSensitivityAggregate irSensitivityAggregate,
		final double sensitivityMarginVariance,
		final double cumulativeRiskFactorSensitivityMargin)
		throws java.lang.Exception
	{
		if (null == (_riskFactorAggregateIR = riskFactorAggregateIR) ||
			null == (_irSensitivityAggregate = irSensitivityAggregate) ||
			!org.drip.quant.common.NumberUtil.IsValid (_sensitivityMarginVariance =
				sensitivityMarginVariance) ||
			!org.drip.quant.common.NumberUtil.IsValid (_cumulativeRiskFactorSensitivityMargin =
				cumulativeRiskFactorSensitivityMargin))
		{
			throw new java.lang.Exception ("BucketAggregateIR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Risk Factor Aggregate IR
	 * 
	 * @return The Risk Factor Aggregate IR
	 */

	public org.drip.simm20.margin.RiskFactorAggregateIR riskFactorAggregateIR()
	{
		return _riskFactorAggregateIR;
	}

	/**
	 * Retrieve the IR Sensitivity Aggregate
	 * 
	 * @return The IR Sensitivity Aggregate
	 */

	public org.drip.simm20.margin.IRSensitivityAggregate irSensitivityAggregate()
	{
		return _irSensitivityAggregate;
	}

	/**
	 * Retrieve the Bucket's Sensitivity Margin Variance
	 * 
	 * @return The Bucket's Sensitivity Margin Variance
	 */

	public double sensitivityMarginVariance()
	{
		return _sensitivityMarginVariance;
	}

	/**
	 * Retrieve the Bucket's Cumulative Risk Factor Sensitivity Margin
	 * 
	 * @return The Bucket's Cumulative Risk Factor Sensitivity Margin
	 */

	public double cumulativeRiskFactorSensitivityMargin()
	{
		return _cumulativeRiskFactorSensitivityMargin;
	}

	/**
	 * Compute the Bounded Sensitivity Margin
	 * 
	 * @return The Bounded Sensitivity Margin
	 */

	public double boundedSensitivityMargin()
	{
		double sensitivityMargin = java.lang.Math.sqrt (_sensitivityMarginVariance);

		return java.lang.Math.max (
			java.lang.Math.min (
				_cumulativeRiskFactorSensitivityMargin,
				sensitivityMargin
			),
			-1. * sensitivityMargin
		);
	}
}
