
package org.drip.state.sequence;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * PathVertexGovvie exposes the Functionality to generate a Sequence of Path/Vertex Govvie Curves.
 *
 * @author Lakshmi Krishnamurthy
 */

public class PathVertexGovvie extends org.drip.state.sequence.PathVertexRd {
	private org.drip.state.sequence.GovvieBuilderSettings _gbs = null;

	private org.drip.state.govvie.GovvieCurve[][] curveVertex (
		final double[][][] aaadblPathForward)
	{
		if (null == aaadblPathForward) return null;

		org.drip.measure.discrete.CorrelatedPathVertexDimension cpvd = cpvd();

		int iNumPath = cpvd.numPath();

		int iNumVertex = cpvd.numVertex();

		java.lang.String[] astrTenor = _gbs.tenors();

		java.lang.String strTreasuryCode = _gbs.code();

		org.drip.analytics.date.JulianDate dtSpot = _gbs.spot();

		java.lang.String strCurrency = _gbs.groundState().currency();

		org.drip.state.nonlinear.FlatForwardGovvieCurve[][] aaFFGC = new
			org.drip.state.nonlinear.FlatForwardGovvieCurve[iNumPath][iNumVertex];

		for (int iTimeVertex = 0; iTimeVertex < iNumVertex; ++iTimeVertex) {
			org.drip.analytics.date.JulianDate dtEvent = dtSpot.addYears (iTimeVertex + 1);

			if (null == dtEvent) return null;

			int iEventDate = dtEvent.julian();

			int[] aiDate = org.drip.analytics.support.Helper.TenorToDate (dtEvent, astrTenor);

			for (int iPath = 0; iPath < iNumPath; ++iPath) {
				try {
					if (null == (aaFFGC[iPath][iTimeVertex] = new
						org.drip.state.nonlinear.FlatForwardGovvieCurve (iEventDate, strTreasuryCode,
							strCurrency, aiDate, aaadblPathForward[iPath][iTimeVertex])))
						return null;
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		return aaFFGC;
	}

	/**
	 * Generate a Standard Instance of PathVertexGovvie
	 * 
	 * @param gbs Govvie Builder Settings Instance
	 * @param cpvd Latent State Evolver CPVD Instance
	 * @param de The Latent State Diffusion Evolver
	 * 
	 * @return Standard Instance of PathVertexGovvie
	 */

	public static final PathVertexGovvie Standard (
		final org.drip.state.sequence.GovvieBuilderSettings gbs,
		final org.drip.measure.discrete.CorrelatedPathVertexDimension cpvd,
		final org.drip.measure.process.DiffusionEvolver de)
	{
		if (null == cpvd || null == de) return null;

		int iNumDimension = cpvd.numDimension();

		org.drip.measure.process.DiffusionEvolver[] aDE = new
			org.drip.measure.process.DiffusionEvolver[iNumDimension];

		for (int iDimension = 0; iDimension < iNumDimension; ++iDimension)
			aDE[iDimension] = de;

		try {
			return new PathVertexGovvie (gbs, cpvd, aDE);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * PathVertexGovvie Constructor
	 * 
	 * @param gbs Govvie Builder Settings
	 * @param cpvd Latent State Evolver CPVD Instance
	 * @param aDE Array of the Latent State Diffusion Evolvers
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PathVertexGovvie (
		final org.drip.state.sequence.GovvieBuilderSettings gbs,
		final org.drip.measure.discrete.CorrelatedPathVertexDimension cpvd,
		final org.drip.measure.process.DiffusionEvolver[] aDE)
		throws java.lang.Exception
	{
		super (cpvd, aDE);

		if (null == (_gbs = gbs))
			throw new java.lang.Exception ("PathVertexGovvie Constructor => Invalid Inputs");
	}

	/**
	 * Generate the Govvie Builder Settings Instance
	 * 
	 * @return The Govvie Builder Settings Instance
	 */

	public org.drip.state.sequence.GovvieBuilderSettings govvieBuilderSettings()
	{
		return _gbs;
	}

	/**
	 * Generate the R^d Path/Vertex Govvie Curves using the Initial R^d and the Evolution Time Width
	 * 
	 * @param adblTimeIncrement Array of the Evolution Time Widths
	 * 
	 * @return The R^d Path//Vertex Govvie Curves
	 */

	public org.drip.state.govvie.GovvieCurve[][] pathVertex (
		final double[] adblTimeIncrement)
	{
		return curveVertex (pathVertex (_gbs.groundForwardYield(), adblTimeIncrement));
	}

	/**
	 * Generate the R^d Path/Vertex Govvie Curves using the Initial R^d and the Evolution Time Width
	 * 
	 * @param dblTimeIncrement The Evolution Time Widths
	 * 
	 * @return The R^d Path//Vertex Govvie Curves
	 */

	public org.drip.state.govvie.GovvieCurve[][] pathVertex (
		final double dblTimeIncrement)
	{
		return curveVertex (pathVertex (_gbs.groundForwardYield(), dblTimeIncrement));
	}

	/**
	 * Generate the R^d Path/Vertex Govvie Curves using the Initial R^d and the Array of Event Tenors
	 * 
	 * @param astrEventTenor The Array of Event Tenors
	 * 
	 * @return The R^d Path//Vertex Govvie Curves
	 */

	public org.drip.state.govvie.GovvieCurve[][] pathVertex (
		final java.lang.String[] astrEventTenor)
	{
		return curveVertex (pathVertex (_gbs.groundForwardYield(), astrEventTenor));
	}

	/**
	 * Generate the R^d Path/Vertex Govvie Curves using the Initial R^d and the Array of Event Tenors
	 * 
	 * @param aiEventDate The Array of Event Dates
	 * 
	 * @return The R^d Path//Vertex Govvie Curves
	 */

	public org.drip.state.govvie.GovvieCurve[][] pathVertex (
		final int[] aiEventDate)
	{
		return curveVertex (pathVertex (_gbs.groundForwardYield(), _gbs.spot().julian(), aiEventDate));
	}
}
