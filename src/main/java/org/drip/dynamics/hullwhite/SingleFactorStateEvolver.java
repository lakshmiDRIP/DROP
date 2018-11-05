
package org.drip.dynamics.hullwhite;

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
 * <i>SingleFactorStateEvolver</i> provides the Hull-White One-Factor Gaussian HJM Short Rate Dynamics
 * 	Implementation.
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics">Dynamics</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/hullwhite">Hull White</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/Fixed Income">Fixed Income Analytics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SingleFactorStateEvolver implements org.drip.dynamics.evolution.PointStateEvolver {
	private double _dblA = java.lang.Double.NaN;
	private double _dblSigma = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _auIFRInitial = null;
	private org.drip.state.identifier.FundingLabel _lslFunding = null;
	private org.drip.sequence.random.UnivariateSequenceGenerator _usg = null;

	/**
	 * SingleFactorStateEvolver Constructor
	 * 
	 * @param lslFunding The Funding Latent State Label
	 * @param dblSigma Sigma
	 * @param dblA A
	 * @param auIFRInitial The Initial Instantaneous Forward Rate Term Structure
	 * @param usg Univariate Random Sequence Generator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SingleFactorStateEvolver (
		final org.drip.state.identifier.FundingLabel lslFunding,
		final double dblSigma,
		final double dblA,
		final org.drip.function.definition.R1ToR1 auIFRInitial,
		final org.drip.sequence.random.UnivariateSequenceGenerator usg)
		throws java.lang.Exception
	{
		if (null == (_lslFunding = lslFunding) || !org.drip.quant.common.NumberUtil.IsValid (_dblSigma =
			dblSigma) || !org.drip.quant.common.NumberUtil.IsValid (_dblA = dblA) || null == (_auIFRInitial =
				auIFRInitial) || null == (_usg = usg))
			throw new java.lang.Exception ("SingleFactorStateEvolver ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Funding Label
	 * 
	 * @return The Funding Label
	 */

	public org.drip.state.identifier.FundingLabel fundingLabel()
	{
		return _lslFunding;
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
	 * Retrieve the Initial Instantaneous Forward Rate Term Structure
	 * 
	 * @return The Initial Instantaneous Forward Rate Term Structure
	 */

	public org.drip.function.definition.R1ToR1 ifrInitialTermStructure()
	{
		return _auIFRInitial;
	}

	/**
	 * Retrieve the Random Sequence Generator
	 * 
	 * @return The Random Sequence Generator
	 */

	public org.drip.sequence.random.UnivariateSequenceGenerator rsg()
	{
		return _usg;
	}

	/**
	 * Calculate the Alpha
	 * 
	 * @param iSpotDate The Spot Date
	 * @param iViewDate The View Date
	 * 
	 * @return Alpha
	 * 
	 * @throws java.lang.Exception Thrown if Alpha cannot be computed
	 */

	public double alpha (
		final int iSpotDate,
		final int iViewDate)
		throws java.lang.Exception
	{
		if (iSpotDate > iViewDate)
			throw new java.lang.Exception ("SingleFactorStateEvolver::alpha => Invalid Inputs");

		double dblAlphaVol = _dblSigma * (1. - java.lang.Math.exp (_dblA * (iViewDate - iSpotDate) / 365.25))
			/ _dblA;

		return _auIFRInitial.evaluate (iViewDate) + 0.5 * dblAlphaVol * dblAlphaVol;
	}

	/**
	 * Calculate the Theta
	 * 
	 * @param iSpotDate The Spot Date
	 * @param iViewDate The View Date
	 * 
	 * @return Theta
	 * 
	 * @throws java.lang.Exception Thrown if Theta cannot be computed
	 */

	public double theta (
		final int iSpotDate,
		final int iViewDate)
		throws java.lang.Exception
	{
		if (iSpotDate > iViewDate)
			throw new java.lang.Exception ("SingleFactorStateEvolver::theta => Invalid Inputs");

		return _auIFRInitial.derivative (iViewDate, 1) + _dblA * _auIFRInitial.evaluate (iViewDate) +
			_dblSigma * _dblSigma / (2. * _dblA) * (1. - java.lang.Math.exp (-2. * _dblA * (iViewDate -
				iSpotDate) / 365.25));
	}

	/**
	 * Calculate the Short Rate Increment
	 * 
	 * @param iSpotDate The Spot Date
	 * @param iViewDate The View Date
	 * @param dblShortRate The Short Rate
	 * @param iViewTimeIncrement The View Time Increment
	 * 
	 * @return The Short Rate Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Short Rate cannot be computed
	 */

	public double shortRateIncrement (
		final int iSpotDate,
		final int iViewDate,
		final double dblShortRate,
		final int iViewTimeIncrement)
		throws java.lang.Exception
	{
		if (iSpotDate > iViewDate || !org.drip.quant.common.NumberUtil.IsValid (dblShortRate))
			throw new java.lang.Exception ("SingleFactorStateEvolver::shortRateIncrement => Invalid Inputs");

		double dblAnnualizedIncrement = 1. * iViewTimeIncrement / 365.25;

		return (theta (iSpotDate, iViewDate) - _dblA * dblShortRate) * dblAnnualizedIncrement + _dblSigma *
			java.lang.Math.sqrt (dblAnnualizedIncrement) * _usg.random();
	}

	@Override public org.drip.dynamics.evolution.LSQMPointUpdate evolve (
		final int iSpotDate,
		final int iViewDate,
		final int iSpotTimeIncrement,
		final org.drip.dynamics.evolution.LSQMPointUpdate lsqmPrev)
	{
		if (iViewDate < iSpotDate || null == lsqmPrev || !(lsqmPrev instanceof
			org.drip.dynamics.hullwhite.ShortRateUpdate))
			return null;

		int iDate = iSpotDate;
		int iTimeIncrement = 1;
		int iFinalDate = iSpotDate + iSpotTimeIncrement;
		double dblInitialShortRate = java.lang.Double.NaN;

		try {
			dblInitialShortRate = ((org.drip.dynamics.hullwhite.ShortRateUpdate)
				lsqmPrev).realizedFinalShortRate();
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		double dblShortRate = dblInitialShortRate;

		while (iDate < iFinalDate) {
			try {
				dblShortRate += shortRateIncrement (iSpotDate, iDate, dblShortRate, iTimeIncrement);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			++iDate;
		}

		double dblADF = java.lang.Math.exp (-1. * _dblA * iSpotTimeIncrement);

		double dblB = (1. - dblADF) / _dblA;

		try {
			return org.drip.dynamics.hullwhite.ShortRateUpdate.Create (_lslFunding, iSpotDate, iFinalDate,
				iViewDate, dblInitialShortRate, dblShortRate, dblInitialShortRate * dblADF + alpha
					(iSpotDate, iFinalDate) - alpha (iSpotDate, iViewDate) * dblADF, 0.5 * _dblSigma *
						_dblSigma * (1. - dblADF * dblADF) / _dblA, java.lang.Math.exp (dblB *
							_auIFRInitial.evaluate (iViewDate) - 0.25 * _dblSigma * _dblSigma * (1. -
								java.lang.Math.exp (-2. * _dblA * (iViewDate - iSpotDate) / 365.25)) * dblB *
									dblB / _dblA));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Metrics associated with the Transition that results from using a Trinomial Tree Using the
	 *  Starting Node Metrics
	 * 
	 * @param iSpotDate The Spot/Epoch Date
	 * @param iInitialDate The Initial Date
	 * @param iTerminalDate The Terminal Date
	 * @param hwnmInitial The Initial Node Metrics
	 * 
	 * @return The Hull White Transition Metrics
	 */

	public org.drip.dynamics.hullwhite.TrinomialTreeTransitionMetrics evolveTrinomialTree (
		final int iSpotDate,
		final int iInitialDate,
		final int iTerminalDate,
		final org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics hwnmInitial)
	{
		if (iInitialDate < iSpotDate || iTerminalDate <= iInitialDate) return null;

		long lTreeTimeIndex = 0L;
		double dblExpectedTerminalX = 0.;
		long lTreeStochasticBaseIndex = 0L;

		if (null != hwnmInitial) {
			dblExpectedTerminalX = hwnmInitial.x();

			lTreeTimeIndex = hwnmInitial.timeIndex() + 1;

			lTreeStochasticBaseIndex = hwnmInitial.xStochasticIndex();
		}

		double dblADF = java.lang.Math.exp (-1. * _dblA * (iTerminalDate - iInitialDate) / 365.25);

		try {
			return new org.drip.dynamics.hullwhite.TrinomialTreeTransitionMetrics (iInitialDate,
				iTerminalDate, lTreeTimeIndex, lTreeStochasticBaseIndex, dblExpectedTerminalX * dblADF, 0.5 *
					_dblSigma * _dblSigma * (1. - dblADF * dblADF) / _dblA, alpha (iSpotDate,
						iTerminalDate));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Evolve the Trinomial Tree Sequence
	 * 
	 * @param iSpotDate The Spot Date
	 * @param iInitialDate The Initial Date
	 * @param iDayIncrement The Day Increment
	 * @param iNumIncrement Number of Times to Increment
	 * @param hwnm Starting Node Metrics
	 * @param hwsm The Sequence Metrics
	 * 
	 * @return TRUE - The Tree Successfully Evolved
	 */

	public boolean evolveTrinomialTreeSequence (
		final int iSpotDate,
		final int iInitialDate,
		final int iDayIncrement,
		final int iNumIncrement,
		final org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics hwnm,
		final org.drip.dynamics.hullwhite.TrinomialTreeSequenceMetrics hwsm)
	{
		if (iInitialDate < iSpotDate || 0 >= iDayIncrement || null == hwsm) return false;

		if (0 == iNumIncrement) return true;

		org.drip.dynamics.hullwhite.TrinomialTreeTransitionMetrics hwtm = evolveTrinomialTree (iSpotDate,
			iInitialDate, iInitialDate + iDayIncrement, hwnm);

		if (!hwsm.addTransitionMetrics (hwtm)) return false;

		org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics hwnmUp = hwtm.upNodeMetrics();

		if (!hwsm.addNodeMetrics (hwnmUp) || (null != hwnm && !hwsm.setTransitionProbability (hwnm, hwnmUp,
			hwtm.probabilityUp())) || !evolveTrinomialTreeSequence (iSpotDate, iInitialDate + iDayIncrement,
				iDayIncrement, iNumIncrement - 1, hwnmUp, hwsm))
			return false;

		org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics hwnmDown = hwtm.downNodeMetrics();

		if (!hwsm.addNodeMetrics (hwnmDown) || (null != hwnm && !hwsm.setTransitionProbability (hwnm,
			hwnmDown, hwtm.probabilityDown())) || !evolveTrinomialTreeSequence (iSpotDate, iInitialDate +
				iDayIncrement, iDayIncrement, iNumIncrement - 1, hwnmDown, hwsm))
			return false;

		org.drip.dynamics.hullwhite.TrinomialTreeNodeMetrics hwnmStay = hwtm.stayNodeMetrics();

		if (!hwsm.addNodeMetrics (hwnmStay) || (null != hwnm && !hwsm.setTransitionProbability (hwnm,
			hwnmStay, hwtm.probabilityStay())) || !evolveTrinomialTreeSequence (iSpotDate, iInitialDate +
				iDayIncrement, iDayIncrement, iNumIncrement - 1, hwnmStay, hwsm))
			return false;

		return true;
	}

	/**
	 * Evolve the Trinomial Tree Sequence
	 * 
	 * @param iSpotDate The Spot Date
	 * @param iDayIncrement The Day Increment
	 * @param iNumIncrement Number of Times to Increment
	 * 
	 * @return The Sequence Metrics
	 */

	public org.drip.dynamics.hullwhite.TrinomialTreeSequenceMetrics evolveTrinomialTreeSequence (
		final int iSpotDate,
		final int iDayIncrement,
		final int iNumIncrement)
	{
		org.drip.dynamics.hullwhite.TrinomialTreeSequenceMetrics hwsm = new
			org.drip.dynamics.hullwhite.TrinomialTreeSequenceMetrics();

		return evolveTrinomialTreeSequence (iSpotDate, iSpotDate, iDayIncrement, iNumIncrement, null, hwsm) ?
			hwsm : null;
	}
}
