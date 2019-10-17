
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
 * <i>BGMForwardTenorSnap</i> contains the Absolute and the Incremental Latent State Quantifier Snapshot
 * traced from the Evolution of the LIBOR Forward Rate as formulated in:
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblLIBOR = dblLIBOR) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblLIBORIncrement = dblLIBORIncrement) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblDiscountFactor = dblDiscountFactor) ||
					!org.drip.numerical.common.NumberUtil.IsValid (_dblDiscountFactorIncrement =
						dblDiscountFactorIncrement) || !org.drip.numerical.common.NumberUtil.IsValid
							(_dblContinuouslyCompoundedForwardIncrement =
								dblContinuouslyCompoundedForwardIncrement) ||
									!org.drip.numerical.common.NumberUtil.IsValid (_dblSpotRateIncrement =
										dblSpotRateIncrement) || !org.drip.numerical.common.NumberUtil.IsValid
											(_dblInstantaneousEffectiveForwardRate =
												dblInstantaneousEffectiveForwardRate) ||
													!org.drip.numerical.common.NumberUtil.IsValid
														(_dblInstantaneousNominalForwardRate =
															dblInstantaneousNominalForwardRate) ||
																!org.drip.numerical.common.NumberUtil.IsValid
			(_dblLognormalLIBORVolatility = dblLognormalLIBORVolatility) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblContinuouslyCompoundedForwardVolatility =
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
		return org.drip.numerical.common.FormatUtil.FormatDouble (_dblLIBOR, 1, 2, 100.) + "% | " +
			org.drip.numerical.common.FormatUtil.FormatDouble (_dblLIBORIncrement, 2, 2, 10000.) + " | " +
				org.drip.numerical.common.FormatUtil.FormatDouble (_dblDiscountFactor, 1, 4, 1.) + " | " +
					org.drip.numerical.common.FormatUtil.FormatDouble (_dblDiscountFactorIncrement, 2, 2, 10000.)
						+ " | " + org.drip.numerical.common.FormatUtil.FormatDouble
							(_dblContinuouslyCompoundedForwardIncrement, 2, 2, 10000.) + " | " +
								org.drip.numerical.common.FormatUtil.FormatDouble (_dblSpotRateIncrement, 2, 2,
									10000.) + " | " + org.drip.numerical.common.FormatUtil.FormatDouble
										(_dblInstantaneousEffectiveForwardRate, 2, 2, 10000.) + " | " +
											org.drip.numerical.common.FormatUtil.FormatDouble
												(_dblInstantaneousNominalForwardRate, 2, 2, 10000.) + " ||";
	}
}
