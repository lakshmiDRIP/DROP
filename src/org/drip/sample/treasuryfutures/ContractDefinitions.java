
package org.drip.sample.treasuryfutures;

import org.drip.market.exchange.*;
import org.drip.service.env.EnvManager;

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
 * ContractDefinitions contains all the pre-fixed Definitions of Exchange-traded Treasury Futures Contracts.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ContractDefinitions {

	private static final void DisplayContractDefinition (
		final String strFuturesCode)
		throws Exception
	{
		TreasuryFuturesContract tfc = TreasuryFuturesContractContainer.TreasuryFuturesContract (strFuturesCode);

		System.out.println (
			"\t| " + strFuturesCode
			+ " | " + tfc.id()
			+ " | " + tfc.code()
			+ " | " + tfc.tenor()
			+ " | " + tfc.type() + " ||"
		);
	}

	public static final void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		System.out.println ("\n\t|------------------------------||");

		System.out.println ("\t|   TREASURY FUTURES CONTRACT  ||");

		System.out.println ("\t|   -------- ------- --------  ||");

		System.out.println ("\t|                              ||");

		System.out.println ("\t|   L -> R:                    ||");

		System.out.println ("\t|                              ||");

		System.out.println ("\t|          Futures Code        ||");

		System.out.println ("\t|          Futures ID          ||");

		System.out.println ("\t|          Treasury Code       ||");

		System.out.println ("\t|          Futures Tenor       ||");

		System.out.println ("\t|          Treasury Type       ||");

		System.out.println ("\t|                              ||");

		System.out.println ("\t|------------------------------||");

		DisplayContractDefinition ("G1");

		DisplayContractDefinition ("CN1");

		DisplayContractDefinition ("DGB");

		DisplayContractDefinition ("DU1");

		DisplayContractDefinition ("FV1");

		DisplayContractDefinition ("IK1");

		DisplayContractDefinition ("JB1");

		DisplayContractDefinition ("OE1");

		DisplayContractDefinition ("RX1");

		DisplayContractDefinition ("TU1");

		DisplayContractDefinition ("TY1");

		DisplayContractDefinition ("UB1");

		DisplayContractDefinition ("US1");

		DisplayContractDefinition ("WB1");

		DisplayContractDefinition ("WN1");

		DisplayContractDefinition ("XM1");

		DisplayContractDefinition ("YM1");

		DisplayContractDefinition ("BOBL");

		DisplayContractDefinition ("BUND");

		DisplayContractDefinition ("BUXL");

		DisplayContractDefinition ("FBB1");

		DisplayContractDefinition ("OAT1");

		DisplayContractDefinition ("ULTRA");

		DisplayContractDefinition ("GSWISS");

		DisplayContractDefinition ("SCHATZ");

		System.out.println ("\t|------------------------------||");
	}
}
