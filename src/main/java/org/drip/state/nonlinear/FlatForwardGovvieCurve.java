
package org.drip.state.nonlinear;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>FlatForwardGovvieCurve</i> manages the Govvie Latent State, using the Flat Forward Rate as the State
 * Response Representation.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear">Non Linear</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FlatForwardGovvieCurve extends org.drip.state.govvie.ExplicitBootGovvieCurve {
	private int[] _aiDate = null;
	private double[] _adblForwardYield = null;

	private double yearFract (
		final int iStartDate,
		final int iEndDate,
		final org.drip.analytics.daycount.ActActDCParams aap,
		final java.lang.String strDayCount)
		throws java.lang.Exception
	{
		return org.drip.analytics.daycount.Convention.YearFraction (iStartDate, iEndDate, strDayCount, false,
			aap, currency());
	}

	/**
	 * Construct a Govvie Curve from an Array of Dates and Flat Forward Yields
	 * 
	 * @param iEpochDate Epoch Date
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Currency
	 * @param aiDate Array of Dates
	 * @param adblForwardYield Array of Forward Yields
	 * 
	 * @throws java.lang.Exception Thrown if the curve cannot be created
	 */

	public FlatForwardGovvieCurve (
		final int iEpochDate,
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency,
		final int[] aiDate,
		final double[] adblForwardYield)
		throws java.lang.Exception
	{
		super (iEpochDate, strTreasuryCode, strCurrency);

		if (null == (_aiDate = aiDate) || null == (_adblForwardYield = adblForwardYield))
			throw new java.lang.Exception ("FlatForwardGovvieCurve Constructor => Invalid Inputs!");

		int iNumNode = _aiDate.length;

		if (0 == iNumNode || iNumNode != _adblForwardYield.length)
			throw new java.lang.Exception ("FlatForwardGovvieCurve Constructor => Invalid Inputs!");
	}

	@Override public double yield (
		final int iDate)
		throws java.lang.Exception
	{
		if (iDate <= _iEpochDate) return 1.;

		int i = 0;
		double dblDF = 1.;
		int iStartDate = _iEpochDate;
		int iNumDate = _aiDate.length;

		int iFreq = freq();

		java.lang.String strDayCount = dayCount();

		org.drip.analytics.daycount.ActActDCParams aap =
			org.drip.analytics.daycount.ActActDCParams.FromFrequency (iFreq);

		while (i < iNumDate && (int) iDate >= (int) _aiDate[i]) {
			dblDF *= java.lang.Math.pow (1. + (_adblForwardYield[i] / iFreq), -1. * yearFract (iStartDate,
				_aiDate[i], aap, strDayCount) * iFreq);

			iStartDate = _aiDate[i++];
		}

		if (i >= iNumDate) i = iNumDate - 1;

		return org.drip.analytics.support.Helper.DF2Yield (iFreq, dblDF * java.lang.Math.pow (1. +
			(_adblForwardYield[i] / iFreq), -1. * yearFract (iStartDate, iDate, aap, strDayCount) * iFreq),
				yearFract (_iEpochDate, iDate, aap, strDayCount));
	}

	@Override public boolean setNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblValue)) return false;

		int iNumDate = _aiDate.length;

		if (iNodeIndex > iNumDate) return false;

		for (int i = iNodeIndex; i < iNumDate; ++i)
			_adblForwardYield[i] = dblValue;

		return true;
	}

	@Override public boolean bumpNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblValue)) return false;

		int iNumDate = _aiDate.length;

		if (iNodeIndex > iNumDate) return false;

		for (int i = iNodeIndex; i < iNumDate; ++i)
			_adblForwardYield[i] += dblValue;

		return true;
	}

	@Override public boolean setFlatValue (
		final double dblValue)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblValue)) return false;

		int iNumDate = _aiDate.length;

		for (int i = 0; i < iNumDate; ++i)
			_adblForwardYield[i] = dblValue;

		return true;
	}

	@Override public org.drip.quant.calculus.WengertJacobian jackDForwardDManifestMeasure (
		final java.lang.String strManifestMeasure,
		final int iDate)
	{
		return null;
	}
}
