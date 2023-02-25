
package org.drip.pricer.option;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>PutGreeks</i> contains the Sensitivities generated during the Put Option Pricing Run.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer/README.md">Custom Pricing Algorithms and the Derivative Fokker Planck Trajectory Generators</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer/option/README.md">Deterministic/Stochastic Volatility Settings/Greeks</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PutGreeks extends org.drip.pricer.option.Greeks {
	private double _dblPutPriceFromParity = java.lang.Double.NaN;

	/**
	 * The PutGreeks Constructor
	 * 
	 * @param dblDF The Payoff Discount Factor
	 * @param dblEffectiveVolatility Effective Volatility
	 * @param dblExpectedPayoff Expected Forward Payoff
	 * @param dblExpectedATMPayoff Expected ATM Forward Payoff
	 * @param dblPutPrice Put Price
	 * @param dblPutPriceFromParity Put Price Computed from Put-Call Parity
	 * @param dblPutProb1 Put Probability Term #1
	 * @param dblPutProb2 Put Probability Term #2
	 * @param dblPutDelta Put Delta
	 * @param dblPutVega Put Vega
	 * @param dblPutTheta Put Theta
	 * @param dblPutRho Put Rho
	 * @param dblPutGamma Put Gamma
	 * @param dblPutVanna Put Vanna
	 * @param dblPutVomma Put Vomma
	 * @param dblPutCharm Put Charm
	 * @param dblPutVeta Put Veta
	 * @param dblPutColor Put Color
	 * @param dblPutSpeed Put Speed
	 * @param dblPutUltima Put Ultima
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PutGreeks (
		final double dblDF,
		final double dblEffectiveVolatility,
		final double dblExpectedPayoff,
		final double dblExpectedATMPayoff,
		final double dblPutPrice,
		final double dblPutPriceFromParity,
		final double dblPutProb1,
		final double dblPutProb2,
		final double dblPutDelta,
		final double dblPutVega,
		final double dblPutTheta,
		final double dblPutRho,
		final double dblPutGamma,
		final double dblPutVanna,
		final double dblPutVomma,
		final double dblPutCharm,
		final double dblPutVeta,
		final double dblPutColor,
		final double dblPutSpeed,
		final double dblPutUltima)
		throws java.lang.Exception
	{
		super (dblDF, dblEffectiveVolatility, dblExpectedPayoff, dblExpectedATMPayoff, dblPutPrice,
			dblPutProb1, dblPutProb2, dblPutDelta, dblPutVega, dblPutTheta, dblPutRho, dblPutGamma,
				dblPutVanna, dblPutVomma, dblPutCharm, dblPutVeta, dblPutColor, dblPutSpeed, dblPutUltima);

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblPutPrice) &&
			!org.drip.numerical.common.NumberUtil.IsValid (_dblPutPriceFromParity = dblPutPriceFromParity))
			throw new java.lang.Exception ("PutGreeks ctr: Invalid Inputs");

		_dblPutPriceFromParity = dblPutPriceFromParity;
	}

	/**
	 * The Put Option Price Computed from the Put-Call Parity Relation
	 * 
	 * @return The Put Option Price Computed from the Put-Call Parity Relation
	 */

	public double putPriceFromParity()
	{
		return _dblPutPriceFromParity;
	}
}
