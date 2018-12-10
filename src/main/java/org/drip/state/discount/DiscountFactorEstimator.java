
package org.drip.state.discount;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>DiscountFactorEstimator</i> is the interface that exposes the calculation of the Discount Factor for a
 * specific Sovereign/Jurisdiction Span. It exposes the following functionality:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Curve Epoch Date
 *  	</li>
 *  	<li>
 *  		Discount Factor Target/Effective Variants - to Specified Julian Dates and/or Tenors
 *  	</li>
 *  	<li>
 *  		Forward Rate Target/Effective Variants - to Specified Julian Dates and/or Tenors
 *  	</li>
 *  	<li>
 *  		Zero Rate Target/Effective Variants - to Specified Julian Dates and/or Tenors
 *  	</li>
 *  	<li>
 *  		LIBOR Rate and LIBOR01 Target/Effective Variants - to Specified Julian Dates and/or Tenors
 *  	</li>
 *  	<li>
 *  		Curve Implied Arbitrary Measure Estimates
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/discount">Discount</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public interface DiscountFactorEstimator {

	/**
	 * Retrieve the Starting (Epoch) Date
	 * 
	 * @return The Starting Date
	 */

	public abstract org.drip.analytics.date.JulianDate epoch();

	/**
	 * Calculate the Discount Factor to the given Date
	 * 
	 * @param iDate Date
	 * 
	 * @return Discount Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Factor cannot be calculated
	 */

	public abstract double df (
		final int iDate)
		throws java.lang.Exception;

	/**
	 * Calculate the discount factor to the given date
	 * 
	 * @param dt Date
	 * 
	 * @return Discount factor
	 * 
	 * @throws java.lang.Exception Thrown if the discount factor cannot be calculated
	 */

	public abstract double df (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception;

	/**
	 * Calculate the Discount Factor to the given Tenor
	 * 
	 * @param strTenor Tenor
	 * 
	 * @return Discount factor
	 * 
	 * @throws java.lang.Exception Thrown if the Discount Factor cannot be calculated
	 */

	public abstract double df (
		final java.lang.String strTenor)
		throws java.lang.Exception;

	/**
	 * Compute the time-weighted discount factor between 2 dates
	 * 
	 * @param iDate1 First Date
	 * @param iDate2 Second Date
	 * 
	 * @return Discount Factor
	 * 
	 * @throws java.lang.Exception Thrown if the discount factor cannot be calculated
	 */

	public abstract double effectiveDF (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception;

	/**
	 * Compute the time-weighted discount factor between 2 dates
	 * 
	 * @param dt1 First Date
	 * @param dt2 Second Date
	 * 
	 * @return Discount Factor
	 * 
	 * @throws java.lang.Exception Thrown if the discount factor cannot be calculated
	 */

	public abstract double effectiveDF (
		final org.drip.analytics.date.JulianDate dt1,
		final org.drip.analytics.date.JulianDate dt2)
		throws java.lang.Exception;

	/**
	 * Compute the time-weighted discount factor between 2 tenors
	 * 
	 * @param strTenor1 First Date
	 * @param strTenor2 Second Date
	 * 
	 * @return Discount Factor
	 * 
	 * @throws java.lang.Exception Thrown if the discount factor cannot be calculated
	 */

	public abstract double effectiveDF (
		final java.lang.String strTenor1,
		final java.lang.String strTenor2)
		throws java.lang.Exception;
}
