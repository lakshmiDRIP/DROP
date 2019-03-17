
package org.drip.portfolioconstruction.cost;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>TransactionChargeMarketImpact</i> contains the Parameters for the Power Law Transaction Charge Scheme.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/cost">Cost</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TransactionChargeMarketImpact extends org.drip.portfolioconstruction.cost.TransactionCharge
{
	private double _dblExponent = java.lang.Double.NaN;
	private double _dblCoefficient = java.lang.Double.NaN;

	/**
	 * Construction of the Two-Third's Power Law TransactionChargeMarketImpact Instance
	 * 
	 * @param strName Transaction Charge Name
	 * @param strID Transaction Charge ID
	 * @param strDescription Description of the Transaction Charge
	 * @param dblCoefficient Transaction Charge Coefficient
	 * 
	 * @return The Two-Third's Power Law TransactionChargeMarketImpact Instance
	 */

	public static final TransactionChargeMarketImpact TwoThirdsPowerLaw (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final double dblCoefficient)
	{
		try {
			return new TransactionChargeMarketImpact (
				strName,
				strID,
				strDescription,
				dblCoefficient,
				2. / 3.
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construction of the Three-Fifth's Power Law TransactionChargeMarketImpact Instance
	 * 
	 * @param strName Transaction Charge Name
	 * @param strID Transaction Charge ID
	 * @param strDescription Description of the Transaction Charge
	 * @param dblCoefficient Transaction Charge Coefficient
	 * 
	 * @return The Three-Fifth's Power Law TransactionChargeMarketImpact Instance
	 */

	public static final TransactionChargeMarketImpact ThreeFifthsPowerLaw (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final double dblCoefficient)
	{
		try {
			return new TransactionChargeMarketImpact (
				strName,
				strID,
				strDescription,
				dblCoefficient,
				3. / 5.
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * TransactionChargeMarketImpact Constructor
	 * 
	 * @param strName Transaction Charge Name
	 * @param strID Transaction Charge ID
	 * @param strDescription Description of the Transaction Charge
	 * @param dblCoefficient Transaction Charge Coefficient
	 * @param dblExponent Transaction Charge Exponent
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	 public TransactionChargeMarketImpact (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final double dblCoefficient,
		final double dblExponent)
		throws java.lang.Exception
	{
		super (
			strName,
			strID,
			strDescription
		);

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblCoefficient = dblCoefficient) || 0. >
			_dblCoefficient || !org.drip.numerical.common.NumberUtil.IsValid (_dblExponent = dblExponent) || 0. >
				_dblExponent)
			throw new java.lang.Exception
				("TransactionChargeMarketImpact Constuctor => Invalid Linear Charge");
	}

	/**
	 * Retrieve the Transaction Charge Coefficient
	 * 
	 * @return The Transaction Charge Coefficient
	 */

	public double coefficient()
	{
		return _dblCoefficient;
	}

	/**
	 * Retrieve the Transaction Charge Exponent
	 * 
	 * @return The Transaction Charge Exponent
	 */

	public double exponent()
	{
		return _dblExponent;
	}

	@Override public double estimate (
		final double dblInitial,
		final double dblFinal)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblInitial) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblFinal))
			throw new java.lang.Exception ("TransactionChargeMarketImpact::estimate => Invalid Inputs");

		return _dblCoefficient * java.lang.Math.pow (java.lang.Math.abs (dblFinal - dblInitial),
			_dblExponent);
	}
}
