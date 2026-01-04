
package org.drip.measure.gaussian;

import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * implements the Incremental, the Cumulative, and the Inverse Cumulative Distribution Densities. It
 * provides the following Functionality:
 *
 *  <ul>
 * 		<li>Retrieve the Density at the specified Point using Zero Mean and Unit Variance</li>
 * 		<li>Compute the Cumulative Distribution Function up to the specified Variate</li>
 * 		<li>Compute the Inverse CDF of the Distribution up to the specified Y</li>
 * 		<li>Compute the Probit of the Distribution up to the specified p</li>
 * 		<li>Generate a Random Univariate Number following a Gaussian Distribution</li>
 * 		<li>Compute the Error Function of x</li>
 * 		<li>Compute the Error Function Complement of x</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/gaussian/README.md">R<sup>1</sup> Covariant Gaussian Quadrature</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Robert Sedgewick
 * @author Lakshmi Krishnamurthy
 */

public class NormalQuadrature
{

	private static final double InverseCDF (
    	final double y,
    	final double tolerance,
    	final double lowCutoff,
    	final double highCutoff)
    	throws Exception
    {
        double mid = 0.5 * (highCutoff + lowCutoff);

        if (highCutoff - lowCutoff < tolerance) {
        	return mid;
        }

        return CDF (mid) > y ?
    		InverseCDF (y, tolerance, lowCutoff, mid) :
			InverseCDF (y, tolerance, mid, highCutoff);
    }

    /**
     * Retrieve the Density at the specified Point using Zero Mean and Unit Variance
     * 
     * @param x The Ordinate
     * 
     * @return The Density at the specified Point Zero Mean and Unit Variance
     * 
     * @throws Exception Thrown if Inputs are Invalid
     */

    public static final double Density (
    	final double x)
    	throws Exception
    {
    	if (!NumberUtil.IsValid (x)) {
    		throw new Exception ("NormalQuadrature::Density => Invalid Inputs");
    	}

    	return Math.exp (-0.5 * x * x) / Math.sqrt (2 * Math.PI);
    }

    /**
     * Compute the Cumulative Distribution Function up to the specified Variate
     * 
     * @param x The Variate
     * 
     * @return The Cumulative Distribution Function up to the specified Variate
     * 
     * @throws Exception thrown if the Inputs are Invalid
     */

    public static final double CDF (
    	final double x)
    	throws Exception
    {
    	if (Double.isNaN (x)) {
    		throw new Exception ("NormalQuadrature::CDF => Invalid Inputs");
    	}

        if (x < -8.) {
        	return 0.;
        }

        if (x > 8.) {
        	return 1.;
        }

        double sum = 0.;
        double term = x;

        for (int i = 3; sum + term != sum; i += 2) {
        	sum  = sum + term;
        	term = term * x * x / i;
        }

        return 0.5 + sum * Density (x);
    }

    /**
     * Compute the Inverse CDF of the Distribution up to the specified Y
     * 
     * @param y Y
     * 
     * @return The Inverse CDF of the Distribution up to the specified Y
     * 
     * @throws Exception Thrown if Inputs are Invalid
     */

    public static final double InverseCDF (
    	final double y)
    	throws Exception
    {
    	if (!NumberUtil.IsValid (y)) {
    		throw new Exception ("NormalQuadrature::InverseCDF => Invalid Inputs");
    	}

        return InverseCDF (y, .00000001, -8., 8.);
    } 

    /**
     * Compute the Probit of the Distribution up to the specified p
     * 
     * @param p p
     * 
     * @return The Probit of the Distribution up to the specified p
     * 
     * @throws Exception Thrown if Inputs are Invalid
     */

    public static final double Probit (
    	final double p)
    	throws Exception
    {
        return InverseCDF (p);
    } 

    /**
     * Generate a Random Univariate Number following a Gaussian Distribution
     * 
     * @return The Random Univariate Number
     * 
     * @throws Exception Thrown the Random Number cannot be generated
     */

    public static final double Random()
    	throws Exception
    {
    	return InverseCDF (Math.random());
    }

    /**
     * Compute the Error Function of x
     * 
     * @param x x
     * 
     * @return The Error Function of x
     * 
     * @throws Exception Thrown if the Inputs are Invalid
     */

    public static final double ERF (
    	final double x)
    	throws Exception
	{
    	return 2. * CDF (x * Math.sqrt (2.)) - 1.;
	}

    /**
     * Compute the Error Function Complement of x
     * 
     * @param x x
     * 
     * @return The Error Function Complement of x
     * 
     * @throws Exception Thrown if the Inputs are Invalid
     */

    public static final double ERFC (
    	final double x)
    	throws Exception
	{
    	return 2. * CDF (1. - x * Math.sqrt (2.));
	}
}
