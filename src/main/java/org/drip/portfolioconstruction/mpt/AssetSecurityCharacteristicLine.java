
package org.drip.portfolioconstruction.mpt;

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
 * <i>AssetSecurityCharacteristicLine</i> holds the Asset Alpha and Beta from which the Asset's Excess
 * Returns over the Risk-Free Rate are estimated.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/mpt">Modern Portfolio Theory</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/AssetAllocation">Asset Allocation Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class AssetSecurityCharacteristicLine {
	private double _dblBeta = java.lang.Double.NaN;
	private double _dblAlpha = java.lang.Double.NaN;

	/**
	 * AssetSecurityCharacteristicLine Constructor
	 * 
	 * @param dblAlpha The Asset's Alpha
	 * @param dblBeta The Asset's Beta
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AssetSecurityCharacteristicLine (
		final double dblAlpha,
		final double dblBeta)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblAlpha = dblAlpha) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblBeta = dblBeta))
			throw new java.lang.Exception ("AssetSecurityCharacteristicLine Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Asset's Alpha
	 * 
	 * @return The Asset's Alpha
	 */

	public double alpha()
	{
		return _dblAlpha;
	}

	/**
	 * Retrieve the Asset's Beta
	 * 
	 * @return The Asset's Beta
	 */

	public double beta()
	{
		return _dblBeta;
	}

	/**
	 * Retrieve the Excess Returns over the Market for the Asset
	 * 
	 * @param dblMarketExcessReturns The Market Premium, i.e., the Excess Market Returns over the Risk Free
	 * 	Rate
	 * 
	 * @return The Excess Returns over the Market for the Asset
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double excessReturns (
		final double dblMarketExcessReturns)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblMarketExcessReturns))
			throw new java.lang.Exception
				("AssetSecurityCharacteristicLine::excessReturns => Invalid Inputs");

		return _dblAlpha + _dblBeta * dblMarketExcessReturns;
	}
}
