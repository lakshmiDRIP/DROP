
package org.drip.sample.xccy;

import org.drip.analytics.date.*;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.market.otc.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.*;
import org.drip.product.params.CurrencyPair;
import org.drip.product.rates.FloatFloatComponent;
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
 * <i>OTCCrossCurrencySwaps</i> demonstrates the Construction and Valuation of the Cross-Currency Floating
 * 	Swap of OTC contracts.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/xccy/README.md">OTC Cross Currency Swaps Definition</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OTCCrossCurrencySwaps {

	private static final FloatFloatComponent OTCCrossCurrencyFloatFloat (
		final String strReferenceCurrency,
		final String strDerivedCurrency,
		final JulianDate dtSpot,
		final String strMaturityTenor,
		final double dblBasis,
		final double dblDerivedNotionalScaler)
	{
		CrossFloatSwapConvention ccfc = CrossFloatConventionContainer.ConventionFromJurisdiction (
			strReferenceCurrency,
			strDerivedCurrency
		);

		return ccfc.createFloatFloatComponent (
			dtSpot,
			strMaturityTenor,
			dblBasis,
			1.,
			-1. * dblDerivedNotionalScaler
		);
	}

	private static final void OTCCrossCurrencyRun (
		final JulianDate dtSpot,
		final String strReferenceCurrency,
		final String strDerivedCurrency,
		final String strMaturityTenor,
		final double dblBasis,
		final double dblReferenceDerivedFXRate)
		throws Exception
	{
		double dblReferenceFundingRate = 0.02;
		double dblDerived3MForwardRate = 0.02;

		double dblReferenceFundingVol = 0.3;
		double dblDerivedForward3MVol = 0.3;
		double dblReferenceDerivedFXVol = 0.3;

		double dblDerived3MReferenceDerivedFXCorr = 0.1;
		double dblReferenceFundingDerived3MCorr = 0.1;
		double dblReferenceFundingReferenceDerivedFXCorr = 0.1;

		MergedDiscountForwardCurve dcReferenceFunding = ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
			dtSpot,
			strReferenceCurrency,
			dblReferenceFundingRate
		);

		ForwardLabel friDerived3M = ForwardLabel.Create (
			strDerivedCurrency,
			"3M"
		);

		ForwardCurve fcDerived3M = ScenarioForwardCurveBuilder.FlatForwardForwardCurve (
			dtSpot,
			friDerived3M,
			dblDerived3MForwardRate
		);

		CurrencyPair cp = CurrencyPair.FromCode (
			strReferenceCurrency + "/" + strDerivedCurrency
		);

		FXLabel fxLabel = FXLabel.Standard (cp);

		FundingLabel fundingLabelReference = org.drip.state.identifier.FundingLabel.Standard (
			strReferenceCurrency
		);

		CurveSurfaceQuoteContainer mktParams = new CurveSurfaceQuoteContainer();

		mktParams.setForwardState (
			fcDerived3M
		);

		mktParams.setFundingState (
			dcReferenceFunding
		);

		mktParams.setFXState (
			ScenarioFXCurveBuilder.CubicPolynomialCurve (
				"FX::" + cp.code(),
				dtSpot,
				cp,
				new String[] {"10Y"},
				new double[] {dblReferenceDerivedFXRate},
				dblReferenceDerivedFXRate
			)
		);

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				dtSpot.julian(),
				VolatilityLabel.Standard (friDerived3M),
				strDerivedCurrency,
				dblDerivedForward3MVol
			)
		);

		mktParams.setFundingVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				dtSpot.julian(),
				VolatilityLabel.Standard (fundingLabelReference),
				strReferenceCurrency,
				dblReferenceFundingVol
			)
		);

		mktParams.setFXVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				dtSpot.julian(),
				VolatilityLabel.Standard (fxLabel),
				strDerivedCurrency,
				dblReferenceDerivedFXVol
			)
		);

		mktParams.setForwardFundingCorrelation (
			friDerived3M,
			fundingLabelReference,
			new FlatUnivariate (
				dblReferenceFundingDerived3MCorr
			)
		);

		mktParams.setForwardFXCorrelation (
			friDerived3M,
			fxLabel,
			new FlatUnivariate (
				dblDerived3MReferenceDerivedFXCorr
			)
		);

		mktParams.setFundingFXCorrelation (
			fundingLabelReference,
			fxLabel,
			new FlatUnivariate (
				dblReferenceFundingReferenceDerivedFXCorr
			)
		);

		FloatFloatComponent xccySwap = OTCCrossCurrencyFloatFloat (
			strReferenceCurrency,
			strDerivedCurrency,
			dtSpot,
			strMaturityTenor,
			dblBasis,
			1. / dblReferenceDerivedFXRate
		);

		xccySwap.setPrimaryCode (
			strDerivedCurrency + "_" + strReferenceCurrency + "_OTC::FLOATFLOAT::" + strMaturityTenor
		);

		mktParams.setFixing (
			xccySwap.effectiveDate(),
			fxLabel,
			dblReferenceDerivedFXRate
		);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strReferenceCurrency + "," + strDerivedCurrency
		);

		CaseInsensitiveTreeMap<Double> mapXCcyOutput = xccySwap.value (
			valParams,
			null,
			mktParams,
			null
		);

		System.out.println (
			"\t| " + xccySwap.name() + "  [" + xccySwap.effectiveDate() + " -> " + xccySwap.maturityDate() + "]  =>  " +
			FormatUtil.FormatDouble (mapXCcyOutput.get ("Price"), 1, 2, 1.) + "  |  " +
			FormatUtil.FormatDouble (mapXCcyOutput.get ("DerivedParBasisSpread"), 1, 2, 1.) + "  |  " +
			FormatUtil.FormatDouble (mapXCcyOutput.get ("ReferenceParBasisSpread"), 1, 2, 1.) + "  |  " +
			FormatUtil.FormatDouble (mapXCcyOutput.get ("DerivedCleanDV01"), 1, 2, 10000.) + "  |  " +
			FormatUtil.FormatDouble (mapXCcyOutput.get ("ReferenceCleanDV01"), 1, 2, 10000.) + "  |"
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{

		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.Today();

		System.out.println ("\t---------------------------------------------------------");

		System.out.println ("\t\tCROSS-CURRENCY FLOAT-FLOAT COMPONENT RUNS");

		System.out.println ("\t---------------------------------------------------------");

		System.out.println ("\tL -> R:");

		System.out.println ("\t\tCross Currency Swap Name");

		System.out.println ("\t\tFloat-Float Effective");

		System.out.println ("\t\tFloat-Float Maturity");

		System.out.println ("\t\tPrice");

		System.out.println ("\t\tDerived Stream Par Basis Spread");

		System.out.println ("\t\tReference Stream Par Basis Spread");

		System.out.println ("\t\tAnnualized Derived Stream Duration");

		System.out.println ("\t\tAnnualized Reference Stream Duration");

		System.out.println ("\t------------------------------------------------------------------------------------------------------------------");

		OTCCrossCurrencyRun (dtSpot, "USD", "AUD", "2Y", 0.0003, 0.7769);

		OTCCrossCurrencyRun (dtSpot, "USD", "CAD", "2Y", 0.0003, 0.7861);

		OTCCrossCurrencyRun (dtSpot, "USD", "CHF", "2Y", 0.0003, 1.0811);

		OTCCrossCurrencyRun (dtSpot, "USD", "CLP", "2Y", 0.0003, 0.0016);

		OTCCrossCurrencyRun (dtSpot, "USD", "DKK", "2Y", 0.0003, 0.1517);

		OTCCrossCurrencyRun (dtSpot, "USD", "EUR", "2Y", 0.0003, 1.1294);

		OTCCrossCurrencyRun (dtSpot, "USD", "GBP", "2Y", 0.0003, 1.5004);

		OTCCrossCurrencyRun (dtSpot, "USD", "JPY", "2Y", 0.0003, 0.0085);

		OTCCrossCurrencyRun (dtSpot, "USD", "MXN", "2Y", 0.0003, 0.0666);

		OTCCrossCurrencyRun (dtSpot, "USD", "NOK", "2Y", 0.0003, 0.1288);

		OTCCrossCurrencyRun (dtSpot, "USD", "PLN", "2Y", 0.0003, 0.2701);

		OTCCrossCurrencyRun (dtSpot, "USD", "SEK", "2Y", 0.0003, 0.1211);

		System.out.println ("\t------------------------------------------------------------------------------------------------------------------");

		EnvManager.TerminateEnv();
	}
}
