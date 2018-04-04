
package org.drip.coverage.xva;

import org.drip.sample.xvabasel.CollateralizedCollateralNeutral;
import org.drip.sample.xvabasel.CollateralizedCollateralNeutralStochastic;
import org.drip.sample.xvabasel.CollateralizedCollateralPayable;
import org.drip.sample.xvabasel.CollateralizedCollateralPayableStochastic;
import org.drip.sample.xvabasel.CollateralizedCollateralReceivable;
import org.drip.sample.xvabasel.CollateralizedCollateralReceivableStochastic;
import org.drip.sample.xvabasel.CollateralizedFundingNeutral;
import org.drip.sample.xvabasel.CollateralizedFundingNeutralStochastic;
import org.drip.sample.xvabasel.CollateralizedFundingPayable;
import org.drip.sample.xvabasel.CollateralizedFundingPayableStochastic;
import org.drip.sample.xvabasel.CollateralizedFundingReceivable;
import org.drip.sample.xvabasel.CollateralizedFundingReceivableStochastic;
import org.drip.sample.xvabasel.CollateralizedNettingNeutral;
import org.drip.sample.xvabasel.CollateralizedNettingNeutralStochastic;
import org.drip.sample.xvabasel.CollateralizedNettingPayable;
import org.drip.sample.xvabasel.CollateralizedNettingPayableStochastic;
import org.drip.sample.xvabasel.CollateralizedNettingReceivable;
import org.drip.sample.xvabasel.CollateralizedNettingReceivableStochastic;
import org.drip.sample.xvabasel.UncollateralizedCollateralNeutral;
import org.drip.sample.xvabasel.UncollateralizedCollateralNeutralStochastic;
import org.drip.sample.xvabasel.UncollateralizedCollateralPayable;
import org.drip.sample.xvabasel.UncollateralizedCollateralPayableStochastic;
import org.drip.sample.xvabasel.UncollateralizedCollateralReceivable;
import org.drip.sample.xvabasel.UncollateralizedCollateralReceivableStochastic;
import org.drip.sample.xvabasel.UncollateralizedFundingNeutral;
import org.drip.sample.xvabasel.UncollateralizedFundingNeutralStochastic;
import org.drip.sample.xvabasel.UncollateralizedFundingPayable;
import org.drip.sample.xvabasel.UncollateralizedFundingPayableStochastic;
import org.drip.sample.xvabasel.UncollateralizedFundingReceivable;
import org.drip.sample.xvabasel.UncollateralizedFundingReceivableStochastic;
import org.drip.sample.xvabasel.UncollateralizedNettingNeutral;
import org.drip.sample.xvabasel.UncollateralizedNettingNeutralStochastic;
import org.drip.sample.xvabasel.UncollateralizedNettingPayable;
import org.drip.sample.xvabasel.UncollateralizedNettingPayableStochastic;
import org.drip.sample.xvabasel.UncollateralizedNettingReceivable;
import org.drip.sample.xvabasel.UncollateralizedNettingReceivableStochastic;
import org.drip.sample.xvabasel.ZeroThresholdCollateralNeutral;
import org.drip.sample.xvabasel.ZeroThresholdCollateralNeutralStochastic;
import org.drip.sample.xvabasel.ZeroThresholdCollateralPayable;
import org.drip.sample.xvabasel.ZeroThresholdCollateralPayableStochastic;
import org.drip.sample.xvabasel.ZeroThresholdCollateralReceivable;
import org.drip.sample.xvabasel.ZeroThresholdCollateralReceivableStochastic;
import org.drip.sample.xvabasel.ZeroThresholdFundingNeutral;
import org.drip.sample.xvabasel.ZeroThresholdFundingNeutralStochastic;
import org.drip.sample.xvabasel.ZeroThresholdFundingPayable;
import org.drip.sample.xvabasel.ZeroThresholdFundingPayableStochastic;
import org.drip.sample.xvabasel.ZeroThresholdFundingReceivable;
import org.drip.sample.xvabasel.ZeroThresholdFundingReceivableStochastic;
import org.drip.sample.xvabasel.ZeroThresholdNettingNeutral;
import org.drip.sample.xvabasel.ZeroThresholdNettingNeutralStochastic;
import org.drip.sample.xvabasel.ZeroThresholdNettingPayable;
import org.drip.sample.xvabasel.ZeroThresholdNettingPayableStochastic;
import org.drip.sample.xvabasel.ZeroThresholdNettingReceivable;
import org.drip.sample.xvabasel.ZeroThresholdNettingReceivableStochastic;

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
 * XVABasel holds the JUnit Code Coverage Tests for the Basel Accounting XVA Module.
 *
 * @author Lakshmi Krishnamurthy
 */

