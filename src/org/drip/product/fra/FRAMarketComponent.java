
package org.drip.product.fra;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * FRAMarketComponent contains the implementation of the Standard Multi-Curve FRA product whose payoff is
 * 	dictated off of Market FRA Conventions.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FRAMarketComponent extends org.drip.product.fra.FRAStandardComponent {

	/**
	 * FRAMarketComponent constructor
	 * 
	 * @param strName Futures Component Name
	 * @param stream Futures Stream
	 * @param dblStrike Futures Strike
	 * @param csp Cash Settle Parameters Instance
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public FRAMarketComponent (
		final java.lang.String strName,
		final org.drip.product.rates.Stream stream,
		final double dblStrike,
		final org.drip.param.valuation.CashSettleParams csp)
		throws java.lang.Exception
	{
		super (strName, stream, dblStrike, csp);
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == valParams || null == csqs) return null;

		long lStart = System.nanoTime();

		int iValueDate = valParams.valueDate();

		int iEffectiveDate = effectiveDate().julian();

		if (iValueDate > iEffectiveDate) return null;

		org.drip.state.identifier.FundingLabel fundingLabel = org.drip.state.identifier.FundingLabel.Standard
			(payCurrency());

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqs.fundingState (fundingLabel);

		if (null == dcFunding) return null;

		org.drip.analytics.date.JulianDate dtMaturity = maturityDate();

		int iMaturityDate = dtMaturity.julian();

		org.drip.state.identifier.ForwardLabel forwardLabel = forwardLabel().get ("DERIVED");

		org.drip.state.forward.ForwardRateEstimator fc = csqs.forwardState (forwardLabel);

		if (null == fc || !forwardLabel.match (fc.index())) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapResult = super.value
			(valParams, pricerParams, csqs, vcp);

		if (null == mapResult || 0 == mapResult.size()) return null;

		try {
			double dblParStandardFRA = csqs.available (dtMaturity, forwardLabel) ? csqs.fixing (dtMaturity,
				forwardLabel) : fc.forward (iMaturityDate);

			double dblForwardDCF = org.drip.analytics.daycount.Convention.YearFraction (iMaturityDate, new
				org.drip.analytics.date.JulianDate (iMaturityDate).addTenor (forwardLabel.tenor()).julian(),
					stream().couponDC(), false, null, stream().calendar());

			double dblParDCForward = dcFunding.libor (iEffectiveDate, iMaturityDate);

			double dblShiftedLogNormalScaler = dblForwardDCF * dblParStandardFRA;
			dblShiftedLogNormalScaler = dblShiftedLogNormalScaler / (1. + dblShiftedLogNormalScaler);

			double dblForwardPrice = dblForwardDCF * (dblParStandardFRA - strike()) / (1. + dblForwardDCF *
				dblParStandardFRA);

			double dblShiftedLogNormalConvexityAdjustmentExponent =
				org.drip.analytics.support.OptionHelper.IntegratedFRACrossVolConvexityExponent
					(csqs.forwardVolatility (forwardLabel), csqs.fundingVolatility (fundingLabel),
						csqs.forwardFundingCorrelation (forwardLabel, fundingLabel),
							dblShiftedLogNormalScaler, dblShiftedLogNormalScaler, iValueDate,
								iEffectiveDate);

			double dblShiftedLogNormalParMarketFRA = ((dblForwardDCF * dblParStandardFRA + 1.) *
				java.lang.Math.exp (dblShiftedLogNormalConvexityAdjustmentExponent) - 1.) / dblForwardDCF;

			mapResult.put ("discountcurveparforward", dblParDCForward);

			mapResult.put ("forwardprice", dblForwardPrice);

			mapResult.put ("parstandardfra", dblParStandardFRA);

			mapResult.put ("parstandardfradc", dblParDCForward);

			mapResult.put ("shiftedlognormalconvexityadjustment",
				dblShiftedLogNormalConvexityAdjustmentExponent);

			mapResult.put ("shiftedlognormalconvexitycorrection", dblShiftedLogNormalParMarketFRA -
				dblParStandardFRA);

			mapResult.put ("shiftedlognormalparmarketfra", dblShiftedLogNormalParMarketFRA);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		mapResult.put ("calctime", (System.nanoTime() - lStart) * 1.e-09);

		return mapResult;
	}
}
