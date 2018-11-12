
package org.drip.measure.stochastic;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>R1R1ToR1</i> interface exposes the stubs for the evaluation of the objective function and its
 * derivatives for a R<sup>1</sup> Deterministic + R<sup>1</sup> Random To R<sup>1</sup> Stochastic Function
 * with one Random Component.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/stochastic">Stochastic</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public interface R1R1ToR1 {

	/**
	 * Evaluate a Single Realization for the given variate
	 * 
	 * @param dblVariate Variate
	 *  
	 * @return Return the Single Realization for the given variate
	 * 
	 * @throws java.lang.Exception Thrown if evaluation cannot be done
	 */

	public abstract double evaluateRealization (
		final double dblVariate)
		throws java.lang.Exception;

	/**
	 * Evaluate the Expectation for the given variate
	 * 
	 * @param dblVariate Variate
	 *  
	 * @return Return the Expectation for the given variate
	 * 
	 * @throws java.lang.Exception Thrown if evaluation cannot be done
	 */

	public abstract double evaluateExpectation (
		final double dblVariate)
		throws java.lang.Exception;

	/**
	 * Evaluate the Derivative for a Single Realization for the given variate
	 * 
	 * @param dblVariate Variate at which the derivative is to be calculated
	 * @param iOrder Order of the derivative to be computed
	 *  
	 * @return Return the Derivative for a Single Realization for the given variate
	 * 
	 * @throws java.lang.Exception Thrown if evaluation cannot be done
	 */

	public abstract double derivativeRealization (
		final double dblVariate,
		final int iOrder)
		throws java.lang.Exception;

	/**
	 * Evaluate the Derivative Expectation at the given variate
	 * 
	 * @param dblVariate Variate at which the derivative is to be calculated
	 * @param iOrder Order of the derivative to be computed
	 *  
	 * @return Return the Derivative Expectation at the given variate
	 * 
	 * @throws java.lang.Exception Thrown if evaluation cannot be done
	 */

	public abstract double derivativeExpectation (
		final double dblVariate,
		final int iOrder)
		throws java.lang.Exception;

	/**
	 * Evaluate a Path-wise Integral between the Vriates
	 * 
	 * @param dblStart Variate Start
	 * @param dblEnd Variate End
	 *  
	 * @return The Path-wise Integral between the Variates
	 * 
	 * @throws java.lang.Exception Thrown if evaluation cannot be done
	 */

	public abstract double integralRealization (
		final double dblStart,
		final double dblEnd)
		throws java.lang.Exception;

	/**
	 * Evaluate the Expected Path-wise Integral between the Vriates
	 * 
	 * @param dblStart Variate Start
	 * @param dblEnd Variate End
	 *  
	 * @return The Expected Path-wise Integral between the Variates
	 * 
	 * @throws java.lang.Exception Thrown if evaluation cannot be done
	 */

	public abstract double integralExpectation (
		final double dblStart,
		final double dblEnd)
		throws java.lang.Exception;
}
