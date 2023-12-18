
package org.drip.state.representation;

import org.drip.state.identifier.LatentStateLabel;

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
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>LatentStateSpecification</i> holds the fields necessary to specify a complete Latent State. It includes
 * the Latent State Type, the Latent State Label, and the Latent State Quantification metric. It exposes the
 * following functionality:
 * 
 *  <br><br>
 *  <ul>
 *		<li>LatentStateSpecification Constructor</li>
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
 *  <br><br><br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/representation/README.md">Latent State Merge Sub-stretch</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateSpecification
{
	private String _latentState = "";
	private LatentStateLabel _label = null;
	private String _quantificationMetric = "";

	/**
	 * LatentStateSpecification constructor
	 * 
	 * @param latentState The Latent State
	 * @param quantificationMetric The Latent State Quantification Metric
	 * @param label The Specific Latent State Label
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	public LatentStateSpecification (
		final String latentState,
		final String quantificationMetric,
		final org.drip.state.identifier.LatentStateLabel label)
		throws Exception
	{
		if (null == (_latentState = latentState) || _latentState.isEmpty() ||
			null == (_quantificationMetric = quantificationMetric) ||
				_quantificationMetric.isEmpty() ||
			null == (_label = label)) {
			throw new Exception ("LatentStateSpecification ctr: Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Latent State
	 * 
	 * @return The Latent State
	 */

	public String latentState()
	{
		return _latentState;
	}

	/**
	 * Retrieve the Latent State Label
	 * 
	 * @return The Latent State Label
	 */

	public org.drip.state.identifier.LatentStateLabel label()
	{
		return _label;
	}

	/**
	 * Retrieve the Latent State Quantification Metric
	 * 
	 * @return The Latent State Quantification Metric
	 */

	public java.lang.String latentStateQuantificationMetric()
	{
		return _quantificationMetric;
	}

	/**
	 * Does the Specified Latent State Specification Instance match the current one?
	 * 
	 * @param other The "Other" Latent State Specification Instance
	 * 
	 * @return TRUE - Matches the Specified Latent State Specification Instance
	 */

	public boolean match (
		final LatentStateSpecification other)
	{
		return null != other && _latentState.equalsIgnoreCase (other.latentState()) &&
			_quantificationMetric.equalsIgnoreCase (other.latentStateQuantificationMetric()) &&
			_label.match (other.label());
	}

	/**
	 * Display the Latent State Details
	 * 
	 * @param strComment The Comment Prefix
	 */

	public void displayString (
		final java.lang.String strComment)
	{
		System.out.println ("\t[LatentStateSpecification]: " + _latentState + " | " +
			_quantificationMetric + " | " + _label.fullyQualifiedName());
	}
}
