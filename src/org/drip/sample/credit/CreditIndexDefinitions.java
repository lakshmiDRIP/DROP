
package org.drip.sample.credit;

import org.drip.market.otc.*;
import org.drip.quant.common.FormatUtil;
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
 * CreditIndexDefinitions displays the Definitions of the CDX NA IG OTC Index CDS Contracts.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CreditIndexDefinitions {

	private static final void DisplayIndexConvention (
		final String strFullIndexName)
		throws Exception
	{
		CreditIndexConvention cic = CreditIndexConventionContainer.ConventionFromFullName (strFullIndexName);

		System.out.println (
			"\t| " + cic.fullName() +
			" | " + cic.indexType() +
			" | " + cic.indexSubType() +
			" | " + cic.seriesName() +
			" | " + cic.currency() +
			" | " + cic.effectiveDate() +
			" | " + cic.maturityDate() +
			" | " + cic.frequency() +
			" | " + cic.dayCount() +
			" | " + FormatUtil.FormatDouble (cic.fixedCoupon(), 3, 0, 10000.) +
			" | " + FormatUtil.FormatDouble (cic.recoveryRate(), 2, 0, 100.) +
			"% | " + cic.numberOfConstituents() + " ||"
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] astrFullIndexName = new String[] {
			"CDX.NA.IG.S15.5Y",
			"CDX.NA.IG.S16.5Y",
			"CDX.NA.IG.S17.5Y",
			"CDX.NA.IG.S18.5Y",
			"CDX.NA.IG.S19.5Y",
			"CDX.NA.IG.S20.5Y",
			"CDX.NA.IG.S21.5Y",
			"CDX.NA.IG.S22.5Y",
			"CDX.NA.IG.S23.5Y",
			"CDX.NA.IG.S24.5Y",
			"CDX.NA.IG.S25.5Y",
			"CDX.NA.IG.S26.5Y"
		};

		System.out.println ("\n");

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                                                                          ||");

		System.out.println ("\t|                                      CDX NA IG OTC INDEX DEFINITIONS                                     ||");

		System.out.println ("\t|                                                                                                          ||");

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                                                                          ||");

		System.out.println ("\t|    L -> R:                                                                                               ||");

		System.out.println ("\t|                                                                                                          ||");

		System.out.println ("\t|        - Full Name                                                                                       ||");

		System.out.println ("\t|        - Index Type                                                                                      ||");

		System.out.println ("\t|        - Index Sub-Type                                                                                  ||");

		System.out.println ("\t|        - Series Name                                                                                     ||");

		System.out.println ("\t|        - Currency                                                                                        ||");

		System.out.println ("\t|        - Effective Date                                                                                  ||");

		System.out.println ("\t|        - Maturity Date                                                                                   ||");

		System.out.println ("\t|        - Coupon/Pay Frequency                                                                            ||");

		System.out.println ("\t|        - Coupon/Pay Day Count                                                                            ||");

		System.out.println ("\t|        - Fixed Coupon Strike (bp)                                                                        ||");

		System.out.println ("\t|        - Fixed Recovery Rate                                                                             ||");

		System.out.println ("\t|        - Number of Constituents                                                                          ||");

		System.out.println ("\t|                                                                                                          ||");

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------||");

		for (String strFullIndexName : astrFullIndexName)
			DisplayIndexConvention (strFullIndexName);

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------||");

		System.out.println ("\n");
	}
}
