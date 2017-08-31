
package org.drip.function.rdtor1solver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * InteriorPointBarrierControl contains the Barrier Iteration Control Parameters.
 *
 * @author Lakshmi Krishnamurthy
 */

public class InteriorPointBarrierControl extends org.drip.function.rdtor1solver.ConvergenceControl {
	private int _iNumDecaySteps = -1;
	private double _dblDecayVelocity = java.lang.Double.NaN;
	private double _dblInitialStrength = java.lang.Double.NaN;

	/**
	 * Construct a Standard InteriorPointBarrierControl Instance
	 * 
	 * @return The Standard InteriorPointBarrierControl Instance
	 */

	public static InteriorPointBarrierControl Standard()
	{
		try {
			return new InteriorPointBarrierControl (VARIATE_CONSTRAINT_SEQUENCE_CONVERGENCE, 5.0e-02,
				1.0e-06, 1.0e+04, 0.5, 70);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * InteriorPointBarrierControl Constructor
	 * 
	 * @param iConvergenceType The Convergence Type
	 * @param dblRelativeTolerance The Objective Function Relative Tolerance
	 * @param dblAbsoluteTolerance The Objective Function Absolute Tolerance
	 * @param dblInitialStrength The Initial Barrier Strength Level
	 * @param dblDecayVelocity The Barrier Decay Velocity
	 * @param iNumDecaySteps The Number Barrier Decay Steps
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public InteriorPointBarrierControl (
		final int iConvergenceType,
		final double dblRelativeTolerance,
		final double dblAbsoluteTolerance,
		final double dblInitialStrength,
		final double dblDecayVelocity,
		final int iNumDecaySteps)
		throws java.lang.Exception
	{
		super (iConvergenceType, dblRelativeTolerance, dblAbsoluteTolerance, 100);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblInitialStrength = dblInitialStrength) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblDecayVelocity = dblDecayVelocity) || 1 >
				(_iNumDecaySteps = iNumDecaySteps))
			throw new java.lang.Exception ("InteriorPointBarrierControl Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Number of Decay Steps
	 * 
	 * @return The Number of Decay Steps
	 */

	public int numDecaySteps()
	{
		return _iNumDecaySteps;
	}

	/**
	 * Retrieve the Initial Barrier Strength
	 * 
	 * @return The Initial Barrier Strength
	 */

	public double initialStrength()
	{
		return _dblInitialStrength;
	}

	/**
	 * Retrieve the Decay Velocity
	 * 
	 * @return The Decay Velocity
	 */

	public double decayVelocity()
	{
		return _dblDecayVelocity;
	}
}
