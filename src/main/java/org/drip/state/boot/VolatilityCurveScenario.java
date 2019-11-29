
package org.drip.state.boot;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>VolatilityCurveScenario</i> uses the Volatility calibration instruments along with the component
 * calibrator to produce scenario Volatility curves.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/boot/README.md">Bootable Discount, Credit, Volatility States</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class VolatilityCurveScenario {

	/**
	 * Calibrate a Volatility Curve
	 * 
	 * @param strName Volatility Curve name
	 * @param valParams ValuationParams
	 * @param lslUnderlying Underlying Latent State Label
	 * @param aFRACapFloor Array of the FRA Cap Floor Instruments
	 * @param adblCalibQuote Array of component quotes
	 * @param astrCalibMeasure Array of the calibration measures
	 * @param bFlat Flat Calibration (True), or real bootstrapping (false)
	 * @param dc Discount Curve
	 * @param fc Forward Curve
	 * @param lsfc Latent State Fixings Container
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return VolatilityCurve Instance
	 */

	public static final org.drip.state.volatility.VolatilityCurve Standard (
		final java.lang.String strName,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.state.identifier.LatentStateLabel lslUnderlying,
		final org.drip.product.fra.FRAStandardCapFloor[] aFRACapFloor,
		final double[] adblCalibQuote,
		final java.lang.String[] astrCalibMeasure,
		final boolean bFlat,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.forward.ForwardCurve fc,
		final org.drip.param.market.LatentStateFixingsContainer lsfc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == valParams || null == aFRACapFloor || null == adblCalibQuote || null == astrCalibMeasure
			|| null == dc)
			return null;

		int iNumComp = aFRACapFloor.length;
		int aiPillarDate[] = new int[iNumComp];
		double adblVolatility[] = new double[iNumComp];
		org.drip.state.volatility.ExplicitBootVolatilityCurve ebvc = null;

		if (0 == iNumComp || adblCalibQuote.length != iNumComp || astrCalibMeasure.length != iNumComp)
			return null;

		for (int i = 0; i < iNumComp; ++i) {
			if (null == aFRACapFloor[i]) return null;

			adblVolatility[i] = 0.001;

			aiPillarDate[i] = aFRACapFloor[i].stream().maturity().julian();
		}

		try {
			ebvc = new org.drip.state.nonlinear.FlatForwardVolatilityCurve (dc.epoch().julian(),
				org.drip.state.identifier.VolatilityLabel.Standard (lslUnderlying), dc.currency(),
					aiPillarDate, adblVolatility);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i < iNumComp; ++i) {
			try {
				org.drip.state.nonlinear.NonlinearCurveBuilder.VolatilityCurveNode (valParams,
					aFRACapFloor[i], adblCalibQuote[i], astrCalibMeasure[i], bFlat, i, ebvc, dc, fc, lsfc,
						vcp);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return ebvc;
	}

	/**
	 * Create an array of tenor bumped Volatility curves
	 * 
	 * @param strName Volatility Curve Name
	 * @param valParams ValuationParams
	 * @param lslUnderlying Underlying Latent State Label
	 * @param aFRACapFloor Array of the FRA Cap Floor Instruments
	 * @param adblCalibQuote Array of component quotes
	 * @param astrCalibMeasure Array of the calibration measures
	 * @param bFlat Flat Calibration (True), or real bootstrapping (false)
	 * @param dblBump Amount of bump applied to the tenor
	 * @param dc Base Discount Curve
	 * @param fc Forward Curve
	 * @param lsfc Latent State Fixings Container
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return Array of Volatility Curves
	 */

	public static final org.drip.state.volatility.VolatilityCurve[] Tenor (
		final java.lang.String strName,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.state.identifier.LatentStateLabel lslUnderlying,
		final org.drip.product.fra.FRAStandardCapFloor[] aFRACapFloor,
		final double[] adblCalibQuote,
		final java.lang.String[] astrCalibMeasure,
		final boolean bFlat,
		final double dblBump,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.forward.ForwardCurve fc,
		final org.drip.param.market.LatentStateFixingsContainer lsfc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == aFRACapFloor || !org.drip.numerical.common.NumberUtil.IsValid (dblBump)) return null;

		int iNumComp = aFRACapFloor.length;
		org.drip.state.volatility.VolatilityCurve[] aVolatilityCurve = new
			org.drip.state.volatility.VolatilityCurve[iNumComp];

		if (0 == iNumComp) return null;

		for (int i = 0; i < iNumComp; ++i) {
			double[] adblTenorQuote = new double [iNumComp];

			for (int j = 0; j < iNumComp; ++j)
				adblTenorQuote[j] += (j == i ? dblBump : 0.);

			if (null == (aVolatilityCurve[i] = Standard (strName, valParams, lslUnderlying, aFRACapFloor,
				adblTenorQuote, astrCalibMeasure, bFlat, dc, fc, lsfc, vcp)))
				return null;
		}

		return aVolatilityCurve;
	}

	/**
	 * Create an tenor named map of tenor bumped Volatility curves
	 * 
	 * @param strName Volatility Curve name
	 * @param valParams ValuationParams
	 * @param lslUnderlying Underlying Latent State Label
	 * @param aFRACapFloor Array of the FRA Cap Floor Instruments
	 * @param adblCalibQuote Array of component quotes
	 * @param astrCalibMeasure Array of the calibration measures
	 * @param bFlat Flat Calibration (True), or real bootstrapping (false)
	 * @param dblBump Amount of bump applied to the tenor
	 * @param dc Base Discount Curve
	 * @param fc Forward Curve
	 * @param lsfc Latent State Fixings Container
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return Tenor named map of tenor bumped Volatility curves
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>
		TenorMap (
			final java.lang.String strName,
			final org.drip.param.valuation.ValuationParams valParams,
			final org.drip.state.identifier.LatentStateLabel lslUnderlying,
			final org.drip.product.fra.FRAStandardCapFloor[] aFRACapFloor,
			final double[] adblCalibQuote,
			final java.lang.String[] astrCalibMeasure,
			final boolean bFlat,
			final double dblBump,
			final org.drip.state.discount.MergedDiscountForwardCurve dc,
			final org.drip.state.forward.ForwardCurve fc,
			final org.drip.param.market.LatentStateFixingsContainer lsfc,
			final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == aFRACapFloor || !org.drip.numerical.common.NumberUtil.IsValid (dblBump)) return null;

		int iNumComp = aFRACapFloor.length;

		if (0 == iNumComp) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>
			mapTenorVolatilityCurve = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.volatility.VolatilityCurve>();

		for (int i = 0; i < iNumComp; ++i) {
			double[] adblTenorQuote = new double[iNumComp];
			org.drip.state.volatility.VolatilityCurve volCurve = null;

			for (int j = 0; j < iNumComp; ++j)
				adblTenorQuote[j] = adblCalibQuote[j] + (j == i ? dblBump : 0.);

			if (null == (volCurve = Standard (strName, valParams, lslUnderlying, aFRACapFloor, adblTenorQuote,
				astrCalibMeasure, bFlat, dc, fc, lsfc, vcp)))
				return null;

			mapTenorVolatilityCurve.put (org.drip.analytics.date.DateUtil.YYYYMMDD
				(aFRACapFloor[i].maturityDate().julian()), volCurve);
		}

		return mapTenorVolatilityCurve;
	}
}
