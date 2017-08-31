
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
 * PathVertexRd exposes the Functionality to generate a Sequence of the Path Vertex Latent State R^d
 *  Realizations across Multiple Paths.
 *
 * @author Lakshmi Krishnamurthy
 */

public class PathVertexRd {
	private org.drip.measure.process.DiffusionEvolver[] _aDE = null;
	private org.drip.measure.discrete.CorrelatedPathVertexDimension _cpvd = null;

	/**
	 * Generate a Standard Instance of PathVertexRd
	 * 
	 * @param cpvd Latent State Evolver CPVD Instance
	 * @param de The Latent State Diffusion Evolver
	 * 
	 * @return Standard Instance of PathVertexRd
	 */

	public static final PathVertexRd Standard (
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
			return new PathVertexRd (cpvd, aDE);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * PathVertexRd Constructor
	 * 
	 * @param cpvd Latent State Evolver CPVD Instance
	 * @param aDE Array of the Latent State Diffusion Evolvers
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PathVertexRd (
		final org.drip.measure.discrete.CorrelatedPathVertexDimension cpvd,
		final org.drip.measure.process.DiffusionEvolver[] aDE)
		throws java.lang.Exception
	{
		if (null == (_cpvd = cpvd) || null == (_aDE = aDE))
			throw new java.lang.Exception ("PathVertexRd Constructor => Invalid Inputs");

		int iNumDimension = _aDE.length;

		if (iNumDimension != _cpvd.numDimension())
			throw new java.lang.Exception ("PathVertexRd Constructor => Invalid Inputs");

		for (int iDimension = 0; iDimension < iNumDimension; ++iDimension) {
			if (null == _aDE[iDimension])
				throw new java.lang.Exception ("PathVertexRd Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Latent State Dimension
	 * 
	 * @return The Latent State Dimension
	 */

	public int dimension()
	{
		return _aDE.length;
	}

	/**
	 * Retrieve the Latent State Evolver CPVD Instance
	 * 
	 * @return The Latent State Evolver CPVD Instance
	 */

	public org.drip.measure.discrete.CorrelatedPathVertexDimension cpvd()
	{
		return _cpvd;
	}

	/**
	 * Retrieve the Array of the Latent State Diffusion Evolvers
	 * 
	 * @return The Array of the Latent State Diffusion Evolvers
	 */

	public org.drip.measure.process.DiffusionEvolver[] evolver()
	{
		return _aDE;
	}

	/**
	 * Generate the R^d Path Vertex Realizations using the Initial R^d and the Evolution Time Width
	 * 
	 * @param adblPathInitial The Initial Path R^d
	 * @param adblTimeIncrement The Array of Evolution Time Width Increments
	 * 
	 * @return The R^d Path Vertex Realizations
	 */

	public double[][][] pathVertex (
		final double[] adblPathInitial,
		final double[] adblTimeIncrement)
	{
		if (null == adblPathInitial || null == adblTimeIncrement) return null;

		int iNumPath = _cpvd.numPath();

		int iNumDimension = dimension();

		int iNumVertex = _cpvd.numVertex();

		if (iNumDimension != adblPathInitial.length || iNumVertex != adblTimeIncrement.length) return null;

		double[][][] aaadblPathForward = new double[iNumPath][iNumVertex][iNumDimension];

		org.drip.measure.discrete.VertexRd[] aVertexRd = _cpvd.multiPathVertexRd();

		if (null == aVertexRd || iNumPath != aVertexRd.length) return null;

		for (int iPath = 0; iPath < iNumPath; ++iPath) {
			if (null == aVertexRd[iPath]) return null;

			java.util.List<double[]> lsVertexRd = aVertexRd[iPath].vertexList();

			org.drip.measure.realization.JumpDiffusionEdgeUnit[][] aaJDEU = new
				org.drip.measure.realization.JumpDiffusionEdgeUnit[iNumDimension][iNumVertex];
			org.drip.measure.realization.JumpDiffusionVertex[][] aaJDV = new
				org.drip.measure.realization.JumpDiffusionVertex[iNumDimension][iNumVertex + 1];

			for (int iTimeVertex = 0; iTimeVertex < iNumVertex; ++iTimeVertex) {
				double[] adblRd = lsVertexRd.get (iTimeVertex);

				if (null == adblRd || iNumDimension != adblRd.length) return null;

				for (int iDimension = 0; iDimension < iNumDimension; ++iDimension) {
					try {
						aaJDEU[iDimension][iTimeVertex] = new
							org.drip.measure.realization.JumpDiffusionEdgeUnit
								(adblTimeIncrement[iDimension], adblRd[iDimension], 0.);
					} catch (java.lang.Exception e) {
						e.printStackTrace();

						return null;
					}
				}
			}

			for (int iDimension = 0; iDimension < iNumDimension; ++iDimension) {
				try {
					aaJDV[iDimension] = _aDE[iDimension].vertexSequence (new
						org.drip.measure.realization.JumpDiffusionVertex (0., adblPathInitial[iDimension],
							0., false), aaJDEU[iDimension], adblTimeIncrement);
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}

			for (int iTimeVertex = 0; iTimeVertex < iNumVertex; ++iTimeVertex) {
				for (int iDimension = 0; iDimension < iNumDimension; ++iDimension)
					aaadblPathForward[iPath][iTimeVertex][iDimension] =
						aaJDV[iDimension][iTimeVertex].value();
			}
		}

		return aaadblPathForward;
	}

	/**
	 * Generate the R^d Path Vertex Realizations using the Initial R^d and the Evolution Time Width
	 * 
	 * @param adblPathInitial The Initial Path R^d
	 * @param dblTimeIncrement The Evolution Time Width
	 * 
	 * @return The R^d Path Vertex Realizations
	 */

	public double[][][] pathVertex (
		final double[] adblPathInitial,
		final double dblTimeIncrement)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblTimeIncrement)) return null;

		int iNumVertex = _cpvd.numVertex();

		double[] adblTimeIncrement = new double[iNumVertex];

		for (int iTimeVertex = 0; iTimeVertex < iNumVertex; ++iTimeVertex)
			adblTimeIncrement[iTimeVertex] = dblTimeIncrement;

		return pathVertex (adblPathInitial, adblTimeIncrement);
	}

	/**
	 * Generate the R^d Path Vertex Realizations using the Initial R^d and the Array of Event Tenors
	 * 
	 * @param adblPathInitial The Initial Path R^d
	 * @param astrEventTenor The Array of Event Tenors
	 * 
	 * @return The R^d Path Vertex Realizations
	 */

	public double[][][] pathVertex (
		final double[] adblPathInitial,
		final java.lang.String[] astrEventTenor)
	{
		if (null == astrEventTenor) return null;

		int iNumVertex = _cpvd.numVertex();

		if (iNumVertex != astrEventTenor.length) return null;

		double[] adblTimeIncrement = new double[iNumVertex];

		for (int iTimeVertex = 0; iTimeVertex < iNumVertex; ++iTimeVertex) {
			try {
				adblTimeIncrement[iTimeVertex] = org.drip.analytics.support.Helper.TenorToYearFraction
					(astrEventTenor[iTimeVertex]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return pathVertex (adblPathInitial, adblTimeIncrement);
	}

	/**
	 * Generate the R^d Path Vertex Realizations using the Initial R^d and the Array of Event Tenors
	 * 
	 * @param adblPathInitial The Initial Path R^d
	 * @param iSpotDate The Spot Date
	 * @param aiEventDate The Array of Event Dates
	 * 
	 * @return The R^d Path Vertex Realizations
	 */

	public double[][][] pathVertex (
		final double[] adblPathInitial,
		final int iSpotDate,
		final int[] aiEventDate)
	{
		if (null == aiEventDate) return null;

		int iNumVertex = _cpvd.numVertex();

		if (iNumVertex != aiEventDate.length) return null;

		double[] adblTimeIncrement = new double[iNumVertex];

		org.drip.analytics.daycount.ActActDCParams aap =
			org.drip.analytics.daycount.ActActDCParams.FromFrequency (1);

		for (int iTimeVertex = 0; iTimeVertex < iNumVertex; ++iTimeVertex) {
			try {
				adblTimeIncrement[iTimeVertex] = org.drip.analytics.daycount.Convention.YearFraction
					(iSpotDate, aiEventDate[iTimeVertex], "Act/Act ISDA", false, aap, "");
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return pathVertex (adblPathInitial, adblTimeIncrement);
	}
}
