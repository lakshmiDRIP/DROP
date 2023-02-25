
package org.drip.state.govvie;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>GovvieCurve</i> is the Stub for the Govvie Curve for the specified Govvie/Treasury.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/govvie/README.md">Govvie Latent State Curve Estimator</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class GovvieCurve extends org.drip.state.discount.DiscountCurve implements
	org.drip.state.govvie.YieldEstimator {
	private static final int NUM_DF_QUADRATURES = 5;

	private int _iFreq = 2;
	private java.lang.String _strCurrency = "";
	private java.lang.String _strTreasuryCode = "";
	private java.lang.String _strDayCount = "DCAct_Act_UST";

	protected int _iEpochDate = java.lang.Integer.MIN_VALUE;
	protected org.drip.analytics.input.CurveConstructionInputSet _ccis = null;

	protected GovvieCurve (
		final int iEpochDate,
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency)
		throws java.lang.Exception
	{
		if (null == (_strTreasuryCode = strTreasuryCode) || _strTreasuryCode.isEmpty() || null ==
			(_strCurrency = strCurrency) || _strCurrency.isEmpty())
			throw new java.lang.Exception ("GovvieCurve ctr: Invalid Inputs");

		_iEpochDate = iEpochDate;
	}

	@Override public org.drip.analytics.date.JulianDate epoch()
	{
		return new org.drip.analytics.date.JulianDate (_iEpochDate);
	}

	@Override public java.lang.String currency()
	{
		return _strCurrency;
	}

	@Override public org.drip.state.identifier.LatentStateLabel label()
	{
		return org.drip.state.identifier.GovvieLabel.Standard (_strTreasuryCode);
	}

	@Override public double yld (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("GovvieCurve::yield => Invalid Inputs");

		return yld (dt.julian());
	}

	@Override public double yld (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		return yld (epoch().addTenor (strTenor));
	}

	@Override public double forwardYield (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		if (iDate1 >= iDate2) throw new java.lang.Exception ("GovvieCurve::forwardYield => Invalid Inputs");

		org.drip.analytics.daycount.ActActDCParams aadp =
			org.drip.analytics.daycount.ActActDCParams.FromFrequency (_iFreq);

		double dblYearFraction = org.drip.analytics.daycount.Convention.YearFraction (iDate1, iDate2,
			_strDayCount, false, aadp, _strCurrency);

		double dblDF1 = org.drip.analytics.support.Helper.Yield2DF  (_iFreq, yld (iDate1),
			org.drip.analytics.daycount.Convention.YearFraction (_iEpochDate, iDate1, _strDayCount, false,
				aadp, _strCurrency));

		double dblDF2 = org.drip.analytics.support.Helper.Yield2DF  (_iFreq, yld (iDate2),
			org.drip.analytics.daycount.Convention.YearFraction (_iEpochDate, iDate2, _strDayCount, false,
				aadp, _strCurrency));

		return org.drip.analytics.support.Helper.DF2Yield (
			_iFreq,
			dblDF2 / dblDF1,
			dblYearFraction
		);
	}

	@Override public double df (
		final int iDate)
		throws java.lang.Exception
	{
		return org.drip.analytics.support.Helper.Yield2DF  (_iFreq, yld (iDate),
			org.drip.analytics.daycount.Convention.YearFraction (_iEpochDate, iDate, _strDayCount, false,
				org.drip.analytics.daycount.ActActDCParams.FromFrequency (_iFreq), _strCurrency));
	}

	@Override public double df (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("GovvieCurve::df => Invalid Inputs");

		return df (dt.julian());
	}

	@Override public double df (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		return df (new org.drip.analytics.date.JulianDate (_iEpochDate).addTenor (strTenor));
	}

	@Override public double effectiveDF (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		if (iDate1 == iDate2) return df (iDate1);

		int iNumQuadratures = 0;
		double dblEffectiveDF = 0.;
		int iQuadratureWidth = (iDate2 - iDate1) / NUM_DF_QUADRATURES;

		if (0 == iQuadratureWidth) iQuadratureWidth = 1;

		for (int iDate = iDate1; iDate <= iDate2; iDate += iQuadratureWidth) {
			++iNumQuadratures;

			dblEffectiveDF += (df (iDate) + df (iDate + iQuadratureWidth));
		}

		return dblEffectiveDF / (2. * iNumQuadratures);
	}

	@Override public double effectiveDF (
		final org.drip.analytics.date.JulianDate dt1,
		final org.drip.analytics.date.JulianDate dt2)
		throws java.lang.Exception
	{
		if (null == dt1 || null == dt2)
			throw new java.lang.Exception ("GovvieCurve::effectiveDF => Got null for date");

		return effectiveDF (dt1.julian(), dt2.julian());
	}

	@Override public double effectiveDF (
		final java.lang.String strTenor1,
		final java.lang.String strTenor2)
		throws java.lang.Exception
	{
		if (null == strTenor1 || strTenor1.isEmpty() || null == strTenor2 || strTenor2.isEmpty())
			throw new java.lang.Exception ("GovvieCurve::effectiveDF => Got bad tenor");

		org.drip.analytics.date.JulianDate dtStart = epoch();

		return effectiveDF (dtStart.addTenor (strTenor1), dtStart.addTenor (strTenor2));
	}

	@Override public double yieldDF (
		final int iDate,
		final double dblDCF)
		throws java.lang.Exception
	{
		return org.drip.analytics.support.Helper.Yield2DF (_iFreq, yld (iDate), dblDCF);
	}

	@Override public boolean setCCIS (
		final org.drip.analytics.input.CurveConstructionInputSet ccis)
	{
		_ccis = ccis;
		return true;
	}

	@Override public org.drip.product.definition.CalibratableComponent[] calibComp()
	{
		return null;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> manifestMeasure (
		final java.lang.String strInstr)
	{
		return null;
	}

	@Override public org.drip.state.representation.LatentState parallelShiftManifestMeasure (
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.state.representation.LatentState shiftManifestMeasure (
		final int iSpanIndex,
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.state.representation.LatentState customTweakManifestMeasure (
		final java.lang.String strManifestMeasure,
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		return null;
	}

	@Override public org.drip.state.representation.LatentState parallelShiftQuantificationMetric (
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.state.representation.LatentState customTweakQuantificationMetric (
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		return null;
	}

	/**
	 * Retrieve the Yield Frequency
	 * 
	 * @return The Yield Frequency
	 */

	public int freq()
	{
		return _iFreq;
	}

	/**
	 * Retrieve the Yield Day Count
	 * 
	 * @return The Yield Day Count
	 */

	public java.lang.String dayCount()
	{
		return _strDayCount;
	}

	/**
	 * Retrieve the Manifest Measure Jacobian of the Forward Rate to the given date
	 * 
	 * @param strManifestMeasure Manifest Measure
	 * @param iDate Date
	 * 
	 * @return The Manifest Measure Jacobian of the Forward Rate to the given date
	 */

	public abstract org.drip.numerical.differentiation.WengertJacobian jackDForwardDManifestMeasure (
		final java.lang.String strManifestMeasure,
		final int iDate);

	/**
	 * Retrieve the Manifest Measure Jacobian of the Forward Rate to the given date
	 * 
	 * @param strManifestMeasure Manifest Measure
	 * @param dt Date
	 * 
	 * @return The Manifest Measure Jacobian of the Forward Rate to the given date
	 */

	public org.drip.numerical.differentiation.WengertJacobian jackDForwardDManifestMeasure (
		final java.lang.String strManifestMeasure,
		final org.drip.analytics.date.JulianDate dt)
	{
		if (null == dt) return null;

		return jackDForwardDManifestMeasure (strManifestMeasure, dt.julian());
	}

	/**
	 * Retrieve the Manifest Measure Jacobian of the Forward Rate to the date implied by the given Tenor
	 * 
	 * @param strManifestMeasure Manifest Measure
	 * @param strTenor Tenor
	 * 
	 * @return The Manifest Measure Jacobian of the Forward Rate to the date implied by the given Tenor
	 */

	public org.drip.numerical.differentiation.WengertJacobian jackDForwardDManifestMeasure (
		final java.lang.String strManifestMeasure,
		final java.lang.String strTenor)
	{
		if (null == strTenor || strTenor.isEmpty()) return null;

		try {
			return jackDForwardDManifestMeasure (strManifestMeasure, epoch().addTenor (strTenor));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
