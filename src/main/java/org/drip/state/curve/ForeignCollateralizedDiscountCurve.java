
package org.drip.state.curve;

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
 * <i>ForeignCollateralizedDiscountCurve</i> computes the discount factor corresponding to one unit of
 * domestic currency collateralized by a foreign collateral.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve">Curve</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ForeignCollateralizedDiscountCurve extends org.drip.state.discount.ExplicitBootDiscountCurve {
	private java.lang.String _strCurrency = null;
	private org.drip.state.fx.FXCurve _fxForward = null;
	private org.drip.state.volatility.VolatilityCurve _vcFX = null;
	private org.drip.state.discount.MergedDiscountForwardCurve _dcCollateralForeign = null;
	private org.drip.state.volatility.VolatilityCurve _vcCollateralForeign = null;
	private org.drip.function.definition.R1ToR1 _r1r1CollateralForeignFXCorrelation = null;

	/**
	 * ForeignCollateralizedDiscountCurve constructor
	 * 
	 * @param strCurrency The Currency
	 * @param dcCollateralForeign The Collateralized Foreign Discount Curve
	 * @param fxForward The FX Forward Curve
	 * @param vcCollateralForeign The Foreign Collateral Volatility Curve
	 * @param vcFX The FX Volatility Curve
	 * @param r1r1CollateralForeignFXCorrelation The FX Foreign Collateral Correlation Curve
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public ForeignCollateralizedDiscountCurve (
		final java.lang.String strCurrency,
		final org.drip.state.discount.MergedDiscountForwardCurve dcCollateralForeign,
		final org.drip.state.fx.FXCurve fxForward,
		final org.drip.state.volatility.VolatilityCurve vcCollateralForeign,
		final org.drip.state.volatility.VolatilityCurve vcFX,
		final org.drip.function.definition.R1ToR1 r1r1CollateralForeignFXCorrelation)
		throws java.lang.Exception
	{
		super (dcCollateralForeign.epoch().julian(), strCurrency);

		if (null == (_strCurrency = strCurrency) || _strCurrency.isEmpty() || null == (_vcCollateralForeign =
			vcCollateralForeign) || null == (_vcFX = vcFX) || null == (_r1r1CollateralForeignFXCorrelation =
				r1r1CollateralForeignFXCorrelation) || null == (_dcCollateralForeign = dcCollateralForeign)
					|| null == (_fxForward = fxForward))
			throw new java.lang.Exception ("ForeignCollateralizedDiscountCurve ctr: Invalid Inputs");
	}

	@Override public double df (
		final int iDate)
		throws java.lang.Exception
	{
		return iDate <= _iEpochDate ? 1. : _dcCollateralForeign.df (iDate) * _fxForward.fx (iDate) *
			java.lang.Math.exp (-1. * org.drip.analytics.support.OptionHelper.IntegratedCrossVolQuanto
				(_vcFX, _vcCollateralForeign, _r1r1CollateralForeignFXCorrelation, _iEpochDate, iDate));
	}

	@Override public double forward (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		if (iDate1 < _iEpochDate || iDate2 < _iEpochDate) return 0.;

		return 365.25 / (iDate2 - iDate1) * java.lang.Math.log (df (iDate1) / df (iDate2));
	}

	@Override public double zero (
		final int iDate)
		throws java.lang.Exception
	{
		if (iDate < _iEpochDate) return 0.;

		return -365.25 / (iDate - _iEpochDate) * java.lang.Math.log (df (iDate));
	}

	@Override public org.drip.state.forward.ForwardRateEstimator forwardRateEstimator (
		final int iDate,
		final org.drip.state.identifier.ForwardLabel fri)
	{
		return null;
	}

	@Override public java.util.Map<java.lang.Integer, java.lang.Double> canonicalTruthness (
		final java.lang.String strLatentQuantificationMetric)
	{
		return null;
	}

	@Override public org.drip.state.nonlinear.FlatForwardDiscountCurve parallelShiftManifestMeasure (
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.state.nonlinear.FlatForwardDiscountCurve shiftManifestMeasure (
		final int iSpanIndex,
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.state.discount.ExplicitBootDiscountCurve customTweakManifestMeasure (
		final java.lang.String strManifestMeasure,
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		return null;
	}

	@Override public org.drip.state.nonlinear.FlatForwardDiscountCurve parallelShiftQuantificationMetric (
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.analytics.definition.Curve customTweakQuantificationMetric (
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		return null;
	}

	@Override public org.drip.state.nonlinear.FlatForwardDiscountCurve createBasisRateShiftedCurve (
		final int[] aiDate,
		final double[] adblBasis)
	{
		return null;
	}

	@Override public java.lang.String latentStateQuantificationMetric()
	{
		return org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_ZERO_RATE;
	}

	@Override public org.drip.quant.calculus.WengertJacobian jackDDFDManifestMeasure (
		final int iDate,
		final java.lang.String strManifestMeasure)
	{
		return null;
	}

	@Override public boolean setNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		return true;
	}

	@Override public boolean bumpNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		return true;
	}

	@Override public boolean setFlatValue (
		final double dblValue)
	{
		return true;
	}
}
