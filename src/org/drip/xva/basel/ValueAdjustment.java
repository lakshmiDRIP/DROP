
package org.drip.xva.basel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * ValueAdjustment holds the Value and the Attribution Category at the Level of a Portfolio. The References
 *  are:
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955, eSSRN.
 *  
 *  - BCBS (2012): Consultative Document: Application of Own Credit Risk Adjustments to Derivatives, Basel
 *  	Committee on Banking Supervision.
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ValueAdjustment {
	private double _dblAmount = java.lang.Double.NaN;
	private org.drip.xva.basel.ValueCategory _vc = null;

	/**
	 * Construct the Collateralized Transaction Value Adjustment Instance
	 * 
	 * @param dblAmount Valuation Adjustment Amount
	 * 
	 * @return The Collateralized Transaction Value Adjustment Instance
	 */

	public static final ValueAdjustment Collateralized (
		final double dblAmount)
	{
		try {
			return new ValueAdjustment (dblAmount, org.drip.xva.basel.ValueCategory.CF1());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the UCVA Value Adjustment Instance
	 * 
	 * @param dblAmount Valuation Adjustment Amount
	 * 
	 * @return The UCVA Value Adjustment Instance
	 */

	public static final ValueAdjustment UCVA (
		final double dblAmount)
	{
		try {
			return new ValueAdjustment (dblAmount, org.drip.xva.basel.ValueCategory.CF2());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the FTDCVA Value Adjustment Instance
	 * 
	 * @param dblAmount Valuation Adjustment Amount
	 * 
	 * @return The FTDCVA Value Adjustment Instance
	 */

	public static final ValueAdjustment FTDCVA (
		final double dblAmount)
	{
		try {
			return new ValueAdjustment (dblAmount, org.drip.xva.basel.ValueCategory.CF2());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the DVA Value Adjustment Instance
	 * 
	 * @param dblAmount Valuation Adjustment Amount
	 * 
	 * @return The DVA Value Adjustment Instance
	 */

	public static final ValueAdjustment DVA (
		final double dblAmount)
	{
		try {
			return new ValueAdjustment (dblAmount, org.drip.xva.basel.ValueCategory.CF3());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the CVA Contra-Liability Value Adjustment Instance
	 * 
	 * @param dblAmount Valuation Adjustment Amount
	 * 
	 * @return The CVA Contra-Liability Value Adjustment Instance
	 */

	public static final ValueAdjustment CVACL (
		final double dblAmount)
	{
		try {
			return new ValueAdjustment (dblAmount, org.drip.xva.basel.ValueCategory.CF3());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the FVA Value Adjustment Instance
	 * 
	 * @param dblAmount Valuation Adjustment Amount
	 * 
	 * @return The FVA Value Adjustment Instance
	 */

	public static final ValueAdjustment FVA (
		final double dblAmount)
	{
		try {
			return new ValueAdjustment (dblAmount, org.drip.xva.basel.ValueCategory.CF4());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the FDA Value Adjustment Instance
	 * 
	 * @param dblAmount Valuation Adjustment Amount
	 * 
	 * @return The FDA Value Adjustment Instance
	 */

	public static final ValueAdjustment FDA (
		final double dblAmount)
	{
		try {
			return new ValueAdjustment (dblAmount, org.drip.xva.basel.ValueCategory.CF5());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the COLVA Value Adjustment Instance
	 * 
	 * @param dblAmount Valuation Adjustment Amount
	 * 
	 * @return The COLVA Value Adjustment Instance
	 */

	public static final ValueAdjustment COLVA (
		final double dblAmount)
	{
		try {
			return new ValueAdjustment (dblAmount, org.drip.xva.basel.ValueCategory.CF6());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the HYBRID Value Adjustment Instance
	 * 
	 * @param dblAmount Valuation Adjustment Amount
	 * 
	 * @return The HYBRID Value Adjustment Instance
	 */

	public static final ValueAdjustment HYBRID (
		final double dblAmount)
	{
		try {
			return new ValueAdjustment (dblAmount, org.drip.xva.basel.ValueCategory.HYBRID());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ValueAdjustment Constructor
	 * 
	 * @param dblAmount Valuation Adjustment Amount
	 * @param vc Valuation Adjustment Attribution Category
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ValueAdjustment (
		final double dblAmount,
		final org.drip.xva.basel.ValueCategory vc)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblAmount = dblAmount) || null == (_vc = vc))
			throw new java.lang.Exception ("ValueAdjustment Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Valuation Adjustment Amount
	 * 
	 * @return The Valuation Adjustment Amount
	 */

	public double amount()
	{
		return _dblAmount;
	}

	/**
	 * Retrieve the Valuation Adjustment Attribution Category
	 * 
	 * @return The Valuation Adjustment Attribution Category
	 */

	public org.drip.xva.basel.ValueCategory valueCategory()
	{
		return _vc;
	}
}
