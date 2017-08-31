
package org.drip.template.statebump;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.Component;
import org.drip.product.fx.FXForwardComponent;
import org.drip.product.params.CurrencyPair;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.*;
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
 * FXStateShifted demonstrates the Generation and the Usage of Tenor Bumped FX Curves.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FXStateShifted {

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
		double dblBump = 0.0001;
		boolean bIsBumpProportional = false;

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

		Map<String, FXCurve> mapBumpedFXCurve = LatentMarketStateBuilder.BumpedFXCurve (
			dtSpot,
			cp,
			astrMaturityTenor,
			adblFXForward,
			"Outright",
			dblFXSpot,
			LatentMarketStateBuilder.SMOOTH,
			dblBump,
			bIsBumpProportional
		);

		FXForwardComponent[] aFXFC = OTCInstrumentBuilder.FXForward (
			dtSpot,
			cp,
			astrMaturityTenor
		);

		System.out.println ("\n\t|-------------------------------------------------------------------------------------------------------------------||");

		ValuationParams valParams = ValuationParams.Spot (dtSpot.julian());

		for (Map.Entry<String, FXCurve> meFX : mapBumpedFXCurve.entrySet()) {
			String strKey = meFX.getKey();

			if (!strKey.startsWith ("fxfwd")) continue;

			CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

			csqc.setFXState (meFX.getValue());

			System.out.print ("\t|  [" + meFX.getKey() + "] => ");

			for (Component comp : aFXFC)
				System.out.print (FormatUtil.FormatDouble (
					comp.measureValue (
						valParams,
						null,
						csqc,
						null,
						"Outright"
					), 1, 4, 1.) + " |");

			System.out.print ("|\n");
		}

		System.out.println ("\t|-------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|---------------------------------------------||");

		CurveSurfaceQuoteContainer csqcBase = new CurveSurfaceQuoteContainer();

		csqcBase.setFXState (mapBumpedFXCurve.get ("Base"));

		CurveSurfaceQuoteContainer csqcBump = new CurveSurfaceQuoteContainer();

		csqcBump.setFXState (mapBumpedFXCurve.get ("Bump"));

		for (Component comp : aFXFC)
			System.out.println (
				"\t| OUTRIGHT  => " +
				comp.maturityDate() + " | " +
				FormatUtil.FormatDouble (comp.measureValue (
					valParams,
					null,
					csqcBase,
					null,
					"Outright"
				), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (comp.measureValue (
					valParams,
					null,
					csqcBump,
					null,
					"Outright"
				), 1, 4, 1.) + " ||"
			);

		System.out.println ("\t|---------------------------------------------||");
	}
}
