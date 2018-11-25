
package org.drip.state.sequence;

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
 * <i>PathVertexGovvie</i> exposes the Functionality to generate a Sequence of Path/Vertex Govvie Curves.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/sequence">Sequence</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
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
