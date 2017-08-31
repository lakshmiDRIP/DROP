
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
 * ScaleSensitiveCoveringBounds implements the Lower/Upper Bounds for the General Class of Functions in terms
 * 	of their scale-sensitive dimensions (i.e., the fat shattering coefficients).
 * 
 * The References are:
 * 
 * 	1) D. Pollard (1984): Convergence of Stochastic Processes, Springer, New York.
 * 
 * 	2) N. Alon, S. Ben-David, N. Cesa-Bianchi, and D. Haussler (1993): Scale-sensitive Dimensions, Uniform-
 * 		Convergence, and Learnability, Proceedings of the ACM Symposium on the Foundations of Computer
 * 		Science.
 * 
 * 	3) P. L. Bartlett, S. R. Kulkarni, and S. E. Posner (1997): Covering Numbers for Real-valued Function
 * 		Classes, IEEE Transactions on Information Theory 43 (5) 1721-1724.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ScaleSensitiveCoveringBounds implements org.drip.spaces.cover.FunctionClassCoveringBounds {
	private int _iSampleSize = -1;
	private org.drip.function.definition.R1ToR1 _r1r1FatShatter = null;

	/**
	 * ScaleSensitiveCoveringBounds Constructor
	 * 
	 * @param r1r1FatShatter The Cover Fat Shattering Coefficient Function
	 * @param iSampleSize Sample Size
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ScaleSensitiveCoveringBounds (
		final org.drip.function.definition.R1ToR1 r1r1FatShatter,
		final int iSampleSize)
		throws java.lang.Exception
	{
		if (null == (_r1r1FatShatter = r1r1FatShatter) || 0 >= (_iSampleSize = iSampleSize))
			throw new java.lang.Exception ("ScaleSensitiveCoveringBounds ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Fat Shattering Coefficient Function
	 * 
	 * @return The Fat Shattering Coefficient Function
	 */

	public org.drip.function.definition.R1ToR1 fatShatteringFunction()
	{
		return _r1r1FatShatter;
	}

	/**
	 * Retrieve the Sample Size
	 * 
	 * @return The Sample Size
	 */

	public int sampleSize()
	{
		return _iSampleSize;
	}

	/**
	 * Compute the Minimum Sample Size required to Estimate the Cardinality corresponding to the Specified
	 * 	Cover
	 * 
	 * @param dblCover The Cover
	 * 
	 * @return The Minimum Sample Size
	 * 
	 * @throws java.lang.Exception Thrown if the Minimum Sample Size Cannot be computed
	 */

	public double sampleSizeLowerBound (
		final double dblCover)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblCover) || 0. == dblCover)
			throw new java.lang.Exception
				("ScaleSensitiveCoveringBounds::sampleSizeLowerBound => Invalid Inputs");

		double dblLog2 = java.lang.Math.log (2.);

		return 2. * _r1r1FatShatter.evaluate (0.25 * dblCover) * java.lang.Math.log (64. * java.lang.Math.E *
			java.lang.Math.E / (dblCover * dblLog2)) / dblLog2;
	}

	/**
	 * Compute the Cardinality for the Subset T (|x) that possesses the Specified Cover for the Restriction
	 * 	of the Input Function Class Family F (|x).
	 *  
	 * @param dblCover The Specified Cover
	 * 
	 * @return The Restricted Subset Cardinality
	 * 
	 * @throws java.lang.Exception Thrown if the Restricted Subset Cardinality cannot be computed
	 */

	public double restrictedSubsetCardinality (
		final double dblCover)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblCover) || 0. == dblCover)
			throw new java.lang.Exception
				("ScaleSensitiveCoveringBounds::restrictedSubsetCardinality => Invalid Inputs");

		double dblLog2 = java.lang.Math.log (2.);

		double dblFatShatteringCoefficient = _r1r1FatShatter.evaluate (0.25 * dblCover);

		if (_iSampleSize < 2. * dblFatShatteringCoefficient * java.lang.Math.log (64. * java.lang.Math.E *
			java.lang.Math.E / (dblCover * dblLog2)) / dblLog2)
			throw new java.lang.Exception
				("ScaleSensitiveCoveringBounds::restrictedSubsetCardinality => Invalid Inputs");

		return 6. * dblFatShatteringCoefficient * java.lang.Math.log (16. / dblCover) * java.lang.Math.log
			(32. * java.lang.Math.E * _iSampleSize / (dblFatShatteringCoefficient * dblCover)) / dblLog2 +
				dblLog2;
	}

	/**
	 * Compute the Log of the Weight Loading Coefficient for the Maximum Cover Term in:
	 * 
	 * 	{Probability that the Empirical Error .gt. Cover} .lte. 4 * exp (-m * Cover^2 / 128) *
	 * 		[[Max Covering Number Over the Specified Sample]]
	 * 
	 * Reference is:
	 *
	 *	- D. Haussler (1995): Sphere Packing Numbers for Subsets of the Boolean n-Cube with Bounded
	 *		Vapnik-Chervonenkis Dimension, Journal of the COmbinatorial Theory A 69 (2) 217.
	 *
	 * @param dblCover The Specified Cover
	 * 
	 * @return Log of the Weight Loading Coefficient for the Maximum Cover Term
	 * 
	 * @throws java.lang.Exception Thrown if the Log of the Weight Loading Coefficient cannot be computed
	 */

	public double upperProbabilityBoundWeight (
		final double dblCover)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblCover) || 0. == dblCover)
			throw new java.lang.Exception
				("ScaleSensitiveCoveringBounds::upperProbabilityBoundWeight => Invalid Inputs");

		return java.lang.Math.log (4.) - (dblCover * dblCover * _iSampleSize / 128.);
	}

	@Override public double logLowerBound (
		final double dblCover)
		throws java.lang.Exception
	{
		return restrictedSubsetCardinality (dblCover);
	}

	@Override public double logUpperBound (
		final double dblCover)
		throws java.lang.Exception
	{
		return _r1r1FatShatter.evaluate (4. * dblCover) / 32.;
	}
}
