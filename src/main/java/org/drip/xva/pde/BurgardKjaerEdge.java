
package org.drip.xva.pde;

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
 * <i>BurgardKjaerEdge</i> holds the Underlier Stochastic and the Credit Risk Free Components of the XVA
 * Derivative Value Growth, as laid out in Burgard and Kjaer (2014). The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): <i>Modeling,
 *  			Pricing, and Hedging Counter-party Credit Exposure - A Technical Guide</i> <b>Springer
 *  			Finance</b> New York
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/pde">PDE</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public abstract class BurgardKjaerEdge
{
	private double _positionValueBump = java.lang.Double.NaN;
	private double _derivativeXVACollateralGrowth = java.lang.Double.NaN;
	private double _derivativeXVAStochasticGrowth = java.lang.Double.NaN;
	private double _derivativeXVAStochasticGrowthUp = java.lang.Double.NaN;
	private double _derivativeXVAStochasticGrowthDown = java.lang.Double.NaN;

	protected BurgardKjaerEdge (
		final double positionValueBump,
		final double derivativeXVAStochasticGrowthDown,
		final double derivativeXVAStochasticGrowth,
		final double derivativeXVAStochasticGrowthUp,
		final double derivativeXVACollateralGrowth)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_positionValueBump = positionValueBump) ||
			!org.drip.quant.common.NumberUtil.IsValid (_derivativeXVAStochasticGrowthDown =
				derivativeXVAStochasticGrowthDown) ||
			!org.drip.quant.common.NumberUtil.IsValid (_derivativeXVAStochasticGrowth =
				derivativeXVAStochasticGrowth) ||
			!org.drip.quant.common.NumberUtil.IsValid (_derivativeXVAStochasticGrowthUp =
				derivativeXVAStochasticGrowthUp) ||
			!org.drip.quant.common.NumberUtil.IsValid (_derivativeXVACollateralGrowth =
				derivativeXVACollateralGrowth))
		{
			throw new java.lang.Exception ("BurgardKjaerEdge Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Position Value Bump
	 * 
	 * @return The Position Value Bump
	 */

	public double positionValueBump()
	{
		return _positionValueBump;
	}

	/**
	 * Retrieve the Stochastic Down Component of the Derivative XVA Value
	 * 
	 * @return The Stochastic Down Component of the Derivative XVA Value
	 */

	public double derivativeXVAStochasticGrowthDown()
	{
		return _derivativeXVAStochasticGrowthDown;
	}

	/**
	 * Retrieve the Stochastic Component of the Derivative XVA Value Growth
	 * 
	 * @return The Stochastic Component of the Derivative XVA Value Growth
	 */

	public double derivativeXVAStochasticGrowth()
	{
		return _derivativeXVAStochasticGrowth;
	}

	/**
	 * Retrieve the Stochastic Up Component of the Derivative XVA Value
	 * 
	 * @return The Stochastic Up Component of the Derivative XVA Value
	 */

	public double derivativeXVAStochasticGrowthUp()
	{
		return _derivativeXVAStochasticGrowthUp;
	}

	/**
	 * Retrieve the Collateral Component of the Derivative XVA Value Growth
	 * 
	 * @return The Collateral Component of the Derivative XVA Value Growth
	 */

	public double derivativeXVACollateralGrowth()
	{
		return _derivativeXVACollateralGrowth;
	}

	/**
	 * Compute the Gross Theta from Position Value Down
	 * 
	 * @return The Gross Theta from Position Value Down
	 */

	public abstract double thetaPositionValueDown();

	/**
	 * Compute the Gross Theta from Position Value Base
	 * 
	 * @return The Gross Theta from Position Value Base
	 */

	public abstract double theta();

	/**
	 * Compute the Gross Theta from Position Value Up
	 * 
	 * @return The Gross Theta from Position Value Up
	 */

	public abstract double thetaPositionValueUp();
}
