
package org.drip.service.api;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>ProductDailyPnL</i> contains the following daily measures computed:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 * 			1D Carry, Roll Down, Curve Shift, and Full Return PnL
 *  	</li>
 *  	<li>
 * 			3D Carry and Roll Down PnL
 *  	</li>
 *  	<li>
 * 			3M Carry and Roll Down PnL
 *  	</li>
 *  	<li>
 * 			Current DV01
 *  	</li>
 *  </ul>
 *  It provides the following Functions:
 * 	<ul>
 * 
 * 	</ul>
 *
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/api/README.md">Horizon Roll Attribution Service API</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ProductDailyPnL
{
	private double _dv01 = Double.NaN;
	private int _oneDayFixedAccrualDays = 0;
	private int _oneDayFloatingAccrualDays = 0;
	private double _oneDayCarryPnL = Double.NaN;
	private double _oneDayCleanPnL = Double.NaN;
	private double _oneDayDirtyPnL = Double.NaN;
	private double _oneDayTotalPnL = Double.NaN;
	private double _oneMonthCarryPnL = Double.NaN;
	private double _oneDayTimeRollPnL = Double.NaN;
	private double _threeMonthCarryPnL = Double.NaN;
	private double _oneDayFixedDayCountFraction = Double.NaN;
	private double _oneMonthFixedDayCountFraction = Double.NaN;
	private double _oneDayFloatingDayCountFraction = Double.NaN;
	private double _threeMonthFixedDayCountFraction = Double.NaN;
	private double _oneMonthFloatingDayCountFraction = Double.NaN;
	private double _threeMonthFloatingDayCountFraction = Double.NaN;

	private double _cleanFixedDV01 = Double.NaN;
	private double _cleanFloatDV01 = Double.NaN;
	private double _dV01WithFixing = Double.NaN;
	private double _periodFixedRate = Double.NaN;
	private double _baselineSwapRate = Double.NaN;
	private double _oneDayCurveShiftPnL = Double.NaN;
	private double _oneDayTimeRollSwapRate = Double.NaN;
	private double _oneDayCleanPnLWithFixing = Double.NaN;
	private double _oneDayDirtyPnLWithFixing = Double.NaN;

	private double _dbl1DTotalPnLWithFixing = Double.NaN;
	private double _dbl1DCurveShiftSwapRate = Double.NaN;
	private double _dblPeriodFloatingRateUsed = Double.NaN;
	private double _dblPeriodCurveFloatingRate = Double.NaN;
	private double _dbl1DMaturityRollUpSwapRate = Double.NaN;
	private double _dblCleanFloatDV01WithFixing = Double.NaN;
	private double _dblPeriodProductFloatingRate = Double.NaN;
	private double _dbl1DMaturityRollDownSwapRate = Double.NaN;
	private double _dbl1MMaturityRollDownSwapRate = Double.NaN;
	private double _dbl3MMaturityRollDownSwapRate = Double.NaN;
	private double _dbl1DMaturityRollUpSwapRatePnL = Double.NaN;
	private double _dbl1DMaturityRollUpFairPremium = Double.NaN;
	private double _dbl1DMaturityRollDownSwapRatePnL = Double.NaN;
	private double _dbl1MMaturityRollDownSwapRatePnL = Double.NaN;
	private double _dbl3MMaturityRollDownSwapRatePnL = Double.NaN;
	private double _dbl1DMaturityRollUpFairPremiumPnL = Double.NaN;
	private double _dbl1DMaturityRollUpFairPremiumWithFixing = Double.NaN;
	private double _dbl1DMaturityRollUpFairPremiumWithFixingPnL = Double.NaN;

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
	 * @throws Exception Thrown if inputs are invalid
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
		throws Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_oneDayTotalPnL = dbl1DTotalPnL) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_oneDayCleanPnL = dbl1DCleanPnL) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_oneDayDirtyPnL = dbl1DDirtyPnL) ||
					!org.drip.numerical.common.NumberUtil.IsValid (_dbl1DTotalPnLWithFixing =
						dbl1DTotalPnLWithFixing) || !org.drip.numerical.common.NumberUtil.IsValid
							(_oneDayCleanPnLWithFixing = dbl1DCleanPnLWithFixing) ||
								!org.drip.numerical.common.NumberUtil.IsValid (_dbl1DDirtyPnLWithFixing =
									dbl1DDirtyPnLWithFixing) || !org.drip.numerical.common.NumberUtil.IsValid
										(_oneDayCarryPnL = dbl1DCarryPnL) ||
											!org.drip.numerical.common.NumberUtil.IsValid (_oneDayTimeRollPnL =
												dbl1DTimeRollPnL) ||
													!org.drip.numerical.common.NumberUtil.IsValid
														(_dbl1DMaturityRollDownSwapRatePnL =
															dbl1DMaturityRollDownSwapRatePnL) ||
																!org.drip.numerical.common.NumberUtil.IsValid
																	(_dbl1DMaturityRollUpSwapRatePnL =
																		dbl1DMaturityRollUpSwapRatePnL) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dbl1DMaturityRollUpFairPremiumPnL =
				dbl1DMaturityRollUpFairPremiumPnL) || !org.drip.numerical.common.NumberUtil.IsValid
					(_dbl1DMaturityRollUpFairPremiumWithFixingPnL =
						dbl1DMaturityRollUpFairPremiumWithFixingPnL) ||
							!org.drip.numerical.common.NumberUtil.IsValid (_oneDayCurveShiftPnL =
								dbl1DCurveShiftPnL) || !org.drip.numerical.common.NumberUtil.IsValid
									(_oneMonthCarryPnL = dbl1MCarryPnL) ||
										!org.drip.numerical.common.NumberUtil.IsValid
											(_dbl1MMaturityRollDownSwapRatePnL =
												dbl1MMaturityRollDownSwapRatePnL) ||
													!org.drip.numerical.common.NumberUtil.IsValid (_threeMonthCarryPnL
														= dbl3MCarryPnL) ||
															!org.drip.numerical.common.NumberUtil.IsValid
																(_dbl3MMaturityRollDownSwapRatePnL =
																	dbl3MMaturityRollDownSwapRatePnL) ||
																		!org.drip.numerical.common.NumberUtil.IsValid
			(_dv01 = dblDV01) || !org.drip.numerical.common.NumberUtil.IsValid (_dV01WithFixing =
				dblDV01WithFixing) || !org.drip.numerical.common.NumberUtil.IsValid (_cleanFixedDV01 =
					dblCleanFixedDV01) || !org.drip.numerical.common.NumberUtil.IsValid (_cleanFloatDV01 =
						dblCleanFloatDV01) || !org.drip.numerical.common.NumberUtil.IsValid
							(_dblCleanFloatDV01WithFixing = dblCleanFloatDV01WithFixing) ||
								!org.drip.numerical.common.NumberUtil.IsValid (_baselineSwapRate =
									dblBaselineSwapRate) || !org.drip.numerical.common.NumberUtil.IsValid
										(_oneDayTimeRollSwapRate = dbl1DTimeRollSwapRate) ||
											!org.drip.numerical.common.NumberUtil.IsValid
												(_dbl1DMaturityRollDownSwapRate =
													dbl1DMaturityRollDownSwapRate) ||
														!org.drip.numerical.common.NumberUtil.IsValid
															(_dbl1MMaturityRollDownSwapRate =
																dbl1MMaturityRollDownSwapRate) ||
																	!org.drip.numerical.common.NumberUtil.IsValid
																		(_dbl3MMaturityRollDownSwapRate =
																			dbl3MMaturityRollDownSwapRate) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dbl1DMaturityRollUpSwapRate =
				dbl1DMaturityRollUpSwapRate) || !org.drip.numerical.common.NumberUtil.IsValid
					(_dbl1DMaturityRollUpFairPremium = dbl1DMaturityRollUpFairPremium) ||
						!org.drip.numerical.common.NumberUtil.IsValid (_dbl1DMaturityRollUpFairPremiumWithFixing
							= dbl1DMaturityRollUpFairPremiumWithFixing) ||
								!org.drip.numerical.common.NumberUtil.IsValid (_dbl1DCurveShiftSwapRate =
									dbl1DCurveShiftSwapRate) || !org.drip.numerical.common.NumberUtil.IsValid
										(_periodFixedRate = dblPeriodFixedRate) ||
											!org.drip.numerical.common.NumberUtil.IsValid
												(_dblPeriodCurveFloatingRate = dblPeriodCurveFloatingRate) ||
													!org.drip.numerical.common.NumberUtil.IsValid
														(_dblPeriodProductFloatingRate =
															dblPeriodProductFloatingRate) ||
																!org.drip.numerical.common.NumberUtil.IsValid
																	(_dblPeriodFloatingRateUsed =
																		dblPeriodFloatingRateUsed) ||
																			!org.drip.numerical.common.NumberUtil.IsValid
			(_oneDayFixedDayCountFraction = dbl1DFixedDCF) || !org.drip.numerical.common.NumberUtil.IsValid (_oneDayFloatingDayCountFraction
				= dbl1DFloatingDCF) || !org.drip.numerical.common.NumberUtil.IsValid (_oneMonthFixedDayCountFraction =
					dbl1MFixedDCF) || !org.drip.numerical.common.NumberUtil.IsValid (_oneMonthFloatingDayCountFraction =
						dbl1MFloatingDCF) || !org.drip.numerical.common.NumberUtil.IsValid (_threeMonthFixedDayCountFraction =
							dbl3MFixedDCF) || !org.drip.numerical.common.NumberUtil.IsValid (_threeMonthFloatingDayCountFraction =
								dbl3MFloatingDCF))
			throw new Exception ("ProductDailyPnL ctr: Invalid Inputs!");

		_oneDayFixedAccrualDays = i1DFixedAccrualDays;
		_oneDayFloatingAccrualDays = i1DFloatingAccrualDays;
	}

	/**
	 * Retrieve the 1D Clean PnL
	 * 
	 * @return The 1D Clean PnL
	 */

	public double clean1DPnL()
	{
		return _oneDayCleanPnL;
	}

	/**
	 * Retrieve the 1D Dirty PnL
	 * 
	 * @return The 1D Dirty PnL
	 */

	public double dirty1DPnL()
	{
		return _oneDayDirtyPnL;
	}

	/**
	 * Retrieve the 1D Total PnL
	 * 
	 * @return The 1D Total PnL
	 */

	public double total1DPnL()
	{
		return _oneDayTotalPnL;
	}

	/**
	 * Retrieve the 1D Clean PnL With Fixing
	 * 
	 * @return The 1D Clean PnL With Fixing
	 */

	public double clean1DPnLWithFixing()
	{
		return _oneDayCleanPnLWithFixing;
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
		return _oneDayCarryPnL;
	}

	/**
	 * Retrieve the 1D Time Roll PnL
	 * 
	 * @return The 1D Time Roll PnL
	 */

	public double timeRoll1DPnL()
	{
		return _oneDayTimeRollPnL;
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
		return _oneDayCurveShiftPnL;
	}

	/**
	 * Retrieve the 1M Carry PnL
	 * 
	 * @return The 1M Carry PnL
	 */

	public double carry1MPnL()
	{
		return _oneMonthCarryPnL;
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
		return _threeMonthCarryPnL;
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
		return _dv01;
	}

	/**
	 * Retrieve the DV01 With Fixing
	 * 
	 * @return The DV01 With Fixing
	 */

	public double DV01WithFixing()
	{
		return _dV01WithFixing;
	}

	/**
	 * Retrieve the Clean Fixed DV01
	 * 
	 * @return The Clean Fixed DV01
	 */

	public double cleanFixedDV01()
	{
		return _cleanFixedDV01;
	}

	/**
	 * Retrieve the Clean Float DV01
	 * 
	 * @return The Clean Float DV01
	 */

	public double cleanFloatDV01()
	{
		return _cleanFloatDV01;
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
		return _baselineSwapRate;
	}

	/**
	 * Retrieve the 1D Time Roll Swap Rate
	 * 
	 * @return The 1D Time Roll Swap Rate
	 */

	public double timeRollSwapRate1D()
	{
		return _oneDayTimeRollSwapRate;
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
		return _periodFixedRate;
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
		return _oneDayFixedAccrualDays;
	}

	/**
	 * Retrieve the 1D Floating Accrual Period
	 * 
	 * @return The 1D Floating Accrual Period
	 */

	public int floating1DAccrualDays()
	{
		return _oneDayFloatingAccrualDays;
	}

	/**
	 * Retrieve the Period 1D Fixed DCF
	 * 
	 * @return The Period 1D Fixed DCF
	 */

	public double fixed1DDCF()
	{
		return _oneDayFixedDayCountFraction;
	}

	/**
	 * Retrieve the Period 1D Floating DCF
	 * 
	 * @return The Period 1D Floating DCF
	 */

	public double floating1DDCF()
	{
		return _oneDayFloatingDayCountFraction;
	}

	/**
	 * Retrieve the Period 1M Fixed DCF
	 * 
	 * @return The Period 1M Fixed DCF
	 */

	public double fixed1MDCF()
	{
		return _oneMonthFixedDayCountFraction;
	}

	/**
	 * Retrieve the Period 1M Floating DCF
	 * 
	 * @return The Period 1M Floating DCF
	 */

	public double floating1MDCF()
	{
		return _oneMonthFloatingDayCountFraction;
	}

	/**
	 * Retrieve the Period 3M Fixed DCF
	 * 
	 * @return The Period 3M Fixed DCF
	 */

	public double fixed3MDCF()
	{
		return _threeMonthFixedDayCountFraction;
	}

	/**
	 * Retrieve the Period 3M Floating DCF
	 * 
	 * @return The Period 3M Floating DCF
	 */

	public double floating3MDCF()
	{
		return _threeMonthFloatingDayCountFraction;
	}

	/**
	 * Retrieve the Array of Metrics
	 * 
	 * @return The Array of Metrics
	 */

	public double[] toArray()
	{
		java.util.List<Double> lsPnLMetric = new java.util.ArrayList<Double>();

		lsPnLMetric.add (_oneDayTotalPnL);

		lsPnLMetric.add (_oneDayCleanPnL);

		lsPnLMetric.add (_oneDayDirtyPnL);

		lsPnLMetric.add (_dbl1DTotalPnLWithFixing);

		lsPnLMetric.add (_oneDayCleanPnLWithFixing);

		lsPnLMetric.add (_dbl1DDirtyPnLWithFixing);

		lsPnLMetric.add (_oneDayCarryPnL);

		lsPnLMetric.add (_oneDayTimeRollPnL);

		lsPnLMetric.add (_dbl1DMaturityRollDownSwapRatePnL);

		lsPnLMetric.add (_dbl1DMaturityRollUpSwapRatePnL);

		lsPnLMetric.add (_dbl1DMaturityRollUpFairPremiumPnL);

		lsPnLMetric.add (_dbl1DMaturityRollUpFairPremiumWithFixingPnL);

		lsPnLMetric.add (_oneDayCurveShiftPnL);

		lsPnLMetric.add (_oneMonthCarryPnL);

		lsPnLMetric.add (_dbl1MMaturityRollDownSwapRatePnL);

		lsPnLMetric.add (_threeMonthCarryPnL);

		lsPnLMetric.add (_dbl3MMaturityRollDownSwapRatePnL);

		lsPnLMetric.add (_dv01);

		lsPnLMetric.add (_dV01WithFixing);

		lsPnLMetric.add (_cleanFixedDV01);

		lsPnLMetric.add (_cleanFloatDV01);

		lsPnLMetric.add (_dblCleanFloatDV01WithFixing);

		lsPnLMetric.add (_baselineSwapRate);

		lsPnLMetric.add (_oneDayTimeRollSwapRate);

		lsPnLMetric.add (_dbl1DMaturityRollDownSwapRate);

		lsPnLMetric.add (_dbl1MMaturityRollDownSwapRate);

		lsPnLMetric.add (_dbl3MMaturityRollDownSwapRate);

		lsPnLMetric.add (_dbl1DMaturityRollUpSwapRate);

		lsPnLMetric.add (_dbl1DMaturityRollUpFairPremium);

		lsPnLMetric.add (_dbl1DMaturityRollUpFairPremiumWithFixing);

		lsPnLMetric.add (_dbl1DCurveShiftSwapRate);

		lsPnLMetric.add (_periodFixedRate);

		lsPnLMetric.add (_dblPeriodCurveFloatingRate);

		lsPnLMetric.add (_dblPeriodProductFloatingRate);

		lsPnLMetric.add (_dblPeriodFloatingRateUsed);

		lsPnLMetric.add ((double) _oneDayFixedAccrualDays);

		lsPnLMetric.add ((double) _oneDayFloatingAccrualDays);

		lsPnLMetric.add (_oneDayFixedDayCountFraction);

		lsPnLMetric.add (_oneDayFloatingDayCountFraction);

		lsPnLMetric.add (_oneMonthFixedDayCountFraction);

		lsPnLMetric.add (_oneMonthFloatingDayCountFraction);

		lsPnLMetric.add (_threeMonthFixedDayCountFraction);

		lsPnLMetric.add (_threeMonthFloatingDayCountFraction);

		int i = 0;

		double[] adblSPCA = new double[lsPnLMetric.size()];

		for (double dbl : lsPnLMetric)
			adblSPCA[i++] = dbl;

		return adblSPCA;
	}

	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();

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
