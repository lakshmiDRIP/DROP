
package org.drip.xva.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>CloseOutBilateral</i> implements the (2002) ISDA Master Agreement Bilateral Close Out Scheme to be
 * applied to the MTM at the Dealer/Client Default. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b>
 *  			82-87
 *  	</li>
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
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md">Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/definition/README.md">XVA Definition - Close Out, Universe</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CloseOutBilateral extends org.drip.xva.definition.CloseOut
{
	private double _clientRecovery = java.lang.Double.NaN;;
	private double _dealerSeniorFundingRecovery = java.lang.Double.NaN;

	/**
	 * Generate the Close Out Bilateral Instance from the Market Vertex
	 * 
	 * @param marketVertex The Market Vertex Instance
	 * 
	 * @return The Close Out Bilateral Instance from the Market Vertex
	 */

	public static final CloseOutBilateral Market (
		final org.drip.exposure.universe.MarketVertex marketVertex)
	{
		if (null == marketVertex)
		{
			return null;
		}

		try
		{
			return new CloseOutBilateral (
				marketVertex.dealer().seniorRecoveryRate(),
				marketVertex.client().seniorRecoveryRate()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CloseOutBilateral Constructor
	 * 
	 * @param dealerSeniorFundingRecovery The Dealer Senior Funding Recovery Rate
	 * @param clientRecovery Client Recovery Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CloseOutBilateral (
		final double dealerSeniorFundingRecovery,
		final double clientRecovery)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dealerSeniorFundingRecovery =
			dealerSeniorFundingRecovery) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_clientRecovery = clientRecovery))
		{
			throw new java.lang.Exception ("CloseOutBilateral Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Dealer Senior Funding Recovery Rate
	 * 
	 * @return The Dealer Senior Funding Recovery Rate
	 */

	public double dealerSeniorFundingRecovery()
	{
		return _dealerSeniorFundingRecovery;
	}

	/**
	 * Retrieve the Client Recovery Rate
	 * 
	 * @return The Client Recovery Rate
	 */

	public double clientRecovery()
	{
		return _clientRecovery;
	}

	@Override public double dealerDefault (
		final double uncollateralizedExposure,
		final double collateralAmount)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (uncollateralizedExposure) ||
			!org.drip.numerical.common.NumberUtil.IsValid (collateralAmount))
		{
			throw new java.lang.Exception ("CloseOutBilateral::dealerDefault => Invalid Inputs");
		}

		double collateralizedExposure = uncollateralizedExposure - collateralAmount;

		return (collateralizedExposure > 0. ? collateralizedExposure : 0.) + _dealerSeniorFundingRecovery *
			(collateralizedExposure < 0. ? collateralizedExposure : 0.) + collateralAmount;
	}

	@Override public double clientDefault (
		final double uncollateralizedExposure,
		final double collateralAmount)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (uncollateralizedExposure) ||
			!org.drip.numerical.common.NumberUtil.IsValid (collateralAmount))
		{
			throw new java.lang.Exception ("CloseOutBilateral::clientDefault => Invalid Inputs");
		}

		double clientCollateralizedExposure = uncollateralizedExposure - collateralAmount;

		return _clientRecovery * (clientCollateralizedExposure > 0. ? clientCollateralizedExposure : 0.) +
			(clientCollateralizedExposure < 0. ? clientCollateralizedExposure : 0.) + collateralAmount;
	}
}
