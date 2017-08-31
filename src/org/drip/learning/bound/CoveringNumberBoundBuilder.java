
package org.drip.learning.bound;

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
 * CoveringNumberBoundBuilder constructs the CoveringNumberProbabilityBound Instances for specific Learning
 *  Situations.
 *  
 *  The References are:
 *  
 *  1) Alon, N., S. Ben-David, N. Cesa Bianchi, and D. Haussler (1997): Scale-sensitive Dimensions, Uniform
 *  	Convergence, and Learnability, Journal of Association of Computational Machinery, 44 (4) 615-631.
 * 
 *  2) Anthony, M., and P. L. Bartlett (1999): Artificial Neural Network Learning - Theoretical Foundations,
 *  	Cambridge University Press, Cambridge, UK.
 *  
 *  3) Kearns, M. J., R. E. Schapire, and L. M. Sellie (1994): Towards Efficient Agnostic Learning, Machine
 *  	Learning, 17 (2) 115-141.
 *  
 *  4) Lee, W. S., P. L. Bartlett, and R. C. Williamson (1998): The Importance of Convexity in Learning with
 *  	Squared Loss, IEEE Transactions on Information Theory, 44 1974-1980.
 * 
 *  5) Vapnik, V. N. (1998): Statistical learning Theory, Wiley, New York.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CoveringNumberBoundBuilder {

	/**
	 * Epsilon Exponent for Regression Learning
	 */

	public static final double EPSILON_EXPONENT_REGRESSION_LEARNING = 1.;

	/**
	 * Epsilon Exponent for Agnostic Learning
	 */

	public static final double EPSILON_EXPONENT_AGNOSTIC_LEARNING = 2.;

	/**
	 * Epsilon Exponent for Agnostic Learning with Convex Functions
	 */

	public static final double EPSILON_EXPONENT_AGNOSTIC_CONVEX_LEARNING = 1.;

	/**
	 * Construct the Regression Learning CoveringNumberProbabilityBound Instance
	 * 
	 * @param funcSampleCoefficient The Sample Coefficient Function
	 * @param dblExponentScaler The Exponent Scaler
	 * 
	 * @return The Regression Learning CoveringNumberProbabilityBound Instance
	 */

	public static final org.drip.learning.bound.CoveringNumberLossBound
		RegressionLearning (
			final org.drip.function.definition.R1ToR1 funcSampleCoefficient,
			final double dblExponentScaler)
	{
		try {
			return new org.drip.learning.bound.CoveringNumberLossBound (funcSampleCoefficient,
				EPSILON_EXPONENT_REGRESSION_LEARNING, dblExponentScaler);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Agnostic Learning CoveringNumberProbabilityBound Instance
	 * 
	 * @param funcSampleCoefficient The Sample Coefficient Function
	 * @param dblExponentScaler The Exponent Scaler
	 * 
	 * @return The Agnostic Learning CoveringNumberProbabilityBound Instance
	 */

	public static final org.drip.learning.bound.CoveringNumberLossBound
		AgnosticLearning (
			final org.drip.function.definition.R1ToR1 funcSampleCoefficient,
			final double dblExponentScaler)
	{
		try {
			return new org.drip.learning.bound.CoveringNumberLossBound (funcSampleCoefficient,
				EPSILON_EXPONENT_AGNOSTIC_LEARNING, dblExponentScaler);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Agnostic Convex Learning CoveringNumberProbabilityBound Instance
	 * 
	 * @param funcSampleCoefficient The Sample Coefficient Function
	 * @param dblExponentScaler The Exponent Scaler
	 * 
	 * @return The Agnostic Convex Learning CoveringNumberProbabilityBound Instance
	 */

	public static final org.drip.learning.bound.CoveringNumberLossBound
		AgnosticConvexLearning (
			final org.drip.function.definition.R1ToR1 funcSampleCoefficient,
			final double dblExponentScaler)
	{
		try {
			return new org.drip.learning.bound.CoveringNumberLossBound (funcSampleCoefficient,
				EPSILON_EXPONENT_AGNOSTIC_CONVEX_LEARNING, dblExponentScaler);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
