
package org.drip.state.nonlinear;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * NonlinearCurveBuilder calibrates the discount and credit/hazard curves from the components and their
 *  quotes.
 * 
 * NonlinearCurveCalibrator employs a set of techniques for achieving this calibration.
 * 	- It bootstraps the nodes in sequence to calibrate the curve.
 * 	- In conjunction with splining estimation techniques, it may also be used to perform dual sweep
 * 		calibration. The inner sweep achieves the calibration of the segment spline parameters, while the
 * 		outer sweep calibrates iteratively for the targeted boundary conditions.
 * 	- It may also be used to custom calibrate a single Interest Rate/Hazard Rate Node from the corresponding
 * 		Component
 * 
 * CurveCalibrator bootstraps/cooks both discount curves and credit curves.
 *
 * @author Lakshmi Krishnamurthy
 */

public class NonlinearCurveBuilder {

	private static final boolean SetNode (
		final org.drip.analytics.definition.ExplicitBootCurve ebc,
		final int iNodeIndex,
		final boolean bFlat,
		final double dblValue)
	{
		return bFlat ? ebc.setFlatValue (dblValue) : ebc.setNodeValue (iNodeIndex, dblValue);
	}

	static class CreditCurveCalibrator extends org.drip.function.definition.R1ToR1 {
		private boolean _bFlat = false;
		private int _iCurveSegmentIndex = -1;
		private java.lang.String _strCalibMeasure = "";
		private double _dblCalibValue = java.lang.Double.NaN;
		private org.drip.state.govvie.GovvieCurve _gc = null;
		private org.drip.state.discount.MergedDiscountForwardCurve _dc = null;
		private org.drip.product.definition.Component _calibComp = null;
		private org.drip.param.valuation.ValuationParams _valParams = null;
		private org.drip.state.credit.ExplicitBootCreditCurve _ebcc = null;
		private org.drip.param.pricer.CreditPricerParams _pricerParams = null;
		private org.drip.param.market.LatentStateFixingsContainer _lsfc = null;
		private org.drip.param.valuation.ValuationCustomizationParams _vcp = null;

		CreditCurveCalibrator (
			final org.drip.param.valuation.ValuationParams valParams,
			final org.drip.product.definition.Component calibComp,
			final double dblCalibValue,
			final java.lang.String strCalibMeasure,
			final boolean bFlat,
			final int iCurveSegmentIndex,
			final org.drip.state.credit.ExplicitBootCreditCurve ebcc,
			final org.drip.state.discount.MergedDiscountForwardCurve dc,
			final org.drip.state.govvie.GovvieCurve gc,
			final org.drip.param.pricer.CreditPricerParams pricerParams,
			final org.drip.param.market.LatentStateFixingsContainer lsfc,
			final org.drip.param.valuation.ValuationCustomizationParams vcp)
			throws java.lang.Exception
		{
			super (null);

			_dc = dc;
			_gc = gc;
			_vcp = vcp;
			_ebcc = ebcc;
			_lsfc = lsfc;
			_bFlat = bFlat;
			_calibComp = calibComp;
			_valParams = valParams;
			_dblCalibValue = dblCalibValue;
			_strCalibMeasure = strCalibMeasure;
			_iCurveSegmentIndex = iCurveSegmentIndex;

			_pricerParams = new org.drip.param.pricer.CreditPricerParams (pricerParams.unitSize(), new
				org.drip.param.definition.CalibrationParams (strCalibMeasure, 0, null),
					pricerParams.survivalToPayDate(), pricerParams.discretizationScheme());
		}

		@Override public double evaluate (
			final double dblRate)
			throws java.lang.Exception
		{
			if (!SetNode (_ebcc, _iCurveSegmentIndex, _bFlat, dblRate))
				throw new java.lang.Exception
					("NonlinearCurveBuilder::CreditCurveCalibrator::evaluate => Cannot set Rate = " + dblRate
						+ " for node " + _iCurveSegmentIndex);

			return _dblCalibValue - _calibComp.measureValue (_valParams, _pricerParams,
				org.drip.param.creator.MarketParamsBuilder.Create (_dc, _gc, _ebcc, null, null, null, _lsfc),
					_vcp, _strCalibMeasure);
		}
	}

	/**
	 * Calibrate a single Hazard Rate Node from the corresponding Component
	 * 
	 * @param valParams Calibration Valuation Parameters
	 * @param calibComp The Calibration Component
	 * @param dblCalibValue The Value to be Calibrated to
	 * @param strCalibMeasure The Calibration Measure
	 * @param bFlat TRUE - Calibrate a Flat Curve across all Tenors
	 * @param iCurveSegmentIndex The Curve Segment Index
	 * @param ebcc The Credit Curve to be calibrated
	 * @param dc The discount curve to be bootstrapped
	 * @param gc The Govvie Curve
	 * @param pricerParams Input Pricer Parameters
	 * @param lsfc The Latent State Fixings Container
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return The successfully calibrated State Hazard Rate Point
	 */

