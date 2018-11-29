
package org.drip.market.otc;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>SwapOptionSettlement</i> contains the details of the OTC Swap Option Settlements.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Market</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/otc">OTC</a></li>
 *  </ul>
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
