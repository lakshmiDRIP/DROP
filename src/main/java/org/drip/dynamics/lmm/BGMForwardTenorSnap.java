
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
 * <i>BGMForwardTenorSnap</i> contains the Absolute and the Incremental Latent State Quantifier Snapshot
 *  traced from the Evolution of the LIBOR Forward Rate as formulated in:
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

public class BGMForwardTenorSnap {
	private double _dblLIBOR = java.lang.Double.NaN;
	private int _iDate = java.lang.Integer.MIN_VALUE;
	private double _dblDiscountFactor = java.lang.Double.NaN;
	private double _dblLIBORIncrement = java.lang.Double.NaN;
	private double _dblSpotRateIncrement = java.lang.Double.NaN;
	private double _dblDiscountFactorIncrement = java.lang.Double.NaN;
	private double _dblLognormalLIBORVolatility = java.lang.Double.NaN;
	private double _dblInstantaneousNominalForwardRate = java.lang.Double.NaN;
	private double _dblInstantaneousEffectiveForwardRate = java.lang.Double.NaN;
	private double _dblContinuouslyCompoundedForwardIncrement = java.lang.Double.NaN;
	private double _dblContinuouslyCompoundedForwardVolatility = java.lang.Double.NaN;

	/**
	 * BGMForwardTenorSnap Constructor
	 * 
	 * @param iDate The Date corresponding to the Tenor
	 * @param dblLIBOR The LIBOR Rate
	 * @param dblLIBORIncrement The LIBOR Rate Increment
	 * @param dblDiscountFactor The Discount Factor
	 * @param dblDiscountFactorIncrement The Discount Factor Increment
	 * @param dblContinuouslyCompoundedForwardIncrement Continuously Compounded Forward Rate Increment
	 * @param dblSpotRateIncrement Spot Rate Increment
	 * @param dblInstantaneousEffectiveForwardRate Instantaneous Effective Annual Forward Rate
	 * @param dblInstantaneousNominalForwardRate Instantaneous Nominal Annual Forward Rate
	 * @param dblLognormalLIBORVolatility The Log-normal LIBOR Rate Volatility
	 * @param dblContinuouslyCompoundedForwardVolatility The Continuously Compounded Forward Rate Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BGMForwardTenorSnap (
		final int iDate,
		final double dblLIBOR,
		final double dblLIBORIncrement,
		final double dblDiscountFactor,
		final double dblDiscountFactorIncrement,
		final double dblContinuouslyCompoundedForwardIncrement,
		final double dblSpotRateIncrement,
		final double dblInstantaneousEffectiveForwardRate,
		final double dblInstantaneousNominalForwardRate,
		final double dblLognormalLIBORVolatility,
		final double dblContinuouslyCompoundedForwardVolatility)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblLIBOR = dblLIBOR) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblLIBORIncrement = dblLIBORIncrement) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblDiscountFactor = dblDiscountFactor) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblDiscountFactorIncrement =
						dblDiscountFactorIncrement) || !org.drip.quant.common.NumberUtil.IsValid
							(_dblContinuouslyCompoundedForwardIncrement =
								dblContinuouslyCompoundedForwardIncrement) ||
									!org.drip.quant.common.NumberUtil.IsValid (_dblSpotRateIncrement =
										dblSpotRateIncrement) || !org.drip.quant.common.NumberUtil.IsValid
											(_dblInstantaneousEffectiveForwardRate =
												dblInstantaneousEffectiveForwardRate) ||
													!org.drip.quant.common.NumberUtil.IsValid
														(_dblInstantaneousNominalForwardRate =
															dblInstantaneousNominalForwardRate) ||
																!org.drip.quant.common.NumberUtil.IsValid
			(_dblLognormalLIBORVolatility = dblLognormalLIBORVolatility) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblContinuouslyCompoundedForwardVolatility =
					dblContinuouslyCompoundedForwardVolatility))
			throw new java.lang.Exception ("BGMForwardTenorSnap ctr: Invalid Inputs");

		_iDate = iDate;
	}

	/**
	 * Retrieve the Tenor Date
	 * 
	 * @return The Tenor Date
	 */

