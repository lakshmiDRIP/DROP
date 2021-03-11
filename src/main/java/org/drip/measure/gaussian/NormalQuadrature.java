
package org.drip.measure.gaussian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Robert Sedgewick
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>NormalQuadrature</i> implements the Quadrature Metrics behind the Univariate Normal Distribution. It
 * implements the Incremental, the Cumulative, and the Inverse Cumulative Distribution Densities.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/gaussian/README.md">R<sup>1</sup> R<sup>d</sup> Covariant Gaussian Quadrature</a></li>
 *  </ul>
 *
 * @author Robert Sedgewick
 * @author Lakshmi Krishnamurthy
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
    	if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
    		throw new java.lang.Exception ("NormalQuadrature::Density => Invalid Inputs");

    	return java.lang.Math.exp (-0.5 * dblX * dblX) / java.lang.Math.sqrt (2 * java.lang.Math.PI);
    }

    /**
     * Compute the Cumulative Distribution Function up to the specified Variate
     * 
     * @param dblX The Variate
     * 
     * @return The Cumulative Distribution Function up to the specified Variate
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
    	if (!org.drip.numerical.common.NumberUtil.IsValid (dblY))
    		throw new java.lang.Exception ("NormalQuadrature::InverseCDF => Invalid Inputs");

        return InverseCDF (dblY, .00000001, -8., 8.);
    } 

    /**
     * Compute the Probit of the Distribution up to the specified p
     * 
     * @param p p
     * 
     * @return The Probit of the Distribution up to the specified p
     * 
     * @throws java.lang.Exception Thrown if Inputs are Invalid
     */

    public static final double Probit (
    	final double p)
    	throws java.lang.Exception
    {
        return InverseCDF (p);
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

    /**
     * Compute the Error Function of x
     * 
     * @param x x
     * 
     * @return The Error Function of x
     * 
     * @throws java.lang.Exception Thrown if the Inputs are Invalid
     */

    public static final double ERF (
    	final double x)
    	throws java.lang.Exception
	{
    	return 2. * CDF (x * java.lang.Math.sqrt (2.)) - 1.;
	}

    /**
     * Compute the Error Function Complement of x
     * 
     * @param x x
     * 
     * @return The Error Function Complement of x
     * 
     * @throws java.lang.Exception Thrown if the Inputs are Invalid
     */

    public static final double ERFC (
    	final double x)
    	throws java.lang.Exception
	{
    	return 2. * CDF (1. - x * java.lang.Math.sqrt (2.));
	}
}
