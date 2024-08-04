
package org.drip.sample.cross;

import java.util.List;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.*;
import org.drip.function.r1tor1operator.Flat;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.period.*;
import org.drip.param.valuation.*;
import org.drip.product.fx.ComponentPair;
import org.drip.product.params.*;
import org.drip.product.rates.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.*;
import org.drip.state.identifier.*;

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
 * <i>FixFloatFixFloatAnalysis</i> demonstrates the Funding Volatility, Forward Volatility, FX Volatility,
 * Funding/Forward Correlation, Funding/FX Correlation, and Forward/FX Correlation across the 2 currencies
 * (USD and EUR) on the Valuation of the Cross Currency Basis Swap built out of a pair of fix-float swaps.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/cross/README.md">Single/Dual Stream XCCY Component</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FixFloatFixFloatAnalysis {

	private static final FixFloatComponent MakeFixFloatSwap (
		final JulianDate dtEffective,
		final boolean bFXMTM,
		final String strPayCurrency,
		final String strCouponCurrency,
		final String strMaturityTenor,
		final int iTenorInMonths)
		throws Exception
	{
		UnitCouponAccrualSetting ucasFixed = new UnitCouponAccrualSetting (
			2,
			"Act/360",
			false,
			"Act/360",
			false,
			strCouponCurrency,
			false,
			CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC
		);

		ComposableFloatingUnitSetting cfusFloating = new ComposableFloatingUnitSetting (
			iTenorInMonths + "M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			ForwardLabel.Create (
				strCouponCurrency,
				iTenorInMonths + "M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		ComposableFixedUnitSetting cfusFixed = new ComposableFixedUnitSetting (
			"6M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			0.02,
			0.,
			strCouponCurrency
		);

		CompositePeriodSetting cpsFloating = new CompositePeriodSetting (
			12 / iTenorInMonths,
			iTenorInMonths + "M",
			strPayCurrency,
			null,
			-1.,
			null,
			null,
			null,
			null
		);

		CompositePeriodSetting cpsFixed = new CompositePeriodSetting (
			2,
			"6M",
			strPayCurrency,
			null,
			1.,
			null,
			null,
			bFXMTM ? null : new FixingSetting (
				FixingSetting.FIXING_PRESET_STATIC,
				null,
				dtEffective.julian()
			),
			null
		);

		List<Integer> lsFloatingStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
			dtEffective,
			iTenorInMonths + "M",
			strMaturityTenor,
			null
		);

		List<Integer> lsFixedStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
			dtEffective,
			"6M",
			strMaturityTenor,
			null
		);

		Stream floatingStream = new Stream (
			CompositePeriodBuilder.FloatingCompositeUnit (
				lsFloatingStreamEdgeDate,
				cpsFloating,
				cfusFloating
			)
		);

		Stream fixedStream = new Stream (
			CompositePeriodBuilder.FixedCompositeUnit (
				lsFixedStreamEdgeDate,
				cpsFixed,
				ucasFixed,
				cfusFixed
			)
		);

		FixFloatComponent fixFloat = new FixFloatComponent (
			fixedStream,
			floatingStream,
			new CashSettleParams (
				0,
				strPayCurrency,
				0
			)
		);

		return fixFloat;
		}

	private static final void SetMarketParams (
		final int iValueDate,
		final CurveSurfaceQuoteContainer mktParams,
		final ForwardLabel forwardLabel1,
		final ForwardLabel forwardLabel2,
		final FundingLabel fundingLabel,
		final FXLabel fxLabel,
		final double dblForward1Vol,
		final double dblForward2Vol,
		final double dblFundingVol,
		final double dblFXVol,
		final double dblForward1FundingCorr,
		final double dblForward2FundingCorr,
		final double dblForward2FXCorr,
		final double dblFundingFXCorr)
		throws Exception
	{
		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (forwardLabel1),
				forwardLabel1.currency(),
				dblForward1Vol
			)
		);

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (forwardLabel2),
				forwardLabel2.currency(),
				dblForward2Vol
			)
		);

		mktParams.setFundingVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (fundingLabel),
				forwardLabel1.currency(),
				dblFundingVol
			)
		);

		mktParams.setFXVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (fxLabel),
				forwardLabel1.currency(),
				dblFXVol
			)
		);

		mktParams.setForwardFundingCorrelation (
			forwardLabel1,
			fundingLabel,
			new Flat (dblForward1FundingCorr)
		);

		mktParams.setForwardFundingCorrelation (
			forwardLabel2,
			fundingLabel,
			new Flat (dblForward2FundingCorr)
		);

		mktParams.setForwardFXCorrelation (
			forwardLabel2,
			fxLabel,
			new Flat (dblForward2FXCorr)
		);

		mktParams.setFundingFXCorrelation (
			fundingLabel,
			fxLabel,
			new Flat (dblFundingFXCorr)
		);
	}

	private static final void VolCorrScenario (
		final ComponentPair[] aCP,
		final ValuationParams valParams,
		final CurveSurfaceQuoteContainer mktParams,
		final ForwardLabel forwardLabel1,
		final ForwardLabel forwardLabel2,
		final FundingLabel fundingLabel,
		final FXLabel fxLabel,
		final double dblForward1Vol,
		final double dblForward2Vol,
		final double dblFundingVol,
		final double dblFXVol,
		final double dblForward1FundingCorr,
		final double dblForward2FundingCorr,
		final double dblForward2FXCorr,
		final double dblFundingFXCorr)
		throws Exception
	{
		SetMarketParams (
			valParams.valueDate(),
			mktParams,
			forwardLabel1,
			forwardLabel2,
			fundingLabel,
			fxLabel,
			dblForward1Vol,
			dblForward2Vol,
			dblFundingVol,
			dblFXVol,
			dblForward1FundingCorr,
			dblForward2FundingCorr,
			dblForward2FXCorr,
			dblFundingFXCorr
		);

		String strDump = "\t[" +
			FormatUtil.FormatDouble (dblForward1Vol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForward2Vol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblFundingVol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblFXVol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForward1FundingCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForward2FundingCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForward2FXCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblFundingFXCorr, 2, 0, 100.) + "%] = ";

		for (int i = 0; i < aCP.length; ++i) {
			CaseInsensitiveTreeMap<Double> mapOutput = aCP[i].value (
				valParams,
				null,
				mktParams,
				null
			);

			if (0 != i) strDump += " || ";

			strDump +=
				FormatUtil.FormatDouble (mapOutput.get ("ReferenceCumulativeConvexityAdjustmentPremium"), 2, 0, 10000.) + " | " +
				FormatUtil.FormatDouble (mapOutput.get ("DerivedCumulativeConvexityAdjustmentPremium"), 2, 0, 10000.) + " | " +
				FormatUtil.FormatDouble (mapOutput.get ("CumulativeConvexityAdjustmentPremium"), 2, 0, 10000.);
		}

		System.out.println (strDump);
	}

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
		double dblUSD3MForwardRate = 0.0275;
		double dblEUR3MForwardRate = 0.0175;
		double dblUSDFundingRate = 0.03;
		double dblUSDEURFXRate = 1. / 1.34;

		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		JulianDate dtToday = org.drip.analytics.date.DateUtil.Today();

		ValuationParams valParams = new ValuationParams (
			dtToday,
			dtToday,
			"USD"
		);

		ForwardLabel fri3MUSD = ForwardLabel.Create (
			"USD",
			"3M"
		);

		ForwardLabel fri3MEUR = ForwardLabel.Create (
			"EUR",
			"3M"
		);

		FundingLabel fundingLabel = FundingLabel.Standard ("USD");

		CurrencyPair cp = CurrencyPair.FromCode ("USD/EUR");

		FXLabel fxLabel = FXLabel.Standard (cp);

		FixFloatComponent fixFloatUSD = MakeFixFloatSwap (
			dtToday,
			false,
			"USD",
			"USD",
			"2Y",
			3
		);

		FixFloatComponent fixFloatEURMTM = MakeFixFloatSwap (
			dtToday,
			true,
			"USD",
			"EUR",
			"2Y",
			3
		);

		ComponentPair cpMTM = new ComponentPair (
			"FFFF_MTM",
			fixFloatUSD,
			fixFloatEURMTM,
			null
		);

		FixFloatComponent fixFloatEURNonMTM = MakeFixFloatSwap (
			dtToday,
			false,
			"USD",
			"EUR",
			"2Y",
			3
		);

		ComponentPair cpNonMTM = new ComponentPair (
			"FFFF_Non_MTM",
			fixFloatUSD,
			fixFloatEURNonMTM,
			null
		);

		CurveSurfaceQuoteContainer mktParams = new CurveSurfaceQuoteContainer();

		mktParams.setFixing (
			dtToday,
			fxLabel,
			dblUSDEURFXRate
		);

		mktParams.setForwardState (
			ScenarioForwardCurveBuilder.FlatForwardForwardCurve (
				dtToday,
				fri3MUSD,
				dblUSD3MForwardRate
			)
		);

		mktParams.setForwardState (
			ScenarioForwardCurveBuilder.FlatForwardForwardCurve (
				dtToday,
				fri3MEUR,
				dblEUR3MForwardRate
			)
		);

		mktParams.setFundingState (
			ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
				dtToday,
				"USD",
				dblUSDFundingRate
			)
		);

		mktParams.setFXState (
			ScenarioFXCurveBuilder.CubicPolynomialCurve (
				fxLabel.fullyQualifiedName(),
				dtToday,
				cp,
				new String[] {"10Y"},
				new double[] {dblUSDEURFXRate},
				dblUSDEURFXRate
			)
		);

		double[] adblUSD3MForwardVol = new double[] {
			0.1, 0.4
		};
		double[] adblEUR3MForwardVol = new double[] {
			0.1, 0.4
		};
		double[] adblUSDFundingVol = new double[] {
			0.1, 0.4
		};
		double[] adblUSDEURFXVol = new double[] {
			0.1, 0.4
		};
		double[] adblUSD3MForwardUSDFundingCorr = new double[] {
			-0.1, 0.2
		};
		double[] adblEUR3MForwardUSDFundingCorr = new double[] {
			-0.1, 0.2
		};
		double[] adblEUR3MForwardUSDEURFXCorr = new double[] {
			-0.1, 0.2
		};
		double[] adblUSDFundingUSDEURFXCorr = new double[] {
			-0.1, 0.2
		};

		for (double dblUSD3MForwardVol : adblUSD3MForwardVol) {
			for (double dblEUR3MForwardVol : adblEUR3MForwardVol) {
				for (double dblUSDFundingVol : adblUSDFundingVol) {
					for (double dblUSDEURFXVol : adblUSDEURFXVol) {
						for (double dblUSD3MForwardUSDFundingCorr : adblUSD3MForwardUSDFundingCorr) {
							for (double dblEUR3MForwardUSDFundingCorr : adblEUR3MForwardUSDFundingCorr) {
								for (double dblEUR3MForwardUSDEURFXCorr : adblEUR3MForwardUSDEURFXCorr) {
									for (double dblUSDFundingUSDEURFXCorr : adblUSDFundingUSDEURFXCorr)
										VolCorrScenario (
											new ComponentPair[] {
												cpMTM,
												cpNonMTM
											},
											valParams,
											mktParams,
											fri3MUSD,
											fri3MEUR,
											fundingLabel,
											fxLabel,
											dblUSD3MForwardVol,
											dblEUR3MForwardVol,
											dblUSDFundingVol,
											dblUSDEURFXVol,
											dblUSD3MForwardUSDFundingCorr,
											dblEUR3MForwardUSDFundingCorr,
											dblEUR3MForwardUSDEURFXCorr,
											dblUSDFundingUSDEURFXCorr
										);
								}
							}
						}
					}
				}
			}
		}

		EnvManager.TerminateEnv();
	}
}