	public static final boolean CreditCurve (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.product.definition.Component calibComp,
		final double dblCalibValue,
		final java.lang.String strCalibMeasure,
		final boolean bFlat,
		final int iCurveSegmentIndex,
		final org.drip.state.credit.ExplicitBootCreditCurve ebcc,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.govvie.GovvieCurve gc,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.LatentStateFixingsContainer lsfc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		try {
			org.drip.function.r1tor1solver.FixedPointFinderOutput rfop = new
				org.drip.function.r1tor1solver.FixedPointFinderZheng (0., new CreditCurveCalibrator
					(valParams, calibComp, dblCalibValue, strCalibMeasure, bFlat, iCurveSegmentIndex, ebcc,
						dc, gc, pricerParams, lsfc, vcp), true).findRoot();

			return null != rfop && rfop.containsRoot();
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Calibrate a Single Discount Curve Segment from the corresponding Component
	 * 
	 * @param valParams Calibration Valuation Parameters
	 * @param comp The Calibration Component
	 * @param dblCalibValue The Value to be Calibrated to
	 * @param strCalibMeasure The Calibration Measure
	 * @param bFlat TRUE - Calibrate a Flat Curve across all Tenors
	 * @param iCurveSegmentIndex The Curve Segment Index
	 * @param ebdc The discount curve to be bootstrapped
	 * @param gc The Govvie Curve
	 * @param lsfc Latent State Fixings Container
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return The successfully calibrated State IR Point
	 * 
	 * @throws java.lang.Exception Thrown if the Bootstrapping is unsuccessful
	 */

	public static final double DiscountCurveNode (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.product.definition.Component comp,
		final double dblCalibValue,
		final java.lang.String strCalibMeasure,
		final boolean bFlat,
		final int iCurveSegmentIndex,
		final org.drip.state.discount.ExplicitBootDiscountCurve ebdc,
		final org.drip.state.govvie.GovvieCurve gc,
		final org.drip.param.market.LatentStateFixingsContainer lsfc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
		throws java.lang.Exception
	{
		if (null == comp)
			throw new java.lang.Exception ("NonlinearCurveBuilder::DiscountCurveNode => Invalid inputs!");

		org.drip.function.definition.R1ToR1 ofIRNode = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblValue)
				throws java.lang.Exception
			{
				if (!SetNode (ebdc, iCurveSegmentIndex, bFlat, dblValue))
					throw new java.lang.Exception
						("NonlinearCurveBuilder::DiscountCurveNode => Cannot set Value = " + dblValue +
							" for node " + iCurveSegmentIndex);

				return dblCalibValue - comp.measureValue (valParams, new
					org.drip.param.pricer.CreditPricerParams (1, new
						org.drip.param.definition.CalibrationParams (strCalibMeasure, 0, null), true, 0),
							org.drip.param.creator.MarketParamsBuilder.Create (ebdc, gc, null, null, null,
								null, lsfc), vcp, strCalibMeasure);
			}
		};

		org.drip.function.r1tor1solver.FixedPointFinderOutput rfop = new
			org.drip.function.r1tor1solver.FixedPointFinderBrent (0., ofIRNode, true).findRoot();

		if (null == rfop || !rfop.containsRoot())
			throw new java.lang.Exception
				("NonlinearCurveBuilder::DiscountCurveNode => Cannot calibrate IR segment for node #" +
					iCurveSegmentIndex);

		return rfop.getRoot();
	}

	/**
	 * Boot-strap a Discount Curve from the set of calibration components
	 * 
	 * @param valParams Calibration Valuation Parameters
	 * @param aCalibComp Array of the calibration components
	 * @param adblCalibValue Array of Calibration Values
	 * @param astrCalibMeasure Array of Calibration Measures
	 * @param dblBump Amount to bump the Quotes by
	 * @param bFlat TRUE - Calibrate a Flat Curve across all Tenors
	 * @param ebdc The discount curve to be bootstrapped
	 * @param gc The Govvie Curve
	 * @param lsfc Latent State Fixings Container
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return TRUE - Bootstrapping was successful
	 */

	public static final boolean DiscountCurve (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.product.definition.Component[] aCalibComp,
		final double[] adblCalibValue,
		final java.lang.String[] astrCalibMeasure,
		final double dblBump,
		final boolean bFlat,
		final org.drip.state.discount.ExplicitBootDiscountCurve ebdc,
		final org.drip.state.govvie.GovvieCurve gc,
		final org.drip.param.market.LatentStateFixingsContainer lsfc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == adblCalibValue || null == aCalibComp || null == astrCalibMeasure ||
			!org.drip.quant.common.NumberUtil.IsValid (dblBump))
			return false;

		int iNumCalibComp = aCalibComp.length;

		if (0 == iNumCalibComp || adblCalibValue.length != iNumCalibComp || astrCalibMeasure.length !=
			iNumCalibComp)
			return false;

		for (int i = 0; i < iNumCalibComp; ++i) {
			try {
				if (!org.drip.quant.common.NumberUtil.IsValid (DiscountCurveNode (valParams, aCalibComp[i],
					adblCalibValue[i] + dblBump, astrCalibMeasure[i], bFlat, i, ebdc, gc, lsfc, vcp)))
					return false;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return false;
			}
		}

		return true;
	}

