
package org.drip.product.params;

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
 * QuoteConvention contains the Component Market Convention Parameters - the quote convention, the
 *  calculation type, the first settle date, and the redemption amount. It exports serialization into and
 *  de-serialization out of byte arrays.
 *
 * @author Lakshmi Krishnamurthy
 */

public class QuoteConvention implements org.drip.product.params.Validatable {
	private java.lang.String _strCalculationType = "";
	private double _dblRedemptionValue = java.lang.Double.NaN;
	private int _iFirstSettleDate = java.lang.Integer.MIN_VALUE;
	private org.drip.param.valuation.CashSettleParams _cashSettleParams = null;
	private org.drip.param.valuation.ValuationCustomizationParams _valuationCustomizationParams = null;

	/**
	 * Construct the QuoteConvention object from the valuation Customization Parameters, the calculation
	 *  type, the first settle date, and the redemption value.
	 * 
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * @param strCalculationType Calculation Type
	 * @param iFirstSettleDate First Settle Date
	 * @param dblRedemptionValue Redemption Value
	 * @param iSettleLag Settle Lag
	 * @param strSettleCalendar Settlement Calendar
	 * @param iSettleAdjustMode Is Settle date business adjusted
	 */

	public QuoteConvention (
		final org.drip.param.valuation.ValuationCustomizationParams valuationCustomizationParams,
		final java.lang.String strCalculationType,
		final int iFirstSettleDate,
		final double dblRedemptionValue,
		final int iSettleLag,
		final java.lang.String strSettleCalendar,
		final int iSettleAdjustMode)
	{
		_iFirstSettleDate = iFirstSettleDate;
		_dblRedemptionValue = dblRedemptionValue;
		_strCalculationType = strCalculationType;
		_valuationCustomizationParams = valuationCustomizationParams;
		
		_cashSettleParams = new org.drip.param.valuation.CashSettleParams (iSettleLag, strSettleCalendar,
			iSettleAdjustMode);
	}

	public int settleDate (
		final org.drip.param.valuation.ValuationParams valParams)
		throws java.lang.Exception
	{
		if (null == valParams)
			throw new java.lang.Exception ("QuoteConvention::settleDate => Invalid inputs");

		return _cashSettleParams.cashSettleDate (valParams.valueDate());
	}

	@Override public boolean validate()
	{
		return org.drip.quant.common.NumberUtil.IsValid (_iFirstSettleDate) &&
			org.drip.quant.common.NumberUtil.IsValid (_dblRedemptionValue);
	}

	/**
	 * Retrieve the Calculation Type
	 * 
	 * @return The Calculation Type
	 */

	public java.lang.String calculationType()
	{
		return _strCalculationType;
	}

	/**
	 * Retrieve the First Settle Date
	 * 
	 * @return The First Settle Date
	 */

	public int firstSettleDate()
	{
		return _iFirstSettleDate;
	}

	/**
	 * Retrieve the Redemption Value
	 * 
	 * @return The Redemption Value
	 */

	public double redemptionValue()
	{
		return _dblRedemptionValue;
	}

	/**
	 * Retrieve the Cash Settle Parameters
	 * 
	 * @return The Cash Settle Parameters
	 */

	public org.drip.param.valuation.CashSettleParams cashSettleParams()
	{
		return _cashSettleParams;
	}

	/**
	 * Retrieve the Valuation Customization Parameters
	 * 
	 * @return The Valuation Customization Parameters
	 */

	public org.drip.param.valuation.ValuationCustomizationParams valuationCustomizationParams()
	{
		return _valuationCustomizationParams;
	}
}
