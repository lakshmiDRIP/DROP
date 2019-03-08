
package org.drip.measure.gaussian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Robert Sedgewick
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/gaussian">Gaussian</a></li>
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
    	if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
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
    	if (!org.drip.quant.common.NumberUtil.IsValid (dblY))
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
