
package org.drip.measure.continuous;

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
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>R1Univariate</i> implements the Base Abstract Class behind R<sup>1</sup> Univariate Distributions. It
 * exports the Methods for incremental, cumulative, and inverse cumulative distribution densities.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous">Continuous</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class R1Univariate {

	/**
	 * Compute the cumulative under the distribution to the given value
	 * 
	 * @param dblX Variate to which the cumulative is to be computed
	 * 
	 * @return The cumulative
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public abstract double cumulative (
		final double dblX)
		throws java.lang.Exception;

	/**
	 * Compute the Incremental under the Distribution between the 2 variates
	 * 
	 * @param dblXLeft Left Variate to which the cumulative is to be computed
	 * @param dblXRight Right Variate to which the cumulative is to be computed
	 * 
	 * @return The Incremental under the Distribution between the 2 variates
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public double incremental (
		final double dblXLeft,
		final double dblXRight)
		throws java.lang.Exception
	{
		return cumulative (dblXRight) - cumulative (dblXLeft);
	}

	/**
	 * Compute the inverse cumulative under the distribution corresponding to the given value
	 * 
	 * @param dblX Value corresponding to which the inverse cumulative is to be computed
	 * 
	 * @return The inverse cumulative
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public abstract double invCumulative (
		final double dblX)
		throws java.lang.Exception;

	/**
	 * Compute the Density under the Distribution at the given Variate
	 * 
	 * @param dblX Variate at which the Density needs to be computed
	 * 
	 * @return The Density
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public abstract double density (
		final double dblX)
		throws java.lang.Exception;

	/**
	 * Retrieve the Mean of the Distribution
	 * 
	 * @return The Mean of the Distribution
	 */

	public abstract double mean();

	/**
	 * Retrieve the Variance of the Distribution
	 * 
	 * @return The Variance of the Distribution
	 */

	public abstract double variance();

	/**
	 * Generate a Random Variable corresponding to the Distribution
	 * 
	 * @return Random Variable corresponding to the Distribution
	 */

	public double random()
	{
		try
		{
			return invCumulative (java.lang.Math.random());
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return java.lang.Double.NaN;
	}

	/**
	 * Retrieve the Population Central Measures
	 * 
	 * @return The Population Central Measures
	 */

	public org.drip.measure.statistics.PopulationCentralMeasures populationCentralMeasures()
	{
		try
		{
			return new org.drip.measure.statistics.PopulationCentralMeasures (
				mean(),
				variance()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Univariate Weighted Histogram
	 * 
	 * @return The Univariate Weighted Histogram
	 */

	public abstract org.drip.numerical.common.Array2D histogram();
}
