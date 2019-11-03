
package org.drip.param.market;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>CreditCurveScenarioContainer</i> contains the place holder for the bump parameters and the curves for
 * the different credit curve scenarios. Contains the spread and the recovery bumps, and the credit curve
 * scenario generator object that wraps the calibration instruments. It also contains the base credit curve,
 * spread bumped up/down credit curves, recovery bumped up/down credit curves, and the tenor mapped up/down
 * credit curves.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/README.md">Product Cash Flow, Valuation, Market, Pricing, and Quoting Parameters</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/market/README.md">Curves Surfaces Quotes Fixings Container</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CreditCurveScenarioContainer {

	/**
	 * CC Scenario Base
	 */

	public static final int CC_BASE = 0;

	/**
	 * CC Scenario Parallel Up
	 */

	public static final int CC_FLAT_UP = 1;

	/**
	 * CC Scenario Parallel Down
	 */

	public static final int CC_FLAT_DN = 2;

	/**
	 * CC Scenario Tenor Up
	 */

	public static final int CC_TENOR_UP = 4;

	/**
	 * CC Scenario Tenor Down
	 */

	public static final int CC_TENOR_DN = 8;

	/**
	 * CC Scenario Recovery Parallel Up
	 */

	public static final int CC_RR_FLAT_UP = 16;

	/**
	 * CC Scenario Recovery Parallel Down
	 */

	public static final int CC_RR_FLAT_DN = 32;

	private double _dblCouponBump = java.lang.Double.NaN;
	private double _dblRecoveryBump = java.lang.Double.NaN;
	private org.drip.state.credit.CreditCurve _ccBase = null;
	private org.drip.state.credit.CreditCurve _ccBumpUp = null;
	private org.drip.state.credit.CreditCurve _ccBumpDn = null;
	private org.drip.state.credit.CreditCurve _ccRecoveryUp = null;
	private org.drip.state.credit.CreditCurve _ccRecoveryDn = null;
	private org.drip.product.definition.CalibratableComponent[] _aCalibInst = null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>
		_mapCCCustom = null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>
		_mapCCTenorBumpUp = null;
	private org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>
		_mapCCTenorBumpDn = null;

	/**
	 * Construct CreditCurveScenarioContainer from the array of calibration instruments, the coupon bump
	 * 	parameter, and the recovery bump parameter
	 * 
	 * @param aCalibInst Array of calibration instruments
	 * @param dblCouponBump Coupon Bump
	 * @param dblRecoveryBump Recovery Bump
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public CreditCurveScenarioContainer (
		final org.drip.product.definition.CalibratableComponent[] aCalibInst,
		final double dblCouponBump,
		final double dblRecoveryBump)
		throws java.lang.Exception
	{
		if (null == (_aCalibInst = aCalibInst) || 0 == _aCalibInst.length ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblCouponBump = dblCouponBump) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblRecoveryBump = dblRecoveryBump))
			throw new java.lang.Exception ("CreditCurveScenarioContainer ctr => Invalid Inputs!");
	}

	/**
	 * Cook and save the credit curves corresponding to the scenario specified
	 * 
	 * @param strName Credit Curve Name
	 * @param valParams ValuationParams
	 * @param dc Base Discount Curve
	 * @param gc Govvie Curve
	 * @param astrCalibMeasure Matched array of Calibration measures
	 * @param adblQuote Matched array of Quotes
	 * @param dblRecovery Curve Recovery
	 * @param lsfc Latent State Fixings Container
	 * @param vcp Valuation Customization Parameters
	 * @param bFlat Whether the calibration is to a flat curve
	 * @param iScenario One of the values in the CC_ enum listed above. 
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean cookScenarioCC (
		final java.lang.String strName,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.govvie.GovvieCurve gc,
		final java.lang.String[] astrCalibMeasure,
		final double[] adblQuote,
		final double dblRecovery,
		final org.drip.param.market.LatentStateFixingsContainer lsfc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final boolean bFlat,
		final int iScenario)
	{
		if (null == (_ccBase = org.drip.state.boot.CreditCurveScenario.Standard (strName, valParams,
			_aCalibInst, adblQuote, astrCalibMeasure, dblRecovery, bFlat, dc, gc, lsfc, vcp, null)))
			return false;

		if (0 != (org.drip.param.market.CreditCurveScenarioContainer.CC_FLAT_UP & iScenario)) {
			if (null == (_ccBumpUp = org.drip.state.boot.CreditCurveScenario.Standard (strName, valParams,
				_aCalibInst, org.drip.analytics.support.Helper.BumpQuotes (adblQuote, _dblCouponBump, false),
					astrCalibMeasure, dblRecovery, bFlat, dc, gc, lsfc, vcp, null)))
				return false;
		}

		if (0 != (org.drip.param.market.CreditCurveScenarioContainer.CC_FLAT_DN & iScenario)) {
			if (null == (_ccBumpDn = org.drip.state.boot.CreditCurveScenario.Standard (strName, valParams,
				_aCalibInst, org.drip.analytics.support.Helper.BumpQuotes (adblQuote, -_dblCouponBump,
					false), astrCalibMeasure, dblRecovery, bFlat, dc, gc, lsfc, vcp, null)))
				return false;
		}

		if (0 != (org.drip.param.market.CreditCurveScenarioContainer.CC_TENOR_UP & iScenario)) {
			if (null == (_mapCCTenorBumpUp = org.drip.state.boot.CreditCurveScenario.TenorMap (strName,
				valParams, _aCalibInst, adblQuote, astrCalibMeasure, dblRecovery, bFlat, _dblCouponBump, dc,
					gc, lsfc, vcp)))
				return false;
		}

		if (0 != (org.drip.param.market.CreditCurveScenarioContainer.CC_TENOR_DN & iScenario)) {
			if (null == (_mapCCTenorBumpDn = org.drip.state.boot.CreditCurveScenario.TenorMap (strName,
				valParams, _aCalibInst, adblQuote, astrCalibMeasure, dblRecovery, bFlat, -_dblCouponBump, dc,
					gc, lsfc, vcp)))
				return false;
		}

		if (0 != (org.drip.param.market.CreditCurveScenarioContainer.CC_RR_FLAT_UP & iScenario)) {
			if (null == (_ccRecoveryUp = org.drip.state.boot.CreditCurveScenario.Standard (strName,
				valParams, _aCalibInst, adblQuote, astrCalibMeasure, dblRecovery + _dblRecoveryBump, bFlat,
					dc, gc, lsfc, vcp, null)))
				return false;
		}

		if (0 != (org.drip.param.market.CreditCurveScenarioContainer.CC_RR_FLAT_DN & iScenario)) {
			if (null == (_ccRecoveryDn = org.drip.state.boot.CreditCurveScenario.Standard (strName,
				valParams, _aCalibInst, adblQuote, astrCalibMeasure, dblRecovery - _dblRecoveryBump, bFlat,
					dc, gc, lsfc, vcp, null)))
				return false;
		}

		return true;
	}

	/**
	 * Cook the credit curve according to the desired tweak parameters
	 * 
	 * @param strName Scenario Credit Curve Name
	 * @param strCustomName Scenario Name
	 * @param valParams Valuation Parameters
	 * @param dc Discount Curve
	 * @param gc Govvie Curve
	 * @param astrCalibMeasure Array of calibration measures
	 * @param adblQuote Double array of input quotes
	 * @param dblRecovery Recovery Rate
	 * @param lsfc Latent State Fixings Container
	 * @param vcp Valuation Customization Parameters
	 * @param bFlat Whether the calibration is flat
	 * @param rvtpDC Node Tweak Parameters for the Base Discount Curve
	 * @param rvtpTSY Node Tweak Parameters for the TSY Discount Curve
	 * @param rvtpCC Node Tweak Parameters for the Credit Curve
	 * 
	 * @return True - Credit Curve successfully created
	 */

	public boolean cookCustomCC (
		final java.lang.String strName,
		final java.lang.String strCustomName,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.govvie.GovvieCurve gc,
		final java.lang.String[] astrCalibMeasure,
		final double[] adblQuote,
		final double dblRecovery,
		final org.drip.param.market.LatentStateFixingsContainer lsfc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final boolean bFlat,
		final org.drip.param.definition.ManifestMeasureTweak rvtpDC,
		final org.drip.param.definition.ManifestMeasureTweak rvtpTSY,
		final org.drip.param.definition.ManifestMeasureTweak rvtpCC)
	{
		if (null == dc) return false;

		org.drip.state.discount.MergedDiscountForwardCurve dcAdj = (org.drip.state.discount.MergedDiscountForwardCurve)
			dc.customTweakManifestMeasure ("Rate", rvtpDC);

		org.drip.state.govvie.GovvieCurve gcAdj = (org.drip.state.govvie.GovvieCurve)
			gc.customTweakManifestMeasure ("Rate", rvtpTSY);

		org.drip.state.credit.CreditCurve ccBaseCustom = org.drip.state.boot.CreditCurveScenario.Standard
			(strName, valParams, _aCalibInst, adblQuote, astrCalibMeasure, dblRecovery, bFlat, null == dcAdj
				? dc : dcAdj, null == gcAdj ? gc : gcAdj, lsfc, vcp, null);

		if (null == ccBaseCustom) return false;

		if (null == _mapCCCustom)
			_mapCCCustom = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>();

		org.drip.state.credit.CreditCurve ccCustom = (org.drip.state.credit.CreditCurve)
			ccBaseCustom.customTweakManifestMeasure ("Rate", rvtpCC);

		if (null == ccCustom)
			_mapCCCustom.put (strCustomName, ccBaseCustom);
		else
			_mapCCCustom.put (strCustomName, ccCustom);

		return true;
	}

	/**
	 * Return the base credit curve
	 * 
	 * @return The base credit curve
	 */

	public org.drip.state.credit.CreditCurve base()
	{
		return _ccBase;
	}

	/**
	 * Return the bump up credit curve
	 * 
	 * @return The Bumped up credit curve
	 */

	public org.drip.state.credit.CreditCurve bumpUp()
	{
		return _ccBumpUp;
	}

	/**
	 * Return the bump Down credit curve
	 * 
	 * @return The Bumped Down credit curve
	 */

	public org.drip.state.credit.CreditCurve bumpDown()
	{
		return _ccBumpDn;
	}

	/**
	 * Return the recovery bump up credit curve
	 * 
	 * @return The Recovery Bumped up credit curve
	 */

	public org.drip.state.credit.CreditCurve bumpRecoveryUp()
	{
		return _ccRecoveryUp;
	}

	/**
	 * Return the recovery bump Down credit curve
	 * 
	 * @return The Recovery Bumped Down credit curve
	 */

	public org.drip.state.credit.CreditCurve bumpRecoveryDown()
	{
		return _ccRecoveryDn;
	}

	/**
	 * Return the tenor bump up credit curve map
	 * 
	 * @return The Tenor Bumped up credit curve Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve> tenorBumpUp()
	{
		return _mapCCTenorBumpUp;
	}

	/**
	 * Return the tenor bump Down credit curve map
	 * 
	 * @return The Tenor Bumped Down credit curve Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve>
		tenorBumpDown()
	{
		return _mapCCTenorBumpDn;
	}

	/**
	 * Return the Custom credit curve map
	 * 
	 * @return The Custom credit curve Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.credit.CreditCurve> custom()
	{
		return _mapCCCustom;
	}
}
