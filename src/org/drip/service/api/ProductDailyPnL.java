
package org.drip.service.api;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * ProductDailyPnL contains the following daily measures computed:
 * 	- 1D Carry, Roll Down, Curve Shift, and Full Return PnL
 * 	- 3D Carry and Roll Down PnL
 * 	- 3M Carry and Roll Down PnL
 * 	- Current DV01
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ProductDailyPnL {
	private int _i1DFixedAccrualDays = 0;
	private int _i1DFloatingAccrualDays = 0;
	private double _dblDV01 = java.lang.Double.NaN;
	private double _dbl1DCarryPnL = java.lang.Double.NaN;
	private double _dbl1MCarryPnL = java.lang.Double.NaN;
	private double _dbl3MCarryPnL = java.lang.Double.NaN;
	private double _dbl1DFixedDCF = java.lang.Double.NaN;
	private double _dbl1MFixedDCF = java.lang.Double.NaN;
	private double _dbl3MFixedDCF = java.lang.Double.NaN;
	private double _dbl1DCleanPnL = java.lang.Double.NaN;
	private double _dbl1DDirtyPnL = java.lang.Double.NaN;
	private double _dbl1DTotalPnL = java.lang.Double.NaN;
	private double _dbl1DTimeRollPnL = java.lang.Double.NaN;
	private double _dbl1DFloatingDCF = java.lang.Double.NaN;
	private double _dbl1MFloatingDCF = java.lang.Double.NaN;
	private double _dbl3MFloatingDCF = java.lang.Double.NaN;
	private double _dblCleanFixedDV01 = java.lang.Double.NaN;
	private double _dblCleanFloatDV01 = java.lang.Double.NaN;
	private double _dblDV01WithFixing = java.lang.Double.NaN;
	private double _dbl1DCurveShiftPnL = java.lang.Double.NaN;
	private double _dblPeriodFixedRate = java.lang.Double.NaN;
	private double _dblBaselineSwapRate = java.lang.Double.NaN;
	private double _dbl1DTimeRollSwapRate = java.lang.Double.NaN;
	private double _dbl1DCleanPnLWithFixing = java.lang.Double.NaN;
	private double _dbl1DDirtyPnLWithFixing = java.lang.Double.NaN;
	private double _dbl1DTotalPnLWithFixing = java.lang.Double.NaN;
	private double _dbl1DCurveShiftSwapRate = java.lang.Double.NaN;
	private double _dblPeriodFloatingRateUsed = java.lang.Double.NaN;
	private double _dblPeriodCurveFloatingRate = java.lang.Double.NaN;
	private double _dbl1DMaturityRollUpSwapRate = java.lang.Double.NaN;
	private double _dblCleanFloatDV01WithFixing = java.lang.Double.NaN;
	private double _dblPeriodProductFloatingRate = java.lang.Double.NaN;
	private double _dbl1DMaturityRollDownSwapRate = java.lang.Double.NaN;
	private double _dbl1MMaturityRollDownSwapRate = java.lang.Double.NaN;
	private double _dbl3MMaturityRollDownSwapRate = java.lang.Double.NaN;
	private double _dbl1DMaturityRollUpSwapRatePnL = java.lang.Double.NaN;
	private double _dbl1DMaturityRollUpFairPremium = java.lang.Double.NaN;
	private double _dbl1DMaturityRollDownSwapRatePnL = java.lang.Double.NaN;
	private double _dbl1MMaturityRollDownSwapRatePnL = java.lang.Double.NaN;
	private double _dbl3MMaturityRollDownSwapRatePnL = java.lang.Double.NaN;
	private double _dbl1DMaturityRollUpFairPremiumPnL = java.lang.Double.NaN;
	private double _dbl1DMaturityRollUpFairPremiumWithFixing = java.lang.Double.NaN;
	private double _dbl1DMaturityRollUpFairPremiumWithFixingPnL = java.lang.Double.NaN;

	/**
	 * ProductDailyPnL constructor
	 * 
	 * @param dbl1DTotalPnL 1D Total PnL
	 * @param dbl1DCleanPnL 1D Clean PnL
	 * @param dbl1DDirtyPnL 1D Dirty PnL
	 * @param dbl1DTotalPnLWithFixing 1D Total PnL With Fixing
	 * @param dbl1DCleanPnLWithFixing 1D Clean PnL With Fixing
	 * @param dbl1DDirtyPnLWithFixing 1D Dirty PnL With Fixing
	 * @param dbl1DCarryPnL 1D Carry PnL
	 * @param dbl1DTimeRollPnL 1D Time Roll PnL
	 * @param dbl1DMaturityRollDownSwapRatePnL 1D Curve Maturity Roll Down implied Par Swap rate PnL
	 * @param dbl1DMaturityRollUpSwapRatePnL 1D Curve Maturity Roll Up implied Par Swap rate PnL
	 * @param dbl1DMaturityRollUpFairPremiumPnL 1D Curve Maturity Roll Up implied Fair Premium PnL
	 * @param dbl1DMaturityRollUpFairPremiumWithFixingPnL 1D Curve Maturity Roll Up implied Fair Premium With
	 * 	Fixing PnL
	 * @param dbl1DCurveShiftPnL 1D Curve Shift PnL
	 * @param dbl1MCarryPnL 1M Carry PnL
	 * @param dbl1MMaturityRollDownSwapRatePnL 1M Curve Maturity Roll Down implied Par Swap rate PnL
	 * @param dbl3MCarryPnL 3M Carry PnL
	 * @param dbl3MMaturityRollDownSwapRatePnL 3M Curve Maturity Roll Down implied Par Swap rate PnL
	 * @param dblDV01 DV01
	 * @param dblDV01WithFixing DV01 With Fixing
	 * @param dblCleanFixedDV01 Clean Fixed DV01
	 * @param dblCleanFloatDV01 Clean Float DV01
	 * @param dblCleanFloatDV01WithFixing Clean Float DV01 With Fixing
	 * @param dblBaselineSwapRate Baseline Par Swap Rate
	 * @param dbl1DTimeRollSwapRate 1D Curve Time Roll implied Par Swap rate
	 * @param dbl1DMaturityRollDownSwapRate 1D Curve Maturity Roll Down Implied Par Swap rate
	 * @param dbl1MMaturityRollDownSwapRate 1M Curve Maturity Roll Down implied Par Swap rate
	 * @param dbl3MMaturityRollDownSwapRate 3M Curve Maturity Roll Down implied Par Swap rate
	 * @param dbl1DMaturityRollUpSwapRate 1D Curve Maturity Roll Up Implied Par Swap rate
	 * @param dbl1DMaturityRollUpFairPremium 1D Curve Maturity Roll Up Implied Fair Premium
	 * @param dbl1DMaturityRollUpFairPremiumWithFixing 1D Curve Maturity Roll Up Implied Fair Premium With
	 * 	Fixing
	 * @param dbl1DCurveShiftSwapRate 1D Day-to-Day Curve Shift implied Par Swap rate
	 * @param dblPeriodFixedRate The Period Fixed Rate
	 * @param dblPeriodCurveFloatingRate The Period Curve Floating Rate
	 * @param dblPeriodProductFloatingRate The Period Product Floating Rate
	 * @param dblPeriodFloatingRateUsed The Period Floating Rate Used
	 * @param i1DFixedAccrualDays 1D Fixed Accrual Days
	 * @param i1DFloatingAccrualDays 1D Floating Accrual Days
	 * @param dbl1DFixedDCF 1D Fixed Coupon DCF
	 * @param dbl1DFloatingDCF 1D Floating Coupon DCF
	 * @param dbl1MFixedDCF 1M Fixed Coupon DCF
	 * @param dbl1MFloatingDCF 1M Floating Coupon DCF
	 * @param dbl3MFixedDCF 3M Fixed Coupon DCF
	 * @param dbl3MFloatingDCF 3M Floating Coupon DCF
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public ProductDailyPnL (
		final double dbl1DTotalPnL,
		final double dbl1DCleanPnL,
		final double dbl1DDirtyPnL,
		final double dbl1DTotalPnLWithFixing,
		final double dbl1DCleanPnLWithFixing,
		final double dbl1DDirtyPnLWithFixing,
		final double dbl1DCarryPnL,
		final double dbl1DTimeRollPnL,
		final double dbl1DMaturityRollDownSwapRatePnL,
		final double dbl1DMaturityRollUpSwapRatePnL,
		final double dbl1DMaturityRollUpFairPremiumPnL,
		final double dbl1DMaturityRollUpFairPremiumWithFixingPnL,
		final double dbl1DCurveShiftPnL,
		final double dbl1MCarryPnL,
		final double dbl1MMaturityRollDownSwapRatePnL,
		final double dbl3MCarryPnL,
		final double dbl3MMaturityRollDownSwapRatePnL,
		final double dblDV01,
		final double dblDV01WithFixing,
		final double dblCleanFixedDV01,
		final double dblCleanFloatDV01,
		final double dblCleanFloatDV01WithFixing,
		final double dblBaselineSwapRate,
		final double dbl1DTimeRollSwapRate,
		final double dbl1DMaturityRollDownSwapRate,
		final double dbl1MMaturityRollDownSwapRate,
		final double dbl3MMaturityRollDownSwapRate,
		final double dbl1DMaturityRollUpSwapRate,
		final double dbl1DMaturityRollUpFairPremium,
		final double dbl1DMaturityRollUpFairPremiumWithFixing,
		final double dbl1DCurveShiftSwapRate,
		final double dblPeriodFixedRate,
		final double dblPeriodCurveFloatingRate,
		final double dblPeriodProductFloatingRate,
		final double dblPeriodFloatingRateUsed,
		final int i1DFixedAccrualDays,
		final int i1DFloatingAccrualDays,
		final double dbl1DFixedDCF,
		final double dbl1DFloatingDCF,
		final double dbl1MFixedDCF,
		final double dbl1MFloatingDCF,
		final double dbl3MFixedDCF,
		final double dbl3MFloatingDCF)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dbl1DTotalPnL = dbl1DTotalPnL) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dbl1DCleanPnL = dbl1DCleanPnL) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dbl1DDirtyPnL = dbl1DDirtyPnL) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dbl1DTotalPnLWithFixing =
						dbl1DTotalPnLWithFixing) || !org.drip.quant.common.NumberUtil.IsValid
							(_dbl1DCleanPnLWithFixing = dbl1DCleanPnLWithFixing) ||
								!org.drip.quant.common.NumberUtil.IsValid (_dbl1DDirtyPnLWithFixing =
									dbl1DDirtyPnLWithFixing) || !org.drip.quant.common.NumberUtil.IsValid
										(_dbl1DCarryPnL = dbl1DCarryPnL) ||
											!org.drip.quant.common.NumberUtil.IsValid (_dbl1DTimeRollPnL =
												dbl1DTimeRollPnL) ||
													!org.drip.quant.common.NumberUtil.IsValid
														(_dbl1DMaturityRollDownSwapRatePnL =
															dbl1DMaturityRollDownSwapRatePnL) ||
																!org.drip.quant.common.NumberUtil.IsValid
																	(_dbl1DMaturityRollUpSwapRatePnL =
																		dbl1DMaturityRollUpSwapRatePnL) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dbl1DMaturityRollUpFairPremiumPnL =
				dbl1DMaturityRollUpFairPremiumPnL) || !org.drip.quant.common.NumberUtil.IsValid
					(_dbl1DMaturityRollUpFairPremiumWithFixingPnL =
						dbl1DMaturityRollUpFairPremiumWithFixingPnL) ||
							!org.drip.quant.common.NumberUtil.IsValid (_dbl1DCurveShiftPnL =
								dbl1DCurveShiftPnL) || !org.drip.quant.common.NumberUtil.IsValid
									(_dbl1MCarryPnL = dbl1MCarryPnL) ||
										!org.drip.quant.common.NumberUtil.IsValid
											(_dbl1MMaturityRollDownSwapRatePnL =
												dbl1MMaturityRollDownSwapRatePnL) ||
													!org.drip.quant.common.NumberUtil.IsValid (_dbl3MCarryPnL
														= dbl3MCarryPnL) ||
															!org.drip.quant.common.NumberUtil.IsValid
																(_dbl3MMaturityRollDownSwapRatePnL =
																	dbl3MMaturityRollDownSwapRatePnL) ||
																		!org.drip.quant.common.NumberUtil.IsValid
			(_dblDV01 = dblDV01) || !org.drip.quant.common.NumberUtil.IsValid (_dblDV01WithFixing =
				dblDV01WithFixing) || !org.drip.quant.common.NumberUtil.IsValid (_dblCleanFixedDV01 =
					dblCleanFixedDV01) || !org.drip.quant.common.NumberUtil.IsValid (_dblCleanFloatDV01 =
						dblCleanFloatDV01) || !org.drip.quant.common.NumberUtil.IsValid
							(_dblCleanFloatDV01WithFixing = dblCleanFloatDV01WithFixing) ||
								!org.drip.quant.common.NumberUtil.IsValid (_dblBaselineSwapRate =
									dblBaselineSwapRate) || !org.drip.quant.common.NumberUtil.IsValid
										(_dbl1DTimeRollSwapRate = dbl1DTimeRollSwapRate) ||
											!org.drip.quant.common.NumberUtil.IsValid
												(_dbl1DMaturityRollDownSwapRate =
													dbl1DMaturityRollDownSwapRate) ||
														!org.drip.quant.common.NumberUtil.IsValid
															(_dbl1MMaturityRollDownSwapRate =
																dbl1MMaturityRollDownSwapRate) ||
																	!org.drip.quant.common.NumberUtil.IsValid
																		(_dbl3MMaturityRollDownSwapRate =
																			dbl3MMaturityRollDownSwapRate) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dbl1DMaturityRollUpSwapRate =
				dbl1DMaturityRollUpSwapRate) || !org.drip.quant.common.NumberUtil.IsValid
					(_dbl1DMaturityRollUpFairPremium = dbl1DMaturityRollUpFairPremium) ||
						!org.drip.quant.common.NumberUtil.IsValid (_dbl1DMaturityRollUpFairPremiumWithFixing
							= dbl1DMaturityRollUpFairPremiumWithFixing) ||
								!org.drip.quant.common.NumberUtil.IsValid (_dbl1DCurveShiftSwapRate =
									dbl1DCurveShiftSwapRate) || !org.drip.quant.common.NumberUtil.IsValid
										(_dblPeriodFixedRate = dblPeriodFixedRate) ||
											!org.drip.quant.common.NumberUtil.IsValid
												(_dblPeriodCurveFloatingRate = dblPeriodCurveFloatingRate) ||
													!org.drip.quant.common.NumberUtil.IsValid
														(_dblPeriodProductFloatingRate =
															dblPeriodProductFloatingRate) ||
																!org.drip.quant.common.NumberUtil.IsValid
																	(_dblPeriodFloatingRateUsed =
																		dblPeriodFloatingRateUsed) ||
																			!org.drip.quant.common.NumberUtil.IsValid
			(_dbl1DFixedDCF = dbl1DFixedDCF) || !org.drip.quant.common.NumberUtil.IsValid (_dbl1DFloatingDCF
				= dbl1DFloatingDCF) || !org.drip.quant.common.NumberUtil.IsValid (_dbl1MFixedDCF =
					dbl1MFixedDCF) || !org.drip.quant.common.NumberUtil.IsValid (_dbl1MFloatingDCF =
						dbl1MFloatingDCF) || !org.drip.quant.common.NumberUtil.IsValid (_dbl3MFixedDCF =
							dbl3MFixedDCF) || !org.drip.quant.common.NumberUtil.IsValid (_dbl3MFloatingDCF =
								dbl3MFloatingDCF))
			throw new java.lang.Exception ("ProductDailyPnL ctr: Invalid Inputs!");

		_i1DFixedAccrualDays = i1DFixedAccrualDays;
		_i1DFloatingAccrualDays = i1DFloatingAccrualDays;
	}

	/**
	 * Retrieve the 1D Clean PnL
	 * 
	 * @return The 1D Clean PnL
	 */

	public double clean1DPnL()
	{
		return _dbl1DCleanPnL;
	}

	/**
	 * Retrieve the 1D Dirty PnL
	 * 
	 * @return The 1D Dirty PnL
	 */

	public double dirty1DPnL()
	{
		return _dbl1DDirtyPnL;
	}

	/**
	 * Retrieve the 1D Total PnL
	 * 
	 * @return The 1D Total PnL
	 */

	public double total1DPnL()
	{
		return _dbl1DTotalPnL;
	}

	/**
	 * Retrieve the 1D Clean PnL With Fixing
	 * 
	 * @return The 1D Clean PnL With Fixing
	 */

	public double clean1DPnLWithFixing()
	{
		return _dbl1DCleanPnLWithFixing;
	}

	/**
	 * Retrieve the 1D Dirty PnL With Fixing
	 * 
	 * @return The 1D Dirty PnL With Fixing
	 */

	public double dirty1DPnLWithFixing()
	{
		return _dbl1DDirtyPnLWithFixing;
	}

	/**
	 * Retrieve the 1D Total PnL With Fixing
	 * 
	 * @return The 1D Total PnL With Fixing
	 */

	public double total1DPnLWithFixing()
	{
		return _dbl1DTotalPnLWithFixing;
	}

	/**
	 * Retrieve the 1D Carry PnL
	 * 
	 * @return The 1D Carry PnL
	 */

	public double carry1DPnL()
	{
		return _dbl1DCarryPnL;
	}

	/**
	 * Retrieve the 1D Time Roll PnL
	 * 
	 * @return The 1D Time Roll PnL
	 */

	public double timeRoll1DPnL()
	{
		return _dbl1DTimeRollPnL;
	}

	/**
	 * Retrieve the 1D Maturity Roll Down Swap Rate PnL
	 * 
	 * @return The 1D Maturity Roll Down Swap Rate PnL
	 */

	public double maturityRollDownSwapRate1DPnL()
	{
		return _dbl1DMaturityRollDownSwapRatePnL;
	}

	/**
	 * Retrieve the 1D Maturity Roll Up Swap Rate PnL
	 * 
	 * @return The 1D Maturity Roll Up Swap Rate PnL
	 */

	public double maturityRollUpSwapRate1DPnL()
	{
		return _dbl1DMaturityRollUpSwapRatePnL;
	}

	/**
	 * Retrieve the 1D Maturity Roll Up Fair Premium PnL
	 * 
	 * @return The 1D Maturity Roll Up Fair Premium PnL
	 */

	public double maturityRollUpFairPremium1DPnL()
	{
		return _dbl1DMaturityRollUpFairPremiumPnL;
	}

	/**
	 * Retrieve the 1D Maturity Roll Up Fair Premium With Fixing PnL
	 * 
	 * @return The 1D Maturity Roll Up Fair Premium With Fixing PnL
	 */

	public double maturityRollUpFairPremiumWithFixing1DPnL()
	{
		return _dbl1DMaturityRollUpFairPremiumWithFixingPnL;
	}

	/**
	 * Retrieve the 1D Curve Shift PnL
	 * 
	 * @return The 1D Curve Shift PnL
	 */

	public double curveShift1DPnL()
	{
		return _dbl1DCurveShiftPnL;
	}

	/**
	 * Retrieve the 1M Carry PnL
	 * 
	 * @return The 1M Carry PnL
	 */

	public double carry1MPnL()
	{
		return _dbl1MCarryPnL;
	}

	/**
	 * Retrieve the 1M Maturity Roll Down Swap Rate PnL
	 * 
	 * @return The 1M Maturity Roll Down Swap Rate PnL
	 */

	public double maturityRollDownSwapRate1MPnL()
	{
		return _dbl1MMaturityRollDownSwapRatePnL;
	}

	/**
	 * Retrieve the 3M Carry PnL
	 * 
	 * @return The 3M Carry PnL
	 */

	public double carry3MPnL()
	{
		return _dbl3MCarryPnL;
	}

	/**
	 * Retrieve the 3M Maturity Roll Down Swap Rate PnL
	 * 
	 * @return The 3M Maturity Roll Down Swap Rate PnL
	 */

	public double maturityRollDownSwapRate3MPnL()
	{
		return _dbl3MMaturityRollDownSwapRatePnL;
	}

	/**
	 * Retrieve the DV01
	 * 
	 * @return The DV01
	 */

	public double DV01()
	{
		return _dblDV01;
	}

	/**
	 * Retrieve the DV01 With Fixing
	 * 
	 * @return The DV01 With Fixing
	 */

	public double DV01WithFixing()
	{
		return _dblDV01WithFixing;
	}

	/**
	 * Retrieve the Clean Fixed DV01
	 * 
	 * @return The Clean Fixed DV01
	 */

	public double cleanFixedDV01()
	{
		return _dblCleanFixedDV01;
	}

	/**
	 * Retrieve the Clean Float DV01
	 * 
	 * @return The Clean Float DV01
	 */

	public double cleanFloatDV01()
	{
		return _dblCleanFloatDV01;
	}

	/**
	 * Retrieve the Clean Float DV01 With Fixing
	 * 
	 * @return The Clean Float DV01 With Fixing
	 */

	public double cleanFloatDV01WithFixing()
	{
		return _dblCleanFloatDV01WithFixing;
	}

	/**
	 * Retrieve the Baseline Swap Rate
	 * 
	 * @return The Baseline Swap Rate
	 */

	public double baselineSwapRate()
	{
		return _dblBaselineSwapRate;
	}

	/**
	 * Retrieve the 1D Time Roll Swap Rate
	 * 
	 * @return The 1D Time Roll Swap Rate
	 */

	public double timeRollSwapRate1D()
	{
		return _dbl1DTimeRollSwapRate;
	}

	/**
	 * Retrieve the 1D Maturity Roll Down Swap Rate
	 * 
	 * @return The 1D Maturity Roll Down Swap Rate
	 */

	public double maturityRollDownSwapRate1D()
	{
		return _dbl1DMaturityRollDownSwapRate;
	}

	/**
	 * Retrieve the 1M Maturity Roll Down Swap Rate
	 * 
	 * @return The 1M Maturity Roll Down Swap Rate
	 */

	public double maturityRollDownSwapRate1M()
	{
		return _dbl1MMaturityRollDownSwapRate;
	}

	/**
	 * Retrieve the 3M Maturity Roll Down Swap Rate
	 * 
	 * @return The 3M Maturity Roll Down Swap Rate
	 */

	public double maturityRollDownSwapRate3M()
	{
		return _dbl3MMaturityRollDownSwapRate;
	}

	/**
	 * Retrieve the 1D Maturity Roll Up Swap Rate
	 * 
	 * @return The 1D Maturity Roll Up Swap Rate
	 */

	public double maturityRollUpSwapRate1D()
	{
		return _dbl1DMaturityRollUpSwapRate;
	}

	/**
	 * Retrieve the 1D Maturity Roll Up Fair Premium
	 * 
	 * @return The 1D Maturity Roll Up Fair Premium
	 */

	public double maturityRollUpFairPremium1D()
	{
		return _dbl1DMaturityRollUpFairPremium;
	}

	/**
	 * Retrieve the 1D Maturity Roll Up Fair Premium With Fixing
	 * 
	 * @return The 1D Maturity Roll Up Fair Premium With Fixing
	 */

	public double maturityRollUpFairPremiumWithFixing1D()
	{
		return _dbl1DMaturityRollUpFairPremiumWithFixing;
	}

	/**
	 * Retrieve the 1D Curve Shift Swap Rate
	 * 
	 * @return The 1D Curve Shift Swap Rate
	 */

	public double curveShiftSwapRate1D()
	{
		return _dbl1DCurveShiftSwapRate;
	}

	/**
	 * Retrieve the Period Fixed Rate
	 * 
	 * @return The Period Fixed Rate
	 */

	public double periodFixedRate()
	{
		return _dblPeriodFixedRate;
	}

	/**
	 * Retrieve the Period Curve Floating Rate
	 * 
	 * @return The Period Curve Floating Rate
	 */

	public double periodCurveFloatingRate()
	{
		return _dblPeriodCurveFloatingRate;
	}

	/**
	 * Retrieve the Period Product Floating Rate
	 * 
	 * @return The Period Product Floating Rate
	 */

	public double periodProductFloatingRate()
	{
		return _dblPeriodProductFloatingRate;
	}

	/**
	 * Retrieve the Period Floating Rate Used
	 * 
	 * @return The Period Floating Rate Used
	 */

	public double periodFloatingRateUsed()
	{
		return _dblPeriodFloatingRateUsed;
	}

	/**
	 * Retrieve the 1D Fixed Accrual Period
	 * 
	 * @return The 1D Fixed Accrual Period
	 */

	public int fixed1DAccrualDays()
	{
		return _i1DFixedAccrualDays;
	}

	/**
	 * Retrieve the 1D Floating Accrual Period
	 * 
	 * @return The 1D Floating Accrual Period
	 */

	public int floating1DAccrualDays()
	{
		return _i1DFloatingAccrualDays;
	}

	/**
	 * Retrieve the Period 1D Fixed DCF
	 * 
	 * @return The Period 1D Fixed DCF
	 */

	public double fixed1DDCF()
	{
		return _dbl1DFixedDCF;
	}

	/**
	 * Retrieve the Period 1D Floating DCF
	 * 
	 * @return The Period 1D Floating DCF
	 */

	public double floating1DDCF()
	{
		return _dbl1DFloatingDCF;
	}

	/**
	 * Retrieve the Period 1M Fixed DCF
	 * 
	 * @return The Period 1M Fixed DCF
	 */

	public double fixed1MDCF()
	{
		return _dbl1MFixedDCF;
	}

	/**
	 * Retrieve the Period 1M Floating DCF
	 * 
	 * @return The Period 1M Floating DCF
	 */

	public double floating1MDCF()
	{
		return _dbl1MFloatingDCF;
	}

	/**
	 * Retrieve the Period 3M Fixed DCF
	 * 
	 * @return The Period 3M Fixed DCF
	 */

	public double fixed3MDCF()
	{
		return _dbl3MFixedDCF;
	}

	/**
	 * Retrieve the Period 3M Floating DCF
	 * 
	 * @return The Period 3M Floating DCF
	 */

	public double floating3MDCF()
	{
		return _dbl3MFloatingDCF;
	}

	/**
	 * Retrieve the Array of Metrics
	 * 
	 * @return The Array of Metrics
	 */

	public double[] toArray()
	{
		java.util.List<java.lang.Double> lsPnLMetric = new java.util.ArrayList<java.lang.Double>();

		lsPnLMetric.add (_dbl1DTotalPnL);

		lsPnLMetric.add (_dbl1DCleanPnL);

		lsPnLMetric.add (_dbl1DDirtyPnL);

		lsPnLMetric.add (_dbl1DTotalPnLWithFixing);

		lsPnLMetric.add (_dbl1DCleanPnLWithFixing);

		lsPnLMetric.add (_dbl1DDirtyPnLWithFixing);

		lsPnLMetric.add (_dbl1DCarryPnL);

		lsPnLMetric.add (_dbl1DTimeRollPnL);

		lsPnLMetric.add (_dbl1DMaturityRollDownSwapRatePnL);

		lsPnLMetric.add (_dbl1DMaturityRollUpSwapRatePnL);

		lsPnLMetric.add (_dbl1DMaturityRollUpFairPremiumPnL);

		lsPnLMetric.add (_dbl1DMaturityRollUpFairPremiumWithFixingPnL);

		lsPnLMetric.add (_dbl1DCurveShiftPnL);

		lsPnLMetric.add (_dbl1MCarryPnL);

		lsPnLMetric.add (_dbl1MMaturityRollDownSwapRatePnL);

		lsPnLMetric.add (_dbl3MCarryPnL);

		lsPnLMetric.add (_dbl3MMaturityRollDownSwapRatePnL);

		lsPnLMetric.add (_dblDV01);

		lsPnLMetric.add (_dblDV01WithFixing);

		lsPnLMetric.add (_dblCleanFixedDV01);

		lsPnLMetric.add (_dblCleanFloatDV01);

		lsPnLMetric.add (_dblCleanFloatDV01WithFixing);

		lsPnLMetric.add (_dblBaselineSwapRate);

		lsPnLMetric.add (_dbl1DTimeRollSwapRate);

		lsPnLMetric.add (_dbl1DMaturityRollDownSwapRate);

		lsPnLMetric.add (_dbl1MMaturityRollDownSwapRate);

		lsPnLMetric.add (_dbl3MMaturityRollDownSwapRate);

		lsPnLMetric.add (_dbl1DMaturityRollUpSwapRate);

		lsPnLMetric.add (_dbl1DMaturityRollUpFairPremium);

		lsPnLMetric.add (_dbl1DMaturityRollUpFairPremiumWithFixing);

		lsPnLMetric.add (_dbl1DCurveShiftSwapRate);

		lsPnLMetric.add (_dblPeriodFixedRate);

		lsPnLMetric.add (_dblPeriodCurveFloatingRate);

		lsPnLMetric.add (_dblPeriodProductFloatingRate);

		lsPnLMetric.add (_dblPeriodFloatingRateUsed);

		lsPnLMetric.add ((double) _i1DFixedAccrualDays);

		lsPnLMetric.add ((double) _i1DFloatingAccrualDays);

		lsPnLMetric.add (_dbl1DFixedDCF);

		lsPnLMetric.add (_dbl1DFloatingDCF);

		lsPnLMetric.add (_dbl1MFixedDCF);

		lsPnLMetric.add (_dbl1MFloatingDCF);

		lsPnLMetric.add (_dbl3MFixedDCF);

		lsPnLMetric.add (_dbl3MFloatingDCF);

		int i = 0;

		double[] adblSPCA = new double[lsPnLMetric.size()];

		for (double dbl : lsPnLMetric)
			adblSPCA[i++] = dbl;

		return adblSPCA;
	}

	@Override public java.lang.String toString()
	{
		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		boolean bStart = true;

		for (double dbl : toArray()) {
			if (bStart)
				bStart = false;
			else
				sb.append (",");

			sb.append (dbl);
		}

		return sb.toString();
	}
}
