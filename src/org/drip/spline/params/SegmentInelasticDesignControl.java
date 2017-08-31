
package org.drip.spline.params;

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
 * SegmentInelasticDesignControl implements basis per-segment inelastic parameter set. It exports the
 *  following functionality:
 *  - Retrieve the Continuity Order.
 *  - Retrieve the Length Penalty and the Curvature Penalty Parameters.
 *  - Create the C2 Inelastic Design Parameters.
 *  - Create the Inelastic Design Parameters for the desired Ck Criterion and the Roughness Penalty Order.
 *
 * @author Lakshmi Krishnamurthy
 */

public class SegmentInelasticDesignControl {
	private int _iCk = -1;
	private org.drip.spline.params.SegmentFlexurePenaltyControl _sfpcLength = null;
	private org.drip.spline.params.SegmentFlexurePenaltyControl _sfpcCurvature = null;

	/**
	 * Create the C2 Inelastic Design Params
	 * 
	 * @return SegmentInelasticDesignControl instance
	 */

	public static final SegmentInelasticDesignControl MakeC2DesignInelasticControl()
	{
		try {
			return new SegmentInelasticDesignControl (2, null, new
				org.drip.spline.params.SegmentFlexurePenaltyControl (2, 1.));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create the Inelastic Design Parameters for the desired Ck Criterion and the Roughness Penalty Order
	 * 
	 * @param iCk Continuity Order
	 * @param iCurvaturePenaltyDerivativeOrder Curvature Penalty Derivative Order
	 * 
	 * @return SegmentInelasticDesignControl instance
	 */

	public static final SegmentInelasticDesignControl Create (
		final int iCk,
		final int iCurvaturePenaltyDerivativeOrder)
	{
		try {
			return new SegmentInelasticDesignControl (iCk, null, new
				org.drip.spline.params.SegmentFlexurePenaltyControl (iCurvaturePenaltyDerivativeOrder, 1.));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Constructor for the Segment Inelastic Design Parameters given the desired Ck, the Segment Length and
	 *  the Roughness Penalty Order
	 * 
	 * @param iCk Continuity Order
	 * @param sfpcLength Segment Length Penalty
	 * @param sfpcCurvature Segment Curvature Penalty
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public SegmentInelasticDesignControl (
		final int iCk,
		final org.drip.spline.params.SegmentFlexurePenaltyControl sfpcLength,
		final org.drip.spline.params.SegmentFlexurePenaltyControl sfpcCurvature)
		throws java.lang.Exception
	{
		if (0 > (_iCk = iCk))
			throw new java.lang.Exception ("SegmentInelasticDesignControl ctr: Invalid Inputs");

		_sfpcLength = sfpcLength;
		_sfpcCurvature = sfpcCurvature;
	}

	/**
	 * Retrieve the Continuity Order
	 * 
	 * @return The Continuity Order
	 */

	public int Ck()
	{
		return _iCk;
	}

	/**
	 * Retrieve the Length Penalty Parameters
	 * 
	 * @return The Length Penalty Parameters
	 */

	public org.drip.spline.params.SegmentFlexurePenaltyControl lengthPenaltyControl()
	{
		return _sfpcLength;
	}

	/**
	 * Retrieve the Curvature Penalty Parameters
	 * 
	 * @return The Curvature Penalty Parameters
	 */

	public org.drip.spline.params.SegmentFlexurePenaltyControl curvaturePenaltyControl()
	{
		return _sfpcCurvature;
	}
}
