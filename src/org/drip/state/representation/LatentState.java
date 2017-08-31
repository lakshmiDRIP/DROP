
package org.drip.state.representation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * LatentState exposes the functionality to manipulate the hidden Variable's Latent State. Specifically it
 * 	exports functions to:
 * 	- Produce node shifted, parallel shifted, and custom manifest-measure tweaked variants of the Latent
 * 		State
 * 	- Produce parallel shifted and custom quantification metric tweaked variants of the Latent State
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
