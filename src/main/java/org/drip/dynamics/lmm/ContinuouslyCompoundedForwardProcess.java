
package org.drip.dynamics.lmm;

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
 * <i>ContinuouslyCompoundedForwardProcess</i> implements the Continuously Compounded Forward Rate Process
 * defined in the LIBOR Market Model. The References are:
 *
 *	<br><br>
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
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">HJM, Hull White, LMM, and SABR Dynamic Evolution Models</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm/README.md">LMM Based Latent State Evolution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ContinuouslyCompoundedForwardProcess {
	private int _iSpotDate = java.lang.Integer.MIN_VALUE;
	private org.drip.measure.stochastic.R1R1ToR1 _funcR1R1ToR1 = null;

	/**
	 * ContinuouslyCompoundedForwardProcess Constructor
	 * 
	 * @param iSpotDate The Spot Date
	 * @param funcR1R1ToR1 The Stochastic Forward Rate Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ContinuouslyCompoundedForwardProcess (
		final int iSpotDate,
		final org.drip.measure.stochastic.R1R1ToR1 funcR1R1ToR1)
		throws java.lang.Exception
	{
		if (null == (_funcR1R1ToR1 = funcR1R1ToR1))
			throw new java.lang.Exception ("ContinuouslyCompoundedForwardProcess ctr: Invalid Inputs");

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
	 * Retrieve the Stochastic Forward Rate Function
	 * 
	 * @return The Stochastic Forward Rate Function
	 */

	public org.drip.measure.stochastic.R1R1ToR1 stochasticForwardRateFunction()
	{
		return _funcR1R1ToR1;
	}

	/**
	 * Retrieve a Realized Zero-Coupon Bond Price
	 * 
	 * @param iMaturityDate The Maturity Date
	 * 
	 * @return The Realized Zero-Coupon Bond Price
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double realizedZeroCouponPrice (
		final int iMaturityDate)
		throws java.lang.Exception
	{
		if (iMaturityDate <= _iSpotDate)
			throw new java.lang.Exception
				("ContinuouslyCompoundedForwardProcess::realizedZeroCouponPrice => Invalid Maturity Date");

		return java.lang.Math.exp (-1. * _funcR1R1ToR1.integralRealization (0., iMaturityDate - _iSpotDate));
	}

	/**
	 * Compute the Realized/Expected Instantaneous Forward Rate Integral to the Target Date
	 * 
	 * @param iTargetDate The Target Date
	 * @param bRealized TRUE - Compute the Realized (TRUE) / Expected (FALSE) Instantaneous Forward Rate
	 *  Integral
	 * 
	 * @return The Realized/Expected Instantaneous Forward Rate Integral
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double instantaneousForwardRateIntegral (
		final int iTargetDate,
		final boolean bRealized)
		throws java.lang.Exception
	{
		if (iTargetDate <= _iSpotDate)
			throw new java.lang.Exception
				("ContinuouslyCompoundedForwardProcess::instantaneousForwardRateIntegral => Invalid Target Date");

		return bRealized ? java.lang.Math.exp (-1. * _funcR1R1ToR1.integralRealization (0., iTargetDate -
			_iSpotDate)) : java.lang.Math.exp (-1. * _funcR1R1ToR1.integralExpectation (0., iTargetDate -
				_iSpotDate));
	}

	/**
	 * Retrieve a Realized/Expected Value of the Discount to the Target Date
	 * 
	 * @param iTargetDate The Target Date
	 * @param bRealized TRUE - Compute the Realized (TRUE) / Expected (FALSE) Instantaneous Forward Rate
	 *  Integral
	 * 
	 * @return The Realized/Expected Value of the Discount to the Target Date
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double discountFunctionValue (
		final int iTargetDate,
		final boolean bRealized)
		throws java.lang.Exception
	{
		if (iTargetDate <= _iSpotDate)
			throw new java.lang.Exception
				("ContinuouslyCompoundedForwardProcess::discountFunctionValue => Invalid Target Date");

		return bRealized ? java.lang.Math.exp (-1. * _funcR1R1ToR1.integralRealization (0., iTargetDate -
			_iSpotDate)) : java.lang.Math.exp (-1. * _funcR1R1ToR1.integralExpectation (0., iTargetDate -
				_iSpotDate));
	}

	/**
	 * Retrieve a Realized/Expected Value of the LIBOR Rate at the Target Date
	 * 
	 * @param iTargetDate The Target Date
	 * @param strTenor The LIBOR Tenor
	 * @param bRealized TRUE - Compute the Realized (TRUE) / Expected (FALSE) LIBOR Rate
	 * 
	 * @return The Realized/Expected Value of the LIBOR Rate at the Target Date
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double liborRate (
		final int iTargetDate,
		final java.lang.String strTenor,
		final boolean bRealized)
		throws java.lang.Exception
	{
		if (iTargetDate <= _iSpotDate)
			throw new java.lang.Exception
				("ContinuouslyCompoundedForwardProcess::liborRate => Invalid Inputs");

		return (discountFunctionValue (new org.drip.analytics.date.JulianDate (iTargetDate).addTenor
			(strTenor).julian(), bRealized) / discountFunctionValue (iTargetDate, bRealized) - 1.) /
				org.drip.analytics.support.Helper.TenorToYearFraction (strTenor);
	}
}
