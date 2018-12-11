
package org.drip.xva.vertex;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>BurgardKjaerExposure</i> holds the Credit, the Debt, and the Funding Exposures, as well as the
 * Collateral Balances at each Re-hypothecation Collateral Group using the Burgard Kjaer (2014) Scheme. The
 * References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Li, B., and Y. Tang (2007): <i>Quantitative Analysis, Derivatives Modeling, and Trading
 *  			Strategies in the Presence of Counter-party Credit Risk for the Fixed Income Market</i>
 *  			<b>World Scientific Publishing</b> Singapore
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva">XVA</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/vertex">Vertex</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BurgardKjaerExposure implements org.drip.xva.hypothecation.CollateralGroupVertexExposureComponent
{
	private double _debt = java.lang.Double.NaN;
	private double _credit = java.lang.Double.NaN;
	private double _funding = java.lang.Double.NaN;
	private double _collateralBalance = java.lang.Double.NaN;

	/**
	 * Generate an Initial Instance of Burgard Kjaer Vertex Exposure
	 * 
	 * @param uncollateralizedExposure The Uncollateralized Exposure
	 * @param collateralGroupVertexCloseOut Collateral Group Vertex Close Out
	 * 
	 * @return Initial Instance of Burgard Kjaer Vertex Exposure
	 */

	public static final BurgardKjaerExposure Initial (
		final double uncollateralizedExposure,
		final org.drip.xva.hypothecation.CollateralGroupVertexCloseOut collateralGroupVertexCloseOut)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (uncollateralizedExposure) ||
			null == collateralGroupVertexCloseOut)
		{
			return null;
		}

		try
		{
			return new BurgardKjaerExposure (
				uncollateralizedExposure - collateralGroupVertexCloseOut.client(),
				uncollateralizedExposure - collateralGroupVertexCloseOut.dealer(),
				0.,
				0.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BurgardKjaerExposure Constructor
	 * 
	 * @param credit The Credit Exposure of the Collateral Group
	 * @param debt The Debt Exposure of the Collateral Group
	 * @param funding The Funding Exposure of the Collateral Group
	 * @param collateralBalance The Collateral Balance of the Collateral Group
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BurgardKjaerExposure (
		final double credit,
		final double debt,
		final double funding,
		final double collateralBalance)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_credit = credit) ||
			!org.drip.quant.common.NumberUtil.IsValid (_debt = debt) ||
			!org.drip.quant.common.NumberUtil.IsValid (_funding = funding) ||
			!org.drip.quant.common.NumberUtil.IsValid (_collateralBalance = collateralBalance))
		{
			throw new java.lang.Exception ("BurgardKjaerExposure Constructor => Invalid Inputs");
		}
	}

	@Override public double credit()
	{
		return _credit;
	}

	@Override public double debt()
	{
		return _debt;
	}

	@Override public double funding()
	{
		return _funding;
	}

	@Override public double variationMarginPosting()
	{
		return _collateralBalance;
	}
}
