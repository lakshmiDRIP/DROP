
package org.drip.coverage.portfolioconstruction;

import org.drip.sample.blacklitterman.DaJagannathan2005a;
import org.drip.sample.blacklitterman.DaJagannathan2005b;
import org.drip.sample.blacklitterman.DaJagannathan2005c;
import org.drip.sample.blacklitterman.DaJagannathan2005d;
import org.drip.sample.blacklitterman.DaJagannathan2005e;
import org.drip.sample.blacklitterman.IdzorekAndrogue2003;
import org.drip.sample.blacklitterman.OToole2013;
import org.drip.sample.blacklitterman.Soontornkit2010;
import org.drip.sample.blacklitterman.Yamabe2016;

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
 * BlackLitterman holds the JUnit Code Coverage Tests for the Black Litterman Portfolio Construction Module.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BlackLitterman
{
	@Test public void codeCoverageTest() throws Exception
	{
		DaJagannathan2005a.main (null);

		DaJagannathan2005b.main (null);

		DaJagannathan2005c.main (null);

		DaJagannathan2005d.main (null);

		DaJagannathan2005e.main (null);

		IdzorekAndrogue2003.main (null);

		OToole2013.main (null);

		Soontornkit2010.main (null);

		Yamabe2016.main (null);
    }
}
