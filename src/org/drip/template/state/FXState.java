
package org.drip.template.state;

import org.drip.analytics.date.*;
import org.drip.product.params.CurrencyPair;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.fx.FXCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * FXState sets up the Calibration and the Construction of the FX Latent State and examine the Emitted
 *  Metrics.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FXState {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		CurrencyPair cp = new CurrencyPair (
			"EUR",
			"USD",
			"USD",
			10000.
		);

		JulianDate dtSpot = DateUtil.Today().addBusDays (
			0,
			cp.denomCcy()
		);

		double dblFXSpot = 1.1013;

		String[] astrMaturityTenor = new String[] {
			"1D",
			"2D",
			"3D",
			"1W",
			"2W",
			"3W",
			"1M",
			"2M",
			"3M",
			"6M",
			"9M"
		};

		double[] adblFXForward = new double[] {
			1.1011,		// "1D"
			1.1007,		// "2D"
			1.0999,		// "3D"
			1.0976,		// "1W"
			1.0942,		// "2W"
			1.0904,		// "3W"
			1.0913,		// "1M"
			1.0980,		// "2M"
			1.1088,		// "3M"
			1.1115,		// "6M"
			1.1011		// "9M"
		};

		FXCurve fxfc = LatentMarketStateBuilder.SmoothFXCurve (
			dtSpot,
			cp,
			astrMaturityTenor,
			adblFXForward,
			"Outright",
			dblFXSpot
		);

		String strLatentStateLabel = fxfc.label().fullyQualifiedName();

		System.out.println ("\n\n\t||--------------------------------------------------------------||");

		for (int i = 0; i < adblFXForward.length; ++i)
			System.out.println (
				"\t||  " + strLatentStateLabel + " |  FX FORWARD  | " +
				astrMaturityTenor[i] + " | " + FormatUtil.FormatDouble (adblFXForward[i], 1, 4, 1.) +
				" | Outright | " +
				FormatUtil.FormatDouble (fxfc.fx (astrMaturityTenor[i]), 1, 4, 1.) +
				"  ||"
			);

		System.out.println ("\t||--------------------------------------------------------------||\n");
	}
}
