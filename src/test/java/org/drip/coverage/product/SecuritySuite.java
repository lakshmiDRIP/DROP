
package org.drip.coverage.product;

import org.drip.sample.securitysuite.Ahmednagar;
import org.drip.sample.securitysuite.Berhampur;
import org.drip.sample.securitysuite.Bhilwara;
import org.drip.sample.securitysuite.CMEFixFloat;
import org.drip.sample.securitysuite.CreditDefaultSwapIndex;
import org.drip.sample.securitysuite.Dhule;
import org.drip.sample.securitysuite.FXSwap;
import org.drip.sample.securitysuite.Kamarhati;
import org.drip.sample.securitysuite.Korba;
import org.drip.sample.securitysuite.Mathura;
import org.drip.sample.securitysuite.Repo;
import org.drip.sample.securitysuite.Rohtak;
import org.drip.sample.securitysuite.Tirupati;

import org.junit.Test;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * SecuritySuite holds the JUnit Code Coverage Tests for the Security Suite Product Module.
 *
 * @author Lakshmi Krishnamurthy
 */

public class SecuritySuite
{
	@Test public void codeCoverageTest() throws Exception
	{
		Ahmednagar.main (null);

		Berhampur.main (null);

		Bhilwara.main (null);

		CMEFixFloat.main (null);

		CreditDefaultSwapIndex.main (null);

		Dhule.main (null);

		FXSwap.main (null);

		Kamarhati.main (null);

		Korba.main (null);

		Mathura.main (null);

		Repo.main (null);

		Rohtak.main (null);

		Tirupati.main (null);
    }
}
