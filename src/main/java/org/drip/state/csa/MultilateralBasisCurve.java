
package org.drip.state.csa;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>MultilateralBasisCurve</i> implements the CSA Cash Rate Curve as a Basis over an Overnight Curve.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/csa/README.md">Credit Support Annex Latent State</a></li>
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
