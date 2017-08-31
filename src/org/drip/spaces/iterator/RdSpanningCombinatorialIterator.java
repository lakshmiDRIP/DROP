
package org.drip.spaces.iterator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * RdSpanningCombinatorialIterator contains the Functionality to conduct a Spanning Iteration through an R^d
 *  Combinatorial Space.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RdSpanningCombinatorialIterator extends
	org.drip.spaces.iterator.RdExhaustiveStateSpaceScan {
	private org.drip.spaces.tensor.R1CombinatorialVector[] _aR1CV = null;

	/**
	 * Retrieve the RdSpanningCombinatorialIterator Instance associated with the Underlying Vector Space
	 * 
	 * @param aR1CV Array of R^1 Combinatorial Vectors
	 * 
	 * @return The RdSpanningCombinatorialIterator Instance associated with the Underlying Vector Space
	 */

	public static final RdSpanningCombinatorialIterator Standard (
		final org.drip.spaces.tensor.R1CombinatorialVector[] aR1CV)
	{
		if (null == aR1CV) return null;

		int iDimension = aR1CV.length;
		int[] aiMax = new int[iDimension];

		if (0 == iDimension) return null;

		for (int i = 0; i < iDimension; ++i)
			aiMax[i] = (int) aR1CV[i].cardinality().number();

		try {
			return new RdSpanningCombinatorialIterator (aR1CV, aiMax);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RdSpanningCombinatorialIterator Constructor
	 * 
	 * @param aR1CV Array of the R^1 Combinatorial Vectors
	 * @param aiMax The Array of Dimension Maximum
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RdSpanningCombinatorialIterator (
		final org.drip.spaces.tensor.R1CombinatorialVector[] aR1CV,
		final int[] aiMax)
		throws java.lang.Exception
	{
		super (aiMax, false);

		if (null == (_aR1CV = aR1CV) || _aR1CV.length != aiMax.length)
			throw new java.lang.Exception ("RdCombinatorialIterator ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Array of the R^1 Combinatorial Vectors
	 * 
	 * @return The Array of the R^1 Combinatorial Vectors
	 */

	public org.drip.spaces.tensor.R1CombinatorialVector[] r1()
	{
		return _aR1CV;
	}

	/**
	 * Convert the Vector Space Index Array to the Variate Array
	 * 
	 * @param aiIndex Vector Space Index Array
	 * 
	 * @return Variate Array
	 */

	public double[] vectorSpaceIndexToVariate (
		final int[] aiIndex)
	{
		if (null == aiIndex) return null;

		int iDimension = _aR1CV.length;
		double[] adblVariate = new double[iDimension];

		if (iDimension != aiIndex.length) return null;

		for (int i = 0; i < iDimension; ++i)
			adblVariate[i] = _aR1CV[i].elementSpace().get (aiIndex[i]);

		return adblVariate;
	}

	/**
	 * Retrieve the Cursor Variate Array
	 * 
	 * @return The Cursor Variate Array
	 */

	public double[] cursorVariates()
	{
		return vectorSpaceIndexToVariate (stateIndexCursor());
	}

	/**
	 * Retrieve the Subsequent Variate Array
	 * 
	 * @return The Subsequent Variate Array
	 */

	public double[] nextVariates()
	{
		return vectorSpaceIndexToVariate (nextStateIndexCursor());
	}
}
