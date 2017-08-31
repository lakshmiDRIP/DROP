
package org.drip.product.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * CreditComponent is the base abstract class on top of which all credit components are implemented. Its
 * 	methods expose Credit Valuation Parameters, product specific recovery, and coupon/loss cash flows.
 *  
 * @author Lakshmi Krishnamurthy
 */

public abstract class CreditComponent extends org.drip.product.definition.CalibratableComponent {

	/**
	 * Generate the loss flow for the credit component based on the pricer parameters
	 * 
	 * @param valParams ValuationParams
	 * @param pricerParams PricerParams
	 * @param csqc ComponentMarketParams
	 * 
	 * @return List of ProductLossPeriodCurveMeasures
	 */

	public abstract java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics> lossFlow (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc);

	/**
	 * Get the recovery of the credit component for the given date
	 * 
	 * @param iDate JulianDate
	 * @param cc Credit Curve
	 * 
	 * @return Recovery
	 * 
	 * @throws java.lang.Exception Thrown if recovery cannot be calculated
	 */

	public abstract double recovery (
		final int iDate,
		final org.drip.state.credit.CreditCurve cc)
		throws java.lang.Exception;

	/**
	 * Get the time-weighted recovery of the credit component between the given dates
	 * 
	 * @param iDate1 JulianDate #1
	 * @param iDate2 JulianDate #2
	 * @param cc Credit Curve
	 * 
	 * @return Recovery
	 * 
	 * @throws java.lang.Exception Thrown if recovery cannot be calculated
	 */

	public abstract double recovery (
		final int iDate1,
		final int iDate2,
		final org.drip.state.credit.CreditCurve cc)
		throws java.lang.Exception;

	/**
	 * Get the credit component's Credit Valuation Parameters
	 * 
	 * @return CompCRValParams
	 */

	public abstract org.drip.product.params.CreditSetting creditValuationParams();

	/**
	 * Generate the loss flow for the credit component based on the pricer parameters
	 * 
	 * @param dtSpot The Spot Date
	 * @param csqc The Component Market Parameters
	 * 
	 * @return List of ProductLossPeriodCurveMeasures
	 */

	public java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics> lossFlow (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
	{
		return null == dtSpot ? null : lossFlow (org.drip.param.valuation.ValuationParams.Spot
			(dtSpot.julian()), org.drip.param.pricer.CreditPricerParams.Standard(), csqc);
	}
}
