
package org.drip.sample.cross;

import java.util.List;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.*;
import org.drip.function.r1tor1operator.Flat;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.period.*;
import org.drip.param.valuation.*;
import org.drip.product.params.*;
import org.drip.product.rates.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.*;
import org.drip.state.discount.*;
import org.drip.state.forward.ForwardCurve;
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
 * <i>CrossFloatCrossFloatAnalysis</i> demonstrates the impact of Funding Volatility, Forward Volatility, and
 * Funding/Forward, Funding/FX, and Forward/FX Correlation for each of the FRI's on the Valuation of a
 * float-float swap with a 3M EUR Floater leg that pays in USD, and a 6M EUR Floater leg that pays in USD.
 * Comparison is done across MTM and non-MTM fixed Leg Counterparts.
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

public class CrossFloatCrossFloatAnalysis {

	private static final FloatFloatComponent MakeFloatFloatSwap (
		final JulianDate dtEffective,
		final boolean bFXMTM,
		final String strPayCurrency,
		final String strCouponCurrency,
		final String strMaturityTenor,
		final int iTenorInMonthsReference,
		final int iTenorInMonthsDerived)
		throws Exception
	{
		ComposableFloatingUnitSetting cfusReference = new ComposableFloatingUnitSetting (
			iTenorInMonthsReference + "M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			ForwardLabel.Create (
				strCouponCurrency,
				iTenorInMonthsReference + "M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		ComposableFloatingUnitSetting cfusDerived = new ComposableFloatingUnitSetting (
			iTenorInMonthsDerived + "M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			ForwardLabel.Create (
				strCouponCurrency,
				iTenorInMonthsDerived + "M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		CompositePeriodSetting cpsReference = new CompositePeriodSetting (
			12 / iTenorInMonthsReference,
			iTenorInMonthsReference + "M",
			strPayCurrency,
			null,
			-1.,
			null,
			null,
			bFXMTM ? null : new FixingSetting (
				FixingSetting.FIXING_PRESET_STATIC,
				null,
				dtEffective.julian()
			),
			null
		);

		CompositePeriodSetting cpsDerived = new CompositePeriodSetting (
			12 / iTenorInMonthsDerived,
			iTenorInMonthsDerived + "M",
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

		List<Integer> lsReferenceStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
			dtEffective,
			iTenorInMonthsReference + "M",
			strMaturityTenor,
			null
		);

		List<Integer> lsDerivedStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
			dtEffective,
			iTenorInMonthsDerived + "M",
			strMaturityTenor,
			null
		);

		Stream referenceStream = new Stream (
			CompositePeriodBuilder.FloatingCompositeUnit (
				lsReferenceStreamEdgeDate,
				cpsReference,
				cfusReference
			)
		);

		Stream derivedStream = new Stream (
			CompositePeriodBuilder.FloatingCompositeUnit (
				lsDerivedStreamEdgeDate,
				cpsDerived,
				cfusDerived
			)
		);

		CashSettleParams csp = new CashSettleParams (
			0,
			strPayCurrency,
			0
		);

		return new FloatFloatComponent (
			referenceStream,
			derivedStream,
			csp
		);
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
		final double dblForward1FXCorr,
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
			forwardLabel1,
			fxLabel,
			new Flat (dblForward1FXCorr)
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
		final FloatFloatComponent[] aFloatFloat,
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
		final double dblForward1FXCorr,
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
			dblForward1FXCorr,
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
			FormatUtil.FormatDouble (dblForward1FXCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForward2FXCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblFundingFXCorr, 2, 0, 100.) + "%] = ";

