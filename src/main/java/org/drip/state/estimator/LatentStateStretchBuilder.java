
package org.drip.state.estimator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>LatentStateStretchBuilder</i> contains the Functionality to construct the Curve Latent State Stretch
 * for the different Latent States.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/estimator">Estimator</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateStretchBuilder {

	/**
	 * Construct a Forward Latent State Stretch Spec Instance
	 * 
	 * @param strName Stretch Name
	 * @param aCalibComp Array of Calibration Components
	 * @param astrCalibMeasure Array of the Calibration Measures
	 * @param adblCalibQuote Array of the Calibration Quotes
	 * 
	 * @return Forward Latent State Stretch Spec Instance
	 */

	public static final org.drip.state.inference.LatentStateStretchSpec ForwardStretchSpec (
		final java.lang.String strName,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp,
		final java.lang.String[] astrCalibMeasure,
		final double[] adblCalibQuote)
	{
		if (null == aCalibComp || null == astrCalibMeasure || null == adblCalibQuote) return null;

		int iNumComp = aCalibComp.length;
		org.drip.state.inference.LatentStateSegmentSpec[] aLSSS = new
			org.drip.state.inference.LatentStateSegmentSpec[iNumComp];

		if (0 == iNumComp || iNumComp != astrCalibMeasure.length || iNumComp != adblCalibQuote.length)
			return null;

		try {
			for (int i = 0; i < iNumComp; ++i) {
				if (null == aCalibComp[i] || null == astrCalibMeasure[i] || astrCalibMeasure[i].isEmpty() ||
					!org.drip.quant.common.NumberUtil.IsValid (adblCalibQuote[i]))
					return null;

				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
					mapForwardLabel = aCalibComp[i].forwardLabel();

				if (null == mapForwardLabel || 0 == mapForwardLabel.size()) return null;

				org.drip.product.calib.ProductQuoteSet pqs = aCalibComp[i].calibQuoteSet (new
					org.drip.state.representation.LatentStateSpecification[] {new
						org.drip.state.representation.LatentStateSpecification 
							(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FORWARD,
								org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE,
									mapForwardLabel.get ("DERIVED"))});

				if (null == pqs || !pqs.set (astrCalibMeasure[i], adblCalibQuote[i])) return null;

				aLSSS[i] = new org.drip.state.inference.LatentStateSegmentSpec (aCalibComp[i], pqs);
			}

			return new org.drip.state.inference.LatentStateStretchSpec (strName, aLSSS);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Merged Forward-Funding Latent State Stretch Spec Instance
	 * 
	 * @param strName Stretch Name
	 * @param aCalibComp Array of Calibration Components
	 * @param astrCalibMeasure Array of the Calibration Measures
	 * @param adblCalibQuote Array of the Calibration Quotes
	 * 
	 * @return Merged Forward-Funding Latent State Stretch Spec Instance
	 */

	public static final org.drip.state.inference.LatentStateStretchSpec ForwardFundingStretchSpec (
		final java.lang.String strName,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp,
		final java.lang.String[] astrCalibMeasure,
		final double[] adblCalibQuote)
	{
		if (null == aCalibComp || null == astrCalibMeasure || null == adblCalibQuote) return null;

		int iNumComp = aCalibComp.length;
		org.drip.state.inference.LatentStateSegmentSpec[] aLSSS = new
			org.drip.state.inference.LatentStateSegmentSpec[iNumComp];

		if (0 == iNumComp || iNumComp != astrCalibMeasure.length || iNumComp != adblCalibQuote.length)
			return null;

		try {
			for (int i = 0; i < iNumComp; ++i) {
				if (null == aCalibComp[i] || null == astrCalibMeasure[i] || astrCalibMeasure[i].isEmpty() ||
					!org.drip.quant.common.NumberUtil.IsValid (adblCalibQuote[i]))
					return null;

				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
					mapForwardLabel = aCalibComp[i].forwardLabel();

				if (null == mapForwardLabel || 0 == mapForwardLabel.size()) return null;

				org.drip.product.calib.ProductQuoteSet pqs = aCalibComp[i].calibQuoteSet (new
					org.drip.state.representation.LatentStateSpecification[] {new
						org.drip.state.representation.LatentStateSpecification
							(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FUNDING,
								org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR,
					org.drip.state.identifier.FundingLabel.Standard (aCalibComp[i].payCurrency())), new
						org.drip.state.representation.LatentStateSpecification 
							(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FORWARD,
								org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE,
									mapForwardLabel.get ("DERIVED"))});

				if (null == pqs || !pqs.set (astrCalibMeasure[i], adblCalibQuote[i])) return null;

				aLSSS[i] = new org.drip.state.inference.LatentStateSegmentSpec (aCalibComp[i], pqs);
			}

			return new org.drip.state.inference.LatentStateStretchSpec (strName, aLSSS);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Funding Latent State Stretch Spec Instance
	 * 
	 * @param strName Stretch Name
	 * @param aCalibComp Array of Calibration Components
	 * @param astrCalibMeasure Array of the Calibration Measures
	 * @param adblCalibQuote Array of the Calibration Quotes
	 * 
	 * @return Funding Latent State Stretch Spec Instance
	 */

	public static final org.drip.state.inference.LatentStateStretchSpec FundingStretchSpec (
		final java.lang.String strName,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp,
		final java.lang.String[] astrCalibMeasure,
		final double[] adblCalibQuote)
	{
		if (null == aCalibComp || null == astrCalibMeasure || null == adblCalibQuote) return null;

		int iNumComp = aCalibComp.length;
		org.drip.state.inference.LatentStateSegmentSpec[] aLSSS = new
			org.drip.state.inference.LatentStateSegmentSpec[iNumComp];

		if (0 == iNumComp || iNumComp != astrCalibMeasure.length || iNumComp != adblCalibQuote.length)
			return null;

		try {
			for (int i = 0; i < iNumComp; ++i) {
				if (null == aCalibComp[i] || null == astrCalibMeasure[i] || astrCalibMeasure[i].isEmpty() ||
					!org.drip.quant.common.NumberUtil.IsValid (adblCalibQuote[i]))
					return null;

				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
					mapForwardLabel = aCalibComp[i].forwardLabel();

				if (null == mapForwardLabel || 0 == mapForwardLabel.size()) return null;

				org.drip.product.calib.ProductQuoteSet pqs = aCalibComp[i].calibQuoteSet (new
					org.drip.state.representation.LatentStateSpecification[] {new
						org.drip.state.representation.LatentStateSpecification
							(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FUNDING,
								org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR,
									org.drip.state.identifier.FundingLabel.Standard
										(aCalibComp[i].payCurrency()))});

				if (null == pqs || !pqs.set (astrCalibMeasure[i], adblCalibQuote[i])) return null;

				aLSSS[i] = new org.drip.state.inference.LatentStateSegmentSpec (aCalibComp[i], pqs);
			}

			return new org.drip.state.inference.LatentStateStretchSpec (strName, aLSSS);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Forward Latent State Stretch Spec Instance
	 * 
	 * @param strName Stretch Name
	 * @param aCalibComp Array of Calibration Components
	 * @param strCalibMeasure The Calibration Measure
	 * @param adblCalibQuote Array of the Calibration Quotes
	 * 
	 * @return Forward Latent State Stretch Spec Instance
	 */

	public static final org.drip.state.inference.LatentStateStretchSpec ForwardStretchSpec (
		final java.lang.String strName,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp,
		final java.lang.String strCalibMeasure,
		final double[] adblCalibQuote)
	{
		if (null == strCalibMeasure || strCalibMeasure.isEmpty() || null == adblCalibQuote) return null;

		int iNumComp = adblCalibQuote.length;
		java.lang.String[] astrCalibMeasure = new java.lang.String[iNumComp];

		for (int i = 0; i < iNumComp; ++i)
			astrCalibMeasure[i] = strCalibMeasure;

		return ForwardStretchSpec (strName, aCalibComp, astrCalibMeasure, adblCalibQuote);
	}

	/**
	 * Construct a Merged Forward-Funding Latent State Stretch Spec Instance
	 * 
	 * @param strName Stretch Name
	 * @param aCalibComp Array of Calibration Components
	 * @param strCalibMeasure The Calibration Measure
	 * @param adblCalibQuote Array of the Calibration Quotes
	 * 
	 * @return Merged Forward-Funding Latent State Stretch Spec Instance
	 */

	public static final org.drip.state.inference.LatentStateStretchSpec ForwardFundingStretchSpec (
		final java.lang.String strName,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp,
		final java.lang.String strCalibMeasure,
		final double[] adblCalibQuote)
	{
		if (null == strCalibMeasure || strCalibMeasure.isEmpty() || null == adblCalibQuote) return null;

		int iNumComp = adblCalibQuote.length;
		java.lang.String[] astrCalibMeasure = new java.lang.String[iNumComp];

		for (int i = 0; i < iNumComp; ++i)
			astrCalibMeasure[i] = strCalibMeasure;

		return ForwardFundingStretchSpec (strName, aCalibComp, astrCalibMeasure, adblCalibQuote);
	}

	/**
	 * Construct a Funding Latent State Stretch Spec Instance
	 * 
	 * @param strName Stretch Name
	 * @param aCalibComp Array of Calibration Components
	 * @param strCalibMeasure The Calibration Measure
	 * @param adblCalibQuote Array of the Calibration Quotes
	 * 
	 * @return Funding Latent State Stretch Spec Instance
	 */

	public static final org.drip.state.inference.LatentStateStretchSpec FundingStretchSpec (
		final java.lang.String strName,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp,
		final java.lang.String strCalibMeasure,
		final double[] adblCalibQuote)
	{
		if (null == strCalibMeasure || strCalibMeasure.isEmpty() || null == adblCalibQuote) return null;

		int iNumComp = adblCalibQuote.length;
		java.lang.String[] astrCalibMeasure = new java.lang.String[iNumComp];

		for (int i = 0; i < iNumComp; ++i)
			astrCalibMeasure[i] = strCalibMeasure;

		return FundingStretchSpec (strName, aCalibComp, astrCalibMeasure, adblCalibQuote);
	}

	/**
	 * Construct an instance of LatentStateStretchSpec for the Construction of the Forward Curve from the
	 * 	specified Inputs
	 * 
	 * @param strName Stretch Name
	 * @param aCCSP Array of Calibration Cross Currency Swap Pair Instances
	 * @param valParams The Valuation Parameters
	 * @param mktParams The Basket Market Parameters to imply the Market Quote Measure
	 * @param adblBasis Array of the Basis on either the Reference Component or the Derived Component
	 * @param bBasisOnDerivedComponent TRUE - Apply the Basis on the Derived Component
	 * @param bBasisOnDerivedStream TRUE - Apply the Basis on the Derived Stream (FALSE - Reference Stream)
	 * 
	 * @return Instance of LatentStateStretchSpec
	 */

	public static final org.drip.state.inference.LatentStateStretchSpec ComponentPairForwardStretch (
		final java.lang.String strName,
		final org.drip.product.fx.ComponentPair[] aCCSP,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer mktParams,
		final double[] adblBasis,
		final boolean bBasisOnDerivedComponent,
		final boolean bBasisOnDerivedStream)
	{
		if (null == aCCSP || null == mktParams || null == adblBasis) return null;

		int iNumCCSP = aCCSP.length;

		if (0 == iNumCCSP || adblBasis.length != iNumCCSP) return null;

		org.drip.state.inference.LatentStateSegmentSpec[] aSegmentSpec = new
			org.drip.state.inference.LatentStateSegmentSpec[iNumCCSP];

		for (int i = 0; i < iNumCCSP; ++i) {
			if (null == aCCSP[i]) return null;

			try {
				if (null == (aSegmentSpec[i] = aCCSP[i].derivedForwardSpec (valParams, mktParams,
					adblBasis[i], bBasisOnDerivedComponent, bBasisOnDerivedStream)))
					return null;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		try {
			return new org.drip.state.inference.LatentStateStretchSpec (strName, aSegmentSpec);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an instance of LatentStateStretchSpec for the Construction of the Discount Curve from the
	 * 	specified Inputs
	 * 
	 * @param strName Stretch Name
	 * @param aCCSP Array of Calibration Cross Currency Swap Pair Instances
	 * @param valParams The Valuation Parameters
	 * @param mktParams The Basket Market Parameters to imply the Market Quote Measure
	 * @param adblReferenceComponentBasis Array of the Reference Component Reference Leg Basis Spread
	 * @param adblSwapRate Array of the IRS Calibration Swap Rates
	 * @param bBasisOnDerivedLeg TRUE - Apply the Basis on the Derived Leg (FALSE - Reference Leg)
	 * 
	 * @return Instance of LatentStateStretchSpec
	 */

	public static final org.drip.state.inference.LatentStateStretchSpec ComponentPairDiscountStretch (
		final java.lang.String strName,
		final org.drip.product.fx.ComponentPair[] aCCSP,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer mktParams,
		final double[] adblReferenceComponentBasis,
		final double[] adblSwapRate,
		final boolean bBasisOnDerivedLeg)
	{
		if (null == aCCSP || null == mktParams || null == adblReferenceComponentBasis || null ==
			adblSwapRate)
			return null;

		int iNumCCSP = aCCSP.length;

		if (0 == iNumCCSP || adblReferenceComponentBasis.length != iNumCCSP || adblSwapRate.length !=
			iNumCCSP)
			return null;

		org.drip.state.inference.LatentStateSegmentSpec[] aSegmentSpec = new
			org.drip.state.inference.LatentStateSegmentSpec[iNumCCSP];

		for (int i = 0; i < iNumCCSP; ++i) {
			if (null == (aSegmentSpec[i] = aCCSP[i].derivedFundingForwardSpec (valParams, mktParams,
				adblReferenceComponentBasis[i], bBasisOnDerivedLeg, adblSwapRate[i])))
				return null;
		}

		try {
			return new org.drip.state.inference.LatentStateStretchSpec (strName, aSegmentSpec);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a FX Latent State Stretch Spec Instance
	 * 
	 * @param strName Stretch Name
	 * @param aCalibComp Array of Calibration Components
	 * @param astrCalibMeasure Array of the Calibration Measures
	 * @param adblCalibQuote Array of the Calibration Quotes
	 * 
	 * @return FX Latent State Stretch Spec Instance
	 */

	public static final org.drip.state.inference.LatentStateStretchSpec FXStretchSpec (
		final java.lang.String strName,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp,
		final java.lang.String[] astrCalibMeasure,
		final double[] adblCalibQuote)
	{
		if (null == aCalibComp || null == astrCalibMeasure || null == adblCalibQuote) return null;

		int iNumComp = aCalibComp.length;
		org.drip.state.inference.LatentStateSegmentSpec[] aLSSS = new
			org.drip.state.inference.LatentStateSegmentSpec[iNumComp];

		if (0 == iNumComp || iNumComp != astrCalibMeasure.length || iNumComp != adblCalibQuote.length)
			return null;

		try {
			for (int i = 0; i < iNumComp; ++i) {
				if (null == aCalibComp[i] || null == astrCalibMeasure[i] || astrCalibMeasure[i].isEmpty() ||
					!org.drip.quant.common.NumberUtil.IsValid (adblCalibQuote[i]))
					return null;

				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel>
					mapFXLabel = aCalibComp[i].fxLabel();

				if (null == mapFXLabel || 0 == mapFXLabel.size()) return null;

				org.drip.product.calib.ProductQuoteSet pqs = aCalibComp[i].calibQuoteSet (new
					org.drip.state.representation.LatentStateSpecification[] {new
						org.drip.state.representation.LatentStateSpecification 
							(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FX,
								org.drip.analytics.definition.LatentStateStatic.FX_QM_FORWARD_OUTRIGHT,
									mapFXLabel.get ("DERIVED"))});

				if (null == pqs || !pqs.set (astrCalibMeasure[i], adblCalibQuote[i])) return null;

				aLSSS[i] = new org.drip.state.inference.LatentStateSegmentSpec (aCalibComp[i], pqs);
			}

			return new org.drip.state.inference.LatentStateStretchSpec (strName, aLSSS);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a FX Latent State Stretch Spec Instance
	 * 
	 * @param strName Stretch Name
	 * @param aCalibComp Array of Calibration Components
	 * @param strCalibMeasure The Calibration Measure
	 * @param adblCalibQuote Array of the Calibration Quotes
	 * 
	 * @return FX Latent State Stretch Spec Instance
	 */

	public static final org.drip.state.inference.LatentStateStretchSpec FXStretchSpec (
		final java.lang.String strName,
		final org.drip.product.definition.CalibratableComponent[] aCalibComp,
		final java.lang.String strCalibMeasure,
		final double[] adblCalibQuote)
	{
		if (null == strCalibMeasure || strCalibMeasure.isEmpty() || null == adblCalibQuote) return null;

		int iNumComp = adblCalibQuote.length;
		java.lang.String[] astrCalibMeasure = new java.lang.String[iNumComp];

		for (int i = 0; i < iNumComp; ++i)
			astrCalibMeasure[i] = strCalibMeasure;

		return FXStretchSpec (strName, aCalibComp, astrCalibMeasure, adblCalibQuote);
	}
}
