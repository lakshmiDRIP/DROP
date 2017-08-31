
package org.drip.market.exchange;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * TreasuryFuturesContractContainer holds the Details of some of the Common Treasury Futures Contracts.
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryFuturesContractContainer {
	private static final
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.market.exchange.TreasuryFuturesContract>
			_mapNameContract = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.market.exchange.TreasuryFuturesContract>();

	private static final
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.market.exchange.TreasuryFuturesContract>
			_mapCodeTenorContract = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.market.exchange.TreasuryFuturesContract>();

	private static final boolean AddContract (
		final java.lang.String[] astrName,
		final java.lang.String strID,
		final java.lang.String strCode,
		final java.lang.String strType,
		final java.lang.String strTenor)
	{
		try {
			org.drip.market.exchange.TreasuryFuturesContract tfc = new
				org.drip.market.exchange.TreasuryFuturesContract (strID, strCode, strType, strTenor);

			for (java.lang.String strName : astrName)
				_mapNameContract.put (strName, tfc);

			_mapCodeTenorContract.put (strCode + "::" + strTenor, tfc);

			return true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Initialize the Treasury Futures Contract Container with the Conventions
	 * 
	 * @return TRUE - The Treasury Futures Contracts Container successfully initialized with the Contracts
	 */

	public static final boolean Init()
	{
		/*
		 * Australian Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"YM1"}, "YM1", "AGB", "NOTE", "03Y")) return false;

		if (!AddContract (new java.lang.String[] {"XM1"}, "XM1", "AGB", "NOTE", "10Y")) return false;

		/*
		 * Canadian Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"CN1"}, "CN1", "CAN", "NOTE", "10Y")) return false;

		/*
		 * Danish Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"DGB"}, "DGB", "DGB", "NOTE", "10Y")) return false;

		/*
		 * French Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"OAT1"}, "OAT1", "FRTR", "NOTE", "10Y")) return false;

		/*
		 * German Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"DU1", "SCHATZ"}, "DU1", "DBR", "NOTE", "02Y"))
			return false;

		if (!AddContract (new java.lang.String[] {"OE1", "BOBL"}, "OE1", "DBR", "NOTE", "05Y")) return false;

		if (!AddContract (new java.lang.String[] {"RX1", "BUND"}, "RX1", "DBR", "NOTE", "10Y")) return false;

		if (!AddContract (new java.lang.String[] {"UB1", "BUXL"}, "UB1", "DBR", "NOTE", "30Y")) return false;

		/*
		 * Italian Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"IK1"}, "IK1", "BTPS", "NOTE", "10Y")) return false;

		/*
		 * Japanese Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"JB1"}, "JB1", "JGB", "NOTE", "10Y")) return false;

		/*
		 * Spanish Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"FBB1"}, "FBB1", "SPGB", "NOTE", "10Y")) return false;

		/*
		 * Swiss Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"GSWISS"}, "GSWISS", "GSWISS", "NOTE", "10Y"))
			return false;

		/*
		 * UK Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"WB1"}, "WB1", "GILT", "NOTE", "02Y")) return false;

		if (!AddContract (new java.lang.String[] {"G1"}, "G1", "GILT", "NOTE", "10Y")) return false;

		/*
		 * US Treasury Futures
		 */

		if (!AddContract (new java.lang.String[] {"TU1"}, "TU1", "UST", "NOTE", "02Y")) return false;

		if (!AddContract (new java.lang.String[] {"FV1"}, "FV1", "UST", "NOTE", "05Y")) return false;

		if (!AddContract (new java.lang.String[] {"TY1"}, "TY1", "UST", "NOTE", "10Y")) return false;

		if (!AddContract (new java.lang.String[] {"US1"}, "US1", "UST", "NOTE", "20Y")) return false;

		if (!AddContract (new java.lang.String[] {"WN1", "ULTRA"}, "WN1", "UST", "NOTE", "30Y"))
			return false;

		return true;
	}

	/**
	 * Retrieve the Treasury Futures Contract by Name
	 * 
	 * @param strTreasuryFuturesName The Treasury Futures Name
	 * 
	 * @return The Treasury Futures Contract
	 */

	public static final org.drip.market.exchange.TreasuryFuturesContract TreasuryFuturesContract (
		final java.lang.String strTreasuryFuturesName)
	{
		return !_mapNameContract.containsKey (strTreasuryFuturesName) ? null : _mapNameContract.get
			(strTreasuryFuturesName);
	}

	/**
	 * Retrieve the Treasury Futures Contract by Code and Tenor
	 * 
	 * @param strCode The Treasury Code
	 * @param strTenor The Futures Tenor
	 * 
	 * @return The Treasury Futures Contract
	 */

	public static final org.drip.market.exchange.TreasuryFuturesContract TreasuryFuturesContract (
		final java.lang.String strCode,
		final java.lang.String strTenor)
	{
		java.lang.String strCodeTenor = strCode + "::" + strTenor;

		return !_mapNameContract.containsKey (strCodeTenor) ? null : _mapNameContract.get (strCodeTenor);
	}
}
