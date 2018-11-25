
package org.drip.state.representation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/representation">Representation</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public interface LatentState {

	/**
	 * Create a LatentState Instance from the Manifest Measure Parallel Shift
	 * 
	 * @param strManifestMeasure The Specified Manifest Measure
	 * @param dblShift Parallel shift of the Manifest Measure
	 * 
	 * @return New LatentState Instance corresponding to the Parallel Shifted Manifest Measure
	 */

	public abstract LatentState parallelShiftManifestMeasure (
		final java.lang.String strManifestMeasure,
		final double dblShift);

	/**
	 * Create a LatentState Instance from the Shift of the Specified Manifest Measure
	 * 
	 * @param iSpanIndex Index into the Span that identifies the Instrument
	 * @param strManifestMeasure The Specified Manifest Measure
	 * @param dblShift Shift of the Manifest Measure
	 * 
	 * @return New LatentState Instance corresponding to the Shift of the Specified Manifest Measure
	 */

	public abstract LatentState shiftManifestMeasure (
		final int iSpanIndex,
		final java.lang.String strManifestMeasure,
		final double dblShift);

	/**
	 * Create a LatentState Instance from the Manifest Measure Tweak Parameters
	 * 
	 * @param strManifestMeasure The Specified Manifest Measure
	 * @param rvtp Manifest Measure Tweak Parameters
	 * 
	 * @return New LatentState Instance corresponding to the Tweaked Manifest Measure
	 */

	public abstract LatentState customTweakManifestMeasure (
		final java.lang.String strManifestMeasure,
		final org.drip.param.definition.ManifestMeasureTweak rvtp);

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
