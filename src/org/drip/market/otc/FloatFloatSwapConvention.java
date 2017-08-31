
package org.drip.market.otc;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * FloatFloatSwapConvention contains the Details of the IBOR Float-Float Component of an OTC contact.
 *
 * @author Lakshmi Krishnamurthy
 */

public class FloatFloatSwapConvention {
	private int _iSpotLag = -1;
	private boolean _bIsComponentPair = false;
	private java.lang.String _strCurrency = "";
	private boolean _bBasisOnDerivedStream = true;
	private boolean _bBasisOnDerivedComponent = true;
	private java.lang.String _strReferenceTenor = "";
	private boolean _bIsDerivedCompoundedToReference = true;

	private org.drip.product.rates.FixFloatComponent fixFloatComponent (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strFloaterTenor,
		final java.lang.String strMaturityTenor,
		final double dblFixedCoupon,
		final double dblBasis,
		final double dblNotional)
	{
		org.drip.market.otc.FixedFloatSwapConvention ffConv =
			org.drip.market.otc.IBORFixedFloatContainer.ConventionFromJurisdiction (_strCurrency);

		if (null == ffConv) return null;

		org.drip.market.otc.FixedStreamConvention fixedConv = ffConv.fixedStreamConvention();

		org.drip.product.rates.Stream streamFixed = fixedConv.createStream (dtEffective, strMaturityTenor,
			dblFixedCoupon + (_bBasisOnDerivedStream ? 0. : dblBasis), dblNotional);

		org.drip.state.identifier.ForwardLabel forwardLabel = org.drip.state.identifier.ForwardLabel.Create
			(org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction (_strCurrency),
				strFloaterTenor);

		java.lang.String strFloaterTenorComposite = _bIsDerivedCompoundedToReference ?
			fixedConv.compositePeriodTenor() : strFloaterTenor;

		try {
			org.drip.param.period.ComposableFloatingUnitSetting cfus = new
				org.drip.param.period.ComposableFloatingUnitSetting (strFloaterTenor,
					org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR, null,
						forwardLabel,
							org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
								_bBasisOnDerivedStream ? dblBasis : 0.);

			org.drip.param.period.CompositePeriodSetting cps = new
				org.drip.param.period.CompositePeriodSetting
					(org.drip.analytics.support.Helper.TenorToFreq (strFloaterTenorComposite),
						strFloaterTenorComposite, _strCurrency, null, -1. * dblNotional, null, null, null,
							null);

			java.util.List<java.lang.Integer> lsEdgeDate =
				org.drip.analytics.support.CompositePeriodBuilder.RegularEdgeDates (dtEffective,
					strFloaterTenorComposite, strMaturityTenor, null);

			org.drip.product.rates.Stream streamFloater = new org.drip.product.rates.Stream
				(org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit (lsEdgeDate, cps,
					cfus));

			org.drip.product.rates.FixFloatComponent ffc = new org.drip.product.rates.FixFloatComponent
				(streamFixed, streamFloater, null);

			ffc.setPrimaryCode ("IRS::" + ffc.forwardLabel().get ("DERIVED").fullyQualifiedName() + "." +
				strMaturityTenor);

			return ffc;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * FloatFloatSwapConvention Constructor
	 * 
	 * @param strCurrency The Currency
	 * @param strReferenceTenor The Reference Tenor
	 * @param bBasisOnDerivedStream TRUE - Apply the Basis to the Derived Stream
	 * @param bBasisOnDerivedComponent TRUE - Apply the Basis to the Derived Component
	 * @param bIsDerivedCompoundedToReference TRUE - The Derived Periods are Compounded onto the Reference
	 * @param bIsComponentPair TRUE - The Float-Float Swap is a Component Pair of 2 Fix-Float Swaps
	 * @param iSpotLag Spot Lag
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FloatFloatSwapConvention (
		final java.lang.String strCurrency,
		final java.lang.String strReferenceTenor,
		final boolean bBasisOnDerivedStream,
		final boolean bBasisOnDerivedComponent,
		final boolean bIsDerivedCompoundedToReference,
		final boolean bIsComponentPair,
		final int iSpotLag)
		throws java.lang.Exception
	{
		if (null == (_strCurrency = strCurrency) || _strCurrency.isEmpty() || null == (_strReferenceTenor =
			strReferenceTenor) || _strReferenceTenor.isEmpty() || 0 > (_iSpotLag = iSpotLag))
			throw new java.lang.Exception ("FloatFloatSwapConvention ctr: Invalid Inputs");

		_bIsComponentPair = bIsComponentPair;
		_bBasisOnDerivedStream = bBasisOnDerivedStream;
		_bBasisOnDerivedComponent = bBasisOnDerivedComponent;
		_bIsDerivedCompoundedToReference = bIsDerivedCompoundedToReference;
	}

	/**
	 * Retrieve the Currency
	 * 
	 * @return The Currency
	 */

	public java.lang.String currency()
	{
		return _strCurrency;
	}

	/**
	 * Retrieve the Reference Tenor
	 * 
	 * @return The Reference Tenor
	 */

	public java.lang.String referenceTenor()
	{
		return _strReferenceTenor;
	}

	/**
	 * Retrieve the Flag indicating whether the Basis is to be applied to the Derived or the Reference Stream
	 * 
	 * @return TRUE - The Basis is applied to the Derived Stream
	 */

	public boolean basisOnDerivedStream()
	{
		return _bBasisOnDerivedStream;
	}

	/**
	 * Retrieve the Flag indicating whether the Basis is to be applied to the Derived or the Reference
	 * 	Component
	 * 
	 * @return TRUE - The Basis is applied to the Derived Component
	 */

	public boolean basisOnDerivedComponent()
	{
		return _bBasisOnDerivedComponent;
	}

	/**
	 * Retrieve the Flag indicating whether the Derived Periods are to be compounded onto the Reference
	 *  Period
	 * 
	 * @return TRUE - The Derived Periods are Compounded onto the Reference
	 */

	public boolean derivedCompoundedToReference()
	{
		return _bIsDerivedCompoundedToReference;
	}

	/**
	 * Retrieve the Flag indicating whether the Float-Float Swap is a Component Pair of 2 Fix-Float Swaps
	 * 
	 * @return TRUE - The Float-Float Swap is a Component Pair of 2 Fix-Float Swaps
	 */

	public boolean componentPair()
	{
		return _bIsComponentPair;
	}

	/**
	 * Retrieve the Spot Lag
	 * 
	 * @return The Spot Lag
	 */

	public int spotLag()
	{
		return _iSpotLag;
	}

	/**
	 * Create an Instance of the Float-Float Component
	 * 
	 * @param dtSpot Spot Date
	 * @param strDerivedTenor The Derived Tenor
	 * @param strMaturityTenor The Maturity Tenor
	 * @param dblBasis Basis
	 * @param dblNotional Notional
	 * 
	 * @return Instance of the Float-Float Component
	 */

	public org.drip.product.rates.FloatFloatComponent createFloatFloatComponent (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strDerivedTenor,
		final java.lang.String strMaturityTenor,
		final double dblBasis,
		final double dblNotional)
	{
		if (_bIsComponentPair || null == dtSpot) return null;

		org.drip.state.identifier.ForwardLabel forwardLabelReference =
			org.drip.state.identifier.ForwardLabel.Create
				(org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction (_strCurrency),
					_strReferenceTenor);

		if (null == forwardLabelReference) return null;

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (_iSpotLag,
			forwardLabelReference.floaterIndex().calendar());

		org.drip.state.identifier.ForwardLabel forwardLabelDerived =
			org.drip.state.identifier.ForwardLabel.Create
				(org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction (_strCurrency),
					strDerivedTenor);

		if (null == forwardLabelDerived) return null;

		java.lang.String strDerivedTenorComposite = _bIsDerivedCompoundedToReference ? _strReferenceTenor :
			strDerivedTenor;

		try {
			org.drip.param.period.ComposableFloatingUnitSetting cfusReference = new
				org.drip.param.period.ComposableFloatingUnitSetting (_strReferenceTenor,
					org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR, null,
						forwardLabelReference,
							org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
								!_bBasisOnDerivedStream ? dblBasis : 0.);

			org.drip.param.period.CompositePeriodSetting cpsReference = new
				org.drip.param.period.CompositePeriodSetting
					(org.drip.analytics.support.Helper.TenorToFreq (_strReferenceTenor),
						_strReferenceTenor, _strCurrency, null, dblNotional, null, null, null, null);

			java.util.List<java.lang.Integer> lsReferenceEdgeDate =
				org.drip.analytics.support.CompositePeriodBuilder.RegularEdgeDates (dtEffective,
					_strReferenceTenor, strMaturityTenor, null);

			org.drip.product.rates.Stream streamReference = new org.drip.product.rates.Stream
				(org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit
					(lsReferenceEdgeDate, cpsReference, cfusReference));

			org.drip.param.period.ComposableFloatingUnitSetting cfusDerived = new
				org.drip.param.period.ComposableFloatingUnitSetting (strDerivedTenor,
					org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR, null,
						forwardLabelDerived,
							org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
								_bBasisOnDerivedStream ? dblBasis : 0.);

			org.drip.param.period.CompositePeriodSetting cpsDerived = new
				org.drip.param.period.CompositePeriodSetting
					(org.drip.analytics.support.Helper.TenorToFreq (strDerivedTenorComposite),
						strDerivedTenorComposite, _strCurrency, null, -1. * dblNotional, null, null, null,
							null);

			java.util.List<java.lang.Integer> lsDerivedEdgeDate =
				org.drip.analytics.support.CompositePeriodBuilder.RegularEdgeDates (dtEffective,
					strDerivedTenor, strMaturityTenor, null);

			org.drip.product.rates.Stream streamDerived = new org.drip.product.rates.Stream
				(org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit (lsDerivedEdgeDate,
					cpsDerived, cfusDerived));

			return new org.drip.product.rates.FloatFloatComponent (streamReference, streamDerived, null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Fix-Float Component Pair
	 * 
	 * @param dtSpot Spot Date
	 * @param strDerivedTenor The Derived Tenor
	 * @param strMaturityTenor The Maturity Tenor
	 * @param dblReferenceFixedCoupon Fixed Coupon Rate for the Reference Component
	 * @param dblDerivedFixedCoupon Fixed Coupon Rate for the Derived Component
	 * @param dblBasis Basis
	 * @param dblNotional Notional
	 * 
	 * @return Instance of the Fix-Float Component Pair
	 */

	public org.drip.product.fx.ComponentPair createFixFloatComponentPair (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strDerivedTenor,
		final java.lang.String strMaturityTenor,
		final double dblReferenceFixedCoupon,
		final double dblDerivedFixedCoupon,
		final double dblBasis,
		final double dblNotional)
	{
		if (!_bIsComponentPair || null == dtSpot) return null;

		org.drip.market.definition.IBORIndex floaterIndex =
			org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction (_strCurrency);

		if (null == floaterIndex) return null;

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (_iSpotLag,
			floaterIndex.calendar());

		org.drip.product.rates.FixFloatComponent ffcReference = fixFloatComponent (dtEffective,
			_strReferenceTenor, strMaturityTenor, dblReferenceFixedCoupon, !_bBasisOnDerivedComponent ?
				dblBasis : 0., dblNotional);

		org.drip.product.rates.FixFloatComponent ffcDerived = fixFloatComponent (dtEffective,
			strDerivedTenor, strMaturityTenor, dblDerivedFixedCoupon, _bBasisOnDerivedComponent ? dblBasis :
				0., -1. * dblNotional);

		try {
			return new org.drip.product.fx.ComponentPair (_strCurrency + "::" + _strReferenceTenor + "/" +
				strDerivedTenor + "_" + strMaturityTenor, ffcReference, ffcDerived, null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
