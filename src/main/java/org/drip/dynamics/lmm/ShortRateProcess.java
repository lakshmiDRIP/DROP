
package org.drip.dynamics.lmm;

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
 * <i>ShortRateProcess</i> implements the Short Rate Process defined in the LIBOR Market Model. The
 * References are:
 * 	<br>
 *  <ul>
 *  	<li>
 *  		Goldys, B., M. Musiela, and D. Sondermann (1994): <i>Log-normality of Rates and Term Structure
 *  			Models</i> <b>The University of New South Wales</b>
 *  	</li>
 *  	<li>
 *  		Musiela, M. (1994): <i>Nominal Annual Rates and Log-normal Volatility Structure</i> <b>The
 *  			University of New South Wales</b>
 *  	</li>
 *  	<li>
 * 			Brace, A., D. Gatarek, and M. Musiela (1997): The Market Model of Interest Rate Dynamics
 * 				<i>Mathematical Finance</i> <b>7 (2)</b> 127-155
 *  	</li>
 *  </ul>
 * 	<br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics">Dynamics</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm">LIBOR Market Model</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/Fixed Income">Fixed Income Analytics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ShortRateProcess {
	private int _iSpotDate = java.lang.Integer.MIN_VALUE;
	private org.drip.measure.stochastic.R1R1ToR1 _funcR1R1ToR1 = null;

	/**
	 * ShortRateProcess Constructor
	 * 
	 * @param iSpotDate The Spot Date
	 * @param funcR1R1ToR1 The Stochastic Short Rate Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ShortRateProcess (
		final int iSpotDate,
		final org.drip.measure.stochastic.R1R1ToR1 funcR1R1ToR1)
		throws java.lang.Exception
	{
		if (null == (_funcR1R1ToR1 = funcR1R1ToR1))
			throw new java.lang.Exception ("ShortRateProcess ctr: Invalid Inputs");

		_iSpotDate = iSpotDate;
	}

	/**
	 * Retrieve the Spot Date
	 * 
	 * @return The Spot Date
	 */

	public int spotDate()
	{
		return _iSpotDate;
	}

	/**
	 * Retrieve the Stochastic Short Rate Function
	 * 
	 * @return The Stochastic Short Rate Function
	 */

	public org.drip.measure.stochastic.R1R1ToR1 stochasticShortRateFunction()
	{
		return _funcR1R1ToR1;
	}

	/**
	 * Retrieve the Continuously Re-invested Accruing Bank Account
	 * 
	 * @param iMaturityDate The Maturity Date
	 * 
	 * @return The Continuously Re-invested Accruing Bank Account
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double continuouslyReinvestedAccrualFactor (
		final int iMaturityDate)
		throws java.lang.Exception
	{
		if (iMaturityDate <= _iSpotDate)
			throw new java.lang.Exception
				("ShortRateProcess::continuouslyReinvestedAccrualFactor => Invalid Maturity Date");

		return java.lang.Math.exp (_funcR1R1ToR1.integralRealization (0., iMaturityDate - _iSpotDate));
	}
}
