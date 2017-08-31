
package org.drip.measure.gaussian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Robert Sedgewick
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
 * NormalQuadrature implements the Quadrature Metrics behind the Univariate Normal Distribution. It
 *  implements the Incremental, the Cumulative, and the Inverse Cumulative Distribution Densities.
 *
 * @author Robert Sedgewick
 */

public class NormalQuadrature {
    private static final double InverseCDF (
    	final double dblY,
    	final double dblTolerance,
    	final double dblLowCutoff,
    	final double dblHighCutoff)
    	throws java.lang.Exception
    {
        double dblMid = 0.5 * (dblHighCutoff + dblLowCutoff);

        if (dblHighCutoff - dblLowCutoff < dblTolerance) return dblMid;

        return CDF (dblMid) > dblY ? InverseCDF (dblY, dblTolerance, dblLowCutoff, dblMid) : InverseCDF
        	(dblY, dblTolerance, dblMid, dblHighCutoff);
    }

    /**
     * Retrieve the Density at the specified Point using Zero Mean and Unit Variance
     * 
     * @param dblX The Ordinate
     * 
     * @return The Density at the specified Point Zero Mean and Unit Variance
     * 
     * @throws java.lang.Exception Thrown if Inputs are Invalid
     */

    public static final double Density (
    	final double dblX)
    	throws java.lang.Exception
    {
    	if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
    		throw new java.lang.Exception ("NormalQuadrature::Density => Invalid Inputs");

    	return java.lang.Math.exp (-0.5 * dblX * dblX) / java.lang.Math.sqrt (2 * java.lang.Math.PI);
    }

    /**
     * Compute the Cumulative Distribution Function up to the specified variate
     * 
     * @param dblX The Variate
     * 
     * @return The Cumulative Distribution Function up to the specified variate
     * 
     * @throws java.lang.Exception thrown if the Inputs are Invalid
     */

    public static final double CDF (
    	final double dblX)
    	throws java.lang.Exception
    {
    	if (java.lang.Double.isNaN (dblX))
    		throw new java.lang.Exception ("NormalQuadrature::CDF => Invalid Inputs");

        if (dblX < -8.) return 0.;

        if (dblX > 8.) return 1.;

        double dblSum = 0.;
        double dblTerm = dblX;

        for (int i = 3; dblSum + dblTerm != dblSum; i += 2) {
        	dblSum  = dblSum + dblTerm;
        	dblTerm = dblTerm * dblX * dblX / i;
        }

        return 0.5 + dblSum * Density (dblX);
    }

    /**
     * Compute the Inverse CDF of the Distribution up to the specified Y
     * 
     * @param dblY Y
     * 
     * @return The Inverse CDF of the Distribution up to the specified Y
     * 
     * @throws java.lang.Exception Thrown if Inputs are Invalid
     */

    public static final double InverseCDF (
    	final double dblY)
    	throws java.lang.Exception
    {
    	if (!org.drip.quant.common.NumberUtil.IsValid (dblY))
    		throw new java.lang.Exception ("NormalQuadrature::InverseCDF => Invalid Inputs");

        return InverseCDF (dblY, .00000001, -8., 8.);
    } 

    /**
     * Generate a Random Univariate Number following a Gaussian Distribution
     * 
     * @return The Random Univariate Number
     * 
     * @throws java.lang.Exception Thrown the Random Number cannot be generated
     */

    public static final double Random()
    	throws java.lang.Exception
    {
    	return InverseCDF (java.lang.Math.random());
    }
}
