
package org.drip.pricer.option;

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
 * <i>PutGreeks</i> contains the Sensitivities generated during the Put Option Pricing Run.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer">Pricer</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pricer/option">Option</a></li>
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
