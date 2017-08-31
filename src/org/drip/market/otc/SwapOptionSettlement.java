
package org.drip.market.otc;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * SwapOptionSettlement contains the details of the OTC Swap Option Settlements.
 *
 * @author Lakshmi Krishnamurthy
 */

public class SwapOptionSettlement {

	/**
	 * Swap Option Settlement Type - Cash Settled
	 */

	public static final int SETTLEMENT_TYPE_CASH_SETTLED = 1;

	/**
	 * Swap Option Settlement Type - Physical Delivery
	 */

	public static final int SETTLEMENT_TYPE_PHYSICAL_DELIVERY = 2;

	/**
	 * Swap Option Cash Settlement Quote Method - Internal Rate of Return
	 */

	public static final int SETTLEMENT_QUOTE_IRR = 1;

	/**
	 * Swap Option Cash Settlement Quote Method - Exact Curve
	 */

	public static final int SETTLEMENT_QUOTE_EXACT_CURVE = 2;

	private int _iSettlementType = -1;
	private int _iSettlementQuote = -1;

	/**
	 * SwapOptionSettlement Constructor
	 * 
	 * @param iSettlementType Settlement Type
	 * @param iSettlementQuote Settlement Quote
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SwapOptionSettlement (
		final int iSettlementType,
		final int iSettlementQuote)
		throws java.lang.Exception
	{
		if (SETTLEMENT_TYPE_CASH_SETTLED != (_iSettlementType = iSettlementType) &&
			SETTLEMENT_TYPE_PHYSICAL_DELIVERY != _iSettlementType)
			throw new java.lang.Exception ("SwapOptionSettlement ctr: Invalid Settlement Type");

		if (SETTLEMENT_TYPE_CASH_SETTLED == _iSettlementType && SETTLEMENT_QUOTE_IRR != (_iSettlementQuote =
			iSettlementQuote) && SETTLEMENT_QUOTE_EXACT_CURVE != _iSettlementQuote)
			throw new java.lang.Exception ("SwapOptionSettlement ctr: Invalid Settlement Quote");
	}

	/**
	 * Retrieve the Settlement Type
	 * 
	 * @return The Settlement Type
	 */

	public int settlementType()
	{
		return _iSettlementType;
	}

	/**
	 * Retrieve the Settlement Quote
	 * 
	 * @return The Settlement Quote
	 */

	public int settlementQuote()
	{
		return _iSettlementQuote;
	}

	@Override public java.lang.String toString()
	{
		if (SETTLEMENT_TYPE_PHYSICAL_DELIVERY == _iSettlementType) return "PHYSICAL DELIVERY";

		return "CASH SETTLED | " + (SETTLEMENT_QUOTE_IRR == _iSettlementQuote ? "INTERNAL RATE OF RETURN" :
			"EXACT CURVE");
	}
}
