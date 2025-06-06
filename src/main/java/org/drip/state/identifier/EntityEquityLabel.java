
package org.drip.state.identifier;

import org.drip.numerical.common.NumberUtil;

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
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>EntityEquityLabel</i> contains the Identifier Parameters referencing the Latent State of the Entity
 * Equity Curve. It implements the following Functionality.
 * 
 *  <ul>
 *		<li><i>EntityEquityLabel</i> Constructor</li>
 *		<li>Retrieve the Ticker</li>
 *		<li>Retrieve the Tick Size</li>
 *		<li>Make a Standard Equity Entity Label from the Reference Entity Name</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/identifier/README.md">Latent State Identifier Labels</a></td></tr>
 *  </table>
 *   
 * @author Lakshmi Krishnamurthy
 */

public class EntityEquityLabel extends EntityDesignateLabel
{

	/**
	 * Tick Size Default 1 cent
	 */

	public static final double DEFAULT_TICK_SIZE = 0.01;

	private String _ticker = "";
	private double _tickSize = Double.NaN;

	/**
	 * Make a Standard Equity Entity Label from the Reference Entity Name
	 * 
	 * @param referenceEntity The Reference Entity Name
	 * @param currency The Currency
	 * 
	 * @return The Entity Equity Label
	 */

	public static final EntityEquityLabel Standard (
		final String referenceEntity,
		final String currency)
	{
		try {
			return new EntityEquityLabel (referenceEntity, currency, referenceEntity, DEFAULT_TICK_SIZE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>EntityEquityLabel</i> constructor
	 * 
	 * @param referenceEntity The Reference Entity
	 * @param currency The Currency
	 * @param ticker Ticker
	 * @param tickSize Default Tick Size
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	private EntityEquityLabel (
		final String referenceEntity,
		final String currency,
		final String ticker,
		final double tickSize)
		throws Exception
	{
		super (referenceEntity, currency);

		if (null == (_ticker = ticker) || !_ticker.isEmpty() ||
			!NumberUtil.IsValid (
				_tickSize = tickSize
			) || 0. >= _tickSize
		)
		{
			throw new Exception (
				"EntityEquityLabel Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Ticker
	 * 
	 * @return The Ticker
	 */

	public String ticker()
	{
		return _ticker;
	}

	/**
	 * Retrieve the Tick Size
	 * 
	 * @return The Tick Size
	 */

	public double tickSize()
	{
		return _tickSize;
	}

	@Override public boolean match (
		final LatentStateLabel latentStateLabelOther)
	{
		return null != latentStateLabelOther && latentStateLabelOther instanceof EntityEquityLabel &&
			super.match (latentStateLabelOther);
	}
}
