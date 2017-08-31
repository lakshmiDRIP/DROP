
package org.drip.product.fx;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * ComponentPair contains the implementation of the dual cross currency components. It is composed of two
 *  different Rates Components - one each for each currency.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ComponentPair extends org.drip.product.definition.BasketProduct {
	private java.lang.String _strName = "";
	private org.drip.param.period.FixingSetting _fxFixingSetting = null;
	private org.drip.product.definition.CalibratableComponent _rcDerived = null;
	private org.drip.product.definition.CalibratableComponent _rcReference = null;

	/**
	 * ComponentPair constructor
	 * 
	 * @param strName The ComponentPair Instance Name
	 * @param rcReference The Reference Component
	 * @param rcDerived The Derived Component
	 * @param fxFixingSetting FX Fixing Setting
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ComponentPair (
		final java.lang.String strName,
		final org.drip.product.definition.CalibratableComponent rcReference,
		final org.drip.product.definition.CalibratableComponent rcDerived,
		final org.drip.param.period.FixingSetting fxFixingSetting)
		throws java.lang.Exception
	{
		if (null == (_strName = strName) || _strName.isEmpty() || null == (_rcDerived = rcDerived) || null ==
			(_rcReference = rcReference))
			throw new java.lang.Exception ("ComponentPair ctr: Invalid Inputs!");

		_fxFixingSetting = fxFixingSetting;
	}

	/**
	 * Retrieve the Reference Component
	 * 
	 * @return The Reference Component
	 */

	public org.drip.product.definition.CalibratableComponent referenceComponent()
	{
		return _rcReference;
	}

	/**
	 * Retrieve the Derived Component
	 * 
	 * @return The Derived Component
	 */

	public org.drip.product.definition.CalibratableComponent derivedComponent()
	{
		return _rcDerived;
	}

	/**
	 * Retrieve the FX Fixing Setting
	 * 
	 * @return The FX Fixing Setting
	 */

	public org.drip.param.period.FixingSetting fxFixingSetting()
	{
		return _fxFixingSetting;
	}

	/**
	 * Retrieve the FX Code
	 * 
	 * @return The FX Code
	 */

	public java.lang.String fxCode()
	{
		java.lang.String strDerivedComponentCouponCurrency = _rcDerived.payCurrency();

		java.lang.String strReferenceComponentCouponCurrency = _rcReference.payCurrency();

		return strDerivedComponentCouponCurrency.equalsIgnoreCase (strReferenceComponentCouponCurrency) ?
			null : strReferenceComponentCouponCurrency + "/" + strDerivedComponentCouponCurrency;
	}

	/**
	 * Generate the Derived Forward Latent State Segment Specification
	 * 
	 * @param valParams Valuation Parameters
	 * @param mktParams Market Parameters
	 * @param dblBasis The Basis on either the Reference Component or the Derived Component
	 * @param bBasisOnDerivedComponent TRUE - Apply the Basis on the Derived Component
	 * @param bBasisOnDerivedStream TRUE - Apply the Basis on the Derived Stream (FALSE - Reference Stream)
	 * 
	 * @return The Derived Forward Latent State Segment Specification
	 */

	public org.drip.state.inference.LatentStateSegmentSpec derivedForwardSpec (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer mktParams,
		final double dblBasis,
		final boolean bBasisOnDerivedComponent,
		final boolean bBasisOnDerivedStream)
	{
		org.drip.product.calib.ProductQuoteSet pqs = null;
		org.drip.state.identifier.ForwardLabel forwardLabel = null;

		org.drip.product.definition.CalibratableComponent comp = derivedComponent();

		if (comp instanceof org.drip.product.rates.DualStreamComponent)
			forwardLabel = ((org.drip.product.rates.DualStreamComponent)
				comp).derivedStream().forwardLabel();
		else {
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
				mapForwardLabel = comp.forwardLabel();

			if (null != mapForwardLabel && 0 != mapForwardLabel.size())
				forwardLabel = mapForwardLabel.get (0);
		}

		try { 
			pqs = comp.calibQuoteSet (new org.drip.state.representation.LatentStateSpecification[] {new
				org.drip.state.representation.LatentStateSpecification
					(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FORWARD,
						org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE,
							forwardLabel)});
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapOP = value (valParams, null,
			mktParams, null);

		org.drip.product.definition.CalibratableComponent rcReference = referenceComponent();

		java.lang.String strReferenceComponentName = rcReference.name();

		org.drip.product.definition.CalibratableComponent rcDerived = derivedComponent();

		java.lang.String strDerivedComponentName = rcDerived.name();

		java.lang.String strReferenceComponentPV = strReferenceComponentName + "[PV]";

		if (!bBasisOnDerivedComponent) {
			java.lang.String strReferenceComponentDerivedStreamCleanDV01 = strReferenceComponentName +
				"[DerivedCleanDV01]";
			java.lang.String strReferenceComponentReferenceStreamCleanDV01 = strReferenceComponentName +
				"[ReferenceCleanDV01]";

			if (null == mapOP || !mapOP.containsKey (strReferenceComponentPV) || !mapOP.containsKey
				(strReferenceComponentReferenceStreamCleanDV01) || !mapOP.containsKey
					(strReferenceComponentDerivedStreamCleanDV01))
				return null;

			if (!pqs.set ("PV", -1. * (mapOP.get (strReferenceComponentPV) + 10000. * (bBasisOnDerivedStream
				? mapOP.get (strReferenceComponentDerivedStreamCleanDV01) : mapOP.get
					(strReferenceComponentReferenceStreamCleanDV01)) * dblBasis)))
				return null;
		} else {
			java.lang.String strDerivedComponentReferenceStreamCleanDV01 = strDerivedComponentName +
				"[ReferenceCleanDV01]";
			java.lang.String strDerivedComponentDerivedStreamCleanDV01 = strDerivedComponentName +
				"[DerivedCleanDV01]";

			if (null == mapOP || !mapOP.containsKey (strReferenceComponentPV) || !mapOP.containsKey
				(strDerivedComponentReferenceStreamCleanDV01) || !mapOP.containsKey
					(strDerivedComponentDerivedStreamCleanDV01))
				return null;

			if (!pqs.set ("PV", -1. * (mapOP.get (strReferenceComponentPV) + 10000. * (bBasisOnDerivedStream
				? mapOP.get (strDerivedComponentDerivedStreamCleanDV01) : mapOP.get
					(strDerivedComponentReferenceStreamCleanDV01)) * dblBasis)))
				return null;
		}

		try {
			return new org.drip.state.inference.LatentStateSegmentSpec (comp, pqs);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Derived Funding/Forward Merged Latent State Segment Specification
	 * 
	 * @param valParams Valuation Parameters
	 * @param mktParams Market Parameters
	 * @param dblReferenceComponentBasis The Reference Component Basis
	 * @param bBasisOnDerivedLeg TRUE - Apply basis on the Derived Leg
	 * @param dblSwapRate The Swap Rate
	 * 
	 * @return The Derived Forward/Funding Latent State Segment Specification
	 */

	public org.drip.state.inference.LatentStateSegmentSpec derivedFundingForwardSpec (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer mktParams,
		final double dblReferenceComponentBasis,
		final boolean bBasisOnDerivedLeg,
		final double dblSwapRate)
	{
		double dblFX = 1.;
		org.drip.product.calib.ProductQuoteSet pqs = null;
		org.drip.state.identifier.ForwardLabel forwardLabel = null;
		org.drip.state.identifier.FundingLabel fundingLabel = null;

		org.drip.product.definition.CalibratableComponent compDerived = derivedComponent();

		org.drip.product.definition.CalibratableComponent compReference = referenceComponent();

		if (compDerived instanceof org.drip.product.rates.DualStreamComponent) {
			org.drip.product.rates.Stream streamDerived = ((org.drip.product.rates.DualStreamComponent)
				compDerived).derivedStream();

			forwardLabel = streamDerived.forwardLabel();

			fundingLabel = streamDerived.fundingLabel();
		} else {
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
				mapForwardLabel = compDerived.forwardLabel();

			org.drip.state.identifier.FundingLabel fundingLabelDerived = compDerived.fundingLabel();

			if (null != mapForwardLabel && 0 != mapForwardLabel.size())
				forwardLabel = mapForwardLabel.get ("DERIVED");

			if (null != fundingLabelDerived) fundingLabel = fundingLabelDerived;
		}

		try { 
			pqs = compDerived.calibQuoteSet (new org.drip.state.representation.LatentStateSpecification[]
				{new org.drip.state.representation.LatentStateSpecification
					(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FUNDING,
						org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR,
							fundingLabel), new org.drip.state.representation.LatentStateSpecification
								(org.drip.analytics.definition.LatentStateStatic.LATENT_STATE_FORWARD,
									org.drip.analytics.definition.LatentStateStatic.FORWARD_QM_FORWARD_RATE,
										forwardLabel)});

			if (null != _fxFixingSetting && org.drip.param.period.FixingSetting.FIXING_PRESET_STATIC ==
				_fxFixingSetting.type()) {
				org.drip.state.fx.FXCurve fxfc = mktParams.fxState (fxLabel()[0]);

				if (null == fxfc) return null;

				dblFX = fxfc.fx (_fxFixingSetting.staticDate());
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapOP = compReference.value
			(valParams, null, mktParams, null);

		if (null == mapOP || !mapOP.containsKey ("PV") || !pqs.set ("SwapRate", dblSwapRate)) return null;

		if (bBasisOnDerivedLeg) {
			if (!mapOP.containsKey ("DerivedCleanDV01") || !pqs.set ("PV", dblFX * (mapOP.get ("PV") + 10000.
				* mapOP.get ("DerivedCleanDV01") * dblReferenceComponentBasis)))
				return null;
		} else {
			if (!mapOP.containsKey ("ReferenceCleanDV01") || !pqs.set ("PV", -1. * dblFX * (mapOP.get ("PV")
				+ 10000. * mapOP.get ("ReferenceCleanDV01") * dblReferenceComponentBasis)))
				return null;
		}

		try {
			return new org.drip.state.inference.LatentStateSegmentSpec (compDerived, pqs);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public java.lang.String name()
	{
		return _strName;
	}

	@Override public org.drip.state.identifier.FXLabel[] fxLabel()
	{
		java.lang.String strReferenceCurrency = _rcReference.payCurrency();

		java.lang.String strDerivedCurrency = _rcDerived.payCurrency();

		return new org.drip.state.identifier.FXLabel[] {org.drip.state.identifier.FXLabel.Standard
			(strReferenceCurrency + "/" + strDerivedCurrency), org.drip.state.identifier.FXLabel.Standard
				(strDerivedCurrency + "/" + strReferenceCurrency)};
	}

	@Override public org.drip.product.definition.Component[] components()
	{
		return new org.drip.product.definition.Component[] {_rcReference, _rcDerived};
	}

	@Override public int measureAggregationType (
		final java.lang.String strMeasureName)
	{
		return org.drip.product.definition.BasketProduct.MEASURE_AGGREGATION_TYPE_UNIT_ACCUMULATE;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		long lStart = System.nanoTime();

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapOutput = super.value
			(valParams, pricerParams, csqs, vcp);

		if (null == mapOutput) return null;

		org.drip.product.definition.CalibratableComponent rcReference = referenceComponent();

		org.drip.product.definition.CalibratableComponent rcDerived = derivedComponent();

		java.lang.String strReferenceCompName = rcReference.name();

		java.lang.String strDerivedCompName = rcDerived.name();

		java.lang.String strDerivedCompPV = strDerivedCompName + "[PV]";
		java.lang.String strReferenceCompPV = strReferenceCompName + "[PV]";
		java.lang.String strDerivedCompDerivedDV01 = strDerivedCompName + "[DerivedCleanDV01]";
		java.lang.String strReferenceCompDerivedDV01 = strReferenceCompName + "[DerivedCleanDV01]";
		java.lang.String strDerivedCompReferenceDV01 = strDerivedCompName + "[ReferenceCleanDV01]";
		java.lang.String strReferenceCompReferenceDV01 = strReferenceCompName + "[ReferenceCleanDV01]";
		java.lang.String strDerivedCompCumulativeConvexityPremium = strDerivedCompName +
			"[CumulativeConvexityAdjustmentPremium]";
		java.lang.String strDerivedCompCumulativeConvexityAdjustment = strDerivedCompName +
			"[CumulativeConvexityAdjustmentFactor]";
		java.lang.String strReferenceCompCumulativeConvexityPremium = strReferenceCompName +
			"[CumulativeConvexityAdjustmentPremium]";
		java.lang.String strReferenceCompCumulativeConvexityAdjustment = strReferenceCompName +
			"[QuantoAdjustmentFactor]";

		if (!mapOutput.containsKey (strDerivedCompPV) || !mapOutput.containsKey (strReferenceCompPV) ||
			!mapOutput.containsKey (strReferenceCompReferenceDV01) || !mapOutput.containsKey
				(strReferenceCompDerivedDV01) || !mapOutput.containsKey (strDerivedCompReferenceDV01) ||
					!mapOutput.containsKey (strDerivedCompDerivedDV01) || !mapOutput.containsKey
						(strDerivedCompCumulativeConvexityPremium) || !mapOutput.containsKey
							(strReferenceCompCumulativeConvexityPremium)) {
			mapOutput.put ("CalcTime", (System.nanoTime() - lStart) * 1.e-09);

			return mapOutput;
		}

		double dblDerivedCompPV = mapOutput.get (strDerivedCompPV);

		double dblReferenceCompPV = mapOutput.get (strReferenceCompPV);

		double dblDerivedCompDerivedDV01 = mapOutput.get (strDerivedCompDerivedDV01);

		double dblDerivedCompReferenceDV01 = mapOutput.get (strDerivedCompReferenceDV01);

		double dblReferenceCompDerivedDV01 = mapOutput.get (strReferenceCompDerivedDV01);

		double dblReferenceCompReferenceDV01 = mapOutput.get (strReferenceCompReferenceDV01);

		mapOutput.put ("ReferenceCompReferenceBasis", -1. * (dblDerivedCompPV + dblReferenceCompPV) /
			dblReferenceCompReferenceDV01);

		mapOutput.put ("ReferenceCompDerivedBasis", -1. * (dblDerivedCompPV + dblReferenceCompPV) /
			dblReferenceCompDerivedDV01);

		mapOutput.put ("DerivedCompReferenceBasis", -1. * (dblDerivedCompPV + dblReferenceCompPV) /
			dblDerivedCompReferenceDV01);

		mapOutput.put ("DerivedCompDerivedBasis", -1. * (dblDerivedCompPV + dblReferenceCompPV) /
			dblDerivedCompDerivedDV01);

		if (mapOutput.containsKey (strReferenceCompCumulativeConvexityAdjustment))
			mapOutput.put ("ReferenceCumulativeConvexityAdjustmentFactor", mapOutput.get
				(strReferenceCompCumulativeConvexityAdjustment));

		double dblReferenceCumulativeConvexityAdjustmentPremium = mapOutput.get
			(strReferenceCompCumulativeConvexityPremium);

		mapOutput.put ("ReferenceCumulativeConvexityAdjustmentPremium",
			dblReferenceCumulativeConvexityAdjustmentPremium);

		if (mapOutput.containsKey (strDerivedCompCumulativeConvexityAdjustment))
			mapOutput.put ("DerivedCumulativeConvexityAdjustmentFactor", mapOutput.get
				(strDerivedCompCumulativeConvexityAdjustment));

		double dblDerivedCumulativeConvexityAdjustmentPremium = mapOutput.get
			(strDerivedCompCumulativeConvexityPremium);

		mapOutput.put ("DerivedCumulativeConvexityAdjustmentPremium",
			dblDerivedCumulativeConvexityAdjustmentPremium);

		try {
			mapOutput.put ("CumulativeConvexityAdjustmentPremium", _rcReference.initialNotional() *
				dblReferenceCumulativeConvexityAdjustmentPremium + _rcDerived.initialNotional() *
					dblDerivedCumulativeConvexityAdjustmentPremium);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		mapOutput.put ("CalcTime", (System.nanoTime() - lStart) * 1.e-09);

		return mapOutput;
	}
}
