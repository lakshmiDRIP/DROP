
package org.drip.spline.tension;

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
 * This class implements the basic framework and the family of C2 Tension Splines outlined in Koch and Lyche
 * 	(1989), Koch and Lyche (1993), and Kvasov (2000) Papers.
 * 
 * Currently, this class exposes functions to create monic and quadratic tension B Spline Basis Function Set.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class KochLycheKvasovBasis {

	/**
	 * Generate the Monic BSpline Basis Function Set
	 * 
	 * @param dblTension The Tension Parameter
	 * 
	 * @return The Monic BSpline Basis Function Set
	 */

	public static final org.drip.function.definition.R1ToR1[] GenerateMonicBSplineSet (
		final double dblTension)
	{
		try {
			return new org.drip.function.definition.R1ToR1[] {new
				org.drip.spline.tension.KLKHyperbolicTensionPhy (dblTension), new
					org.drip.spline.tension.KLKHyperbolicTensionPsy (dblTension)};
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Quadratic BSpline Basis Function Set
	 * 
	 * @param dblTension The Tension Parameter
	 * 
	 * @return The Quadratic BSpline Basis Function Set
	 */

	public static final org.drip.function.definition.R1ToR1[] GenerateQuadraticBSplineSet (
		final double dblTension)
	{
		try {
			return new org.drip.function.definition.R1ToR1[] {new
				org.drip.spline.tension.KLKHyperbolicTensionPhy (dblTension), new
					org.drip.spline.tension.KLKHyperbolicTensionPsy (dblTension)};
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
