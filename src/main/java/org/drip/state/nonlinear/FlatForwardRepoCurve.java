
package org.drip.state.nonlinear;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>FlatForwardRepoCurve</i> manages the Repo Latent State, using the Forward Repo Rate as the State
 * Response Representation.
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

public class FlatForwardRepoCurve extends org.drip.state.repo.ExplicitBootRepoCurve {
	private int[] _aiPillarDate = null;
	private double[] _adblRepoForward = null;

	/**
	 * FlatForwardRepoCurve Constructor
	 * 
	 * @param iEpochDate Epoch Date
	 * @param comp The Repo Component
	 * @param aiPillarDate Array of Pillar Dates
	 * @param adblRepoForward Array of Repo Forward Rates
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FlatForwardRepoCurve (
		final int iEpochDate,
		final org.drip.product.definition.Component comp,
		final int[] aiPillarDate,
		final double[] adblRepoForward)
		throws java.lang.Exception
	{
		super (iEpochDate, comp);

		if (null == (_aiPillarDate = aiPillarDate) || null == (_adblRepoForward = adblRepoForward) ||
			_aiPillarDate.length != _adblRepoForward.length)
			throw new java.lang.Exception ("FlatForwardRepoCurve ctr => Invalid Inputs");

		int iNumPillar = _aiPillarDate.length;

		for (int i = 0; i < iNumPillar; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (_aiPillarDate[i]) ||
				!org.drip.quant.common.NumberUtil.IsValid (_adblRepoForward[i]))
				throw new java.lang.Exception ("FlatForwardRepoCurve ctr => Invalid Inputs");
		}
	}

	@Override public double repo (
		final int iDate)
		throws java.lang.Exception
	{
		if (iDate >= component().maturityDate().julian())
			throw new java.lang.Exception ("FlatForwardRepoCurve::repo => Invalid Input");

		if (iDate <= epoch().julian()) return _adblRepoForward[0];

		int iNumPillar = _adblRepoForward.length;

		for (int i = 1; i < iNumPillar; ++i) {
			if (_aiPillarDate[i - 1] <= iDate && _aiPillarDate[i] > iDate)
				return _adblRepoForward[i];
		}

		return _adblRepoForward[iNumPillar - 1];
	}

	@Override public boolean setNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblValue) || iNodeIndex > _adblRepoForward.length)
			return false;

		for (int i = iNodeIndex; i < _adblRepoForward.length; ++i)
			_adblRepoForward[i] = dblValue;

		return true;
	}

	@Override public boolean bumpNodeValue (
		final int iNodeIndex,
		final double dblValue)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblValue) || iNodeIndex > _adblRepoForward.length)
			return false;

		for (int i = iNodeIndex; i < _adblRepoForward.length; ++i)
			_adblRepoForward[i] += dblValue;

		return true;
	}

	@Override public boolean setFlatValue (
		final double dblValue)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblValue)) return false;

		for (int i = 0; i < _adblRepoForward.length; ++i)
			_adblRepoForward[i] = dblValue;

		return true;
	}
}
