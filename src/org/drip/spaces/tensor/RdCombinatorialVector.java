
package org.drip.spaces.tensor;

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
 * RdCombinatorialVector exposes the Normed/Non-normed Discrete Spaces with R^d Combinatorial Vector
 *  Elements.
 *
 * @author Lakshmi Krishnamurthy
 */

public class RdCombinatorialVector extends org.drip.spaces.tensor.RdAggregate {

	/**
	 * RdCombinatorialVector Constructor
	 * 
	 * @param aR1CV Array of the Underlying R^1 Combinatorial Vector Spaces
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RdCombinatorialVector (
		final org.drip.spaces.tensor.R1CombinatorialVector[] aR1CV)
		throws java.lang.Exception
	{
		super (aR1CV);
	}

	@Override public org.drip.spaces.tensor.Cardinality cardinality()
	{
		org.drip.spaces.tensor.R1GeneralizedVector[] aR1GV = vectorSpaces();

		int iDimension = aR1GV.length;
		double dblCardinalNumber = 1.;

		for (int i = 0; i < iDimension; ++i)
			dblCardinalNumber *= ((org.drip.spaces.tensor.R1CombinatorialVector)
				aR1GV[i]).cardinality().number();

		return org.drip.spaces.tensor.Cardinality.CountablyFinite (dblCardinalNumber);
	}

	/**
	 * Retrieve the Multidimensional Iterator associated with the Underlying Vector Space
	 * 
	 * @return The Multidimensional Iterator associated with the Underlying Vector Space
	 */

	public org.drip.spaces.iterator.RdSpanningCombinatorialIterator iterator()
	{
		org.drip.spaces.tensor.R1GeneralizedVector[] aR1GV = vectorSpaces();

		int iDimension = aR1GV.length;
		org.drip.spaces.tensor.R1CombinatorialVector[] aR1CV = new
			org.drip.spaces.tensor.R1CombinatorialVector[iDimension];

		for (int i = 0; i < iDimension; ++i)
			aR1CV[i] = (org.drip.spaces.tensor.R1CombinatorialVector) aR1GV[i];

		return org.drip.spaces.iterator.RdSpanningCombinatorialIterator.Standard (aR1CV);
	}

	@Override public double[] leftDimensionEdge()
	{
		org.drip.spaces.tensor.R1GeneralizedVector[] aR1GV = vectorSpaces();

		int iDimension = aR1GV.length;
		double[] adblLeftEdge = new double[iDimension];

		for (int i = 0; i < iDimension; ++i)
			adblLeftEdge[i] = ((org.drip.spaces.tensor.R1ContinuousVector) aR1GV[i]).leftEdge();

		return adblLeftEdge;
	}

	@Override public double[] rightDimensionEdge()
	{
		org.drip.spaces.tensor.R1GeneralizedVector[] aR1GV = vectorSpaces();

		int iDimension = aR1GV.length;
		double[] adblRightEdge = new double[iDimension];

		for (int i = 0; i < iDimension; ++i)
			adblRightEdge[i] = ((org.drip.spaces.tensor.R1ContinuousVector) aR1GV[i]).rightEdge();

		return adblRightEdge;
	}

	@Override public double leftEdge()
	{
		double[] adblLeftEdge = leftDimensionEdge();

		int iDimension = adblLeftEdge.length;
		double dblLeftEdge = adblLeftEdge[0];

		for (int i = 1; i < iDimension; ++i) {
			if (dblLeftEdge > adblLeftEdge[i]) dblLeftEdge = adblLeftEdge[i];
		}

		return dblLeftEdge;
	}

	@Override public double rightEdge()
	{
		double[] adblRightEdge = rightDimensionEdge();

		int iDimension = adblRightEdge.length;
		double dblRightEdge = adblRightEdge[0];

		for (int i = 1; i < iDimension; ++i) {
			if (dblRightEdge < adblRightEdge[i]) dblRightEdge = adblRightEdge[i];
		}

		return dblRightEdge;
	}

	@Override public double hyperVolume()
		throws java.lang.Exception
	{
		if (!isPredictorBounded())
			throw new java.lang.Exception ("RdCombinatorialVector::hyperVolume => Space not Bounded");

		double[] adblLeftEdge = leftDimensionEdge();

		double dblHyperVolume = 1.;
		int iDimension = adblLeftEdge.length;

		double[] adblRightEdge = rightDimensionEdge();

		for (int i = 0; i < iDimension; ++i)
			dblHyperVolume *= (adblRightEdge[i] - adblLeftEdge[i]);

		return dblHyperVolume;
	}
}
