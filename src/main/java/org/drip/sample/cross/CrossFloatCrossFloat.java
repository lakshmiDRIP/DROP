
package org.drip.sample.cross;

import java.util.*;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.*;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.numerical.common.*;
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
 * <i>CrossFloatCrossFloat</i> demonstrates the construction, usage, and eventual valuation of the
 * Mark-to-market float-float swap with a 3M EUR Floater leg that pays in USD, and a 6M EUR Floater leg that
 * pays in USD. Comparison is done across MTM and non-MTM fixed Leg Counterparts.
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

public class CrossFloatCrossFloat {

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
		double dblUSDFundingRate = 0.02;
		double dblEUR3MForwardRate = 0.02;
		double dblEUR6MForwardRate = 0.025;
		double dblUSDEURFXRate = 1. / 1.35;

		double dblUSDFundingVol = 0.3;
		double dblEURForward3MVol = 0.3;
		double dblEURForward6MVol = 0.3;
		double dblUSDEURFXVol = 0.3;

		double dblEUR3MUSDEURFXCorr = 0.1;
		double dblEUR6MUSDEURFXCorr = 0.1;
		double dblUSDFundingEUR3MCorr = 0.1;
		double dblUSDFundingEUR6MCorr = 0.1;
		double dblUSDFundingUSDEURFXCorr = 0.1;

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

		FundingLabel fundingLabelUSD = org.drip.state.identifier.FundingLabel.Standard ("USD");

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

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				valParams.valueDate(),
				VolatilityLabel.Standard (friEUR3M),
				"EUR",
				dblEURForward3MVol
			)
		);

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				valParams.valueDate(),
				VolatilityLabel.Standard (friEUR6M),
				"EUR",
				dblEURForward6MVol
			)
		);

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				valParams.valueDate(),
				VolatilityLabel.Standard (fundingLabelUSD),
				"USD",
				dblUSDFundingVol
			)
		);

		mktParams.setFXVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				valParams.valueDate(),
				VolatilityLabel.Standard (fxLabel),
				"USD",
				dblUSDEURFXVol
			)
		);

		mktParams.setForwardFundingCorrelation (
			friEUR3M,
			fundingLabelUSD,
			new FlatUnivariate (dblUSDFundingEUR3MCorr)
		);

		mktParams.setForwardFundingCorrelation (
			friEUR6M,
			fundingLabelUSD,
			new FlatUnivariate (dblUSDFundingEUR6MCorr)
		);

		mktParams.setForwardFXCorrelation (
			friEUR3M,
			fxLabel,
			new FlatUnivariate (dblEUR3MUSDEURFXCorr)
		);

		mktParams.setForwardFXCorrelation (
			friEUR6M,
			fxLabel,
			new FlatUnivariate (dblEUR6MUSDEURFXCorr)
		);

		mktParams.setFundingFXCorrelation (
			fundingLabelUSD,
			fxLabel,
			new FlatUnivariate (dblUSDFundingUSDEURFXCorr)
		);

		CaseInsensitiveTreeMap<Double> mapMTMOutput = floatFloatMTM.value (
			valParams,
			null,
			mktParams,
			null
		);

		CaseInsensitiveTreeMap<Double> mapNonMTMOutput = floatFloatNonMTM.value (
			valParams,
			null,
			mktParams,
			null
		);

		for (Map.Entry<String, Double> me : mapMTMOutput.entrySet()) {
			String strKey = me.getKey();

			if (null != me.getValue() && null != mapNonMTMOutput.get (strKey)) {
				double dblMTMMeasure = me.getValue();

				double dblNonMTMMeasure = mapNonMTMOutput.get (strKey);

				String strReconcile = NumberUtil.WithinTolerance (
					dblMTMMeasure,
					dblNonMTMMeasure,
					1.e-08,
					1.e-04
				) ? "RECONCILES" : "DOES NOT RECONCILE";

				System.out.println ("\t" +
					FormatUtil.FormatDouble (dblMTMMeasure, 1, 8, 1.) + " | " +
					FormatUtil.FormatDouble (dblNonMTMMeasure, 1, 8, 1.) + " | " +
					strReconcile + " <= " + strKey);
			}
		}

		EnvManager.TerminateEnv();
	}
}
