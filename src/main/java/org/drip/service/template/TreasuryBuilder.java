
package org.drip.service.template;

import org.drip.analytics.date.JulianDate;
import org.drip.market.issue.TreasurySetting;
import org.drip.market.issue.TreasurySettingContainer;
import org.drip.product.creator.BondBuilder;
import org.drip.product.govvie.TreasuryComponent;

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
 * <i>TreasuryBuilder</i> contains Static Helper API to facilitate Construction of the Sovereign Treasury
 * 	Bonds. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Construct an Instance of the Australian Treasury AUD AGB Bond</li>
 * 		<li>Construct an Instance of the Italian Treasury EUR BTPS Bond</li>
 * 		<li>Construct an Instance of the Canadian Government CAD CAN Bond</li>
 * 		<li>Construct an Instance of the German Treasury EUR DBR Bond</li>
 * 		<li>Construct an Instance of the French Treasury EUR FRTR Bond</li>
 * 		<li>Construct an Instance of the Greek Treasury EUR GGB Bond</li>
 * 		<li>Construct an Instance of the UK Treasury GBP GILT Bond</li>
 * 		<li>Construct an Instance of the Japanese Treasury JPY JGB Bond</li>
 * 		<li>Construct an Instance of the Mexican Treasury MXN MBONO Bond</li>
 * 		<li>Construct an Instance of the Spanish Treasury EUR SPGB Bond</li>
 * 		<li>Construct an Instance of the US Treasury USD UST Bond</li>
 * 		<li>Construct an Instance of the Treasury Bond From the Code</li>
 * 		<li>Construct an Array of the Treasury Instances from the Code</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/template/README.md">Curve Construction Product Builder Templates</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryBuilder
{

	/**
	 * Construct an Instance of the Australian Treasury AUD AGB Bond
	 * 
	 * @param effectiveDate Effective Date
	 * @param maturityDate Maturity Date
	 * @param coupon Coupon
	 * 
	 * @return Instance of the Australian Treasury AUD AGB Bond
	 */

	public static final TreasuryComponent AGB (
		final JulianDate effectiveDate,
		final JulianDate maturityDate,
		final double coupon)
	{
		TreasurySetting treasurySetting = TreasurySettingContainer.TreasurySetting ("AGB");

		return null == treasurySetting ? null : BondBuilder.Treasury (
			treasurySetting.code(),
			effectiveDate,
			maturityDate,
			treasurySetting.currency(),
			coupon,
			treasurySetting.frequency(),
			treasurySetting.dayCount()
		);
	}

	/**
	 * Construct an Instance of the Italian Treasury EUR BTPS Bond
	 * 
	 * @param effectiveDate Effective Date
	 * @param maturityDate Maturity Date
	 * @param coupon Coupon
	 * 
	 * @return Instance of the Italian Treasury EUR BTPS Bond
	 */

	public static final TreasuryComponent BTPS (
		final JulianDate effectiveDate,
		final JulianDate maturityDate,
		final double coupon)
	{
		TreasurySetting treasurySetting = TreasurySettingContainer.TreasurySetting ("BTPS");

		return null == treasurySetting ? null : BondBuilder.Treasury (
			treasurySetting.code(),
			effectiveDate,
			maturityDate,
			treasurySetting.currency(),
			coupon,
			treasurySetting.frequency(),
			treasurySetting.dayCount()
		);
	}

	/**
	 * Construct an Instance of the Canadian Government CAD CAN Bond
	 * 
	 * @param effectiveDate Effective Date
	 * @param maturityDate Maturity Date
	 * @param coupon Coupon
	 * 
	 * @return Instance of the Canadian Government CAD CAN Bond
	 */

	public static final TreasuryComponent CAN (
		final JulianDate effectiveDate,
		final JulianDate maturityDate,
		final double coupon)
	{
		TreasurySetting treasurySetting = TreasurySettingContainer.TreasurySetting ("CAN");

		return null == treasurySetting ? null : BondBuilder.Treasury (
			treasurySetting.code(),
			effectiveDate,
			maturityDate,
			treasurySetting.currency(),
			coupon,
			treasurySetting.frequency(),
			treasurySetting.dayCount()
		);
	}

	/**
	 * Construct an Instance of the German Treasury EUR DBR Bond
	 * 
	 * @param effectiveDate Effective Date
	 * @param maturityDate Maturity Date
	 * @param coupon Coupon
	 * 
	 * @return Instance of the German Treasury EUR DBR Bond
	 */

	public static final TreasuryComponent DBR (
		final JulianDate effectiveDate,
		final JulianDate maturityDate,
		final double coupon)
	{
		TreasurySetting treasurySetting = TreasurySettingContainer.TreasurySetting ("DBR");

		return null == treasurySetting ? null : BondBuilder.Treasury (
			treasurySetting.code(),
			effectiveDate,
			maturityDate,
			treasurySetting.currency(),
			coupon,
			treasurySetting.frequency(),
			treasurySetting.dayCount()
		);
	}

	/**
	 * Construct an Instance of the French Treasury EUR FRTR Bond
	 * 
	 * @param effectiveDate Effective Date
	 * @param maturityDate Maturity Date
	 * @param coupon Coupon
	 * 
	 * @return Instance of the French Treasury EUR FRTR Bond
	 */

	public static final TreasuryComponent FRTR (
		final JulianDate effectiveDate,
		final JulianDate maturityDate,
		final double coupon)
	{
		TreasurySetting treasurySetting = TreasurySettingContainer.TreasurySetting ("FRTR");

		return null == treasurySetting ? null : BondBuilder.Treasury (
			treasurySetting.code(),
			effectiveDate,
			maturityDate,
			treasurySetting.currency(),
			coupon,
			treasurySetting.frequency(),
			treasurySetting.dayCount()
		);
	}

	/**
	 * Construct an Instance of the Greek Treasury EUR GGB Bond
	 * 
	 * @param effectiveDate Effective Date
	 * @param maturityDate Maturity Date
	 * @param coupon Coupon
	 * 
	 * @return Instance of the Greek Treasury EUR GGB Bond
	 */

	public static final TreasuryComponent GGB (
		final JulianDate effectiveDate,
		final JulianDate maturityDate,
		final double coupon)
	{
		TreasurySetting treasurySetting = TreasurySettingContainer.TreasurySetting ("GGB");

		return null == treasurySetting ? null : BondBuilder.Treasury (
			treasurySetting.code(),
			effectiveDate,
			maturityDate,
			treasurySetting.currency(),
			coupon,
			treasurySetting.frequency(),
			treasurySetting.dayCount()
		);
	}

	/**
	 * Construct an Instance of the UK Treasury GBP GILT Bond
	 * 
	 * @param effectiveDate Effective Date
	 * @param maturityDate Maturity Date
	 * @param coupon Coupon
	 * 
	 * @return Instance of the UK Treasury GBP GILT Bond
	 */

	public static final TreasuryComponent GILT (
		final JulianDate effectiveDate,
		final JulianDate maturityDate,
		final double coupon)
	{
		TreasurySetting treasurySetting = TreasurySettingContainer.TreasurySetting ("GILT");

		return null == treasurySetting ? null : BondBuilder.Treasury (
			treasurySetting.code(),
			effectiveDate,
			maturityDate,
			treasurySetting.currency(),
			coupon,
			treasurySetting.frequency(),
			treasurySetting.dayCount()
		);
	}

	/**
	 * Construct an Instance of the Japanese Treasury JPY JGB Bond
	 * 
	 * @param effectiveDate Effective Date
	 * @param maturityDate Maturity Date
	 * @param coupon Coupon
	 * 
	 * @return Instance of the Japanese Treasury JPY JGB Bond
	 */

	public static final TreasuryComponent JGB (
		final JulianDate effectiveDate,
		final JulianDate maturityDate,
		final double coupon)
	{
		TreasurySetting treasurySetting = TreasurySettingContainer.TreasurySetting ("JGB");

		return null == treasurySetting ? null : BondBuilder.Treasury (
			treasurySetting.code(),
			effectiveDate,
			maturityDate,
			treasurySetting.currency(),
			coupon,
			treasurySetting.frequency(),
			treasurySetting.dayCount()
		);
	}

	/**
	 * Construct an Instance of the Mexican Treasury MXN MBONO Bond
	 * 
	 * @param effectiveDate Effective Date
	 * @param maturityDate Maturity Date
	 * @param coupon Coupon
	 * 
	 * @return Instance of the Mexican Treasury MXN MBONO Bond
	 */

	public static final TreasuryComponent MBONO (
		final JulianDate effectiveDate,
		final JulianDate maturityDate,
		final double coupon)
	{
		TreasurySetting treasurySetting = TreasurySettingContainer.TreasurySetting ("MBONO");

		return null == treasurySetting ? null : BondBuilder.Treasury (
			treasurySetting.code(),
			effectiveDate,
			maturityDate,
			treasurySetting.currency(),
			coupon,
			treasurySetting.frequency(),
			treasurySetting.dayCount()
		);
	}

	/**
	 * Construct an Instance of the Spanish Treasury EUR SPGB Bond
	 * 
	 * @param effectiveDate Effective Date
	 * @param maturityDate Maturity Date
	 * @param coupon Coupon
	 * 
	 * @return Instance of the Spanish Treasury EUR SPGB Bond
	 */

	public static final TreasuryComponent SPGB (
		final JulianDate effectiveDate,
		final JulianDate maturityDate,
		final double coupon)
	{
		TreasurySetting treasurySetting = TreasurySettingContainer.TreasurySetting ("SPGB");

		return null == treasurySetting ? null : BondBuilder.Treasury (
			treasurySetting.code(),
			effectiveDate,
			maturityDate,
			treasurySetting.currency(),
			coupon,
			treasurySetting.frequency(),
			treasurySetting.dayCount()
		);
	}

	/**
	 * Construct an Instance of the US Treasury USD UST Bond
	 * 
	 * @param effectiveDate Effective Date
	 * @param maturityDate Maturity Date
	 * @param coupon Coupon
	 * 
	 * @return Instance of the US Treasury USD UST Bond
	 */

	public static final TreasuryComponent UST (
		final JulianDate effectiveDate,
		final JulianDate maturityDate,
		final double coupon)
	{
		TreasurySetting treasurySetting = TreasurySettingContainer.TreasurySetting ("UST");

		return null == treasurySetting ? null : BondBuilder.Treasury (
			treasurySetting.code(),
			effectiveDate,
			maturityDate,
			treasurySetting.currency(),
			coupon,
			treasurySetting.frequency(),
			treasurySetting.dayCount()
		);
	}

	/**
	 * Construct an Instance of the Treasury Bond From the Code
	 * 
	 * @param strCode The Treasury Code
	 * @param effectiveDate Effective Date
	 * @param maturityDate Maturity Date
	 * @param coupon Coupon
	 * 
	 * @return Instance of the Treasury Bond From the Code
	 */

	public static final TreasuryComponent FromCode (
		final java.lang.String strCode,
		final JulianDate effectiveDate,
		final JulianDate maturityDate,
		final double coupon)
	{
		TreasurySetting treasurySetting = TreasurySettingContainer.TreasurySetting (strCode);

		return null == treasurySetting ? null : BondBuilder.Treasury (
			treasurySetting.code(),
			effectiveDate,
			maturityDate,
			treasurySetting.currency(),
			coupon,
			treasurySetting.frequency(),
			treasurySetting.dayCount()
		);
	}

	/**
	 * Construct an Array of the Treasury Instances from the Code
	 * 
	 * @param treasuryCode The Treasury Code
	 * @param effectiveDateArray Array of Effective Dates
	 * @param maturityDateArray Array of Maturity Dates
	 * @param couponArray Array of Coupons
	 * 
	 * @return Array of the Treasury Instances from the Code
	 */

	public static final TreasuryComponent[] FromCode (
		final String treasuryCode,
		final JulianDate[] effectiveDateArray,
		final JulianDate[] maturityDateArray,
		final double[] couponArray)
	{
		if (null == effectiveDateArray || 0 == effectiveDateArray.length ||
			null == maturityDateArray ||
			null == couponArray)
		{
			return null;
		}

		TreasuryComponent[] treasuryComponentArray = new TreasuryComponent[effectiveDateArray.length];

		if (effectiveDateArray.length != maturityDateArray.length ||
			effectiveDateArray.length != couponArray.length)
		{
			return null;
		}

		for (int effectiveDateIndex = 0;
			effectiveDateIndex < effectiveDateArray.length;
			++effectiveDateIndex)
		{
			if (null == (
				treasuryComponentArray[effectiveDateIndex] = FromCode (
					treasuryCode,
					effectiveDateArray[effectiveDateIndex],
					maturityDateArray[effectiveDateIndex],
					couponArray[effectiveDateIndex]
				)
			))
			{
				return null;
			}
		}

		return treasuryComponentArray;
	}
}
