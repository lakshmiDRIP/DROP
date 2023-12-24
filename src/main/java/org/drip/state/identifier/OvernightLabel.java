
package org.drip.state.identifier;

import org.drip.market.definition.OvernightIndex;
import org.drip.market.definition.OvernightIndexContainer;
import org.drip.param.period.UnitCouponAccrualSetting;

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
 * <i>OvernightLabel</i> contains the Index Parameters referencing an Overnight Index. It provides the
 * 	functionality to Retrieve Index, Tenor, Currency, and Fully Qualified Name. It provides the following
 * 	functionality:
 *
 *  <ul>
 *  	<li>Construct an <i>OvernightLabel</i> from the Jurisdiction</li>
 *  	<li>Construct an <i>OvernightLabel</i> from the Index</li>
 *  	<li><i>OvernightLabel</i> Constructor</li>
 *  	<li>Retrieve the Currency</li>
 *  	<li>Retrieve the Family</li>
 *  	<li>Retrieve the Tenor</li>
 *  	<li>Indicate if the Index is an Overnight Index</li>
 *  	<li>Retrieve the Overnight Index</li>
 *  	<li>Retrieve the Unit Coupon Accrual Setting</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/README.md">Latent State Identifier Labels</a></td></tr>
 *  </table>
 *  
 * @author Lakshmi Krishnamurthy
 */

public class OvernightLabel extends ForwardLabel
{
	private OvernightIndex _overnightIndex = null;

	/**
	 * Construct an <i>OvernightLabel</i> from the Jurisdiction
	 * 
	 * @param currency The Currency
	 * 
	 * @return The <i>OvernightLabel</i> Instance
	 */

	public static final OvernightLabel Create (
		final String currency)
	{
		try {
			return new OvernightLabel (OvernightIndexContainer.IndexFromJurisdiction (currency));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an <i>OvernightLabel</i> from the Index
	 * 
	 * @param overnightIndex The Overnight Index Details
	 * 
	 * @return The <i>OvernightLabel</i> Instance
	 */

	public static final OvernightLabel Create (
		final OvernightIndex overnightIndex)
	{
		try {
			return new OvernightLabel (overnightIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>OvernightLabel</i> Constructor
	 * 
	 * @param overnightIndex The Overnight Index Details
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	private OvernightLabel (
		final OvernightIndex overnightIndex)
		throws Exception
	{
		super (overnightIndex, "ON");

		if (null == (_overnightIndex = overnightIndex)) {
			throw new Exception ("OvernightLabel ctr: Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Currency
	 * 
	 * @return The Currency
	 */

	public String currency()
	{
		return _overnightIndex.currency();
	}

	/**
	 * Retrieve the Family
	 * 
	 * @return The Family
	 */

	public String family()
	{
		return _overnightIndex.family();
	}

	/**
	 * Retrieve the Tenor
	 * 
	 * @return The Tenor
	 */

	public String tenor()
	{
		return "ON";
	}

	/**
	 * Indicate if the Index is an Overnight Index
	 * 
	 * @return TRUE - Overnight Index
	 */

	public boolean overnight()
	{
		return true;
	}

	/**
	 * Retrieve the Overnight Index
	 * 
	 * @return The Overnight Index
	 */

	public OvernightIndex overnightIndex()
	{
		return _overnightIndex;
	}

	/**
	 * Retrieve the Unit Coupon Accrual Setting
	 * 
	 * @return Unit Coupon Accrual Setting
	 */

	public UnitCouponAccrualSetting ucas()
	{
		String dayCount = _overnightIndex.dayCount();

		try {
			return new UnitCouponAccrualSetting (
				360,
				dayCount,
				false,
				dayCount,
				false,
				_overnightIndex.currency(),
				false,
				_overnightIndex.accrualCompoundingRule()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public String fullyQualifiedName()
	{
		return _overnightIndex.currency() + "-" + _overnightIndex.family() + "-ON";
	}

	@Override public boolean match (
		final LatentStateLabel latentStateLabelOther)
	{
		return null != latentStateLabelOther && latentStateLabelOther instanceof OvernightLabel &&
			fullyQualifiedName().equalsIgnoreCase (latentStateLabelOther.fullyQualifiedName());
	}
}
