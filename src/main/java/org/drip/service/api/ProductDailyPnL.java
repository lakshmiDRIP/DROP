
package org.drip.service.api;

import java.util.ArrayList;
import java.util.List;

import org.drip.numerical.common.NumberUtil;

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
 * <i>ProductDailyPnL</i> contains the daily measures computed. It provides the following Functions:
 * 	<ul>
 * 		<li><i>ProductDailyPnL</i> constructor</li>
 * 		<li>Retrieve the 1D Clean PnL</li>
 * 		<li>Retrieve the 1D Dirty PnL</li>
 * 		<li>Retrieve the 1D Total PnL</li>
 * 		<li>Retrieve the 1D Clean PnL With Fixing</li>
 * 		<li>Retrieve the 1D Dirty PnL With Fixing</li>
 * 		<li>Retrieve the 1D Total PnL With Fixing</li>
 * 		<li>Retrieve the 1D Carry PnL</li>
 * 		<li>Retrieve the 1D Time Roll PnL</li>
 * 		<li>Retrieve the 1D Maturity Roll Down Swap Rate PnL</li>
 * 		<li>Retrieve the 1D Maturity Roll Up Swap Rate PnL</li>
 * 		<li>Retrieve the 1D Maturity Roll Up Fair Premium PnL</li>
 * 		<li>Retrieve the 1D Maturity Roll Up Fair Premium With Fixing PnL</li>
 * 		<li>Retrieve the 1D Curve Shift PnL</li>
 * 		<li>Retrieve the 1M Carry PnL</li>
 * 		<li>Retrieve the 1M Maturity Roll Down Swap Rate PnL</li>
 * 		<li>Retrieve the 3M Carry PnL</li>
 * 		<li>Retrieve the 3M Maturity Roll Down Swap Rate PnL</li>
 * 		<li>Retrieve the DV01</li>
 * 		<li>Retrieve the DV01 With Fixing</li>
 * 		<li>Retrieve the Clean Fixed DV01</li>
 * 		<li>Retrieve the Clean Float DV01</li>
 * 		<li>Retrieve the Clean Float DV01 With Fixing</li>
 * 		<li>Retrieve the Baseline Swap Rate</li>
 * 		<li>Retrieve the 1D Time Roll Swap Rate</li>
 * 		<li>Retrieve the 1D Maturity Roll Down Swap Rate</li>
 * 		<li>Retrieve the 1M Maturity Roll Down Swap Rate</li>
 * 		<li>Retrieve the 3M Maturity Roll Down Swap Rate</li>
 * 		<li>Retrieve the 1D Maturity Roll Up Swap Rate</li>
 * 		<li>Retrieve the 1D Maturity Roll Up Fair Premium</li>
 * 		<li>Retrieve the 1D Maturity Roll Up Fair Premium With Fixing</li>
 * 		<li>Retrieve the 1D Curve Shift Swap Rate</li>
 * 		<li>Retrieve the Period Fixed Rate</li>
 * 		<li>Retrieve the Period Curve Floating Rate</li>
 * 		<li>Retrieve the Period Product Floating Rate</li>
 * 		<li>Retrieve the Period Floating Rate Used</li>
 * 		<li>Retrieve the 1D Fixed Accrual Period</li>
 * 		<li>Retrieve the 1D Floating Accrual Period</li>
 * 		<li>Retrieve the Period 1D Fixed DCF</li>
 * 		<li>Retrieve the Period 1D Floating DCF</li>
 * 		<li>Retrieve the Period 1M Fixed DCF</li>
 * 		<li>Retrieve the Period 1M Floating DCF</li>
 * 		<li>Retrieve the Period 3M Fixed DCF</li>
 * 		<li>Retrieve the Period 3M Floating DCF</li>
 * 		<li>Retrieve the Array of Metrics</li>
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
	private double _cleanFixedDV01 = Double.NaN;
	private double _cleanFloatDV01 = Double.NaN;
	private double _dV01WithFixing = Double.NaN;
	private double _oneDayCarryPnL = Double.NaN;
	private double _oneDayCleanPnL = Double.NaN;
	private double _oneDayDirtyPnL = Double.NaN;
	private double _oneDayTotalPnL = Double.NaN;
	private double _periodFixedRate = Double.NaN;
	private double _baselineSwapRate = Double.NaN;
	private double _oneMonthCarryPnL = Double.NaN;
	private double _oneDayTimeRollPnL = Double.NaN;
	private double _threeMonthCarryPnL = Double.NaN;
	private double _oneDayCurveShiftPnL = Double.NaN;
	private double _oneDayTimeRollSwapRate = Double.NaN;
	private double _periodFloatingRateUsed = Double.NaN;
	private double _periodCurveFloatingRate = Double.NaN;
	private double _cleanFloatDV01WithFixing = Double.NaN;
	private double _oneDayCleanPnLWithFixing = Double.NaN;
	private double _oneDayCurveShiftSwapRate = Double.NaN;
	private double _oneDayDirtyPnLWithFixing = Double.NaN;
	private double _oneDayTotalPnLWithFixing = Double.NaN;
	private double _periodProductFloatingRate = Double.NaN;
	private double _oneDayFixedDayCountFraction = Double.NaN;
	private double _oneDayMaturityRollUpSwapRate = Double.NaN;
	private double _oneMonthFixedDayCountFraction = Double.NaN;
	private double _oneDayFloatingDayCountFraction = Double.NaN;
	private double _oneDayMaturityRollDownSwapRate = Double.NaN;
	private double _oneDayMaturityRollUpFairPremium = Double.NaN;
	private double _oneDayMaturityRollUpSwapRatePnL = Double.NaN;
	private double _threeMonthFixedDayCountFraction = Double.NaN;
	private double _oneMonthFloatingDayCountFraction = Double.NaN;
	private double _oneMonthMaturityRollDownSwapRate = Double.NaN;
	private double _oneDayMaturityRollDownSwapRatePnL = Double.NaN;
	private double _oneDayMaturityRollUpFairPremiumPnL = Double.NaN;
	private double _threeMonthFloatingDayCountFraction = Double.NaN;
	private double _threeMonthMaturityRollDownSwapRate = Double.NaN;
	private double _oneMonthMaturityRollDownSwapRatePnL = Double.NaN;
	private double _threeMonthMaturityRollDownSwapRatePnL = Double.NaN;
	private double _oneDayMaturityRollUpFairPremiumWithFixing = Double.NaN;
	private double _oneDayMaturityRollUpFairPremiumWithFixingPnL = Double.NaN;

	/**
	 * <i>ProductDailyPnL</i> constructor
	 * 
	 * @param oneDayTotalPnL 1D Total PnL
	 * @param oneDayCleanPnL 1D Clean PnL
	 * @param oneDayDirtyPnL 1D Dirty PnL
	 * @param oneDayTotalPnLWithFixing 1D Total PnL With Fixing
	 * @param oneDayCleanPnLWithFixing 1D Clean PnL With Fixing
	 * @param oneDayDirtyPnLWithFixing 1D Dirty PnL With Fixing
	 * @param oneDayCarryPnL 1D Carry PnL
	 * @param oneDayTimeRollPnL 1D Time Roll PnL
	 * @param oneDayMaturityRollDownSwapRatePnL 1D Curve Maturity Roll Down implied Par Swap rate PnL
	 * @param oneDayMaturityRollUpSwapRatePnL 1D Curve Maturity Roll Up implied Par Swap rate PnL
	 * @param oneDayMaturityRollUpFairPremiumPnL 1D Curve Maturity Roll Up implied Fair Premium PnL
	 * @param oneDayMaturityRollUpFairPremiumWithFixingPnL 1D Curve Maturity Roll Up implied Fair Premium
	 * 	with Fixing PnL
	 * @param oneDayCurveShiftPnL 1D Curve Shift PnL
	 * @param oneMonthCarryPnL 1M Carry PnL
	 * @param oneMonthMaturityRollDownSwapRatePnL 1M Curve Maturity Roll Down implied Par Swap rate PnL
	 * @param threeMonthCarryPnL 3M Carry PnL
	 * @param threeMonthMaturityRollDownSwapRatePnL 3M Curve Maturity Roll Down implied Par Swap rate PnL
	 * @param dv01 DV01
	 * @param dv01WithFixing DV01 With Fixing
	 * @param cleanFixedDV01 Clean Fixed DV01
	 * @param cleanFloatDV01 Clean Float DV01
	 * @param cleanFloatDV01WithFixing Clean Float DV01 With Fixing
	 * @param baselineSwapRate Baseline Par Swap Rate
	 * @param oneDayTimeRollSwapRate 1D Curve Time Roll implied Par Swap rate
	 * @param oneDayMaturityRollDownSwapRate 1D Curve Maturity Roll Down Implied Par Swap rate
	 * @param oneMonthMaturityRollDownSwapRate 1M Curve Maturity Roll Down implied Par Swap rate
	 * @param threeMonthMaturityRollDownSwapRate 3M Curve Maturity Roll Down implied Par Swap rate
	 * @param oneDayMaturityRollUpSwapRate 1D Curve Maturity Roll Up Implied Par Swap rate
	 * @param oneDayMaturityRollUpFairPremium 1D Curve Maturity Roll Up Implied Fair Premium
	 * @param oneDayMaturityRollUpFairPremiumWithFixing 1D Curve Maturity Roll Up Implied Fair Premium With
	 * 	Fixing
	 * @param oneDayCurveShiftSwapRate 1D Day-to-Day Curve Shift implied Par Swap rate
	 * @param periodFixedRate The Period Fixed Rate
	 * @param periodCurveFloatingRate The Period Curve Floating Rate
	 * @param periodProductFloatingRate The Period Product Floating Rate
	 * @param periodFloatingRateUsed The Period Floating Rate Used
	 * @param oneDayFixedAccrualDays 1D Fixed Accrual Days
	 * @param oneDayFloatingAccrualDays 1D Floating Accrual Days
	 * @param oneDayFixedDCF 1D Fixed Coupon DCF
	 * @param oneDayFloatingDCF 1D Floating Coupon DCF
	 * @param oneMonthFixedDCF 1M Fixed Coupon DCF
	 * @param oneMonthFloatingDCF 1M Floating Coupon DCF
	 * @param threeMonthFixedDCF 3M Fixed Coupon DCF
	 * @param threeMonthFloatingDCF 3M Floating Coupon DCF
	 * 
	 * @throws Exception Thrown if inputs are invalid
	 */

	public ProductDailyPnL (
		final double oneDayTotalPnL,
		final double oneDayCleanPnL,
		final double oneDayDirtyPnL,
		final double oneDayTotalPnLWithFixing,
		final double oneDayCleanPnLWithFixing,
		final double oneDayDirtyPnLWithFixing,
		final double oneDayCarryPnL,
		final double oneDayTimeRollPnL,
		final double oneDayMaturityRollDownSwapRatePnL,
		final double oneDayMaturityRollUpSwapRatePnL,
		final double oneDayMaturityRollUpFairPremiumPnL,
		final double oneDayMaturityRollUpFairPremiumWithFixingPnL,
		final double oneDayCurveShiftPnL,
		final double oneMonthCarryPnL,
		final double oneMonthMaturityRollDownSwapRatePnL,
		final double threeMonthCarryPnL,
		final double threeMonthMaturityRollDownSwapRatePnL,
		final double dv01,
		final double dv01WithFixing,
		final double cleanFixedDV01,
		final double cleanFloatDV01,
		final double cleanFloatDV01WithFixing,
		final double baselineSwapRate,
		final double oneDayTimeRollSwapRate,
		final double oneDayMaturityRollDownSwapRate,
		final double oneMonthMaturityRollDownSwapRate,
		final double threeMonthMaturityRollDownSwapRate,
		final double oneDayMaturityRollUpSwapRate,
		final double oneDayMaturityRollUpFairPremium,
		final double oneDayMaturityRollUpFairPremiumWithFixing,
		final double oneDayCurveShiftSwapRate,
		final double periodFixedRate,
		final double periodCurveFloatingRate,
		final double periodProductFloatingRate,
		final double periodFloatingRateUsed,
		final int oneDayFixedAccrualDays,
		final int oneDayFloatingAccrualDays,
		final double oneDayFixedDCF,
		final double oneDayFloatingDCF,
		final double oneMonthFixedDCF,
		final double oneMonthFloatingDCF,
		final double threeMonthFixedDCF,
		final double threeMonthFloatingDCF)
		throws Exception
	{
		if (!NumberUtil.IsValid (_oneDayTotalPnL = oneDayTotalPnL) ||
			!NumberUtil.IsValid (_oneDayCleanPnL = oneDayCleanPnL) ||
			!NumberUtil.IsValid (_oneDayDirtyPnL = oneDayDirtyPnL) ||
			!NumberUtil.IsValid (_oneDayTotalPnLWithFixing = oneDayTotalPnLWithFixing) ||
			!NumberUtil.IsValid (_oneDayCleanPnLWithFixing = oneDayCleanPnLWithFixing) ||
			!NumberUtil.IsValid (_oneDayDirtyPnLWithFixing = oneDayDirtyPnLWithFixing) ||
			!NumberUtil.IsValid (_oneDayCarryPnL = oneDayCarryPnL) ||
			!NumberUtil.IsValid (_oneDayTimeRollPnL = oneDayTimeRollPnL) ||
			!NumberUtil.IsValid (_oneDayMaturityRollDownSwapRatePnL = oneDayMaturityRollDownSwapRatePnL) ||
			!NumberUtil.IsValid (_oneDayMaturityRollUpSwapRatePnL = oneDayMaturityRollUpSwapRatePnL) ||
			!NumberUtil.IsValid (_oneDayMaturityRollUpFairPremiumPnL =oneDayMaturityRollUpFairPremiumPnL) ||
			!NumberUtil.IsValid (
				_oneDayMaturityRollUpFairPremiumWithFixingPnL = oneDayMaturityRollUpFairPremiumWithFixingPnL
			) ||
			!NumberUtil.IsValid (_oneDayCurveShiftPnL = oneDayCurveShiftPnL) ||
			!NumberUtil.IsValid (_oneMonthCarryPnL = oneMonthCarryPnL) ||
			!NumberUtil.IsValid (
				_oneMonthMaturityRollDownSwapRatePnL = oneMonthMaturityRollDownSwapRatePnL
			) ||
			!NumberUtil.IsValid (_threeMonthCarryPnL = threeMonthCarryPnL) ||
			!NumberUtil.IsValid (
				_threeMonthMaturityRollDownSwapRatePnL = threeMonthMaturityRollDownSwapRatePnL
			) ||
			!NumberUtil.IsValid (_dv01 = dv01) ||
			!NumberUtil.IsValid (_dV01WithFixing = dv01WithFixing) ||
			!NumberUtil.IsValid (_cleanFixedDV01 = cleanFixedDV01) ||
			!NumberUtil.IsValid (_cleanFloatDV01 = cleanFloatDV01) ||
			!NumberUtil.IsValid (_cleanFloatDV01WithFixing = cleanFloatDV01WithFixing) ||
			!NumberUtil.IsValid (_baselineSwapRate = baselineSwapRate) ||
			!NumberUtil.IsValid (_oneDayTimeRollSwapRate = oneDayTimeRollSwapRate) ||
			!NumberUtil.IsValid (_oneDayMaturityRollDownSwapRate = oneDayMaturityRollDownSwapRate) ||
			!NumberUtil.IsValid (_oneMonthMaturityRollDownSwapRate = oneMonthMaturityRollDownSwapRate) ||
			!NumberUtil.IsValid (_threeMonthMaturityRollDownSwapRate = threeMonthMaturityRollDownSwapRate) ||
			!NumberUtil.IsValid (_oneDayMaturityRollUpSwapRate = oneDayMaturityRollUpSwapRate) ||
			!NumberUtil.IsValid (_oneDayMaturityRollUpFairPremium = oneDayMaturityRollUpFairPremium) ||
			!NumberUtil.IsValid (
				_oneDayMaturityRollUpFairPremiumWithFixing = oneDayMaturityRollUpFairPremiumWithFixing
			) ||
			!NumberUtil.IsValid (_oneDayCurveShiftSwapRate = oneDayCurveShiftSwapRate) ||
			!NumberUtil.IsValid (_periodFixedRate = periodFixedRate) ||
			!NumberUtil.IsValid (_periodCurveFloatingRate = periodCurveFloatingRate) ||
			!NumberUtil.IsValid (_periodProductFloatingRate = periodProductFloatingRate) ||
			!NumberUtil.IsValid (_periodFloatingRateUsed = periodFloatingRateUsed) ||
			!NumberUtil.IsValid (_oneDayFixedDayCountFraction = oneDayFixedDCF) ||
			!NumberUtil.IsValid (_oneDayFloatingDayCountFraction = oneDayFloatingDCF) ||
			!NumberUtil.IsValid (_oneMonthFixedDayCountFraction = oneMonthFixedDCF) ||
			!NumberUtil.IsValid (_oneMonthFloatingDayCountFraction = oneMonthFloatingDCF) ||
			!NumberUtil.IsValid (_threeMonthFixedDayCountFraction = threeMonthFixedDCF) ||
			!NumberUtil.IsValid (_threeMonthFloatingDayCountFraction = threeMonthFloatingDCF))
		{
			throw new Exception ("ProductDailyPnL ctr: Invalid Inputs!");
		}

		_oneDayFixedAccrualDays = oneDayFixedAccrualDays;
		_oneDayFloatingAccrualDays = oneDayFloatingAccrualDays;
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
		return _oneDayDirtyPnLWithFixing;
	}

	/**
	 * Retrieve the 1D Total PnL With Fixing
	 * 
	 * @return The 1D Total PnL With Fixing
	 */

	public double total1DPnLWithFixing()
	{
		return _oneDayTotalPnLWithFixing;
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
		return _oneDayMaturityRollDownSwapRatePnL;
	}

	/**
	 * Retrieve the 1D Maturity Roll Up Swap Rate PnL
	 * 
	 * @return The 1D Maturity Roll Up Swap Rate PnL
	 */

	public double maturityRollUpSwapRate1DPnL()
	{
		return _oneDayMaturityRollUpSwapRatePnL;
	}

	/**
	 * Retrieve the 1D Maturity Roll Up Fair Premium PnL
	 * 
	 * @return The 1D Maturity Roll Up Fair Premium PnL
	 */

	public double maturityRollUpFairPremium1DPnL()
	{
		return _oneDayMaturityRollUpFairPremiumPnL;
	}

	/**
	 * Retrieve the 1D Maturity Roll Up Fair Premium With Fixing PnL
	 * 
	 * @return The 1D Maturity Roll Up Fair Premium With Fixing PnL
	 */

	public double maturityRollUpFairPremiumWithFixing1DPnL()
	{
		return _oneDayMaturityRollUpFairPremiumWithFixingPnL;
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
		return _oneMonthMaturityRollDownSwapRatePnL;
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
		return _threeMonthMaturityRollDownSwapRatePnL;
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
		return _cleanFloatDV01WithFixing;
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
		return _oneDayMaturityRollDownSwapRate;
	}

	/**
	 * Retrieve the 1M Maturity Roll Down Swap Rate
	 * 
	 * @return The 1M Maturity Roll Down Swap Rate
	 */

	public double maturityRollDownSwapRate1M()
	{
		return _oneMonthMaturityRollDownSwapRate;
	}

	/**
	 * Retrieve the 3M Maturity Roll Down Swap Rate
	 * 
	 * @return The 3M Maturity Roll Down Swap Rate
	 */

	public double maturityRollDownSwapRate3M()
	{
		return _threeMonthMaturityRollDownSwapRate;
	}

	/**
	 * Retrieve the 1D Maturity Roll Up Swap Rate
	 * 
	 * @return The 1D Maturity Roll Up Swap Rate
	 */

	public double maturityRollUpSwapRate1D()
	{
		return _oneDayMaturityRollUpSwapRate;
	}

	/**
	 * Retrieve the 1D Maturity Roll Up Fair Premium
	 * 
	 * @return The 1D Maturity Roll Up Fair Premium
	 */

	public double maturityRollUpFairPremium1D()
	{
		return _oneDayMaturityRollUpFairPremium;
	}

	/**
	 * Retrieve the 1D Maturity Roll Up Fair Premium With Fixing
	 * 
	 * @return The 1D Maturity Roll Up Fair Premium With Fixing
	 */

	public double maturityRollUpFairPremiumWithFixing1D()
	{
		return _oneDayMaturityRollUpFairPremiumWithFixing;
	}

	/**
	 * Retrieve the 1D Curve Shift Swap Rate
	 * 
	 * @return The 1D Curve Shift Swap Rate
	 */

	public double curveShiftSwapRate1D()
	{
		return _oneDayCurveShiftSwapRate;
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
		return _periodCurveFloatingRate;
	}

	/**
	 * Retrieve the Period Product Floating Rate
	 * 
	 * @return The Period Product Floating Rate
	 */

	public double periodProductFloatingRate()
	{
		return _periodProductFloatingRate;
	}

	/**
	 * Retrieve the Period Floating Rate Used
	 * 
	 * @return The Period Floating Rate Used
	 */

	public double periodFloatingRateUsed()
	{
		return _periodFloatingRateUsed;
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
		List<Double> pnlMetricList = new ArrayList<Double>();

		pnlMetricList.add (_oneDayTotalPnL);

		pnlMetricList.add (_oneDayCleanPnL);

		pnlMetricList.add (_oneDayDirtyPnL);

		pnlMetricList.add (_oneDayTotalPnLWithFixing);

		pnlMetricList.add (_oneDayCleanPnLWithFixing);

		pnlMetricList.add (_oneDayDirtyPnLWithFixing);

		pnlMetricList.add (_oneDayCarryPnL);

		pnlMetricList.add (_oneDayTimeRollPnL);

		pnlMetricList.add (_oneDayMaturityRollDownSwapRatePnL);

		pnlMetricList.add (_oneDayMaturityRollUpSwapRatePnL);

		pnlMetricList.add (_oneDayMaturityRollUpFairPremiumPnL);

		pnlMetricList.add (_oneDayMaturityRollUpFairPremiumWithFixingPnL);

		pnlMetricList.add (_oneDayCurveShiftPnL);

		pnlMetricList.add (_oneMonthCarryPnL);

		pnlMetricList.add (_oneMonthMaturityRollDownSwapRatePnL);

		pnlMetricList.add (_threeMonthCarryPnL);

		pnlMetricList.add (_threeMonthMaturityRollDownSwapRatePnL);

		pnlMetricList.add (_dv01);

		pnlMetricList.add (_dV01WithFixing);

		pnlMetricList.add (_cleanFixedDV01);

		pnlMetricList.add (_cleanFloatDV01);

		pnlMetricList.add (_cleanFloatDV01WithFixing);

		pnlMetricList.add (_baselineSwapRate);

		pnlMetricList.add (_oneDayTimeRollSwapRate);

		pnlMetricList.add (_oneDayMaturityRollDownSwapRate);

		pnlMetricList.add (_oneMonthMaturityRollDownSwapRate);

		pnlMetricList.add (_threeMonthMaturityRollDownSwapRate);

		pnlMetricList.add (_oneDayMaturityRollUpSwapRate);

		pnlMetricList.add (_oneDayMaturityRollUpFairPremium);

		pnlMetricList.add (_oneDayMaturityRollUpFairPremiumWithFixing);

		pnlMetricList.add (_oneDayCurveShiftSwapRate);

		pnlMetricList.add (_periodFixedRate);

		pnlMetricList.add (_periodCurveFloatingRate);

		pnlMetricList.add (_periodProductFloatingRate);

		pnlMetricList.add (_periodFloatingRateUsed);

		pnlMetricList.add ((double) _oneDayFixedAccrualDays);

		pnlMetricList.add ((double) _oneDayFloatingAccrualDays);

		pnlMetricList.add (_oneDayFixedDayCountFraction);

		pnlMetricList.add (_oneDayFloatingDayCountFraction);

		pnlMetricList.add (_oneMonthFixedDayCountFraction);

		pnlMetricList.add (_oneMonthFloatingDayCountFraction);

		pnlMetricList.add (_threeMonthFixedDayCountFraction);

		pnlMetricList.add (_threeMonthFloatingDayCountFraction);

		int i = 0;

		double[] pnlMetricArray = new double[pnlMetricList.size()];

		for (double pnlMetric : pnlMetricList) {
			pnlMetricArray[i++] = pnlMetric;
		}

		return pnlMetricArray;
	}

	@Override public String toString()
	{
		StringBuffer stringBuffer = new StringBuffer();

		boolean firstMetric = true;

		for (double metric : toArray()) {
			if (firstMetric) {
				firstMetric = false;
			} else {
				stringBuffer.append (",");
			}

			stringBuffer.append (metric);
		}

		return stringBuffer.toString();
	}
}