	/**
	 * Calibrate a Single Volatility Curve Segment from the corresponding Component
	 * 
	 * @param valParams Calibration Valuation Parameters
	 * @param comp The Calibration Component
	 * @param dblCalibValue The Value to be Calibrated to
	 * @param strCalibMeasure The Calibration Measure
	 * @param bFlat TRUE - Calibrate a Flat Curve across all Tenors
	 * @param iCurveSegmentIndex The Curve Segment Index
	 * @param ebvc The Volatility Curve to be bootstrapped
	 * @param dc The Discount Curve
	 * @param fc The Forward Curve
	 * @param lsfc Latent State Fixings Container
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return The successfully calibrated State IR Point
	 * 
	 * @throws java.lang.Exception Thrown if the Bootstrapping is unsuccessful
	 */

	public static final double VolatilityCurveNode (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.product.definition.Component comp,
		final double dblCalibValue,
		final java.lang.String strCalibMeasure,
		final boolean bFlat,
		final int iCurveSegmentIndex,
		final org.drip.state.volatility.ExplicitBootVolatilityCurve ebvc,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.forward.ForwardCurve fc,
		final org.drip.param.market.LatentStateFixingsContainer lsfc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
		throws java.lang.Exception
	{
		if (null == comp)
			throw new java.lang.Exception ("NonlinearCurveBuilder::VolatilityCurveNode => Invalid inputs!");

		org.drip.function.definition.R1ToR1 r1r1VolMetric = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblValue)
				throws java.lang.Exception
			{
				if (!SetNode (ebvc, iCurveSegmentIndex, bFlat, dblValue))
					throw new java.lang.Exception
						("NonlinearCurveBuilder::VolatilityCurveNode => Cannot set Value = " + dblValue +
							" for node " + iCurveSegmentIndex);

				org.drip.param.market.CurveSurfaceQuoteContainer csqs =
					org.drip.param.creator.MarketParamsBuilder.Create (dc, null, null, null, null, null,
						lsfc);

				if (null == csqs || !csqs.setForwardState (fc) || !csqs.setForwardVolatility (ebvc))
					throw new java.lang.Exception
						("NonlinearCurveBuilder::VolatilityCurveNode => Cannot set Value = " + dblValue +
							" for node " + iCurveSegmentIndex);

				return dblCalibValue - comp.measureValue (valParams, new
					org.drip.param.pricer.CreditPricerParams (1, new
						org.drip.param.definition.CalibrationParams (strCalibMeasure, 0, null), true, 0),
							csqs, vcp, strCalibMeasure);
			}
		};

		org.drip.function.r1tor1solver.FixedPointFinderOutput fpfo = (new
			org.drip.function.r1tor1solver.FixedPointFinderBrent (0., r1r1VolMetric, true)).findRoot
				(org.drip.function.r1tor1solver.InitializationHeuristics.FromHardSearchEdges (0.00001, 5.));

		if (null == fpfo || !fpfo.containsRoot())
			throw new java.lang.Exception
				("NonlinearCurveBuilder::VolatilityCurveNode => Cannot calibrate segment for node #" +
					iCurveSegmentIndex + " => " + dblCalibValue);

		return fpfo.getRoot();
	}

	/**
	 * Boot-strap a Volatility Curve from the set of calibration components
	 * 
	 * @param valParams Calibration Valuation Parameters
	 * @param aCalibComp Array of the calibration components
	 * @param adblCalibValue Array of Calibration Values
	 * @param astrCalibMeasure Array of Calibration Measures
	 * @param dblBump Amount to bump the Quotes by
	 * @param bFlat TRUE - Calibrate a Flat Curve across all Tenors
	 * @param ebvc The Volatility Curve to be bootstrapped
	 * @param dc The Discount Curve
	 * @param fc The Forward Curve
	 * @param lsfc Latent State Fixings Container
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return TRUE - Bootstrapping was successful
	 */

	public static final boolean VolatilityCurve (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.product.definition.Component[] aCalibComp,
		final double[] adblCalibValue,
		final java.lang.String[] astrCalibMeasure,
		final double dblBump,
		final boolean bFlat,
		final org.drip.state.volatility.ExplicitBootVolatilityCurve ebvc,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.forward.ForwardCurve fc,
		final org.drip.param.market.LatentStateFixingsContainer lsfc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == adblCalibValue || null == aCalibComp || null == astrCalibMeasure ||
			!org.drip.quant.common.NumberUtil.IsValid (dblBump))
			return false;

		int iNumCalibComp = aCalibComp.length;

		if (0 == iNumCalibComp || adblCalibValue.length != iNumCalibComp || astrCalibMeasure.length !=
			iNumCalibComp)
			return false;

		for (int i = 0; i < iNumCalibComp; ++i) {
			try {
				if (!org.drip.quant.common.NumberUtil.IsValid (VolatilityCurveNode (valParams, aCalibComp[i],
					adblCalibValue[i] + dblBump, astrCalibMeasure[i], bFlat, i, ebvc, dc, fc, lsfc, vcp)))
					return false;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return false;
			}
		}

		return true;
	}
}
