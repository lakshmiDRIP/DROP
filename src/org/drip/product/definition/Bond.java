
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
 * Bond abstract class implements the pricing, the valuation, and the RV analytics functionality for the bond
 *  product.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class Bond extends CreditComponent {

	/**
	 * Retrieve the work-out information from price
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Bond Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price
	 * 
	 * @return The Optimal Work-out Information
	 */

	public abstract org.drip.param.valuation.WorkoutInfo exerciseYieldFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice);

	/**
	 * Retrieve the array of double for the bond's secondary treasury spreads from the Valuation
	 * 	Parameters and the component market parameters
	 * 
	 * @param valParams ValuationParams
	 * @param csqs ComponentMarketParams
	 * 
	 * @return Array of double for the bond's secondary treasury spreads
	 */

	public abstract double[] secTreasurySpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs);

	/**
	 * Retrieve the effective treasury benchmark yield from the valuation, the component market parameters,
	 * 	and the market price
	 * 
	 * @param valParams ValuationParams
	 * @param csqs ComponentMarketParams
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Market price
	 * 
	 * @return Effective treasury benchmark yield
	 * 
	 * @throws java.lang.Exception Thrown if the effective benchmark cannot be calculated
	 */

	public abstract double effectiveTreasuryBenchmarkYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Get the ISIN
	 * 
	 * @return ISIN string
	 */

	public abstract java.lang.String isin();

	/**
	 * Get the CUSIP
	 * 
	 * @return CUSIP string
	 */

	public abstract java.lang.String cusip();

	/**
	 * Get the bond's loss flow from price
	 * 
	 * @param valParams ValuationParams
	 * @param pricerParams PricerParams
	 * @param csqs ComponentMarketParams
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Input price
	 * 
	 * @return List of LossQuadratureMetrics
	 */

	public abstract java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics> lossFlowFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice);

	/**
	 * Return whether the bond is a floater
	 * 
	 * @return True if the bond is a floater
	 */

	public abstract boolean isFloater();

	/**
	 * Return the rate index of the bond
	 * 
	 * @return Rate index
	 */

	public abstract java.lang.String rateIndex();

	/**
	 * Return the current bond coupon
	 * 
	 * @return Current coupon
	 */

	public abstract double currentCoupon();

	/**
	 * Return the floating spread of the bond
	 * 
	 * @return Floating spread
	 */

	public abstract double floatSpread();

	/**
	 * Return the bond ticker
	 * 
	 * @return Bond Ticker
	 */

	public abstract java.lang.String ticker();

	/**
	 * Indicate if the bond is callable
	 * 
	 * @return True - callable
	 */

	public abstract boolean callable();

	/**
	 * Indicate if the bond is putable
	 * 
	 * @return True - putable
	 */

	public abstract boolean putable();

	/**
	 * Indicate if the bond is sinkable
	 * 
	 * @return True - sinkable
	 */

	public abstract boolean sinkable();

	/**
	 * Indicate if the bond has variable coupon
	 * 
	 * @return True - has variable coupon
	 */

	public abstract boolean variableCoupon();

	/**
	 * Indicate if the bond has been exercised
	 * 
	 * @return True - Has been exercised
	 */

	public abstract boolean exercised();

	/**
	 * Indicate if the bond has defaulted
	 * 
	 * @return True - Bond has defaulted
	 */

	public abstract boolean defaulted();

	/**
	 * Indicate if the bond is perpetual
	 * 
	 * @return True - Bond is Perpetual
	 */

	public abstract boolean perpetual();

	/**
	 * Calculate if the bond is tradeable on the given date
	 * 
	 * @param valParams Valuation Parameters
	 * 
	 * @return True indicates the bond is tradeable
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public abstract boolean tradeable (
		final org.drip.param.valuation.ValuationParams valParams)
		throws java.lang.Exception;

	/**
	 * Return the bond's embedded call schedule
	 * 
	 * @return EOS Call
	 */

	public abstract org.drip.product.params.EmbeddedOptionSchedule callSchedule();

	/**
	 * Return the bond's embedded put schedule
	 * 
	 * @return EOS Put
	 */

	public abstract org.drip.product.params.EmbeddedOptionSchedule putSchedule();

	/**
	 * Return the bond's coupon type
	 * 
	 * @return Bond's coupon Type
	 */

	public abstract java.lang.String couponType();

	/**
	 * Return the bond's coupon day count
	 * 
	 * @return Coupon day count string
	 */

	public abstract java.lang.String couponDC();

	/**
	 * Return the bond's accrual day count
	 * 
	 * @return Accrual day count string
	 */

	public abstract java.lang.String accrualDC();

	/**
	 * Return the bond's maturity type
	 * 
	 * @return Bond's maturity type
	 */

	public abstract java.lang.String maturityType();

	/**
	 * Return the bond's coupon frequency
	 * 
	 * @return Bond's coupon frequency
	 */

	public abstract int freq();

	/**
	 * Return the bond's final maturity
	 * 
	 * @return Bond's final maturity
	 */

	public abstract org.drip.analytics.date.JulianDate finalMaturity();

	/**
	 * Return the bond's calculation type
	 * 
	 * @return Bond's calculation type
	 */

	public abstract java.lang.String calculationType();

	/**
	 * Return the bond's redemption value
	 * 
	 * @return Bond's redemption value
	 */

	public abstract double redemptionValue();

	/**
	 * Return the bond's coupon currency
	 * 
	 * @return Bond's coupon currency
	 */

	public abstract java.lang.String currency();

	/**
	 * Return the bond's redemption currency
	 * 
	 * @return Bond's redemption currency
	 */

	public abstract java.lang.String redemptionCurrency();

	/**
	 * Indicate whether the given date is in the first coupon period
	 * 
	 * @param iDate Valuation Date
	 * 
	 * @return True - The given date is in the first coupon period
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public abstract boolean inFirstCouponPeriod (
		final int iDate)
		throws java.lang.Exception;

	/**
	 * Indicate whether the given date is in the final coupon period
	 * 
	 * @param iDate Valuation Date
	 * 
	 * @return True - The given date is in the last coupon period
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public abstract boolean inLastCouponPeriod (
		final int iDate)
		throws java.lang.Exception;

	/**
	 * Return the bond's floating coupon convention
	 * 
	 * @return Bond's floating coupon convention
	 */

	public abstract java.lang.String floatCouponConvention();

	/**
	 * Get the bond's reset date for the period identified by the valuation date
	 * 
	 * @param iValueDate Valuation Date
	 * 
	 * @return Reset JulianDate
	 */

	public abstract org.drip.analytics.date.JulianDate periodFixingDate (
		final int iValueDate);

	/**
	 * Return the coupon date for the period prior to the specified date
	 * 
	 * @param dt Valuation Date
	 * 
	 * @return Previous Coupon Date
	 */

	public abstract org.drip.analytics.date.JulianDate previousCouponDate (
		final org.drip.analytics.date.JulianDate dt);

	/**
	 * Return the coupon rate for the period prior to the specified date
	 * 
	 * @param dt Valuation Date
	 * @param csqs Component Market Params
	 * 
	 * @return Previous Coupon Rate
	 * 
	 * @throws java.lang.Exception Thrown if the previous coupon rate cannot be calculated
	 */

	public abstract double previousCouponRate (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
		throws java.lang.Exception;

	/**
	 * Return the coupon date for the period containing the specified date
	 * 
	 * @param dt Valuation Date
	 * 
	 * @return Current Coupon Date
	 */

	public abstract org.drip.analytics.date.JulianDate currentCouponDate (
		final org.drip.analytics.date.JulianDate dt);

	/**
	 * Return the coupon date for the period subsequent to the specified date
	 * 
	 * @param dt Valuation Date
	 * 
	 * @return Next Coupon Date
	 */

	public abstract org.drip.analytics.date.JulianDate nextCouponDate (
		final org.drip.analytics.date.JulianDate dt);

	/**
	 * Return the next exercise info of the given exercise type (call/put) subsequent to the specified date
	 * 
	 * @param dt Valuation Date
	 * @param bGetPut TRUE - Gets the next put date
	 * 
	 * @return Next Exercise Information
	 */

	public abstract org.drip.analytics.output.ExerciseInfo nextValidExerciseDateOfType (
		final org.drip.analytics.date.JulianDate dt,
		final boolean bGetPut);

	/**
	 * Return the next exercise info subsequent to the specified date
	 * 
	 * @param dt Valuation Date
	 * 
	 * @return Next Exercise Info
	 */

	public abstract org.drip.analytics.output.ExerciseInfo nextValidExerciseInfo (
		final org.drip.analytics.date.JulianDate dt);

	/**
	 * Return the coupon rate for the period corresponding to the specified date
	 * 
	 * @param dt Valuation Date
	 * @param csqs Component Market Params
	 * 
	 * @return Next Coupon Rate
	 * 
	 * @throws java.lang.Exception Thrown if the current period coupon rate cannot be calculated
	 */

	public abstract double currentCouponRate (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
		throws java.lang.Exception;

	/**
	 * Return the coupon rate for the period subsequent to the specified date
	 * 
	 * @param dt Valuation Date
	 * @param csqs Component Market Params
	 * 
	 * @return Next Coupon Rate
	 * 
	 * @throws java.lang.Exception Thrown if the subsequent coupon rate cannot be calculated
	 */

	public abstract double nextCouponRate (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
		throws java.lang.Exception;

	/**
	 * Calculate the bond's accrued for the period identified by the valuation date
	 * 
	 * @param iDate Valuation Date
	 * @param csqs Bond market parameters
	 * 
	 * @return The coupon accrued in the current period
	 * 
	 * @throws java.lang.Exception Thrown if accrual cannot be calculated
	 */

	public abstract double accrued (
		final int iDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
		throws java.lang.Exception;

	/**
	 * Calculate the Bond's Weighted Average Maturity Date from the Valuation Date
	 * 
	 * @param valParams ValuationParams
	 * @param csqc ComponentMarketParams
	 * @param iWorkoutDate Work-out date
	 * @param dblWorkoutFactor Double Work-out factor
	 * 
	 * @return The Bond's Weighted Average Maturity Date from the Valuation Date
	 * 
	 * @throws java.lang.Exception Thrown if Bond's Weighted Average Maturity Date cannot be calculated
	 */

	public abstract int weightedAverageMaturityDate (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final int iWorkoutDate,
		final double dblWorkoutFactor)
		throws java.lang.Exception;

	/**
	 * Calculate the Bond's Weighted Average Maturity Date To Maturity from the Valuation Date
	 * 
	 * @param valParams ValuationParams
	 * @param csqc ComponentMarketParams
	 * 
	 * @return The Bond's Weighted Average Life from the Valuation Date
	 * 
	 * @throws java.lang.Exception Thrown if Bond's Weighted Average Maturity Date To Maturity cannot be
	 *  calculated
	 */

	public abstract int weightedAverageMaturityDate (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception;

	/**
	 * Calculate the Bond's Weighted Average Life from the Valuation Date
	 * 
	 * @param valParams ValuationParams
	 * @param csqc ComponentMarketParams
	 * @param iWorkoutDate Work-out date
	 * @param dblWorkoutFactor Double Work-out factor
	 * 
	 * @return The Bond's Weighted Average Life from the Valuation Date
	 * 
	 * @throws java.lang.Exception Thrown if Bond's Weighted Average Life cannot be calculated
	 */

	public abstract double weightedAverageLife (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final int iWorkoutDate,
		final double dblWorkoutFactor)
		throws java.lang.Exception;

	/**
	 * Calculate the Bond's Weighted Average Life To Maturity from the Valuation Date
	 * 
	 * @param valParams ValuationParams
	 * @param csqc ComponentMarketParams
	 * 
	 * @return The Bond's Weighted Average Life from the Valuation Date
	 * 
	 * @throws java.lang.Exception Thrown if Bond's Weighted Average Life To Maturity cannot be calculated
	 */

	public abstract double weightedAverageLife (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception;

	/**
	 * Calculate the Bond's Principal Only Weighted Average Life from the Valuation Date
	 * 
	 * @param valParams ValuationParams
	 * @param csqc ComponentMarketParams
	 * @param iWorkoutDate Work-out date
	 * @param dblWorkoutFactor Double Work-out factor
	 * 
	 * @return The Bond's Principal Only Weighted Average Life from the Valuation Date
	 * 
	 * @throws java.lang.Exception Thrown if Bond's Weighted Average Life cannot be calculated
	 */

	public abstract double weightedAverageLifePrincipalOnly (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final int iWorkoutDate,
		final double dblWorkoutFactor)
		throws java.lang.Exception;

	/**
	 * Calculate the Bond's Principal Only Weighted Average Life To Maturity from the Valuation Date
	 * 
	 * @param valParams ValuationParams
	 * @param csqc ComponentMarketParams
	 * 
	 * @return The Bond's Weighted Average Life from the Valuation Date
	 * 
	 * @throws java.lang.Exception Thrown if Bond's Principal Only Weighted Average Life To Maturity
	 *  cannot be calculated
	 */

	public abstract double weightedAverageLifePrincipalOnly (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception;

	/**
	 * Calculate the Bond's Coupon Only Weighted Average Life from the Valuation Date
	 * 
	 * @param valParams ValuationParams
	 * @param csqc ComponentMarketParams
	 * @param iWorkoutDate Work-out date
	 * @param dblWorkoutFactor Double Work-out factor
	 * 
	 * @return The Bond's Coupon Only Weighted Average Life from the Valuation Date
	 * 
	 * @throws java.lang.Exception Thrown if Bond's Weighted Average Life cannot be calculated
	 */

	public abstract double weightedAverageLifeCouponOnly (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final int iWorkoutDate,
		final double dblWorkoutFactor)
		throws java.lang.Exception;

	/**
	 * Calculate the Bond's Coupon Only Weighted Average Life To Maturity from the Valuation Date
	 * 
	 * @param valParams ValuationParams
	 * @param csqc ComponentMarketParams
	 * 
	 * @return The Bond's Coupon Only Weighted Average Life from the Valuation Date
	 * 
	 * @throws java.lang.Exception Thrown if Bond's Coupon Only Weighted Average Life To Maturity cannot be
	 *  calculated
	 */

	public abstract double weightedAverageLifeCouponOnly (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception;

	/**
	 * Calculate the Bond's Weighted Average Life of Losses Only from the Valuation Date
	 * 
	 * @param valParams ValuationParams
	 * @param csqc ComponentMarketParams
	 * @param iWorkoutDate Work-out date
	 * @param dblWorkoutFactor Double Work-out factor
	 * 
	 * @return The Bond's Weighted Average Life from the Valuation Date
	 * 
	 * @throws java.lang.Exception Thrown if Bond's Weighted Average Life of Losses Only cannot be calculated
	 */

	public abstract double weightedAverageLifeLossOnly (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final int iWorkoutDate,
		final double dblWorkoutFactor)
		throws java.lang.Exception;

	/**
	 * Calculate the Bond's Weighted Average Life of Losses Only To Maturity from the Valuation Date
	 * 
	 * @param valParams ValuationParams
	 * @param csqc ComponentMarketParams
	 * 
	 * @return The Bond's Weighted Average Life from the Valuation Date
	 * 
	 * @throws java.lang.Exception Thrown if Bond's Weighted Average Life of Losses Only To Maturity cannot
	 *  be calculated
	 */

	public abstract double weightedAverageLifeLossOnly (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception;

	/**
	 * Calculate the bond's non-credit risky theoretical price from the Bumped Zero Curve
	 * 
	 * @param valParams ValuationParams
	 * @param csqs ComponentMarketParams
	 * @param vcp Valuation Customization Parameters
	 * @param iZeroCurveBaseDC The Discount Curve to derive the zero curve off of
	 * @param iWorkoutDate Work-out date
	 * @param dblWorkoutFactor Double Work-out factor
	 * @param dblZCBump Bump to be applied to the zero curve
	 * 
	 * @return Bond's non-credit risky theoretical price
	 * 
	 * @throws java.lang.Exception Thrown if the price cannot be calculated
	 */

	public abstract double priceFromZeroCurve (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iZeroCurveBaseDC,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZCBump)
		throws java.lang.Exception;

	/**
	 * Calculate the bond's non-credit risky theoretical price from the Bumped Funding curve
	 * 
	 * @param valParams ValuationParams
	 * @param csqs ComponentMarketParams
	 * @param iWorkoutDate Work-out date
	 * @param dblWorkoutFactor Double Work-out factor
	 * @param dblDCBump Bump to be applied to the DC
	 * 
	 * @return Bond's non-credit risky theoretical price from the Bumped Funding curve
	 * 
	 * @throws java.lang.Exception Thrown if the price cannot be calculated
	 */

	public abstract double priceFromFundingCurve (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDCBump)
		throws java.lang.Exception;

	/**
	 * Calculate the bond's non-credit risky theoretical price from the Bumped Funding curve
	 * 
	 * @param valParams ValuationParams
	 * @param csqs ComponentMarketParams
	 * @param iWorkoutDate Work-out date
	 * @param dblWorkoutFactor Double Work-out factor
	 * @param dblDCBump Bump to be applied to the DC
	 * 
	 * @return Bond's non-credit risky theoretical price from the Bumped Treasury curve
	 * 
	 * @throws java.lang.Exception Thrown if the price cannot be calculated
	 */

	public abstract double priceFromTreasuryCurve (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDCBump)
		throws java.lang.Exception;

	/**
	 * Calculate the bond's credit risky theoretical price from the bumped credit curve
	 * 
	 * @param valParams ValuationParams
	 * @param csqs ComponentMarketParams
	 * @param iWorkoutDate Work-out date
	 * @param dblWorkoutFactor Double Work-out factor
	 * @param dblCreditBasis Bump to be applied to the credit curve
	 * @param bFlat Is the CDS Curve flat (for PECS)
	 * 
	 * @return Bond's credit risky theoretical price
	 * 
	 * @throws java.lang.Exception Thrown if the bond's credit risky theoretical price cannot be calculated
	 */

	public abstract double priceFromCreditCurve (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis,
		final boolean bFlat)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return ASW from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return ASW from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return ASW from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return ASW from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the ASW cannot be calculated
	 */

	public abstract double aswFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return ASW from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return ASW from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return ASW from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the ASW cannot be calculated
	 */

	public abstract double aswFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return ASW from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return ASW from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return ASW from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the ASW cannot be calculated
	 */

	public abstract double aswFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return ASW from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return ASW from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return ASW from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the ASW cannot be calculated
	 */

	public abstract double aswFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return ASW from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return ASW from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return ASW from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the ASW cannot be calculated
	 */

	public abstract double aswFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return ASW from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Optimal Exercise
	 * 
	 * @return ASW from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return ASW from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the ASW cannot be calculated
	 */

	public abstract double aswFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return ASW from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Optimal Exercise
	 * 
	 * @return ASW from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return ASW from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the ASW cannot be calculated
	 */

	public abstract double aswFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return ASW from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return ASW from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return ASW from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the ASW cannot be calculated
	 */

	public abstract double aswFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return ASW from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return ASW from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return ASW from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return ASW from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return ASW from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return ASW from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the ASW cannot be calculated
	 */

	public abstract double aswFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return ASW from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return ASW from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return ASW from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the ASW cannot be calculated
	 */

	public abstract double aswFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return ASW from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return ASW from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return ASW from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the ASW cannot be calculated
	 */

	public abstract double aswFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return ASW from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return ASW from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return ASW from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the ASW cannot be calculated
	 */

	public abstract double aswFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return ASW from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate ASW from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return ASW from Z Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if ASW cannot be calculated
	 */

	public abstract double aswFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return Bond Basis from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return Bond Basis from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return Bond Basis from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return Bond Basis from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return Bond Basis from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return Bond Basis from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return Bond Basis from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return Bond Basis from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return Bond Basis from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return Bond Basis from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return Bond Basis from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return Bond Basis from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return Bond Basis from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return Bond Basis from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return Bond Basis from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return Bond Basis from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return Bond Basis from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Optimal Exercise
	 * 
	 * @return Bond Basis from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return Bond Basis from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return Bond Basis from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Optimal Exercise
	 * 
	 * @return Bond Basis from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return Bond Basis from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return Bond Basis from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return Bond Basis from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return Bond Basis from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return Bond Basis from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return Bond Basis from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return Bond Basis from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return Bond Basis from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return Bond Basis from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return Bond Basis from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return Bond Basis from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return Bond Basis from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return Bond Basis from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return Bond Basis from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return Bond Basis from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return Bond Basis from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return Bond Basis from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return Bond Basis from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return Bond Basis from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return Bond Basis from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Bond Basis from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return Bond Basis from Z Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Bond Basis cannot be calculated
	 */

	public abstract double bondBasisFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return Convexity from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Convexity cannot be calculated
	 */

	public abstract double convexityFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return Convexity from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return Convexity from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return Convexity from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Convexity cannot be calculated
	 */

	public abstract double convexityFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return Convexity from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return Convexity from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return Convexity from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Convexity cannot be calculated
	 */

	public abstract double convexityFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return Convexity from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return Convexity from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return Convexity from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Convexity cannot be calculated
	 */

	public abstract double convexityFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return Convexity from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return Convexity from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return Convexity from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Convexity cannot be calculated
	 */

	public abstract double convexityFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return Convexity from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return Convexity from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return Convexity from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Convexity cannot be calculated
	 */

	public abstract double convexityFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return Convexity from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return Convexity from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return Convexity from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Convexity cannot be calculated
	 */

	public abstract double convexityFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return Convexity from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Optimal Exercise
	 * 
	 * @return Convexity from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return Convexity from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Convexity cannot be calculated
	 */

	public abstract double convexityFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return Convexity from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Optimal Exercise
	 * 
	 * @return Convexity from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return Convexity from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Convexity cannot be calculated
	 */

	public abstract double convexityFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return Convexity from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return Convexity from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return Convexity from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Convexity cannot be calculated
	 */

	public abstract double convexityFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return Convexity from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return Convexity from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return Convexity from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Convexity cannot be calculated
	 */

	public abstract double convexityFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return Convexity from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return Convexity from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return Convexity from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Convexity cannot be calculated
	 */

	public abstract double convexityFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return Convexity from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return Convexity from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return Convexity from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Convexity cannot be calculated
	 */

	public abstract double convexityFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return Convexity from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return Convexity from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return Convexity from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Convexity cannot be calculated
	 */

	public abstract double convexityFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return Convexity from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return Convexity from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return Convexity from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Convexity cannot be calculated
	 */

	public abstract double convexityFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return Convexity from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Convexity from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return Convexity from Z to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Convexity cannot be calculated
	 */

	public abstract double convexityFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return Credit Basis from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return Credit Basis from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return Credit Basis from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return Credit Basis from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return Credit Basis from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return Credit Basis from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return Credit Basis from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return Credit Basis from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return Credit Basis from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double creditBasisFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return Credit Basis from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return Credit Basis from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return Credit Basis from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return Credit Basis from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return Credit Basis from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return Credit Basis from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return Credit Basis from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return Credit Basis from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Optimal Exercise
	 * 
	 * @return Credit Basis from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return Credit Basis from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return Credit Basis from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Optimal Exercise
	 * 
	 * @return Credit Basis from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return Credit Basis from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return Credit Basis from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return Credit Basis from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return Credit Basis from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return Credit Basis from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return Credit Basis from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return Credit Basis from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return Credit Basis from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return Credit Basis from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return Credit Basis from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return Credit Basis from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return Credit Basis from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return Credit Basis from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return Credit Basis from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return Credit Basis from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return Credit Basis from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return Credit Basis from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return Credit Basis from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return Credit Basis from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return Credit Basis from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Credit Basis from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return Credit Basis from Z Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Credit Basis cannot be calculated
	 */

	public abstract double creditBasisFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return Discount Margin from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return Discount Margin from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return Discount Margin from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return Discount Margin from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return Discount Margin from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return Discount Margin from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return Discount Margin from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return Discount Margin from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return Discount Margin from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return Discount Margin from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return Discount Margin from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return Discount Margin from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return Discount Margin from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return Discount Margin from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return Discount Margin from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return Discount Margin from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return Discount Margin from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Optimal Exercise
	 * 
	 * @return Discount Margin from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return Discount Margin from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return Discount Margin from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Optimal Exercise
	 * 
	 * @return Discount Margin from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return Discount Margin from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return Discount Margin from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return Discount Margin from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return Discount Margin from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return Discount Margin from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return Discount Margin from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return Discount Margin from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return Discount Margin from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return Discount Margin from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return Discount Margin from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return Discount Margin from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return Discount Margin from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return Discount Margin from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return Discount Margin from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return Discount Margin from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return Discount Margin from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return Discount Margin from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return Discount Margin from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return Discount Margin from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return Discount Margin from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Discount Margin from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return Discount Margin from Z Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Discount Margin cannot be calculated
	 */

	public abstract double discountMarginFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return Duration from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Duration cannot be calculated
	 */

	public abstract double durationFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return Duration from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return Duration from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return Duration from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return Duration from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return Duration from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return Duration from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return Duration from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return Duration from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return Duration from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return Duration from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return Duration from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return Duration from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return Duration from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return Duration from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return Duration from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return Duration from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return Duration from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return Duration from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return Duration from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Optimal Exercise
	 * 
	 * @return Duration from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return Duration from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return Duration from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Optimal Exercise
	 * 
	 * @return Duration from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return Duration from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return Duration from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return Duration from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return Duration from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return Duration from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return Duration from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return Duration from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return Duration from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return Duration from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return Duration from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return Duration from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return Duration from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return Duration from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return Duration from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return Duration from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return Duration from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return Duration from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return Duration from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return Duration from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return Duration from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Duration from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return Duration from Z Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Duration cannot be calculated
	 */

	public abstract double durationFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return E Spread from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the E Spread cannot be calculated
	 */

	public abstract double eSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return E Spread from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double eSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return E Spread from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return E Spread from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Z Spread cannot be calculated
	 */

	public abstract double eSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return E Spread from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return E Spread from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return E Spread from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the E Spread cannot be calculated
	 */

	public abstract double eSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return E Spread from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return E Spread from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return E Spread from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the E Spread cannot be calculated
	 */

	public abstract double eSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return E Spread from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return E Spread from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return E Spread from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the E Spread cannot be calculated
	 */

	public abstract double eSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return E Spread from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return E Spread from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return E Spread from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the E Spread cannot be calculated
	 */

	public abstract double eSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return E Spread from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread ISpread to Optimal Exercise
	 * 
	 * @return E Spread from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return E Spread from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the E Spread cannot be calculated
	 */

	public abstract double eSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return E Spread from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread JSpread to Optimal Exercise
	 * 
	 * @return E Spread from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return E Spread from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the E Spread cannot be calculated
	 */

	public abstract double eSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return E Spread from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return E Spread from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return E Spread from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the E Spread cannot be calculated
	 */

	public abstract double eSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return E Spread from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return E Spread from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return E Spread from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the E Spread cannot be calculated
	 */

	public abstract double eSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return E Spread from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return E Spread from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return E Spread from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the E Spread cannot be calculated
	 */

	public abstract double eSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return E Spread from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return E Spread from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return E Spread from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the E Spread cannot be calculated
	 */

	public abstract double eSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return E Spread from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return E Spread from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return E Spread from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the E Spread cannot be calculated
	 */

	public abstract double eSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return E Spread from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate E Spread from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return E Spread from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if E Spread cannot be calculated
	 */

	public abstract double eSpreadFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return G Spread from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the G Spread cannot be calculated
	 */

	public abstract double gSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return G Spread from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return G Spread from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return G Spread from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the G Spread cannot be calculated
	 */

	public abstract double gSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return G Spread from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return G Spread from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return G Spread from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the G Spread cannot be calculated
	 */

	public abstract double gSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return G Spread from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return G Spread from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return G Spread from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the G Spread cannot be calculated
	 */

	public abstract double gSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return G Spread from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return G Spread from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return G Spread from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the G Spread cannot be calculated
	 */

	public abstract double gSpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return G Spread from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return G Spread from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return G Spread from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the G Spread cannot be calculated
	 */

	public abstract double gSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return G Spread from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Optimal Exercise
	 * 
	 * @return G Spread from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return G Spread from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the G Spread cannot be calculated
	 */

	public abstract double gSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return G Spread from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Optimal Exercise
	 * 
	 * @return G Spread from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return G Spread from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the G Spread cannot be calculated
	 */

	public abstract double gSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return G Spread from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return G Spread from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return G Spread from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the G Spread cannot be calculated
	 */

	public abstract double gSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return G Spread from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return G Spread from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return G Spread from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the G Spread cannot be calculated
	 */

	public abstract double gSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return G Spread from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return G Spread from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return G Spread from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the G Spread cannot be calculated
	 */

	public abstract double gSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return G Spread from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return G Spread from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return G Spread from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the G Spread cannot be calculated
	 */

	public abstract double gSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return G Spread from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return G Spread from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return G Spread from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the G Spread cannot be calculated
	 */

	public abstract double gSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return G Spread from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return G Spread from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return G Spread from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the G Spread cannot be calculated
	 */

	public abstract double gSpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return G Spread from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate G Spread from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return G Spread from Z Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if G Spread cannot be calculated
	 */

	public abstract double gSpreadFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return I Spread from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the I Spread cannot be calculated
	 */

	public abstract double iSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return I Spread from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return I Spread from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return I Spread from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the I Spread cannot be calculated
	 */

	public abstract double iSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return I Spread from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return I Spread from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return I Spread from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the I Spread cannot be calculated
	 */

	public abstract double iSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return I Spread from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return I Spread from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return I Spread from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the I Spread cannot be calculated
	 */

	public abstract double iSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return I Spread from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return I Spread from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return I Spread from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the I Spread cannot be calculated
	 */

	public abstract double iSpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return I Spread from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return I Spread from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return I Spread from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the I Spread cannot be calculated
	 */

	public abstract double iSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return I Spread from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return I Spread from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return J Spread from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the J Spread cannot be calculated
	 */

	public abstract double iSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return I Spread from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Optimal Exercise
	 * 
	 * @return I Spread from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return I Spread from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the I Spread cannot be calculated
	 */

	public abstract double iSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return I Spread from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return I Spread from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return I Spread from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the I Spread cannot be calculated
	 */

	public abstract double iSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return I Spread from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return I Spread from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return I Spread from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the I Spread cannot be calculated
	 */

	public abstract double iSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return I Spread from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return I Spread from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return I Spread from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the I Spread cannot be calculated
	 */

	public abstract double iSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return I Spread from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return I Spread from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return I Spread from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the I Spread cannot be calculated
	 */

	public abstract double iSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return I Spread from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return I Spread from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return I Spread from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the I Spread cannot be calculated
	 */

	public abstract double iSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return I Spread from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return I Spread from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return I Spread from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the I Spread cannot be calculated
	 */

	public abstract double iSpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return I Spread from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate I Spread from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return I Spread from Z Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if I Spread cannot be calculated
	 */

	public abstract double iSpreadFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return J Spread from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the J Spread cannot be calculated
	 */

	public abstract double jSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return J Spread from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return J Spread from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return J Spread from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the J Spread cannot be calculated
	 */

	public abstract double jSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return J Spread from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return J Spread from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return J Spread from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the J Spread cannot be calculated
	 */

	public abstract double jSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return J Spread from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return J Spread from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return J Spread from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the J Spread cannot be calculated
	 */

	public abstract double jSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return J Spread from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return J Spread from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return J Spread from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the J Spread cannot be calculated
	 */

	public abstract double jSpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return J Spread from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return J Spread from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return J Spread from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the J Spread cannot be calculated
	 */

	public abstract double jSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return J Spread from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return J Spread from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return J Spread from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the J Spread cannot be calculated
	 */

	public abstract double jSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return J Spread from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Optimal Exercise
	 * 
	 * @return J Spread from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return J Spread from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the J Spread cannot be calculated
	 */

	public abstract double jSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return J Spread from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return J Spread from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return J Spread from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the J Spread cannot be calculated
	 */

	public abstract double jSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return J Spread from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return J Spread from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return J Spread from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the J Spread cannot be calculated
	 */

	public abstract double jSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return J Spread from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return J Spread from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return J Spread from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the J Spread cannot be calculated
	 */

	public abstract double jSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return J Spread from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return J Spread from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return J Spread from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the J Spread cannot be calculated
	 */

	public abstract double jSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return J Spread from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return J Spread from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return J Spread from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the J Spread cannot be calculated
	 */

	public abstract double jSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return J Spread from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return J Spread from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return J Spread from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the J Spread cannot be calculated
	 */

	public abstract double jSpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return J Spread from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate J Spread from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return J Spread from Z Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if J Spread cannot be calculated
	 */

	public abstract double jSpreadFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return Macaulay Duration from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return Macaulay Duration from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return Macaulay Duration from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return Macaulay Duration from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return Macaulay Duration from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return Macaulay Duration from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return Macaulay Duration from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return Macaulay Duration from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return Macaulay Duration from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return Macaulay Duration from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return Macaulay Duration from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return Macaulay Duration from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return Macaulay Duration from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return Macaulay Duration from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return Macaulay Duration from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return Macaulay Duration from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return Macaulay Duration from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return Macaulay Duration from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return Macaulay Duration from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return Macaulay Duration from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Optimal Exercise
	 * 
	 * @return Macaulay Duration from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return Macaulay Duration from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return Macaulay Duration from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Optimal Exercise
	 * 
	 * @return Macaulay Duration from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return Macaulay Duration from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return Macaulay Duration from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return Macaulay Duration from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double mnacaulayDurationFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return Macaulay Duration from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return Macaulay Duration from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return Macaulay Duration from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return Macaulay Duration from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return Macaulay Duration from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return Macaulay Duration from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return Macaulay Duration from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return Macaulay Duration from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return Macaulay Duration from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return Macaulay Duration from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return Macaulay Duration from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return Macaulay Duration from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return Macaulay Duration from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return Macaulay Duration from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return Macaulay Duration from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return Macaulay Duration from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return Macaulay Duration from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Macaulay Duration from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return Macaulay Duration from Z Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Macaulay Duration cannot be calculated
	 */

	public abstract double macaulayDurationFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return Modified Duration from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return Modified Duration from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return Modified Duration from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return Modified Duration from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return Modified Duration from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return Modified Duration from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return Modified Duration from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return Modified Duration from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return Modified Duration from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return Modified Duration from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return Modified Duration from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return Modified Duration from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return Modified Duration from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return Modified Duration from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return Modified Duration from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return Modified Duration from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return Modified Duration from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return Modified Duration from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return Modified Duration from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return Modified Duration from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Optimal Exercise
	 * 
	 * @return Modified Duration from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread JSpread to Work-out
	 * 
	 * @return Modified Duration from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return Modified Duration from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Optimal Exercise
	 * 
	 * @return Modified Duration from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return Modified Duration from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return Modified Duration from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return Modified Duration from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return Modified Duration from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return Modified Duration from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return Modified Duration from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return Modified Duration from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return Modified Duration from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return Modified Duration from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return Modified Duration from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return Modified Duration from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return Modified Duration from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return Modified Duration from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return Modified Duration from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return Modified Duration from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return Modified Duration from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return Modified Duration from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return Modified Duration from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return Modified Duration from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return Modified Duration from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Modified Duration from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return Modified Duration from Z Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Modified Duration cannot be calculated
	 */

	public abstract double modifiedDurationFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return OAS from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the OAS cannot be calculated
	 */

	public abstract double oasFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return OAS from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return OAS from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return OAS from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the OAS cannot be calculated
	 */

	public abstract double oasFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return OAS from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return OAS from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return OAS from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the OAS cannot be calculated
	 */

	public abstract double oasFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return OAS from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return OAS from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return OAS from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the OAS cannot be calculated
	 */

	public abstract double oasFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return OAS from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return OAS from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return OAS from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the OAS cannot be calculated
	 */

	public abstract double oasFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return OAS from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return OAS from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return OAS from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the OAS cannot be calculated
	 */

	public abstract double oasFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return OAS from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return OAS from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return OAS from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the OAS cannot be calculated
	 */

	public abstract double oasFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return OAS from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread ISpread to Optimal Exercise
	 * 
	 * @return OAS from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return OAS from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the OAS cannot be calculated
	 */

	public abstract double oasFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return OAS from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread JSpread to Optimal Exercise
	 * 
	 * @return OAS from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return OAS from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the OAS cannot be calculated
	 */

	public abstract double oasFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return OAS from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return OAS from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return OAS from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the OAS cannot be calculated
	 */

	public abstract double oasFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return OAS from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return OAS from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return OAS from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the OAS cannot be calculated
	 */

	public abstract double oasFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return OAS from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return OAS from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return OAS from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the OAS cannot be calculated
	 */

	public abstract double oasFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return OAS from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return OAS from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return OAS from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the OAS cannot be calculated
	 */

	public abstract double oasFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return OAS from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return OAS from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return OAS from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the OAS cannot be calculated
	 */

	public abstract double oasFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return OAS from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate OAS from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return OAS from Z Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if OAS cannot be calculated
	 */

	public abstract double oasFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return PECS from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the PECS cannot be calculated
	 */

	public abstract double pecsFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return PECS from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return PECS from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return PECS from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the PECS cannot be calculated
	 */

	public abstract double pecsFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return PECS from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return PECS from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return PECS from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the PECS cannot be calculated
	 */

	public abstract double pecsFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return PECS from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return PECS from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return PECS from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the PECS cannot be calculated
	 */

	public abstract double pecsFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return PECS from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return PECS from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return PECS from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the PECS cannot be calculated
	 */

	public abstract double pecsFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return PECS from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return PECS from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return PECS from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the PECS cannot be calculated
	 */

	public abstract double pecsFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return PECS from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return PECS from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return PECS from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the PECS cannot be calculated
	 */

	public abstract double pecsFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return PECS from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread ISpread to Optimal Exercise
	 * 
	 * @return PECS from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return PECS from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the PECS cannot be calculated
	 */

	public abstract double pecsFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return PECS from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread JSpread to Optimal Exercise
	 * 
	 * @return PECS from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return PECS from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the PECS cannot be calculated
	 */

	public abstract double pecsFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return PECS from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return PECS from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return PECS from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public abstract double pecsFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return PECS from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return PECS from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return PECS from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the PECS cannot be calculated
	 */

	public abstract double pecsFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return PECS from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return PECS from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return PECS from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the PECS cannot be calculated
	 */

	public abstract double pecsFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return PECS from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return PECS from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return PECS from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the PECS cannot be calculated
	 */

	public abstract double pecsFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return PECS from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return PECS from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return PECS from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the PECS cannot be calculated
	 */

	public abstract double pecsFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return PECS from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate PECS from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return PECS from Z Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if PECS cannot be calculated
	 */

	public abstract double pecsFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return Price from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public abstract double priceFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Price from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return Price from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Price from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return Price from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return Price from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public abstract double priceFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return Price from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return Price from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return Price from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public abstract double priceFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return Price from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return Price from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return Price from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public abstract double priceFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return Price from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return Price from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Price from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return Price from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public abstract double priceFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return Price from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return Price from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return Price from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public abstract double priceFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return Price from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return Price from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return Price from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public abstract double priceFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return Price from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Optimal Exercise
	 * 
	 * @return Price from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return Price from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public abstract double priceFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return Price from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread ISpread to Optimal Exercise
	 * 
	 * @return Price from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return Price from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public abstract double priceFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Price from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return Price from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Price from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return Price from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Price from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return Price from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the PECS cannot be calculated
	 */

	public abstract double priceFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Price from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return Price from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Price from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return Price from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Price from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return Price from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public abstract double priceFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return Price from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return Price from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return Price from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public abstract double priceFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return Price from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return Price from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return Price from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public abstract double priceFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return Price from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return Price from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return Price from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Price cannot be calculated
	 */

	public abstract double priceFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return Price from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Price from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return Price from Z Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Price cannot be calculated
	 */

	public abstract double priceFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return TSY Spread from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return TSY Spread from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return TSY Spread from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return TSY Spread from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return TSY Spread from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return TSY Spread from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return TSY Spread from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return TSY Spread from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return TSY Spread from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return TSY Spread from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return TSY Spread from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return TSY Spread from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return TSY Spread from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return TSY Spread from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Optimal Exercise
	 * 
	 * @return TSY Spread from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return TSY Spread from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return TSY Spread from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return TSY Spread from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return TSY Spread from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return TSY Spread from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return TSY Spread from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return TSY Spread from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return TSY Spread from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Optimal Exercise
	 * 
	 * @return TSY Spread from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return TSY Spread from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return TSY Spread from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return TSY Spread from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return TSY Spread from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return TSY Spread from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return TSY Spread from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return TSY Spread from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return TSY Spread from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return TSY Spread from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return TSY Spread from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return TSY Spread from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return TSY Spread from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return TSY Spread from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return TSY Spread from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return TSY Spread from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return TSY Spread from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return TSY Spread from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate TSY Spread from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return TSY Spread from Z Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if TSY Spread cannot be calculated
	 */

	public abstract double tsySpreadFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return Yield from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield cannot be calculated
	 */

	public abstract double yieldFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return Yield from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return Yield from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return Yield from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield cannot be calculated
	 */

	public abstract double yieldFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return Yield from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return Yield from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return Yield from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield cannot be calculated
	 */

	public abstract double yieldFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return Yield from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return Yield from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return Yield from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield cannot be calculated
	 */

	public abstract double yieldFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return Yield from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return Yield from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return Yield from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield cannot be calculated
	 */

	public abstract double yieldFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return Yield from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return Yield from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return Yield from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield cannot be calculated
	 */

	public abstract double yieldFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return Yield from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return Yield from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return Yield from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield cannot be calculated
	 */

	public abstract double yieldFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return Yield from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Optimal Exercise
	 * 
	 * @return Yield from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return Yield from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield cannot be calculated
	 */

	public abstract double yieldFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return Yield from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread ISpread to Optimal Exercise
	 * 
	 * @return Yield from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return Yield from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield cannot be calculated
	 */

	public abstract double yieldFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return Yield from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return Yield from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return Yield from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield cannot be calculated
	 */

	public abstract double yieldFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return Yield from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return Yield from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return Yield from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield cannot be calculated
	 */

	public abstract double yieldFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return Yield from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return Yield from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Price to Work-out after applying the Tax Credit Coupon Extension
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return Yield from Price to Work-out after applying the Tax Credit Coupon Extension
	 * 
	 * @throws java.lang.Exception Thrown if the Yield cannot be calculated
	 */

	public abstract double yieldFromPriceTC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return Yield from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield cannot be calculated
	 */

	public abstract double yieldFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return Yield from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return Yield from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return Yield from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield cannot be calculated
	 */

	public abstract double yieldFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return Yield from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return Yield from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return Yield from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield cannot be calculated
	 */

	public abstract double yieldFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return Yield from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return Yield from Z Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield cannot be calculated
	 */

	public abstract double yieldFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return Yield01 from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield01 cannot be calculated
	 */

	public abstract double yield01FromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return Yield01 from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return Yield01 from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return Yield01 from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield01 cannot be calculated
	 */

	public abstract double yield01FromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return Yield01 from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return Yield01 from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return Yield01 from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield01 cannot be calculated
	 */

	public abstract double yield01FromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return Yield01 from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return Yield01 from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return Yield01 from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield01 cannot be calculated
	 */

	public abstract double yield01FromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return Yield01 from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return Yield01 from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return Yield01 from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield01 cannot be calculated
	 */

	public abstract double yield01FromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return Yield01 from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return Yield01 from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return Yield01 from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield01 cannot be calculated
	 */

	public abstract double yield01FromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return Yield01 from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return Yield01 from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return Yield01 from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield01 cannot be calculated
	 */

	public abstract double yield01FromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return Yield01 from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread ISpread to Optimal Exercise
	 * 
	 * @return Yield01 from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return Yield01 from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield01 cannot be calculated
	 */

	public abstract double yield01FromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return Yield01 from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread JSpread to Optimal Exercise
	 * 
	 * @return Yield01 from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return Yield01 from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield01 cannot be calculated
	 */

	public abstract double yield01FromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return Yield01 from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return Yield01 from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return Yield01 from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield01 cannot be calculated
	 */

	public abstract double yield01FromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return Yield01 from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return Yield01 from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return Yield01 from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield01 cannot be calculated
	 */

	public abstract double yield01FromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return Yield01 from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return Yield01 from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return Yield01 from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield01 cannot be calculated
	 */

	public abstract double yield01FromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return Yield01 from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return Yield01 from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return Yield01 from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield01 cannot be calculated
	 */

	public abstract double yield01FromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return Yield01 from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return Yield01 from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return Yield01 from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield01 cannot be calculated
	 */

	public abstract double yield01FromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return Yield01 from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return Yield01 from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return Yield01 from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield01 cannot be calculated
	 */

	public abstract double yield01FromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return Yield01 from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield01 from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return Yield01 from Z Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield01 cannot be calculated
	 */

	public abstract double yield01FromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return Yield Spread from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return Yield Spread from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return Yield Spread from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return Yield Spread from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return Yield Spread from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return Yield Spread from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return Yield Spread from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return Yield Spread from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return Yield Spread from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return Yield Spread from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return Yield Spread from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return Yield Spread from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from E Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblESpread E Spread to Work-out
	 * 
	 * @return Yield Spread from E Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from E Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Maturity
	 * 
	 * @return Yield Spread from E Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from E Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblESpread E Spread to Optimal Exercise
	 * 
	 * @return Yield Spread from E Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return Yield Spread from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return Yield Spread from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return Yield Spread from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return Yield Spread from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return Yield Spread from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread ISpread to Optimal Exercise
	 * 
	 * @return Yield Spread from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return Yield Spread from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return Yield Spread from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread JSpread to Optimal Exercise
	 * 
	 * @return Yield Spread from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return Yield Spread from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return Yield Spread from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return Yield Spread from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return Yield Spread from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return Yield Spread from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return Yield Spread from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return Yield Spread from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return Yield Spread from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return Yield Spread from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return Yield Spread from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return Yield Spread from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return Yield Spread from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return Yield Spread from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return Yield Spread from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return Yield Spread from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Z Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblZSpread Z Spread to Work-out
	 * 
	 * @return Yield Spread from Z Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Z Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Maturity
	 * 
	 * @return Yield Spread from Z Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Yield Spread from Z Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblZSpread Z Spread to Optimal Exercise
	 * 
	 * @return Yield Spread from Z Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double yieldSpreadFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from ASW to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblASW ASW to Work-out
	 * 
	 * @return Z Spread from ASW to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from ASW to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Maturity
	 * 
	 * @return Z Spread from ASW to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from ASW to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblASW ASW to Optimal Exercise
	 * 
	 * @return Z Spread from ASW to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Bond Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblBondBasis Bond Basis to Work-out
	 * 
	 * @return Z Spread from Bond Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Bond Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Maturity
	 * 
	 * @return Z Spread from Bond Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Bond Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblBondBasis Bond Basis to Optimal Exercise
	 * 
	 * @return Z Spread from Bond Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Credit Basis to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblCreditBasis Credit Basis to Work-out
	 * 
	 * @return Z Spread from Credit Basis to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Credit Basis to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Maturity
	 * 
	 * @return Z Spread from Credit Basis to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Credit Basis to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblCreditBasis Credit Basis to Optimal Exercise
	 * 
	 * @return Z Spread from Credit Basis to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Discount Margin to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblDiscountMargin Discount Margin to Work-out
	 * 
	 * @return Z Spread from Discount Margin to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Discount Margin to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Maturity
	 * 
	 * @return Z Spread from Discount Margin to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Discount Margin to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblDiscountMargin Discount Margin to Optimal Exercise
	 * 
	 * @return Z Spread from Discount Margin to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from G Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblGSpread G Spread to Work-out
	 * 
	 * @return Z Spread from G Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from G Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Maturity
	 * 
	 * @return Z Spread from G Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from G Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblGSpread G Spread to Optimal Exercise
	 * 
	 * @return Z Spread from G Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from I Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblISpread I Spread to Work-out
	 * 
	 * @return Z Spread from I Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from I Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread I Spread to Maturity
	 * 
	 * @return Z Spread from I Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from I Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblISpread ISpread to Optimal Exercise
	 * 
	 * @return Z Spread from I Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from J Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblJSpread J Spread to Work-out
	 * 
	 * @return Z Spread from J Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from J Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread J Spread to Maturity
	 * 
	 * @return Z Spread from J Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from J Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblJSpread JSpread to Optimal Exercise
	 * 
	 * @return Z Spread from J Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from OAS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblOAS OAS to Work-out
	 * 
	 * @return Z Spread from OAS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from OAS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Maturity
	 * 
	 * @return Z Spread from OAS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from OAS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblOAS OAS to Optimal Exercise
	 * 
	 * @return Z Spread from OAS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Price to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPrice Price to Work-out
	 * 
	 * @return Z Spread from Price to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Price to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Maturity
	 * 
	 * @return Z Spread from Price to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Price to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPrice Price to Optimal Exercise
	 * 
	 * @return Z Spread from Price to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from PECS to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblPECS PECS to Work-out
	 * 
	 * @return Z Spread from PECS to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from PECS to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Maturity
	 * 
	 * @return Z Spread from PECS to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from PECS to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblPECS PECS to Optimal Exercise
	 * 
	 * @return Z Spread from PECS to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from TSY Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblTSYSpread TSY Spread to Work-out
	 * 
	 * @return Z Spread from TSY Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from TSY Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Maturity
	 * 
	 * @return Z Spread from TSY Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from TSY Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblTSYSpread TSY Spread to Optimal Exercise
	 * 
	 * @return Z Spread from TSY Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Yield to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYield Yield to Work-out
	 * 
	 * @return Z Spread from Yield to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Yield to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Maturity
	 * 
	 * @return Z Spread from Yield to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Yield to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYield Yield to Optimal Exercise
	 * 
	 * @return Z Spread from Yield to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Yield Spread to Work-out
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param iWorkoutDate Work-out Date
	 * @param dblWorkoutFactor Work-out Factor
	 * @param dblYieldSpread Yield Spread to Work-out
	 * 
	 * @return Z Spread from Yield Spread to Work-out
	 * 
	 * @throws java.lang.Exception Thrown if the Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Yield Spread to Maturity
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Maturity
	 * 
	 * @return Z Spread from Yield Spread to Maturity
	 * 
	 * @throws java.lang.Exception Thrown if Z Spread cannot be calculated
	 */

	public abstract double zSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate Z Spread from Yield Spread to Optimal Exercise
	 * 
	 * @param valParams Valuation Parameters
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param dblYieldSpread Yield Spread to Optimal Exercise
	 * 
	 * @return Z Spread from Yield Spread to Optimal Exercise
	 * 
	 * @throws java.lang.Exception Thrown if Yield Spread cannot be calculated
	 */

	public abstract double zSpreadFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception;

	/**
	 * Calculate the full set of Bond RV Measures from the Price Input
	 * 
	 * @param valParams ValuationParams
	 * @param pricerParams Pricing Parameters
	 * @param csqs Bond market parameters
	 * @param vcp Valuation Customization Parameters
	 * @param wi Work out Information
	 * @param dblPrice Input Price
	 * 
	 * @return Bond RV Measure Set
	 */

	public abstract org.drip.analytics.output.BondRVMeasures standardMeasures (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.param.valuation.WorkoutInfo wi,
		final double dblPrice);

	/**
	 * Display all the coupon periods onto stdout
	 * 
	 * @throws java.lang.Exception Thrown if the coupon periods cannot be displayed onto stdout
	 */

	public abstract void showPeriods()
		throws java.lang.Exception;
}
