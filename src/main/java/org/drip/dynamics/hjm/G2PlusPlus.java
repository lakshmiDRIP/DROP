
package org.drip.dynamics.hjm;

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
 * <i>G2PlusPlus</i> provides the Hull-White-type, but 2F Gaussian HJM Short Rate Dynamics Implementation.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">HJM, Hull White, LMM, and SABR Dynamic Evolution Models</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/hjm/README.md">HJM Based Latent State Evolution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class G2PlusPlus {
	private double _dblA = java.lang.Double.NaN;
	private double _dblB = java.lang.Double.NaN;
	private double _dblEta = java.lang.Double.NaN;
	private double _dblRho = java.lang.Double.NaN;
	private double _dblSigma = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _auIFRInitial = null;
	private org.drip.sequence.random.UnivariateSequenceGenerator[] _aRSG = null;

	/**
	 * G2PlusPlus Constructor
	 * 
	 * @param dblSigma Sigma
	 * @param dblA A
	 * @param dblEta Eta
	 * @param dblB B
	 * @param aRSG Array of the Random Sequence Generators
	 * @param dblRho Rho
	 * @param auIFRInitial The Initial Instantaneous Forward Rate Term Structure
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public G2PlusPlus (
		final double dblSigma,
		final double dblA,
		final double dblEta,
		final double dblB,
		final org.drip.sequence.random.UnivariateSequenceGenerator[] aRSG,
		final double dblRho,
		final org.drip.function.definition.R1ToR1 auIFRInitial)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblSigma = dblSigma) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblA = dblA) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_dblEta = dblEta) ||
					!org.drip.numerical.common.NumberUtil.IsValid (_dblB = dblB) || null == (_aRSG = aRSG) || 2
						!= _aRSG.length || !org.drip.numerical.common.NumberUtil.IsValid (_dblRho = dblRho) ||
							null == (_auIFRInitial = auIFRInitial))
			throw new java.lang.Exception ("G2PlusPlus ctr: Invalid Inputs");
	}

	/**
	 * Retrieve Sigma
	 * 
	 * @return Sigma
	 */

	public double sigma()
	{
		return _dblSigma;
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
	 * Retrieve Eta
	 * 
	 * @return Eta
	 */

	public double eta()
	{
		return _dblEta;
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
	 * Retrieve the Initial Instantaneous Forward Rate Term Structure
	 * 
	 * @return The Initial Instantaneous Forward Rate Term Structure
	 */

	public org.drip.function.definition.R1ToR1 ifrInitialTermStructure()
	{
		return _auIFRInitial;
	}

	/**
	 * Retrieve the Random Sequence Generator Array
	 * 
	 * @return The Random Sequence Generator Array
	 */

	public org.drip.sequence.random.UnivariateSequenceGenerator[] rsg()
	{
		return _aRSG;
	}

	/**
	 * Retrieve Rho
	 * 
	 * @return Rho
	 */

	public double rho()
	{
		return _dblRho;
	}

	/**
	 * Compute the G2++ Phi
	 * 
	 * @param iSpotDate The Spot Date
	 * @param iViewDate The View Date
	 * 
	 * @return The G2++ Phi
	 * 
	 * @throws java.lang.Exception Thrown if the G2++ Phi cannot be computed
	 */

	public double phi (
		final int iSpotDate,
		final int iViewDate)
		throws java.lang.Exception
	{
		if (iSpotDate > iViewDate) throw new java.lang.Exception ("G2PlusPlus::phi => Invalid Inputs");

		double dblSpotViewDCF = 1. * (iViewDate - iSpotDate) / 365.25;

		double dblFactor1Phi = _dblSigma / _dblA * (1. - java.lang.Math.exp (-1. * _dblA * dblSpotViewDCF));

		double dblFactor2Phi = _dblEta / _dblB * (1. - java.lang.Math.exp (-1. * _dblB * dblSpotViewDCF));

		return _auIFRInitial.evaluate (iViewDate) + 0.5 * dblFactor1Phi * dblFactor1Phi + 0.5 * dblFactor2Phi
			* dblFactor2Phi;
	}

	/**
	 * Compute the X Increment
	 * 
	 * @param iSpotDate The Spot Date
	 * @param iViewDate The View Date
	 * @param dblX The X Value
	 * @param iSpotTimeIncrement The Spot Time Increment
	 * 
	 * @return The X Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double deltaX (
		final int iSpotDate,
		final int iViewDate,
		final double dblX,
		final int iSpotTimeIncrement)
		throws java.lang.Exception
	{
		if (iSpotDate > iViewDate || !org.drip.numerical.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception ("G2PlusPlus::deltaX => Invalid Inputs");

		double dblAnnualizedIncrement = 1. * iSpotTimeIncrement / 365.25;

		return -1. * _dblA * dblX * dblAnnualizedIncrement + _dblSigma * java.lang.Math.sqrt
			(dblAnnualizedIncrement) * _aRSG[0].random();
	}

	/**
	 * Compute the Y Increment
	 * 
	 * @param iSpotDate The Spot Date
	 * @param iViewDate The View Date
	 * @param dblY The Y Value
	 * @param iSpotTimeIncrement The Spot Time Increment
	 * 
	 * @return The Y Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double deltaY (
		final int iSpotDate,
		final int iViewDate,
		final double dblY,
		final int iSpotTimeIncrement)
		throws java.lang.Exception
	{
		if (iSpotDate > iViewDate || !org.drip.numerical.common.NumberUtil.IsValid (dblY))
			throw new java.lang.Exception ("G2PlusPlus::deltaY => Invalid Inputs");

		double dblAnnualizedIncrement = 1. * iSpotTimeIncrement / 365.25;

		return -1. * _dblB * dblY * dblAnnualizedIncrement + _dblEta * java.lang.Math.sqrt
			(dblAnnualizedIncrement) * _aRSG[1].random();
	}
}
