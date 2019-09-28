
package org.drip.alm.dynamics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>MatchingPortfolio</i> implements theMatching ALM Portfolio and its Evolution. The References are:
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ALMAnalyticsLibrary.md">Asset Liability Management Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/alm/dynamics/README.md">ALM Dynamics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/alm/dynamics/README.md">ALM Portfolio Allocation and Evolution</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MatchingPortfolio extends org.drip.alm.dynamics.EvolvableAsset
{
	private org.drip.alm.dynamics.MaturingAsset _maturingAsset = null;
	private org.drip.alm.dynamics.NonMaturingAsset _nonMaturingAsset = null;

	/**
	 * MatchingPortfolio Constructor
	 * 
	 * @param id Asset ID
	 * @param maturingAsset Maturing Asset
	 * @param nonMaturingAsset Non-maturing Asset
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MatchingPortfolio (
		final java.lang.String id,
		final org.drip.alm.dynamics.MaturingAsset maturingAsset,
		final org.drip.alm.dynamics.NonMaturingAsset nonMaturingAsset)
		throws java.lang.Exception
	{
		super (
			id,
			maturingAsset.amount() + nonMaturingAsset.amount()
		);

		_maturingAsset = maturingAsset;
		_nonMaturingAsset = nonMaturingAsset;
	}

	/**
	 * Retrieve the Maturing Asset
	 * 
	 * @return Maturing Asset
	 */

	public org.drip.alm.dynamics.MaturingAsset maturingAsset()
	{
		return _maturingAsset;
	}

	/**
	 * Retrieve the Non-maturing Asset
	 * 
	 * @return Non-maturing Asset
	 */

	public org.drip.alm.dynamics.NonMaturingAsset nonMaturingAsset()
	{
		return _nonMaturingAsset;
	}

	@Override public double[] realizePath (
		final org.drip.alm.dynamics.SpotMarketParameters spotMarketParameters,
		final int horizonTenorInMonths,
		final int evolutionTenorInMonths)
	{
		double[] maturingAssetPathRealization = _maturingAsset.realizePath (
			spotMarketParameters,
			horizonTenorInMonths,
			evolutionTenorInMonths
		);

		double[] nonMaturingAssetPathRealization = _nonMaturingAsset.realizePath (
			spotMarketParameters,
			horizonTenorInMonths,
			evolutionTenorInMonths
		);

		if (null == maturingAssetPathRealization || null == nonMaturingAssetPathRealization)
		{
			return null;
		}

		int horizonPeriod = horizonTenorInMonths / evolutionTenorInMonths;
		double[] priceTrajectory = new double[horizonPeriod + 1];

		for (int periodIndex = 0; periodIndex <= horizonPeriod; ++periodIndex)
		{
			priceTrajectory[periodIndex] = maturingAssetPathRealization[periodIndex] +
				nonMaturingAssetPathRealization[periodIndex];
		}

		return priceTrajectory;
	}
}
