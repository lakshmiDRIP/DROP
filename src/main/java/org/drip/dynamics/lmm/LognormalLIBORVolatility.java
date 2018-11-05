
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
 * <i>LognormalLIBORVolatility</i> implements the Multi-Factor Log-normal LIBOR Volatility as formulated in:
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
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LognormalLIBORVolatility extends org.drip.dynamics.hjm.MultiFactorVolatility {
	private int _iSpotDate = java.lang.Integer.MIN_VALUE;
	private org.drip.state.identifier.ForwardLabel _lslForward = null;

	/**
	 * LognormalLIBORVolatility Constructor
	 * 
	 * @param iSpotDate The Spot Date
	 * @param lslForward The Forward Label
	 * @param aMSVolatility Array of the Multi-Factor Volatility Surfaces
	 * @param pfsg Principal Factor Sequence Generator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LognormalLIBORVolatility (
		final int iSpotDate,
		final org.drip.state.identifier.ForwardLabel lslForward,
		final org.drip.analytics.definition.MarketSurface[] aMSVolatility,
		final org.drip.sequence.random.PrincipalFactorSequenceGenerator pfsg)
		throws java.lang.Exception
	{
		super (aMSVolatility, pfsg);

		if (null == (_lslForward = lslForward))
			throw new java.lang.Exception ("LognormalLIBORVolatility ctr: Invalid Inputs");

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
	 * Retrieve the Forward Label
	 * 
	 * @return The Forward Label
	 */

	public org.drip.state.identifier.ForwardLabel forwardLabel()
	{
		return _lslForward;
	}

	/**
	 * Compute the Constraint in the Difference in the Volatility of the Continuously Compounded Forward Rate
	 * 	between the Target Date and the Target Date + Forward Tenor
	 * 
	 * @param fc The Forward Curve Instance
	 * @param iTargetDate The Target Date
	 * 
	 * @return The Constraint in the Difference in the Volatility of the Continuously Compounded Forward Rate
	 */

	public double[] continuousForwardVolatilityConstraint (
		final org.drip.state.forward.ForwardCurve fc,
		final int iTargetDate)
	{
		if (null == fc || iTargetDate <= _iSpotDate) return null;

		java.lang.String strTenor = _lslForward.tenor();

		org.drip.analytics.definition.MarketSurface[] aMS = volatilitySurface();

		try {
			double dblLIBORDCF = fc.forward (new org.drip.analytics.date.JulianDate (iTargetDate).addTenor
				(strTenor)) * org.drip.analytics.support.Helper.TenorToYearFraction (strTenor);

			int iNumSurface = aMS.length;
			double dblConstraintWeight = dblLIBORDCF / (1. + dblLIBORDCF);
			double[] adblContinuousForwardVolatilityConstraint = new double[iNumSurface];

			for (int i = 0; i < iNumSurface; ++i)
				adblContinuousForwardVolatilityConstraint[i] = dblConstraintWeight * aMS[i].node (_iSpotDate,
					iTargetDate);

			return adblContinuousForwardVolatilityConstraint;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Volatility of the Continuously Compounded Forward Rate Up to the Target Date
	 * 
	 * @param iTargetDate The Target Date
	 * @param fc The Forward Curve Instance
	 * 
	 * @return The Volatility of the Continuously Compounded Forward Rate Up to the Target Date
	 */

	public double[] continuousForwardVolatility (
		final int iTargetDate,
		final org.drip.state.forward.ForwardCurve fc)
	{
		if (iTargetDate <= _iSpotDate || null == fc) return null;

		org.drip.sequence.random.PrincipalFactorSequenceGenerator pfsg = msg();

		int iNumFactor = pfsg.numFactor();

		boolean bLoop = true;
		int iEndDate = _iSpotDate;
		double dblTenorDCF = java.lang.Double.NaN;
		double[] adblContinuousForwardVolatility = new double[iNumFactor];

		java.lang.String strTenor = _lslForward.tenor();

		try {
			dblTenorDCF = org.drip.analytics.support.Helper.TenorToYearFraction (strTenor);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i < iNumFactor; ++i)
			adblContinuousForwardVolatility[i] = 0.;

		double[] adblFactorPointVolatility = factorPointVolatility (_iSpotDate, iEndDate);

		while (bLoop) {
			try {
				if ((iEndDate = new org.drip.analytics.date.JulianDate (iEndDate).addTenor
					(strTenor).julian()) > iTargetDate)
					bLoop = false;

				double dblLIBORTenorDCF = fc.forward (iEndDate) * dblTenorDCF;

				double dblLIBORLognormalVolatilityScaler = dblLIBORTenorDCF / (1. + dblLIBORTenorDCF);

				for (int i = 0; i < iNumFactor; ++i)
					adblContinuousForwardVolatility[i] += dblLIBORLognormalVolatilityScaler *
						adblFactorPointVolatility[i];
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adblContinuousForwardVolatility;
	}

	/**
	 * Compute the Volatility of the Continuously Compounded Forward Rate Up to the Target Date
	 * 
	 * @param iTargetDate The Target Date
	 * @param dc The Discount Curve Instance
	 * 
	 * @return The Volatility of the Continuously Compounded Forward Rate Up to the Target Date
	 */

	public double[] continuousForwardVolatility (
		final int iTargetDate,
		final org.drip.state.discount.MergedDiscountForwardCurve dc)
	{
		if (iTargetDate <= _iSpotDate || null == dc) return null;

		org.drip.sequence.random.PrincipalFactorSequenceGenerator pfsg = msg();

		int iNumFactor = pfsg.numFactor();

		boolean bLoop = true;
		int iStartDate = _iSpotDate;
		double dblTenorDCF = java.lang.Double.NaN;
		double[] adblContinuousForwardVolatility = new double[iNumFactor];

		java.lang.String strTenor = _lslForward.tenor();

		try {
			dblTenorDCF = org.drip.analytics.support.Helper.TenorToYearFraction (strTenor);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i < iNumFactor; ++i)
			adblContinuousForwardVolatility[i] = 0.;

		double[] adblFactorPointVolatility = factorPointVolatility (_iSpotDate, iStartDate);

		while (bLoop) {
			try {
				double dblLIBORTenorDCF = dc.libor (iStartDate, strTenor) * dblTenorDCF;

				double dblLIBORLognormalVolatilityScaler = dblLIBORTenorDCF / (1. + dblLIBORTenorDCF);

				for (int i = 0; i < iNumFactor; ++i)
					adblContinuousForwardVolatility[i] += dblLIBORLognormalVolatilityScaler *
						adblFactorPointVolatility[i];

				if ((iStartDate = new org.drip.analytics.date.JulianDate (iStartDate).addTenor
					(strTenor).julian()) > iTargetDate)
					bLoop = false;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adblContinuousForwardVolatility;
	}

	/**
	 * Multi-Factor Cross Volatility Integral
	 * 
	 * @param iForwardDate1 Forward Date #1
	 * @param iForwardDate2 Forward Date #2
	 * @param iTerminalDate The Terminal Date
	 * 
	 * @return The Multi-Factor Cross Volatility Integral
	 * 
	 * @throws java.lang.Exception Thrown if the Multi-Factor Cross Volatility Integral cannot be computed
	 */

	public double crossVolatilityIntegralProduct (
		final int iForwardDate1,
		final int iForwardDate2,
		final int iTerminalDate)
		throws java.lang.Exception
	{
		if (iForwardDate1 < iTerminalDate || iForwardDate2 < iTerminalDate)
			throw new java.lang.Exception
				("LognormalLIBORVolatility::crossVolatilityIntegralProduct => Invalid Inputs");

		org.drip.function.definition.R1ToR1 crossVolR1ToR1 = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblDate)
				throws java.lang.Exception
			{
				double dblCrossVolProduct = 0.;

				int iNumFactor = msg().numFactor();

				for (int iFactorIndex = 0; iFactorIndex < iNumFactor; ++iFactorIndex)
					dblCrossVolProduct += factorPointVolatility (iFactorIndex, (int) dblDate, iForwardDate1)
						* factorPointVolatility (iFactorIndex, (int) dblDate, iForwardDate2);

				return dblCrossVolProduct;
			}
		};

		return crossVolR1ToR1.integrate (_iSpotDate, iTerminalDate);
	}
}
