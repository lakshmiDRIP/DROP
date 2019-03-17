
package org.drip.function.e2erf;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>MacLaurinSeriesGenerator</i> implements the E<sub>2</sub> MacLaurin Series Term Generator. The
 * References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Chang, S. H., P. C. Cosman, L. B. Milstein (2011): Chernoff-Type Bounds for Gaussian Error
 * 				Function <i>IEEE Transactions on Communications</i> <b>59 (11)</b> 2939-2944
 * 		</li>
 * 		<li>
 * 			Cody, W. J. (1991): Algorithm 715: SPECFUN – A Portable FORTRAN Package of Special Function
 * 				Routines and Test Drivers <i>ACM Transactions on Mathematical Software</i> <b>19 (1)</b>
 * 				22-32
 * 		</li>
 * 		<li>
 * 			Schopf, H. M., and P. H. Supancic (2014): On Burmann’s Theorem and its Application to Problems of
 * 				Linear and Non-linear Heat Transfer and Diffusion
 * 				https://www.mathematica-journal.com/2014/11/on-burmanns-theorem-and-its-application-to-problems-of-linear-and-nonlinear-heat-transfer-and-diffusion/#more-39602/
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Error Function https://en.wikipedia.org/wiki/Error_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/e2erf/README.md">E<sub>2</sub> erf and erf<sup>-1</sup> Implementations</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MacLaurinSeriesGenerator extends org.drip.numerical.estimation.R1ToR1SeriesGenerator
{

	/**
	 * Generate the ERFI E<sub>2</sub> MacLaurin Coefficient corresponding to the specified Series Index
	 * 
	 * @param seriesIndex Series Index
	 * 
	 * @return The ERFI E<sub>2</sub> MacLaurin Coefficient corresponding to the specified Series Index
	 */

	public static final double ERFICoefficient (
		final int seriesIndex)
	{
		if (0 >= seriesIndex)
		{
			return 1.;
		}

		double seriesIndexLoader = 0.;

		for (int termIndex = 0; termIndex < seriesIndex; ++termIndex)
		{
			seriesIndexLoader = seriesIndexLoader +
				ERFICoefficient (termIndex) * ERFICoefficient (seriesIndex - 1 - termIndex)
				/ ((1. + termIndex) * (1. + 2. * termIndex));
		}

		return seriesIndexLoader;
	}

	/**
	 * Construct the E<sub>2</sub> erf MacLaurin Series Generator Version
	 * 
	 * @param termCount Count of the Number of Terms
	 * 
	 * @return E<sub>2</sub> erf MacLaurin Series Generator Version
	 */

	public static final MacLaurinSeriesGenerator ERF (
		final int termCount)
	{
		java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		double signedInverseFactorial = 1.;

		for (int termIndex = 0; termIndex <= termCount; ++termIndex)
		{
			signedInverseFactorial = 0 == termIndex ? 1. : signedInverseFactorial * -1. / termIndex;

			termWeightMap.put (
				termIndex,
				signedInverseFactorial / (2. * termIndex + 1.)
			);
		}

		try
		{
			return new MacLaurinSeriesGenerator (
				new org.drip.function.e2erf.MacLaurinSeriesTerm(),
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the E<sub>2</sub> erfi MacLaurin Series Generator Version
	 * 
	 * @param termCount Count of the Number of Terms
	 * 
	 * @return The E<sub>2</sub> erfi MacLaurin Series Generator Version
	 */

	public static final MacLaurinSeriesGenerator ERFI (
		final int termCount)
	{
		java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		double sqrtPIOver2 = 0.5 * java.lang.Math.sqrt (java.lang.Math.PI);

		for (int termIndex = 0; termIndex <= termCount; ++termIndex)
		{
			int twoKPlusOne = 2 * termIndex + 1;

			termWeightMap.put (
				termIndex,
				(termIndex % 2 == 0 ? 1. : -1.) * ERFICoefficient (termIndex) * java.lang.Math.pow (
					sqrtPIOver2,
					twoKPlusOne
				) / twoKPlusOne
			);
		}

		try
		{
			return new MacLaurinSeriesGenerator (
				new org.drip.function.e2erf.MacLaurinSeriesTerm(),
				termWeightMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * MacLaurinSeriesGenerator Constructor
	 * 
	 * @param macLaurinSeriesGenerator E<sub>2</sub> erf MacLaurin Series Term
	 * @param termWeightMap Series Term Weight Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MacLaurinSeriesGenerator (
		final org.drip.function.e2erf.MacLaurinSeriesTerm macLaurinSeriesGenerator,
		final java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap)
		throws java.lang.Exception
	{
		super (
			macLaurinSeriesGenerator,
			false,
			termWeightMap
		);
	}
}
