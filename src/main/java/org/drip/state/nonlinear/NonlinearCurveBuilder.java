
package org.drip.state.nonlinear;

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
 * <i>NonlinearCurveBuilder</i> calibrates the discount and credit/hazard curves from the components and
 * their quotes. NonlinearCurveCalibrator employs a set of techniques for achieving this calibration.
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 * 			It bootstraps the nodes in sequence to calibrate the curve.
 *  	</li>
 *  	<li>
 * 			In conjunction with splining estimation techniques, it may also be used to perform dual sweep
 * 				calibration. The inner sweep achieves the calibration of the segment spline parameters, while
 * 				the outer sweep calibrates iteratively for the targeted boundary conditions.
 *  	</li>
 *  	<li>
 * 			It may also be used to custom calibrate a single Interest Rate/Hazard Rate Node from the
 * 				corresponding Component.
 *  	</li>
 *  </ul>
 * 
 * CurveCalibrator bootstraps/cooks both discount curves and credit curves.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear/README.md">Nonlinear (i.e., Boot) Latent State Construction</a></li>
 *  </ul>
 * <br><br>
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
		private org.drip.param.definition.CalibrationParams _cp = null;
		private org.drip.product.definition.Component _calibComp = null;
		private org.drip.param.valuation.ValuationParams _valParams = null;
		private org.drip.state.credit.ExplicitBootCreditCurve _ebcc = null;
		private org.drip.param.pricer.CreditPricerParams _pricerParams = null;
		private org.drip.state.discount.MergedDiscountForwardCurve _dc = null;
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
			final org.drip.param.valuation.ValuationCustomizationParams vcp,
			final org.drip.param.definition.CalibrationParams cp)
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

			if (null == (_cp = cp))
				_cp = new org.drip.param.definition.CalibrationParams (strCalibMeasure, 0, null);

			_pricerParams = new org.drip.param.pricer.CreditPricerParams (pricerParams.unitSize(), _cp,
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
	 * @param cp The Calibration Parameters
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
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.param.definition.CalibrationParams cp)
	{
		try {
			org.drip.function.r1tor1solver.FixedPointFinderOutput rfop = new
				org.drip.function.r1tor1solver.FixedPointFinderZheng (0., new CreditCurveCalibrator
					(valParams, calibComp, dblCalibValue, strCalibMeasure, bFlat, iCurveSegmentIndex, ebcc,
						dc, gc, pricerParams, lsfc, vcp, cp), true).findRoot();

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
			!org.drip.numerical.common.NumberUtil.IsValid (dblBump))
			return false;

		int iNumCalibComp = aCalibComp.length;

		if (0 == iNumCalibComp || adblCalibValue.length != iNumCalibComp || astrCalibMeasure.length !=
			iNumCalibComp)
			return false;

		for (int i = 0; i < iNumCalibComp; ++i) {
			try {
				if (!org.drip.numerical.common.NumberUtil.IsValid (DiscountCurveNode (valParams, aCalibComp[i],
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
			!org.drip.numerical.common.NumberUtil.IsValid (dblBump))
			return false;

		int iNumCalibComp = aCalibComp.length;

		if (0 == iNumCalibComp || adblCalibValue.length != iNumCalibComp || astrCalibMeasure.length !=
			iNumCalibComp)
			return false;

		for (int i = 0; i < iNumCalibComp; ++i) {
			try {
				if (!org.drip.numerical.common.NumberUtil.IsValid (VolatilityCurveNode (valParams, aCalibComp[i],
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