public class XVABasel
{
	@Test public void codeCoverageTest() throws Exception
	{
		CollateralizedCollateralNeutral.main (null);

		CollateralizedCollateralNeutralStochastic.main (null);

		CollateralizedCollateralPayable.main (null);

		CollateralizedCollateralPayableStochastic.main (null);

		CollateralizedCollateralReceivable.main (null);

		CollateralizedCollateralReceivableStochastic.main (null);

		CollateralizedFundingNeutral.main (null);

		CollateralizedFundingNeutralStochastic.main (null);

		CollateralizedFundingPayable.main (null);

		CollateralizedFundingPayableStochastic.main (null);

		CollateralizedFundingReceivable.main (null);

		CollateralizedFundingReceivableStochastic.main (null);

		CollateralizedNettingNeutral.main (null);

		CollateralizedNettingNeutralStochastic.main (null);

		CollateralizedNettingPayable.main (null);

		CollateralizedNettingPayableStochastic.main (null);

		CollateralizedNettingReceivable.main (null);

		CollateralizedNettingReceivableStochastic.main (null);

		UncollateralizedCollateralNeutral.main (null);

		UncollateralizedCollateralNeutralStochastic.main (null);

		UncollateralizedCollateralPayable.main (null);

		UncollateralizedCollateralPayableStochastic.main (null);

		UncollateralizedCollateralReceivable.main (null);

		UncollateralizedCollateralReceivableStochastic.main (null);

		UncollateralizedFundingNeutral.main (null);

		UncollateralizedFundingNeutralStochastic.main (null);

		UncollateralizedFundingPayable.main (null);

		UncollateralizedFundingPayableStochastic.main (null);

		UncollateralizedFundingReceivable.main (null);

		UncollateralizedFundingReceivableStochastic.main (null);

		UncollateralizedNettingNeutral.main (null);

		UncollateralizedNettingNeutralStochastic.main (null);

		UncollateralizedNettingPayable.main (null);

		UncollateralizedNettingPayableStochastic.main (null);

		UncollateralizedNettingReceivable.main (null);

		UncollateralizedNettingReceivableStochastic.main (null);

		ZeroThresholdCollateralNeutral.main (null);

		ZeroThresholdCollateralNeutralStochastic.main (null);

		ZeroThresholdCollateralPayable.main (null);

		ZeroThresholdCollateralPayableStochastic.main (null);

		ZeroThresholdCollateralReceivable.main (null);

		ZeroThresholdCollateralReceivableStochastic.main (null);

		ZeroThresholdFundingNeutral.main (null);

		ZeroThresholdFundingNeutralStochastic.main (null);

		ZeroThresholdFundingPayable.main (null);

		ZeroThresholdFundingPayableStochastic.main (null);

		ZeroThresholdFundingReceivable.main (null);

		ZeroThresholdFundingReceivableStochastic.main (null);

		ZeroThresholdNettingNeutral.main (null);

		ZeroThresholdNettingNeutralStochastic.main (null);

		ZeroThresholdNettingPayable.main (null);

		ZeroThresholdNettingPayableStochastic.main (null);

		ZeroThresholdNettingReceivable.main (null);

		ZeroThresholdNettingReceivableStochastic.main (null);
    }
}
