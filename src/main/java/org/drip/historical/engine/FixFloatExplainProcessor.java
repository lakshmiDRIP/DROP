
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
 * <i>FixFloatExplainProcessor</i> contains the Functionality associated with the Horizon Analysis of the Fix
 * Float Swap.
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

public class FixFloatExplainProcessor extends org.drip.historical.engine.HorizonChangeExplainProcessor {

	/**
	 * FixFloatExplainProcessor Constructor
	 * 
	 * @param ffc The Fix Float Component
	 * @param iSettleLag The Component's Settle Lag
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

	public FixFloatExplainProcessor (
		final org.drip.product.rates.FixFloatComponent ffc,
		final int iSettleLag,
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
		super (ffc, iSettleLag, strMarketMeasureName, dblMarketMeasureValue, dtFirst, dtSecond, csqcFirst,
			csqcSecond, mapCSQCRollDown);
	}

	@Override public org.drip.historical.attribution.PositionMarketSnap snapFirstMarketValue()
	{
		org.drip.analytics.date.JulianDate dtFirst = firstDate();

		org.drip.product.rates.FixFloatComponent ffc = (org.drip.product.rates.FixFloatComponent)
			component();

		java.lang.String strPayCurrency = ffc.payCurrency();

		java.util.Map<java.lang.String, java.lang.Double> mapFixFloat = ffc.value
			(org.drip.param.valuation.ValuationParams.Spot (dtFirst.addBusDays (settleLag(),
				strPayCurrency).julian()), null, firstMarketParameters(), null);

		if (null == mapFixFloat || !mapFixFloat.containsKey ("Accrued") || !mapFixFloat.containsKey
			("CleanFixedDV01") || !mapFixFloat.containsKey ("CleanFloatingDV01") || !mapFixFloat.containsKey
				("CleanPV") || !mapFixFloat.containsKey ("CumulativeCouponAmount") ||
					!mapFixFloat.containsKey ("CumulativeCouponDCF") || !mapFixFloat.containsKey
						("DerivedCleanPV") || !mapFixFloat.containsKey ("DerivedCumulativeCouponAmount") ||
							!mapFixFloat.containsKey ("DerivedCumulativeCouponDCF") ||
								!mapFixFloat.containsKey ("ReferenceCleanPV") || !mapFixFloat.containsKey
									("ReferenceCumulativeCouponAmount") || !mapFixFloat.containsKey
										("ReferenceCumulativeCouponDCF") || !mapFixFloat.containsKey
											("SwapRate"))
			return null;

		double dblCleanPV = mapFixFloat.get ("CleanPV");

		double dblSwapRate = mapFixFloat.get ("SwapRate");

		double dblSwapRateSensitivity = 10000. * mapFixFloat.get ("CleanFixedDV01");

		org.drip.state.identifier.ForwardLabel forwardLabel = ffc.derivedStream().forwardLabel();

		org.drip.historical.engine.MarketMeasureRollDown mmrd = rollDownMeasureMap();

		if (null == mmrd) return null;

		double dblRollDownInnate = mmrd.innate();

		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> mapHorizonMetric =
			mmrd.horizon();

		try {
			org.drip.historical.attribution.PositionMarketSnap pms = new
				org.drip.historical.attribution.PositionMarketSnap (dtFirst, dblCleanPV);

			if (!pms.setR1 ("Accrued", mapFixFloat.get ("Accrued"))) return null;

			if (!pms.setR1 ("CleanFixedDV01", dblSwapRateSensitivity)) return null;

			if (!pms.setR1 ("CleanFloatingDV01", 10000. * mapFixFloat.get ("CleanFloatingDV01")))
				return null;

			if (!pms.setC1 ("CouponCurrency", forwardLabel.currency())) return null;

			if (!pms.setR1 ("CumulativeCouponAmount", mapFixFloat.get ("CumulativeCouponAmount")))
				return null;

			if (!pms.setR1 ("CumulativeCouponDCF", mapFixFloat.get ("CumulativeCouponDCF"))) return null;

			if (!pms.setR1 ("DerivedCleanPV", mapFixFloat.get ("DerivedCleanPV"))) return null;

			if (!pms.setDate ("EffectiveDate", ffc.effectiveDate())) return null;

			if (!pms.setC1 ("FixedAccrualDayCount", ffc.referenceStream().accrualDC())) return null;

			if (!pms.setR1 ("FixedCoupon", dblSwapRate)) return null;

			if (!pms.setR1 ("FixedCumulativeCouponAmount", mapFixFloat.get
				("ReferenceCumulativeCouponAmount")))
				return null;

			if (!pms.setR1 ("FixedCumulativeCouponDCF", mapFixFloat.get ("ReferenceCumulativeCouponDCF")))
				return null;

			if (!pms.setC1 ("FloatAccrualDayCount", forwardLabel.floaterIndex().dayCount())) return null;

			if (!pms.setR1 ("FloatCumulativeCouponAmount", mapFixFloat.get
				("DerivedCumulativeCouponAmount")))
				return null;

			if (!pms.setR1 ("FloatCumulativeCouponDCF", mapFixFloat.get ("DerivedCumulativeCouponDCF")))
				return null;

			if (!pms.setC1 ("FloaterLabel", forwardLabel.fullyQualifiedName())) return null;

			if (!pms.setDate ("MaturityDate", ffc.maturityDate())) return null;

			if (!pms.setC1 ("MaturityTenor", ffc.tenor())) return null;

			if (!pms.setC1 ("PayCurrency", strPayCurrency)) return null;

			if (!pms.setR1 ("ReferenceCleanPV", mapFixFloat.get ("ReferenceCleanPV"))) return null;

			if (!pms.setR1 ("SwapRate", dblSwapRate)) return null;

			if (!pms.setR1 ("SwapRateRollDown", dblRollDownInnate)) return null;

			for (java.lang.String strRollDownTenor : mapHorizonMetric.keySet()) {
				if (!pms.setR1 ("SwapRateRollDown" + strRollDownTenor, mapHorizonMetric.get
					(strRollDownTenor)))
					return null;
			}

			if (!pms.addManifestMeasureSnap ("SwapRate", dblSwapRate, -1. * dblSwapRateSensitivity,
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
		org.drip.product.rates.FixFloatComponent ffc = (org.drip.product.rates.FixFloatComponent)
			component();

		org.drip.product.rates.Stream floatingStream = ffc.derivedStream();

		int iDate = secondDate().julian();

		if (iDate > ffc.maturityDate().julian()) return false;

		int iEffectiveDate = ffc.effectiveDate().julian();

		if (iDate <= iEffectiveDate) iDate = iEffectiveDate;

		org.drip.analytics.cashflow.CompositePeriod cpFixing = floatingStream.containingPeriod (iDate);

		if (null == cpFixing) return false;

		org.drip.analytics.cashflow.ComposableUnitPeriod cupEnclosing = cpFixing.enclosingCUP (iDate);

		if (null == cupEnclosing || !(cupEnclosing instanceof
			org.drip.analytics.cashflow.ComposableUnitFloatingPeriod))
			return false;

		org.drip.param.market.CurveSurfaceQuoteContainer csqcFirst = firstMarketParameters();

		org.drip.state.identifier.ForwardLabel forwardLabel = floatingStream.forwardLabel();

		int iFixingDate = ((org.drip.analytics.cashflow.ComposableUnitFloatingPeriod)
			cupEnclosing).referenceIndexPeriod().fixingDate();

		try {
			double dblResetFixingRate = cupEnclosing.baseRate (csqcFirst);

			return csqcFirst.setFixing (iFixingDate, forwardLabel, dblResetFixingRate) &&
				secondMarketParameters().setFixing (iFixingDate, forwardLabel, dblResetFixingRate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override public org.drip.historical.attribution.PositionMarketSnap snapSecondMarketValue()
	{
		org.drip.analytics.date.JulianDate dtSecond = secondDate();

		org.drip.product.rates.FixFloatComponent ffc = (org.drip.product.rates.FixFloatComponent)
			component();

		java.util.Map<java.lang.String, java.lang.Double> mapFixFloat = ffc.value
			(org.drip.param.valuation.ValuationParams.Spot (dtSecond.addBusDays (settleLag(),
				ffc.payCurrency()).julian()), null, secondMarketParameters(), null);

		if (null == mapFixFloat || !mapFixFloat.containsKey ("CleanFixedDV01") || !mapFixFloat.containsKey
			("CleanPV") || !mapFixFloat.containsKey ("CumulativeCouponAmount") || !mapFixFloat.containsKey
				("CumulativeCouponDCF") || !mapFixFloat.containsKey ("DerivedCumulativeCouponAmount") ||
					!mapFixFloat.containsKey ("DerivedCumulativeCouponDCF") || !mapFixFloat.containsKey
						("ReferenceCumulativeCouponAmount") || !mapFixFloat.containsKey
							("ReferenceCumulativeCouponDCF") || !mapFixFloat.containsKey ("ResetDate") ||
								!mapFixFloat.containsKey ("ResetRate") || !mapFixFloat.containsKey
									("SwapRate"))
			return null;

		double dblSwapRate = mapFixFloat.get ("SwapRate");

		try {
			org.drip.historical.attribution.PositionMarketSnap pms = new
				org.drip.historical.attribution.PositionMarketSnap (dtSecond, mapFixFloat.get ("CleanPV"));

			if (!pms.setR1 ("CumulativeCouponAmount", mapFixFloat.get ("CumulativeCouponAmount")))
				return null;

			if (!pms.setR1 ("CumulativeCouponDCF", mapFixFloat.get ("CumulativeCouponDCF"))) return null;

			if (!pms.setR1 ("FixedCumulativeCouponAmount", mapFixFloat.get
				("ReferenceCumulativeCouponAmount")))
				return null;

			if (!pms.setR1 ("FixedCumulativeCouponDCF", mapFixFloat.get ("ReferenceCumulativeCouponDCF")))
				return null;

			if (!pms.setR1 ("FloatCumulativeCouponAmount", mapFixFloat.get
				("DerivedCumulativeCouponAmount")))
				return null;

			if (!pms.setR1 ("FloatCumulativeCouponDCF", mapFixFloat.get ("DerivedCumulativeCouponDCF")))
				return null;

			if (!pms.setDate ("ResetDate", new org.drip.analytics.date.JulianDate ((int) (double)
				mapFixFloat.get ("ResetDate"))))
				return null;

			if (!pms.setR1 ("ResetRate", mapFixFloat.get ("ResetRate"))) return null;

			if (!pms.setR1 ("SwapRate", dblSwapRate)) return null;

			if (!pms.addManifestMeasureSnap ("SwapRate", dblSwapRate, -10000. * mapFixFloat.get
				("CleanFixedDV01"), 0.))
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

		java.lang.String strFixedAccrualDayCount = pmsFirst.c1 ("FixedAccrualDayCount");

		java.lang.String strFloatAccrualDayCount = pmsFirst.c1 ("FloatAccrualDayCount");

		java.lang.String strCalendar = pmsFirst.c1 ("PayCurrency");

		int iDate1M = dtEffective.addTenor ("1M").julian();

		int iDate3M = dtEffective.addTenor ("3M").julian();

		int iEffectiveDate = dtEffective.julian();

		try {
			double dblFixedCumulativeCouponAmount = pmsSecond.r1 ("FixedCumulativeCouponAmount") -
				pmsFirst.r1 ("FixedCumulativeCouponAmount");

			double dblFixedCumulativeCouponDCF = pmsSecond.r1 ("FixedCumulativeCouponDCF") - pmsFirst.r1
				("FixedCumulativeCouponDCF");

			double dblFloatCumulativeCouponAmount = pmsSecond.r1 ("FloatCumulativeCouponAmount") -
				pmsFirst.r1 ("FloatCumulativeCouponAmount");

			double dblFloatCumulativeCouponDCF = pmsSecond.r1 ("FloatCumulativeCouponDCF") - pmsFirst.r1
				("FloatCumulativeCouponDCF");

			mapDifferentialMetric.put ("CumulativeCouponAmount", pmsSecond.r1 ("CumulativeCouponAmount") -
				pmsFirst.r1 ("CumulativeCouponAmount"));

			mapDifferentialMetric.put ("CumulativeCouponDCF", pmsSecond.r1 ("CumulativeCouponDCF") -
				pmsFirst.r1 ("CumulativeCouponDCF"));

			mapDifferentialMetric.put ("EffectiveFixedCouponRate", dblFixedCumulativeCouponAmount /
				dblFixedCumulativeCouponDCF);

			mapDifferentialMetric.put ("EffectiveFloatCouponRate", dblFloatCumulativeCouponAmount /
				dblFloatCumulativeCouponDCF);

			mapDifferentialMetric.put ("FixedAccrualDCF1M",
				org.drip.analytics.daycount.Convention.YearFraction (iEffectiveDate, iDate1M,
					strFixedAccrualDayCount, false, null, strCalendar));

			mapDifferentialMetric.put ("FixedAccrualDCF3M",
				org.drip.analytics.daycount.Convention.YearFraction (iEffectiveDate, iDate3M,
					strFixedAccrualDayCount, false, null, strCalendar));

			mapDifferentialMetric.put ("FixedCumulativeCouponAmount", dblFixedCumulativeCouponAmount);

			mapDifferentialMetric.put ("FixedCumulativeCouponDCF", dblFixedCumulativeCouponDCF);

			mapDifferentialMetric.put ("FloatAccrualDCF1M",
				org.drip.analytics.daycount.Convention.YearFraction (iEffectiveDate, iDate1M,
					strFloatAccrualDayCount, false, null, strCalendar));

			mapDifferentialMetric.put ("FloatAccrualDCF3M",
				org.drip.analytics.daycount.Convention.YearFraction (iEffectiveDate, iDate3M,
					strFloatAccrualDayCount, false, null, strCalendar));

			mapDifferentialMetric.put ("FloatCumulativeCouponAmount", dblFloatCumulativeCouponAmount);

			mapDifferentialMetric.put ("FloatCumulativeCouponDCF", dblFloatCumulativeCouponDCF);

			return mapDifferentialMetric;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
