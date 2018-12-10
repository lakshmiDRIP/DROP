
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
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>DiscountFactorDiscountCurve</i> manages the Discounting Latent State, using the Discount Factor as the
 * State Response Representation. It exports the following functionality:
 *  <ul>
 *  	<li>
 *  		Compute the discount factor, forward rate, or the zero rate from the Discount Factor Latent State
 *  	</li>
 *  	<li>
 *  		Create a ForwardRateEstimator instance for the given Index
 *  	</li>
 *  	<li>
 *  		Retrieve Array of the Calibration Components
 *  	</li>
 *  	<li>
 *  		Retrieve the Curve Construction Input Set
 *  	</li>
 *  	<li>
 *  		Compute the Jacobian of the Discount Factor Latent State to the input Quote
 *  	</li>
 *  	<li>
 *  		Synthesize scenario Latent State by parallel shifting/custom tweaking the quantification metric
 *  	</li>
 *  	<li>
 *  		Synthesize scenario Latent State by parallel/custom shifting/custom tweaking the manifest measure
 *  	</li>
 *  	<li>
 *  		Serialize into and de-serialize out of byte array
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve">Curve</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DiscountFactorDiscountCurve extends org.drip.state.discount.MergedDiscountForwardCurve {
	private org.drip.spline.grid.Span _span = null;
	private double _dblRightFlatForwardRate = java.lang.Double.NaN;

	private DiscountFactorDiscountCurve shiftManifestMeasure (
		final double[] adblShiftedManifestMeasure)
	{
		return null;
	}

	/**
	 * DiscountFactorDiscountCurve constructor
	 * 
	 * @param strCurrency Currency
	 * @param span The Span Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DiscountFactorDiscountCurve (
		final java.lang.String strCurrency,
		final org.drip.spline.grid.Span span)
		throws java.lang.Exception
	{
		super ((int) span.left(), strCurrency, null);

		_dblRightFlatForwardRate = -365.25 * java.lang.Math.log ((_span = span).calcResponseValue
			(_span.right())) / (_span.right() - _span.left());
	}

	@Override public double df (
		final int iDate)
		throws java.lang.Exception
	{
		int iEpochDate = epoch().julian();

		if (iDate <= iEpochDate) return 1.;

		return (iDate <= _span.right() ? _span.calcResponseValue (iDate) : java.lang.Math.exp (-1. *
			_dblRightFlatForwardRate * (iDate - iEpochDate) / 365.25)) * turnAdjust (iEpochDate, iDate);
	}

	@Override public double forward (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		int iEpochDate = epoch().julian();

		if (iDate1 < iEpochDate || iDate2 < iEpochDate) return 0.;

		return 365.25 / (iDate2 - iDate1) * java.lang.Math.log (df (iDate1) / df (iDate2));
	}

	@Override public double zero (
		final int iDate)
		throws java.lang.Exception
	{
		int iEpochDate = epoch().julian();

		if (iDate < iEpochDate) return 0.;

		return -365.25 / (iDate - iEpochDate) * java.lang.Math.log (df (iDate));
	}

	@Override public org.drip.state.forward.ForwardRateEstimator forwardRateEstimator (
		final int iDate,
		final org.drip.state.identifier.ForwardLabel fri)
	{
		if (null == _span || !_span.isMergeState (iDate, fri)) return null;

		return new org.drip.state.forward.ForwardRateEstimator() {
			@Override public org.drip.state.identifier.ForwardLabel index()
			{
				return fri;
			}

			@Override public java.lang.String tenor()
			{
				return fri.tenor();
			}

			@Override public double forward (
				final org.drip.analytics.date.JulianDate dt)
				throws java.lang.Exception
			{
				if (null == dt)
					throw new java.lang.Exception
						("DiscountFactorDiscountCurve::ForwardEstimator::forward => Invalid Inputs!");

				java.lang.String strTenor = fri.tenor();

				return libor (dt.subtractTenor (strTenor).julian(), strTenor);
			}

			@Override public double forward (
				final int iDate)
				throws java.lang.Exception
			{
				return forward (new org.drip.analytics.date.JulianDate (iDate));
			}

			@Override public double forward (
				final java.lang.String strTenor)
				throws java.lang.Exception
			{
				if (null == strTenor || strTenor.isEmpty())
					throw new java.lang.Exception
						("DiscountFactorDiscountCurve::ForwardEstimator::forward => Invalid Inputs!");

				return forward (epoch().addTenor (strTenor));
			}
		};
	}

	@Override public java.lang.String latentStateQuantificationMetric()
	{
		return org.drip.analytics.definition.LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR;
	}

	@Override public DiscountFactorDiscountCurve parallelShiftManifestMeasure (
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblShift)) return null;

		org.drip.product.definition.CalibratableComponent[] aCC = calibComp();

		if (null == aCC) return null;

		int iNumComp = aCC.length;
		double[] adblShiftedManifestMeasure = new double[iNumComp];

		for (int i = 0; i < iNumComp; ++i)
			adblShiftedManifestMeasure[i] += dblShift;

		return shiftManifestMeasure (adblShiftedManifestMeasure);
	}

	@Override public DiscountFactorDiscountCurve shiftManifestMeasure (
		final int iSpanIndex,
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblShift)) return null;

		org.drip.product.definition.CalibratableComponent[] aCC = calibComp();

		if (null == aCC) return null;

		int iNumComp = aCC.length;
		double[] adblShiftedManifestMeasure = new double[iNumComp];

		if (iSpanIndex >= iNumComp) return null;

		for (int i = 0; i < iNumComp; ++i)
			adblShiftedManifestMeasure[i] += (i == iSpanIndex ? dblShift : 0.);

		return shiftManifestMeasure (adblShiftedManifestMeasure);
	}

	@Override public org.drip.state.discount.MergedDiscountForwardCurve customTweakManifestMeasure (
		final java.lang.String strManifestMeasure,
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		if (null == rvtp) return null;

		org.drip.product.definition.CalibratableComponent[] aCC = calibComp();

		if (null == aCC) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			mapQuote = _ccis.quoteMap();

		int iNumComp = aCC.length;
		double[] adblQuote = new double[iNumComp];

		for (int i = 0; i < iNumComp; ++i)
			adblQuote[i] = mapQuote.get (aCC[i].primaryCode()).get (strManifestMeasure);

		double[] adblShiftedManifestMeasure = org.drip.analytics.support.Helper.TweakManifestMeasure
			(adblQuote, rvtp);

		return shiftManifestMeasure (adblShiftedManifestMeasure);
	}

	@Override public DiscountFactorDiscountCurve parallelShiftQuantificationMetric (
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.analytics.definition.Curve customTweakQuantificationMetric (
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		return null;
	}

	@Override public org.drip.quant.calculus.WengertJacobian jackDDFDManifestMeasure (
		final int iDate,
		final java.lang.String strManifestMeasure)
	{
		return null == _span ? null : _span.jackDResponseDManifestMeasure (strManifestMeasure, iDate, 1);
	}

	@Override public org.drip.product.definition.CalibratableComponent[] calibComp()
	{
		return null == _ccis ? null : _ccis.components();
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> manifestMeasure (
		final java.lang.String strInstrumentCode)
	{
		if (null == _ccis) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			mapQuote = _ccis.quoteMap();

		if (null == mapQuote || !mapQuote.containsKey (strInstrumentCode)) return null;

		return mapQuote.get (strInstrumentCode);
	}
}
