
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
 * <i>FlatYieldGovvieCurve</i> manages the Govvie Latent State, using the Flat Yield as the State Response
 * Representation.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/nonlinear">Non-Linear</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FlatYieldGovvieCurve extends org.drip.state.govvie.ExplicitBootGovvieCurve {
	private int[] _aiDate = null;
	private double[] _adblYield = null;

	/**
	 * Construct a Govvie curve from an array of dates and Yields
	 * 
	 * @param iEpochDate Epoch Date
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Currency
	 * @param aiDate Array of Dates
	 * @param adblYield Array of Yields
	 * 
	 * @throws java.lang.Exception Thrown if the curve cannot be created
	 */

	public FlatYieldGovvieCurve (
		final int iEpochDate,
		final java.lang.String strTreasuryCode,
		final java.lang.String strCurrency,
		final int[] aiDate,
		final double[] adblYield)
		throws java.lang.Exception
	{
		super (iEpochDate, strTreasuryCode, strCurrency);

		if (null == (_aiDate = aiDate) || null == (_adblYield = adblYield))
			throw new java.lang.Exception ("FlatYieldGovvieCurve Constructor => Invalid Inputs!");

		int iNumNode = _aiDate.length;

		if (0 == iNumNode || iNumNode != _adblYield.length)
			throw new java.lang.Exception ("FlatYieldGovvieCurve Constructor => Invalid Inputs!");
	}

	@Override public double yield (
		final int iDate)
		throws java.lang.Exception
	{
		if (iDate <= _iEpochDate) return _adblYield[0];

		int iNumDate = _aiDate.length;

		for (int i = 1; i < iNumDate; ++i) {
			if (iDate > _aiDate[i - 1] && iDate <= _aiDate[i]) return _adblYield[i];
		}

		return _adblYield[iNumDate - 1];
	}

	@Override public boolean setNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblValue)) return false;

		int iNumDate = _aiDate.length;

		if (iNodeIndex > iNumDate) return false;

		for (int i = iNodeIndex; i < iNumDate; ++i)
			_adblYield[i] = dblValue;

		return true;
	}

	@Override public boolean bumpNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblValue)) return false;

		int iNumDate = _aiDate.length;

		if (iNodeIndex > iNumDate) return false;

		for (int i = iNodeIndex; i < iNumDate; ++i)
			_adblYield[i] += dblValue;

		return true;
	}

	@Override public boolean setFlatValue (
		final double dblValue)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblValue)) return false;

		int iNumDate = _aiDate.length;

		for (int i = 0; i < iNumDate; ++i)
			_adblYield[i] = dblValue;

		return true;
	}

	@Override public org.drip.numerical.differentiation.WengertJacobian jackDForwardDManifestMeasure (
		final java.lang.String strManifestMeasure,
		final int iDate)
	{
		return null;
	}
}
