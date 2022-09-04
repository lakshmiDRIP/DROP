
package org.drip.historical.engine;

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
 * <i>TreasuryBondExplainProcessor</i> contains the Functionality associated with the Horizon Analysis of the
 * Treasury Bond.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/README.md">Historical State Processing Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/engine/README.md">Product Horizon Change Explain Engine</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryBondExplainProcessor extends org.drip.historical.engine.HorizonChangeExplainProcessor {

	/**
	 * TreasuryBondExplainProcessor Constructor
	 * 
	 * @param tsyComponent The Treasury Component
	 * @param strMarketMeasureName The Market Measure Name
	 * @param dblMarketMeasureValue The Market Measure Value
	 * @param dtFirst First Date
	 * @param dtSecond Second Date
	 * @param csqcFirst First Market Parameters
	 * @param csqcSecond Second Market Parameters
	 * @param mapCSQCRollDown Map of the Roll Down Market Parameters
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TreasuryBondExplainProcessor (
		final org.drip.product.govvie.TreasuryComponent tsyComponent,
		final java.lang.String strMarketMeasureName,
		final double dblMarketMeasureValue,
		final org.drip.analytics.date.JulianDate dtFirst,
		final org.drip.analytics.date.JulianDate dtSecond,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqcFirst,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqcSecond,
		final
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			mapCSQCRollDown)
		throws java.lang.Exception
	{
		super (tsyComponent, 0, strMarketMeasureName, dblMarketMeasureValue, dtFirst, dtSecond, csqcFirst,
			csqcSecond, mapCSQCRollDown);
	}

	@Override public org.drip.historical.engine.MarketMeasureRollDown rollDownMeasureMap()
	{
		org.drip.product.definition.Component comp = component();

		int iMaturityDate = comp.maturityDate().julian();

		org.drip.historical.engine.MarketMeasureRollDown mmrd = null;

		org.drip.state.identifier.GovvieLabel govvieLabel = comp.govvieLabel();

		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			mapCSQCRollDown = rollDownMarketParameters();

		for (java.lang.String strRollDownTenor : mapCSQCRollDown.keySet()) {
			org.drip.state.govvie.GovvieCurve gc = mapCSQCRollDown.get (strRollDownTenor).govvieState
				(govvieLabel);

			try {
				double dblMarketMeasureRollDown = gc.yld (iMaturityDate);

				if ("Native".equalsIgnoreCase (strRollDownTenor))
					mmrd = new org.drip.historical.engine.MarketMeasureRollDown (dblMarketMeasureRollDown);
				else
					mmrd.add (strRollDownTenor, dblMarketMeasureRollDown);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return mmrd;
	}

	@Override public org.drip.historical.attribution.PositionMarketSnap snapFirstMarketValue()
	{
		org.drip.analytics.date.JulianDate dtValuation = firstDate();

		org.drip.product.govvie.TreasuryComponent tsyComponent = (org.drip.product.govvie.TreasuryComponent)
			component();

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = firstMarketParameters();

		org.drip.state.identifier.GovvieLabel govvieLabel = tsyComponent.govvieLabel();

		org.drip.analytics.date.JulianDate dtEffective = tsyComponent.effectiveDate();

		org.drip.analytics.date.JulianDate dtMaturity = tsyComponent.maturityDate();

		org.drip.state.govvie.GovvieCurve gc = csqc.govvieState (govvieLabel);

		double dblFixedCoupon = tsyComponent.couponSetting().couponRate();

		java.lang.String strCurrency = tsyComponent.currency();

		int iValuationDate = dtValuation.julian();

		org.drip.param.valuation.ValuationParams valParams = org.drip.param.valuation.ValuationParams.Spot
			(iValuationDate);

		org.drip.historical.engine.MarketMeasureRollDown mmrd = rollDownMeasureMap();

		if (null == mmrd) return null;

		double dblRollDownInnate = mmrd.innate();

		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> mapHorizonMetric =
			mmrd.horizon();

		java.lang.String strAccrualDC = tsyComponent.accrualDC();

		try {
			double dblYield = gc.yld (dtMaturity.julian());

			double dblAccrued = tsyComponent.accrued (iValuationDate, csqc);

			double dblCleanPrice = tsyComponent.priceFromYield (valParams, csqc, null, dblYield);

			double dblYieldSensitivity = 10000. * tsyComponent.modifiedDurationFromYield (valParams, csqc,
				null, dblYield);

			double dblCumulativeCouponDCF = org.drip.analytics.daycount.Convention.YearFraction
				(dtEffective.julian(), iValuationDate, strAccrualDC, false,
					org.drip.analytics.daycount.ActActDCParams.FromFrequency (gc.freq()), strCurrency);

			org.drip.historical.attribution.PositionMarketSnap pms = new
				org.drip.historical.attribution.PositionMarketSnap (dtValuation, dblCleanPrice);

			if (!pms.setR1 ("Accrued", dblAccrued)) return null;

			if (!pms.setC1 ("AccruedDC", strAccrualDC)) return null;

			if (!pms.setR1 ("CleanPrice", dblCleanPrice)) return null;

			if (!pms.setR1 ("CumulativeCouponAmount", dblCumulativeCouponDCF * dblFixedCoupon)) return null;

			if (!pms.setR1 ("CumulativeCouponDCF", dblCumulativeCouponDCF)) return null;

			if (!pms.setC1 ("Currency", strCurrency)) return null;

			if (!pms.setR1 ("DirtyPrice", dblCleanPrice + dblAccrued)) return null;

			if (!pms.setDate ("EffectiveDate", dtEffective)) return null;

			if (!pms.setC1 ("FixedAccrualDayCount", strAccrualDC)) return null;

			if (!pms.setR1 ("FixedCoupon", dblFixedCoupon)) return null;

			if (!pms.setDate ("MaturityDate", dtMaturity)) return null;

			if (!pms.setC1 ("MaturityTenor", tsyComponent.tenor())) return null;

			if (!pms.setR1 ("ModifiedDuration", dblYieldSensitivity)) return null;

			if (!pms.setR1 ("Yield", dblYield)) return null;

			if (!pms.setR1 ("YieldRollDown", dblRollDownInnate)) return null;

			for (java.lang.String strRollDownTenor : mapHorizonMetric.keySet()) {
				if (!pms.setR1 ("YieldRollDown" + strRollDownTenor, mapHorizonMetric.get (strRollDownTenor)))
					return null;
			}

			if (!pms.addManifestMeasureSnap ("Yield", dblYield, -1. * dblYieldSensitivity,
				dblRollDownInnate))
				return null;

			return pms;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public boolean updateFixings()
	{
		return true;
	}

	@Override public org.drip.historical.attribution.PositionMarketSnap snapSecondMarketValue()
	{
		org.drip.product.govvie.TreasuryComponent tsyComponent = (org.drip.product.govvie.TreasuryComponent)
			component();

		org.drip.analytics.date.JulianDate dtValuation = secondDate();

		int iValuationDate = dtValuation.julian();

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = secondMarketParameters();

		org.drip.param.valuation.ValuationParams valParams = org.drip.param.valuation.ValuationParams.Spot
			(iValuationDate);

		try {
			org.drip.state.govvie.GovvieCurve gc = csqc.govvieState (tsyComponent.govvieLabel());

			double dblYield = gc.yld (tsyComponent.maturityDate().julian());

			double dblCumulativeCouponDCF = org.drip.analytics.daycount.Convention.YearFraction
				(tsyComponent.effectiveDate().julian(), iValuationDate, tsyComponent.accrualDC(), false,
					org.drip.analytics.daycount.ActActDCParams.FromFrequency (gc.freq()),
						tsyComponent.currency());

			org.drip.historical.attribution.PositionMarketSnap pms = new
				org.drip.historical.attribution.PositionMarketSnap (dtValuation, tsyComponent.priceFromYield
					(valParams, csqc, null, dblYield));

			if (!pms.setR1 ("CumulativeCouponAmount", dblCumulativeCouponDCF *
				tsyComponent.couponSetting().couponRate()))
				return null;

			if (!pms.setR1 ("CumulativeCouponDCF", dblCumulativeCouponDCF)) return null;

			if (!pms.setR1 ("Yield", dblYield)) return null;

			if (!pms.addManifestMeasureSnap ("Yield", dblYield, -10000. *
				tsyComponent.modifiedDurationFromYield (valParams, csqc, null, dblYield), 0.))
				return null;

			return pms;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>
		crossHorizonDifferentialMetrics (
			final org.drip.historical.attribution.PositionMarketSnap pmsFirst,
			final org.drip.historical.attribution.PositionMarketSnap pmsSecond)
	{
		if (null == pmsFirst || null == pmsSecond) return null;

		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> mapDifferentialMetric = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		org.drip.analytics.date.JulianDate dtEffective = pmsFirst.date ("EffectiveDate");

		java.lang.String strAccrualDC = pmsFirst.c1 ("AccruedDC");

		java.lang.String strCalendar = pmsFirst.c1 ("Currency");

		int iDate1M = dtEffective.addTenor ("1M").julian();

		int iDate3M = dtEffective.addTenor ("3M").julian();

		int iEffectiveDate = dtEffective.julian();

		org.drip.analytics.daycount.ActActDCParams aap =
			org.drip.analytics.daycount.ActActDCParams.FromFrequency
				(((org.drip.product.govvie.TreasuryComponent) component()).freq());

		try {
			mapDifferentialMetric.put ("CumulativeCouponAmount", pmsSecond.r1 ("CumulativeCouponAmount") -
				pmsFirst.r1 ("CumulativeCouponAmount"));

			mapDifferentialMetric.put ("CumulativeCouponDCF", pmsSecond.r1 ("CumulativeCouponDCF") -
				pmsFirst.r1 ("CumulativeCouponDCF"));

			mapDifferentialMetric.put ("CumulativeCouponDCF1M",
				org.drip.analytics.daycount.Convention.YearFraction (iEffectiveDate, iDate1M, strAccrualDC,
					false, aap, strCalendar));

			mapDifferentialMetric.put ("CumulativeCouponDCF3M",
				org.drip.analytics.daycount.Convention.YearFraction (iEffectiveDate, iDate3M, strAccrualDC,
					false, aap, strCalendar));

			return mapDifferentialMetric;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
