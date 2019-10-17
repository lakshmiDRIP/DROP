
package org.drip.dynamics.sabr;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>ImpliedBlackVolatility</i> contains the Output of the Black Volatility Implication Calculations.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">HJM, Hull White, LMM, and SABR Dynamic Evolution Models</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/sabr/README.md">SABR Based Latent State Evolution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ImpliedBlackVolatility {
	private double _dblA = java.lang.Double.NaN;
	private double _dblB = java.lang.Double.NaN;
	private double _dblZ = java.lang.Double.NaN;
	private double _dblTTE = java.lang.Double.NaN;
	private double _dblChiZ = java.lang.Double.NaN;
	private double _dblStrike = java.lang.Double.NaN;
	private double _dblATMForwardRate = java.lang.Double.NaN;
	private double _dblImpliedVolatility = java.lang.Double.NaN;

	/**
	 * ImpliedBlackVolatility Constructor
	 * 
	 * @param dblStrike Strike
	 * @param dblATMForwardRate Forward Rate
	 * @param dblTTE Time To Expiry
	 * @param dblA A
	 * @param dblZ Z
	 * @param dblChiZ Chi (Z)
	 * @param dblB B
	 * @param dblImpliedVolatility The Implied Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public ImpliedBlackVolatility (
		final double dblStrike,
		final double dblATMForwardRate,
		final double dblTTE,
		final double dblA,
		final double dblZ,
		final double dblChiZ,
		final double dblB,
		final double dblImpliedVolatility)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblStrike = dblStrike) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblATMForwardRate = dblATMForwardRate) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblTTE = dblTTE) ||
					!org.drip.numerical.common.NumberUtil.IsValid (_dblA = dblA) ||
						!org.drip.numerical.common.NumberUtil.IsValid (_dblZ = dblZ) ||
							!org.drip.numerical.common.NumberUtil.IsValid (_dblChiZ = dblChiZ) ||
								!org.drip.numerical.common.NumberUtil.IsValid (_dblB = dblB) ||
									!org.drip.numerical.common.NumberUtil.IsValid (_dblImpliedVolatility =
										dblImpliedVolatility))
			throw new java.lang.Exception ("ImpliedBlackVolatility ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Strike
	 * 
	 * @return The Strike
	 */

	public double strike()
	{
		return _dblStrike;
	}

	/**
	 * Retrieve the ATM Forward Rate
	 * 
	 * @return The ATM Forward Rate
	 */

	public double atmForwardRate()
	{
		return _dblATMForwardRate;
	}

	/**
	 * Retrieve TTE
	 * 
	 * @return TTE
	 */

	public double tte()
	{
		return _dblTTE;
	}

	/**
	 * Retrieve A
	 * 
	 * @return A
	 */

	public double a()
	{
		return _dblA;
	}

	/**
	 * Retrieve Z
	 * 
	 * @return Z
	 */

	public double z()
	{
		return _dblZ;
	}

	/**
	 * Retrieve Chi
	 * 
	 * @return Chi
	 */

	public double chi()
	{
		return _dblChiZ;
	}

	/**
	 * Retrieve B
	 * 
	 * @return B
	 */

	public double b()
	{
		return _dblB;
	}

	/**
	 * Retrieve the Implied Volatility
	 * 
	 * @return The Implied Volatility
	 */

	public double impliedVolatility()
	{
		return _dblImpliedVolatility;
	}
}
