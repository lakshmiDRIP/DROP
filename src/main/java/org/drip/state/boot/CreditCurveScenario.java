
package org.drip.state.boot;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>CreditCurveScenario</i> uses the hazard rate calibration instruments along with the component
 * calibrator to produce scenario hazard rate curves. CreditCurveScenario typically first constructs the
 * actual curve calibrator instance to localize the intelligence around curve construction. It then uses this
 * curve calibrator instance to build individual curves or the sequence of node bumped scenario curves. The
 * curves in the set may be an array, or tenor-keyed.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/boot">Boot</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CreditCurveScenario {
	static class TranslatedQuoteMeasure {
		java.lang.String _strMeasure = "";
		double _dblQuote = java.lang.Double.NaN;

		TranslatedQuoteMeasure (
			final java.lang.String strMeasure,
			final double dblQuote)
		{
			_dblQuote = dblQuote;
			_strMeasure = strMeasure;
		}
	}

	private static final TranslatedQuoteMeasure TranslateQuoteMeasure (
		final org.drip.product.definition.CalibratableComponent comp,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.credit.CreditCurve cc,
		final java.lang.String strMeasure,
		final double dblQuote)
	{
		if (!(comp instanceof org.drip.product.definition.CreditDefaultSwap) ||
			(!"FlatSpread".equalsIgnoreCase (strMeasure) && !"QuotedSpread".equalsIgnoreCase (strMeasure)))
			return new TranslatedQuoteMeasure (strMeasure, dblQuote);

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapQSMeasures =
			((org.drip.product.definition.CreditDefaultSwap) comp).valueFromQuotedSpread (valParams,
				pricerParams, org.drip.param.creator.MarketParamsBuilder.Credit (dc, cc), null,
					0.01, dblQuote);

		return null == mapQSMeasures ? null : new TranslatedQuoteMeasure ("Upfront", mapQSMeasures.get
			("Upfront"));
	}

	/**
	 * Calibrate a Credit Curve
	 * 
	 * @param strName Credit Curve name
	 * @param valParams ValuationParams
	 * @param aCalibInst Array of Calibration Instruments
	 * @param adblCalibQuote Array of component quotes
	 * @param astrCalibMeasure Array of the calibration measures
	 * @param dblRecovery Component recovery
	 * @param bFlat Flat Calibration (True), or real bootstrapping (false)
	 * @param dc Base Discount Curve
	 * @param gc Govvie Curve
	 * @param lsfc Latent State Fixings Container
	 * @param vcp Valuation Customization Parameters
	 * @param cp The Calibration Parameters
	 * 
	 * @return CreditCurve Instance
	 */

	public static final org.drip.state.credit.CreditCurve Standard (
		final java.lang.String strName,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.product.definition.CalibratableComponent[] aCalibInst,
		final double[] adblCalibQuote,
		final java.lang.String[] astrCalibMeasure,
		final double dblRecovery,
		final boolean bFlat,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.govvie.GovvieCurve gc,
		final org.drip.param.market.LatentStateFixingsContainer lsfc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.param.definition.CalibrationParams cp)
	{
		if (null == valParams || null == aCalibInst || null == adblCalibQuote || null == astrCalibMeasure ||
			null == dc)
			return null;

		int iNumComp = aCalibInst.length;
		int aiDate[] = new int[iNumComp];
		double adblHazardRate[] = new double[iNumComp];

		if (0 == iNumComp || adblCalibQuote.length != iNumComp || astrCalibMeasure.length != iNumComp)
			return null;

		for (int i = 0; i < iNumComp; ++i) {
			if (null == aCalibInst[i]) return null;

			adblHazardRate[i] = java.lang.Double.NaN;

			aiDate[i] = aCalibInst[i].maturityDate().julian();
		}

		org.drip.state.credit.ExplicitBootCreditCurve ebcc =
			org.drip.state.creator.ScenarioCreditCurveBuilder.Hazard (new org.drip.analytics.date.JulianDate
				(valParams.valueDate()), strName, dc.currency(), aiDate, adblHazardRate, dblRecovery);

		org.drip.param.pricer.CreditPricerParams pricerParams = new org.drip.param.pricer.CreditPricerParams
			(7, null, false, org.drip.param.pricer.CreditPricerParams.PERIOD_DISCRETIZATION_DAY_STEP);

		for (int i = 0; i < iNumComp; ++i) {
			TranslatedQuoteMeasure tqm = TranslateQuoteMeasure (aCalibInst[i], valParams, pricerParams, dc,
				ebcc, astrCalibMeasure[i], adblCalibQuote[i]);

			if (null == tqm) return null;

			if (!org.drip.state.nonlinear.NonlinearCurveBuilder.CreditCurve (valParams, aCalibInst[i],
				tqm._dblQuote, tqm._strMeasure, bFlat, i, ebcc, dc, gc, pricerParams, lsfc, vcp, cp))
			{
				return null;
			}
		}

		ebcc.setInstrCalibInputs (valParams, bFlat, dc, gc, pricerParams, aCalibInst, adblCalibQuote,
			astrCalibMeasure, lsfc, vcp);

		return ebcc;
	}

	/**
	 * Create an array of tenor bumped credit curves
	 * 
	 * @param strName Credit Curve Name
	 * @param valParams ValuationParams
	 * @param aCalibInst Array of Calibration Instruments
	 * @param adblCalibQuote Array of component quotes
	 * @param astrCalibMeasure Array of the calibration measures
	 * @param dblRecovery Component recovery
	 * @param bFlat Flat Calibration (True), or real bootstrapping (false)
	 * @param dblBump Amount of bump applied to the tenor
	 * @param dc Base Discount Curve
	 * @param gc Govvie Curve
	 * @param lsfc Latent State Fixings Container
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return Array of CreditCurves
	 */

	public static final org.drip.state.credit.CreditCurve[] Tenor (
		final java.lang.String strName,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.product.definition.CalibratableComponent[] aCalibInst,
		final double[] adblCalibQuote,
		final java.lang.String[] astrCalibMeasure,
		final double dblRecovery,
		final boolean bFlat,
		final double dblBump,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.govvie.GovvieCurve gc,
		final org.drip.param.market.LatentStateFixingsContainer lsfc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == valParams || null == aCalibInst || null == adblCalibQuote || null == astrCalibMeasure ||
			null == dc)
			return null;

		int iNumComp = aCalibInst.length;
		org.drip.state.credit.CreditCurve[] aCreditCurve = new org.drip.state.credit.CreditCurve[iNumComp];

		if (0 == iNumComp || adblCalibQuote.length != iNumComp || astrCalibMeasure.length != iNumComp)
			return null;

		for (int i = 0; i < iNumComp; ++i) {
			double[] adblTenorQuote = new double [iNumComp];

			for (int j = 0; j < iNumComp; ++j)
				adblTenorQuote[j] += (j == i ? dblBump : 0.);

			if (null == (aCreditCurve[i] = Standard (strName, valParams, aCalibInst, adblTenorQuote,
				astrCalibMeasure, dblRecovery, bFlat, dc, gc, lsfc, vcp, null)))
				return null;
		}

		return aCreditCurve;
	}

	/**
	 * Create an tenor named map of tenor bumped credit curves
	 * 
	 * @param strName Credit Curve name
	 * @param valParams ValuationParams
	 * @param aCalibInst Array of Calibration Instruments
	 * @param adblCalibQuote Array of component quotes
	 * @param astrCalibMeasure Array of the calibration measures
	 * @param dblRecovery Component recovery
	 * @param bFlat Flat Calibration (True), or real bootstrapping (false)
	 * @param dblBump Amount of bump applied to the tenor
	 * @param dc Base Discount Curve
	 * @param gc Govvie Curve
	 * @param lsfc Latent State Fixings Container
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return Tenor named map of tenor bumped credit curves
	 */

	public static final org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>
		TenorMap (
			final java.lang.String strName,
			final org.drip.param.valuation.ValuationParams valParams,
			final org.drip.product.definition.CalibratableComponent[] aCalibInst,
			final double[] adblCalibQuote,
			final java.lang.String[] astrCalibMeasure,
			final double dblRecovery,
			final boolean bFlat,
			final double dblBump,
			final org.drip.state.discount.MergedDiscountForwardCurve dc,
			final org.drip.state.govvie.GovvieCurve gc,
			final org.drip.param.market.LatentStateFixingsContainer lsfc,
			final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == valParams || null == aCalibInst || null == adblCalibQuote || null == astrCalibMeasure ||
			null == dc)
			return null;

		int iNumComp = aCalibInst.length;

		if (0 == iNumComp || adblCalibQuote.length != iNumComp || astrCalibMeasure.length != iNumComp)
			return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>
			mapTenorCreditCurve = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>();

		for (int i = 0; i < iNumComp; ++i) {
			org.drip.state.credit.CreditCurve cc = null;
			double[] adblTenorQuote = new double[iNumComp];

			for (int j = 0; j < iNumComp; ++j)
				adblTenorQuote[j] = adblCalibQuote[j] + (j == i ? dblBump : 0.);

			if (null == (cc = Standard (strName, valParams, aCalibInst, adblTenorQuote, astrCalibMeasure,
				dblRecovery, bFlat, dc, gc, lsfc, vcp, null)))
				return null;

			mapTenorCreditCurve.put (org.drip.analytics.date.DateUtil.YYYYMMDD
				(aCalibInst[i].maturityDate().julian()), cc);
		}

		return mapTenorCreditCurve;
	}
}
