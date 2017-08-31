
package org.drip.spaces.functionclass;

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
 * NormedR1ToL1R1Finite implements the Class f E F : Normed R^1 To L1 R^1 Spaces of Finite Functions.
 * 
 * The Reference we've used is:
 * 
 * 	- Carl, B., and I. Stephani (1990): Entropy, Compactness, and Approximation of Operators, Cambridge
 * 		University Press, Cambridge UK.
 *
 * @author Lakshmi Krishnamurthy
 */

public class NormedR1ToL1R1Finite extends org.drip.spaces.functionclass.NormedR1ToNormedR1Finite {

	/**
	 * Create Bounded R^1 To Bounded L1 R^1 Function Class for the specified Bounded Class of Finite
	 *  Functions
	 * 
	 * @param dblMaureyConstant Maurey Constant
	 * @param aR1ToR1 The Bounded R^1 To Bounded R^1 Function Set
	 * @param dblPredictorSupport The Set Predictor Support
	 * @param dblResponseBound The Set Response Bound
	 * 
	 * @return The Bounded R^1 To Bounded R^1 Function Class for the specified Function Set
	 */

	public static final NormedR1ToL1R1Finite BoundedPredictorBoundedResponse (
		final double dblMaureyConstant,
		final org.drip.function.definition.R1ToR1[] aR1ToR1,
		final double dblPredictorSupport,
		final double dblResponseBound)
	{
		if (null == aR1ToR1) return null;

		int iNumFunction = aR1ToR1.length;
		org.drip.spaces.rxtor1.NormedR1ToNormedR1[] aR1ToR1FunctionSpace = new
			org.drip.spaces.rxtor1.NormedR1ToNormedR1[iNumFunction];

		if (0 == iNumFunction) return null;

		try {
			org.drip.spaces.metric.R1Continuous r1ContinuousInput = new org.drip.spaces.metric.R1Continuous
				(-0.5 * dblPredictorSupport, 0.5 * dblPredictorSupport, null, 1);

			org.drip.spaces.metric.R1Continuous r1ContinuousOutput = new org.drip.spaces.metric.R1Continuous
				(-0.5 * dblResponseBound, 0.5 * dblResponseBound, null, 1);

			for (int i = 0; i < iNumFunction; ++i)
				aR1ToR1FunctionSpace[i] = new org.drip.spaces.rxtor1.NormedR1ContinuousToR1Continuous
					(r1ContinuousInput, r1ContinuousOutput, aR1ToR1[i]);

			return new NormedR1ToL1R1Finite (dblMaureyConstant, aR1ToR1FunctionSpace);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	protected NormedR1ToL1R1Finite (
		final double dblMaureyConstant,
		final org.drip.spaces.rxtor1.NormedR1ToNormedR1[] aR1ToR1FunctionSpace)
		throws java.lang.Exception
	{
		super (dblMaureyConstant, aR1ToR1FunctionSpace);
	}

	@Override public org.drip.spaces.cover.FunctionClassCoveringBounds agnosticCoveringNumberBounds()
	{
		org.drip.spaces.rxtor1.NormedR1ToNormedR1[] aNormedR1ToNormedR1 =
			(org.drip.spaces.rxtor1.NormedR1ToNormedR1[]) functionSpaces();

		int iNumFunction = aNormedR1ToNormedR1.length;
		double dblResponseLowerBound = java.lang.Double.NaN;
		double dblResponseUpperBound = java.lang.Double.NaN;
		double dblPredictorLowerBound = java.lang.Double.NaN;
		double dblPredictorUpperBound = java.lang.Double.NaN;

		for (int i = 0; i < iNumFunction; ++i) {
			org.drip.spaces.rxtor1.NormedR1ToNormedR1 r1Tor1 = aNormedR1ToNormedR1[i];

			org.drip.spaces.metric.R1Normed runsInput = r1Tor1.inputMetricVectorSpace();

			org.drip.spaces.metric.R1Normed runsOutput = r1Tor1.outputMetricVectorSpace();

			if (!runsInput.isPredictorBounded() || !runsOutput.isPredictorBounded()) return null;

			double dblResponseLeftBound = runsOutput.leftEdge();

			double dblPredictorLeftBound = runsInput.leftEdge();

			double dblResponseRightBound = runsOutput.rightEdge();

			double dblPredictorRightBound = runsInput.rightEdge();

			if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorLowerBound))
				dblPredictorLowerBound = dblPredictorLeftBound;
			else {
				if (dblPredictorLowerBound > dblPredictorLeftBound)
					dblPredictorLowerBound = dblPredictorLeftBound;
			}

			if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorUpperBound))
				dblPredictorUpperBound = dblPredictorRightBound;
			else {
				if (dblPredictorUpperBound < dblPredictorRightBound)
					dblPredictorUpperBound = dblPredictorRightBound;
			}

			if (!org.drip.quant.common.NumberUtil.IsValid (dblResponseLowerBound))
				dblResponseLowerBound = dblResponseLeftBound;
			else {
				if (dblResponseLowerBound > dblResponseLeftBound)
					dblResponseLowerBound = dblResponseLeftBound;
			}

			if (!org.drip.quant.common.NumberUtil.IsValid (dblResponseUpperBound))
				dblResponseUpperBound = dblResponseRightBound;
			else {
				if (dblResponseUpperBound < dblResponseRightBound)
					dblResponseUpperBound = dblResponseRightBound;
			}
		}

		double dblVariation = dblResponseUpperBound - dblResponseLowerBound;

		try {
			return new org.drip.spaces.cover.L1R1CoveringBounds (dblPredictorUpperBound -
				dblPredictorLowerBound, dblVariation, dblVariation);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
