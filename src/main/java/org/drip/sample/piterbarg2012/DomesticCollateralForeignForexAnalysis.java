
package org.drip.sample.piterbarg2012;

import org.drip.analytics.date.*;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.function.r1tor1operator.Flat;
import org.drip.param.creator.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.*;
import org.drip.product.fx.DomesticCollateralizedForeignForward;
import org.drip.product.params.CurrencyPair;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.*;
import org.drip.state.curve.ForeignCollateralizedDiscountCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.fx.FXCurve;
import org.drip.state.identifier.*;
import org.drip.state.nonlinear.*;
import org.drip.state.volatility.VolatilityCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>DomesticCollateralForeignForexAnalysis</i> contains an analysis of the correlation and volatility
 * 	impact on the price of a Domestic Collateralized Foreign Pay-out Forex Contract.
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Barden, P. (2009): Equity Forward Prices in the Presence of Funding Spreads <i>ICBI
 *  			Conference</i> <b>Rome</b>
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2009): Modeling and successful Management of Credit Counter-party Risk
 *  			of Derivative Portfolios <i>ICBI Conference</i> <b>Rome</b>
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Johannes, M., and S. Sundaresan (2007): Pricing Collateralized Swaps <i>Journal of Finance</i>
 *  			<b>62</b> 383-410
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/piterbarg2012/README.md">Piterbarg (2012) Domestic Foreign Collateral</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class DomesticCollateralForeignForexAnalysis {

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		JulianDate dtToday = DateUtil.Today();

		String strDomesticCurrency = "USD";
		String strForeignCurrency = "EUR";
		String strMaturity = "1Y";
		double dblFXFwdStrike = 0.984;
		double dblDomesticCollateralRate = 0.02;
		double dblCollateralizedFXRate = 1.10;

		CurrencyPair cp = CurrencyPair.FromCode (strForeignCurrency + "/" + strDomesticCurrency);

		MergedDiscountForwardCurve dcCcyDomesticCollatDomestic = ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
			dtToday,
			strDomesticCurrency,
			dblDomesticCollateralRate
		);

		ValuationParams valParams = new ValuationParams (
			dtToday,
			dtToday,
			strDomesticCurrency
		);

		FXCurve fxCurve = new FlatForwardFXCurve (
			dtToday.julian(),
			cp,
			dblCollateralizedFXRate,
			new int[] {dtToday.julian()},
			new double[] {dblCollateralizedFXRate}
		);

		VolatilityCurve vcForeignFunding = new FlatForwardVolatilityCurve (
			dtToday.julian(),
			VolatilityLabel.Standard (CollateralLabel.Standard (strForeignCurrency)),
			strDomesticCurrency,
			new int[] {dtToday.julian()},
			new double[] {0.}
		);

		VolatilityCurve vcFX = new FlatForwardVolatilityCurve (
			dtToday.julian(),
			VolatilityLabel.Standard (FXLabel.Standard (cp)),
			strDomesticCurrency,
			new int[] {dtToday.julian()},
			new double[] {0.}
		);

		MergedDiscountForwardCurve dcCcyForeignCollatDomestic = new ForeignCollateralizedDiscountCurve (
			strForeignCurrency,
			dcCcyDomesticCollatDomestic,
			fxCurve,
			vcForeignFunding,
			vcFX,
			new Flat (0.)
		);

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Create (
			null,
			null,
			null,
			null,
			null,
			null,
			null
		);

		mktParams.setPayCurrencyCollateralCurrencyCurve (
			strForeignCurrency,
			strDomesticCurrency,
			dcCcyForeignCollatDomestic
		);

		mktParams.setPayCurrencyCollateralCurrencyCurve (
			strDomesticCurrency,
			strDomesticCurrency,
			dcCcyDomesticCollatDomestic
		);

		mktParams.setFXState (
			ScenarioFXCurveBuilder.CubicPolynomialCurve (
				"FX::" + cp.code(),
				dtToday,
				cp,
				new String[] {"10Y"},
				new double[] {dblCollateralizedFXRate},
				dblCollateralizedFXRate
			)
		);

		DomesticCollateralizedForeignForward dcff = new DomesticCollateralizedForeignForward (
			cp,
			dblFXFwdStrike,
			dtToday.addTenor (strMaturity)
		);

		CaseInsensitiveTreeMap<Double> mapBaseValue = dcff.value (
			new ValuationParams (
				dtToday,
				dtToday,
				strDomesticCurrency
			),
			null,
			mktParams,
			null
		);

		double dblBaselinePrice = mapBaseValue.get ("Price");

		double dblBaselineParForward = mapBaseValue.get ("ParForward");

		double[] adblForeignRatesVolatility = new double[] {
			0.1, 0.2, 0.3, 0.4, 0.5
		};
		double[] adblFXVolatility = new double[] {
			0.10, 0.15, 0.20, 0.25, 0.30
		};
		double[] adblFXForeignRatesCorrelation = new double[] {
			-0.99, -0.50, 0.00, 0.50, 0.99
		};

		System.out.println ("\tPrinting the Domestic Collateralized Foreign Forex Output in Order (Left -> Right):");

		System.out.println ("\t\tPrice (%)");

		System.out.println ("\t\tPrice Difference (%)");

		System.out.println ("\t\tPar Forward (abs)");

		System.out.println ("\t\tPar Forward Difference (abs)");

		System.out.println ("\t-------------------------------------------------------------");

		System.out.println ("\t-------------------------------------------------------------");

		for (double dblForeignRatesVolatility : adblForeignRatesVolatility) {
			for (double dblFXVolatility : adblFXVolatility) {
				for (double dblFXForeignRatesCorrelation : adblFXForeignRatesCorrelation) {
					dcCcyForeignCollatDomestic = new ForeignCollateralizedDiscountCurve (
						strForeignCurrency,
						dcCcyDomesticCollatDomestic,
						fxCurve,
						new FlatForwardVolatilityCurve (
							dtToday.julian(),
							VolatilityLabel.Standard (CollateralLabel.Standard (strForeignCurrency)),
							strDomesticCurrency,
							new int[] {dtToday.julian()},
							new double[] {dblForeignRatesVolatility}
						),
						new FlatForwardVolatilityCurve (
							dtToday.julian(),
							VolatilityLabel.Standard (FXLabel.Standard (cp)),
							strDomesticCurrency,
							new int[] {dtToday.julian()},
							new double[] {dblFXVolatility}
						),
						new Flat (dblFXForeignRatesCorrelation)
					);

					mktParams.setPayCurrencyCollateralCurrencyCurve (
						strForeignCurrency,
						strDomesticCurrency,
						dcCcyForeignCollatDomestic
					);

					CaseInsensitiveTreeMap<Double> mapDCFF = dcff.value (
						valParams,
						null,
						mktParams,
						null
					);

					double dblPrice = mapDCFF.get ("Price");

					double dblParForward = mapDCFF.get ("ParForward");

					System.out.println ("\t[" +
						org.drip.service.common.FormatUtil.FormatDouble (dblForeignRatesVolatility, 2, 0, 100.) + "%," +
						org.drip.service.common.FormatUtil.FormatDouble (dblFXVolatility, 2, 0, 100.) + "%," +
						org.drip.service.common.FormatUtil.FormatDouble (dblFXForeignRatesCorrelation, 2, 0, 100.) + "%] = " +
						org.drip.service.common.FormatUtil.FormatDouble (dblPrice, 2, 2, 100.) + " | " +
						org.drip.service.common.FormatUtil.FormatDouble (dblPrice - dblBaselinePrice, 2, 2, 100.) + " | " +
						org.drip.service.common.FormatUtil.FormatDouble (dblParForward, 1, 4, 1.) + " | " +
						org.drip.service.common.FormatUtil.FormatDouble (dblParForward - dblBaselineParForward, 1, 4, 1.)
					);
				}
			}
		}

		EnvManager.TerminateEnv();
	}
}
