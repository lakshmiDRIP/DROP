
package org.drip.spaces.cover;

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
 * CoveringBoundsHelper contains the assortment of Utilities used in the Computation of Upper Bounds for
 * 	Normed Single Function Spaces and Function Space Products.
 * 
 *  The References are:
 *  
 *  1) Carl, B. (1985): Inequalities of the Bernstein-Jackson type and the Degree of Compactness of Operators
 *  	in Banach Spaces, Annals of the Fourier Institute 35 (3) 79-118.
 *  
 *  2) Carl, B., and I. Stephani (1990): Entropy, Compactness, and the Approximation of Operators, Cambridge
 *  	University Press, Cambridge UK. 
 *  
 *  3) Williamson, R. C., A. J. Smola, and B. Scholkopf (2000): Entropy Numbers of Linear Function Classes,
 *  	in: Proceedings of the 13th Annual Conference on Computational Learning Theory, ACM New York.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CoveringBoundsHelper {

	/**
	 * Compute the Dyadic Entropy Number from the nth Entropy Number
	 * 
	 * @param dblLogNEntropyNumber Log of the nth Entropy Number
	 * 
	 * @return The Dyadic Entropy Number
	 * 
	 * @throws java.lang.Exception Thrown if the Dyadic Entropy Number cannot be calculated
	 */

	public static final double DyadicEntropyNumber (
		final double dblLogNEntropyNumber)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblLogNEntropyNumber))
			throw new java.lang.Exception ("CoveringBoundsHelper::DyadicEntropyNumber => Invalid Inputs");

		return 1. + (dblLogNEntropyNumber / java.lang.Math.log (2.));
	}

	/**
	 * Compute the Upper Bound for the Entropy Number of the Operator Custom Covering Number Metric Product
	 *  across both the Function Classes
	 * 
	 * @param mocbA The Maurey Operator Covering Bounds for Class A
	 * @param mocbB The Maurey Operator Covering Bounds for Class B
	 * @param iEntropyNumberIndexA Entropy Number Index for Class A
	 * @param iEntropyNumberIndexB Entropy Number Index for Class B
	 * 
	 * @return The Upper Bound for the Entropy Number of the Operator Custom Covering Number Metric Product
	 *  across both the Function Classes
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double CarlStephaniProductBound (
		final org.drip.spaces.cover.MaureyOperatorCoveringBounds mocbA,
		final org.drip.spaces.cover.MaureyOperatorCoveringBounds mocbB,
		final int iEntropyNumberIndexA,
		final int iEntropyNumberIndexB)
		throws java.lang.Exception
	{
		if (null == mocbA || null == mocbB)
			throw new java.lang.Exception
				("CoveringBoundsHelper::CarlStephaniProductBound => Invalid Maurey Bounds for the Function Class");

		return mocbA.entropyNumberUpperBound (iEntropyNumberIndexA) * mocbB.entropyNumberUpperBound
			(iEntropyNumberIndexB);
	}

	/**
	 * Compute the Upper Bound for the Entropy Number of the Operator Custom Covering Number Metric Product
	 *  across both the Function Classes using the Function Class Norm
	 * 
	 * @param mocbA The Maurey Operator Covering Bounds for Class A
	 * @param mocbB The Maurey Operator Covering Bounds for Class B
	 * @param dblNormA The Function Class A Norm
	 * @param dblNormB The Function Class B Norm
	 * @param iEntropyNumberIndex Entropy Number Index for either Class
	 * 
	 * @return The Upper Bound for the Entropy Number of the Operator Custom Covering Number Metric
	 * 	Product across both the Function Classes using the Function Norm
	 */

	public static final org.drip.spaces.cover.CarlStephaniNormedBounds CarlStephaniProductNorm (
		final org.drip.spaces.cover.MaureyOperatorCoveringBounds mocbA,
		final org.drip.spaces.cover.MaureyOperatorCoveringBounds mocbB,
		final double dblNormA,
		final double dblNormB,
		final int iEntropyNumberIndex)
	{
		if (null == mocbA || null == mocbB) return null;

		try {
			return new org.drip.spaces.cover.CarlStephaniNormedBounds (mocbA.entropyNumberUpperBound
				(iEntropyNumberIndex) * dblNormB, mocbB.entropyNumberUpperBound (iEntropyNumberIndex) *
					dblNormA);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
