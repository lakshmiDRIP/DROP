
package org.drip.state.representation;

import org.drip.param.definition.ManifestMeasureTweak;

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
 * <i>LatentState</i> exposes the functionality to manipulate the hidden Variable's Latent State.
 * Specifically it exports functions to:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 * 			Produce node shifted, parallel shifted, and custom manifest-measure tweaked variants of the
 * 				Latent State
 *  	</li>
 *  	<li>
 * 			Produce parallel shifted and custom quantification metric tweaked variants of the Latent State
 *  	</li>
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

public interface LatentState
{

	/**
	 * Create a LatentState Instance from the Manifest Measure Parallel Shift
	 * 
	 * @param manifestMeasure The Specified Manifest Measure
	 * @param shift Parallel shift of the Manifest Measure
	 * 
	 * @return New LatentState Instance corresponding to the Parallel Shifted Manifest Measure
	 */

	public abstract LatentState parallelShiftManifestMeasure (
		final String manifestMeasure,
		final double shift
	);

	/**
	 * Create a LatentState Instance from the Shift of the Specified Manifest Measure
	 * 
	 * @param spanIndex Index into the Span that identifies the Instrument
	 * @param manifestMeasure The Specified Manifest Measure
	 * @param shift Shift of the Manifest Measure
	 * 
	 * @return New LatentState Instance corresponding to the Shift of the Specified Manifest Measure
	 */

	public abstract LatentState shiftManifestMeasure (
		final int spanIndex,
		final String manifestMeasure,
		final double shift
	);

	/**
	 * Create a LatentState Instance from the Manifest Measure Tweak Parameters
	 * 
	 * @param manifestMeasure The Specified Manifest Measure
	 * @param manifestMeasureTweak Manifest Measure Tweak Parameters
	 * 
	 * @return New LatentState Instance corresponding to the Tweaked Manifest Measure
	 */

	public abstract LatentState customTweakManifestMeasure (
		final String manifestMeasure,
		final ManifestMeasureTweak manifestMeasureTweak
	);

	/**
	 * Create a LatentState Instance from the Quantification Metric Parallel Shift
	 * 
	 * @param dblShift Parallel shift of the Quantification Metric
	 * 
	 * @return New LatentState Instance corresponding to the Parallel Shifted Quantification Metric
	 */

	public abstract LatentState parallelShiftQuantificationMetric (
		final double dblShift);

	/**
	 * Create a LatentState Instance from the Quantification Metric Tweak Parameters
	 * 
	 * @param rvtp Quantification Metric Tweak Parameters
	 * 
	 * @return New LatentState Instance corresponding to the Tweaked Quantification Metric
	 */

	public abstract LatentState customTweakQuantificationMetric (
		final org.drip.param.definition.ManifestMeasureTweak rvtp);
}
