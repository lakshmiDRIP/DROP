
package org.drip.xva.derivative;

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
 * <i>ReplicationPortfolioVertex</i> contains the Dynamic Replicating Portfolio of the Pay-out using the
 * Assets in the Economy, from the Dealer's View Point. The References are:
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
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva">XVA</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/derivative">Derivative</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/XVA">XVA Analytics Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ReplicationPortfolioVertex
{
	private double _cashAccount = java.lang.Double.NaN;
	private double _positionHoldings = java.lang.Double.NaN;
	private double _clientNumeraireHoldings = java.lang.Double.NaN;
	private double _dealerSeniorNumeraireHoldings = java.lang.Double.NaN;
	private double _dealerSubordinateNumeraireHoldings = java.lang.Double.NaN;

	/**
	 * Construct a ReplicationPortfolioVertex Instance without the Zero Recovery Dealer Numeraire
	 * 
	 * @param positionHoldings The Asset Numeraire Holdings
	 * @param dealerSeniorNumeraireHoldings The Dealer Senior Numeraire Holdings
	 * @param clientNumeraireHoldings The Client Numeraire Replication Holdings
	 * @param cashAccount The Cash Account
	 * 
	 * @return The ReplicationPortfolioVertex Instance without the Zero Recovery Dealer Numeraire
	 */

	public static final ReplicationPortfolioVertex Standard (
		final double positionHoldings,
		final double dealerSeniorNumeraireHoldings,
		final double clientNumeraireHoldings,
		final double cashAccount)
	{
		try
		{
			return new ReplicationPortfolioVertex (
				positionHoldings,
				dealerSeniorNumeraireHoldings,
				0.,
				clientNumeraireHoldings,
				cashAccount
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ReplicationPortfolioVertex Constructor
	 * 
	 * @param positionHoldings The Asset Numeraire Holdings
	 * @param dealerSeniorNumeraireHoldings The Dealer Senior Numeraire Holdings
	 * @param dealerSubordinateNumeraireHoldings The Dealer Subordinate Numeraire Holdings
	 * @param clientNumeraireHoldings The Client Numeraire Holdings
	 * @param cashAccount The Cash Account
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ReplicationPortfolioVertex (
		final double positionHoldings,
		final double dealerSeniorNumeraireHoldings,
		final double dealerSubordinateNumeraireHoldings,
		final double clientNumeraireHoldings,
		final double cashAccount)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dealerSeniorNumeraireHoldings =
				dealerSeniorNumeraireHoldings) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dealerSubordinateNumeraireHoldings =
				dealerSubordinateNumeraireHoldings) ||
			!org.drip.quant.common.NumberUtil.IsValid (_clientNumeraireHoldings = clientNumeraireHoldings) ||
			!org.drip.quant.common.NumberUtil.IsValid (_cashAccount = cashAccount))
		{
			throw new java.lang.Exception ("ReplicationPortfolioVertex Constructor => Invalid Inputs");
		}

		_positionHoldings = positionHoldings;
	}

	/**
	 * Retrieve the Number of Position Holdings
	 * 
	 * @return The Number of Position Holdings
	 */

	public double positionHoldings()
	{
		return _positionHoldings;
	}

	/**
	 * Retrieve the Number of Dealer Senior Numeraire Holdings
	 * 
	 * @return The Number of Dealer Senior Numeraire Holdings
	 */

	public double dealerSeniorNumeraireHoldings()
	{
		return _dealerSeniorNumeraireHoldings;
	}

	/**
	 * Retrieve the Number of Dealer Subordinate Numeraire Holdings
	 * 
	 * @return The Number of Dealer Subordinate Numeraire Holdings
	 */

	public double dealerSubordinateNumeraireHoldings()
	{
		return _dealerSubordinateNumeraireHoldings;
	}

	/**
	 * Retrieve the Client Numeraire Holdings
	 * 
	 * @return The Client Numeraire Holdings
	 */

	public double clientNumeraireHoldings()
	{
		return _clientNumeraireHoldings;
	}

	/**
	 * Retrieve the Cash Account Amount
	 * 
	 * @return The Cash Account Amount
	 */

	public double cashAccount()
	{
		return _cashAccount;
	}

	/**
	 * Compute the Market Value of the Dealer Position Pre-Default
	 * 
	 * @param marketVertex The Market Vertex
	 * 
	 * @return The Market Value of the Dealer Position Pre-Default
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double dealerPreDefaultPositionValue (
		final org.drip.exposure.universe.MarketVertex marketVertex)
		throws java.lang.Exception
	{
		if (null == marketVertex)
		{
			throw new java.lang.Exception
				("ReplicationPortfolioVertex::dealerPreDefaultPositionValue => Invalid Inputs");
		}

		org.drip.exposure.universe.MarketVertexEntity dealerMarketVertex = marketVertex.dealer();

		double value = -1. * dealerMarketVertex.seniorFundingReplicator() * _dealerSeniorNumeraireHoldings;

		double dealerSubordinateFundingMarketVertex = dealerMarketVertex.subordinateFundingReplicator();

		if (org.drip.quant.common.NumberUtil.IsValid (dealerSubordinateFundingMarketVertex))
		{
			value -= dealerSubordinateFundingMarketVertex * _dealerSubordinateNumeraireHoldings;
		}

		return value;
	}

	/**
	 * Compute the Market Value of the Dealer Position Post-Default
	 * 
	 * @param marketVertex The Market Vertex
	 * 
	 * @return The Market Value of the Dealer Position Post-Default
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double dealerPostDefaultPositionValue (
		final org.drip.exposure.universe.MarketVertex marketVertex)
		throws java.lang.Exception
	{
		if (null == marketVertex)
		{
			throw new java.lang.Exception
				("ReplicationPortfolioVertex::dealerPostDefaultPositionValue => Invalid Inputs");
		}

		org.drip.exposure.universe.MarketVertexEntity dealerMarketVertex = marketVertex.dealer();

		double value = dealerMarketVertex.seniorFundingReplicator() * _dealerSeniorNumeraireHoldings *
			dealerMarketVertex.seniorRecoveryRate();

		double dealerSubordinateFundingMarketVertex = dealerMarketVertex.subordinateFundingReplicator();

		if (org.drip.quant.common.NumberUtil.IsValid (dealerSubordinateFundingMarketVertex))
		{
			value -= dealerSubordinateFundingMarketVertex * _dealerSubordinateNumeraireHoldings *
				dealerMarketVertex.subordinateRecoveryRate();
		}

		return value;
	}
}
