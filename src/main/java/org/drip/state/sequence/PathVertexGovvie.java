
package org.drip.state.sequence;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.Helper;
import org.drip.measure.discontinuous.CorrelatedPathVertexDimension;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.state.govvie.GovvieCurve;
import org.drip.state.nonlinear.FlatForwardGovvieCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>PathVertexGovvie</i> exposes the Functionality to generate a Sequence of Path/Vertex Govvie Curves. It
 *  exposes the following functionality:
 *  
 *  <br><br>
 *  <ul>
 *		<li>PathVertexGovvie Constructor</li>
 *		<li>Generate the Govvie Builder Settings Instance</li>
 *		<li>Generate the R<sup>d</sup> Path/Vertex Govvie Curves using the Initial R<sup>d</sup> and the Evolution Time Increment Array</li>
 *		<li>Generate the R<sup>d</sup> Path/Vertex Govvie Curves using the Initial R<sup>d</sup> and a Fixed Evolution Time Width</li>
 *		<li>Generate the R<sup>d</sup> Path/Vertex Govvie Curves using the Initial R<sup>d</sup> and the Array of Forward Evolution Tenors</li>
 *		<li>Generate the R<sup>d</sup> Path/Vertex Govvie Curves using the Initial R<sup>d</sup> and the Array of Forward Evolution Dates</li>
 *		<li>Generate a Standard Instance of PathVertexGovvie</li>
 *  </ul>
 *  <br><br>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/sequence/README.md">Monte Carlo Path State Realizations</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PathVertexGovvie extends PathVertexRd
{
	private GovvieBuilderSettings _govvieBuilderSettings = null;

	private GovvieCurve[][] curveVertex (
		final double[][][] forwardPathRdVertexRealization)
	{
		if (null == forwardPathRdVertexRealization) {
			return null;
		}

		CorrelatedPathVertexDimension correlatedPathVertexDimension = cpvd();

		int pathCount = correlatedPathVertexDimension.numPath();

		int timeVertexCount = correlatedPathVertexDimension.numVertex();

		String[] tenorArray = _govvieBuilderSettings.tenors();

		String treasuryCode = _govvieBuilderSettings.code();

		JulianDate spotDate = _govvieBuilderSettings.spot();

		String currency = _govvieBuilderSettings.groundState().currency();

		FlatForwardGovvieCurve[][] flatForwardGovvieCurveGrid =
			new FlatForwardGovvieCurve[pathCount][timeVertexCount];

		for (int timeVertexIndex = 0; timeVertexIndex < timeVertexCount; ++timeVertexIndex) {
			JulianDate eventDate = spotDate.addYears (timeVertexIndex + 1);

			if (null == eventDate) {
				return null;
			}

			int eventDateInteger = eventDate.julian();

			int[] tenorDateArray = Helper.TenorToDate (eventDate, tenorArray);

			for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
				try {
					if (null == (
						flatForwardGovvieCurveGrid[pathIndex][timeVertexIndex] = new FlatForwardGovvieCurve (
							eventDateInteger,
							treasuryCode,
							currency,
							tenorDateArray,
							forwardPathRdVertexRealization[pathIndex][timeVertexIndex]
						))) {
						return null;
					}
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		return flatForwardGovvieCurveGrid;
	}

	/**
	 * Generate a Standard Instance of PathVertexGovvie
	 * 
	 * @param govvieBuilderSettings Govvie Builder Settings Instance
	 * @param correlatedPathVertexDimension Latent State Evolver CPVD Instance
	 * @param diffusionEvolver The Latent State Diffusion Evolver
	 * 
	 * @return Standard Instance of PathVertexGovvie
	 */

	public static final PathVertexGovvie Standard (
		final GovvieBuilderSettings govvieBuilderSettings,
		final CorrelatedPathVertexDimension correlatedPathVertexDimension,
		final DiffusionEvolver diffusionEvolver)
	{
		if (null == correlatedPathVertexDimension || null == diffusionEvolver) {
			return null;
		}

		int dimension = correlatedPathVertexDimension.numDimension();

		DiffusionEvolver[] diffusionEvolverArray = new DiffusionEvolver[dimension];

		for (int dimensionIndex = 0; dimensionIndex < dimension; ++dimensionIndex) {
			diffusionEvolverArray[dimensionIndex] = diffusionEvolver;
		}

		try {
			return new PathVertexGovvie (
				govvieBuilderSettings,
				correlatedPathVertexDimension,
				diffusionEvolverArray
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * PathVertexGovvie Constructor
	 * 
	 * @param govvieBuilderSettings Govvie Builder Settings
	 * @param correlatedPathVertexDimension Latent State Evolver CPVD Instance
	 * @param diffusionEvolverArray Array of the Latent State Diffusion Evolvers
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public PathVertexGovvie (
		final GovvieBuilderSettings govvieBuilderSettings,
		final CorrelatedPathVertexDimension correlatedPathVertexDimension,
		final DiffusionEvolver[] diffusionEvolverArray)
		throws Exception
	{
		super (correlatedPathVertexDimension, diffusionEvolverArray);

		if (null == (_govvieBuilderSettings = govvieBuilderSettings)) {
			throw new Exception ("PathVertexGovvie Constructor => Invalid Inputs");
		}
	}

	/**
	 * Generate the Govvie Builder Settings Instance
	 * 
	 * @return The Govvie Builder Settings Instance
	 */

	public GovvieBuilderSettings govvieBuilderSettings()
	{
		return _govvieBuilderSettings;
	}

	/**
	 * Generate the R<sup>d</sup> Path/Vertex Govvie Curves using the Initial R<sup>d</sup> and the Evolution
	 *  Time Increment Array
	 * 
	 * @param timeIncrementArray Array of the Evolution Time Widths
	 * 
	 * @return The R<sup>d</sup> Path//Vertex Govvie Curves
	 */

	public GovvieCurve[][] pathVertex (
		final double[] timeIncrementArray)
	{
		return curveVertex (pathVertex (_govvieBuilderSettings.groundForwardYield(), timeIncrementArray));
	}

	/**
	 * Generate the R<sup>d</sup> Path/Vertex Govvie Curves using the Initial R<sup>d</sup> and a Fixed
	 *  Evolution Time Width
	 * 
	 * @param timeIncrement The Evolution Time Widths
	 * 
	 * @return The R<sup>d</sup> Path//Vertex Govvie Curves
	 */

	public GovvieCurve[][] pathVertex (
		final double timeIncrement)
	{
		return curveVertex (pathVertex (_govvieBuilderSettings.groundForwardYield(), timeIncrement));
	}

	/**
	 * Generate the R<sup>d</sup> Path/Vertex Govvie Curves using the Initial R<sup>d</sup> and the Array of
	 *  Forward Evolution Tenors
	 * 
	 * @param forwardEvolutionTenorArray The Array of Forward Evolution Tenors
	 * 
	 * @return The R<sup>d</sup> Path/Vertex Govvie Curves
	 */

	public GovvieCurve[][] pathVertex (
		final String[] forwardEvolutionTenorArray)
	{
		return curveVertex (
			pathVertex (_govvieBuilderSettings.groundForwardYield(), forwardEvolutionTenorArray)
		);
	}

	/**
	 * Generate the R<sup>d</sup> Path/Vertex Govvie Curves using the Initial R<sup>d</sup> and the Array of
	 *  Forward Evolution Dates
	 * 
	 * @param forwardEventDateArray The Array of Forward Event Dates
	 * 
	 * @return The R<sup>d</sup> Path/Vertex Govvie Curves
	 */

	public GovvieCurve[][] pathVertex (
		final int[] forwardEventDateArray)
	{
		return curveVertex (
			pathVertex (
				_govvieBuilderSettings.groundForwardYield(),
				_govvieBuilderSettings.spot().julian(),
				forwardEventDateArray
			)
		);
	}
}
