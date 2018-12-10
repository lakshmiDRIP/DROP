
package org.drip.state.csa;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>MultilateralBasisCurve</i> implements the CSA Cash Rate Curve as a Basis over an Overnight Curve.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/csa">CSA Numeraire</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultilateralBasisCurve implements org.drip.state.csa.CashFlowEstimator
{
	private static final int NUM_DF_QUADRATURES = 5;

	private double _dblBasis = java.lang.Double.NaN;
	private org.drip.state.discount.MergedDiscountForwardCurve _mdfcOvernight = null;

	/**
	 * Retrieve the Overnight Curve
	 * 
	 * @return The Overnight Curve
	 */

	public org.drip.state.discount.MergedDiscountForwardCurve overnightCurve()
	{
		return _mdfcOvernight;
	}

	/**
	 * Retrieve the Basis to the Overnight Curve
	 * 
	 * @return The Basis to the Overnight Curve
	 */

	public double basis()
	{
		return _dblBasis;
	}

	@Override public org.drip.analytics.date.JulianDate epoch()
	{
		return _mdfcOvernight.epoch();
	}

	@Override public double df (
		final int iDate)
		throws java.lang.Exception
	{
		int iEpochDate = epoch().julian();

		if (iEpochDate >= iDate)
			throw new java.lang.Exception ("MultilateralBasisCurve::df => Invalid Inputs");

		return _mdfcOvernight.df (iDate) * java.lang.Math.exp (_dblBasis * (iEpochDate - iDate) / 365.25);
	}

	@Override public double df (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("MultilateralBasisCurve::df => Invalid Inputs");

		return df (dt.julian());
	}

	@Override public double df (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		return df (epoch().addTenor (strTenor));
	}

	@Override public double effectiveDF (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		if (epoch().julian() > iDate1 || iDate1 >= iDate2)
			throw new java.lang.Exception ("MultilateralFlatForwardCurve::effectiveDF => Invalid Inputs");

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
			throw new java.lang.Exception ("MultilateralFlatForwardCurve::effectiveDF => Invalid Inputs");

		return effectiveDF (
			dt1.julian(),
			dt2.julian()
		);
	}

	@Override public double effectiveDF (
		final java.lang.String strTenor1,
		final java.lang.String strTenor2)
		throws java.lang.Exception
	{
		org.drip.analytics.date.JulianDate dtEpoch = epoch();

		return effectiveDF (
			dtEpoch.addTenor (strTenor1),
			dtEpoch.addTenor (strTenor2)
		);
	}

	@Override public double rate (
		final int iDate)
		throws java.lang.Exception
	{
		int iEpochDate = epoch().julian();

		if (iEpochDate >= iDate)
			throw new java.lang.Exception ("MultilateralFlatForwardCurve::rate => Invalid Inputs");

		return 365.25 * java.lang.Math.log (df (iEpochDate) / df (iDate)) / (iEpochDate - iDate);
	}

	@Override public double rate (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt)
			throw new java.lang.Exception ("MultilateralFlatForwardCurve::rate => Invalid Inputs");

		return rate (dt.julian());
	}

	@Override public double rate (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		return rate (epoch().addTenor (strTenor));
	}

	@Override public double rate (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		int iEpochDate = epoch().julian();

		if (iEpochDate > iDate1 || iDate1 >= iDate2)
			throw new java.lang.Exception ("MultilateralFlatForwardCurve::rate => Invalid Inputs");

		return 365.25 * java.lang.Math.log (df (iDate1) / df (iDate2)) / (iDate2 - iDate1);
	}

	@Override public double rate (
		final org.drip.analytics.date.JulianDate dt1,
		final org.drip.analytics.date.JulianDate dt2)
		throws java.lang.Exception
	{
		if (null == dt1 || null == dt2)
			throw new java.lang.Exception ("MultilateralFlatForwardCurve::rate => Invalid Inputs");

		return rate (
			dt1.julian(),
			dt2.julian()
		);
	}

	@Override public double rate (
		final java.lang.String strTenor1,
		final java.lang.String strTenor2)
		throws java.lang.Exception
	{
		org.drip.analytics.date.JulianDate dtEpoch = epoch();

		return rate (
			dtEpoch.addTenor (strTenor1),
			dtEpoch.addTenor (strTenor2)
		);
	}
}
