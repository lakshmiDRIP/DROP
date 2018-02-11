
package org.drip.coverage.treasury;

import org.drip.sample.treasury.GovvieBondDefinitions;
import org.drip.sample.treasury.TreasuryFixedBullet;
import org.drip.sample.treasury.YAS_BTPS;
import org.drip.sample.treasury.YAS_CAN;
import org.drip.sample.treasury.YAS_DBR;
import org.drip.sample.treasury.YAS_FRTR;
import org.drip.sample.treasury.YAS_GGB;
import org.drip.sample.treasury.YAS_GILT;
import org.drip.sample.treasury.YAS_JGB;
import org.drip.sample.treasury.YAS_MBONO;
import org.drip.sample.treasury.YAS_SPGB;
import org.drip.sample.treasury.YAS_UST;

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
 * Treasury holds the JUnit Code Coverage Tests for the Treasury Product Module.
 *
 * @author Lakshmi Krishnamurthy
 */

public class Treasury
{
	@Test public void codeCoverageTest() throws Exception
	{
		GovvieBondDefinitions.main (null);

		TreasuryFixedBullet.main (null);

		YAS_BTPS.main (null);

		YAS_CAN.main (null);

		YAS_DBR.main (null);

		YAS_FRTR.main (null);

		YAS_GGB.main (null);

		YAS_GILT.main (null);

		YAS_JGB.main (null);

		YAS_MBONO.main (null);

		YAS_SPGB.main (null);

		YAS_UST.main (null);
    }
}
