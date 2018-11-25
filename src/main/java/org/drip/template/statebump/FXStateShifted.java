
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
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>FXStateShifted</i> demonstrates the Generation and the Usage of Tenor Bumped FX Curves.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template">Template</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/statebump">State Bump</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
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