	public int date()
	{
		return _iDate;
	}

	/**
	 * Retrieve the LIBOR Rate
	 * 
	 * @return The LIBOR Rate
	 */

	public double libor()
	{
		return _dblLIBOR;
	}

	/**
	 * Retrieve the LIBOR Rate Increment
	 * 
	 * @return The LIBOR Rate Increment
	 */

	public double liborIncrement()
	{
		return _dblLIBORIncrement;
	}

	/**
	 * Retrieve the Discount Factor
	 * 
	 * @return The Discount Factor
	 */

	public double discountFactor()
	{
		return _dblDiscountFactor;
	}

	/**
	 * Retrieve the Discount Factor Increment
	 * 
	 * @return The Discount Factor Increment
	 */

	public double discountFactorIncrement()
	{
		return _dblDiscountFactorIncrement;
	}

	/**
	 * Retrieve the Continuously Compounded Forward Rate Increment
	 * 
	 * @return The Continuously Compounded Forward Rate Increment
	 */

	public double continuouslyCompoundedForwardIncrement()
	{
		return _dblContinuouslyCompoundedForwardIncrement;
	}

	/**
	 * Retrieve the Spot Rate Increment
	 * 
	 * @return The Spot Rate Increment
	 */

	public double spotRateIncrement()
	{
		return _dblSpotRateIncrement;
	}

	/**
	 * Retrieve the Instantaneous Effective Annual Forward Rate
	 * 
	 * @return The Instantaneous Effective Annual Forward Rate
	 */

	public double instantaneousEffectiveForwardRate()
	{
		return _dblInstantaneousEffectiveForwardRate;
	}

	/**
	 * Retrieve the Instantaneous Nominal Annual Forward Rate
	 * 
	 * @return The Instantaneous Nominal Annual Forward Rate
	 */

	public double instantaneousNominalForwardRate()
	{
		return _dblInstantaneousNominalForwardRate;
	}

	/**
	 * Retrieve the Log-normal LIBOR Volatility
	 * 
	 * @return The Log-normal LIBOR Volatility
	 */

	public double lognormalLIBORVolatility()
	{
		return _dblLognormalLIBORVolatility;
	}

	/**
	 * Retrieve the Continuously Compounded Forward Rate Volatility
	 * 
	 * @return The Continuously Compounded Forward Rate Volatility
	 */

	public double continuouslyCompoundedForwardVolatility()
	{
		return _dblContinuouslyCompoundedForwardVolatility;
	}

	@Override public java.lang.String toString()
	{
		return org.drip.quant.common.FormatUtil.FormatDouble (_dblLIBOR, 1, 2, 100.) + "% | " +
			org.drip.quant.common.FormatUtil.FormatDouble (_dblLIBORIncrement, 2, 2, 10000.) + " | " +
				org.drip.quant.common.FormatUtil.FormatDouble (_dblDiscountFactor, 1, 4, 1.) + " | " +
					org.drip.quant.common.FormatUtil.FormatDouble (_dblDiscountFactorIncrement, 2, 2, 10000.)
						+ " | " + org.drip.quant.common.FormatUtil.FormatDouble
							(_dblContinuouslyCompoundedForwardIncrement, 2, 2, 10000.) + " | " +
								org.drip.quant.common.FormatUtil.FormatDouble (_dblSpotRateIncrement, 2, 2,
									10000.) + " | " + org.drip.quant.common.FormatUtil.FormatDouble
										(_dblInstantaneousEffectiveForwardRate, 2, 2, 10000.) + " | " +
											org.drip.quant.common.FormatUtil.FormatDouble
												(_dblInstantaneousNominalForwardRate, 2, 2, 10000.) + " ||";
	}
}