		for (int i = 0; i < aFloatFloat.length; ++i) {
			CaseInsensitiveTreeMap<Double> mapOutput = aFloatFloat[i].value (
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
		double dblEUR3MForwardRate = 0.02;
		double dblEUR6MForwardRate = 0.025;
		double dblUSDFundingRate = 0.02;
		double dblUSDEURFXRate = 1. / 1.35;

		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		JulianDate dtToday = org.drip.analytics.date.DateUtil.Today();

		ValuationParams valParams = new ValuationParams (
			dtToday,
			dtToday,
			"EUR"
		);

		MergedDiscountForwardCurve dcUSDFunding = ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
			dtToday,
			"USD",
			dblUSDFundingRate
		);

		ForwardLabel friEUR3M = ForwardLabel.Create (
			"EUR",
			"3M"
		);

		ForwardCurve fcEUR3M = ScenarioForwardCurveBuilder.FlatForwardForwardCurve (
			dtToday,
			friEUR3M,
			dblEUR3MForwardRate
		);

		ForwardLabel friEUR6M = ForwardLabel.Create (
			"EUR",
			"6M"
		);

		ForwardCurve fcEUR6M = ScenarioForwardCurveBuilder.FlatForwardForwardCurve (
			dtToday,
			friEUR6M,
			dblEUR6MForwardRate
		);

		CurrencyPair cp = CurrencyPair.FromCode ("USD/EUR");

		FloatFloatComponent floatFloatMTM = MakeFloatFloatSwap (
			dtToday,
			true,
			"USD",
			"EUR",
			"2Y",
			6,
			3
		);

		floatFloatMTM.setPrimaryCode ("EUR__USD__MTM::FLOAT::3M::6M::2Y");

		FloatFloatComponent floatFloatNonMTM = MakeFloatFloatSwap (
			dtToday,
			false,
			"USD",
			"EUR",
			"2Y",
			6,
			3
		);

		floatFloatNonMTM.setPrimaryCode ("EUR__USD__NONMTM::FLOAT::3M::6M::2Y");

		FXLabel fxLabel = FXLabel.Standard (cp);

		CurveSurfaceQuoteContainer mktParams = new CurveSurfaceQuoteContainer();

		mktParams.setFixing (
			dtToday,
			fxLabel,
			dblUSDEURFXRate
		);

		mktParams.setForwardState (fcEUR3M);

		mktParams.setForwardState (fcEUR6M);

		mktParams.setFundingState (dcUSDFunding);

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

		double[] adblEURForward3MVol = new double[] {
			0.1, 0.3, 0.5
		};

		double[] adblEURForward6MVol = new double[] {
			0.1, 0.3, 0.5
		};

		double[] adblUSDFundingVol = new double[] {
			0.1, 0.3, 0.5
		};

		double[] adblUSDEURFXVol = new double[] {
			0.1, 0.3, 0.5
		};

		double[] adblEUR3MUSDFundingCorr = new double[] {
			-0.2, 0.25
		};

		double[] adblEUR6MUSDFundingCorr = new double[] {
			-0.2, 0.25
		};

		double[] adblEUR3MUSDEURFXCorr = new double[] {
			-0.2, 0.25
		};

		double[] adblEUR6MUSDEURFXCorr = new double[] {
			-0.2, 0.25
		};

		double[] adblUSDFundingUSDEURFXCorr = new double[] {
			-0.2, 0.25
		};

		for (double dblEURForward3MVol : adblEURForward3MVol) {
			for (double dblEURForward6MVol : adblEURForward6MVol) {
				for (double dblUSDFundingVol : adblUSDFundingVol) {
					for (double dblUSDEURFXVol : adblUSDEURFXVol) {
						for (double dblEUR3MUSDFundingCorr : adblEUR3MUSDFundingCorr) {
							for (double dblEUR6MUSDFundingCorr : adblEUR6MUSDFundingCorr) {
								for (double dblEUR3MUSDEURFXCorr : adblEUR3MUSDEURFXCorr) {
									for (double dblEUR6MUSDEURFXCorr : adblEUR6MUSDEURFXCorr) {
										for (double dblUSDFundingUSDEURFXCorr : adblUSDFundingUSDEURFXCorr)
											VolCorrScenario (
												new FloatFloatComponent[] {
													floatFloatMTM,
													floatFloatNonMTM
												},
												valParams,
												mktParams,
												friEUR3M,
												friEUR6M,
												FundingLabel.Standard ("USD"),
												fxLabel,
												dblEURForward3MVol,
												dblEURForward6MVol,
												dblUSDFundingVol,
												dblUSDEURFXVol,
												dblEUR3MUSDFundingCorr,
												dblEUR6MUSDFundingCorr,
												dblEUR3MUSDEURFXCorr,
												dblEUR6MUSDEURFXCorr,
												dblUSDFundingUSDEURFXCorr
											);
									}
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
