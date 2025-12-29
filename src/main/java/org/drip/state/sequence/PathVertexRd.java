
package org.drip.state.sequence;

import java.util.List;

import org.drip.analytics.daycount.ActActDCParams;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.support.Helper;
import org.drip.measure.discontinuous.CorrelatedFactorsPathVertexRealization;
import org.drip.measure.discontinuous.VertexRd;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.JumpDiffusionEdgeUnit;
import org.drip.measure.realization.JumpDiffusionVertex;
import org.drip.numerical.common.NumberUtil;

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
 * <i>PathVertexRd</i> exposes the Functionality to generate a Sequence of the Path Vertex Latent State
 * R<sup>d</sup> Realizations across Multiple Paths. It exposes the following functionality:
 * 
 *  <br><br>
 *  <ul>
 *		<li>PathVertexRd Constructor</li>
 *		<li>Retrieve the Latent State Dimension</li>
 *		<li>Retrieve the Latent State Evolver CorrelatedPathVertexDimension Instance</li>
 *		<li>Retrieve the Array of the Latent State Diffusion Evolvers</li>
 *		<li>Generate the R<sup>d</sup> Path Vertex Realizations using the Initial R<sup>d</sup> and the Evolution Time Increment Array</li>
 *		<li>Generate the R<sup>d</sup> Path Vertex Realizations using the Initial R<sup>d</sup> and a Fixed Evolution Time Width</li>
 *		<li>Generate the R<sup>d</sup> Path Vertex Realizations using the Initial R<sup>d</sup> and the Array of Evolution Tenors</li>
 *		<li>Generate the R<sup>d</sup> Path Vertex Realizations using the Initial R<sup>d</sup>, Spot Date, and the Array of Event Dates</li>
 *		<li>Generate a Standard Instance of PathVertexRd</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/sequence/README.md">Monte Carlo Path State Realizations</a></li>
 *  </ul>
 *  <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PathVertexRd
{
	private DiffusionEvolver[] _diffusionEvolverArray = null;
	private CorrelatedFactorsPathVertexRealization _correlatedPathVertexDimension = null;

	/**
	 * Generate a Standard Instance of PathVertexRd
	 * 
	 * @param cpvd Latent State Evolver CPVD Instance
	 * @param de The Latent State Diffusion Evolver
	 * 
	 * @return Standard Instance of PathVertexRd
	 */

	public static final PathVertexRd Standard (
		final org.drip.measure.discontinuous.CorrelatedFactorsPathVertexRealization cpvd,
		final org.drip.measure.process.DiffusionEvolver de)
	{
		if (null == cpvd || null == de) return null;

		int iNumDimension = cpvd.dimensionCount();

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
	 * @param correlatedPathVertexDimension Latent State Evolver CPVD Instance
	 * @param diffusionEvolverArray Array of the Latent State Diffusion Evolvers
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public PathVertexRd (
		final CorrelatedFactorsPathVertexRealization correlatedPathVertexDimension,
		final DiffusionEvolver[] diffusionEvolverArray)
		throws Exception
	{
		if (null == (_correlatedPathVertexDimension = correlatedPathVertexDimension) ||
			null == (_diffusionEvolverArray = diffusionEvolverArray)) {
			throw new Exception ("PathVertexRd Constructor => Invalid Inputs");
		}

		int dimension = _diffusionEvolverArray.length;

		if (dimension != _correlatedPathVertexDimension.dimensionCount()) {
			throw new Exception ("PathVertexRd Constructor => Invalid Inputs");
		}

		for (int dimensionIndex = 0; dimensionIndex < dimension; ++dimensionIndex) {
			if (null == _diffusionEvolverArray[dimensionIndex]) {
				throw new Exception ("PathVertexRd Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Latent State Dimension
	 * 
	 * @return The Latent State Dimension
	 */

	public int dimension()
	{
		return _diffusionEvolverArray.length;
	}

	/**
	 * Retrieve the Latent State Evolver CPVD Instance
	 * 
	 * @return The Latent State Evolver CPVD Instance
	 */

	public CorrelatedFactorsPathVertexRealization cpvd()
	{
		return _correlatedPathVertexDimension;
	}

	/**
	 * Retrieve the Array of the Latent State Diffusion Evolvers
	 * 
	 * @return The Array of the Latent State Diffusion Evolvers
	 */

	public DiffusionEvolver[] evolver()
	{
		return _diffusionEvolverArray;
	}

	/**
	 * Generate the R<sup>d</sup> Path Vertex Realizations using the Initial R<sup>d</sup> and the Evolution
	 *  Time Increment Array
	 * 
	 * @param startingRd The Starting R<sup>d</sup> Value
	 * @param timeIncrementArray The Array of Evolution Time Width Increments
	 * 
	 * @return The R<sup>d</sup> Path Vertex Realizations
	 */

	public double[][][] pathVertex (
		final double[] startingRd,
		final double[] timeIncrementArray)
	{
		if (null == startingRd || null == timeIncrementArray) {
			return null;
		}

		int pathCount = _correlatedPathVertexDimension.simulationCount();

		int dimension = dimension();

		int vertexCount = _correlatedPathVertexDimension.nodeCount();

		if (dimension != startingRd.length || vertexCount != timeIncrementArray.length) {
			return null;
		}

		double[][][] forwardPathVertexDimension = new double[pathCount][vertexCount][dimension];

		VertexRd[] vertexRdArray = _correlatedPathVertexDimension.multiPathVertexRd();

		if (null == vertexRdArray || pathCount != vertexRdArray.length) {
			return null;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex) {
			if (null == vertexRdArray[pathIndex]) {
				return null;
			}

			List<double[]> vertexRdList = vertexRdArray[pathIndex].nodeList();

			JumpDiffusionEdgeUnit[][] jumpDiffusionEdgeUnitGrid =
				new JumpDiffusionEdgeUnit[dimension][vertexCount];
			JumpDiffusionVertex[][] jumpDiffusionVertexGrid =
				new JumpDiffusionVertex[dimension][vertexCount + 1];

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				double[] rdArray = vertexRdList.get (vertexIndex);

				if (null == rdArray || dimension != rdArray.length) {
					return null;
				}

				for (int dimensionIndex = 0; dimensionIndex < dimension; ++dimensionIndex) {
					try {
						jumpDiffusionEdgeUnitGrid[dimensionIndex][vertexIndex] = new JumpDiffusionEdgeUnit (
							timeIncrementArray[vertexIndex],
							rdArray[dimensionIndex],
							0.
						);
					} catch (Exception e) {
						e.printStackTrace();

						return null;
					}
				}
			}

			for (int dimensionIndex = 0; dimensionIndex < dimension; ++dimensionIndex) {
				try {
					jumpDiffusionVertexGrid[dimensionIndex] =
						_diffusionEvolverArray[dimensionIndex].vertexSequence (
							new JumpDiffusionVertex (0., startingRd[dimensionIndex], 0., false),
							jumpDiffusionEdgeUnitGrid[dimensionIndex],
							timeIncrementArray
						);
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			}

			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				for (int dimensionIndex = 0; dimensionIndex < dimension; ++dimensionIndex) {
					forwardPathVertexDimension[pathIndex][vertexIndex][dimensionIndex] =
						jumpDiffusionVertexGrid[dimensionIndex][vertexIndex].value();
				}
			}
		}

		return forwardPathVertexDimension;
	}

	/**
	 * Generate the R<sup>d</sup> Path Vertex Realizations using the Initial R<sup>d</sup> and a Fixed
	 *  Evolution Time Width
	 * 
	 * @param startingRd The Starting R<sup>d</sup> Value
	 * @param timeIncrement The Evolution Time Width
	 * 
	 * @return The R<sup>d</sup> Path Vertex Realizations
	 */

	public double[][][] pathVertex (
		final double[] startingRd,
		final double timeIncrement)
	{
		if (!NumberUtil.IsValid (timeIncrement)) {
			return null;
		}

		int timeVertexCount = _correlatedPathVertexDimension.nodeCount();

		double[] timeIncrementArray = new double[timeVertexCount];

		for (int timeVertexIndex = 0; timeVertexIndex < timeVertexCount; ++timeVertexIndex) {
			timeIncrementArray[timeVertexIndex] = timeIncrement;
		}

		return pathVertex (startingRd, timeIncrementArray);
	}

	/**
	 * Generate the R<sup>d</sup> Path Vertex Realizations using the Initial R<sup>d</sup> and the Array of
	 *  Evolution Tenors
	 * 
	 * @param startingRd The Starting R<sup>d</sup> Value
	 * @param evolutionTenorArray The Array of Evolution Tenors
	 * 
	 * @return The R<sup>d</sup> Path Vertex Realizations
	 */

	public double[][][] pathVertex (
		final double[] startingRd,
		final String[] evolutionTenorArray)
	{
		if (null == evolutionTenorArray) {
			return null;
		}

		int timeVertexCount = _correlatedPathVertexDimension.nodeCount();

		if (timeVertexCount != evolutionTenorArray.length) {
			return null;
		}

		double[] timeIncrementArray = new double[timeVertexCount];

		for (int timeVertexIndex = 0; timeVertexIndex < timeVertexCount; ++timeVertexIndex) {
			try {
				timeIncrementArray[timeVertexIndex] = Helper.TenorToYearFraction (
					evolutionTenorArray[timeVertexIndex]
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return pathVertex (startingRd, timeIncrementArray);
	}

	/**
	 * Generate the R<sup>d</sup> Path Vertex Realizations using the Initial R<sup>d</sup>, Spot Date, and
	 *  the Array of Event Dates
	 * 
	 * @param startingRd The Starting R<sup>d</sup> Value
	 * @param spotDate The Spot Date
	 * @param eventDateArray The Array of Event Dates
	 * 
	 * @return The R<sup>d</sup> Path Vertex Realizations
	 */

	public double[][][] pathVertex (
		final double[] startingRd,
		final int spotDate,
		final int[] eventDateArray)
	{
		if (null == eventDateArray) {
			return null;
		}

		int timeVertexCount = _correlatedPathVertexDimension.nodeCount();

		if (timeVertexCount != eventDateArray.length) return null;

		double[] timeIncrementArray = new double[timeVertexCount];

		ActActDCParams actActDCParams = ActActDCParams.FromFrequency (1);

		for (int timeVertexIndex = 0; timeVertexIndex < timeVertexCount; ++timeVertexIndex) {
			try {
				timeIncrementArray[timeVertexIndex] = Convention.YearFraction (
					spotDate,
					eventDateArray[timeVertexIndex],
					"Act/Act ISDA",
					false,
					actActDCParams,
					""
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return pathVertex (startingRd, timeIncrementArray);
	}
}
