
package org.drip.xva.vertex;

import org.drip.analytics.date.JulianDate;
import org.drip.xva.derivative.ReplicationPortfolioVertexDealer;
import org.drip.xva.hypothecation.CollateralGroupVertex;
import org.drip.xva.hypothecation.CollateralGroupVertexCloseOut;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 * <i>BurgardKjaer</i> holds the Close Out Based Vertex Exposures of a Projected Path of a Simulation Run of
 * a Collateral Hypothecation Group using the Generalized Burgard Kjaer (2013) Scheme. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md">Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/vertex/README.md">XVA Hypothecation Group Vertex Generators</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BurgardKjaer extends CollateralGroupVertex
{
	private BurgardKjaerExposure _burgardKjaerVertexExposure = null;
	private CollateralGroupVertexCloseOut _collateralGroupCloseOut = null;
	private ReplicationPortfolioVertexDealer _dealerReplicationPortfolioVertex = null;

	/**
	 * BurgardKjaer Constructor
	 * 
	 * @param anchorDate The Vertex Date Anchor
	 * @param forward The Unrealized Forward Exposure
	 * @param accrued The Accrued Exposure
	 * @param burgardKjaerVertexExposure The Collateral Group Vertex
	 * @param collateralGroupCloseOut The Collateral Group Vertex Close Out Instance
	 * @param dealerReplicationPortfolioVertex The Dealer Replication Portfolio Vertex Instance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BurgardKjaer (
		final JulianDate anchorDate,
		final double forward,
		final double accrued,
		final BurgardKjaerExposure burgardKjaerVertexExposure,
		final CollateralGroupVertexCloseOut collateralGroupCloseOut,
		final ReplicationPortfolioVertexDealer dealerReplicationPortfolioVertex)
		throws Exception
	{
		super (anchorDate, forward, accrued, burgardKjaerVertexExposure.variationMarginPosting());

		if (null == (_burgardKjaerVertexExposure = burgardKjaerVertexExposure) ||
			null == (_collateralGroupCloseOut = collateralGroupCloseOut) ||
			null == (_dealerReplicationPortfolioVertex = dealerReplicationPortfolioVertex)) {
			throw new Exception ("BurgardKjaer Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Close Out on Dealer Default
	 * 
	 * @return Close Out on Dealer Default
	 */

	public double dealerDefaultCloseOut()
	{
		return _collateralGroupCloseOut.dealer();
	}

	/**
	 * Retrieve the Close Out on Client Default
	 * 
	 * @return Close Out on Client Default
	 */

	public double clientDefaultCloseOut()
	{
		return _collateralGroupCloseOut.client();
	}

	@Override public double credit()
	{
		return _burgardKjaerVertexExposure.credit();
	}

	@Override public double debt()
	{
		return _burgardKjaerVertexExposure.debt();
	}

	@Override public double funding()
	{
		return _burgardKjaerVertexExposure.funding();
	}

	/**
	 * Retrieve the Hedge Error
	 * 
	 * @return The Hedge Error
	 */

	public double hedgeError()
	{
		return _burgardKjaerVertexExposure.funding();
	}

	/**
	 * Retrieve the Dealer Replication Potrfolio Instance
	 * 
	 * @return The Dealer Replication Potrfolio Instance
	 */

	public ReplicationPortfolioVertexDealer dealerReplicationPortfolio()
	{
		return _dealerReplicationPortfolioVertex;
	}
}
