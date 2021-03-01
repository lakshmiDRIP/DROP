
package org.drip.coverage.xva;

import org.drip.sample.burgard2013.BilateralCSACollateralizedFunding;
import org.drip.sample.burgard2013.BilateralCSACollateralizedFundingStochastic;
import org.drip.sample.burgard2013.BilateralCSAUncollateralizedFunding;
import org.drip.sample.burgard2013.BilateralCSAUncollateralizedFundingStochastic;
import org.drip.sample.burgard2013.BilateralCSAZeroThresholdFunding;
import org.drip.sample.burgard2013.BilateralCSAZeroThresholdFundingStochastic;
import org.drip.sample.burgard2013.PerfectReplicationCollateralizedFunding;
import org.drip.sample.burgard2013.PerfectReplicationCollateralizedFundingStochastic;
import org.drip.sample.burgard2013.PerfectReplicationUncollateralizedFunding;
import org.drip.sample.burgard2013.PerfectReplicationUncollateralizedFundingStochastic;
import org.drip.sample.burgard2013.PerfectReplicationZeroThresholdFunding;
import org.drip.sample.burgard2013.PerfectReplicationZeroThresholdFundingStochastic;
import org.drip.sample.burgard2013.SemiReplicationCollateralizedFunding;
import org.drip.sample.burgard2013.SemiReplicationCollateralizedFundingStochastic;
import org.drip.sample.burgard2013.SemiReplicationUncollateralizedFunding;
import org.drip.sample.burgard2013.SemiReplicationUncollateralizedFundingStochastic;
import org.drip.sample.burgard2013.SemiReplicationZeroThresholdFunding;
import org.drip.sample.burgard2013.SemiReplicationZeroThresholdFundingStochastic;
import org.drip.sample.burgard2013.SetOffCollateralizedFunding;
import org.drip.sample.burgard2013.SetOffCollateralizedFundingStochastic;
import org.drip.sample.burgard2013.SetOffUncollateralizedFunding;
import org.drip.sample.burgard2013.SetOffUncollateralizedFundingStochastic;
import org.drip.sample.burgard2013.SetOffZeroThresholdFunding;
import org.drip.sample.burgard2013.SetOffZeroThresholdFundingStochastic;
import org.drip.sample.burgard2013.UnilateralCSACollateralizedFunding;
import org.drip.sample.burgard2013.UnilateralCSACollateralizedFundingStochastic;
import org.drip.sample.burgard2013.UnilateralCSAUncollateralizedFunding;
import org.drip.sample.burgard2013.UnilateralCSAUncollateralizedFundingStochastic;
import org.drip.sample.burgard2013.UnilateralCSAZeroThresholdFunding;
import org.drip.sample.burgard2013.UnilateralCSAZeroThresholdFundingStochastic;
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
 * Burgard2013 holds the JUnit Code Coverage Tests for the Burgard-Kjaer (2013) XVA Module.
 *
 * @author Lakshmi Krishnamurthy
 */

public class Burgard2013
{
	@Test public void codeCoverageTest() throws Exception
	{
		BilateralCSACollateralizedFunding.main (null);

		BilateralCSACollateralizedFundingStochastic.main (null);

		BilateralCSAUncollateralizedFunding.main (null);

		BilateralCSAUncollateralizedFundingStochastic.main (null);

		BilateralCSAZeroThresholdFunding.main (null);

		BilateralCSAZeroThresholdFundingStochastic.main (null);

		PerfectReplicationCollateralizedFunding.main (null);

		PerfectReplicationCollateralizedFundingStochastic.main (null);

		PerfectReplicationUncollateralizedFunding.main (null);

		PerfectReplicationUncollateralizedFundingStochastic.main (null);

		PerfectReplicationZeroThresholdFunding.main (null);

		PerfectReplicationZeroThresholdFundingStochastic.main (null);

		SemiReplicationCollateralizedFunding.main (null);

		SemiReplicationCollateralizedFundingStochastic.main (null);

		SemiReplicationUncollateralizedFunding.main (null);

		SemiReplicationUncollateralizedFundingStochastic.main (null);

		SemiReplicationZeroThresholdFunding.main (null);

		SemiReplicationZeroThresholdFundingStochastic.main (null);

		SetOffCollateralizedFunding.main (null);

		SetOffCollateralizedFundingStochastic.main (null);

		SetOffUncollateralizedFunding.main (null);

		SetOffUncollateralizedFundingStochastic.main (null);

		SetOffZeroThresholdFunding.main (null);

		SetOffZeroThresholdFundingStochastic.main (null);

		UnilateralCSACollateralizedFunding.main (null);

		UnilateralCSACollateralizedFundingStochastic.main (null);

		UnilateralCSAUncollateralizedFunding.main (null);

		UnilateralCSAUncollateralizedFundingStochastic.main (null);

		UnilateralCSAZeroThresholdFunding.main (null);

		UnilateralCSAZeroThresholdFundingStochastic.main (null);
	}
}
