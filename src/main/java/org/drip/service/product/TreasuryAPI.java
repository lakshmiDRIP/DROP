
package org.drip.service.product;

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
 * <i>TreasuryAPI</i> demonstrates the Details behind the Pricing and the Scenario Runs behind a Treasury
 * Bond.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/product/README.md">Product Horizon PnL Attribution Decomposition</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryAPI {

	/**
	 * Compute the Horizon Change Attribution Details for the Specified Treasury Bond
	 * 
	 * @param gcFirst First Day Govvie Curve
	 * @param gcSecond Second Date Govvie Curve
	 * @param mapRollDownGovvieCurve Map of the Roll Down Govvie Curves
	 * @param strMaturityTenor Treasury Bond Maturity Tenor
	 * @param strCode Treasury Bond Code
	 * 
	 * @return The Horizon Change Attribution Instance
	 */

	public static final org.drip.historical.attribution.PositionChangeComponents HorizonChangeAttribution (
		final org.drip.state.govvie.GovvieCurve gcFirst,
		final org.drip.state.govvie.GovvieCurve gcSecond,
		final org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.govvie.GovvieCurve>
			mapRollDownGovvieCurve,
		final java.lang.String strMaturityTenor,
		final java.lang.String strCode)
	{
		if (null == gcFirst || null == mapRollDownGovvieCurve || 0 == mapRollDownGovvieCurve.size())
			return null;

		double dblFirstGovvieCurveYield = java.lang.Double.NaN;

		try {
			dblFirstGovvieCurveYield = gcFirst.yld (strMaturityTenor);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.date.JulianDate dtFirst = gcFirst.epoch();

		org.drip.param.market.CurveSurfaceQuoteContainer csqcFirst = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		if (!csqcFirst.setGovvieState (gcFirst)) return null;

		org.drip.param.market.CurveSurfaceQuoteContainer csqcSecond = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		if (!csqcSecond.setGovvieState (gcSecond)) return null;

		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			mapCSQCRollDown = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.param.market.CurveSurfaceQuoteContainer>();

		for (java.lang.String strRollDownTenor : mapRollDownGovvieCurve.keySet()) {
			org.drip.param.market.CurveSurfaceQuoteContainer csqcRollDown = new
				org.drip.param.market.CurveSurfaceQuoteContainer();

			org.drip.state.govvie.GovvieCurve gcRollDown = mapRollDownGovvieCurve.get (strRollDownTenor);

			if (null == gcRollDown || !csqcRollDown.setGovvieState (gcRollDown)) return null;

			mapCSQCRollDown.put (strRollDownTenor, csqcRollDown);
		}

		try {
			return org.drip.historical.engine.HorizonChangeExplainExecutor.GenerateAttribution (new
				org.drip.historical.engine.TreasuryBondExplainProcessor
					(org.drip.service.template.TreasuryBuilder.FromCode (strCode, dtFirst, dtFirst.addTenor
						(strMaturityTenor), dblFirstGovvieCurveYield), "Yield", dblFirstGovvieCurveYield,
							dtFirst, gcSecond.epoch(), csqcFirst, csqcSecond, mapCSQCRollDown));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Govvie Curve Horizon Metrics
	 * 
	 * @param dtFirst The First Date
	 * @param dtSecond The Second Date
	 * @param astrGovvieTreasuryInstrumentTenor Array of Govvie Curve Treasury Instrument Maturity Tenors
	 * @param adblFirstGovvieTreasuryInstrument Array of First Date Govvie Curve Treasury Instrument Quotes
	 * @param adblSecondGovvieTreasuryInstrument Array of Second Date Govvie Curve Treasury Instrument Quotes
	 * @param strMaturityTenor Treasury Bond Maturity Tenor
	 * @param strCode Treasury Bond Code
	 * @param astrRollDownHorizon Array of the Roll Down Horizon Tenors
	 * @param iLatentStateType Latent State Type
	 * 
	 * @return The Govvie Curve Horizon Metrics
	 */

	public static final org.drip.historical.attribution.PositionChangeComponents HorizonChangeAttribution (
		final org.drip.analytics.date.JulianDate dtFirst,
		final org.drip.analytics.date.JulianDate dtSecond,
		final java.lang.String[] astrGovvieTreasuryInstrumentTenor,
		final double[] adblFirstGovvieTreasuryInstrument,
		final double[] adblSecondGovvieTreasuryInstrument,
		final java.lang.String strMaturityTenor,
		final java.lang.String strCode,
		final java.lang.String[] astrRollDownHorizon,
		final int iLatentStateType)
	{
		if (null == dtFirst || null == dtSecond || dtFirst.julian() >= dtSecond.julian()) return null;

		int iNumGovvieTreasuryInstrument = null == astrGovvieTreasuryInstrumentTenor ? 0 :
			astrGovvieTreasuryInstrumentTenor.length;
		int iNumFirstGovvieTreasuryInstrument = null == adblFirstGovvieTreasuryInstrument ? 0 :
			adblFirstGovvieTreasuryInstrument.length;
		int iNumSecondGovvieTreasuryInstrument = null == adblSecondGovvieTreasuryInstrument ? 0 :
			adblSecondGovvieTreasuryInstrument.length;
		int iNumRollDownHorizon = null == astrRollDownHorizon ? 0 : astrRollDownHorizon .length;
		org.drip.analytics.date.JulianDate[] adtFirstEffective = new
			org.drip.analytics.date.JulianDate[iNumGovvieTreasuryInstrument];
		org.drip.analytics.date.JulianDate[] adtFirstMaturity = new
			org.drip.analytics.date.JulianDate[iNumGovvieTreasuryInstrument];
		org.drip.analytics.date.JulianDate[] adtSecondEffective = new
			org.drip.analytics.date.JulianDate[iNumGovvieTreasuryInstrument];
		org.drip.analytics.date.JulianDate[] adtSecondMaturity = new
			org.drip.analytics.date.JulianDate[iNumGovvieTreasuryInstrument];
		org.drip.analytics.date.JulianDate[] adtRollDownEffective = new
			org.drip.analytics.date.JulianDate[iNumGovvieTreasuryInstrument];
		org.drip.analytics.date.JulianDate[] adtRollDownMaturity = new
			org.drip.analytics.date.JulianDate[iNumGovvieTreasuryInstrument];

		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.govvie.GovvieCurve>
			mapRollDownGovvieCurve = 0 == iNumRollDownHorizon ? null : new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.state.govvie.GovvieCurve>();

		if (0 == iNumGovvieTreasuryInstrument || iNumGovvieTreasuryInstrument !=
			iNumFirstGovvieTreasuryInstrument || iNumGovvieTreasuryInstrument !=
				iNumSecondGovvieTreasuryInstrument)
			return null;

		for (int i = 0; i < iNumGovvieTreasuryInstrument; ++i) {
			adtFirstMaturity[i] = (adtFirstEffective[i] = dtFirst).addTenor
				(astrGovvieTreasuryInstrumentTenor[i]);

			adtSecondMaturity[i] = (adtSecondEffective[i] = dtSecond).addTenor
				(astrGovvieTreasuryInstrumentTenor[i]);
		}

		org.drip.state.govvie.GovvieCurve gcFirst =
			org.drip.service.template.LatentMarketStateBuilder.GovvieCurve (strCode, dtFirst,
				adtFirstEffective, adtFirstMaturity, adblFirstGovvieTreasuryInstrument,
					adblFirstGovvieTreasuryInstrument, "Yield", iLatentStateType);

		org.drip.state.govvie.GovvieCurve gcSecond =
			org.drip.service.template.LatentMarketStateBuilder.GovvieCurve (strCode, dtSecond,
				adtSecondEffective, adtSecondMaturity, adblSecondGovvieTreasuryInstrument,
					adblSecondGovvieTreasuryInstrument, "Yield", iLatentStateType);

		org.drip.state.govvie.GovvieCurve gcRollDown =
			org.drip.service.template.LatentMarketStateBuilder.GovvieCurve (strCode, dtSecond,
				adtSecondEffective, adtSecondMaturity, adblFirstGovvieTreasuryInstrument,
					adblFirstGovvieTreasuryInstrument, "Yield", iLatentStateType);

		if (null == gcRollDown) return null;

		mapRollDownGovvieCurve.put ("Native", gcRollDown);

		for (int j = 0; j < iNumRollDownHorizon; ++j) {
			org.drip.analytics.date.JulianDate dtRollDown = dtFirst.addTenor (astrRollDownHorizon[j]);

			for (int i = 0; i < iNumGovvieTreasuryInstrument; ++i)
				adtRollDownMaturity[i] = (adtRollDownEffective[i] = dtRollDown).addTenor
					(astrGovvieTreasuryInstrumentTenor[i]);

			org.drip.state.govvie.GovvieCurve gcHorizonRollDown =
				org.drip.service.template.LatentMarketStateBuilder.GovvieCurve (strCode, dtRollDown,
					adtRollDownEffective, adtRollDownMaturity, adblFirstGovvieTreasuryInstrument,
						adblFirstGovvieTreasuryInstrument, "Yield", iLatentStateType);

			if (null == gcHorizonRollDown) return null;

			mapRollDownGovvieCurve.put (astrRollDownHorizon[j], gcHorizonRollDown);
		}

		return HorizonChangeAttribution (gcFirst, gcSecond, mapRollDownGovvieCurve, strMaturityTenor,
			strCode);
	}

	/**
	 * Generate the Govvie Curve Horizon Metrics
	 * 
	 * @param adtSpot Array of the Spot Dates
	 * @param iHorizonGap The Horizon Gap
	 * @param astrGovvieTreasuryInstrumentTenor Array of Govvie Curve Treasury Instrument Maturity Tenors
	 * @param aadblGovvieTreasuryInstrumentQuote Array of Govvie Curve Treasury Instrument Quotes
	 * @param strMaturityTenor Treasury Bond Maturity Tenor
	 * @param strCode Treasury Bond Code
	 * @param astrRollDownHorizon Array of the Roll Down Horizon Tenors
	 * @param iLatentStateType Latent State Type
	 * 
	 * @return The Govvie Curve Horizon Metrics
	 */

	public static final java.util.List<org.drip.historical.attribution.PositionChangeComponents>
		HorizonChangeAttribution (
			final org.drip.analytics.date.JulianDate[] adtSpot,
			final int iHorizonGap,
			final java.lang.String[] astrGovvieTreasuryInstrumentTenor,
			final double[][] aadblGovvieTreasuryInstrumentQuote,
			final java.lang.String strMaturityTenor,
			final java.lang.String strCode,
			final java.lang.String[] astrRollDownHorizon,
			final int iLatentStateType)
	{
		if (null == adtSpot || 0 >= iHorizonGap || null == aadblGovvieTreasuryInstrumentQuote) return null;

		int iNumClose = adtSpot.length;
		int iNumRollDownTenor = null == astrRollDownHorizon ? 0 : astrRollDownHorizon.length;

		if (0 == iNumClose || iNumClose != aadblGovvieTreasuryInstrumentQuote.length || 0 ==
			iNumRollDownTenor)
			return null;

		java.util.List<org.drip.historical.attribution.PositionChangeComponents> lsPCC = new
			java.util.ArrayList<org.drip.historical.attribution.PositionChangeComponents>();

		for (int i = iHorizonGap; i < iNumClose; ++i) {
			org.drip.historical.attribution.PositionChangeComponents pcc = HorizonChangeAttribution
				(adtSpot[i - iHorizonGap], adtSpot[i], astrGovvieTreasuryInstrumentTenor,
					aadblGovvieTreasuryInstrumentQuote[i - iHorizonGap],
						aadblGovvieTreasuryInstrumentQuote[i], strMaturityTenor, strCode,
							astrRollDownHorizon, iLatentStateType);

			if (null != pcc) lsPCC.add (pcc);
		}

		return lsPCC;
	}
}
