
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
 * <i>BucketAggregateCR</i> holds the Single Bucket CR Sensitivity Margin, the Cumulative CR Bucket Risk
 * Factor Sensitivity Margin, as well as the Aggregate CR Risk Factor Maps. The References are:
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

public class BucketAggregateCR
{
	private double _sensitivityMarginVariance = java.lang.Double.NaN;
	private double _cumulativeSensitivityMargin = java.lang.Double.NaN;
	private org.drip.simm.margin.RiskFactorAggregateCR _riskFactorAggregate = null;
	private org.drip.simm.margin.SensitivityAggregateCR _sensitivityAggregate = null;

	/**
	 * BucketAggregateCR Constructor
	 * 
	 * @param riskFactorAggregate The CR Risk Factor Aggregate
	 * @param sensitivityAggregate THe CR Sensitivity Aggregate
	 * @param sensitivityMarginVariance The Bucket's Sensitivity Margin Variance
	 * @param cumulativeSensitivityMargin The Cumulative Risk Factor Sensitivity Margin
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BucketAggregateCR (
		final org.drip.simm.margin.RiskFactorAggregateCR riskFactorAggregate,
		final org.drip.simm.margin.SensitivityAggregateCR sensitivityAggregate,
		final double sensitivityMarginVariance,
		final double cumulativeSensitivityMargin)
		throws java.lang.Exception
	{
		if (null == (_riskFactorAggregate = riskFactorAggregate) ||
			null == (_sensitivityAggregate = sensitivityAggregate) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_sensitivityMarginVariance =
				sensitivityMarginVariance) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_cumulativeSensitivityMargin =
				cumulativeSensitivityMargin))
		{
			throw new java.lang.Exception ("BucketAggregateCR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the CR Bucket Sensitivity Margin Variance
	 * 
	 * @return The CR Bucket Sensitivity Margin Variance
	 */

	public double sensitivityMarginVariance()
	{
		return _sensitivityMarginVariance;
	}

	/**
	 * Retrieve the CR Bucket Cumulative Sensitivity Margin
	 * 
	 * @return The CR Bucket Cumulative Sensitivity Margin
	 */

	public double cumulativeSensitivityMargin()
	{
		return _cumulativeSensitivityMargin;
	}

	/**
	 * Retrieve the CR Risk Factor Aggregate
	 * 
	 * @return The CR Risk Factor Aggregate
	 */

	public org.drip.simm.margin.RiskFactorAggregateCR riskFactorAggregate()
	{
		return _riskFactorAggregate;
	}

	/**
	 * Retrieve the CR Sensitivity Aggregate
	 * 
	 * @return The CR Sensitivity Aggregate
	 */

	public org.drip.simm.margin.SensitivityAggregateCR sensitivityAggregate()
	{
		return _sensitivityAggregate;
	}

	/**
	 * Compute the ISDA SIMM Position Principal Component Co-variance
	 * 
	 * @return The ISDA SIMM Position Principal Component Co-variance
	 */

	public double positionPrincipalComponentCovarianceISDA()
	{
		double sensitivityMargin = java.lang.Math.sqrt (_sensitivityMarginVariance);

		return java.lang.Math.max (
			java.lang.Math.min (
				_cumulativeSensitivityMargin,
				sensitivityMargin
			),
			-1. * sensitivityMargin
		);
	}

	/**
	 * Compute the FRTB SBA-C Position Principal Component Co-variance
	 * 
	 * @return The FRTB SBA-C Position Principal Component Co-variance
	 */

	public double positionPrincipalComponentCovarianceFRTB()
	{
		return _cumulativeSensitivityMargin;
	}
}
