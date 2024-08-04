
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
 * <i>FloatFloatFloatFloatAnalysis</i> demonstrates the Funding Volatility, Forward Volatility, FX
 * Volatility, Funding/Forward Correlation, Funding/FX Correlation, and Forward/FX Correlation of the Cross
 * Currency Basis Swap built out of a pair of float-float swaps.
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

public class FloatFloatFloatFloatAnalysis {

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
		final ForwardLabel forwardReferenceLabel1,
		final ForwardLabel forwardReferenceLabel2,
		final ForwardLabel forwardDerivedLabel1,
		final ForwardLabel forwardDerivedLabel2,
		final FundingLabel fundingLabel,
		final FXLabel fxLabel,
		final double dblForwardReference1Vol,
		final double dblForwardReference2Vol,
		final double dblForwardDerived1Vol,
		final double dblForwardDerived2Vol,
		final double dblFundingVol,
		final double dblFXVol,
		final double dblForwardReference1FundingCorr,
		final double dblForwardReference2FundingCorr,
		final double dblForwardDerived1FundingCorr,
		final double dblForwardDerived2FundingCorr,
		final double dblForwardReference1FXCorr,
		final double dblForwardReference2FXCorr,
		final double dblForwardDerived1FXCorr,
		final double dblForwardDerived2FXCorr,
		final double dblFundingFXCorr)
		throws Exception
	{
		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (forwardReferenceLabel1),
				forwardReferenceLabel1.currency(),
				dblForwardReference1Vol
			)
		);

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (forwardReferenceLabel2),
				forwardReferenceLabel2.currency(),
				dblForwardReference2Vol
			)
		);

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (forwardDerivedLabel1),
				forwardDerivedLabel1.currency(),
				dblForwardDerived1Vol
			)
		);

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (forwardDerivedLabel2),
				forwardDerivedLabel2.currency(),
				dblForwardDerived2Vol
			)
		);

		mktParams.setFundingVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (fundingLabel),
				forwardDerivedLabel1.currency(),
				dblFundingVol
			)
		);

		mktParams.setFXVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				iValueDate,
				VolatilityLabel.Standard (fxLabel),
				forwardDerivedLabel1.currency(),
				dblFXVol
			)
		);

		mktParams.setForwardFundingCorrelation (
			forwardReferenceLabel1,
			fundingLabel,
			new Flat (dblForwardReference1FundingCorr)
		);

		mktParams.setForwardFundingCorrelation (
			forwardReferenceLabel2,
			fundingLabel,
			new Flat (dblForwardReference2FundingCorr)
		);

		mktParams.setForwardFundingCorrelation (
			forwardDerivedLabel1,
			fundingLabel,
			new Flat (dblForwardDerived1FundingCorr)
		);

		mktParams.setForwardFundingCorrelation (
			forwardDerivedLabel2,
			fundingLabel,
			new Flat (dblForwardDerived2FundingCorr)
		);

		mktParams.setForwardFXCorrelation (
			forwardReferenceLabel1,
			fxLabel,
			new Flat (dblForwardReference1FXCorr)
		);

		mktParams.setForwardFXCorrelation (
			forwardReferenceLabel2,
			fxLabel,
			new Flat (dblForwardReference2FXCorr)
		);

		mktParams.setForwardFXCorrelation (
			forwardDerivedLabel1,
			fxLabel,
			new Flat (dblForwardDerived1FXCorr)
		);

		mktParams.setForwardFXCorrelation (
			forwardDerivedLabel2,
			fxLabel,
			new Flat (dblForwardDerived2FXCorr)
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
		final ForwardLabel forwardReferenceLabel1,
		final ForwardLabel forwardReferenceLabel2,
		final ForwardLabel forwardDerivedLabel1,
		final ForwardLabel forwardDerivedLabel2,
		final FundingLabel fundingLabel,
		final FXLabel fxLabel,
		final double dblForwardReference1Vol,
		final double dblForwardReference2Vol,
		final double dblForwardDerived1Vol,
		final double dblForwardDerived2Vol,
		final double dblFundingVol,
		final double dblFXVol,
		final double dblForwardReference1FundingCorr,
		final double dblForwardReference2FundingCorr,
		final double dblForwardDerived1FundingCorr,
		final double dblForwardDerived2FundingCorr,
		final double dblForwardReference1FXCorr,
		final double dblForwardReference2FXCorr,
		final double dblForwardDerived1FXCorr,
		final double dblForwardDerived2FXCorr,
		final double dblFundingFXCorr)
		throws Exception
	{
		SetMarketParams (
			valParams.valueDate(),
			mktParams,
			forwardReferenceLabel1,
			forwardReferenceLabel2,
			forwardDerivedLabel1,
			forwardDerivedLabel2,
			fundingLabel,
			fxLabel,
			dblForwardReference1Vol,
			dblForwardReference2Vol,
			dblForwardDerived1Vol,
			dblForwardDerived2Vol,
			dblFundingVol,
			dblFXVol,
			dblForwardReference1FundingCorr,
			dblForwardReference2FundingCorr,
			dblForwardDerived1FundingCorr,
			dblForwardDerived2FundingCorr,
			dblForwardReference1FXCorr,
			dblForwardReference2FXCorr,
			dblForwardDerived1FXCorr,
			dblForwardDerived2FXCorr,
			dblFundingFXCorr
		);

		String strDump = "\t[" +
			FormatUtil.FormatDouble (dblForwardReference1Vol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardReference2Vol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardDerived1Vol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardDerived2Vol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblFundingVol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblFXVol, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardReference1FundingCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardReference2FundingCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardDerived1FundingCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardDerived2FundingCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardReference1FXCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardReference2FXCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardDerived1FXCorr, 2, 0, 100.) + "%," +
			FormatUtil.FormatDouble (dblForwardDerived2FXCorr, 2, 0, 100.) + "%," +
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
		String strReferenceCurrency = "USD";
		String strDerivedCurrency = "EUR";

		double dblReference3MForwardRate = 0.00750;
		double dblReference6MForwardRate = 0.01000;
		double dblDerived3MForwardRate = 0.00375;
		double dblDerived6MForwardRate = 0.00625;
		double dblReferenceFundingRate = 0.02;
		double dblReferenceDerivedFXRate = 1. / 1.28;

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

		ForwardLabel fri3MReference = ForwardLabel.Create (
			strReferenceCurrency,
			"3M"
		);

		ForwardLabel fri6MReference = ForwardLabel.Create (
			strReferenceCurrency,
			"6M"
		);

		ForwardLabel fri3MDerived = ForwardLabel.Create (
			strDerivedCurrency,
			"3M"
		);

		ForwardLabel fri6MDerived = ForwardLabel.Create (
			strDerivedCurrency,
			"6M"
		);

		FundingLabel fundingLabelReference = FundingLabel.Standard (strReferenceCurrency);

		CurrencyPair cp = CurrencyPair.FromCode (strReferenceCurrency + "/" + strDerivedCurrency);

		FXLabel fxLabel = FXLabel.Standard (cp);

		FloatFloatComponent floatFloatReference = MakeFloatFloatSwap (
			dtToday,
			false,
			strReferenceCurrency,
			strReferenceCurrency,
			"2Y",
			6,
			3
		);

		floatFloatReference.setPrimaryCode (
			"FLOAT::FLOAT::" + strReferenceCurrency + "::" + strReferenceCurrency + "_3M::" + strReferenceCurrency + "_6M::2Y"
		);

		FloatFloatComponent floatFloatDerivedMTM = MakeFloatFloatSwap (
			dtToday,
			true,
			strReferenceCurrency,
			strDerivedCurrency,
			"2Y",
			6,
			3
		);

		floatFloatDerivedMTM.setPrimaryCode (
			"FLOAT::FLOAT::MTM::" + strReferenceCurrency + "::" + strDerivedCurrency + "_3M::" + strDerivedCurrency + "_6M::2Y"
		);

		ComponentPair cpMTM = new ComponentPair (
			"FFFF_MTM",
			floatFloatReference,
			floatFloatDerivedMTM,
			null
		);

		FloatFloatComponent floatFloatDerivedNonMTM = MakeFloatFloatSwap (
			dtToday,
			false,
			strReferenceCurrency,
			strDerivedCurrency,
			"2Y",
			6,
			3
		);

		floatFloatDerivedNonMTM.setPrimaryCode (
			"FLOAT::FLOAT::NONMTM::" + strReferenceCurrency + "::" + strDerivedCurrency + "_3M::" + strDerivedCurrency + "_6M::2Y"
		);

		ComponentPair cpNonMTM = new ComponentPair (
			"FFFF_NonMTM",
			floatFloatReference,
			floatFloatDerivedNonMTM,
			null
		);

		CurveSurfaceQuoteContainer mktParams = new CurveSurfaceQuoteContainer();

		mktParams.setFixing (
			dtToday,
			fxLabel,
			dblReferenceDerivedFXRate
		);

		mktParams.setForwardState (
			ScenarioForwardCurveBuilder.FlatForwardForwardCurve (
				dtToday,
				fri3MReference,
				dblReference3MForwardRate
			)
		);

		mktParams.setForwardState (
			ScenarioForwardCurveBuilder.FlatForwardForwardCurve (
				dtToday,
				fri6MReference,
				dblReference6MForwardRate
			)
		);

		mktParams.setForwardState (
			ScenarioForwardCurveBuilder.FlatForwardForwardCurve (
				dtToday,
				fri3MDerived,
				dblDerived3MForwardRate
			)
		);

		mktParams.setForwardState (
			ScenarioForwardCurveBuilder.FlatForwardForwardCurve (
				dtToday,
				fri6MDerived,
				dblDerived6MForwardRate
			)
		);

		mktParams.setFundingState (
			ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
				dtToday,
				strReferenceCurrency,
				dblReferenceFundingRate
			)
		);

		mktParams.setFXState (
			ScenarioFXCurveBuilder.CubicPolynomialCurve (
				fxLabel.fullyQualifiedName(),
				dtToday,
				cp,
				new String[] {"10Y"},
				new double[] {dblReferenceDerivedFXRate},
				dblReferenceDerivedFXRate
			)
		);

		double[] adblReference3MForwardVol = new double[] {
			0.1, 0.4
		};
		double[] adblReference6MForwardVol = new double[] {
			0.1, 0.4
		};
		double[] adblDerived3MForwardVol = new double[] {
			0.1, 0.4
		};
		double[] adblDerived6MForwardVol = new double[] {
			0.1, 0.4
		};
		double[] adblReferenceFundingVol = new double[] {
			0.1, 0.4
		};
		double[] adblReferenceDerivedFXVol = new double[] {
			0.1, 0.4
		};

		double[] adblReference3MForwardFundingCorr = new double[] {
			-0.1, 0.2
		};
		double[] adblReference6MForwardFundingCorr = new double[] {
			-0.1, 0.2
		};
		double[] adblDerived3MForwardFundingCorr = new double[] {
			-0.1, 0.2
		};
		double[] adblDerived6MForwardFundingCorr = new double[] {
			-0.1, 0.2
		};

		double[] adblReference3MForwardFXCorr = new double[] {
			-0.1, 0.2
		};
		double[] adblReference6MForwardFXCorr = new double[] {
			-0.1, 0.2
		};
		double[] adblDerived3MForwardFXCorr = new double[] {
			-0.1, 0.2
		};
		double[] adblDerived6MForwardFXCorr = new double[] {
			-0.1, 0.2
		};

		double[] adblFundingFXCorr = new double[] {
			-0.1, 0.2
		};

		for (double dblReference3MForwardVol : adblReference3MForwardVol) {
			for (double dblReference6MForwardVol : adblReference6MForwardVol) {
				for (double dblDerived3MForwardVol : adblDerived3MForwardVol) {
					for (double dblDerived6MForwardVol : adblDerived6MForwardVol) {
						for (double dblReferenceFundingVol : adblReferenceFundingVol) {
							for (double dblReferenceDerivedFXVol : adblReferenceDerivedFXVol) {
								for (double dblReference3MForwardFundingCorr : adblReference3MForwardFundingCorr) {
									for (double dblReference6MForwardFundingCorr : adblReference6MForwardFundingCorr) {
										for (double dblDerived3MForwardFundingCorr : adblDerived3MForwardFundingCorr) {
											for (double dblDerived6MForwardFundingCorr : adblDerived6MForwardFundingCorr) {
												for (double dblReference3MForwardFXCorr : adblReference3MForwardFXCorr) {
													for (double dblReference6MForwardFXCorr : adblReference6MForwardFXCorr) {
														for (double dblDerived3MForwardFXCorr : adblDerived3MForwardFXCorr) {
															for (double dblDerived6MForwardFXCorr : adblDerived6MForwardFXCorr) {
																for (double dblFundingFXCorr : adblFundingFXCorr)
																	VolCorrScenario (
																		new ComponentPair[] {
																			cpMTM,
																			cpNonMTM
																		},
																		valParams,
																		mktParams,
																		fri3MReference,
																		fri6MReference,
																		fri3MDerived,
																		fri6MDerived,
																		fundingLabelReference,
																		fxLabel,
																		dblReference3MForwardVol,
																		dblReference6MForwardVol,
																		dblDerived3MForwardVol,
																		dblDerived6MForwardVol,
																		dblReferenceFundingVol,
																		dblReferenceDerivedFXVol,
																		dblReference3MForwardFundingCorr,
																		dblReference6MForwardFundingCorr,
																		dblDerived3MForwardFundingCorr,
																		dblDerived6MForwardFundingCorr,
																		dblReference3MForwardFXCorr,
																		dblReference6MForwardFXCorr,
																		dblDerived3MForwardFXCorr,
																		dblDerived6MForwardFXCorr,
																		dblFundingFXCorr
																	);
															}
														}
													}
												}
											}
										}
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
