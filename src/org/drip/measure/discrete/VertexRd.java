
package org.drip.measure.discrete;

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
 * VertexRd holds the R^d Realizations at the Individual Vertexes.
 *
 * @author Lakshmi Krishnamurthy
 */

public class VertexRd {
	private java.util.List<double[]> _lsVertexRd = new java.util.ArrayList<double[]>();

	/**
	 * Construct a VertexRd Instance from the R^d Sequence
	 * 
	 * @param aadblSequence The R^d Sequence
	 * 
	 * @return The VertexRd Instance
	 */

	public static final VertexRd FromFlatForm (
		final double[][] aadblSequence)
	{
		if (null == aadblSequence) return null;

		int iSequenceSize = aadblSequence.length;

		if (0 == iSequenceSize) return null;

		VertexRd vertexRd = new VertexRd();

		for (int iSequence = 0; iSequence < iSequenceSize; ++iSequence) {
			if (null == aadblSequence[iSequence] || !vertexRd.add (iSequence, aadblSequence[iSequence]))
				return null;
		}

		return vertexRd;
	}

	/**
	 * Empty VertexRd Constructor
	 */

	public VertexRd()
	{
	}

	/**
	 * Retrieve the Vertex R^d List
	 * 
	 * @return The Vertex R^d List
	 */

	public java.util.List<double[]> vertexList()
	{
		return _lsVertexRd;
	}

	/**
	 * Add the Vertex Index and its corresponding Realization
	 * 
	 * @param iVertex The Vertex Index
	 * @param adblRealization The R^d Realization Array
	 * 
	 * @return TRUE - The Vertex Index/Realization successfully added
	 */

	public boolean add (
		final int iVertex,
		final double[] adblRealization)
	{
		if (-1 >= iVertex || null == adblRealization || 0 == adblRealization.length ||
			!org.drip.quant.common.NumberUtil.IsValid (adblRealization))
			return false;

		_lsVertexRd.add (iVertex, adblRealization);

		return true;
	}

	/**
	 * Retrieve the Vertex Realization given the Vertex Index
	 * 
	 * @param iVertex The Vertex Index
	 * 
	 * @return Array of Vertex Realizations
	 */

	public double[] vertexRealization (
		final int iVertex)
	{
		return -1 >= iVertex ? null : _lsVertexRd.get (iVertex);
	}

	/**
	 * Flatten out into a 2D Array
	 * 
	 * @return The 2D Array of the VertexRd Realizations
	 */

	public double[][] flatform()
	{
		int iSize = _lsVertexRd.size();

		if (0 == iSize) return null;

		double[][] aadblSequence = new double[iSize][];

		for (int i = 0; i < iSize; ++i)
			aadblSequence[i] = _lsVertexRd.get (i);

		return aadblSequence;
	}
}
