
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
 * <i>RiskMeasureAggregateIR</i> holds the Bucket Aggregate and the Computed SIMM Margin for the IR Risk
 * Measure. The References are:
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
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/simm/margin">Margin</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RiskMeasureAggregateIR
{
	private double _coreSBAVariance = java.lang.Double.NaN;
	private double _residualSBAVariance = java.lang.Double.NaN;
	private java.util.Map<java.lang.String, org.drip.simm.margin.BucketAggregateIR> _bucketAggregateMap =
		null;

	/**
	 * RiskMeasureAggregateIR Constructor
	 * 
	 * @param bucketAggregateMap IR Bucket Aggregate Map
	 * @param coreSBAVariance The Core SBA Variance
	 * @param residualSBAVariance The Residual SBA Variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskMeasureAggregateIR (
		final java.util.Map<java.lang.String, org.drip.simm.margin.BucketAggregateIR> bucketAggregateMap,
		final double coreSBAVariance,
		final double residualSBAVariance)
		throws java.lang.Exception
	{
		if (null == (_bucketAggregateMap = bucketAggregateMap) ||
			!org.drip.quant.common.NumberUtil.IsValid (_coreSBAVariance = coreSBAVariance) ||
			!org.drip.quant.common.NumberUtil.IsValid (_residualSBAVariance = residualSBAVariance))
		{
			throw new java.lang.Exception ("RiskMeasureAggregateIR Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Core SBA Variance
	 * 
	 * @return The Core SBA Variance
	 */

	public double coreSBAVariance()
	{
		return _coreSBAVariance;
	}

	/**
	 * Retrieve the Residual SBA Variance
	 * 
	 * @return The Residual SBA Variance
	 */

	public double residualSBAVariance()
	{
		return _residualSBAVariance;
	}

	/**
	 * Retrieve the Aggregate Bucket Map
	 * 
	 * @return The Aggregate Bucket Map
	 */

	public java.util.Map<java.lang.String, org.drip.simm.margin.BucketAggregateIR> bucketAggregateMap()
	{
		return _bucketAggregateMap;
	}

	/**
	 * Retrieve the Total SBA Margin
	 * 
	 * @return The Total SBA Margin
	 */

	public double sba()
	{
		return java.lang.Math.sqrt (_coreSBAVariance + _residualSBAVariance);
	}
}
